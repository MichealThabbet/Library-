package com.marshalchen.ultimaterecyclerview.uiUtils;

import android.content.Context;
import android.graphics.PointF;
import android.view.View;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSmoothScroller;
import androidx.recyclerview.widget.RecyclerView;

/* loaded from: classes2.dex */
public class ScrollSmoothLineaerLayoutManager extends LinearLayoutManager {
    private final int duration;

    public ScrollSmoothLineaerLayoutManager(Context context, int i, boolean z, int i2) {
        super(context, i, z);
        this.duration = i2;
    }

    @Override // androidx.recyclerview.widget.LinearLayoutManager, androidx.recyclerview.widget.RecyclerView.LayoutManager
    public void smoothScrollToPosition(RecyclerView recyclerView, RecyclerView.State state, int i) {
        View childAt = recyclerView.getChildAt(0);
        int iAbs = Math.abs((recyclerView.getChildLayoutPosition(childAt) - i) * childAt.getHeight());
        if (iAbs == 0) {
            iAbs = (int) Math.abs(childAt.getY());
        }
        SmoothScroller smoothScroller = new SmoothScroller(recyclerView.getContext(), iAbs, this.duration);
        smoothScroller.setTargetPosition(i);
        startSmoothScroll(smoothScroller);
    }

    private class SmoothScroller extends LinearSmoothScroller {
        private static final int TARGET_SEEK_SCROLL_DISTANCE_PX = 10000;
        private final float distanceInPixels;
        private final float duration;

        public SmoothScroller(Context context, int i, int i2) {
            super(context);
            this.distanceInPixels = i;
            this.duration = i < 10000 ? (int) (Math.abs(i) * calculateSpeedPerPixel(context.getResources().getDisplayMetrics())) : i2;
        }

        @Override // androidx.recyclerview.widget.RecyclerView.SmoothScroller
        public PointF computeScrollVectorForPosition(int i) {
            return ScrollSmoothLineaerLayoutManager.this.computeScrollVectorForPosition(i);
        }

        @Override // androidx.recyclerview.widget.LinearSmoothScroller
        protected int calculateTimeForScrolling(int i) {
            return (int) (this.duration * (i / this.distanceInPixels));
        }
    }
}
