package com.google.android.gms.internal.gtm;

import com.google.android.gms.internal.gtm.zzc;
import com.google.android.gms.internal.gtm.zzg;
import com.google.android.gms.tagmanager.zzgj;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/* loaded from: classes.dex */
public final class zzor {
    public static zzov zza(zzi zziVar) throws zzoz {
        zzl[] zzlVarArr = new zzl[zziVar.zzpj.length];
        for (int i = 0; i < zziVar.zzpj.length; i++) {
            zza(i, zziVar, zzlVarArr, new HashSet(0));
        }
        zzow zzowVarZzmn = zzov.zzmn();
        ArrayList arrayList = new ArrayList();
        for (int i2 = 0; i2 < zziVar.zzpm.length; i2++) {
            arrayList.add(zza(zziVar.zzpm[i2], zziVar, zzlVarArr, i2));
        }
        ArrayList arrayList2 = new ArrayList();
        for (int i3 = 0; i3 < zziVar.zzpn.length; i3++) {
            arrayList2.add(zza(zziVar.zzpn[i3], zziVar, zzlVarArr, i3));
        }
        ArrayList arrayList3 = new ArrayList();
        for (int i4 = 0; i4 < zziVar.zzpl.length; i4++) {
            zzot zzotVarZza = zza(zziVar.zzpl[i4], zziVar, zzlVarArr, i4);
            zzowVarZzmn.zzc(zzotVarZza);
            arrayList3.add(zzotVarZza);
        }
        for (zzc.zze zzeVar : zziVar.zzpo) {
            zzoy zzoyVar = new zzoy();
            Iterator<Integer> it2 = zzeVar.zzn().iterator();
            while (it2.hasNext()) {
                zzoyVar.zzd((zzot) arrayList2.get(it2.next().intValue()));
            }
            Iterator<Integer> it3 = zzeVar.zzo().iterator();
            while (it3.hasNext()) {
                zzoyVar.zze((zzot) arrayList2.get(it3.next().intValue()));
            }
            Iterator<Integer> it4 = zzeVar.zzp().iterator();
            while (it4.hasNext()) {
                zzoyVar.zzf((zzot) arrayList.get(it4.next().intValue()));
            }
            Iterator<Integer> it5 = zzeVar.zzr().iterator();
            while (it5.hasNext()) {
                zzoyVar.zzct(zziVar.zzpj[it5.next().intValue()].string);
            }
            Iterator<Integer> it6 = zzeVar.zzq().iterator();
            while (it6.hasNext()) {
                zzoyVar.zzg((zzot) arrayList.get(it6.next().intValue()));
            }
            Iterator<Integer> it7 = zzeVar.zzs().iterator();
            while (it7.hasNext()) {
                zzoyVar.zzcu(zziVar.zzpj[it7.next().intValue()].string);
            }
            Iterator<Integer> it8 = zzeVar.zzt().iterator();
            while (it8.hasNext()) {
                zzoyVar.zzh((zzot) arrayList3.get(it8.next().intValue()));
            }
            Iterator<Integer> it9 = zzeVar.zzv().iterator();
            while (it9.hasNext()) {
                zzoyVar.zzcv(zziVar.zzpj[it9.next().intValue()].string);
            }
            Iterator<Integer> it10 = zzeVar.zzu().iterator();
            while (it10.hasNext()) {
                zzoyVar.zzi((zzot) arrayList3.get(it10.next().intValue()));
            }
            Iterator<Integer> it11 = zzeVar.zzw().iterator();
            while (it11.hasNext()) {
                zzoyVar.zzcw(zziVar.zzpj[it11.next().intValue()].string);
            }
            zzowVarZzmn.zzb(zzoyVar.zzms());
        }
        zzowVarZzmn.zzcs(zziVar.version);
        zzowVarZzmn.zzaf(zziVar.zzpw);
        return zzowVarZzmn.zzmp();
    }

    public static zzl zzk(zzl zzlVar) {
        zzl zzlVar2 = new zzl();
        zzlVar2.type = zzlVar.type;
        zzlVar2.zzqv = (int[]) zzlVar.zzqv.clone();
        if (zzlVar.zzqw) {
            zzlVar2.zzqw = zzlVar.zzqw;
        }
        return zzlVar2;
    }

