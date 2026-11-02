package com.google.android.gms.internal.measurement;

/* compiled from: com.google.android.gms:play-services-measurement@@19.0.0 */
/* loaded from: classes.dex */
public final class zzfk extends zzkd<zzfk, zzfj> implements zzlj {
    private static final zzfk zzi;
    private int zza;
    private int zze;
    private zzgd zzf;
    private zzgd zzg;
    private boolean zzh;

    static {
        zzfk zzfkVar = new zzfk();
        zzi = zzfkVar;
        zzkd.zzby(zzfk.class, zzfkVar);
    }

    private zzfk() {
    }

    public static zzfj zzh() {
        return zzi.zzbt();
    }

    static /* synthetic */ void zzj(zzfk zzfkVar, int i) {
        zzfkVar.zza |= 1;
        zzfkVar.zze = i;
    }

    static /* synthetic */ void zzk(zzfk zzfkVar, zzgd zzgdVar) {
        zzgdVar.getClass();
        zzfkVar.zzf = zzgdVar;
        zzfkVar.zza |= 2;
    }

    static /* synthetic */ void zzm(zzfk zzfkVar, zzgd zzgdVar) {
        zzfkVar.zzg = zzgdVar;
        zzfkVar.zza |= 4;
    }

    static /* synthetic */ void zzn(zzfk zzfkVar, boolean z) {
        zzfkVar.zza |= 8;
        zzfkVar.zzh = z;
    }

    public final boolean zza() {
        return (this.zza & 1) != 0;
    }

    public final int zzb() {
        return this.zze;
    }

    public final zzgd zzc() {
        zzgd zzgdVar = this.zzf;
        return zzgdVar == null ? zzgd.zzm() : zzgdVar;
    }

    public final boolean zzd() {
        return (this.zza & 4) != 0;
    }

    public final zzgd zze() {
        zzgd zzgdVar = this.zzg;
        return zzgdVar == null ? zzgd.zzm() : zzgdVar;
    }

    public final boolean zzf() {
        return (this.zza & 8) != 0;
    }

    public final boolean zzg() {
        return this.zzh;
    }

    @Override // com.google.android.gms.internal.measurement.zzkd
    protected final Object zzl(int i, Object obj, Object obj2) {
        int i2 = i - 1;
        if (i2 == 0) {
            return (byte) 1;
        }
        if (i2 == 2) {
            return zzbz(zzi, "\u0001\u0004\u0000\u0001\u0001\u0004\u0004\u0000\u0000\u0000\u0001င\u0000\u0002ဉ\u0001\u0003ဉ\u0002\u0004ဇ\u0003", new Object[]{"zza", "zze", "zzf", "zzg", "zzh"});
        }
        if (i2 == 3) {
            return new zzfk();
        }
        zzff zzffVar = null;
        if (i2 == 4) {
            return new zzfj(zzffVar);
        }
        if (i2 != 5) {
            return null;
        }
        return zzi;
    }
}
