package com.app.piapostolos.Views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.util.AttributeSet;
import androidx.appcompat.widget.AppCompatTextView;

/* loaded from: classes.dex */
public class CustomCopticTextView extends AppCompatTextView {
    public static final String TWO_CHINESE_BLANK = "  ";
    private int mLineY;
    private int mViewWidth;

    public CustomCopticTextView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    @Override // androidx.appcompat.widget.AppCompatTextView, android.widget.TextView, android.view.View
    protected void onLayout(boolean z, int i, int i2, int i3, int i4) {
        super.onLayout(z, i, i2, i3, i4);
    }

    @Override // android.widget.TextView, android.view.View
    protected void onDraw(Canvas canvas) {
        TextPaint paint = getPaint();
        paint.setColor(getCurrentTextColor());
        paint.drawableState = getDrawableState();
        this.mViewWidth = getMeasuredWidth();
        String string = getText().toString();
        this.mLineY = 0;
        this.mLineY = (int) (0 + getTextSize());
        Layout layout = getLayout();
        if (layout == null) {
            return;
        }
        Paint.FontMetrics fontMetrics = paint.getFontMetrics();
        int iCeil = (int) ((((int) Math.ceil(fontMetrics.descent - fontMetrics.ascent)) * layout.getSpacingMultiplier()) + layout.getSpacingAdd());
        for (int i = 0; i < layout.getLineCount(); i++) {
            int lineStart = layout.getLineStart(i);
            int lineEnd = layout.getLineEnd(i);
            float desiredWidth = StaticLayout.getDesiredWidth(string, lineStart, lineEnd, getPaint());
            String strSubstring = string.substring(lineStart, lineEnd);
            if (needScale(strSubstring) && i < layout.getLineCount() - 1) {
                drawScaledText(canvas, lineStart, strSubstring, desiredWidth);
            } else {
                canvas.drawText(strSubstring.replaceAll("%", " "), 0.0f, this.mLineY, paint);
            }
            this.mLineY += iCeil;
        }
    }

    private void drawScaledText(Canvas canvas, int i, String str, float f) {
        float desiredWidth = 0.0f;
        if (isFirstLineOfParagraph(i, str)) {
            canvas.drawText(TWO_CHINESE_BLANK, 0.0f, this.mLineY, getPaint());
            desiredWidth = 0.0f + StaticLayout.getDesiredWidth(TWO_CHINESE_BLANK, getPaint());
            str = str.substring(3);
        }
        int length = str.length() - 1;
        int i2 = 2;
        if (str.length() > 2 && str.charAt(0) == 12288 && str.charAt(1) == 12288) {
            String strSubstring = str.substring(0, 2);
            float desiredWidth2 = StaticLayout.getDesiredWidth(strSubstring, getPaint());
            canvas.drawText(strSubstring.replaceAll("%", " "), desiredWidth, this.mLineY, getPaint());
            desiredWidth += desiredWidth2;
        } else {
            i2 = 0;
        }
        float f2 = (this.mViewWidth - f) / length;
        while (i2 < str.length()) {
            String strValueOf = String.valueOf(str.charAt(i2));
            float desiredWidth3 = StaticLayout.getDesiredWidth(strValueOf, getPaint());
            canvas.drawText(strValueOf.replaceAll("%", " "), desiredWidth, this.mLineY, getPaint());
            desiredWidth += desiredWidth3 + f2;
            i2++;
        }
    }

    private boolean isFirstLineOfParagraph(int i, String str) {
        return str.length() > 3 && str.charAt(0) == ' ' && str.charAt(1) == ' ';
    }

    private boolean needScale(String str) {
        return (str == null || str.length() == 0 || str.charAt(str.length() - 1) == '\n') ? false : true;
    }
}
