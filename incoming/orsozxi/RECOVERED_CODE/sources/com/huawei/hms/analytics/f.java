package com.huawei.hms.analytics;

import android.content.Context;
import android.text.TextUtils;
import com.huawei.hms.analytics.core.log.HiLog;
import org.json.JSONObject;

/* loaded from: classes2.dex */
public final class f {
    private static void klm(Context context, JSONObject jSONObject) {
        if (ba.lmn()) {
            bh bhVarLmn = fgh.lmn().lmn("_openness_config_tag");
            if (bhVarLmn instanceof ghi) {
                jSONObject.remove("t");
                ((ghi) bhVarLmn).klm("$CampaignPushClick", new dj("$CampaignPushClick", jSONObject), System.currentTimeMillis());
                return;
            }
            return;
        }
        String strKlm = dl.klm(context, "Privacy_MY", "_openness_config_tag_region", "");
        if (TextUtils.isEmpty(strKlm)) {
            HiAnalytics.getInstance(context);
        } else {
            HiAnalytics.getInstance(context, strKlm);
        }
    }

    private static d lmn(bw bwVar, Context context) {
        return z.ikl() ? new e(bwVar, context) : new c(bwVar, context);
    }

    private static JSONObject lmn(bw bwVar) {
        String strLmn = cz.lmn(bwVar);
        if (!TextUtils.isEmpty(strLmn) && strLmn.length() <= 20480) {
            try {
                JSONObject jSONObject = new JSONObject(strLmn);
                if (jSONObject.has("t")) {
                    return jSONObject;
                }
                HiLog.w("PshCamp", "The time key is missing.");
                return null;
            } catch (Throwable unused) {
                HiLog.w("PshCamp", "JSONException: Failed to format Json.");
            }
        }
        return null;
    }

    public static void lmn(Context context, bw bwVar) {
        if (bwVar == null) {
            return;
        }
        cz.klm(bwVar);
        JSONObject jSONObjectLmn = lmn(bwVar);
        if (jSONObjectLmn != null) {
            new c(bwVar, context).lmn(jSONObjectLmn, (JSONObject) null);
            return;
        }
        d dVarLmn = lmn(bwVar, context);
        ao aoVarLmn = dVarLmn.lmn();
        if (aoVarLmn == null) {
            return;
        }
        JSONObject jSONObjectLmn2 = dVarLmn.lmn(aoVarLmn).lmn();
        dVarLmn.lmn(aoVarLmn.lmn(), jSONObjectLmn2);
        lmn(context, jSONObjectLmn2);
    }

    private static void lmn(Context context, JSONObject jSONObject) {
        if (jSONObject == null) {
            return;
        }
        klm(context, jSONObject);
    }
}
