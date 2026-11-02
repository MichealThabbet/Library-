package com.huawei.hms.framework.common.check;

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.ProviderInfo;
import android.net.Uri;
import com.huawei.hms.framework.common.ContextHolder;
import com.huawei.hms.framework.common.Logger;

/* loaded from: classes2.dex */
public class ProviderCheckUtil {
    private static String TAG = "ProviderCheckUtil";

    public static boolean isValid(Uri uri) {
        if (uri == null) {
            return false;
        }
        PackageManager packageManager = ContextHolder.getAppContext().getPackageManager();
        ProviderInfo providerInfoResolveContentProvider = packageManager.resolveContentProvider(uri.getAuthority(), 0);
        if (providerInfoResolveContentProvider != null) {
            ApplicationInfo applicationInfo = providerInfoResolveContentProvider.applicationInfo;
            if (applicationInfo == null) {
                return false;
            }
            String str = applicationInfo.packageName;
            Logger.v(TAG, "Target provider service's package name is : " + str);
            return str != null && packageManager.checkSignatures("com.huawei.hwid", str) == 0;
        }
        Logger.w(TAG, "Invalid param");
        return false;
    }
}
