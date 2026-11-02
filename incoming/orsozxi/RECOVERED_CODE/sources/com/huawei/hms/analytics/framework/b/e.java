package com.huawei.hms.analytics.framework.b;

import com.huawei.hms.analytics.core.crypto.RandomUtil;
import com.huawei.hms.analytics.core.crypto.RsaCipher;
import java.security.spec.InvalidKeySpecException;
import java.util.HashMap;
import java.util.Map;

/* loaded from: classes2.dex */
public final class e {
    private static e b = new e();
    public final Map<String, d> a = new HashMap();

    public static e a() {
        return b;
    }

    public static String a(String str) {
        return b.a().a.getPubKeyVersion(str);
    }

    public static void a(d dVar, String str) throws InvalidKeySpecException {
        byte[] rsaPublicKey = b.a().a.getRsaPublicKey(str);
        if (rsaPublicKey == null || rsaPublicKey.length == 0) {
            throw new IllegalArgumentException("pub key is error");
        }
        String strGenerateSecureRandomStr = RandomUtil.getInstance().generateSecureRandomStr(16);
        String strEncrypt = RsaCipher.encrypt(rsaPublicKey, strGenerateSecureRandomStr);
        String strA = a(str);
        long jCurrentTimeMillis = System.currentTimeMillis();
        dVar.d = strA;
        dVar.a(rsaPublicKey);
        dVar.b = strEncrypt;
        dVar.a = strGenerateSecureRandomStr;
        dVar.c = jCurrentTimeMillis;
    }
}
