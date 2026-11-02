package com.nhaarman.listviewanimations.itemmanipulation.dragdrop;

import android.R;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import androidx.constraintlayout.solver.widgets.analyzer.BasicMeasure;

/* loaded from: classes2.dex */
public class GripView extends View {
    private static final int[] ATTRS = {R.attr.color};
    public static final int DEFAULT_COLUMN_COUNT = 2;
    public static final int DEFAULT_DOT_COLOR = 17170432;
    public static final float DEFAULT_DOT_SIZE_RADIUS_DP = 2.0f;
    private int mColumnCount;
    private final Paint mDotPaint;
    private float mDotSizeRadiusPx;
    private float mPaddingTop;
    private int mRowCount;

    public GripView(Context context) {
        this(context, null);
    }

    public GripView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public GripView(Context context, AttributeSet attributeSet, int i) throws Resources.NotFoundException {
        super(context, attributeSet, i);
        this.mColumnCount = 2;
        this.mDotPaint = new Paint(1);
        int color = getResources().getColor(17170432);
        if (attributeSet != null) {
            TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, ATTRS);
            color = typedArrayObtainStyledAttributes.getColor(0, color);
            typedArrayObtainStyledAttributes.recycle();
        }
        this.mDotPaint.setColor(color);
        this.mDotSizeRadiusPx = (int) TypedValue.applyDimension(1, 2.0f, context.getResources().getDisplayMetrics());
    }

    public void setColor(int i) {
        this.mDotPaint.setColor(getResources().getColor(i));
    }

    public void setDotSizeRadiusPx(float f) {
        this.mDotSizeRadiusPx = f;
    }

    public void setColumnCount(int i) {
        this.mColumnCount = i;
        requestLayout();
    }

    @Override // android.view.View
    protected void onSizeChanged(int i, int i2, int i3, int i4) {
        super.onSizeChanged(i, i2, i3, i4);
        float paddingTop = (i2 - getPaddingTop()) - getPaddingBottom();
        float f = this.mDotSizeRadiusPx;
        int i5 = (int) (paddingTop / (4.0f * f));
        this.mRowCount = i5;
        this.mPaddingTop = ((i2 - ((i5 * f) * 2.0f)) - (((i5 - 1) * f) * 2.0f)) / 2.0f;
    }

    @Override // android.view.View
    protected void onMeasure(int i, int i2) {
        super.onMeasure(View.MeasureSpec.makeMeasureSpec(getPaddingLeft() + getPaddingRight() + ((int) (this.mColumnCount * ((this.mDotSizeRadiusPx * 4.0f) - 2.0f))), BasicMeasure.EXACTLY), i2);
    }

    @Override // android.view.View
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        for (int i = 0; i < this.mColumnCount; i++) {
            float paddingLeft = getPaddingLeft() + (i * 2 * this.mDotSizeRadiusPx * 2.0f);
            for (int i2 = 0; i2 < this.mRowCount; i2++) {
                float f = this.mPaddingTop;
                float f2 = this.mDotSizeRadiusPx;
                canvas.drawCircle(paddingLeft + f2, f + (i2 * 2 * f2 * 2.0f) + f2, f2, this.mDotPaint);
            }
        }
    }
}
