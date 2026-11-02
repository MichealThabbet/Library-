package com.google.android.gms.measurement.internal;

import android.os.Bundle;
import com.google.android.gms.common.internal.Preconditions;

/* compiled from: com.google.android.gms:play-services-measurement@@19.0.0 */
/* loaded from: classes.dex */
final class zzkk implements Runnable {
    final /* synthetic */ String zza;
    final /* synthetic */ String zzb = "_err";
    final /* synthetic */ Bundle zzc;
    final /* synthetic */ zzkl zzd;

    zzkk(zzkl zzklVar, String str, String str2, Bundle bundle) {
        this.zzd = zzklVar;
        this.zza = str;
        this.zzc = bundle;
    }

    @Override // java.lang.Runnable
    public final void run() {
        this.zzd.zza.zzv((zzas) Preconditions.checkNotNull(this.zzd.zza.zzq().zzV(this.zza, this.zzb, this.zzc, "auto", this.zzd.zza.zzay().currentTimeMillis(), false, false)), this.zza);
    }
}
