package com.google.android.gms.internal.gtm;

import it.orsozoxi.android.orsonotes.R2;
import java.io.IOException;

/* loaded from: classes.dex */
public final class zzg {

    public static final class zza extends zzuq<zza> {
        public static final zzur<zzl, zza> zzpx = zzur.zza(11, zza.class, 810);
        private static final zza[] zzpy = new zza[0];
        public int[] zzpz = zzuz.zzbcw;
        public int[] zzqa = zzuz.zzbcw;
        public int[] zzqb = zzuz.zzbcw;
        private int zzqc = 0;
        public int[] zzqd = zzuz.zzbcw;
        public int zzqe = 0;
        private int zzqf = 0;

        public zza() {
            this.zzbhb = null;
            this.zzbhl = -1;
        }

        public final boolean equals(Object obj) {
            if (obj == this) {
                return true;
            }
            if (!(obj instanceof zza)) {
                return false;
            }
            zza zzaVar = (zza) obj;
            if (!zzuu.equals(this.zzpz, zzaVar.zzpz) || !zzuu.equals(this.zzqa, zzaVar.zzqa) || !zzuu.equals(this.zzqb, zzaVar.zzqb) || this.zzqc != zzaVar.zzqc || !zzuu.equals(this.zzqd, zzaVar.zzqd) || this.zzqe != zzaVar.zzqe || this.zzqf != zzaVar.zzqf) {
                return false;
            }
            if (this.zzbhb == null || this.zzbhb.isEmpty()) {
                return zzaVar.zzbhb == null || zzaVar.zzbhb.isEmpty();
            }
            return this.zzbhb.equals(zzaVar.zzbhb);
        }

        public final int hashCode() {
            return ((((((((((((((((getClass().getName().hashCode() + R2.attr.hoveredFocusedTranslationZ) * 31) + zzuu.hashCode(this.zzpz)) * 31) + zzuu.hashCode(this.zzqa)) * 31) + zzuu.hashCode(this.zzqb)) * 31) + this.zzqc) * 31) + zzuu.hashCode(this.zzqd)) * 31) + this.zzqe) * 31) + this.zzqf) * 31) + ((this.zzbhb == null || this.zzbhb.isEmpty()) ? 0 : this.zzbhb.hashCode());
        }

        @Override // com.google.android.gms.internal.gtm.zzuq, com.google.android.gms.internal.gtm.zzuw
        public final void zza(zzuo zzuoVar) throws IOException {
            int[] iArr = this.zzpz;
            int i = 0;
            if (iArr != null && iArr.length > 0) {
                int i2 = 0;
                while (true) {
                    int[] iArr2 = this.zzpz;
                    if (i2 >= iArr2.length) {
                        break;
                    }
                    zzuoVar.zze(1, iArr2[i2]);
                    i2++;
                }
            }
            int[] iArr3 = this.zzqa;
            if (iArr3 != null && iArr3.length > 0) {
                int i3 = 0;
                while (true) {
                    int[] iArr4 = this.zzqa;
                    if (i3 >= iArr4.length) {
                        break;
                    }
                    zzuoVar.zze(2, iArr4[i3]);
                    i3++;
                }
            }
            int[] iArr5 = this.zzqb;
            if (iArr5 != null && iArr5.length > 0) {
                int i4 = 0;
                while (true) {
                    int[] iArr6 = this.zzqb;
                    if (i4 >= iArr6.length) {
                        break;
                    }
                    zzuoVar.zze(3, iArr6[i4]);
                    i4++;
                }
            }
            int i5 = this.zzqc;
            if (i5 != 0) {
                zzuoVar.zze(4, i5);
            }
            int[] iArr7 = this.zzqd;
            if (iArr7 != null && iArr7.length > 0) {
                while (true) {
                    int[] iArr8 = this.zzqd;
                    if (i >= iArr8.length) {
                        break;
                    }
                    zzuoVar.zze(5, iArr8[i]);
                    i++;
                }
            }
            int i6 = this.zzqe;
            if (i6 != 0) {
                zzuoVar.zze(6, i6);
            }
            int i7 = this.zzqf;
            if (i7 != 0) {
                zzuoVar.zze(7, i7);
            }
            super.zza(zzuoVar);
        }

