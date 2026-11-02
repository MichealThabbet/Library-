package com.marshalchen.ultimaterecyclerview.quickAdapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.recyclerview.widget.RecyclerView;
import com.marshalchen.ultimaterecyclerview.UltimateRecyclerviewViewHolder;
import com.marshalchen.ultimaterecyclerview.UltimateViewAdapter;
import java.util.List;

/* loaded from: classes2.dex */
public abstract class easyRegularAdapter<T, BINDHOLDER extends UltimateRecyclerviewViewHolder> extends UltimateViewAdapter {
    private List<T> currentlistsource;

    @Override // com.marshalchen.ultimaterecyclerview.UltimateViewAdapter
    public long generateHeaderId(int i) {
        return 0L;
    }

    protected abstract int getNormalLayoutResId();

    protected abstract BINDHOLDER newViewHolder(View view);

    @Override // com.marshalchen.ultimaterecyclerview.stickyheadersrecyclerview.StickyRecyclerHeadersAdapter
    public void onBindHeaderViewHolder(RecyclerView.ViewHolder viewHolder, int i) {
    }

    @Override // com.marshalchen.ultimaterecyclerview.stickyheadersrecyclerview.StickyRecyclerHeadersAdapter
    public RecyclerView.ViewHolder onCreateHeaderViewHolder(ViewGroup viewGroup) {
        return null;
    }

    protected abstract void withBindHolder(BINDHOLDER bindholder, T t, int i);

    @Override // com.marshalchen.ultimaterecyclerview.UltimateViewAdapter
    public UltimateRecyclerviewViewHolder getViewHolder(View view) {
        return new UltimateRecyclerviewViewHolder(view);
    }

    public easyRegularAdapter(List<T> list) {
        this.currentlistsource = list;
    }

    @Override // com.marshalchen.ultimaterecyclerview.UltimateViewAdapter
    public UltimateRecyclerviewViewHolder onCreateViewHolder(ViewGroup viewGroup) {
        return newViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(getNormalLayoutResId(), viewGroup, false));
    }

    @Override // com.marshalchen.ultimaterecyclerview.UltimateViewAdapter
    public int getAdapterItemCount() {
        return this.currentlistsource.size();
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i) {
        if (i == getAdapterItemCount()) {
            return;
        }
        withBindHolder((UltimateRecyclerviewViewHolder) viewHolder, this.currentlistsource.get(i), i);
    }

    public void insert(T t) {
        List<T> list = this.currentlistsource;
        insert(list, t, list.size());
    }

    public void removeAll() {
        this.currentlistsource.clear();
        notifyDataSetChanged();
    }
}
