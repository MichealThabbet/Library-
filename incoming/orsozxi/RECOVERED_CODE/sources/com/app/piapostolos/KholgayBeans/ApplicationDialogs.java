package com.app.piapostolos.KholgayBeans;

import android.R;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

/* loaded from: classes.dex */
public class ApplicationDialogs {
    public static final String NO_CONNECTION = "NO_CONNECTION";

    public static void alertDialog(final Activity activity, String str, String str2, String str3, final boolean z) {
        AlertDialog alertDialogCreate = new AlertDialog.Builder(activity).create();
        alertDialogCreate.setTitle(str2);
        alertDialogCreate.setMessage(str);
        alertDialogCreate.setButton(str3, new DialogInterface.OnClickListener() { // from class: com.app.piapostolos.KholgayBeans.ApplicationDialogs.1
            @Override // android.content.DialogInterface.OnClickListener
            public void onClick(DialogInterface dialogInterface, int i) {
                if (z) {
                    activity.finish();
                }
            }
        });
        alertDialogCreate.show();
    }

    public static boolean isConnectionOn(Activity activity) {
        return ((ConnectivityManager) activity.getSystemService("connectivity")).getActiveNetworkInfo() != null;
    }

    public static void hideKeyboard(Activity activity, EditText editText) {
        ((InputMethodManager) activity.getSystemService("input_method")).hideSoftInputFromWindow(editText.getWindowToken(), 0);
    }

    public static void showKeyboard(Activity activity, EditText editText) {
        ((InputMethodManager) activity.getSystemService("input_method")).showSoftInputFromInputMethod(editText.getWindowToken(), 0);
    }

    public static void showToast(final Activity activity, final String str) {
        try {
            activity.runOnUiThread(new Runnable() { // from class: com.app.piapostolos.KholgayBeans.ApplicationDialogs.2
                @Override // java.lang.Runnable
                public void run() {
                    Toast.makeText(activity, str, 1).show();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static ProgressDialog showLoadingDialog(Activity activity, String str) {
        ProgressDialog progressDialog = new ProgressDialog(activity) { // from class: com.app.piapostolos.KholgayBeans.ApplicationDialogs.3
            @Override // android.app.ProgressDialog, android.app.AlertDialog, android.app.Dialog
            protected void onCreate(Bundle bundle) {
                super.onCreate(bundle);
                findViewById(R.id.message);
            }
        };
        progressDialog.setCancelable(false);
        progressDialog.setMessage(str);
        if (!progressDialog.isShowing()) {
            progressDialog.show();
        }
        return progressDialog;
    }

    public static ProgressDialog showLoadingDialog(Activity activity, String str, boolean z) {
        ProgressDialog progressDialog = new ProgressDialog(activity);
        progressDialog.setCancelable(false);
        progressDialog.setMessage(str);
        if (z) {
            progressDialog.setProgressStyle(1);
        }
        if (!progressDialog.isShowing()) {
            try {
                progressDialog.show();
            } catch (Exception unused) {
            }
        }
        return progressDialog;
    }

    public static void dismissLoadingDialog(ProgressDialog progressDialog) {
        if (progressDialog == null || !progressDialog.isShowing()) {
            return;
        }
        progressDialog.dismiss();
    }

    public static boolean isGPSAvailable(Activity activity) {
        return ((LocationManager) activity.getSystemService("location")).isProviderEnabled("gps");
    }
}
