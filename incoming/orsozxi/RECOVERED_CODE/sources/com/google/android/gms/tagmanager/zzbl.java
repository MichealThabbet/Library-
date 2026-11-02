package com.google.android.gms.tagmanager;

import java.util.Map;

/* loaded from: classes.dex */
final class zzbl extends zzfz {
    private static final String ID = com.google.android.gms.internal.gtm.zza.ENDS_WITH.toString();

    public zzbl() {
        super(ID);
    }

    @Override // com.google.android.gms.tagmanager.zzfz
    protected final boolean zza(String str, String str2, Map<String, com.google.android.gms.internal.gtm.zzl> map) {
        return str.endsWith(str2);
    }
}
