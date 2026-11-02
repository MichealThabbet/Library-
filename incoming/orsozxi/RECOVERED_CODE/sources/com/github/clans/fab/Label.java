package com.github.clans.fab;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Outline;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.graphics.Xfermode;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.RippleDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.StateListDrawable;
import android.graphics.drawable.shapes.RoundRectShape;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewOutlineProvider;
import android.view.animation.Animation;
import android.widget.TextView;

/* loaded from: classes.dex */
public class Label extends TextView {
    private static final Xfermode PORTER_DUFF_CLEAR = new PorterDuffXfermode(PorterDuff.Mode.CLEAR);
    private Drawable mBackgroundDrawable;
    private int mColorNormal;
    private int mColorPressed;
    private int mColorRipple;
    private int mCornerRadius;
    private FloatingActionButton mFab;
    GestureDetector mGestureDetector;
    private boolean mHandleVisibilityChanges;
    private Animation mHideAnimation;
    private int mRawHeight;
    private int mRawWidth;
    private int mShadowColor;
    private int mShadowRadius;
    private int mShadowXOffset;
    private int mShadowYOffset;
    private Animation mShowAnimation;
    private boolean mShowShadow;
    private boolean mUsingStyle;

    public Label(Context context) {
        super(context);
        this.mShowShadow = true;
        this.mHandleVisibilityChanges = true;
        this.mGestureDetector = new GestureDetector(getContext(), new GestureDetector.SimpleOnGestureListener() { // from class: com.github.clans.fab.Label.2
            @Override // android.view.GestureDetector.SimpleOnGestureListener, android.view.GestureDetector.OnGestureListener
            public boolean onDown(MotionEvent motionEvent) {
                Label.this.onActionDown();
                if (Label.this.mFab != null) {
                    Label.this.mFab.onActionDown();
                }
                return super.onDown(motionEvent);
            }

            @Override // android.view.GestureDetector.SimpleOnGestureListener, android.view.GestureDetector.OnGestureListener
            public boolean onSingleTapUp(MotionEvent motionEvent) {
                Label.this.onActionUp();
                if (Label.this.mFab != null) {
                    Label.this.mFab.onActionUp();
                }
                return super.onSingleTapUp(motionEvent);
            }
        });
    }

    public Label(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mShowShadow = true;
        this.mHandleVisibilityChanges = true;
        this.mGestureDetector = new GestureDetector(getContext(), new GestureDetector.SimpleOnGestureListener() { // from class: com.github.clans.fab.Label.2
            @Override // android.view.GestureDetector.SimpleOnGestureListener, android.view.GestureDetector.OnGestureListener
            public boolean onDown(MotionEvent motionEvent) {
                Label.this.onActionDown();
                if (Label.this.mFab != null) {
                    Label.this.mFab.onActionDown();
                }
                return super.onDown(motionEvent);
            }

            @Override // android.view.GestureDetector.SimpleOnGestureListener, android.view.GestureDetector.OnGestureListener
            public boolean onSingleTapUp(MotionEvent motionEvent) {
                Label.this.onActionUp();
                if (Label.this.mFab != null) {
                    Label.this.mFab.onActionUp();
                }
                return super.onSingleTapUp(motionEvent);
            }
        });
    }

    public Label(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mShowShadow = true;
        this.mHandleVisibilityChanges = true;
        this.mGestureDetector = new GestureDetector(getContext(), new GestureDetector.SimpleOnGestureListener() { // from class: com.github.clans.fab.Label.2
            @Override // android.view.GestureDetector.SimpleOnGestureListener, android.view.GestureDetector.OnGestureListener
            public boolean onDown(MotionEvent motionEvent) {
                Label.this.onActionDown();
                if (Label.this.mFab != null) {
                    Label.this.mFab.onActionDown();
                }
                return super.onDown(motionEvent);
            }

            @Override // android.view.GestureDetector.SimpleOnGestureListener, android.view.GestureDetector.OnGestureListener
            public boolean onSingleTapUp(MotionEvent motionEvent) {
                Label.this.onActionUp();
                if (Label.this.mFab != null) {
                    Label.this.mFab.onActionUp();
                }
                return super.onSingleTapUp(motionEvent);
            }
        });
    }

    @Override // android.widget.TextView, android.view.View
    protected void onMeasure(int i, int i2) {
        super.onMeasure(i, i2);
        setMeasuredDimension(calculateMeasuredWidth(), calculateMeasuredHeight());
    }

