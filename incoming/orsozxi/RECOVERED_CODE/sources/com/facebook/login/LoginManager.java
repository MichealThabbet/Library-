package com.facebook.login;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookActivity;
import com.facebook.FacebookAuthorizationException;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphResponse;
import com.facebook.LoginStatusCallback;
import com.facebook.Profile;
import com.facebook.internal.CallbackManagerImpl;
import com.facebook.internal.FragmentWrapper;
import com.facebook.internal.NativeProtocol;
import com.facebook.internal.PlatformServiceClient;
import com.facebook.internal.Utility;
import com.facebook.internal.Validate;
import com.facebook.login.LoginClient;
import com.facebook.share.internal.ShareConstants;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import org.json.JSONException;

/* loaded from: classes.dex */
public class LoginManager {
    private static final String EXPRESS_LOGIN_ALLOWED = "express_login_allowed";
    private static final String MANAGE_PERMISSION_PREFIX = "manage";
    private static final Set<String> OTHER_PUBLISH_PERMISSIONS = getOtherPublishPermissions();
    private static final String PREFERENCE_LOGIN_MANAGER = "com.facebook.loginManager";
    private static final String PUBLISH_PERMISSION_PREFIX = "publish";
    private static volatile LoginManager instance;
    private final SharedPreferences sharedPreferences;
    private LoginBehavior loginBehavior = LoginBehavior.NATIVE_WITH_FALLBACK;
    private DefaultAudience defaultAudience = DefaultAudience.FRIENDS;

    LoginManager() {
        Validate.sdkInitialized();
        this.sharedPreferences = FacebookSdk.getApplicationContext().getSharedPreferences(PREFERENCE_LOGIN_MANAGER, 0);
    }

    public static LoginManager getInstance() {
        if (instance == null) {
            synchronized (LoginManager.class) {
                if (instance == null) {
                    instance = new LoginManager();
                }
            }
        }
        return instance;
    }

    public void resolveError(Activity activity, GraphResponse graphResponse) throws JSONException, FacebookException {
        startLogin(new ActivityStartActivityDelegate(activity), createLoginRequestFromResponse(graphResponse));
    }

    public void resolveError(Fragment fragment, GraphResponse graphResponse) throws JSONException, FacebookException {
        resolveError(new FragmentWrapper(fragment), graphResponse);
    }

    public void resolveError(android.app.Fragment fragment, GraphResponse graphResponse) throws JSONException, FacebookException {
        resolveError(new FragmentWrapper(fragment), graphResponse);
    }

    private void resolveError(FragmentWrapper fragmentWrapper, GraphResponse graphResponse) throws JSONException, FacebookException {
        startLogin(new FragmentStartActivityDelegate(fragmentWrapper), createLoginRequestFromResponse(graphResponse));
    }

    private LoginClient.Request createLoginRequestFromResponse(GraphResponse graphResponse) {
        Validate.notNull(graphResponse, "response");
        AccessToken accessToken = graphResponse.getRequest().getAccessToken();
        return createLoginRequest(accessToken != null ? accessToken.getPermissions() : null);
    }

    public void registerCallback(CallbackManager callbackManager, final FacebookCallback<LoginResult> facebookCallback) {
        if (!(callbackManager instanceof CallbackManagerImpl)) {
            throw new FacebookException("Unexpected CallbackManager, please use the provided Factory.");
        }
        ((CallbackManagerImpl) callbackManager).registerCallback(CallbackManagerImpl.RequestCodeOffset.Login.toRequestCode(), new CallbackManagerImpl.Callback() { // from class: com.facebook.login.LoginManager.1
            @Override // com.facebook.internal.CallbackManagerImpl.Callback
            public boolean onActivityResult(int i, Intent intent) {
                return LoginManager.this.onActivityResult(i, intent, facebookCallback);
            }
        });
    }

