package fr.castorflex.android.smoothprogressbar;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.shapes.Shape;

/* loaded from: classes2.dex */
public class ColorsShape extends Shape {
    private int[] mColors;
    private float mStrokeWidth;

    public ColorsShape(float f, int[] iArr) {
        this.mStrokeWidth = f;
        this.mColors = iArr;
    }

    public float getStrokeWidth() {
        return this.mStrokeWidth;
    }

    public void setStrokeWidth(float f) {
        this.mStrokeWidth = f;
    }

    public int[] getColors() {
        return this.mColors;
    }

    public void setColors(int[] iArr) {
        this.mColors = iArr;
    }

    @Override // android.graphics.drawable.shapes.Shape
    public void draw(Canvas canvas, Paint paint) {
        float length = 1.0f / this.mColors.length;
        paint.setStrokeWidth(this.mStrokeWidth);
        int i = 0;
        for (int i2 : this.mColors) {
            paint.setColor(i2);
            i++;
            canvas.drawLine(i * length * getWidth(), getHeight() / 2.0f, i * length * getWidth(), getHeight() / 2.0f, paint);
        }
    }
}
