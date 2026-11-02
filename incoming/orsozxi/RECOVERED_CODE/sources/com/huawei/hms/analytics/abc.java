package com.huawei.hms.analytics;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import com.huawei.hms.analytics.core.log.HiLog;

/* loaded from: classes2.dex */
public class abc implements Application.ActivityLifecycleCallbacks {
    private static abc lmn;
    private Context ijk;
    private boolean klm = false;
    private boolean ikl = false;

    public static abc lmn() {
        synchronized (abc.class) {
            if (lmn == null) {
                lmn = new abc();
            }
        }
        return lmn;
    }

    public final void lmn(Application application) {
        if (application == null || this.ikl) {
            HiLog.d("InitializeCallback", "application is null or has registered.");
            return;
        }
        try {
            this.ijk = application.getApplicationContext();
            application.registerActivityLifecycleCallbacks(lmn);
            this.ikl = true;
        } catch (Exception unused) {
            HiLog.e("InitializeCallback", "register lifecycle callback failed");
        }
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public void onActivityCreated(Activity activity, Bundle bundle) {
        if (activity == null) {
            return;
        }
        try {
            if (dl.klm(this.ijk, "global_v2", "is_analytics_enabled", true)) {
                bw bwVarLmn = cz.lmn(activity);
                if (!this.klm && TextUtils.isEmpty(ar.lmn().lmn.d)) {
                    cz.lmn(activity, bwVarLmn);
                    this.klm = true;
                }
                f.lmn(this.ijk, bwVarLmn);
            }
        } catch (Throwable th) {
            HiLog.w("InitializeCallback", th.getMessage());
            di.lmn(this.ijk, th);
        }
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public void onActivityDestroyed(Activity activity) {
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public void onActivityPaused(Activity activity) {
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public void onActivityResumed(Activity activity) {
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public void onActivityStarted(Activity activity) {
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public void onActivityStopped(Activity activity) {
    }
}
