package it.orsozoxi.android.orsonotes;

import android.view.View;
import android.view.ViewGroup;
import androidx.appcompat.widget.Toolbar;
import butterknife.Unbinder;
import butterknife.internal.Utils;

/* loaded from: classes3.dex */
public class SettingsActivity_ViewBinding implements Unbinder {
    private SettingsActivity target;

    public SettingsActivity_ViewBinding(SettingsActivity settingsActivity) {
        this(settingsActivity, settingsActivity.getWindow().getDecorView());
    }

    public SettingsActivity_ViewBinding(SettingsActivity settingsActivity, View view) {
        this.target = settingsActivity;
        settingsActivity.toolbar = (Toolbar) Utils.findRequiredViewAsType(view, R.id.toolbar, "field 'toolbar'", Toolbar.class);
        settingsActivity.croutonViewContainer = (ViewGroup) Utils.findRequiredViewAsType(view, R.id.crouton_handle, "field 'croutonViewContainer'", ViewGroup.class);
    }

    @Override // butterknife.Unbinder
    public void unbind() {
        SettingsActivity settingsActivity = this.target;
        if (settingsActivity == null) {
            throw new IllegalStateException("Bindings already cleared.");
        }
        this.target = null;
        settingsActivity.toolbar = null;
        settingsActivity.croutonViewContainer = null;
    }
}
