package com.app.orsozoxi.Others;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Environment;

/* loaded from: classes.dex */
public final class CommonUtilities {
    public static final String DISPLAY_MESSAGE_ACTION = "com.androidhive.pushnotifications.DISPLAY_MESSAGE";
    public static final String EXTRA_MESSAGE = "message";
    public static final String SENDER_ID = "111326151494";
    public static final String SERVER_URL = "https://www.orsozoxi.net/gcmsms/register.php";
    public static final String SERVER_URL_AGPYA_SIGN = "https://www.orsozoxi.net/app_update/dd/agbeya.pdf";
    public static final String SERVER_URL_KHOLAGY_SIGN = "https://www.orsozoxi.net/app_update/dd/kholagy.pdf";
    public static final String TAG = "AndroidHive GCM";

    public static String getStoragePath(Context context) {
        if (Build.VERSION.SDK_INT >= 30) {
            return context.getExternalFilesDir(null).getPath();
        }
        return Environment.getExternalStorageDirectory().getPath();
    }

    public static String getInternalPath(Context context) {
        return context.getFilesDir().getPath();
    }

    public static void displayMessage(Context context, String str) {
        Intent intent = new Intent(DISPLAY_MESSAGE_ACTION);
        intent.putExtra("message", str);
        context.sendBroadcast(intent);
    }
}
