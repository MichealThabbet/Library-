package com.google.android.gms.internal.measurement;

/* compiled from: com.google.android.gms:play-services-measurement-base@@19.0.0 */
/* loaded from: classes.dex */
final class zzkv extends zzkw {
    private zzkv() {
        super(null);
    }

    /* synthetic */ zzkv(zzkt zzktVar) {
        super(null);
    }

    @Override // com.google.android.gms.internal.measurement.zzkw
    final void zza(Object obj, long j) {
        ((zzkk) zzmr.zzn(obj, j)).zzb();
    }

    @Override // com.google.android.gms.internal.measurement.zzkw
    final <E> void zzb(Object obj, Object obj2, long j) {
        zzkk zzkkVarZze = (zzkk) zzmr.zzn(obj, j);
        zzkk zzkkVar = (zzkk) zzmr.zzn(obj2, j);
        int size = zzkkVarZze.size();
        int size2 = zzkkVar.size();
        if (size > 0 && size2 > 0) {
            if (!zzkkVarZze.zza()) {
                zzkkVarZze = zzkkVarZze.zze(size2 + size);
            }
            zzkkVarZze.addAll(zzkkVar);
        }
        if (size > 0) {
            zzkkVar = zzkkVarZze;
        }
        zzmr.zzo(obj, j, zzkkVar);
    }
}
