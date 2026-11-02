package fr.castorflex.android.smoothprogressbar;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Shader;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.Drawable;
import android.os.SystemClock;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Interpolator;
import de.keyboardsurfer.android.widget.crouton.Style;

/* loaded from: classes2.dex */
public class SmoothProgressDrawable extends Drawable implements Animatable {
    private static final long FRAME_DURATION = 16;
    private static final float OFFSET_PER_FRAME = 0.01f;
    private final Rect fBackgroundRect;
    private Drawable mBackgroundDrawable;
    private Rect mBounds;
    private Callbacks mCallbacks;
    private int[] mColors;
    private int mColorsIndex;
    private float mCurrentOffset;
    private int mCurrentSections;
    private boolean mFinishing;
    private float mFinishingOffset;
    private Interpolator mInterpolator;
    private int[] mLinearGradientColors;
    private float[] mLinearGradientPositions;
    private float mMaxOffset;
    private boolean mMirrorMode;
    private boolean mNewTurn;
    private Paint mPaint;
    private boolean mProgressiveStartActivated;
    private float mProgressiveStartSpeed;
    private float mProgressiveStopSpeed;
    private boolean mReversed;
    private boolean mRunning;
    private int mSectionsCount;
    private int mSeparatorLength;
    private float mSpeed;
    private int mStartSection;
    private float mStrokeWidth;
    private final Runnable mUpdater;
    private boolean mUseGradients;

    public interface Callbacks {
        void onStart();

        void onStop();
    }

    @Override // android.graphics.drawable.Drawable
    public int getOpacity() {
        return -2;
    }

    private SmoothProgressDrawable(Interpolator interpolator, int i, int i2, int[] iArr, float f, float f2, float f3, float f4, boolean z, boolean z2, Callbacks callbacks, boolean z3, Drawable drawable, boolean z4) {
        this.fBackgroundRect = new Rect();
        this.mUpdater = new Runnable() { // from class: fr.castorflex.android.smoothprogressbar.SmoothProgressDrawable.1
            @Override // java.lang.Runnable
            public void run() {
                if (SmoothProgressDrawable.this.isFinishing()) {
                    SmoothProgressDrawable.this.mFinishingOffset += SmoothProgressDrawable.this.mProgressiveStopSpeed * SmoothProgressDrawable.OFFSET_PER_FRAME;
                    SmoothProgressDrawable.this.mCurrentOffset += SmoothProgressDrawable.this.mProgressiveStopSpeed * SmoothProgressDrawable.OFFSET_PER_FRAME;
                    if (SmoothProgressDrawable.this.mFinishingOffset >= 1.0f) {
                        SmoothProgressDrawable.this.stop();
                    }
                } else if (SmoothProgressDrawable.this.isStarting()) {
                    SmoothProgressDrawable.this.mCurrentOffset += SmoothProgressDrawable.this.mProgressiveStartSpeed * SmoothProgressDrawable.OFFSET_PER_FRAME;
                } else {
                    SmoothProgressDrawable.this.mCurrentOffset += SmoothProgressDrawable.this.mSpeed * SmoothProgressDrawable.OFFSET_PER_FRAME;
                }
                if (SmoothProgressDrawable.this.mCurrentOffset >= SmoothProgressDrawable.this.mMaxOffset) {
                    SmoothProgressDrawable.this.mNewTurn = true;
                    SmoothProgressDrawable.this.mCurrentOffset -= SmoothProgressDrawable.this.mMaxOffset;
                }
                if (SmoothProgressDrawable.this.isRunning()) {
                    SmoothProgressDrawable smoothProgressDrawable = SmoothProgressDrawable.this;
                    smoothProgressDrawable.scheduleSelf(smoothProgressDrawable.mUpdater, SystemClock.uptimeMillis() + 16);
                }
                SmoothProgressDrawable.this.invalidateSelf();
            }
        };
        this.mRunning = false;
        this.mInterpolator = interpolator;
        this.mSectionsCount = i;
        this.mStartSection = 0;
        this.mCurrentSections = i;
        this.mSeparatorLength = i2;
        this.mSpeed = f2;
        this.mProgressiveStartSpeed = f3;
        this.mProgressiveStopSpeed = f4;
        this.mReversed = z;
        this.mColors = iArr;
        this.mColorsIndex = 0;
        this.mMirrorMode = z2;
        this.mFinishing = false;
        this.mBackgroundDrawable = drawable;
        this.mStrokeWidth = f;
        this.mMaxOffset = 1.0f / i;
        Paint paint = new Paint();
        this.mPaint = paint;
        paint.setStrokeWidth(f);
        this.mPaint.setStyle(Paint.Style.STROKE);
        this.mPaint.setDither(false);
        this.mPaint.setAntiAlias(false);
        this.mProgressiveStartActivated = z3;
        this.mCallbacks = callbacks;
        this.mUseGradients = z4;
        refreshLinearGradientOptions();
    }

