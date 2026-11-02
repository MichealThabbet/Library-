package it.piapostolos.android.pinotes.models.holders;

import android.view.View;
import android.widget.ImageView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.neopixl.pixlui.components.textview.TextView;
import it.piapostolos.android.pinotes.R2;
import it.piapostolos.android.pinotes.models.views.SquareImageView;

/* loaded from: classes3.dex */
public class NoteViewHolder {

    @BindView(R2.id.alarmIcon)
    public ImageView alarmIcon;

    @BindView(R2.id.archivedIcon)
    public ImageView archiveIcon;

    @BindView(R2.id.attachmentIcon)
    public ImageView attachmentIcon;

    @BindView(R2.id.attachmentThumbnail)
    public SquareImageView attachmentThumbnail;

    @BindView(R2.id.card_layout)
    public View cardLayout;

    @BindView(R2.id.category_marker)
    public View categoryMarker;

    @BindView(R2.id.note_content)
    public TextView content;

    @BindView(R2.id.note_date)
    public TextView date;

    @BindView(R2.id.locationIcon)
    public ImageView locationIcon;

    @BindView(R2.id.lockedIcon)
    public ImageView lockedIcon;

    @BindView(R2.id.root)
    public View root;

    @BindView(R2.id.note_title)
    public TextView title;

    public NoteViewHolder(View view) throws NoSuchMethodException, SecurityException {
        ButterKnife.bind(this, view);
    }
}
