package com.google.android.gms.tagmanager;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

/* loaded from: classes.dex */
public class PreviewActivity extends Activity {
    @Override // android.app.Activity
    public void onCreate(Bundle bundle) {
        try {
            super.onCreate(bundle);
            zzdi.zzaw("Preview activity");
            Uri data = getIntent().getData();
            if (!TagManager.getInstance(this).zza(data)) {
                String strValueOf = String.valueOf(data);
                StringBuilder sb = new StringBuilder(String.valueOf(strValueOf).length() + 73);
                sb.append("Cannot preview the app with the uri: ");
                sb.append(strValueOf);
                sb.append(". Launching current version instead.");
                String string = sb.toString();
                zzdi.zzac(string);
                AlertDialog alertDialogCreate = new AlertDialog.Builder(this).create();
                alertDialogCreate.setTitle("Preview failure");
                alertDialogCreate.setMessage(string);
                alertDialogCreate.setButton(-1, "Continue", new zzeg(this));
                alertDialogCreate.show();
            }
            Intent launchIntentForPackage = getPackageManager().getLaunchIntentForPackage(getPackageName());
            if (launchIntentForPackage == null) {
                String strValueOf2 = String.valueOf(getPackageName());
                zzdi.zzaw(strValueOf2.length() != 0 ? "No launch activity found for package name: ".concat(strValueOf2) : new String("No launch activity found for package name: "));
            } else {
                String strValueOf3 = String.valueOf(getPackageName());
                zzdi.zzaw(strValueOf3.length() != 0 ? "Invoke the launch activity for package name: ".concat(strValueOf3) : new String("Invoke the launch activity for package name: "));
                startActivity(launchIntentForPackage);
            }
        } catch (Exception e) {
            String strValueOf4 = String.valueOf(e.getMessage());
            zzdi.zzav(strValueOf4.length() != 0 ? "Calling preview threw an exception: ".concat(strValueOf4) : new String("Calling preview threw an exception: "));
        }
    }
}
