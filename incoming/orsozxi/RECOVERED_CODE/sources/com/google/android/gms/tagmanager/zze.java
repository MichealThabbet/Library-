package com.google.android.gms.tagmanager;

import android.content.Context;
import java.util.Map;

/* loaded from: classes.dex */
final class zze extends zzbq {
    private static final String ID = com.google.android.gms.internal.gtm.zza.ADVERTISER_ID.toString();
    private final zza zzadt;

    public zze(Context context) {
        this(zza.zzf(context));
    }

    @Override // com.google.android.gms.tagmanager.zzbq
    public final boolean zzgw() {
        return false;
    }

    private zze(zza zzaVar) {
        super(ID, new String[0]);
        this.zzadt = zzaVar;
        zzaVar.zzgq();
    }

    @Override // com.google.android.gms.tagmanager.zzbq
    public final com.google.android.gms.internal.gtm.zzl zzb(Map<String, com.google.android.gms.internal.gtm.zzl> map) {
        String strZzgq = this.zzadt.zzgq();
        return strZzgq == null ? zzgj.zzkc() : zzgj.zzi(strZzgq);
    }
}
