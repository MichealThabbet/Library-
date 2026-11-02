package com.marshalchen.ultimaterecyclerview.dragsortadapter;

import android.graphics.PointF;
import android.view.DragEvent;
import android.view.View;
import androidx.recyclerview.widget.RecyclerView;
import java.lang.ref.WeakReference;

/* loaded from: classes2.dex */
public class DragManager implements View.OnDragListener {
    private final DragSortAdapter<?> adapter;
    private DragInfo lastDragInfo;
    private final WeakReference<RecyclerView> recyclerViewRef;
    private long draggingId = -1;
    private final PointF nextMoveTouchPoint = new PointF(Float.MIN_VALUE, Float.MIN_VALUE);

    public DragManager(RecyclerView recyclerView, DragSortAdapter<?> dragSortAdapter) {
        this.recyclerViewRef = new WeakReference<>(recyclerView);
        this.adapter = dragSortAdapter;
    }

    @Override // android.view.View.OnDragListener
    public boolean onDrag(View view, DragEvent dragEvent) {
        if (view != this.recyclerViewRef.get() || !(dragEvent.getLocalState() instanceof DragInfo)) {
            return false;
        }
        final RecyclerView recyclerView = (RecyclerView) view;
        DragInfo dragInfo = (DragInfo) dragEvent.getLocalState();
        final long jItemId = dragInfo.itemId();
        int action = dragEvent.getAction();
        if (action == 1) {
            this.draggingId = jItemId;
            this.adapter.notifyItemChanged(recyclerView.findViewHolderForItemId(jItemId).getAdapterPosition());
        } else if (action == 2) {
            float x = dragEvent.getX();
            float y = dragEvent.getY();
            int positionForId = this.adapter.getPositionForId(jItemId);
            View viewFindChildViewUnder = recyclerView.findChildViewUnder(dragEvent.getX(), dragEvent.getY());
            int adapterPosition = viewFindChildViewUnder != null ? recyclerView.getChildViewHolder(viewFindChildViewUnder).getAdapterPosition() : -1;
            if (adapterPosition >= 0 && positionForId != adapterPosition) {
                RecyclerView.ItemAnimator itemAnimator = recyclerView.getItemAnimator();
                boolean zEquals = this.nextMoveTouchPoint.equals(Float.MIN_VALUE, Float.MIN_VALUE);
                this.nextMoveTouchPoint.set(x, y);
                if (zEquals) {
                    itemAnimator.isRunning(new RecyclerView.ItemAnimator.ItemAnimatorFinishedListener() { // from class: com.marshalchen.ultimaterecyclerview.dragsortadapter.DragManager.1
                        @Override // androidx.recyclerview.widget.RecyclerView.ItemAnimator.ItemAnimatorFinishedListener
                        public void onAnimationsFinished() {
                            if (DragManager.this.nextMoveTouchPoint.equals(Float.MIN_VALUE, Float.MIN_VALUE)) {
                                return;
                            }
                            final int positionForId2 = DragManager.this.adapter.getPositionForId(jItemId);
                            View viewFindChildViewUnder2 = recyclerView.findChildViewUnder(DragManager.this.nextMoveTouchPoint.x, DragManager.this.nextMoveTouchPoint.y);
                            if (viewFindChildViewUnder2 != null) {
                                final int adapterPosition2 = recyclerView.getChildViewHolder(viewFindChildViewUnder2).getAdapterPosition();
                                if (DragManager.this.adapter.move(positionForId2, adapterPosition2)) {
                                    if (positionForId2 == 0 || adapterPosition2 == 0) {
                                        recyclerView.scrollToPosition(0);
                                    }
                                    recyclerView.post(new Runnable() { // from class: com.marshalchen.ultimaterecyclerview.dragsortadapter.DragManager.1.1
                                        @Override // java.lang.Runnable
                                        public void run() {
                                            DragManager.this.adapter.notifyItemMoved(positionForId2, adapterPosition2);
                                        }
                                    });
                                }
                            }
                            DragManager.this.clearNextMove();
                        }
                    });
                }
            }
            this.lastDragInfo = dragInfo;
            dragInfo.setDragPoint(x, y);
            this.adapter.handleDragScroll(recyclerView, dragInfo);
        } else if (action == 3) {
            this.adapter.onDrop();
        } else if (action == 4) {
            this.draggingId = -1L;
            this.lastDragInfo = null;
            recyclerView.getItemAnimator().isRunning(new AnonymousClass2(jItemId, recyclerView));
        }
        return true;
    }

    /* renamed from: com.marshalchen.ultimaterecyclerview.dragsortadapter.DragManager$2, reason: invalid class name */
    class AnonymousClass2 implements RecyclerView.ItemAnimator.ItemAnimatorFinishedListener {
        final /* synthetic */ long val$itemId;
        final /* synthetic */ RecyclerView val$recyclerView;

        AnonymousClass2(long j, RecyclerView recyclerView) {
            this.val$itemId = j;
            this.val$recyclerView = recyclerView;
        }

        @Override // androidx.recyclerview.widget.RecyclerView.ItemAnimator.ItemAnimatorFinishedListener
        public void onAnimationsFinished() {
            int positionForId = DragManager.this.adapter.getPositionForId(this.val$itemId);
            RecyclerView.ViewHolder viewHolderFindViewHolderForItemId = this.val$recyclerView.findViewHolderForItemId(this.val$itemId);
            if (viewHolderFindViewHolderForItemId == null || viewHolderFindViewHolderForItemId.getAdapterPosition() == positionForId) {
                DragManager.this.adapter.notifyItemChanged(DragManager.this.adapter.getPositionForId(this.val$itemId));
            } else {
                this.val$recyclerView.post(new Runnable() { // from class: com.marshalchen.ultimaterecyclerview.dragsortadapter.DragManager.2.1
                    @Override // java.lang.Runnable
                    public void run() {
                        AnonymousClass2.this.val$recyclerView.getItemAnimator().isRunning(new RecyclerView.ItemAnimator.ItemAnimatorFinishedListener() { // from class: com.marshalchen.ultimaterecyclerview.dragsortadapter.DragManager.2.1.1
                            @Override // androidx.recyclerview.widget.RecyclerView.ItemAnimator.ItemAnimatorFinishedListener
                            public void onAnimationsFinished() {
                                DragManager.this.adapter.notifyItemChanged(DragManager.this.adapter.getPositionForId(AnonymousClass2.this.val$itemId));
                            }
                        });
                    }
                });
            }
        }
    }

    public void clearNextMove() {
        this.nextMoveTouchPoint.set(Float.MIN_VALUE, Float.MIN_VALUE);
    }

    public long getDraggingId() {
        return this.draggingId;
    }

    public DragInfo getLastDragInfo() {
        return this.lastDragInfo;
    }
}
