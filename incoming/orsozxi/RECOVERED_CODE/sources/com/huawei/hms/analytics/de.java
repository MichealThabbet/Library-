package com.huawei.hms.analytics;

import android.text.TextUtils;
import com.huawei.hms.analytics.core.log.HiLog;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import org.apache.commons.codec.digest.MessageDigestAlgorithms;

/* loaded from: classes2.dex */
public final class de {
    private static final String[] lmn = {MessageDigestAlgorithms.SHA_256, MessageDigestAlgorithms.SHA_384, MessageDigestAlgorithms.SHA_512};

    public static String lmn(String str) {
        return lmn(str, MessageDigestAlgorithms.SHA_256);
    }

    private static String lmn(String str, String str2) throws NoSuchAlgorithmException {
        boolean z;
        String str3;
        if (TextUtils.isEmpty(str) || TextUtils.isEmpty(str2)) {
            HiLog.e("SHA", "content or algorithm is null.");
            return "";
        }
        String[] strArr = lmn;
        int length = strArr.length;
        int i = 0;
        while (true) {
            if (i >= length) {
                z = false;
                break;
            }
            if (strArr[i].equals(str2)) {
                z = true;
                break;
            }
            i++;
        }
        if (z) {
            try {
                MessageDigest messageDigest = MessageDigest.getInstance(str2);
                messageDigest.update(str.getBytes("UTF-8"));
                byte[] bArrDigest = messageDigest.digest();
                if (bArrDigest == null || bArrDigest.length == 0) {
                    return "";
                }
                StringBuilder sb = new StringBuilder();
                for (byte b : bArrDigest) {
                    String hexString = Integer.toHexString(b & 255);
                    if (hexString.length() == 1) {
                        sb.append('0');
                    }
                    sb.append(hexString);
                }
                return sb.toString();
            } catch (UnsupportedEncodingException unused) {
                str3 = "Error in generate SHA UnsupportedEncodingException";
            } catch (NoSuchAlgorithmException unused2) {
                str3 = "Error in generate SHA NoSuchAlgorithmException";
            }
        } else {
            str3 = "algorithm is not safe or legal";
        }
        HiLog.e("SHA", str3);
        return "";
    }
}
