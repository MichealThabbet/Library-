package com.huawei.secure.android.common.encrypt.aes;

import android.os.Build;
import android.text.TextUtils;
import com.huawei.secure.android.common.encrypt.utils.EncryptUtil;
import com.huawei.secure.android.common.encrypt.utils.HexUtil;
import com.huawei.secure.android.common.encrypt.utils.b;
import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.AlgorithmParameterSpec;
import java.util.Arrays;
import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/* loaded from: classes2.dex */
public final class AesGcm {
    private static final String a = "security:";
    private static final String b = "AES/GCM/NoPadding";
    private static final String c = "AES";
    private static final String d = "GCM";
    private static final String e = "";
    private static final int f = 16;
    private static final int g = 12;
    private static final int h = 2;

    private AesGcm() {
    }

    private static byte[] a(String str, byte[] bArr, byte[] bArr2) {
        if (TextUtils.isEmpty(str)) {
            b.b("GCM", "encrypt 5 content is null");
            return new byte[0];
        }
        if (bArr == null) {
            b.b("GCM", "encrypt 5 key is null");
            return new byte[0];
        }
        if (bArr.length < 16) {
            b.b("GCM", "encrypt 5 key lengh is not right");
            return new byte[0];
        }
        if (bArr2 == null) {
            b.b("GCM", "encrypt 5 iv is null");
            return new byte[0];
        }
        if (bArr2.length < 12) {
            b.b("GCM", "encrypt 5 iv lengh is not right");
            return new byte[0];
        }
        if (!isBuildVersionHigherThan19()) {
            b.b("GCM", "encrypt 5 build version not higher than 19");
            return new byte[0];
        }
        try {
            return encrypt(str.getBytes("UTF-8"), bArr, bArr2);
        } catch (UnsupportedEncodingException e2) {
            b.b("GCM", "GCM encrypt data error" + e2.getMessage());
            return new byte[0];
        }
    }

    private static byte[] b(byte[] bArr) {
        byte[] bArr2 = new byte[bArr.length - 12];
        System.arraycopy(bArr, 12, bArr2, 0, bArr.length - 12);
        return bArr2;
    }

    public static String decrypt(String str, String str2) throws UnsupportedEncodingException {
        if (TextUtils.isEmpty(str)) {
            b.b("GCM", "decrypt 1 content is null");
            return "";
        }
        if (TextUtils.isEmpty(str2)) {
            b.b("GCM", "decrypt 1 key is null");
            return "";
        }
        if (!isBuildVersionHigherThan19()) {
            b.b("GCM", "decrypt 1 build version not higher than 19");
            return "";
        }
        byte[] bArrHexStr2ByteArray = HexUtil.hexStr2ByteArray(str2);
        if (bArrHexStr2ByteArray.length >= 16) {
            return decrypt(str, bArrHexStr2ByteArray);
        }
        b.b("GCM", "decrypt 1 key length is not right");
        return "";
    }

    public static String decryptWithCryptHead(String str, byte[] bArr) throws UnsupportedEncodingException {
        if (!TextUtils.isEmpty(str) && bArr != null && bArr.length >= 16) {
            String strC = AesCbc.c(str);
            if ("".equals(strC)) {
                return "";
            }
            int iIndexOf = strC.indexOf(58);
            if (iIndexOf >= 0) {
                return decrypt(HexUtil.byteArray2HexStr(HexUtil.hexStr2ByteArray(strC.substring(iIndexOf + 1))), bArr, HexUtil.hexStr2ByteArray(strC.substring(0, iIndexOf)));
            }
            b.b("GCM", " gcm cipherText data missing colon");
        }
        return "";
    }

    public static byte[] decryptWithCryptHeadReturnByte(byte[] bArr, byte[] bArr2) {
        if (bArr == null || bArr2 == null || bArr2.length < 16) {
            return new byte[0];
        }
        byte[] bArrD = AesCbc.d(bArr);
        if (bArrD.length == 0) {
            return new byte[0];
        }
        int iA = a(bArrD);
        if (iA < 0) {
            b.b("GCM", " gcm cipherText data missing colon");
            return new byte[0];
        }
        byte[] bArrCopyOf = Arrays.copyOf(bArrD, iA);
        int length = (bArrD.length - bArrCopyOf.length) - 1;
        byte[] bArr3 = new byte[length];
        System.arraycopy(bArrD, iA + 1, bArr3, 0, length);
        return decrypt(bArr3, bArr2, bArrCopyOf);
    }

