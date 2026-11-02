package androidx.mediarouter.app;

import android.app.Dialog;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import androidx.fragment.app.DialogFragment;
import androidx.mediarouter.media.MediaRouteSelector;

/* loaded from: classes.dex */
public class MediaRouteControllerDialogFragment extends DialogFragment {
    private static final String ARGUMENT_SELECTOR = "selector";
    private static final boolean USE_SUPPORT_DYNAMIC_GROUP = Log.isLoggable("UseSupportDynamicGroup", 3);
    private Dialog mDialog;
    private MediaRouteSelector mSelector;

    public MediaRouteControllerDialogFragment() {
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
        if (dialog == null || !USE_SUPPORT_DYNAMIC_GROUP) {
            return;
        }
        ((MediaRouteCastDialog) dialog).setRouteSelector(mediaRouteSelector);
    }

    public MediaRouteCastDialog onCreateCastDialog(Context context) {
        return new MediaRouteCastDialog(context);
    }

    public MediaRouteControllerDialog onCreateControllerDialog(Context context, Bundle bundle) {
        return new MediaRouteControllerDialog(context);
    }

    @Override // androidx.fragment.app.DialogFragment
    public Dialog onCreateDialog(Bundle bundle) {
        if (USE_SUPPORT_DYNAMIC_GROUP) {
            MediaRouteCastDialog mediaRouteCastDialogOnCreateCastDialog = onCreateCastDialog(getContext());
            this.mDialog = mediaRouteCastDialogOnCreateCastDialog;
            mediaRouteCastDialogOnCreateCastDialog.setRouteSelector(this.mSelector);
        } else {
            this.mDialog = onCreateControllerDialog(getContext(), bundle);
        }
        return this.mDialog;
    }

    @Override // androidx.fragment.app.DialogFragment, androidx.fragment.app.Fragment
    public void onStop() {
        super.onStop();
        Dialog dialog = this.mDialog;
        if (dialog == null || USE_SUPPORT_DYNAMIC_GROUP) {
            return;
        }
        ((MediaRouteControllerDialog) dialog).clearGroupListAnimation(false);
    }

    @Override // androidx.fragment.app.Fragment, android.content.ComponentCallbacks
    public void onConfigurationChanged(Configuration configuration) throws Resources.NotFoundException {
        super.onConfigurationChanged(configuration);
        Dialog dialog = this.mDialog;
        if (dialog != null) {
            if (USE_SUPPORT_DYNAMIC_GROUP) {
                ((MediaRouteCastDialog) dialog).updateLayout();
            } else {
                ((MediaRouteControllerDialog) dialog).updateLayout();
            }
        }
    }
}
