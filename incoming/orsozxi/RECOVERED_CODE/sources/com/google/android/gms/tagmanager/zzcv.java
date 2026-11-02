package com.google.android.gms.tagmanager;

import android.content.Context;
import java.util.Map;

/* loaded from: classes.dex */
final class zzcv extends zzbq {
    private static final String ID = com.google.android.gms.internal.gtm.zza.INSTALL_REFERRER.toString();
    private static final String zzadu = com.google.android.gms.internal.gtm.zzb.COMPONENT.toString();
    private final Context zzrm;

    public zzcv(Context context) {
        super(ID, new String[0]);
        this.zzrm = context;
    }

    @Override // com.google.android.gms.tagmanager.zzbq
    public final boolean zzgw() {
        return true;
    }

    @Override // com.google.android.gms.tagmanager.zzbq
    public final com.google.android.gms.internal.gtm.zzl zzb(Map<String, com.google.android.gms.internal.gtm.zzl> map) {
        String strZze = zzcw.zze(this.zzrm, map.get(zzadu) != null ? zzgj.zzc(map.get(zzadu)) : null);
        return strZze != null ? zzgj.zzi(strZze) : zzgj.zzkc();
    }
}
