package com.huawei.hms.framework.common;

import android.content.Context;

/* loaded from: classes2.dex */
public class ContextHolder {
    private static final String TAG = "ContextHolder";
    private static Context sAppContext;
    private static Context sKitContext;

    public static Context getAppContext() {
        return sAppContext;
    }

    public static Context getResourceContext() {
        if (getKitContext() != null) {
            return getKitContext();
        }
        return getAppContext();
    }

    public static Context getKitContext() {
        return sKitContext;
    }

    public static void setAppContext(Context context) {
        CheckParamUtils.checkNotNull(context, "sAppContext == null");
        sAppContext = context.getApplicationContext();
    }

    public static void setKitContext(Context context) {
        CheckParamUtils.checkNotNull(context, "sKitContext == null");
        sKitContext = context;
    }
}
