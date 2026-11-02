package com.huawei.hms.analytics;

import com.huawei.hms.analytics.bc;
import com.huawei.hms.analytics.database.LogConfig;
import com.huawei.hms.analytics.database.LogEvent;
import com.huawei.hms.analytics.database.LogEventDaoManager;
import java.util.List;

/* loaded from: classes2.dex */
public final class be {
    private static be klm;
    public final bf lmn;

    private be() throws bc.lmn {
        try {
            this.lmn = new LogEventDaoManager(new LogEventDaoManager.lmn(ar.lmn().lmn.ghi, "ha_levt.db", (byte) 0).getWritableDatabase()).newSession();
        } catch (Exception e) {
            throw new bc.lmn(e.getMessage());
        }
    }

    private static synchronized void hij() {
        klm = new be();
    }

    public static be lmn() {
        if (klm == null) {
            hij();
        }
        return klm;
    }

    public final void ijk() {
        this.lmn.lmn.deleteAll();
        this.lmn.lmn();
    }

    public final void ikl() {
        this.lmn.klm.deleteAll();
        this.lmn.lmn.deleteAll();
        this.lmn.lmn();
    }

    public final long klm() {
        return this.lmn.klm.count();
    }

    public final void klm(LogConfig logConfig) {
        this.lmn.lmn.update(logConfig);
    }

    public final long lmn(LogConfig logConfig) {
        return this.lmn.lmn.insert(logConfig);
    }

    public final void lmn(LogEvent logEvent) {
        this.lmn.klm.insert(logEvent);
    }

    public final void lmn(List<Long> list) {
        this.lmn.klm.deleteByKeyInTx(list);
    }
}
