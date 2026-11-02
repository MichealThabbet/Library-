package com.larswerkman.holocolorpicker;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.SweepGradient;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import androidx.core.internal.view.SupportMenu;
import androidx.core.view.InputDeviceCompat;
import androidx.core.view.ViewCompat;

/* loaded from: classes2.dex */
public class ColorPicker extends View {
    private static final int[] COLORS = {SupportMenu.CATEGORY_MASK, -65281, -16776961, -16711681, -16711936, InputDeviceCompat.SOURCE_ANY, SupportMenu.CATEGORY_MASK};
    private static final String STATE_ANGLE = "angle";
    private static final String STATE_OLD_COLOR = "color";
    private static final String STATE_PARENT = "parent";
    private static final String STATE_SHOW_OLD_COLOR = "showColor";
    private float mAngle;
    private Paint mCenterHaloPaint;
    private int mCenterNewColor;
    private Paint mCenterNewPaint;
    private int mCenterOldColor;
    private Paint mCenterOldPaint;
    private RectF mCenterRectangle;
    private int mColor;
    private int mColorCenterHaloRadius;
    private int mColorCenterRadius;
    private int mColorPointerHaloRadius;
    private int mColorPointerRadius;
    private Paint mColorWheelPaint;
    private int mColorWheelRadius;
    private RectF mColorWheelRectangle;
    private int mColorWheelThickness;
    private float[] mHSV;
    private OpacityBar mOpacityBar;
    private Paint mPointerColor;
    private Paint mPointerHaloPaint;
    private int mPreferredColorCenterHaloRadius;
    private int mPreferredColorCenterRadius;
    private int mPreferredColorWheelRadius;
    private SVBar mSVbar;
    private SaturationBar mSaturationBar;
    private boolean mShowCenterOldColor;
    private float mSlopX;
    private float mSlopY;
    private float mTranslationOffset;
    private boolean mUserIsMovingPointer;
    private ValueBar mValueBar;
    private int oldChangedListenerColor;
    private int oldSelectedListenerColor;
    private OnColorChangedListener onColorChangedListener;
    private OnColorSelectedListener onColorSelectedListener;

    public interface OnColorChangedListener {
        void onColorChanged(int i);
    }

    public interface OnColorSelectedListener {
        void onColorSelected(int i);
    }

    public ColorPicker(Context context) {
        super(context);
        this.mColorWheelRectangle = new RectF();
        this.mCenterRectangle = new RectF();
        this.mUserIsMovingPointer = false;
        this.mHSV = new float[3];
        this.mSVbar = null;
        this.mOpacityBar = null;
        this.mSaturationBar = null;
        this.mValueBar = null;
        init(null, 0);
    }

    public ColorPicker(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mColorWheelRectangle = new RectF();
        this.mCenterRectangle = new RectF();
        this.mUserIsMovingPointer = false;
        this.mHSV = new float[3];
        this.mSVbar = null;
        this.mOpacityBar = null;
        this.mSaturationBar = null;
        this.mValueBar = null;
        init(attributeSet, 0);
    }

    public ColorPicker(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mColorWheelRectangle = new RectF();
        this.mCenterRectangle = new RectF();
        this.mUserIsMovingPointer = false;
        this.mHSV = new float[3];
        this.mSVbar = null;
        this.mOpacityBar = null;
        this.mSaturationBar = null;
        this.mValueBar = null;
        init(attributeSet, i);
    }

    public void setOnColorChangedListener(OnColorChangedListener onColorChangedListener) {
        this.onColorChangedListener = onColorChangedListener;
    }

    public OnColorChangedListener getOnColorChangedListener() {
        return this.onColorChangedListener;
    }

    public void setOnColorSelectedListener(OnColorSelectedListener onColorSelectedListener) {
        this.onColorSelectedListener = onColorSelectedListener;
    }

    public OnColorSelectedListener getOnColorSelectedListener() {
        return this.onColorSelectedListener;
    }

