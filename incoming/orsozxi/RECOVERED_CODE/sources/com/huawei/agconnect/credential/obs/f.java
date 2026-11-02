package com.huawei.agconnect.credential.obs;

import android.text.TextUtils;
import com.huawei.agconnect.common.api.Logger;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import net.lingala.zip4j.crypto.PBKDF2.BinTools;
import org.apache.commons.codec.digest.MessageDigestAlgorithms;

/* loaded from: classes2.dex */
final class f {
    private static final char[] a = BinTools.hex.toCharArray();

    static String a(String str, String str2) throws NoSuchAlgorithmException {
        try {
            if (TextUtils.isEmpty(str2)) {
                str2 = MessageDigestAlgorithms.SHA_256;
            }
            MessageDigest messageDigest = MessageDigest.getInstance(str2);
            messageDigest.update(str.getBytes(Charset.defaultCharset()));
            return a(messageDigest.digest());
        } catch (NoSuchAlgorithmException e) {
            Logger.e("", "", e);
            return null;
        }
    }

    static String a(byte[] bArr) {
        StringBuilder sb = new StringBuilder(bArr.length * 2);
        for (byte b : bArr) {
            sb.append(a[(b >> 4) & 15]);
            sb.append(a[b & 15]);
        }
        return sb.toString();
    }
}
