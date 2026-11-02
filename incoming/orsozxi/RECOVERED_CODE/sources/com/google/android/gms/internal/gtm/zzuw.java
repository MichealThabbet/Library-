package com.google.android.gms.internal.gtm;

import java.io.IOException;

/* loaded from: classes.dex */
public abstract class zzuw {
    protected volatile int zzbhl = -1;

    public abstract zzuw zza(zzun zzunVar) throws IOException;

    public void zza(zzuo zzuoVar) throws IOException {
    }

    protected int zzy() {
        return 0;
    }

    public final int zzse() {
        if (this.zzbhl < 0) {
            zzpe();
        }
        return this.zzbhl;
    }

    public final int zzpe() {
        int iZzy = zzy();
        this.zzbhl = iZzy;
        return iZzy;
    }

    public static final void zza(zzuw zzuwVar, byte[] bArr, int i, int i2) {
        try {
            zzuo zzuoVarZzk = zzuo.zzk(bArr, 0, i2);
            zzuwVar.zza(zzuoVarZzk);
            zzuoVarZzk.zzrx();
        } catch (IOException e) {
            throw new RuntimeException("Serializing to a byte array threw an IOException (should never happen).", e);
        }
    }

    public static final <T extends zzuw> T zza(T t, byte[] bArr) throws zzuv {
        return (T) zzb(t, bArr, 0, bArr.length);
    }

    private static final <T extends zzuw> T zzb(T t, byte[] bArr, int i, int i2) throws zzuv {
        try {
            zzun zzunVarZzj = zzun.zzj(bArr, 0, i2);
            t.zza(zzunVarZzj);
            zzunVarZzj.zzan(0);
            return t;
        } catch (zzuv e) {
            throw e;
        } catch (IOException e2) {
            throw new RuntimeException("Reading from a byte array threw an IOException (should never happen).", e2);
        }
    }

    public String toString() {
        return zzux.zzc(this);
    }

    @Override // 
    /* renamed from: zzry, reason: merged with bridge method [inline-methods] */
    public zzuw clone() throws CloneNotSupportedException {
        return (zzuw) super.clone();
    }
}