    public static String encrypt(String str, String str2) throws UnsupportedEncodingException {
        if (TextUtils.isEmpty(str)) {
            b.b("GCM", "encrypt 1 content is null");
            return "";
        }
        if (TextUtils.isEmpty(str2)) {
            b.b("GCM", "encrypt 1 key is null");
            return "";
        }
        if (!isBuildVersionHigherThan19()) {
            b.b("GCM", "encrypt 1 build version not higher than 19");
            return "";
        }
        byte[] bArrHexStr2ByteArray = HexUtil.hexStr2ByteArray(str2);
        if (bArrHexStr2ByteArray.length >= 16) {
            return encrypt(str, bArrHexStr2ByteArray);
        }
        b.b("GCM", "encrypt 1 key length is not right");
        return "";
    }

    public static AlgorithmParameterSpec getGcmAlgorithmParams(byte[] bArr) {
        return Build.VERSION.SDK_INT < 21 ? new IvParameterSpec(bArr) : new GCMParameterSpec(128, bArr);
    }

    public static boolean isBuildVersionHigherThan19() {
        return Build.VERSION.SDK_INT >= 19;
    }

    private static byte[] c(byte[] bArr) {
        byte[] bArr2 = new byte[12];
        System.arraycopy(bArr, 0, bArr2, 0, 12);
        return bArr2;
    }

    private static String b(String str) {
        if (!TextUtils.isEmpty(str) && str.length() >= 24) {
            return str.substring(0, 24);
        }
        b.b("GCM", "IV is invalid.");
        return "";
    }

    public static String decrypt(String str, byte[] bArr) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, InvalidAlgorithmParameterException {
        if (TextUtils.isEmpty(str)) {
            b.b("GCM", "decrypt 2 content is null");
            return "";
        }
        if (bArr == null) {
            b.b("GCM", "decrypt 2 key is null");
            return "";
        }
        if (bArr.length < 16) {
            b.b("GCM", "decrypt 2 key lengh is not right");
            return "";
        }
        if (!isBuildVersionHigherThan19()) {
            b.b("GCM", "decrypt 2 build version not higher than 19");
            return "";
        }
        try {
            SecretKeySpec secretKeySpec = new SecretKeySpec(bArr, c);
            Cipher cipher = Cipher.getInstance(b);
            String strB = b(str);
            String strA = a(str);
            if (TextUtils.isEmpty(strB)) {
                b.b("GCM", "decrypt 2 iv is null");
                return "";
            }
            if (TextUtils.isEmpty(strA)) {
                b.b("GCM", "decrypt 2 encrypt content is null");
                return "";
            }
            cipher.init(2, secretKeySpec, getGcmAlgorithmParams(HexUtil.hexStr2ByteArray(strB)));
            return new String(cipher.doFinal(HexUtil.hexStr2ByteArray(strA)), "UTF-8");
        } catch (UnsupportedEncodingException | GeneralSecurityException e2) {
            b.b("GCM", "GCM decrypt data exception: " + e2.getMessage());
            return "";
        }
    }

    public static String decryptWithCryptHead(byte[] bArr, byte[] bArr2) {
        try {
            return new String(decryptWithCryptHeadReturnByte(bArr, bArr2), "UTF-8");
        } catch (UnsupportedEncodingException unused) {
            b.b("GCM", "UnsupportedEncodingException");
            return "";
        }
    }

    public static String encrypt(String str, byte[] bArr) throws NoSuchAlgorithmException {
        if (TextUtils.isEmpty(str)) {
            b.b("GCM", "encrypt 2 content is null");
            return "";
        }
        if (bArr == null) {
            b.b("GCM", "encrypt 2 key is null");
            return "";
        }
        if (bArr.length < 16) {
            b.b("GCM", "encrypt 2 key lengh is not right");
            return "";
        }
        if (!isBuildVersionHigherThan19()) {
            b.b("GCM", "encrypt 2 build version not higher than 19");
            return "";
        }
        byte[] bArrGenerateSecureRandom = EncryptUtil.generateSecureRandom(12);
        byte[] bArrA = a(str, bArr, bArrGenerateSecureRandom);
        if (bArrA == null || bArrA.length == 0) {
            return "";
        }
        return HexUtil.byteArray2HexStr(bArrGenerateSecureRandom) + HexUtil.byteArray2HexStr(bArrA);
    }

