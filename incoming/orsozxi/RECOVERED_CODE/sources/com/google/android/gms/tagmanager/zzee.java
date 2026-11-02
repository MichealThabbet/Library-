package com.google.android.gms.tagmanager;

import java.util.Map;

/* loaded from: classes.dex */
final class zzee extends zzbq {
    private static final String ID = com.google.android.gms.internal.gtm.zza.PLATFORM.toString();
    private static final com.google.android.gms.internal.gtm.zzl zzain = zzgj.zzi("Android");

    public zzee() {
        super(ID, new String[0]);
    }

    @Override // com.google.android.gms.tagmanager.zzbq
    public final boolean zzgw() {
        return true;
    }

    @Override // com.google.android.gms.tagmanager.zzbq
    public final com.google.android.gms.internal.gtm.zzl zzb(Map<String, com.google.android.gms.internal.gtm.zzl> map) {
        return zzain;
    }
}
