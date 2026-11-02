package com.google.android.gms.internal.measurement;

/* compiled from: com.google.android.gms:play-services-measurement@@19.0.0 */
/* loaded from: classes.dex */
public final class zzel extends zzkd<zzel, zzek> implements zzlj {
    private static final zzel zzi;
    private int zza;
    private zzex zze;
    private zzeq zzf;
    private boolean zzg;
    private String zzh = "";

    static {
        zzel zzelVar = new zzel();
        zzi = zzelVar;
        zzkd.zzby(zzel.class, zzelVar);
    }

    private zzel() {
    }

    public static zzel zzi() {
        return zzi;
    }

    static /* synthetic */ void zzk(zzel zzelVar, String str) {
        zzelVar.zza |= 8;
        zzelVar.zzh = str;
    }

    public final boolean zza() {
        return (this.zza & 1) != 0;
    }

    public final zzex zzb() {
        zzex zzexVar = this.zze;
        return zzexVar == null ? zzex.zzi() : zzexVar;
    }

    public final boolean zzc() {
        return (this.zza & 2) != 0;
    }

    public final zzeq zzd() {
        zzeq zzeqVar = this.zzf;
        return zzeqVar == null ? zzeq.zzk() : zzeqVar;
    }

    public final boolean zze() {
        return (this.zza & 4) != 0;
    }

    public final boolean zzf() {
        return this.zzg;
    }

    public final boolean zzg() {
        return (this.zza & 8) != 0;
    }

    public final String zzh() {
        return this.zzh;
    }

    @Override // com.google.android.gms.internal.measurement.zzkd
    protected final Object zzl(int i, Object obj, Object obj2) {
        int i2 = i - 1;
        if (i2 == 0) {
            return (byte) 1;
        }
        if (i2 == 2) {
            return zzbz(zzi, "\u0001\u0004\u0000\u0001\u0001\u0004\u0004\u0000\u0000\u0000\u0001ဉ\u0000\u0002ဉ\u0001\u0003ဇ\u0002\u0004ဈ\u0003", new Object[]{"zza", "zze", "zzf", "zzg", "zzh"});
        }
        if (i2 == 3) {
            return new zzel();
        }
        zzef zzefVar = null;
        if (i2 == 4) {
            return new zzek(zzefVar);
        }
        if (i2 != 5) {
            return null;
        }
        return zzi;
    }
}
