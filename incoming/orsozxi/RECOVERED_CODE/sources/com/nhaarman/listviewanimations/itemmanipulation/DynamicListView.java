package com.nhaarman.listviewanimations.itemmanipulation;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Pair;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ListView;
import androidx.mediarouter.media.SystemMediaRouteProvider;
import com.nhaarman.listviewanimations.itemmanipulation.animateaddition.AnimateAdditionAdapter;
import com.nhaarman.listviewanimations.itemmanipulation.dragdrop.DragAndDropHandler;
import com.nhaarman.listviewanimations.itemmanipulation.dragdrop.DraggableManager;
import com.nhaarman.listviewanimations.itemmanipulation.dragdrop.DynamicListViewWrapper;
import com.nhaarman.listviewanimations.itemmanipulation.dragdrop.OnItemMovedListener;
import com.nhaarman.listviewanimations.itemmanipulation.swipedismiss.DismissableManager;
import com.nhaarman.listviewanimations.itemmanipulation.swipedismiss.OnDismissCallback;
import com.nhaarman.listviewanimations.itemmanipulation.swipedismiss.SwipeDismissTouchListener;
import com.nhaarman.listviewanimations.itemmanipulation.swipedismiss.SwipeTouchListener;
import com.nhaarman.listviewanimations.itemmanipulation.swipedismiss.undo.SwipeUndoAdapter;
import com.nhaarman.listviewanimations.itemmanipulation.swipedismiss.undo.SwipeUndoTouchListener;
import com.nhaarman.listviewanimations.itemmanipulation.swipedismiss.undo.UndoCallback;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;

/* loaded from: classes2.dex */
public class DynamicListView extends ListView {
    private AnimateAdditionAdapter<Object> mAnimateAdditionAdapter;
    private TouchEventHandler mCurrentHandlingTouchEventHandler;
    private DragAndDropHandler mDragAndDropHandler;
    private final MyOnScrollListener mMyOnScrollListener;
    private SwipeTouchListener mSwipeTouchListener;
    private SwipeUndoAdapter mSwipeUndoAdapter;

    public DynamicListView(Context context) {
        this(context, null);
    }

