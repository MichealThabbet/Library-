package com.google.android.gms.common.api.internal;

/* loaded from: classes.dex */
final class zabn implements Runnable {
    private final /* synthetic */ zabm zaja;

    zabn(zabm zabmVar) {
        this.zaja = zabmVar;
    }

    @Override // java.lang.Runnable
    public final void run() {
        this.zaja.zaiy.zaio.disconnect();
    }
}
