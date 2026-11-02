package com.google.android.gms.tagmanager;

import java.util.Map;

/* loaded from: classes.dex */
abstract class zzfz extends zzef {
    public zzfz(String str) {
        super(str);
    }

    protected abstract boolean zza(String str, String str2, Map<String, com.google.android.gms.internal.gtm.zzl> map);

    @Override // com.google.android.gms.tagmanager.zzef
    protected final boolean zza(com.google.android.gms.internal.gtm.zzl zzlVar, com.google.android.gms.internal.gtm.zzl zzlVar2, Map<String, com.google.android.gms.internal.gtm.zzl> map) {
        String strZzc = zzgj.zzc(zzlVar);
        String strZzc2 = zzgj.zzc(zzlVar2);
        if (strZzc == zzgj.zzkb() || strZzc2 == zzgj.zzkb()) {
            return false;
        }
        return zza(strZzc, strZzc2, map);
    }
}
