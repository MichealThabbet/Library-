package com.google.android.gms.internal.measurement;

import it.piapostolos.android.pinotes.R2;
import java.io.IOException;
import java.util.Arrays;

/* compiled from: com.google.android.gms:play-services-measurement-base@@19.0.0 */
/* loaded from: classes.dex */
public final class zzmi {
    private static final zzmi zza = new zzmi(0, new int[0], new Object[0], false);
    private int zzb;
    private int[] zzc;
    private Object[] zzd;
    private int zze;
    private boolean zzf;

    private zzmi() {
        this(0, new int[8], new Object[8], true);
    }

    private zzmi(int i, int[] iArr, Object[] objArr, boolean z) {
        this.zze = -1;
        this.zzb = i;
        this.zzc = iArr;
        this.zzd = objArr;
        this.zzf = z;
    }

    public static zzmi zza() {
        return zza;
    }

    static zzmi zzb() {
        return new zzmi(0, new int[8], new Object[8], true);
    }

    static zzmi zzc(zzmi zzmiVar, zzmi zzmiVar2) {
        int i = zzmiVar.zzb + zzmiVar2.zzb;
        int[] iArrCopyOf = Arrays.copyOf(zzmiVar.zzc, i);
        System.arraycopy(zzmiVar2.zzc, 0, iArrCopyOf, zzmiVar.zzb, zzmiVar2.zzb);
        Object[] objArrCopyOf = Arrays.copyOf(zzmiVar.zzd, i);
        System.arraycopy(zzmiVar2.zzd, 0, objArrCopyOf, zzmiVar.zzb, zzmiVar2.zzb);
        return new zzmi(i, iArrCopyOf, objArrCopyOf, true);
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof zzmi)) {
            return false;
        }
        zzmi zzmiVar = (zzmi) obj;
        int i = this.zzb;
        if (i == zzmiVar.zzb) {
            int[] iArr = this.zzc;
            int[] iArr2 = zzmiVar.zzc;
            int i2 = 0;
            while (true) {
                if (i2 >= i) {
                    Object[] objArr = this.zzd;
                    Object[] objArr2 = zzmiVar.zzd;
                    int i3 = this.zzb;
                    for (int i4 = 0; i4 < i3; i4++) {
                        if (objArr[i4].equals(objArr2[i4])) {
                        }
                    }
                    return true;
                }
                if (iArr[i2] != iArr2[i2]) {
                    break;
                }
                i2++;
            }
        }
        return false;
    }

    public final int hashCode() {
        int i = this.zzb;
        int i2 = (i + R2.attr.hoveredFocusedTranslationZ) * 31;
        int[] iArr = this.zzc;
        int iHashCode = 17;
        int i3 = 17;
        for (int i4 = 0; i4 < i; i4++) {
            i3 = (i3 * 31) + iArr[i4];
        }
        int i5 = (i2 + i3) * 31;
        Object[] objArr = this.zzd;
        int i6 = this.zzb;
        for (int i7 = 0; i7 < i6; i7++) {
            iHashCode = (iHashCode * 31) + objArr[i7].hashCode();
        }
        return i5 + iHashCode;
    }

    public final void zzd() {
        this.zzf = false;
    }

    public final int zze() {
        int i = this.zze;
        if (i != -1) {
            return i;
        }
        int iZzw = 0;
        for (int i2 = 0; i2 < this.zzb; i2++) {
            int i3 = this.zzc[i2];
            zzjd zzjdVar = (zzjd) this.zzd[i2];
            int iZzw2 = zzjk.zzw(8);
            int iZzc = zzjdVar.zzc();
            iZzw += iZzw2 + iZzw2 + zzjk.zzw(16) + zzjk.zzw(i3 >>> 3) + zzjk.zzw(24) + zzjk.zzw(iZzc) + iZzc;
        }
        this.zze = iZzw;
        return iZzw;
    }

    public final int zzf() {
        int iZzw;
        int iZzx;
        int iZzw2;
        int i = this.zze;
        if (i != -1) {
            return i;
        }
        int iZzw3 = 0;
        for (int i2 = 0; i2 < this.zzb; i2++) {
            int i3 = this.zzc[i2];
            int i4 = i3 >>> 3;
            int i5 = i3 & 7;
            if (i5 != 0) {
                if (i5 == 1) {
                    ((Long) this.zzd[i2]).longValue();
                    iZzw2 = zzjk.zzw(i4 << 3) + 8;
                } else if (i5 == 2) {
                    zzjd zzjdVar = (zzjd) this.zzd[i2];
                    int iZzw4 = zzjk.zzw(i4 << 3);
                    int iZzc = zzjdVar.zzc();
                    iZzw3 += iZzw4 + zzjk.zzw(iZzc) + iZzc;
                } else if (i5 == 3) {
                    int iZzu = zzjk.zzu(i4);
                    iZzw = iZzu + iZzu;
                    iZzx = ((zzmi) this.zzd[i2]).zzf();
                } else {
                    if (i5 != 5) {
                        throw new IllegalStateException(zzkn.zzd());
                    }
                    ((Integer) this.zzd[i2]).intValue();
                    iZzw2 = zzjk.zzw(i4 << 3) + 4;
                }
                iZzw3 += iZzw2;
            } else {
                long jLongValue = ((Long) this.zzd[i2]).longValue();
                iZzw = zzjk.zzw(i4 << 3);
                iZzx = zzjk.zzx(jLongValue);
            }
            iZzw2 = iZzw + iZzx;
            iZzw3 += iZzw2;
        }
        this.zze = iZzw3;
        return iZzw3;
    }

    final void zzg(StringBuilder sb, int i) {
        for (int i2 = 0; i2 < this.zzb; i2++) {
            zzlk.zzb(sb, i, String.valueOf(this.zzc[i2] >>> 3), this.zzd[i2]);
        }
    }

    final void zzh(int i, Object obj) {
        if (!this.zzf) {
            throw new UnsupportedOperationException();
        }
        int i2 = this.zzb;
        int[] iArr = this.zzc;
        if (i2 == iArr.length) {
            int i3 = i2 + (i2 < 4 ? 8 : i2 >> 1);
            this.zzc = Arrays.copyOf(iArr, i3);
            this.zzd = Arrays.copyOf(this.zzd, i3);
        }
        int[] iArr2 = this.zzc;
        int i4 = this.zzb;
        iArr2[i4] = i;
        this.zzd[i4] = obj;
        this.zzb = i4 + 1;
    }

    public final void zzi(zzjl zzjlVar) throws IOException {
        if (this.zzb != 0) {
            for (int i = 0; i < this.zzb; i++) {
                int i2 = this.zzc[i];
                Object obj = this.zzd[i];
                int i3 = i2 >>> 3;
                int i4 = i2 & 7;
                if (i4 == 0) {
                    zzjlVar.zzc(i3, ((Long) obj).longValue());
                } else if (i4 == 1) {
                    zzjlVar.zzj(i3, ((Long) obj).longValue());
                } else if (i4 == 2) {
                    zzjlVar.zzn(i3, (zzjd) obj);
                } else if (i4 == 3) {
                    zzjlVar.zzt(i3);
                    ((zzmi) obj).zzi(zzjlVar);
                    zzjlVar.zzu(i3);
                } else {
                    if (i4 != 5) {
                        throw new RuntimeException(zzkn.zzd());
                    }
                    zzjlVar.zzk(i3, ((Integer) obj).intValue());
                }
            }
        }
    }
}
