package com.google.android.gms.tagmanager;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Map;
import org.apache.commons.codec.digest.MessageDigestAlgorithms;

/* loaded from: classes.dex */
final class zzbv extends zzbq {
    private static final String ID = com.google.android.gms.internal.gtm.zza.HASH.toString();
    private static final String zzags = com.google.android.gms.internal.gtm.zzb.ARG0.toString();
    private static final String zzagx = com.google.android.gms.internal.gtm.zzb.ALGORITHM.toString();
    private static final String zzagu = com.google.android.gms.internal.gtm.zzb.INPUT_FORMAT.toString();

    public zzbv() {
        super(ID, zzags);
    }

    @Override // com.google.android.gms.tagmanager.zzbq
    public final boolean zzgw() {
        return true;
    }

    @Override // com.google.android.gms.tagmanager.zzbq
    public final com.google.android.gms.internal.gtm.zzl zzb(Map<String, com.google.android.gms.internal.gtm.zzl> map) throws NoSuchAlgorithmException {
        byte[] bArrDecode;
        com.google.android.gms.internal.gtm.zzl zzlVar = map.get(zzags);
        if (zzlVar == null || zzlVar == zzgj.zzkc()) {
            return zzgj.zzkc();
        }
        String strZzc = zzgj.zzc(zzlVar);
        com.google.android.gms.internal.gtm.zzl zzlVar2 = map.get(zzagx);
        String strZzc2 = zzlVar2 == null ? MessageDigestAlgorithms.MD5 : zzgj.zzc(zzlVar2);
        com.google.android.gms.internal.gtm.zzl zzlVar3 = map.get(zzagu);
        String strZzc3 = zzlVar3 == null ? "text" : zzgj.zzc(zzlVar3);
        if ("text".equals(strZzc3)) {
            bArrDecode = strZzc.getBytes();
        } else if ("base16".equals(strZzc3)) {
            bArrDecode = zzo.decode(strZzc);
        } else {
            String strValueOf = String.valueOf(strZzc3);
            zzdi.zzav(strValueOf.length() != 0 ? "Hash: unknown input format: ".concat(strValueOf) : new String("Hash: unknown input format: "));
            return zzgj.zzkc();
        }
        try {
            MessageDigest messageDigest = MessageDigest.getInstance(strZzc2);
            messageDigest.update(bArrDecode);
            return zzgj.zzi(zzo.encode(messageDigest.digest()));
        } catch (NoSuchAlgorithmException unused) {
            String strValueOf2 = String.valueOf(strZzc2);
            zzdi.zzav(strValueOf2.length() != 0 ? "Hash: unknown algorithm: ".concat(strValueOf2) : new String("Hash: unknown algorithm: "));
            return zzgj.zzkc();
        }
    }
}
