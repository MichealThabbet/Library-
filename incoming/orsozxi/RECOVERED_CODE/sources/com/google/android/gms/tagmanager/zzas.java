package com.google.android.gms.tagmanager;

import java.util.Map;

/* loaded from: classes.dex */
final class zzas extends zzbq {
    private static final String ID = com.google.android.gms.internal.gtm.zza.CUSTOM_VAR.toString();
    private static final String NAME = com.google.android.gms.internal.gtm.zzb.NAME.toString();
    private static final String zzafw = com.google.android.gms.internal.gtm.zzb.DEFAULT_VALUE.toString();
    private final DataLayer zzaed;

    public zzas(DataLayer dataLayer) {
        super(ID, NAME);
        this.zzaed = dataLayer;
    }

    @Override // com.google.android.gms.tagmanager.zzbq
    public final boolean zzgw() {
        return false;
    }

    @Override // com.google.android.gms.tagmanager.zzbq
    public final com.google.android.gms.internal.gtm.zzl zzb(Map<String, com.google.android.gms.internal.gtm.zzl> map) {
        Object obj = this.zzaed.get(zzgj.zzc(map.get(NAME)));
        if (obj == null) {
            com.google.android.gms.internal.gtm.zzl zzlVar = map.get(zzafw);
            return zzlVar != null ? zzlVar : zzgj.zzkc();
        }
        return zzgj.zzi(obj);
    }
}
