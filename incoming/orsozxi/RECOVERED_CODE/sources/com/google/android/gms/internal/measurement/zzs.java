package com.google.android.gms.internal.measurement;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/* compiled from: com.google.android.gms:play-services-measurement@@19.0.0 */
/* loaded from: classes.dex */
final class zzs extends zzai {
    final boolean zza;
    final boolean zzb;
    final /* synthetic */ zzt zzc;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public zzs(zzt zztVar, boolean z, boolean z2) {
        super("log");
        this.zzc = zztVar;
        this.zza = z;
        this.zzb = z2;
    }

    @Override // com.google.android.gms.internal.measurement.zzai
    public final zzap zza(zzg zzgVar, List<zzap> list) {
        zzh.zzb("log", 1, list);
        if (list.size() == 1) {
            this.zzc.zza.zza(3, zzgVar.zza(list.get(0)).zzc(), Collections.emptyList(), this.zza, this.zzb);
            return zzf;
        }
        int iZzg = zzh.zzg(zzgVar.zza(list.get(0)).zzd().doubleValue());
        int i = iZzg != 2 ? iZzg != 3 ? iZzg != 5 ? iZzg != 6 ? 3 : 2 : 5 : 1 : 4;
        String strZzc = zzgVar.zza(list.get(1)).zzc();
        if (list.size() == 2) {
            this.zzc.zza.zza(i, strZzc, Collections.emptyList(), this.zza, this.zzb);
            return zzf;
        }
        ArrayList arrayList = new ArrayList();
        for (int i2 = 2; i2 < Math.min(list.size(), 5); i2++) {
            arrayList.add(zzgVar.zza(list.get(i2)).zzc());
        }
        this.zzc.zza.zza(i, strZzc, arrayList, this.zza, this.zzb);
        return zzf;
    }
}
