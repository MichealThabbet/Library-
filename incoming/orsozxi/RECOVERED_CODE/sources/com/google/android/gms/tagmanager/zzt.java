package com.google.android.gms.tagmanager;

import java.util.Map;

/* loaded from: classes.dex */
final class zzt extends zzbq {
    private static final String ID = com.google.android.gms.internal.gtm.zza.CONSTANT.toString();
    private static final String VALUE = com.google.android.gms.internal.gtm.zzb.VALUE.toString();

    public static String zzgy() {
        return ID;
    }

    @Override // com.google.android.gms.tagmanager.zzbq
    public final boolean zzgw() {
        return true;
    }

    public zzt() {
        super(ID, VALUE);
    }

    public static String zzgz() {
        return VALUE;
    }

    @Override // com.google.android.gms.tagmanager.zzbq
    public final com.google.android.gms.internal.gtm.zzl zzb(Map<String, com.google.android.gms.internal.gtm.zzl> map) {
        return map.get(VALUE);
    }
}
