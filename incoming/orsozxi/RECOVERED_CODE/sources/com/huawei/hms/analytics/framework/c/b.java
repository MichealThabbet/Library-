package com.huawei.hms.analytics.framework.c;

import com.huawei.hms.analytics.core.crypto.AesCipher;
import com.huawei.hms.analytics.framework.config.CipherType;
import com.huawei.hms.analytics.framework.config.IMandatoryParameters;

/* loaded from: classes2.dex */
public final class b {

    /* renamed from: com.huawei.hms.analytics.framework.c.b$1, reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] a;

        static {
            int[] iArr = new int[CipherType.values().length];
            a = iArr;
            try {
                iArr[CipherType.AESGCM.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                a[CipherType.AESCBC.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
        }
    }

    public static String a(String str) {
        IMandatoryParameters iMandatoryParameters = com.huawei.hms.analytics.framework.b.b.a().a;
        if (iMandatoryParameters == null) {
            return "";
        }
        String loadWorkKey = iMandatoryParameters.getLoadWorkKey();
        int i = AnonymousClass1.a[iMandatoryParameters.getCipherType().ordinal()];
        return i != 1 ? i != 2 ? "" : AesCipher.encryptCbc(str, loadWorkKey) : AesCipher.gcmEncrypt(str, loadWorkKey);
    }

    public static String a(String str, IMandatoryParameters iMandatoryParameters) {
        if (iMandatoryParameters == null) {
            return "";
        }
        int i = AnonymousClass1.a[iMandatoryParameters.getCipherType().ordinal()];
        return i != 1 ? i != 2 ? "" : AesCipher.decryptCbc(str, iMandatoryParameters.getLoadWorkKey()) : AesCipher.gcmDecrypt(str, iMandatoryParameters.getLoadWorkKey());
    }
}
