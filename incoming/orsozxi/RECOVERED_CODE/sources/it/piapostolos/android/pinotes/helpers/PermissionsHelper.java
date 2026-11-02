package it.piapostolos.android.pinotes.helpers;

import android.app.Activity;
import android.view.View;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import com.google.android.material.snackbar.Snackbar;
import com.tbruyelle.rxpermissions.RxPermissions;
import it.piapostolos.android.pinotes.R;
import it.piapostolos.android.pinotes.models.listeners.OnPermissionRequestedListener;
import rx.functions.Action1;

/* loaded from: classes3.dex */
public class PermissionsHelper {
    private PermissionsHelper() {
    }

    public static void requestPermission(final Activity activity, final String str, int i, final View view, final OnPermissionRequestedListener onPermissionRequestedListener) {
        if (ContextCompat.checkSelfPermission(activity, str) == 0) {
            if (onPermissionRequestedListener != null) {
                onPermissionRequestedListener.onPermissionGranted();
            }
        } else if (ActivityCompat.shouldShowRequestPermissionRationale(activity, str)) {
            Snackbar.make(view, i, -2).setAction(R.string.ok, new View.OnClickListener() { // from class: it.piapostolos.android.pinotes.helpers.-$$Lambda$PermissionsHelper$MCQKS_gmok5D17HhZJjIDbzG5Tc
                @Override // android.view.View.OnClickListener
                public final void onClick(View view2) {
                    PermissionsHelper.requestPermissionExecute(activity, str, onPermissionRequestedListener, view);
                }
            }).show();
        } else {
            requestPermissionExecute(activity, str, onPermissionRequestedListener, view);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void requestPermissionExecute(final Activity activity, final String str, final OnPermissionRequestedListener onPermissionRequestedListener, final View view) {
        RxPermissions.getInstance(activity).request(str).subscribe(new Action1() { // from class: it.piapostolos.android.pinotes.helpers.-$$Lambda$PermissionsHelper$eVku5BRp3EHKNaVBElXMCm4kjDU
            @Override // rx.functions.Action1
            public final void call(Object obj) {
                PermissionsHelper.lambda$requestPermissionExecute$1(onPermissionRequestedListener, activity, str, view, (Boolean) obj);
            }
        });
    }

    static /* synthetic */ void lambda$requestPermissionExecute$1(OnPermissionRequestedListener onPermissionRequestedListener, Activity activity, String str, View view, Boolean bool) {
        if (bool.booleanValue() && onPermissionRequestedListener != null) {
            onPermissionRequestedListener.onPermissionGranted();
            return;
        }
        Snackbar.make(view, activity.getString(R.string.permission_not_granted) + ": " + str, 0).show();
    }
}
