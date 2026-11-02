package com.huawei.agconnect.applinking.a.a;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.text.TextUtils;
import com.huawei.agconnect.common.api.Logger;
import com.huawei.agconnect.config.impl.Hex;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import org.apache.commons.codec.digest.MessageDigestAlgorithms;

/* loaded from: classes2.dex */
public class d {
    public static String a(Context context, String str) throws PackageManager.NameNotFoundException, NoSuchAlgorithmException {
        PackageManager packageManager;
        String str2;
        if (context == null || TextUtils.isEmpty(str) || (packageManager = context.getPackageManager()) == null) {
            return null;
        }
        try {
            PackageInfo packageInfo = packageManager.getPackageInfo(str, 64);
            if (packageInfo == null) {
                return null;
            }
            byte[] byteArray = packageInfo.signatures[0].toByteArray();
            MessageDigest messageDigest = MessageDigest.getInstance(MessageDigestAlgorithms.SHA_256);
            if (messageDigest != null) {
                return Hex.encodeHexString(messageDigest.digest(byteArray));
            }
            return null;
        } catch (PackageManager.NameNotFoundException unused) {
            str2 = "not found: " + str;
            Logger.e("AppLinkingSDK", str2);
            return null;
        } catch (RuntimeException unused2) {
            str2 = "get packageInfo runtimeException";
            Logger.e("AppLinkingSDK", str2);
            return null;
        } catch (NoSuchAlgorithmException unused3) {
            str2 = "not found sha256 digest";
            Logger.e("AppLinkingSDK", str2);
            return null;
        }
    }

    public static boolean a(ApplicationInfo applicationInfo) {
        return (applicationInfo.flags & 128) != 0;
    }

    /* JADX WARN: Removed duplicated region for block: B:17:0x0037  */
    /* JADX WARN: Removed duplicated region for block: B:26:? A[RETURN, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public static boolean b(android.content.Context r4, java.lang.String r5) throws android.content.pm.PackageManager.NameNotFoundException {
        /*
            java.lang.String r0 = "AppLinkingSDK"
            r1 = 0
            if (r4 == 0) goto L44
            boolean r2 = android.text.TextUtils.isEmpty(r5)
            if (r2 == 0) goto Lc
            goto L44
        Lc:
            android.content.pm.PackageManager r4 = r4.getPackageManager()
            if (r4 != 0) goto L13
            return r1
        L13:
            r2 = 0
            android.content.pm.ApplicationInfo r2 = r4.getApplicationInfo(r5, r1)     // Catch: java.lang.Exception -> L19 android.content.pm.PackageManager.NameNotFoundException -> L21
            goto L35
        L19:
            java.lang.StringBuilder r4 = new java.lang.StringBuilder
            r4.<init>()
            java.lang.String r3 = "getApplicationInfo Exception: "
            goto L28
        L21:
            java.lang.StringBuilder r4 = new java.lang.StringBuilder
            r4.<init>()
            java.lang.String r3 = "not found: "
        L28:
            r4.append(r3)
            r4.append(r5)
            java.lang.String r4 = r4.toString()
            com.huawei.agconnect.common.api.Logger.e(r0, r4)
        L35:
            if (r2 == 0) goto L44
            boolean r4 = a(r2)
            if (r4 != 0) goto L43
            boolean r4 = b(r2)
            if (r4 == 0) goto L44
        L43:
            r1 = 1
        L44:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.huawei.agconnect.applinking.a.a.d.b(android.content.Context, java.lang.String):boolean");
    }

    public static boolean b(ApplicationInfo applicationInfo) {
        return (applicationInfo.flags & 1) != 0;
    }
}
