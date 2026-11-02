package com.huawei.secure.android.common.encrypt.keystore.aes;

import android.os.Build;
import android.security.keystore.KeyGenParameterSpec;
import android.text.TextUtils;
import com.huawei.secure.android.common.encrypt.utils.HexUtil;
import com.huawei.secure.android.common.encrypt.utils.b;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;

/* loaded from: classes2.dex */
public class AesCbcKS {
    private static final String a = "CBCKS";
    private static final String b = "AndroidKeyStore";
    private static final String c = "AES/CBC/PKCS7Padding";
    private static final String d = "";
    private static final int e = 16;
    private static final int f = 256;
    private static Map<String, SecretKey> g = new HashMap();

    private static synchronized SecretKey a(String str) {
        SecretKey secretKeyGenerateKey;
        b.c(a, "load key");
        secretKeyGenerateKey = null;
        try {
            try {
                try {
                    try {
                        try {
                            try {
                                KeyStore keyStore = KeyStore.getInstance(b);
                                keyStore.load(null);
                                Key key = keyStore.getKey(str, null);
                                if (key == null || !(key instanceof SecretKey)) {
                                    b.c(a, "generate key");
                                    KeyGenerator keyGenerator = KeyGenerator.getInstance("AES", b);
                                    keyGenerator.init(new KeyGenParameterSpec.Builder(str, 3).setBlockModes("CBC").setEncryptionPaddings("PKCS7Padding").setKeySize(256).build());
                                    secretKeyGenerateKey = keyGenerator.generateKey();
                                } else {
                                    secretKeyGenerateKey = (SecretKey) key;
                                }
                            } catch (CertificateException e2) {
                                b.b(a, "CertificateException: " + e2.getMessage());
                            }
                        } catch (InvalidAlgorithmParameterException e3) {
                            b.b(a, "InvalidAlgorithmParameterException: " + e3.getMessage());
                        }
                    } catch (NoSuchProviderException e4) {
                        b.b(a, "NoSuchProviderException: " + e4.getMessage());
                    }
                } catch (UnrecoverableKeyException e5) {
                    b.b(a, "UnrecoverableKeyException: " + e5.getMessage());
                }
            } catch (IOException e6) {
                b.b(a, "IOException: " + e6.getMessage());
            } catch (Exception e7) {
                b.b(a, "Exception: " + e7.getMessage());
            }
        } catch (KeyStoreException e8) {
            b.b(a, "KeyStoreException: " + e8.getMessage());
        } catch (NoSuchAlgorithmException e9) {
            b.b(a, "NoSuchAlgorithmException: " + e9.getMessage());
        }
        g.put(str, secretKeyGenerateKey);
        return secretKeyGenerateKey;
    }

    private static SecretKey b(String str) {
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        if (g.get(str) == null) {
            a(str);
        }
        return g.get(str);
    }

    public static String decrypt(String str, String str2) {
        if (TextUtils.isEmpty(str) || TextUtils.isEmpty(str2)) {
            b.b(a, "alias or encrypt content is null");
            return "";
        }
        try {
            return new String(decrypt(str, HexUtil.hexStr2ByteArray(str2)), "UTF-8");
        } catch (UnsupportedEncodingException unused) {
            b.b(a, "encrypt: UnsupportedEncodingException");
            return "";
        }
    }

    public static String encrypt(String str, String str2) {
        if (TextUtils.isEmpty(str2)) {
            b.b(a, "encrypt 1 content is null");
            return "";
        }
        try {
            return HexUtil.byteArray2HexStr(encrypt(str, str2.getBytes("UTF-8")));
        } catch (UnsupportedEncodingException unused) {
            b.b(a, "encrypt: UnsupportedEncodingException");
            return "";
        }
    }

