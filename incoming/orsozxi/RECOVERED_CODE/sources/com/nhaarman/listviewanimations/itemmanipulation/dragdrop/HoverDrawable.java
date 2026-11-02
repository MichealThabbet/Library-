package com.nhaarman.listviewanimations.itemmanipulation.dragdrop;

import android.graphics.drawable.BitmapDrawable;
import android.view.MotionEvent;
import android.view.View;

/* loaded from: classes2.dex */
class HoverDrawable extends BitmapDrawable {
    private float mDownY;
    private float mOriginalY;
    private float mScrollDistance;

    HoverDrawable(View view, MotionEvent motionEvent) {
        this(view, motionEvent.getY());
    }

    HoverDrawable(View view, float f) {
        super(view.getResources(), BitmapUtils.getBitmapFromView(view));
        this.mOriginalY = view.getTop();
        this.mDownY = f;
        setBounds(view.getLeft(), view.getTop(), view.getRight(), view.getBottom());
    }

    void handleMoveEvent(MotionEvent motionEvent) {
        setTop((int) ((this.mOriginalY - this.mDownY) + motionEvent.getY() + this.mScrollDistance));
    }

    void onScroll(float f) {
        this.mScrollDistance += this.mOriginalY - f;
        this.mOriginalY = f;
    }

    boolean isMovingUpwards() {
        return this.mOriginalY > ((float) getBounds().top);
    }

    int getDeltaY() {
        return (int) (getBounds().top - this.mOriginalY);
    }

    int getTop() {
        return getBounds().top;
    }

    void setTop(int i) {
        setBounds(getBounds().left, i, getBounds().left + getIntrinsicWidth(), getIntrinsicHeight() + i);
    }

    void shift(int i) {
        if (isMovingUpwards()) {
            i = -i;
        }
        float f = i;
        this.mOriginalY += f;
        this.mDownY += f;
    }
}
