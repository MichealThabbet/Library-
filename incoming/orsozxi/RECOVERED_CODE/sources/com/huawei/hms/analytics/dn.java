package com.huawei.hms.analytics;

import java.util.concurrent.CountDownLatch;

/* loaded from: classes2.dex */
public final class dn {
    private static final Object lmn = new Object();
    private static final CountDownLatch klm = new CountDownLatch(1);
    private static boolean ikl = false;

    public static void ikl() {
        synchronized (lmn) {
            ikl = true;
        }
    }

    public static boolean klm() {
        boolean z;
        synchronized (lmn) {
            z = ikl;
        }
        return z;
    }

    public static CountDownLatch lmn() {
        return klm;
    }
}
