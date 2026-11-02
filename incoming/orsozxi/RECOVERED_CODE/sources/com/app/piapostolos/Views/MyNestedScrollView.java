package com.app.piapostolos.Views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewConfiguration;
import androidx.core.widget.NestedScrollView;

/* loaded from: classes.dex */
public class MyNestedScrollView extends NestedScrollView {
    private float lastX;
    private float lastY;
    private float mInitialMotionX;
    private float mInitialMotionY;
    private int slop;
    private float xDistance;
    private float yDistance;

    public MyNestedScrollView(Context context) {
        super(context);
        init(context);
    }

    private void init(Context context) {
        this.slop = ViewConfiguration.get(context).getScaledEdgeSlop();
    }

    public MyNestedScrollView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        init(context);
    }

    public MyNestedScrollView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        init(context);
    }

    @Override // androidx.core.widget.NestedScrollView, android.view.ViewGroup
    public boolean onInterceptTouchEvent(MotionEvent motionEvent) {
        motionEvent.getX();
        motionEvent.getY();
        int action = motionEvent.getAction();
        if (action == 0) {
            this.yDistance = 0.0f;
            this.xDistance = 0.0f;
            this.lastX = motionEvent.getX();
            this.lastY = motionEvent.getY();
        } else if (action == 2) {
            float x = motionEvent.getX();
            float y = motionEvent.getY();
            this.xDistance += Math.abs(x - this.lastX);
            float fAbs = this.yDistance + Math.abs(y - this.lastY);
            this.yDistance = fAbs;
            this.lastX = x;
            this.lastY = y;
            if (this.xDistance > fAbs) {
                return false;
            }
        }
        return super.onInterceptTouchEvent(motionEvent) || motionEvent.getPointerCount() == 2;
    }
}
