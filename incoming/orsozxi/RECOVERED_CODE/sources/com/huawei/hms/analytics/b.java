package com.huawei.hms.analytics;

import android.content.Context;
import android.text.TextUtils;
import com.huawei.hms.analytics.df;
import java.util.Collections;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes2.dex */
public abstract class b implements d {
    protected bw ikl;
    protected Context klm;
    protected String lmn = "BaseCampHandler";

    private static JSONArray klm(String str) {
        try {
            JSONArray jSONArray = new JSONArray(str);
            if (jSONArray.length() < 20) {
                return jSONArray;
            }
            List<JSONObject> listKlm = dd.klm(jSONArray);
            Collections.sort(listKlm, new df.lmn());
            if (listKlm.size() >= 20) {
                listKlm.remove(0);
            }
            return dd.lmn(listKlm);
        } catch (JSONException unused) {
            return new JSONArray();
        }
    }

    private JSONArray lmn(JSONObject jSONObject, String str) {
        if (jSONObject == null) {
            return null;
        }
        String strKlm = dl.klm(this.klm, "global_v2", str, "");
        if (TextUtils.isEmpty(strKlm)) {
            return new JSONArray().put(jSONObject);
        }
        JSONArray jSONArrayKlm = klm(strKlm);
        jSONArrayKlm.put(jSONObject);
        return jSONArrayKlm;
    }

    public static boolean lmn(String str) {
        return TextUtils.isEmpty(str) || str.length() > 20480;
    }

    @Override // com.huawei.hms.analytics.d
    public final ap lmn(ao aoVar) {
        return new ap(this.ikl.klm("_push_msgid"), this.ikl.klm("_push_cmd_type"), String.valueOf(this.ikl.ikl("_push_notifyid")), aoVar);
    }

    @Override // com.huawei.hms.analytics.d
    public final void lmn(JSONObject jSONObject, JSONObject jSONObject2) {
        if (jSONObject == null) {
            return;
        }
        dl.lmn(this.klm, "global_v2", "camp_info", lmn(jSONObject, "camp_info").toString(), "camp_evt", lmn(jSONObject2, "camp_evt").toString());
    }
}
