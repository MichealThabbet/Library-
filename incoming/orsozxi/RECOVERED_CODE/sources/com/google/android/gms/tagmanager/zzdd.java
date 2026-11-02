package com.google.android.gms.tagmanager;

import java.util.Locale;
import java.util.Map;
import java.util.Set;

/* loaded from: classes.dex */
public final class zzdd extends zzbq {
    private static final String ID = com.google.android.gms.internal.gtm.zza.LANGUAGE.toString();

    public zzdd() {
        super(ID, new String[0]);
    }

    @Override // com.google.android.gms.tagmanager.zzbq
    public final boolean zzgw() {
        return false;
    }

    @Override // com.google.android.gms.tagmanager.zzbq
    public final com.google.android.gms.internal.gtm.zzl zzb(Map<String, com.google.android.gms.internal.gtm.zzl> map) {
        Locale locale = Locale.getDefault();
        if (locale == null) {
            return zzgj.zzkc();
        }
        String language = locale.getLanguage();
        if (language == null) {
            return zzgj.zzkc();
        }
        return zzgj.zzi(language.toLowerCase());
    }

    @Override // com.google.android.gms.tagmanager.zzbq
    public final /* bridge */ /* synthetic */ Set zzig() {
        return super.zzig();
    }

    @Override // com.google.android.gms.tagmanager.zzbq
    public final /* bridge */ /* synthetic */ String zzif() {
        return super.zzif();
    }
}
