package com.appeaser.sublimepickerlibrary.drawables;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.drawable.Drawable;
import com.appeaser.sublimepickerlibrary.R;

/* loaded from: classes.dex */
public class OverflowDrawable extends Drawable {
    PointF center1;
    PointF center2;
    PointF center3;
    Paint mPaintCircle;
    float mRadius;
    int mWidthHeight;

    @Override // android.graphics.drawable.Drawable
    public int getOpacity() {
        return -1;
    }

    public OverflowDrawable(Context context, int i) {
        this.mWidthHeight = context.getResources().getDimensionPixelSize(R.dimen.options_size);
        float f = r4.getDisplayMetrics().densityDpi / 160.0f;
        this.mRadius = f * 2.0f;
        float f2 = this.mWidthHeight / 2.0f;
        float f3 = f * 6.0f;
        this.center1 = new PointF(f2, f2 - f3);
        this.center2 = new PointF(f2, f2);
        this.center3 = new PointF(f2, f3 + f2);
        Paint paint = new Paint();
        this.mPaintCircle = paint;
        paint.setColor(i);
        this.mPaintCircle.setAntiAlias(true);
    }

    @Override // android.graphics.drawable.Drawable
    public void draw(Canvas canvas) {
        canvas.drawCircle(this.center1.x, this.center1.y, this.mRadius, this.mPaintCircle);
        canvas.drawCircle(this.center2.x, this.center2.y, this.mRadius, this.mPaintCircle);
        canvas.drawCircle(this.center3.x, this.center3.y, this.mRadius, this.mPaintCircle);
    }

    @Override // android.graphics.drawable.Drawable
    public int getMinimumHeight() {
        return this.mWidthHeight;
    }

    @Override // android.graphics.drawable.Drawable
    public int getMinimumWidth() {
        return this.mWidthHeight;
    }

    @Override // android.graphics.drawable.Drawable
    public int getIntrinsicHeight() {
        return this.mWidthHeight;
    }

    @Override // android.graphics.drawable.Drawable
    public int getIntrinsicWidth() {
        return this.mWidthHeight;
    }

    @Override // android.graphics.drawable.Drawable
    public void setAlpha(int i) {
        this.mPaintCircle.setAlpha(i);
        invalidateSelf();
    }

    @Override // android.graphics.drawable.Drawable
    public void setColorFilter(ColorFilter colorFilter) {
        this.mPaintCircle.setColorFilter(colorFilter);
        invalidateSelf();
    }
}