    private int calculateMeasuredWidth() {
        if (this.mRawWidth == 0) {
            this.mRawWidth = getMeasuredWidth();
        }
        return getMeasuredWidth() + calculateShadowWidth();
    }

    private int calculateMeasuredHeight() {
        if (this.mRawHeight == 0) {
            this.mRawHeight = getMeasuredHeight();
        }
        return getMeasuredHeight() + calculateShadowHeight();
    }

    int calculateShadowWidth() {
        if (this.mShowShadow) {
            return this.mShadowRadius + Math.abs(this.mShadowXOffset);
        }
        return 0;
    }

    int calculateShadowHeight() {
        if (this.mShowShadow) {
            return this.mShadowRadius + Math.abs(this.mShadowYOffset);
        }
        return 0;
    }

    void updateBackground() {
        LayerDrawable layerDrawable;
        if (this.mShowShadow) {
            layerDrawable = new LayerDrawable(new Drawable[]{new Shadow(), createFillDrawable()});
            layerDrawable.setLayerInset(1, this.mShadowRadius + Math.abs(this.mShadowXOffset), this.mShadowRadius + Math.abs(this.mShadowYOffset), this.mShadowRadius + Math.abs(this.mShadowXOffset), this.mShadowRadius + Math.abs(this.mShadowYOffset));
        } else {
            layerDrawable = new LayerDrawable(new Drawable[]{createFillDrawable()});
        }
        setBackgroundCompat(layerDrawable);
    }

    private Drawable createFillDrawable() {
        StateListDrawable stateListDrawable = new StateListDrawable();
        stateListDrawable.addState(new int[]{android.R.attr.state_pressed}, createRectDrawable(this.mColorPressed));
        stateListDrawable.addState(new int[0], createRectDrawable(this.mColorNormal));
        if (Util.hasLollipop()) {
            RippleDrawable rippleDrawable = new RippleDrawable(new ColorStateList(new int[][]{new int[0]}, new int[]{this.mColorRipple}), stateListDrawable, null);
            setOutlineProvider(new ViewOutlineProvider() { // from class: com.github.clans.fab.Label.1
                @Override // android.view.ViewOutlineProvider
                public void getOutline(View view, Outline outline) {
                    outline.setOval(0, 0, view.getWidth(), view.getHeight());
                }
            });
            setClipToOutline(true);
            this.mBackgroundDrawable = rippleDrawable;
            return rippleDrawable;
        }
        this.mBackgroundDrawable = stateListDrawable;
        return stateListDrawable;
    }

    private Drawable createRectDrawable(int i) {
        int i2 = this.mCornerRadius;
        ShapeDrawable shapeDrawable = new ShapeDrawable(new RoundRectShape(new float[]{i2, i2, i2, i2, i2, i2, i2, i2}, null, null));
        shapeDrawable.getPaint().setColor(i);
        return shapeDrawable;
    }

    private void setShadow(FloatingActionButton floatingActionButton) {
        this.mShadowColor = floatingActionButton.getShadowColor();
        this.mShadowRadius = floatingActionButton.getShadowRadius();
        this.mShadowXOffset = floatingActionButton.getShadowXOffset();
        this.mShadowYOffset = floatingActionButton.getShadowYOffset();
        this.mShowShadow = floatingActionButton.hasShadow();
    }

    private void setBackgroundCompat(Drawable drawable) {
        if (Util.hasJellyBean()) {
            setBackground(drawable);
        } else {
            setBackgroundDrawable(drawable);
        }
    }

    private void playShowAnimation() {
        if (this.mShowAnimation != null) {
            this.mHideAnimation.cancel();
            startAnimation(this.mShowAnimation);
        }
    }

    private void playHideAnimation() {
        if (this.mHideAnimation != null) {
            this.mShowAnimation.cancel();
            startAnimation(this.mHideAnimation);
        }
    }

    void onActionDown() {
        if (this.mUsingStyle) {
            this.mBackgroundDrawable = getBackground();
        }
        Drawable drawable = this.mBackgroundDrawable;
        if (drawable instanceof StateListDrawable) {
            ((StateListDrawable) drawable).setState(new int[]{android.R.attr.state_pressed});
            return;
        }
        if (Util.hasLollipop()) {
            Drawable drawable2 = this.mBackgroundDrawable;
            if (drawable2 instanceof RippleDrawable) {
                RippleDrawable rippleDrawable = (RippleDrawable) drawable2;
                rippleDrawable.setState(new int[]{android.R.attr.state_enabled, android.R.attr.state_pressed});
                rippleDrawable.setHotspot(getMeasuredWidth() / 2, getMeasuredHeight() / 2);
                rippleDrawable.setVisible(true, true);
            }
        }
    }

