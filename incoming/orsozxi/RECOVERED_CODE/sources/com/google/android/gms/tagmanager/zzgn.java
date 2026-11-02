package com.google.android.gms.tagmanager;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/* loaded from: classes.dex */
final class zzgn {
    static zzdz<com.google.android.gms.internal.gtm.zzl> zza(zzdz<com.google.android.gms.internal.gtm.zzl> zzdzVar, int... iArr) {
        for (int i : iArr) {
            if (!(zzgj.zzh(zzdzVar.getObject()) instanceof String)) {
                zzdi.zzav("Escaping can only be applied to strings.");
            } else if (i != 12) {
                StringBuilder sb = new StringBuilder(39);
                sb.append("Unsupported Value Escaping: ");
                sb.append(i);
                zzdi.zzav(sb.toString());
            } else {
                zzdzVar = zza(zzdzVar);
            }
        }
        return zzdzVar;
    }

    static String zzbs(String str) throws UnsupportedEncodingException {
        return URLEncoder.encode(str, "UTF-8").replaceAll("\\+", "%20");
    }

    private static zzdz<com.google.android.gms.internal.gtm.zzl> zza(zzdz<com.google.android.gms.internal.gtm.zzl> zzdzVar) {
        try {
            return new zzdz<>(zzgj.zzi(zzbs(zzgj.zzc(zzdzVar.getObject()))), zzdzVar.zziu());
        } catch (UnsupportedEncodingException e) {
            zzdi.zza("Escape URI: unsupported encoding", e);
            return zzdzVar;
        }
    }
}
