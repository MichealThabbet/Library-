package com.google.android.gms.internal.measurement;

import it.piapostolos.android.pinotes.utils.ConstantsBase;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/* compiled from: com.google.android.gms:play-services-measurement@@19.0.0 */
/* loaded from: classes.dex */
public final class zzbb {
    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    public static zzap zza(String str, zzae zzaeVar, zzg zzgVar, List<zzap> list) {
        String str2;
        String str3;
        char c;
        String strZzc;
        zzai zzaiVar;
        switch (str.hashCode()) {
            case -1776922004:
                str2 = "toString";
                str3 = "filter";
                if (str.equals(str2)) {
                    c = 18;
                    break;
                } else {
                    c = 65535;
                    break;
                }
            case -1354795244:
                str3 = "filter";
                if (str.equals("concat")) {
                    str2 = "toString";
                    c = 0;
                    break;
                }
                str2 = "toString";
                c = 65535;
                break;
            case -1274492040:
                str3 = "filter";
                if (str.equals(str3)) {
                    str2 = "toString";
                    c = 2;
                    break;
                }
                str2 = "toString";
                c = 65535;
                break;
            case -934873754:
                if (str.equals("reduce")) {
                    c = '\n';
                    str2 = "toString";
                    str3 = "filter";
                    break;
                }
                str2 = "toString";
                str3 = "filter";
                c = 65535;
                break;
            case -895859076:
                if (str.equals("splice")) {
                    c = 17;
                    str2 = "toString";
                    str3 = "filter";
                    break;
                }
                str2 = "toString";
                str3 = "filter";
                c = 65535;
                break;
            case -678635926:
                if (str.equals("forEach")) {
                    c = 3;
                    str2 = "toString";
                    str3 = "filter";
                    break;
                }
                str2 = "toString";
                str3 = "filter";
                c = 65535;
                break;
            case -467511597:
                if (str.equals("lastIndexOf")) {
                    c = 6;
                    str2 = "toString";
                    str3 = "filter";
                    break;
                }
                str2 = "toString";
                str3 = "filter";
                c = 65535;
                break;
            case -277637751:
                if (str.equals("unshift")) {
                    c = 19;
                    str2 = "toString";
                    str3 = "filter";
                    break;
                }
                str2 = "toString";
                str3 = "filter";
                c = 65535;
                break;
            case 107868:
                if (str.equals("map")) {
                    c = 7;
                    str2 = "toString";
                    str3 = "filter";
                    break;
                }
                str2 = "toString";
                str3 = "filter";
                c = 65535;
                break;
            case 111185:
                if (str.equals("pop")) {
                    c = '\b';
                    str2 = "toString";
                    str3 = "filter";
                    break;
                }
                str2 = "toString";
                str3 = "filter";
                c = 65535;
                break;
            case 3267882:
                if (str.equals("join")) {
                    c = 5;
                    str2 = "toString";
                    str3 = "filter";
                    break;
                }
                str2 = "toString";
                str3 = "filter";
                c = 65535;
                break;
            case 3452698:
                if (str.equals("push")) {
                    c = '\t';
                    str2 = "toString";
                    str3 = "filter";
                    break;
                }
                str2 = "toString";
                str3 = "filter";
                c = 65535;
                break;
            case 3536116:
                if (str.equals("some")) {
                    c = 15;
                    str2 = "toString";
                    str3 = "filter";
                    break;
                }
                str2 = "toString";
                str3 = "filter";
                c = 65535;
                break;
            case 3536286:
                if (str.equals("sort")) {
                    c = 16;
                    str2 = "toString";
                    str3 = "filter";
                    break;
                }
                str2 = "toString";
                str3 = "filter";
                c = 65535;
                break;
            case 96891675:
                if (str.equals("every")) {
                    str2 = "toString";
                    str3 = "filter";
                    c = 1;
                    break;
                }
                str2 = "toString";
                str3 = "filter";
                c = 65535;
                break;
            case 109407362:
                if (str.equals("shift")) {
                    c = '\r';
                    str2 = "toString";
                    str3 = "filter";
                    break;
                }
                str2 = "toString";
                str3 = "filter";
                c = 65535;
                break;
            case 109526418:
                if (str.equals("slice")) {
                    c = 14;
                    str2 = "toString";
                    str3 = "filter";
                    break;
                }
                str2 = "toString";
                str3 = "filter";
                c = 65535;
                break;
            case 965561430:
                if (str.equals("reduceRight")) {
                    c = 11;
                    str2 = "toString";
                    str3 = "filter";
                    break;
                }
                str2 = "toString";
                str3 = "filter";
                c = 65535;
                break;
            case 1099846370:
                if (str.equals("reverse")) {
                    c = '\f';
                    str2 = "toString";
                    str3 = "filter";
                    break;
                }
                str2 = "toString";
                str3 = "filter";
                c = 65535;
                break;
            case 1943291465:
                if (str.equals("indexOf")) {
                    c = 4;
                    str2 = "toString";
                    str3 = "filter";
                    break;
                }
                str2 = "toString";
                str3 = "filter";
                c = 65535;
                break;
            default:
                str2 = "toString";
                str3 = "filter";
                c = 65535;
                break;
        }
        String str4 = str2;
        String str5 = str3;
        double dZzh = 0.0d;
        switch (c) {
            case 0:
                zzap zzapVarZzt = zzaeVar.zzt();
                if (!list.isEmpty()) {
                    Iterator<zzap> it2 = list.iterator();
                    while (it2.hasNext()) {
                        zzap zzapVarZza = zzgVar.zza(it2.next());
                        if (zzapVarZza instanceof zzag) {
                            throw new IllegalStateException("Failed evaluation of arguments");
                        }
                        zzae zzaeVar2 = (zzae) zzapVarZzt;
                        int iZzh = zzaeVar2.zzh();
                        if (zzapVarZza instanceof zzae) {
                            zzae zzaeVar3 = (zzae) zzapVarZza;
                            Iterator<Integer> itZzg = zzaeVar3.zzg();
                            while (itZzg.hasNext()) {
                                Integer next = itZzg.next();
                                zzaeVar2.zzn(next.intValue() + iZzh, zzaeVar3.zzl(next.intValue()));
                            }
                        } else {
                            zzaeVar2.zzn(iZzh, zzapVarZza);
                        }
                    }
                }
                return zzapVarZzt;
            case 1:
                zzh.zza("every", 1, list);
                zzap zzapVarZza2 = zzgVar.zza(list.get(0));
                if (!(zzapVarZza2 instanceof zzao)) {
                    throw new IllegalArgumentException("Callback should be a method");
                }
                if (zzaeVar.zzh() != 0 && zzc(zzaeVar, zzgVar, (zzao) zzapVarZza2, false, true).zzh() != zzaeVar.zzh()) {
                    return zzap.zzl;
                }
                return zzap.zzk;
            case 2:
                zzh.zza(str5, 1, list);
                zzap zzapVarZza3 = zzgVar.zza(list.get(0));
                if (!(zzapVarZza3 instanceof zzao)) {
                    throw new IllegalArgumentException("Callback should be a method");
                }
                if (zzaeVar.zzi() == 0) {
                    return new zzae();
                }
                zzap zzapVarZzt2 = zzaeVar.zzt();
                zzae zzaeVarZzc = zzc(zzaeVar, zzgVar, (zzao) zzapVarZza3, null, true);
                zzae zzaeVar4 = new zzae();
                Iterator<Integer> itZzg2 = zzaeVarZzc.zzg();
                while (itZzg2.hasNext()) {
                    zzaeVar4.zzn(zzaeVar4.zzh(), ((zzae) zzapVarZzt2).zzl(itZzg2.next().intValue()));
                }
                return zzaeVar4;
            case 3:
                zzh.zza("forEach", 1, list);
                zzap zzapVarZza4 = zzgVar.zza(list.get(0));
                if (!(zzapVarZza4 instanceof zzao)) {
                    throw new IllegalArgumentException("Callback should be a method");
                }
                if (zzaeVar.zzi() == 0) {
                    return zzap.zzf;
                }
                zzc(zzaeVar, zzgVar, (zzao) zzapVarZza4, null, null);
                return zzap.zzf;
            case 4:
                zzh.zzc("indexOf", 2, list);
                zzap zzapVarZza5 = zzap.zzf;
                if (!list.isEmpty()) {
                    zzapVarZza5 = zzgVar.zza(list.get(0));
                }
                if (list.size() > 1) {
                    double dZzi = zzh.zzi(zzgVar.zza(list.get(1)).zzd().doubleValue());
                    if (dZzi >= zzaeVar.zzh()) {
                        return new zzah(Double.valueOf(-1.0d));
                    }
                    dZzh = dZzi < 0.0d ? zzaeVar.zzh() + dZzi : dZzi;
                }
                Iterator<Integer> itZzg3 = zzaeVar.zzg();
                while (itZzg3.hasNext()) {
                    int iIntValue = itZzg3.next().intValue();
                    double d = iIntValue;
                    if (d >= dZzh && zzh.zzf(zzaeVar.zzl(iIntValue), zzapVarZza5)) {
                        return new zzah(Double.valueOf(d));
                    }
                }
                return new zzah(Double.valueOf(-1.0d));
            case 5:
                zzh.zzc("join", 1, list);
                if (zzaeVar.zzh() == 0) {
                    return zzap.zzm;
                }
                if (list.size() > 0) {
                    zzap zzapVarZza6 = zzgVar.zza(list.get(0));
                    strZzc = ((zzapVarZza6 instanceof zzan) || (zzapVarZza6 instanceof zzau)) ? "" : zzapVarZza6.zzc();
                } else {
                    strZzc = ConstantsBase.PROPERTIES_PARAMS_SEPARATOR;
                }
                return new zzat(zzaeVar.zzs(strZzc));
            case 6:
                zzh.zzc("lastIndexOf", 2, list);
                zzap zzapVarZza7 = zzap.zzf;
                if (!list.isEmpty()) {
                    zzapVarZza7 = zzgVar.zza(list.get(0));
                }
                double dZzh2 = zzaeVar.zzh() - 1;
                if (list.size() > 1) {
                    zzap zzapVarZza8 = zzgVar.zza(list.get(1));
                    dZzh2 = Double.isNaN(zzapVarZza8.zzd().doubleValue()) ? zzaeVar.zzh() - 1 : zzh.zzi(zzapVarZza8.zzd().doubleValue());
                    if (dZzh2 < 0.0d) {
                        dZzh2 += zzaeVar.zzh();
                    }
                }
                if (dZzh2 < 0.0d) {
                    return new zzah(Double.valueOf(-1.0d));
                }
                for (int iMin = (int) Math.min(zzaeVar.zzh(), dZzh2); iMin >= 0; iMin--) {
                    if (zzaeVar.zzo(iMin) && zzh.zzf(zzaeVar.zzl(iMin), zzapVarZza7)) {
                        return new zzah(Double.valueOf(iMin));
                    }
                }
                return new zzah(Double.valueOf(-1.0d));
            case 7:
                zzh.zza("map", 1, list);
                zzap zzapVarZza9 = zzgVar.zza(list.get(0));
                if (zzapVarZza9 instanceof zzao) {
                    return zzaeVar.zzh() == 0 ? new zzae() : zzc(zzaeVar, zzgVar, (zzao) zzapVarZza9, null, null);
                }
                throw new IllegalArgumentException("Callback should be a method");
            case '\b':
                zzh.zza("pop", 0, list);
                int iZzh2 = zzaeVar.zzh();
                if (iZzh2 == 0) {
                    return zzap.zzf;
                }
                int i = iZzh2 - 1;
                zzap zzapVarZzl = zzaeVar.zzl(i);
                zzaeVar.zzr(i);
                return zzapVarZzl;
            case '\t':
                if (!list.isEmpty()) {
                    Iterator<zzap> it3 = list.iterator();
                    while (it3.hasNext()) {
                        zzaeVar.zzn(zzaeVar.zzh(), zzgVar.zza(it3.next()));
                    }
                }
                return new zzah(Double.valueOf(zzaeVar.zzh()));
            case '\n':
                return zzb(zzaeVar, zzgVar, list, true);
            case 11:
                return zzb(zzaeVar, zzgVar, list, false);
            case '\f':
                zzh.zza("reverse", 0, list);
                int iZzh3 = zzaeVar.zzh();
                if (iZzh3 != 0) {
                    for (int i2 = 0; i2 < iZzh3 / 2; i2++) {
                        if (zzaeVar.zzo(i2)) {
                            zzap zzapVarZzl2 = zzaeVar.zzl(i2);
                            zzaeVar.zzn(i2, null);
                            int i3 = (iZzh3 - 1) - i2;
                            if (zzaeVar.zzo(i3)) {
                                zzaeVar.zzn(i2, zzaeVar.zzl(i3));
                            }
                            zzaeVar.zzn(i3, zzapVarZzl2);
                        }
                    }
                }
                return zzaeVar;
            case '\r':
                zzh.zza("shift", 0, list);
                if (zzaeVar.zzh() == 0) {
                    return zzap.zzf;
                }
                zzap zzapVarZzl3 = zzaeVar.zzl(0);
                zzaeVar.zzr(0);
                return zzapVarZzl3;
            case 14:
                zzh.zzc("slice", 2, list);
                if (list.isEmpty()) {
                    return zzaeVar.zzt();
                }
                double dZzh3 = zzaeVar.zzh();
                double dZzi2 = zzh.zzi(zzgVar.zza(list.get(0)).zzd().doubleValue());
                double dMax = dZzi2 < 0.0d ? Math.max(dZzi2 + dZzh3, 0.0d) : Math.min(dZzi2, dZzh3);
                if (list.size() == 2) {
                    double dZzi3 = zzh.zzi(zzgVar.zza(list.get(1)).zzd().doubleValue());
                    dZzh3 = dZzi3 < 0.0d ? Math.max(dZzh3 + dZzi3, 0.0d) : Math.min(dZzh3, dZzi3);
                }
                zzae zzaeVar5 = new zzae();
                for (int i4 = (int) dMax; i4 < dZzh3; i4++) {
                    zzaeVar5.zzn(zzaeVar5.zzh(), zzaeVar.zzl(i4));
                }
                return zzaeVar5;
            case 15:
                zzh.zza("some", 1, list);
                zzap zzapVarZza10 = zzgVar.zza(list.get(0));
                if (!(zzapVarZza10 instanceof zzai)) {
                    throw new IllegalArgumentException("Callback should be a method");
                }
                if (zzaeVar.zzh() == 0) {
                    return zzap.zzl;
                }
                zzai zzaiVar2 = (zzai) zzapVarZza10;
                Iterator<Integer> itZzg4 = zzaeVar.zzg();
                while (itZzg4.hasNext()) {
                    int iIntValue2 = itZzg4.next().intValue();
                    if (zzaeVar.zzo(iIntValue2) && zzaiVar2.zza(zzgVar, Arrays.asList(zzaeVar.zzl(iIntValue2), new zzah(Double.valueOf(iIntValue2)), zzaeVar)).zze().booleanValue()) {
                        return zzap.zzk;
                    }
                }
                return zzap.zzl;
            case 16:
                zzh.zzc("sort", 1, list);
                if (zzaeVar.zzh() >= 2) {
                    List<zzap> listZzb = zzaeVar.zzb();
                    if (list.isEmpty()) {
                        zzaiVar = null;
                    } else {
                        zzap zzapVarZza11 = zzgVar.zza(list.get(0));
                        if (!(zzapVarZza11 instanceof zzai)) {
                            throw new IllegalArgumentException("Comparator should be a method");
                        }
                        zzaiVar = (zzai) zzapVarZza11;
                    }
                    Collections.sort(listZzb, new zzba(zzaiVar, zzgVar));
                    zzaeVar.zzp();
                    Iterator<zzap> it4 = listZzb.iterator();
                    int i5 = 0;
                    while (it4.hasNext()) {
                        zzaeVar.zzn(i5, it4.next());
                        i5++;
                    }
                }
                return zzaeVar;
            case 17:
                if (list.isEmpty()) {
                    return new zzae();
                }
                int iZzi = (int) zzh.zzi(zzgVar.zza(list.get(0)).zzd().doubleValue());
                if (iZzi < 0) {
                    iZzi = Math.max(0, iZzi + zzaeVar.zzh());
                } else if (iZzi > zzaeVar.zzh()) {
                    iZzi = zzaeVar.zzh();
                }
                int iZzh4 = zzaeVar.zzh();
                zzae zzaeVar6 = new zzae();
                if (list.size() > 1) {
                    int iMax = Math.max(0, (int) zzh.zzi(zzgVar.zza(list.get(1)).zzd().doubleValue()));
                    if (iMax > 0) {
                        for (int i6 = iZzi; i6 < Math.min(iZzh4, iZzi + iMax); i6++) {
                            zzaeVar6.zzn(zzaeVar6.zzh(), zzaeVar.zzl(iZzi));
                            zzaeVar.zzr(iZzi);
                        }
                    }
                    if (list.size() > 2) {
                        for (int i7 = 2; i7 < list.size(); i7++) {
                            zzap zzapVarZza12 = zzgVar.zza(list.get(i7));
                            if (zzapVarZza12 instanceof zzag) {
                                throw new IllegalArgumentException("Failed to parse elements to add");
                            }
                            zzaeVar.zzq((iZzi + i7) - 2, zzapVarZza12);
                        }
                    }
                } else {
                    while (iZzi < iZzh4) {
                        zzaeVar6.zzn(zzaeVar6.zzh(), zzaeVar.zzl(iZzi));
                        zzaeVar.zzn(iZzi, null);
                        iZzi++;
                    }
                }
                return zzaeVar6;
            case 18:
                zzh.zza(str4, 0, list);
                return new zzat(zzaeVar.zzs(ConstantsBase.PROPERTIES_PARAMS_SEPARATOR));
            case 19:
                if (!list.isEmpty()) {
                    zzae zzaeVar7 = new zzae();
                    Iterator<zzap> it5 = list.iterator();
                    while (it5.hasNext()) {
                        zzap zzapVarZza13 = zzgVar.zza(it5.next());
                        if (zzapVarZza13 instanceof zzag) {
                            throw new IllegalStateException("Argument evaluation failed");
                        }
                        zzaeVar7.zzn(zzaeVar7.zzh(), zzapVarZza13);
                    }
                    int iZzh5 = zzaeVar7.zzh();
                    Iterator<Integer> itZzg5 = zzaeVar.zzg();
                    while (itZzg5.hasNext()) {
                        Integer next2 = itZzg5.next();
                        zzaeVar7.zzn(next2.intValue() + iZzh5, zzaeVar.zzl(next2.intValue()));
                    }
                    zzaeVar.zzp();
                    Iterator<Integer> itZzg6 = zzaeVar7.zzg();
                    while (itZzg6.hasNext()) {
                        Integer next3 = itZzg6.next();
                        zzaeVar.zzn(next3.intValue(), zzaeVar7.zzl(next3.intValue()));
                    }
                }
                return new zzah(Double.valueOf(zzaeVar.zzh()));
            default:
                throw new IllegalArgumentException("Command not supported");
        }
    }