        @Override // com.google.android.gms.internal.gtm.zzuq, com.google.android.gms.internal.gtm.zzuw
        protected final int zzy() {
            int[] iArr;
            int[] iArr2;
            int[] iArr3;
            int[] iArr4;
            int iZzy = super.zzy();
            int[] iArr5 = this.zzpz;
            int i = 0;
            if (iArr5 != null && iArr5.length > 0) {
                int i2 = 0;
                int iZzbc = 0;
                while (true) {
                    iArr4 = this.zzpz;
                    if (i2 >= iArr4.length) {
                        break;
                    }
                    iZzbc += zzuo.zzbc(iArr4[i2]);
                    i2++;
                }
                iZzy = iZzy + iZzbc + (iArr4.length * 1);
            }
            int[] iArr6 = this.zzqa;
            if (iArr6 != null && iArr6.length > 0) {
                int i3 = 0;
                int iZzbc2 = 0;
                while (true) {
                    iArr3 = this.zzqa;
                    if (i3 >= iArr3.length) {
                        break;
                    }
                    iZzbc2 += zzuo.zzbc(iArr3[i3]);
                    i3++;
                }
                iZzy = iZzy + iZzbc2 + (iArr3.length * 1);
            }
            int[] iArr7 = this.zzqb;
            if (iArr7 != null && iArr7.length > 0) {
                int i4 = 0;
                int iZzbc3 = 0;
                while (true) {
                    iArr2 = this.zzqb;
                    if (i4 >= iArr2.length) {
                        break;
                    }
                    iZzbc3 += zzuo.zzbc(iArr2[i4]);
                    i4++;
                }
                iZzy = iZzy + iZzbc3 + (iArr2.length * 1);
            }
            int i5 = this.zzqc;
            if (i5 != 0) {
                iZzy += zzuo.zzi(4, i5);
            }
            int[] iArr8 = this.zzqd;
            if (iArr8 != null && iArr8.length > 0) {
                int iZzbc4 = 0;
                while (true) {
                    iArr = this.zzqd;
                    if (i >= iArr.length) {
                        break;
                    }
                    iZzbc4 += zzuo.zzbc(iArr[i]);
                    i++;
                }
                iZzy = iZzy + iZzbc4 + (iArr.length * 1);
            }
            int i6 = this.zzqe;
            if (i6 != 0) {
                iZzy += zzuo.zzi(6, i6);
            }
            int i7 = this.zzqf;
            return i7 != 0 ? iZzy + zzuo.zzi(7, i7) : iZzy;
        }

