package com.facebook.appevents.internal;

import android.os.Looper;

/* loaded from: classes.dex */
public class AppEventUtility {
    public static void assertIsMainThread() {
    }

    public static void assertIsNotMainThread() {
    }

    private static boolean isMainThread() {
        return Looper.myLooper() == Looper.getMainLooper();
    }
}
