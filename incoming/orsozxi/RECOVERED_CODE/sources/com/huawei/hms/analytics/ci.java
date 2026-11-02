package com.huawei.hms.analytics;

import com.huawei.hms.analytics.bx;

/* loaded from: classes2.dex */
public final class ci implements Runnable {
    private final String lmn;

    public static class lmn implements bx.lmn {
        @Override // com.huawei.hms.analytics.bx.lmn
        public final void lmn() {
            bh bhVarLmn = fgh.lmn().lmn("_openness_config_tag");
            bh bhVarLmn2 = fgh.lmn().lmn("AGC_TAG");
            if (bhVarLmn != null) {
                bhVarLmn.lmn();
            }
            if (bhVarLmn2 != null) {
                bhVarLmn2.lmn();
            }
        }
    }

    public ci(String str) {
        this.lmn = str;
    }

    @Override // java.lang.Runnable
    public final void run() {
        bx bxVar = new bx(this.lmn);
        bxVar.lmn = new lmn();
        bxVar.klm();
    }
}
