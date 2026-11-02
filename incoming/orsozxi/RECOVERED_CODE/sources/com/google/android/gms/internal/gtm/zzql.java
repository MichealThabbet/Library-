package com.google.android.gms.internal.gtm;

import com.google.android.gms.internal.gtm.zzrc;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/* loaded from: classes.dex */
final class zzql implements zzum {
    private final zzqj zzawd;

    public static zzql zza(zzqj zzqjVar) {
        return zzqjVar.zzawu != null ? zzqjVar.zzawu : new zzql(zzqjVar);
    }

    private zzql(zzqj zzqjVar) {
        zzqj zzqjVar2 = (zzqj) zzre.zza(zzqjVar, "output");
        this.zzawd = zzqjVar2;
        zzqjVar2.zzawu = this;
    }

    @Override // com.google.android.gms.internal.gtm.zzum
    public final int zzol() {
        return zzrc.zze.zzbbc;
    }

    @Override // com.google.android.gms.internal.gtm.zzum
    public final void zzo(int i, int i2) throws IOException {
        this.zzawd.zzh(i, i2);
    }

    @Override // com.google.android.gms.internal.gtm.zzum
    public final void zzi(int i, long j) throws IOException {
        this.zzawd.zza(i, j);
    }

    @Override // com.google.android.gms.internal.gtm.zzum
    public final void zzj(int i, long j) throws IOException {
        this.zzawd.zzc(i, j);
    }

    @Override // com.google.android.gms.internal.gtm.zzum
    public final void zza(int i, float f) throws IOException {
        this.zzawd.zza(i, f);
    }

    @Override // com.google.android.gms.internal.gtm.zzum
    public final void zza(int i, double d) throws IOException {
        this.zzawd.zza(i, d);
    }

    @Override // com.google.android.gms.internal.gtm.zzum
    public final void zzp(int i, int i2) throws IOException {
        this.zzawd.zze(i, i2);
    }

    @Override // com.google.android.gms.internal.gtm.zzum
    public final void zza(int i, long j) throws IOException {
        this.zzawd.zza(i, j);
    }

    @Override // com.google.android.gms.internal.gtm.zzum
    public final void zze(int i, int i2) throws IOException {
        this.zzawd.zze(i, i2);
    }

    @Override // com.google.android.gms.internal.gtm.zzum
    public final void zzc(int i, long j) throws IOException {
        this.zzawd.zzc(i, j);
    }

    @Override // com.google.android.gms.internal.gtm.zzum
    public final void zzh(int i, int i2) throws IOException {
        this.zzawd.zzh(i, i2);
    }

    @Override // com.google.android.gms.internal.gtm.zzum
    public final void zzb(int i, boolean z) throws IOException {
        this.zzawd.zzb(i, z);
    }

    @Override // com.google.android.gms.internal.gtm.zzum
    public final void zza(int i, String str) throws IOException {
        this.zzawd.zza(i, str);
    }

    @Override // com.google.android.gms.internal.gtm.zzum
    public final void zza(int i, zzps zzpsVar) throws IOException {
        this.zzawd.zza(i, zzpsVar);
    }

    @Override // com.google.android.gms.internal.gtm.zzum
    public final void zzf(int i, int i2) throws IOException {
        this.zzawd.zzf(i, i2);
    }

    @Override // com.google.android.gms.internal.gtm.zzum
    public final void zzg(int i, int i2) throws IOException {
        this.zzawd.zzg(i, i2);
    }

    @Override // com.google.android.gms.internal.gtm.zzum
    public final void zzb(int i, long j) throws IOException {
        this.zzawd.zzb(i, j);
    }

    @Override // com.google.android.gms.internal.gtm.zzum
    public final void zza(int i, Object obj, zzsz zzszVar) throws IOException {
        this.zzawd.zza(i, (zzsk) obj, zzszVar);
    }

    @Override // com.google.android.gms.internal.gtm.zzum
    public final void zzb(int i, Object obj, zzsz zzszVar) throws IOException {
        zzqj zzqjVar = this.zzawd;
        zzqjVar.zzd(i, 3);
        zzszVar.zza((zzsk) obj, zzqjVar.zzawu);
        zzqjVar.zzd(i, 4);
    }

