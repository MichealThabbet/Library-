package org.apache.poi.poifs.crypt;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.spec.SecretKeySpec;
import org.apache.commons.codec.digest.MessageDigestAlgorithms;
import org.apache.poi.poifs.filesystem.DocumentInputStream;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.util.LittleEndian;

/* loaded from: classes3.dex */
public class Decryptor {
    public static final String DEFAULT_PASSWORD = "VelvetSweatshop";
    private final EncryptionInfo info;
    private byte[] passwordHash;

    public Decryptor(EncryptionInfo encryptionInfo) {
        this.info = encryptionInfo;
    }

    private void generatePasswordHash(String str) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        MessageDigest messageDigest = MessageDigest.getInstance(MessageDigestAlgorithms.SHA_1);
        try {
            byte[] bytes = str.getBytes("UTF-16LE");
            messageDigest.update(this.info.getVerifier().getSalt());
            byte[] bArrDigest = messageDigest.digest(bytes);
            byte[] bArr = new byte[4];
            for (int i = 0; i < 50000; i++) {
                messageDigest.reset();
                LittleEndian.putInt(bArr, i);
                messageDigest.update(bArr);
                bArrDigest = messageDigest.digest(bArrDigest);
            }
            this.passwordHash = bArrDigest;
        } catch (UnsupportedEncodingException unused) {
            throw new RuntimeException("Your JVM is broken - UTF16 not found!");
        }
    }

    private byte[] generateKey(int i) throws NoSuchAlgorithmException {
        MessageDigest messageDigest = MessageDigest.getInstance(MessageDigestAlgorithms.SHA_1);
        messageDigest.update(this.passwordHash);
        byte[] bArr = new byte[4];
        LittleEndian.putInt(bArr, i);
        byte[] bArrDigest = messageDigest.digest(bArr);
        int keySize = this.info.getHeader().getKeySize() / 8;
        byte[] bArr2 = new byte[64];
        Arrays.fill(bArr2, (byte) 54);
        for (int i2 = 0; i2 < bArrDigest.length; i2++) {
            bArr2[i2] = (byte) (bArr2[i2] ^ bArrDigest[i2]);
        }
        messageDigest.reset();
        byte[] bArrDigest2 = messageDigest.digest(bArr2);
        Arrays.fill(bArr2, (byte) 92);
        for (int i3 = 0; i3 < bArrDigest.length; i3++) {
            bArr2[i3] = (byte) (bArr2[i3] ^ bArrDigest[i3]);
        }
        messageDigest.reset();
        byte[] bArrDigest3 = messageDigest.digest(bArr2);
        byte[] bArr3 = new byte[bArrDigest2.length + bArrDigest3.length];
        System.arraycopy(bArrDigest2, 0, bArr3, 0, bArrDigest2.length);
        System.arraycopy(bArrDigest3, 0, bArr3, bArrDigest2.length, bArrDigest3.length);
        return truncateOrPad(bArr3, keySize);
    }

    public boolean verifyPassword(String str) throws GeneralSecurityException, UnsupportedEncodingException {
        generatePasswordHash(str);
        Cipher cipher = getCipher();
        byte[] bArrDigest = MessageDigest.getInstance(MessageDigestAlgorithms.SHA_1).digest(cipher.doFinal(this.info.getVerifier().getVerifier()));
        return Arrays.equals(bArrDigest, truncateOrPad(cipher.doFinal(this.info.getVerifier().getVerifierHash()), bArrDigest.length));
    }

    private byte[] truncateOrPad(byte[] bArr, int i) {
        byte[] bArr2 = new byte[i];
        System.arraycopy(bArr, 0, bArr2, 0, Math.min(i, bArr.length));
        if (i > bArr.length) {
            for (int length = bArr.length; length < i; length++) {
                bArr2[length] = 0;
            }
        }
        return bArr2;
    }

    private Cipher getCipher() throws GeneralSecurityException {
        byte[] bArrGenerateKey = generateKey(0);
        Cipher cipher = Cipher.getInstance("AES/ECB/NoPadding");
        cipher.init(2, new SecretKeySpec(bArrGenerateKey, "AES"));
        return cipher;
    }

    public InputStream getDataStream(POIFSFileSystem pOIFSFileSystem) throws GeneralSecurityException, IOException {
        DocumentInputStream documentInputStreamCreateDocumentInputStream = pOIFSFileSystem.createDocumentInputStream("EncryptedPackage");
        documentInputStreamCreateDocumentInputStream.readLong();
        return new CipherInputStream(documentInputStreamCreateDocumentInputStream, getCipher());
    }
}
