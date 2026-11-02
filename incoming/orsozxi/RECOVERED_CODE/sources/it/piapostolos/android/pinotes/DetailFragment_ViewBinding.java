package it.piapostolos.android.pinotes;

import android.view.View;
import android.view.ViewGroup;
import android.view.ViewManager;
import android.view.ViewStub;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.neopixl.pixlui.components.edittext.EditText;
import com.neopixl.pixlui.components.textview.TextView;
import it.piapostolos.android.pinotes.models.views.ExpandableHeightGridView;

/* loaded from: classes3.dex */
public class DetailFragment_ViewBinding implements Unbinder {
    private DetailFragment target;

    public DetailFragment_ViewBinding(DetailFragment detailFragment, View view) {
        this.target = detailFragment;
        detailFragment.root = (ViewGroup) Utils.findRequiredViewAsType(view, R.id.detail_root, "field 'root'", ViewGroup.class);
        detailFragment.title = (EditText) Utils.findRequiredViewAsType(view, R.id.detail_title, "field 'title'", EditText.class);
        detailFragment.content = (EditText) Utils.findRequiredViewAsType(view, R.id.detail_content, "field 'content'", EditText.class);
        detailFragment.attachmentsAbove = (ViewStub) Utils.findRequiredViewAsType(view, R.id.detail_attachments_above, "field 'attachmentsAbove'", ViewStub.class);
        detailFragment.attachmentsBelow = (ViewStub) Utils.findRequiredViewAsType(view, R.id.detail_attachments_below, "field 'attachmentsBelow'", ViewStub.class);
        detailFragment.mGridView = (ExpandableHeightGridView) Utils.findOptionalViewAsType(view, R.id.gridview, "field 'mGridView'", ExpandableHeightGridView.class);
        detailFragment.locationTextView = (TextView) Utils.findRequiredViewAsType(view, R.id.location, "field 'locationTextView'", TextView.class);
        detailFragment.timestampsView = Utils.findRequiredView(view, R.id.detail_timestamps, "field 'timestampsView'");
        detailFragment.reminderLayout = (LinearLayout) Utils.findRequiredViewAsType(view, R.id.reminder_layout, "field 'reminderLayout'", LinearLayout.class);
        detailFragment.reminderIcon = (ImageView) Utils.findRequiredViewAsType(view, R.id.reminder_icon, "field 'reminderIcon'", ImageView.class);
        detailFragment.datetime = (TextView) Utils.findRequiredViewAsType(view, R.id.datetime, "field 'datetime'", TextView.class);
        detailFragment.titleCardView = Utils.findRequiredView(view, R.id.detail_tile_card, "field 'titleCardView'");
        detailFragment.scrollView = (ScrollView) Utils.findRequiredViewAsType(view, R.id.content_wrapper, "field 'scrollView'", ScrollView.class);
        detailFragment.creationTextView = (TextView) Utils.findRequiredViewAsType(view, R.id.creation, "field 'creationTextView'", TextView.class);
        detailFragment.lastModificationTextView = (TextView) Utils.findRequiredViewAsType(view, R.id.last_modification, "field 'lastModificationTextView'", TextView.class);
        detailFragment.titleWrapperView = Utils.findRequiredView(view, R.id.title_wrapper, "field 'titleWrapperView'");
        detailFragment.tagMarkerView = Utils.findRequiredView(view, R.id.tag_marker, "field 'tagMarkerView'");
        detailFragment.detailWrapperView = (ViewManager) Utils.findRequiredViewAsType(view, R.id.detail_wrapper, "field 'detailWrapperView'", ViewManager.class);
        detailFragment.snackBarPlaceholder = Utils.findRequiredView(view, R.id.snackbar_placeholder, "field 'snackBarPlaceholder'");
    }

    @Override // butterknife.Unbinder
    public void unbind() {
        DetailFragment detailFragment = this.target;
        if (detailFragment == null) {
            throw new IllegalStateException("Bindings already cleared.");
        }
        this.target = null;
        detailFragment.root = null;
        detailFragment.title = null;
        detailFragment.content = null;
        detailFragment.attachmentsAbove = null;
        detailFragment.attachmentsBelow = null;
        detailFragment.mGridView = null;
        detailFragment.locationTextView = null;
        detailFragment.timestampsView = null;
        detailFragment.reminderLayout = null;
        detailFragment.reminderIcon = null;
        detailFragment.datetime = null;
        detailFragment.titleCardView = null;
        detailFragment.scrollView = null;
        detailFragment.creationTextView = null;
        detailFragment.lastModificationTextView = null;
        detailFragment.titleWrapperView = null;
        detailFragment.tagMarkerView = null;
        detailFragment.detailWrapperView = null;
        detailFragment.snackBarPlaceholder = null;
    }
}
