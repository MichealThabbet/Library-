package com.google.android.gms.internal.measurement;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import sun.misc.Unsafe;

/* compiled from: com.google.android.gms:play-services-measurement-base@@19.0.0 */
/* loaded from: classes.dex */
final class zzll<T> implements zzlt<T> {
    private static final int[] zza = new int[0];
    private static final Unsafe zzb = zzmr.zzq();
    private final int[] zzc;
    private final Object[] zzd;
    private final int zze;
    private final int zzf;
    private final zzli zzg;
    private final boolean zzh;
    private final boolean zzi;
    private final int[] zzj;
    private final int zzk;
    private final int zzl;
    private final zzkw zzm;
    private final zzmh<?, ?> zzn;
    private final zzjq<?> zzo;
    private final zzln zzp;
    private final zzld zzq;

    /* JADX WARN: Multi-variable type inference failed */
    private zzll(int[] iArr, int[] iArr2, Object[] objArr, int i, int i2, zzli zzliVar, boolean z, boolean z2, int[] iArr3, int i3, int i4, zzln zzlnVar, zzkw zzkwVar, zzmh<?, ?> zzmhVar, zzjq<?> zzjqVar, zzld zzldVar) {
        this.zzc = iArr;
        this.zzd = iArr2;
        this.zze = objArr;
        this.zzf = i;
        this.zzi = zzliVar;
        boolean z3 = false;
        if (zzmhVar != 0 && zzmhVar.zza(i2)) {
            z3 = true;
        }
        this.zzh = z3;
        this.zzj = z2;
        this.zzk = iArr3;
        this.zzl = i3;
        this.zzp = i4;
        this.zzm = zzlnVar;
        this.zzn = zzkwVar;
        this.zzo = zzmhVar;
        this.zzg = i2;
        this.zzq = zzjqVar;
    }

    private final int zzA(int i) {
        return this.zzc[i + 1];
    }

    private final int zzB(int i) {
        return this.zzc[i + 2];
    }

    private static int zzC(int i) {
        return (i >>> 20) & 255;
    }

    private static <T> double zzD(T t, long j) {
        return ((Double) zzmr.zzn(t, j)).doubleValue();
    }

    private static <T> float zzE(T t, long j) {
        return ((Float) zzmr.zzn(t, j)).floatValue();
    }

    private static <T> int zzF(T t, long j) {
        return ((Integer) zzmr.zzn(t, j)).intValue();
    }

    private static <T> long zzG(T t, long j) {
        return ((Long) zzmr.zzn(t, j)).longValue();
    }

    private static <T> boolean zzH(T t, long j) {
        return ((Boolean) zzmr.zzn(t, j)).booleanValue();
    }

    private final boolean zzI(T t, T t2, int i) {
        return zzK(t, i) == zzK(t2, i);
    }

    private final boolean zzJ(T t, int i, int i2, int i3, int i4) {
        return i2 == 1048575 ? zzK(t, i) : (i3 & i4) != 0;
    }

    private final boolean zzK(T t, int i) {
        int iZzB = zzB(i);
        long j = iZzB & 1048575;
        if (j != 1048575) {
            return (zzmr.zzd(t, j) & (1 << (iZzB >>> 20))) != 0;
        }
        int iZzA = zzA(i);
        long j2 = iZzA & 1048575;
        switch (zzC(iZzA)) {
            case 0:
                return zzmr.zzl(t, j2) != 0.0d;
            case 1:
                return zzmr.zzj(t, j2) != 0.0f;
            case 2:
                return zzmr.zzf(t, j2) != 0;
            case 3:
                return zzmr.zzf(t, j2) != 0;
            case 4:
                return zzmr.zzd(t, j2) != 0;
            case 5:
                return zzmr.zzf(t, j2) != 0;
            case 6:
                return zzmr.zzd(t, j2) != 0;
            case 7:
                return zzmr.zzh(t, j2);
            case 8:
                Object objZzn = zzmr.zzn(t, j2);
                if (objZzn instanceof String) {
                    return !((String) objZzn).isEmpty();
                }
                if (objZzn instanceof zzjd) {
                    return !zzjd.zzb.equals(objZzn);
                }
                throw new IllegalArgumentException();
            case 9:
                return zzmr.zzn(t, j2) != null;
            case 10:
                return !zzjd.zzb.equals(zzmr.zzn(t, j2));
            case 11:
                return zzmr.zzd(t, j2) != 0;
            case 12:
                return zzmr.zzd(t, j2) != 0;
            case 13:
                return zzmr.zzd(t, j2) != 0;
            case 14:
                return zzmr.zzf(t, j2) != 0;
            case 15:
                return zzmr.zzd(t, j2) != 0;
            case 16:
                return zzmr.zzf(t, j2) != 0;
            case 17:
                return zzmr.zzn(t, j2) != null;
            default:
                throw new IllegalArgumentException();
        }
    }

    private final void zzL(T t, int i) {
        int iZzB = zzB(i);
        long j = 1048575 & iZzB;
        if (j == 1048575) {
            return;
        }
        zzmr.zze(t, j, (1 << (iZzB >>> 20)) | zzmr.zzd(t, j));
    }

    private final boolean zzM(T t, int i, int i2) {
        return zzmr.zzd(t, (long) (zzB(i2) & 1048575)) == i;
    }

    private final void zzN(T t, int i, int i2) {
        zzmr.zze(t, zzB(i2) & 1048575, i);
    }

    private final int zzO(int i) {
        if (i < this.zze || i > this.zzf) {
            return -1;
        }
        return zzQ(i, 0);
    }

    private final int zzP(int i, int i2) {
        if (i < this.zze || i > this.zzf) {
            return -1;
        }
        return zzQ(i, i2);
    }

    private final int zzQ(int i, int i2) {
        int length = (this.zzc.length / 3) - 1;
        while (i2 <= length) {
            int i3 = (length + i2) >>> 1;
            int i4 = i3 * 3;
            int i5 = this.zzc[i4];
            if (i == i5) {
                return i4;
            }
            if (i < i5) {
                length = i3 - 1;
            } else {
                i2 = i3 + 1;
            }
        }
        return -1;
    }

    private final void zzR(T t, zzjl zzjlVar) throws IOException {
        int i;
        if (this.zzh) {
            this.zzo.zzb(t);
            throw null;
        }
        int length = this.zzc.length;
        Unsafe unsafe = zzb;
        int i2 = 1048575;
        int i3 = 0;
        int i4 = 0;
        int i5 = 1048575;
        while (i3 < length) {
            int iZzA = zzA(i3);
            int i6 = this.zzc[i3];
            int iZzC = zzC(iZzA);
            if (iZzC <= 17) {
                int i7 = this.zzc[i3 + 2];
                int i8 = i7 & i2;
                if (i8 != i5) {
                    i4 = unsafe.getInt(t, i8);
                    i5 = i8;
                }
                i = 1 << (i7 >>> 20);
            } else {
                i = 0;
            }
            long j = iZzA & i2;
            switch (iZzC) {
                case 0:
                    if ((i4 & i) != 0) {
                        zzjlVar.zzf(i6, zzmr.zzl(t, j));
                        continue;
                    }
                    i3 += 3;
                    i2 = 1048575;
                case 1:
                    if ((i4 & i) != 0) {
                        zzjlVar.zze(i6, zzmr.zzj(t, j));
                    } else {
                        continue;
                    }
                    i3 += 3;
                    i2 = 1048575;
                case 2:
                    if ((i4 & i) != 0) {
                        zzjlVar.zzc(i6, unsafe.getLong(t, j));
                    } else {
                        continue;
                    }
                    i3 += 3;
                    i2 = 1048575;
                case 3:
                    if ((i4 & i) != 0) {
                        zzjlVar.zzh(i6, unsafe.getLong(t, j));
                    } else {
                        continue;
                    }
                    i3 += 3;
                    i2 = 1048575;
                case 4:
                    if ((i4 & i) != 0) {
                        zzjlVar.zzi(i6, unsafe.getInt(t, j));
                    } else {
                        continue;
                    }
                    i3 += 3;
                    i2 = 1048575;
                case 5:
                    if ((i4 & i) != 0) {
                        zzjlVar.zzj(i6, unsafe.getLong(t, j));
                    } else {
                        continue;
                    }
                    i3 += 3;
                    i2 = 1048575;
                case 6:
                    if ((i4 & i) != 0) {
                        zzjlVar.zzk(i6, unsafe.getInt(t, j));
                    } else {
                        continue;
                    }
                    i3 += 3;
                    i2 = 1048575;
                case 7:
                    if ((i4 & i) != 0) {
                        zzjlVar.zzl(i6, zzmr.zzh(t, j));
                    } else {
                        continue;
                    }
                    i3 += 3;
                    i2 = 1048575;
                case 8:
                    if ((i4 & i) != 0) {
                        zzT(i6, unsafe.getObject(t, j), zzjlVar);
                    } else {
                        continue;
                    }
                    i3 += 3;
                    i2 = 1048575;
                case 9:
                    if ((i4 & i) != 0) {
                        zzjlVar.zzr(i6, unsafe.getObject(t, j), zzv(i3));
                    } else {
                        continue;
                    }
                    i3 += 3;
                    i2 = 1048575;
                case 10:
                    if ((i4 & i) != 0) {
                        zzjlVar.zzn(i6, (zzjd) unsafe.getObject(t, j));
                    } else {
                        continue;
                    }
                    i3 += 3;
                    i2 = 1048575;
                case 11:
                    if ((i4 & i) != 0) {
                        zzjlVar.zzo(i6, unsafe.getInt(t, j));
                    } else {
                        continue;
                    }
                    i3 += 3;
                    i2 = 1048575;
                case 12:
                    if ((i4 & i) != 0) {
                        zzjlVar.zzg(i6, unsafe.getInt(t, j));
                    } else {
                        continue;
                    }
                    i3 += 3;
                    i2 = 1048575;
                case 13:
                    if ((i4 & i) != 0) {
                        zzjlVar.zzb(i6, unsafe.getInt(t, j));
                    } else {
                        continue;
                    }
                    i3 += 3;
                    i2 = 1048575;
                case 14:
                    if ((i4 & i) != 0) {
                        zzjlVar.zzd(i6, unsafe.getLong(t, j));
                    } else {
                        continue;
                    }
                    i3 += 3;
                    i2 = 1048575;
                case 15:
                    if ((i4 & i) != 0) {
                        zzjlVar.zzp(i6, unsafe.getInt(t, j));
                    } else {
                        continue;
                    }
                    i3 += 3;
                    i2 = 1048575;
                case 16:
                    if ((i4 & i) != 0) {
                        zzjlVar.zzq(i6, unsafe.getLong(t, j));
                    } else {
                        continue;
                    }
                    i3 += 3;
                    i2 = 1048575;
                case 17:
                    if ((i4 & i) != 0) {
                        zzjlVar.zzs(i6, unsafe.getObject(t, j), zzv(i3));
                    } else {
                        continue;
                    }
                    i3 += 3;
                    i2 = 1048575;
                case 18:
                    zzlv.zzJ(this.zzc[i3], (List) unsafe.getObject(t, j), zzjlVar, false);
                    continue;
                    i3 += 3;
                    i2 = 1048575;
                case 19:
                    zzlv.zzK(this.zzc[i3], (List) unsafe.getObject(t, j), zzjlVar, false);
                    continue;
                    i3 += 3;
                    i2 = 1048575;
                case 20:
                    zzlv.zzL(this.zzc[i3], (List) unsafe.getObject(t, j), zzjlVar, false);
                    continue;
                    i3 += 3;
                    i2 = 1048575;
                case 21:
                    zzlv.zzM(this.zzc[i3], (List) unsafe.getObject(t, j), zzjlVar, false);
                    continue;
                    i3 += 3;
                    i2 = 1048575;
                case 22:
                    zzlv.zzQ(this.zzc[i3], (List) unsafe.getObject(t, j), zzjlVar, false);
                    continue;
                    i3 += 3;
                    i2 = 1048575;
                case 23:
                    zzlv.zzO(this.zzc[i3], (List) unsafe.getObject(t, j), zzjlVar, false);
                    continue;
                    i3 += 3;
                    i2 = 1048575;
                case 24:
                    zzlv.zzT(this.zzc[i3], (List) unsafe.getObject(t, j), zzjlVar, false);
                    continue;
                    i3 += 3;
                    i2 = 1048575;
                case 25:
                    zzlv.zzW(this.zzc[i3], (List) unsafe.getObject(t, j), zzjlVar, false);
                    continue;
                    i3 += 3;
                    i2 = 1048575;
                case 26:
                    zzlv.zzX(this.zzc[i3], (List) unsafe.getObject(t, j), zzjlVar);
                    break;
                case 27:
                    zzlv.zzZ(this.zzc[i3], (List) unsafe.getObject(t, j), zzjlVar, zzv(i3));
                    break;
                case 28:
                    zzlv.zzY(this.zzc[i3], (List) unsafe.getObject(t, j), zzjlVar);
                    break;
                case 29:
                    zzlv.zzR(this.zzc[i3], (List) unsafe.getObject(t, j), zzjlVar, false);
                    break;
                case 30:
                    zzlv.zzV(this.zzc[i3], (List) unsafe.getObject(t, j), zzjlVar, false);
                    break;
                case 31:
                    zzlv.zzU(this.zzc[i3], (List) unsafe.getObject(t, j), zzjlVar, false);
                    break;
                case 32:
                    zzlv.zzP(this.zzc[i3], (List) unsafe.getObject(t, j), zzjlVar, false);
                    break;
                case 33:
                    zzlv.zzS(this.zzc[i3], (List) unsafe.getObject(t, j), zzjlVar, false);
                    break;
                case 34:
                    zzlv.zzN(this.zzc[i3], (List) unsafe.getObject(t, j), zzjlVar, false);
                    break;
                case 35:
                    zzlv.zzJ(this.zzc[i3], (List) unsafe.getObject(t, j), zzjlVar, true);
                    break;
                case 36:
                    zzlv.zzK(this.zzc[i3], (List) unsafe.getObject(t, j), zzjlVar, true);
                    break;
                case 37:
                    zzlv.zzL(this.zzc[i3], (List) unsafe.getObject(t, j), zzjlVar, true);
                    break;
                case 38:
                    zzlv.zzM(this.zzc[i3], (List) unsafe.getObject(t, j), zzjlVar, true);
                    break;
                case 39:
                    zzlv.zzQ(this.zzc[i3], (List) unsafe.getObject(t, j), zzjlVar, true);
                    break;
                case 40:
                    zzlv.zzO(this.zzc[i3], (List) unsafe.getObject(t, j), zzjlVar, true);
                    break;
                case 41:
                    zzlv.zzT(this.zzc[i3], (List) unsafe.getObject(t, j), zzjlVar, true);
                    break;
                case 42:
                    zzlv.zzW(this.zzc[i3], (List) unsafe.getObject(t, j), zzjlVar, true);
                    break;
                case 43:
                    zzlv.zzR(this.zzc[i3], (List) unsafe.getObject(t, j), zzjlVar, true);
                    break;
                case 44:
                    zzlv.zzV(this.zzc[i3], (List) unsafe.getObject(t, j), zzjlVar, true);
                    break;
                case 45:
                    zzlv.zzU(this.zzc[i3], (List) unsafe.getObject(t, j), zzjlVar, true);
                    break;
                case 46:
                    zzlv.zzP(this.zzc[i3], (List) unsafe.getObject(t, j), zzjlVar, true);
                    break;
                case 47:
                    zzlv.zzS(this.zzc[i3], (List) unsafe.getObject(t, j), zzjlVar, true);
                    break;
                case 48:
                    zzlv.zzN(this.zzc[i3], (List) unsafe.getObject(t, j), zzjlVar, true);
                    break;
                case 49:
                    zzlv.zzaa(this.zzc[i3], (List) unsafe.getObject(t, j), zzjlVar, zzv(i3));
                    break;
                case 50:
                    zzS(zzjlVar, i6, unsafe.getObject(t, j), i3);
                    break;
                case 51:
                    if (zzM(t, i6, i3)) {
                        zzjlVar.zzf(i6, zzD(t, j));
                        break;
                    }
                    break;
                case 52:
                    if (zzM(t, i6, i3)) {
                        zzjlVar.zze(i6, zzE(t, j));
                        break;
                    }
                    break;
                case 53:
                    if (zzM(t, i6, i3)) {
                        zzjlVar.zzc(i6, zzG(t, j));
                        break;
                    }
                    break;
                case 54:
                    if (zzM(t, i6, i3)) {
                        zzjlVar.zzh(i6, zzG(t, j));
                        break;
                    }
                    break;
                case 55:
                    if (zzM(t, i6, i3)) {
                        zzjlVar.zzi(i6, zzF(t, j));
                        break;
                    }
                    break;
                case 56:
                    if (zzM(t, i6, i3)) {
                        zzjlVar.zzj(i6, zzG(t, j));
                        break;
                    }
                    break;
                case 57:
                    if (zzM(t, i6, i3)) {
                        zzjlVar.zzk(i6, zzF(t, j));
                        break;
                    }
                    break;
                case 58:
                    if (zzM(t, i6, i3)) {
                        zzjlVar.zzl(i6, zzH(t, j));
                        break;
                    }
                    break;
                case 59:
                    if (zzM(t, i6, i3)) {
                        zzT(i6, unsafe.getObject(t, j), zzjlVar);
                        break;
                    }
                    break;
                case 60:
                    if (zzM(t, i6, i3)) {
                        zzjlVar.zzr(i6, unsafe.getObject(t, j), zzv(i3));
                        break;
                    }
                    break;
                case 61:
                    if (zzM(t, i6, i3)) {
                        zzjlVar.zzn(i6, (zzjd) unsafe.getObject(t, j));
                        break;
                    }
                    break;
                case 62:
                    if (zzM(t, i6, i3)) {
                        zzjlVar.zzo(i6, zzF(t, j));
                        break;
                    }
                    break;
                case 63:
                    if (zzM(t, i6, i3)) {
                        zzjlVar.zzg(i6, zzF(t, j));
                        break;
                    }
                    break;
                case 64:
                    if (zzM(t, i6, i3)) {
                        zzjlVar.zzb(i6, zzF(t, j));
                        break;
                    }
                    break;
                case 65:
                    if (zzM(t, i6, i3)) {
                        zzjlVar.zzd(i6, zzG(t, j));
                        break;
                    }
                    break;
                case 66:
                    if (zzM(t, i6, i3)) {
                        zzjlVar.zzp(i6, zzF(t, j));
                        break;
                    }
                    break;
                case 67:
                    if (zzM(t, i6, i3)) {
                        zzjlVar.zzq(i6, zzG(t, j));
                        break;
                    }
                    break;
                case 68:
                    if (zzM(t, i6, i3)) {
                        zzjlVar.zzs(i6, unsafe.getObject(t, j), zzv(i3));
                        break;
                    }
                    break;
            }
            i3 += 3;
            i2 = 1048575;
        }
        zzmh<?, ?> zzmhVar = this.zzn;
        zzmhVar.zzi(zzmhVar.zzd(t), zzjlVar);
    }

