package com.huawei.agconnect.credential.obs;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.text.TextUtils;
import com.huawei.agconnect.common.api.RandomWrapper;
import com.huawei.agconnect.common.appinfo.SafeAppInfo;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;
import org.apache.commons.codec.digest.MessageDigestAlgorithms;

/* loaded from: classes2.dex */
public class c {
    private static String a(int i) {
        return f.a(RandomWrapper.generateSecureRandom(i));
    }

    static String a(Context context) {
        PackageInfo packageInfoSafeGetPackageInfo;
        if (context == null || (packageInfoSafeGetPackageInfo = SafeAppInfo.safeGetPackageInfo(context.getPackageManager(), context.getPackageName(), 64)) == null) {
            return null;
        }
        return packageInfoSafeGetPackageInfo.signatures[0].toCharsString();
    }

    public static synchronized String a(e eVar) {
        String strA;
        if (eVar.c("aaid")) {
            strA = eVar.a("aaid");
        } else {
            String string = UUID.randomUUID().toString();
            eVar.a("aaid", string);
            eVar.a("creationTime", Long.valueOf(System.currentTimeMillis()));
            strA = string;
        }
        return strA;
    }

    static synchronized String a(String str) {
        return b(str + a(32));
    }

    private static String b(String str) throws NoSuchAlgorithmException {
        String strA = f.a(str, MessageDigestAlgorithms.SHA_256);
        return ((TextUtils.isEmpty(strA) || strA.length() != 64) ? UUID.randomUUID() : c(strA)).toString();
    }

    private static UUID c(String str) {
        return new UUID(d(str.substring(0, 32)), d(str.substring(32, 64)));
    }

    private static long d(String str) {
        return (((((Long.decode("0x" + str.substring(0, 8)).longValue() << 16) | Long.decode("0x" + str.substring(8, 16)).longValue()) << 16) | Long.decode("0x" + str.substring(16, 24)).longValue()) << 16) | Long.decode("0x" + str.substring(24, 32)).longValue();
    }
}
