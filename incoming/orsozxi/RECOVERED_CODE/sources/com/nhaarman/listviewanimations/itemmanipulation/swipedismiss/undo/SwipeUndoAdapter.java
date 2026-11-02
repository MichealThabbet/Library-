package com.nhaarman.listviewanimations.itemmanipulation.swipedismiss.undo;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import com.nhaarman.listviewanimations.BaseAdapterDecorator;
import com.nhaarman.listviewanimations.itemmanipulation.DynamicListView;
import com.nhaarman.listviewanimations.itemmanipulation.swipedismiss.DismissableManager;
import com.nhaarman.listviewanimations.util.ListViewWrapper;

/* loaded from: classes2.dex */
public abstract class SwipeUndoAdapter extends BaseAdapterDecorator {
    private SwipeUndoTouchListener mSwipeUndoTouchListener;
    private UndoCallback mUndoCallback;

    protected SwipeUndoAdapter(BaseAdapter baseAdapter, UndoCallback undoCallback) {
        super(baseAdapter);
        this.mUndoCallback = undoCallback;
    }

    @Override // com.nhaarman.listviewanimations.BaseAdapterDecorator, com.nhaarman.listviewanimations.util.ListViewWrapperSetter
    public void setListViewWrapper(ListViewWrapper listViewWrapper) {
        super.setListViewWrapper(listViewWrapper);
        this.mSwipeUndoTouchListener = new SwipeUndoTouchListener(listViewWrapper, this.mUndoCallback);
        if (listViewWrapper.getListView() instanceof DynamicListView) {
            return;
        }
        listViewWrapper.getListView().setOnTouchListener(this.mSwipeUndoTouchListener);
    }

    public void setDismissableManager(DismissableManager dismissableManager) {
        SwipeUndoTouchListener swipeUndoTouchListener = this.mSwipeUndoTouchListener;
        if (swipeUndoTouchListener == null) {
            throw new IllegalStateException("You must call setAbsListView() first.");
        }
        swipeUndoTouchListener.setDismissableManager(dismissableManager);
    }

    public void setSwipeUndoTouchListener(SwipeUndoTouchListener swipeUndoTouchListener) {
        this.mSwipeUndoTouchListener = swipeUndoTouchListener;
    }

    @Override // com.nhaarman.listviewanimations.BaseAdapterDecorator, android.widget.Adapter
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (getListViewWrapper() == null) {
            throw new IllegalArgumentException("Call setAbsListView() on this SwipeUndoAdapter before setAdapter()!");
        }
        return super.getView(i, view, viewGroup);
    }

    public void setUndoCallback(UndoCallback undoCallback) {
        this.mUndoCallback = undoCallback;
    }

    public UndoCallback getUndoCallback() {
        return this.mUndoCallback;
    }

    public void undo(View view) {
        this.mSwipeUndoTouchListener.undo(view);
    }

    public void dismiss(int i) {
        this.mSwipeUndoTouchListener.dismiss(i);
    }
}
