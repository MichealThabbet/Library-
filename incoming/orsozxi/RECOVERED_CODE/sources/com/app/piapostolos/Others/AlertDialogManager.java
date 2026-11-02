package com.app.piapostolos.Others;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import com.app.piapostolos.R;

/* loaded from: classes.dex */
public class AlertDialogManager {
    public void showAlertDialog(Context context, String str, String str2, Boolean bool) {
        AlertDialog alertDialogCreate = new AlertDialog.Builder(context).create();
        alertDialogCreate.setTitle(str);
        alertDialogCreate.setMessage(str2);
        if (bool != null) {
            alertDialogCreate.setIcon(bool.booleanValue() ? R.drawable.success : R.drawable.fail);
        }
        alertDialogCreate.setButton("OK", new DialogInterface.OnClickListener() { // from class: com.app.piapostolos.Others.AlertDialogManager.1
            @Override // android.content.DialogInterface.OnClickListener
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        });
        alertDialogCreate.show();
    }
}
