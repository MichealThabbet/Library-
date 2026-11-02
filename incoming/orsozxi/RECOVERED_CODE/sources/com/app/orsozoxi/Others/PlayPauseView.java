package com.app.orsozoxi.Others;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Outline;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Property;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewOutlineProvider;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import com.app.orsozoxi.R;

/* loaded from: classes.dex */
public class PlayPauseView extends ImageView {
    private static final Property<PlayPauseView, Integer> COLOR = new Property<PlayPauseView, Integer>(Integer.class, "color") { // from class: com.app.orsozoxi.Others.PlayPauseView.1
        @Override // android.util.Property
        public Integer get(PlayPauseView playPauseView) {
            return Integer.valueOf(playPauseView.getColor());
        }

        @Override // android.util.Property
        public void set(PlayPauseView playPauseView, Integer num) {
            playPauseView.setColor(num.intValue());
        }
    };
    private static final long PLAY_PAUSE_ANIMATION_DURATION = 200;
    public boolean isDrawCircle;
    private AnimatorSet mAnimatorSet;
    private int mBackgroundColor;
    private final PlayPauseDrawable mDrawable;
    private int mHeight;
    private boolean mIsPlay;
    private final Paint mPaint;
    private final int mPauseBackgroundColor;
    private final int mPlayBackgroundColor;
    private int mWidth;

    public PlayPauseView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mPaint = new Paint();
        this.isDrawCircle = true;
        setWillNotDraw(false);
        context.getTheme().resolveAttribute(R.attr.colorAccent, new TypedValue(), true);
        this.mBackgroundColor = Color.parseColor("#EE7429");
        this.mPaint.setAntiAlias(true);
        this.mPaint.setStyle(Paint.Style.FILL);
        PlayPauseDrawable playPauseDrawable = new PlayPauseDrawable(context);
        this.mDrawable = playPauseDrawable;
        playPauseDrawable.setCallback(this);
        this.mPauseBackgroundColor = Color.parseColor("#EE7429");
        this.mPlayBackgroundColor = Color.parseColor("#EE7429");
        this.isDrawCircle = context.obtainStyledAttributes(attributeSet, R.styleable.PlayPause).getBoolean(0, this.isDrawCircle);
    }

    @Override // android.widget.ImageView, android.view.View
    protected void onMeasure(int i, int i2) {
        super.onMeasure(i, i2);
    }

    @Override // android.view.View
    protected void onSizeChanged(int i, int i2, int i3, int i4) {
        super.onSizeChanged(i, i2, i3, i4);
        this.mDrawable.setBounds(0, 0, i, i2);
        this.mWidth = i;
        this.mHeight = i2;
        if (Build.VERSION.SDK_INT >= 21) {
            setOutlineProvider(new ViewOutlineProvider() { // from class: com.app.orsozoxi.Others.PlayPauseView.2
                @Override // android.view.ViewOutlineProvider
                public void getOutline(View view, Outline outline) {
                    outline.setOval(0, 0, view.getWidth(), view.getHeight());
                }
            });
            setClipToOutline(true);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setColor(int i) {
        this.mBackgroundColor = i;
        invalidate();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int getColor() {
        return this.mBackgroundColor;
    }

    @Override // android.widget.ImageView, android.view.View
    protected boolean verifyDrawable(Drawable drawable) {
        return drawable == this.mDrawable || super.verifyDrawable(drawable);
    }

    @Override // android.widget.ImageView, android.view.View
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        this.mPaint.setColor(this.mBackgroundColor);
        float fMin = Math.min(this.mWidth, this.mHeight) / 2.0f;
        if (this.isDrawCircle) {
            canvas.drawCircle(this.mWidth / 2.0f, this.mHeight / 2.0f, fMin, this.mPaint);
        }
        this.mDrawable.draw(canvas);
    }

    public boolean isPlay() {
        return this.mIsPlay;
    }

    public void Play() {
        AnimatorSet animatorSet = this.mAnimatorSet;
        if (animatorSet != null) {
            animatorSet.cancel();
        }
        this.mAnimatorSet = new AnimatorSet();
        ObjectAnimator objectAnimatorOfInt = ObjectAnimator.ofInt(this, COLOR, this.mPlayBackgroundColor);
        this.mIsPlay = true;
        objectAnimatorOfInt.setEvaluator(new ArgbEvaluator());
        this.mDrawable.setmIsPlay(this.mIsPlay);
        Animator pausePlayAnimator = this.mDrawable.getPausePlayAnimator();
        this.mAnimatorSet.setInterpolator(new DecelerateInterpolator());
        this.mAnimatorSet.setDuration(PLAY_PAUSE_ANIMATION_DURATION);
        this.mAnimatorSet.playTogether(objectAnimatorOfInt, pausePlayAnimator);
        this.mAnimatorSet.start();
    }

    public void Pause() {
        AnimatorSet animatorSet = this.mAnimatorSet;
        if (animatorSet != null) {
            animatorSet.cancel();
        }
        this.mAnimatorSet = new AnimatorSet();
        ObjectAnimator objectAnimatorOfInt = ObjectAnimator.ofInt(this, COLOR, this.mPauseBackgroundColor);
        this.mIsPlay = false;
        objectAnimatorOfInt.setEvaluator(new ArgbEvaluator());
        this.mDrawable.setmIsPlay(this.mIsPlay);
        Animator pausePlayAnimator = this.mDrawable.getPausePlayAnimator();
        this.mAnimatorSet.setInterpolator(new DecelerateInterpolator());
        this.mAnimatorSet.setDuration(PLAY_PAUSE_ANIMATION_DURATION);
        this.mAnimatorSet.playTogether(objectAnimatorOfInt, pausePlayAnimator);
        this.mAnimatorSet.start();
    }
}
