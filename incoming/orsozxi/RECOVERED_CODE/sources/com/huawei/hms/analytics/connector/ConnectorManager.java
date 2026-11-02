package com.huawei.hms.analytics.connector;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import com.huawei.hms.analytics.a;
import com.huawei.hms.analytics.ak;
import com.huawei.hms.analytics.ar;
import com.huawei.hms.analytics.ba;
import com.huawei.hms.analytics.bh;
import com.huawei.hms.analytics.core.log.HiLog;
import com.huawei.hms.analytics.cx;
import com.huawei.hms.analytics.dd;
import com.huawei.hms.analytics.di;
import com.huawei.hms.analytics.dq;
import com.huawei.hms.analytics.fgh;
import com.huawei.hms.analytics.framework.SyncManager;
import com.huawei.hms.analytics.hij;
import com.huawei.hms.analytics.instance.CallBack;
import com.huawei.hms.analytics.klm;
import com.huawei.hms.framework.network.grs.GrsBaseInfo;
import java.util.HashMap;
import java.util.Map;

/* loaded from: classes2.dex */
public class ConnectorManager {
    private String ikl;
    private ak klm;
    private boolean lmn;

    public ConnectorManager(Context context, String str, String str2) {
        lmn(context, str, str2, GrsBaseInfo.CountryCodeSource.UNKNOWN);
    }

    public ConnectorManager(Context context, String str, String str2, String str3) {
        lmn(context, str, str2, str3);
    }

    private void klm(Context context, String str, String str2, String str3) {
        Context applicationContext = context.getApplicationContext() != null ? context.getApplicationContext() : context;
        fgh.lmn();
        fgh.lmn(new hij(context, new klm(!GrsBaseInfo.CountryCodeSource.UNKNOWN.equals(str3), str3), str, str3, SyncManager.TaskNames.INIT_TASK_CONNECTOR));
        bh bhVarLmn = fgh.lmn().lmn(str);
        if (bhVarLmn == null) {
            ak akVar = new ak(applicationContext, str);
            this.klm = akVar;
            akVar.lmn(str2);
            fgh.lmn().lmn(str, this.klm);
        } else {
            this.klm = (ak) bhVarLmn;
        }
        a.lmn(context);
    }

    private void lmn(Context context, String str, String str2, String str3) {
        if (!dq.lmn().lmn(context)) {
            HiLog.w("InteractionManager", "user unlock");
            return;
        }
        if (context == null || TextUtils.isEmpty(str) || str.length() > 256 || TextUtils.isEmpty(str2) || str2.length() > 256) {
            HiLog.w("InteractionManager", "connectManager param is not right");
            return;
        }
        try {
            this.ikl = str;
            klm(context, str, str2, str3);
            this.lmn = true;
        } catch (Throwable th) {
            HiLog.e("InteractionManager", "other exception,init connector instance error ");
            di.lmn(th);
        }
    }

    public Map<String, String> getUserProfiles(boolean z) {
        if (this.lmn) {
            if (ba.klm(this.ikl)) {
                return this.klm.lmn(z);
            }
            try {
                SyncManager.getInstance().await(SyncManager.TaskNames.INIT_TASK_CONNECTOR, 150L);
                return this.klm.lmn(z);
            } catch (InterruptedException unused) {
                HiLog.w("InteractionManager", "getUserProfiles Interrupted Exception");
            }
        }
        return new HashMap();
    }

    public void onEvent(final String str, Bundle bundle) {
        if (this.lmn) {
            HiLog.i("InteractionManager", "connectManager onEvent");
            final Bundle bundleLmn = dd.lmn(bundle);
            cx.lmn().lmn(new Runnable() { // from class: com.huawei.hms.analytics.connector.ConnectorManager.1
                @Override // java.lang.Runnable
                public final void run() {
                    if (ba.klm(ConnectorManager.this.ikl)) {
                        ConnectorManager.this.klm.lmn(str, bundleLmn);
                    }
                }
            });
        }
    }

    public void onReport() {
        if (this.lmn && ar.lmn().lmn.fgh) {
            HiLog.i("InteractionManager", "connectManager onReport");
            cx.lmn().lmn(new Runnable() { // from class: com.huawei.hms.analytics.connector.ConnectorManager.2
                @Override // java.lang.Runnable
                public final void run() {
                    if (ba.klm(ConnectorManager.this.ikl)) {
                        ConnectorManager.this.klm.lmn();
                    }
                }
            });
        }
    }

    @Deprecated
    public void setEnableAndroidID(Boolean bool) {
        if (this.lmn) {
            this.klm.klm(bool.booleanValue());
        }
    }

    public void syncOaid(CallBack callBack) {
        if (callBack == null) {
            HiLog.w("InteractionManager", "callback is null");
            return;
        }
        if (!ar.lmn().lmn.fgh) {
            HiLog.e("InteractionManager", "isAnalyticsEnabled false");
            callBack.onResult(CallBack.ANALYTICS_CLOSED, "analytics closed");
        } else {
            if (this.lmn) {
                this.klm.lmn(callBack);
                return;
            }
            callBack.onResult(CallBack.INIT_FAILED, this.ikl + " init failed");
        }
    }
}
