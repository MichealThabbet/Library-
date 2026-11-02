package it.piapostolos.android.pinotes;

import android.view.View;
import android.view.ViewGroup;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import butterknife.Unbinder;
import butterknife.internal.Utils;

/* loaded from: classes3.dex */
public class NotesMainActivity_ViewBinding implements Unbinder {
    private NotesMainActivity target;

    public NotesMainActivity_ViewBinding(NotesMainActivity notesMainActivity) {
        this(notesMainActivity, notesMainActivity.getWindow().getDecorView());
    }

    public NotesMainActivity_ViewBinding(NotesMainActivity notesMainActivity, View view) {
        this.target = notesMainActivity;
        notesMainActivity.croutonViewContainer = (ViewGroup) Utils.findRequiredViewAsType(view, R.id.crouton_handle, "field 'croutonViewContainer'", ViewGroup.class);
        notesMainActivity.toolbar = (Toolbar) Utils.findRequiredViewAsType(view, R.id.toolbar, "field 'toolbar'", Toolbar.class);
        notesMainActivity.drawerLayout = (DrawerLayout) Utils.findRequiredViewAsType(view, R.id.drawer_layout, "field 'drawerLayout'", DrawerLayout.class);
    }

    @Override // butterknife.Unbinder
    public void unbind() {
        NotesMainActivity notesMainActivity = this.target;
        if (notesMainActivity == null) {
            throw new IllegalStateException("Bindings already cleared.");
        }
        this.target = null;
        notesMainActivity.croutonViewContainer = null;
        notesMainActivity.toolbar = null;
        notesMainActivity.drawerLayout = null;
    }
}
