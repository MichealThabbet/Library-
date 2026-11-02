package com.google.android.gms.measurement.internal;

import android.content.ComponentName;

/* compiled from: com.google.android.gms:play-services-measurement-impl@@19.0.0 */
/* loaded from: classes.dex */
final class zzjf implements Runnable {
    final /* synthetic */ ComponentName zza;
    final /* synthetic */ zzjj zzb;

    zzjf(zzjj zzjjVar, ComponentName componentName) {
        this.zzb = zzjjVar;
        this.zza = componentName;
    }

    @Override // java.lang.Runnable
    public final void run() {
        zzjk.zzJ(this.zzb.zza, this.zza);
    }
}
