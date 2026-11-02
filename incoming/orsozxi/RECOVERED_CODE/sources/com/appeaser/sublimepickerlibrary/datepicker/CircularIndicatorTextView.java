package com.appeaser.sublimepickerlibrary.datepicker;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;
import com.appeaser.sublimepickerlibrary.R;

/* loaded from: classes.dex */
class CircularIndicatorTextView extends TextView {
    private static final int SELECTED_CIRCLE_ALPHA = 60;
    private int mCircleColor;
    private final Paint mCirclePaint;
    private boolean mDrawIndicator;
    private final String mItemIsSelectedText;

    public CircularIndicatorTextView(Context context) {
        this(context, null);
    }

    public CircularIndicatorTextView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public CircularIndicatorTextView(Context context, AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, 0);
    }

    public CircularIndicatorTextView(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet);
        this.mCirclePaint = new Paint();
        this.mItemIsSelectedText = context.getResources().getString(R.string.item_is_selected);
        init();
    }

    private void init() {
        Paint paint = this.mCirclePaint;
        paint.setTypeface(Typeface.create(paint.getTypeface(), 1));
        this.mCirclePaint.setAntiAlias(true);
        this.mCirclePaint.setTextAlign(Paint.Align.CENTER);
        this.mCirclePaint.setStyle(Paint.Style.FILL);
    }

    public void setCircleColor(int i) {
        if (i != this.mCircleColor) {
            this.mCircleColor = i;
            this.mCirclePaint.setColor(i);
            this.mCirclePaint.setAlpha(60);
            requestLayout();
        }
    }

    public void setDrawIndicator(boolean z) {
        this.mDrawIndicator = z;
    }

    @Override // android.widget.TextView, android.view.View
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (this.mDrawIndicator) {
            int width = getWidth();
            int height = getHeight();
            canvas.drawCircle(width / 2, height / 2, Math.min(width, height) / 2, this.mCirclePaint);
        }
    }

    @Override // android.view.View
    public CharSequence getContentDescription() {
        CharSequence text = getText();
        return this.mDrawIndicator ? String.format(this.mItemIsSelectedText, text) : text;
    }
}
