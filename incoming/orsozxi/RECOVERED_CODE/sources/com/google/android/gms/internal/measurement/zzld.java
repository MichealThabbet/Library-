package com.google.android.gms.internal.measurement;

import java.util.Iterator;
import java.util.Map;

/* compiled from: com.google.android.gms:play-services-measurement-base@@19.0.0 */
/* loaded from: classes.dex */
final class zzld {
    zzld() {
    }

    public static final int zza(int i, Object obj, Object obj2) {
        zzlc zzlcVar = (zzlc) obj;
        if (zzlcVar.isEmpty()) {
            return 0;
        }
        Iterator it2 = zzlcVar.entrySet().iterator();
        if (!it2.hasNext()) {
            return 0;
        }
        Map.Entry entry = (Map.Entry) it2.next();
        entry.getKey();
        entry.getValue();
        throw null;
    }

    public static final Object zzb(Object obj, Object obj2) {
        zzlc zzlcVarZzc = (zzlc) obj;
        zzlc zzlcVar = (zzlc) obj2;
        if (!zzlcVar.isEmpty()) {
            if (!zzlcVarZzc.zze()) {
                zzlcVarZzc = zzlcVarZzc.zzc();
            }
            zzlcVarZzc.zzb(zzlcVar);
        }
        return zzlcVarZzc;
    }
}
