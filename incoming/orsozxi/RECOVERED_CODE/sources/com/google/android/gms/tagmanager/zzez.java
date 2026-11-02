package com.google.android.gms.tagmanager;

import com.google.android.gms.internal.gtm.zzop;

/* loaded from: classes.dex */
final class zzez implements Runnable {
    private final /* synthetic */ zzex zzajn;
    private final /* synthetic */ zzop zzajo;

    zzez(zzex zzexVar, zzop zzopVar) {
        this.zzajn = zzexVar;
        this.zzajo = zzopVar;
    }

    @Override // java.lang.Runnable
    public final void run() {
        this.zzajn.zzb(this.zzajo);
    }
}
