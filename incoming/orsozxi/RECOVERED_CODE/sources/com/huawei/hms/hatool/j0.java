package com.huawei.hms.hatool;

import android.content.Context;
import android.text.TextUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes2.dex */
public class j0 implements n0 {
    public Context a = b.i();
    public String b;
    public JSONObject c;
    public String d;
    public String e;
    public String f;
    public String g;
    public Boolean h;

    public j0(String str, JSONObject jSONObject, String str2, String str3, long j) {
        this.b = str;
        this.c = jSONObject;
        this.d = str2;
        this.e = str3;
        this.f = String.valueOf(j);
        if (a.i(str2, "oper")) {
            f0 f0VarA = e0.a().a(str2, j);
            this.g = f0VarA.a();
            this.h = Boolean.valueOf(f0VarA.b());
        }
    }

    @Override // java.lang.Runnable
    public void run() {
        JSONArray jSONArray;
        y.c("hmsSdk", "Begin to run EventRecordTask...");
        int iH = b.h();
        int iK = c.k(this.d, this.e);
        if (q0.a(this.a, "stat_v2_1", iH * 1048576)) {
            y.c("hmsSdk", "stat sp file reach max limited size, discard new event");
            h0.a().a("", "alltype");
            return;
        }
        q qVar = new q();
        qVar.b(this.b);
        qVar.a(this.c.toString());
        qVar.d(this.e);
        qVar.c(this.f);
        qVar.f(this.g);
        Boolean bool = this.h;
        qVar.e(bool == null ? null : String.valueOf(bool));
        try {
            JSONObject jSONObjectD = qVar.d();
            String strA = u0.a(this.d, this.e);
            String strA2 = g0.a(this.a, "stat_v2_1", strA, "");
            try {
                jSONArray = !TextUtils.isEmpty(strA2) ? new JSONArray(strA2) : new JSONArray();
            } catch (JSONException unused) {
                y.d("hmsSdk", "Cached data corrupted: stat_v2_1");
                jSONArray = new JSONArray();
            }
            jSONArray.put(jSONObjectD);
            g0.b(this.a, "stat_v2_1", strA, jSONArray.toString());
            if (jSONArray.toString().length() > iK * 1024) {
                h0.a().a(this.d, this.e);
            }
        } catch (JSONException unused2) {
            y.e("hmsSdk", "eventRecord toJson error! The record failed.");
        }
    }
}
