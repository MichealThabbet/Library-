package com.marshalchen.ultimaterecyclerview;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.view.View;
import android.view.ViewGroup;
import androidx.recyclerview.widget.RecyclerView;
import com.marshalchen.ultimaterecyclerview.UltimateRecyclerView;
import com.marshalchen.ultimaterecyclerview.itemTouchHelper.ItemTouchHelperAdapter;
import com.marshalchen.ultimaterecyclerview.stickyheadersrecyclerview.StickyRecyclerHeadersAdapter;
import java.util.Collections;
import java.util.List;

/* loaded from: classes2.dex */
public abstract class NormalUltimateViewAdapter extends RecyclerView.Adapter<UltimateRecyclerviewViewHolder> implements StickyRecyclerHeadersAdapter<RecyclerView.ViewHolder>, ItemTouchHelperAdapter {
    protected View customLoadMoreView = null;
    protected UltimateRecyclerView.CustomRelativeWrapper customHeaderView = null;
    public boolean isLoadMoreChanged = false;
    protected OnStartDragListener mDragStartListener = null;

    protected enum AdapterAnimationType {
        AlphaIn,
        SlideInBottom,
        ScaleIn,
        SlideInLeft,
        SlideInRight
    }

    public interface OnStartDragListener {
        void onStartDrag(RecyclerView.ViewHolder viewHolder);
    }

    public abstract long generateHeaderId(int i);

    public abstract int getAdapterItemCount();

    public abstract UltimateRecyclerviewViewHolder onCreateViewHolder(ViewGroup viewGroup);

    public void setCustomHeaderView(UltimateRecyclerView.CustomRelativeWrapper customRelativeWrapper) {
        this.customHeaderView = customRelativeWrapper;
    }

    public UltimateRecyclerView.CustomRelativeWrapper getCustomHeaderView() {
        return this.customHeaderView;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public UltimateRecyclerviewViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        if (i == 2) {
            UltimateRecyclerviewViewHolder ultimateRecyclerviewViewHolder = new UltimateRecyclerviewViewHolder(this.customLoadMoreView);
            if (getAdapterItemCount() == 0) {
                ultimateRecyclerviewViewHolder.itemView.setVisibility(8);
            }
            return ultimateRecyclerviewViewHolder;
        }
        if (i == 1) {
            UltimateRecyclerView.CustomRelativeWrapper customRelativeWrapper = this.customHeaderView;
            if (customRelativeWrapper != null) {
                return new UltimateRecyclerviewViewHolder(customRelativeWrapper);
            }
        } else if (i == 3) {
            UltimateRecyclerviewViewHolder ultimateRecyclerviewViewHolder2 = new UltimateRecyclerviewViewHolder(this.customLoadMoreView);
            if (getAdapterItemCount() == 0) {
                ultimateRecyclerviewViewHolder2.itemView.setVisibility(8);
            }
            return ultimateRecyclerviewViewHolder2;
        }
        return onCreateViewHolder(viewGroup);
    }

    public void setCustomLoadMoreView(View view) {
        this.customLoadMoreView = view;
    }

    public void swipeCustomLoadMoreView(View view) {
        this.customLoadMoreView = view;
        this.isLoadMoreChanged = true;
    }

    public View getCustomLoadMoreView() {
        return this.customLoadMoreView;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemViewType(int i) {
        return (i != getItemCount() - 1 || this.customLoadMoreView == null) ? (i != 0 || this.customHeaderView == null) ? 0 : 1 : this.isLoadMoreChanged ? 3 : 2;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemCount() {
        int i = this.customHeaderView != null ? 1 : 0;
        if (this.customLoadMoreView != null) {
            i++;
        }
        return getAdapterItemCount() + i;
    }

    public void toggleSelection(int i) {
        notifyItemChanged(i);
    }

    public void clearSelection(int i) {
        notifyItemChanged(i);
    }

    public void setSelected(int i) {
        notifyItemChanged(i);
    }

    public void swapPositions(List<?> list, int i, int i2) {
        if (this.customHeaderView != null) {
            i--;
            i2--;
        }
        Collections.swap(list, i, i2);
    }

    public <T> void insert(List<T> list, T t, int i) {
        list.add(i, t);
        if (this.customHeaderView != null) {
            i++;
        }
        notifyItemInserted(i);
    }

    public void remove(List<?> list, int i) {
        if (list.size() > 0) {
            list.remove(this.customHeaderView != null ? i - 1 : i);
            notifyItemRemoved(i);
        }
    }

    public void clear(List<?> list) {
        int size = list.size();
        list.clear();
        notifyItemRangeRemoved(0, size);
    }

    @Override // com.marshalchen.ultimaterecyclerview.stickyheadersrecyclerview.StickyRecyclerHeadersAdapter
    public long getHeaderId(int i) {
        if (this.customHeaderView != null && i == 0) {
            return -1L;
        }
        if ((this.customLoadMoreView == null || i < getItemCount() - 1) && getAdapterItemCount() > 0) {
            return generateHeaderId(i);
        }
        return -1L;
    }

    protected class VIEW_TYPES {
        public static final int CHANGED_FOOTER = 3;
        public static final int FOOTER = 2;
        public static final int HEADER = 1;
        public static final int NORMAL = 0;

        protected VIEW_TYPES() {
        }
    }

    protected Animator[] getAdapterAnimations(View view, AdapterAnimationType adapterAnimationType) {
        if (adapterAnimationType == AdapterAnimationType.ScaleIn) {
            return new ObjectAnimator[]{ObjectAnimator.ofFloat(view, "scaleX", 0.5f, 1.0f), ObjectAnimator.ofFloat(view, "scaleY", 0.5f, 1.0f)};
        }
        if (adapterAnimationType == AdapterAnimationType.AlphaIn) {
            return new Animator[]{ObjectAnimator.ofFloat(view, "alpha", 0.5f, 1.0f)};
        }
        if (adapterAnimationType == AdapterAnimationType.SlideInBottom) {
            return new Animator[]{ObjectAnimator.ofFloat(view, "translationY", view.getMeasuredHeight(), 0.0f)};
        }
        if (adapterAnimationType == AdapterAnimationType.SlideInLeft) {
            return new Animator[]{ObjectAnimator.ofFloat(view, "translationX", -view.getRootView().getWidth(), 0.0f)};
        }
        if (adapterAnimationType == AdapterAnimationType.SlideInRight) {
            return new Animator[]{ObjectAnimator.ofFloat(view, "translationX", view.getRootView().getWidth(), 0.0f)};
        }
        return null;
    }

    @Override // com.marshalchen.ultimaterecyclerview.itemTouchHelper.ItemTouchHelperAdapter
    public void onItemMove(int i, int i2) {
        notifyItemMoved(i, i2);
    }

    @Override // com.marshalchen.ultimaterecyclerview.itemTouchHelper.ItemTouchHelperAdapter
    public void onItemDismiss(int i) {
        notifyDataSetChanged();
    }
}
