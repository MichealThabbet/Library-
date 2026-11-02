package com.google.android.gms.internal.measurement;

import kotlin.text.Typography;

/* compiled from: com.google.android.gms:play-services-measurement@@19.0.0 */
/* loaded from: classes.dex */
public enum zzga implements zzkf {
    RADS(1),
    PROVISIONING(2);

    private static final zzkg<zzga> zzc = new zzkg<zzga>() { // from class: com.google.android.gms.internal.measurement.zzfy
    };
    private final int zzd;

    zzga(int i) {
        this.zzd = i;
    }

    public static zzga zza(int i) {
        if (i == 1) {
            return RADS;
        }
        if (i != 2) {
            return null;
        }
        return PROVISIONING;
    }

    public static zzkh zzb() {
        return zzfz.zza;
    }

    @Override // java.lang.Enum
    public final String toString() {
        return "<" + getClass().getName() + '@' + Integer.toHexString(System.identityHashCode(this)) + " number=" + this.zzd + " name=" + name() + Typography.greater;
    }
}
