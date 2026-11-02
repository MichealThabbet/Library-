package it.orsozoxi.android.orsonotes.utils;

import android.app.ProgressDialog;
import android.content.Context;

/* loaded from: classes3.dex */
public class LoadingDialog extends ProgressDialog {
    Context context;

    public LoadingDialog(Context context) {
        super(context);
        this.context = context;
    }

    @Override // android.app.Dialog
    public void onBackPressed() {
        super.onBackPressed();
        cancel();
    }
}
