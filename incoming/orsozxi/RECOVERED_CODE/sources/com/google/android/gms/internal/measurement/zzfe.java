package com.google.android.gms.internal.measurement;

/* compiled from: com.google.android.gms:play-services-measurement@@19.0.0 */
/* loaded from: classes.dex */
public final class zzfe extends zzkd<zzfe, zzfd> implements zzlj {
    private static final zzfe zzg;
    private int zza;
    private String zze = "";
    private String zzf = "";

    static {
        zzfe zzfeVar = new zzfe();
        zzg = zzfeVar;
        zzkd.zzby(zzfe.class, zzfeVar);
    }

    private zzfe() {
    }

    public final String zza() {
        return this.zze;
    }

    public final String zzb() {
        return this.zzf;
    }

    @Override // com.google.android.gms.internal.measurement.zzkd
    protected final Object zzl(int i, Object obj, Object obj2) {
        int i2 = i - 1;
        if (i2 == 0) {
            return (byte) 1;
        }
        if (i2 == 2) {
            return zzbz(zzg, "\u0001\u0002\u0000\u0001\u0001\u0002\u0002\u0000\u0000\u0000\u0001ဈ\u0000\u0002ဈ\u0001", new Object[]{"zza", "zze", "zzf"});
        }
        if (i2 == 3) {
            return new zzfe();
        }
        zzey zzeyVar = null;
        if (i2 == 4) {
            return new zzfd(zzeyVar);
        }
        if (i2 != 5) {
            return null;
        }
        return zzg;
    }
}