    public static byte[] encrypt(String str, byte[] bArr) throws BadPaddingException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, InvalidKeyException {
        byte[] bArr2 = new byte[0];
        if (!TextUtils.isEmpty(str) && bArr != null) {
            if (!a()) {
                b.b(a, "sdk version is too low");
                return bArr2;
            }
            try {
                Cipher cipher = Cipher.getInstance(c);
                SecretKey secretKeyB = b(str);
                if (secretKeyB == null) {
                    b.b(a, "encrypt secret key is null");
                    return bArr2;
                }
                cipher.init(1, secretKeyB);
                byte[] bArrDoFinal = cipher.doFinal(bArr);
                byte[] iv = cipher.getIV();
                if (iv != null && iv.length == 16) {
                    byte[] bArrCopyOf = Arrays.copyOf(iv, iv.length + bArrDoFinal.length);
                    System.arraycopy(bArrDoFinal, 0, bArrCopyOf, iv.length, bArrDoFinal.length);
                    return bArrCopyOf;
                }
                b.b(a, "IV is invalid.");
                return bArr2;
            } catch (InvalidKeyException e2) {
                b.b(a, "InvalidKeyException: " + e2.getMessage());
                return bArr2;
            } catch (NoSuchAlgorithmException e3) {
                b.b(a, "NoSuchAlgorithmException: " + e3.getMessage());
                return bArr2;
            } catch (BadPaddingException e4) {
                b.b(a, "BadPaddingException: " + e4.getMessage());
                return bArr2;
            } catch (IllegalBlockSizeException e5) {
                b.b(a, "IllegalBlockSizeException: " + e5.getMessage());
                return bArr2;
            } catch (NoSuchPaddingException e6) {
                b.b(a, "NoSuchPaddingException: " + e6.getMessage());
                return bArr2;
            } catch (Exception e7) {
                b.b(a, "Exception: " + e7.getMessage());
                return bArr2;
            }
        }
        b.b(a, "alias or encrypt content is null");
        return bArr2;
    }

    public static byte[] decrypt(String str, byte[] bArr) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, InvalidAlgorithmParameterException {
        byte[] bArr2 = new byte[0];
        if (!TextUtils.isEmpty(str) && bArr != null) {
            if (!a()) {
                b.b(a, "sdk version is too low");
                return bArr2;
            }
            if (bArr.length <= 16) {
                b.b(a, "Decrypt source data is invalid.");
                return bArr2;
            }
            SecretKey secretKeyB = b(str);
            if (secretKeyB == null) {
                b.b(a, "decrypt secret key is null");
                return bArr2;
            }
            byte[] bArrCopyOf = Arrays.copyOf(bArr, 16);
            try {
                Cipher cipher = Cipher.getInstance(c);
                cipher.init(2, secretKeyB, new IvParameterSpec(bArrCopyOf));
                return cipher.doFinal(bArr, 16, bArr.length - 16);
            } catch (InvalidAlgorithmParameterException e2) {
                b.b(a, "InvalidAlgorithmParameterException: " + e2.getMessage());
                return bArr2;
            } catch (InvalidKeyException e3) {
                b.b(a, "InvalidKeyException: " + e3.getMessage());
                return bArr2;
            } catch (NoSuchAlgorithmException e4) {
                b.b(a, "NoSuchAlgorithmException: " + e4.getMessage());
                return bArr2;
            } catch (BadPaddingException e5) {
                b.b(a, "BadPaddingException: " + e5.getMessage());
                return bArr2;
            } catch (IllegalBlockSizeException e6) {
                b.b(a, "IllegalBlockSizeException: " + e6.getMessage());
                return bArr2;
            } catch (NoSuchPaddingException e7) {
                b.b(a, "NoSuchPaddingException: " + e7.getMessage());
                return bArr2;
            } catch (Exception e8) {
                b.b(a, "Exception: " + e8.getMessage());
                return bArr2;
            }
        }
        b.b(a, "alias or encrypt content is null");
        return bArr2;
    }

    private static boolean a() {
        return Build.VERSION.SDK_INT >= 23;
    }
}
