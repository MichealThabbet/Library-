package com.marshalchen.ultimaterecyclerview;

import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import androidx.recyclerview.widget.RecyclerView;

/* loaded from: classes2.dex */
public class ItemTouchListenerAdapter extends GestureDetector.SimpleOnGestureListener implements RecyclerView.OnItemTouchListener {
    private GestureDetector gestureDetector;
    private RecyclerViewOnItemClickListener listener;
    private RecyclerView recyclerView;

    public interface RecyclerViewOnItemClickListener {
        void onItemClick(RecyclerView recyclerView, View view, int i);

        void onItemLongClick(RecyclerView recyclerView, View view, int i);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.OnItemTouchListener
    public void onRequestDisallowInterceptTouchEvent(boolean z) {
    }

    @Override // androidx.recyclerview.widget.RecyclerView.OnItemTouchListener
    public void onTouchEvent(RecyclerView recyclerView, MotionEvent motionEvent) {
    }

    public ItemTouchListenerAdapter(RecyclerView recyclerView, RecyclerViewOnItemClickListener recyclerViewOnItemClickListener) {
        if (recyclerView == null || recyclerViewOnItemClickListener == null) {
            throw new IllegalArgumentException("RecyclerView and Listener arguments can not be null");
        }
        this.recyclerView = recyclerView;
        this.listener = recyclerViewOnItemClickListener;
        this.gestureDetector = new GestureDetector(recyclerView.getContext(), this);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.OnItemTouchListener
    public boolean onInterceptTouchEvent(RecyclerView recyclerView, MotionEvent motionEvent) {
        this.gestureDetector.onTouchEvent(motionEvent);
        return false;
    }

    @Override // android.view.GestureDetector.SimpleOnGestureListener, android.view.GestureDetector.OnGestureListener
    public void onShowPress(MotionEvent motionEvent) {
        View childViewUnder = getChildViewUnder(motionEvent);
        if (childViewUnder != null) {
            childViewUnder.setPressed(true);
        }
    }

    private int shiftAdjustInt(int i) {
        if (!(this.recyclerView.getAdapter() instanceof AdmobAdapter) || i <= 0) {
            return i;
        }
        AdmobAdapter admobAdapter = (AdmobAdapter) this.recyclerView.getAdapter();
        if (admobAdapter.isPosOnAdView(i)) {
            return -1;
        }
        return admobAdapter.getFinalShiftPosition(i);
    }

    @Override // android.view.GestureDetector.SimpleOnGestureListener, android.view.GestureDetector.OnGestureListener
    public boolean onSingleTapUp(MotionEvent motionEvent) {
        View childViewUnder = getChildViewUnder(motionEvent);
        if (childViewUnder == null) {
            return false;
        }
        childViewUnder.setPressed(false);
        int iShiftAdjustInt = shiftAdjustInt(this.recyclerView.getChildPosition(childViewUnder));
        if (iShiftAdjustInt == -1) {
            return true;
        }
        this.listener.onItemClick(this.recyclerView, childViewUnder, iShiftAdjustInt);
        return true;
    }

    @Override // android.view.GestureDetector.SimpleOnGestureListener, android.view.GestureDetector.OnGestureListener
    public void onLongPress(MotionEvent motionEvent) {
        View childViewUnder = getChildViewUnder(motionEvent);
        if (childViewUnder == null) {
            return;
        }
        int iShiftAdjustInt = shiftAdjustInt(this.recyclerView.getChildPosition(childViewUnder));
        if (iShiftAdjustInt != -1) {
            this.listener.onItemLongClick(this.recyclerView, childViewUnder, iShiftAdjustInt);
        }
        childViewUnder.setPressed(false);
    }

    private View getChildViewUnder(MotionEvent motionEvent) {
        return this.recyclerView.findChildViewUnder(motionEvent.getX(), motionEvent.getY());
    }
}
