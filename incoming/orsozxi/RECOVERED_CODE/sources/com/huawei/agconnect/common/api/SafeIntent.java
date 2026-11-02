package com.huawei.agconnect.common.api;

import android.content.Intent;
import android.os.Bundle;
import java.io.Serializable;

/* loaded from: classes2.dex */
public class SafeIntent {
    private static final String TAG = "SafeIntent";

    public static Bundle getBundleExtra(Intent intent, String str) {
        if (intent == null) {
            return null;
        }
        try {
            return intent.getBundleExtra(str);
        } catch (Throwable th) {
            Logger.e(TAG, "intent get bundle extra fail:" + th.getMessage());
            return null;
        }
    }

    public static Bundle getExtras(Intent intent) {
        if (intent == null) {
            return null;
        }
        try {
            return intent.getExtras();
        } catch (Throwable th) {
            Logger.e(TAG, "intent get extras fail:" + th.getMessage());
            return null;
        }
    }

    public static Serializable getSerializableExtra(Intent intent, String str) {
        if (intent == null) {
            return null;
        }
        try {
            return intent.getSerializableExtra(str);
        } catch (Throwable th) {
            Logger.e(TAG, "intent get serializable extra fail:" + th.getMessage());
            return null;
        }
    }

    public static String getString(Bundle bundle, String str) {
        if (bundle == null) {
            return null;
        }
        try {
            return bundle.getString(str);
        } catch (Throwable th) {
            Logger.e(TAG, "bundle get string extra fail:" + th.getMessage());
            return null;
        }
    }

    public static String getStringExtra(Intent intent, String str) {
        if (intent == null) {
            return null;
        }
        try {
            return intent.getStringExtra(str);
        } catch (Throwable th) {
            Logger.e(TAG, "intent get string extra fail:" + th.getMessage());
            return null;
        }
    }
}
