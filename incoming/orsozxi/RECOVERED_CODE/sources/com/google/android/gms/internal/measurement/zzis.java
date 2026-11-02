package com.google.android.gms.internal.measurement;

import java.io.IOException;

/* compiled from: com.google.android.gms:play-services-measurement-base@@19.0.0 */
/* loaded from: classes.dex */
final class zzis {
    static int zza(byte[] bArr, int i, zzir zzirVar) {
        int i2 = i + 1;
        byte b = bArr[i];
        if (b < 0) {
            return zzb(b, bArr, i2, zzirVar);
        }
        zzirVar.zza = b;
        return i2;
    }

    static int zzb(int i, byte[] bArr, int i2, zzir zzirVar) {
        int i3 = i & 127;
        int i4 = i2 + 1;
        byte b = bArr[i2];
        if (b >= 0) {
            zzirVar.zza = i3 | (b << 7);
            return i4;
        }
        int i5 = i3 | ((b & 127) << 7);
        int i6 = i4 + 1;
        byte b2 = bArr[i4];
        if (b2 >= 0) {
            zzirVar.zza = i5 | (b2 << 14);
            return i6;
        }
        int i7 = i5 | ((b2 & 127) << 14);
        int i8 = i6 + 1;
        byte b3 = bArr[i6];
        if (b3 >= 0) {
            zzirVar.zza = i7 | (b3 << 21);
            return i8;
        }
        int i9 = i7 | ((b3 & 127) << 21);
        int i10 = i8 + 1;
        byte b4 = bArr[i8];
        if (b4 >= 0) {
            zzirVar.zza = i9 | (b4 << 28);
            return i10;
        }
        int i11 = i9 | ((b4 & 127) << 28);
        while (true) {
            int i12 = i10 + 1;
            if (bArr[i10] >= 0) {
                zzirVar.zza = i11;
                return i12;
            }
            i10 = i12;
        }
    }

    static int zzc(byte[] bArr, int i, zzir zzirVar) {
        int i2 = i + 1;
        long j = bArr[i];
        if (j >= 0) {
            zzirVar.zzb = j;
            return i2;
        }
        int i3 = i2 + 1;
        byte b = bArr[i2];
        long j2 = (j & 127) | ((b & 127) << 7);
        int i4 = 7;
        while (b < 0) {
            int i5 = i3 + 1;
            i4 += 7;
            j2 |= (r10 & 127) << i4;
            b = bArr[i3];
            i3 = i5;
        }
        zzirVar.zzb = j2;
        return i3;
    }

    static int zzd(byte[] bArr, int i) {
        return ((bArr[i + 3] & 255) << 24) | (bArr[i] & 255) | ((bArr[i + 1] & 255) << 8) | ((bArr[i + 2] & 255) << 16);
    }

    static long zze(byte[] bArr, int i) {
        return ((bArr[i + 7] & 255) << 56) | (bArr[i] & 255) | ((bArr[i + 1] & 255) << 8) | ((bArr[i + 2] & 255) << 16) | ((bArr[i + 3] & 255) << 24) | ((bArr[i + 4] & 255) << 32) | ((bArr[i + 5] & 255) << 40) | ((bArr[i + 6] & 255) << 48);
    }

    static int zzf(byte[] bArr, int i, zzir zzirVar) throws zzkn {
        int iZza = zza(bArr, i, zzirVar);
        int i2 = zzirVar.zza;
        if (i2 < 0) {
            throw zzkn.zzb();
        }
        if (i2 == 0) {
            zzirVar.zzc = "";
            return iZza;
        }
        zzirVar.zzc = new String(bArr, iZza, i2, zzkl.zza);
        return iZza + i2;
    }

    static int zzg(byte[] bArr, int i, zzir zzirVar) throws zzkn {
        int iZza = zza(bArr, i, zzirVar);
        int i2 = zzirVar.zza;
        if (i2 < 0) {
            throw zzkn.zzb();
        }
        if (i2 == 0) {
            zzirVar.zzc = "";
            return iZza;
        }
        zzirVar.zzc = zzmw.zze(bArr, iZza, i2);
        return iZza + i2;
    }

    static int zzh(byte[] bArr, int i, zzir zzirVar) throws zzkn {
        int iZza = zza(bArr, i, zzirVar);
        int i2 = zzirVar.zza;
        if (i2 < 0) {
            throw zzkn.zzb();
        }
        if (i2 > bArr.length - iZza) {
            throw zzkn.zza();
        }
        if (i2 == 0) {
            zzirVar.zzc = zzjd.zzb;
            return iZza;
        }
        zzirVar.zzc = zzjd.zzj(bArr, iZza, i2);
        return iZza + i2;
    }

