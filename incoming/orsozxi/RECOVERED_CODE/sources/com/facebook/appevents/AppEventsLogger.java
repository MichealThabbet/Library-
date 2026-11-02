package com.facebook.appevents;

import android.app.Activity;
import android.app.Application;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import bolts.AppLinks;
import com.facebook.AccessToken;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.HttpMethod;
import com.facebook.LoggingBehavior;
import com.facebook.appevents.internal.ActivityLifecycleTracker;
import com.facebook.appevents.internal.AutomaticAnalyticsLogger;
import com.facebook.internal.AnalyticsEvents;
import com.facebook.internal.AttributionIdentifiers;
import com.facebook.internal.BundleJSONConverter;
import com.facebook.internal.FetchedAppSettingsManager;
import com.facebook.internal.Logger;
import com.facebook.internal.Utility;
import com.facebook.internal.Validate;
import java.io.BufferedOutputStream;
import java.io.ObjectOutputStream;
import java.math.BigDecimal;
import java.util.Currency;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.Executor;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes.dex */
public class AppEventsLogger {
    public static final String ACTION_APP_EVENTS_FLUSHED = "com.facebook.sdk.APP_EVENTS_FLUSHED";
    public static final String APP_EVENTS_EXTRA_FLUSH_RESULT = "com.facebook.sdk.APP_EVENTS_FLUSH_RESULT";
    public static final String APP_EVENTS_EXTRA_NUM_EVENTS_FLUSHED = "com.facebook.sdk.APP_EVENTS_NUM_EVENTS_FLUSHED";
    private static final String APP_EVENT_NAME_PUSH_OPENED = "fb_mobile_push_opened";
    public static final String APP_EVENT_PREFERENCES = "com.facebook.sdk.appEventPreferences";
    private static final String APP_EVENT_PUSH_PARAMETER_ACTION = "fb_push_action";
    private static final String APP_EVENT_PUSH_PARAMETER_CAMPAIGN = "fb_push_campaign";
    private static final int APP_SUPPORTS_ATTRIBUTION_ID_RECHECK_PERIOD_IN_SECONDS = 86400;
    private static final int FLUSH_APP_SESSION_INFO_IN_SECONDS = 30;
    private static final String PUSH_PAYLOAD_CAMPAIGN_KEY = "campaign";
    private static final String PUSH_PAYLOAD_KEY = "fb_push_payload";
    private static final String SOURCE_APPLICATION_HAS_BEEN_SET_BY_THIS_INTENT = "_fbSourceApplicationHasBeenSet";
    private static String anonymousAppDeviceGUID;
    private static ScheduledThreadPoolExecutor backgroundExecutor;
    private static boolean isActivateAppEventRequested;
    private static boolean isOpenedByAppLink;
    private static String pushNotificationsRegistrationId;
    private static String sourceApplication;
    private final AccessTokenAppIdPair accessTokenAppId;
    private final String contextName;
    private static final String TAG = AppEventsLogger.class.getCanonicalName();
    private static FlushBehavior flushBehavior = FlushBehavior.AUTO;
    private static Object staticLock = new Object();

    public enum FlushBehavior {
        AUTO,
        EXPLICIT_ONLY
    }

    public static void activateApp(Application application) {
        activateApp(application, (String) null);
    }

    public static void activateApp(Application application, String str) {
        if (!FacebookSdk.isInitialized()) {
            throw new FacebookException("The Facebook sdk must be initialized before calling activateApp");
        }
        AnalyticsUserIDStore.initStore();
        if (str == null) {
            str = FacebookSdk.getApplicationId();
        }
        FacebookSdk.publishInstallAsync(application, str);
        ActivityLifecycleTracker.startTracking(application, str);
    }

    @Deprecated
    public static void activateApp(Context context) {
        if (ActivityLifecycleTracker.isTracking()) {
            Log.w(TAG, "activateApp events are being logged automatically. There's no need to call activateApp explicitly, this is safe to remove.");
        } else {
            FacebookSdk.sdkInitialize(context);
            activateApp(context, Utility.getMetadataApplicationId(context));
        }
    }