    public DynamicListView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, Resources.getSystem().getIdentifier("listViewStyle", "attr", SystemMediaRouteProvider.PACKAGE_NAME));
    }

    public DynamicListView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        MyOnScrollListener myOnScrollListener = new MyOnScrollListener();
        this.mMyOnScrollListener = myOnScrollListener;
        super.setOnScrollListener(myOnScrollListener);
    }

    @Override // android.view.View
    public void setOnTouchListener(View.OnTouchListener onTouchListener) {
        if (onTouchListener instanceof SwipeTouchListener) {
            return;
        }
        super.setOnTouchListener(onTouchListener);
    }

    @Override // android.widget.AbsListView
    public void setOnScrollListener(AbsListView.OnScrollListener onScrollListener) {
        this.mMyOnScrollListener.addOnScrollListener(onScrollListener);
    }

    public void enableDragAndDrop() {
        if (Build.VERSION.SDK_INT < 14) {
            throw new UnsupportedOperationException("Drag and drop is only supported API levels 14 and up!");
        }
        this.mDragAndDropHandler = new DragAndDropHandler(this);
    }

    public void disableDragAndDrop() {
        this.mDragAndDropHandler = null;
    }

    public void enableSwipeToDismiss(OnDismissCallback onDismissCallback) {
        this.mSwipeTouchListener = new SwipeDismissTouchListener(new DynamicListViewWrapper(this), onDismissCallback);
    }

    public void enableSwipeUndo(UndoCallback undoCallback) {
        this.mSwipeTouchListener = new SwipeUndoTouchListener(new DynamicListViewWrapper(this), undoCallback);
    }

    public void enableSimpleSwipeUndo() {
        if (this.mSwipeUndoAdapter == null) {
            throw new IllegalStateException("enableSimpleSwipeUndo requires a SwipeUndoAdapter to be set as an adapter");
        }
        SwipeUndoTouchListener swipeUndoTouchListener = new SwipeUndoTouchListener(new DynamicListViewWrapper(this), this.mSwipeUndoAdapter.getUndoCallback());
        this.mSwipeTouchListener = swipeUndoTouchListener;
        this.mSwipeUndoAdapter.setSwipeUndoTouchListener(swipeUndoTouchListener);
    }

    public void disableSwipeToDismiss() {
        this.mSwipeTouchListener = null;
    }

    /* JADX WARN: Removed duplicated region for block: B:14:0x0030  */
    @Override // android.widget.AdapterView
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public void setAdapter(android.widget.ListAdapter r4) {
        /*
            r3 = this;
            r0 = 0
            r3.mSwipeUndoAdapter = r0
            boolean r0 = r4 instanceof android.widget.BaseAdapter
            if (r0 == 0) goto L30
            r0 = r4
            android.widget.BaseAdapter r0 = (android.widget.BaseAdapter) r0
            r1 = r0
        Lb:
            boolean r2 = r1 instanceof com.nhaarman.listviewanimations.BaseAdapterDecorator
            if (r2 == 0) goto L1f
            boolean r2 = r1 instanceof com.nhaarman.listviewanimations.itemmanipulation.swipedismiss.undo.SwipeUndoAdapter
            if (r2 == 0) goto L18
            r2 = r1
            com.nhaarman.listviewanimations.itemmanipulation.swipedismiss.undo.SwipeUndoAdapter r2 = (com.nhaarman.listviewanimations.itemmanipulation.swipedismiss.undo.SwipeUndoAdapter) r2
            r3.mSwipeUndoAdapter = r2
        L18:
            com.nhaarman.listviewanimations.BaseAdapterDecorator r1 = (com.nhaarman.listviewanimations.BaseAdapterDecorator) r1
            android.widget.BaseAdapter r1 = r1.getDecoratedBaseAdapter()
            goto Lb
        L1f:
            boolean r1 = r1 instanceof com.nhaarman.listviewanimations.util.Insertable
            if (r1 == 0) goto L30
            com.nhaarman.listviewanimations.itemmanipulation.animateaddition.AnimateAdditionAdapter r1 = new com.nhaarman.listviewanimations.itemmanipulation.animateaddition.AnimateAdditionAdapter
            r1.<init>(r0)
            r3.mAnimateAdditionAdapter = r1
            r1.setListView(r3)
            com.nhaarman.listviewanimations.itemmanipulation.animateaddition.AnimateAdditionAdapter<java.lang.Object> r0 = r3.mAnimateAdditionAdapter
            goto L31
        L30:
            r0 = r4
        L31:
            super.setAdapter(r0)
            com.nhaarman.listviewanimations.itemmanipulation.dragdrop.DragAndDropHandler r0 = r3.mDragAndDropHandler
            if (r0 == 0) goto L3b
            r0.setAdapter(r4)
        L3b:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.nhaarman.listviewanimations.itemmanipulation.DynamicListView.setAdapter(android.widget.ListAdapter):void");
    }

    @Override // android.view.ViewGroup, android.view.View
    public boolean dispatchTouchEvent(MotionEvent motionEvent) {
        DragAndDropHandler dragAndDropHandler;
        boolean zIsInteracting;
        SwipeTouchListener swipeTouchListener;
        if (this.mCurrentHandlingTouchEventHandler == null) {
            SwipeTouchListener swipeTouchListener2 = this.mSwipeTouchListener;
            if (((swipeTouchListener2 instanceof SwipeUndoTouchListener) && ((SwipeUndoTouchListener) swipeTouchListener2).hasPendingItems()) || (dragAndDropHandler = this.mDragAndDropHandler) == null) {
                zIsInteracting = false;
            } else {
                dragAndDropHandler.onTouchEvent(motionEvent);
                zIsInteracting = this.mDragAndDropHandler.isInteracting();
                if (zIsInteracting) {
                    this.mCurrentHandlingTouchEventHandler = this.mDragAndDropHandler;
                    sendCancelEvent(this.mSwipeTouchListener, motionEvent);
                }
            }
            if (this.mCurrentHandlingTouchEventHandler == null && (swipeTouchListener = this.mSwipeTouchListener) != null) {
                swipeTouchListener.onTouchEvent(motionEvent);
                zIsInteracting = this.mSwipeTouchListener.isInteracting();
                if (zIsInteracting) {
                    this.mCurrentHandlingTouchEventHandler = this.mSwipeTouchListener;
                    sendCancelEvent(this.mDragAndDropHandler, motionEvent);
                }
            }
            if (zIsInteracting) {
                MotionEvent motionEventObtain = MotionEvent.obtain(motionEvent);
                motionEventObtain.setAction(3);
                super.onTouchEvent(motionEventObtain);
            }
            return zIsInteracting || super.dispatchTouchEvent(motionEvent);
        }
        return onTouchEvent(motionEvent);
    }

    @Override // android.widget.AbsListView, android.view.View
    public boolean onTouchEvent(MotionEvent motionEvent) {
        TouchEventHandler touchEventHandler = this.mCurrentHandlingTouchEventHandler;
        if (touchEventHandler != null) {
            touchEventHandler.onTouchEvent(motionEvent);
        }
        if (motionEvent.getActionMasked() == 1 || motionEvent.getActionMasked() == 3) {
            this.mCurrentHandlingTouchEventHandler = null;
        }
        return this.mCurrentHandlingTouchEventHandler != null || super.onTouchEvent(motionEvent);
    }

    private void sendCancelEvent(TouchEventHandler touchEventHandler, MotionEvent motionEvent) {
        if (touchEventHandler != null) {
            MotionEvent motionEventObtain = MotionEvent.obtain(motionEvent);
            motionEventObtain.setAction(3);
            touchEventHandler.onTouchEvent(motionEventObtain);
        }
    }

    @Override // android.widget.ListView, android.widget.AbsListView, android.view.ViewGroup, android.view.View
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
        DragAndDropHandler dragAndDropHandler = this.mDragAndDropHandler;
        if (dragAndDropHandler != null) {
            dragAndDropHandler.dispatchDraw(canvas);
        }
    }

    @Override // android.widget.AbsListView, android.view.View
    public int computeVerticalScrollOffset() {
        return super.computeVerticalScrollOffset();
    }

    @Override // android.widget.AbsListView, android.view.View
    public int computeVerticalScrollExtent() {
        return super.computeVerticalScrollExtent();
    }

    @Override // android.widget.AbsListView, android.view.View
    public int computeVerticalScrollRange() {
        return super.computeVerticalScrollRange();
    }

    public void insert(int i, Object obj) {
        AnimateAdditionAdapter<Object> animateAdditionAdapter = this.mAnimateAdditionAdapter;
        if (animateAdditionAdapter == null) {
            throw new IllegalStateException("Adapter should implement Insertable!");
        }
        animateAdditionAdapter.insert(i, (int) obj);
    }

    public void insert(int i, Object... objArr) {
        AnimateAdditionAdapter<Object> animateAdditionAdapter = this.mAnimateAdditionAdapter;
        if (animateAdditionAdapter == null) {
            throw new IllegalStateException("Adapter should implement Insertable!");
        }
        animateAdditionAdapter.insert(i, objArr);
    }

    public <T> void insert(Pair<Integer, T>... pairArr) {
        AnimateAdditionAdapter<Object> animateAdditionAdapter = this.mAnimateAdditionAdapter;
        if (animateAdditionAdapter == null) {
            throw new IllegalStateException("Adapter should implement Insertable!");
        }
        animateAdditionAdapter.insert(pairArr);
    }

    public <T> void insert(Iterable<Pair<Integer, T>> iterable) {
        AnimateAdditionAdapter<Object> animateAdditionAdapter = this.mAnimateAdditionAdapter;
        if (animateAdditionAdapter == null) {
            throw new IllegalStateException("Adapter should implement Insertable!");
        }
        animateAdditionAdapter.insert(iterable);
    }

    public void setDraggableManager(DraggableManager draggableManager) {
        DragAndDropHandler dragAndDropHandler = this.mDragAndDropHandler;
        if (dragAndDropHandler != null) {
            dragAndDropHandler.setDraggableManager(draggableManager);
        }
    }

    public void setOnItemMovedListener(OnItemMovedListener onItemMovedListener) {
        DragAndDropHandler dragAndDropHandler = this.mDragAndDropHandler;
        if (dragAndDropHandler != null) {
            dragAndDropHandler.setOnItemMovedListener(onItemMovedListener);
        }
    }

    public void startDragging(int i) {
        DragAndDropHandler dragAndDropHandler;
        SwipeTouchListener swipeTouchListener = this.mSwipeTouchListener;
        if (((swipeTouchListener instanceof SwipeUndoTouchListener) && ((SwipeUndoTouchListener) swipeTouchListener).hasPendingItems()) || (dragAndDropHandler = this.mDragAndDropHandler) == null) {
            return;
        }
        dragAndDropHandler.startDragging(i);
    }

    public void setScrollSpeed(float f) {
        DragAndDropHandler dragAndDropHandler = this.mDragAndDropHandler;
        if (dragAndDropHandler != null) {
            dragAndDropHandler.setScrollSpeed(f);
        }
    }

    public void setDismissableManager(DismissableManager dismissableManager) {
        SwipeTouchListener swipeTouchListener = this.mSwipeTouchListener;
        if (swipeTouchListener != null) {
            swipeTouchListener.setDismissableManager(dismissableManager);
        }
    }

    @Override // android.widget.AbsListView
    public void fling(int i) {
        SwipeTouchListener swipeTouchListener = this.mSwipeTouchListener;
        if (swipeTouchListener != null) {
            swipeTouchListener.fling(i);
        }
    }

    public void setSwipeTouchChild(int i) {
        SwipeTouchListener swipeTouchListener = this.mSwipeTouchListener;
        if (swipeTouchListener != null) {
            swipeTouchListener.setTouchChild(i);
        }
    }

    public void setMinimumAlpha(float f) {
        SwipeTouchListener swipeTouchListener = this.mSwipeTouchListener;
        if (swipeTouchListener != null) {
            swipeTouchListener.setMinimumAlpha(f);
        }
    }

    public void dismiss(int i) {
        SwipeTouchListener swipeTouchListener = this.mSwipeTouchListener;
        if (swipeTouchListener != null) {
            if (swipeTouchListener instanceof SwipeDismissTouchListener) {
                ((SwipeDismissTouchListener) swipeTouchListener).dismiss(i);
                return;
            }
            throw new IllegalStateException("Enabled swipe functionality does not support dismiss");
        }
    }

    public void undo(View view) {
        SwipeTouchListener swipeTouchListener = this.mSwipeTouchListener;
        if (swipeTouchListener != null) {
            if (swipeTouchListener instanceof SwipeUndoTouchListener) {
                ((SwipeUndoTouchListener) swipeTouchListener).undo(view);
                return;
            }
            throw new IllegalStateException("Enabled swipe functionality does not support undo");
        }
    }

    private class MyOnScrollListener implements AbsListView.OnScrollListener {
        private final Collection<AbsListView.OnScrollListener> mOnScrollListeners;

        private MyOnScrollListener() {
            this.mOnScrollListeners = new HashSet();
        }

        @Override // android.widget.AbsListView.OnScrollListener
        public void onScrollStateChanged(AbsListView absListView, int i) {
            Iterator<AbsListView.OnScrollListener> it2 = this.mOnScrollListeners.iterator();
            while (it2.hasNext()) {
                it2.next().onScrollStateChanged(absListView, i);
            }
            if (i == 1 && (DynamicListView.this.mSwipeTouchListener instanceof SwipeUndoTouchListener)) {
                ((SwipeUndoTouchListener) DynamicListView.this.mSwipeTouchListener).dimissPending();
            }
        }

        @Override // android.widget.AbsListView.OnScrollListener
        public void onScroll(AbsListView absListView, int i, int i2, int i3) {
            Iterator<AbsListView.OnScrollListener> it2 = this.mOnScrollListeners.iterator();
            while (it2.hasNext()) {
                it2.next().onScroll(absListView, i, i2, i3);
            }
        }

        public void addOnScrollListener(AbsListView.OnScrollListener onScrollListener) {
            this.mOnScrollListeners.add(onScrollListener);
        }
    }
}
