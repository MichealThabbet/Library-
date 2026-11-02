package com.google.android.gms.tagmanager;

import android.content.Context;
import android.provider.Settings;
import java.util.Map;

/* loaded from: classes.dex */
final class zzbc extends zzbq {
    private static final String ID = com.google.android.gms.internal.gtm.zza.DEVICE_ID.toString();
    private final Context zzrm;

    public zzbc(Context context) {
        super(ID, new String[0]);
        this.zzrm = context;
    }

    @Override // com.google.android.gms.tagmanager.zzbq
    public final boolean zzgw() {
        return true;
    }

    @Override // com.google.android.gms.tagmanager.zzbq
    public final com.google.android.gms.internal.gtm.zzl zzb(Map<String, com.google.android.gms.internal.gtm.zzl> map) {
        String string = Settings.Secure.getString(this.zzrm.getContentResolver(), "android_id");
        return string == null ? zzgj.zzkc() : zzgj.zzi(string);
    }
}
