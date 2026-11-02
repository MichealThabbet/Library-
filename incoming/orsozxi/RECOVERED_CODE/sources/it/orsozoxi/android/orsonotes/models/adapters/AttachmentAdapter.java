package it.orsozoxi.android.orsonotes.models.adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import it.orsozoxi.android.orsonotes.R;
import it.orsozoxi.android.orsonotes.helpers.LogDelegate;
import it.orsozoxi.android.orsonotes.helpers.date.DateHelper;
import it.orsozoxi.android.orsonotes.models.Attachment;
import it.orsozoxi.android.orsonotes.models.views.ExpandableHeightGridView;
import it.orsozoxi.android.orsonotes.models.views.SquareImageView;
import it.orsozoxi.android.orsonotes.utils.BitmapHelper;
import it.orsozoxi.android.orsonotes.utils.ConstantsBase;
import it.orsozoxi.android.orsonotes.utils.date.DateUtils;
import java.util.Collections;
import java.util.List;

/* loaded from: classes3.dex */
public class AttachmentAdapter extends BaseAdapter {
    private List<Attachment> attachmentsList;
    private LayoutInflater inflater;
    private Activity mActivity;

    @Override // android.widget.Adapter
    public long getItemId(int i) {
        return 0L;
    }

    public AttachmentAdapter(Activity activity, List<Attachment> list, ExpandableHeightGridView expandableHeightGridView) {
        this.mActivity = activity;
        this.attachmentsList = list == null ? Collections.emptyList() : list;
        this.inflater = (LayoutInflater) activity.getSystemService("layout_inflater");
    }

    @Override // android.widget.Adapter
    public int getCount() {
        return this.attachmentsList.size();
    }

    @Override // android.widget.Adapter
    public Attachment getItem(int i) {
        return this.attachmentsList.get(i);
    }

    @Override // android.widget.Adapter
    public View getView(int i, View view, ViewGroup viewGroup) {
        AttachmentHolder attachmentHolder;
        String localizedDateTime;
        LogDelegate.v("GridView called for position " + i);
        Attachment attachment = this.attachmentsList.get(i);
        if (view == null) {
            view = this.inflater.inflate(R.layout.gridview_item, viewGroup, false);
            attachmentHolder = new AttachmentHolder();
            attachmentHolder.image = (SquareImageView) view.findViewById(R.id.gridview_item_picture);
            attachmentHolder.text = (TextView) view.findViewById(R.id.gridview_item_text);
            view.setTag(attachmentHolder);
        } else {
            attachmentHolder = (AttachmentHolder) view.getTag();
        }
        if (attachment.getMime_type() != null && attachment.getMime_type().equals(ConstantsBase.MIME_TYPE_AUDIO)) {
            if (attachment.getLength() > 0) {
                localizedDateTime = DateHelper.formatShortTime(this.mActivity, attachment.getLength());
            } else {
                localizedDateTime = DateUtils.getLocalizedDateTime(this.mActivity, attachment.getUri().getLastPathSegment().split("\\.")[0], ConstantsBase.DATE_FORMAT_SORTABLE);
            }
            if (localizedDateTime == null) {
                localizedDateTime = this.mActivity.getString(R.string.attachment);
            }
            attachmentHolder.text.setText(localizedDateTime);
            attachmentHolder.text.setVisibility(0);
        } else {
            attachmentHolder.text.setVisibility(8);
        }
        if (attachment.getMime_type() != null && attachment.getMime_type().equals(ConstantsBase.MIME_TYPE_FILES)) {
            attachmentHolder.text.setText(attachment.getName());
            attachmentHolder.text.setVisibility(0);
        }
        Glide.with(this.mActivity.getApplicationContext()).load(BitmapHelper.getThumbnailUri(this.mActivity, attachment)).into(attachmentHolder.image);
        return view;
    }

    public List<Attachment> getAttachmentsList() {
        return this.attachmentsList;
    }

    public class AttachmentHolder {
        SquareImageView image;
        TextView text;

        public AttachmentHolder() {
        }
    }
}
