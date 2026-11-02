package com.huawei.hms.analytics;

import android.content.Context;
import com.huawei.hms.analytics.core.log.HiLog;
import org.json.JSONException;

/* loaded from: classes2.dex */
public final class c extends b {
    public c(bw bwVar, Context context) {
        this.ikl = bwVar;
        this.klm = context;
        this.lmn = "HwPushHandler";
    }

    @Override // com.huawei.hms.analytics.d
    public final ao lmn() {
        String str;
        String str2;
        String strHij = this.ikl.hij("$CampaignPushInfo");
        if (lmn(strHij)) {
            str = this.lmn;
            str2 = "campaign info check failed";
        } else {
            try {
                return new ao(strHij);
            } catch (JSONException unused) {
                str = this.lmn;
                str2 = "getCampInfo exception";
            }
        }
        HiLog.w(str, str2);
        return null;
    }
}