    private void init(AttributeSet attributeSet, int i) {
        TypedArray typedArrayObtainStyledAttributes = getContext().obtainStyledAttributes(attributeSet, R.styleable.ColorPicker, i, 0);
        Resources resources = getContext().getResources();
        this.mColorWheelThickness = typedArrayObtainStyledAttributes.getDimensionPixelSize(1, resources.getDimensionPixelSize(R.dimen.color_wheel_thickness));
        int dimensionPixelSize = typedArrayObtainStyledAttributes.getDimensionPixelSize(0, resources.getDimensionPixelSize(R.dimen.color_wheel_radius));
        this.mColorWheelRadius = dimensionPixelSize;
        this.mPreferredColorWheelRadius = dimensionPixelSize;
        int dimensionPixelSize2 = typedArrayObtainStyledAttributes.getDimensionPixelSize(2, resources.getDimensionPixelSize(R.dimen.color_center_radius));
        this.mColorCenterRadius = dimensionPixelSize2;
        this.mPreferredColorCenterRadius = dimensionPixelSize2;
        int dimensionPixelSize3 = typedArrayObtainStyledAttributes.getDimensionPixelSize(3, resources.getDimensionPixelSize(R.dimen.color_center_halo_radius));
        this.mColorCenterHaloRadius = dimensionPixelSize3;
        this.mPreferredColorCenterHaloRadius = dimensionPixelSize3;
        this.mColorPointerRadius = typedArrayObtainStyledAttributes.getDimensionPixelSize(4, resources.getDimensionPixelSize(R.dimen.color_pointer_radius));
        this.mColorPointerHaloRadius = typedArrayObtainStyledAttributes.getDimensionPixelSize(5, resources.getDimensionPixelSize(R.dimen.color_pointer_halo_radius));
        typedArrayObtainStyledAttributes.recycle();
        this.mAngle = -1.5707964f;
        SweepGradient sweepGradient = new SweepGradient(0.0f, 0.0f, COLORS, (float[]) null);
        Paint paint = new Paint(1);
        this.mColorWheelPaint = paint;
        paint.setShader(sweepGradient);
        this.mColorWheelPaint.setStyle(Paint.Style.STROKE);
        this.mColorWheelPaint.setStrokeWidth(this.mColorWheelThickness);
        Paint paint2 = new Paint(1);
        this.mPointerHaloPaint = paint2;
        paint2.setColor(ViewCompat.MEASURED_STATE_MASK);
        this.mPointerHaloPaint.setAlpha(80);
        Paint paint3 = new Paint(1);
        this.mPointerColor = paint3;
        paint3.setColor(calculateColor(this.mAngle));
        Paint paint4 = new Paint(1);
        this.mCenterNewPaint = paint4;
        paint4.setColor(calculateColor(this.mAngle));
        this.mCenterNewPaint.setStyle(Paint.Style.FILL);
        Paint paint5 = new Paint(1);
        this.mCenterOldPaint = paint5;
        paint5.setColor(calculateColor(this.mAngle));
        this.mCenterOldPaint.setStyle(Paint.Style.FILL);
        Paint paint6 = new Paint(1);
        this.mCenterHaloPaint = paint6;
        paint6.setColor(ViewCompat.MEASURED_STATE_MASK);
        this.mCenterHaloPaint.setAlpha(0);
        this.mCenterNewColor = calculateColor(this.mAngle);
        this.mCenterOldColor = calculateColor(this.mAngle);
        this.mShowCenterOldColor = true;
    }

    @Override // android.view.View
    protected void onDraw(Canvas canvas) {
        float f = this.mTranslationOffset;
        canvas.translate(f, f);
        canvas.drawOval(this.mColorWheelRectangle, this.mColorWheelPaint);
        float[] fArrCalculatePointerPosition = calculatePointerPosition(this.mAngle);
        canvas.drawCircle(fArrCalculatePointerPosition[0], fArrCalculatePointerPosition[1], this.mColorPointerHaloRadius, this.mPointerHaloPaint);
        canvas.drawCircle(fArrCalculatePointerPosition[0], fArrCalculatePointerPosition[1], this.mColorPointerRadius, this.mPointerColor);
        canvas.drawCircle(0.0f, 0.0f, this.mColorCenterHaloRadius, this.mCenterHaloPaint);
        if (this.mShowCenterOldColor) {
            canvas.drawArc(this.mCenterRectangle, 90.0f, 180.0f, true, this.mCenterOldPaint);
            canvas.drawArc(this.mCenterRectangle, 270.0f, 180.0f, true, this.mCenterNewPaint);
        } else {
            canvas.drawArc(this.mCenterRectangle, 0.0f, 360.0f, true, this.mCenterNewPaint);
        }
    }

