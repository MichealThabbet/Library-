package com.google.android.gms.internal.gtm;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.RandomAccess;

/* loaded from: classes.dex */
final class zztb {
    private static final Class<?> zzbdu = zzqy();
    private static final zztr<?, ?> zzbdv = zzl(false);
    private static final zztr<?, ?> zzbdw = zzl(true);
    private static final zztr<?, ?> zzbdx = new zztt();

    public static void zzj(Class<?> cls) {
        Class<?> cls2;
        if (!zzrc.class.isAssignableFrom(cls) && (cls2 = zzbdu) != null && !cls2.isAssignableFrom(cls)) {
            throw new IllegalArgumentException("Message classes must extend GeneratedMessage or GeneratedMessageLite");
        }
    }

    public static void zza(int i, List<Double> list, zzum zzumVar, boolean z) throws IOException {
        if (list == null || list.isEmpty()) {
            return;
        }
        zzumVar.zzg(i, list, z);
    }

    public static void zzb(int i, List<Float> list, zzum zzumVar, boolean z) throws IOException {
        if (list == null || list.isEmpty()) {
            return;
        }
        zzumVar.zzf(i, list, z);
    }

    public static void zzc(int i, List<Long> list, zzum zzumVar, boolean z) throws IOException {
        if (list == null || list.isEmpty()) {
            return;
        }
        zzumVar.zzc(i, list, z);
    }

    public static void zzd(int i, List<Long> list, zzum zzumVar, boolean z) throws IOException {
        if (list == null || list.isEmpty()) {
            return;
        }
        zzumVar.zzd(i, list, z);
    }

    public static void zze(int i, List<Long> list, zzum zzumVar, boolean z) throws IOException {
        if (list == null || list.isEmpty()) {
            return;
        }
        zzumVar.zzn(i, list, z);
    }

    public static void zzf(int i, List<Long> list, zzum zzumVar, boolean z) throws IOException {
        if (list == null || list.isEmpty()) {
            return;
        }
        zzumVar.zze(i, list, z);
    }

    public static void zzg(int i, List<Long> list, zzum zzumVar, boolean z) throws IOException {
        if (list == null || list.isEmpty()) {
            return;
        }
        zzumVar.zzl(i, list, z);
    }

    public static void zzh(int i, List<Integer> list, zzum zzumVar, boolean z) throws IOException {
        if (list == null || list.isEmpty()) {
            return;
        }
        zzumVar.zza(i, list, z);
    }

    public static void zzi(int i, List<Integer> list, zzum zzumVar, boolean z) throws IOException {
        if (list == null || list.isEmpty()) {
            return;
        }
        zzumVar.zzj(i, list, z);
    }

    public static void zzj(int i, List<Integer> list, zzum zzumVar, boolean z) throws IOException {
        if (list == null || list.isEmpty()) {
            return;
        }
        zzumVar.zzm(i, list, z);
    }

    public static void zzk(int i, List<Integer> list, zzum zzumVar, boolean z) throws IOException {
        if (list == null || list.isEmpty()) {
            return;
        }
        zzumVar.zzb(i, list, z);
    }

    public static void zzl(int i, List<Integer> list, zzum zzumVar, boolean z) throws IOException {
        if (list == null || list.isEmpty()) {
            return;
        }
        zzumVar.zzk(i, list, z);
    }

    public static void zzm(int i, List<Integer> list, zzum zzumVar, boolean z) throws IOException {
        if (list == null || list.isEmpty()) {
            return;
        }
        zzumVar.zzh(i, list, z);
    }

    public static void zzn(int i, List<Boolean> list, zzum zzumVar, boolean z) throws IOException {
        if (list == null || list.isEmpty()) {
            return;
        }
        zzumVar.zzi(i, list, z);
    }

    public static void zza(int i, List<String> list, zzum zzumVar) throws IOException {
        if (list == null || list.isEmpty()) {
            return;
        }
        zzumVar.zza(i, list);
    }

    public static void zzb(int i, List<zzps> list, zzum zzumVar) throws IOException {
        if (list == null || list.isEmpty()) {
            return;
        }
        zzumVar.zzb(i, list);
    }

