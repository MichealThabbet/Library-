package com.huawei.agconnect.config.impl;

import net.lingala.zip4j.crypto.PBKDF2.BinTools;

/* loaded from: classes2.dex */
public class Hex {
    private static final char[] HEX_CODE = BinTools.hex.toCharArray();

    private static byte[] decodeHex(char[] cArr) {
        if ((cArr.length & 1) != 0) {
            throw new IllegalArgumentException("Odd number of characters.");
        }
        byte[] bArr = new byte[cArr.length >> 1];
        int i = 0;
        int i2 = 0;
        while (i < cArr.length) {
            int iDigit = Character.digit(cArr[i], 16);
            if (iDigit == -1) {
                throw new IllegalArgumentException("Illegal hexadecimal character at index " + i);
            }
            int i3 = i + 1;
            int iDigit2 = Character.digit(cArr[i3], 16);
            if (iDigit2 == -1) {
                throw new IllegalArgumentException("Illegal hexadecimal character at index " + i3);
            }
            i = i3 + 1;
            bArr[i2] = (byte) (((iDigit << 4) | iDigit2) & 255);
            i2++;
        }
        return bArr;
    }

    public static byte[] decodeHexString(String str) {
        return decodeHex(str.toCharArray());
    }

    public static String encodeHexString(byte[] bArr) {
        StringBuilder sb = new StringBuilder(bArr.length * 2);
        for (byte b : bArr) {
            sb.append(HEX_CODE[(b >> 4) & 15]);
            sb.append(HEX_CODE[b & 15]);
        }
        return sb.toString();
    }
}
