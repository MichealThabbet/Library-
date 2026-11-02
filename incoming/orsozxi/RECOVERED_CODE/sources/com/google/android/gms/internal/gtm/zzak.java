package com.google.android.gms.internal.gtm;

/* loaded from: classes.dex */
final class zzak implements Runnable {
    private final /* synthetic */ zzae zzvw;
    private final /* synthetic */ zzbw zzwb;

    zzak(zzae zzaeVar, zzbw zzbwVar) {
        this.zzvw = zzaeVar;
        this.zzwb = zzbwVar;
    }

    @Override // java.lang.Runnable
    public final void run() {
        this.zzvw.zzvu.zzb(this.zzwb);
    }
}
