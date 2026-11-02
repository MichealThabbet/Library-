package fr.castorflex.android.smoothprogressbar;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.widget.ProgressBar;
import fr.castorflex.android.smoothprogressbar.SmoothProgressDrawable;

/* loaded from: classes2.dex */
public class SmoothProgressBar extends ProgressBar {
    private static final int INTERPOLATOR_ACCELERATE = 0;
    private static final int INTERPOLATOR_ACCELERATEDECELERATE = 2;
    private static final int INTERPOLATOR_DECELERATE = 3;
    private static final int INTERPOLATOR_LINEAR = 1;

    public SmoothProgressBar(Context context) {
        this(context, null);
    }

    public SmoothProgressBar(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, R.attr.spbStyle);
    }

    public SmoothProgressBar(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        if (isInEditMode()) {
            setIndeterminateDrawable(new SmoothProgressDrawable.Builder(context, true).build());
            return;
        }
        Resources resources = context.getResources();
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.SmoothProgressBar, i, 0);
        int color = typedArrayObtainStyledAttributes.getColor(R.styleable.SmoothProgressBar_spb_color, resources.getColor(R.color.spb_default_color));
        int integer = typedArrayObtainStyledAttributes.getInteger(R.styleable.SmoothProgressBar_spb_sections_count, resources.getInteger(R.integer.spb_default_sections_count));
        int dimensionPixelSize = typedArrayObtainStyledAttributes.getDimensionPixelSize(R.styleable.SmoothProgressBar_spb_stroke_separator_length, resources.getDimensionPixelSize(R.dimen.spb_default_stroke_separator_length));
        float dimension = typedArrayObtainStyledAttributes.getDimension(R.styleable.SmoothProgressBar_spb_stroke_width, resources.getDimension(R.dimen.spb_default_stroke_width));
        float f = typedArrayObtainStyledAttributes.getFloat(R.styleable.SmoothProgressBar_spb_speed, Float.parseFloat(resources.getString(R.string.spb_default_speed)));
        float f2 = typedArrayObtainStyledAttributes.getFloat(R.styleable.SmoothProgressBar_spb_progressiveStart_speed, f);
        float f3 = typedArrayObtainStyledAttributes.getFloat(R.styleable.SmoothProgressBar_spb_progressiveStop_speed, f);
        int integer2 = typedArrayObtainStyledAttributes.getInteger(R.styleable.SmoothProgressBar_spb_interpolator, -1);
        boolean z = typedArrayObtainStyledAttributes.getBoolean(R.styleable.SmoothProgressBar_spb_reversed, resources.getBoolean(R.bool.spb_default_reversed));
        boolean z2 = typedArrayObtainStyledAttributes.getBoolean(R.styleable.SmoothProgressBar_spb_mirror_mode, resources.getBoolean(R.bool.spb_default_mirror_mode));
        int resourceId = typedArrayObtainStyledAttributes.getResourceId(R.styleable.SmoothProgressBar_spb_colors, 0);
        boolean z3 = typedArrayObtainStyledAttributes.getBoolean(R.styleable.SmoothProgressBar_spb_progressiveStart_activated, resources.getBoolean(R.bool.spb_default_progressiveStart_activated));
        Drawable drawable = typedArrayObtainStyledAttributes.getDrawable(R.styleable.SmoothProgressBar_spb_background);
        boolean z4 = typedArrayObtainStyledAttributes.getBoolean(R.styleable.SmoothProgressBar_spb_generate_background_with_colors, false);
        boolean z5 = typedArrayObtainStyledAttributes.getBoolean(R.styleable.SmoothProgressBar_spb_gradients, false);
        typedArrayObtainStyledAttributes.recycle();
        Interpolator interpolator = integer2 == -1 ? getInterpolator() : null;
        if (interpolator == null) {
            if (integer2 == 1) {
                interpolator = new LinearInterpolator();
            } else if (integer2 == 2) {
                interpolator = new AccelerateDecelerateInterpolator();
            } else if (integer2 == 3) {
                interpolator = new DecelerateInterpolator();
            } else {
                interpolator = new AccelerateInterpolator();
            }
        }
        int[] intArray = resourceId != 0 ? resources.getIntArray(resourceId) : null;
        SmoothProgressDrawable.Builder builderGradients = new SmoothProgressDrawable.Builder(context).speed(f).progressiveStartSpeed(f2).progressiveStopSpeed(f3).interpolator(interpolator).sectionsCount(integer).separatorLength(dimensionPixelSize).strokeWidth(dimension).reversed(z).mirrorMode(z2).progressiveStart(z3).gradients(z5);
        if (drawable != null) {
            builderGradients.backgroundDrawable(drawable);
        }
        if (z4) {
            builderGradients.generateBackgroundUsingColors();
        }
        if (intArray != null && intArray.length > 0) {
            builderGradients.colors(intArray);
        } else {
            builderGradients.color(color);
        }
        setIndeterminateDrawable(builderGradients.build());
    }

    public void applyStyle(int i) {
        int resourceId;
        int[] intArray;
        Interpolator accelerateInterpolator = null;
        TypedArray typedArrayObtainStyledAttributes = getContext().obtainStyledAttributes(null, R.styleable.SmoothProgressBar, 0, i);
        if (typedArrayObtainStyledAttributes.hasValue(R.styleable.SmoothProgressBar_spb_color)) {
            setSmoothProgressDrawableColor(typedArrayObtainStyledAttributes.getColor(R.styleable.SmoothProgressBar_spb_color, 0));
        }
        if (typedArrayObtainStyledAttributes.hasValue(R.styleable.SmoothProgressBar_spb_colors) && (resourceId = typedArrayObtainStyledAttributes.getResourceId(R.styleable.SmoothProgressBar_spb_colors, 0)) != 0 && (intArray = getResources().getIntArray(resourceId)) != null && intArray.length > 0) {
            setSmoothProgressDrawableColors(intArray);
        }
        if (typedArrayObtainStyledAttributes.hasValue(R.styleable.SmoothProgressBar_spb_sections_count)) {
            setSmoothProgressDrawableSectionsCount(typedArrayObtainStyledAttributes.getInteger(R.styleable.SmoothProgressBar_spb_sections_count, 0));
        }
        if (typedArrayObtainStyledAttributes.hasValue(R.styleable.SmoothProgressBar_spb_stroke_separator_length)) {
            setSmoothProgressDrawableSeparatorLength(typedArrayObtainStyledAttributes.getDimensionPixelSize(R.styleable.SmoothProgressBar_spb_stroke_separator_length, 0));
        }
        if (typedArrayObtainStyledAttributes.hasValue(R.styleable.SmoothProgressBar_spb_stroke_width)) {
            setSmoothProgressDrawableStrokeWidth(typedArrayObtainStyledAttributes.getDimension(R.styleable.SmoothProgressBar_spb_stroke_width, 0.0f));
        }
        if (typedArrayObtainStyledAttributes.hasValue(R.styleable.SmoothProgressBar_spb_speed)) {
            setSmoothProgressDrawableSpeed(typedArrayObtainStyledAttributes.getFloat(R.styleable.SmoothProgressBar_spb_speed, 0.0f));
        }
        if (typedArrayObtainStyledAttributes.hasValue(R.styleable.SmoothProgressBar_spb_progressiveStart_speed)) {
            setSmoothProgressDrawableProgressiveStartSpeed(typedArrayObtainStyledAttributes.getFloat(R.styleable.SmoothProgressBar_spb_progressiveStart_speed, 0.0f));
        }
        if (typedArrayObtainStyledAttributes.hasValue(R.styleable.SmoothProgressBar_spb_progressiveStop_speed)) {
            setSmoothProgressDrawableProgressiveStopSpeed(typedArrayObtainStyledAttributes.getFloat(R.styleable.SmoothProgressBar_spb_progressiveStop_speed, 0.0f));
        }
        if (typedArrayObtainStyledAttributes.hasValue(R.styleable.SmoothProgressBar_spb_reversed)) {
            setSmoothProgressDrawableReversed(typedArrayObtainStyledAttributes.getBoolean(R.styleable.SmoothProgressBar_spb_reversed, false));
        }
        if (typedArrayObtainStyledAttributes.hasValue(R.styleable.SmoothProgressBar_spb_mirror_mode)) {
            setSmoothProgressDrawableMirrorMode(typedArrayObtainStyledAttributes.getBoolean(R.styleable.SmoothProgressBar_spb_mirror_mode, false));
        }
        if (typedArrayObtainStyledAttributes.hasValue(R.styleable.SmoothProgressBar_spb_progressiveStart_activated)) {
            setProgressiveStartActivated(typedArrayObtainStyledAttributes.getBoolean(R.styleable.SmoothProgressBar_spb_progressiveStart_activated, false));
        }
        if (typedArrayObtainStyledAttributes.hasValue(R.styleable.SmoothProgressBar_spb_progressiveStart_activated)) {
            setProgressiveStartActivated(typedArrayObtainStyledAttributes.getBoolean(R.styleable.SmoothProgressBar_spb_progressiveStart_activated, false));
        }
        if (typedArrayObtainStyledAttributes.hasValue(R.styleable.SmoothProgressBar_spb_gradients)) {
            setSmoothProgressDrawableUseGradients(typedArrayObtainStyledAttributes.getBoolean(R.styleable.SmoothProgressBar_spb_gradients, false));
        }
        if (typedArrayObtainStyledAttributes.hasValue(R.styleable.SmoothProgressBar_spb_generate_background_with_colors) && typedArrayObtainStyledAttributes.getBoolean(R.styleable.SmoothProgressBar_spb_generate_background_with_colors, false)) {
            setSmoothProgressDrawableBackgroundDrawable(SmoothProgressBarUtils.generateDrawableWithColors(checkIndeterminateDrawable().getColors(), checkIndeterminateDrawable().getStrokeWidth()));
        }
        if (typedArrayObtainStyledAttributes.hasValue(R.styleable.SmoothProgressBar_spb_interpolator)) {
            int integer = typedArrayObtainStyledAttributes.getInteger(R.styleable.SmoothProgressBar_spb_interpolator, -1);
            if (integer == 0) {
                accelerateInterpolator = new AccelerateInterpolator();
            } else if (integer == 1) {
                accelerateInterpolator = new LinearInterpolator();
            } else if (integer == 2) {
                accelerateInterpolator = new AccelerateDecelerateInterpolator();
            } else if (integer == 3) {
                accelerateInterpolator = new DecelerateInterpolator();
            }
            if (accelerateInterpolator != null) {
                setInterpolator(accelerateInterpolator);
            }
        }
        typedArrayObtainStyledAttributes.recycle();
    }

    @Override // android.widget.ProgressBar, android.view.View
    protected synchronized void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (isIndeterminate() && (getIndeterminateDrawable() instanceof SmoothProgressDrawable) && !((SmoothProgressDrawable) getIndeterminateDrawable()).isRunning()) {
            getIndeterminateDrawable().draw(canvas);
        }
    }

    private SmoothProgressDrawable checkIndeterminateDrawable() {
        Drawable indeterminateDrawable = getIndeterminateDrawable();
        if (indeterminateDrawable == null || !(indeterminateDrawable instanceof SmoothProgressDrawable)) {
            throw new RuntimeException("The drawable is not a SmoothProgressDrawable");
        }
        return (SmoothProgressDrawable) indeterminateDrawable;
    }

    @Override // android.widget.ProgressBar
    public void setInterpolator(Interpolator interpolator) {
        super.setInterpolator(interpolator);
        Drawable indeterminateDrawable = getIndeterminateDrawable();
        if (indeterminateDrawable == null || !(indeterminateDrawable instanceof SmoothProgressDrawable)) {
            return;
        }
        ((SmoothProgressDrawable) indeterminateDrawable).setInterpolator(interpolator);
    }

    public void setSmoothProgressDrawableInterpolator(Interpolator interpolator) {
        checkIndeterminateDrawable().setInterpolator(interpolator);
    }

    public void setSmoothProgressDrawableColors(int[] iArr) {
        checkIndeterminateDrawable().setColors(iArr);
    }

    public void setSmoothProgressDrawableColor(int i) {
        checkIndeterminateDrawable().setColor(i);
    }

    public void setSmoothProgressDrawableSpeed(float f) {
        checkIndeterminateDrawable().setSpeed(f);
    }

    public void setSmoothProgressDrawableProgressiveStartSpeed(float f) {
        checkIndeterminateDrawable().setProgressiveStartSpeed(f);
    }

    public void setSmoothProgressDrawableProgressiveStopSpeed(float f) {
        checkIndeterminateDrawable().setProgressiveStopSpeed(f);
    }

    public void setSmoothProgressDrawableSectionsCount(int i) {
        checkIndeterminateDrawable().setSectionsCount(i);
    }

    public void setSmoothProgressDrawableSeparatorLength(int i) {
        checkIndeterminateDrawable().setSeparatorLength(i);
    }

    public void setSmoothProgressDrawableStrokeWidth(float f) {
        checkIndeterminateDrawable().setStrokeWidth(f);
    }

    public void setSmoothProgressDrawableReversed(boolean z) {
        checkIndeterminateDrawable().setReversed(z);
    }

    public void setSmoothProgressDrawableMirrorMode(boolean z) {
        checkIndeterminateDrawable().setMirrorMode(z);
    }

    public void setProgressiveStartActivated(boolean z) {
        checkIndeterminateDrawable().setProgressiveStartActivated(z);
    }

    public void setSmoothProgressDrawableCallbacks(SmoothProgressDrawable.Callbacks callbacks) {
        checkIndeterminateDrawable().setCallbacks(callbacks);
    }

    public void setSmoothProgressDrawableBackgroundDrawable(Drawable drawable) {
        checkIndeterminateDrawable().setBackgroundDrawable(drawable);
    }

    public void setSmoothProgressDrawableUseGradients(boolean z) {
        checkIndeterminateDrawable().setUseGradients(z);
    }

    public void progressiveStart() {
        checkIndeterminateDrawable().progressiveStart();
    }

    public void progressiveStop() {
        checkIndeterminateDrawable().progressiveStop();
    }
}
