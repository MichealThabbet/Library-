package com.robohorse.gpversionchecker.manager;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.robohorse.gpversionchecker.R;
import com.robohorse.gpversionchecker.domain.Version;

/* loaded from: classes2.dex */
public class UIManager {
    public void showInfoView(final Activity activity, final Version version) {
        activity.runOnUiThread(new Runnable() { // from class: com.robohorse.gpversionchecker.manager.UIManager.1
            @Override // java.lang.Runnable
            public void run() {
                UIManager.this.showDialogOnUIThread(activity, version);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showDialogOnUIThread(final Context context, Version version) {
        View viewInflate = ((LayoutInflater) context.getSystemService("layout_inflater")).inflate(R.layout.gpvch_layout_dialog, (ViewGroup) null);
        bindVersionData(viewInflate, version, context);
        new AlertDialog.Builder(context).setTitle(R.string.gpvch_header).setView(viewInflate).setPositiveButton(R.string.gpvch_button_positive, new DialogInterface.OnClickListener() { // from class: com.robohorse.gpversionchecker.manager.UIManager.2
            @Override // android.content.DialogInterface.OnClickListener
            public void onClick(DialogInterface dialogInterface, int i) {
                UIManager.this.openGooglePlay(context);
            }
        }).setNegativeButton(R.string.gpvch_button_negative, (DialogInterface.OnClickListener) null).create().show();
    }

    private void bindVersionData(View view, Version version, Context context) {
        ((TextView) view.findViewById(R.id.tvVersionCode)).setText(context.getString(R.string.app_name) + ": " + version.getNewVersionCode());
        TextView textView = (TextView) view.findViewById(R.id.tvChanges);
        String changes = version.getChanges();
        if (!TextUtils.isEmpty(changes)) {
            textView.setText(changes);
        } else {
            view.findViewById(R.id.lnChangesInfo).setVisibility(8);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void openGooglePlay(Context context) {
        context.startActivity(new Intent("android.intent.action.VIEW", Uri.parse(context.getString(R.string.gpvch_google_play_url) + context.getApplicationContext().getPackageName())));
    }
}