    private final <K, V> void zzS(zzjl zzjlVar, int i, Object obj, int i2) throws IOException {
        if (obj == null) {
            return;
        }
        throw null;
    }

    private static final void zzT(int i, Object obj, zzjl zzjlVar) throws IOException {
        if (obj instanceof String) {
            zzjlVar.zzm(i, (String) obj);
        } else {
            zzjlVar.zzn(i, (zzjd) obj);
        }
    }

    static zzmi zzf(Object obj) {
        zzkd zzkdVar = (zzkd) obj;
        zzmi zzmiVar = zzkdVar.zzc;
        if (zzmiVar != zzmi.zza()) {
            return zzmiVar;
        }
        zzmi zzmiVarZzb = zzmi.zzb();
        zzkdVar.zzc = zzmiVarZzb;
        return zzmiVarZzb;
    }

    static <T> zzll<T> zzk(Class<T> cls, zzlf zzlfVar, zzln zzlnVar, zzkw zzkwVar, zzmh<?, ?> zzmhVar, zzjq<?> zzjqVar, zzld zzldVar) {
        if (zzlfVar instanceof zzls) {
            return zzl((zzls) zzlfVar, zzlnVar, zzkwVar, zzmhVar, zzjqVar, zzldVar);
        }
        throw null;
    }

