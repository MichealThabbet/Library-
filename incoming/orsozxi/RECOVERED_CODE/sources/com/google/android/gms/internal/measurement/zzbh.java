package com.google.android.gms.internal.measurement;

import java.util.Iterator;
import java.util.List;

/* compiled from: com.google.android.gms:play-services-measurement@@19.0.0 */
/* loaded from: classes.dex */
public final class zzbh extends zzaw {
    protected zzbh() {
        this.zza.add(zzbl.FOR_IN);
        this.zza.add(zzbl.FOR_IN_CONST);
        this.zza.add(zzbl.FOR_IN_LET);
        this.zza.add(zzbl.FOR_LET);
        this.zza.add(zzbl.FOR_OF);
        this.zza.add(zzbl.FOR_OF_CONST);
        this.zza.add(zzbl.FOR_OF_LET);
        this.zza.add(zzbl.WHILE);
    }

    private static zzap zzc(zzbf zzbfVar, zzap zzapVar, zzap zzapVar2) {
        return zze(zzbfVar, zzapVar.zzf(), zzapVar2);
    }

    private static zzap zzd(zzbf zzbfVar, zzap zzapVar, zzap zzapVar2) {
        if (zzapVar instanceof Iterable) {
            return zze(zzbfVar, ((Iterable) zzapVar).iterator(), zzapVar2);
        }
        throw new IllegalArgumentException("Non-iterable type in for...of loop.");
    }

    private static zzap zze(zzbf zzbfVar, Iterator<zzap> it2, zzap zzapVar) {
        if (it2 != null) {
            while (it2.hasNext()) {
                zzap zzapVarZzb = zzbfVar.zza(it2.next()).zzb((zzae) zzapVar);
                if (zzapVarZzb instanceof zzag) {
                    zzag zzagVar = (zzag) zzapVarZzb;
                    if ("break".equals(zzagVar.zzg())) {
                        return zzap.zzf;
                    }
                    if ("return".equals(zzagVar.zzg())) {
                        return zzagVar;
                    }
                }
            }
        }
        return zzap.zzf;
    }

