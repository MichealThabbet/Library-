package com.huawei.hms.analytics;

import com.huawei.hms.analytics.core.log.HiLog;

/* loaded from: classes2.dex */
public final class ct extends cw {
    public ct(bh bhVar) {
        super(bhVar, "$AppFirstOpen", "isFirstRun");
        this.lmn = "FsOpenHandler";
    }

    @Override // com.huawei.hms.analytics.cw
    public final boolean klm() {
        return true;
    }

    @Override // com.huawei.hms.analytics.cw
    public final long lmn() {
        try {
            return Long.parseLong(dl.klm(this.ikl, "global_v2", "firstRunTime", ""));
        } catch (NumberFormatException unused) {
            HiLog.w(this.lmn, "first run time error");
            long jCurrentTimeMillis = System.currentTimeMillis();
            dl.lmn(this.ikl, "global_v2", "firstRunTime", String.valueOf(jCurrentTimeMillis));
            return jCurrentTimeMillis;
        }
    }
}
