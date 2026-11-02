package org.apache.poi.hssf.record.crypto;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import org.apache.commons.codec.digest.MessageDigestAlgorithms;
import org.apache.poi.poifs.crypt.Decryptor;
import org.apache.poi.util.HexDump;
import org.apache.poi.util.LittleEndianOutputStream;

/* loaded from: classes3.dex */
public final class Biff8EncryptionKey {
    private static final int KEY_DIGEST_LENGTH = 5;
    private static final int PASSWORD_HASH_NUMBER_OF_BYTES_USED = 5;
    private static final ThreadLocal<String> _userPasswordTLS = new ThreadLocal<>();
    private final byte[] _keyDigest;

    public static Biff8EncryptionKey create(byte[] bArr) {
        return new Biff8EncryptionKey(createKeyDigest(Decryptor.DEFAULT_PASSWORD, bArr));
    }

    public static Biff8EncryptionKey create(String str, byte[] bArr) {
        return new Biff8EncryptionKey(createKeyDigest(str, bArr));
    }

    Biff8EncryptionKey(byte[] bArr) {
        if (bArr.length != 5) {
            throw new IllegalArgumentException("Expected 5 byte key digest, but got " + HexDump.toHex(bArr));
        }
        this._keyDigest = bArr;
    }

    static byte[] createKeyDigest(String str, byte[] bArr) throws NoSuchAlgorithmException {
        check16Bytes(bArr, "docId");
        int iMin = Math.min(str.length(), 16);
        byte[] bArr2 = new byte[iMin * 2];
        for (int i = 0; i < iMin; i++) {
            char cCharAt = str.charAt(i);
            int i2 = i * 2;
            bArr2[i2 + 0] = (byte) ((cCharAt << 0) & 255);
            bArr2[i2 + 1] = (byte) ((cCharAt << '\b') & 255);
        }
        try {
            MessageDigest messageDigest = MessageDigest.getInstance(MessageDigestAlgorithms.MD5);
            messageDigest.update(bArr2);
            byte[] bArrDigest = messageDigest.digest();
            messageDigest.reset();
            for (int i3 = 0; i3 < 16; i3++) {
                messageDigest.update(bArrDigest, 0, 5);
                messageDigest.update(bArr, 0, bArr.length);
            }
            byte[] bArr3 = new byte[5];
            System.arraycopy(messageDigest.digest(), 0, bArr3, 0, 5);
            return bArr3;
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean validate(byte[] bArr, byte[] bArr2) throws NoSuchAlgorithmException, IOException {
        check16Bytes(bArr, "saltData");
        check16Bytes(bArr2, "saltHash");
        RC4 rc4CreateRC4 = createRC4(0);
        byte[] bArr3 = (byte[]) bArr.clone();
        rc4CreateRC4.encrypt(bArr3);
        byte[] bArr4 = (byte[]) bArr2.clone();
        rc4CreateRC4.encrypt(bArr4);
        try {
            MessageDigest messageDigest = MessageDigest.getInstance(MessageDigestAlgorithms.MD5);
            messageDigest.update(bArr3);
            return Arrays.equals(bArr4, messageDigest.digest());
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    private static byte[] xor(byte[] bArr, byte[] bArr2) {
        int length = bArr.length;
        byte[] bArr3 = new byte[length];
        for (int i = 0; i < length; i++) {
            bArr3[i] = (byte) (bArr[i] ^ bArr2[i]);
        }
        return bArr3;
    }

    private static void check16Bytes(byte[] bArr, String str) {
        if (bArr.length == 16) {
            return;
        }
        throw new IllegalArgumentException("Expected 16 byte " + str + ", but got " + HexDump.toHex(bArr));
    }

    RC4 createRC4(int i) throws NoSuchAlgorithmException, IOException {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance(MessageDigestAlgorithms.MD5);
            messageDigest.update(this._keyDigest);
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(4);
            new LittleEndianOutputStream(byteArrayOutputStream).writeInt(i);
            messageDigest.update(byteArrayOutputStream.toByteArray());
            return new RC4(messageDigest.digest());
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    public static void setCurrentUserPassword(String str) {
        _userPasswordTLS.set(str);
    }

    public static String getCurrentUserPassword() {
        return _userPasswordTLS.get();
    }
}
