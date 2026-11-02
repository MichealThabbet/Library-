package com.google.firebase.crashlytics.internal.settings;

import android.content.Context;
import android.content.SharedPreferences;
import com.google.android.gms.tasks.SuccessContinuation;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskCompletionSource;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.crashlytics.internal.Logger;
import com.google.firebase.crashlytics.internal.common.CommonUtils;
import com.google.firebase.crashlytics.internal.common.CurrentTimeProvider;
import com.google.firebase.crashlytics.internal.common.DataCollectionArbiter;
import com.google.firebase.crashlytics.internal.common.DeliveryMechanism;
import com.google.firebase.crashlytics.internal.common.IdManager;
import com.google.firebase.crashlytics.internal.common.SystemCurrentTimeProvider;
import com.google.firebase.crashlytics.internal.network.HttpRequestFactory;
import com.google.firebase.crashlytics.internal.settings.model.AppSettingsData;
import com.google.firebase.crashlytics.internal.settings.model.Settings;
import com.google.firebase.crashlytics.internal.settings.model.SettingsData;
import com.google.firebase.crashlytics.internal.settings.model.SettingsRequest;
import com.google.firebase.crashlytics.internal.settings.network.DefaultSettingsSpiCall;
import com.google.firebase.crashlytics.internal.settings.network.SettingsSpiCall;
import java.util.Locale;
import java.util.concurrent.Executor;
import java.util.concurrent.atomic.AtomicReference;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes2.dex */
public class SettingsController implements SettingsDataProvider {
    private static final String PREFS_BUILD_INSTANCE_IDENTIFIER = "existing_instance_identifier";
    private static final String SETTINGS_URL_FORMAT = "https://firebase-settings.crashlytics.com/spi/v2/platforms/android/gmp/%s/settings";
    private final CachedSettingsIo cachedSettingsIo;
    private final Context context;
    private final CurrentTimeProvider currentTimeProvider;
    private final DataCollectionArbiter dataCollectionArbiter;
    private final SettingsJsonParser settingsJsonParser;
    private final SettingsRequest settingsRequest;
    private final SettingsSpiCall settingsSpiCall;
    private final AtomicReference<Settings> settings = new AtomicReference<>();
    private final AtomicReference<TaskCompletionSource<AppSettingsData>> appSettingsData = new AtomicReference<>(new TaskCompletionSource());

    SettingsController(Context context, SettingsRequest settingsRequest, CurrentTimeProvider currentTimeProvider, SettingsJsonParser settingsJsonParser, CachedSettingsIo cachedSettingsIo, SettingsSpiCall settingsSpiCall, DataCollectionArbiter dataCollectionArbiter) {
        this.context = context;
        this.settingsRequest = settingsRequest;
        this.currentTimeProvider = currentTimeProvider;
        this.settingsJsonParser = settingsJsonParser;
        this.cachedSettingsIo = cachedSettingsIo;
        this.settingsSpiCall = settingsSpiCall;
        this.dataCollectionArbiter = dataCollectionArbiter;
        this.settings.set(DefaultSettingsJsonTransform.defaultSettings(currentTimeProvider));
    }

    public static SettingsController create(Context context, String str, IdManager idManager, HttpRequestFactory httpRequestFactory, String str2, String str3, DataCollectionArbiter dataCollectionArbiter) {
        String installerPackageName = idManager.getInstallerPackageName();
        SystemCurrentTimeProvider systemCurrentTimeProvider = new SystemCurrentTimeProvider();
        return new SettingsController(context, new SettingsRequest(str, idManager.getModelName(), idManager.getOsBuildVersionString(), idManager.getOsDisplayVersionString(), idManager, CommonUtils.createInstanceIdFrom(CommonUtils.getMappingFileId(context), str, str3, str2), str3, str2, DeliveryMechanism.determineFrom(installerPackageName).getId()), systemCurrentTimeProvider, new SettingsJsonParser(systemCurrentTimeProvider), new CachedSettingsIo(context), new DefaultSettingsSpiCall(String.format(Locale.US, SETTINGS_URL_FORMAT, str), httpRequestFactory), dataCollectionArbiter);
    }

    @Override // com.google.firebase.crashlytics.internal.settings.SettingsDataProvider
    public Settings getSettings() {
        return this.settings.get();
    }

    @Override // com.google.firebase.crashlytics.internal.settings.SettingsDataProvider
    public Task<AppSettingsData> getAppSettings() {
        return this.appSettingsData.get().getTask();
    }

    public Task<Void> loadSettingsData(Executor executor) {
        return loadSettingsData(SettingsCacheBehavior.USE_CACHE, executor);
    }