    public void unregisterCallback(CallbackManager callbackManager) {
        if (!(callbackManager instanceof CallbackManagerImpl)) {
            throw new FacebookException("Unexpected CallbackManager, please use the provided Factory.");
        }
        ((CallbackManagerImpl) callbackManager).unregisterCallback(CallbackManagerImpl.RequestCodeOffset.Login.toRequestCode());
    }

    boolean onActivityResult(int i, Intent intent) {
        return onActivityResult(i, intent, null);
    }

    boolean onActivityResult(int i, Intent intent, FacebookCallback<LoginResult> facebookCallback) throws JSONException {
        LoginClient.Result.Code code;
        AccessToken accessToken;
        LoginClient.Request request;
        Map<String, String> map;
        boolean z;
        Map<String, String> map2;
        LoginClient.Request request2;
        boolean z2;
        LoginClient.Result.Code code2 = LoginClient.Result.Code.ERROR;
        FacebookException facebookException = null;
        boolean z3 = false;
        if (intent != null) {
            LoginClient.Result result = (LoginClient.Result) intent.getParcelableExtra("com.facebook.LoginFragment:Result");
            if (result != null) {
                LoginClient.Request request3 = result.request;
                LoginClient.Result.Code code3 = result.code;
                if (i == -1) {
                    if (result.code == LoginClient.Result.Code.SUCCESS) {
                        accessToken = result.token;
                    } else {
                        facebookException = new FacebookAuthorizationException(result.errorMessage);
                        accessToken = null;
                    }
                } else if (i == 0) {
                    accessToken = null;
                    z3 = true;
                } else {
                    accessToken = null;
                }
                map2 = result.loggingExtras;
                boolean z4 = z3;
                request2 = request3;
                code2 = code3;
                z2 = z4;
            } else {
                accessToken = null;
                map2 = null;
                request2 = null;
                z2 = false;
            }
            map = map2;
            code = code2;
            request = request2;
            z = z2;
        } else if (i == 0) {
            code = LoginClient.Result.Code.CANCEL;
            accessToken = null;
            request = null;
            map = null;
            z = true;
        } else {
            code = code2;
            accessToken = null;
            request = null;
            map = null;
            z = false;
        }
        if (facebookException == null && accessToken == null && !z) {
            facebookException = new FacebookException("Unexpected call to LoginManager.onActivityResult");
        }
        logCompleteLogin(null, code, map, facebookException, true, request);
        finishLogin(accessToken, request, facebookException, z, facebookCallback);
        return true;
    }

    public LoginBehavior getLoginBehavior() {
        return this.loginBehavior;
    }

    public LoginManager setLoginBehavior(LoginBehavior loginBehavior) {
        this.loginBehavior = loginBehavior;
        return this;
    }

    public DefaultAudience getDefaultAudience() {
        return this.defaultAudience;
    }

    public LoginManager setDefaultAudience(DefaultAudience defaultAudience) {
        this.defaultAudience = defaultAudience;
        return this;
    }

    public void logOut() {
        AccessToken.setCurrentAccessToken(null);
        Profile.setCurrentProfile(null);
        setExpressLoginStatus(false);
    }

    public void retrieveLoginStatus(Context context, LoginStatusCallback loginStatusCallback) {
        retrieveLoginStatus(context, 5000L, loginStatusCallback);
    }

    public void retrieveLoginStatus(Context context, long j, LoginStatusCallback loginStatusCallback) {
        retrieveLoginStatusImpl(context, loginStatusCallback, j);
    }

    public void logInWithReadPermissions(Fragment fragment, Collection<String> collection) throws JSONException, FacebookException {
        logInWithReadPermissions(new FragmentWrapper(fragment), collection);
    }

    public void logInWithReadPermissions(android.app.Fragment fragment, Collection<String> collection) throws JSONException, FacebookException {
        logInWithReadPermissions(new FragmentWrapper(fragment), collection);
    }

    private void logInWithReadPermissions(FragmentWrapper fragmentWrapper, Collection<String> collection) throws JSONException, FacebookException {
        validateReadPermissions(collection);
        startLogin(new FragmentStartActivityDelegate(fragmentWrapper), createLoginRequest(collection));
    }

