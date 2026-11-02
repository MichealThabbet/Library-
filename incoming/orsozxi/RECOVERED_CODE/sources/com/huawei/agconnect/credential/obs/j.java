package com.huawei.agconnect.credential.obs;

import android.util.Log;

/* loaded from: classes2.dex */
public class j {
    private static boolean a(int i) {
        return Log.isLoggable("AGC_LOG", i);
    }

    public void a(String str, String str2) {
        if (a(2)) {
            Log.d("[AGC_LOG]" + str, str2);
        }
    }

    public void a(String str, String str2, Throwable th) {
        if (a(6)) {
            Log.e("[AGC_LOG]" + str, str2, th);
        }
    }

    public void b(String str, String str2) {
        if (a(3)) {
            Log.d("[AGC_LOG]" + str, str2);
        }
    }

    public void c(String str, String str2) {
        if (a(4)) {
            Log.i("[AGC_LOG]" + str, str2);
        }
    }

    public void d(String str, String str2) {
        if (a(5)) {
            Log.w("[AGC_LOG]" + str, str2);
        }
    }
}