    @Override // com.google.android.gms.internal.gtm.zzum
    public final void zzbk(int i) throws IOException {
        this.zzawd.zzd(i, 3);
    }

    @Override // com.google.android.gms.internal.gtm.zzum
    public final void zzbl(int i) throws IOException {
        this.zzawd.zzd(i, 4);
    }

    @Override // com.google.android.gms.internal.gtm.zzum
    public final void zza(int i, Object obj) throws IOException {
        if (obj instanceof zzps) {
            this.zzawd.zzb(i, (zzps) obj);
        } else {
            this.zzawd.zzb(i, (zzsk) obj);
        }
    }

    @Override // com.google.android.gms.internal.gtm.zzum
    public final void zza(int i, List<Integer> list, boolean z) throws IOException {
        int i2 = 0;
        if (z) {
            this.zzawd.zzd(i, 2);
            int iZzbc = 0;
            for (int i3 = 0; i3 < list.size(); i3++) {
                iZzbc += zzqj.zzbc(list.get(i3).intValue());
            }
            this.zzawd.zzay(iZzbc);
            while (i2 < list.size()) {
                this.zzawd.zzax(list.get(i2).intValue());
                i2++;
            }
            return;
        }
        while (i2 < list.size()) {
            this.zzawd.zze(i, list.get(i2).intValue());
            i2++;
        }
    }

    @Override // com.google.android.gms.internal.gtm.zzum
    public final void zzb(int i, List<Integer> list, boolean z) throws IOException {
        int i2 = 0;
        if (z) {
            this.zzawd.zzd(i, 2);
            int iZzbf = 0;
            for (int i3 = 0; i3 < list.size(); i3++) {
                iZzbf += zzqj.zzbf(list.get(i3).intValue());
            }
            this.zzawd.zzay(iZzbf);
            while (i2 < list.size()) {
                this.zzawd.zzba(list.get(i2).intValue());
                i2++;
            }
            return;
        }
        while (i2 < list.size()) {
            this.zzawd.zzh(i, list.get(i2).intValue());
            i2++;
        }
    }

    @Override // com.google.android.gms.internal.gtm.zzum
    public final void zzc(int i, List<Long> list, boolean z) throws IOException {
        int i2 = 0;
        if (z) {
            this.zzawd.zzd(i, 2);
            int iZzs = 0;
            for (int i3 = 0; i3 < list.size(); i3++) {
                iZzs += zzqj.zzs(list.get(i3).longValue());
            }
            this.zzawd.zzay(iZzs);
            while (i2 < list.size()) {
                this.zzawd.zzp(list.get(i2).longValue());
                i2++;
            }
            return;
        }
        while (i2 < list.size()) {
            this.zzawd.zza(i, list.get(i2).longValue());
            i2++;
        }
    }

    @Override // com.google.android.gms.internal.gtm.zzum
    public final void zzd(int i, List<Long> list, boolean z) throws IOException {
        int i2 = 0;
        if (z) {
            this.zzawd.zzd(i, 2);
            int iZzt = 0;
            for (int i3 = 0; i3 < list.size(); i3++) {
                iZzt += zzqj.zzt(list.get(i3).longValue());
            }
            this.zzawd.zzay(iZzt);
            while (i2 < list.size()) {
                this.zzawd.zzp(list.get(i2).longValue());
                i2++;
            }
            return;
        }
        while (i2 < list.size()) {
            this.zzawd.zza(i, list.get(i2).longValue());
            i2++;
        }
    }

    @Override // com.google.android.gms.internal.gtm.zzum
    public final void zze(int i, List<Long> list, boolean z) throws IOException {
        int i2 = 0;
        if (z) {
            this.zzawd.zzd(i, 2);
            int iZzv = 0;
            for (int i3 = 0; i3 < list.size(); i3++) {
                iZzv += zzqj.zzv(list.get(i3).longValue());
            }
            this.zzawd.zzay(iZzv);
            while (i2 < list.size()) {
                this.zzawd.zzr(list.get(i2).longValue());
                i2++;
            }
            return;
        }
        while (i2 < list.size()) {
            this.zzawd.zzc(i, list.get(i2).longValue());
            i2++;
        }
    }

