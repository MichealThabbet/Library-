package com.huawei.hms.analytics;

import com.huawei.hms.analytics.core.log.HiLog;
import org.json.JSONArray;
import org.json.JSONException;

/* loaded from: classes2.dex */
public final class aw {
    public static JSONArray lmn() {
        String strKlm = dl.klm(ar.lmn().lmn.ghi, "global_v2", "ab_info", "");
        if (strKlm.isEmpty()) {
            return new JSONArray();
        }
        try {
            return new JSONArray(strKlm);
        } catch (JSONException unused) {
            HiLog.w("ABTskIdHolder", "cache abProperty is error, json exception");
            dl.lmn(ar.lmn().lmn.ghi, "global_v2", "ab_info");
            return new JSONArray();
        }
    }

    public static JSONArray lmn(JSONArray jSONArray) {
        int length = jSONArray.length();
        JSONArray jSONArray2 = new JSONArray();
        for (int i = length - 25; i < length; i++) {
            try {
                jSONArray2.put(jSONArray.get(i));
            } catch (JSONException unused) {
                HiLog.w("ABTskIdHolder", "cache ab info is error");
            }
        }
        return jSONArray2;
    }
}
