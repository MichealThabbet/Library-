package com.tbruyelle.rxpermissions;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import com.facebook.internal.NativeProtocol;

/* loaded from: classes2.dex */
public class ShadowActivity extends Activity {
    @Override // android.app.Activity
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        if (bundle == null) {
            handleIntent(getIntent());
        }
    }

    @Override // android.app.Activity
    protected void onNewIntent(Intent intent) {
        handleIntent(intent);
    }

    private void handleIntent(Intent intent) {
        requestPermissions(intent.getStringArrayExtra(NativeProtocol.RESULT_ARGS_PERMISSIONS), 42);
    }

    @Override // android.app.Activity
    protected void onDestroy() {
        super.onDestroy();
        RxPermissions.getInstance(this).onDestroy();
    }

    @Override // android.app.Activity
    public void onRequestPermissionsResult(int i, String[] strArr, int[] iArr) {
        RxPermissions.getInstance(this).onRequestPermissionsResult(i, strArr, iArr);
        finish();
    }
}
