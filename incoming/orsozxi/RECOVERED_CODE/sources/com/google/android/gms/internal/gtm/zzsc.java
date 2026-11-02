package com.google.android.gms.internal.gtm;

import java.io.IOException;

/* loaded from: classes.dex */
public final class zzsc<K, V> {
    static <K, V> void zza(zzqj zzqjVar, zzsd<K, V> zzsdVar, K k, V v) throws IOException {
        zzqt.zza(zzqjVar, zzsdVar.zzbcp, 1, k);
        zzqt.zza(zzqjVar, zzsdVar.zzbcr, 2, v);
    }

    static <K, V> int zza(zzsd<K, V> zzsdVar, K k, V v) {
        return zzqt.zza(zzsdVar.zzbcp, 1, k) + zzqt.zza(zzsdVar.zzbcr, 2, v);
    }
}
