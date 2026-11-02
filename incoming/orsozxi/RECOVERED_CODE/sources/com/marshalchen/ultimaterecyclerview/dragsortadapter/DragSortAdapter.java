package com.marshalchen.ultimaterecyclerview.dragsortadapter;

import android.content.res.Resources;
import android.graphics.Point;
import android.graphics.PointF;
import android.view.MotionEvent;
import android.view.View;
import androidx.recyclerview.widget.RecyclerView;
import com.marshalchen.ultimaterecyclerview.dragsortadapter.DragSortAdapter.ViewHolder;

/* loaded from: classes2.dex */
public abstract class DragSortAdapter<VH extends ViewHolder> extends RecyclerView.Adapter<VH> {
    private static final String TAG = DragSortAdapter.class.getSimpleName();
    private final DragManager dragManager;
    private final int SCROLL_AMOUNT = (int) (Resources.getSystem().getDisplayMetrics().density * 2.0f);
    private int scrollState = 0;
    private final PointF lastTouchPoint = new PointF();

    public abstract int getPositionForId(long j);

    public abstract boolean move(int i, int i2);

    public void onDrop() {
    }

    public DragSortAdapter(RecyclerView recyclerView) {
        setHasStableIds(true);
        DragManager dragManager = new DragManager(recyclerView, this);
        this.dragManager = dragManager;
        recyclerView.setOnDragListener(dragManager);
        recyclerView.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() { // from class: com.marshalchen.ultimaterecyclerview.dragsortadapter.DragSortAdapter.1
            @Override // androidx.recyclerview.widget.RecyclerView.OnItemTouchListener
            public void onRequestDisallowInterceptTouchEvent(boolean z) {
            }

            @Override // androidx.recyclerview.widget.RecyclerView.OnItemTouchListener
            public void onTouchEvent(RecyclerView recyclerView2, MotionEvent motionEvent) {
            }

            @Override // androidx.recyclerview.widget.RecyclerView.OnItemTouchListener
            public boolean onInterceptTouchEvent(RecyclerView recyclerView2, MotionEvent motionEvent) {
                DragSortAdapter.this.lastTouchPoint.set(motionEvent.getX(), motionEvent.getY());
                return false;
            }
        });
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() { // from class: com.marshalchen.ultimaterecyclerview.dragsortadapter.DragSortAdapter.2
            @Override // androidx.recyclerview.widget.RecyclerView.OnScrollListener
            public void onScrolled(final RecyclerView recyclerView2, int i, int i2) {
                recyclerView2.post(new Runnable() { // from class: com.marshalchen.ultimaterecyclerview.dragsortadapter.DragSortAdapter.2.1
                    @Override // java.lang.Runnable
                    public void run() {
                        DragSortAdapter.this.handleScroll(recyclerView2);
                    }
                });
            }

            @Override // androidx.recyclerview.widget.RecyclerView.OnScrollListener
            public void onScrollStateChanged(RecyclerView recyclerView2, int i) {
                DragSortAdapter.this.scrollState = i;
                if (i != 0) {
                    return;
                }
                DragSortAdapter.this.handleScroll(recyclerView2);
            }
        });
    }

    public long getDraggingId() {
        return this.dragManager.getDraggingId();
    }

    public PointF getLastTouchPoint() {
        return new PointF(this.lastTouchPoint.x, this.lastTouchPoint.y);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleScroll(RecyclerView recyclerView) {
        DragInfo lastDragInfo;
        if (this.scrollState == 0 && (lastDragInfo = this.dragManager.getLastDragInfo()) != null) {
            handleDragScroll(recyclerView, lastDragInfo);
        }
    }

    void handleDragScroll(RecyclerView recyclerView, DragInfo dragInfo) {
        if (recyclerView.getLayoutManager().canScrollHorizontally()) {
            if (recyclerView.canScrollHorizontally(-1) && dragInfo.shouldScrollLeft()) {
                recyclerView.scrollBy(-this.SCROLL_AMOUNT, 0);
                this.dragManager.clearNextMove();
                return;
            } else {
                if (recyclerView.canScrollHorizontally(1) && dragInfo.shouldScrollRight(recyclerView.getWidth())) {
                    recyclerView.scrollBy(this.SCROLL_AMOUNT, 0);
                    this.dragManager.clearNextMove();
                    return;
                }
                return;
            }
        }
        if (recyclerView.getLayoutManager().canScrollVertically()) {
            if (recyclerView.canScrollVertically(-1) && dragInfo.shouldScrollUp()) {
                recyclerView.scrollBy(0, -this.SCROLL_AMOUNT);
                this.dragManager.clearNextMove();
            } else if (recyclerView.canScrollVertically(1) && dragInfo.shouldScrollDown(recyclerView.getHeight())) {
                recyclerView.scrollBy(0, this.SCROLL_AMOUNT);
                this.dragManager.clearNextMove();
            }
        }
    }

    public static abstract class ViewHolder extends RecyclerView.ViewHolder {
        final DragSortAdapter<?> adapter;

        public ViewHolder(DragSortAdapter<?> dragSortAdapter, View view) {
            super(view);
            this.adapter = dragSortAdapter;
        }

        public final void startDrag() {
            PointF lastTouchPoint = this.adapter.getLastTouchPoint();
            startDrag(getShadowBuilder(this.itemView, new Point((int) (lastTouchPoint.x - this.itemView.getX()), (int) (lastTouchPoint.y - this.itemView.getY()))));
        }

        public View.DragShadowBuilder getShadowBuilder(View view, Point point) {
            return new DragSortShadowBuilder(view, point);
        }

        public final void startDrag(View.DragShadowBuilder dragShadowBuilder) {
            Point point = new Point();
            Point point2 = new Point();
            dragShadowBuilder.onProvideShadowMetrics(point, point2);
            this.itemView.startDrag(null, dragShadowBuilder, new DragInfo(getItemId(), point, point2, this.adapter.getLastTouchPoint()), 0);
            this.adapter.notifyItemChanged(getAdapterPosition());
        }
    }
}
