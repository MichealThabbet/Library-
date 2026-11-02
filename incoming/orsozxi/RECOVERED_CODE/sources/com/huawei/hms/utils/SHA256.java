package com.huawei.hms.utils;

import com.huawei.hms.support.log.HMSLog;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import org.apache.commons.codec.digest.MessageDigestAlgorithms;

/* loaded from: classes2.dex */
public abstract class SHA256 {
    public static byte[] digest(byte[] bArr) {
        try {
            return MessageDigest.getInstance(MessageDigestAlgorithms.SHA_256).digest(bArr);
        } catch (NoSuchAlgorithmException e) {
            HMSLog.e("SHA256", "NoSuchAlgorithmException" + e.getMessage());
            return new byte[0];
        }
    }

    public static byte[] digest(File file) throws Throwable {
        MessageDigest messageDigest;
        BufferedInputStream bufferedInputStream;
        int i;
        InputStream inputStream = null;
        BufferedInputStream bufferedInputStream2 = null;
        try {
            try {
                messageDigest = MessageDigest.getInstance(MessageDigestAlgorithms.SHA_256);
                bufferedInputStream = new BufferedInputStream(new FileInputStream(file));
            } catch (Throwable th) {
                th = th;
            }
        } catch (IOException | NoSuchAlgorithmException unused) {
        }
        try {
            byte[] bArr = new byte[4096];
            i = 0;
            while (true) {
                int i2 = bufferedInputStream.read(bArr);
                if (i2 == -1) {
                    break;
                }
                messageDigest.update(bArr, 0, i2);
                i += i2;
            }
        } catch (IOException | NoSuchAlgorithmException unused2) {
            bufferedInputStream2 = bufferedInputStream;
            HMSLog.e("SHA256", "An exception occurred while computing file 'SHA-256'.");
            IOUtils.closeQuietly((InputStream) bufferedInputStream2);
            inputStream = bufferedInputStream2;
            return new byte[0];
        } catch (Throwable th2) {
            th = th2;
            inputStream = bufferedInputStream;
            IOUtils.closeQuietly(inputStream);
            throw th;
        }
        if (i > 0) {
            byte[] bArrDigest = messageDigest.digest();
            IOUtils.closeQuietly((InputStream) bufferedInputStream);
            return bArrDigest;
        }
        IOUtils.closeQuietly((InputStream) bufferedInputStream);
        inputStream = i;
        return new byte[0];
    }
}
