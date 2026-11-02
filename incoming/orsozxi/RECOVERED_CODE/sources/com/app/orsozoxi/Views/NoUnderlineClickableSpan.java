package com.app.orsozoxi.Views;

import android.graphics.Color;
import android.text.TextPaint;
import android.text.style.ClickableSpan;
import android.view.View;

/* loaded from: classes.dex */
public class NoUnderlineClickableSpan extends ClickableSpan {
    private int color = -1;

    @Override // android.text.style.ClickableSpan
    public void onClick(View view) {
    }

    public void setColor(int i) {
        this.color = i;
    }

    @Override // android.text.style.ClickableSpan, android.text.style.CharacterStyle
    public void updateDrawState(TextPaint textPaint) {
        textPaint.setUnderlineText(false);
        textPaint.setColor(Color.parseColor("#EE7429"));
    }
}
