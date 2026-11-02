package com.huawei.hms.analytics;

import android.os.Build;
import android.text.TextUtils;

/* loaded from: classes2.dex */
public final class z {
    public static boolean ikl() {
        return "XIAOMI".equalsIgnoreCase(Build.MANUFACTURER);
    }

    public static boolean klm() {
        return !TextUtils.isEmpty(ar.lmn().lmn.lmn);
    }

    public static boolean lmn() {
        String str = Build.MANUFACTURER;
        return "HUAWEI".equalsIgnoreCase(str) || "HONOR".equalsIgnoreCase(str);
    }
}