    @Override // android.view.View
    protected void onMeasure(int i, int i2) {
        int iMin = (this.mPreferredColorWheelRadius + this.mColorPointerHaloRadius) * 2;
        int mode = View.MeasureSpec.getMode(i);
        int size = View.MeasureSpec.getSize(i);
        int mode2 = View.MeasureSpec.getMode(i2);
        int size2 = View.MeasureSpec.getSize(i2);
        if (mode != 1073741824) {
            size = mode == Integer.MIN_VALUE ? Math.min(iMin, size) : iMin;
        }
        if (mode2 == 1073741824) {
            iMin = size2;
        } else if (mode2 == Integer.MIN_VALUE) {
            iMin = Math.min(iMin, size2);
        }
        int iMin2 = Math.min(size, iMin);
        setMeasuredDimension(iMin2, iMin2);
        this.mTranslationOffset = iMin2 * 0.5f;
        int i3 = ((iMin2 / 2) - this.mColorWheelThickness) - this.mColorPointerHaloRadius;
        this.mColorWheelRadius = i3;
        this.mColorWheelRectangle.set(-i3, -i3, i3, i3);
        float f = this.mPreferredColorCenterRadius;
        int i4 = this.mColorWheelRadius;
        int i5 = this.mPreferredColorWheelRadius;
        int i6 = (int) (f * (i4 / i5));
        this.mColorCenterRadius = i6;
        this.mColorCenterHaloRadius = (int) (this.mPreferredColorCenterHaloRadius * (i4 / i5));
        this.mCenterRectangle.set(-i6, -i6, i6, i6);
    }

    private int ave(int i, int i2, float f) {
        return i + Math.round(f * (i2 - i));
    }

    private int calculateColor(float f) {
        float f2 = (float) (f / 6.283185307179586d);
        if (f2 < 0.0f) {
            f2 += 1.0f;
        }
        if (f2 <= 0.0f) {
            int[] iArr = COLORS;
            this.mColor = iArr[0];
            return iArr[0];
        }
        if (f2 >= 1.0f) {
            int[] iArr2 = COLORS;
            this.mColor = iArr2[iArr2.length - 1];
            return iArr2[iArr2.length - 1];
        }
        int[] iArr3 = COLORS;
        float length = f2 * (iArr3.length - 1);
        int i = (int) length;
        float f3 = length - i;
        int i2 = iArr3[i];
        int i3 = iArr3[i + 1];
        int iAve = ave(Color.alpha(i2), Color.alpha(i3), f3);
        int iAve2 = ave(Color.red(i2), Color.red(i3), f3);
        int iAve3 = ave(Color.green(i2), Color.green(i3), f3);
        int iAve4 = ave(Color.blue(i2), Color.blue(i3), f3);
        this.mColor = Color.argb(iAve, iAve2, iAve3, iAve4);
        return Color.argb(iAve, iAve2, iAve3, iAve4);
    }

    public int getColor() {
        return this.mCenterNewColor;
    }

    public void setColor(int i) {
        float fColorToAngle = colorToAngle(i);
        this.mAngle = fColorToAngle;
        this.mPointerColor.setColor(calculateColor(fColorToAngle));
        this.mCenterNewPaint.setColor(calculateColor(this.mAngle));
        OpacityBar opacityBar = this.mOpacityBar;
        if (opacityBar != null) {
            opacityBar.setColor(this.mColor);
            this.mOpacityBar.setOpacity(Color.alpha(i));
        }
        if (this.mSVbar != null) {
            Color.colorToHSV(i, this.mHSV);
            this.mSVbar.setColor(this.mColor);
            float[] fArr = this.mHSV;
            if (fArr[1] < fArr[2]) {
                this.mSVbar.setSaturation(fArr[1]);
            } else {
                this.mSVbar.setValue(fArr[2]);
            }
        }
        if (this.mSaturationBar != null) {
            Color.colorToHSV(i, this.mHSV);
            this.mSaturationBar.setColor(this.mColor);
            this.mSaturationBar.setSaturation(this.mHSV[1]);
        }
        if (this.mValueBar != null && this.mSaturationBar == null) {
            Color.colorToHSV(i, this.mHSV);
            this.mValueBar.setColor(this.mColor);
            this.mValueBar.setValue(this.mHSV[2]);
        } else if (this.mValueBar != null) {
            Color.colorToHSV(i, this.mHSV);
            this.mValueBar.setValue(this.mHSV[2]);
        }
        invalidate();
    }

