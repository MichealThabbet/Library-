package com.github.clans.fab;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Outline;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.graphics.Xfermode;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.RippleDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.StateListDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.graphics.drawable.shapes.Shape;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewOutlineProvider;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;

/* loaded from: classes.dex */
public class FloatingActionButton extends ImageButton {
    private static final int BAR_MAX_LENGTH = 270;
    private static final double BAR_SPIN_CYCLE_TIME = 500.0d;
    private static final long PAUSE_GROWING_TIME = 200;
    private static final Xfermode PORTER_DUFF_CLEAR = new PorterDuffXfermode(PorterDuff.Mode.CLEAR);
    public static final int SIZE_MINI = 1;
    public static final int SIZE_NORMAL = 0;
    private boolean mAnimateProgress;
    private Drawable mBackgroundDrawable;
    private Paint mBackgroundPaint;
    private float mBarExtraLength;
    private boolean mBarGrowingFromFront;
    private int mBarLength;
    private boolean mButtonPositionSaved;
    private View.OnClickListener mClickListener;
    private int mColorDisabled;
    private int mColorNormal;
    private int mColorPressed;
    private int mColorRipple;
    private float mCurrentProgress;
    int mFabSize;
    GestureDetector mGestureDetector;
    private Animation mHideAnimation;
    private Drawable mIcon;
    private int mIconSize;
    private String mLabelText;
    private long mLastTimeAnimated;
    private float mOriginalX;
    private float mOriginalY;
    private long mPausedTimeWithoutGrowing;
    private int mProgress;
    private int mProgressBackgroundColor;
    private boolean mProgressBarEnabled;
    private RectF mProgressCircleBounds;
    private int mProgressColor;
    private boolean mProgressIndeterminate;
    private int mProgressMax;
    private Paint mProgressPaint;
    private int mProgressWidth;
    int mShadowColor;
    int mShadowRadius;
    int mShadowXOffset;
    int mShadowYOffset;
    private boolean mShouldProgressIndeterminate;
    private boolean mShouldSetProgress;
    private boolean mShouldUpdateButtonPosition;
    private Animation mShowAnimation;
    private boolean mShowProgressBackground;
    boolean mShowShadow;
    private float mSpinSpeed;
    private float mTargetProgress;
    private double mTimeStartGrowing;
    private boolean mUsingElevation;
    private boolean mUsingElevationCompat;

    public FloatingActionButton(Context context) {
        this(context, null);
    }

