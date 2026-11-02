package com.google.android.gms.internal.gtm;

import com.google.android.gms.internal.gtm.zzuq;
import java.io.IOException;

/* loaded from: classes.dex */
public abstract class zzuq<M extends zzuq<M>> extends zzuw {
    protected zzus zzbhb;

    @Override // com.google.android.gms.internal.gtm.zzuw
    protected int zzy() {
        if (this.zzbhb == null) {
            return 0;
        }
        int iZzy = 0;
        for (int i = 0; i < this.zzbhb.size(); i++) {
            iZzy += this.zzbhb.zzce(i).zzy();
        }
        return iZzy;
    }

    @Override // com.google.android.gms.internal.gtm.zzuw
    public void zza(zzuo zzuoVar) throws IOException {
        if (this.zzbhb == null) {
            return;
        }
        for (int i = 0; i < this.zzbhb.size(); i++) {
            this.zzbhb.zzce(i).zza(zzuoVar);
        }
    }

    public final <T> T zza(zzur<M, T> zzurVar) {
        zzut zzutVarZzcd;
        zzus zzusVar = this.zzbhb;
        if (zzusVar == null || (zzutVarZzcd = zzusVar.zzcd(zzurVar.tag >>> 3)) == null) {
            return null;
        }
        return (T) zzutVarZzcd.zzb(zzurVar);
    }

    protected final boolean zza(zzun zzunVar, int i) throws IOException {
        int position = zzunVar.getPosition();
        if (!zzunVar.zzao(i)) {
            return false;
        }
        int i2 = i >>> 3;
        zzuy zzuyVar = new zzuy(i, zzunVar.zzt(position, zzunVar.getPosition() - position));
        zzut zzutVarZzcd = null;
        zzus zzusVar = this.zzbhb;
        if (zzusVar == null) {
            this.zzbhb = new zzus();
        } else {
            zzutVarZzcd = zzusVar.zzcd(i2);
        }
        if (zzutVarZzcd == null) {
            zzutVarZzcd = new zzut();
            this.zzbhb.zza(i2, zzutVarZzcd);
        }
        zzutVarZzcd.zza(zzuyVar);
        return true;
    }

    @Override // com.google.android.gms.internal.gtm.zzuw
    /* renamed from: zzry */
    public final /* synthetic */ zzuw clone() throws CloneNotSupportedException {
        return (zzuq) clone();
    }

    @Override // com.google.android.gms.internal.gtm.zzuw
    public /* synthetic */ Object clone() throws CloneNotSupportedException {
        zzuq zzuqVar = (zzuq) super.clone();
        zzuu.zza(this, zzuqVar);
        return zzuqVar;
    }
}
