package com.google.android.gms.internal.measurement;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/* compiled from: com.google.android.gms:play-services-measurement-base@@19.0.0 */
/* loaded from: classes.dex */
final class zzku extends zzkw {
    private static final Class<?> zza = Collections.unmodifiableList(Collections.emptyList()).getClass();

    private zzku() {
        super(null);
    }

    /* synthetic */ zzku(zzkt zzktVar) {
        super(null);
    }

    @Override // com.google.android.gms.internal.measurement.zzkw
    final void zza(Object obj, long j) {
        Object objUnmodifiableList;
        List list = (List) zzmr.zzn(obj, j);
        if (list instanceof zzks) {
            objUnmodifiableList = ((zzks) list).zzi();
        } else {
            if (zza.isAssignableFrom(list.getClass())) {
                return;
            }
            if ((list instanceof zzlp) && (list instanceof zzkk)) {
                zzkk zzkkVar = (zzkk) list;
                if (zzkkVar.zza()) {
                    zzkkVar.zzb();
                    return;
                }
                return;
            }
            objUnmodifiableList = Collections.unmodifiableList(list);
        }
        zzmr.zzo(obj, j, objUnmodifiableList);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.google.android.gms.internal.measurement.zzkw
    final <E> void zzb(Object obj, Object obj2, long j) {
        zzkr zzkrVar;
        List list = (List) zzmr.zzn(obj2, j);
        int size = list.size();
        List listZze = (List) zzmr.zzn(obj, j);
        if (listZze.isEmpty()) {
            listZze = listZze instanceof zzks ? new zzkr(size) : ((listZze instanceof zzlp) && (listZze instanceof zzkk)) ? ((zzkk) listZze).zze(size) : new ArrayList(size);
            zzmr.zzo(obj, j, listZze);
        } else {
            if (zza.isAssignableFrom(listZze.getClass())) {
                ArrayList arrayList = new ArrayList(listZze.size() + size);
                arrayList.addAll(listZze);
                zzmr.zzo(obj, j, arrayList);
                zzkrVar = arrayList;
            } else if (listZze instanceof zzmm) {
                zzkr zzkrVar2 = new zzkr(listZze.size() + size);
                zzkrVar2.addAll(zzkrVar2.size(), (zzmm) listZze);
                zzmr.zzo(obj, j, zzkrVar2);
                zzkrVar = zzkrVar2;
            } else if ((listZze instanceof zzlp) && (listZze instanceof zzkk)) {
                zzkk zzkkVar = (zzkk) listZze;
                if (!zzkkVar.zza()) {
                    listZze = zzkkVar.zze(listZze.size() + size);
                    zzmr.zzo(obj, j, listZze);
                }
            }
            listZze = zzkrVar;
        }
        int size2 = listZze.size();
        int size3 = list.size();
        if (size2 > 0 && size3 > 0) {
            listZze.addAll(list);
        }
        if (size2 > 0) {
            list = listZze;
        }
        zzmr.zzo(obj, j, list);
    }
}
