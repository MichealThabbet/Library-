package it.piapostolos.android.pinotes.async;

import android.view.View;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import it.piapostolos.android.pinotes.R;
import it.piapostolos.android.pinotes.models.views.NonScrollableListView;

/* loaded from: classes3.dex */
public class MainMenuTask_ViewBinding implements Unbinder {
    private MainMenuTask target;

    public MainMenuTask_ViewBinding(MainMenuTask mainMenuTask, View view) {
        this.target = mainMenuTask;
        mainMenuTask.mDrawerList = (NonScrollableListView) Utils.findRequiredViewAsType(view, R.id.drawer_nav_list, "field 'mDrawerList'", NonScrollableListView.class);
        mainMenuTask.mDrawerCategoriesList = (NonScrollableListView) Utils.findRequiredViewAsType(view, R.id.drawer_tag_list, "field 'mDrawerCategoriesList'", NonScrollableListView.class);
    }

    @Override // butterknife.Unbinder
    public void unbind() {
        MainMenuTask mainMenuTask = this.target;
        if (mainMenuTask == null) {
            throw new IllegalStateException("Bindings already cleared.");
        }
        this.target = null;
        mainMenuTask.mDrawerList = null;
        mainMenuTask.mDrawerCategoriesList = null;
    }
}