    private static byte[] a(byte[] bArr, byte[] bArr2) {
        byte[] bArr3 = new byte[bArr.length + bArr2.length];
        System.arraycopy(bArr, 0, bArr3, 0, bArr.length);
        System.arraycopy(bArr2, 0, bArr3, bArr.length, bArr2.length);
        return bArr3;
    }

    private static String a(String str) {
        return (TextUtils.isEmpty(str) || str.length() < 24) ? "" : str.substring(24);
    }

    private static int a(byte[] bArr) {
        return bArr[12] == 58 ? 12 : -1;
    }

    public static String encrypt(String str, String str2, String str3) throws UnsupportedEncodingException {
        if (TextUtils.isEmpty(str)) {
            b.b("GCM", "encrypt 3 content is null");
            return "";
        }
        if (TextUtils.isEmpty(str2)) {
            b.b("GCM", "encrypt 3 key is null");
            return "";
        }
        if (TextUtils.isEmpty(str3)) {
            b.b("GCM", "encrypt 3 iv is null");
            return "";
        }
        if (!isBuildVersionHigherThan19()) {
            b.b("GCM", "encrypt 3 build version not higher than 19");
            return "";
        }
        byte[] bArrHexStr2ByteArray = HexUtil.hexStr2ByteArray(str2);
        byte[] bArrHexStr2ByteArray2 = HexUtil.hexStr2ByteArray(str3);
        if (bArrHexStr2ByteArray.length < 16) {
            b.b("GCM", "encrypt 3 key length is not right");
            return "";
        }
        if (bArrHexStr2ByteArray2.length < 12) {
            b.b("GCM", "encrypt 3 iv length is not right");
            return "";
        }
        return encrypt(str, bArrHexStr2ByteArray, bArrHexStr2ByteArray2);
    }

    public static String decrypt(String str, String str2, String str3) throws UnsupportedEncodingException {
        if (TextUtils.isEmpty(str)) {
            b.b("GCM", "decrypt 3 content is null");
            return "";
        }
        if (TextUtils.isEmpty(str2)) {
            b.b("GCM", "decrypt 3 key is null");
            return "";
        }
        if (TextUtils.isEmpty(str3)) {
            b.b("GCM", "decrypt 3 iv is null");
            return "";
        }
        if (!isBuildVersionHigherThan19()) {
            b.b("GCM", "decrypt 3 build version not higher than 19");
            return "";
        }
        byte[] bArrHexStr2ByteArray = HexUtil.hexStr2ByteArray(str2);
        byte[] bArrHexStr2ByteArray2 = HexUtil.hexStr2ByteArray(str3);
        if (bArrHexStr2ByteArray.length < 16) {
            b.b("GCM", "decrypt 3 key length is not right");
            return "";
        }
        if (bArrHexStr2ByteArray2.length < 12) {
            b.b("GCM", "decrypt 3 iv length is not right");
            return "";
        }
        return decrypt(str, bArrHexStr2ByteArray, bArrHexStr2ByteArray2);
    }

    public static String encrypt(String str, byte[] bArr, byte[] bArr2) {
        if (TextUtils.isEmpty(str)) {
            b.b("GCM", "encrypt 4 content is null");
            return "";
        }
        if (bArr == null) {
            b.b("GCM", "encrypt 4 key is null");
            return "";
        }
        if (bArr.length < 16) {
            b.b("GCM", "encrypt 4 key lengh is not right");
            return "";
        }
        if (bArr2 == null) {
            b.b("GCM", "encrypt 4 iv is null");
            return "";
        }
        if (bArr2.length < 12) {
            b.b("GCM", "encrypt 4 iv lengh is not right");
            return "";
        }
        if (!isBuildVersionHigherThan19()) {
            b.b("GCM", "encrypt 4 build version not higher than 19");
            return "";
        }
        return HexUtil.byteArray2HexStr(a(str, bArr, bArr2));
    }

