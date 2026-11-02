package com.google.android.gms.tagmanager;

import android.util.Base64;
import java.util.Map;

/* loaded from: classes.dex */
final class zzbk extends zzbq {
    private static final String ID = com.google.android.gms.internal.gtm.zza.ENCODE.toString();
    private static final String zzags = com.google.android.gms.internal.gtm.zzb.ARG0.toString();
    private static final String zzagt = com.google.android.gms.internal.gtm.zzb.NO_PADDING.toString();
    private static final String zzagu = com.google.android.gms.internal.gtm.zzb.INPUT_FORMAT.toString();
    private static final String zzagv = com.google.android.gms.internal.gtm.zzb.OUTPUT_FORMAT.toString();

    public zzbk() {
        super(ID, zzags);
    }

    @Override // com.google.android.gms.tagmanager.zzbq
    public final boolean zzgw() {
        return true;
    }

    @Override // com.google.android.gms.tagmanager.zzbq
    public final com.google.android.gms.internal.gtm.zzl zzb(Map<String, com.google.android.gms.internal.gtm.zzl> map) {
        byte[] bArrDecode;
        String strEncodeToString;
        com.google.android.gms.internal.gtm.zzl zzlVar = map.get(zzags);
        if (zzlVar == null || zzlVar == zzgj.zzkc()) {
            return zzgj.zzkc();
        }
        String strZzc = zzgj.zzc(zzlVar);
        com.google.android.gms.internal.gtm.zzl zzlVar2 = map.get(zzagu);
        String strZzc2 = zzlVar2 == null ? "text" : zzgj.zzc(zzlVar2);
        com.google.android.gms.internal.gtm.zzl zzlVar3 = map.get(zzagv);
        String strZzc3 = zzlVar3 == null ? "base16" : zzgj.zzc(zzlVar3);
        int i = 2;
        com.google.android.gms.internal.gtm.zzl zzlVar4 = map.get(zzagt);
        if (zzlVar4 != null && zzgj.zzg(zzlVar4).booleanValue()) {
            i = 3;
        }
        try {
            if ("text".equals(strZzc2)) {
                bArrDecode = strZzc.getBytes();
            } else if ("base16".equals(strZzc2)) {
                bArrDecode = zzo.decode(strZzc);
            } else if ("base64".equals(strZzc2)) {
                bArrDecode = Base64.decode(strZzc, i);
            } else if ("base64url".equals(strZzc2)) {
                bArrDecode = Base64.decode(strZzc, i | 8);
            } else {
                String strValueOf = String.valueOf(strZzc2);
                zzdi.zzav(strValueOf.length() != 0 ? "Encode: unknown input format: ".concat(strValueOf) : new String("Encode: unknown input format: "));
                return zzgj.zzkc();
            }
            if ("base16".equals(strZzc3)) {
                strEncodeToString = zzo.encode(bArrDecode);
            } else if ("base64".equals(strZzc3)) {
                strEncodeToString = Base64.encodeToString(bArrDecode, i);
            } else if ("base64url".equals(strZzc3)) {
                strEncodeToString = Base64.encodeToString(bArrDecode, i | 8);
            } else {
                String strValueOf2 = String.valueOf(strZzc3);
                zzdi.zzav(strValueOf2.length() != 0 ? "Encode: unknown output format: ".concat(strValueOf2) : new String("Encode: unknown output format: "));
                return zzgj.zzkc();
            }
            return zzgj.zzi(strEncodeToString);
        } catch (IllegalArgumentException unused) {
            zzdi.zzav("Encode: invalid input:");
            return zzgj.zzkc();
        }
    }
}
