package com.google.android.gms.internal.gtm;

import com.google.android.gms.internal.gtm.zzrc;

/* loaded from: classes.dex */
final class zzrz implements zzta {
    private static final zzsj zzbcn = new zzsa();
    private final zzsj zzbcm;

    public zzrz() {
        this(new zzsb(zzrb.zzpc(), zzqe()));
    }

    private zzrz(zzsj zzsjVar) {
        this.zzbcm = (zzsj) zzre.zza(zzsjVar, "messageInfoFactory");
    }

    @Override // com.google.android.gms.internal.gtm.zzta
    public final <T> zzsz<T> zzh(Class<T> cls) {
        zztb.zzj(cls);
        zzsi zzsiVarZzf = this.zzbcm.zzf(cls);
        if (zzsiVarZzf.zzqm()) {
            if (zzrc.class.isAssignableFrom(cls)) {
                return zzsp.zza(zztb.zzqx(), zzqs.zzot(), zzsiVarZzf.zzqn());
            }
            return zzsp.zza(zztb.zzqv(), zzqs.zzou(), zzsiVarZzf.zzqn());
        }
        if (zzrc.class.isAssignableFrom(cls)) {
            if (zza(zzsiVarZzf)) {
                return zzso.zza(cls, zzsiVarZzf, zzst.zzqq(), zzru.zzqd(), zztb.zzqx(), zzqs.zzot(), zzsh.zzqj());
            }
            return zzso.zza(cls, zzsiVarZzf, zzst.zzqq(), zzru.zzqd(), zztb.zzqx(), null, zzsh.zzqj());
        }
        if (zza(zzsiVarZzf)) {
            return zzso.zza(cls, zzsiVarZzf, zzst.zzqp(), zzru.zzqc(), zztb.zzqv(), zzqs.zzou(), zzsh.zzqi());
        }
        return zzso.zza(cls, zzsiVarZzf, zzst.zzqp(), zzru.zzqc(), zztb.zzqw(), null, zzsh.zzqi());
    }

    private static boolean zza(zzsi zzsiVar) {
        return zzsiVar.zzql() == zzrc.zze.zzbaz;
    }

    private static zzsj zzqe() {
        try {
            return (zzsj) Class.forName("com.google.protobuf.DescriptorMessageInfoFactory").getDeclaredMethod("getInstance", new Class[0]).invoke(null, new Object[0]);
        } catch (Exception unused) {
            return zzbcn;
        }
    }
}