    public Task<Void> loadSettingsData(SettingsCacheBehavior settingsCacheBehavior, Executor executor) throws Throwable {
        SettingsData cachedSettingsData;
        if (!buildInstanceIdentifierChanged() && (cachedSettingsData = getCachedSettingsData(settingsCacheBehavior)) != null) {
            this.settings.set(cachedSettingsData);
            this.appSettingsData.get().trySetResult(cachedSettingsData.getAppSettingsData());
            return Tasks.forResult(null);
        }
        SettingsData cachedSettingsData2 = getCachedSettingsData(SettingsCacheBehavior.IGNORE_CACHE_EXPIRATION);
        if (cachedSettingsData2 != null) {
            this.settings.set(cachedSettingsData2);
            this.appSettingsData.get().trySetResult(cachedSettingsData2.getAppSettingsData());
        }
        return this.dataCollectionArbiter.waitForDataCollectionPermission().onSuccessTask(executor, new SuccessContinuation<Void, Void>() { // from class: com.google.firebase.crashlytics.internal.settings.SettingsController.1
            @Override // com.google.android.gms.tasks.SuccessContinuation
            public Task<Void> then(Void r5) throws Exception {
                JSONObject jSONObjectInvoke = SettingsController.this.settingsSpiCall.invoke(SettingsController.this.settingsRequest, true);
                if (jSONObjectInvoke != null) {
                    SettingsData settingsJson = SettingsController.this.settingsJsonParser.parseSettingsJson(jSONObjectInvoke);
                    SettingsController.this.cachedSettingsIo.writeCachedSettings(settingsJson.getExpiresAtMillis(), jSONObjectInvoke);
                    SettingsController.this.logSettings(jSONObjectInvoke, "Loaded settings: ");
                    SettingsController settingsController = SettingsController.this;
                    settingsController.setStoredBuildInstanceIdentifier(settingsController.settingsRequest.instanceId);
                    SettingsController.this.settings.set(settingsJson);
                    ((TaskCompletionSource) SettingsController.this.appSettingsData.get()).trySetResult(settingsJson.getAppSettingsData());
                    TaskCompletionSource taskCompletionSource = new TaskCompletionSource();
                    taskCompletionSource.trySetResult(settingsJson.getAppSettingsData());
                    SettingsController.this.appSettingsData.set(taskCompletionSource);
                }
                return Tasks.forResult(null);
            }
        });
    }

    private SettingsData getCachedSettingsData(SettingsCacheBehavior settingsCacheBehavior) throws Throwable {
        SettingsData settingsData = null;
        try {
            if (!SettingsCacheBehavior.SKIP_CACHE_LOOKUP.equals(settingsCacheBehavior)) {
                JSONObject cachedSettings = this.cachedSettingsIo.readCachedSettings();
                if (cachedSettings != null) {
                    SettingsData settingsJson = this.settingsJsonParser.parseSettingsJson(cachedSettings);
                    if (settingsJson != null) {
                        logSettings(cachedSettings, "Loaded cached settings: ");
                        long currentTimeMillis = this.currentTimeProvider.getCurrentTimeMillis();
                        if (SettingsCacheBehavior.IGNORE_CACHE_EXPIRATION.equals(settingsCacheBehavior) || !settingsJson.isExpired(currentTimeMillis)) {
                            try {
                                Logger.getLogger().v("Returning cached settings.");
                                settingsData = settingsJson;
                            } catch (Exception e) {
                                e = e;
                                settingsData = settingsJson;
                                Logger.getLogger().e("Failed to get cached settings", e);
                                return settingsData;
                            }
                        } else {
                            Logger.getLogger().v("Cached settings have expired.");
                        }
                    } else {
                        Logger.getLogger().e("Failed to parse cached settings data.", null);
                    }
                } else {
                    Logger.getLogger().d("No cached settings data found.");
                }
            }
        } catch (Exception e2) {
            e = e2;
        }
        return settingsData;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void logSettings(JSONObject jSONObject, String str) throws JSONException {
        Logger.getLogger().d(str + jSONObject.toString());
    }

    private String getStoredBuildInstanceIdentifier() {
        return CommonUtils.getSharedPrefs(this.context).getString(PREFS_BUILD_INSTANCE_IDENTIFIER, "");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean setStoredBuildInstanceIdentifier(String str) {
        SharedPreferences.Editor editorEdit = CommonUtils.getSharedPrefs(this.context).edit();
        editorEdit.putString(PREFS_BUILD_INSTANCE_IDENTIFIER, str);
        editorEdit.apply();
        return true;
    }

    boolean buildInstanceIdentifierChanged() {
        return !getStoredBuildInstanceIdentifier().equals(this.settingsRequest.instanceId);
    }
}
