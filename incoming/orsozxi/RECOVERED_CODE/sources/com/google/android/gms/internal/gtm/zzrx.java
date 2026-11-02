package com.google.android.gms.internal.gtm;

import java.util.List;

/* loaded from: classes.dex */
final class zzrx extends zzru {
    private zzrx() {
        super();
    }

    @Override // com.google.android.gms.internal.gtm.zzru
    final <L> List<L> zza(Object obj, long j) {
        zzrj zzrjVarZzd = zzd(obj, j);
        if (zzrjVarZzd.zzmy()) {
            return zzrjVarZzd;
        }
        int size = zzrjVarZzd.size();
        zzrj zzrjVarZzaj = zzrjVarZzd.zzaj(size == 0 ? 10 : size << 1);
        zztx.zza(obj, j, zzrjVarZzaj);
        return zzrjVarZzaj;
    }

    @Override // com.google.android.gms.internal.gtm.zzru
    final void zzb(Object obj, long j) {
        zzd(obj, j).zzmi();
    }

    @Override // com.google.android.gms.internal.gtm.zzru
    final <E> void zza(Object obj, Object obj2, long j) {
        zzrj zzrjVarZzd = zzd(obj, j);
        zzrj zzrjVarZzd2 = zzd(obj2, j);
        int size = zzrjVarZzd.size();
        int size2 = zzrjVarZzd2.size();
        if (size > 0 && size2 > 0) {
            if (!zzrjVarZzd.zzmy()) {
                zzrjVarZzd = zzrjVarZzd.zzaj(size2 + size);
            }
            zzrjVarZzd.addAll(zzrjVarZzd2);
        }
        if (size > 0) {
            zzrjVarZzd2 = zzrjVarZzd;
        }
        zztx.zza(obj, j, zzrjVarZzd2);
    }

    private static <E> zzrj<E> zzd(Object obj, long j) {
        return (zzrj) zztx.zzp(obj, j);
    }
}
