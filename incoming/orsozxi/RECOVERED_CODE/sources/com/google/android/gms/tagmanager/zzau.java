package com.google.android.gms.tagmanager;

import java.util.List;

/* loaded from: classes.dex */
final class zzau implements Runnable {
    private final /* synthetic */ List zzagb;
    private final /* synthetic */ long zzagc;
    private final /* synthetic */ zzat zzagd;

    zzau(zzat zzatVar, List list, long j) {
        this.zzagd = zzatVar;
        this.zzagb = list;
        this.zzagc = j;
    }

    @Override // java.lang.Runnable
    public final void run() {
        this.zzagd.zzb(this.zzagb, this.zzagc);
    }
}
