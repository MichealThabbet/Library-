package it.orsozoxi.android.orsonotes.models.listeners;

import android.widget.AbsListView;

/* loaded from: classes3.dex */
public abstract class AbsListViewScrollDetector implements AbsListView.OnScrollListener {
    private int mLastScrollY;
    private AbsListView mListView;
    private int mPreviousFirstVisibleItem;
    private int mScrollThreshold;

    public abstract void onScrollDown();

    @Override // android.widget.AbsListView.OnScrollListener
    public void onScrollStateChanged(AbsListView absListView, int i) {
    }

    public abstract void onScrollUp();

    @Override // android.widget.AbsListView.OnScrollListener
    public void onScroll(AbsListView absListView, int i, int i2, int i3) {
        if (isSameRow(i)) {
            int topItemScrollY = getTopItemScrollY();
            if (Math.abs(this.mLastScrollY - topItemScrollY) > this.mScrollThreshold) {
                if (this.mLastScrollY > topItemScrollY) {
                    onScrollUp();
                } else {
                    onScrollDown();
                }
            }
            this.mLastScrollY = topItemScrollY;
            return;
        }
        if (i > this.mPreviousFirstVisibleItem) {
            onScrollUp();
        } else {
            onScrollDown();
        }
        this.mLastScrollY = getTopItemScrollY();
        this.mPreviousFirstVisibleItem = i;
    }

    public void setScrollThreshold(int i) {
        this.mScrollThreshold = i;
    }

    public void setListView(AbsListView absListView) {
        this.mListView = absListView;
    }

    private boolean isSameRow(int i) {
        return i == this.mPreviousFirstVisibleItem;
    }

    private int getTopItemScrollY() {
        AbsListView absListView = this.mListView;
        if (absListView == null || absListView.getChildAt(0) == null) {
            return 0;
        }
        return this.mListView.getChildAt(0).getTop();
    }
}
