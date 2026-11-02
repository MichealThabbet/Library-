package com.google.android.gms.internal.gtm;

import java.io.IOException;

/* loaded from: classes.dex */
abstract class zztr<T, B> {
    zztr() {
    }

    abstract void zza(B b, int i, long j);

    abstract void zza(B b, int i, zzps zzpsVar);

    abstract void zza(B b, int i, T t);

    abstract void zza(T t, zzum zzumVar) throws IOException;

    abstract boolean zza(zzsy zzsyVar);

    abstract T zzaa(B b);

    abstract int zzad(T t);

    abstract T zzag(Object obj);

    abstract B zzah(Object obj);

    abstract int zzai(T t);

    abstract void zzb(B b, int i, long j);

    abstract void zzc(B b, int i, int i2);

    abstract void zzc(T t, zzum zzumVar) throws IOException;

    abstract void zzf(Object obj, T t);

    abstract void zzg(Object obj, B b);

    abstract T zzh(T t, T t2);

    abstract B zzri();

    abstract void zzt(Object obj);

    final boolean zza(B b, zzsy zzsyVar) throws IOException {
        int tag = zzsyVar.getTag();
        int i = tag >>> 3;
        int i2 = tag & 7;
        if (i2 == 0) {
            zza((zztr<T, B>) b, i, zzsyVar.zznk());
            return true;
        }
        if (i2 == 1) {
            zzb(b, i, zzsyVar.zznm());
            return true;
        }
        if (i2 == 2) {
            zza((zztr<T, B>) b, i, zzsyVar.zznq());
            return true;
        }
        if (i2 != 3) {
            if (i2 == 4) {
                return false;
            }
            if (i2 != 5) {
                throw zzrk.zzpt();
            }
            zzc(b, i, zzsyVar.zznn());
            return true;
        }
        B bZzri = zzri();
        int i3 = 4 | (i << 3);
        while (zzsyVar.zzog() != Integer.MAX_VALUE && zza((zztr<T, B>) bZzri, zzsyVar)) {
        }
        if (i3 != zzsyVar.getTag()) {
            throw zzrk.zzps();
        }
        zza((zztr<T, B>) b, i, (int) zzaa(bZzri));
        return true;
    }
}
