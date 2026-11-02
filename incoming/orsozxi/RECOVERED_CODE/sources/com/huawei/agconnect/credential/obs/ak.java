package com.huawei.agconnect.credential.obs;

import java.util.HashMap;
import java.util.Map;

/* loaded from: classes2.dex */
public final class ak {
    private static final Map<String, aj> a = new HashMap();

    /* JADX WARN: Removed duplicated region for block: B:25:0x004f  */
    /* JADX WARN: Removed duplicated region for block: B:28:0x0052 A[Catch: all -> 0x007f, TRY_ENTER, TryCatch #0 {, blocks: (B:4:0x0003, B:7:0x0012, B:28:0x0052, B:30:0x005e, B:33:0x006c, B:37:0x007a, B:10:0x001d, B:12:0x0025, B:15:0x0030, B:17:0x0038), top: B:43:0x0003 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    static synchronized com.huawei.agconnect.credential.obs.ai a(android.content.Context r4, com.huawei.agconnect.credential.obs.al r5) {
        /*
            java.lang.Class<com.huawei.agconnect.credential.obs.ak> r0 = com.huawei.agconnect.credential.obs.ak.class
            monitor-enter(r0)
            android.os.Looper r1 = android.os.Looper.getMainLooper()     // Catch: java.lang.Throwable -> L7f
            android.os.Looper r2 = android.os.Looper.myLooper()     // Catch: java.lang.Throwable -> L7f
            r3 = 0
            if (r1 == r2) goto L16
            java.lang.String r1 = "HAInstanceManager"
            java.lang.String r2 = "init must be called in the main thread"
        L12:
            android.util.Log.e(r1, r2)     // Catch: java.lang.Throwable -> L7f
            goto L4d
        L16:
            if (r4 != 0) goto L1d
            java.lang.String r1 = "HAInstanceManager"
            java.lang.String r2 = "context cannot be null to init HiAnalyticsInstanceEx."
            goto L12
        L1d:
            java.lang.String r1 = r5.a     // Catch: java.lang.Throwable -> L7f
            boolean r1 = android.text.TextUtils.isEmpty(r1)     // Catch: java.lang.Throwable -> L7f
            if (r1 != 0) goto L48
            java.lang.String r1 = r5.a     // Catch: java.lang.Throwable -> L7f
            int r1 = r1.length()     // Catch: java.lang.Throwable -> L7f
            r2 = 256(0x100, float:3.59E-43)
            if (r1 <= r2) goto L30
            goto L48
        L30:
            java.lang.String r1 = r5.b     // Catch: java.lang.Throwable -> L7f
            boolean r1 = android.text.TextUtils.isEmpty(r1)     // Catch: java.lang.Throwable -> L7f
            if (r1 != 0) goto L43
            java.lang.String r1 = r5.b     // Catch: java.lang.Throwable -> L7f
            int r1 = r1.length()     // Catch: java.lang.Throwable -> L7f
            if (r1 <= r2) goto L41
            goto L43
        L41:
            r3 = 1
            goto L4d
        L43:
            java.lang.String r1 = "HAInstanceManager"
            java.lang.String r2 = "httpheader check failed"
            goto L12
        L48:
            java.lang.String r1 = "HAInstanceManager"
            java.lang.String r2 = "serviceTag check failed"
            goto L12
        L4d:
            if (r3 != 0) goto L52
            r4 = 0
            monitor-exit(r0)
            return r4
        L52:
            java.util.Map<java.lang.String, com.huawei.agconnect.credential.obs.aj> r1 = com.huawei.agconnect.credential.obs.ak.a     // Catch: java.lang.Throwable -> L7f
            java.lang.String r2 = r5.a     // Catch: java.lang.Throwable -> L7f
            java.lang.Object r1 = r1.get(r2)     // Catch: java.lang.Throwable -> L7f
            com.huawei.agconnect.credential.obs.aj r1 = (com.huawei.agconnect.credential.obs.aj) r1     // Catch: java.lang.Throwable -> L7f
            if (r1 != 0) goto L6c
            com.huawei.agconnect.credential.obs.aj r1 = new com.huawei.agconnect.credential.obs.aj     // Catch: java.lang.Throwable -> L7f
            r1.<init>(r4, r5)     // Catch: java.lang.Throwable -> L7f
            java.util.Map<java.lang.String, com.huawei.agconnect.credential.obs.aj> r4 = com.huawei.agconnect.credential.obs.ak.a     // Catch: java.lang.Throwable -> L7f
            java.lang.String r5 = r5.a     // Catch: java.lang.Throwable -> L7f
            r4.put(r5, r1)     // Catch: java.lang.Throwable -> L7f
            monitor-exit(r0)
            return r1
        L6c:
            java.lang.String r2 = r5.c     // Catch: java.lang.Throwable -> L7f
            com.huawei.agconnect.credential.obs.al r3 = r1.a     // Catch: java.lang.Throwable -> L7f
            java.lang.String r3 = r3.c     // Catch: java.lang.Throwable -> L7f
            boolean r2 = r2.equals(r3)     // Catch: java.lang.Throwable -> L7f
            if (r2 == 0) goto L7a
            monitor-exit(r0)
            return r1
        L7a:
            r1.a(r4, r5)     // Catch: java.lang.Throwable -> L7f
            monitor-exit(r0)
            return r1
        L7f:
            r4 = move-exception
            monitor-exit(r0)
            throw r4
        */
        throw new UnsupportedOperationException("Method not decompiled: com.huawei.agconnect.credential.obs.ak.a(android.content.Context, com.huawei.agconnect.credential.obs.al):com.huawei.agconnect.credential.obs.ai");
    }
}
