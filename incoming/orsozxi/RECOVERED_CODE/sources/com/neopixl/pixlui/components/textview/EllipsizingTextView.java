package com.neopixl.pixlui.components.textview;

import android.R;
import android.content.Context;
import android.graphics.Canvas;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextUtils;
import android.util.AttributeSet;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Pattern;

/* loaded from: classes2.dex */
public class EllipsizingTextView extends android.widget.TextView {
    private static final Pattern TEXTVIEW_ELLIPSIZING_DEFAULT_END_PUNCTUATION = Pattern.compile("[\\.,…;:\\s]*$", 32);
    private static final String TEXTVIEW_ELLIPSIZING_ELLIPSIS = "…";
    private final List<EllipsizeListener> ellipsizeListeners;
    private Pattern endPunctuationPattern;
    private String fullText;
    private boolean isEllipsized;
    private boolean isStale;
    private float lineAdditionalVerticalPadding;
    private float lineSpacingMultiplier;
    private int maxLines;
    private boolean programmaticChange;

    public interface EllipsizeListener {
        void ellipsizeStateChanged(boolean z);
    }

    @Override // android.widget.TextView
    public void setEllipsize(TextUtils.TruncateAt truncateAt) {
    }

    public EllipsizingTextView(Context context) {
        this(context, null);
    }

    public EllipsizingTextView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public EllipsizingTextView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.ellipsizeListeners = new ArrayList();
        this.lineSpacingMultiplier = 1.0f;
        this.lineAdditionalVerticalPadding = 0.0f;
        super.setEllipsize(null);
        setMaxLines(context.obtainStyledAttributes(attributeSet, new int[]{R.attr.maxLines}).getInt(0, Integer.MAX_VALUE));
        setEndPunctuationPattern(TEXTVIEW_ELLIPSIZING_DEFAULT_END_PUNCTUATION);
    }

    public void setEndPunctuationPattern(Pattern pattern) {
        this.endPunctuationPattern = pattern;
    }

    public void addEllipsizeListener(EllipsizeListener ellipsizeListener) {
        if (ellipsizeListener == null) {
            throw null;
        }
        this.ellipsizeListeners.add(ellipsizeListener);
    }

    public void removeEllipsizeListener(EllipsizeListener ellipsizeListener) {
        this.ellipsizeListeners.remove(ellipsizeListener);
    }

    public boolean isEllipsized() {
        return this.isEllipsized;
    }

    @Override // android.widget.TextView
    public void setMaxLines(int i) {
        super.setMaxLines(i);
        this.maxLines = i;
        this.isStale = true;
    }

    @Override // android.widget.TextView
    public int getMaxLines() {
        return this.maxLines;
    }

    public boolean ellipsizingLastFullyVisibleLine() {
        return this.maxLines == Integer.MAX_VALUE;
    }

    @Override // android.widget.TextView
    public void setLineSpacing(float f, float f2) {
        this.lineAdditionalVerticalPadding = f;
        this.lineSpacingMultiplier = f2;
        super.setLineSpacing(f, f2);
    }

    @Override // android.widget.TextView
    protected void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        super.onTextChanged(charSequence, i, i2, i3);
        if (this.programmaticChange) {
            return;
        }
        this.fullText = charSequence.toString();
        this.isStale = true;
    }

    @Override // android.view.View
    protected void onSizeChanged(int i, int i2, int i3, int i4) {
        super.onSizeChanged(i, i2, i3, i4);
        if (ellipsizingLastFullyVisibleLine()) {
            this.isStale = true;
        }
    }

    @Override // android.widget.TextView, android.view.View
    public void setPadding(int i, int i2, int i3, int i4) {
        super.setPadding(i, i2, i3, i4);
        if (ellipsizingLastFullyVisibleLine()) {
            this.isStale = true;
        }
    }

    @Override // android.widget.TextView, android.view.View
    protected void onDraw(Canvas canvas) {
        if (this.isStale) {
            resetText();
        }
        super.onDraw(canvas);
    }

    private void resetText() {
        boolean z;
        int iLastIndexOf;
        String str = this.fullText;
        Layout layoutCreateWorkingLayout = createWorkingLayout(str);
        int linesCount = getLinesCount();
        if (layoutCreateWorkingLayout.getLineCount() > linesCount) {
            String strTrim = this.fullText.substring(0, layoutCreateWorkingLayout.getLineEnd(linesCount - 1)).trim();
            while (true) {
                if (createWorkingLayout(strTrim + TEXTVIEW_ELLIPSIZING_ELLIPSIS).getLineCount() <= linesCount || (iLastIndexOf = strTrim.lastIndexOf(32)) == -1) {
                    break;
                } else {
                    strTrim = strTrim.substring(0, iLastIndexOf);
                }
            }
            str = this.endPunctuationPattern.matcher(strTrim).replaceFirst("") + TEXTVIEW_ELLIPSIZING_ELLIPSIS;
            z = true;
        } else {
            z = false;
        }
        if (!str.equals(getText())) {
            this.programmaticChange = true;
            try {
                setText(str);
            } finally {
                this.programmaticChange = false;
            }
        }
        this.isStale = false;
        if (z != this.isEllipsized) {
            this.isEllipsized = z;
            Iterator<EllipsizeListener> it2 = this.ellipsizeListeners.iterator();
            while (it2.hasNext()) {
                it2.next().ellipsizeStateChanged(z);
            }
        }
    }

    private int getLinesCount() {
        if (ellipsizingLastFullyVisibleLine()) {
            int fullyVisibleLinesCount = getFullyVisibleLinesCount();
            if (fullyVisibleLinesCount == -1) {
                return 1;
            }
            return fullyVisibleLinesCount;
        }
        return this.maxLines;
    }

    private int getFullyVisibleLinesCount() {
        return ((getHeight() - getPaddingTop()) - getPaddingBottom()) / createWorkingLayout("").getLineBottom(0);
    }

    private Layout createWorkingLayout(String str) {
        return new StaticLayout(str, getPaint(), (getWidth() - getPaddingLeft()) - getPaddingRight(), Layout.Alignment.ALIGN_NORMAL, this.lineSpacingMultiplier, this.lineAdditionalVerticalPadding, false);
    }
}
