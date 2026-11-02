package com.google.android.gms.measurement.internal;

import java.util.List;

/* compiled from: com.google.android.gms:play-services-measurement@@19.0.0 */
/* loaded from: classes.dex */
final class zzfj implements com.google.android.gms.internal.measurement.zzr {
    final /* synthetic */ zzfl zza;

    zzfj(zzfl zzflVar) {
        this.zza = zzflVar;
    }

    @Override // com.google.android.gms.internal.measurement.zzr
    public final void zza(int i, String str, List<String> list, boolean z, boolean z2) {
        int i2 = i - 1;
        zzek zzekVarZzi = i2 != 0 ? i2 != 1 ? i2 != 3 ? i2 != 4 ? this.zza.zzs.zzau().zzi() : z ? this.zza.zzs.zzau().zzf() : !z2 ? this.zza.zzs.zzau().zzh() : this.zza.zzs.zzau().zze() : this.zza.zzs.zzau().zzk() : z ? this.zza.zzs.zzau().zzc() : !z2 ? this.zza.zzs.zzau().zzd() : this.zza.zzs.zzau().zzb() : this.zza.zzs.zzau().zzj();
        int size = list.size();
        if (size == 1) {
            zzekVarZzi.zzb(str, list.get(0));
            return;
        }
        if (size == 2) {
            zzekVarZzi.zzc(str, list.get(0), list.get(1));
        } else if (size != 3) {
            zzekVarZzi.zza(str);
        } else {
            zzekVarZzi.zzd(str, list.get(0), list.get(1), list.get(2));
        }
    }
}
