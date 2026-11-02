package com.huawei.hms.analytics;

import android.os.Build;
import android.text.TextUtils;
import com.huawei.hms.analytics.core.crypto.AesCipher;
import com.huawei.hms.analytics.core.crypto.HAHexUtil;
import com.huawei.hms.analytics.core.crypto.PBKDF2encrypt;
import com.huawei.hms.analytics.core.crypto.RandomUtil;
import com.huawei.hms.analytics.core.log.HiLog;
import java.util.List;

/* loaded from: classes2.dex */
public final class bo {
    private static bo klm;
    private String ghi;
    private String hij;
    public List<lmn> lmn;
    private static final Object ikl = new Object();
    private static final Object ijk = new Object();

    public interface lmn {
        void klm(String str);
    }

    private static boolean fgh() {
        return Build.VERSION.SDK_INT >= 23;
    }

    private String ghi() {
        String strLmn;
        String strLmn2;
        String strLmn3;
        String strLmn4;
        if (TextUtils.isEmpty(this.hij)) {
            synchronized (ijk) {
                if (TextUtils.isEmpty(this.hij)) {
                    bn bnVar = new bn();
                    long jKlm = dl.klm(bnVar.lmn, "Privacy_MY", "assemblyFlash");
                    boolean z = true;
                    if (-1 == jKlm) {
                        HiLog.i("ComponentCommander", "First init components");
                    } else if (System.currentTimeMillis() - jKlm <= 31536000000L) {
                        z = false;
                    }
                    if (z) {
                        HiLog.i("ComponentCommander", "refresh components");
                        strLmn = RandomUtil.getInstance().generateSecureRandomStr(128);
                        bnVar.lmn("aprpap", strLmn);
                        strLmn2 = RandomUtil.getInstance().generateSecureRandomStr(128);
                        bnVar.lmn("febdoc", strLmn2);
                        strLmn3 = RandomUtil.getInstance().generateSecureRandomStr(128);
                        bnVar.lmn("marfil", strLmn3);
                        strLmn4 = RandomUtil.getInstance().generateSecureRandomStr(128);
                        bnVar.lmn("maywnj", strLmn4);
                        dl.lmn(bnVar.lmn, "Privacy_MY", "assemblyFlash", System.currentTimeMillis());
                    } else {
                        strLmn = bnVar.lmn("aprpap");
                        strLmn2 = bnVar.lmn("febdoc");
                        strLmn3 = bnVar.lmn("marfil");
                        strLmn4 = bnVar.lmn("maywnj");
                    }
                    byte[] bArrHexString2ByteArray = HAHexUtil.hexString2ByteArray(strLmn);
                    byte[] bArrHexString2ByteArray2 = HAHexUtil.hexString2ByteArray(strLmn2);
                    byte[] bArrHexString2ByteArray3 = HAHexUtil.hexString2ByteArray(strLmn3);
                    byte[] bArrHexString2ByteArray4 = HAHexUtil.hexString2ByteArray("f6040d0e807aaec325ecf44823765544e92905158169f694b282bf17388632cf95a83bae7d2d235c1f039b0df1dcca5fda619b6f7f459f2ff8d70ddb7b601592fe29fcae58c028f319b3b12495e67aa5390942a997a8cb572c8030b2df5c2b622608bea02b0c3e5d4dff3f72c9e3204049a45c0760cd3604af8d57f0e0c693cc");
                    int length = bArrHexString2ByteArray.length;
                    if (length > bArrHexString2ByteArray2.length) {
                        length = bArrHexString2ByteArray2.length;
                    }
                    if (length > bArrHexString2ByteArray3.length) {
                        length = bArrHexString2ByteArray3.length;
                    }
                    if (length > bArrHexString2ByteArray4.length) {
                        length = bArrHexString2ByteArray4.length;
                    }
                    char[] cArr = new char[length];
                    for (int i = 0; i < length; i++) {
                        cArr[i] = (char) (((bArrHexString2ByteArray[i] ^ bArrHexString2ByteArray2[i]) ^ bArrHexString2ByteArray3[i]) ^ bArrHexString2ByteArray4[i]);
                    }
                    this.hij = HAHexUtil.byteArray2HexString(PBKDF2encrypt.pbkdf2(cArr, HAHexUtil.hexString2ByteArray(strLmn4), 10000, 128));
                }
            }
        }
        return this.hij;
    }

    private static synchronized void hij() {
        if (klm == null) {
            klm = new bo();
        }
    }

    public static boolean ikl() {
        long jKlm = dl.klm(ar.lmn().lmn.ghi, "Privacy_MY", "flashKeyTime");
        return jKlm != -1 && System.currentTimeMillis() - jKlm > 31536000000L;
    }

    private String klm(String str) {
        if (!fgh()) {
            return AesCipher.encryptCbc(str, ghi());
        }
        HiLog.i("RootKeyCommander", "load work key encrypt is gcm ks");
        return AesCipher.aesKsEncrypt("analytics_keystore", str);
    }

    public static bo lmn() {
        if (klm == null) {
            hij();
        }
        return klm;
    }

    private static void lmn(String str) {
        at atVar = ar.lmn().lmn;
        dl.lmn(atVar.ghi, "Privacy_MY", "PrivacyData", str);
        dl.lmn(atVar.ghi, "Privacy_MY", "flashKeyTime", System.currentTimeMillis());
    }

    public final void ijk() {
        synchronized (ikl) {
            String strGenerateSecureRandomStr = RandomUtil.getInstance().generateSecureRandomStr(16);
            String strKlm = klm(this.ghi);
            if (TextUtils.isEmpty(strKlm)) {
                HiLog.e("RootKeyCommander", "New working secret key encryption failed");
                return;
            }
            this.ghi = strGenerateSecureRandomStr;
            lmn(strKlm);
            if (this.lmn == null) {
                return;
            }
            for (lmn lmnVar : this.lmn) {
                if (lmnVar != null) {
                    lmnVar.klm(strGenerateSecureRandomStr);
                }
            }
        }
    }

    public final String klm() {
        String strDecryptCbc;
        synchronized (ikl) {
            if (TextUtils.isEmpty(this.ghi) && TextUtils.isEmpty(this.ghi)) {
                String strKlm = dl.klm(ar.lmn().lmn.ghi, "Privacy_MY", "PrivacyData", "");
                if (TextUtils.isEmpty(strKlm)) {
                    strDecryptCbc = RandomUtil.getInstance().generateSecureRandomStr(16);
                    lmn(klm(strDecryptCbc));
                } else {
                    String strAesKsDecrypt = fgh() ? AesCipher.aesKsDecrypt("analytics_keystore", strKlm) : "";
                    if (TextUtils.isEmpty(strAesKsDecrypt)) {
                        HiLog.i("RootKeyCommander", "deCrypt work key first");
                        strDecryptCbc = AesCipher.decryptCbc(strKlm, ghi());
                        if (TextUtils.isEmpty(strDecryptCbc)) {
                            strDecryptCbc = RandomUtil.getInstance().generateSecureRandomStr(16);
                            lmn(klm(strDecryptCbc));
                            if (fgh()) {
                                bn.lmn();
                            }
                        } else if (fgh()) {
                            lmn(klm(strDecryptCbc));
                            bn.lmn();
                        }
                    }
                    strDecryptCbc = strAesKsDecrypt;
                }
                this.ghi = strDecryptCbc;
            }
        }
        return this.ghi;
    }
}
