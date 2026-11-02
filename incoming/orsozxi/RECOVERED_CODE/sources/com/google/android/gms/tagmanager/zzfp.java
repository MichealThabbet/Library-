package com.google.android.gms.tagmanager;

/* loaded from: classes.dex */
final class zzfp implements Runnable {
    private final /* synthetic */ zzfn zzakz;

    zzfp(zzfn zzfnVar) {
        this.zzakz = zzfnVar;
    }

    @Override // java.lang.Runnable
    public final void run() {
        this.zzakz.zzakp.dispatch();
    }
}
