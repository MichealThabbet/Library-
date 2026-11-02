package com.marshalchen.ultimaterecyclerview.stickyheadersrecyclerview;

import android.view.ViewGroup;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;

/* loaded from: classes2.dex */
public interface StickyRecyclerHeadersAdapter<VH extends RecyclerView.ViewHolder> {
    long getHeaderId(int i);

    int getItemCount();

    void onBindHeaderViewHolder(VH vh, int i);

    VH onCreateHeaderViewHolder(ViewGroup viewGroup);
}
