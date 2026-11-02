package com.google.android.gms.tagmanager;

import java.util.Map;

/* loaded from: classes.dex */
final class zzak extends zzfz {
    private static final String ID = com.google.android.gms.internal.gtm.zza.CONTAINS.toString();

    public zzak() {
        super(ID);
    }

    @Override // com.google.android.gms.tagmanager.zzfz
    protected final boolean zza(String str, String str2, Map<String, com.google.android.gms.internal.gtm.zzl> map) {
        return str.contains(str2);
    }
}
