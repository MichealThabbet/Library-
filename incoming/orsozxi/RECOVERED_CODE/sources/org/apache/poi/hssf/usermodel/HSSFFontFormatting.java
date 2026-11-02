package org.apache.poi.hssf.usermodel;

import org.apache.poi.hssf.record.CFRuleRecord;
import org.apache.poi.hssf.record.cf.FontFormatting;

/* loaded from: classes3.dex */
public final class HSSFFontFormatting {
    public static final short SS_NONE = 0;
    public static final short SS_SUB = 2;
    public static final short SS_SUPER = 1;
    public static final byte U_DOUBLE = 2;
    public static final byte U_DOUBLE_ACCOUNTING = 34;
    public static final byte U_NONE = 0;
    public static final byte U_SINGLE = 1;
    public static final byte U_SINGLE_ACCOUNTING = 33;
    private final FontFormatting fontFormatting;

    protected HSSFFontFormatting(CFRuleRecord cFRuleRecord) {
        this.fontFormatting = cFRuleRecord.getFontFormatting();
    }

    protected FontFormatting getFontFormattingBlock() {
        return this.fontFormatting;
    }

    public short getEscapementType() {
        return this.fontFormatting.getEscapementType();
    }

    public short getFontColorIndex() {
        return this.fontFormatting.getFontColorIndex();
    }

    public int getFontHeight() {
        return this.fontFormatting.getFontHeight();
    }

    public short getFontWeight() {
        return this.fontFormatting.getFontWeight();
    }

    protected byte[] getRawRecord() {
        return this.fontFormatting.getRawRecord();
    }

    public short getUnderlineType() {
        return this.fontFormatting.getUnderlineType();
    }

    public boolean isBold() {
        return this.fontFormatting.isFontWeightModified() && this.fontFormatting.isBold();
    }

    public boolean isEscapementTypeModified() {
        return this.fontFormatting.isEscapementTypeModified();
    }

    public boolean isFontCancellationModified() {
        return this.fontFormatting.isFontCancellationModified();
    }

    public boolean isFontOutlineModified() {
        return this.fontFormatting.isFontOutlineModified();
    }

    public boolean isFontShadowModified() {
        return this.fontFormatting.isFontShadowModified();
    }

    public boolean isFontStyleModified() {
        return this.fontFormatting.isFontStyleModified();
    }

    public boolean isItalic() {
        return this.fontFormatting.isFontStyleModified() && this.fontFormatting.isItalic();
    }

    public boolean isOutlineOn() {
        return this.fontFormatting.isFontOutlineModified() && this.fontFormatting.isOutlineOn();
    }

    public boolean isShadowOn() {
        return this.fontFormatting.isFontOutlineModified() && this.fontFormatting.isShadowOn();
    }

    public boolean isStruckout() {
        return this.fontFormatting.isFontCancellationModified() && this.fontFormatting.isStruckout();
    }

    public boolean isUnderlineTypeModified() {
        return this.fontFormatting.isUnderlineTypeModified();
    }

    public boolean isFontWeightModified() {
        return this.fontFormatting.isFontWeightModified();
    }

    public void setFontStyle(boolean z, boolean z2) {
        boolean z3 = z || z2;
        this.fontFormatting.setItalic(z);
        this.fontFormatting.setBold(z2);
        this.fontFormatting.setFontStyleModified(z3);
        this.fontFormatting.setFontWieghtModified(z3);
    }

    public void resetFontStyle() {
        setFontStyle(false, false);
    }

    public void setEscapementType(short s) {
        if (s == 0) {
            this.fontFormatting.setEscapementType(s);
            this.fontFormatting.setEscapementTypeModified(false);
        } else if (s == 1 || s == 2) {
            this.fontFormatting.setEscapementType(s);
            this.fontFormatting.setEscapementTypeModified(true);
        }
    }

    public void setEscapementTypeModified(boolean z) {
        this.fontFormatting.setEscapementTypeModified(z);
    }

    public void setFontCancellationModified(boolean z) {
        this.fontFormatting.setFontCancellationModified(z);
    }

    public void setFontColorIndex(short s) {
        this.fontFormatting.setFontColorIndex(s);
    }

    public void setFontHeight(int i) {
        this.fontFormatting.setFontHeight(i);
    }

    public void setFontOutlineModified(boolean z) {
        this.fontFormatting.setFontOutlineModified(z);
    }

    public void setFontShadowModified(boolean z) {
        this.fontFormatting.setFontShadowModified(z);
    }

    public void setFontStyleModified(boolean z) {
        this.fontFormatting.setFontStyleModified(z);
    }

    public void setOutline(boolean z) {
        this.fontFormatting.setOutline(z);
        this.fontFormatting.setFontOutlineModified(z);
    }

    public void setShadow(boolean z) {
        this.fontFormatting.setShadow(z);
        this.fontFormatting.setFontShadowModified(z);
    }

    public void setStrikeout(boolean z) {
        this.fontFormatting.setStrikeout(z);
        this.fontFormatting.setFontCancellationModified(z);
    }

    public void setUnderlineType(short s) {
        if (s == 0) {
            this.fontFormatting.setUnderlineType(s);
            setUnderlineTypeModified(false);
        } else if (s == 1 || s == 2 || s == 33 || s == 34) {
            this.fontFormatting.setUnderlineType(s);
            setUnderlineTypeModified(true);
        }
    }

    public void setUnderlineTypeModified(boolean z) {
        this.fontFormatting.setUnderlineTypeModified(z);
    }
}
