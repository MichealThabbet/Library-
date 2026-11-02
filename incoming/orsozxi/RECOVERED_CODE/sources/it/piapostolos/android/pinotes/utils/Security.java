package it.piapostolos.android.pinotes.utils;

import android.util.Base64;
import it.piapostolos.android.pinotes.helpers.LogDelegate;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import org.apache.commons.codec.digest.MessageDigestAlgorithms;

/* loaded from: classes3.dex */
public class Security {
    private Security() {
    }

    public static String md5(String str) throws NoSuchAlgorithmException {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance(MessageDigestAlgorithms.MD5);
            messageDigest.update(str.getBytes());
            byte[] bArrDigest = messageDigest.digest();
            StringBuilder sb = new StringBuilder();
            for (byte b : bArrDigest) {
                sb.append(Integer.toHexString(b & 255));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            LogDelegate.w("Something is gone wrong calculating MD5", e);
            return "";
        }
    }

    public static String encrypt(String str, String str2) throws InvalidKeySpecException, NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException {
        try {
            SecretKey secretKeyGenerateSecret = SecretKeyFactory.getInstance("DES").generateSecret(new DESKeySpec(str2.getBytes(StandardCharsets.UTF_8)));
            byte[] bytes = str.getBytes(StandardCharsets.UTF_8);
            Cipher cipher = Cipher.getInstance("DES");
            cipher.init(1, secretKeyGenerateSecret);
            return Base64.encodeToString(cipher.doFinal(bytes), 0);
        } catch (InvalidKeyException | NoSuchAlgorithmException | InvalidKeySpecException | BadPaddingException | IllegalBlockSizeException | NoSuchPaddingException e) {
            LogDelegate.w("Something is gone wrong encrypting", e);
            return "";
        }
    }

    public static String decrypt(String str, String str2) throws InvalidKeySpecException, NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException {
        try {
            SecretKey secretKeyGenerateSecret = SecretKeyFactory.getInstance("DES").generateSecret(new DESKeySpec(str2.getBytes(StandardCharsets.UTF_8)));
            byte[] bArrDecode = Base64.decode(str, 0);
            Cipher cipher = Cipher.getInstance("DES");
            cipher.init(2, secretKeyGenerateSecret);
            return new String(cipher.doFinal(bArrDecode));
        } catch (IllegalArgumentException unused) {
            LogDelegate.e("Error decrypting: old notes were not encrypted but just masked to users");
            return str;
        } catch (InvalidKeyException | NoSuchAlgorithmException | InvalidKeySpecException | BadPaddingException | IllegalBlockSizeException | NoSuchPaddingException unused2) {
            LogDelegate.e("Error decrypting");
            return str;
        }
    }
}
