package it.feio.android.checklistview.dragging;

import android.graphics.Point;
import android.view.View;

/* loaded from: classes2.dex */
class ChecklistViewDragShadowBuilder extends View.DragShadowBuilder {
    ChecklistViewDragShadowBuilder(View view) {
        super(view);
    }

    @Override // android.view.View.DragShadowBuilder
    public void onProvideShadowMetrics(Point point, Point point2) {
        point.set(getView().getWidth(), getView().getHeight());
        point2.set(10, 20);
    }
}
