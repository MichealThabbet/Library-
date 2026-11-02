package com.afollestad.materialdialogs.simplelist;

import android.graphics.PorterDuff;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.afollestad.materialdialogs.MaterialDialog;
import com.afollestad.materialdialogs.commons.R;
import com.afollestad.materialdialogs.internal.MDAdapter;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes.dex */
public class MaterialSimpleListAdapter extends RecyclerView.Adapter<SimpleListVH> implements MDAdapter {
    private Callback callback;
    private MaterialDialog dialog;
    private List<MaterialSimpleListItem> items = new ArrayList(4);

    public interface Callback {
        void onMaterialListItemSelected(MaterialDialog materialDialog, int i, MaterialSimpleListItem materialSimpleListItem);
    }

    public MaterialSimpleListAdapter(Callback callback) {
        this.callback = callback;
    }

    public void add(MaterialSimpleListItem materialSimpleListItem) {
        this.items.add(materialSimpleListItem);
        notifyItemInserted(this.items.size() - 1);
    }

    public void clear() {
        this.items.clear();
        notifyDataSetChanged();
    }

    public MaterialSimpleListItem getItem(int i) {
        return this.items.get(i);
    }

    @Override // com.afollestad.materialdialogs.internal.MDAdapter
    public void setDialog(MaterialDialog materialDialog) {
        this.dialog = materialDialog;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public SimpleListVH onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new SimpleListVH(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.md_simplelist_item, viewGroup, false), this);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public void onBindViewHolder(SimpleListVH simpleListVH, int i) {
        if (this.dialog != null) {
            MaterialSimpleListItem materialSimpleListItem = this.items.get(i);
            if (materialSimpleListItem.getIcon() != null) {
                simpleListVH.icon.setImageDrawable(materialSimpleListItem.getIcon());
                simpleListVH.icon.setPadding(materialSimpleListItem.getIconPadding(), materialSimpleListItem.getIconPadding(), materialSimpleListItem.getIconPadding(), materialSimpleListItem.getIconPadding());
                simpleListVH.icon.getBackground().setColorFilter(materialSimpleListItem.getBackgroundColor(), PorterDuff.Mode.SRC_ATOP);
            } else {
                simpleListVH.icon.setVisibility(8);
            }
            simpleListVH.title.setTextColor(this.dialog.getBuilder().getItemColor());
            simpleListVH.title.setText(materialSimpleListItem.getContent());
            this.dialog.setTypeface(simpleListVH.title, this.dialog.getBuilder().getRegularFont());
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemCount() {
        return this.items.size();
    }

    static class SimpleListVH extends RecyclerView.ViewHolder implements View.OnClickListener {
        final MaterialSimpleListAdapter adapter;
        final ImageView icon;
        final TextView title;

        SimpleListVH(View view, MaterialSimpleListAdapter materialSimpleListAdapter) {
            super(view);
            this.icon = (ImageView) view.findViewById(android.R.id.icon);
            this.title = (TextView) view.findViewById(android.R.id.title);
            this.adapter = materialSimpleListAdapter;
            view.setOnClickListener(this);
        }

        @Override // android.view.View.OnClickListener
        public void onClick(View view) {
            if (this.adapter.callback != null) {
                this.adapter.callback.onMaterialListItemSelected(this.adapter.dialog, getAdapterPosition(), this.adapter.getItem(getAdapterPosition()));
            }
        }
    }
}