    public FloatingActionButton(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public FloatingActionButton(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mShadowRadius = Util.dpToPx(getContext(), 4.0f);
        this.mShadowXOffset = Util.dpToPx(getContext(), 1.0f);
        this.mShadowYOffset = Util.dpToPx(getContext(), 3.0f);
        this.mIconSize = Util.dpToPx(getContext(), 24.0f);
        this.mProgressWidth = Util.dpToPx(getContext(), 6.0f);
        this.mOriginalX = -1.0f;
        this.mOriginalY = -1.0f;
        this.mProgressCircleBounds = new RectF();
        this.mBackgroundPaint = new Paint(1);
        this.mProgressPaint = new Paint(1);
        this.mSpinSpeed = 195.0f;
        this.mPausedTimeWithoutGrowing = 0L;
        this.mBarGrowingFromFront = true;
        this.mBarLength = 16;
        this.mProgressMax = 100;
        this.mGestureDetector = new GestureDetector(getContext(), new GestureDetector.SimpleOnGestureListener() { // from class: com.github.clans.fab.FloatingActionButton.2
            @Override // android.view.GestureDetector.SimpleOnGestureListener, android.view.GestureDetector.OnGestureListener
            public boolean onDown(MotionEvent motionEvent) {
                Label label = (Label) FloatingActionButton.this.getTag(R.id.fab_label);
                if (label != null) {
                    label.onActionDown();
                }
                FloatingActionButton.this.onActionDown();
                return super.onDown(motionEvent);
            }

            @Override // android.view.GestureDetector.SimpleOnGestureListener, android.view.GestureDetector.OnGestureListener
            public boolean onSingleTapUp(MotionEvent motionEvent) {
                Label label = (Label) FloatingActionButton.this.getTag(R.id.fab_label);
                if (label != null) {
                    label.onActionUp();
                }
                FloatingActionButton.this.onActionUp();
                return super.onSingleTapUp(motionEvent);
            }
        });
        init(context, attributeSet, i);
    }

    public FloatingActionButton(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        this.mShadowRadius = Util.dpToPx(getContext(), 4.0f);
        this.mShadowXOffset = Util.dpToPx(getContext(), 1.0f);
        this.mShadowYOffset = Util.dpToPx(getContext(), 3.0f);
        this.mIconSize = Util.dpToPx(getContext(), 24.0f);
        this.mProgressWidth = Util.dpToPx(getContext(), 6.0f);
        this.mOriginalX = -1.0f;
        this.mOriginalY = -1.0f;
        this.mProgressCircleBounds = new RectF();
        this.mBackgroundPaint = new Paint(1);
        this.mProgressPaint = new Paint(1);
        this.mSpinSpeed = 195.0f;
        this.mPausedTimeWithoutGrowing = 0L;
        this.mBarGrowingFromFront = true;
        this.mBarLength = 16;
        this.mProgressMax = 100;
        this.mGestureDetector = new GestureDetector(getContext(), new GestureDetector.SimpleOnGestureListener() { // from class: com.github.clans.fab.FloatingActionButton.2
            @Override // android.view.GestureDetector.SimpleOnGestureListener, android.view.GestureDetector.OnGestureListener
            public boolean onDown(MotionEvent motionEvent) {
                Label label = (Label) FloatingActionButton.this.getTag(R.id.fab_label);
                if (label != null) {
                    label.onActionDown();
                }
                FloatingActionButton.this.onActionDown();
                return super.onDown(motionEvent);
            }

            @Override // android.view.GestureDetector.SimpleOnGestureListener, android.view.GestureDetector.OnGestureListener
            public boolean onSingleTapUp(MotionEvent motionEvent) {
                Label label = (Label) FloatingActionButton.this.getTag(R.id.fab_label);
                if (label != null) {
                    label.onActionUp();
                }
                FloatingActionButton.this.onActionUp();
                return super.onSingleTapUp(motionEvent);
            }
        });
        init(context, attributeSet, i);
    }

    private void init(Context context, AttributeSet attributeSet, int i) {
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.FloatingActionButton, i, 0);
        this.mColorNormal = typedArrayObtainStyledAttributes.getColor(R.styleable.FloatingActionButton_fab_colorNormal, -2473162);
        this.mColorPressed = typedArrayObtainStyledAttributes.getColor(R.styleable.FloatingActionButton_fab_colorPressed, -1617853);
        this.mColorDisabled = typedArrayObtainStyledAttributes.getColor(R.styleable.FloatingActionButton_fab_colorDisabled, -5592406);
        this.mColorRipple = typedArrayObtainStyledAttributes.getColor(R.styleable.FloatingActionButton_fab_colorRipple, -1711276033);
        this.mShowShadow = typedArrayObtainStyledAttributes.getBoolean(R.styleable.FloatingActionButton_fab_showShadow, true);
        this.mShadowColor = typedArrayObtainStyledAttributes.getColor(R.styleable.FloatingActionButton_fab_shadowColor, 1711276032);
        this.mShadowRadius = typedArrayObtainStyledAttributes.getDimensionPixelSize(R.styleable.FloatingActionButton_fab_shadowRadius, this.mShadowRadius);
        this.mShadowXOffset = typedArrayObtainStyledAttributes.getDimensionPixelSize(R.styleable.FloatingActionButton_fab_shadowXOffset, this.mShadowXOffset);
        this.mShadowYOffset = typedArrayObtainStyledAttributes.getDimensionPixelSize(R.styleable.FloatingActionButton_fab_shadowYOffset, this.mShadowYOffset);
        this.mFabSize = typedArrayObtainStyledAttributes.getInt(R.styleable.FloatingActionButton_fab_size, 0);
        this.mLabelText = typedArrayObtainStyledAttributes.getString(R.styleable.FloatingActionButton_fab_label);
        this.mShouldProgressIndeterminate = typedArrayObtainStyledAttributes.getBoolean(R.styleable.FloatingActionButton_fab_progress_indeterminate, false);
        this.mProgressColor = typedArrayObtainStyledAttributes.getColor(R.styleable.FloatingActionButton_fab_progress_color, -16738680);
        this.mProgressBackgroundColor = typedArrayObtainStyledAttributes.getColor(R.styleable.FloatingActionButton_fab_progress_backgroundColor, 1291845632);
        this.mProgressMax = typedArrayObtainStyledAttributes.getInt(R.styleable.FloatingActionButton_fab_progress_max, this.mProgressMax);
        this.mShowProgressBackground = typedArrayObtainStyledAttributes.getBoolean(R.styleable.FloatingActionButton_fab_progress_showBackground, true);
        if (typedArrayObtainStyledAttributes.hasValue(R.styleable.FloatingActionButton_fab_progress)) {
            this.mProgress = typedArrayObtainStyledAttributes.getInt(R.styleable.FloatingActionButton_fab_progress, 0);
            this.mShouldSetProgress = true;
        }
        if (typedArrayObtainStyledAttributes.hasValue(R.styleable.FloatingActionButton_fab_elevationCompat)) {
            float dimensionPixelOffset = typedArrayObtainStyledAttributes.getDimensionPixelOffset(R.styleable.FloatingActionButton_fab_elevationCompat, 0);
            if (isInEditMode()) {
                setElevation(dimensionPixelOffset);
            } else {
                setElevationCompat(dimensionPixelOffset);
            }
        }
        initShowAnimation(typedArrayObtainStyledAttributes);
        initHideAnimation(typedArrayObtainStyledAttributes);
        typedArrayObtainStyledAttributes.recycle();
        if (isInEditMode()) {
            if (this.mShouldProgressIndeterminate) {
                setIndeterminate(true);
            } else if (this.mShouldSetProgress) {
                saveButtonOriginalPosition();
                setProgress(this.mProgress, false);
            }
        }
        setClickable(true);
    }

    private void initShowAnimation(TypedArray typedArray) {
        this.mShowAnimation = AnimationUtils.loadAnimation(getContext(), typedArray.getResourceId(R.styleable.FloatingActionButton_fab_showAnimation, R.anim.fab_scale_up));
    }

