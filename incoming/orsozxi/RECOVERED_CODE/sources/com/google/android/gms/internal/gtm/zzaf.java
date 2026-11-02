package com.google.android.gms.internal.gtm;

/* loaded from: classes.dex */
final class zzaf implements Runnable {
    private final /* synthetic */ int zzvv;
    private final /* synthetic */ zzae zzvw;

    zzaf(zzae zzaeVar, int i) {
        this.zzvw = zzaeVar;
        this.zzvv = i;
    }

    @Override // java.lang.Runnable
    public final void run() {
        this.zzvw.zzvu.zzg(this.zzvv * 1000);
    }
}
