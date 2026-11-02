package com.huawei.hms.analytics;

import android.content.Context;
import com.huawei.hms.analytics.core.log.HiLog;
import java.util.Map;

/* loaded from: classes2.dex */
public final class e extends b {
    public e(bw bwVar, Context context) {
        this.ikl = bwVar;
        this.klm = context;
        this.lmn = "MiPushHelper";
    }

    @Override // com.huawei.hms.analytics.d
    public final ao lmn() {
        try {
            Class<?> cls = Class.forName("com.xiaomi.mipush.sdk.MiPushMessage");
            Object objInvoke = cls.getDeclaredMethod("getExtra", new Class[0]).invoke(this.ikl.ijk("key_message"), new Object[0]);
            if (objInvoke instanceof Map) {
                String str = (String) ((Map) objInvoke).remove("$CampaignPushInfo");
                if (!lmn(str)) {
                    return new ao(str);
                }
                HiLog.i(this.lmn, "campaign info check failed");
                return null;
            }
        } catch (Throwable unused) {
            HiLog.w("MiPushHelper", "getCampInfo exception");
        }
        return null;
    }
}
