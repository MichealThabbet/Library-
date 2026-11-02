package com.huawei.agconnect.common.appinfo;

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import com.huawei.agconnect.common.api.Logger;

/* loaded from: classes2.dex */
public class SafeAppInfo {
    private static final String TAG = "SafeAppInfo";

    public static ApplicationInfo safeGetApplicationInfo(PackageManager packageManager, String str, int i) {
        try {
            return packageManager.getApplicationInfo(str, i);
        } catch (PackageManager.NameNotFoundException e) {
            Logger.e(TAG, "not found", e);
            return null;
        } catch (RuntimeException unused) {
            Logger.e(TAG, "get ApplicationInfo Exception: " + str);
            return null;
        }
    }

    public static PackageInfo safeGetPackageInfo(PackageManager packageManager, String str, int i) {
        try {
            return packageManager.getPackageInfo(str, i);
        } catch (PackageManager.NameNotFoundException e) {
            Logger.e(TAG, "not found", e);
            return null;
        } catch (RuntimeException unused) {
            Logger.e(TAG, "get PackageInfo Exception: " + str);
            return null;
        }
    }
}
