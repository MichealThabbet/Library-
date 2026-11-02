package com.google.android.gms.tagmanager;

import android.content.Context;
import java.util.Map;

/* loaded from: classes.dex */
final class zzf extends zzbq {
    private static final String ID = com.google.android.gms.internal.gtm.zza.ADVERTISING_TRACKING_ENABLED.toString();
    private final zza zzadt;

    public zzf(Context context) {
        this(zza.zzf(context));
    }

    @Override // com.google.android.gms.tagmanager.zzbq
    public final boolean zzgw() {
        return false;
    }

    private zzf(zza zzaVar) {
        super(ID, new String[0]);
        this.zzadt = zzaVar;
    }

    @Override // com.google.android.gms.tagmanager.zzbq
    public final com.google.android.gms.internal.gtm.zzl zzb(Map<String, com.google.android.gms.internal.gtm.zzl> map) {
        return zzgj.zzi(Boolean.valueOf(!this.zzadt.isLimitAdTrackingEnabled()));
    }
}
