package com.huawei.hms.push;

import android.content.Context;
import com.huawei.hms.support.log.HMSLog;
import java.net.URISyntaxException;

/* compiled from: SelfShowType.java */
/* loaded from: classes2.dex */
public class j {
    public static final String[] a = {"url", "app", "cosa", "rp"};
    public Context b;
    public k c;

    public j(Context context, k kVar) {
        this.b = context;
        this.c = kVar;
    }

    public static boolean a(String str) {
        for (String str2 : a) {
            if (str2.equals(str)) {
                return true;
            }
        }
        return false;
    }

    /* JADX WARN: Removed duplicated region for block: B:23:0x00bc A[PHI: r2 r3
      0x00bc: PHI (r2v22 android.content.Intent) = (r2v16 android.content.Intent), (r2v25 android.content.Intent) binds: [B:13:0x007a, B:22:0x00ba] A[DONT_GENERATE, DONT_INLINE]
      0x00bc: PHI (r3v5 boolean) = (r3v3 boolean), (r3v1 boolean) binds: [B:13:0x007a, B:22:0x00ba] A[DONT_GENERATE, DONT_INLINE]] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final void b() throws java.net.URISyntaxException {
        /*
            r6 = this;
            java.lang.String r0 = "PushSelfShowLog"
            java.lang.String r1 = "run into launchCosaApp"
            com.huawei.hms.support.log.HMSLog.i(r0, r1)
            java.lang.StringBuilder r1 = new java.lang.StringBuilder     // Catch: java.lang.Exception -> Le1
            r1.<init>()     // Catch: java.lang.Exception -> Le1
            java.lang.String r2 = "enter launchExistApp cosa, appPackageName ="
            r1.append(r2)     // Catch: java.lang.Exception -> Le1
            com.huawei.hms.push.k r2 = r6.c     // Catch: java.lang.Exception -> Le1
            java.lang.String r2 = r2.d()     // Catch: java.lang.Exception -> Le1
            r1.append(r2)     // Catch: java.lang.Exception -> Le1
            java.lang.String r2 = ",and msg.intentUri is "
            r1.append(r2)     // Catch: java.lang.Exception -> Le1
            com.huawei.hms.push.k r2 = r6.c     // Catch: java.lang.Exception -> Le1
            java.lang.String r2 = r2.n()     // Catch: java.lang.Exception -> Le1
            r1.append(r2)     // Catch: java.lang.Exception -> Le1
            java.lang.String r1 = r1.toString()     // Catch: java.lang.Exception -> Le1
            com.huawei.hms.support.log.HMSLog.i(r0, r1)     // Catch: java.lang.Exception -> Le1
            android.content.Context r1 = r6.b     // Catch: java.lang.Exception -> Le1
            com.huawei.hms.push.k r2 = r6.c     // Catch: java.lang.Exception -> Le1
            java.lang.String r2 = r2.d()     // Catch: java.lang.Exception -> Le1
            android.content.Intent r1 = com.huawei.hms.push.q.b(r1, r2)     // Catch: java.lang.Exception -> Le1
            com.huawei.hms.push.k r2 = r6.c     // Catch: java.lang.Exception -> Le1
            java.lang.String r2 = r2.n()     // Catch: java.lang.Exception -> Le1
            r3 = 0
            if (r2 == 0) goto L97
            com.huawei.hms.push.k r2 = r6.c     // Catch: java.lang.Exception -> L7d
            java.lang.String r2 = r2.n()     // Catch: java.lang.Exception -> L7d
            android.content.Intent r2 = android.content.Intent.parseUri(r2, r3)     // Catch: java.lang.Exception -> L7d
            r4 = 0
            r2.setSelector(r4)     // Catch: java.lang.Exception -> L7d
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch: java.lang.Exception -> L7d
            r4.<init>()     // Catch: java.lang.Exception -> L7d
            java.lang.String r5 = "Intent.parseUri(msg.intentUri, 0), action:"
            r4.append(r5)     // Catch: java.lang.Exception -> L7d
            java.lang.String r5 = r2.getAction()     // Catch: java.lang.Exception -> L7d
            r4.append(r5)     // Catch: java.lang.Exception -> L7d
            java.lang.String r4 = r4.toString()     // Catch: java.lang.Exception -> L7d
            com.huawei.hms.support.log.HMSLog.i(r0, r4)     // Catch: java.lang.Exception -> L7d
            android.content.Context r4 = r6.b     // Catch: java.lang.Exception -> L7d
            com.huawei.hms.push.k r5 = r6.c     // Catch: java.lang.Exception -> L7d
            java.lang.String r5 = r5.d()     // Catch: java.lang.Exception -> L7d
            java.lang.Boolean r4 = com.huawei.hms.push.q.a(r4, r5, r2)     // Catch: java.lang.Exception -> L7d
            boolean r3 = r4.booleanValue()     // Catch: java.lang.Exception -> L7d
            if (r3 == 0) goto Lbd
            goto Lbc
        L7d:
            r2 = move-exception
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch: java.lang.Exception -> Le1
            r4.<init>()     // Catch: java.lang.Exception -> Le1
            java.lang.String r5 = "intentUri error."
            r4.append(r5)     // Catch: java.lang.Exception -> Le1
            java.lang.String r2 = r2.toString()     // Catch: java.lang.Exception -> Le1
            r4.append(r2)     // Catch: java.lang.Exception -> Le1
            java.lang.String r2 = r4.toString()     // Catch: java.lang.Exception -> Le1
            com.huawei.hms.support.log.HMSLog.w(r0, r2)     // Catch: java.lang.Exception -> Le1
            goto Lbd
        L97:
            com.huawei.hms.push.k r2 = r6.c     // Catch: java.lang.Exception -> Le1
            java.lang.String r2 = r2.a()     // Catch: java.lang.Exception -> Le1
            if (r2 == 0) goto Lbd
            android.content.Intent r2 = new android.content.Intent     // Catch: java.lang.Exception -> Le1
            com.huawei.hms.push.k r4 = r6.c     // Catch: java.lang.Exception -> Le1
            java.lang.String r4 = r4.a()     // Catch: java.lang.Exception -> Le1
            r2.<init>(r4)     // Catch: java.lang.Exception -> Le1
            android.content.Context r4 = r6.b     // Catch: java.lang.Exception -> Le1
            com.huawei.hms.push.k r5 = r6.c     // Catch: java.lang.Exception -> Le1
            java.lang.String r5 = r5.d()     // Catch: java.lang.Exception -> Le1
            java.lang.Boolean r4 = com.huawei.hms.push.q.a(r4, r5, r2)     // Catch: java.lang.Exception -> Le1
            boolean r4 = r4.booleanValue()     // Catch: java.lang.Exception -> Le1
            if (r4 == 0) goto Lbd
        Lbc:
            r1 = r2
        Lbd:
            if (r1 != 0) goto Lc5
            java.lang.String r1 = "launchCosaApp,intent == null"
            com.huawei.hms.support.log.HMSLog.i(r0, r1)     // Catch: java.lang.Exception -> Le1
            return
        Lc5:
            com.huawei.hms.push.k r2 = r6.c     // Catch: java.lang.Exception -> Le1
            java.lang.String r2 = r2.d()     // Catch: java.lang.Exception -> Le1
            r1.setPackage(r2)     // Catch: java.lang.Exception -> Le1
            if (r3 == 0) goto Ld6
            r2 = 268435456(0x10000000, float:2.524355E-29)
            r1.addFlags(r2)     // Catch: java.lang.Exception -> Le1
            goto Ldb
        Ld6:
            r2 = 805437440(0x30020000, float:4.7293724E-10)
            r1.setFlags(r2)     // Catch: java.lang.Exception -> Le1
        Ldb:
            android.content.Context r2 = r6.b     // Catch: java.lang.Exception -> Le1
            r2.startActivity(r1)     // Catch: java.lang.Exception -> Le1
            goto Lfa
        Le1:
            r1 = move-exception
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r2.<init>()
            java.lang.String r3 = "launch Cosa App exception."
            r2.append(r3)
            java.lang.String r1 = r1.toString()
            r2.append(r1)
            java.lang.String r1 = r2.toString()
            com.huawei.hms.support.log.HMSLog.e(r0, r1)
        Lfa:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.huawei.hms.push.j.b():void");
    }

    public void c() throws URISyntaxException {
        k kVar;
        HMSLog.d("PushSelfShowLog", "enter launchNotify()");
        if (this.b == null || (kVar = this.c) == null) {
            HMSLog.d("PushSelfShowLog", "launchNotify  context or msg is null");
            return;
        }
        if ("app".equals(kVar.i())) {
            a();
            return;
        }
        if ("cosa".equals(this.c.i())) {
            b();
            return;
        }
        if ("rp".equals(this.c.i())) {
            HMSLog.w("PushSelfShowLog", this.c.i() + " not support rich message.");
            return;
        }
        if ("url".equals(this.c.i())) {
            HMSLog.w("PushSelfShowLog", this.c.i() + " not support URL.");
            return;
        }
        HMSLog.d("PushSelfShowLog", this.c.i() + " is not exist in hShowType");
    }

    public final void a() {
        try {
            StringBuilder sb = new StringBuilder();
            sb.append("enter launchApp, appPackageName =");
            sb.append(this.c.d());
            HMSLog.i("PushSelfShowLog", sb.toString());
            if (q.c(this.b, this.c.d())) {
                b();
            }
        } catch (Exception e) {
            HMSLog.e("PushSelfShowLog", "launchApp error:" + e.toString());
        }
    }
}
