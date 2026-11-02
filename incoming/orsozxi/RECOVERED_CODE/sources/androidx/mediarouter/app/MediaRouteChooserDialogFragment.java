package androidx.mediarouter.app;

import android.app.Dialog;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import androidx.fragment.app.DialogFragment;
import androidx.mediarouter.media.MediaRouteSelector;

/* loaded from: classes.dex */
public class MediaRouteChooserDialogFragment extends DialogFragment {
    private static final String ARGUMENT_SELECTOR = "selector";
    private static final boolean USE_SUPPORT_DYNAMIC_GROUP = Log.isLoggable("UseSupportDynamicGroup", 3);
    private Dialog mDialog;
    private MediaRouteSelector mSelector;

    public MediaRouteChooserDialogFragment() {
        setCancelable(true);
    }

    public MediaRouteSelector getRouteSelector() {
        ensureRouteSelector();
        return this.mSelector;
    }

    private void ensureRouteSelector() {
        if (this.mSelector == null) {
            Bundle arguments = getArguments();
            if (arguments != null) {
                this.mSelector = MediaRouteSelector.fromBundle(arguments.getBundle(ARGUMENT_SELECTOR));
            }
            if (this.mSelector == null) {
                this.mSelector = MediaRouteSelector.EMPTY;
            }
        }
    }

    public void setRouteSelector(MediaRouteSelector mediaRouteSelector) {
        if (mediaRouteSelector == null) {
            throw new IllegalArgumentException("selector must not be null");
        }
        ensureRouteSelector();
        if (this.mSelector.equals(mediaRouteSelector)) {
            return;
        }
        this.mSelector = mediaRouteSelector;
        Bundle arguments = getArguments();
        if (arguments == null) {
            arguments = new Bundle();
        }
        arguments.putBundle(ARGUMENT_SELECTOR, mediaRouteSelector.asBundle());
        setArguments(arguments);
        Dialog dialog = this.mDialog;
        if (dialog != null) {
            if (USE_SUPPORT_DYNAMIC_GROUP) {
                ((MediaRouteDevicePickerDialog) dialog).setRouteSelector(mediaRouteSelector);
            } else {
                ((MediaRouteChooserDialog) dialog).setRouteSelector(mediaRouteSelector);
            }
        }
    }

    public MediaRouteDevicePickerDialog onCreateDevicePickerDialog(Context context) {
        return new MediaRouteDevicePickerDialog(context);
    }

    public MediaRouteChooserDialog onCreateChooserDialog(Context context, Bundle bundle) {
        return new MediaRouteChooserDialog(context);
    }

    @Override // androidx.fragment.app.DialogFragment
    public Dialog onCreateDialog(Bundle bundle) {
        if (USE_SUPPORT_DYNAMIC_GROUP) {
            MediaRouteDevicePickerDialog mediaRouteDevicePickerDialogOnCreateDevicePickerDialog = onCreateDevicePickerDialog(getContext());
            this.mDialog = mediaRouteDevicePickerDialogOnCreateDevicePickerDialog;
            mediaRouteDevicePickerDialogOnCreateDevicePickerDialog.setRouteSelector(getRouteSelector());
        } else {
            MediaRouteChooserDialog mediaRouteChooserDialogOnCreateChooserDialog = onCreateChooserDialog(getContext(), bundle);
            this.mDialog = mediaRouteChooserDialogOnCreateChooserDialog;
            mediaRouteChooserDialogOnCreateChooserDialog.setRouteSelector(getRouteSelector());
        }
        return this.mDialog;
    }

    @Override // androidx.fragment.app.Fragment, android.content.ComponentCallbacks
    public void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        Dialog dialog = this.mDialog;
        if (dialog == null) {
            return;
        }
        if (USE_SUPPORT_DYNAMIC_GROUP) {
            ((MediaRouteDevicePickerDialog) dialog).updateLayout();
        } else {
            ((MediaRouteChooserDialog) dialog).updateLayout();
        }
    }
}
