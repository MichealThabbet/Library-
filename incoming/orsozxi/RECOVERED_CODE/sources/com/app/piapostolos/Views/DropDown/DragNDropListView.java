package com.app.piapostolos.Views.DropDown;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.WrapperListAdapter;
import it.orsozoxi.android.orsonotes.R2;

/* loaded from: classes.dex */
public class DragNDropListView extends ListView {
    private boolean isDraggingEnabled;
    int mDragHandler;
    boolean mDragMode;
    OnItemDragNDropListener mDragNDropListener;
    int mDragPointOffset;
    ImageView mDragView;
    int mStartPosition;
    WindowManager mWm;

    public interface OnItemDragNDropListener {
        void onItemDrag(DragNDropListView dragNDropListView, View view, int i, long j);

        void onItemDrop(DragNDropListView dragNDropListView, View view, int i, int i2, long j);
    }

    private void init() {
        this.mWm = (WindowManager) getContext().getSystemService("window");
    }

    public DragNDropListView(Context context) {
        super(context);
        this.mStartPosition = -1;
        this.mDragHandler = 0;
        this.isDraggingEnabled = true;
        init();
    }

    public DragNDropListView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mStartPosition = -1;
        this.mDragHandler = 0;
        this.isDraggingEnabled = true;
        init();
    }

    public DragNDropListView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mStartPosition = -1;
        this.mDragHandler = 0;
        this.isDraggingEnabled = true;
        init();
    }

    public void setOnItemDragNDropListener(OnItemDragNDropListener onItemDragNDropListener) {
        this.mDragNDropListener = onItemDragNDropListener;
    }

    public void setDragNDropAdapter(DragNDropAdapter dragNDropAdapter) {
        this.mDragHandler = dragNDropAdapter.getDragHandler();
        setAdapter((ListAdapter) dragNDropAdapter);
    }

    public boolean isDrag(MotionEvent motionEvent) {
        int x;
        int y;
        int iPointToPosition;
        View childAt;
        View viewFindViewById;
        if (this.mDragMode) {
            return true;
        }
        if (this.mDragHandler == 0 || (iPointToPosition = pointToPosition((x = (int) motionEvent.getX()), (y = (int) motionEvent.getY()))) == -1 || (viewFindViewById = (childAt = getChildAt(iPointToPosition - getFirstVisiblePosition())).findViewById(this.mDragHandler)) == null) {
            return false;
        }
        int top = childAt.getTop() + viewFindViewById.getTop();
        int height = viewFindViewById.getHeight() + top;
        int left = childAt.getLeft() + viewFindViewById.getLeft();
        return left <= x && x <= viewFindViewById.getWidth() + left && top <= y && y <= height;
    }

    public boolean isDragging() {
        return this.mDragMode;
    }

    public View getDragView() {
        return this.mDragView;
    }

    @Override // android.widget.AbsListView, android.view.View
    public boolean onTouchEvent(MotionEvent motionEvent) {
        int action = motionEvent.getAction();
        int x = (int) motionEvent.getX();
        int y = (int) motionEvent.getY();
        if (action == 0 && isDrag(motionEvent)) {
            this.mDragMode = true;
        }
        if (!this.mDragMode || !this.isDraggingEnabled) {
            return super.onTouchEvent(motionEvent);
        }
        if (action == 0) {
            int iPointToPosition = pointToPosition(x, y);
            this.mStartPosition = iPointToPosition;
            if (iPointToPosition != -1) {
                int firstVisiblePosition = iPointToPosition - getFirstVisiblePosition();
                int top = y - getChildAt(firstVisiblePosition).getTop();
                this.mDragPointOffset = top;
                this.mDragPointOffset = top - (((int) motionEvent.getRawY()) - y);
                startDrag(firstVisiblePosition, y);
                drag(0, y);
            }
        } else if (action == 2) {
            drag(0, y);
        } else {
            this.mDragMode = false;
            if (this.mStartPosition != -1) {
                int iPointToPosition2 = pointToPosition(x, y);
                stopDrag(this.mStartPosition - getFirstVisiblePosition(), iPointToPosition2 <= (getCount() - getFooterViewsCount()) - 1 ? iPointToPosition2 : -1);
            }
        }
        return true;
    }

    private void startDrag(int i, int i2) {
        DragNDropAdapter dragNDropAdapter;
        View childAt = getChildAt(i);
        if (childAt == null) {
            return;
        }
        long itemIdAtPosition = getItemIdAtPosition(this.mStartPosition);
        OnItemDragNDropListener onItemDragNDropListener = this.mDragNDropListener;
        if (onItemDragNDropListener != null) {
            onItemDragNDropListener.onItemDrag(this, childAt, this.mStartPosition, itemIdAtPosition);
        }
        ListAdapter adapter = getAdapter();
        if (adapter instanceof WrapperListAdapter) {
            dragNDropAdapter = (DragNDropAdapter) ((WrapperListAdapter) adapter).getWrappedAdapter();
        } else {
            dragNDropAdapter = (DragNDropAdapter) adapter;
        }
        dragNDropAdapter.onItemDrag(this, childAt, this.mStartPosition, itemIdAtPosition);
        childAt.setDrawingCacheEnabled(true);
        Bitmap bitmapCreateBitmap = Bitmap.createBitmap(childAt.getDrawingCache());
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        layoutParams.gravity = 48;
        layoutParams.x = 0;
        layoutParams.y = i2 - this.mDragPointOffset;
        layoutParams.height = -2;
        layoutParams.width = -2;
        layoutParams.flags = R2.attr.singleLineTitle;
        layoutParams.format = -3;
        layoutParams.windowAnimations = 0;
        ImageView imageView = new ImageView(getContext());
        imageView.setImageBitmap(bitmapCreateBitmap);
        this.mWm.addView(imageView, layoutParams);
        this.mDragView = imageView;
        childAt.setVisibility(4);
        childAt.invalidate();
    }

    private void stopDrag(int i, int i2) {
        DragNDropAdapter dragNDropAdapter;
        if (this.mDragView == null) {
            return;
        }
        View childAt = getChildAt(i);
        if (i2 != -1) {
            long itemIdAtPosition = getItemIdAtPosition(this.mStartPosition);
            OnItemDragNDropListener onItemDragNDropListener = this.mDragNDropListener;
            if (onItemDragNDropListener != null) {
                onItemDragNDropListener.onItemDrop(this, childAt, this.mStartPosition, i2, itemIdAtPosition);
            }
            ListAdapter adapter = getAdapter();
            if (adapter instanceof WrapperListAdapter) {
                dragNDropAdapter = (DragNDropAdapter) ((WrapperListAdapter) adapter).getWrappedAdapter();
            } else {
                dragNDropAdapter = (DragNDropAdapter) adapter;
            }
            dragNDropAdapter.onItemDrop(this, childAt, this.mStartPosition, i2, itemIdAtPosition);
        }
        this.mDragView.setVisibility(8);
        this.mWm.removeView(this.mDragView);
        this.mDragView.setImageDrawable(null);
        this.mDragView = null;
        childAt.setDrawingCacheEnabled(false);
        childAt.destroyDrawingCache();
        childAt.setVisibility(0);
        this.mStartPosition = -1;
        invalidateViews();
    }

    private void drag(int i, int i2) {
        ImageView imageView = this.mDragView;
        if (imageView == null) {
            return;
        }
        WindowManager.LayoutParams layoutParams = (WindowManager.LayoutParams) imageView.getLayoutParams();
        layoutParams.x = i;
        layoutParams.y = i2 - this.mDragPointOffset;
        this.mWm.updateViewLayout(this.mDragView, layoutParams);
    }

    public void setDraggingEnabled(boolean z) {
        this.isDraggingEnabled = z;
    }
}
