package com.google.android.gms.internal.measurement;

import com.google.firebase.messaging.Constants;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/* compiled from: com.google.android.gms:play-services-measurement-base@@19.0.0 */
/* loaded from: classes.dex */
final class zzlq {
    private static final zzlq zza = new zzlq();
    private final ConcurrentMap<Class<?>, zzlt<?>> zzc = new ConcurrentHashMap();
    private final zzlu zzb = new zzla();

    private zzlq() {
    }

    public static zzlq zza() {
        return zza;
    }

    public final <T> zzlt<T> zzb(Class<T> cls) {
        zzkl.zzb(cls, Constants.FirelogAnalytics.PARAM_MESSAGE_TYPE);
        zzlt<T> zzltVarZza = (zzlt) this.zzc.get(cls);
        if (zzltVarZza == null) {
            zzltVarZza = this.zzb.zza(cls);
            zzkl.zzb(cls, Constants.FirelogAnalytics.PARAM_MESSAGE_TYPE);
            zzkl.zzb(zzltVarZza, "schema");
            zzlt<T> zzltVar = (zzlt) this.zzc.putIfAbsent(cls, zzltVarZza);
            if (zzltVar != null) {
                return zzltVar;
            }
        }
        return zzltVarZza;
    }
}