    @Override // com.google.android.gms.internal.measurement.zzaw
    public final zzap zza(String str, zzg zzgVar, List<zzap> list) {
        zzbl zzblVar = zzbl.ADD;
        int iOrdinal = zzh.zze(str).ordinal();
        if (iOrdinal == 65) {
            zzh.zza(zzbl.WHILE.name(), 4, list);
            zzap zzapVar = list.get(0);
            zzap zzapVar2 = list.get(1);
            zzap zzapVar3 = list.get(2);
            zzap zzapVarZza = zzgVar.zza(list.get(3));
            if (zzgVar.zza(zzapVar3).zze().booleanValue()) {
                zzap zzapVarZzb = zzgVar.zzb((zzae) zzapVarZza);
                if (zzapVarZzb instanceof zzag) {
                    zzag zzagVar = (zzag) zzapVarZzb;
                    if ("break".equals(zzagVar.zzg())) {
                        return zzap.zzf;
                    }
                    if ("return".equals(zzagVar.zzg())) {
                        return zzagVar;
                    }
                }
            }
            while (zzgVar.zza(zzapVar).zze().booleanValue()) {
                zzap zzapVarZzb2 = zzgVar.zzb((zzae) zzapVarZza);
                if (zzapVarZzb2 instanceof zzag) {
                    zzag zzagVar2 = (zzag) zzapVarZzb2;
                    if ("break".equals(zzagVar2.zzg())) {
                        return zzap.zzf;
                    }
                    if ("return".equals(zzagVar2.zzg())) {
                        return zzagVar2;
                    }
                }
                zzgVar.zza(zzapVar2);
            }
            return zzap.zzf;
        }
        switch (iOrdinal) {
            case 26:
                zzh.zza(zzbl.FOR_IN.name(), 3, list);
                if (!(list.get(0) instanceof zzat)) {
                    throw new IllegalArgumentException("Variable name in FOR_IN must be a string");
                }
                String strZzc = list.get(0).zzc();
                return zzc(new zzbg(zzgVar, strZzc), zzgVar.zza(list.get(1)), zzgVar.zza(list.get(2)));
            case 27:
                zzh.zza(zzbl.FOR_IN_CONST.name(), 3, list);
                if (!(list.get(0) instanceof zzat)) {
                    throw new IllegalArgumentException("Variable name in FOR_IN_CONST must be a string");
                }
                String strZzc2 = list.get(0).zzc();
                return zzc(new zzbd(zzgVar, strZzc2), zzgVar.zza(list.get(1)), zzgVar.zza(list.get(2)));
            case 28:
                zzh.zza(zzbl.FOR_IN_LET.name(), 3, list);
                if (!(list.get(0) instanceof zzat)) {
                    throw new IllegalArgumentException("Variable name in FOR_IN_LET must be a string");
                }
                String strZzc3 = list.get(0).zzc();
                return zzc(new zzbe(zzgVar, strZzc3), zzgVar.zza(list.get(1)), zzgVar.zza(list.get(2)));
            case 29:
                zzh.zza(zzbl.FOR_LET.name(), 4, list);
                zzap zzapVarZza2 = zzgVar.zza(list.get(0));
                if (!(zzapVarZza2 instanceof zzae)) {
                    throw new IllegalArgumentException("Initializer variables in FOR_LET must be an ArrayList");
                }
                zzae zzaeVar = (zzae) zzapVarZza2;
                zzap zzapVar4 = list.get(1);
                zzap zzapVar5 = list.get(2);
                zzap zzapVarZza3 = zzgVar.zza(list.get(3));
                zzg zzgVarZzc = zzgVar.zzc();
                for (int i = 0; i < zzaeVar.zzh(); i++) {
                    String strZzc4 = zzaeVar.zzl(i).zzc();
                    zzgVarZzc.zze(strZzc4, zzgVar.zzh(strZzc4));
                }
                while (zzgVar.zza(zzapVar4).zze().booleanValue()) {
                    zzap zzapVarZzb3 = zzgVar.zzb((zzae) zzapVarZza3);
                    if (zzapVarZzb3 instanceof zzag) {
                        zzag zzagVar3 = (zzag) zzapVarZzb3;
                        if ("break".equals(zzagVar3.zzg())) {
                            return zzap.zzf;
                        }
                        if ("return".equals(zzagVar3.zzg())) {
                            return zzagVar3;
                        }
                    }
                    zzg zzgVarZzc2 = zzgVar.zzc();
                    for (int i2 = 0; i2 < zzaeVar.zzh(); i2++) {
                        String strZzc5 = zzaeVar.zzl(i2).zzc();
                        zzgVarZzc2.zze(strZzc5, zzgVarZzc.zzh(strZzc5));
                    }
                    zzgVarZzc2.zza(zzapVar5);
                    zzgVarZzc = zzgVarZzc2;
                }
                return zzap.zzf;
            case 30:
                zzh.zza(zzbl.FOR_OF.name(), 3, list);
                if (!(list.get(0) instanceof zzat)) {
                    throw new IllegalArgumentException("Variable name in FOR_OF must be a string");
                }
                String strZzc6 = list.get(0).zzc();
                return zzd(new zzbg(zzgVar, strZzc6), zzgVar.zza(list.get(1)), zzgVar.zza(list.get(2)));
            case 31:
                zzh.zza(zzbl.FOR_OF_CONST.name(), 3, list);
                if (!(list.get(0) instanceof zzat)) {
                    throw new IllegalArgumentException("Variable name in FOR_OF_CONST must be a string");
                }
                String strZzc7 = list.get(0).zzc();
                return zzd(new zzbd(zzgVar, strZzc7), zzgVar.zza(list.get(1)), zzgVar.zza(list.get(2)));
            case 32:
                zzh.zza(zzbl.FOR_OF_LET.name(), 3, list);
                if (!(list.get(0) instanceof zzat)) {
                    throw new IllegalArgumentException("Variable name in FOR_OF_LET must be a string");
                }
                String strZzc8 = list.get(0).zzc();
                return zzd(new zzbe(zzgVar, strZzc8), zzgVar.zza(list.get(1)), zzgVar.zza(list.get(2)));
            default:
                return super.zzb(str);
        }
    }
}
