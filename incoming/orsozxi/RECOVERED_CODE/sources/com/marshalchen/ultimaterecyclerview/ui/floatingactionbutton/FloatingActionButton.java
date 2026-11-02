package com.marshalchen.ultimaterecyclerview.ui.floatingactionbutton;

import android.R;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Build;
import android.util.AttributeSet;
import android.view.Display;
import android.view.WindowManager;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Interpolator;
import android.widget.ImageButton;
import androidx.core.view.ViewCompat;
import com.ibm.icu.text.DateFormat;
import com.nineoldandroids.animation.ObjectAnimator;
import com.nineoldandroids.view.ViewHelper;

/* loaded from: classes2.dex */
public class FloatingActionButton extends ImageButton {
    public static final int SIZE_MINI = 1;
    public static final int SIZE_NORMAL = 0;
    public static final int SIZE_NOSHADOW = 2;
    protected float mCircleSize;
    protected int mColorNormal;
    protected int mColorPressed;
    protected int mDrawableSize;
    private boolean mHidden;
    protected int mIcon;
    private final Interpolator mInterpolator;
    protected float mShadowOffset;
    protected float mShadowRadius;
    protected int mSize;
    private float mYDisplayed;
    private float mYHidden;
    private static final int HALF_TRANSPARENT_WHITE = Color.argb(128, 255, 255, 255);
    private static final int HALF_TRANSPARENT_BLACK = Color.argb(128, 0, 0, 0);

    protected void load_extended_attributes(TypedArray typedArray) {
    }

    protected int opacityToAlpha(float f) {
        return (int) (f * 255.0f);
    }

    public FloatingActionButton(Context context) {
        this(context, null);
    }

    public FloatingActionButton(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mInterpolator = new AccelerateDecelerateInterpolator();
        this.mHidden = false;
        this.mYDisplayed = -1.0f;
        this.mYHidden = -1.0f;
        init(context, attributeSet);
    }

    public FloatingActionButton(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mInterpolator = new AccelerateDecelerateInterpolator();
        this.mHidden = false;
        this.mYDisplayed = -1.0f;
        this.mYHidden = -1.0f;
        init(context, attributeSet);
    }

    protected void init(Context context, AttributeSet attributeSet) {
        this.mColorNormal = getColor(R.color.holo_blue_dark);
        this.mColorPressed = getColor(R.color.holo_blue_light);
        this.mIcon = 0;
        this.mSize = 0;
        if (attributeSet != null) {
            initAttributes(context, attributeSet);
        }
        this.mCircleSize = getCircleSize(this.mSize);
        this.mShadowRadius = getDimension(com.marshalchen.ultimaterecyclerview.R.dimen.fab_shadow_radius);
        this.mShadowOffset = getDimension(com.marshalchen.ultimaterecyclerview.R.dimen.fab_shadow_offset);
        this.mDrawableSize = (int) (this.mCircleSize + (this.mShadowRadius * 2.0f));
        Display defaultDisplay = ((WindowManager) context.getSystemService("window")).getDefaultDisplay();
        Point point = new Point();
        if (Build.VERSION.SDK_INT >= 13) {
            defaultDisplay.getSize(point);
            this.mYHidden = point.y;
        } else {
            this.mYHidden = defaultDisplay.getHeight();
        }
        updateBackground();
    }

    protected void updateBackground() {
        float f = this.mShadowRadius;
        float f2 = f - this.mShadowOffset;
        float f3 = this.mCircleSize;
        LayerDrawable layerDrawableGenerateFinalDrawables = generateFinalDrawables(new RectF(f, f2, f + f3, f3 + f2));
        float dimension = (this.mCircleSize - getDimension(com.marshalchen.ultimaterecyclerview.R.dimen.fab_icon_size)) / 2.0f;
        float f4 = this.mShadowRadius;
        int i = (int) (f4 + dimension);
        layerDrawableGenerateFinalDrawables.setLayerInset(layerDrawableGenerateFinalDrawables.getNumberOfLayers() - 1, i, (int) (f2 + dimension), i, (int) (f4 + this.mShadowOffset + dimension));
        setBackgroundCompat(layerDrawableGenerateFinalDrawables);
    }

