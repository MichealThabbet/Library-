package com.marshalchen.ultimaterecyclerview;

import android.view.View;
import androidx.recyclerview.widget.RecyclerView;
import com.marshalchen.ultimaterecyclerview.swipe.SwipeItemManagerImpl;
import com.marshalchen.ultimaterecyclerview.swipe.SwipeItemManagerInterface;
import com.marshalchen.ultimaterecyclerview.swipe.SwipeLayout;
import java.util.List;

/* loaded from: classes2.dex */
public abstract class SwipeableUltimateViewAdapter extends UltimateViewAdapter<UltimateRecyclerviewViewHolder> implements SwipeItemManagerInterface {
    protected SwipeItemManagerImpl mItemManger = new SwipeItemManagerImpl(this);

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public void onBindViewHolder(UltimateRecyclerviewViewHolder ultimateRecyclerviewViewHolder, int i) {
        this.mItemManger.updateConvertView(ultimateRecyclerviewViewHolder, i);
    }

    @Override // com.marshalchen.ultimaterecyclerview.swipe.SwipeItemManagerInterface
    public void openItem(int i) {
        this.mItemManger.openItem(i);
    }

    @Override // com.marshalchen.ultimaterecyclerview.swipe.SwipeItemManagerInterface
    public void closeItem(int i) {
        this.mItemManger.closeItem(i);
    }

    @Override // com.marshalchen.ultimaterecyclerview.swipe.SwipeItemManagerInterface
    public void closeAllExcept(SwipeLayout swipeLayout) {
        this.mItemManger.closeAllExcept(swipeLayout);
    }

    @Override // com.marshalchen.ultimaterecyclerview.swipe.SwipeItemManagerInterface
    public List<Integer> getOpenItems() {
        return this.mItemManger.getOpenItems();
    }

    @Override // com.marshalchen.ultimaterecyclerview.swipe.SwipeItemManagerInterface
    public List<SwipeLayout> getOpenLayouts() {
        return this.mItemManger.getOpenLayouts();
    }

    @Override // com.marshalchen.ultimaterecyclerview.swipe.SwipeItemManagerInterface
    public void removeShownLayouts(SwipeLayout swipeLayout) {
        this.mItemManger.removeShownLayouts(swipeLayout);
    }

    @Override // com.marshalchen.ultimaterecyclerview.swipe.SwipeItemManagerInterface
    public boolean isOpen(int i) {
        return this.mItemManger.isOpen(i);
    }

    @Override // com.marshalchen.ultimaterecyclerview.swipe.SwipeItemManagerInterface
    public SwipeItemManagerInterface.Mode getMode() {
        return this.mItemManger.getMode();
    }

    @Override // com.marshalchen.ultimaterecyclerview.swipe.SwipeItemManagerInterface
    public void setMode(SwipeItemManagerInterface.Mode mode) {
        this.mItemManger.setMode(mode);
    }

    public static class BaseSwipeableViewHolder extends RecyclerView.ViewHolder {
        public SwipeLayout.OnLayout onLayoutListener;
        public int position;
        public SwipeLayout swipeLayout;
        public SwipeLayout.SwipeListener swipeMemory;

        public BaseSwipeableViewHolder(View view) {
            super(view);
            this.swipeLayout = null;
            this.onLayoutListener = null;
            this.swipeMemory = null;
            this.position = -1;
            this.swipeLayout = (SwipeLayout) view.findViewById(R.id.recyclerview_swipe);
        }
    }
}
