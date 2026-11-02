package com.huawei.hms.analytics;

import android.app.AppOpsManager;
import android.content.Context;
import android.os.Build;

/* loaded from: classes2.dex */
public final class dk {
    public static int lmn(Context context, String str, int i, int i2) {
        if (context == null || context.checkPermission(str, i, i2) == -1) {
            return -1;
        }
        String strLmn = lmn(str);
        if (strLmn == null) {
            return 0;
        }
        String packageName = context.getPackageName();
        if (packageName == null) {
            String[] packagesForUid = context.getPackageManager().getPackagesForUid(i2);
            if (packagesForUid == null || packagesForUid.length <= 0) {
                return -1;
            }
            packageName = packagesForUid[0];
        }
        return lmn(context, strLmn, packageName) != 0 ? -2 : 0;
    }

    private static int lmn(Context context, String str, String str2) {
        if (Build.VERSION.SDK_INT < 23) {
            return 0;
        }
        AppOpsManager appOpsManager = (AppOpsManager) context.getSystemService(AppOpsManager.class);
        if (appOpsManager == null) {
            return -2;
        }
        return appOpsManager.noteProxyOpNoThrow(str, str2);
    }

    private static String lmn(String str) {
        if (Build.VERSION.SDK_INT >= 23) {
            return AppOpsManager.permissionToOp(str);
        }
        return null;
    }
}
