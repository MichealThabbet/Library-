package com.google.android.gms.internal.gtm;

/* loaded from: classes.dex */
final class zzai implements Runnable {
    private final /* synthetic */ zzae zzvw;
    private final /* synthetic */ zzcd zzwa;

    zzai(zzae zzaeVar, zzcd zzcdVar) {
        this.zzvw = zzaeVar;
        this.zzwa = zzcdVar;
    }

    @Override // java.lang.Runnable
    public final void run() {
        this.zzvw.zzvu.zza(this.zzwa);
    }
}