    public static void zza(int i, List<?> list, zzum zzumVar, zzsz zzszVar) throws IOException {
        if (list == null || list.isEmpty()) {
            return;
        }
        zzumVar.zza(i, list, zzszVar);
    }

    public static void zzb(int i, List<?> list, zzum zzumVar, zzsz zzszVar) throws IOException {
        if (list == null || list.isEmpty()) {
            return;
        }
        zzumVar.zzb(i, list, zzszVar);
    }

    static int zzw(List<Long> list) {
        int iZzs;
        int size = list.size();
        int i = 0;
        if (size == 0) {
            return 0;
        }
        if (list instanceof zzry) {
            zzry zzryVar = (zzry) list;
            iZzs = 0;
            while (i < size) {
                iZzs += zzqj.zzs(zzryVar.getLong(i));
                i++;
            }
        } else {
            iZzs = 0;
            while (i < size) {
                iZzs += zzqj.zzs(list.get(i).longValue());
                i++;
            }
        }
        return iZzs;
    }

    static int zzo(int i, List<Long> list, boolean z) {
        if (list.size() == 0) {
            return 0;
        }
        return zzw(list) + (list.size() * zzqj.zzbb(i));
    }

    static int zzx(List<Long> list) {
        int iZzt;
        int size = list.size();
        int i = 0;
        if (size == 0) {
            return 0;
        }
        if (list instanceof zzry) {
            zzry zzryVar = (zzry) list;
            iZzt = 0;
            while (i < size) {
                iZzt += zzqj.zzt(zzryVar.getLong(i));
                i++;
            }
        } else {
            iZzt = 0;
            while (i < size) {
                iZzt += zzqj.zzt(list.get(i).longValue());
                i++;
            }
        }
        return iZzt;
    }

    static int zzp(int i, List<Long> list, boolean z) {
        int size = list.size();
        if (size == 0) {
            return 0;
        }
        return zzx(list) + (size * zzqj.zzbb(i));
    }

    static int zzy(List<Long> list) {
        int iZzu;
        int size = list.size();
        int i = 0;
        if (size == 0) {
            return 0;
        }
        if (list instanceof zzry) {
            zzry zzryVar = (zzry) list;
            iZzu = 0;
            while (i < size) {
                iZzu += zzqj.zzu(zzryVar.getLong(i));
                i++;
            }
        } else {
            iZzu = 0;
            while (i < size) {
                iZzu += zzqj.zzu(list.get(i).longValue());
                i++;
            }
        }
        return iZzu;
    }

    static int zzq(int i, List<Long> list, boolean z) {
        int size = list.size();
        if (size == 0) {
            return 0;
        }
        return zzy(list) + (size * zzqj.zzbb(i));
    }

    static int zzz(List<Integer> list) {
        int iZzbh;
        int size = list.size();
        int i = 0;
        if (size == 0) {
            return 0;
        }
        if (list instanceof zzrd) {
            zzrd zzrdVar = (zzrd) list;
            iZzbh = 0;
            while (i < size) {
                iZzbh += zzqj.zzbh(zzrdVar.getInt(i));
                i++;
            }
        } else {
            iZzbh = 0;
            while (i < size) {
                iZzbh += zzqj.zzbh(list.get(i).intValue());
                i++;
            }
        }
        return iZzbh;
    }

    static int zzr(int i, List<Integer> list, boolean z) {
        int size = list.size();
        if (size == 0) {
            return 0;
        }
        return zzz(list) + (size * zzqj.zzbb(i));
    }

    static int zzaa(List<Integer> list) {
        int iZzbc;
        int size = list.size();
        int i = 0;
        if (size == 0) {
            return 0;
        }
        if (list instanceof zzrd) {
            zzrd zzrdVar = (zzrd) list;
            iZzbc = 0;
            while (i < size) {
                iZzbc += zzqj.zzbc(zzrdVar.getInt(i));
                i++;
            }
        } else {
            iZzbc = 0;
            while (i < size) {
                iZzbc += zzqj.zzbc(list.get(i).intValue());
                i++;
            }
        }
        return iZzbc;
    }

    static int zzs(int i, List<Integer> list, boolean z) {
        int size = list.size();
        if (size == 0) {
            return 0;
        }
        return zzaa(list) + (size * zzqj.zzbb(i));
    }

