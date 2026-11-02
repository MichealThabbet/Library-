package com.app.orsozoxi.Alarm.Bubbles;

import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import com.app.orsozoxi.R;

/* loaded from: classes.dex */
public class BubbleLayout extends BubbleBaseLayout {
    private float initialTouchX;
    private float initialTouchY;
    private int initialX;
    private int initialY;
    private OnBubbleRemoveListener onBubbleRemoveListener;

    public interface OnBubbleRemoveListener {
        void onBubbleRemoved(BubbleLayout bubbleLayout);
    }

    public void setOnBubbleRemoveListener(OnBubbleRemoveListener onBubbleRemoveListener) {
        this.onBubbleRemoveListener = onBubbleRemoveListener;
    }

    public BubbleLayout(Context context) {
        super(context);
        initializeView();
    }

    public BubbleLayout(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        initializeView();
    }

    public BubbleLayout(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        initializeView();
    }

    public void notifyBubbleRemoved() {
        OnBubbleRemoveListener onBubbleRemoveListener = this.onBubbleRemoveListener;
        if (onBubbleRemoveListener != null) {
            onBubbleRemoveListener.onBubbleRemoved(this);
        }
    }

    private void initializeView() {
        setClickable(true);
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        playAnimation();
    }

    @Override // android.view.View
    public boolean onTouchEvent(MotionEvent motionEvent) {
        if (motionEvent != null) {
            int action = motionEvent.getAction();
            if (action == 0) {
                this.initialX = getViewParams().x;
                this.initialY = getViewParams().y;
                this.initialTouchX = motionEvent.getRawX();
                this.initialTouchY = motionEvent.getRawY();
            } else if (action != 1) {
                if (action == 2) {
                    int rawX = this.initialX + ((int) (motionEvent.getRawX() - this.initialTouchX));
                    int rawY = this.initialY + ((int) (motionEvent.getRawY() - this.initialTouchY));
                    getViewParams().x = rawX;
                    getViewParams().y = rawY;
                    getWindowManager().updateViewLayout(this, getViewParams());
                    if (getLayoutCoordinator() != null) {
                        getLayoutCoordinator().HideScroll(this);
                        getLayoutCoordinator().notifyBubblePositionChanged(this, rawX, rawY);
                    }
                }
            } else if (getLayoutCoordinator() != null) {
                getLayoutCoordinator().notifyBubbleRelease(this);
            }
        }
        return super.onTouchEvent(motionEvent);
    }

    private void playAnimation() {
        if (isInEditMode()) {
            return;
        }
        AnimatorSet animatorSet = (AnimatorSet) AnimatorInflater.loadAnimator(getContext(), R.animator.bubble_shown_animator);
        animatorSet.setTarget(this);
        animatorSet.start();
    }
}
