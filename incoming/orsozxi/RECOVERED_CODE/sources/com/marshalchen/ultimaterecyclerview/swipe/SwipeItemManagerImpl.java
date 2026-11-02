package com.marshalchen.ultimaterecyclerview.swipe;

import androidx.recyclerview.widget.RecyclerView;
import com.marshalchen.ultimaterecyclerview.UltimateRecyclerviewViewHolder;
import com.marshalchen.ultimaterecyclerview.swipe.SwipeItemManagerInterface;
import com.marshalchen.ultimaterecyclerview.swipe.SwipeLayout;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/* loaded from: classes2.dex */
public class SwipeItemManagerImpl implements SwipeItemManagerInterface {
    protected RecyclerView.Adapter mAdapter;
    public final int INVALID_POSITION = -1;
    private SwipeItemManagerInterface.Mode mode = SwipeItemManagerInterface.Mode.Single;
    protected int mOpenPosition = -1;
    protected Set<Integer> mOpenPositions = new HashSet();
    protected Set<SwipeLayout> mShownLayouts = new HashSet();

    public SwipeItemManagerImpl(RecyclerView.Adapter adapter) {
        if (adapter == null) {
            throw new IllegalArgumentException("Adapter can not be null");
        }
        if (!(adapter instanceof SwipeItemManagerInterface)) {
            throw new IllegalArgumentException("adapter should implement the SwipeAdapterInterface");
        }
        this.mAdapter = adapter;
    }

    @Override // com.marshalchen.ultimaterecyclerview.swipe.SwipeItemManagerInterface
    public SwipeItemManagerInterface.Mode getMode() {
        return this.mode;
    }

    @Override // com.marshalchen.ultimaterecyclerview.swipe.SwipeItemManagerInterface
    public void setMode(SwipeItemManagerInterface.Mode mode) {
        this.mode = mode;
        this.mOpenPositions.clear();
        this.mShownLayouts.clear();
        this.mOpenPosition = -1;
    }

    private void initialize(UltimateRecyclerviewViewHolder ultimateRecyclerviewViewHolder, int i) {
        ultimateRecyclerviewViewHolder.onLayoutListener = new OnLayoutListener(i);
        ultimateRecyclerviewViewHolder.swipeMemory = new SwipeMemory(i);
        ultimateRecyclerviewViewHolder.position = i;
        ultimateRecyclerviewViewHolder.swipeLayout.addSwipeListener(ultimateRecyclerviewViewHolder.swipeMemory);
        ultimateRecyclerviewViewHolder.swipeLayout.addOnLayoutListener(ultimateRecyclerviewViewHolder.onLayoutListener);
    }

    public void updateConvertView(UltimateRecyclerviewViewHolder ultimateRecyclerviewViewHolder, int i) {
        if (ultimateRecyclerviewViewHolder.onLayoutListener == null) {
            initialize(ultimateRecyclerviewViewHolder, i);
        }
        SwipeLayout swipeLayout = ultimateRecyclerviewViewHolder.swipeLayout;
        if (swipeLayout == null) {
            throw new IllegalStateException("can not find SwipeLayout in target view");
        }
        this.mShownLayouts.add(swipeLayout);
        ((SwipeMemory) ultimateRecyclerviewViewHolder.swipeMemory).setPosition(i);
        ((OnLayoutListener) ultimateRecyclerviewViewHolder.onLayoutListener).setPosition(i);
        ultimateRecyclerviewViewHolder.position = i;
    }

    @Override // com.marshalchen.ultimaterecyclerview.swipe.SwipeItemManagerInterface
    public void openItem(int i) {
        if (this.mode == SwipeItemManagerInterface.Mode.Multiple) {
            if (this.mOpenPositions.contains(Integer.valueOf(i))) {
                return;
            }
            this.mOpenPositions.add(Integer.valueOf(i));
            return;
        }
        this.mOpenPosition = i;
    }

    @Override // com.marshalchen.ultimaterecyclerview.swipe.SwipeItemManagerInterface
    public void closeItem(int i) {
        if (this.mode == SwipeItemManagerInterface.Mode.Multiple) {
            this.mOpenPositions.remove(Integer.valueOf(i));
        } else if (this.mOpenPosition == i) {
            this.mOpenPosition = -1;
        }
    }