    @Override // com.google.android.gms.internal.gtm.zzum
    public final void zzf(int i, List<Float> list, boolean z) throws IOException {
        int i2 = 0;
        if (z) {
            this.zzawd.zzd(i, 2);
            int iZzb = 0;
            for (int i3 = 0; i3 < list.size(); i3++) {
                iZzb += zzqj.zzb(list.get(i3).floatValue());
            }
            this.zzawd.zzay(iZzb);
            while (i2 < list.size()) {
                this.zzawd.zza(list.get(i2).floatValue());
                i2++;
            }
            return;
        }
        while (i2 < list.size()) {
            this.zzawd.zza(i, list.get(i2).floatValue());
            i2++;
        }
    }

    @Override // com.google.android.gms.internal.gtm.zzum
    public final void zzg(int i, List<Double> list, boolean z) throws IOException {
        int i2 = 0;
        if (z) {
            this.zzawd.zzd(i, 2);
            int iZzc = 0;
            for (int i3 = 0; i3 < list.size(); i3++) {
                iZzc += zzqj.zzc(list.get(i3).doubleValue());
            }
            this.zzawd.zzay(iZzc);
            while (i2 < list.size()) {
                this.zzawd.zzb(list.get(i2).doubleValue());
                i2++;
            }
            return;
        }
        while (i2 < list.size()) {
            this.zzawd.zza(i, list.get(i2).doubleValue());
            i2++;
        }
    }

    @Override // com.google.android.gms.internal.gtm.zzum
    public final void zzh(int i, List<Integer> list, boolean z) throws IOException {
        int i2 = 0;
        if (z) {
            this.zzawd.zzd(i, 2);
            int iZzbh = 0;
            for (int i3 = 0; i3 < list.size(); i3++) {
                iZzbh += zzqj.zzbh(list.get(i3).intValue());
            }
            this.zzawd.zzay(iZzbh);
            while (i2 < list.size()) {
                this.zzawd.zzax(list.get(i2).intValue());
                i2++;
            }
            return;
        }
        while (i2 < list.size()) {
            this.zzawd.zze(i, list.get(i2).intValue());
            i2++;
        }
    }

    @Override // com.google.android.gms.internal.gtm.zzum
    public final void zzi(int i, List<Boolean> list, boolean z) throws IOException {
        int i2 = 0;
        if (z) {
            this.zzawd.zzd(i, 2);
            int iZzj = 0;
            for (int i3 = 0; i3 < list.size(); i3++) {
                iZzj += zzqj.zzj(list.get(i3).booleanValue());
            }
            this.zzawd.zzay(iZzj);
            while (i2 < list.size()) {
                this.zzawd.zzi(list.get(i2).booleanValue());
                i2++;
            }
            return;
        }
        while (i2 < list.size()) {
            this.zzawd.zzb(i, list.get(i2).booleanValue());
            i2++;
        }
    }

    @Override // com.google.android.gms.internal.gtm.zzum
    public final void zza(int i, List<String> list) throws IOException {
        int i2 = 0;
        if (list instanceof zzrt) {
            zzrt zzrtVar = (zzrt) list;
            while (i2 < list.size()) {
                Object objZzbn = zzrtVar.zzbn(i2);
                if (objZzbn instanceof String) {
                    this.zzawd.zza(i, (String) objZzbn);
                } else {
                    this.zzawd.zza(i, (zzps) objZzbn);
                }
                i2++;
            }
            return;
        }
        while (i2 < list.size()) {
            this.zzawd.zza(i, list.get(i2));
            i2++;
        }
    }

    @Override // com.google.android.gms.internal.gtm.zzum
    public final void zzb(int i, List<zzps> list) throws IOException {
        for (int i2 = 0; i2 < list.size(); i2++) {
            this.zzawd.zza(i, list.get(i2));
        }
    }

    @Override // com.google.android.gms.internal.gtm.zzum
    public final void zzj(int i, List<Integer> list, boolean z) throws IOException {
        int i2 = 0;
        if (z) {
            this.zzawd.zzd(i, 2);
            int iZzbd = 0;
            for (int i3 = 0; i3 < list.size(); i3++) {
                iZzbd += zzqj.zzbd(list.get(i3).intValue());
            }
            this.zzawd.zzay(iZzbd);
            while (i2 < list.size()) {
                this.zzawd.zzay(list.get(i2).intValue());
                i2++;
            }
            return;
        }
        while (i2 < list.size()) {
            this.zzawd.zzf(i, list.get(i2).intValue());
            i2++;
        }
    }

