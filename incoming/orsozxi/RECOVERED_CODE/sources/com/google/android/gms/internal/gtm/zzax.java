package com.google.android.gms.internal.gtm;

import android.content.ComponentName;

/* loaded from: classes.dex */
final class zzax implements Runnable {
    private final /* synthetic */ ComponentName val$name;
    private final /* synthetic */ zzav zzxi;

    zzax(zzav zzavVar, ComponentName componentName) {
        this.zzxi = zzavVar;
        this.val$name = componentName;
    }

    @Override // java.lang.Runnable
    public final void run() {
        this.zzxi.zzxe.onServiceDisconnected(this.val$name);
    }
}
