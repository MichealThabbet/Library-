package com.google.android.gms.internal.measurement;

import java.util.List;

/* compiled from: com.google.android.gms:play-services-measurement@@19.0.0 */
/* loaded from: classes.dex */
public final class zzbi extends zzaw {
    protected zzbi() {
        this.zza.add(zzbl.ADD);
        this.zza.add(zzbl.DIVIDE);
        this.zza.add(zzbl.MODULUS);
        this.zza.add(zzbl.MULTIPLY);
        this.zza.add(zzbl.NEGATE);
        this.zza.add(zzbl.POST_DECREMENT);
        this.zza.add(zzbl.POST_INCREMENT);
        this.zza.add(zzbl.PRE_DECREMENT);
        this.zza.add(zzbl.PRE_INCREMENT);
        this.zza.add(zzbl.SUBTRACT);
    }

    @Override // com.google.android.gms.internal.measurement.zzaw
    public final zzap zza(String str, zzg zzgVar, List<zzap> list) {
        zzbl zzblVar = zzbl.ADD;
        int iOrdinal = zzh.zze(str).ordinal();
        if (iOrdinal == 0) {
            zzh.zza(zzbl.ADD.name(), 2, list);
            zzap zzapVarZza = zzgVar.zza(list.get(0));
            zzap zzapVarZza2 = zzgVar.zza(list.get(1));
            if (!(zzapVarZza instanceof zzal) && !(zzapVarZza instanceof zzat) && !(zzapVarZza2 instanceof zzal) && !(zzapVarZza2 instanceof zzat)) {
                return new zzah(Double.valueOf(zzapVarZza.zzd().doubleValue() + zzapVarZza2.zzd().doubleValue()));
            }
            String strValueOf = String.valueOf(zzapVarZza.zzc());
            String strValueOf2 = String.valueOf(zzapVarZza2.zzc());
            return new zzat(strValueOf2.length() != 0 ? strValueOf.concat(strValueOf2) : new String(strValueOf));
        }
        if (iOrdinal == 21) {
            zzh.zza(zzbl.DIVIDE.name(), 2, list);
            return new zzah(Double.valueOf(zzgVar.zza(list.get(0)).zzd().doubleValue() / zzgVar.zza(list.get(1)).zzd().doubleValue()));
        }
        if (iOrdinal == 59) {
            zzh.zza(zzbl.SUBTRACT.name(), 2, list);
            return new zzah(Double.valueOf(zzgVar.zza(list.get(0)).zzd().doubleValue() + new zzah(Double.valueOf(-zzgVar.zza(list.get(1)).zzd().doubleValue())).zzd().doubleValue()));
        }
        if (iOrdinal == 52 || iOrdinal == 53) {
            zzh.zza(str, 2, list);
            zzap zzapVarZza3 = zzgVar.zza(list.get(0));
            zzgVar.zza(list.get(1));
            return zzapVarZza3;
        }
        if (iOrdinal == 55 || iOrdinal == 56) {
            zzh.zza(str, 1, list);
            return zzgVar.zza(list.get(0));
        }
        switch (iOrdinal) {
            case 44:
                zzh.zza(zzbl.MODULUS.name(), 2, list);
                return new zzah(Double.valueOf(zzgVar.zza(list.get(0)).zzd().doubleValue() % zzgVar.zza(list.get(1)).zzd().doubleValue()));
            case 45:
                zzh.zza(zzbl.MULTIPLY.name(), 2, list);
                return new zzah(Double.valueOf(zzgVar.zza(list.get(0)).zzd().doubleValue() * zzgVar.zza(list.get(1)).zzd().doubleValue()));
            case 46:
                zzh.zza(zzbl.NEGATE.name(), 1, list);
                return new zzah(Double.valueOf(-zzgVar.zza(list.get(0)).zzd().doubleValue()));
            default:
                return super.zzb(str);
        }
    }
}
