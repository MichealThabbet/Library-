package com.appeaser.sublimepickerlibrary.drawables;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.view.animation.AnticipateInterpolator;
import android.view.animation.OvershootInterpolator;

/* loaded from: classes.dex */
public class CheckableDrawable extends Drawable {
    private AnimatorSet asTransition;
    private boolean mChecked;
    private int mExpandedWidthHeight;
    private int mMaxAlpha;
    private Paint mPaint;
    private boolean mReady;
    private final int ANIMATION_DURATION_EXPAND = 500;
    private final int ANIMATION_DURATION_COLLAPSE = 400;
    private final OvershootInterpolator mExpandInterpolator = new OvershootInterpolator();
    private final AnticipateInterpolator mCollapseInterpolator = new AnticipateInterpolator();
    private final CRectFEvaluator mRectEvaluator = new CRectFEvaluator();
    private int mMinAlpha = 0;
    private RectF mRectToDraw = new RectF();
    private RectF mExpandedRect = new RectF();
    private RectF mCollapsedRect = new RectF();

    public interface OnAnimationDone {
        void animationHasBeenCancelled();

        void animationIsDone();
    }

    @Override // android.graphics.drawable.Drawable
    public int getOpacity() {
        return -3;
    }

    @Override // android.graphics.drawable.Drawable
    public void setColorFilter(ColorFilter colorFilter) {
    }

    public CheckableDrawable(int i, boolean z, int i2) {
        this.mChecked = z;
        this.mExpandedWidthHeight = i2;
        this.mMaxAlpha = Color.alpha(i);
        Paint paint = new Paint();
        this.mPaint = paint;
        paint.setColor(i);
        this.mPaint.setAlpha(this.mMaxAlpha);
        this.mPaint.setAntiAlias(true);
        this.mPaint.setStyle(Paint.Style.FILL);
    }

    private void setDimens(int i, int i2) {
        this.mReady = true;
        int i3 = this.mExpandedWidthHeight;
        float f = (i - i3) / 2.0f;
        float f2 = (i2 - i3) / 2.0f;
        float f3 = (i + i3) / 2.0f;
        float f4 = (i3 + i2) / 2.0f;
        float f5 = i / 2.0f;
        float f6 = i2 / 2.0f;
        this.mCollapsedRect = new RectF(f5, f6, f5, f6);
        this.mExpandedRect = new RectF(f, f2, f3, f4);
        reset();
    }

    public void setCheckedOnClick(boolean z, OnAnimationDone onAnimationDone) {
        this.mChecked = z;
        if (!this.mReady) {
            invalidateSelf();
        } else {
            reset();
            onClick(onAnimationDone);
        }
    }

    private void onClick(OnAnimationDone onAnimationDone) {
        animate(this.mChecked, onAnimationDone);
    }

    private void cancelAnimationInTracks() {
        AnimatorSet animatorSet = this.asTransition;
        if (animatorSet == null || !animatorSet.isRunning()) {
            return;
        }
        this.asTransition.cancel();
    }

    public void setChecked(boolean z) {
        if (this.mChecked == z) {
            return;
        }
        this.mChecked = z;
        reset();
    }

    private void reset() {
        cancelAnimationInTracks();
        if (this.mChecked) {
            this.mRectToDraw.set(this.mExpandedRect);
        } else {
            this.mRectToDraw.set(this.mCollapsedRect);
        }
        invalidateSelf();
    }

    private void animate(boolean z, final OnAnimationDone onAnimationDone) {
        RectF rectF = z ? this.mCollapsedRect : this.mExpandedRect;
        RectF rectF2 = z ? this.mExpandedRect : this.mCollapsedRect;
        this.mRectToDraw.set(rectF);
        ObjectAnimator objectAnimatorOfObject = ObjectAnimator.ofObject(this, "newRectBounds", this.mRectEvaluator, rectF, rectF2);
        long j = z ? 500 : 400;
        objectAnimatorOfObject.setDuration(j);
        objectAnimatorOfObject.setInterpolator(z ? this.mExpandInterpolator : this.mCollapseInterpolator);
        int[] iArr = new int[2];
        iArr[0] = z ? this.mMinAlpha : this.mMaxAlpha;
        iArr[1] = z ? this.mMaxAlpha : this.mMinAlpha;
        ObjectAnimator objectAnimatorOfInt = ObjectAnimator.ofInt(this, "alpha", iArr);
        objectAnimatorOfInt.setDuration(j);
        AnimatorSet animatorSet = new AnimatorSet();
        this.asTransition = animatorSet;
        animatorSet.playTogether(objectAnimatorOfObject, objectAnimatorOfInt);
        this.asTransition.addListener(new AnimatorListenerAdapter() { // from class: com.appeaser.sublimepickerlibrary.drawables.CheckableDrawable.1
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animator) {
                super.onAnimationEnd(animator);
                OnAnimationDone onAnimationDone2 = onAnimationDone;
                if (onAnimationDone2 != null) {
                    onAnimationDone2.animationIsDone();
                }
            }

            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationCancel(Animator animator) {
                super.onAnimationCancel(animator);
                OnAnimationDone onAnimationDone2 = onAnimationDone;
                if (onAnimationDone2 != null) {
                    onAnimationDone2.animationHasBeenCancelled();
                }
            }
        });
        this.asTransition.start();
    }

    @Override // android.graphics.drawable.Drawable
    public void draw(Canvas canvas) {
        if (!this.mReady) {
            setDimens(getBounds().width(), getBounds().height());
        } else {
            canvas.drawOval(this.mRectToDraw, this.mPaint);
        }
    }

    @Override // android.graphics.drawable.Drawable
    public void setAlpha(int i) {
        this.mPaint.setAlpha(i);
    }

    public void setNewRectBounds(RectF rectF) {
        this.mRectToDraw = rectF;
        invalidateSelf();
    }
}
