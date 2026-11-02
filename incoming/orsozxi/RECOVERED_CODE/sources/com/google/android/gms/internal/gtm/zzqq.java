package com.google.android.gms.internal.gtm;

import com.google.android.gms.internal.gtm.zzqv;
import java.io.IOException;
import java.util.Map;

/* loaded from: classes.dex */
abstract class zzqq<T extends zzqv<T>> {
    zzqq() {
    }

    abstract Object zza(zzqp zzqpVar, zzsk zzskVar, int i);

    abstract <UT, UB> UB zza(zzsy zzsyVar, Object obj, zzqp zzqpVar, zzqt<T> zzqtVar, UB ub, zztr<UT, UB> zztrVar) throws IOException;

    abstract void zza(zzps zzpsVar, Object obj, zzqp zzqpVar, zzqt<T> zzqtVar) throws IOException;

    abstract void zza(zzsy zzsyVar, Object obj, zzqp zzqpVar, zzqt<T> zzqtVar) throws IOException;

    abstract void zza(zzum zzumVar, Map.Entry<?, ?> entry) throws IOException;

    abstract int zzb(Map.Entry<?, ?> entry);

    abstract boolean zze(zzsk zzskVar);

    abstract zzqt<T> zzr(Object obj);

    abstract zzqt<T> zzs(Object obj);

    abstract void zzt(Object obj);
}
