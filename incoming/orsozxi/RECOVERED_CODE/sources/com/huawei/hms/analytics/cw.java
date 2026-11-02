package com.huawei.hms.analytics;

import android.content.Context;
import com.huawei.hms.analytics.core.log.HiLog;

/* loaded from: classes2.dex */
public abstract class cw {
    private final String ghi;
    private final String hij;
    private final bh ijk;
    protected Context ikl = ar.lmn().lmn.ghi;
    protected boolean klm;
    protected String lmn;

    public cw(bh bhVar, String str, String str2) {
        this.ijk = bhVar;
        this.hij = str;
        this.ghi = str2;
    }

    public abstract boolean klm();

    public abstract long lmn();

    public final void lmn(cv cvVar) {
        boolean zKlm = dl.klm(this.ikl, "global_v2", this.ghi, false);
        boolean zGhi = dg.ghi(this.ikl);
        this.klm = zGhi;
        if (zGhi || !zKlm) {
            dj djVar = new dj(this.hij, cvVar.lmn());
            long jLmn = lmn();
            if (this.klm) {
                HiLog.i(this.lmn, "debugview referrer event");
                this.ijk.lmn(this.hij, djVar, jLmn);
                return;
            }
            HiLog.i(this.lmn, "normal referrer event");
            if (klm()) {
                ((ghi) this.ijk).klm(this.hij, djVar, jLmn);
            } else {
                this.ijk.lmn(this.hij, djVar, jLmn);
            }
            dl.lmn(this.ikl, "global_v2", this.ghi, true);
        }
    }
}
