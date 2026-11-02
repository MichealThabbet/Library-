package com.google.android.gms.tagmanager;

/* loaded from: classes.dex */
final class zzav implements Runnable {
    private final /* synthetic */ zzat zzagd;
    private final /* synthetic */ zzaq zzage;

    zzav(zzat zzatVar, zzaq zzaqVar) {
        this.zzagd = zzatVar;
        this.zzage = zzaqVar;
    }

    @Override // java.lang.Runnable
    public final void run() {
        this.zzage.zzc(this.zzagd.zzht());
    }
}