    public static String decrypt(String str, byte[] bArr, byte[] bArr2) {
        if (TextUtils.isEmpty(str)) {
            b.b("GCM", "decrypt 4 content is null");
            return "";
        }
        if (bArr == null) {
            b.b("GCM", "decrypt 4 key is null");
            return "";
        }
        if (bArr.length < 16) {
            b.b("GCM", "decrypt 4 key lengh is not right");
            return "";
        }
        if (bArr2 == null) {
            b.b("GCM", "decrypt 4 iv is null");
            return "";
        }
        if (bArr2.length < 12) {
            b.b("GCM", "decrypt 4 iv lengh is not right");
            return "";
        }
        if (!isBuildVersionHigherThan19()) {
            b.b("GCM", "decrypt 4 build version not higher than 19");
            return "";
        }
        try {
            return new String(decrypt(HexUtil.hexStr2ByteArray(str), bArr, bArr2), "UTF-8");
        } catch (UnsupportedEncodingException e2) {
            b.b("GCM", "GCM decrypt data exception: " + e2.getMessage());
            return "";
        }
    }

    public static byte[] encrypt(byte[] bArr, byte[] bArr2, byte[] bArr3) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, InvalidAlgorithmParameterException {
        if (bArr == null) {
            b.b("GCM", "encrypt 6 content is null");
            return new byte[0];
        }
        if (bArr.length == 0) {
            b.b("GCM", "encrypt 6 content length is 0");
            return new byte[0];
        }
        if (bArr2 == null) {
            b.b("GCM", "encrypt 6 key is null");
            return new byte[0];
        }
        if (bArr2.length < 16) {
            b.b("GCM", "encrypt 6 key length is error");
            return new byte[0];
        }
        if (bArr3 == null) {
            b.b("GCM", "encrypt 6 iv is null");
            return new byte[0];
        }
        if (bArr3.length < 12) {
            b.b("GCM", "encrypt 6 iv length is error");
            return new byte[0];
        }
        if (!isBuildVersionHigherThan19()) {
            b.b("GCM", "encrypt 6 build version not higher than 19");
            return new byte[0];
        }
        try {
            SecretKeySpec secretKeySpec = new SecretKeySpec(bArr2, c);
            Cipher cipher = Cipher.getInstance(b);
            cipher.init(1, secretKeySpec, getGcmAlgorithmParams(bArr3));
            return cipher.doFinal(bArr);
        } catch (GeneralSecurityException e2) {
            b.b("GCM", "GCM encrypt data error" + e2.getMessage());
            return new byte[0];
        }
    }

    public static byte[] decrypt(byte[] bArr, byte[] bArr2, byte[] bArr3) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, InvalidAlgorithmParameterException {
        if (bArr == null) {
            b.b("GCM", "decrypt 6 content is null");
            return new byte[0];
        }
        if (bArr.length == 0) {
            b.b("GCM", "decrypt 6 content length is 0");
            return new byte[0];
        }
        if (bArr2 == null) {
            b.b("GCM", "decrypt 6 key is null");
            return new byte[0];
        }
        if (bArr2.length < 16) {
            b.b("GCM", "decrypt 6 key length is error");
            return new byte[0];
        }
        if (bArr3 == null) {
            b.b("GCM", "decrypt 6 iv is null");
            return new byte[0];
        }
        if (bArr3.length < 12) {
            b.b("GCM", "decrypt 6 iv length is error");
            return new byte[0];
        }
        if (!isBuildVersionHigherThan19()) {
            b.b("GCM", "decrypt 6 build version not higher than 19");
            return new byte[0];
        }
        try {
            SecretKeySpec secretKeySpec = new SecretKeySpec(bArr2, c);
            Cipher cipher = Cipher.getInstance(b);
            cipher.init(2, secretKeySpec, getGcmAlgorithmParams(bArr3));
            return cipher.doFinal(bArr);
        } catch (GeneralSecurityException e2) {
            b.b("GCM", "GCM decrypt data exception: " + e2.getMessage());
            return new byte[0];
        }
    }

    public static byte[] encrypt(byte[] bArr, byte[] bArr2) throws NoSuchAlgorithmException {
        byte[] bArrGenerateSecureRandom = EncryptUtil.generateSecureRandom(12);
        return a(bArrGenerateSecureRandom, encrypt(bArr, bArr2, bArrGenerateSecureRandom));
    }

    public static byte[] decrypt(byte[] bArr, byte[] bArr2) {
        return decrypt(b(bArr), bArr2, c(bArr));
    }
}