    public void logInWithReadPermissions(Activity activity, Collection<String> collection) throws JSONException, FacebookException {
        validateReadPermissions(collection);
        startLogin(new ActivityStartActivityDelegate(activity), createLoginRequest(collection));
    }

    public void logInWithPublishPermissions(Fragment fragment, Collection<String> collection) throws JSONException, FacebookException {
        logInWithPublishPermissions(new FragmentWrapper(fragment), collection);
    }

    public void logInWithPublishPermissions(android.app.Fragment fragment, Collection<String> collection) throws JSONException, FacebookException {
        logInWithPublishPermissions(new FragmentWrapper(fragment), collection);
    }

    private void logInWithPublishPermissions(FragmentWrapper fragmentWrapper, Collection<String> collection) throws JSONException, FacebookException {
        validatePublishPermissions(collection);
        startLogin(new FragmentStartActivityDelegate(fragmentWrapper), createLoginRequest(collection));
    }

    public void logInWithPublishPermissions(Activity activity, Collection<String> collection) throws JSONException, FacebookException {
        validatePublishPermissions(collection);
        startLogin(new ActivityStartActivityDelegate(activity), createLoginRequest(collection));
    }

    private void validateReadPermissions(Collection<String> collection) {
        if (collection == null) {
            return;
        }
        for (String str : collection) {
            if (isPublishPermission(str)) {
                throw new FacebookException(String.format("Cannot pass a publish or manage permission (%s) to a request for read authorization", str));
            }
        }
    }

    private void validatePublishPermissions(Collection<String> collection) {
        if (collection == null) {
            return;
        }
        for (String str : collection) {
            if (!isPublishPermission(str)) {
                throw new FacebookException(String.format("Cannot pass a read permission (%s) to a request for publish authorization", str));
            }
        }
    }

    static boolean isPublishPermission(String str) {
        return str != null && (str.startsWith(PUBLISH_PERMISSION_PREFIX) || str.startsWith(MANAGE_PERMISSION_PREFIX) || OTHER_PUBLISH_PERMISSIONS.contains(str));
    }

    private static Set<String> getOtherPublishPermissions() {
        return Collections.unmodifiableSet(new HashSet<String>() { // from class: com.facebook.login.LoginManager.2
            {
                add("ads_management");
                add("create_event");
                add("rsvp_event");
            }
        });
    }

    protected LoginClient.Request createLoginRequest(Collection<String> collection) {
        LoginClient.Request request = new LoginClient.Request(this.loginBehavior, Collections.unmodifiableSet(collection != null ? new HashSet(collection) : new HashSet()), this.defaultAudience, FacebookSdk.getApplicationId(), UUID.randomUUID().toString());
        request.setRerequest(AccessToken.getCurrentAccessToken() != null);
        return request;
    }

    private void startLogin(StartActivityDelegate startActivityDelegate, LoginClient.Request request) throws JSONException, FacebookException {
        logStartLogin(startActivityDelegate.getActivityContext(), request);
        CallbackManagerImpl.registerStaticCallback(CallbackManagerImpl.RequestCodeOffset.Login.toRequestCode(), new CallbackManagerImpl.Callback() { // from class: com.facebook.login.LoginManager.3
            @Override // com.facebook.internal.CallbackManagerImpl.Callback
            public boolean onActivityResult(int i, Intent intent) {
                return LoginManager.this.onActivityResult(i, intent);
            }
        });
        if (tryFacebookActivity(startActivityDelegate, request)) {
            return;
        }
        FacebookException facebookException = new FacebookException("Log in attempt failed: FacebookActivity could not be started. Please make sure you added FacebookActivity to the AndroidManifest.");
        logCompleteLogin(startActivityDelegate.getActivityContext(), LoginClient.Result.Code.ERROR, null, facebookException, false, request);
        throw facebookException;
    }

