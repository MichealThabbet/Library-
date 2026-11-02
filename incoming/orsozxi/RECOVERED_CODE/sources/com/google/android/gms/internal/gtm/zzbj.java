package com.google.android.gms.internal.gtm;

import java.util.concurrent.Callable;

/* loaded from: classes.dex */
final class zzbj implements Callable<String> {
    private final /* synthetic */ zzbh zzyi;

    zzbj(zzbh zzbhVar) {
        this.zzyi = zzbhVar;
    }

    @Override // java.util.concurrent.Callable
    public final /* synthetic */ String call() throws Exception {
        return this.zzyi.zzek();
    }
}
