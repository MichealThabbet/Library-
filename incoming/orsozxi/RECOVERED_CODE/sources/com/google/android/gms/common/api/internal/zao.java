package com.google.android.gms.common.api.internal;

import android.app.Dialog;

/* loaded from: classes.dex */
final class zao extends zabr {
    private final /* synthetic */ Dialog zadl;
    private final /* synthetic */ zan zadm;

    zao(zan zanVar, Dialog dialog) {
        this.zadm = zanVar;
        this.zadl = dialog;
    }

    @Override // com.google.android.gms.common.api.internal.zabr
    public final void zas() {
        this.zadm.zadk.zaq();
        if (this.zadl.isShowing()) {
            this.zadl.dismiss();
        }
    }
}
