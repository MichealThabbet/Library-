package com.huawei.agconnect.credential.obs;

import android.text.TextUtils;
import com.huawei.agconnect.common.api.Logger;
import com.huawei.agconnect.common.api.RandomWrapper;
import com.huawei.agconnect.datastore.annotation.SharedPreference;
import com.huawei.secure.android.common.util.HexUtil;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

/* loaded from: classes2.dex */
public class h {
    public static final h a = new h();
    private String b = null;
    private SecretKey c;

    @SharedPreference(fileName = "com.huawei.appgallery.datastore", key = "fish")
    String fish;

    @SharedPreference(fileName = "com.huawei.agconnect.internal", key = "jerry")
    String jerry;

    @SharedPreference(fileName = "com.huawei.appgallery.datastore", key = "key")
    String randmonKey;

    private h() {
    }

    private String a(int i) {
        return HexUtil.byteArray2HexStr(RandomWrapper.generateSecureRandom(i / 2));
    }

    private static String a(String str, int i) throws UnsupportedEncodingException {
        byte[] bArrHexStr2ByteArray = HexUtil.hexStr2ByteArray(str);
        if (bArrHexStr2ByteArray == null || bArrHexStr2ByteArray.length == 0) {
            return null;
        }
        for (int i2 = 0; i2 < bArrHexStr2ByteArray.length; i2++) {
            if (i < 0) {
                bArrHexStr2ByteArray[i2] = (byte) (bArrHexStr2ByteArray[i2] << (-i));
            } else {
                bArrHexStr2ByteArray[i2] = (byte) (bArrHexStr2ByteArray[i2] >> i);
            }
        }
        return HexUtil.byteArray2HexStr(bArrHexStr2ByteArray);
    }

    private static String a(String str, String str2) throws UnsupportedEncodingException {
        byte[] bArrHexStr2ByteArray = HexUtil.hexStr2ByteArray(str);
        byte[] bArrHexStr2ByteArray2 = HexUtil.hexStr2ByteArray(str2);
        if (bArrHexStr2ByteArray == null || bArrHexStr2ByteArray2 == null || bArrHexStr2ByteArray.length != bArrHexStr2ByteArray2.length) {
            return null;
        }
        int length = bArrHexStr2ByteArray.length;
        byte[] bArr = new byte[length];
        for (int i = 0; i < length; i++) {
            bArr[i] = (byte) (bArrHexStr2ByteArray[i] ^ bArrHexStr2ByteArray2[i]);
        }
        return HexUtil.byteArray2HexStr(bArr);
    }

    private static String a(String str, String str2, String str3) {
        return a(a(a(a(str, -4), str2), 6), str3);
    }

    private static SecretKey a(String str, String str2, String str3, String str4) {
        if (str.length() != 32 || str2.length() != 32 || str3.length() != 32) {
            throw new IllegalArgumentException("invalid data for generating the key.");
        }
        return new SecretKeySpec(SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1").generateSecret(new PBEKeySpec(a(str, str2, str3).toCharArray(), HexUtil.hexStr2ByteArray(str4), 5000, 128)).getEncoded(), "AES");
    }

    private boolean a(String str) {
        return TextUtils.isEmpty(str) || str.length() != 32;
    }

    private String c() {
        i.a().d(this);
        if (TextUtils.isEmpty(this.randmonKey) || this.randmonKey.length() != 16) {
            this.randmonKey = a(16);
            i.a().a(this);
        }
        return this.randmonKey;
    }

    private void d() {
        i.a().f(this);
        i.a().e(this);
        if (a(this.jerry) || a(this.fish)) {
            this.jerry = a(32);
            this.fish = a(32);
            i.a().b(this);
            i.a().c(this);
        }
        try {
            this.c = a(w.d(), this.jerry, "767d9b01105c2acd25fb7955a5394f78", this.fish);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException unused) {
            Logger.e("KeyManager", "Exception when reading the 'T&J&C&F' for 'KeyManager'.");
            this.c = null;
        }
    }

    public synchronized String a() {
        if (TextUtils.isEmpty(this.b)) {
            this.b = a(a(w.c(), 6), g.a()) + a(a("AE6D8285", -4), "4D5480E9") + c();
        }
        return this.b;
    }

    public synchronized SecretKey b() {
        if (this.c == null) {
            d();
        }
        return this.c;
    }
}
