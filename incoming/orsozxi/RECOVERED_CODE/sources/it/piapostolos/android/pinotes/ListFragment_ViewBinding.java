package it.piapostolos.android.pinotes;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.nhaarman.listviewanimations.itemmanipulation.DynamicListView;
import com.pnikosis.materialishprogress.ProgressWheel;
import it.piapostolos.android.pinotes.models.views.InterceptorLinearLayout;

/* loaded from: classes3.dex */
public class ListFragment_ViewBinding implements Unbinder {
    private ListFragment target;

    public ListFragment_ViewBinding(ListFragment listFragment, View view) {
        this.target = listFragment;
        listFragment.listRoot = (InterceptorLinearLayout) Utils.findRequiredViewAsType(view, R.id.list_root, "field 'listRoot'", InterceptorLinearLayout.class);
        listFragment.list = (DynamicListView) Utils.findRequiredViewAsType(view, R.id.list, "field 'list'", DynamicListView.class);
        listFragment.searchLayout = Utils.findRequiredView(view, R.id.search_layout, "field 'searchLayout'");
        listFragment.searchQueryView = (TextView) Utils.findRequiredViewAsType(view, R.id.search_query, "field 'searchQueryView'", TextView.class);
        listFragment.searchCancel = (ImageView) Utils.findRequiredViewAsType(view, R.id.search_cancel, "field 'searchCancel'", ImageView.class);
        listFragment.empyListItem = (com.neopixl.pixlui.components.textview.TextView) Utils.findRequiredViewAsType(view, R.id.empty_list, "field 'empyListItem'", com.neopixl.pixlui.components.textview.TextView.class);
        listFragment.expandedImageView = (ImageView) Utils.findRequiredViewAsType(view, R.id.expanded_image, "field 'expandedImageView'", ImageView.class);
        listFragment.fabView = Utils.findRequiredView(view, R.id.fab, "field 'fabView'");
        listFragment.undoBarView = Utils.findRequiredView(view, R.id.undobar, "field 'undoBarView'");
        listFragment.progress_wheel = (ProgressWheel) Utils.findRequiredViewAsType(view, R.id.progress_wheel, "field 'progress_wheel'", ProgressWheel.class);
        listFragment.snackBarPlaceholder = Utils.findRequiredView(view, R.id.snackbar_placeholder, "field 'snackBarPlaceholder'");
    }

    @Override // butterknife.Unbinder
    public void unbind() {
        ListFragment listFragment = this.target;
        if (listFragment == null) {
            throw new IllegalStateException("Bindings already cleared.");
        }
        this.target = null;
        listFragment.listRoot = null;
        listFragment.list = null;
        listFragment.searchLayout = null;
        listFragment.searchQueryView = null;
        listFragment.searchCancel = null;
        listFragment.empyListItem = null;
        listFragment.expandedImageView = null;
        listFragment.fabView = null;
        listFragment.undoBarView = null;
        listFragment.progress_wheel = null;
        listFragment.snackBarPlaceholder = null;
    }
}