    @Override // com.google.android.gms.internal.gtm.zzum
    public final void zzk(int i, List<Integer> list, boolean z) throws IOException {
        int i2 = 0;
        if (z) {
            this.zzawd.zzd(i, 2);
            int iZzbg = 0;
            for (int i3 = 0; i3 < list.size(); i3++) {
                iZzbg += zzqj.zzbg(list.get(i3).intValue());
            }
            this.zzawd.zzay(iZzbg);
            while (i2 < list.size()) {
                this.zzawd.zzba(list.get(i2).intValue());
                i2++;
            }
            return;
        }
        while (i2 < list.size()) {
            this.zzawd.zzh(i, list.get(i2).intValue());
            i2++;
        }
    }

    @Override // com.google.android.gms.internal.gtm.zzum
    public final void zzl(int i, List<Long> list, boolean z) throws IOException {
        int i2 = 0;
        if (z) {
            this.zzawd.zzd(i, 2);
            int iZzw = 0;
            for (int i3 = 0; i3 < list.size(); i3++) {
                iZzw += zzqj.zzw(list.get(i3).longValue());
            }
            this.zzawd.zzay(iZzw);
            while (i2 < list.size()) {
                this.zzawd.zzr(list.get(i2).longValue());
                i2++;
            }
            return;
        }
        while (i2 < list.size()) {
            this.zzawd.zzc(i, list.get(i2).longValue());
            i2++;
        }
    }

    @Override // com.google.android.gms.internal.gtm.zzum
    public final void zzm(int i, List<Integer> list, boolean z) throws IOException {
        int i2 = 0;
        if (z) {
            this.zzawd.zzd(i, 2);
            int iZzbe = 0;
            for (int i3 = 0; i3 < list.size(); i3++) {
                iZzbe += zzqj.zzbe(list.get(i3).intValue());
            }
            this.zzawd.zzay(iZzbe);
            while (i2 < list.size()) {
                this.zzawd.zzaz(list.get(i2).intValue());
                i2++;
            }
            return;
        }
        while (i2 < list.size()) {
            this.zzawd.zzg(i, list.get(i2).intValue());
            i2++;
        }
    }

    @Override // com.google.android.gms.internal.gtm.zzum
    public final void zzn(int i, List<Long> list, boolean z) throws IOException {
        int i2 = 0;
        if (z) {
            this.zzawd.zzd(i, 2);
            int iZzu = 0;
            for (int i3 = 0; i3 < list.size(); i3++) {
                iZzu += zzqj.zzu(list.get(i3).longValue());
            }
            this.zzawd.zzay(iZzu);
            while (i2 < list.size()) {
                this.zzawd.zzq(list.get(i2).longValue());
                i2++;
            }
            return;
        }
        while (i2 < list.size()) {
            this.zzawd.zzb(i, list.get(i2).longValue());
            i2++;
        }
    }

    @Override // com.google.android.gms.internal.gtm.zzum
    public final void zza(int i, List<?> list, zzsz zzszVar) throws IOException {
        for (int i2 = 0; i2 < list.size(); i2++) {
            zza(i, list.get(i2), zzszVar);
        }
    }

    @Override // com.google.android.gms.internal.gtm.zzum
    public final void zzb(int i, List<?> list, zzsz zzszVar) throws IOException {
        for (int i2 = 0; i2 < list.size(); i2++) {
            zzb(i, list.get(i2), zzszVar);
        }
    }

    @Override // com.google.android.gms.internal.gtm.zzum
    public final <K, V> void zza(int i, zzsd<K, V> zzsdVar, Map<K, V> map) throws IOException {
        for (Map.Entry<K, V> entry : map.entrySet()) {
            this.zzawd.zzd(i, 2);
            this.zzawd.zzay(zzsc.zza(zzsdVar, entry.getKey(), entry.getValue()));
            zzsc.zza(this.zzawd, zzsdVar, entry.getKey(), entry.getValue());
        }
    }
}
