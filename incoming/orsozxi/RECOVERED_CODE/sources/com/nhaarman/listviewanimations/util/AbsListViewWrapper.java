package com.nhaarman.listviewanimations.util;

import android.view.View;
import android.widget.AbsListView;
import android.widget.ListAdapter;
import android.widget.ListView;

/* loaded from: classes2.dex */
public class AbsListViewWrapper implements ListViewWrapper {
    private final AbsListView mAbsListView;

    public AbsListViewWrapper(AbsListView absListView) {
        this.mAbsListView = absListView;
    }

    @Override // com.nhaarman.listviewanimations.util.ListViewWrapper
    public AbsListView getListView() {
        return this.mAbsListView;
    }

    @Override // com.nhaarman.listviewanimations.util.ListViewWrapper
    public View getChildAt(int i) {
        return this.mAbsListView.getChildAt(i);
    }

    @Override // com.nhaarman.listviewanimations.util.ListViewWrapper
    public int getFirstVisiblePosition() {
        return this.mAbsListView.getFirstVisiblePosition();
    }

    @Override // com.nhaarman.listviewanimations.util.ListViewWrapper
    public int getLastVisiblePosition() {
        return this.mAbsListView.getLastVisiblePosition();
    }

    @Override // com.nhaarman.listviewanimations.util.ListViewWrapper
    public int getCount() {
        return this.mAbsListView.getCount();
    }

    @Override // com.nhaarman.listviewanimations.util.ListViewWrapper
    public int getChildCount() {
        return this.mAbsListView.getChildCount();
    }

    @Override // com.nhaarman.listviewanimations.util.ListViewWrapper
    public int getHeaderViewsCount() {
        AbsListView absListView = this.mAbsListView;
        if (absListView instanceof ListView) {
            return ((ListView) absListView).getHeaderViewsCount();
        }
        return 0;
    }

    @Override // com.nhaarman.listviewanimations.util.ListViewWrapper
    public int getPositionForView(View view) {
        return this.mAbsListView.getPositionForView(view);
    }

    @Override // com.nhaarman.listviewanimations.util.ListViewWrapper
    public ListAdapter getAdapter() {
        return (ListAdapter) this.mAbsListView.getAdapter();
    }

    @Override // com.nhaarman.listviewanimations.util.ListViewWrapper
    public void smoothScrollBy(int i, int i2) {
        this.mAbsListView.smoothScrollBy(i, i2);
    }
}