    /* JADX WARN: Removed duplicated region for block: B:123:0x025d  */
    /* JADX WARN: Removed duplicated region for block: B:124:0x0260  */
    /* JADX WARN: Removed duplicated region for block: B:127:0x0278  */
    /* JADX WARN: Removed duplicated region for block: B:128:0x027b  */
    /* JADX WARN: Removed duplicated region for block: B:162:0x032b  */
    /* JADX WARN: Removed duplicated region for block: B:177:0x0378  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    static <T> com.google.android.gms.internal.measurement.zzll<T> zzl(com.google.android.gms.internal.measurement.zzls r34, com.google.android.gms.internal.measurement.zzln r35, com.google.android.gms.internal.measurement.zzkw r36, com.google.android.gms.internal.measurement.zzmh<?, ?> r37, com.google.android.gms.internal.measurement.zzjq<?> r38, com.google.android.gms.internal.measurement.zzld r39) {
        /*
            Method dump skipped, instructions count: 1016
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.measurement.zzll.zzl(com.google.android.gms.internal.measurement.zzls, com.google.android.gms.internal.measurement.zzln, com.google.android.gms.internal.measurement.zzkw, com.google.android.gms.internal.measurement.zzmh, com.google.android.gms.internal.measurement.zzjq, com.google.android.gms.internal.measurement.zzld):com.google.android.gms.internal.measurement.zzll");
    }

    private static Field zzn(Class<?> cls, String str) {
        try {
            return cls.getDeclaredField(str);
        } catch (NoSuchFieldException unused) {
            Field[] declaredFields = cls.getDeclaredFields();
            for (Field field : declaredFields) {
                if (str.equals(field.getName())) {
                    return field;
                }
            }
            String name = cls.getName();
            String string = Arrays.toString(declaredFields);
            StringBuilder sb = new StringBuilder(String.valueOf(str).length() + 40 + String.valueOf(name).length() + String.valueOf(string).length());
            sb.append("Field ");
            sb.append(str);
            sb.append(" for ");
            sb.append(name);
            sb.append(" not found. Known fields are ");
            sb.append(string);
            throw new RuntimeException(sb.toString());
        }
    }

    private final void zzo(T t, T t2, int i) {
        long jZzA = zzA(i) & 1048575;
        if (zzK(t2, i)) {
            Object objZzn = zzmr.zzn(t, jZzA);
            Object objZzn2 = zzmr.zzn(t2, jZzA);
            if (objZzn != null && objZzn2 != null) {
                zzmr.zzo(t, jZzA, zzkl.zzi(objZzn, objZzn2));
                zzL(t, i);
            } else if (objZzn2 != null) {
                zzmr.zzo(t, jZzA, objZzn2);
                zzL(t, i);
            }
        }
    }

    private final void zzp(T t, T t2, int i) {
        int iZzA = zzA(i);
        int i2 = this.zzc[i];
        long j = iZzA & 1048575;
        if (zzM(t2, i2, i)) {
            Object objZzn = zzM(t, i2, i) ? zzmr.zzn(t, j) : null;
            Object objZzn2 = zzmr.zzn(t2, j);
            if (objZzn != null && objZzn2 != null) {
                zzmr.zzo(t, j, zzkl.zzi(objZzn, objZzn2));
                zzN(t, i2, i);
            } else if (objZzn2 != null) {
                zzmr.zzo(t, j, objZzn2);
                zzN(t, i2, i);
            }
        }
    }

    private final int zzq(T t) {
        int i;
        int iZzw;
        int iZzw2;
        int iZzw3;
        int iZzx;
        int iZzw4;
        int iZzv;
        int iZzw5;
        int iZzw6;
        int iZzc;
        int iZzw7;
        int iZzw8;
        int iZzu;
        int iZzw9;
        int i2;
        Unsafe unsafe = zzb;
        int i3 = 0;
        int i4 = 0;
        int i5 = 1048575;
        for (int i6 = 0; i6 < this.zzc.length; i6 += 3) {
            int iZzA = zzA(i6);
            int i7 = this.zzc[i6];
            int iZzC = zzC(iZzA);
            if (iZzC <= 17) {
                int i8 = this.zzc[i6 + 2];
                int i9 = i8 & 1048575;
                i = 1 << (i8 >>> 20);
                if (i9 != i5) {
                    i4 = unsafe.getInt(t, i9);
                    i5 = i9;
                }
            } else {
                i = 0;
            }
            long j = iZzA & 1048575;
            switch (iZzC) {
                case 0:
                    if ((i4 & i) != 0) {
                        iZzw = zzjk.zzw(i7 << 3);
                        iZzw8 = iZzw + 8;
                        i3 += iZzw8;
                        break;
                    } else {
                        break;
                    }
                case 1:
                    if ((i4 & i) != 0) {
                        iZzw2 = zzjk.zzw(i7 << 3);
                        iZzw8 = iZzw2 + 4;
                        i3 += iZzw8;
                        break;
                    } else {
                        break;
                    }
                case 2:
                    if ((i4 & i) != 0) {
                        long j2 = unsafe.getLong(t, j);
                        iZzw3 = zzjk.zzw(i7 << 3);
                        iZzx = zzjk.zzx(j2);
                        iZzw8 = iZzw3 + iZzx;
                        i3 += iZzw8;
                        break;
                    } else {
                        break;
                    }
                case 3:
                    if ((i4 & i) != 0) {
                        long j3 = unsafe.getLong(t, j);
                        iZzw3 = zzjk.zzw(i7 << 3);
                        iZzx = zzjk.zzx(j3);
                        iZzw8 = iZzw3 + iZzx;
                        i3 += iZzw8;
                        break;
                    } else {
                        break;
                    }
                case 4:
                    if ((i4 & i) != 0) {
                        int i10 = unsafe.getInt(t, j);
                        iZzw4 = zzjk.zzw(i7 << 3);
                        iZzv = zzjk.zzv(i10);
                        i2 = iZzw4 + iZzv;
                        i3 += i2;
                        break;
                    } else {
                        break;
                    }
                case 5:
                    if ((i4 & i) != 0) {
                        iZzw = zzjk.zzw(i7 << 3);
                        iZzw8 = iZzw + 8;
                        i3 += iZzw8;
                        break;
                    } else {
                        break;
                    }
                case 6:
                    if ((i4 & i) != 0) {
                        iZzw2 = zzjk.zzw(i7 << 3);
                        iZzw8 = iZzw2 + 4;
                        i3 += iZzw8;
                        break;
                    } else {
                        break;
                    }
                case 7:
                    if ((i4 & i) != 0) {
                        iZzw5 = zzjk.zzw(i7 << 3);
                        iZzw8 = iZzw5 + 1;
                        i3 += iZzw8;
                        break;
                    } else {
                        break;
                    }
                case 8:
                    if ((i4 & i) == 0) {
                        break;
                    } else {
                        Object object = unsafe.getObject(t, j);
                        if (object instanceof zzjd) {
                            iZzw6 = zzjk.zzw(i7 << 3);
                            iZzc = ((zzjd) object).zzc();
                            iZzw7 = zzjk.zzw(iZzc);
                            i2 = iZzw6 + iZzw7 + iZzc;
                            i3 += i2;
                            break;
                        } else {
                            iZzw4 = zzjk.zzw(i7 << 3);
                            iZzv = zzjk.zzy((String) object);
                            i2 = iZzw4 + iZzv;
                            i3 += i2;
                        }
                    }
                case 9:
                    if ((i4 & i) != 0) {
                        iZzw8 = zzlv.zzw(i7, unsafe.getObject(t, j), zzv(i6));
                        i3 += iZzw8;
                        break;
                    } else {
                        break;
                    }
                case 10:
                    if ((i4 & i) != 0) {
                        zzjd zzjdVar = (zzjd) unsafe.getObject(t, j);
                        iZzw6 = zzjk.zzw(i7 << 3);
                        iZzc = zzjdVar.zzc();
                        iZzw7 = zzjk.zzw(iZzc);
                        i2 = iZzw6 + iZzw7 + iZzc;
                        i3 += i2;
                        break;
                    } else {
                        break;
                    }
                case 11:
                    if ((i4 & i) != 0) {
                        int i11 = unsafe.getInt(t, j);
                        iZzw4 = zzjk.zzw(i7 << 3);
                        iZzv = zzjk.zzw(i11);
                        i2 = iZzw4 + iZzv;
                        i3 += i2;
                        break;
                    } else {
                        break;
                    }
                case 12:
                    if ((i4 & i) != 0) {
                        int i12 = unsafe.getInt(t, j);
                        iZzw4 = zzjk.zzw(i7 << 3);
                        iZzv = zzjk.zzv(i12);
                        i2 = iZzw4 + iZzv;
                        i3 += i2;
                        break;
                    } else {
                        break;
                    }
                case 13:
                    if ((i4 & i) != 0) {
                        iZzw2 = zzjk.zzw(i7 << 3);
                        iZzw8 = iZzw2 + 4;
                        i3 += iZzw8;
                        break;
                    } else {
                        break;
                    }
                case 14:
                    if ((i4 & i) != 0) {
                        iZzw = zzjk.zzw(i7 << 3);
                        iZzw8 = iZzw + 8;
                        i3 += iZzw8;
                        break;
                    } else {
                        break;
                    }
                case 15:
                    if ((i4 & i) != 0) {
                        int i13 = unsafe.getInt(t, j);
                        iZzw4 = zzjk.zzw(i7 << 3);
                        iZzv = zzjk.zzw((i13 >> 31) ^ (i13 + i13));
                        i2 = iZzw4 + iZzv;
                        i3 += i2;
                        break;
                    } else {
                        break;
                    }
                case 16:
                    if ((i4 & i) != 0) {
                        long j4 = unsafe.getLong(t, j);
                        iZzw4 = zzjk.zzw(i7 << 3);
                        iZzv = zzjk.zzx((j4 >> 63) ^ (j4 + j4));
                        i2 = iZzw4 + iZzv;
                        i3 += i2;
                        break;
                    } else {
                        break;
                    }
                case 17:
                    if ((i4 & i) != 0) {
                        iZzw8 = zzjk.zzE(i7, (zzli) unsafe.getObject(t, j), zzv(i6));
                        i3 += iZzw8;
                        break;
                    } else {
                        break;
                    }
                case 18:
                    iZzw8 = zzlv.zzs(i7, (List) unsafe.getObject(t, j), false);
                    i3 += iZzw8;
                    break;
                case 19:
                    iZzw8 = zzlv.zzq(i7, (List) unsafe.getObject(t, j), false);
                    i3 += iZzw8;
                    break;
                case 20:
                    iZzw8 = zzlv.zzc(i7, (List) unsafe.getObject(t, j), false);
                    i3 += iZzw8;
                    break;
                case 21:
                    iZzw8 = zzlv.zze(i7, (List) unsafe.getObject(t, j), false);
                    i3 += iZzw8;
                    break;
                case 22:
                    iZzw8 = zzlv.zzk(i7, (List) unsafe.getObject(t, j), false);
                    i3 += iZzw8;
                    break;
                case 23:
                    iZzw8 = zzlv.zzs(i7, (List) unsafe.getObject(t, j), false);
                    i3 += iZzw8;
                    break;
                case 24:
                    iZzw8 = zzlv.zzq(i7, (List) unsafe.getObject(t, j), false);
                    i3 += iZzw8;
                    break;
                case 25:
                    iZzw8 = zzlv.zzu(i7, (List) unsafe.getObject(t, j), false);
                    i3 += iZzw8;
                    break;
                case 26:
                    iZzw8 = zzlv.zzv(i7, (List) unsafe.getObject(t, j));
                    i3 += iZzw8;
                    break;
                case 27:
                    iZzw8 = zzlv.zzx(i7, (List) unsafe.getObject(t, j), zzv(i6));
                    i3 += iZzw8;
                    break;
                case 28:
                    iZzw8 = zzlv.zzy(i7, (List) unsafe.getObject(t, j));
                    i3 += iZzw8;
                    break;
                case 29:
                    iZzw8 = zzlv.zzm(i7, (List) unsafe.getObject(t, j), false);
                    i3 += iZzw8;
                    break;
                case 30:
                    iZzw8 = zzlv.zzi(i7, (List) unsafe.getObject(t, j), false);
                    i3 += iZzw8;
                    break;
                case 31:
                    iZzw8 = zzlv.zzq(i7, (List) unsafe.getObject(t, j), false);
                    i3 += iZzw8;
                    break;
                case 32:
                    iZzw8 = zzlv.zzs(i7, (List) unsafe.getObject(t, j), false);
                    i3 += iZzw8;
                    break;
                case 33:
                    iZzw8 = zzlv.zzo(i7, (List) unsafe.getObject(t, j), false);
                    i3 += iZzw8;
                    break;
                case 34:
                    iZzw8 = zzlv.zzg(i7, (List) unsafe.getObject(t, j), false);
                    i3 += iZzw8;
                    break;
                case 35:
                    iZzv = zzlv.zzr((List) unsafe.getObject(t, j));
                    if (iZzv > 0) {
                        iZzu = zzjk.zzu(i7);
                        iZzw9 = zzjk.zzw(iZzv);
                        iZzw4 = iZzu + iZzw9;
                        i2 = iZzw4 + iZzv;
                        i3 += i2;
                        break;
                    } else {
                        break;
                    }
                case 36:
                    iZzv = zzlv.zzp((List) unsafe.getObject(t, j));
                    if (iZzv > 0) {
                        iZzu = zzjk.zzu(i7);
                        iZzw9 = zzjk.zzw(iZzv);
                        iZzw4 = iZzu + iZzw9;
                        i2 = iZzw4 + iZzv;
                        i3 += i2;
                        break;
                    } else {
                        break;
                    }
                case 37:
                    iZzv = zzlv.zzb((List) unsafe.getObject(t, j));
                    if (iZzv > 0) {
                        iZzu = zzjk.zzu(i7);
                        iZzw9 = zzjk.zzw(iZzv);
                        iZzw4 = iZzu + iZzw9;
                        i2 = iZzw4 + iZzv;
                        i3 += i2;
                        break;
                    } else {
                        break;
                    }
                case 38:
                    iZzv = zzlv.zzd((List) unsafe.getObject(t, j));
                    if (iZzv > 0) {
                        iZzu = zzjk.zzu(i7);
                        iZzw9 = zzjk.zzw(iZzv);
                        iZzw4 = iZzu + iZzw9;
                        i2 = iZzw4 + iZzv;
                        i3 += i2;
                        break;
                    } else {
                        break;
                    }
                case 39:
                    iZzv = zzlv.zzj((List) unsafe.getObject(t, j));
                    if (iZzv > 0) {
                        iZzu = zzjk.zzu(i7);
                        iZzw9 = zzjk.zzw(iZzv);
                        iZzw4 = iZzu + iZzw9;
                        i2 = iZzw4 + iZzv;
                        i3 += i2;
                        break;
                    } else {
                        break;
                    }
                case 40:
                    iZzv = zzlv.zzr((List) unsafe.getObject(t, j));
                    if (iZzv > 0) {
                        iZzu = zzjk.zzu(i7);
                        iZzw9 = zzjk.zzw(iZzv);
                        iZzw4 = iZzu + iZzw9;
                        i2 = iZzw4 + iZzv;
                        i3 += i2;
                        break;
                    } else {
                        break;
                    }
                case 41:
                    iZzv = zzlv.zzp((List) unsafe.getObject(t, j));
                    if (iZzv > 0) {
                        iZzu = zzjk.zzu(i7);
                        iZzw9 = zzjk.zzw(iZzv);
                        iZzw4 = iZzu + iZzw9;
                        i2 = iZzw4 + iZzv;
                        i3 += i2;
                        break;
                    } else {
                        break;
                    }
                case 42:
                    iZzv = zzlv.zzt((List) unsafe.getObject(t, j));
                    if (iZzv > 0) {
                        iZzu = zzjk.zzu(i7);
                        iZzw9 = zzjk.zzw(iZzv);
                        iZzw4 = iZzu + iZzw9;
                        i2 = iZzw4 + iZzv;
                        i3 += i2;
                        break;
                    } else {
                        break;
                    }
                case 43:
                    iZzv = zzlv.zzl((List) unsafe.getObject(t, j));
                    if (iZzv > 0) {
                        iZzu = zzjk.zzu(i7);
                        iZzw9 = zzjk.zzw(iZzv);
                        iZzw4 = iZzu + iZzw9;
                        i2 = iZzw4 + iZzv;
                        i3 += i2;
                        break;
                    } else {
                        break;
                    }
                case 44:
                    iZzv = zzlv.zzh((List) unsafe.getObject(t, j));
                    if (iZzv > 0) {
                        iZzu = zzjk.zzu(i7);
                        iZzw9 = zzjk.zzw(iZzv);
                        iZzw4 = iZzu + iZzw9;
                        i2 = iZzw4 + iZzv;
                        i3 += i2;
                        break;
                    } else {
                        break;
                    }
                case 45:
                    iZzv = zzlv.zzp((List) unsafe.getObject(t, j));
                    if (iZzv > 0) {
                        iZzu = zzjk.zzu(i7);
                        iZzw9 = zzjk.zzw(iZzv);
                        iZzw4 = iZzu + iZzw9;
                        i2 = iZzw4 + iZzv;
                        i3 += i2;
                        break;
                    } else {
                        break;
                    }
                case 46:
                    iZzv = zzlv.zzr((List) unsafe.getObject(t, j));
                    if (iZzv > 0) {
                        iZzu = zzjk.zzu(i7);
                        iZzw9 = zzjk.zzw(iZzv);
                        iZzw4 = iZzu + iZzw9;
                        i2 = iZzw4 + iZzv;
                        i3 += i2;
                        break;
                    } else {
                        break;
                    }
                case 47:
                    iZzv = zzlv.zzn((List) unsafe.getObject(t, j));
                    if (iZzv > 0) {
                        iZzu = zzjk.zzu(i7);
                        iZzw9 = zzjk.zzw(iZzv);
                        iZzw4 = iZzu + iZzw9;
                        i2 = iZzw4 + iZzv;
                        i3 += i2;
                        break;
                    } else {
                        break;
                    }
                case 48:
                    iZzv = zzlv.zzf((List) unsafe.getObject(t, j));
                    if (iZzv > 0) {
                        iZzu = zzjk.zzu(i7);
                        iZzw9 = zzjk.zzw(iZzv);
                        iZzw4 = iZzu + iZzw9;
                        i2 = iZzw4 + iZzv;
                        i3 += i2;
                        break;
                    } else {
                        break;
                    }
                case 49:
                    iZzw8 = zzlv.zzz(i7, (List) unsafe.getObject(t, j), zzv(i6));
                    i3 += iZzw8;
                    break;
                case 50:
                    zzld.zza(i7, unsafe.getObject(t, j), zzw(i6));
                    break;
                case 51:
                    if (zzM(t, i7, i6)) {
                        iZzw = zzjk.zzw(i7 << 3);
                        iZzw8 = iZzw + 8;
                        i3 += iZzw8;
                        break;
                    } else {
                        break;
                    }
                case 52:
                    if (zzM(t, i7, i6)) {
                        iZzw2 = zzjk.zzw(i7 << 3);
                        iZzw8 = iZzw2 + 4;
                        i3 += iZzw8;
                        break;
                    } else {
                        break;
                    }
                case 53:
                    if (zzM(t, i7, i6)) {
                        long jZzG = zzG(t, j);
                        iZzw3 = zzjk.zzw(i7 << 3);
                        iZzx = zzjk.zzx(jZzG);
                        iZzw8 = iZzw3 + iZzx;
                        i3 += iZzw8;
                        break;
                    } else {
                        break;
                    }
                case 54:
                    if (zzM(t, i7, i6)) {
                        long jZzG2 = zzG(t, j);
                        iZzw3 = zzjk.zzw(i7 << 3);
                        iZzx = zzjk.zzx(jZzG2);
                        iZzw8 = iZzw3 + iZzx;
                        i3 += iZzw8;
                        break;
                    } else {
                        break;
                    }
                case 55:
                    if (zzM(t, i7, i6)) {
                        int iZzF = zzF(t, j);
                        iZzw4 = zzjk.zzw(i7 << 3);
                        iZzv = zzjk.zzv(iZzF);
                        i2 = iZzw4 + iZzv;
                        i3 += i2;
                        break;
                    } else {
                        break;
                    }
                case 56:
                    if (zzM(t, i7, i6)) {
                        iZzw = zzjk.zzw(i7 << 3);
                        iZzw8 = iZzw + 8;
                        i3 += iZzw8;
                        break;
                    } else {
                        break;
                    }
                case 57:
                    if (zzM(t, i7, i6)) {
                        iZzw2 = zzjk.zzw(i7 << 3);
                        iZzw8 = iZzw2 + 4;
                        i3 += iZzw8;
                        break;
                    } else {
                        break;
                    }
                case 58:
                    if (zzM(t, i7, i6)) {
                        iZzw5 = zzjk.zzw(i7 << 3);
                        iZzw8 = iZzw5 + 1;
                        i3 += iZzw8;
                        break;
                    } else {
                        break;
                    }
                case 59:
                    if (!zzM(t, i7, i6)) {
                        break;
                    } else {
                        Object object2 = unsafe.getObject(t, j);
                        if (object2 instanceof zzjd) {
                            iZzw6 = zzjk.zzw(i7 << 3);
                            iZzc = ((zzjd) object2).zzc();
                            iZzw7 = zzjk.zzw(iZzc);
                            i2 = iZzw6 + iZzw7 + iZzc;
                            i3 += i2;
                            break;
                        } else {
                            iZzw4 = zzjk.zzw(i7 << 3);
                            iZzv = zzjk.zzy((String) object2);
                            i2 = iZzw4 + iZzv;
                            i3 += i2;
                        }
                    }
                case 60:
                    if (zzM(t, i7, i6)) {
                        iZzw8 = zzlv.zzw(i7, unsafe.getObject(t, j), zzv(i6));
                        i3 += iZzw8;
                        break;
                    } else {
                        break;
                    }
                case 61:
                    if (zzM(t, i7, i6)) {
                        zzjd zzjdVar2 = (zzjd) unsafe.getObject(t, j);
                        iZzw6 = zzjk.zzw(i7 << 3);
                        iZzc = zzjdVar2.zzc();
                        iZzw7 = zzjk.zzw(iZzc);
                        i2 = iZzw6 + iZzw7 + iZzc;
                        i3 += i2;
                        break;
                    } else {
                        break;
                    }
                case 62:
                    if (zzM(t, i7, i6)) {
                        int iZzF2 = zzF(t, j);
                        iZzw4 = zzjk.zzw(i7 << 3);
                        iZzv = zzjk.zzw(iZzF2);
                        i2 = iZzw4 + iZzv;
                        i3 += i2;
                        break;
                    } else {
                        break;
                    }
                case 63:
                    if (zzM(t, i7, i6)) {
                        int iZzF3 = zzF(t, j);
                        iZzw4 = zzjk.zzw(i7 << 3);
                        iZzv = zzjk.zzv(iZzF3);
                        i2 = iZzw4 + iZzv;
                        i3 += i2;
                        break;
                    } else {
                        break;
                    }
                case 64:
                    if (zzM(t, i7, i6)) {
                        iZzw2 = zzjk.zzw(i7 << 3);
                        iZzw8 = iZzw2 + 4;
                        i3 += iZzw8;
                        break;
                    } else {
                        break;
                    }
                case 65:
                    if (zzM(t, i7, i6)) {
                        iZzw = zzjk.zzw(i7 << 3);
                        iZzw8 = iZzw + 8;
                        i3 += iZzw8;
                        break;
                    } else {
                        break;
                    }
                case 66:
                    if (zzM(t, i7, i6)) {
                        int iZzF4 = zzF(t, j);
                        iZzw4 = zzjk.zzw(i7 << 3);
                        iZzv = zzjk.zzw((iZzF4 >> 31) ^ (iZzF4 + iZzF4));
                        i2 = iZzw4 + iZzv;
                        i3 += i2;
                        break;
                    } else {
                        break;
                    }
                case 67:
                    if (zzM(t, i7, i6)) {
                        long jZzG3 = zzG(t, j);
                        iZzw4 = zzjk.zzw(i7 << 3);
                        iZzv = zzjk.zzx((jZzG3 >> 63) ^ (jZzG3 + jZzG3));
                        i2 = iZzw4 + iZzv;
                        i3 += i2;
                        break;
                    } else {
                        break;
                    }
                case 68:
                    if (zzM(t, i7, i6)) {
                        iZzw8 = zzjk.zzE(i7, (zzli) unsafe.getObject(t, j), zzv(i6));
                        i3 += iZzw8;
                        break;
                    } else {
                        break;
                    }
            }
        }
        zzmh<?, ?> zzmhVar = this.zzn;
        int iZzh = i3 + zzmhVar.zzh(zzmhVar.zzd(t));
        if (!this.zzh) {
            return iZzh;
        }
        this.zzo.zzb(t);
        throw null;
    }

    private final int zzr(T t) {
        int iZzw;
        int iZzw2;
        int iZzw3;
        int iZzx;
        int iZzw4;
        int iZzv;
        int iZzw5;
        int iZzw6;
        int iZzc;
        int iZzw7;
        int iZzw8;
        int iZzu;
        int iZzw9;
        int i;
        Unsafe unsafe = zzb;
        int i2 = 0;
        for (int i3 = 0; i3 < this.zzc.length; i3 += 3) {
            int iZzA = zzA(i3);
            int iZzC = zzC(iZzA);
            int i4 = this.zzc[i3];
            long j = iZzA & 1048575;
            if (iZzC >= zzjv.DOUBLE_LIST_PACKED.zza() && iZzC <= zzjv.SINT64_LIST_PACKED.zza()) {
                int i5 = this.zzc[i3 + 2];
            }
            switch (iZzC) {
                case 0:
                    if (zzK(t, i3)) {
                        iZzw = zzjk.zzw(i4 << 3);
                        iZzw8 = iZzw + 8;
                        i2 += iZzw8;
                        break;
                    } else {
                        break;
                    }
                case 1:
                    if (zzK(t, i3)) {
                        iZzw2 = zzjk.zzw(i4 << 3);
                        iZzw8 = iZzw2 + 4;
                        i2 += iZzw8;
                        break;
                    } else {
                        break;
                    }
                case 2:
                    if (zzK(t, i3)) {
                        long jZzf = zzmr.zzf(t, j);
                        iZzw3 = zzjk.zzw(i4 << 3);
                        iZzx = zzjk.zzx(jZzf);
                        i2 += iZzw3 + iZzx;
                        break;
                    } else {
                        break;
                    }
                case 3:
                    if (zzK(t, i3)) {
                        long jZzf2 = zzmr.zzf(t, j);
                        iZzw3 = zzjk.zzw(i4 << 3);
                        iZzx = zzjk.zzx(jZzf2);
                        i2 += iZzw3 + iZzx;
                        break;
                    } else {
                        break;
                    }
                case 4:
                    if (zzK(t, i3)) {
                        int iZzd = zzmr.zzd(t, j);
                        iZzw4 = zzjk.zzw(i4 << 3);
                        iZzv = zzjk.zzv(iZzd);
                        i = iZzw4 + iZzv;
                        i2 += i;
                        break;
                    } else {
                        break;
                    }
                case 5:
                    if (zzK(t, i3)) {
                        iZzw = zzjk.zzw(i4 << 3);
                        iZzw8 = iZzw + 8;
                        i2 += iZzw8;
                        break;
                    } else {
                        break;
                    }
                case 6:
                    if (zzK(t, i3)) {
                        iZzw2 = zzjk.zzw(i4 << 3);
                        iZzw8 = iZzw2 + 4;
                        i2 += iZzw8;
                        break;
                    } else {
                        break;
                    }
                case 7:
                    if (zzK(t, i3)) {
                        iZzw5 = zzjk.zzw(i4 << 3);
                        iZzw8 = iZzw5 + 1;
                        i2 += iZzw8;
                        break;
                    } else {
                        break;
                    }
                case 8:
                    if (!zzK(t, i3)) {
                        break;
                    } else {
                        Object objZzn = zzmr.zzn(t, j);
                        if (objZzn instanceof zzjd) {
                            iZzw6 = zzjk.zzw(i4 << 3);
                            iZzc = ((zzjd) objZzn).zzc();
                            iZzw7 = zzjk.zzw(iZzc);
                            i = iZzw6 + iZzw7 + iZzc;
                            i2 += i;
                            break;
                        } else {
                            iZzw4 = zzjk.zzw(i4 << 3);
                            iZzv = zzjk.zzy((String) objZzn);
                            i = iZzw4 + iZzv;
                            i2 += i;
                        }
                    }
                case 9:
                    if (zzK(t, i3)) {
                        iZzw8 = zzlv.zzw(i4, zzmr.zzn(t, j), zzv(i3));
                        i2 += iZzw8;
                        break;
                    } else {
                        break;
                    }
                case 10:
                    if (zzK(t, i3)) {
                        zzjd zzjdVar = (zzjd) zzmr.zzn(t, j);
                        iZzw6 = zzjk.zzw(i4 << 3);
                        iZzc = zzjdVar.zzc();
                        iZzw7 = zzjk.zzw(iZzc);
                        i = iZzw6 + iZzw7 + iZzc;
                        i2 += i;
                        break;
                    } else {
                        break;
                    }
                case 11:
                    if (zzK(t, i3)) {
                        int iZzd2 = zzmr.zzd(t, j);
                        iZzw4 = zzjk.zzw(i4 << 3);
                        iZzv = zzjk.zzw(iZzd2);
                        i = iZzw4 + iZzv;
                        i2 += i;
                        break;
                    } else {
                        break;
                    }
                case 12:
                    if (zzK(t, i3)) {
                        int iZzd3 = zzmr.zzd(t, j);
                        iZzw4 = zzjk.zzw(i4 << 3);
                        iZzv = zzjk.zzv(iZzd3);
                        i = iZzw4 + iZzv;
                        i2 += i;
                        break;
                    } else {
                        break;
                    }
                case 13:
                    if (zzK(t, i3)) {
                        iZzw2 = zzjk.zzw(i4 << 3);
                        iZzw8 = iZzw2 + 4;
                        i2 += iZzw8;
                        break;
                    } else {
                        break;
                    }
                case 14:
                    if (zzK(t, i3)) {
                        iZzw = zzjk.zzw(i4 << 3);
                        iZzw8 = iZzw + 8;
                        i2 += iZzw8;
                        break;
                    } else {
                        break;
                    }
                case 15:
                    if (zzK(t, i3)) {
                        int iZzd4 = zzmr.zzd(t, j);
                        iZzw4 = zzjk.zzw(i4 << 3);
                        iZzv = zzjk.zzw((iZzd4 >> 31) ^ (iZzd4 + iZzd4));
                        i = iZzw4 + iZzv;
                        i2 += i;
                        break;
                    } else {
                        break;
                    }
                case 16:
                    if (zzK(t, i3)) {
                        long jZzf3 = zzmr.zzf(t, j);
                        iZzw4 = zzjk.zzw(i4 << 3);
                        iZzv = zzjk.zzx((jZzf3 >> 63) ^ (jZzf3 + jZzf3));
                        i = iZzw4 + iZzv;
                        i2 += i;
                        break;
                    } else {
                        break;
                    }
                case 17:
                    if (zzK(t, i3)) {
                        iZzw8 = zzjk.zzE(i4, (zzli) zzmr.zzn(t, j), zzv(i3));
                        i2 += iZzw8;
                        break;
                    } else {
                        break;
                    }
                case 18:
                    iZzw8 = zzlv.zzs(i4, (List) zzmr.zzn(t, j), false);
                    i2 += iZzw8;
                    break;
                case 19:
                    iZzw8 = zzlv.zzq(i4, (List) zzmr.zzn(t, j), false);
                    i2 += iZzw8;
                    break;
                case 20:
                    iZzw8 = zzlv.zzc(i4, (List) zzmr.zzn(t, j), false);
                    i2 += iZzw8;
                    break;
                case 21:
                    iZzw8 = zzlv.zze(i4, (List) zzmr.zzn(t, j), false);
                    i2 += iZzw8;
                    break;
                case 22:
                    iZzw8 = zzlv.zzk(i4, (List) zzmr.zzn(t, j), false);
                    i2 += iZzw8;
                    break;
                case 23:
                    iZzw8 = zzlv.zzs(i4, (List) zzmr.zzn(t, j), false);
                    i2 += iZzw8;
                    break;
                case 24:
                    iZzw8 = zzlv.zzq(i4, (List) zzmr.zzn(t, j), false);
                    i2 += iZzw8;
                    break;
                case 25:
                    iZzw8 = zzlv.zzu(i4, (List) zzmr.zzn(t, j), false);
                    i2 += iZzw8;
                    break;
                case 26:
                    iZzw8 = zzlv.zzv(i4, (List) zzmr.zzn(t, j));
                    i2 += iZzw8;
                    break;
                case 27:
                    iZzw8 = zzlv.zzx(i4, (List) zzmr.zzn(t, j), zzv(i3));
                    i2 += iZzw8;
                    break;
                case 28:
                    iZzw8 = zzlv.zzy(i4, (List) zzmr.zzn(t, j));
                    i2 += iZzw8;
                    break;
                case 29:
                    iZzw8 = zzlv.zzm(i4, (List) zzmr.zzn(t, j), false);
                    i2 += iZzw8;
                    break;
                case 30:
                    iZzw8 = zzlv.zzi(i4, (List) zzmr.zzn(t, j), false);
                    i2 += iZzw8;
                    break;
                case 31:
                    iZzw8 = zzlv.zzq(i4, (List) zzmr.zzn(t, j), false);
                    i2 += iZzw8;
                    break;
                case 32:
                    iZzw8 = zzlv.zzs(i4, (List) zzmr.zzn(t, j), false);
                    i2 += iZzw8;
                    break;
                case 33:
                    iZzw8 = zzlv.zzo(i4, (List) zzmr.zzn(t, j), false);
                    i2 += iZzw8;
                    break;
                case 34:
                    iZzw8 = zzlv.zzg(i4, (List) zzmr.zzn(t, j), false);
                    i2 += iZzw8;
                    break;
                case 35:
                    iZzv = zzlv.zzr((List) unsafe.getObject(t, j));
                    if (iZzv > 0) {
                        iZzu = zzjk.zzu(i4);
                        iZzw9 = zzjk.zzw(iZzv);
                        iZzw4 = iZzu + iZzw9;
                        i = iZzw4 + iZzv;
                        i2 += i;
                        break;
                    } else {
                        break;
                    }
                case 36:
                    iZzv = zzlv.zzp((List) unsafe.getObject(t, j));
                    if (iZzv > 0) {
                        iZzu = zzjk.zzu(i4);
                        iZzw9 = zzjk.zzw(iZzv);
                        iZzw4 = iZzu + iZzw9;
                        i = iZzw4 + iZzv;
                        i2 += i;
                        break;
                    } else {
                        break;
                    }
                case 37:
                    iZzv = zzlv.zzb((List) unsafe.getObject(t, j));
                    if (iZzv > 0) {
                        iZzu = zzjk.zzu(i4);
                        iZzw9 = zzjk.zzw(iZzv);
                        iZzw4 = iZzu + iZzw9;
                        i = iZzw4 + iZzv;
                        i2 += i;
                        break;
                    } else {
                        break;
                    }
                case 38:
                    iZzv = zzlv.zzd((List) unsafe.getObject(t, j));
                    if (iZzv > 0) {
                        iZzu = zzjk.zzu(i4);
                        iZzw9 = zzjk.zzw(iZzv);
                        iZzw4 = iZzu + iZzw9;
                        i = iZzw4 + iZzv;
                        i2 += i;
                        break;
                    } else {
                        break;
                    }
                case 39:
                    iZzv = zzlv.zzj((List) unsafe.getObject(t, j));
                    if (iZzv > 0) {
                        iZzu = zzjk.zzu(i4);
                        iZzw9 = zzjk.zzw(iZzv);
                        iZzw4 = iZzu + iZzw9;
                        i = iZzw4 + iZzv;
                        i2 += i;
                        break;
                    } else {
                        break;
                    }
                case 40:
                    iZzv = zzlv.zzr((List) unsafe.getObject(t, j));
                    if (iZzv > 0) {
                        iZzu = zzjk.zzu(i4);
                        iZzw9 = zzjk.zzw(iZzv);
                        iZzw4 = iZzu + iZzw9;
                        i = iZzw4 + iZzv;
                        i2 += i;
                        break;
                    } else {
                        break;
                    }
                case 41:
                    iZzv = zzlv.zzp((List) unsafe.getObject(t, j));
                    if (iZzv > 0) {
                        iZzu = zzjk.zzu(i4);
                        iZzw9 = zzjk.zzw(iZzv);
                        iZzw4 = iZzu + iZzw9;
                        i = iZzw4 + iZzv;
                        i2 += i;
                        break;
                    } else {
                        break;
                    }
                case 42:
                    iZzv = zzlv.zzt((List) unsafe.getObject(t, j));
                    if (iZzv > 0) {
                        iZzu = zzjk.zzu(i4);
                        iZzw9 = zzjk.zzw(iZzv);
                        iZzw4 = iZzu + iZzw9;
                        i = iZzw4 + iZzv;
                        i2 += i;
                        break;
                    } else {
                        break;
                    }
                case 43:
                    iZzv = zzlv.zzl((List) unsafe.getObject(t, j));
                    if (iZzv > 0) {
                        iZzu = zzjk.zzu(i4);
                        iZzw9 = zzjk.zzw(iZzv);
                        iZzw4 = iZzu + iZzw9;
                        i = iZzw4 + iZzv;
                        i2 += i;
                        break;
                    } else {
                        break;
                    }
                case 44:
                    iZzv = zzlv.zzh((List) unsafe.getObject(t, j));
                    if (iZzv > 0) {
                        iZzu = zzjk.zzu(i4);
                        iZzw9 = zzjk.zzw(iZzv);
                        iZzw4 = iZzu + iZzw9;
                        i = iZzw4 + iZzv;
                        i2 += i;
                        break;
                    } else {
                        break;
                    }
                case 45:
                    iZzv = zzlv.zzp((List) unsafe.getObject(t, j));
                    if (iZzv > 0) {
                        iZzu = zzjk.zzu(i4);
                        iZzw9 = zzjk.zzw(iZzv);
                        iZzw4 = iZzu + iZzw9;
                        i = iZzw4 + iZzv;
                        i2 += i;
                        break;
                    } else {
                        break;
                    }
                case 46:
                    iZzv = zzlv.zzr((List) unsafe.getObject(t, j));
                    if (iZzv > 0) {
                        iZzu = zzjk.zzu(i4);
                        iZzw9 = zzjk.zzw(iZzv);
                        iZzw4 = iZzu + iZzw9;
                        i = iZzw4 + iZzv;
                        i2 += i;
                        break;
                    } else {
                        break;
                    }
                case 47:
                    iZzv = zzlv.zzn((List) unsafe.getObject(t, j));
                    if (iZzv > 0) {
                        iZzu = zzjk.zzu(i4);
                        iZzw9 = zzjk.zzw(iZzv);
                        iZzw4 = iZzu + iZzw9;
                        i = iZzw4 + iZzv;
                        i2 += i;
                        break;
                    } else {
                        break;
                    }
                case 48:
                    iZzv = zzlv.zzf((List) unsafe.getObject(t, j));
                    if (iZzv > 0) {
                        iZzu = zzjk.zzu(i4);
                        iZzw9 = zzjk.zzw(iZzv);
                        iZzw4 = iZzu + iZzw9;
                        i = iZzw4 + iZzv;
                        i2 += i;
                        break;
                    } else {
                        break;
                    }
                case 49:
                    iZzw8 = zzlv.zzz(i4, (List) zzmr.zzn(t, j), zzv(i3));
                    i2 += iZzw8;
                    break;
                case 50:
                    zzld.zza(i4, zzmr.zzn(t, j), zzw(i3));
                    break;
                case 51:
                    if (zzM(t, i4, i3)) {
                        iZzw = zzjk.zzw(i4 << 3);
                        iZzw8 = iZzw + 8;
                        i2 += iZzw8;
                        break;
                    } else {
                        break;
                    }
                case 52:
                    if (zzM(t, i4, i3)) {
                        iZzw2 = zzjk.zzw(i4 << 3);
                        iZzw8 = iZzw2 + 4;
                        i2 += iZzw8;
                        break;
                    } else {
                        break;
                    }
                case 53:
                    if (zzM(t, i4, i3)) {
                        long jZzG = zzG(t, j);
                        iZzw3 = zzjk.zzw(i4 << 3);
                        iZzx = zzjk.zzx(jZzG);
                        i2 += iZzw3 + iZzx;
                        break;
                    } else {
                        break;
                    }
                case 54:
                    if (zzM(t, i4, i3)) {
                        long jZzG2 = zzG(t, j);
                        iZzw3 = zzjk.zzw(i4 << 3);
                        iZzx = zzjk.zzx(jZzG2);
                        i2 += iZzw3 + iZzx;
                        break;
                    } else {
                        break;
                    }
                case 55:
                    if (zzM(t, i4, i3)) {
                        int iZzF = zzF(t, j);
                        iZzw4 = zzjk.zzw(i4 << 3);
                        iZzv = zzjk.zzv(iZzF);
                        i = iZzw4 + iZzv;
                        i2 += i;
                        break;
                    } else {
                        break;
                    }
                case 56:
                    if (zzM(t, i4, i3)) {
                        iZzw = zzjk.zzw(i4 << 3);
                        iZzw8 = iZzw + 8;
                        i2 += iZzw8;
                        break;
                    } else {
                        break;
                    }
                case 57:
                    if (zzM(t, i4, i3)) {
                        iZzw2 = zzjk.zzw(i4 << 3);
                        iZzw8 = iZzw2 + 4;
                        i2 += iZzw8;
                        break;
                    } else {
                        break;
                    }
                case 58:
                    if (zzM(t, i4, i3)) {
                        iZzw5 = zzjk.zzw(i4 << 3);
                        iZzw8 = iZzw5 + 1;
                        i2 += iZzw8;
                        break;
                    } else {
                        break;
                    }
                case 59:
                    if (!zzM(t, i4, i3)) {
                        break;
                    } else {
                        Object objZzn2 = zzmr.zzn(t, j);
                        if (objZzn2 instanceof zzjd) {
                            iZzw6 = zzjk.zzw(i4 << 3);
                            iZzc = ((zzjd) objZzn2).zzc();
                            iZzw7 = zzjk.zzw(iZzc);
                            i = iZzw6 + iZzw7 + iZzc;
                            i2 += i;
                            break;
                        } else {
                            iZzw4 = zzjk.zzw(i4 << 3);
                            iZzv = zzjk.zzy((String) objZzn2);
                            i = iZzw4 + iZzv;
                            i2 += i;
                        }
                    }
                case 60:
                    if (zzM(t, i4, i3)) {
                        iZzw8 = zzlv.zzw(i4, zzmr.zzn(t, j), zzv(i3));
                        i2 += iZzw8;
                        break;
                    } else {
                        break;
                    }
                case 61:
                    if (zzM(t, i4, i3)) {
                        zzjd zzjdVar2 = (zzjd) zzmr.zzn(t, j);
                        iZzw6 = zzjk.zzw(i4 << 3);
                        iZzc = zzjdVar2.zzc();
                        iZzw7 = zzjk.zzw(iZzc);
                        i = iZzw6 + iZzw7 + iZzc;
                        i2 += i;
                        break;
                    } else {
                        break;
                    }
                case 62:
                    if (zzM(t, i4, i3)) {
                        int iZzF2 = zzF(t, j);
                        iZzw4 = zzjk.zzw(i4 << 3);
                        iZzv = zzjk.zzw(iZzF2);
                        i = iZzw4 + iZzv;
                        i2 += i;
                        break;
                    } else {
                        break;
                    }
                case 63:
                    if (zzM(t, i4, i3)) {
                        int iZzF3 = zzF(t, j);
                        iZzw4 = zzjk.zzw(i4 << 3);
                        iZzv = zzjk.zzv(iZzF3);
                        i = iZzw4 + iZzv;
                        i2 += i;
                        break;
                    } else {
                        break;
                    }
                case 64:
                    if (zzM(t, i4, i3)) {
                        iZzw2 = zzjk.zzw(i4 << 3);
                        iZzw8 = iZzw2 + 4;
                        i2 += iZzw8;
                        break;
                    } else {
                        break;
                    }
                case 65:
                    if (zzM(t, i4, i3)) {
                        iZzw = zzjk.zzw(i4 << 3);
                        iZzw8 = iZzw + 8;
                        i2 += iZzw8;
                        break;
                    } else {
                        break;
                    }
                case 66:
                    if (zzM(t, i4, i3)) {
                        int iZzF4 = zzF(t, j);
                        iZzw4 = zzjk.zzw(i4 << 3);
                        iZzv = zzjk.zzw((iZzF4 >> 31) ^ (iZzF4 + iZzF4));
                        i = iZzw4 + iZzv;
                        i2 += i;
                        break;
                    } else {
                        break;
                    }
                case 67:
                    if (zzM(t, i4, i3)) {
                        long jZzG3 = zzG(t, j);
                        iZzw4 = zzjk.zzw(i4 << 3);
                        iZzv = zzjk.zzx((jZzG3 >> 63) ^ (jZzG3 + jZzG3));
                        i = iZzw4 + iZzv;
                        i2 += i;
                        break;
                    } else {
                        break;
                    }
                case 68:
                    if (zzM(t, i4, i3)) {
                        iZzw8 = zzjk.zzE(i4, (zzli) zzmr.zzn(t, j), zzv(i3));
                        i2 += iZzw8;
                        break;
                    } else {
                        break;
                    }
            }
        }
        zzmh<?, ?> zzmhVar = this.zzn;
        return i2 + zzmhVar.zzh(zzmhVar.zzd(t));
    }

    /* JADX WARN: Multi-variable type inference failed */
    private final int zzs(T t, byte[] bArr, int i, int i2, int i3, int i4, int i5, int i6, long j, int i7, long j2, zzir zzirVar) throws IOException {
        int i8;
        int i9;
        int i10;
        int i11;
        int iZza;
        int iZza2 = i;
        zzkk zzkkVarZze = (zzkk) zzb.getObject(t, j2);
        if (!zzkkVarZze.zza()) {
            int size = zzkkVarZze.size();
            zzkkVarZze = zzkkVarZze.zze(size == 0 ? 10 : size + size);
            zzb.putObject(t, j2, zzkkVarZze);
        }
        switch (i7) {
            case 18:
            case 35:
                if (i5 == 2) {
                    zzjm zzjmVar = (zzjm) zzkkVarZze;
                    int iZza3 = zzis.zza(bArr, iZza2, zzirVar);
                    int i12 = zzirVar.zza + iZza3;
                    while (iZza3 < i12) {
                        zzjmVar.zzd(Double.longBitsToDouble(zzis.zze(bArr, iZza3)));
                        iZza3 += 8;
                    }
                    if (iZza3 == i12) {
                        return iZza3;
                    }
                    throw zzkn.zza();
                }
                if (i5 == 1) {
                    zzjm zzjmVar2 = (zzjm) zzkkVarZze;
                    zzjmVar2.zzd(Double.longBitsToDouble(zzis.zze(bArr, i)));
                    while (true) {
                        i8 = iZza2 + 8;
                        if (i8 < i2) {
                            iZza2 = zzis.zza(bArr, i8, zzirVar);
                            if (i3 == zzirVar.zza) {
                                zzjmVar2.zzd(Double.longBitsToDouble(zzis.zze(bArr, iZza2)));
                            }
                        }
                    }
                    return i8;
                }
                return iZza2;
            case 19:
            case 36:
                if (i5 == 2) {
                    zzjw zzjwVar = (zzjw) zzkkVarZze;
                    int iZza4 = zzis.zza(bArr, iZza2, zzirVar);
                    int i13 = zzirVar.zza + iZza4;
                    while (iZza4 < i13) {
                        zzjwVar.zzd(Float.intBitsToFloat(zzis.zzd(bArr, iZza4)));
                        iZza4 += 4;
                    }
                    if (iZza4 == i13) {
                        return iZza4;
                    }
                    throw zzkn.zza();
                }
                if (i5 == 5) {
                    zzjw zzjwVar2 = (zzjw) zzkkVarZze;
                    zzjwVar2.zzd(Float.intBitsToFloat(zzis.zzd(bArr, i)));
                    while (true) {
                        i9 = iZza2 + 4;
                        if (i9 < i2) {
                            iZza2 = zzis.zza(bArr, i9, zzirVar);
                            if (i3 == zzirVar.zza) {
                                zzjwVar2.zzd(Float.intBitsToFloat(zzis.zzd(bArr, iZza2)));
                            }
                        }
                    }
                    return i9;
                }
                return iZza2;
            case 20:
            case 21:
            case 37:
            case 38:
                if (i5 == 2) {
                    zzkx zzkxVar = (zzkx) zzkkVarZze;
                    int iZza5 = zzis.zza(bArr, iZza2, zzirVar);
                    int i14 = zzirVar.zza + iZza5;
                    while (iZza5 < i14) {
                        iZza5 = zzis.zzc(bArr, iZza5, zzirVar);
                        zzkxVar.zzg(zzirVar.zzb);
                    }
                    if (iZza5 == i14) {
                        return iZza5;
                    }
                    throw zzkn.zza();
                }
                if (i5 == 0) {
                    zzkx zzkxVar2 = (zzkx) zzkkVarZze;
                    int iZzc = zzis.zzc(bArr, iZza2, zzirVar);
                    zzkxVar2.zzg(zzirVar.zzb);
                    while (iZzc < i2) {
                        int iZza6 = zzis.zza(bArr, iZzc, zzirVar);
                        if (i3 != zzirVar.zza) {
                            return iZzc;
                        }
                        iZzc = zzis.zzc(bArr, iZza6, zzirVar);
                        zzkxVar2.zzg(zzirVar.zzb);
                    }
                    return iZzc;
                }
                return iZza2;
            case 22:
            case 29:
            case 39:
            case 43:
                if (i5 == 2) {
                    return zzis.zzl(bArr, iZza2, zzkkVarZze, zzirVar);
                }
                if (i5 == 0) {
                    return zzis.zzk(i3, bArr, i, i2, zzkkVarZze, zzirVar);
                }
                return iZza2;
            case 23:
            case 32:
            case 40:
            case 46:
                if (i5 == 2) {
                    zzkx zzkxVar3 = (zzkx) zzkkVarZze;
                    int iZza7 = zzis.zza(bArr, iZza2, zzirVar);
                    int i15 = zzirVar.zza + iZza7;
                    while (iZza7 < i15) {
                        zzkxVar3.zzg(zzis.zze(bArr, iZza7));
                        iZza7 += 8;
                    }
                    if (iZza7 == i15) {
                        return iZza7;
                    }
                    throw zzkn.zza();
                }
                if (i5 == 1) {
                    zzkx zzkxVar4 = (zzkx) zzkkVarZze;
                    zzkxVar4.zzg(zzis.zze(bArr, i));
                    while (true) {
                        i10 = iZza2 + 8;
                        if (i10 < i2) {
                            iZza2 = zzis.zza(bArr, i10, zzirVar);
                            if (i3 == zzirVar.zza) {
                                zzkxVar4.zzg(zzis.zze(bArr, iZza2));
                            }
                        }
                    }
                    return i10;
                }
                return iZza2;
            case 24:
            case 31:
            case 41:
            case 45:
                if (i5 == 2) {
                    zzke zzkeVar = (zzke) zzkkVarZze;
                    int iZza8 = zzis.zza(bArr, iZza2, zzirVar);
                    int i16 = zzirVar.zza + iZza8;
                    while (iZza8 < i16) {
                        zzkeVar.zzh(zzis.zzd(bArr, iZza8));
                        iZza8 += 4;
                    }
                    if (iZza8 == i16) {
                        return iZza8;
                    }
                    throw zzkn.zza();
                }
                if (i5 == 5) {
                    zzke zzkeVar2 = (zzke) zzkkVarZze;
                    zzkeVar2.zzh(zzis.zzd(bArr, i));
                    while (true) {
                        i11 = iZza2 + 4;
                        if (i11 < i2) {
                            iZza2 = zzis.zza(bArr, i11, zzirVar);
                            if (i3 == zzirVar.zza) {
                                zzkeVar2.zzh(zzis.zzd(bArr, iZza2));
                            }
                        }
                    }
                    return i11;
                }
                return iZza2;
            case 25:
            case 42:
                if (i5 == 2) {
                    zzit zzitVar = (zzit) zzkkVarZze;
                    iZza = zzis.zza(bArr, iZza2, zzirVar);
                    int i17 = zzirVar.zza + iZza;
                    while (iZza < i17) {
                        iZza = zzis.zzc(bArr, iZza, zzirVar);
                        zzitVar.zzd(zzirVar.zzb != 0);
                    }
                    if (iZza != i17) {
                        throw zzkn.zza();
                    }
                    return iZza;
                }
                if (i5 == 0) {
                    zzit zzitVar2 = (zzit) zzkkVarZze;
                    int iZzc2 = zzis.zzc(bArr, iZza2, zzirVar);
                    zzitVar2.zzd(zzirVar.zzb != 0);
                    while (iZzc2 < i2) {
                        int iZza9 = zzis.zza(bArr, iZzc2, zzirVar);
                        if (i3 != zzirVar.zza) {
                            return iZzc2;
                        }
                        iZzc2 = zzis.zzc(bArr, iZza9, zzirVar);
                        zzitVar2.zzd(zzirVar.zzb != 0);
                    }
                    return iZzc2;
                }
                return iZza2;
            case 26:
                if (i5 == 2) {
                    if ((j & 536870912) == 0) {
                        iZza2 = zzis.zza(bArr, iZza2, zzirVar);
                        int i18 = zzirVar.zza;
                        if (i18 < 0) {
                            throw zzkn.zzb();
                        }
                        if (i18 == 0) {
                            zzkkVarZze.add("");
                        } else {
                            zzkkVarZze.add(new String(bArr, iZza2, i18, zzkl.zza));
                            iZza2 += i18;
                        }
                        while (iZza2 < i2) {
                            int iZza10 = zzis.zza(bArr, iZza2, zzirVar);
                            if (i3 == zzirVar.zza) {
                                iZza2 = zzis.zza(bArr, iZza10, zzirVar);
                                int i19 = zzirVar.zza;
                                if (i19 < 0) {
                                    throw zzkn.zzb();
                                }
                                if (i19 == 0) {
                                    zzkkVarZze.add("");
                                } else {
                                    zzkkVarZze.add(new String(bArr, iZza2, i19, zzkl.zza));
                                    iZza2 += i19;
                                }
                            }
                        }
                    } else {
                        iZza2 = zzis.zza(bArr, iZza2, zzirVar);
                        int i20 = zzirVar.zza;
                        if (i20 < 0) {
                            throw zzkn.zzb();
                        }
                        if (i20 == 0) {
                            zzkkVarZze.add("");
                        } else {
                            int i21 = iZza2 + i20;
                            if (!zzmw.zzb(bArr, iZza2, i21)) {
                                throw zzkn.zzf();
                            }
                            zzkkVarZze.add(new String(bArr, iZza2, i20, zzkl.zza));
                            iZza2 = i21;
                        }
                        while (iZza2 < i2) {
                            int iZza11 = zzis.zza(bArr, iZza2, zzirVar);
                            if (i3 == zzirVar.zza) {
                                iZza2 = zzis.zza(bArr, iZza11, zzirVar);
                                int i22 = zzirVar.zza;
                                if (i22 < 0) {
                                    throw zzkn.zzb();
                                }
                                if (i22 == 0) {
                                    zzkkVarZze.add("");
                                } else {
                                    int i23 = iZza2 + i22;
                                    if (!zzmw.zzb(bArr, iZza2, i23)) {
                                        throw zzkn.zzf();
                                    }
                                    zzkkVarZze.add(new String(bArr, iZza2, i22, zzkl.zza));
                                    iZza2 = i23;
                                }
                            }
                        }
                    }
                }
                return iZza2;
            case 27:
                if (i5 == 2) {
                    return zzis.zzm(zzv(i6), i3, bArr, i, i2, zzkkVarZze, zzirVar);
                }
                return iZza2;
            case 28:
                if (i5 == 2) {
                    int iZza12 = zzis.zza(bArr, iZza2, zzirVar);
                    int i24 = zzirVar.zza;
                    if (i24 < 0) {
                        throw zzkn.zzb();
                    }
                    if (i24 > bArr.length - iZza12) {
                        throw zzkn.zza();
                    }
                    if (i24 == 0) {
                        zzkkVarZze.add(zzjd.zzb);
                    } else {
                        zzkkVarZze.add(zzjd.zzj(bArr, iZza12, i24));
                        iZza12 += i24;
                    }
                    while (iZza12 < i2) {
                        int iZza13 = zzis.zza(bArr, iZza12, zzirVar);
                        if (i3 != zzirVar.zza) {
                            return iZza12;
                        }
                        iZza12 = zzis.zza(bArr, iZza13, zzirVar);
                        int i25 = zzirVar.zza;
                        if (i25 < 0) {
                            throw zzkn.zzb();
                        }
                        if (i25 > bArr.length - iZza12) {
                            throw zzkn.zza();
                        }
                        if (i25 == 0) {
                            zzkkVarZze.add(zzjd.zzb);
                        } else {
                            zzkkVarZze.add(zzjd.zzj(bArr, iZza12, i25));
                            iZza12 += i25;
                        }
                    }
                    return iZza12;
                }
                return iZza2;
            case 30:
            case 44:
                if (i5 != 2) {
                    if (i5 == 0) {
                        iZza = zzis.zzk(i3, bArr, i, i2, zzkkVarZze, zzirVar);
                    }
                    return iZza2;
                }
                iZza = zzis.zzl(bArr, iZza2, zzkkVarZze, zzirVar);
                zzkd zzkdVar = (zzkd) t;
                zzmi zzmiVar = zzkdVar.zzc;
                if (zzmiVar == zzmi.zza()) {
                    zzmiVar = null;
                }
                Object objZzG = zzlv.zzG(i4, zzkkVarZze, zzx(i6), zzmiVar, this.zzn);
                if (objZzG != null) {
                    zzkdVar.zzc = (zzmi) objZzG;
                    return iZza;
                }
                return iZza;
            case 33:
            case 47:
                if (i5 == 2) {
                    zzke zzkeVar3 = (zzke) zzkkVarZze;
                    int iZza14 = zzis.zza(bArr, iZza2, zzirVar);
                    int i26 = zzirVar.zza + iZza14;
                    while (iZza14 < i26) {
                        iZza14 = zzis.zza(bArr, iZza14, zzirVar);
                        zzkeVar3.zzh(zzjg.zzb(zzirVar.zza));
                    }
                    if (iZza14 == i26) {
                        return iZza14;
                    }
                    throw zzkn.zza();
                }
                if (i5 == 0) {
                    zzke zzkeVar4 = (zzke) zzkkVarZze;
                    int iZza15 = zzis.zza(bArr, iZza2, zzirVar);
                    zzkeVar4.zzh(zzjg.zzb(zzirVar.zza));
                    while (iZza15 < i2) {
                        int iZza16 = zzis.zza(bArr, iZza15, zzirVar);
                        if (i3 != zzirVar.zza) {
                            return iZza15;
                        }
                        iZza15 = zzis.zza(bArr, iZza16, zzirVar);
                        zzkeVar4.zzh(zzjg.zzb(zzirVar.zza));
                    }
                    return iZza15;
                }
                return iZza2;
            case 34:
            case 48:
                if (i5 == 2) {
                    zzkx zzkxVar5 = (zzkx) zzkkVarZze;
                    int iZza17 = zzis.zza(bArr, iZza2, zzirVar);
                    int i27 = zzirVar.zza + iZza17;
                    while (iZza17 < i27) {
                        iZza17 = zzis.zzc(bArr, iZza17, zzirVar);
                        zzkxVar5.zzg(zzjg.zzc(zzirVar.zzb));
                    }
                    if (iZza17 == i27) {
                        return iZza17;
                    }
                    throw zzkn.zza();
                }
                if (i5 == 0) {
                    zzkx zzkxVar6 = (zzkx) zzkkVarZze;
                    int iZzc3 = zzis.zzc(bArr, iZza2, zzirVar);
                    zzkxVar6.zzg(zzjg.zzc(zzirVar.zzb));
                    while (iZzc3 < i2) {
                        int iZza18 = zzis.zza(bArr, iZzc3, zzirVar);
                        if (i3 != zzirVar.zza) {
                            return iZzc3;
                        }
                        iZzc3 = zzis.zzc(bArr, iZza18, zzirVar);
                        zzkxVar6.zzg(zzjg.zzc(zzirVar.zzb));
                    }
                    return iZzc3;
                }
                return iZza2;
            default:
                if (i5 == 3) {
                    zzlt zzltVarZzv = zzv(i6);
                    int i28 = (i3 & (-8)) | 4;
                    int iZzj = zzis.zzj(zzltVarZzv, bArr, i, i2, i28, zzirVar);
                    zzkkVarZze.add(zzirVar.zzc);
                    while (iZzj < i2) {
                        int iZza19 = zzis.zza(bArr, iZzj, zzirVar);
                        if (i3 != zzirVar.zza) {
                            return iZzj;
                        }
                        iZzj = zzis.zzj(zzltVarZzv, bArr, iZza19, i2, i28, zzirVar);
                        zzkkVarZze.add(zzirVar.zzc);
                    }
                    return iZzj;
                }
                return iZza2;
        }
    }

