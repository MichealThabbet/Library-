package com.huawei.hms.hatool;

import android.text.TextUtils;
import java.io.UnsupportedEncodingException;
import java.util.Iterator;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes2.dex */
public class u {
    public String a;
    public String b;
    public String c;
    public List<q> d;
    public String e;

    public u(String str, String str2, String str3, List<q> list, String str4) {
        this.a = str;
        this.b = str2;
        this.c = str3;
        this.d = list;
        this.e = str4;
    }

    public final String a(String str, String str2) {
        String str3;
        String strF = c.f(str, str2);
        if (TextUtils.isEmpty(strF)) {
            y.a("hmsSdk", "No report address,TAG : %s,TYPE: %s ", str, str2);
            return "";
        }
        if ("oper".equals(str2)) {
            str3 = "{url}/common/hmshioperqrt";
        } else if ("maint".equals(str2)) {
            str3 = "{url}/common/hmshimaintqrt";
        } else {
            if (!"diffprivacy".equals(str2)) {
                return "";
            }
            str3 = "{url}/common/common2";
        }
        return str3.replace("{url}", strF);
    }

    public void a() {
        n0 l0Var;
        o0 o0VarC;
        String str;
        String strA = a(this.a, this.b);
        if (!TextUtils.isEmpty(strA) || "preins".equals(this.b)) {
            if (!"_hms_config_tag".equals(this.a) && !"_openness_config_tag".equals(this.a)) {
                b();
            }
            C0103r c0103rD = d();
            if (c0103rD != null) {
                byte[] bArrA = a(c0103rD);
                if (bArrA.length == 0) {
                    str = "request body is empty";
                } else {
                    l0Var = new i0(bArrA, strA, this.a, this.b, this.e, this.d);
                    o0VarC = o0.b();
                }
            } else {
                l0Var = new l0(this.d, this.a, this.e, this.b);
                o0VarC = o0.c();
            }
            o0VarC.a(l0Var);
            return;
        }
        str = "collectUrl is empty";
        y.e("hmsSdk", str);
    }

    public final byte[] a(C0103r c0103r) {
        String str;
        try {
            JSONObject jSONObjectA = c0103r.a();
            if (jSONObjectA != null) {
                return t0.a(jSONObjectA.toString().getBytes("UTF-8"));
            }
            y.e("hmsSdk", "uploadEvents is null");
            return new byte[0];
        } catch (UnsupportedEncodingException unused) {
            str = "sendData(): getBytes - Unsupported coding format!!";
            y.e("hmsSdk", str);
            return new byte[0];
        } catch (JSONException unused2) {
            str = "uploadEvents to json error";
            y.e("hmsSdk", str);
            return new byte[0];
        }
    }

    public final void b() {
        if (q0.a(b.i(), "backup_event", 5242880)) {
            y.d("hmsSdk", "backup file reach max limited size, discard new event ");
            return;
        }
        JSONArray jSONArrayC = c();
        String strA = u0.a(this.a, this.b, this.e);
        y.c("hmsSdk", "Update data cached into backup,spKey: " + strA);
        g0.b(b.i(), "backup_event", strA, jSONArrayC.toString());
    }

    public final JSONArray c() {
        JSONArray jSONArray = new JSONArray();
        Iterator<q> it2 = this.d.iterator();
        while (it2.hasNext()) {
            try {
                jSONArray.put(it2.next().d());
            } catch (JSONException unused) {
                y.c("hmsSdk", "handleEvents: json error,Abandon this data");
            }
        }
        return jSONArray;
    }

    public final C0103r d() {
        return d1.a(this.d, this.a, this.b, this.e, this.c);
    }
}
