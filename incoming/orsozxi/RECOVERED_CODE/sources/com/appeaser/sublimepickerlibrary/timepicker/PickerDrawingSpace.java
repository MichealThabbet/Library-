package com.appeaser.sublimepickerlibrary.timepicker;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

/* loaded from: classes.dex */
public final class PickerDrawingSpace extends View {
    public PickerDrawingSpace(Context context) {
        this(context, null);
    }

    public PickerDrawingSpace(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public PickerDrawingSpace(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }

    public PickerDrawingSpace(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
    }

    private static int getDefaultSizeNonGreedy(int i, int i2) {
        int mode = View.MeasureSpec.getMode(i2);
        int size = View.MeasureSpec.getSize(i2);
        if (mode != Integer.MIN_VALUE) {
            return mode != 1073741824 ? i : size;
        }
        return Math.min(i, size);
    }

    @Override // android.view.View
    protected void onMeasure(int i, int i2) {
        setMeasuredDimension(getDefaultSizeNonGreedy(getSuggestedMinimumWidth(), i), getDefaultSizeNonGreedy(getSuggestedMinimumHeight(), i2));
    }
}
