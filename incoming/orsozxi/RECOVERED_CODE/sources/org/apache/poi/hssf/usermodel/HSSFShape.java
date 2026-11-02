package org.apache.poi.hssf.usermodel;

/* loaded from: classes3.dex */
public abstract class HSSFShape {
    public static final int LINESTYLE_DASHDOTDOTSYS = 4;
    public static final int LINESTYLE_DASHDOTGEL = 8;
    public static final int LINESTYLE_DASHDOTSYS = 3;
    public static final int LINESTYLE_DASHGEL = 6;
    public static final int LINESTYLE_DASHSYS = 1;
    public static final int LINESTYLE_DOTGEL = 5;
    public static final int LINESTYLE_DOTSYS = 2;
    public static final int LINESTYLE_LONGDASHDOTDOTGEL = 10;
    public static final int LINESTYLE_LONGDASHDOTGEL = 9;
    public static final int LINESTYLE_LONGDASHGEL = 7;
    public static final int LINESTYLE_NONE = -1;
    public static final int LINESTYLE_SOLID = 0;
    public static final int LINEWIDTH_DEFAULT = 9525;
    public static final int LINEWIDTH_ONE_PT = 12700;
    HSSFAnchor anchor;
    final HSSFShape parent;
    private int _lineStyleColor = 134217792;
    int _fillColor = 134217737;
    private int _lineWidth = LINEWIDTH_DEFAULT;
    private int _lineStyle = 0;
    private boolean _noFill = false;

    public int countOfAllChildren() {
        return 1;
    }

    HSSFShape(HSSFShape hSSFShape, HSSFAnchor hSSFAnchor) {
        this.parent = hSSFShape;
        this.anchor = hSSFAnchor;
    }

    public HSSFShape getParent() {
        return this.parent;
    }

    public HSSFAnchor getAnchor() {
        return this.anchor;
    }

    public void setAnchor(HSSFAnchor hSSFAnchor) {
        if (this.parent == null) {
            if (hSSFAnchor instanceof HSSFChildAnchor) {
                throw new IllegalArgumentException("Must use client anchors for shapes directly attached to sheet.");
            }
        } else if (hSSFAnchor instanceof HSSFClientAnchor) {
            throw new IllegalArgumentException("Must use child anchors for shapes attached to groups.");
        }
        this.anchor = hSSFAnchor;
    }

    public int getLineStyleColor() {
        return this._lineStyleColor;
    }

    public void setLineStyleColor(int i) {
        this._lineStyleColor = i;
    }

    public void setLineStyleColor(int i, int i2, int i3) {
        this._lineStyleColor = i | (i2 << 8) | (i3 << 16);
    }

    public int getFillColor() {
        return this._fillColor;
    }

    public void setFillColor(int i) {
        this._fillColor = i;
    }

    public void setFillColor(int i, int i2, int i3) {
        this._fillColor = i | (i2 << 8) | (i3 << 16);
    }

    public int getLineWidth() {
        return this._lineWidth;
    }

    public void setLineWidth(int i) {
        this._lineWidth = i;
    }

    public int getLineStyle() {
        return this._lineStyle;
    }

    public void setLineStyle(int i) {
        this._lineStyle = i;
    }

    public boolean isNoFill() {
        return this._noFill;
    }

    public void setNoFill(boolean z) {
        this._noFill = z;
    }
}
