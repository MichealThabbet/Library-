package com.nhaarman.listviewanimations.itemmanipulation.dragdrop;

import android.view.View;
import android.widget.AbsListView;
import android.widget.ListAdapter;
import com.nhaarman.listviewanimations.itemmanipulation.DynamicListView;

/* loaded from: classes2.dex */
public class DynamicListViewWrapper implements DragAndDropListViewWrapper {
    private final DynamicListView mDynamicListView;

    public DynamicListViewWrapper(DynamicListView dynamicListView) {
        this.mDynamicListView = dynamicListView;
    }

    @Override // com.nhaarman.listviewanimations.util.ListViewWrapper
    public DynamicListView getListView() {
        return this.mDynamicListView;
    }

    @Override // com.nhaarman.listviewanimations.util.ListViewWrapper
    public View getChildAt(int i) {
        return this.mDynamicListView.getChildAt(i);
    }

    @Override // com.nhaarman.listviewanimations.util.ListViewWrapper
    public int getFirstVisiblePosition() {
        return this.mDynamicListView.getFirstVisiblePosition();
    }

    @Override // com.nhaarman.listviewanimations.util.ListViewWrapper
    public int getLastVisiblePosition() {
        return this.mDynamicListView.getLastVisiblePosition();
    }

    @Override // com.nhaarman.listviewanimations.util.ListViewWrapper
    public int getCount() {
        return this.mDynamicListView.getCount();
    }

    @Override // com.nhaarman.listviewanimations.util.ListViewWrapper
    public int getChildCount() {
        return this.mDynamicListView.getChildCount();
    }

    @Override // com.nhaarman.listviewanimations.util.ListViewWrapper
    public int getHeaderViewsCount() {
        return this.mDynamicListView.getHeaderViewsCount();
    }

    @Override // com.nhaarman.listviewanimations.util.ListViewWrapper
    public int getPositionForView(View view) {
        return this.mDynamicListView.getPositionForView(view);
    }

    @Override // com.nhaarman.listviewanimations.util.ListViewWrapper
    public ListAdapter getAdapter() {
        return this.mDynamicListView.getAdapter();
    }

    @Override // com.nhaarman.listviewanimations.util.ListViewWrapper
    public void smoothScrollBy(int i, int i2) {
        this.mDynamicListView.smoothScrollBy(i, i2);
    }

    @Override // com.nhaarman.listviewanimations.itemmanipulation.dragdrop.DragAndDropListViewWrapper
    public void setOnScrollListener(AbsListView.OnScrollListener onScrollListener) {
        this.mDynamicListView.setOnScrollListener(onScrollListener);
    }

    @Override // com.nhaarman.listviewanimations.itemmanipulation.dragdrop.DragAndDropListViewWrapper
    public int pointToPosition(int i, int i2) {
        return this.mDynamicListView.pointToPosition(i, i2);
    }

    @Override // com.nhaarman.listviewanimations.itemmanipulation.dragdrop.DragAndDropListViewWrapper
    public int computeVerticalScrollOffset() {
        return this.mDynamicListView.computeVerticalScrollOffset();
    }

    @Override // com.nhaarman.listviewanimations.itemmanipulation.dragdrop.DragAndDropListViewWrapper
    public int computeVerticalScrollExtent() {
        return this.mDynamicListView.computeVerticalScrollExtent();
    }

    @Override // com.nhaarman.listviewanimations.itemmanipulation.dragdrop.DragAndDropListViewWrapper
    public int computeVerticalScrollRange() {
        return this.mDynamicListView.computeVerticalScrollRange();
    }
}
