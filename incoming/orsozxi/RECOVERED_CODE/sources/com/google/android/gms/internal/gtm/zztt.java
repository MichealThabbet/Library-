package com.google.android.gms.internal.gtm;

import java.io.IOException;

/* loaded from: classes.dex */
final class zztt extends zztr<zzts, zzts> {
    zztt() {
    }

    @Override // com.google.android.gms.internal.gtm.zztr
    final boolean zza(zzsy zzsyVar) {
        return false;
    }

    private static void zza(Object obj, zzts zztsVar) {
        ((zzrc) obj).zzbak = zztsVar;
    }

    @Override // com.google.android.gms.internal.gtm.zztr
    final void zzt(Object obj) {
        ((zzrc) obj).zzbak.zzmi();
    }

    @Override // com.google.android.gms.internal.gtm.zztr
    final /* synthetic */ int zzad(zzts zztsVar) {
        return zztsVar.zzpe();
    }

    @Override // com.google.android.gms.internal.gtm.zztr
    final /* synthetic */ int zzai(zzts zztsVar) {
        return zztsVar.zzrl();
    }

    @Override // com.google.android.gms.internal.gtm.zztr
    final /* synthetic */ zzts zzh(zzts zztsVar, zzts zztsVar2) {
        zzts zztsVar3 = zztsVar;
        zzts zztsVar4 = zztsVar2;
        return zztsVar4.equals(zzts.zzrj()) ? zztsVar3 : zzts.zza(zztsVar3, zztsVar4);
    }

    @Override // com.google.android.gms.internal.gtm.zztr
    final /* synthetic */ void zzc(zzts zztsVar, zzum zzumVar) throws IOException {
        zztsVar.zza(zzumVar);
    }

    @Override // com.google.android.gms.internal.gtm.zztr
    final /* synthetic */ void zza(zzts zztsVar, zzum zzumVar) throws IOException {
        zztsVar.zzb(zzumVar);
    }

    @Override // com.google.android.gms.internal.gtm.zztr
    final /* synthetic */ void zzg(Object obj, zzts zztsVar) {
        zza(obj, zztsVar);
    }

    @Override // com.google.android.gms.internal.gtm.zztr
    final /* synthetic */ zzts zzah(Object obj) {
        zzts zztsVar = ((zzrc) obj).zzbak;
        if (zztsVar != zzts.zzrj()) {
            return zztsVar;
        }
        zzts zztsVarZzrk = zzts.zzrk();
        zza(obj, zztsVarZzrk);
        return zztsVarZzrk;
    }

    @Override // com.google.android.gms.internal.gtm.zztr
    final /* synthetic */ zzts zzag(Object obj) {
        return ((zzrc) obj).zzbak;
    }

    @Override // com.google.android.gms.internal.gtm.zztr
    final /* synthetic */ void zzf(Object obj, zzts zztsVar) {
        zza(obj, zztsVar);
    }

    @Override // com.google.android.gms.internal.gtm.zztr
    final /* synthetic */ zzts zzaa(zzts zztsVar) {
        zzts zztsVar2 = zztsVar;
        zztsVar2.zzmi();
        return zztsVar2;
    }

    @Override // com.google.android.gms.internal.gtm.zztr
    final /* synthetic */ zzts zzri() {
        return zzts.zzrk();
    }

    @Override // com.google.android.gms.internal.gtm.zztr
    final /* synthetic */ void zza(zzts zztsVar, int i, zzts zztsVar2) {
        zztsVar.zzb((i << 3) | 3, zztsVar2);
    }

    @Override // com.google.android.gms.internal.gtm.zztr
    final /* synthetic */ void zza(zzts zztsVar, int i, zzps zzpsVar) {
        zztsVar.zzb((i << 3) | 2, zzpsVar);
    }

    @Override // com.google.android.gms.internal.gtm.zztr
    final /* synthetic */ void zzb(zzts zztsVar, int i, long j) {
        zztsVar.zzb((i << 3) | 1, Long.valueOf(j));
    }

    @Override // com.google.android.gms.internal.gtm.zztr
    final /* synthetic */ void zzc(zzts zztsVar, int i, int i2) {
        zztsVar.zzb((i << 3) | 5, Integer.valueOf(i2));
    }

    @Override // com.google.android.gms.internal.gtm.zztr
    final /* synthetic */ void zza(zzts zztsVar, int i, long j) {
        zztsVar.zzb(i << 3, Long.valueOf(j));
    }
}
