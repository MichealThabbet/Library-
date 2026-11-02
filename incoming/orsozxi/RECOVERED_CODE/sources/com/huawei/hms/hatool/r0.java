package com.huawei.hms.hatool;

import android.text.TextUtils;
import com.huawei.secure.android.common.encrypt.keystore.aes.AesGcmKS;
import java.util.HashMap;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes2.dex */
public class r0 {
    public static void a(String str, String str2) {
        b0 b0VarA;
        String str3;
        String strReplace = "{url}/getPublicKey?keytype=2".replace("{url}", c.f(str, str2));
        String strF = b.f();
        HashMap map = new HashMap();
        map.put("App-Id", strF);
        try {
            b0VarA = a0.a(strReplace, new byte[0], map);
        } catch (Exception e) {
            y.e("GetPublicKey", "get pubKey response Exception :" + e.getMessage());
            b0VarA = null;
        }
        if (b0VarA == null) {
            str3 = "get pubKey response is null";
        } else if (b0VarA.b() == 200) {
            if (TextUtils.isEmpty(b0VarA.a())) {
                return;
            }
            c(b0VarA.a(), str2);
            return;
        } else {
            str3 = "get pubKey fail HttpCode :" + b0VarA.b();
        }
        y.e("GetPublicKey", str3);
    }

    public static boolean a() throws NumberFormatException {
        String strA = b.a();
        if (TextUtils.isEmpty(strA)) {
            strA = g0.a(b.i(), "Privacy_MY", "public_key_time_interval", "");
            b.f(strA);
        }
        String strM = b.m();
        if (TextUtils.isEmpty(strM)) {
            strM = g0.a(b.i(), "Privacy_MY", "public_key_time_last", "");
            b.c(strM);
        }
        if (TextUtils.isEmpty(strA) || TextUtils.isEmpty(strM)) {
            return true;
        }
        try {
            return System.currentTimeMillis() - Long.parseLong(strM) > ((long) Integer.parseInt(strA));
        } catch (NumberFormatException e) {
            y.e("GetPublicKey", "checkCachePubKey NumberFormatException :" + e.getMessage());
            return true;
        }
    }

    public static String b(String str, String str2) {
        String strO;
        String strC = b.c();
        if (TextUtils.isEmpty(strC)) {
            strC = g0.a(b.i(), "Privacy_MY", "public_key_version", "");
            b.g(strC);
        }
        if ("maint".equals(str2)) {
            strO = b.n();
            if (TextUtils.isEmpty(strO)) {
                strO = AesGcmKS.decrypt("HiAnalytics_Sdk_Public_Sp_Key", g0.a(b.i(), "Privacy_MY", "public_key_maint", ""));
                b.d(strO);
            }
        } else {
            strO = b.o();
            if (TextUtils.isEmpty(strO)) {
                strO = AesGcmKS.decrypt("HiAnalytics_Sdk_Public_Sp_Key", g0.a(b.i(), "Privacy_MY", "public_key_oper", ""));
                b.e(strO);
            }
        }
        if (!TextUtils.isEmpty(strO) && !TextUtils.isEmpty(strC) && !a()) {
            return strO;
        }
        o0.a().a(new m0(str, str2));
        return null;
    }

    public static void c(String str, String str2) {
        try {
            JSONObject jSONObject = new JSONObject(str);
            String strOptString = jSONObject.optString("publicKey");
            String strOptString2 = jSONObject.optString("publicKeyOM");
            String strOptString3 = jSONObject.optString("pubkey_version");
            String str3 = System.currentTimeMillis() + "";
            String strOptString4 = jSONObject.optString("timeInterval");
            g0.b(b.i(), "Privacy_MY", "public_key_oper", AesGcmKS.encrypt("HiAnalytics_Sdk_Public_Sp_Key", strOptString));
            g0.b(b.i(), "Privacy_MY", "public_key_maint", AesGcmKS.encrypt("HiAnalytics_Sdk_Public_Sp_Key", strOptString2));
            g0.b(b.i(), "Privacy_MY", "public_key_time_interval", strOptString4);
            g0.b(b.i(), "Privacy_MY", "public_key_version", strOptString3);
            g0.b(b.i(), "Privacy_MY", "public_key_time_last", str3);
            b.e(strOptString);
            b.d(strOptString2);
            b.g(strOptString3);
            b.c(str3);
            b.f(strOptString4);
        } catch (JSONException e) {
            y.e("GetPublicKey", "get pubKey parse json JSONException :" + e.getMessage());
        }
    }
}
