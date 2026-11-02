package com.huawei.hms.analytics;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import com.huawei.hms.analytics.i;
import com.huawei.hms.analytics.x;
import java.security.MessageDigest;

/* loaded from: classes2.dex */
public final class ag extends ad {
    private String ijk;

    protected ag(Context context, i.lmn lmnVar) {
        super(context, lmnVar, new v(context, lmnVar));
        Intent intent = new Intent("action.com.heytap.openid.OPEN_ID_SERVICE");
        intent.setComponent(new ComponentName("com.heytap.openid", "com.heytap.openid.IdentifyService"));
        this.ikl = new x(context, intent, this);
    }

    @Override // com.huawei.hms.analytics.ad
    public final boolean klm() {
        return klm("com.heytap.openid");
    }

    @Override // com.huawei.hms.analytics.ad
    public final void lmn() {
        try {
            this.ikl.lmn();
        } catch (Exception unused) {
            lmn("OPPO getOaid,bindService error, begin get gaid");
        }
    }

    @Override // com.huawei.hms.analytics.ad, com.huawei.hms.analytics.w
    public final void lmn(x.lmn lmnVar) {
        try {
            ae aeVar = new ae("com.heytap.openid.IOpenID", lmnVar.lmn());
            String packageName = this.klm.getPackageName();
            if (this.ijk == null) {
                byte[] bArrDigest = MessageDigest.getInstance("SHA1").digest(this.klm.getPackageManager().getPackageInfo(packageName, 64).signatures[0].toByteArray());
                StringBuilder sb = new StringBuilder();
                for (byte b : bArrDigest) {
                    sb.append(Integer.toHexString((b & 255) | 256).substring(1, 3));
                }
                this.ijk = sb.toString();
            }
            this.lmn.lmn(aeVar.lmn(packageName, this.ijk, "OUID"), "", "oaid");
        } finally {
            ikl();
        }
    }
}
