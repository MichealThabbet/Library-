package com.huawei.hms.analytics.framework.c;

import com.huawei.hms.analytics.core.storage.Event;
import com.huawei.hms.analytics.framework.config.ICallback;
import java.util.List;

/* loaded from: classes2.dex */
public final class k implements Runnable {
    String a;
    String b;
    List<Event> c;
    ICallback d;

    public k(String str, String str2, List<Event> list, ICallback iCallback) {
        this.a = str;
        this.b = str2;
        this.c = list;
        this.d = iCallback;
    }

    /* JADX WARN: Removed duplicated region for block: B:8:0x003b  */
    @Override // java.lang.Runnable
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final void run() {
        /*
            r6 = this;
            com.huawei.hms.analytics.framework.c.a.a r0 = new com.huawei.hms.analytics.framework.c.a.a
            r0.<init>()
            r1 = 1
            r0.e = r1
            java.lang.String r2 = r6.a
            r0.a = r2
            java.lang.String r2 = r6.b
            r0.b = r2
            com.huawei.hms.analytics.framework.b.b r2 = com.huawei.hms.analytics.framework.b.b.a()
            java.lang.String r3 = r6.a
            com.huawei.hms.analytics.framework.config.ICollectorConfig r2 = r2.a(r3)
            java.lang.String r2 = r2.getRegion()
            r0.c = r2
            com.huawei.hms.analytics.framework.config.ICallback r2 = r6.d
            boolean r2 = r2.isStopEvent()
            r3 = 0
            if (r2 != 0) goto L74
            java.lang.String r2 = r6.a
            java.lang.String r4 = r6.b
            java.util.List r2 = com.huawei.hms.analytics.framework.c.c.a(r2, r4, r1)
            if (r2 == 0) goto L74
            java.util.List<com.huawei.hms.analytics.core.storage.Event> r4 = r6.c
            int r4 = r4.size()
            if (r4 <= r1) goto L41
        L3b:
            java.util.List<com.huawei.hms.analytics.core.storage.Event> r1 = r6.c
            r1.addAll(r2)
            goto L74
        L41:
            java.util.List<com.huawei.hms.analytics.core.storage.Event> r1 = r6.c
            java.lang.Object r1 = r1.get(r3)
            com.huawei.hms.analytics.core.storage.Event r1 = (com.huawei.hms.analytics.core.storage.Event) r1
            java.lang.String r1 = r1.getPushEvtFlag()
            boolean r4 = android.text.TextUtils.isEmpty(r1)
            if (r4 == 0) goto L54
            goto L3b
        L54:
            java.util.Iterator r2 = r2.iterator()
        L58:
            boolean r4 = r2.hasNext()
            if (r4 == 0) goto L74
            java.lang.Object r4 = r2.next()
            com.huawei.hms.analytics.core.storage.Event r4 = (com.huawei.hms.analytics.core.storage.Event) r4
            java.lang.String r5 = r4.getPushEvtFlag()
            boolean r5 = r1.equals(r5)
            if (r5 != 0) goto L58
            java.util.List<com.huawei.hms.analytics.core.storage.Event> r5 = r6.c
            r5.add(r4)
            goto L58
        L74:
            com.huawei.hms.analytics.framework.c.j r1 = new com.huawei.hms.analytics.framework.c.j
            java.util.List<com.huawei.hms.analytics.core.storage.Event> r2 = r6.c
            com.huawei.hms.analytics.framework.config.ICallback r4 = r6.d
            r1.<init>(r2, r0, r4)
            boolean r0 = r1.b()
            if (r0 == 0) goto L96
            java.lang.String r0 = ""
            r1.a(r3, r0)
            r1.c()     // Catch: java.lang.IllegalArgumentException -> L8c
            return
        L8c:
            r0 = move-exception
            java.lang.String r0 = r0.getMessage()
            java.lang.String r2 = "ReportAssignment"
            com.huawei.hms.analytics.core.log.HiLog.e(r2, r0)
        L96:
            long r2 = java.lang.System.currentTimeMillis()
            r1.a(r2)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.huawei.hms.analytics.framework.c.k.run():void");
    }
}
