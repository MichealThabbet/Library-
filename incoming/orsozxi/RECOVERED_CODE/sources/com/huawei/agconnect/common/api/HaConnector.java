package com.huawei.agconnect.common.api;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import com.huawei.agconnect.AGConnectInstance;
import com.huawei.agconnect.common.network.AccessNetworkManager;
import com.huawei.agconnect.credential.obs.ah;
import com.huawei.agconnect.credential.obs.ai;
import java.util.HashMap;
import java.util.Map;

/* loaded from: classes2.dex */
public class HaConnector {
    private static final String HA_HTTP_HEADER = "com.huawei.agconnect";
    private static final String HA_SERVICE_TAG = "AGC_TAG";
    private static final HaConnector INSTANCE = new HaConnector();
    private static final String TAG = "HaConnector";
    private ai hiAnalytics;
    private final Handler mainHandler = new Handler(Looper.getMainLooper());

    private HaConnector() {
    }

    public static synchronized HaConnector getInstance() {
        return INSTANCE;
    }

    private void initHiAnalytics(Context context) {
        try {
            this.hiAnalytics = useOldHaInitFunction() ? ah.a(context, HA_SERVICE_TAG, HA_HTTP_HEADER) : ah.a(context, HA_SERVICE_TAG, HA_HTTP_HEADER, AGConnectInstance.getInstance().getOptions().getRoutePolicy().getRouteName());
            if (this.hiAnalytics == null) {
                Logger.e(TAG, "init HiAnalyticsBridge SDK error, please make sure the project inherits HiAnalytics SDK correctlyor download the latest agconnect-services.json file and try again.");
            } else {
                Logger.i(TAG, "init HiAnalyticsBridge SDK end.");
            }
        } catch (Error e) {
            if (e instanceof NoSuchMethodError) {
                Logger.e(TAG, "please upgrade HiAnalytics SDK (com.huawei.hms:hianalytics) to the latest version");
            }
            throw e;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void initInMainThread(Context context) {
        initHiAnalytics(context);
    }

    private boolean useOldHaInitFunction() {
        return TextUtils.isEmpty(AGConnectInstance.getInstance().getOptions().getString("/service/analytics/collector_url_ru")) || TextUtils.isEmpty(AGConnectInstance.getInstance().getOptions().getString("/service/analytics/collector_url_sg")) || TextUtils.isEmpty(AGConnectInstance.getInstance().getOptions().getString("/service/analytics/collector_url_de")) || TextUtils.isEmpty(AGConnectInstance.getInstance().getOptions().getString("/service/analytics/collector_url_cn"));
    }

    public boolean containHaInstance() {
        return this.hiAnalytics != null;
    }

    public Map<String, String> getUserProfiles(boolean z) {
        HashMap map = new HashMap();
        ai aiVar = this.hiAnalytics;
        return aiVar != null ? aiVar.a(z) : map;
    }

    public void init(final Context context) {
        AccessNetworkManager.getInstance().addCallback(new AccessNetworkManager.AccessNetworkCallback() { // from class: com.huawei.agconnect.common.api.HaConnector.1
            @Override // com.huawei.agconnect.common.network.AccessNetworkManager.AccessNetworkCallback
            public void onNetWorkReady() {
                if (Looper.getMainLooper() == Looper.myLooper()) {
                    HaConnector.this.initInMainThread(context);
                } else {
                    HaConnector.this.mainHandler.post(new Runnable() { // from class: com.huawei.agconnect.common.api.HaConnector.1.1
                        @Override // java.lang.Runnable
                        public void run() {
                            HaConnector.this.initInMainThread(context);
                        }
                    });
                }
                HaConnector.this.mainHandler.postDelayed(new Runnable() { // from class: com.huawei.agconnect.common.api.HaConnector.1.2
                    @Override // java.lang.Runnable
                    public void run() {
                        if (HaConnector.this.hiAnalytics != null) {
                            Logger.i(HaConnector.TAG, "Auto onReport");
                            HaConnector.this.hiAnalytics.a();
                        }
                    }
                }, 5000L);
            }
        });
    }

    public void onEvent(String str, Bundle bundle) {
        ai aiVar = this.hiAnalytics;
        if (aiVar != null) {
            aiVar.a(str, bundle);
        }
    }

    public void onReport() {
        ai aiVar = this.hiAnalytics;
        if (aiVar != null) {
            aiVar.a();
        }
    }
}
