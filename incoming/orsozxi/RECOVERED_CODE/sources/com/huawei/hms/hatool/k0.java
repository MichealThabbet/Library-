package com.huawei.hms.hatool;

import android.content.Context;
import android.util.Pair;
import java.util.List;
import java.util.Map;

/* loaded from: classes2.dex */
public class k0 implements n0 {
    public Context a = b.i();
    public String b;
    public String c;
    public String d;

    public k0(String str, String str2, String str3) {
        this.b = str;
        this.c = str2;
        this.d = str3;
    }

    public final void a(String str, List<q> list) {
        Pair<String, String> pairA = u0.a(str);
        new t(list, (String) pairA.first, (String) pairA.second, this.d).a();
    }

    @Override // java.lang.Runnable
    public void run() {
        Map<String, List<q>> mapA;
        y.c("hmsSdk", "eventReportTask is running");
        boolean zA = q0.a(this.a);
        if (zA) {
            y.c("hmsSdk", "workKey is refresh,begin report all data");
            this.c = "alltype";
        }
        try {
            try {
                mapA = w.a(this.a, this.b, this.c);
            } catch (IllegalArgumentException e) {
                y.e("hmsSdk", "readEventRecords handData IllegalArgumentException:" + e.getMessage());
                if ("alltype".equals(this.c)) {
                    g0.a(this.a, "stat_v2_1", new String[0]);
                    g0.a(this.a, "cached_v2_1", new String[0]);
                } else {
                    String strA = u0.a(this.b, this.c);
                    g0.a(this.a, "stat_v2_1", strA);
                    g0.a(this.a, "cached_v2_1", strA);
                }
            } catch (Exception e2) {
                y.e("hmsSdk", "readEventRecords handData Exception:" + e2.getMessage());
                if ("alltype".equals(this.c)) {
                    g0.a(this.a, "stat_v2_1", new String[0]);
                    g0.a(this.a, "cached_v2_1", new String[0]);
                } else {
                    String strA2 = u0.a(this.b, this.c);
                    g0.a(this.a, "stat_v2_1", strA2);
                    g0.a(this.a, "cached_v2_1", strA2);
                }
            }
            if (mapA.size() == 0) {
                y.b("hmsSdk", "no have events to report: tag:%s : type:%s", this.b, this.c);
                if ("alltype".equals(this.c)) {
                    g0.a(this.a, "stat_v2_1", new String[0]);
                    g0.a(this.a, "cached_v2_1", new String[0]);
                    return;
                } else {
                    String strA3 = u0.a(this.b, this.c);
                    g0.a(this.a, "stat_v2_1", strA3);
                    g0.a(this.a, "cached_v2_1", strA3);
                    return;
                }
            }
            for (Map.Entry<String, List<q>> entry : mapA.entrySet()) {
                a(entry.getKey(), entry.getValue());
            }
            if ("alltype".equals(this.c)) {
                g0.a(this.a, "stat_v2_1", new String[0]);
                g0.a(this.a, "cached_v2_1", new String[0]);
            } else {
                String strA4 = u0.a(this.b, this.c);
                g0.a(this.a, "stat_v2_1", strA4);
                g0.a(this.a, "cached_v2_1", strA4);
            }
            if (zA) {
                y.c("hmsSdk", "refresh local key");
                d0.f().b();
            }
        } catch (Throwable th) {
            if ("alltype".equals(this.c)) {
                g0.a(this.a, "stat_v2_1", new String[0]);
                g0.a(this.a, "cached_v2_1", new String[0]);
            } else {
                String strA5 = u0.a(this.b, this.c);
                g0.a(this.a, "stat_v2_1", strA5);
                g0.a(this.a, "cached_v2_1", strA5);
            }
            throw th;
        }
    }
}
