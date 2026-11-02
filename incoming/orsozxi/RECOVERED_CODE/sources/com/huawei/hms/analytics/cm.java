package com.huawei.hms.analytics;

import com.huawei.hms.analytics.core.log.HiLog;
import java.util.concurrent.TimeUnit;

/* loaded from: classes2.dex */
public final class cm implements Runnable {
    private String klm;
    private String lmn;

    public cm(String str, String str2) {
        this.klm = str2;
        this.lmn = str;
    }

    @Override // java.lang.Runnable
    public final void run() throws InterruptedException {
        Cdo cdo;
        HiLog.i("SyncTokenBaseMission", "SyncToken task is running");
        if (dn.klm()) {
            try {
                dn.lmn().await(3L, TimeUnit.SECONDS);
            } catch (InterruptedException unused) {
                HiLog.w("SyncTokenBaseMission", "Interrupted Exception");
            }
            cdo = new Cdo(new bz(this.lmn, this.klm));
        } else {
            dn.ikl();
            cdo = new Cdo(dn.lmn(), new bz(this.lmn, this.klm));
        }
        cdo.lmn();
    }
}
