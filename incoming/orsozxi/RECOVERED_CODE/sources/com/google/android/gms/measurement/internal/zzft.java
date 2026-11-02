package com.google.android.gms.measurement.internal;

/* compiled from: com.google.android.gms:play-services-measurement-impl@@19.0.0 */
/* loaded from: classes.dex */
final class zzft implements Runnable {
    final /* synthetic */ zzgw zza;
    final /* synthetic */ zzfu zzb;

    zzft(zzfu zzfuVar, zzgw zzgwVar) {
        this.zzb = zzfuVar;
        this.zza = zzgwVar;
    }

    @Override // java.lang.Runnable
    public final void run() throws ClassNotFoundException {
        zzfu.zzO(this.zzb, this.zza);
        this.zzb.zza(this.zza.zzg);
    }
}
