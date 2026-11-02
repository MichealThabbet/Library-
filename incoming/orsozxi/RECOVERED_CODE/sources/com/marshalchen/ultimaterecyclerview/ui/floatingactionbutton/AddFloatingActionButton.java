package com.marshalchen.ultimaterecyclerview.ui.floatingactionbutton;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.Shape;
import android.util.AttributeSet;
import com.marshalchen.ultimaterecyclerview.R;

/* loaded from: classes2.dex */
public class AddFloatingActionButton extends FloatingActionButton {
    protected int mPlusColor;

    public AddFloatingActionButton(Context context) {
        this(context, null);
    }

    public AddFloatingActionButton(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public AddFloatingActionButton(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }

    @Override // com.marshalchen.ultimaterecyclerview.ui.floatingactionbutton.FloatingActionButton
    protected void init(Context context, AttributeSet attributeSet) {
        if (attributeSet != null) {
            TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.AddFloatingActionButton, 0, 0);
            if (typedArrayObtainStyledAttributes != null) {
                try {
                    this.mPlusColor = typedArrayObtainStyledAttributes.getColor(R.styleable.AddFloatingActionButton_plusIconColor, getColor(android.R.color.white));
                } finally {
                    typedArrayObtainStyledAttributes.recycle();
                }
            }
        } else {
            this.mPlusColor = getColor(android.R.color.white);
        }
        super.init(context, attributeSet);
    }

    @Override // com.marshalchen.ultimaterecyclerview.ui.floatingactionbutton.FloatingActionButton
    protected Drawable getIconDrawable() {
        final float dimension = getDimension(R.dimen.fab_icon_size);
        final float f = dimension / 2.0f;
        float dimension2 = getDimension(R.dimen.fab_plus_icon_size);
        final float dimension3 = getDimension(R.dimen.fab_plus_icon_stroke) / 2.0f;
        final float f2 = (dimension - dimension2) / 2.0f;
        ShapeDrawable shapeDrawable = new ShapeDrawable(new Shape() { // from class: com.marshalchen.ultimaterecyclerview.ui.floatingactionbutton.AddFloatingActionButton.1
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
