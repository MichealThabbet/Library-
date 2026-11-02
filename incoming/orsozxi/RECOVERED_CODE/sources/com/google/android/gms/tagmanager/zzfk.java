package com.google.android.gms.tagmanager;

import android.os.Build;
import java.util.Map;

/* loaded from: classes.dex */
final class zzfk extends zzbq {
    private static final String ID = com.google.android.gms.internal.gtm.zza.SDK_VERSION.toString();

    public zzfk() {
        super(ID, new String[0]);
    }

    @Override // com.google.android.gms.tagmanager.zzbq
    public final boolean zzgw() {
        return true;
    }

    @Override // com.google.android.gms.tagmanager.zzbq
    public final com.google.android.gms.internal.gtm.zzl zzb(Map<String, com.google.android.gms.internal.gtm.zzl> map) {
        return zzgj.zzi(Integer.valueOf(Build.VERSION.SDK_INT));
    }
}
