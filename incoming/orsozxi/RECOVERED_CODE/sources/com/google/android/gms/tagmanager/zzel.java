package com.google.android.gms.tagmanager;

import java.util.Map;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

/* loaded from: classes.dex */
final class zzel extends zzfz {
    private static final String ID = com.google.android.gms.internal.gtm.zza.REGEX.toString();
    private static final String zzajb = com.google.android.gms.internal.gtm.zzb.IGNORE_CASE.toString();

    public zzel() {
        super(ID);
    }

    @Override // com.google.android.gms.tagmanager.zzfz
    protected final boolean zza(String str, String str2, Map<String, com.google.android.gms.internal.gtm.zzl> map) {
        try {
            return Pattern.compile(str2, zzgj.zzg(map.get(zzajb)).booleanValue() ? 66 : 64).matcher(str).find();
        } catch (PatternSyntaxException unused) {
            return false;
        }
    }
}