    @Deprecated
    public static void activateApp(Context context, String str) {
        if (ActivityLifecycleTracker.isTracking()) {
            Log.w(TAG, "activateApp events are being logged automatically. There's no need to call activateApp explicitly, this is safe to remove.");
            return;
        }
        if (context == null || str == null) {
            throw new IllegalArgumentException("Both context and applicationId must be non-null");
        }
        AnalyticsUserIDStore.initStore();
        if (context instanceof Activity) {
            setSourceApplication((Activity) context);
        } else {
            resetSourceApplication();
            Log.d(AppEventsLogger.class.getName(), "To set source application the context of activateApp must be an instance of Activity");
        }
        FacebookSdk.publishInstallAsync(context, str);
        AppEventsLogger appEventsLogger = new AppEventsLogger(context, str, (AccessToken) null);
        final long jCurrentTimeMillis = System.currentTimeMillis();
        final String sourceApplication2 = getSourceApplication();
        backgroundExecutor.execute(new Runnable() { // from class: com.facebook.appevents.AppEventsLogger.1
            @Override // java.lang.Runnable
            public void run() {
                AppEventsLogger.this.logAppSessionResumeEvent(jCurrentTimeMillis, sourceApplication2);
            }
        });
    }

    @Deprecated
    public static void deactivateApp(Context context) {
        if (ActivityLifecycleTracker.isTracking()) {
            Log.w(TAG, "deactivateApp events are being logged automatically. There's no need to call deactivateApp, this is safe to remove.");
        } else {
            deactivateApp(context, Utility.getMetadataApplicationId(context));
        }
    }