    private void logStartLogin(Context context, LoginClient.Request request) throws JSONException {
        LoginLogger logger = LoginLoggerHolder.getLogger(context);
        if (logger == null || request == null) {
            return;
        }
        logger.logStartLogin(request);
    }

    private void logCompleteLogin(Context context, LoginClient.Result.Code code, Map<String, String> map, Exception exc, boolean z, LoginClient.Request request) throws JSONException {
        LoginLogger logger = LoginLoggerHolder.getLogger(context);
        if (logger == null) {
            return;
        }
        if (request == null) {
            logger.logUnexpectedError("fb_mobile_login_complete", "Unexpected call to logCompleteLogin with null pendingAuthorizationRequest.");
            return;
        }
        HashMap map2 = new HashMap();
        map2.put("try_login_activity", z ? "1" : "0");
        logger.logCompleteLogin(request.getAuthId(), map2, code, map, exc);
    }

    private boolean tryFacebookActivity(StartActivityDelegate startActivityDelegate, LoginClient.Request request) {
        Intent facebookActivityIntent = getFacebookActivityIntent(request);
        if (!resolveIntent(facebookActivityIntent)) {
            return false;
        }
        try {
            startActivityDelegate.startActivityForResult(facebookActivityIntent, LoginClient.getLoginRequestCode());
            return true;
        } catch (ActivityNotFoundException unused) {
            return false;
        }
    }

    private boolean resolveIntent(Intent intent) {
        return FacebookSdk.getApplicationContext().getPackageManager().resolveActivity(intent, 0) != null;
    }

    protected Intent getFacebookActivityIntent(LoginClient.Request request) {
        Intent intent = new Intent();
        intent.setClass(FacebookSdk.getApplicationContext(), FacebookActivity.class);
        intent.setAction(request.getLoginBehavior().toString());
        Bundle bundle = new Bundle();
        bundle.putParcelable(ShareConstants.WEB_DIALOG_RESULT_PARAM_REQUEST_ID, request);
        intent.putExtra("com.facebook.LoginFragment:Request", bundle);
        return intent;
    }

    static LoginResult computeLoginResult(LoginClient.Request request, AccessToken accessToken) {
        Set<String> permissions = request.getPermissions();
        HashSet hashSet = new HashSet(accessToken.getPermissions());
        if (request.isRerequest()) {
            hashSet.retainAll(permissions);
        }
        HashSet hashSet2 = new HashSet(permissions);
        hashSet2.removeAll(hashSet);
        return new LoginResult(accessToken, hashSet, hashSet2);
    }

    private void finishLogin(AccessToken accessToken, LoginClient.Request request, FacebookException facebookException, boolean z, FacebookCallback<LoginResult> facebookCallback) {
        if (accessToken != null) {
            AccessToken.setCurrentAccessToken(accessToken);
            Profile.fetchProfileForCurrentAccessToken();
        }
        if (facebookCallback != null) {
            LoginResult loginResultComputeLoginResult = accessToken != null ? computeLoginResult(request, accessToken) : null;
            if (z || (loginResultComputeLoginResult != null && loginResultComputeLoginResult.getRecentlyGrantedPermissions().size() == 0)) {
                facebookCallback.onCancel();
                return;
            }
            if (facebookException != null) {
                facebookCallback.onError(facebookException);
            } else if (accessToken != null) {
                setExpressLoginStatus(true);
                facebookCallback.onSuccess(loginResultComputeLoginResult);
            }
        }
    }

