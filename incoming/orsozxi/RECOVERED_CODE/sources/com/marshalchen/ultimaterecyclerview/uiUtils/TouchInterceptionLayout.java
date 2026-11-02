package com.marshalchen.ultimaterecyclerview.uiUtils;

import android.content.Context;
import android.graphics.PointF;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;

/* loaded from: classes2.dex */
public class TouchInterceptionLayout extends FrameLayout {
    private boolean mBeganFromDownMotionEvent;
    private boolean mChildrenEventsCanceled;
    private boolean mDownMotionEventPended;
    private PointF mInitialPoint;
    private boolean mIntercepting;
    private MotionEvent mPendingDownMotionEvent;
    private TouchInterceptionListener mTouchInterceptionListener;

    public interface TouchInterceptionListener {
        void onDownMotionEvent(MotionEvent motionEvent);

        void onMoveMotionEvent(MotionEvent motionEvent, float f, float f2);

        void onUpOrCancelMotionEvent(MotionEvent motionEvent);

        boolean shouldInterceptTouchEvent(MotionEvent motionEvent, boolean z, float f, float f2);
    }

    public TouchInterceptionLayout(Context context) {
        super(context);
    }

    public TouchInterceptionLayout(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public TouchInterceptionLayout(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }

    public TouchInterceptionLayout(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
    }

    public void setScrollInterceptionListener(TouchInterceptionListener touchInterceptionListener) {
        this.mTouchInterceptionListener = touchInterceptionListener;
    }

    @Override // android.view.ViewGroup
    public boolean onInterceptTouchEvent(MotionEvent motionEvent) {
        if (this.mTouchInterceptionListener == null) {
            return false;
        }
        int actionMasked = motionEvent.getActionMasked();
        if (actionMasked == 0) {
            this.mInitialPoint = new PointF(motionEvent.getX(), motionEvent.getY());
            this.mPendingDownMotionEvent = MotionEvent.obtainNoHistory(motionEvent);
            this.mDownMotionEventPended = true;
            boolean zShouldInterceptTouchEvent = this.mTouchInterceptionListener.shouldInterceptTouchEvent(motionEvent, false, 0.0f, 0.0f);
            this.mIntercepting = zShouldInterceptTouchEvent;
            this.mBeganFromDownMotionEvent = zShouldInterceptTouchEvent;
            this.mChildrenEventsCanceled = false;
            return zShouldInterceptTouchEvent;
        }
        if (actionMasked != 2) {
            return false;
        }
        if (this.mInitialPoint == null) {
            this.mInitialPoint = new PointF(motionEvent.getX(), motionEvent.getY());
        }
        boolean zShouldInterceptTouchEvent2 = this.mTouchInterceptionListener.shouldInterceptTouchEvent(motionEvent, true, motionEvent.getX() - this.mInitialPoint.x, motionEvent.getY() - this.mInitialPoint.y);
        this.mIntercepting = zShouldInterceptTouchEvent2;
        return zShouldInterceptTouchEvent2;
    }

    /* JADX WARN: Code restructure failed: missing block: B:9:0x0012, code lost:
    
        if (r0 != 3) goto L43;
     */
    @Override // android.view.View
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public boolean onTouchEvent(android.view.MotionEvent r7) {
        /*
            Method dump skipped, instructions count: 252
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.marshalchen.ultimaterecyclerview.uiUtils.TouchInterceptionLayout.onTouchEvent(android.view.MotionEvent):boolean");
    }

    private MotionEvent obtainMotionEvent(MotionEvent motionEvent, int i) {
        MotionEvent motionEventObtainNoHistory = MotionEvent.obtainNoHistory(motionEvent);
        motionEventObtainNoHistory.setAction(i);
        return motionEventObtainNoHistory;
    }

    private void duplicateTouchEventForChildren(MotionEvent motionEvent, MotionEvent... motionEventArr) {
        if (motionEvent == null) {
            return;
        }
        for (int childCount = getChildCount() - 1; childCount >= 0; childCount--) {
            View childAt = getChildAt(childCount);
            if (childAt != null) {
                Rect rect = new Rect();
                childAt.getHitRect(rect);
                MotionEvent motionEventObtainNoHistory = MotionEvent.obtainNoHistory(motionEvent);
                if (rect.contains((int) motionEventObtainNoHistory.getX(), (int) motionEventObtainNoHistory.getY())) {
                    float f = -childAt.getLeft();
                    float f2 = -childAt.getTop();
                    boolean z = false;
                    if (motionEventArr != null) {
                        boolean zDispatchTouchEvent = false;
                        for (MotionEvent motionEvent2 : motionEventArr) {
                            if (motionEvent2 != null) {
                                MotionEvent motionEventObtainNoHistory2 = MotionEvent.obtainNoHistory(motionEvent2);
                                motionEventObtainNoHistory2.offsetLocation(f, f2);
                                zDispatchTouchEvent |= childAt.dispatchTouchEvent(motionEventObtainNoHistory2);
                            }
                        }
                        z = zDispatchTouchEvent;
                    }
                    motionEventObtainNoHistory.offsetLocation(f, f2);
                    if ((childAt.dispatchTouchEvent(motionEventObtainNoHistory) | z) != 0) {
                        return;
                    }
                } else {
                    continue;
                }
            }
        }
    }
}
