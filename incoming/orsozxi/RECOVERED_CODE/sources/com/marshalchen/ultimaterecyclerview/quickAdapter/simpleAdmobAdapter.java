package com.marshalchen.ultimaterecyclerview.quickAdapter;

import android.view.View;
import android.view.ViewGroup;
import androidx.recyclerview.widget.RecyclerView;
import com.marshalchen.ultimaterecyclerview.AdmobAdapter;
import com.marshalchen.ultimaterecyclerview.UltimateRecyclerviewViewHolder;
import java.util.List;

/* loaded from: classes2.dex */
public abstract class simpleAdmobAdapter<T, B extends UltimateRecyclerviewViewHolder, GoogleAdView extends ViewGroup> extends AdmobAdapter<GoogleAdView, T, B> {
    @Override // com.marshalchen.ultimaterecyclerview.UltimateViewAdapter
    public long generateHeaderId(int i) {
        return 0L;
    }

    @Override // com.marshalchen.ultimaterecyclerview.stickyheadersrecyclerview.StickyRecyclerHeadersAdapter
    public void onBindHeaderViewHolder(RecyclerView.ViewHolder viewHolder, int i) {
    }

    @Override // com.marshalchen.ultimaterecyclerview.stickyheadersrecyclerview.StickyRecyclerHeadersAdapter
    public RecyclerView.ViewHolder onCreateHeaderViewHolder(ViewGroup viewGroup) {
        return null;
    }

    protected abstract void withBindHolder(B b, T t, int i);

    public simpleAdmobAdapter(GoogleAdView googleadview, boolean z, int i, List<T> list) {
        super(googleadview, z, i, list);
    }

    public simpleAdmobAdapter(GoogleAdView googleadview, boolean z, int i, List<T> list, AdmobAdapter.AdviewListener adviewListener) {
        super(googleadview, z, i, list, adviewListener);
    }

    @Override // com.marshalchen.ultimaterecyclerview.UltimateViewAdapter
    public UltimateRecyclerviewViewHolder getViewHolder(View view) {
        return new UltimateRecyclerviewViewHolder(view);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public void onBindViewHolder(UltimateRecyclerviewViewHolder ultimateRecyclerviewViewHolder, int i) {
        if (onActionToBindData(i, this.list)) {
            withBindHolder(ultimateRecyclerviewViewHolder, this.list.get(i), i);
        }
    }

    @Override // com.marshalchen.ultimaterecyclerview.AdmobAdapter, com.marshalchen.ultimaterecyclerview.UltimateViewAdapter
    public int getAdapterItemCount() {
        return super.getAdapterItemCount();
    }
}
