package com.google.android.gms.internal.gtm;

import com.google.android.gms.internal.gtm.zzrc;
import java.io.IOException;
import java.util.Map;

/* loaded from: classes.dex */
final class zzqr extends zzqq<Object> {
    zzqr() {
    }

    @Override // com.google.android.gms.internal.gtm.zzqq
    final boolean zze(zzsk zzskVar) {
        return zzskVar instanceof zzrc.zzc;
    }

    @Override // com.google.android.gms.internal.gtm.zzqq
    final zzqt<Object> zzr(Object obj) {
        return ((zzrc.zzc) obj).zzbaq;
    }

    @Override // com.google.android.gms.internal.gtm.zzqq
    final zzqt<Object> zzs(Object obj) {
        zzrc.zzc zzcVar = (zzrc.zzc) obj;
        if (zzcVar.zzbaq.isImmutable()) {
            zzcVar.zzbaq = (zzqt) zzcVar.zzbaq.clone();
        }
        return zzcVar.zzbaq;
    }

    @Override // com.google.android.gms.internal.gtm.zzqq
    final void zzt(Object obj) {
        zzr(obj).zzmi();
    }

    @Override // com.google.android.gms.internal.gtm.zzqq
    final <UT, UB> UB zza(zzsy zzsyVar, Object obj, zzqp zzqpVar, zzqt<Object> zzqtVar, UB ub, zztr<UT, UB> zztrVar) throws IOException {
        throw new NoSuchMethodError();
    }

    @Override // com.google.android.gms.internal.gtm.zzqq
    final int zzb(Map.Entry<?, ?> entry) {
        entry.getKey();
        throw new NoSuchMethodError();
    }

    @Override // com.google.android.gms.internal.gtm.zzqq
    final void zza(zzum zzumVar, Map.Entry<?, ?> entry) throws IOException {
        entry.getKey();
        throw new NoSuchMethodError();
    }

    @Override // com.google.android.gms.internal.gtm.zzqq
    final Object zza(zzqp zzqpVar, zzsk zzskVar, int i) {
        return zzqpVar.zza(zzskVar, i);
    }

    @Override // com.google.android.gms.internal.gtm.zzqq
    final void zza(zzsy zzsyVar, Object obj, zzqp zzqpVar, zzqt<Object> zzqtVar) throws IOException {
        throw new NoSuchMethodError();
    }

    @Override // com.google.android.gms.internal.gtm.zzqq
    final void zza(zzps zzpsVar, Object obj, zzqp zzqpVar, zzqt<Object> zzqtVar) throws IOException {
        throw new NoSuchMethodError();
    }
}