    private void retrieveLoginStatusImpl(Context context, final LoginStatusCallback loginStatusCallback, long j) {
        final String applicationId = FacebookSdk.getApplicationId();
        final String string = UUID.randomUUID().toString();
        final LoginLogger loginLogger = new LoginLogger(context, applicationId);
        if (!isExpressLoginAllowed()) {
            loginLogger.logLoginStatusFailure(string);
            loginStatusCallback.onFailure();
            return;
        }
        LoginStatusClient loginStatusClient = new LoginStatusClient(context, applicationId, string, FacebookSdk.getGraphApiVersion(), j);
        loginStatusClient.setCompletedListener(new PlatformServiceClient.CompletedListener() { // from class: com.facebook.login.LoginManager.4
            @Override // com.facebook.internal.PlatformServiceClient.CompletedListener
            public void completed(Bundle bundle) {
                if (bundle != null) {
                    String string2 = bundle.getString(NativeProtocol.STATUS_ERROR_TYPE);
                    String string3 = bundle.getString(NativeProtocol.STATUS_ERROR_DESCRIPTION);
                    if (string2 != null) {
                        LoginManager.handleLoginStatusError(string2, string3, string, loginLogger, loginStatusCallback);
                        return;
                    }
                    String string4 = bundle.getString(NativeProtocol.EXTRA_ACCESS_TOKEN);
                    long j2 = bundle.getLong(NativeProtocol.EXTRA_EXPIRES_SECONDS_SINCE_EPOCH);
                    ArrayList<String> stringArrayList = bundle.getStringArrayList(NativeProtocol.EXTRA_PERMISSIONS);
                    String string5 = bundle.getString(NativeProtocol.RESULT_ARGS_SIGNED_REQUEST);
                    String userIDFromSignedRequest = Utility.isNullOrEmpty(string5) ? null : LoginMethodHandler.getUserIDFromSignedRequest(string5);
                    if (!Utility.isNullOrEmpty(string4) && stringArrayList != null && !stringArrayList.isEmpty() && !Utility.isNullOrEmpty(userIDFromSignedRequest)) {
                        AccessToken accessToken = new AccessToken(string4, applicationId, userIDFromSignedRequest, stringArrayList, null, null, new Date(j2), null);
                        AccessToken.setCurrentAccessToken(accessToken);
                        Profile profileFromBundle = LoginManager.getProfileFromBundle(bundle);
                        if (profileFromBundle != null) {
                            Profile.setCurrentProfile(profileFromBundle);
                        } else {
                            Profile.fetchProfileForCurrentAccessToken();
                        }
                        loginLogger.logLoginStatusSuccess(string);
                        loginStatusCallback.onCompleted(accessToken);
                        return;
                    }
                    loginLogger.logLoginStatusFailure(string);
                    loginStatusCallback.onFailure();
                    return;
                }
                loginLogger.logLoginStatusFailure(string);
                loginStatusCallback.onFailure();
            }
        });
        loginLogger.logLoginStatusStart(string);
        if (loginStatusClient.start()) {
            return;
        }
        loginLogger.logLoginStatusFailure(string);
        loginStatusCallback.onFailure();
    }

    private void setExpressLoginStatus(boolean z) {
        SharedPreferences.Editor editorEdit = this.sharedPreferences.edit();
        editorEdit.putBoolean(EXPRESS_LOGIN_ALLOWED, z);
        editorEdit.apply();
    }

