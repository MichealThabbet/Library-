package com.huawei.hms.analytics;

import android.os.Bundle;

/* loaded from: classes2.dex */
public abstract class cv {
    public String klm;

    public cv() {
    }

    public cv(String str) {
        this.klm = str;
    }

    protected final Bundle klm() {
        Bundle bundle = new Bundle();
        at atVar = ar.lmn().lmn;
        bundle.putString("$StartType", atVar.cde == null ? "" : atVar.cde);
        String str = this.klm;
        bundle.putString("$InstallChannel", str != null ? str : "");
        return bundle;
    }

    public abstract Bundle lmn();
}