    public void setInterpolator(Interpolator interpolator) {
        if (interpolator == null) {
            throw new IllegalArgumentException("Interpolator cannot be null");
        }
        this.mInterpolator = interpolator;
        invalidateSelf();
    }

    public void setColors(int[] iArr) {
        if (iArr == null || iArr.length == 0) {
            throw new IllegalArgumentException("Colors cannot be null or empty");
        }
        this.mColorsIndex = 0;
        this.mColors = iArr;
        refreshLinearGradientOptions();
        invalidateSelf();
    }

    public void setColor(int i) {
        setColors(new int[]{i});
    }

    public void setSpeed(float f) {
        if (f < 0.0f) {
            throw new IllegalArgumentException("Speed must be >= 0");
        }
        this.mSpeed = f;
        invalidateSelf();
    }

    public void setProgressiveStartSpeed(float f) {
        if (f < 0.0f) {
            throw new IllegalArgumentException("SpeedProgressiveStart must be >= 0");
        }
        this.mProgressiveStartSpeed = f;
        invalidateSelf();
    }

    public void setProgressiveStopSpeed(float f) {
        if (f < 0.0f) {
            throw new IllegalArgumentException("SpeedProgressiveStop must be >= 0");
        }
        this.mProgressiveStopSpeed = f;
        invalidateSelf();
    }

    public void setSectionsCount(int i) {
        if (i <= 0) {
            throw new IllegalArgumentException("SectionsCount must be > 0");
        }
        this.mSectionsCount = i;
        float f = 1.0f / i;
        this.mMaxOffset = f;
        this.mCurrentOffset %= f;
        refreshLinearGradientOptions();
        invalidateSelf();
    }

    public void setSeparatorLength(int i) {
        if (i < 0) {
            throw new IllegalArgumentException("SeparatorLength must be >= 0");
        }
        this.mSeparatorLength = i;
        invalidateSelf();
    }

    public void setStrokeWidth(float f) {
        if (f < 0.0f) {
            throw new IllegalArgumentException("The strokeWidth must be >= 0");
        }
        this.mPaint.setStrokeWidth(f);
        invalidateSelf();
    }

    public void setReversed(boolean z) {
        if (this.mReversed == z) {
            return;
        }
        this.mReversed = z;
        invalidateSelf();
    }

    public void setMirrorMode(boolean z) {
        if (this.mMirrorMode == z) {
            return;
        }
        this.mMirrorMode = z;
        invalidateSelf();
    }

    public void setBackgroundDrawable(Drawable drawable) {
        if (this.mBackgroundDrawable == drawable) {
            return;
        }
        this.mBackgroundDrawable = drawable;
        invalidateSelf();
    }

    public Drawable getBackgroundDrawable() {
        return this.mBackgroundDrawable;
    }

    public int[] getColors() {
        return this.mColors;
    }

