package com.huawei.hms.analytics;

/* loaded from: classes2.dex */
public final class cs extends cw {
    public cs(bh bhVar) {
        super(bhVar, "$CustomReferrer", "is_cs_refr_run");
        this.lmn = "CusRefrHandler";
    }

    @Override // com.huawei.hms.analytics.cw
    public final boolean klm() {
        return false;
    }

    @Override // com.huawei.hms.analytics.cw
    public final long lmn() {
        return System.currentTimeMillis();
    }
}