    private boolean isExpressLoginAllowed() {
        return this.sharedPreferences.getBoolean(EXPRESS_LOGIN_ALLOWED, true);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static Profile getProfileFromBundle(Bundle bundle) {
        String string = bundle.getString(NativeProtocol.EXTRA_ARGS_PROFILE_NAME);
        String string2 = bundle.getString(NativeProtocol.EXTRA_ARGS_PROFILE_FIRST_NAME);
        String string3 = bundle.getString(NativeProtocol.EXTRA_ARGS_PROFILE_MIDDLE_NAME);
        String string4 = bundle.getString(NativeProtocol.EXTRA_ARGS_PROFILE_LAST_NAME);
        String string5 = bundle.getString(NativeProtocol.EXTRA_ARGS_PROFILE_LINK);
        String string6 = bundle.getString(NativeProtocol.EXTRA_ARGS_PROFILE_USER_ID);
        if (string == null || string2 == null || string3 == null || string4 == null || string5 == null || string6 == null) {
            return null;
        }
        return new Profile(string6, string2, string3, string4, string, Uri.parse(string5));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void handleLoginStatusError(String str, String str2, String str3, LoginLogger loginLogger, LoginStatusCallback loginStatusCallback) {
        FacebookException facebookException = new FacebookException(str + ": " + str2);
        loginLogger.logLoginStatusError(str3, facebookException);
        loginStatusCallback.onError(facebookException);
    }

    private static class ActivityStartActivityDelegate implements StartActivityDelegate {
        private final Activity activity;

        ActivityStartActivityDelegate(Activity activity) {
            Validate.notNull(activity, "activity");
            this.activity = activity;
        }

        @Override // com.facebook.login.StartActivityDelegate
        public void startActivityForResult(Intent intent, int i) {
            this.activity.startActivityForResult(intent, i);
        }

        @Override // com.facebook.login.StartActivityDelegate
        public Activity getActivityContext() {
            return this.activity;
        }
    }

    private static class FragmentStartActivityDelegate implements StartActivityDelegate {
        private final FragmentWrapper fragment;

        FragmentStartActivityDelegate(FragmentWrapper fragmentWrapper) {
            Validate.notNull(fragmentWrapper, "fragment");
            this.fragment = fragmentWrapper;
        }

        @Override // com.facebook.login.StartActivityDelegate
        public void startActivityForResult(Intent intent, int i) {
            this.fragment.startActivityForResult(intent, i);
        }

        @Override // com.facebook.login.StartActivityDelegate
        public Activity getActivityContext() {
            return this.fragment.getActivity();
        }
    }

    private static class LoginLoggerHolder {
        private static LoginLogger logger;

        private LoginLoggerHolder() {
        }

        /* JADX INFO: Access modifiers changed from: private */
        /* JADX WARN: Removed duplicated region for block: B:11:0x000f A[Catch: all -> 0x0022, TRY_ENTER, TryCatch #0 {, blocks: (B:6:0x0006, B:11:0x000f, B:13:0x0013, B:14:0x001e), top: B:20:0x0006 }] */
        /* JADX WARN: Removed duplicated region for block: B:8:0x000c  */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct add '--show-bad-code' argument
        */
        public static synchronized com.facebook.login.LoginLogger getLogger(android.content.Context r3) {
            /*
                java.lang.Class<com.facebook.login.LoginManager$LoginLoggerHolder> r0 = com.facebook.login.LoginManager.LoginLoggerHolder.class
                monitor-enter(r0)
                if (r3 == 0) goto L6
                goto La
            L6:
                android.content.Context r3 = com.facebook.FacebookSdk.getApplicationContext()     // Catch: java.lang.Throwable -> L22
            La:
                if (r3 != 0) goto Lf
                r3 = 0
                monitor-exit(r0)
                return r3
            Lf:
                com.facebook.login.LoginLogger r1 = com.facebook.login.LoginManager.LoginLoggerHolder.logger     // Catch: java.lang.Throwable -> L22
                if (r1 != 0) goto L1e
                com.facebook.login.LoginLogger r1 = new com.facebook.login.LoginLogger     // Catch: java.lang.Throwable -> L22
                java.lang.String r2 = com.facebook.FacebookSdk.getApplicationId()     // Catch: java.lang.Throwable -> L22
                r1.<init>(r3, r2)     // Catch: java.lang.Throwable -> L22
                com.facebook.login.LoginManager.LoginLoggerHolder.logger = r1     // Catch: java.lang.Throwable -> L22
            L1e:
                com.facebook.login.LoginLogger r3 = com.facebook.login.LoginManager.LoginLoggerHolder.logger     // Catch: java.lang.Throwable -> L22
                monitor-exit(r0)
                return r3
            L22:
                r3 = move-exception
                monitor-exit(r0)
                throw r3
            */
            throw new UnsupportedOperationException("Method not decompiled: com.facebook.login.LoginManager.LoginLoggerHolder.getLogger(android.content.Context):com.facebook.login.LoginLogger");
        }
    }
}
