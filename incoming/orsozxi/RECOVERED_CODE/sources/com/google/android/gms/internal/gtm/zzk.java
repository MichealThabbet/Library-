package com.google.android.gms.internal.gtm;

import it.orsozoxi.android.orsonotes.R2;
import java.io.IOException;

/* loaded from: classes.dex */
public final class zzk extends zzuq<zzk> {
    public zzj[] zzqj = zzj.zzz();
    public zzi zzqk = null;
    public String zzql = "";

    public zzk() {
        this.zzbhb = null;
        this.zzbhl = -1;
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof zzk)) {
            return false;
        }
        zzk zzkVar = (zzk) obj;
        if (!zzuu.equals(this.zzqj, zzkVar.zzqj)) {
            return false;
        }
        zzi zziVar = this.zzqk;
        if (zziVar == null) {
            if (zzkVar.zzqk != null) {
                return false;
            }
        } else if (!zziVar.equals(zzkVar.zzqk)) {
            return false;
        }
        String str = this.zzql;
        if (str == null) {
            if (zzkVar.zzql != null) {
                return false;
            }
        } else if (!str.equals(zzkVar.zzql)) {
            return false;
        }
        if (this.zzbhb == null || this.zzbhb.isEmpty()) {
            return zzkVar.zzbhb == null || zzkVar.zzbhb.isEmpty();
        }
        return this.zzbhb.equals(zzkVar.zzbhb);
    }

    public final int hashCode() {
        int iHashCode = ((getClass().getName().hashCode() + R2.attr.hoveredFocusedTranslationZ) * 31) + zzuu.hashCode(this.zzqj);
        zzi zziVar = this.zzqk;
        int iHashCode2 = 0;
        int iHashCode3 = ((iHashCode * 31) + (zziVar == null ? 0 : zziVar.hashCode())) * 31;
        String str = this.zzql;
        int iHashCode4 = (iHashCode3 + (str == null ? 0 : str.hashCode())) * 31;
        if (this.zzbhb != null && !this.zzbhb.isEmpty()) {
            iHashCode2 = this.zzbhb.hashCode();
        }
        return iHashCode4 + iHashCode2;
    }

    @Override // com.google.android.gms.internal.gtm.zzuq, com.google.android.gms.internal.gtm.zzuw
    public final void zza(zzuo zzuoVar) throws IOException {
        zzj[] zzjVarArr = this.zzqj;
        if (zzjVarArr != null && zzjVarArr.length > 0) {
            int i = 0;
            while (true) {
                zzj[] zzjVarArr2 = this.zzqj;
                if (i >= zzjVarArr2.length) {
                    break;
                }
                zzj zzjVar = zzjVarArr2[i];
                if (zzjVar != null) {
                    zzuoVar.zza(1, zzjVar);
                }
                i++;
            }
        }
        zzi zziVar = this.zzqk;
        if (zziVar != null) {
            zzuoVar.zza(2, zziVar);
        }
        String str = this.zzql;
        if (str != null && !str.equals("")) {
            zzuoVar.zza(3, this.zzql);
        }
        super.zza(zzuoVar);
    }

    @Override // com.google.android.gms.internal.gtm.zzuq, com.google.android.gms.internal.gtm.zzuw
    protected final int zzy() {
        int iZzy = super.zzy();
        zzj[] zzjVarArr = this.zzqj;
        if (zzjVarArr != null && zzjVarArr.length > 0) {
            int i = 0;
            while (true) {
                zzj[] zzjVarArr2 = this.zzqj;
                if (i >= zzjVarArr2.length) {
                    break;
                }
                zzj zzjVar = zzjVarArr2[i];
                if (zzjVar != null) {
                    iZzy += zzuo.zzb(1, zzjVar);
                }
                i++;
            }
        }
        zzi zziVar = this.zzqk;
        if (zziVar != null) {
            iZzy += zzuo.zzb(2, zziVar);
        }
        String str = this.zzql;
        return (str == null || str.equals("")) ? iZzy : iZzy + zzuo.zzb(3, this.zzql);
    }

    @Override // com.google.android.gms.internal.gtm.zzuw
    public final /* synthetic */ zzuw zza(zzun zzunVar) throws IOException {
        while (true) {
            int iZzni = zzunVar.zzni();
            if (iZzni == 0) {
                return this;
            }
            if (iZzni == 10) {
                int iZzb = zzuz.zzb(zzunVar, 10);
                zzj[] zzjVarArr = this.zzqj;
                int length = zzjVarArr == null ? 0 : zzjVarArr.length;
                int i = iZzb + length;
                zzj[] zzjVarArr2 = new zzj[i];
                if (length != 0) {
                    System.arraycopy(this.zzqj, 0, zzjVarArr2, 0, length);
                }
                while (length < i - 1) {
                    zzjVarArr2[length] = new zzj();
                    zzunVar.zza(zzjVarArr2[length]);
                    zzunVar.zzni();
                    length++;
                }
                zzjVarArr2[length] = new zzj();
                zzunVar.zza(zzjVarArr2[length]);
                this.zzqj = zzjVarArr2;
            } else if (iZzni == 18) {
                if (this.zzqk == null) {
                    this.zzqk = new zzi();
                }
                zzunVar.zza(this.zzqk);
            } else if (iZzni != 26) {
                if (!super.zza(zzunVar, iZzni)) {
                    return this;
                }
            } else {
                this.zzql = zzunVar.readString();
            }
        }
    }
}
