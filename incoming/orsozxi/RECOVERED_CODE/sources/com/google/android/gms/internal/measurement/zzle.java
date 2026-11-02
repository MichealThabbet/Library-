package com.google.android.gms.internal.measurement;

/* compiled from: com.google.android.gms:play-services-measurement-base@@19.0.0 */
/* loaded from: classes.dex */
final class zzle {
    private static final zzld zza;
    private static final zzld zzb;

    static {
        zzld zzldVar;
        try {
            zzldVar = (zzld) Class.forName("com.google.protobuf.MapFieldSchemaFull").getDeclaredConstructor(new Class[0]).newInstance(new Object[0]);
        } catch (Exception unused) {
            zzldVar = null;
        }
        zza = zzldVar;
        zzb = new zzld();
    }

    static zzld zza() {
        return zza;
    }

    static zzld zzb() {
        return zzb;
    }
}
