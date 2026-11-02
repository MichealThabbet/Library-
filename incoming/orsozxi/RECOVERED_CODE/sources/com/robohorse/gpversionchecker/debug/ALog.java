package com.robohorse.gpversionchecker.debug;

import android.text.TextUtils;
import android.util.Log;
import com.robohorse.gpversionchecker.GPVersionChecker;

/* loaded from: classes2.dex */
public class ALog {
    private static final String TAG = "GPVersionChecker";

    public static void d(String str) {
        if (!GPVersionChecker.useLog || TextUtils.isEmpty(str)) {
            return;
        }
        Log.d(TAG, str);
    }
}
