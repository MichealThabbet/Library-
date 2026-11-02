package com.google.android.gms.tagmanager;

import java.util.Map;

/* loaded from: classes.dex */
final class zzbp extends zzbq {
    private static final String ID = com.google.android.gms.internal.gtm.zza.EVENT.toString();
    private final zzfb zzaee;

    public zzbp(zzfb zzfbVar) {
        super(ID, new String[0]);
        this.zzaee = zzfbVar;
    }

    @Override // com.google.android.gms.tagmanager.zzbq
    public final boolean zzgw() {
        return false;
    }

    @Override // com.google.android.gms.tagmanager.zzbq
    public final com.google.android.gms.internal.gtm.zzl zzb(Map<String, com.google.android.gms.internal.gtm.zzl> map) {
        String strZzjf = this.zzaee.zzjf();
        if (strZzjf == null) {
            return zzgj.zzkc();
        }
        return zzgj.zzi(strZzjf);
    }
}
