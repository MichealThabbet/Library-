package com.google.android.gms.tagmanager;

import android.content.Context;
import java.util.Map;

/* loaded from: classes.dex */
final class zzi extends zzbq {
    private static final String ID = com.google.android.gms.internal.gtm.zza.APP_ID.toString();
    private final Context zzrm;

    public zzi(Context context) {
        super(ID, new String[0]);
        this.zzrm = context;
    }

    @Override // com.google.android.gms.tagmanager.zzbq
    public final boolean zzgw() {
        return true;
    }

    @Override // com.google.android.gms.tagmanager.zzbq
    public final com.google.android.gms.internal.gtm.zzl zzb(Map<String, com.google.android.gms.internal.gtm.zzl> map) {
        return zzgj.zzi(this.zzrm.getPackageName());
    }
}
