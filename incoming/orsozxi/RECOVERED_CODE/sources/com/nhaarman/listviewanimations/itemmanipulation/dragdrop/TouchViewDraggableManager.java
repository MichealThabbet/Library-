package com.nhaarman.listviewanimations.itemmanipulation.dragdrop;

import android.view.View;

/* loaded from: classes2.dex */
public class TouchViewDraggableManager implements DraggableManager {
    private final int mTouchViewResId;

    public TouchViewDraggableManager(int i) {
        this.mTouchViewResId = i;
    }

    @Override // com.nhaarman.listviewanimations.itemmanipulation.dragdrop.DraggableManager
    public boolean isDraggable(View view, int i, float f, float f2) {
        View viewFindViewById = view.findViewById(this.mTouchViewResId);
        if (viewFindViewById != null) {
            return ((((float) viewFindViewById.getLeft()) > f ? 1 : (((float) viewFindViewById.getLeft()) == f ? 0 : -1)) <= 0 && (((float) viewFindViewById.getRight()) > f ? 1 : (((float) viewFindViewById.getRight()) == f ? 0 : -1)) >= 0) && ((((float) viewFindViewById.getTop()) > f2 ? 1 : (((float) viewFindViewById.getTop()) == f2 ? 0 : -1)) <= 0 && (((float) viewFindViewById.getBottom()) > f2 ? 1 : (((float) viewFindViewById.getBottom()) == f2 ? 0 : -1)) >= 0);
        }
        return false;
    }
}