    void onActionUp() {
        if (this.mUsingStyle) {
            this.mBackgroundDrawable = getBackground();
        }
        Drawable drawable = this.mBackgroundDrawable;
        if (drawable instanceof StateListDrawable) {
            ((StateListDrawable) drawable).setState(new int[0]);
            return;
        }
        if (Util.hasLollipop()) {
            Drawable drawable2 = this.mBackgroundDrawable;
            if (drawable2 instanceof RippleDrawable) {
                RippleDrawable rippleDrawable = (RippleDrawable) drawable2;
                rippleDrawable.setState(new int[0]);
                rippleDrawable.setHotspot(getMeasuredWidth() / 2, getMeasuredHeight() / 2);
                rippleDrawable.setVisible(true, true);
            }
        }
    }

    void setFab(FloatingActionButton floatingActionButton) {
        this.mFab = floatingActionButton;
        setShadow(floatingActionButton);
    }

    void setShowShadow(boolean z) {
        this.mShowShadow = z;
    }

    void setCornerRadius(int i) {
        this.mCornerRadius = i;
    }

    void setColors(int i, int i2, int i3) {
        this.mColorNormal = i;
        this.mColorPressed = i2;
        this.mColorRipple = i3;
    }

    void show(boolean z) {
        if (z) {
            playShowAnimation();
        }
        setVisibility(0);
    }

    void hide(boolean z) {
        if (z) {
            playHideAnimation();
        }
        setVisibility(4);
    }

    void setShowAnimation(Animation animation) {
        this.mShowAnimation = animation;
    }

    void setHideAnimation(Animation animation) {
        this.mHideAnimation = animation;
    }

    void setUsingStyle(boolean z) {
        this.mUsingStyle = z;
    }

    void setHandleVisibilityChanges(boolean z) {
        this.mHandleVisibilityChanges = z;
    }

    boolean isHandleVisibilityChanges() {
        return this.mHandleVisibilityChanges;
    }

    @Override // android.widget.TextView, android.view.View
    public boolean onTouchEvent(MotionEvent motionEvent) {
        FloatingActionButton floatingActionButton = this.mFab;
        if (floatingActionButton == null || floatingActionButton.getOnClickListener() == null || !this.mFab.isEnabled()) {
            return super.onTouchEvent(motionEvent);
        }
        int action = motionEvent.getAction();
        if (action == 1 || action == 3) {
            onActionUp();
            this.mFab.onActionUp();
        }
        this.mGestureDetector.onTouchEvent(motionEvent);
        return super.onTouchEvent(motionEvent);
    }

    private class Shadow extends Drawable {
        private Paint mErase;
        private Paint mPaint;

        @Override // android.graphics.drawable.Drawable
        public int getOpacity() {
            return 0;
        }

        @Override // android.graphics.drawable.Drawable
        public void setAlpha(int i) {
        }

        @Override // android.graphics.drawable.Drawable
        public void setColorFilter(ColorFilter colorFilter) {
        }

        private Shadow() {
            this.mPaint = new Paint(1);
            this.mErase = new Paint(1);
            init();
        }

        private void init() {
            Label.this.setLayerType(1, null);
            this.mPaint.setStyle(Paint.Style.FILL);
            this.mPaint.setColor(Label.this.mColorNormal);
            this.mErase.setXfermode(Label.PORTER_DUFF_CLEAR);
            if (Label.this.isInEditMode()) {
                return;
            }
            this.mPaint.setShadowLayer(Label.this.mShadowRadius, Label.this.mShadowXOffset, Label.this.mShadowYOffset, Label.this.mShadowColor);
        }

        @Override // android.graphics.drawable.Drawable
        public void draw(Canvas canvas) {
            RectF rectF = new RectF(Label.this.mShadowRadius + Math.abs(Label.this.mShadowXOffset), Label.this.mShadowRadius + Math.abs(Label.this.mShadowYOffset), Label.this.mRawWidth, Label.this.mRawHeight);
            canvas.drawRoundRect(rectF, Label.this.mCornerRadius, Label.this.mCornerRadius, this.mPaint);
            canvas.drawRoundRect(rectF, Label.this.mCornerRadius, Label.this.mCornerRadius, this.mErase);
        }
    }
}
