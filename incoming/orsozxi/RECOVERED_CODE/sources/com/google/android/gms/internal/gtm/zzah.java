package com.google.android.gms.internal.gtm;

/* loaded from: classes.dex */
final class zzah implements Runnable {
    private final /* synthetic */ zzae zzvw;
    private final /* synthetic */ String zzvy;
    private final /* synthetic */ Runnable zzvz;

    zzah(zzae zzaeVar, String str, Runnable runnable) {
        this.zzvw = zzaeVar;
        this.zzvy = str;
        this.zzvz = runnable;
    }

    @Override // java.lang.Runnable
    public final void run() {
        this.zzvw.zzvu.zzy(this.zzvy);
        Runnable runnable = this.zzvz;
        if (runnable != null) {
            runnable.run();
        }
    }
}