    private void initHideAnimation(TypedArray typedArray) {
        this.mHideAnimation = AnimationUtils.loadAnimation(getContext(), typedArray.getResourceId(R.styleable.FloatingActionButton_fab_hideAnimation, R.anim.fab_scale_down));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int getCircleSize() {
        return getResources().getDimensionPixelSize(this.mFabSize == 0 ? R.dimen.fab_size_normal : R.dimen.fab_size_mini);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int calculateMeasuredWidth() {
        int circleSize = getCircleSize() + calculateShadowWidth();
        return this.mProgressBarEnabled ? circleSize + (this.mProgressWidth * 2) : circleSize;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int calculateMeasuredHeight() {
        int circleSize = getCircleSize() + calculateShadowHeight();
        return this.mProgressBarEnabled ? circleSize + (this.mProgressWidth * 2) : circleSize;
    }

    int calculateShadowWidth() {
        if (hasShadow()) {
            return getShadowX() * 2;
        }
        return 0;
    }

    int calculateShadowHeight() {
        if (hasShadow()) {
            return getShadowY() * 2;
        }
        return 0;
    }

    private int getShadowX() {
        return this.mShadowRadius + Math.abs(this.mShadowXOffset);
    }

    private int getShadowY() {
        return this.mShadowRadius + Math.abs(this.mShadowYOffset);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public float calculateCenterX() {
        return getMeasuredWidth() / 2;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public float calculateCenterY() {
        return getMeasuredHeight() / 2;
    }

    @Override // android.widget.ImageView, android.view.View
    protected void onMeasure(int i, int i2) {
        setMeasuredDimension(calculateMeasuredWidth(), calculateMeasuredHeight());
    }

    @Override // android.widget.ImageView, android.view.View
    protected void onDraw(Canvas canvas) {
        float f;
        float f2;
        super.onDraw(canvas);
        if (this.mProgressBarEnabled) {
            if (this.mShowProgressBackground) {
                canvas.drawArc(this.mProgressCircleBounds, 360.0f, 360.0f, false, this.mBackgroundPaint);
            }
            boolean z = false;
            boolean z2 = true;
            if (this.mProgressIndeterminate) {
                long jUptimeMillis = SystemClock.uptimeMillis() - this.mLastTimeAnimated;
                float f3 = (jUptimeMillis * this.mSpinSpeed) / 1000.0f;
                updateProgressLength(jUptimeMillis);
                float f4 = this.mCurrentProgress + f3;
                this.mCurrentProgress = f4;
                if (f4 > 360.0f) {
                    this.mCurrentProgress = f4 - 360.0f;
                }
                this.mLastTimeAnimated = SystemClock.uptimeMillis();
                float f5 = this.mCurrentProgress - 90.0f;
                float f6 = this.mBarLength + this.mBarExtraLength;
                if (isInEditMode()) {
                    f = 0.0f;
                    f2 = 135.0f;
                } else {
                    f = f5;
                    f2 = f6;
                }
                canvas.drawArc(this.mProgressCircleBounds, f, f2, false, this.mProgressPaint);
            } else {
                if (this.mCurrentProgress != this.mTargetProgress) {
                    float fUptimeMillis = ((SystemClock.uptimeMillis() - this.mLastTimeAnimated) / 1000.0f) * this.mSpinSpeed;
                    float f7 = this.mCurrentProgress;
                    float f8 = this.mTargetProgress;
                    if (f7 > f8) {
                        this.mCurrentProgress = Math.max(f7 - fUptimeMillis, f8);
                    } else {
                        this.mCurrentProgress = Math.min(f7 + fUptimeMillis, f8);
                    }
                    this.mLastTimeAnimated = SystemClock.uptimeMillis();
                    z = true;
                }
                canvas.drawArc(this.mProgressCircleBounds, -90.0f, this.mCurrentProgress, false, this.mProgressPaint);
                z2 = z;
            }
            if (z2) {
                invalidate();
            }
        }
    }

    private void updateProgressLength(long j) {
        long j2 = this.mPausedTimeWithoutGrowing;
        if (j2 >= PAUSE_GROWING_TIME) {
            double d = this.mTimeStartGrowing + j;
            this.mTimeStartGrowing = d;
            if (d > BAR_SPIN_CYCLE_TIME) {
                this.mTimeStartGrowing = d - BAR_SPIN_CYCLE_TIME;
                this.mPausedTimeWithoutGrowing = 0L;
                this.mBarGrowingFromFront = !this.mBarGrowingFromFront;
            }
            float fCos = (((float) Math.cos(((this.mTimeStartGrowing / BAR_SPIN_CYCLE_TIME) + 1.0d) * 3.141592653589793d)) / 2.0f) + 0.5f;
            float f = 270 - this.mBarLength;
            if (this.mBarGrowingFromFront) {
                this.mBarExtraLength = fCos * f;
                return;
            }
            float f2 = f * (1.0f - fCos);
            this.mCurrentProgress += this.mBarExtraLength - f2;
            this.mBarExtraLength = f2;
            return;
        }
        this.mPausedTimeWithoutGrowing = j2 + j;
    }

    @Override // android.view.View
    protected void onSizeChanged(int i, int i2, int i3, int i4) {
        saveButtonOriginalPosition();
        if (this.mShouldProgressIndeterminate) {
            setIndeterminate(true);
            this.mShouldProgressIndeterminate = false;
        } else if (this.mShouldSetProgress) {
            setProgress(this.mProgress, this.mAnimateProgress);
            this.mShouldSetProgress = false;
        } else if (this.mShouldUpdateButtonPosition) {
            updateButtonPosition();
            this.mShouldUpdateButtonPosition = false;
        }
        super.onSizeChanged(i, i2, i3, i4);
        setupProgressBounds();
        setupProgressBarPaints();
        updateBackground();
    }

    @Override // android.view.View
    public void setLayoutParams(ViewGroup.LayoutParams layoutParams) {
        if ((layoutParams instanceof ViewGroup.MarginLayoutParams) && this.mUsingElevationCompat) {
            ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) layoutParams;
            marginLayoutParams.leftMargin += getShadowX();
            marginLayoutParams.topMargin += getShadowY();
            marginLayoutParams.rightMargin += getShadowX();
            marginLayoutParams.bottomMargin += getShadowY();
        }
        super.setLayoutParams(layoutParams);
    }

    void updateBackground() {
        LayerDrawable layerDrawable;
        if (hasShadow()) {
            layerDrawable = new LayerDrawable(new Drawable[]{new Shadow(), createFillDrawable(), getIconDrawable()});
        } else {
            layerDrawable = new LayerDrawable(new Drawable[]{createFillDrawable(), getIconDrawable()});
        }
        int iMax = getIconDrawable() != null ? Math.max(getIconDrawable().getIntrinsicWidth(), getIconDrawable().getIntrinsicHeight()) : -1;
        int circleSize = getCircleSize();
        if (iMax <= 0) {
            iMax = this.mIconSize;
        }
        int i = (circleSize - iMax) / 2;
        int iAbs = hasShadow() ? this.mShadowRadius + Math.abs(this.mShadowXOffset) : 0;
        int iAbs2 = hasShadow() ? this.mShadowRadius + Math.abs(this.mShadowYOffset) : 0;
        if (this.mProgressBarEnabled) {
            int i2 = this.mProgressWidth;
            iAbs += i2;
            iAbs2 += i2;
        }
        int i3 = iAbs + i;
        int i4 = iAbs2 + i;
        layerDrawable.setLayerInset(hasShadow() ? 2 : 1, i3, i4, i3, i4);
        setBackgroundCompat(layerDrawable);
    }

    protected Drawable getIconDrawable() {
        Drawable drawable = this.mIcon;
        return drawable != null ? drawable : new ColorDrawable(0);
    }

    private Drawable createFillDrawable() {
        StateListDrawable stateListDrawable = new StateListDrawable();
        stateListDrawable.addState(new int[]{-16842910}, createCircleDrawable(this.mColorDisabled));
        stateListDrawable.addState(new int[]{android.R.attr.state_pressed}, createCircleDrawable(this.mColorPressed));
        stateListDrawable.addState(new int[0], createCircleDrawable(this.mColorNormal));
        if (Util.hasLollipop()) {
            RippleDrawable rippleDrawable = new RippleDrawable(new ColorStateList(new int[][]{new int[0]}, new int[]{this.mColorRipple}), stateListDrawable, null);
            setOutlineProvider(new ViewOutlineProvider() { // from class: com.github.clans.fab.FloatingActionButton.1
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

    private Drawable createCircleDrawable(int i) {
        CircleDrawable circleDrawable = new CircleDrawable(new OvalShape());
        circleDrawable.getPaint().setColor(i);
        return circleDrawable;
    }

    private void setBackgroundCompat(Drawable drawable) {
        if (Util.hasJellyBean()) {
            setBackground(drawable);
        } else {
            setBackgroundDrawable(drawable);
        }
    }

    private void saveButtonOriginalPosition() {
        if (this.mButtonPositionSaved) {
            return;
        }
        if (this.mOriginalX == -1.0f) {
            this.mOriginalX = getX();
        }
        if (this.mOriginalY == -1.0f) {
            this.mOriginalY = getY();
        }
        this.mButtonPositionSaved = true;
    }

    private void updateButtonPosition() {
        float x;
        float y;
        if (this.mProgressBarEnabled) {
            x = this.mOriginalX > getX() ? getX() + this.mProgressWidth : getX() - this.mProgressWidth;
            y = this.mOriginalY > getY() ? getY() + this.mProgressWidth : getY() - this.mProgressWidth;
        } else {
            x = this.mOriginalX;
            y = this.mOriginalY;
        }
        setX(x);
        setY(y);
    }

    private void setupProgressBarPaints() {
        this.mBackgroundPaint.setColor(this.mProgressBackgroundColor);
        this.mBackgroundPaint.setStyle(Paint.Style.STROKE);
        this.mBackgroundPaint.setStrokeWidth(this.mProgressWidth);
        this.mProgressPaint.setColor(this.mProgressColor);
        this.mProgressPaint.setStyle(Paint.Style.STROKE);
        this.mProgressPaint.setStrokeWidth(this.mProgressWidth);
    }

    private void setupProgressBounds() {
        int shadowX = hasShadow() ? getShadowX() : 0;
        int shadowY = hasShadow() ? getShadowY() : 0;
        int i = this.mProgressWidth;
        this.mProgressCircleBounds = new RectF((i / 2) + shadowX, (i / 2) + shadowY, (calculateMeasuredWidth() - shadowX) - (this.mProgressWidth / 2), (calculateMeasuredHeight() - shadowY) - (this.mProgressWidth / 2));
    }

    Animation getShowAnimation() {
        return this.mShowAnimation;
    }

    Animation getHideAnimation() {
        return this.mHideAnimation;
    }

    void playShowAnimation() {
        this.mHideAnimation.cancel();
        startAnimation(this.mShowAnimation);
    }

    void playHideAnimation() {
        this.mShowAnimation.cancel();
        startAnimation(this.mHideAnimation);
    }

    View.OnClickListener getOnClickListener() {
        return this.mClickListener;
    }

    Label getLabelView() {
        return (Label) getTag(R.id.fab_label);
    }

    void setColors(int i, int i2, int i3) {
        this.mColorNormal = i;
        this.mColorPressed = i2;
        this.mColorRipple = i3;
    }

    void onActionDown() {
        Drawable drawable = this.mBackgroundDrawable;
        if (drawable instanceof StateListDrawable) {
            ((StateListDrawable) drawable).setState(new int[]{android.R.attr.state_enabled, android.R.attr.state_pressed});
        } else if (Util.hasLollipop()) {
            RippleDrawable rippleDrawable = (RippleDrawable) this.mBackgroundDrawable;
            rippleDrawable.setState(new int[]{android.R.attr.state_enabled, android.R.attr.state_pressed});
            rippleDrawable.setHotspot(calculateCenterX(), calculateCenterY());
            rippleDrawable.setVisible(true, true);
        }
    }

    void onActionUp() {
        Drawable drawable = this.mBackgroundDrawable;
        if (drawable instanceof StateListDrawable) {
            ((StateListDrawable) drawable).setState(new int[]{android.R.attr.state_enabled});
        } else if (Util.hasLollipop()) {
            RippleDrawable rippleDrawable = (RippleDrawable) this.mBackgroundDrawable;
            rippleDrawable.setState(new int[]{android.R.attr.state_enabled});
            rippleDrawable.setHotspot(calculateCenterX(), calculateCenterY());
            rippleDrawable.setVisible(true, true);
        }
    }

    @Override // android.view.View
    public boolean onTouchEvent(MotionEvent motionEvent) {
        if (this.mClickListener != null && isEnabled()) {
            Label label = (Label) getTag(R.id.fab_label);
            if (label == null) {
                return super.onTouchEvent(motionEvent);
            }
            int action = motionEvent.getAction();
            if (action == 1) {
                if (label != null) {
                    label.onActionUp();
                }
                onActionUp();
            } else if (action == 3) {
                if (label != null) {
                    label.onActionUp();
                }
                onActionUp();
            }
            this.mGestureDetector.onTouchEvent(motionEvent);
        }
        return super.onTouchEvent(motionEvent);
    }

    @Override // android.view.View
    public Parcelable onSaveInstanceState() {
        ProgressSavedState progressSavedState = new ProgressSavedState(super.onSaveInstanceState());
        progressSavedState.mCurrentProgress = this.mCurrentProgress;
        progressSavedState.mTargetProgress = this.mTargetProgress;
        progressSavedState.mSpinSpeed = this.mSpinSpeed;
        progressSavedState.mProgressWidth = this.mProgressWidth;
        progressSavedState.mProgressColor = this.mProgressColor;
        progressSavedState.mProgressBackgroundColor = this.mProgressBackgroundColor;
        progressSavedState.mShouldProgressIndeterminate = this.mProgressIndeterminate;
        progressSavedState.mShouldSetProgress = this.mProgressBarEnabled && this.mProgress > 0 && !this.mProgressIndeterminate;
        progressSavedState.mProgress = this.mProgress;
        progressSavedState.mAnimateProgress = this.mAnimateProgress;
        progressSavedState.mShowProgressBackground = this.mShowProgressBackground;
        return progressSavedState;
    }

    @Override // android.view.View
    public void onRestoreInstanceState(Parcelable parcelable) {
        if (!(parcelable instanceof ProgressSavedState)) {
            super.onRestoreInstanceState(parcelable);
            return;
        }
        ProgressSavedState progressSavedState = (ProgressSavedState) parcelable;
        super.onRestoreInstanceState(progressSavedState.getSuperState());
        this.mCurrentProgress = progressSavedState.mCurrentProgress;
        this.mTargetProgress = progressSavedState.mTargetProgress;
        this.mSpinSpeed = progressSavedState.mSpinSpeed;
        this.mProgressWidth = progressSavedState.mProgressWidth;
        this.mProgressColor = progressSavedState.mProgressColor;
        this.mProgressBackgroundColor = progressSavedState.mProgressBackgroundColor;
        this.mShouldProgressIndeterminate = progressSavedState.mShouldProgressIndeterminate;
        this.mShouldSetProgress = progressSavedState.mShouldSetProgress;
        this.mProgress = progressSavedState.mProgress;
        this.mAnimateProgress = progressSavedState.mAnimateProgress;
        this.mShowProgressBackground = progressSavedState.mShowProgressBackground;
        this.mLastTimeAnimated = SystemClock.uptimeMillis();
    }

    private class CircleDrawable extends ShapeDrawable {
        private int circleInsetHorizontal;
        private int circleInsetVertical;

        private CircleDrawable() {
        }

        private CircleDrawable(Shape shape) {
            super(shape);
            this.circleInsetHorizontal = FloatingActionButton.this.hasShadow() ? FloatingActionButton.this.mShadowRadius + Math.abs(FloatingActionButton.this.mShadowXOffset) : 0;
            this.circleInsetVertical = FloatingActionButton.this.hasShadow() ? Math.abs(FloatingActionButton.this.mShadowYOffset) + FloatingActionButton.this.mShadowRadius : 0;
            if (FloatingActionButton.this.mProgressBarEnabled) {
                this.circleInsetHorizontal += FloatingActionButton.this.mProgressWidth;
                this.circleInsetVertical += FloatingActionButton.this.mProgressWidth;
            }
        }

        @Override // android.graphics.drawable.ShapeDrawable, android.graphics.drawable.Drawable
        public void draw(Canvas canvas) {
            setBounds(this.circleInsetHorizontal, this.circleInsetVertical, FloatingActionButton.this.calculateMeasuredWidth() - this.circleInsetHorizontal, FloatingActionButton.this.calculateMeasuredHeight() - this.circleInsetVertical);
            super.draw(canvas);
        }
    }

    private class Shadow extends Drawable {
        private Paint mErase;
        private Paint mPaint;
        private float mRadius;

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
            FloatingActionButton.this.setLayerType(1, null);
            this.mPaint.setStyle(Paint.Style.FILL);
            this.mPaint.setColor(FloatingActionButton.this.mColorNormal);
            this.mErase.setXfermode(FloatingActionButton.PORTER_DUFF_CLEAR);
            if (!FloatingActionButton.this.isInEditMode()) {
                this.mPaint.setShadowLayer(FloatingActionButton.this.mShadowRadius, FloatingActionButton.this.mShadowXOffset, FloatingActionButton.this.mShadowYOffset, FloatingActionButton.this.mShadowColor);
            }
            this.mRadius = FloatingActionButton.this.getCircleSize() / 2;
            if (FloatingActionButton.this.mProgressBarEnabled && FloatingActionButton.this.mShowProgressBackground) {
                this.mRadius += FloatingActionButton.this.mProgressWidth;
            }
        }

        @Override // android.graphics.drawable.Drawable
        public void draw(Canvas canvas) {
            canvas.drawCircle(FloatingActionButton.this.calculateCenterX(), FloatingActionButton.this.calculateCenterY(), this.mRadius, this.mPaint);
            canvas.drawCircle(FloatingActionButton.this.calculateCenterX(), FloatingActionButton.this.calculateCenterY(), this.mRadius, this.mErase);
        }
    }

    static class ProgressSavedState extends View.BaseSavedState {
        public static final Parcelable.Creator<ProgressSavedState> CREATOR = new Parcelable.Creator<ProgressSavedState>() { // from class: com.github.clans.fab.FloatingActionButton.ProgressSavedState.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public ProgressSavedState createFromParcel(Parcel parcel) {
                return new ProgressSavedState(parcel);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public ProgressSavedState[] newArray(int i) {
                return new ProgressSavedState[i];
            }
        };
        boolean mAnimateProgress;
        float mCurrentProgress;
        int mProgress;
        int mProgressBackgroundColor;
        boolean mProgressBarEnabled;
        boolean mProgressBarVisibilityChanged;
        int mProgressColor;
        boolean mProgressIndeterminate;
        int mProgressWidth;
        boolean mShouldProgressIndeterminate;
        boolean mShouldSetProgress;
        boolean mShowProgressBackground;
        float mSpinSpeed;
        float mTargetProgress;

        ProgressSavedState(Parcelable parcelable) {
            super(parcelable);
        }

        private ProgressSavedState(Parcel parcel) {
            super(parcel);
            this.mCurrentProgress = parcel.readFloat();
            this.mTargetProgress = parcel.readFloat();
            this.mProgressBarEnabled = parcel.readInt() != 0;
            this.mSpinSpeed = parcel.readFloat();
            this.mProgress = parcel.readInt();
            this.mProgressWidth = parcel.readInt();
            this.mProgressColor = parcel.readInt();
            this.mProgressBackgroundColor = parcel.readInt();
            this.mProgressBarVisibilityChanged = parcel.readInt() != 0;
            this.mProgressIndeterminate = parcel.readInt() != 0;
            this.mShouldProgressIndeterminate = parcel.readInt() != 0;
            this.mShouldSetProgress = parcel.readInt() != 0;
            this.mAnimateProgress = parcel.readInt() != 0;
            this.mShowProgressBackground = parcel.readInt() != 0;
        }

        @Override // android.view.View.BaseSavedState, android.view.AbsSavedState, android.os.Parcelable
        public void writeToParcel(Parcel parcel, int i) {
            super.writeToParcel(parcel, i);
            parcel.writeFloat(this.mCurrentProgress);
            parcel.writeFloat(this.mTargetProgress);
            parcel.writeInt(this.mProgressBarEnabled ? 1 : 0);
            parcel.writeFloat(this.mSpinSpeed);
            parcel.writeInt(this.mProgress);
            parcel.writeInt(this.mProgressWidth);
            parcel.writeInt(this.mProgressColor);
            parcel.writeInt(this.mProgressBackgroundColor);
            parcel.writeInt(this.mProgressBarVisibilityChanged ? 1 : 0);
            parcel.writeInt(this.mProgressIndeterminate ? 1 : 0);
            parcel.writeInt(this.mShouldProgressIndeterminate ? 1 : 0);
            parcel.writeInt(this.mShouldSetProgress ? 1 : 0);
            parcel.writeInt(this.mAnimateProgress ? 1 : 0);
            parcel.writeInt(this.mShowProgressBackground ? 1 : 0);
        }
    }

    @Override // android.widget.ImageView
    public void setImageDrawable(Drawable drawable) {
        if (this.mIcon != drawable) {
            this.mIcon = drawable;
            updateBackground();
        }
    }

    @Override // android.widget.ImageView
    public void setImageResource(int i) throws Resources.NotFoundException {
        Drawable drawable = getResources().getDrawable(i);
        if (this.mIcon != drawable) {
            this.mIcon = drawable;
            updateBackground();
        }
    }

    @Override // android.view.View
    public void setOnClickListener(View.OnClickListener onClickListener) {
        super.setOnClickListener(onClickListener);
        this.mClickListener = onClickListener;
        View view = (View) getTag(R.id.fab_label);
        if (view != null) {
            view.setOnClickListener(new View.OnClickListener() { // from class: com.github.clans.fab.FloatingActionButton.3
                @Override // android.view.View.OnClickListener
                public void onClick(View view2) {
                    if (FloatingActionButton.this.mClickListener != null) {
                        FloatingActionButton.this.mClickListener.onClick(FloatingActionButton.this);
                    }
                }
            });
        }
    }

    public void setButtonSize(int i) {
        if (i != 0 && i != 1) {
            throw new IllegalArgumentException("Use @FabSize constants only!");
        }
        if (this.mFabSize != i) {
            this.mFabSize = i;
            updateBackground();
        }
    }

    public int getButtonSize() {
        return this.mFabSize;
    }

    public void setColorNormal(int i) {
        if (this.mColorNormal != i) {
            this.mColorNormal = i;
            updateBackground();
        }
    }

    public void setColorNormalResId(int i) {
        setColorNormal(getResources().getColor(i));
    }

    public int getColorNormal() {
        return this.mColorNormal;
    }

    public void setColorPressed(int i) {
        if (i != this.mColorPressed) {
            this.mColorPressed = i;
            updateBackground();
        }
    }

    public void setColorPressedResId(int i) {
        setColorPressed(getResources().getColor(i));
    }

    public int getColorPressed() {
        return this.mColorPressed;
    }

    public void setColorRipple(int i) {
        if (i != this.mColorRipple) {
            this.mColorRipple = i;
            updateBackground();
        }
    }

    public void setColorRippleResId(int i) {
        setColorRipple(getResources().getColor(i));
    }

    public int getColorRipple() {
        return this.mColorRipple;
    }

    public void setColorDisabled(int i) {
        if (i != this.mColorDisabled) {
            this.mColorDisabled = i;
            updateBackground();
        }
    }

    public void setColorDisabledResId(int i) {
        setColorDisabled(getResources().getColor(i));
    }

    public int getColorDisabled() {
        return this.mColorDisabled;
    }

    public void setShowShadow(boolean z) {
        if (this.mShowShadow != z) {
            this.mShowShadow = z;
            updateBackground();
        }
    }

    public boolean hasShadow() {
        return !this.mUsingElevation && this.mShowShadow;
    }

    public void setShadowRadius(int i) throws Resources.NotFoundException {
        int dimensionPixelSize = getResources().getDimensionPixelSize(i);
        if (this.mShadowRadius != dimensionPixelSize) {
            this.mShadowRadius = dimensionPixelSize;
            requestLayout();
            updateBackground();
        }
    }

    public void setShadowRadius(float f) {
        this.mShadowRadius = Util.dpToPx(getContext(), f);
        requestLayout();
        updateBackground();
    }

    public int getShadowRadius() {
        return this.mShadowRadius;
    }

    public void setShadowXOffset(int i) throws Resources.NotFoundException {
        int dimensionPixelSize = getResources().getDimensionPixelSize(i);
        if (this.mShadowXOffset != dimensionPixelSize) {
            this.mShadowXOffset = dimensionPixelSize;
            requestLayout();
            updateBackground();
        }
    }

    public void setShadowXOffset(float f) {
        this.mShadowXOffset = Util.dpToPx(getContext(), f);
        requestLayout();
        updateBackground();
    }

    public int getShadowXOffset() {
        return this.mShadowXOffset;
    }

    public void setShadowYOffset(int i) throws Resources.NotFoundException {
        int dimensionPixelSize = getResources().getDimensionPixelSize(i);
        if (this.mShadowYOffset != dimensionPixelSize) {
            this.mShadowYOffset = dimensionPixelSize;
            requestLayout();
            updateBackground();
        }
    }

    public void setShadowYOffset(float f) {
        this.mShadowYOffset = Util.dpToPx(getContext(), f);
        requestLayout();
        updateBackground();
    }

    public int getShadowYOffset() {
        return this.mShadowYOffset;
    }

    public void setShadowColorResource(int i) throws Resources.NotFoundException {
        int color = getResources().getColor(i);
        if (this.mShadowColor != color) {
            this.mShadowColor = color;
            updateBackground();
        }
    }

    public void setShadowColor(int i) {
        if (this.mShadowColor != i) {
            this.mShadowColor = i;
            updateBackground();
        }
    }

    public int getShadowColor() {
        return this.mShadowColor;
    }

    public boolean isHidden() {
        return getVisibility() == 4;
    }

    public void show(boolean z) {
        if (isHidden()) {
            if (z) {
                playShowAnimation();
            }
            super.setVisibility(0);
        }
    }

    public void hide(boolean z) {
        if (isHidden()) {
            return;
        }
        if (z) {
            playHideAnimation();
        }
        super.setVisibility(4);
    }

    public void toggle(boolean z) {
        if (isHidden()) {
            show(z);
        } else {
            hide(z);
        }
    }

    public void setLabelText(String str) {
        this.mLabelText = str;
        Label labelView = getLabelView();
        if (labelView != null) {
            labelView.setText(str);
        }
    }

    public String getLabelText() {
        return this.mLabelText;
    }

    public void setShowAnimation(Animation animation) {
        this.mShowAnimation = animation;
    }

    public void setHideAnimation(Animation animation) {
        this.mHideAnimation = animation;
    }

    public void setLabelVisibility(int i) {
        Label labelView = getLabelView();
        if (labelView != null) {
            labelView.setVisibility(i);
            labelView.setHandleVisibilityChanges(i == 0);
        }
    }

    public int getLabelVisibility() {
        Label labelView = getLabelView();
        if (labelView != null) {
            return labelView.getVisibility();
        }
        return -1;
    }

    @Override // android.view.View
    public void setElevation(float f) {
        if (!Util.hasLollipop() || f <= 0.0f) {
            return;
        }
        super.setElevation(f);
        if (!isInEditMode()) {
            this.mUsingElevation = true;
            this.mShowShadow = false;
        }
        updateBackground();
    }

    public void setElevationCompat(float f) {
        this.mShadowColor = 637534208;
        float f2 = f / 2.0f;
        this.mShadowRadius = Math.round(f2);
        this.mShadowXOffset = 0;
        if (this.mFabSize == 0) {
            f2 = f;
        }
        this.mShadowYOffset = Math.round(f2);
        if (Util.hasLollipop()) {
            super.setElevation(f);
            this.mUsingElevationCompat = true;
            this.mShowShadow = false;
            updateBackground();
            ViewGroup.LayoutParams layoutParams = getLayoutParams();
            if (layoutParams != null) {
                setLayoutParams(layoutParams);
                return;
            }
            return;
        }
        this.mShowShadow = true;
        updateBackground();
    }

    public synchronized void setIndeterminate(boolean z) {
        if (!z) {
            this.mCurrentProgress = 0.0f;
            this.mProgressBarEnabled = z;
            this.mShouldUpdateButtonPosition = true;
            this.mProgressIndeterminate = z;
            this.mLastTimeAnimated = SystemClock.uptimeMillis();
            setupProgressBounds();
            updateBackground();
        } else {
            this.mProgressBarEnabled = z;
            this.mShouldUpdateButtonPosition = true;
            this.mProgressIndeterminate = z;
            this.mLastTimeAnimated = SystemClock.uptimeMillis();
            setupProgressBounds();
            updateBackground();
        }
    }

    public synchronized void setMax(int i) {
        this.mProgressMax = i;
    }

    public synchronized int getMax() {
        return this.mProgressMax;
    }

    public synchronized void setProgress(int i, boolean z) {
        if (this.mProgressIndeterminate) {
            return;
        }
        this.mProgress = i;
        this.mAnimateProgress = z;
        if (!this.mButtonPositionSaved) {
            this.mShouldSetProgress = true;
            return;
        }
        this.mProgressBarEnabled = true;
        this.mShouldUpdateButtonPosition = true;
        setupProgressBounds();
        saveButtonOriginalPosition();
        updateBackground();
        if (i < 0) {
            i = 0;
        } else if (i > this.mProgressMax) {
            i = this.mProgressMax;
        }
        float f = i;
        if (f == this.mTargetProgress) {
            return;
        }
        this.mTargetProgress = this.mProgressMax > 0 ? (f / this.mProgressMax) * 360.0f : 0.0f;
        this.mLastTimeAnimated = SystemClock.uptimeMillis();
        if (!z) {
            this.mCurrentProgress = this.mTargetProgress;
        }
        invalidate();
    }

    public synchronized int getProgress() {
        return this.mProgressIndeterminate ? 0 : this.mProgress;
    }

    public synchronized void hideProgress() {
        this.mProgressBarEnabled = false;
        this.mShouldUpdateButtonPosition = true;
        updateBackground();
    }

    public synchronized void setShowProgressBackground(boolean z) {
        this.mShowProgressBackground = z;
    }

    public synchronized boolean isProgressBackgroundShown() {
        return this.mShowProgressBackground;
    }

    @Override // android.view.View
    public void setEnabled(boolean z) {
        super.setEnabled(z);
        Label label = (Label) getTag(R.id.fab_label);
        if (label != null) {
            label.setEnabled(z);
        }
    }

    @Override // android.widget.ImageView, android.view.View
    public void setVisibility(int i) {
        super.setVisibility(i);
        Label label = (Label) getTag(R.id.fab_label);
        if (label != null) {
            label.setVisibility(i);
        }
    }

    public void hideButtonInMenu(boolean z) {
        if (isHidden() || getVisibility() == 8) {
            return;
        }
        hide(z);
        Label labelView = getLabelView();
        if (labelView != null) {
            labelView.hide(z);
        }
        getHideAnimation().setAnimationListener(new Animation.AnimationListener() { // from class: com.github.clans.fab.FloatingActionButton.4
            @Override // android.view.animation.Animation.AnimationListener
            public void onAnimationRepeat(Animation animation) {
            }

            @Override // android.view.animation.Animation.AnimationListener
            public void onAnimationStart(Animation animation) {
            }

            @Override // android.view.animation.Animation.AnimationListener
            public void onAnimationEnd(Animation animation) {
                FloatingActionButton.this.setVisibility(8);
                FloatingActionButton.this.getHideAnimation().setAnimationListener(null);
            }
        });
    }

    public void showButtonInMenu(boolean z) {
        if (getVisibility() == 0) {
            return;
        }
        setVisibility(4);
        show(z);
        Label labelView = getLabelView();
        if (labelView != null) {
            labelView.show(z);
        }
    }

    public void setLabelColors(int i, int i2, int i3) {
        Label labelView = getLabelView();
        int paddingLeft = labelView.getPaddingLeft();
        int paddingTop = labelView.getPaddingTop();
        int paddingRight = labelView.getPaddingRight();
        int paddingBottom = labelView.getPaddingBottom();
        labelView.setColors(i, i2, i3);
        labelView.updateBackground();
        labelView.setPadding(paddingLeft, paddingTop, paddingRight, paddingBottom);
    }

    public void setLabelTextColor(int i) {
        getLabelView().setTextColor(i);
    }

    public void setLabelTextColor(ColorStateList colorStateList) {
        getLabelView().setTextColor(colorStateList);
    }
}