    private final <K, V> int zzt(T t, byte[] bArr, int i, int i2, int i3, long j, zzir zzirVar) throws IOException {
        Unsafe unsafe = zzb;
        Object objZzw = zzw(i3);
        Object object = unsafe.getObject(t, j);
        if (!((zzlc) object).zze()) {
            zzlc<K, V> zzlcVarZzc = zzlc.zza().zzc();
            zzld.zzb(zzlcVarZzc, object);
            unsafe.putObject(t, j, zzlcVarZzc);
        }
        throw null;
    }

    private final int zzu(T t, byte[] bArr, int i, int i2, int i3, int i4, int i5, int i6, int i7, long j, int i8, zzir zzirVar) throws IOException {
        Unsafe unsafe = zzb;
        long j2 = this.zzc[i8 + 2] & 1048575;
        switch (i7) {
            case 51:
                if (i5 == 1) {
                    unsafe.putObject(t, j, Double.valueOf(Double.longBitsToDouble(zzis.zze(bArr, i))));
                    unsafe.putInt(t, j2, i4);
                    return i + 8;
                }
                break;
            case 52:
                if (i5 == 5) {
                    unsafe.putObject(t, j, Float.valueOf(Float.intBitsToFloat(zzis.zzd(bArr, i))));
                    unsafe.putInt(t, j2, i4);
                    return i + 4;
                }
                break;
            case 53:
            case 54:
                if (i5 == 0) {
                    int iZzc = zzis.zzc(bArr, i, zzirVar);
                    unsafe.putObject(t, j, Long.valueOf(zzirVar.zzb));
                    unsafe.putInt(t, j2, i4);
                    return iZzc;
                }
                break;
            case 55:
            case 62:
                if (i5 == 0) {
                    int iZza = zzis.zza(bArr, i, zzirVar);
                    unsafe.putObject(t, j, Integer.valueOf(zzirVar.zza));
                    unsafe.putInt(t, j2, i4);
                    return iZza;
                }
                break;
            case 56:
            case 65:
                if (i5 == 1) {
                    unsafe.putObject(t, j, Long.valueOf(zzis.zze(bArr, i)));
                    unsafe.putInt(t, j2, i4);
                    return i + 8;
                }
                break;
            case 57:
            case 64:
                if (i5 == 5) {
                    unsafe.putObject(t, j, Integer.valueOf(zzis.zzd(bArr, i)));
                    unsafe.putInt(t, j2, i4);
                    return i + 4;
                }
                break;
            case 58:
                if (i5 == 0) {
                    int iZzc2 = zzis.zzc(bArr, i, zzirVar);
                    unsafe.putObject(t, j, Boolean.valueOf(zzirVar.zzb != 0));
                    unsafe.putInt(t, j2, i4);
                    return iZzc2;
                }
                break;
            case 59:
                if (i5 == 2) {
                    int iZza2 = zzis.zza(bArr, i, zzirVar);
                    int i9 = zzirVar.zza;
                    if (i9 == 0) {
                        unsafe.putObject(t, j, "");
                    } else {
                        if ((i6 & 536870912) != 0 && !zzmw.zzb(bArr, iZza2, iZza2 + i9)) {
                            throw zzkn.zzf();
                        }
                        unsafe.putObject(t, j, new String(bArr, iZza2, i9, zzkl.zza));
                        iZza2 += i9;
                    }
                    unsafe.putInt(t, j2, i4);
                    return iZza2;
                }
                break;
            case 60:
                if (i5 == 2) {
                    int iZzi = zzis.zzi(zzv(i8), bArr, i, i2, zzirVar);
                    Object object = unsafe.getInt(t, j2) == i4 ? unsafe.getObject(t, j) : null;
                    if (object == null) {
                        unsafe.putObject(t, j, zzirVar.zzc);
                    } else {
                        unsafe.putObject(t, j, zzkl.zzi(object, zzirVar.zzc));
                    }
                    unsafe.putInt(t, j2, i4);
                    return iZzi;
                }
                break;
            case 61:
                if (i5 == 2) {
                    int iZzh = zzis.zzh(bArr, i, zzirVar);
                    unsafe.putObject(t, j, zzirVar.zzc);
                    unsafe.putInt(t, j2, i4);
                    return iZzh;
                }
                break;
            case 63:
                if (i5 == 0) {
                    int iZza3 = zzis.zza(bArr, i, zzirVar);
                    int i10 = zzirVar.zza;
                    zzkh zzkhVarZzx = zzx(i8);
                    if (zzkhVarZzx == null || zzkhVarZzx.zza(i10)) {
                        unsafe.putObject(t, j, Integer.valueOf(i10));
                        unsafe.putInt(t, j2, i4);
                    } else {
                        zzf(t).zzh(i3, Long.valueOf(i10));
                    }
                    return iZza3;
                }
                break;
            case 66:
                if (i5 == 0) {
                    int iZza4 = zzis.zza(bArr, i, zzirVar);
                    unsafe.putObject(t, j, Integer.valueOf(zzjg.zzb(zzirVar.zza)));
                    unsafe.putInt(t, j2, i4);
                    return iZza4;
                }
                break;
            case 67:
                if (i5 == 0) {
                    int iZzc3 = zzis.zzc(bArr, i, zzirVar);
                    unsafe.putObject(t, j, Long.valueOf(zzjg.zzc(zzirVar.zzb)));
                    unsafe.putInt(t, j2, i4);
                    return iZzc3;
                }
                break;
            case 68:
                if (i5 == 3) {
                    int iZzj = zzis.zzj(zzv(i8), bArr, i, i2, (i3 & (-8)) | 4, zzirVar);
                    Object object2 = unsafe.getInt(t, j2) == i4 ? unsafe.getObject(t, j) : null;
                    if (object2 == null) {
                        unsafe.putObject(t, j, zzirVar.zzc);
                    } else {
                        unsafe.putObject(t, j, zzkl.zzi(object2, zzirVar.zzc));
                    }
                    unsafe.putInt(t, j2, i4);
                    return iZzj;
                }
                break;
        }
        return i;
    }

