package com.google.android.gms.tagmanager;

import android.content.Context;
import android.content.pm.PackageManager;
import java.util.Map;

/* loaded from: classes.dex */
final class zzj extends zzbq {
    private static final String ID = com.google.android.gms.internal.gtm.zza.APP_NAME.toString();
    private final Context zzrm;

    public zzj(Context context) {
        super(ID, new String[0]);
        this.zzrm = context;
    }

    @Override // com.google.android.gms.tagmanager.zzbq
    public final boolean zzgw() {
        return true;
    }

    @Override // com.google.android.gms.tagmanager.zzbq
    public final com.google.android.gms.internal.gtm.zzl zzb(Map<String, com.google.android.gms.internal.gtm.zzl> map) {
        try {
            PackageManager packageManager = this.zzrm.getPackageManager();
            return zzgj.zzi(packageManager.getApplicationLabel(packageManager.getApplicationInfo(this.zzrm.getPackageName(), 0)).toString());
        } catch (PackageManager.NameNotFoundException e) {
            zzdi.zza("App name is not found.", e);
            return zzgj.zzkc();
        }
    }
}