    private float colorToAngle(int i) {
        Color.colorToHSV(i, new float[3]);
        return (float) Math.toRadians(-r0[0]);
    }

    @Override // android.view.View
    public boolean onTouchEvent(MotionEvent motionEvent) {
        int i;
        OnColorSelectedListener onColorSelectedListener;
        int i2;
        getParent().requestDisallowInterceptTouchEvent(true);
        float x = motionEvent.getX() - this.mTranslationOffset;
        float y = motionEvent.getY() - this.mTranslationOffset;
        int action = motionEvent.getAction();
        if (action == 0) {
            float[] fArrCalculatePointerPosition = calculatePointerPosition(this.mAngle);
            float f = fArrCalculatePointerPosition[0];
            int i3 = this.mColorPointerHaloRadius;
            if (x >= f - i3 && x <= fArrCalculatePointerPosition[0] + i3 && y >= fArrCalculatePointerPosition[1] - i3 && y <= fArrCalculatePointerPosition[1] + i3) {
                this.mSlopX = x - fArrCalculatePointerPosition[0];
                this.mSlopY = y - fArrCalculatePointerPosition[1];
                this.mUserIsMovingPointer = true;
                invalidate();
            } else {
                int i4 = this.mColorCenterRadius;
                if (x >= (-i4) && x <= i4 && y >= (-i4) && y <= i4 && this.mShowCenterOldColor) {
                    this.mCenterHaloPaint.setAlpha(80);
                    setColor(getOldCenterColor());
                    invalidate();
                } else {
                    getParent().requestDisallowInterceptTouchEvent(false);
                    return false;
                }
            }
        } else if (action == 1) {
            this.mUserIsMovingPointer = false;
            this.mCenterHaloPaint.setAlpha(0);
            OnColorSelectedListener onColorSelectedListener2 = this.onColorSelectedListener;
            if (onColorSelectedListener2 != null && (i = this.mCenterNewColor) != this.oldSelectedListenerColor) {
                onColorSelectedListener2.onColorSelected(i);
                this.oldSelectedListenerColor = this.mCenterNewColor;
            }
            invalidate();
        } else if (action != 2) {
            if (action == 3 && (onColorSelectedListener = this.onColorSelectedListener) != null && (i2 = this.mCenterNewColor) != this.oldSelectedListenerColor) {
                onColorSelectedListener.onColorSelected(i2);
                this.oldSelectedListenerColor = this.mCenterNewColor;
            }
        } else if (this.mUserIsMovingPointer) {
            float fAtan2 = (float) Math.atan2(y - this.mSlopY, x - this.mSlopX);
            this.mAngle = fAtan2;
            this.mPointerColor.setColor(calculateColor(fAtan2));
            int iCalculateColor = calculateColor(this.mAngle);
            this.mCenterNewColor = iCalculateColor;
            setNewCenterColor(iCalculateColor);
            OpacityBar opacityBar = this.mOpacityBar;
            if (opacityBar != null) {
                opacityBar.setColor(this.mColor);
            }
            ValueBar valueBar = this.mValueBar;
            if (valueBar != null) {
                valueBar.setColor(this.mColor);
            }
            SaturationBar saturationBar = this.mSaturationBar;
            if (saturationBar != null) {
                saturationBar.setColor(this.mColor);
            }
            SVBar sVBar = this.mSVbar;
            if (sVBar != null) {
                sVBar.setColor(this.mColor);
            }
            invalidate();
        } else {
            getParent().requestDisallowInterceptTouchEvent(false);
            return false;
        }
        return true;
    }

