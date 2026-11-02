package com.huawei.hms.analytics;

import android.os.Build;
import android.text.TextUtils;
import com.huawei.hms.analytics.core.log.HiLog;
import com.huawei.hms.analytics.core.transport.net.Response;
import it.orsozoxi.android.orsonotes.R2;
import java.util.Map;
import java.util.Random;
import org.apache.http.client.methods.HttpPost;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes2.dex */
public final class bz extends cb {
    private String lmn;

    public bz(String str, String str2) {
        super(cd.lmn("GetPublicKey#execute"), HttpPost.METHOD_NAME, "/getPublicKey", str2);
        this.lmn = str;
    }

    private boolean ijk() {
        at atVar = ar.lmn().lmn;
        String strKlm = dl.klm(atVar.ghi, "Privacy_MY", this.ijk, "");
        if (TextUtils.isEmpty(strKlm)) {
            return true;
        }
        try {
            JSONObject jSONObject = new JSONObject(strKlm);
            String string = jSONObject.getString("timeInterval");
            long j = jSONObject.getLong("requestTime");
            String string2 = jSONObject.getString("publicKey");
            String string3 = jSONObject.getString("pubkey_version");
            if (System.currentTimeMillis() - j > Long.parseLong(string)) {
                return true;
            }
            atVar.lmn(this.ijk, string2);
            atVar.klm(this.ijk, string3);
            return false;
        } catch (Throwable th) {
            dl.lmn(atVar.ghi, "Privacy_MY", this.ijk);
            di.lmn(th);
            return true;
        }
    }

    private static String lmn(String str) {
        au auVarLmn;
        if (str == null || (auVarLmn = ar.lmn().lmn(str)) == null || auVarLmn.klm == null) {
            return null;
        }
        return auVarLmn.klm.get("ha-sdk-service");
    }

    @Override // com.huawei.hms.analytics.cb
    public final void klm() {
        if (!ijk()) {
            HiLog.d("RequestBas", "no need sync pub key");
        } else {
            dl.lmn(ar.lmn().lmn.ghi, "Privacy_MY", "pubK_info");
            super.klm();
        }
    }

    @Override // com.huawei.hms.analytics.cb
    public final Map<String, String> lmn() {
        Map<String, String> mapLmn = super.lmn();
        mapLmn.put("Os", "0");
        mapLmn.put("Os-Ver", Build.VERSION.RELEASE);
        if (lmn(this.lmn) != null) {
            mapLmn.put("ha-sdk-service", lmn(this.lmn));
        }
        return mapLmn;
    }

    @Override // com.huawei.hms.analytics.core.transport.CallbackListener
    public final void onFailure(int i) {
        HiLog.e("RequestBas", "GET pub key exception,errorCode: ".concat(String.valueOf(i)));
        lmn(i);
    }

    @Override // com.huawei.hms.analytics.core.transport.CallbackListener
    public final void onSuccess(Response response) {
        String str;
        this.ikl.lmn = "0";
        lmn(response.getHttpCode());
        try {
            if (response.getHttpCode() == 200) {
                HiLog.i("RequestBas", "get pub key result code : " + response.getHttpCode());
                JSONObject jSONObject = new JSONObject(response.getContent());
                String string = jSONObject.getString("publicKey");
                String string2 = jSONObject.getString("pubkey_version");
                if (!TextUtils.isEmpty(string) && !TextUtils.isEmpty(string2)) {
                    jSONObject.put("timeInterval", Long.parseLong(jSONObject.getString("timeInterval")) + (new Random().nextInt(R2.color.material_slider_active_track_color) * 60000));
                    jSONObject.put("requestTime", System.currentTimeMillis());
                    at atVar = ar.lmn().lmn;
                    atVar.lmn(this.ijk, string.trim());
                    atVar.klm(this.ijk, string2);
                    dl.lmn(atVar.ghi, "Privacy_MY", this.ijk, jSONObject.toString());
                    return;
                }
                HiLog.e("RequestBas", "pub key or version is empty");
            }
        } catch (JSONException unused) {
            str = "GET pub key,json exception";
            HiLog.e("RequestBas", str);
        } catch (Throwable th) {
            str = "GET pub key exception " + th.getMessage();
            HiLog.e("RequestBas", str);
        }
    }
}
