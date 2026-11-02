package it.orsozoxi.android.orsonotes.models.holders;

import android.view.View;
import android.widget.ImageView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.neopixl.pixlui.components.textview.TextView;
import it.orsozoxi.android.orsonotes.R;
import it.orsozoxi.android.orsonotes.models.views.SquareImageView;

/* loaded from: classes3.dex */
public class NoteViewHolder_ViewBinding implements Unbinder {
    private NoteViewHolder target;

    public NoteViewHolder_ViewBinding(NoteViewHolder noteViewHolder, View view) {
        this.target = noteViewHolder;
        noteViewHolder.root = Utils.findRequiredView(view, R.id.root, "field 'root'");
        noteViewHolder.cardLayout = Utils.findRequiredView(view, R.id.card_layout, "field 'cardLayout'");
        noteViewHolder.categoryMarker = Utils.findRequiredView(view, R.id.category_marker, "field 'categoryMarker'");
        noteViewHolder.title = (TextView) Utils.findRequiredViewAsType(view, R.id.note_title, "field 'title'", TextView.class);
        noteViewHolder.content = (TextView) Utils.findRequiredViewAsType(view, R.id.note_content, "field 'content'", TextView.class);
        noteViewHolder.date = (TextView) Utils.findRequiredViewAsType(view, R.id.note_date, "field 'date'", TextView.class);
        noteViewHolder.archiveIcon = (ImageView) Utils.findRequiredViewAsType(view, R.id.archivedIcon, "field 'archiveIcon'", ImageView.class);
        noteViewHolder.locationIcon = (ImageView) Utils.findRequiredViewAsType(view, R.id.locationIcon, "field 'locationIcon'", ImageView.class);
        noteViewHolder.alarmIcon = (ImageView) Utils.findRequiredViewAsType(view, R.id.alarmIcon, "field 'alarmIcon'", ImageView.class);
        noteViewHolder.lockedIcon = (ImageView) Utils.findRequiredViewAsType(view, R.id.lockedIcon, "field 'lockedIcon'", ImageView.class);
        noteViewHolder.attachmentIcon = (ImageView) Utils.findOptionalViewAsType(view, R.id.attachmentIcon, "field 'attachmentIcon'", ImageView.class);
        noteViewHolder.attachmentThumbnail = (SquareImageView) Utils.findOptionalViewAsType(view, R.id.attachmentThumbnail, "field 'attachmentThumbnail'", SquareImageView.class);
    }

    @Override // butterknife.Unbinder
    public void unbind() {
        NoteViewHolder noteViewHolder = this.target;
        if (noteViewHolder == null) {
            throw new IllegalStateException("Bindings already cleared.");
        }
        this.target = null;
        noteViewHolder.root = null;
        noteViewHolder.cardLayout = null;
        noteViewHolder.categoryMarker = null;
        noteViewHolder.title = null;
        noteViewHolder.content = null;
        noteViewHolder.date = null;
        noteViewHolder.archiveIcon = null;
        noteViewHolder.locationIcon = null;
        noteViewHolder.alarmIcon = null;
        noteViewHolder.lockedIcon = null;
        noteViewHolder.attachmentIcon = null;
        noteViewHolder.attachmentThumbnail = null;
    }
}
