package it.feio.android.omninotes.commons.utils;

/* loaded from: classes2.dex */
public class EqualityChecker {
    /* JADX WARN: Removed duplicated region for block: B:8:0x000d  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public static synchronized boolean check(java.lang.Object r3, java.lang.Object r4) {
        /*
            java.lang.Class<it.feio.android.omninotes.commons.utils.EqualityChecker> r0 = it.feio.android.omninotes.commons.utils.EqualityChecker.class
            monitor-enter(r0)
            r1 = 0
            r2 = 1
            if (r3 == 0) goto L12
            boolean r3 = r3.equals(r4)     // Catch: java.lang.Throwable -> L10
            if (r3 == 0) goto Le
        Ld:
            r1 = 1
        Le:
            r2 = r1
            goto L1d
        L10:
            r3 = move-exception
            goto L1b
        L12:
            if (r4 == 0) goto L1d
            boolean r3 = r4.equals(r3)     // Catch: java.lang.Throwable -> L10
            if (r3 == 0) goto Le
            goto Ld
        L1b:
            monitor-exit(r0)
            throw r3
        L1d:
            monitor-exit(r0)
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: it.feio.android.omninotes.commons.utils.EqualityChecker.check(java.lang.Object, java.lang.Object):boolean");
    }

    /* JADX WARN: Removed duplicated region for block: B:17:0x0020  */
    /* JADX WARN: Removed duplicated region for block: B:18:0x0022  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public static synchronized boolean check(java.lang.Object[] r7, java.lang.Object[] r8) {
        /*
            java.lang.Class<it.feio.android.omninotes.commons.utils.EqualityChecker> r0 = it.feio.android.omninotes.commons.utils.EqualityChecker.class
            monitor-enter(r0)
            int r1 = r7.length     // Catch: java.lang.Throwable -> L37
            int r2 = r8.length     // Catch: java.lang.Throwable -> L37
            r3 = 1
            r4 = 0
            if (r1 != r2) goto Lb
            r1 = 1
            goto Lc
        Lb:
            r1 = 0
        Lc:
            if (r1 == 0) goto L35
            r2 = 0
        Lf:
            int r5 = r7.length     // Catch: java.lang.Throwable -> L37
            if (r2 >= r5) goto L35
            r5 = r7[r2]     // Catch: java.lang.Throwable -> L37
            r6 = r8[r2]     // Catch: java.lang.Throwable -> L37
            if (r5 == 0) goto L24
            if (r1 == 0) goto L22
            boolean r1 = r5.equals(r6)     // Catch: java.lang.Throwable -> L37
            if (r1 == 0) goto L22
        L20:
            r1 = 1
            goto L2f
        L22:
            r1 = 0
            goto L2f
        L24:
            if (r6 == 0) goto L2f
            if (r1 == 0) goto L22
            boolean r1 = r6.equals(r5)     // Catch: java.lang.Throwable -> L37
            if (r1 == 0) goto L22
            goto L20
        L2f:
            if (r1 != 0) goto L32
            goto L35
        L32:
            int r2 = r2 + 1
            goto Lf
        L35:
            monitor-exit(r0)
            return r1
        L37:
            r7 = move-exception
            monitor-exit(r0)
            throw r7
        */
        throw new UnsupportedOperationException("Method not decompiled: it.feio.android.omninotes.commons.utils.EqualityChecker.check(java.lang.Object[], java.lang.Object[]):boolean");
    }
}
