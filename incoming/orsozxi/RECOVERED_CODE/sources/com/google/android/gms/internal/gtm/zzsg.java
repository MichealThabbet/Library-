package com.google.android.gms.internal.gtm;

import java.util.Iterator;
import java.util.Map;

/* loaded from: classes.dex */
final class zzsg implements zzsf {
    zzsg() {
    }

    @Override // com.google.android.gms.internal.gtm.zzsf
    public final Map<?, ?> zzx(Object obj) {
        return (zzse) obj;
    }

    @Override // com.google.android.gms.internal.gtm.zzsf
    public final zzsd<?, ?> zzac(Object obj) {
        throw new NoSuchMethodError();
    }

    @Override // com.google.android.gms.internal.gtm.zzsf
    public final Map<?, ?> zzy(Object obj) {
        return (zzse) obj;
    }

    @Override // com.google.android.gms.internal.gtm.zzsf
    public final boolean zzz(Object obj) {
        return !((zzse) obj).isMutable();
    }

    @Override // com.google.android.gms.internal.gtm.zzsf
    public final Object zzaa(Object obj) {
        ((zzse) obj).zzmi();
        return obj;
    }

    @Override // com.google.android.gms.internal.gtm.zzsf
    public final Object zzab(Object obj) {
        return zzse.zzqf().zzqg();
    }

    @Override // com.google.android.gms.internal.gtm.zzsf
    public final Object zzc(Object obj, Object obj2) {
        zzse zzseVarZzqg = (zzse) obj;
        zzse zzseVar = (zzse) obj2;
        if (!zzseVar.isEmpty()) {
            if (!zzseVarZzqg.isMutable()) {
                zzseVarZzqg = zzseVarZzqg.zzqg();
            }
            zzseVarZzqg.zza(zzseVar);
        }
        return zzseVarZzqg;
    }

    @Override // com.google.android.gms.internal.gtm.zzsf
    public final int zzb(int i, Object obj, Object obj2) {
        zzse zzseVar = (zzse) obj;
        if (zzseVar.isEmpty()) {
            return 0;
        }
        Iterator it2 = zzseVar.entrySet().iterator();
        if (!it2.hasNext()) {
            return 0;
        }
        Map.Entry entry = (Map.Entry) it2.next();
        entry.getKey();
        entry.getValue();
        throw new NoSuchMethodError();
    }
}
