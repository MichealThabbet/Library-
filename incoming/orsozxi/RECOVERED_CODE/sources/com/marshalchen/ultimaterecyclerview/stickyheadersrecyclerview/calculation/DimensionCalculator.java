package com.marshalchen.ultimaterecyclerview.stickyheadersrecyclerview.calculation;

import android.graphics.Rect;
import android.view.View;
import android.view.ViewGroup;

/* loaded from: classes2.dex */
public class DimensionCalculator {
    public Rect getMargins(View view) {
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        if (layoutParams instanceof ViewGroup.MarginLayoutParams) {
            return getMarginRect((ViewGroup.MarginLayoutParams) layoutParams);
        }
        return new Rect();
    }

    private Rect getMarginRect(ViewGroup.MarginLayoutParams marginLayoutParams) {
        return new Rect(marginLayoutParams.leftMargin, marginLayoutParams.topMargin, marginLayoutParams.rightMargin, marginLayoutParams.bottomMargin);
    }
}