    static int zzi(zzlt zzltVar, byte[] bArr, int i, int i2, zzir zzirVar) throws IOException {
        int iZzb = i + 1;
        int i3 = bArr[i];
        if (i3 < 0) {
            iZzb = zzb(i3, bArr, iZzb, zzirVar);
            i3 = zzirVar.zza;
        }
        int i4 = iZzb;
        if (i3 < 0 || i3 > i2 - i4) {
            throw zzkn.zza();
        }
        Object objZza = zzltVar.zza();
        int i5 = i3 + i4;
        zzltVar.zzh(objZza, bArr, i4, i5, zzirVar);
        zzltVar.zzi(objZza);
        zzirVar.zzc = objZza;
        return i5;
    }

    static int zzj(zzlt zzltVar, byte[] bArr, int i, int i2, int i3, zzir zzirVar) throws IOException {
        zzll zzllVar = (zzll) zzltVar;
        Object objZza = zzllVar.zza();
        int iZzg = zzllVar.zzg(objZza, bArr, i, i2, i3, zzirVar);
        zzllVar.zzi(objZza);
        zzirVar.zzc = objZza;
        return iZzg;
    }

    static int zzk(int i, byte[] bArr, int i2, int i3, zzkk<?> zzkkVar, zzir zzirVar) {
        zzke zzkeVar = (zzke) zzkkVar;
        int iZza = zza(bArr, i2, zzirVar);
        zzkeVar.zzh(zzirVar.zza);
        while (iZza < i3) {
            int iZza2 = zza(bArr, iZza, zzirVar);
            if (i != zzirVar.zza) {
                break;
            }
            iZza = zza(bArr, iZza2, zzirVar);
            zzkeVar.zzh(zzirVar.zza);
        }
        return iZza;
    }

    static int zzl(byte[] bArr, int i, zzkk<?> zzkkVar, zzir zzirVar) throws IOException {
        zzke zzkeVar = (zzke) zzkkVar;
        int iZza = zza(bArr, i, zzirVar);
        int i2 = zzirVar.zza + iZza;
        while (iZza < i2) {
            iZza = zza(bArr, iZza, zzirVar);
            zzkeVar.zzh(zzirVar.zza);
        }
        if (iZza == i2) {
            return iZza;
        }
        throw zzkn.zza();
    }

    static int zzm(zzlt<?> zzltVar, int i, byte[] bArr, int i2, int i3, zzkk<?> zzkkVar, zzir zzirVar) throws IOException {
        int iZzi = zzi(zzltVar, bArr, i2, i3, zzirVar);
        zzkkVar.add(zzirVar.zzc);
        while (iZzi < i3) {
            int iZza = zza(bArr, iZzi, zzirVar);
            if (i != zzirVar.zza) {
                break;
            }
            iZzi = zzi(zzltVar, bArr, iZza, i3, zzirVar);
            zzkkVar.add(zzirVar.zzc);
        }
        return iZzi;
    }

    static int zzn(int i, byte[] bArr, int i2, int i3, zzmi zzmiVar, zzir zzirVar) throws zzkn {
        if ((i >>> 3) == 0) {
            throw zzkn.zzc();
        }
        int i4 = i & 7;
        if (i4 == 0) {
            int iZzc = zzc(bArr, i2, zzirVar);
            zzmiVar.zzh(i, Long.valueOf(zzirVar.zzb));
            return iZzc;
        }
        if (i4 == 1) {
            zzmiVar.zzh(i, Long.valueOf(zze(bArr, i2)));
            return i2 + 8;
        }
        if (i4 == 2) {
            int iZza = zza(bArr, i2, zzirVar);
            int i5 = zzirVar.zza;
            if (i5 < 0) {
                throw zzkn.zzb();
            }
            if (i5 > bArr.length - iZza) {
                throw zzkn.zza();
            }
            if (i5 == 0) {
                zzmiVar.zzh(i, zzjd.zzb);
            } else {
                zzmiVar.zzh(i, zzjd.zzj(bArr, iZza, i5));
            }
            return iZza + i5;
        }
        if (i4 != 3) {
            if (i4 != 5) {
                throw zzkn.zzc();
            }
            zzmiVar.zzh(i, Integer.valueOf(zzd(bArr, i2)));
            return i2 + 4;
        }
        int i6 = (i & (-8)) | 4;
        zzmi zzmiVarZzb = zzmi.zzb();
        int i7 = 0;
        while (true) {
            if (i2 >= i3) {
                break;
            }
            int iZza2 = zza(bArr, i2, zzirVar);
            int i8 = zzirVar.zza;
            if (i8 == i6) {
                i7 = i8;
                i2 = iZza2;
                break;
            }
            i7 = i8;
            i2 = zzn(i8, bArr, iZza2, i3, zzmiVarZzb, zzirVar);
        }
        if (i2 > i3 || i7 != i6) {
            throw zzkn.zze();
        }
        zzmiVar.zzh(i, zzmiVarZzb);
        return i2;
    }
}
