package com.marshalchen.ultimaterecyclerview.multiViewTypes;

import android.view.ViewGroup;
import com.marshalchen.ultimaterecyclerview.UltimateDifferentViewTypeAdapter;
import com.marshalchen.ultimaterecyclerview.UltimateRecyclerviewViewHolder;

/* loaded from: classes2.dex */
public abstract class DataBinder<T extends UltimateRecyclerviewViewHolder> {
    private UltimateDifferentViewTypeAdapter mDataBindAdapter;

    public abstract void bindViewHolder(T t, int i);

    public abstract int getItemCount();

    public abstract T newViewHolder(ViewGroup viewGroup);

    public DataBinder(UltimateDifferentViewTypeAdapter ultimateDifferentViewTypeAdapter) {
        this.mDataBindAdapter = ultimateDifferentViewTypeAdapter;
    }

    public final void notifyDataSetChanged() {
        this.mDataBindAdapter.notifyDataSetChanged();
    }

    public final void notifyBinderDataSetChanged() {
        notifyBinderItemRangeChanged(0, getItemCount());
    }

    public final void notifyBinderItemChanged(int i) {
        this.mDataBindAdapter.notifyBinderItemChanged(this, i);
    }

    public final void notifyBinderItemRangeChanged(int i, int i2) {
        this.mDataBindAdapter.notifyBinderItemRangeChanged(this, i, i2);
    }

    public final void notifyBinderItemInserted(int i) {
        this.mDataBindAdapter.notifyBinderItemInserted(this, i);
    }

    public final void notifyBinderItemMoved(int i, int i2) {
        this.mDataBindAdapter.notifyBinderItemMoved(this, i, i2);
    }

    public final void notifyBinderItemRangeInserted(int i, int i2) {
        this.mDataBindAdapter.notifyBinderItemRangeInserted(this, i, i2);
    }

    public final void notifyBinderItemRemoved(int i) {
        this.mDataBindAdapter.notifyBinderItemRemoved(this, i);
    }

    public final void notifyBinderItemRangeRemoved(int i, int i2) {
        this.mDataBindAdapter.notifyBinderItemRangeRemoved(this, i, i2);
    }
}