    private final zzlt zzv(int i) {
        int i2 = i / 3;
        int i3 = i2 + i2;
        zzlt zzltVar = (zzlt) this.zzd[i3];
        if (zzltVar != null) {
            return zzltVar;
        }
        zzlt<T> zzltVarZzb = zzlq.zza().zzb((Class) this.zzd[i3 + 1]);
        this.zzd[i3] = zzltVarZzb;
        return zzltVarZzb;
    }

    private final Object zzw(int i) {
        int i2 = i / 3;
        return this.zzd[i2 + i2];
    }

    private final zzkh zzx(int i) {
        int i2 = i / 3;
        return (zzkh) this.zzd[i2 + i2 + 1];
    }

    /* JADX WARN: Code restructure failed: missing block: B:104:0x02a8, code lost:
    
        if (r0 != r15) goto L105;
     */
    /* JADX WARN: Code restructure failed: missing block: B:105:0x02aa, code lost:
    
        r15 = r30;
        r14 = r31;
        r12 = r32;
        r13 = r34;
        r11 = r35;
        r2 = r19;
        r1 = r20;
        r6 = r24;
        r7 = r25;
     */
    /* JADX WARN: Code restructure failed: missing block: B:106:0x02be, code lost:
    
        r2 = r0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:112:0x02f1, code lost:
    
        if (r0 != r15) goto L105;
     */
    /* JADX WARN: Code restructure failed: missing block: B:117:0x0314, code lost:
    
        if (r0 != r15) goto L105;
     */
    /* JADX WARN: Failed to find 'out' block for switch in B:28:0x0097. Please report as an issue. */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r3v10, types: [int] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    private final int zzy(T r31, byte[] r32, int r33, int r34, com.google.android.gms.internal.measurement.zzir r35) throws java.io.IOException {
        /*
            Method dump skipped, instructions count: 896
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.measurement.zzll.zzy(java.lang.Object, byte[], int, int, com.google.android.gms.internal.measurement.zzir):int");
    }

    /* JADX WARN: Multi-variable type inference failed */
    private static boolean zzz(Object obj, int i, zzlt zzltVar) {
        return zzltVar.zzj(zzmr.zzn(obj, i & 1048575));
    }

    @Override // com.google.android.gms.internal.measurement.zzlt
    public final T zza() {
        return (T) ((zzkd) this.zzg).zzl(4, null, null);
    }

    @Override // com.google.android.gms.internal.measurement.zzlt
    public final boolean zzb(T t, T t2) {
        boolean zZzD;
        int length = this.zzc.length;
        for (int i = 0; i < length; i += 3) {
            int iZzA = zzA(i);
            long j = iZzA & 1048575;
            switch (zzC(iZzA)) {
                case 0:
                    if (!zzI(t, t2, i) || Double.doubleToLongBits(zzmr.zzl(t, j)) != Double.doubleToLongBits(zzmr.zzl(t2, j))) {
                        return false;
                    }
                    continue;
                    break;
                case 1:
                    if (!zzI(t, t2, i) || Float.floatToIntBits(zzmr.zzj(t, j)) != Float.floatToIntBits(zzmr.zzj(t2, j))) {
                        return false;
                    }
                    continue;
                    break;
                case 2:
                    if (!zzI(t, t2, i) || zzmr.zzf(t, j) != zzmr.zzf(t2, j)) {
                        return false;
                    }
                    continue;
                    break;
                case 3:
                    if (!zzI(t, t2, i) || zzmr.zzf(t, j) != zzmr.zzf(t2, j)) {
                        return false;
                    }
                    continue;
                    break;
                case 4:
                    if (!zzI(t, t2, i) || zzmr.zzd(t, j) != zzmr.zzd(t2, j)) {
                        return false;
                    }
                    continue;
                    break;
                case 5:
                    if (!zzI(t, t2, i) || zzmr.zzf(t, j) != zzmr.zzf(t2, j)) {
                        return false;
                    }
                    continue;
                    break;
                case 6:
                    if (!zzI(t, t2, i) || zzmr.zzd(t, j) != zzmr.zzd(t2, j)) {
                        return false;
                    }
                    continue;
                    break;
                case 7:
                    if (!zzI(t, t2, i) || zzmr.zzh(t, j) != zzmr.zzh(t2, j)) {
                        return false;
                    }
                    continue;
                    break;
                case 8:
                    if (!zzI(t, t2, i) || !zzlv.zzD(zzmr.zzn(t, j), zzmr.zzn(t2, j))) {
                        return false;
                    }
                    continue;
                    break;
                case 9:
                    if (!zzI(t, t2, i) || !zzlv.zzD(zzmr.zzn(t, j), zzmr.zzn(t2, j))) {
                        return false;
                    }
                    continue;
                    break;
                case 10:
                    if (!zzI(t, t2, i) || !zzlv.zzD(zzmr.zzn(t, j), zzmr.zzn(t2, j))) {
                        return false;
                    }
                    continue;
                    break;
                case 11:
                    if (!zzI(t, t2, i) || zzmr.zzd(t, j) != zzmr.zzd(t2, j)) {
                        return false;
                    }
                    continue;
                    break;
                case 12:
                    if (!zzI(t, t2, i) || zzmr.zzd(t, j) != zzmr.zzd(t2, j)) {
                        return false;
                    }
                    continue;
                    break;
                case 13:
                    if (!zzI(t, t2, i) || zzmr.zzd(t, j) != zzmr.zzd(t2, j)) {
                        return false;
                    }
                    continue;
                    break;
                case 14:
                    if (!zzI(t, t2, i) || zzmr.zzf(t, j) != zzmr.zzf(t2, j)) {
                        return false;
                    }
                    continue;
                    break;
                case 15:
                    if (!zzI(t, t2, i) || zzmr.zzd(t, j) != zzmr.zzd(t2, j)) {
                        return false;
                    }
                    continue;
                    break;
                case 16:
                    if (!zzI(t, t2, i) || zzmr.zzf(t, j) != zzmr.zzf(t2, j)) {
                        return false;
                    }
                    continue;
                    break;
                case 17:
                    if (!zzI(t, t2, i) || !zzlv.zzD(zzmr.zzn(t, j), zzmr.zzn(t2, j))) {
                        return false;
                    }
                    continue;
                    break;
                case 18:
                case 19:
                case 20:
                case 21:
                case 22:
                case 23:
                case 24:
                case 25:
                case 26:
                case 27:
                case 28:
                case 29:
                case 30:
                case 31:
                case 32:
                case 33:
                case 34:
                case 35:
                case 36:
                case 37:
                case 38:
                case 39:
                case 40:
                case 41:
                case 42:
                case 43:
                case 44:
                case 45:
                case 46:
                case 47:
                case 48:
                case 49:
                    zZzD = zzlv.zzD(zzmr.zzn(t, j), zzmr.zzn(t2, j));
                    break;
                case 50:
                    zZzD = zzlv.zzD(zzmr.zzn(t, j), zzmr.zzn(t2, j));
                    break;
                case 51:
                case 52:
                case 53:
                case 54:
                case 55:
                case 56:
                case 57:
                case 58:
                case 59:
                case 60:
                case 61:
                case 62:
                case 63:
                case 64:
                case 65:
                case 66:
                case 67:
                case 68:
                    long jZzB = zzB(i) & 1048575;
                    if (zzmr.zzd(t, jZzB) != zzmr.zzd(t2, jZzB) || !zzlv.zzD(zzmr.zzn(t, j), zzmr.zzn(t2, j))) {
                        return false;
                    }
                    continue;
                    break;
                default:
            }
            if (!zZzD) {
                return false;
            }
        }
        if (!this.zzn.zzd(t).equals(this.zzn.zzd(t2))) {
            return false;
        }
        if (!this.zzh) {
            return true;
        }
        this.zzo.zzb(t);
        this.zzo.zzb(t2);
        throw null;
    }

    @Override // com.google.android.gms.internal.measurement.zzlt
    public final int zzc(T t) {
        int i;
        int iZze;
        int length = this.zzc.length;
        int i2 = 0;
        for (int i3 = 0; i3 < length; i3 += 3) {
            int iZzA = zzA(i3);
            int i4 = this.zzc[i3];
            long j = 1048575 & iZzA;
            int iHashCode = 37;
            switch (zzC(iZzA)) {
                case 0:
                    i = i2 * 53;
                    iZze = zzkl.zze(Double.doubleToLongBits(zzmr.zzl(t, j)));
                    i2 = i + iZze;
                    break;
                case 1:
                    i = i2 * 53;
                    iZze = Float.floatToIntBits(zzmr.zzj(t, j));
                    i2 = i + iZze;
                    break;
                case 2:
                    i = i2 * 53;
                    iZze = zzkl.zze(zzmr.zzf(t, j));
                    i2 = i + iZze;
                    break;
                case 3:
                    i = i2 * 53;
                    iZze = zzkl.zze(zzmr.zzf(t, j));
                    i2 = i + iZze;
                    break;
                case 4:
                    i = i2 * 53;
                    iZze = zzmr.zzd(t, j);
                    i2 = i + iZze;
                    break;
                case 5:
                    i = i2 * 53;
                    iZze = zzkl.zze(zzmr.zzf(t, j));
                    i2 = i + iZze;
                    break;
                case 6:
                    i = i2 * 53;
                    iZze = zzmr.zzd(t, j);
                    i2 = i + iZze;
                    break;
                case 7:
                    i = i2 * 53;
                    iZze = zzkl.zzf(zzmr.zzh(t, j));
                    i2 = i + iZze;
                    break;
                case 8:
                    i = i2 * 53;
                    iZze = ((String) zzmr.zzn(t, j)).hashCode();
                    i2 = i + iZze;
                    break;
                case 9:
                    Object objZzn = zzmr.zzn(t, j);
                    if (objZzn != null) {
                        iHashCode = objZzn.hashCode();
                    }
                    i2 = (i2 * 53) + iHashCode;
                    break;
                case 10:
                    i = i2 * 53;
                    iZze = zzmr.zzn(t, j).hashCode();
                    i2 = i + iZze;
                    break;
                case 11:
                    i = i2 * 53;
                    iZze = zzmr.zzd(t, j);
                    i2 = i + iZze;
                    break;
                case 12:
                    i = i2 * 53;
                    iZze = zzmr.zzd(t, j);
                    i2 = i + iZze;
                    break;
                case 13:
                    i = i2 * 53;
                    iZze = zzmr.zzd(t, j);
                    i2 = i + iZze;
                    break;
                case 14:
                    i = i2 * 53;
                    iZze = zzkl.zze(zzmr.zzf(t, j));
                    i2 = i + iZze;
                    break;
                case 15:
                    i = i2 * 53;
                    iZze = zzmr.zzd(t, j);
                    i2 = i + iZze;
                    break;
                case 16:
                    i = i2 * 53;
                    iZze = zzkl.zze(zzmr.zzf(t, j));
                    i2 = i + iZze;
                    break;
                case 17:
                    Object objZzn2 = zzmr.zzn(t, j);
                    if (objZzn2 != null) {
                        iHashCode = objZzn2.hashCode();
                    }
                    i2 = (i2 * 53) + iHashCode;
                    break;
                case 18:
                case 19:
                case 20:
                case 21:
                case 22:
                case 23:
                case 24:
                case 25:
                case 26:
                case 27:
                case 28:
                case 29:
                case 30:
                case 31:
                case 32:
                case 33:
                case 34:
                case 35:
                case 36:
                case 37:
                case 38:
                case 39:
                case 40:
                case 41:
                case 42:
                case 43:
                case 44:
                case 45:
                case 46:
                case 47:
                case 48:
                case 49:
                    i = i2 * 53;
                    iZze = zzmr.zzn(t, j).hashCode();
                    i2 = i + iZze;
                    break;
                case 50:
                    i = i2 * 53;
                    iZze = zzmr.zzn(t, j).hashCode();
                    i2 = i + iZze;
                    break;
                case 51:
                    if (zzM(t, i4, i3)) {
                        i = i2 * 53;
                        iZze = zzkl.zze(Double.doubleToLongBits(zzD(t, j)));
                        i2 = i + iZze;
                        break;
                    } else {
                        break;
                    }
                case 52:
                    if (zzM(t, i4, i3)) {
                        i = i2 * 53;
                        iZze = Float.floatToIntBits(zzE(t, j));
                        i2 = i + iZze;
                        break;
                    } else {
                        break;
                    }
                case 53:
                    if (zzM(t, i4, i3)) {
                        i = i2 * 53;
                        iZze = zzkl.zze(zzG(t, j));
                        i2 = i + iZze;
                        break;
                    } else {
                        break;
                    }
                case 54:
                    if (zzM(t, i4, i3)) {
                        i = i2 * 53;
                        iZze = zzkl.zze(zzG(t, j));
                        i2 = i + iZze;
                        break;
                    } else {
                        break;
                    }
                case 55:
                    if (zzM(t, i4, i3)) {
                        i = i2 * 53;
                        iZze = zzF(t, j);
                        i2 = i + iZze;
                        break;
                    } else {
                        break;
                    }
                case 56:
                    if (zzM(t, i4, i3)) {
                        i = i2 * 53;
                        iZze = zzkl.zze(zzG(t, j));
                        i2 = i + iZze;
                        break;
                    } else {
                        break;
                    }
                case 57:
                    if (zzM(t, i4, i3)) {
                        i = i2 * 53;
                        iZze = zzF(t, j);
                        i2 = i + iZze;
                        break;
                    } else {
                        break;
                    }
                case 58:
                    if (zzM(t, i4, i3)) {
                        i = i2 * 53;
                        iZze = zzkl.zzf(zzH(t, j));
                        i2 = i + iZze;
                        break;
                    } else {
                        break;
                    }
                case 59:
                    if (zzM(t, i4, i3)) {
                        i = i2 * 53;
                        iZze = ((String) zzmr.zzn(t, j)).hashCode();
                        i2 = i + iZze;
                        break;
                    } else {
                        break;
                    }
                case 60:
                    if (zzM(t, i4, i3)) {
                        i = i2 * 53;
                        iZze = zzmr.zzn(t, j).hashCode();
                        i2 = i + iZze;
                        break;
                    } else {
                        break;
                    }
                case 61:
                    if (zzM(t, i4, i3)) {
                        i = i2 * 53;
                        iZze = zzmr.zzn(t, j).hashCode();
                        i2 = i + iZze;
                        break;
                    } else {
                        break;
                    }
                case 62:
                    if (zzM(t, i4, i3)) {
                        i = i2 * 53;
                        iZze = zzF(t, j);
                        i2 = i + iZze;
                        break;
                    } else {
                        break;
                    }
                case 63:
                    if (zzM(t, i4, i3)) {
                        i = i2 * 53;
                        iZze = zzF(t, j);
                        i2 = i + iZze;
                        break;
                    } else {
                        break;
                    }
                case 64:
                    if (zzM(t, i4, i3)) {
                        i = i2 * 53;
                        iZze = zzF(t, j);
                        i2 = i + iZze;
                        break;
                    } else {
                        break;
                    }
                case 65:
                    if (zzM(t, i4, i3)) {
                        i = i2 * 53;
                        iZze = zzkl.zze(zzG(t, j));
                        i2 = i + iZze;
                        break;
                    } else {
                        break;
                    }
                case 66:
                    if (zzM(t, i4, i3)) {
                        i = i2 * 53;
                        iZze = zzF(t, j);
                        i2 = i + iZze;
                        break;
                    } else {
                        break;
                    }
                case 67:
                    if (zzM(t, i4, i3)) {
                        i = i2 * 53;
                        iZze = zzkl.zze(zzG(t, j));
                        i2 = i + iZze;
                        break;
                    } else {
                        break;
                    }
                case 68:
                    if (zzM(t, i4, i3)) {
                        i = i2 * 53;
                        iZze = zzmr.zzn(t, j).hashCode();
                        i2 = i + iZze;
                        break;
                    } else {
                        break;
                    }
            }
        }
        int iHashCode2 = (i2 * 53) + this.zzn.zzd(t).hashCode();
        if (!this.zzh) {
            return iHashCode2;
        }
        this.zzo.zzb(t);
        throw null;
    }

    @Override // com.google.android.gms.internal.measurement.zzlt
    public final void zzd(T t, T t2) {
        if (t2 == null) {
            throw null;
        }
        for (int i = 0; i < this.zzc.length; i += 3) {
            int iZzA = zzA(i);
            long j = 1048575 & iZzA;
            int i2 = this.zzc[i];
            switch (zzC(iZzA)) {
                case 0:
                    if (zzK(t2, i)) {
                        zzmr.zzm(t, j, zzmr.zzl(t2, j));
                        zzL(t, i);
                        break;
                    } else {
                        break;
                    }
                case 1:
                    if (zzK(t2, i)) {
                        zzmr.zzk(t, j, zzmr.zzj(t2, j));
                        zzL(t, i);
                        break;
                    } else {
                        break;
                    }
                case 2:
                    if (zzK(t2, i)) {
                        zzmr.zzg(t, j, zzmr.zzf(t2, j));
                        zzL(t, i);
                        break;
                    } else {
                        break;
                    }
                case 3:
                    if (zzK(t2, i)) {
                        zzmr.zzg(t, j, zzmr.zzf(t2, j));
                        zzL(t, i);
                        break;
                    } else {
                        break;
                    }
                case 4:
                    if (zzK(t2, i)) {
                        zzmr.zze(t, j, zzmr.zzd(t2, j));
                        zzL(t, i);
                        break;
                    } else {
                        break;
                    }
                case 5:
                    if (zzK(t2, i)) {
                        zzmr.zzg(t, j, zzmr.zzf(t2, j));
                        zzL(t, i);
                        break;
                    } else {
                        break;
                    }
                case 6:
                    if (zzK(t2, i)) {
                        zzmr.zze(t, j, zzmr.zzd(t2, j));
                        zzL(t, i);
                        break;
                    } else {
                        break;
                    }
                case 7:
                    if (zzK(t2, i)) {
                        zzmr.zzi(t, j, zzmr.zzh(t2, j));
                        zzL(t, i);
                        break;
                    } else {
                        break;
                    }
                case 8:
                    if (zzK(t2, i)) {
                        zzmr.zzo(t, j, zzmr.zzn(t2, j));
                        zzL(t, i);
                        break;
                    } else {
                        break;
                    }
                case 9:
                    zzo(t, t2, i);
                    break;
                case 10:
                    if (zzK(t2, i)) {
                        zzmr.zzo(t, j, zzmr.zzn(t2, j));
                        zzL(t, i);
                        break;
                    } else {
                        break;
                    }
                case 11:
                    if (zzK(t2, i)) {
                        zzmr.zze(t, j, zzmr.zzd(t2, j));
                        zzL(t, i);
                        break;
                    } else {
                        break;
                    }
                case 12:
                    if (zzK(t2, i)) {
                        zzmr.zze(t, j, zzmr.zzd(t2, j));
                        zzL(t, i);
                        break;
                    } else {
                        break;
                    }
                case 13:
                    if (zzK(t2, i)) {
                        zzmr.zze(t, j, zzmr.zzd(t2, j));
                        zzL(t, i);
                        break;
                    } else {
                        break;
                    }
                case 14:
                    if (zzK(t2, i)) {
                        zzmr.zzg(t, j, zzmr.zzf(t2, j));
                        zzL(t, i);
                        break;
                    } else {
                        break;
                    }
                case 15:
                    if (zzK(t2, i)) {
                        zzmr.zze(t, j, zzmr.zzd(t2, j));
                        zzL(t, i);
                        break;
                    } else {
                        break;
                    }
                case 16:
                    if (zzK(t2, i)) {
                        zzmr.zzg(t, j, zzmr.zzf(t2, j));
                        zzL(t, i);
                        break;
                    } else {
                        break;
                    }
                case 17:
                    zzo(t, t2, i);
                    break;
                case 18:
                case 19:
                case 20:
                case 21:
                case 22:
                case 23:
                case 24:
                case 25:
                case 26:
                case 27:
                case 28:
                case 29:
                case 30:
                case 31:
                case 32:
                case 33:
                case 34:
                case 35:
                case 36:
                case 37:
                case 38:
                case 39:
                case 40:
                case 41:
                case 42:
                case 43:
                case 44:
                case 45:
                case 46:
                case 47:
                case 48:
                case 49:
                    this.zzm.zzb(t, t2, j);
                    break;
                case 50:
                    zzlv.zzI(this.zzq, t, t2, j);
                    break;
                case 51:
                case 52:
                case 53:
                case 54:
                case 55:
                case 56:
                case 57:
                case 58:
                case 59:
                    if (zzM(t2, i2, i)) {
                        zzmr.zzo(t, j, zzmr.zzn(t2, j));
                        zzN(t, i2, i);
                        break;
                    } else {
                        break;
                    }
                case 60:
                    zzp(t, t2, i);
                    break;
                case 61:
                case 62:
                case 63:
                case 64:
                case 65:
                case 66:
                case 67:
                    if (zzM(t2, i2, i)) {
                        zzmr.zzo(t, j, zzmr.zzn(t2, j));
                        zzN(t, i2, i);
                        break;
                    } else {
                        break;
                    }
                case 68:
                    zzp(t, t2, i);
                    break;
            }
        }
        zzlv.zzF(this.zzn, t, t2);
        if (this.zzh) {
            zzlv.zzE(this.zzo, t, t2);
        }
    }

    @Override // com.google.android.gms.internal.measurement.zzlt
    public final int zze(T t) {
        return this.zzi ? zzr(t) : zzq(t);
    }

    /* JADX WARN: Code restructure failed: missing block: B:144:0x0417, code lost:
    
        if (r6 == 1048575) goto L146;
     */
    /* JADX WARN: Code restructure failed: missing block: B:145:0x0419, code lost:
    
        r26.putInt(r12, r6, r5);
     */
    /* JADX WARN: Code restructure failed: missing block: B:146:0x041f, code lost:
    
        r3 = r9.zzk;
     */
    /* JADX WARN: Code restructure failed: missing block: B:148:0x0423, code lost:
    
        if (r3 >= r9.zzl) goto L217;
     */
    /* JADX WARN: Code restructure failed: missing block: B:149:0x0425, code lost:
    
        r4 = r9.zzj[r3];
        r5 = r9.zzc[r4];
        r5 = com.google.android.gms.internal.measurement.zzmr.zzn(r12, r9.zzA(r4) & 1048575);
     */
    /* JADX WARN: Code restructure failed: missing block: B:150:0x0437, code lost:
    
        if (r5 != null) goto L152;
     */
    /* JADX WARN: Code restructure failed: missing block: B:153:0x043e, code lost:
    
        if (r9.zzx(r4) != null) goto L218;
     */
    /* JADX WARN: Code restructure failed: missing block: B:154:0x0440, code lost:
    
        r3 = r3 + 1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:155:0x0443, code lost:
    
        r5 = (com.google.android.gms.internal.measurement.zzlc) r5;
        r0 = (com.google.android.gms.internal.measurement.zzlb) r9.zzw(r4);
     */
    /* JADX WARN: Code restructure failed: missing block: B:156:0x044b, code lost:
    
        throw null;
     */
    /* JADX WARN: Code restructure failed: missing block: B:157:0x044c, code lost:
    
        if (r7 != 0) goto L163;
     */
    /* JADX WARN: Code restructure failed: missing block: B:159:0x0450, code lost:
    
        if (r0 != r32) goto L161;
     */
    /* JADX WARN: Code restructure failed: missing block: B:162:0x0457, code lost:
    
        throw com.google.android.gms.internal.measurement.zzkn.zze();
     */
    /* JADX WARN: Code restructure failed: missing block: B:164:0x045a, code lost:
    
        if (r0 > r32) goto L167;
     */
    /* JADX WARN: Code restructure failed: missing block: B:165:0x045c, code lost:
    
        if (r1 != r7) goto L167;
     */
    /* JADX WARN: Code restructure failed: missing block: B:166:0x045e, code lost:
    
        return r0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:168:0x0463, code lost:
    
        throw com.google.android.gms.internal.measurement.zzkn.zze();
     */
    /* JADX WARN: Multi-variable type inference failed */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    final int zzg(T r29, byte[] r30, int r31, int r32, int r33, com.google.android.gms.internal.measurement.zzir r34) throws java.io.IOException {
        /*
            Method dump skipped, instructions count: 1162
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.measurement.zzll.zzg(java.lang.Object, byte[], int, int, int, com.google.android.gms.internal.measurement.zzir):int");
    }

    @Override // com.google.android.gms.internal.measurement.zzlt
    public final void zzh(T t, byte[] bArr, int i, int i2, zzir zzirVar) throws IOException {
        if (this.zzi) {
            zzy(t, bArr, i, i2, zzirVar);
        } else {
            zzg(t, bArr, i, i2, 0, zzirVar);
        }
    }

    @Override // com.google.android.gms.internal.measurement.zzlt
    public final void zzi(T t) {
        int i;
        int i2 = this.zzk;
        while (true) {
            i = this.zzl;
            if (i2 >= i) {
                break;
            }
            long jZzA = zzA(this.zzj[i2]) & 1048575;
            Object objZzn = zzmr.zzn(t, jZzA);
            if (objZzn != null) {
                ((zzlc) objZzn).zzd();
                zzmr.zzo(t, jZzA, objZzn);
            }
            i2++;
        }
        int length = this.zzj.length;
        while (i < length) {
            this.zzm.zza(t, this.zzj[i]);
            i++;
        }
        this.zzn.zze(t);
        if (this.zzh) {
            this.zzo.zzc(t);
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:42:0x00a0  */
    @Override // com.google.android.gms.internal.measurement.zzlt
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final boolean zzj(T r19) {
        /*
            Method dump skipped, instructions count: 246
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.measurement.zzll.zzj(java.lang.Object):boolean");
    }

    @Override // com.google.android.gms.internal.measurement.zzlt
    public final void zzm(T t, zzjl zzjlVar) throws IOException {
        if (!this.zzi) {
            zzR(t, zzjlVar);
            return;
        }
        if (this.zzh) {
            this.zzo.zzb(t);
            throw null;
        }
        int length = this.zzc.length;
        for (int i = 0; i < length; i += 3) {
            int iZzA = zzA(i);
            int i2 = this.zzc[i];
            switch (zzC(iZzA)) {
                case 0:
                    if (zzK(t, i)) {
                        zzjlVar.zzf(i2, zzmr.zzl(t, iZzA & 1048575));
                        break;
                    } else {
                        break;
                    }
                case 1:
                    if (zzK(t, i)) {
                        zzjlVar.zze(i2, zzmr.zzj(t, iZzA & 1048575));
                        break;
                    } else {
                        break;
                    }
                case 2:
                    if (zzK(t, i)) {
                        zzjlVar.zzc(i2, zzmr.zzf(t, iZzA & 1048575));
                        break;
                    } else {
                        break;
                    }
                case 3:
                    if (zzK(t, i)) {
                        zzjlVar.zzh(i2, zzmr.zzf(t, iZzA & 1048575));
                        break;
                    } else {
                        break;
                    }
                case 4:
                    if (zzK(t, i)) {
                        zzjlVar.zzi(i2, zzmr.zzd(t, iZzA & 1048575));
                        break;
                    } else {
                        break;
                    }
                case 5:
                    if (zzK(t, i)) {
                        zzjlVar.zzj(i2, zzmr.zzf(t, iZzA & 1048575));
                        break;
                    } else {
                        break;
                    }
                case 6:
                    if (zzK(t, i)) {
                        zzjlVar.zzk(i2, zzmr.zzd(t, iZzA & 1048575));
                        break;
                    } else {
                        break;
                    }
                case 7:
                    if (zzK(t, i)) {
                        zzjlVar.zzl(i2, zzmr.zzh(t, iZzA & 1048575));
                        break;
                    } else {
                        break;
                    }
                case 8:
                    if (zzK(t, i)) {
                        zzT(i2, zzmr.zzn(t, iZzA & 1048575), zzjlVar);
                        break;
                    } else {
                        break;
                    }
                case 9:
                    if (zzK(t, i)) {
                        zzjlVar.zzr(i2, zzmr.zzn(t, iZzA & 1048575), zzv(i));
                        break;
                    } else {
                        break;
                    }
                case 10:
                    if (zzK(t, i)) {
                        zzjlVar.zzn(i2, (zzjd) zzmr.zzn(t, iZzA & 1048575));
                        break;
                    } else {
                        break;
                    }
                case 11:
                    if (zzK(t, i)) {
                        zzjlVar.zzo(i2, zzmr.zzd(t, iZzA & 1048575));
                        break;
                    } else {
                        break;
                    }
                case 12:
                    if (zzK(t, i)) {
                        zzjlVar.zzg(i2, zzmr.zzd(t, iZzA & 1048575));
                        break;
                    } else {
                        break;
                    }
                case 13:
                    if (zzK(t, i)) {
                        zzjlVar.zzb(i2, zzmr.zzd(t, iZzA & 1048575));
                        break;
                    } else {
                        break;
                    }
                case 14:
                    if (zzK(t, i)) {
                        zzjlVar.zzd(i2, zzmr.zzf(t, iZzA & 1048575));
                        break;
                    } else {
                        break;
                    }
                case 15:
                    if (zzK(t, i)) {
                        zzjlVar.zzp(i2, zzmr.zzd(t, iZzA & 1048575));
                        break;
                    } else {
                        break;
                    }
                case 16:
                    if (zzK(t, i)) {
                        zzjlVar.zzq(i2, zzmr.zzf(t, iZzA & 1048575));
                        break;
                    } else {
                        break;
                    }
                case 17:
                    if (zzK(t, i)) {
                        zzjlVar.zzs(i2, zzmr.zzn(t, iZzA & 1048575), zzv(i));
                        break;
                    } else {
                        break;
                    }
                case 18:
                    zzlv.zzJ(this.zzc[i], (List) zzmr.zzn(t, iZzA & 1048575), zzjlVar, false);
                    break;
                case 19:
                    zzlv.zzK(this.zzc[i], (List) zzmr.zzn(t, iZzA & 1048575), zzjlVar, false);
                    break;
                case 20:
                    zzlv.zzL(this.zzc[i], (List) zzmr.zzn(t, iZzA & 1048575), zzjlVar, false);
                    break;
                case 21:
                    zzlv.zzM(this.zzc[i], (List) zzmr.zzn(t, iZzA & 1048575), zzjlVar, false);
                    break;
                case 22:
                    zzlv.zzQ(this.zzc[i], (List) zzmr.zzn(t, iZzA & 1048575), zzjlVar, false);
                    break;
                case 23:
                    zzlv.zzO(this.zzc[i], (List) zzmr.zzn(t, iZzA & 1048575), zzjlVar, false);
                    break;
                case 24:
                    zzlv.zzT(this.zzc[i], (List) zzmr.zzn(t, iZzA & 1048575), zzjlVar, false);
                    break;
                case 25:
                    zzlv.zzW(this.zzc[i], (List) zzmr.zzn(t, iZzA & 1048575), zzjlVar, false);
                    break;
                case 26:
                    zzlv.zzX(this.zzc[i], (List) zzmr.zzn(t, iZzA & 1048575), zzjlVar);
                    break;
                case 27:
                    zzlv.zzZ(this.zzc[i], (List) zzmr.zzn(t, iZzA & 1048575), zzjlVar, zzv(i));
                    break;
                case 28:
                    zzlv.zzY(this.zzc[i], (List) zzmr.zzn(t, iZzA & 1048575), zzjlVar);
                    break;
                case 29:
                    zzlv.zzR(this.zzc[i], (List) zzmr.zzn(t, iZzA & 1048575), zzjlVar, false);
                    break;
                case 30:
                    zzlv.zzV(this.zzc[i], (List) zzmr.zzn(t, iZzA & 1048575), zzjlVar, false);
                    break;
                case 31:
                    zzlv.zzU(this.zzc[i], (List) zzmr.zzn(t, iZzA & 1048575), zzjlVar, false);
                    break;
                case 32:
                    zzlv.zzP(this.zzc[i], (List) zzmr.zzn(t, iZzA & 1048575), zzjlVar, false);
                    break;
                case 33:
                    zzlv.zzS(this.zzc[i], (List) zzmr.zzn(t, iZzA & 1048575), zzjlVar, false);
                    break;
                case 34:
                    zzlv.zzN(this.zzc[i], (List) zzmr.zzn(t, iZzA & 1048575), zzjlVar, false);
                    break;
                case 35:
                    zzlv.zzJ(this.zzc[i], (List) zzmr.zzn(t, iZzA & 1048575), zzjlVar, true);
                    break;
                case 36:
                    zzlv.zzK(this.zzc[i], (List) zzmr.zzn(t, iZzA & 1048575), zzjlVar, true);
                    break;
                case 37:
                    zzlv.zzL(this.zzc[i], (List) zzmr.zzn(t, iZzA & 1048575), zzjlVar, true);
                    break;
                case 38:
                    zzlv.zzM(this.zzc[i], (List) zzmr.zzn(t, iZzA & 1048575), zzjlVar, true);
                    break;
                case 39:
                    zzlv.zzQ(this.zzc[i], (List) zzmr.zzn(t, iZzA & 1048575), zzjlVar, true);
                    break;
                case 40:
                    zzlv.zzO(this.zzc[i], (List) zzmr.zzn(t, iZzA & 1048575), zzjlVar, true);
                    break;
                case 41:
                    zzlv.zzT(this.zzc[i], (List) zzmr.zzn(t, iZzA & 1048575), zzjlVar, true);
                    break;
                case 42:
                    zzlv.zzW(this.zzc[i], (List) zzmr.zzn(t, iZzA & 1048575), zzjlVar, true);
                    break;
                case 43:
                    zzlv.zzR(this.zzc[i], (List) zzmr.zzn(t, iZzA & 1048575), zzjlVar, true);
                    break;
                case 44:
                    zzlv.zzV(this.zzc[i], (List) zzmr.zzn(t, iZzA & 1048575), zzjlVar, true);
                    break;
                case 45:
                    zzlv.zzU(this.zzc[i], (List) zzmr.zzn(t, iZzA & 1048575), zzjlVar, true);
                    break;
                case 46:
                    zzlv.zzP(this.zzc[i], (List) zzmr.zzn(t, iZzA & 1048575), zzjlVar, true);
                    break;
                case 47:
                    zzlv.zzS(this.zzc[i], (List) zzmr.zzn(t, iZzA & 1048575), zzjlVar, true);
                    break;
                case 48:
                    zzlv.zzN(this.zzc[i], (List) zzmr.zzn(t, iZzA & 1048575), zzjlVar, true);
                    break;
                case 49:
                    zzlv.zzaa(this.zzc[i], (List) zzmr.zzn(t, iZzA & 1048575), zzjlVar, zzv(i));
                    break;
                case 50:
                    zzS(zzjlVar, i2, zzmr.zzn(t, iZzA & 1048575), i);
                    break;
                case 51:
                    if (zzM(t, i2, i)) {
                        zzjlVar.zzf(i2, zzD(t, iZzA & 1048575));
                        break;
                    } else {
                        break;
                    }
                case 52:
                    if (zzM(t, i2, i)) {
                        zzjlVar.zze(i2, zzE(t, iZzA & 1048575));
                        break;
                    } else {
                        break;
                    }
                case 53:
                    if (zzM(t, i2, i)) {
                        zzjlVar.zzc(i2, zzG(t, iZzA & 1048575));
                        break;
                    } else {
                        break;
                    }
                case 54:
                    if (zzM(t, i2, i)) {
                        zzjlVar.zzh(i2, zzG(t, iZzA & 1048575));
                        break;
                    } else {
                        break;
                    }
                case 55:
                    if (zzM(t, i2, i)) {
                        zzjlVar.zzi(i2, zzF(t, iZzA & 1048575));
                        break;
                    } else {
                        break;
                    }
                case 56:
                    if (zzM(t, i2, i)) {
                        zzjlVar.zzj(i2, zzG(t, iZzA & 1048575));
                        break;
                    } else {
                        break;
                    }
                case 57:
                    if (zzM(t, i2, i)) {
                        zzjlVar.zzk(i2, zzF(t, iZzA & 1048575));
                        break;
                    } else {
                        break;
                    }
                case 58:
                    if (zzM(t, i2, i)) {
                        zzjlVar.zzl(i2, zzH(t, iZzA & 1048575));
                        break;
                    } else {
                        break;
                    }
                case 59:
                    if (zzM(t, i2, i)) {
                        zzT(i2, zzmr.zzn(t, iZzA & 1048575), zzjlVar);
                        break;
                    } else {
                        break;
                    }
                case 60:
                    if (zzM(t, i2, i)) {
                        zzjlVar.zzr(i2, zzmr.zzn(t, iZzA & 1048575), zzv(i));
                        break;
                    } else {
                        break;
                    }
                case 61:
                    if (zzM(t, i2, i)) {
                        zzjlVar.zzn(i2, (zzjd) zzmr.zzn(t, iZzA & 1048575));
                        break;
                    } else {
                        break;
                    }
                case 62:
                    if (zzM(t, i2, i)) {
                        zzjlVar.zzo(i2, zzF(t, iZzA & 1048575));
                        break;
                    } else {
                        break;
                    }
                case 63:
                    if (zzM(t, i2, i)) {
                        zzjlVar.zzg(i2, zzF(t, iZzA & 1048575));
                        break;
                    } else {
                        break;
                    }
                case 64:
                    if (zzM(t, i2, i)) {
                        zzjlVar.zzb(i2, zzF(t, iZzA & 1048575));
                        break;
                    } else {
                        break;
                    }
                case 65:
                    if (zzM(t, i2, i)) {
                        zzjlVar.zzd(i2, zzG(t, iZzA & 1048575));
                        break;
                    } else {
                        break;
                    }
                case 66:
                    if (zzM(t, i2, i)) {
                        zzjlVar.zzp(i2, zzF(t, iZzA & 1048575));
                        break;
                    } else {
                        break;
                    }
                case 67:
                    if (zzM(t, i2, i)) {
                        zzjlVar.zzq(i2, zzG(t, iZzA & 1048575));
                        break;
                    } else {
                        break;
                    }
                case 68:
                    if (zzM(t, i2, i)) {
                        zzjlVar.zzs(i2, zzmr.zzn(t, iZzA & 1048575), zzv(i));
                        break;
                    } else {
                        break;
                    }
            }
        }
        zzmh<?, ?> zzmhVar = this.zzn;
        zzmhVar.zzi(zzmhVar.zzd(t), zzjlVar);
    }
}
