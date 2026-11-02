package it.gmariotti.changelibs.library.internal;

import android.content.Context;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import it.gmariotti.changelibs.R;
import it.gmariotti.changelibs.library.Constants;
import java.util.List;

/* loaded from: classes2.dex */
public class ChangeLogAdapter extends ArrayAdapter<ChangeLogRow> {
    protected static final int TYPE_HEADER = 1;
    protected static final int TYPE_ROW = 0;
    private final Context mContext;
    private int mRowHeaderLayoutId;
    private int mRowLayoutId;
    private int mStringVersionHeader;

    @Override // android.widget.BaseAdapter, android.widget.Adapter
    public int getViewTypeCount() {
        return 2;
    }

    @Override // android.widget.BaseAdapter, android.widget.ListAdapter
    public boolean isEnabled(int i) {
        return false;
    }

    public ChangeLogAdapter(Context context, List<ChangeLogRow> list) {
        super(context, 0, list);
        this.mRowLayoutId = Constants.mRowLayoutId;
        this.mRowHeaderLayoutId = Constants.mRowHeaderLayoutId;
        this.mStringVersionHeader = Constants.mStringVersionHeader;
        this.mContext = context;
    }

    @Override // android.widget.ArrayAdapter, android.widget.Adapter
    public View getView(int i, View view, ViewGroup viewGroup) {
        ChangeLogRow item = getItem(i);
        int itemViewType = getItemViewType(i);
        LayoutInflater layoutInflater = (LayoutInflater) this.mContext.getSystemService("layout_inflater");
        ViewHolderRow viewHolderRow = null;
        viewHolderHeader = null;
        ViewHolderHeader viewHolderHeader = null;
        viewHolderRow = null;
        if (itemViewType == 0) {
            if (view != null) {
                Object tag = view.getTag();
                if (tag instanceof ViewHolderRow) {
                    viewHolderRow = (ViewHolderRow) tag;
                }
            }
            if (view == null || viewHolderRow == null) {
                view = layoutInflater.inflate(this.mRowLayoutId, viewGroup, false);
                viewHolderRow = new ViewHolderRow((TextView) view.findViewById(R.id.chg_text), (TextView) view.findViewById(R.id.chg_textbullet));
                view.setTag(viewHolderRow);
            }
            if (item != null && viewHolderRow != null) {
                if (viewHolderRow.text != null) {
                    viewHolderRow.text.setText(Html.fromHtml(item.getChangeText(this.mContext)));
                    viewHolderRow.text.setMovementMethod(LinkMovementMethod.getInstance());
                }
                if (viewHolderRow.bulletText != null) {
                    if (item.isBulletedList()) {
                        viewHolderRow.bulletText.setVisibility(0);
                    } else {
                        viewHolderRow.bulletText.setVisibility(8);
                    }
                }
            }
        } else if (itemViewType == 1) {
            if (view != null) {
                Object tag2 = view.getTag();
                if (tag2 instanceof ViewHolderHeader) {
                    viewHolderHeader = (ViewHolderHeader) tag2;
                }
            }
            if (view == null || viewHolderHeader == null) {
                view = layoutInflater.inflate(this.mRowHeaderLayoutId, viewGroup, false);
                viewHolderHeader = new ViewHolderHeader((TextView) view.findViewById(R.id.chg_headerVersion), (TextView) view.findViewById(R.id.chg_headerDate));
                view.setTag(viewHolderHeader);
            }
            if (item != null && viewHolderHeader != null) {
                if (viewHolderHeader.version != null) {
                    StringBuilder sb = new StringBuilder();
                    String string = getContext().getString(this.mStringVersionHeader);
                    if (string != null) {
                        sb.append(string);
                    }
                    sb.append(item.versionName);
                    viewHolderHeader.version.setText(sb.toString());
                }
                if (viewHolderHeader.date != null) {
                    if (item.changeDate != null) {
                        viewHolderHeader.date.setText(item.changeDate);
                        viewHolderHeader.date.setVisibility(0);
                    } else {
                        viewHolderHeader.date.setText("");
                        viewHolderHeader.date.setVisibility(8);
                    }
                }
            }
        }
        return view;
    }

    @Override // android.widget.BaseAdapter, android.widget.Adapter
    public int getItemViewType(int i) {
        return getItem(i).isHeader() ? 1 : 0;
    }

    static class ViewHolderHeader {
        TextView date;
        TextView version;

        public ViewHolderHeader(TextView textView, TextView textView2) {
            this.version = textView;
            this.date = textView2;
        }
    }

    static class ViewHolderRow {
        TextView bulletText;
        TextView text;

        public ViewHolderRow(TextView textView, TextView textView2) {
            this.text = textView;
            this.bulletText = textView2;
        }
    }

    public int getmRowLayoutId() {
        return this.mRowLayoutId;
    }

    public void setmRowLayoutId(int i) {
        this.mRowLayoutId = i;
    }

    public int getmRowHeaderLayoutId() {
        return this.mRowHeaderLayoutId;
    }

    public void setmRowHeaderLayoutId(int i) {
        this.mRowHeaderLayoutId = i;
    }

    public int getmStringVersionHeader() {
        return this.mStringVersionHeader;
    }

    public void setmStringVersionHeader(int i) {
        this.mStringVersionHeader = i;
    }
}
