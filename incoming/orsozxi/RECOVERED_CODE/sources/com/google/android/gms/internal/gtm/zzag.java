package com.google.android.gms.internal.gtm;

/* loaded from: classes.dex */
final class zzag implements Runnable {
    private final /* synthetic */ zzae zzvw;
    private final /* synthetic */ boolean zzvx;

    zzag(zzae zzaeVar, boolean z) {
        this.zzvw = zzaeVar;
        this.zzvx = z;
    }

    @Override // java.lang.Runnable
    public final void run() {
        this.zzvw.zzvu.zzec();
    }
}
