package com.huawei.hms.analytics;

import android.text.TextUtils;
import com.facebook.internal.ServerProtocol;
import com.huawei.hms.analytics.core.log.HiLog;
import com.huawei.hms.analytics.core.transport.net.Response;
import org.apache.http.client.methods.HttpGet;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes2.dex */
public final class by extends cb {
    public by(String str) {
        super(cd.lmn("GetOaidSwitch#execute"), HttpGet.METHOD_NAME, "/analytics/api/config?config_key=oaid_flag", str);
    }

    private static boolean ijk() throws JSONException {
        at atVar = ar.lmn().lmn;
        String strKlm = dl.klm(atVar.ghi, "global_v2", "oid_switch_info", "");
        if (TextUtils.isEmpty(strKlm)) {
            return true;
        }
        try {
            JSONObject jSONObject = new JSONArray(strKlm).getJSONObject(0);
            if (System.currentTimeMillis() - jSONObject.getLong("timestamp") > 86400000) {
                return true;
            }
            if (jSONObject.getString("config_value").equals(ServerProtocol.DIALOG_RETURN_SCOPES_TRUE)) {
                atVar.m = true;
            }
            return false;
        } catch (JSONException unused) {
            dl.lmn(atVar.ghi, "global_v2", "oid_switch_info");
            return true;
        }
    }

    @Override // com.huawei.hms.analytics.cb
    public final void klm() {
        if (ijk()) {
            super.klm();
        } else {
            HiLog.d("RequestBas", "no need sync oaid switch");
        }
    }

    @Override // com.huawei.hms.analytics.core.transport.CallbackListener
    public final void onFailure(int i) {
        HiLog.e("RequestBas", "GET oaid flag exception,errorCode: ".concat(String.valueOf(i)));
        lmn(i);
    }

    @Override // com.huawei.hms.analytics.core.transport.CallbackListener
    public final void onSuccess(Response response) throws JSONException {
        this.ikl.lmn = "0";
        lmn(response.getHttpCode());
        at atVar = ar.lmn().lmn;
        if (response.getHttpCode() == 200) {
            HiLog.i("RequestBas", "get oaid_flag result code : " + response.getHttpCode());
            try {
                JSONArray jSONArray = new JSONObject(response.getContent()).getJSONArray("configList");
                if (jSONArray.length() > 0) {
                    JSONObject jSONObject = jSONArray.getJSONObject(0);
                    atVar.m = jSONObject.getString("config_value").equals(ServerProtocol.DIALOG_RETURN_SCOPES_TRUE);
                    jSONObject.put("timestamp", System.currentTimeMillis());
                    dl.lmn(atVar.ghi, "global_v2", "oid_switch_info", jSONArray.toString());
                }
            } catch (JSONException unused) {
                atVar.m = false;
            }
        }
    }
}
