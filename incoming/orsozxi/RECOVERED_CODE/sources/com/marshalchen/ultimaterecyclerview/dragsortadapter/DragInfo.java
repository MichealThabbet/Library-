package com.marshalchen.ultimaterecyclerview.dragsortadapter;

import android.graphics.Point;
import android.graphics.PointF;

/* loaded from: classes2.dex */
public class DragInfo {
    private final PointF dragPoint;
    private final long itemId;
    private final Point shadowSize;
    private final Point shadowTouchPoint;

    public DragInfo(long j, Point point, Point point2, PointF pointF) {
        this.itemId = j;
        this.shadowSize = new Point(point);
        this.shadowTouchPoint = new Point(point2);
        this.dragPoint = pointF;
    }

    public long itemId() {
        return this.itemId;
    }

    public boolean shouldScrollLeft() {
        return this.dragPoint.x < ((float) this.shadowTouchPoint.x);
    }

    public boolean shouldScrollRight(int i) {
        return this.dragPoint.x > ((float) (i - (this.shadowSize.x - this.shadowTouchPoint.x)));
    }

    public boolean shouldScrollUp() {
        return this.dragPoint.y < ((float) this.shadowTouchPoint.y);
    }

    public boolean shouldScrollDown(int i) {
        return this.dragPoint.y > ((float) (i - (this.shadowSize.y - this.shadowTouchPoint.y)));
    }

    public void setDragPoint(float f, float f2) {
        this.dragPoint.set(f, f2);
    }
}
