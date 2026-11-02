package it.gmariotti.changelibs.library.internal;

import android.content.Context;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import it.gmariotti.changelibs.R;
import it.gmariotti.changelibs.library.Constants;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/* loaded from: classes2.dex */
public class ChangeLogRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int TYPE_HEADER = 1;
    private static final int TYPE_ROW = 0;
    private List<ChangeLogRow> items;
    private final Context mContext;
    private int mRowLayoutId = Constants.mRowLayoutId;
    private int mRowHeaderLayoutId = Constants.mRowHeaderLayoutId;
    private int mStringVersionHeader = Constants.mStringVersionHeader;

    public ChangeLogRecyclerViewAdapter(Context context, List<ChangeLogRow> list) {
        this.mContext = context;
        this.items = list == null ? new ArrayList<>() : list;
    }

    public void add(LinkedList<ChangeLogRow> linkedList) {
        int size = this.items.size();
        this.items.addAll(linkedList);
        notifyItemRangeInserted(size, linkedList.size() + size);
    }

    public static class ViewHolderHeader extends RecyclerView.ViewHolder {
        public TextView dateHeader;
        public TextView versionHeader;

        public ViewHolderHeader(View view) {
            super(view);
            this.versionHeader = (TextView) view.findViewById(R.id.chg_headerVersion);
            this.dateHeader = (TextView) view.findViewById(R.id.chg_headerDate);
        }
    }

    public static class ViewHolderRow extends RecyclerView.ViewHolder {
        public TextView bulletRow;
        public TextView textRow;

        public ViewHolderRow(View view) {
            super(view);
            this.textRow = (TextView) view.findViewById(R.id.chg_text);
            this.bulletRow = (TextView) view.findViewById(R.id.chg_textbullet);
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        if (i == 1) {
            return new ViewHolderHeader(LayoutInflater.from(viewGroup.getContext()).inflate(this.mRowHeaderLayoutId, viewGroup, false));
        }
        return new ViewHolderRow(LayoutInflater.from(viewGroup.getContext()).inflate(this.mRowLayoutId, viewGroup, false));
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i) {
        if (isHeader(i)) {
            populateViewHolderHeader((ViewHolderHeader) viewHolder, i);
        } else {
            populateViewHolderRow((ViewHolderRow) viewHolder, i);
        }
    }

    private void populateViewHolderRow(ViewHolderRow viewHolderRow, int i) {
        ChangeLogRow item = getItem(i);
        if (item != null) {
            if (viewHolderRow.textRow != null) {
                viewHolderRow.textRow.setText(Html.fromHtml(item.getChangeText(this.mContext)));
                viewHolderRow.textRow.setMovementMethod(LinkMovementMethod.getInstance());
            }
            if (viewHolderRow.bulletRow != null) {
                if (item.isBulletedList()) {
                    viewHolderRow.bulletRow.setVisibility(0);
                } else {
                    viewHolderRow.bulletRow.setVisibility(8);
                }
            }
        }
    }

    private void populateViewHolderHeader(ViewHolderHeader viewHolderHeader, int i) {
        ChangeLogRow item = getItem(i);
        if (item != null) {
            if (viewHolderHeader.versionHeader != null) {
                StringBuilder sb = new StringBuilder();
                String string = this.mContext.getString(this.mStringVersionHeader);
                if (string != null) {
                    sb.append(string);
                }
                sb.append(item.versionName);
                viewHolderHeader.versionHeader.setText(sb.toString());
            }
            if (viewHolderHeader.dateHeader != null) {
                if (item.changeDate != null) {
                    viewHolderHeader.dateHeader.setText(item.changeDate);
                    viewHolderHeader.dateHeader.setVisibility(0);
                } else {
                    viewHolderHeader.dateHeader.setText("");
                    viewHolderHeader.dateHeader.setVisibility(8);
                }
            }
        }
    }

    private boolean isHeader(int i) {
        return getItem(i).isHeader();
    }

    private ChangeLogRow getItem(int i) {
        return this.items.get(i);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemViewType(int i) {
        return isHeader(i) ? 1 : 0;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemCount() {
        return this.items.size();
    }

    public void setRowLayoutId(int i) {
        this.mRowLayoutId = i;
    }

    public void setRowHeaderLayoutId(int i) {
        this.mRowHeaderLayoutId = i;
    }
}