    private static zzap zzb(zzae zzaeVar, zzg zzgVar, List<zzap> list, boolean z) {
        zzap zzapVarZza;
        zzh.zzb("reduce", 1, list);
        zzh.zzc("reduce", 2, list);
        zzap zzapVarZza2 = zzgVar.zza(list.get(0));
        if (!(zzapVarZza2 instanceof zzai)) {
            throw new IllegalArgumentException("Callback should be a method");
        }
        if (list.size() == 2) {
            zzapVarZza = zzgVar.zza(list.get(1));
            if (zzapVarZza instanceof zzag) {
                throw new IllegalArgumentException("Failed to parse initial value");
            }
        } else {
            if (zzaeVar.zzh() == 0) {
                throw new IllegalStateException("Empty array with no initial value error");
            }
            zzapVarZza = null;
        }
        zzai zzaiVar = (zzai) zzapVarZza2;
        int iZzh = zzaeVar.zzh();
        int i = z ? 0 : iZzh - 1;
        int i2 = z ? iZzh - 1 : 0;
        int i3 = true == z ? 1 : -1;
        if (zzapVarZza == null) {
            zzapVarZza = zzaeVar.zzl(i);
            i += i3;
        }
        while ((i2 - i) * i3 >= 0) {
            if (zzaeVar.zzo(i)) {
                zzapVarZza = zzaiVar.zza(zzgVar, Arrays.asList(zzapVarZza, zzaeVar.zzl(i), new zzah(Double.valueOf(i)), zzaeVar));
                if (zzapVarZza instanceof zzag) {
                    throw new IllegalStateException("Reduce operation failed");
                }
                i += i3;
            } else {
                i += i3;
            }
        }
        return zzapVarZza;
    }

    private static zzae zzc(zzae zzaeVar, zzg zzgVar, zzai zzaiVar, Boolean bool, Boolean bool2) {
        zzae zzaeVar2 = new zzae();
        Iterator<Integer> itZzg = zzaeVar.zzg();
        while (itZzg.hasNext()) {
            int iIntValue = itZzg.next().intValue();
            if (zzaeVar.zzo(iIntValue)) {
                zzap zzapVarZza = zzaiVar.zza(zzgVar, Arrays.asList(zzaeVar.zzl(iIntValue), new zzah(Double.valueOf(iIntValue)), zzaeVar));
                if (zzapVarZza.zze().equals(bool)) {
                    return zzaeVar2;
                }
                if (bool2 == null || zzapVarZza.zze().equals(bool2)) {
                    zzaeVar2.zzn(iIntValue, zzapVarZza);
                }
            }
        }
        return zzaeVar2;
    }
}
