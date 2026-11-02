package com.google.android.gms.internal.measurement;

/* compiled from: com.google.android.gms:play-services-measurement@@19.0.0 */
/* loaded from: classes.dex */
public final class zzfq extends zzkd<zzfq, zzfp> implements zzlj {
    private static final zzfq zzg;
    private int zza;
    private String zze = "";
    private long zzf;

    static {
        zzfq zzfqVar = new zzfq();
        zzg = zzfqVar;
        zzkd.zzby(zzfq.class, zzfqVar);
    }

    private zzfq() {
    }

    public static zzfp zza() {
        return zzg.zzbt();
    }

    static /* synthetic */ void zzc(zzfq zzfqVar, String str) {
        str.getClass();
        zzfqVar.zza |= 1;
        zzfqVar.zze = str;
    }

    static /* synthetic */ void zzd(zzfq zzfqVar, long j) {
        zzfqVar.zza |= 2;
        zzfqVar.zzf = j;
    }

    @Override // com.google.android.gms.internal.measurement.zzkd
    protected final Object zzl(int i, Object obj, Object obj2) {
        int i2 = i - 1;
        if (i2 == 0) {
            return (byte) 1;
        }
        if (i2 == 2) {
            return zzbz(zzg, "\u0001\u0002\u0000\u0001\u0001\u0002\u0002\u0000\u0000\u0000\u0001ဈ\u0000\u0002ဂ\u0001", new Object[]{"zza", "zze", "zzf"});
        }
        if (i2 == 3) {
            return new zzfq();
        }
        zzff zzffVar = null;
        if (i2 == 4) {
            return new zzfp(zzffVar);
        }
        if (i2 != 5) {
            return null;
        }
        return zzg;
    }
}