    @Deprecated
    public static void deactivateApp(Context context, String str) {
        if (ActivityLifecycleTracker.isTracking()) {
            Log.w(TAG, "deactivateApp events are being logged automatically. There's no need to call deactivateApp, this is safe to remove.");
            return;
        }
        if (context == null || str == null) {
            throw new IllegalArgumentException("Both context and applicationId must be non-null");
        }
        resetSourceApplication();
        AppEventsLogger appEventsLogger = new AppEventsLogger(context, str, (AccessToken) null);
        final long jCurrentTimeMillis = System.currentTimeMillis();
        backgroundExecutor.execute(new Runnable() { // from class: com.facebook.appevents.AppEventsLogger.2
            @Override // java.lang.Runnable
            public void run() {
                AppEventsLogger.this.logAppSessionSuspendEvent(jCurrentTimeMillis);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void logAppSessionResumeEvent(long j, String str) {
        PersistedAppSessionInfo.onResume(FacebookSdk.getApplicationContext(), this.accessTokenAppId, this, j, str);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void logAppSessionSuspendEvent(long j) {
        PersistedAppSessionInfo.onSuspend(FacebookSdk.getApplicationContext(), this.accessTokenAppId, this, j);
    }

    public static void initializeLib(Context context, String str) {
        backgroundExecutor.execute(new Runnable() { // from class: com.facebook.appevents.AppEventsLogger.3
            @Override // java.lang.Runnable
            public void run() throws ClassNotFoundException {
                Bundle bundle = new Bundle();
                try {
                    Class.forName("com.facebook.core.Core");
                    bundle.putInt("core_lib_included", 1);
                } catch (ClassNotFoundException unused) {
                }
                try {
                    Class.forName("com.facebook.login.Login");
                    bundle.putInt("login_lib_included", 1);
                } catch (ClassNotFoundException unused2) {
                }
                try {
                    Class.forName("com.facebook.share.Share");
                    bundle.putInt("share_lib_included", 1);
                } catch (ClassNotFoundException unused3) {
                }
                try {
                    Class.forName("com.facebook.places.Places");
                    bundle.putInt("places_lib_included", 1);
                } catch (ClassNotFoundException unused4) {
                }
                try {
                    Class.forName("com.facebook.messenger.Messenger");
                    bundle.putInt("messenger_lib_included", 1);
                } catch (ClassNotFoundException unused5) {
                }
                try {
                    Class.forName("com.facebook.applinks.AppLinks");
                    bundle.putInt("applinks_lib_included", 1);
                } catch (ClassNotFoundException unused6) {
                }
                try {
                    Class.forName("com.facebook.all.All");
                    bundle.putInt("all_lib_included", 1);
                } catch (ClassNotFoundException unused7) {
                }
                AppEventsLogger.this.logSdkEvent(AnalyticsEvents.EVENT_SDK_INITIALIZE, null, bundle);
            }
        });
    }

    public static AppEventsLogger newLogger(Context context) {
        return new AppEventsLogger(context, (String) null, (AccessToken) null);
    }

    public static AppEventsLogger newLogger(Context context, AccessToken accessToken) {
        return new AppEventsLogger(context, (String) null, accessToken);
    }

    public static AppEventsLogger newLogger(Context context, String str, AccessToken accessToken) {
        return new AppEventsLogger(context, str, accessToken);
    }

    public static AppEventsLogger newLogger(Context context, String str) {
        return new AppEventsLogger(context, str, (AccessToken) null);
    }

    public static FlushBehavior getFlushBehavior() {
        FlushBehavior flushBehavior2;
        synchronized (staticLock) {
            flushBehavior2 = flushBehavior;
        }
        return flushBehavior2;
    }

    public static void setFlushBehavior(FlushBehavior flushBehavior2) {
        synchronized (staticLock) {
            flushBehavior = flushBehavior2;
        }
    }

    public void logEvent(String str) {
        logEvent(str, (Bundle) null);
    }

    public void logEvent(String str, double d) {
        logEvent(str, d, (Bundle) null);
    }

    public void logEvent(String str, Bundle bundle) {
        logEvent(str, null, bundle, false, ActivityLifecycleTracker.getCurrentSessionGuid());
    }

    public void logEvent(String str, double d, Bundle bundle) {
        logEvent(str, Double.valueOf(d), bundle, false, ActivityLifecycleTracker.getCurrentSessionGuid());
    }

    public void logPurchase(BigDecimal bigDecimal, Currency currency) {
        if (AutomaticAnalyticsLogger.isImplicitPurchaseLoggingEnabled()) {
            Log.w(TAG, "You are logging purchase events while auto-logging of in-app purchase is enabled in the SDK. Make sure you don't log duplicate events");
        }
        logPurchase(bigDecimal, currency, null, false);
    }

    public void logPurchase(BigDecimal bigDecimal, Currency currency, Bundle bundle) {
        if (AutomaticAnalyticsLogger.isImplicitPurchaseLoggingEnabled()) {
            Log.w(TAG, "You are logging purchase events while auto-logging of in-app purchase is enabled in the SDK. Make sure you don't log duplicate events");
        }
        logPurchase(bigDecimal, currency, bundle, false);
    }

    public void logPurchaseImplicitly(BigDecimal bigDecimal, Currency currency, Bundle bundle) {
        logPurchase(bigDecimal, currency, bundle, true);
    }

    private void logPurchase(BigDecimal bigDecimal, Currency currency, Bundle bundle, boolean z) {
        if (bigDecimal == null) {
            notifyDeveloperError("purchaseAmount cannot be null");
            return;
        }
        if (currency == null) {
            notifyDeveloperError("currency cannot be null");
            return;
        }
        if (bundle == null) {
            bundle = new Bundle();
        }
        Bundle bundle2 = bundle;
        bundle2.putString(AppEventsConstants.EVENT_PARAM_CURRENCY, currency.getCurrencyCode());
        logEvent(AppEventsConstants.EVENT_NAME_PURCHASED, Double.valueOf(bigDecimal.doubleValue()), bundle2, z, ActivityLifecycleTracker.getCurrentSessionGuid());
        eagerFlush();
    }

    public void logPushNotificationOpen(Bundle bundle) throws JSONException {
        logPushNotificationOpen(bundle, null);
    }

    public void logPushNotificationOpen(Bundle bundle, String str) throws JSONException {
        String string;
        String string2;
        try {
            string2 = bundle.getString(PUSH_PAYLOAD_KEY);
        } catch (JSONException unused) {
            string = null;
        }
        if (Utility.isNullOrEmpty(string2)) {
            return;
        }
        string = new JSONObject(string2).getString("campaign");
        if (string == null) {
            Logger.log(LoggingBehavior.DEVELOPER_ERRORS, TAG, "Malformed payload specified for logging a push notification open.");
            return;
        }
        Bundle bundle2 = new Bundle();
        bundle2.putString(APP_EVENT_PUSH_PARAMETER_CAMPAIGN, string);
        if (str != null) {
            bundle2.putString(APP_EVENT_PUSH_PARAMETER_ACTION, str);
        }
        logEvent(APP_EVENT_NAME_PUSH_OPENED, bundle2);
    }

    public void flush() {
        AppEventQueue.flush(FlushReason.EXPLICIT);
    }

    public static void onContextStop() {
        AppEventQueue.persistToDisk();
    }

    public boolean isValidForAccessToken(AccessToken accessToken) {
        return this.accessTokenAppId.equals(new AccessTokenAppIdPair(accessToken));
    }

    public static void setPushNotificationsRegistrationId(String str) {
        synchronized (staticLock) {
            if (!Utility.stringsEqualOrEmpty(pushNotificationsRegistrationId, str)) {
                pushNotificationsRegistrationId = str;
                AppEventsLogger appEventsLoggerNewLogger = newLogger(FacebookSdk.getApplicationContext());
                appEventsLoggerNewLogger.logEvent(AppEventsConstants.EVENT_NAME_PUSH_TOKEN_OBTAINED);
                if (getFlushBehavior() != FlushBehavior.EXPLICIT_ONLY) {
                    appEventsLoggerNewLogger.flush();
                }
            }
        }
    }

    static String getPushNotificationsRegistrationId() {
        String str;
        synchronized (staticLock) {
            str = pushNotificationsRegistrationId;
        }
        return str;
    }

    public static void setUserID(String str) {
        AnalyticsUserIDStore.setUserID(str);
    }

    public static String getUserID() {
        return AnalyticsUserIDStore.getUserID();
    }

    public static void clearUserID() {
        AnalyticsUserIDStore.setUserID(null);
    }

    public static void updateUserProperties(Bundle bundle, GraphRequest.Callback callback) {
        updateUserProperties(bundle, FacebookSdk.getApplicationId(), callback);
    }

    public static void updateUserProperties(final Bundle bundle, final String str, final GraphRequest.Callback callback) {
        final String userID = getUserID();
        if (userID == null || userID.isEmpty()) {
            Logger.log(LoggingBehavior.APP_EVENTS, TAG, "AppEventsLogger userID cannot be null or empty");
        } else {
            getAnalyticsExecutor().execute(new Runnable() { // from class: com.facebook.appevents.AppEventsLogger.4
                @Override // java.lang.Runnable
                public void run() {
                    Bundle bundle2 = new Bundle();
                    bundle2.putString("user_unique_id", userID);
                    bundle2.putBundle("custom_data", bundle);
                    AttributionIdentifiers attributionIdentifiers = AttributionIdentifiers.getAttributionIdentifiers(FacebookSdk.getApplicationContext());
                    if (attributionIdentifiers != null && attributionIdentifiers.getAndroidAdvertiserId() != null) {
                        bundle2.putString("advertiser_id", attributionIdentifiers.getAndroidAdvertiserId());
                    }
                    Bundle bundle3 = new Bundle();
                    try {
                        JSONObject jSONObjectConvertToJSON = BundleJSONConverter.convertToJSON(bundle2);
                        JSONArray jSONArray = new JSONArray();
                        jSONArray.put(jSONObjectConvertToJSON);
                        bundle3.putString("data", jSONArray.toString());
                        GraphRequest graphRequest = new GraphRequest(AccessToken.getCurrentAccessToken(), String.format(Locale.US, "%s/user_properties", str), bundle3, HttpMethod.POST, callback);
                        graphRequest.setSkipClientToken(true);
                        graphRequest.executeAsync();
                    } catch (JSONException e) {
                        throw new FacebookException("Failed to construct request", e);
                    }
                }
            });
        }
    }

    public void logSdkEvent(String str, Double d, Bundle bundle) {
        logEvent(str, d, bundle, true, ActivityLifecycleTracker.getCurrentSessionGuid());
    }

    public String getApplicationId() {
        return this.accessTokenAppId.getApplicationId();
    }

    private AppEventsLogger(Context context, String str, AccessToken accessToken) {
        this(Utility.getActivityName(context), str, accessToken);
    }

    protected AppEventsLogger(String str, String str2, AccessToken accessToken) {
        Validate.sdkInitialized();
        this.contextName = str;
        accessToken = accessToken == null ? AccessToken.getCurrentAccessToken() : accessToken;
        if (accessToken != null && (str2 == null || str2.equals(accessToken.getApplicationId()))) {
            this.accessTokenAppId = new AccessTokenAppIdPair(accessToken);
        } else {
            this.accessTokenAppId = new AccessTokenAppIdPair(null, str2 == null ? Utility.getMetadataApplicationId(FacebookSdk.getApplicationContext()) : str2);
        }
        initializeTimersIfNeeded();
    }

    private static void initializeTimersIfNeeded() {
        synchronized (staticLock) {
            if (backgroundExecutor != null) {
                return;
            }
            backgroundExecutor = new ScheduledThreadPoolExecutor(1);
            backgroundExecutor.scheduleAtFixedRate(new Runnable() { // from class: com.facebook.appevents.AppEventsLogger.5
                @Override // java.lang.Runnable
                public void run() {
                    HashSet hashSet = new HashSet();
                    Iterator<AccessTokenAppIdPair> it2 = AppEventQueue.getKeySet().iterator();
                    while (it2.hasNext()) {
                        hashSet.add(it2.next().getApplicationId());
                    }
                    Iterator it3 = hashSet.iterator();
                    while (it3.hasNext()) {
                        FetchedAppSettingsManager.queryAppSettings((String) it3.next(), true);
                    }
                }
            }, 0L, 86400L, TimeUnit.SECONDS);
        }
    }

    private void logEvent(String str, Double d, Bundle bundle, boolean z, UUID uuid) {
        try {
            logEvent(FacebookSdk.getApplicationContext(), new AppEvent(this.contextName, str, d, bundle, z, uuid), this.accessTokenAppId);
        } catch (FacebookException e) {
            Logger.log(LoggingBehavior.APP_EVENTS, "AppEvents", "Invalid app event: %s", e.toString());
        } catch (JSONException e2) {
            Logger.log(LoggingBehavior.APP_EVENTS, "AppEvents", "JSON encoding for app event failed: '%s'", e2.toString());
        }
    }

    private static void logEvent(Context context, AppEvent appEvent, AccessTokenAppIdPair accessTokenAppIdPair) {
        AppEventQueue.add(accessTokenAppIdPair, appEvent);
        if (appEvent.getIsImplicit() || isActivateAppEventRequested) {
            return;
        }
        if (appEvent.getName() == AppEventsConstants.EVENT_NAME_ACTIVATED_APP) {
            isActivateAppEventRequested = true;
        } else {
            Logger.log(LoggingBehavior.APP_EVENTS, "AppEvents", "Warning: Please call AppEventsLogger.activateApp(...)from the long-lived activity's onResume() methodbefore logging other app events.");
        }
    }

    static void eagerFlush() {
        if (getFlushBehavior() != FlushBehavior.EXPLICIT_ONLY) {
            AppEventQueue.flush(FlushReason.EAGER_FLUSHING_EVENT);
        }
    }

    private static void notifyDeveloperError(String str) {
        Logger.log(LoggingBehavior.DEVELOPER_ERRORS, "AppEvents", str);
    }

    private static void setSourceApplication(Activity activity) {
        ComponentName callingActivity = activity.getCallingActivity();
        if (callingActivity != null) {
            String packageName = callingActivity.getPackageName();
            if (packageName.equals(activity.getPackageName())) {
                resetSourceApplication();
                return;
            }
            sourceApplication = packageName;
        }
        Intent intent = activity.getIntent();
        if (intent == null || intent.getBooleanExtra(SOURCE_APPLICATION_HAS_BEEN_SET_BY_THIS_INTENT, false)) {
            resetSourceApplication();
            return;
        }
        Bundle appLinkData = AppLinks.getAppLinkData(intent);
        if (appLinkData == null) {
            resetSourceApplication();
            return;
        }
        isOpenedByAppLink = true;
        Bundle bundle = appLinkData.getBundle("referer_app_link");
        if (bundle == null) {
            sourceApplication = null;
        } else {
            sourceApplication = bundle.getString("package");
            intent.putExtra(SOURCE_APPLICATION_HAS_BEEN_SET_BY_THIS_INTENT, true);
        }
    }

    static void setSourceApplication(String str, boolean z) {
        sourceApplication = str;
        isOpenedByAppLink = z;
    }

    static String getSourceApplication() {
        String str = isOpenedByAppLink ? "Applink" : "Unclassified";
        if (sourceApplication == null) {
            return str;
        }
        return str + "(" + sourceApplication + ")";
    }

    static void resetSourceApplication() {
        sourceApplication = null;
        isOpenedByAppLink = false;
    }

    static Executor getAnalyticsExecutor() {
        if (backgroundExecutor == null) {
            initializeTimersIfNeeded();
        }
        return backgroundExecutor;
    }

    public static String getAnonymousAppDeviceGUID(Context context) {
        if (anonymousAppDeviceGUID == null) {
            synchronized (staticLock) {
                if (anonymousAppDeviceGUID == null) {
                    String string = context.getSharedPreferences(APP_EVENT_PREFERENCES, 0).getString("anonymousAppDeviceGUID", null);
                    anonymousAppDeviceGUID = string;
                    if (string == null) {
                        anonymousAppDeviceGUID = "XZ" + UUID.randomUUID().toString();
                        context.getSharedPreferences(APP_EVENT_PREFERENCES, 0).edit().putString("anonymousAppDeviceGUID", anonymousAppDeviceGUID).apply();
                    }
                }
            }
        }
        return anonymousAppDeviceGUID;
    }

    static class PersistedAppSessionInfo {
        private static final String PERSISTED_SESSION_INFO_FILENAME = "AppEventsLogger.persistedsessioninfo";
        private static Map<AccessTokenAppIdPair, FacebookTimeSpentData> appSessionInfoMap;
        private static final Object staticLock = new Object();
        private static boolean hasChanges = false;
        private static boolean isLoaded = false;
        private static final Runnable appSessionInfoFlushRunnable = new Runnable() { // from class: com.facebook.appevents.AppEventsLogger.PersistedAppSessionInfo.1
            @Override // java.lang.Runnable
            public void run() {
                PersistedAppSessionInfo.saveAppSessionInformation(FacebookSdk.getApplicationContext());
            }
        };

        PersistedAppSessionInfo() {
        }

        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Removed duplicated region for block: B:30:0x008b A[Catch: all -> 0x00af, TryCatch #6 {, blocks: (B:4:0x0003, B:9:0x0026, B:11:0x0032, B:12:0x0039, B:13:0x003b, B:23:0x0068, B:25:0x0074, B:26:0x007b, B:28:0x007f, B:30:0x008b, B:31:0x0092, B:32:0x0096, B:33:0x0097, B:35:0x00a3, B:36:0x00aa, B:37:0x00ad), top: B:46:0x0003 }] */
        /* JADX WARN: Type inference failed for: r9v12, types: [java.util.Map<com.facebook.appevents.AccessTokenAppIdPair, com.facebook.appevents.FacebookTimeSpentData>] */
        /* JADX WARN: Type inference failed for: r9v14, types: [java.util.HashMap, java.util.Map<com.facebook.appevents.AccessTokenAppIdPair, com.facebook.appevents.FacebookTimeSpentData>] */
        /* JADX WARN: Type inference failed for: r9v6, types: [java.util.Map<com.facebook.appevents.AccessTokenAppIdPair, com.facebook.appevents.FacebookTimeSpentData>] */
        /* JADX WARN: Type inference failed for: r9v8, types: [java.util.HashMap, java.util.Map<com.facebook.appevents.AccessTokenAppIdPair, com.facebook.appevents.FacebookTimeSpentData>] */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct add '--show-bad-code' argument
        */
        private static void restoreAppSessionInformation(android.content.Context r9) {
            /*
                java.lang.Object r0 = com.facebook.appevents.AppEventsLogger.PersistedAppSessionInfo.staticLock
                monitor-enter(r0)
                boolean r1 = com.facebook.appevents.AppEventsLogger.PersistedAppSessionInfo.isLoaded     // Catch: java.lang.Throwable -> Laf
                if (r1 != 0) goto Lad
                r1 = 0
                r2 = 1
                r3 = 0
                java.io.ObjectInputStream r4 = new java.io.ObjectInputStream     // Catch: java.lang.Throwable -> L43 java.lang.Exception -> L48 java.io.FileNotFoundException -> L97
                java.lang.String r5 = "AppEventsLogger.persistedsessioninfo"
                java.io.FileInputStream r5 = r9.openFileInput(r5)     // Catch: java.lang.Throwable -> L43 java.lang.Exception -> L48 java.io.FileNotFoundException -> L97
                r4.<init>(r5)     // Catch: java.lang.Throwable -> L43 java.lang.Exception -> L48 java.io.FileNotFoundException -> L97
                java.lang.Object r3 = r4.readObject()     // Catch: java.lang.Exception -> L3f java.io.FileNotFoundException -> L41 java.lang.Throwable -> L7e
                java.util.HashMap r3 = (java.util.HashMap) r3     // Catch: java.lang.Exception -> L3f java.io.FileNotFoundException -> L41 java.lang.Throwable -> L7e
                com.facebook.appevents.AppEventsLogger.PersistedAppSessionInfo.appSessionInfoMap = r3     // Catch: java.lang.Exception -> L3f java.io.FileNotFoundException -> L41 java.lang.Throwable -> L7e
                com.facebook.LoggingBehavior r3 = com.facebook.LoggingBehavior.APP_EVENTS     // Catch: java.lang.Exception -> L3f java.io.FileNotFoundException -> L41 java.lang.Throwable -> L7e
                java.lang.String r5 = "AppEvents"
                java.lang.String r6 = "App session info loaded"
                com.facebook.internal.Logger.log(r3, r5, r6)     // Catch: java.lang.Exception -> L3f java.io.FileNotFoundException -> L41 java.lang.Throwable -> L7e
                com.facebook.internal.Utility.closeQuietly(r4)     // Catch: java.lang.Throwable -> Laf
                java.lang.String r3 = "AppEventsLogger.persistedsessioninfo"
                r9.deleteFile(r3)     // Catch: java.lang.Throwable -> Laf
                java.util.Map<com.facebook.appevents.AccessTokenAppIdPair, com.facebook.appevents.FacebookTimeSpentData> r9 = com.facebook.appevents.AppEventsLogger.PersistedAppSessionInfo.appSessionInfoMap     // Catch: java.lang.Throwable -> Laf
                if (r9 != 0) goto L39
                java.util.HashMap r9 = new java.util.HashMap     // Catch: java.lang.Throwable -> Laf
                r9.<init>()     // Catch: java.lang.Throwable -> Laf
                com.facebook.appevents.AppEventsLogger.PersistedAppSessionInfo.appSessionInfoMap = r9     // Catch: java.lang.Throwable -> Laf
            L39:
                com.facebook.appevents.AppEventsLogger.PersistedAppSessionInfo.isLoaded = r2     // Catch: java.lang.Throwable -> Laf
            L3b:
                com.facebook.appevents.AppEventsLogger.PersistedAppSessionInfo.hasChanges = r1     // Catch: java.lang.Throwable -> Laf
                goto Lad
            L3f:
                r3 = move-exception
                goto L4c
            L41:
                r3 = r4
                goto L97
            L43:
                r4 = move-exception
                r8 = r4
                r4 = r3
                r3 = r8
                goto L7f
            L48:
                r4 = move-exception
                r8 = r4
                r4 = r3
                r3 = r8
            L4c:
                java.lang.String r5 = com.facebook.appevents.AppEventsLogger.access$200()     // Catch: java.lang.Throwable -> L7e
                java.lang.StringBuilder r6 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L7e
                r6.<init>()     // Catch: java.lang.Throwable -> L7e
                java.lang.String r7 = "Got unexpected exception restoring app session info: "
                r6.append(r7)     // Catch: java.lang.Throwable -> L7e
                java.lang.String r3 = r3.toString()     // Catch: java.lang.Throwable -> L7e
                r6.append(r3)     // Catch: java.lang.Throwable -> L7e
                java.lang.String r3 = r6.toString()     // Catch: java.lang.Throwable -> L7e
                android.util.Log.w(r5, r3)     // Catch: java.lang.Throwable -> L7e
                com.facebook.internal.Utility.closeQuietly(r4)     // Catch: java.lang.Throwable -> Laf
                java.lang.String r3 = "AppEventsLogger.persistedsessioninfo"
                r9.deleteFile(r3)     // Catch: java.lang.Throwable -> Laf
                java.util.Map<com.facebook.appevents.AccessTokenAppIdPair, com.facebook.appevents.FacebookTimeSpentData> r9 = com.facebook.appevents.AppEventsLogger.PersistedAppSessionInfo.appSessionInfoMap     // Catch: java.lang.Throwable -> Laf
                if (r9 != 0) goto L7b
                java.util.HashMap r9 = new java.util.HashMap     // Catch: java.lang.Throwable -> Laf
                r9.<init>()     // Catch: java.lang.Throwable -> Laf
                com.facebook.appevents.AppEventsLogger.PersistedAppSessionInfo.appSessionInfoMap = r9     // Catch: java.lang.Throwable -> Laf
            L7b:
                com.facebook.appevents.AppEventsLogger.PersistedAppSessionInfo.isLoaded = r2     // Catch: java.lang.Throwable -> Laf
                goto L3b
            L7e:
                r3 = move-exception
            L7f:
                com.facebook.internal.Utility.closeQuietly(r4)     // Catch: java.lang.Throwable -> Laf
                java.lang.String r4 = "AppEventsLogger.persistedsessioninfo"
                r9.deleteFile(r4)     // Catch: java.lang.Throwable -> Laf
                java.util.Map<com.facebook.appevents.AccessTokenAppIdPair, com.facebook.appevents.FacebookTimeSpentData> r9 = com.facebook.appevents.AppEventsLogger.PersistedAppSessionInfo.appSessionInfoMap     // Catch: java.lang.Throwable -> Laf
                if (r9 != 0) goto L92
                java.util.HashMap r9 = new java.util.HashMap     // Catch: java.lang.Throwable -> Laf
                r9.<init>()     // Catch: java.lang.Throwable -> Laf
                com.facebook.appevents.AppEventsLogger.PersistedAppSessionInfo.appSessionInfoMap = r9     // Catch: java.lang.Throwable -> Laf
            L92:
                com.facebook.appevents.AppEventsLogger.PersistedAppSessionInfo.isLoaded = r2     // Catch: java.lang.Throwable -> Laf
                com.facebook.appevents.AppEventsLogger.PersistedAppSessionInfo.hasChanges = r1     // Catch: java.lang.Throwable -> Laf
                throw r3     // Catch: java.lang.Throwable -> Laf
            L97:
                com.facebook.internal.Utility.closeQuietly(r3)     // Catch: java.lang.Throwable -> Laf
                java.lang.String r3 = "AppEventsLogger.persistedsessioninfo"
                r9.deleteFile(r3)     // Catch: java.lang.Throwable -> Laf
                java.util.Map<com.facebook.appevents.AccessTokenAppIdPair, com.facebook.appevents.FacebookTimeSpentData> r9 = com.facebook.appevents.AppEventsLogger.PersistedAppSessionInfo.appSessionInfoMap     // Catch: java.lang.Throwable -> Laf
                if (r9 != 0) goto Laa
                java.util.HashMap r9 = new java.util.HashMap     // Catch: java.lang.Throwable -> Laf
                r9.<init>()     // Catch: java.lang.Throwable -> Laf
                com.facebook.appevents.AppEventsLogger.PersistedAppSessionInfo.appSessionInfoMap = r9     // Catch: java.lang.Throwable -> Laf
            Laa:
                com.facebook.appevents.AppEventsLogger.PersistedAppSessionInfo.isLoaded = r2     // Catch: java.lang.Throwable -> Laf
                goto L3b
            Lad:
                monitor-exit(r0)     // Catch: java.lang.Throwable -> Laf
                return
            Laf:
                r9 = move-exception
                monitor-exit(r0)     // Catch: java.lang.Throwable -> Laf
                throw r9
            */
            throw new UnsupportedOperationException("Method not decompiled: com.facebook.appevents.AppEventsLogger.PersistedAppSessionInfo.restoreAppSessionInformation(android.content.Context):void");
        }

        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Type inference failed for: r1v8, types: [java.lang.String] */
        static void saveAppSessionInformation(Context context) {
            ObjectOutputStream objectOutputStream;
            synchronized (staticLock) {
                if (hasChanges) {
                    ObjectOutputStream objectOutputStream2 = null;
                    ObjectOutputStream objectOutputStream3 = null;
                    try {
                        try {
                            objectOutputStream = new ObjectOutputStream(new BufferedOutputStream(context.openFileOutput(PERSISTED_SESSION_INFO_FILENAME, 0)));
                        } catch (Throwable th) {
                            th = th;
                        }
                    } catch (Exception e) {
                        e = e;
                    }
                    try {
                        objectOutputStream.writeObject(appSessionInfoMap);
                        hasChanges = false;
                        ?? r1 = "AppEvents";
                        Logger.log(LoggingBehavior.APP_EVENTS, "AppEvents", "App session info saved");
                        Utility.closeQuietly(objectOutputStream);
                        objectOutputStream2 = r1;
                    } catch (Exception e2) {
                        e = e2;
                        objectOutputStream3 = objectOutputStream;
                        Log.w(AppEventsLogger.TAG, "Got unexpected exception while writing app session info: " + e.toString());
                        Utility.closeQuietly(objectOutputStream3);
                        objectOutputStream2 = objectOutputStream3;
                    } catch (Throwable th2) {
                        th = th2;
                        objectOutputStream2 = objectOutputStream;
                        Utility.closeQuietly(objectOutputStream2);
                        throw th;
                    }
                }
            }
        }

        static void onResume(Context context, AccessTokenAppIdPair accessTokenAppIdPair, AppEventsLogger appEventsLogger, long j, String str) {
            synchronized (staticLock) {
                getTimeSpentData(context, accessTokenAppIdPair).onResume(appEventsLogger, j, str);
                onTimeSpentDataUpdate();
            }
        }

        static void onSuspend(Context context, AccessTokenAppIdPair accessTokenAppIdPair, AppEventsLogger appEventsLogger, long j) {
            synchronized (staticLock) {
                getTimeSpentData(context, accessTokenAppIdPair).onSuspend(appEventsLogger, j);
                onTimeSpentDataUpdate();
            }
        }

        private static FacebookTimeSpentData getTimeSpentData(Context context, AccessTokenAppIdPair accessTokenAppIdPair) {
            restoreAppSessionInformation(context);
            FacebookTimeSpentData facebookTimeSpentData = appSessionInfoMap.get(accessTokenAppIdPair);
            if (facebookTimeSpentData != null) {
                return facebookTimeSpentData;
            }
            FacebookTimeSpentData facebookTimeSpentData2 = new FacebookTimeSpentData();
            appSessionInfoMap.put(accessTokenAppIdPair, facebookTimeSpentData2);
            return facebookTimeSpentData2;
        }

        private static void onTimeSpentDataUpdate() {
            if (hasChanges) {
                return;
            }
            hasChanges = true;
            AppEventsLogger.backgroundExecutor.schedule(appSessionInfoFlushRunnable, 30L, TimeUnit.SECONDS);
        }
    }
}
