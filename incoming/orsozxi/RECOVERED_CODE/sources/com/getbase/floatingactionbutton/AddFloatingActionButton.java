package com.getbase.floatingactionbutton;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.Shape;
import android.util.AttributeSet;

/* loaded from: classes.dex */
public class AddFloatingActionButton extends FloatingActionButton {
    int mPlusColor;

    public AddFloatingActionButton(Context context) {
        this(context, null);
    }

    public AddFloatingActionButton(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public AddFloatingActionButton(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }

    @Override // com.getbase.floatingactionbutton.FloatingActionButton
    void init(Context context, AttributeSet attributeSet) {
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.AddFloatingActionButton, 0, 0);
        this.mPlusColor = typedArrayObtainStyledAttributes.getColor(R.styleable.AddFloatingActionButton_fab_plusIconColor, getColor(android.R.color.white));
        typedArrayObtainStyledAttributes.recycle();
        super.init(context, attributeSet);
    }

    public int getPlusColor() {
        return this.mPlusColor;
    }

    public void setPlusColorResId(int i) {
        setPlusColor(getColor(i));
    }

    public void setPlusColor(int i) {
        if (this.mPlusColor != i) {
            this.mPlusColor = i;
            updateBackground();
        }
    }

    @Override // com.getbase.floatingactionbutton.FloatingActionButton
    public void setIcon(int i) {
        throw new UnsupportedOperationException("Use FloatingActionButton if you want to use custom icon");
    }

    @Override // com.getbase.floatingactionbutton.FloatingActionButton
    Drawable getIconDrawable() {
        final float dimension = getDimension(R.dimen.fab_icon_size);
        final float f = dimension / 2.0f;
        float dimension2 = getDimension(R.dimen.fab_plus_icon_size);
        final float dimension3 = getDimension(R.dimen.fab_plus_icon_stroke) / 2.0f;
        final float f2 = (dimension - dimension2) / 2.0f;
        ShapeDrawable shapeDrawable = new ShapeDrawable(new Shape() { // from class: com.getbase.floatingactionbutton.AddFloatingActionButton.1
            @Override // android.graphics.drawable.shapes.Shape
            public void draw(Canvas canvas, Paint paint) {
                float f3 = f2;
                float f4 = f;
                float f5 = dimension3;
                canvas.drawRect(f3, f4 - f5, dimension - f3, f4 + f5, paint);
                float f6 = f;
                float f7 = dimension3;
                float f8 = f2;
                canvas.drawRect(f6 - f7, f8, f6 + f7, dimension - f8, paint);
            }
        });
        Paint paint = shapeDrawable.getPaint();
        paint.setColor(this.mPlusColor);
        paint.setStyle(Paint.Style.FILL);
        paint.setAntiAlias(true);
        return shapeDrawable;
    }
}
