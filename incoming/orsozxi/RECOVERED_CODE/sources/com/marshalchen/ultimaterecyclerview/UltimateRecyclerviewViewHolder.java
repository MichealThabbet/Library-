package com.marshalchen.ultimaterecyclerview;

import android.view.View;
import androidx.recyclerview.widget.RecyclerView;
import com.marshalchen.ultimaterecyclerview.itemTouchHelper.ItemTouchHelperViewHolder;
import com.marshalchen.ultimaterecyclerview.swipe.SwipeLayout;

/* loaded from: classes2.dex */
public class UltimateRecyclerviewViewHolder extends RecyclerView.ViewHolder implements ItemTouchHelperViewHolder {
    public SwipeLayout.OnLayout onLayoutListener;
    public int position;
    public SwipeLayout swipeLayout;
    public SwipeLayout.SwipeListener swipeMemory;

    @Override // com.marshalchen.ultimaterecyclerview.itemTouchHelper.ItemTouchHelperViewHolder
    public void onItemClear() {
    }

    @Override // com.marshalchen.ultimaterecyclerview.itemTouchHelper.ItemTouchHelperViewHolder
    public void onItemSelected() {
    }

    public UltimateRecyclerviewViewHolder(View view) {
        super(view);
        this.swipeLayout = null;
        this.onLayoutListener = null;
        this.swipeMemory = null;
        this.position = -1;
        this.swipeLayout = (SwipeLayout) view.findViewById(R.id.recyclerview_swipe);
    }
}
