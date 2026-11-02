package com.huawei.hms.analytics;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import com.huawei.hms.analytics.core.log.HiLog;
import com.huawei.hms.analytics.instance.CallBack;

/* loaded from: classes2.dex */
public final class ak extends ijk {
    public ak(Context context, String str) {
        super(context, str);
        this.lmn = this.klm.build(str);
    }

    public final void lmn(CallBack callBack) {
        cx.lmn().lmn(new al(this.ghi, this.hij, callBack));
    }

    public final void lmn(String str, Bundle bundle) {
        Bundle bundle2;
        if (!ar.lmn().lmn.fgh) {
            HiLog.w("interactionInstance", "The Analytics Kit is disabled");
            return;
        }
        if (TextUtils.isEmpty(str)) {
            HiLog.w("interactionInstance", HiLog.ErrorCode.PE001, " param is null");
            return;
        }
        if (bundle == null) {
            HiLog.w("interactionInstance", "bundle is null");
            bundle2 = new Bundle();
        } else {
            bundle2 = (Bundle) bundle.clone();
        }
        dj djVar = new dj(str, this.hij);
        if (!dc.lmn(str, an.klm)) {
            djVar.ikl(bundle2);
            lmn(str, djVar);
        } else if (dc.lmn(str, 256)) {
            if (djVar.lmn(bundle2)) {
                lmn(str, djVar, System.currentTimeMillis());
            } else {
                HiLog.w("interactionInstance", HiLog.ErrorCode.PE006, "bundle params is invalid.");
            }
        }
    }
}
