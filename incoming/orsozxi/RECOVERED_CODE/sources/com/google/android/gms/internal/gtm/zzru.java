package com.google.android.gms.internal.gtm;

import java.util.List;

/* loaded from: classes.dex */
abstract class zzru {
    private static final zzru zzbch;
    private static final zzru zzbci;

    private zzru() {
    }

    abstract <L> List<L> zza(Object obj, long j);

    abstract <L> void zza(Object obj, Object obj2, long j);

    abstract void zzb(Object obj, long j);

    static zzru zzqc() {
        return zzbch;
    }

    static zzru zzqd() {
        return zzbci;
    }

    static {
        zzrv zzrvVar = null;
        zzbch = new zzrw();
        zzbci = new zzrx();
    }
}
