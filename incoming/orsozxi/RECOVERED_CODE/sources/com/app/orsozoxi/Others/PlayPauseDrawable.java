package com.app.orsozoxi.Others;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.util.Property;
import androidx.core.app.NotificationCompat;

/* loaded from: classes.dex */
public class PlayPauseDrawable extends Drawable {
    private static final Property<PlayPauseDrawable, Float> PROGRESS = new Property<PlayPauseDrawable, Float>(Float.class, NotificationCompat.CATEGORY_PROGRESS) { // from class: com.app.orsozoxi.Others.PlayPauseDrawable.1
        @Override // android.util.Property
        public Float get(PlayPauseDrawable playPauseDrawable) {
            return Float.valueOf(playPauseDrawable.getProgress());
        }

        @Override // android.util.Property
        public void set(PlayPauseDrawable playPauseDrawable, Float f) {
            playPauseDrawable.setProgress(f.floatValue());
        }
    };
    private float mHeight;
    private boolean mIsPlay;
    private float mPauseBarDistance;
    private float mPauseBarHeight;
    private float mPauseBarWidth;
    private float mProgress;
    private float mWidth;
    private final Path mLeftPauseBar = new Path();
    private final Path mRightPauseBar = new Path();
    private final Paint mPaint = new Paint();
    private final RectF mBounds = new RectF();

    private static float lerp(float f, float f2, float f3) {
        return f + ((f2 - f) * f3);
    }

    @Override // android.graphics.drawable.Drawable
    public int getOpacity() {
        return -3;
    }

    public void setmIsPlay(boolean z) {
        this.mIsPlay = z;
    }

    public PlayPauseDrawable(Context context) {
        context.getResources();
        this.mPaint.setAntiAlias(true);
        this.mPaint.setStyle(Paint.Style.FILL);
        this.mPaint.setColor(-1);
    }

    @Override // android.graphics.drawable.Drawable
    protected void onBoundsChange(Rect rect) {
        super.onBoundsChange(rect);
        this.mBounds.set(rect);
        this.mWidth = this.mBounds.width();
        float fHeight = this.mBounds.height();
        this.mHeight = fHeight;
        float f = this.mWidth / 10.0f;
        this.mPauseBarWidth = f;
        this.mPauseBarHeight = fHeight * 0.3f;
        this.mPauseBarDistance = f;
    }

    @Override // android.graphics.drawable.Drawable
    public void draw(Canvas canvas) {
        this.mLeftPauseBar.rewind();
        this.mRightPauseBar.rewind();
        float fLerp = lerp(this.mPauseBarDistance, 0.0f, this.mProgress);
        float fLerp2 = lerp(this.mPauseBarWidth, this.mPauseBarHeight / 2.0f, this.mProgress);
        float fLerp3 = lerp(0.0f, fLerp2, this.mProgress);
        float f = (fLerp2 * 2.0f) + fLerp;
        float f2 = fLerp + fLerp2;
        float fLerp4 = lerp(f, f2, this.mProgress);
        this.mLeftPauseBar.moveTo(0.0f, 0.0f);
        this.mLeftPauseBar.lineTo(fLerp3, -this.mPauseBarHeight);
        this.mLeftPauseBar.lineTo(fLerp2, -this.mPauseBarHeight);
        this.mLeftPauseBar.lineTo(fLerp2, 0.0f);
        this.mLeftPauseBar.close();
        this.mRightPauseBar.moveTo(f2, 0.0f);
        this.mRightPauseBar.lineTo(f2, -this.mPauseBarHeight);
        this.mRightPauseBar.lineTo(fLerp4, -this.mPauseBarHeight);
        this.mRightPauseBar.lineTo(f, 0.0f);
        this.mRightPauseBar.close();
        canvas.save();
        canvas.translate(lerp(0.0f, this.mPauseBarHeight / 8.0f, this.mProgress), 0.0f);
        float f3 = this.mIsPlay ? 1.0f - this.mProgress : this.mProgress;
        float f4 = this.mIsPlay ? 90.0f : 0.0f;
        canvas.rotate(lerp(f4, 90.0f + f4, f3), this.mWidth / 2.0f, this.mHeight / 2.0f);
        canvas.translate((this.mWidth / 2.0f) - (f / 2.0f), (this.mHeight / 2.0f) + (this.mPauseBarHeight / 2.0f));
        canvas.drawPath(this.mLeftPauseBar, this.mPaint);
        canvas.drawPath(this.mRightPauseBar, this.mPaint);
        canvas.restore();
    }

    public Animator getPausePlayAnimator() {
        Property<PlayPauseDrawable, Float> property = PROGRESS;
        float[] fArr = new float[2];
        fArr[0] = this.mIsPlay ? 1.0f : 0.0f;
        fArr[1] = this.mIsPlay ? 0.0f : 1.0f;
        ObjectAnimator objectAnimatorOfFloat = ObjectAnimator.ofFloat(this, property, fArr);
        objectAnimatorOfFloat.addListener(new AnimatorListenerAdapter() { // from class: com.app.orsozoxi.Others.PlayPauseDrawable.2
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animator) {
                PlayPauseDrawable.this.mIsPlay = !r2.mIsPlay;
            }
        });
        return objectAnimatorOfFloat;
    }

    public boolean isPlay() {
        return this.mIsPlay;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setProgress(float f) {
        this.mProgress = f;
        invalidateSelf();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public float getProgress() {
        return this.mProgress;
    }

    @Override // android.graphics.drawable.Drawable
    public void setAlpha(int i) {
        this.mPaint.setAlpha(i);
        invalidateSelf();
    }

    @Override // android.graphics.drawable.Drawable
    public void setColorFilter(ColorFilter colorFilter) {
        this.mPaint.setColorFilter(colorFilter);
        invalidateSelf();
    }
}
