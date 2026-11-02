package com.marshalchen.ultimaterecyclerview.ui.floatingactionbutton;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.StateListDrawable;
import android.util.AttributeSet;
import com.marshalchen.ultimaterecyclerview.R;

/* loaded from: classes2.dex */
public class JellyBeanFloatingActionButton extends FloatingActionButton {
    protected float mAlpha_normal;
    protected float mAlpha_press;

    public JellyBeanFloatingActionButton(Context context) {
        super(context);
    }

    public JellyBeanFloatingActionButton(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public JellyBeanFloatingActionButton(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }

    @Override // com.marshalchen.ultimaterecyclerview.ui.floatingactionbutton.FloatingActionButton
    protected void load_extended_attributes(TypedArray typedArray) {
        this.mAlpha_press = typedArray.getFloat(R.styleable.FloatActionButton_urv_fab_alphaPressed, 0.5f);
        this.mAlpha_normal = typedArray.getFloat(R.styleable.FloatActionButton_urv_fab_alphaNormal, 0.5f);
        this.mSize = 2;
    }

    @Override // com.marshalchen.ultimaterecyclerview.ui.floatingactionbutton.FloatingActionButton
    protected StateListDrawable createFillDrawable(RectF rectF) {
        StateListDrawable stateListDrawable = new StateListDrawable();
        stateListDrawable.addState(new int[]{android.R.attr.state_pressed}, createAlphaDrawble(rectF, this.mColorPressed, this.mAlpha_press));
        stateListDrawable.addState(new int[0], createAlphaDrawble(rectF, this.mColorNormal, this.mAlpha_normal));
        return stateListDrawable;
    }

    @Override // com.marshalchen.ultimaterecyclerview.ui.floatingactionbutton.FloatingActionButton
    protected LayerDrawable generateFinalDrawables(RectF rectF) {
        return new LayerDrawable(new Drawable[]{createFillDrawable(rectF), getIconDrawable()});
    }
}
