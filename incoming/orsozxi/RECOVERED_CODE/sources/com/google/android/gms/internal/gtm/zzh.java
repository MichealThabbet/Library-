package com.google.android.gms.internal.gtm;

import com.google.android.gms.internal.gtm.zzc;
import it.orsozoxi.android.orsonotes.R2;
import java.io.IOException;

/* loaded from: classes.dex */
public final class zzh extends zzuq<zzh> {
    public zzl[] zzpe = zzl.zzaa();
    public zzl[] zzpf = zzl.zzaa();
    public zzc.C0029zzc[] zzpg = new zzc.C0029zzc[0];

    public zzh() {
        this.zzbhb = null;
        this.zzbhl = -1;
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof zzh)) {
            return false;
        }
        zzh zzhVar = (zzh) obj;
        if (!zzuu.equals(this.zzpe, zzhVar.zzpe) || !zzuu.equals(this.zzpf, zzhVar.zzpf) || !zzuu.equals(this.zzpg, zzhVar.zzpg)) {
            return false;
        }
        if (this.zzbhb == null || this.zzbhb.isEmpty()) {
            return zzhVar.zzbhb == null || zzhVar.zzbhb.isEmpty();
        }
        return this.zzbhb.equals(zzhVar.zzbhb);
    }

    public final int hashCode() {
        return ((((((((getClass().getName().hashCode() + R2.attr.hoveredFocusedTranslationZ) * 31) + zzuu.hashCode(this.zzpe)) * 31) + zzuu.hashCode(this.zzpf)) * 31) + zzuu.hashCode(this.zzpg)) * 31) + ((this.zzbhb == null || this.zzbhb.isEmpty()) ? 0 : this.zzbhb.hashCode());
    }

    @Override // com.google.android.gms.internal.gtm.zzuq, com.google.android.gms.internal.gtm.zzuw
    public final void zza(zzuo zzuoVar) throws IOException {
        zzl[] zzlVarArr = this.zzpe;
        int i = 0;
        if (zzlVarArr != null && zzlVarArr.length > 0) {
            int i2 = 0;
            while (true) {
                zzl[] zzlVarArr2 = this.zzpe;
                if (i2 >= zzlVarArr2.length) {
                    break;
                }
                zzl zzlVar = zzlVarArr2[i2];
                if (zzlVar != null) {
                    zzuoVar.zza(1, zzlVar);
                }
                i2++;
            }
        }
        zzl[] zzlVarArr3 = this.zzpf;
        if (zzlVarArr3 != null && zzlVarArr3.length > 0) {
            int i3 = 0;
            while (true) {
                zzl[] zzlVarArr4 = this.zzpf;
                if (i3 >= zzlVarArr4.length) {
                    break;
                }
                zzl zzlVar2 = zzlVarArr4[i3];
                if (zzlVar2 != null) {
                    zzuoVar.zza(2, zzlVar2);
                }
                i3++;
            }
        }
        zzc.C0029zzc[] c0029zzcArr = this.zzpg;
        if (c0029zzcArr != null && c0029zzcArr.length > 0) {
            while (true) {
                zzc.C0029zzc[] c0029zzcArr2 = this.zzpg;
                if (i >= c0029zzcArr2.length) {
                    break;
                }
                zzc.C0029zzc c0029zzc = c0029zzcArr2[i];
                if (c0029zzc != null) {
                    zzuoVar.zze(3, c0029zzc);
                }
                i++;
            }
        }
        super.zza(zzuoVar);
    }

    @Override // com.google.android.gms.internal.gtm.zzuq, com.google.android.gms.internal.gtm.zzuw
    protected final int zzy() {
        int iZzy = super.zzy();
        zzl[] zzlVarArr = this.zzpe;
        int i = 0;
        if (zzlVarArr != null && zzlVarArr.length > 0) {
            int i2 = 0;
            while (true) {
                zzl[] zzlVarArr2 = this.zzpe;
                if (i2 >= zzlVarArr2.length) {
                    break;
                }
                zzl zzlVar = zzlVarArr2[i2];
                if (zzlVar != null) {
                    iZzy += zzuo.zzb(1, zzlVar);
                }
                i2++;
            }
        }
        zzl[] zzlVarArr3 = this.zzpf;
        if (zzlVarArr3 != null && zzlVarArr3.length > 0) {
            int i3 = 0;
            while (true) {
                zzl[] zzlVarArr4 = this.zzpf;
                if (i3 >= zzlVarArr4.length) {
                    break;
                }
                zzl zzlVar2 = zzlVarArr4[i3];
                if (zzlVar2 != null) {
                    iZzy += zzuo.zzb(2, zzlVar2);
                }
                i3++;
            }
        }
        zzc.C0029zzc[] c0029zzcArr = this.zzpg;
        if (c0029zzcArr != null && c0029zzcArr.length > 0) {
            while (true) {
                zzc.C0029zzc[] c0029zzcArr2 = this.zzpg;
                if (i >= c0029zzcArr2.length) {
                    break;
                }
                zzc.C0029zzc c0029zzc = c0029zzcArr2[i];
                if (c0029zzc != null) {
                    iZzy += zzqj.zzc(3, c0029zzc);
                }
                i++;
            }
        }
        return iZzy;
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
                zzl[] zzlVarArr = this.zzpe;
                int length = zzlVarArr == null ? 0 : zzlVarArr.length;
                int i = iZzb + length;
                zzl[] zzlVarArr2 = new zzl[i];
                if (length != 0) {
                    System.arraycopy(this.zzpe, 0, zzlVarArr2, 0, length);
                }
                while (length < i - 1) {
                    zzlVarArr2[length] = new zzl();
                    zzunVar.zza(zzlVarArr2[length]);
                    zzunVar.zzni();
                    length++;
                }
                zzlVarArr2[length] = new zzl();
                zzunVar.zza(zzlVarArr2[length]);
                this.zzpe = zzlVarArr2;
            } else if (iZzni == 18) {
                int iZzb2 = zzuz.zzb(zzunVar, 18);
                zzl[] zzlVarArr3 = this.zzpf;
                int length2 = zzlVarArr3 == null ? 0 : zzlVarArr3.length;
                int i2 = iZzb2 + length2;
                zzl[] zzlVarArr4 = new zzl[i2];
                if (length2 != 0) {
                    System.arraycopy(this.zzpf, 0, zzlVarArr4, 0, length2);
                }
                while (length2 < i2 - 1) {
                    zzlVarArr4[length2] = new zzl();
                    zzunVar.zza(zzlVarArr4[length2]);
                    zzunVar.zzni();
                    length2++;
                }
                zzlVarArr4[length2] = new zzl();
                zzunVar.zza(zzlVarArr4[length2]);
                this.zzpf = zzlVarArr4;
            } else if (iZzni != 26) {
                if (!super.zza(zzunVar, iZzni)) {
                    return this;
                }
            } else {
                int iZzb3 = zzuz.zzb(zzunVar, 26);
                zzc.C0029zzc[] c0029zzcArr = this.zzpg;
                int length3 = c0029zzcArr == null ? 0 : c0029zzcArr.length;
                int i3 = iZzb3 + length3;
                zzc.C0029zzc[] c0029zzcArr2 = new zzc.C0029zzc[i3];
                if (length3 != 0) {
                    System.arraycopy(this.zzpg, 0, c0029zzcArr2, 0, length3);
                }
                while (length3 < i3 - 1) {
                    c0029zzcArr2[length3] = (zzc.C0029zzc) zzunVar.zza(zzc.C0029zzc.zza());
                    zzunVar.zzni();
                    length3++;
                }
                c0029zzcArr2[length3] = (zzc.C0029zzc) zzunVar.zza(zzc.C0029zzc.zza());
                this.zzpg = c0029zzcArr2;
            }
        }
    }
}