    private static zzl zza(int i, zzi zziVar, zzl[] zzlVarArr, Set<Integer> set) throws zzoz {
        if (set.contains(Integer.valueOf(i))) {
            String strValueOf = String.valueOf(set);
            StringBuilder sb = new StringBuilder(String.valueOf(strValueOf).length() + 90);
            sb.append("Value cycle detected.  Current value reference: ");
            sb.append(i);
            sb.append(".  Previous value references: ");
            sb.append(strValueOf);
            sb.append(".");
            zzcf(sb.toString());
        }
        zzl zzlVar = (zzl) zza(zziVar.zzpj, i, "values");
        if (zzlVarArr[i] != null) {
            return zzlVarArr[i];
        }
        zzl zzlVarZzk = null;
        set.add(Integer.valueOf(i));
        int i2 = 0;
        switch (zzlVar.type) {
            case 1:
            case 5:
            case 6:
            case 8:
                zzlVarZzk = zzlVar;
                break;
            case 2:
                zzg.zza zzaVarZzl = zzl(zzlVar);
                zzl zzlVarZzk2 = zzk(zzlVar);
                zzlVarZzk2.zzqn = new zzl[zzaVarZzl.zzpz.length];
                int[] iArr = zzaVarZzl.zzpz;
                int length = iArr.length;
                int i3 = 0;
                while (i2 < length) {
                    int i4 = i3 + 1;
                    zzlVarZzk2.zzqn[i3] = zza(iArr[i2], zziVar, zzlVarArr, set);
                    i2++;
                    i3 = i4;
                }
                zzlVarZzk = zzlVarZzk2;
                break;
            case 3:
                zzlVarZzk = zzk(zzlVar);
                zzg.zza zzaVarZzl2 = zzl(zzlVar);
                if (zzaVarZzl2.zzqa.length != zzaVarZzl2.zzqb.length) {
                    int length2 = zzaVarZzl2.zzqa.length;
                    int length3 = zzaVarZzl2.zzqb.length;
                    StringBuilder sb2 = new StringBuilder(58);
                    sb2.append("Uneven map keys (");
                    sb2.append(length2);
                    sb2.append(") and map values (");
                    sb2.append(length3);
                    sb2.append(")");
                    zzcf(sb2.toString());
                }
                zzlVarZzk.zzqo = new zzl[zzaVarZzl2.zzqa.length];
                zzlVarZzk.zzqp = new zzl[zzaVarZzl2.zzqa.length];
                int[] iArr2 = zzaVarZzl2.zzqa;
                int length4 = iArr2.length;
                int i5 = 0;
                int i6 = 0;
                while (i5 < length4) {
                    zzlVarZzk.zzqo[i6] = zza(iArr2[i5], zziVar, zzlVarArr, set);
                    i5++;
                    i6++;
                }
                int[] iArr3 = zzaVarZzl2.zzqb;
                int length5 = iArr3.length;
                int i7 = 0;
                while (i2 < length5) {
                    zzlVarZzk.zzqp[i7] = zza(iArr3[i2], zziVar, zzlVarArr, set);
                    i2++;
                    i7++;
                }
                break;
            case 4:
                zzlVarZzk = zzk(zzlVar);
                zzlVarZzk.zzqq = zzgj.zzc(zza(zzl(zzlVar).zzqe, zziVar, zzlVarArr, set));
                break;
            case 7:
                zzlVarZzk = zzk(zzlVar);
                zzg.zza zzaVarZzl3 = zzl(zzlVar);
                zzlVarZzk.zzqu = new zzl[zzaVarZzl3.zzqd.length];
                int[] iArr4 = zzaVarZzl3.zzqd;
                int length6 = iArr4.length;
                int i8 = 0;
                while (i2 < length6) {
                    zzlVarZzk.zzqu[i8] = zza(iArr4[i2], zziVar, zzlVarArr, set);
                    i2++;
                    i8++;
                }
                break;
        }
        if (zzlVarZzk == null) {
            String strValueOf2 = String.valueOf(zzlVar);
            StringBuilder sb3 = new StringBuilder(String.valueOf(strValueOf2).length() + 15);
            sb3.append("Invalid value: ");
            sb3.append(strValueOf2);
            zzcf(sb3.toString());
        }
        zzlVarArr[i] = zzlVarZzk;
        set.remove(Integer.valueOf(i));
        return zzlVarZzk;
    }

    private static zzg.zza zzl(zzl zzlVar) throws zzoz {
        if (((zzg.zza) zzlVar.zza(zzg.zza.zzpx)) == null) {
            String strValueOf = String.valueOf(zzlVar);
            StringBuilder sb = new StringBuilder(String.valueOf(strValueOf).length() + 54);
            sb.append("Expected a ServingValue and didn't get one. Value is: ");
            sb.append(strValueOf);
            zzcf(sb.toString());
        }
        return (zzg.zza) zzlVar.zza(zzg.zza.zzpx);
    }

    private static void zzcf(String str) throws zzoz {
        com.google.android.gms.tagmanager.zzdi.zzav(str);
        throw new zzoz(str);
    }

    private static <T> T zza(T[] tArr, int i, String str) throws zzoz {
        if (i < 0 || i >= tArr.length) {
            StringBuilder sb = new StringBuilder(String.valueOf(str).length() + 45);
            sb.append("Index out of bounds detected: ");
            sb.append(i);
            sb.append(" in ");
            sb.append(str);
            zzcf(sb.toString());
        }
        return tArr[i];
    }

    private static zzot zza(zzc.zzb zzbVar, zzi zziVar, zzl[] zzlVarArr, int i) throws zzoz {
        zzou zzouVarZzml = zzot.zzml();
        Iterator<Integer> it2 = zzbVar.zze().iterator();
        while (it2.hasNext()) {
            zzc.zzd zzdVar = (zzc.zzd) zza(zziVar.zzpk, it2.next().intValue(), "properties");
            String str = (String) zza(zziVar.zzpi, zzdVar.zzl(), "keys");
            zzl zzlVar = (zzl) zza(zzlVarArr, zzdVar.getValue(), "values");
            if (zzb.PUSH_AFTER_EVALUATE.toString().equals(str)) {
                zzouVarZzml.zzm(zzlVar);
            } else {
                zzouVarZzml.zzb(str, zzlVar);
            }
        }
        return zzouVarZzml.zzmm();
    }

    public static void zza(InputStream inputStream, OutputStream outputStream) throws IOException {
        byte[] bArr = new byte[1024];
        while (true) {
            int i = inputStream.read(bArr);
            if (i == -1) {
                return;
            } else {
                outputStream.write(bArr, 0, i);
            }
        }
    }
}
