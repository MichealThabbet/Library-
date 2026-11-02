package com.huawei.hms.analytics;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/* loaded from: classes2.dex */
public final class ar {
    private static final ar klm = new ar();
    private volatile aq fgh;
    private volatile aq ghi;
    private final Object ikl = new Object();
    private final Object ijk = new Object();
    public at lmn = new at();
    private Map<String, au> hij = new ConcurrentHashMap();

    public static ar lmn() {
        return klm;
    }

    public final aq ikl() {
        if (this.fgh == null) {
            synchronized (this.ikl) {
                if (this.fgh == null) {
                    this.fgh = new as(this.lmn.ghi);
                }
            }
        }
        return this.fgh;
    }

    public final aq klm() {
        if (this.ghi == null) {
            synchronized (this.ijk) {
                if (this.ghi == null) {
                    this.ghi = new bb(this.lmn.ghi);
                }
            }
        }
        return this.ghi;
    }

    public final au lmn(String str) {
        return this.hij.get(str);
    }

    public final void lmn(String str, au auVar) {
        this.hij.put(str, auVar);
    }
}