        @Override // com.google.android.gms.internal.gtm.zzuw
        public final /* synthetic */ zzuw zza(zzun zzunVar) throws IOException {
            while (true) {
                int iZzni = zzunVar.zzni();
                switch (iZzni) {
                    case 0:
                        return this;
                    case 8:
                        int iZzb = zzuz.zzb(zzunVar, 8);
                        int[] iArr = this.zzpz;
                        int length = iArr == null ? 0 : iArr.length;
                        int i = iZzb + length;
                        int[] iArr2 = new int[i];
                        if (length != 0) {
                            System.arraycopy(this.zzpz, 0, iArr2, 0, length);
                        }
                        while (length < i - 1) {
                            iArr2[length] = zzunVar.zzoa();
                            zzunVar.zzni();
                            length++;
                        }
                        iArr2[length] = zzunVar.zzoa();
                        this.zzpz = iArr2;
                        break;
                    case 10:
                        int iZzaq = zzunVar.zzaq(zzunVar.zzoa());
                        int position = zzunVar.getPosition();
                        int i2 = 0;
                        while (zzunVar.zzrv() > 0) {
                            zzunVar.zzoa();
                            i2++;
                        }
                        zzunVar.zzbz(position);
                        int[] iArr3 = this.zzpz;
                        int length2 = iArr3 == null ? 0 : iArr3.length;
                        int i3 = i2 + length2;
                        int[] iArr4 = new int[i3];
                        if (length2 != 0) {
                            System.arraycopy(this.zzpz, 0, iArr4, 0, length2);
                        }
                        while (length2 < i3) {
                            iArr4[length2] = zzunVar.zzoa();
                            length2++;
                        }
                        this.zzpz = iArr4;
                        zzunVar.zzar(iZzaq);
                        break;
                    case 16:
                        int iZzb2 = zzuz.zzb(zzunVar, 16);
                        int[] iArr5 = this.zzqa;
                        int length3 = iArr5 == null ? 0 : iArr5.length;
                        int i4 = iZzb2 + length3;
                        int[] iArr6 = new int[i4];
                        if (length3 != 0) {
                            System.arraycopy(this.zzqa, 0, iArr6, 0, length3);
                        }
                        while (length3 < i4 - 1) {
                            iArr6[length3] = zzunVar.zzoa();
                            zzunVar.zzni();
                            length3++;
                        }
                        iArr6[length3] = zzunVar.zzoa();
                        this.zzqa = iArr6;
                        break;
                    case 18:
                        int iZzaq2 = zzunVar.zzaq(zzunVar.zzoa());
                        int position2 = zzunVar.getPosition();
                        int i5 = 0;
                        while (zzunVar.zzrv() > 0) {
                            zzunVar.zzoa();
                            i5++;
                        }
                        zzunVar.zzbz(position2);
                        int[] iArr7 = this.zzqa;
                        int length4 = iArr7 == null ? 0 : iArr7.length;
                        int i6 = i5 + length4;
                        int[] iArr8 = new int[i6];
                        if (length4 != 0) {
                            System.arraycopy(this.zzqa, 0, iArr8, 0, length4);
                        }
                        while (length4 < i6) {
                            iArr8[length4] = zzunVar.zzoa();
                            length4++;
                        }
                        this.zzqa = iArr8;
                        zzunVar.zzar(iZzaq2);
                        break;
                    case 24:
                        int iZzb3 = zzuz.zzb(zzunVar, 24);
                        int[] iArr9 = this.zzqb;
                        int length5 = iArr9 == null ? 0 : iArr9.length;
                        int i7 = iZzb3 + length5;
                        int[] iArr10 = new int[i7];
                        if (length5 != 0) {
                            System.arraycopy(this.zzqb, 0, iArr10, 0, length5);
                        }
                        while (length5 < i7 - 1) {
                            iArr10[length5] = zzunVar.zzoa();
                            zzunVar.zzni();
                            length5++;
                        }
                        iArr10[length5] = zzunVar.zzoa();
                        this.zzqb = iArr10;
                        break;
                    case 26:
                        int iZzaq3 = zzunVar.zzaq(zzunVar.zzoa());
                        int position3 = zzunVar.getPosition();
                        int i8 = 0;
                        while (zzunVar.zzrv() > 0) {
                            zzunVar.zzoa();
                            i8++;
                        }
                        zzunVar.zzbz(position3);
                        int[] iArr11 = this.zzqb;
                        int length6 = iArr11 == null ? 0 : iArr11.length;
                        int i9 = i8 + length6;
                        int[] iArr12 = new int[i9];
                        if (length6 != 0) {
                            System.arraycopy(this.zzqb, 0, iArr12, 0, length6);
                        }
                        while (length6 < i9) {
                            iArr12[length6] = zzunVar.zzoa();
                            length6++;
                        }
                        this.zzqb = iArr12;
                        zzunVar.zzar(iZzaq3);
                        break;
                    case 32:
                        this.zzqc = zzunVar.zzoa();
                        break;
                    case 40:
                        int iZzb4 = zzuz.zzb(zzunVar, 40);
                        int[] iArr13 = this.zzqd;
                        int length7 = iArr13 == null ? 0 : iArr13.length;
                        int i10 = iZzb4 + length7;
                        int[] iArr14 = new int[i10];
                        if (length7 != 0) {
                            System.arraycopy(this.zzqd, 0, iArr14, 0, length7);
                        }
                        while (length7 < i10 - 1) {
                            iArr14[length7] = zzunVar.zzoa();
                            zzunVar.zzni();
                            length7++;
                        }
                        iArr14[length7] = zzunVar.zzoa();
                        this.zzqd = iArr14;
                        break;
                    case 42:
                        int iZzaq4 = zzunVar.zzaq(zzunVar.zzoa());
                        int position4 = zzunVar.getPosition();
                        int i11 = 0;
                        while (zzunVar.zzrv() > 0) {
                            zzunVar.zzoa();
                            i11++;
                        }
                        zzunVar.zzbz(position4);
                        int[] iArr15 = this.zzqd;
                        int length8 = iArr15 == null ? 0 : iArr15.length;
                        int i12 = i11 + length8;
                        int[] iArr16 = new int[i12];
                        if (length8 != 0) {
                            System.arraycopy(this.zzqd, 0, iArr16, 0, length8);
                        }
                        while (length8 < i12) {
                            iArr16[length8] = zzunVar.zzoa();
                            length8++;
                        }
                        this.zzqd = iArr16;
                        zzunVar.zzar(iZzaq4);
                        break;
                    case 48:
                        this.zzqe = zzunVar.zzoa();
                        break;
                    case 56:
                        this.zzqf = zzunVar.zzoa();
                        break;
                    default:
                        if (!super.zza(zzunVar, iZzni)) {
                            return this;
                        }
                        break;
                }
            }
        }
    }
}
