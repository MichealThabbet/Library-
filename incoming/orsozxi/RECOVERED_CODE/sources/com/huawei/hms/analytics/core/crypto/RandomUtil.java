package com.huawei.hms.analytics.core.crypto;

import com.huawei.hms.analytics.core.log.HiLog;
import com.huawei.secure.android.common.encrypt.utils.EncryptUtil;
import java.security.SecureRandom;

/* loaded from: classes2.dex */
public final class RandomUtil {
    private static RandomUtil a;
    private final SecureRandom b;

    private RandomUtil() {
        try {
            EncryptUtil.setBouncycastleFlag(false);
        } catch (Throwable th) {
            HiLog.w("RandomUtil", "setBouncycastleFlag exception. " + th.getMessage());
        }
        this.b = EncryptUtil.genSecureRandom();
    }

    private static synchronized void a() {
        if (a == null) {
            a = new RandomUtil();
        }
    }

    public static RandomUtil getInstance() {
        if (a == null) {
            a();
        }
        return a;
    }

    public final byte[] generateSecureRandom(int i) {
        byte[] bArr = new byte[i];
        this.b.nextBytes(bArr);
        return bArr;
    }

    public final String generateSecureRandomStr(int i) {
        byte[] bArr = new byte[i];
        this.b.nextBytes(bArr);
        return HAHexUtil.byteArray2HexString(bArr);
    }
}