    protected int getColor(int i) {
        return getResources().getColor(i);
    }

    protected float getDimension(int i) {
        return getResources().getDimension(i);
    }

    protected void initAttributes(Context context, AttributeSet attributeSet) {
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, com.marshalchen.ultimaterecyclerview.R.styleable.FloatActionButton, 0, 0);
        if (typedArrayObtainStyledAttributes != null) {
            try {
                this.mColorNormal = typedArrayObtainStyledAttributes.getColor(com.marshalchen.ultimaterecyclerview.R.styleable.FloatActionButton_urv_fab_colorNormal, getColor(R.color.holo_blue_dark));
                this.mColorPressed = typedArrayObtainStyledAttributes.getColor(com.marshalchen.ultimaterecyclerview.R.styleable.FloatActionButton_urv_fab_colorPressed, getColor(R.color.holo_blue_light));
                this.mSize = typedArrayObtainStyledAttributes.getInt(com.marshalchen.ultimaterecyclerview.R.styleable.FloatActionButton_urv_fab_size, 0);
                this.mIcon = typedArrayObtainStyledAttributes.getResourceId(com.marshalchen.ultimaterecyclerview.R.styleable.FloatActionButton_icon, 0);
                load_extended_attributes(typedArrayObtainStyledAttributes);
            } finally {
                typedArrayObtainStyledAttributes.recycle();
            }
        }
    }

    @Override // android.widget.ImageView, android.view.View
    protected void onMeasure(int i, int i2) {
        super.onMeasure(i, i2);
        int i3 = this.mDrawableSize;
        setMeasuredDimension(i3, i3);
    }

    protected float getCircleSize(int i) {
        if (i == 0) {
            return getDimension(com.marshalchen.ultimaterecyclerview.R.dimen.fab_size_normal);
        }
        if (i == 1) {
            return getDimension(com.marshalchen.ultimaterecyclerview.R.dimen.fab_size_mini);
        }
        if (i == 2) {
            return getDimension(com.marshalchen.ultimaterecyclerview.R.dimen.fab_size_normal);
        }
        return getDimension(com.marshalchen.ultimaterecyclerview.R.dimen.fab_size_normal);
    }

    protected int getDrawableBySize(int i) {
        if (i == 0) {
            return com.marshalchen.ultimaterecyclerview.R.drawable.urv_floating_action_button_fab_bg_normal;
        }
        if (i != 1) {
            return -1;
        }
        return com.marshalchen.ultimaterecyclerview.R.drawable.urv_floating_action_button_fab_bg_mini;
    }

    protected LayerDrawable generateFinalDrawables(RectF rectF) {
        if (this.mSize == 2) {
            return new LayerDrawable(new Drawable[]{createFillDrawable(rectF), createStrokesDrawable(rectF), getIconDrawable()});
        }
        return new LayerDrawable(new Drawable[]{getResources().getDrawable(getDrawableBySize(this.mSize)), createFillDrawable(rectF), createStrokesDrawable(rectF), getIconDrawable()});
    }

    protected Drawable getIconDrawable() {
        if (this.mIcon != 0) {
            return getResources().getDrawable(this.mIcon);
        }
        return new ColorDrawable(0);
    }

    protected StateListDrawable createFillDrawable(RectF rectF, float f) {
        StateListDrawable stateListDrawable = new StateListDrawable();
        stateListDrawable.addState(new int[]{R.attr.state_pressed}, createAlphaDrawble(rectF, this.mColorPressed, f));
        stateListDrawable.addState(new int[0], createAlphaDrawble(rectF, this.mColorNormal, f));
        return stateListDrawable;
    }

    protected StateListDrawable createFillDrawable(RectF rectF) {
        StateListDrawable stateListDrawable = new StateListDrawable();
        stateListDrawable.addState(new int[]{R.attr.state_pressed}, createCircleDrawable(rectF, this.mColorPressed));
        stateListDrawable.addState(new int[0], createCircleDrawable(rectF, this.mColorNormal));
        return stateListDrawable;
    }

    protected Drawable createCircleDrawable(RectF rectF, int i) {
        int i2 = this.mDrawableSize;
        Bitmap bitmapCreateBitmap = Bitmap.createBitmap(i2, i2, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmapCreateBitmap);
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(i);
        canvas.drawOval(rectF, paint);
        return new BitmapDrawable(getResources(), bitmapCreateBitmap);
    }

    protected Drawable createAlphaDrawble(RectF rectF, int i, float f) {
        int i2 = this.mDrawableSize;
        Bitmap bitmapCreateBitmap = Bitmap.createBitmap(i2, i2, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmapCreateBitmap);
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(i);
        paint.setAlpha(opacityToAlpha(f));
        canvas.drawOval(rectF, paint);
        return new BitmapDrawable(getResources(), bitmapCreateBitmap);
    }

    protected Drawable createStrokesDrawable(RectF rectF) {
        int i = this.mDrawableSize;
        Bitmap bitmapCreateBitmap = Bitmap.createBitmap(i, i, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmapCreateBitmap);
        float dimension = getDimension(com.marshalchen.ultimaterecyclerview.R.dimen.fab_stroke_width);
        float f = dimension / 2.0f;
        RectF rectF2 = new RectF(rectF.left - f, rectF.top - f, rectF.right + f, rectF.bottom + f);
        RectF rectF3 = new RectF(rectF.left + f, rectF.top + f, rectF.right - f, rectF.bottom - f);
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setStrokeWidth(dimension);
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(ViewCompat.MEASURED_STATE_MASK);
        paint.setAlpha(opacityToAlpha(0.02f));
        canvas.drawOval(rectF2, paint);
        paint.setShader(new LinearGradient(rectF3.centerX(), rectF3.top, rectF3.centerX(), rectF3.bottom, new int[]{0, HALF_TRANSPARENT_BLACK, ViewCompat.MEASURED_STATE_MASK}, new float[]{0.0f, 0.8f, 1.0f}, Shader.TileMode.CLAMP));
        paint.setAlpha(opacityToAlpha(0.04f));
        canvas.drawOval(rectF3, paint);
        paint.setShader(new LinearGradient(rectF3.centerX(), rectF3.top, rectF3.centerX(), rectF3.bottom, new int[]{-1, HALF_TRANSPARENT_WHITE, 0}, new float[]{0.0f, 0.2f, 1.0f}, Shader.TileMode.CLAMP));
        paint.setAlpha(opacityToAlpha(0.8f));
        canvas.drawOval(rectF3, paint);
        return new BitmapDrawable(getResources(), bitmapCreateBitmap);
    }

    private void setBackgroundCompat(Drawable drawable) {
        if (Build.VERSION.SDK_INT >= 16) {
            setBackground(drawable);
        } else {
            setBackgroundDrawable(drawable);
        }
    }

    public void hide(boolean z) {
        if (this.mHidden != z) {
            this.mHidden = z;
            float[] fArr = new float[1];
            fArr[0] = z ? this.mYHidden : this.mYDisplayed;
            ObjectAnimator duration = ObjectAnimator.ofFloat(this, DateFormat.YEAR, fArr).setDuration(500L);
            duration.setInterpolator(this.mInterpolator);
            duration.start();
        }
    }

    @Override // android.view.View
    protected void onLayout(boolean z, int i, int i2, int i3, int i4) {
        super.onLayout(z, i, i2, i3, i4);
        if (this.mYDisplayed == -1.0f) {
            this.mYDisplayed = ViewHelper.getY(this);
        }
    }
}
