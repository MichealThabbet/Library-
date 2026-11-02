package com.google.android.gms.internal.gtm;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/* loaded from: classes.dex */
final class zzrw extends zzru {
    private static final Class<?> zzbcj = Collections.unmodifiableList(Collections.emptyList()).getClass();

    private zzrw() {
        super();
    }

    @Override // com.google.android.gms.internal.gtm.zzru
    final <L> List<L> zza(Object obj, long j) {
        return zza(obj, j, 10);
    }

    @Override // com.google.android.gms.internal.gtm.zzru
    final void zzb(Object obj, long j) {
        Object objUnmodifiableList;
        List list = (List) zztx.zzp(obj, j);
        if (list instanceof zzrt) {
            objUnmodifiableList = ((zzrt) list).zzqb();
        } else {
            if (zzbcj.isAssignableFrom(list.getClass())) {
                return;
            }
            if ((list instanceof zzsv) && (list instanceof zzrj)) {
                zzrj zzrjVar = (zzrj) list;
                if (zzrjVar.zzmy()) {
                    zzrjVar.zzmi();
                    return;
                }
                return;
            }
            objUnmodifiableList = Collections.unmodifiableList(list);
        }
        zztx.zza(obj, j, objUnmodifiableList);
    }

    /* JADX WARN: Multi-variable type inference failed */
    private static <L> List<L> zza(Object obj, long j, int i) {
        zzrs zzrsVar;
        List<L> arrayList;
        List<L> listZzc = zzc(obj, j);
        if (listZzc.isEmpty()) {
            if (listZzc instanceof zzrt) {
                arrayList = new zzrs(i);
            } else if ((listZzc instanceof zzsv) && (listZzc instanceof zzrj)) {
                arrayList = ((zzrj) listZzc).zzaj(i);
            } else {
                arrayList = new ArrayList<>(i);
            }
            zztx.zza(obj, j, arrayList);
            return arrayList;
        }
        if (zzbcj.isAssignableFrom(listZzc.getClass())) {
            ArrayList arrayList2 = new ArrayList(listZzc.size() + i);
            arrayList2.addAll(listZzc);
            zztx.zza(obj, j, arrayList2);
            zzrsVar = arrayList2;
        } else if (listZzc instanceof zztu) {
            zzrs zzrsVar2 = new zzrs(listZzc.size() + i);
            zzrsVar2.addAll((zztu) listZzc);
            zztx.zza(obj, j, zzrsVar2);
            zzrsVar = zzrsVar2;
        } else {
            if (!(listZzc instanceof zzsv) || !(listZzc instanceof zzrj)) {
                return listZzc;
            }
            zzrj zzrjVar = (zzrj) listZzc;
            if (zzrjVar.zzmy()) {
                return listZzc;
            }
            zzrj zzrjVarZzaj = zzrjVar.zzaj(listZzc.size() + i);
            zztx.zza(obj, j, zzrjVarZzaj);
            return zzrjVarZzaj;
        }
        return zzrsVar;
    }

    @Override // com.google.android.gms.internal.gtm.zzru
    final <E> void zza(Object obj, Object obj2, long j) {
        List listZzc = zzc(obj2, j);
        List listZza = zza(obj, j, listZzc.size());
        int size = listZza.size();
        int size2 = listZzc.size();
        if (size > 0 && size2 > 0) {
            listZza.addAll(listZzc);
        }
        if (size > 0) {
            listZzc = listZza;
        }
        zztx.zza(obj, j, listZzc);
    }

    private static <E> List<E> zzc(Object obj, long j) {
        return (List) zztx.zzp(obj, j);
    }
}