    public float getStrokeWidth() {
        return this.mStrokeWidth;
    }

    public void setProgressiveStartActivated(boolean z) {
        this.mProgressiveStartActivated = z;
    }

    public void setUseGradients(boolean z) {
        if (this.mUseGradients == z) {
            return;
        }
        this.mUseGradients = z;
        refreshLinearGradientOptions();
        invalidateSelf();
    }

    protected void refreshLinearGradientOptions() {
        if (this.mUseGradients) {
            int i = this.mSectionsCount;
            this.mLinearGradientColors = new int[i + 2];
            this.mLinearGradientPositions = new float[i + 2];
        } else {
            this.mPaint.setShader(null);
            this.mLinearGradientColors = null;
            this.mLinearGradientPositions = null;
        }
    }

    @Override // android.graphics.drawable.Drawable
    public void draw(Canvas canvas) {
        Rect bounds = getBounds();
        this.mBounds = bounds;
        canvas.clipRect(bounds);
        if (this.mNewTurn) {
            this.mColorsIndex = decrementColor(this.mColorsIndex);
            this.mNewTurn = false;
            if (isFinishing()) {
                int i = this.mStartSection + 1;
                this.mStartSection = i;
                if (i > this.mSectionsCount) {
                    stop();
                    return;
                }
            }
            int i2 = this.mCurrentSections;
            if (i2 < this.mSectionsCount) {
                this.mCurrentSections = i2 + 1;
            }
        }
        if (this.mUseGradients) {
            drawGradient(canvas);
        }
        drawStrokes(canvas);
    }

    private void drawGradient(Canvas canvas) {
        float f = 1.0f / this.mSectionsCount;
        int length = this.mColorsIndex;
        float[] fArr = this.mLinearGradientPositions;
        int i = 0;
        fArr[0] = 0.0f;
        fArr[fArr.length - 1] = 1.0f;
        int length2 = length - 1;
        if (length2 < 0) {
            length2 += this.mColors.length;
        }
        this.mLinearGradientColors[0] = this.mColors[length2];
        while (i < this.mSectionsCount) {
            float interpolation = this.mInterpolator.getInterpolation((i * f) + this.mCurrentOffset);
            i++;
            this.mLinearGradientPositions[i] = interpolation;
            int[] iArr = this.mLinearGradientColors;
            int[] iArr2 = this.mColors;
            iArr[i] = iArr2[length];
            length = (length + 1) % iArr2.length;
        }
        this.mLinearGradientColors[r10.length - 1] = this.mColors[length];
        this.mPaint.setShader(new LinearGradient((this.mReversed && this.mMirrorMode) ? Math.abs(this.mBounds.left - this.mBounds.right) / 2 : this.mBounds.left, this.mBounds.centerY() - (this.mStrokeWidth / 2.0f), this.mMirrorMode ? this.mReversed ? this.mBounds.left : Math.abs(this.mBounds.left - this.mBounds.right) / 2 : this.mBounds.right, (this.mStrokeWidth / 2.0f) + this.mBounds.centerY(), this.mLinearGradientColors, this.mLinearGradientPositions, this.mMirrorMode ? Shader.TileMode.MIRROR : Shader.TileMode.CLAMP));
    }

