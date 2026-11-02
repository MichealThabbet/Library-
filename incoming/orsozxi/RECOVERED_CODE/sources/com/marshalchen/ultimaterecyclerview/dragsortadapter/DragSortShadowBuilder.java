package com.marshalchen.ultimaterecyclerview.dragsortadapter;

import android.graphics.Canvas;
import android.graphics.Point;
import android.util.Log;
import android.view.View;

/* loaded from: classes2.dex */
public class DragSortShadowBuilder extends View.DragShadowBuilder {
    public static final String TAG = DragSortShadowBuilder.class.getSimpleName();
    final Point touchPoint;

    public DragSortShadowBuilder(View view, Point point) {
        super(view);
        Point point2 = new Point();
        this.touchPoint = point2;
        point2.set(point.x, point.y);
    }

    @Override // android.view.View.DragShadowBuilder
    public void onProvideShadowMetrics(Point point, Point point2) {
        View view = getView();
        if (view != null) {
            point.set(view.getWidth(), view.getHeight());
            point2.set(this.touchPoint.x, this.touchPoint.y);
        } else {
            Log.e(TAG, "Asked for drag thumb metrics but no view");
        }
    }

    @Override // android.view.View.DragShadowBuilder
    public void onDrawShadow(Canvas canvas) {
        super.onDrawShadow(canvas);
    }
}
