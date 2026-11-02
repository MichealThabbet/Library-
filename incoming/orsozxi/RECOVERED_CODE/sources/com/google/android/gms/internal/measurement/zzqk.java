package com.google.android.gms.internal.measurement;

/* compiled from: com.google.android.gms:play-services-measurement-impl@@19.0.0 */
/* loaded from: classes.dex */
public final class zzqk implements zzqj {
    public static final zzht<Boolean> zza = new zzhr(zzhk.zza("com.google.android.gms.measurement")).zzb("measurement.integration.disable_firebase_instance_id", false);

    @Override // com.google.android.gms.internal.measurement.zzqj
    public final boolean zza() {
        return true;
    }

    @Override // com.google.android.gms.internal.measurement.zzqj
    public final boolean zzb() {
        return zza.zze().booleanValue();
    }
}