    private float[] calculatePointerPosition(float f) {
        double d = f;
        return new float[]{(float) (this.mColorWheelRadius * Math.cos(d)), (float) (this.mColorWheelRadius * Math.sin(d))};
    }

    public void addSVBar(SVBar sVBar) {
        this.mSVbar = sVBar;
        sVBar.setColorPicker(this);
        this.mSVbar.setColor(this.mColor);
    }

    public void addOpacityBar(OpacityBar opacityBar) {
        this.mOpacityBar = opacityBar;
        opacityBar.setColorPicker(this);
        this.mOpacityBar.setColor(this.mColor);
    }

    public void addSaturationBar(SaturationBar saturationBar) {
        this.mSaturationBar = saturationBar;
        saturationBar.setColorPicker(this);
        this.mSaturationBar.setColor(this.mColor);
    }

    public void addValueBar(ValueBar valueBar) {
        this.mValueBar = valueBar;
        valueBar.setColorPicker(this);
        this.mValueBar.setColor(this.mColor);
    }

    public void setNewCenterColor(int i) {
        this.mCenterNewColor = i;
        this.mCenterNewPaint.setColor(i);
        if (this.mCenterOldColor == 0) {
            this.mCenterOldColor = i;
            this.mCenterOldPaint.setColor(i);
        }
        OnColorChangedListener onColorChangedListener = this.onColorChangedListener;
        if (onColorChangedListener != null && i != this.oldChangedListenerColor) {
            onColorChangedListener.onColorChanged(i);
            this.oldChangedListenerColor = i;
        }
        invalidate();
    }

    public void setOldCenterColor(int i) {
        this.mCenterOldColor = i;
        this.mCenterOldPaint.setColor(i);
        invalidate();
    }

    public int getOldCenterColor() {
        return this.mCenterOldColor;
    }

    public void setShowOldCenterColor(boolean z) {
        this.mShowCenterOldColor = z;
        invalidate();
    }

    public boolean getShowOldCenterColor() {
        return this.mShowCenterOldColor;
    }

    public void changeOpacityBarColor(int i) {
        OpacityBar opacityBar = this.mOpacityBar;
        if (opacityBar != null) {
            opacityBar.setColor(i);
        }
    }

    public void changeSaturationBarColor(int i) {
        SaturationBar saturationBar = this.mSaturationBar;
        if (saturationBar != null) {
            saturationBar.setColor(i);
        }
    }

    public void changeValueBarColor(int i) {
        ValueBar valueBar = this.mValueBar;
        if (valueBar != null) {
            valueBar.setColor(i);
        }
    }

    public boolean hasOpacityBar() {
        return this.mOpacityBar != null;
    }

    public boolean hasValueBar() {
        return this.mValueBar != null;
    }

    public boolean hasSaturationBar() {
        return this.mSaturationBar != null;
    }

    public boolean hasSVBar() {
        return this.mSVbar != null;
    }

    @Override // android.view.View
    protected Parcelable onSaveInstanceState() {
        Parcelable parcelableOnSaveInstanceState = super.onSaveInstanceState();
        Bundle bundle = new Bundle();
        bundle.putParcelable(STATE_PARENT, parcelableOnSaveInstanceState);
        bundle.putFloat(STATE_ANGLE, this.mAngle);
        bundle.putInt("color", this.mCenterOldColor);
        bundle.putBoolean(STATE_SHOW_OLD_COLOR, this.mShowCenterOldColor);
        return bundle;
    }

    @Override // android.view.View
    protected void onRestoreInstanceState(Parcelable parcelable) {
        Bundle bundle = (Bundle) parcelable;
        super.onRestoreInstanceState(bundle.getParcelable(STATE_PARENT));
        this.mAngle = bundle.getFloat(STATE_ANGLE);
        setOldCenterColor(bundle.getInt("color"));
        this.mShowCenterOldColor = bundle.getBoolean(STATE_SHOW_OLD_COLOR);
        int iCalculateColor = calculateColor(this.mAngle);
        this.mPointerColor.setColor(iCalculateColor);
        setNewCenterColor(iCalculateColor);
    }
}
