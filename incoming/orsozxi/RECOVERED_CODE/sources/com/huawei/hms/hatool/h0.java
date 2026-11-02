package com.huawei.hms.hatool;

import android.content.Context;
import android.text.TextUtils;
import java.util.HashMap;
import org.json.JSONObject;

/* loaded from: classes2.dex */
public class h0 {
    public static h0 b;
    public Context a;

    static {
        new HashMap();
    }

    public static h0 a() {
        return b();
    }

    public static synchronized h0 b() {
        if (b == null) {
            b = new h0();
        }
        return b;
    }

    public void a(Context context) {
        this.a = context;
        b(context);
        i.c().b().h(f.a());
    }

    public void a(String str, int i) {
        if (this.a == null) {
            y.e("hmsSdk", "onReport() null context or SDK was not init.");
        } else {
            y.c("hmsSdk", "onReport: Before calling runtaskhandler()");
            a(str, u0.a(i), b.g());
        }
    }

    public void a(String str, int i, String str2, JSONObject jSONObject) {
        long jCurrentTimeMillis = System.currentTimeMillis();
        if (2 == i) {
            jCurrentTimeMillis = u0.a("yyyy-MM-dd", jCurrentTimeMillis);
        }
        o0.c().a(new j0(str2, jSONObject, str, u0.a(i), jCurrentTimeMillis));
    }

    public void a(String str, String str2) {
        if (!c.a(str, str2)) {
            y.c("hmsSdk", "auto report is closed tag:" + str);
            return;
        }
        long j = c.j(str, str2);
        long jCurrentTimeMillis = System.currentTimeMillis();
        if (jCurrentTimeMillis - j <= 30000) {
            y.f("hmsSdk", "autoReport timeout. interval < 30s ");
            return;
        }
        y.a("hmsSdk", "begin to call onReport!");
        c.a(str, str2, jCurrentTimeMillis);
        a(str, str2, b.g());
    }

    public void a(String str, String str2, String str3) {
        Context context = this.a;
        if (context == null) {
            y.e("hmsSdk", "onReport() null context or SDK was not init.");
            return;
        }
        String strA = h.a(context);
        if (c.e(str, str2) && !"WIFI".equals(strA)) {
            y.c("hmsSdk", "strNetworkType is :" + strA);
            return;
        }
        if (TextUtils.isEmpty(strA) || "2G".equals(strA)) {
            y.e("hmsSdk", "The network is bad.");
        } else {
            o0.c().a(new k0(str, str2, str3));
        }
    }

    public final void b(Context context) {
        String str;
        String strD = f.d(context);
        b.a(strD);
        if (w0.b().a()) {
            String strA = g0.a(context, "global_v2", "app_ver", "");
            g0.b(context, "global_v2", "app_ver", strD);
            b.b(strA);
            if (!TextUtils.isEmpty(strA)) {
                if (strA.equals(strD)) {
                    return;
                }
                y.c("hmsSdk", "the appVers are different!");
                a().a("", "alltype", strA);
                return;
            }
            str = "app ver is first save!";
        } else {
            str = "userManager.isUserUnlocked() == false";
        }
        y.c("hmsSdk", str);
    }
}
