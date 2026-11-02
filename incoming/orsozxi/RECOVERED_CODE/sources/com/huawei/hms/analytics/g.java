package com.huawei.hms.analytics;

import android.content.Context;
import com.huawei.hmf.tasks.OnFailureListener;
import com.huawei.hmf.tasks.OnSuccessListener;
import com.huawei.hms.analytics.core.log.HiLog;

/* loaded from: classes2.dex */
public final class g {
    public p lmn;

    public static class klm implements OnSuccessListener<String> {
        @Override // com.huawei.hmf.tasks.OnSuccessListener
        public final /* synthetic */ void onSuccess(String str) {
            HiLog.i("HAObservableHolder", str);
        }
    }

    public static class lmn implements OnFailureListener {
        @Override // com.huawei.hmf.tasks.OnFailureListener
        public final void onFailure(Exception exc) {
            HiLog.e("HAObservableHolder", "Task notifyKitAPP fail." + exc.getMessage());
        }
    }

    public g(Context context) {
        this.lmn = new p(context);
    }
}
