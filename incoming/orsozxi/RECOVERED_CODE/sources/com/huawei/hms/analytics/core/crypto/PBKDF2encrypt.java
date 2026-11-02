package com.huawei.hms.analytics.core.crypto;

import com.huawei.secure.android.common.encrypt.hash.PBKDF2;

/* loaded from: classes2.dex */
public class PBKDF2encrypt {
    public static byte[] pbkdf2(char[] cArr, byte[] bArr, int i, int i2) {
        return PBKDF2.pbkdf2(cArr, bArr, i, i2);
    }
}
