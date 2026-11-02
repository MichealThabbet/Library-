package com.marshalchen.ultimaterecyclerview.stickyheadersrecyclerview;

import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import androidx.recyclerview.widget.RecyclerView;

/* loaded from: classes2.dex */
public class StickyRecyclerHeadersTouchListener implements RecyclerView.OnItemTouchListener {
    private final StickyRecyclerHeadersDecoration mDecor;
    private OnHeaderClickListener mOnHeaderClickListener;
    private final RecyclerView mRecyclerView;
    private final GestureDetector mTapDetector;

    public interface OnHeaderClickListener {
        void onHeaderClick(View view, int i, long j);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.OnItemTouchListener
    public void onRequestDisallowInterceptTouchEvent(boolean z) {
    }

    @Override // androidx.recyclerview.widget.RecyclerView.OnItemTouchListener
    public void onTouchEvent(RecyclerView recyclerView, MotionEvent motionEvent) {
    }

    public StickyRecyclerHeadersTouchListener(RecyclerView recyclerView, StickyRecyclerHeadersDecoration stickyRecyclerHeadersDecoration) {
        this.mTapDetector = new GestureDetector(recyclerView.getContext(), new SingleTapDetector());
        this.mRecyclerView = recyclerView;
        this.mDecor = stickyRecyclerHeadersDecoration;
    }

    public StickyRecyclerHeadersAdapter getAdapter() {
        if (this.mRecyclerView.getAdapter() instanceof StickyRecyclerHeadersAdapter) {
            return (StickyRecyclerHeadersAdapter) this.mRecyclerView.getAdapter();
        }
        throw new IllegalStateException("A RecyclerView with " + StickyRecyclerHeadersTouchListener.class.getSimpleName() + " requires a " + StickyRecyclerHeadersAdapter.class.getSimpleName());
    }

    public void setOnHeaderClickListener(OnHeaderClickListener onHeaderClickListener) {
        this.mOnHeaderClickListener = onHeaderClickListener;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.OnItemTouchListener
    public boolean onInterceptTouchEvent(RecyclerView recyclerView, MotionEvent motionEvent) {
        return this.mOnHeaderClickListener != null && this.mTapDetector.onTouchEvent(motionEvent);
    }

    private class SingleTapDetector extends GestureDetector.SimpleOnGestureListener {
        @Override // android.view.GestureDetector.SimpleOnGestureListener, android.view.GestureDetector.OnDoubleTapListener
        public boolean onDoubleTap(MotionEvent motionEvent) {
            return true;
        }

        private SingleTapDetector() {
        }

        @Override // android.view.GestureDetector.SimpleOnGestureListener, android.view.GestureDetector.OnGestureListener
        public boolean onSingleTapUp(MotionEvent motionEvent) {
            int iFindHeaderPositionUnder = StickyRecyclerHeadersTouchListener.this.mDecor.findHeaderPositionUnder((int) motionEvent.getX(), (int) motionEvent.getY());
            if (iFindHeaderPositionUnder == -1) {
                return false;
            }
            View headerView = StickyRecyclerHeadersTouchListener.this.mDecor.getHeaderView(StickyRecyclerHeadersTouchListener.this.mRecyclerView, iFindHeaderPositionUnder);
            StickyRecyclerHeadersTouchListener.this.mOnHeaderClickListener.onHeaderClick(headerView, iFindHeaderPositionUnder, StickyRecyclerHeadersTouchListener.this.getAdapter().getHeaderId(iFindHeaderPositionUnder));
            StickyRecyclerHeadersTouchListener.this.mRecyclerView.playSoundEffect(0);
            headerView.onTouchEvent(motionEvent);
            return true;
        }
    }
}