    static int zzab(List<Integer> list) {
        int iZzbd;
        int size = list.size();
        int i = 0;
        if (size == 0) {
            return 0;
        }
        if (list instanceof zzrd) {
            zzrd zzrdVar = (zzrd) list;
            iZzbd = 0;
            while (i < size) {
                iZzbd += zzqj.zzbd(zzrdVar.getInt(i));
                i++;
            }
        } else {
            iZzbd = 0;
            while (i < size) {
                iZzbd += zzqj.zzbd(list.get(i).intValue());
                i++;
            }
        }
        return iZzbd;
    }

    static int zzt(int i, List<Integer> list, boolean z) {
        int size = list.size();
        if (size == 0) {
            return 0;
        }
        return zzab(list) + (size * zzqj.zzbb(i));
    }

    static int zzac(List<Integer> list) {
        int iZzbe;
        int size = list.size();
        int i = 0;
        if (size == 0) {
            return 0;
        }
        if (list instanceof zzrd) {
            zzrd zzrdVar = (zzrd) list;
            iZzbe = 0;
            while (i < size) {
                iZzbe += zzqj.zzbe(zzrdVar.getInt(i));
                i++;
            }
        } else {
            iZzbe = 0;
            while (i < size) {
                iZzbe += zzqj.zzbe(list.get(i).intValue());
                i++;
            }
        }
        return iZzbe;
    }

    static int zzu(int i, List<Integer> list, boolean z) {
        int size = list.size();
        if (size == 0) {
            return 0;
        }
        return zzac(list) + (size * zzqj.zzbb(i));
    }

    static int zzad(List<?> list) {
        return list.size() << 2;
    }

    static int zzv(int i, List<?> list, boolean z) {
        int size = list.size();
        if (size == 0) {
            return 0;
        }
        return size * zzqj.zzl(i, 0);
    }

    static int zzae(List<?> list) {
        return list.size() << 3;
    }

    static int zzw(int i, List<?> list, boolean z) {
        int size = list.size();
        if (size == 0) {
            return 0;
        }
        return size * zzqj.zzg(i, 0L);
    }

    static int zzaf(List<?> list) {
        return list.size();
    }

    static int zzx(int i, List<?> list, boolean z) {
        int size = list.size();
        if (size == 0) {
            return 0;
        }
        return size * zzqj.zzc(i, true);
    }

    static int zzc(int i, List<?> list) {
        int iZzda;
        int iZzda2;
        int size = list.size();
        int i2 = 0;
        if (size == 0) {
            return 0;
        }
        int iZzbb = zzqj.zzbb(i) * size;
        if (list instanceof zzrt) {
            zzrt zzrtVar = (zzrt) list;
            while (i2 < size) {
                Object objZzbn = zzrtVar.zzbn(i2);
                if (objZzbn instanceof zzps) {
                    iZzda2 = zzqj.zzb((zzps) objZzbn);
                } else {
                    iZzda2 = zzqj.zzda((String) objZzbn);
                }
                iZzbb += iZzda2;
                i2++;
            }
        } else {
            while (i2 < size) {
                Object obj = list.get(i2);
                if (obj instanceof zzps) {
                    iZzda = zzqj.zzb((zzps) obj);
                } else {
                    iZzda = zzqj.zzda((String) obj);
                }
                iZzbb += iZzda;
                i2++;
            }
        }
        return iZzbb;
    }

    static int zzc(int i, Object obj, zzsz zzszVar) {
        if (obj instanceof zzrr) {
            return zzqj.zza(i, (zzrr) obj);
        }
        return zzqj.zzb(i, (zzsk) obj, zzszVar);
    }

    static int zzc(int i, List<?> list, zzsz zzszVar) {
        int iZzb;
        int size = list.size();
        if (size == 0) {
            return 0;
        }
        int iZzbb = zzqj.zzbb(i) * size;
        for (int i2 = 0; i2 < size; i2++) {
            Object obj = list.get(i2);
            if (obj instanceof zzrr) {
                iZzb = zzqj.zza((zzrr) obj);
            } else {
                iZzb = zzqj.zzb((zzsk) obj, zzszVar);
            }
            iZzbb += iZzb;
        }
        return iZzbb;
    }

