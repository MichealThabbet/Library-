package com.huawei.hms.analytics;

import android.os.Bundle;

/* loaded from: classes2.dex */
public final class cu extends cv {
    private String ikl;
    public String lmn;

    public cu() {
    }

    public cu(String str) {
        this.ikl = str;
    }

    public cu(String str, String str2) {
        super(str2);
        this.ikl = str;
    }

    @Override // com.huawei.hms.analytics.cv
    public final Bundle lmn() {
        Bundle bundleKlm = klm();
        String str = this.lmn;
        if (str == null) {
            str = "";
        }
        bundleKlm.putString("$TrackId", str);
        String str2 = this.ikl;
        bundleKlm.putString("$ChannelType", str2 != null ? str2 : "");
        return bundleKlm;
    }
}
