package com.app.piapostolos.Others;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import androidx.core.internal.view.SupportMenu;

/* loaded from: classes.dex */
public class LineProgress extends View {
    public Paint paint;
    public int progress;

    public LineProgress(Context context) {
        super(context);
        this.paint = new Paint();
    }

    public LineProgress(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.paint = new Paint();
    }

    public LineProgress(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.paint = new Paint();
    }

    @Override // android.view.View
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Paint paint = new Paint();
        this.paint = paint;
        paint.setColor(SupportMenu.CATEGORY_MASK);
        canvas.drawRect(0.0f, 0.0f, getWidth(), getHeight(), this.paint);
        Paint paint2 = new Paint();
        this.paint = paint2;
        paint2.setColor(-1);
        canvas.drawRect(0.0f, 0.0f, this.progress, getHeight(), this.paint);
    }

    public void setLineProgress(int i) {
        this.progress = i * (getWidth() / 100);
        invalidate();
    }
}
