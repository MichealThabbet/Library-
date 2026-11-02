package com.huawei.hms.hatool;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/* loaded from: classes2.dex */
public class h {
    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Removed duplicated region for block: B:12:0x0022 A[ORIG_RETURN, RETURN] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public static java.lang.String a(int r0, java.lang.String r1) {
        /*
            switch(r0) {
                case 1: goto L1f;
                case 2: goto L1f;
                case 3: goto L22;
                case 4: goto L1f;
                case 5: goto L22;
                case 6: goto L22;
                case 7: goto L1f;
                case 8: goto L22;
                case 9: goto L22;
                case 10: goto L22;
                case 11: goto L1f;
                case 12: goto L22;
                case 13: goto L1c;
                case 14: goto L22;
                case 15: goto L22;
                default: goto L3;
            }
        L3:
            java.lang.String r0 = "TD-SCDMA"
            boolean r0 = r1.equalsIgnoreCase(r0)
            if (r0 != 0) goto L22
            java.lang.String r0 = "WCDMA"
            boolean r0 = r1.equalsIgnoreCase(r0)
            if (r0 != 0) goto L22
            java.lang.String r0 = "CDMA2000"
            boolean r0 = r1.equalsIgnoreCase(r0)
            if (r0 == 0) goto L24
            goto L22
        L1c:
            java.lang.String r1 = "4G"
            goto L24
        L1f:
            java.lang.String r1 = "2G"
            goto L24
        L22:
            java.lang.String r1 = "3G"
        L24:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.huawei.hms.hatool.h.a(int, java.lang.String):java.lang.String");
    }

    public static String a(Context context) {
        NetworkInfo activeNetworkInfo;
        if (context == null || context.getPackageManager().checkPermission("android.permission.ACCESS_NETWORK_STATE", context.getPackageName()) != 0) {
            y.f("hmsSdk", "not have network state phone permission!");
            return "";
        }
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService("connectivity");
        if (connectivityManager == null || (activeNetworkInfo = connectivityManager.getActiveNetworkInfo()) == null || !activeNetworkInfo.isConnected()) {
            return "";
        }
        if (activeNetworkInfo.getType() == 1) {
            return "WIFI";
        }
        if (activeNetworkInfo.getType() == 0) {
            String subtypeName = activeNetworkInfo.getSubtypeName();
            y.c("hmsSdk", "Network getSubtypeName : " + subtypeName);
            return a(activeNetworkInfo.getSubtype(), subtypeName);
        }
        if (activeNetworkInfo.getType() == 16) {
            y.f("hmsSdk", "type name = COMPANION_PROXY");
            return "COMPANION_PROXY";
        }
        if (activeNetworkInfo.getType() == 9) {
            y.c("hmsSdk", "type name = ETHERNET");
            return "ETHERNET";
        }
        y.c("hmsSdk", "type name = " + activeNetworkInfo.getType());
        return "OTHER_NETWORK_TYPE";
    }
}