    @Override // com.marshalchen.ultimaterecyclerview.swipe.SwipeItemManagerInterface
    public void closeAllExcept(SwipeLayout swipeLayout) {
        for (SwipeLayout swipeLayout2 : this.mShownLayouts) {
            if (swipeLayout2 != swipeLayout) {
                swipeLayout2.close();
            }
        }
    }

    @Override // com.marshalchen.ultimaterecyclerview.swipe.SwipeItemManagerInterface
    public void removeShownLayouts(SwipeLayout swipeLayout) {
        this.mShownLayouts.remove(swipeLayout);
    }

    @Override // com.marshalchen.ultimaterecyclerview.swipe.SwipeItemManagerInterface
    public List<Integer> getOpenItems() {
        return this.mode == SwipeItemManagerInterface.Mode.Multiple ? new ArrayList(this.mOpenPositions) : Arrays.asList(Integer.valueOf(this.mOpenPosition));
    }

    @Override // com.marshalchen.ultimaterecyclerview.swipe.SwipeItemManagerInterface
    public List<SwipeLayout> getOpenLayouts() {
        return new ArrayList(this.mShownLayouts);
    }

    @Override // com.marshalchen.ultimaterecyclerview.swipe.SwipeItemManagerInterface
    public boolean isOpen(int i) {
        if (this.mode == SwipeItemManagerInterface.Mode.Multiple) {
            return this.mOpenPositions.contains(Integer.valueOf(i));
        }
        return this.mOpenPosition == i;
    }

    private class OnLayoutListener implements SwipeLayout.OnLayout {
        private int position;

        OnLayoutListener(int i) {
            this.position = i;
        }

        public void setPosition(int i) {
            this.position = i;
        }

        @Override // com.marshalchen.ultimaterecyclerview.swipe.SwipeLayout.OnLayout
        public void onLayout(SwipeLayout swipeLayout) {
            if (SwipeItemManagerImpl.this.isOpen(this.position)) {
                swipeLayout.open(false, false);
            } else {
                swipeLayout.close(false, false);
            }
        }
    }

    private class SwipeMemory extends SimpleSwipeListener {
        private int position;

        SwipeMemory(int i) {
            this.position = i;
        }

        @Override // com.marshalchen.ultimaterecyclerview.swipe.SimpleSwipeListener, com.marshalchen.ultimaterecyclerview.swipe.SwipeLayout.SwipeListener
        public void onClose(SwipeLayout swipeLayout) {
            if (SwipeItemManagerImpl.this.mode == SwipeItemManagerInterface.Mode.Multiple) {
                SwipeItemManagerImpl.this.mOpenPositions.remove(Integer.valueOf(this.position));
            } else {
                SwipeItemManagerImpl.this.mOpenPosition = -1;
            }
        }

        @Override // com.marshalchen.ultimaterecyclerview.swipe.SimpleSwipeListener, com.marshalchen.ultimaterecyclerview.swipe.SwipeLayout.SwipeListener
        public void onStartOpen(SwipeLayout swipeLayout) {
            if (SwipeItemManagerImpl.this.mode == SwipeItemManagerInterface.Mode.Single) {
                SwipeItemManagerImpl.this.closeAllExcept(swipeLayout);
            }
        }

        @Override // com.marshalchen.ultimaterecyclerview.swipe.SimpleSwipeListener, com.marshalchen.ultimaterecyclerview.swipe.SwipeLayout.SwipeListener
        public void onOpen(SwipeLayout swipeLayout) {
            if (SwipeItemManagerImpl.this.mode == SwipeItemManagerInterface.Mode.Multiple) {
                SwipeItemManagerImpl.this.mOpenPositions.add(Integer.valueOf(this.position));
                return;
            }
            SwipeItemManagerImpl.this.closeAllExcept(swipeLayout);
            SwipeItemManagerImpl.this.mOpenPosition = this.position;
        }

        public void setPosition(int i) {
            this.position = i;
        }
    }
}
