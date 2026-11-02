package com.app.piapostolos.Others;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;
import java.util.Locale;

/* loaded from: classes.dex */
public class Util {
    public static final String TAG = "Orso";
    private static final boolean showLog = true;

    public static void showLog(String str) {
        Log.d(TAG, str);
    }

    public static boolean hasConnection(Activity activity) {
        ConnectivityManager connectivityManager = (ConnectivityManager) activity.getApplication().getSystemService("connectivity");
        NetworkInfo networkInfo = connectivityManager.getNetworkInfo(1);
        if (networkInfo != null && networkInfo.isConnected()) {
            return true;
        }
        NetworkInfo networkInfo2 = connectivityManager.getNetworkInfo(0);
        if (networkInfo2 != null && networkInfo2.isConnected()) {
            return true;
        }
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public static boolean isEmptyString(String str) {
        return str == null || str.equals("");
    }

    public static void showToast(Activity activity, int i) {
        Toast.makeText(activity, activity.getString(i), 1).show();
    }

    public static void showToast(Activity activity, String str) {
        Toast.makeText(activity, str, 1).show();
    }

    public static void showToast(Context context, String str) {
        Toast.makeText(context, str, 1).show();
    }

    public static void hideKeyboard(Activity activity) {
        ((InputMethodManager) activity.getSystemService("input_method")).hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 2);
    }

    public static boolean isArabicLocale() {
        return Locale.getDefault().getLanguage().equals("ar");
    }
}
