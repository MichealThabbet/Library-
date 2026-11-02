package com.appeaser.sublimepickerlibrary.drawables;

import android.animation.TypeEvaluator;
import android.graphics.RectF;

/* loaded from: classes.dex */
public class CRectFEvaluator implements TypeEvaluator<RectF> {
    private RectF mRectF;

    public CRectFEvaluator() {
    }

    public CRectFEvaluator(RectF rectF) {
        this.mRectF = rectF;
    }

    @Override // android.animation.TypeEvaluator
    public RectF evaluate(float f, RectF rectF, RectF rectF2) {
        float f2 = rectF.left + ((rectF2.left - rectF.left) * f);
        float f3 = rectF.top + ((rectF2.top - rectF.top) * f);
        float f4 = rectF.right + ((rectF2.right - rectF.right) * f);
        float f5 = rectF.bottom + ((rectF2.bottom - rectF.bottom) * f);
        RectF rectF3 = this.mRectF;
        if (rectF3 == null) {
            return new RectF(f2, f3, f4, f5);
        }
        rectF3.set(f2, f3, f4, f5);
        return this.mRectF;
    }
}
