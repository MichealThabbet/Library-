package com.huawei.hms.analytics;

import android.content.Context;
import android.content.Intent;
import com.huawei.hms.analytics.core.log.HiLog;
import com.huawei.hms.analytics.i;
import com.huawei.hms.analytics.x;

/* loaded from: classes2.dex */
public final class v extends ad {
    private final i.lmn ijk;

    public v(Context context, i.lmn lmnVar) {
        super(context, lmnVar, null);
        Intent intent = new Intent("com.google.android.gms.ads.identifier.service.START");
        intent.setPackage("com.google.android.gms");
        this.ikl = new x(context, intent, this);
        this.ijk = lmnVar;
    }

    @Override // com.huawei.hms.analytics.ad
    public final boolean klm() {
        return klm("com.android.vending");
    }

    @Override // com.huawei.hms.analytics.ad
    public final void lmn() {
        try {
            this.ikl.lmn();
        } catch (Exception unused) {
            lmn("GMS getOaid,bindService error, begin get gaid");
        }
    }

    @Override // com.huawei.hms.analytics.ad, com.huawei.hms.analytics.w
    public final void lmn(x.lmn lmnVar) {
        try {
            ae aeVar = new ae("com.google.android.gms.ads.identifier.internal.IAdvertisingIdService", lmnVar.lmn());
            String strLmn = aeVar.lmn(new String[0]);
            boolean zLmn = aeVar.lmn();
            ikl();
            this.ijk.lmn(strLmn, String.valueOf(!zLmn), "gaid");
        } catch (Throwable th) {
            ikl();
            throw th;
        }
    }

    @Override // com.huawei.hms.analytics.ad, com.huawei.hms.analytics.w
    public final void lmn(String str) {
        HiLog.w("gaidHepler", "bind service error");
        this.ijk.lmn("", "", "");
    }
}