    static int zzd(int i, List<zzps> list) {
        int size = list.size();
        if (size == 0) {
            return 0;
        }
        int iZzbb = size * zzqj.zzbb(i);
        for (int i2 = 0; i2 < list.size(); i2++) {
            iZzbb += zzqj.zzb(list.get(i2));
        }
        return iZzbb;
    }

    static int zzd(int i, List<zzsk> list, zzsz zzszVar) {
        int size = list.size();
        if (size == 0) {
            return 0;
        }
        int iZzc = 0;
        for (int i2 = 0; i2 < size; i2++) {
            iZzc += zzqj.zzc(i, list.get(i2), zzszVar);
        }
        return iZzc;
    }

    public static zztr<?, ?> zzqv() {
        return zzbdv;
    }

    public static zztr<?, ?> zzqw() {
        return zzbdw;
    }

    public static zztr<?, ?> zzqx() {
        return zzbdx;
    }

    private static zztr<?, ?> zzl(boolean z) {
        try {
            Class<?> clsZzqz = zzqz();
            if (clsZzqz == null) {
                return null;
            }
            return (zztr) clsZzqz.getConstructor(Boolean.TYPE).newInstance(Boolean.valueOf(z));
        } catch (Throwable unused) {
            return null;
        }
    }

    private static Class<?> zzqy() {
        try {
            return Class.forName("com.google.protobuf.GeneratedMessage");
        } catch (Throwable unused) {
            return null;
        }
    }

    private static Class<?> zzqz() {
        try {
            return Class.forName("com.google.protobuf.UnknownFieldSetSchema");
        } catch (Throwable unused) {
            return null;
        }
    }

    static boolean zze(Object obj, Object obj2) {
        if (obj != obj2) {
            return obj != null && obj.equals(obj2);
        }
        return true;
    }

    static <T> void zza(zzsf zzsfVar, T t, T t2, long j) {
        zztx.zza(t, j, zzsfVar.zzc(zztx.zzp(t, j), zztx.zzp(t2, j)));
    }

    /* JADX WARN: Multi-variable type inference failed */
    static <T, FT extends zzqv<FT>> void zza(zzqq<FT> zzqqVar, T t, T t2) {
        zzqt<T> zzqtVarZzr = zzqqVar.zzr(t2);
        if (zzqtVarZzr.zzaxo.isEmpty()) {
            return;
        }
        zzqqVar.zzs(t).zza(zzqtVarZzr);
    }

    static <T, UT, UB> void zza(zztr<UT, UB> zztrVar, T t, T t2) {
        zztrVar.zzf(t, zztrVar.zzh(zztrVar.zzag(t), zztrVar.zzag(t2)));
    }

    static <UT, UB> UB zza(int i, List<Integer> list, zzrh zzrhVar, UB ub, zztr<UT, UB> zztrVar) {
        if (zzrhVar == null) {
            return ub;
        }
        if (list instanceof RandomAccess) {
            int size = list.size();
            int i2 = 0;
            for (int i3 = 0; i3 < size; i3++) {
                int iIntValue = list.get(i3).intValue();
                if (zzrhVar.zzb(iIntValue)) {
                    if (i3 != i2) {
                        list.set(i2, Integer.valueOf(iIntValue));
                    }
                    i2++;
                } else {
                    ub = (UB) zza(i, iIntValue, ub, zztrVar);
                }
            }
            if (i2 != size) {
                list.subList(i2, size).clear();
            }
        } else {
            Iterator<Integer> it2 = list.iterator();
            while (it2.hasNext()) {
                int iIntValue2 = it2.next().intValue();
                if (!zzrhVar.zzb(iIntValue2)) {
                    ub = (UB) zza(i, iIntValue2, ub, zztrVar);
                    it2.remove();
                }
            }
        }
        return ub;
    }

    static <UT, UB> UB zza(int i, int i2, UB ub, zztr<UT, UB> zztrVar) {
        if (ub == null) {
            ub = zztrVar.zzri();
        }
        zztrVar.zza((zztr<UT, UB>) ub, i, i2);
        return ub;
    }
}
