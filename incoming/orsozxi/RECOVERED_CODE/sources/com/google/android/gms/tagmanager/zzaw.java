package com.google.android.gms.tagmanager;

/* loaded from: classes.dex */
final class zzaw implements Runnable {
    private final /* synthetic */ zzat zzagd;
    private final /* synthetic */ String zzagf;

    zzaw(zzat zzatVar, String str) {
        this.zzagd = zzatVar;
        this.zzagf = str;
    }

    @Override // java.lang.Runnable
    public final void run() {
        this.zzagd.zzat(this.zzagf);
    }
}
