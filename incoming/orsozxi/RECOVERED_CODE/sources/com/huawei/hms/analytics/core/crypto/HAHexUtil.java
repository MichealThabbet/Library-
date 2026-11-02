package com.huawei.hms.analytics.core.crypto;

import com.huawei.secure.android.common.encrypt.utils.HexUtil;

/* loaded from: classes2.dex */
public abstract class HAHexUtil {
    public static String byteArray2HexString(byte[] bArr) {
        return HexUtil.byteArray2HexStr(bArr);
    }

    public static byte[] hexString2ByteArray(String str) {
        return HexUtil.hexStr2ByteArray(str);
    }
}
