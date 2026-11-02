package com.google.android.gms.tagmanager;

import android.os.Build;
import java.util.Map;

/* loaded from: classes.dex */
final class zzbd extends zzbq {
    private static final String ID = com.google.android.gms.internal.gtm.zza.DEVICE_NAME.toString();

    public zzbd() {
        super(ID, new String[0]);
    }

    @Override // com.google.android.gms.tagmanager.zzbq
    public final boolean zzgw() {
        return true;
    }

    @Override // com.google.android.gms.tagmanager.zzbq
    public final com.google.android.gms.internal.gtm.zzl zzb(Map<String, com.google.android.gms.internal.gtm.zzl> map) {
        String str = Build.MANUFACTURER;
        String string = Build.MODEL;
        if (!string.startsWith(str) && !str.equals("unknown")) {
            StringBuilder sb = new StringBuilder(String.valueOf(str).length() + 1 + String.valueOf(string).length());
            sb.append(str);
            sb.append(" ");
            sb.append(string);
            string = sb.toString();
        }
        return zzgj.zzi(string);
    }
}