    /* JADX WARN: Removed duplicated region for block: B:35:0x00f9  */
    /* JADX WARN: Removed duplicated region for block: B:36:0x00fc  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    private void drawStrokes(android.graphics.Canvas r24) {
        /*
            Method dump skipped, instructions count: 278
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: fr.castorflex.android.smoothprogressbar.SmoothProgressDrawable.drawStrokes(android.graphics.Canvas):void");
    }

    private void drawLine(Canvas canvas, int i, float f, float f2, float f3, float f4, int i2) {
        this.mPaint.setColor(this.mColors[i2]);
        if (!this.mMirrorMode) {
            canvas.drawLine(f, f2, f3, f4, this.mPaint);
            return;
        }
        if (this.mReversed) {
            float f5 = i;
            canvas.drawLine(f5 + f, f2, f5 + f3, f4, this.mPaint);
            canvas.drawLine(f5 - f, f2, f5 - f3, f4, this.mPaint);
        } else {
            canvas.drawLine(f, f2, f3, f4, this.mPaint);
            float f6 = i * 2;
            canvas.drawLine(f6 - f, f2, f6 - f3, f4, this.mPaint);
        }
    }

    private void drawBackgroundIfNeeded(Canvas canvas, float f, float f2) {
        if (this.mBackgroundDrawable == null) {
            return;
        }
        this.fBackgroundRect.top = (int) ((canvas.getHeight() - this.mStrokeWidth) / 2.0f);
        this.fBackgroundRect.bottom = (int) ((canvas.getHeight() + this.mStrokeWidth) / 2.0f);
        this.fBackgroundRect.left = 0;
        this.fBackgroundRect.right = this.mMirrorMode ? canvas.getWidth() / 2 : canvas.getWidth();
        this.mBackgroundDrawable.setBounds(this.fBackgroundRect);
        if (!isRunning()) {
            if (this.mMirrorMode) {
                canvas.save();
                canvas.translate(canvas.getWidth() / 2, 0.0f);
                drawBackground(canvas, 0.0f, this.fBackgroundRect.width());
                canvas.scale(-1.0f, 1.0f);
                drawBackground(canvas, 0.0f, this.fBackgroundRect.width());
                canvas.restore();
                return;
            }
            drawBackground(canvas, 0.0f, this.fBackgroundRect.width());
            return;
        }
        if (isFinishing() || isStarting()) {
            if (f > f2) {
                f2 = f;
                f = f2;
            }
            if (f > 0.0f) {
                if (this.mMirrorMode) {
                    canvas.save();
                    canvas.translate(canvas.getWidth() / 2, 0.0f);
                    if (this.mReversed) {
                        drawBackground(canvas, 0.0f, f);
                        canvas.scale(-1.0f, 1.0f);
                        drawBackground(canvas, 0.0f, f);
                    } else {
                        drawBackground(canvas, (canvas.getWidth() / 2) - f, canvas.getWidth() / 2);
                        canvas.scale(-1.0f, 1.0f);
                        drawBackground(canvas, (canvas.getWidth() / 2) - f, canvas.getWidth() / 2);
                    }
                    canvas.restore();
                } else {
                    drawBackground(canvas, 0.0f, f);
                }
            }
            if (f2 <= canvas.getWidth()) {
                if (this.mMirrorMode) {
                    canvas.save();
                    canvas.translate(canvas.getWidth() / 2, 0.0f);
                    if (this.mReversed) {
                        drawBackground(canvas, f2, canvas.getWidth() / 2);
                        canvas.scale(-1.0f, 1.0f);
                        drawBackground(canvas, f2, canvas.getWidth() / 2);
                    } else {
                        drawBackground(canvas, 0.0f, (canvas.getWidth() / 2) - f2);
                        canvas.scale(-1.0f, 1.0f);
                        drawBackground(canvas, 0.0f, (canvas.getWidth() / 2) - f2);
                    }
                    canvas.restore();
                    return;
                }
                drawBackground(canvas, f2, canvas.getWidth());
            }
        }
    }

    private void drawBackground(Canvas canvas, float f, float f2) {
        int iSave = canvas.save();
        canvas.clipRect(f, (int) ((canvas.getHeight() - this.mStrokeWidth) / 2.0f), f2, (int) ((canvas.getHeight() + this.mStrokeWidth) / 2.0f));
        this.mBackgroundDrawable.draw(canvas);
        canvas.restoreToCount(iSave);
    }

    private int incrementColor(int i) {
        int i2 = i + 1;
        if (i2 >= this.mColors.length) {
            return 0;
        }
        return i2;
    }

    private int decrementColor(int i) {
        int i2 = i - 1;
        return i2 < 0 ? this.mColors.length - 1 : i2;
    }

    public void progressiveStart() {
        progressiveStart(0);
    }

    public void progressiveStart(int i) {
        resetProgressiveStart(i);
        start();
    }

    private void resetProgressiveStart(int i) {
        checkColorIndex(i);
        this.mCurrentOffset = 0.0f;
        this.mFinishing = false;
        this.mFinishingOffset = 0.0f;
        this.mStartSection = 0;
        this.mCurrentSections = 0;
        this.mColorsIndex = i;
    }

    public void progressiveStop() {
        this.mFinishing = true;
        this.mStartSection = 0;
    }

    @Override // android.graphics.drawable.Drawable
    public void setAlpha(int i) {
        this.mPaint.setAlpha(i);
    }

    @Override // android.graphics.drawable.Drawable
    public void setColorFilter(ColorFilter colorFilter) {
        this.mPaint.setColorFilter(colorFilter);
    }

    @Override // android.graphics.drawable.Animatable
    public void start() {
        if (this.mProgressiveStartActivated) {
            resetProgressiveStart(0);
        }
        if (isRunning()) {
            return;
        }
        Callbacks callbacks = this.mCallbacks;
        if (callbacks != null) {
            callbacks.onStart();
        }
        scheduleSelf(this.mUpdater, SystemClock.uptimeMillis() + 16);
        invalidateSelf();
    }

    @Override // android.graphics.drawable.Animatable
    public void stop() {
        if (isRunning()) {
            Callbacks callbacks = this.mCallbacks;
            if (callbacks != null) {
                callbacks.onStop();
            }
            this.mRunning = false;
            unscheduleSelf(this.mUpdater);
        }
    }

    @Override // android.graphics.drawable.Drawable
    public void scheduleSelf(Runnable runnable, long j) {
        this.mRunning = true;
        super.scheduleSelf(runnable, j);
    }

    @Override // android.graphics.drawable.Animatable
    public boolean isRunning() {
        return this.mRunning;
    }

    public boolean isStarting() {
        return this.mCurrentSections < this.mSectionsCount;
    }

    public boolean isFinishing() {
        return this.mFinishing;
    }

    public void setCallbacks(Callbacks callbacks) {
        this.mCallbacks = callbacks;
    }

    private void checkColorIndex(int i) {
        if (i < 0 || i >= this.mColors.length) {
            throw new IllegalArgumentException(String.format("Index %d not valid", Integer.valueOf(i)));
        }
    }

    public static class Builder {
        private Drawable mBackgroundDrawableWhenHidden;
        private int[] mColors;
        private boolean mGenerateBackgroundUsingColors;
        private boolean mGradients;
        private Interpolator mInterpolator;
        private boolean mMirrorMode;
        private Callbacks mOnProgressiveStopEndedListener;
        private boolean mProgressiveStartActivated;
        private float mProgressiveStartSpeed;
        private float mProgressiveStopSpeed;
        private boolean mReversed;
        private int mSectionsCount;
        private float mSpeed;
        private int mStrokeSeparatorLength;
        private float mStrokeWidth;

        public Builder(Context context) {
            this(context, false);
        }

        public Builder(Context context, boolean z) {
            initValues(context, z);
        }

        public SmoothProgressDrawable build() {
            if (this.mGenerateBackgroundUsingColors) {
                this.mBackgroundDrawableWhenHidden = SmoothProgressBarUtils.generateDrawableWithColors(this.mColors, this.mStrokeWidth);
            }
            return new SmoothProgressDrawable(this.mInterpolator, this.mSectionsCount, this.mStrokeSeparatorLength, this.mColors, this.mStrokeWidth, this.mSpeed, this.mProgressiveStartSpeed, this.mProgressiveStopSpeed, this.mReversed, this.mMirrorMode, this.mOnProgressiveStopEndedListener, this.mProgressiveStartActivated, this.mBackgroundDrawableWhenHidden, this.mGradients);
        }

        private void initValues(Context context, boolean z) {
            Resources resources = context.getResources();
            this.mInterpolator = new AccelerateInterpolator();
            if (!z) {
                this.mSectionsCount = resources.getInteger(R.integer.spb_default_sections_count);
                this.mSpeed = Float.parseFloat(resources.getString(R.string.spb_default_speed));
                this.mReversed = resources.getBoolean(R.bool.spb_default_reversed);
                this.mProgressiveStartActivated = resources.getBoolean(R.bool.spb_default_progressiveStart_activated);
                this.mColors = new int[]{resources.getColor(R.color.spb_default_color)};
                this.mStrokeSeparatorLength = resources.getDimensionPixelSize(R.dimen.spb_default_stroke_separator_length);
                this.mStrokeWidth = resources.getDimensionPixelOffset(R.dimen.spb_default_stroke_width);
            } else {
                this.mSectionsCount = 4;
                this.mSpeed = 1.0f;
                this.mReversed = false;
                this.mProgressiveStartActivated = false;
                this.mColors = new int[]{Style.holoBlueLight};
                this.mStrokeSeparatorLength = 4;
                this.mStrokeWidth = 4.0f;
            }
            float f = this.mSpeed;
            this.mProgressiveStartSpeed = f;
            this.mProgressiveStopSpeed = f;
            this.mGradients = false;
        }

        public Builder interpolator(Interpolator interpolator) {
            SmoothProgressBarUtils.checkNotNull(interpolator, "Interpolator");
            this.mInterpolator = interpolator;
            return this;
        }

        public Builder sectionsCount(int i) {
            SmoothProgressBarUtils.checkPositive(i, "Sections count");
            this.mSectionsCount = i;
            return this;
        }

        public Builder separatorLength(int i) {
            SmoothProgressBarUtils.checkPositiveOrZero(i, "Separator length");
            this.mStrokeSeparatorLength = i;
            return this;
        }

        public Builder color(int i) {
            this.mColors = new int[]{i};
            return this;
        }

        public Builder colors(int[] iArr) {
            SmoothProgressBarUtils.checkColors(iArr);
            this.mColors = iArr;
            return this;
        }

        public Builder strokeWidth(float f) {
            SmoothProgressBarUtils.checkPositiveOrZero(f, "Width");
            this.mStrokeWidth = f;
            return this;
        }

        public Builder speed(float f) {
            SmoothProgressBarUtils.checkSpeed(f);
            this.mSpeed = f;
            return this;
        }

        public Builder progressiveStartSpeed(float f) {
            SmoothProgressBarUtils.checkSpeed(f);
            this.mProgressiveStartSpeed = f;
            return this;
        }

        public Builder progressiveStopSpeed(float f) {
            SmoothProgressBarUtils.checkSpeed(f);
            this.mProgressiveStopSpeed = f;
            return this;
        }

        public Builder reversed(boolean z) {
            this.mReversed = z;
            return this;
        }

        public Builder mirrorMode(boolean z) {
            this.mMirrorMode = z;
            return this;
        }

        public Builder progressiveStart(boolean z) {
            this.mProgressiveStartActivated = z;
            return this;
        }

        public Builder callbacks(Callbacks callbacks) {
            this.mOnProgressiveStopEndedListener = callbacks;
            return this;
        }

        public Builder backgroundDrawable(Drawable drawable) {
            this.mBackgroundDrawableWhenHidden = drawable;
            return this;
        }

        public Builder generateBackgroundUsingColors() {
            this.mGenerateBackgroundUsingColors = true;
            return this;
        }

        public Builder gradients() {
            return gradients(true);
        }

        public Builder gradients(boolean z) {
            this.mGradients = z;
            return this;
        }
    }
}
