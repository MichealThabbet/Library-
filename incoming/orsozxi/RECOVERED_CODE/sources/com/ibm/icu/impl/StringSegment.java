package com.ibm.icu.impl;

import com.ibm.icu.lang.UCharacter;
import com.ibm.icu.text.UnicodeSet;

/* loaded from: classes2.dex */
public class StringSegment implements CharSequence {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private int end;
    private boolean foldCase;
    private int start = 0;
    private final String str;

    public StringSegment(String str, boolean z) {
        this.str = str;
        this.end = str.length();
        this.foldCase = z;
    }

    public int getOffset() {
        return this.start;
    }

    public void setOffset(int i) {
        this.start = i;
    }

    public void adjustOffset(int i) {
        this.start += i;
    }

    public void adjustOffsetByCodePoint() {
        this.start += Character.charCount(getCodePoint());
    }

    public void setLength(int i) {
        this.end = this.start + i;
    }

    public void resetLength() {
        this.end = this.str.length();
    }

    @Override // java.lang.CharSequence
    public int length() {
        return this.end - this.start;
    }

    @Override // java.lang.CharSequence
    public char charAt(int i) {
        return this.str.charAt(i + this.start);
    }

    @Override // java.lang.CharSequence
    public CharSequence subSequence(int i, int i2) {
        String str = this.str;
        int i3 = this.start;
        return str.subSequence(i + i3, i2 + i3);
    }

    public int getCodePoint() {
        char cCharAt = this.str.charAt(this.start);
        if (!Character.isHighSurrogate(cCharAt)) {
            return cCharAt;
        }
        int i = this.start;
        if (i + 1 >= this.end) {
            return cCharAt;
        }
        char cCharAt2 = this.str.charAt(i + 1);
        return Character.isLowSurrogate(cCharAt2) ? Character.toCodePoint(cCharAt, cCharAt2) : cCharAt;
    }

    public int codePointAt(int i) {
        return this.str.codePointAt(this.start + i);
    }

    public boolean startsWith(int i) {
        return codePointsEqual(getCodePoint(), i, this.foldCase);
    }

    public boolean startsWith(UnicodeSet unicodeSet) {
        int codePoint = getCodePoint();
        if (codePoint == -1) {
            return false;
        }
        return unicodeSet.contains(codePoint);
    }

    public boolean startsWith(CharSequence charSequence) {
        if (charSequence == null || charSequence.length() == 0 || length() == 0) {
            return false;
        }
        return codePointsEqual(Character.codePointAt(this, 0), Character.codePointAt(charSequence, 0), this.foldCase);
    }

    public int getCommonPrefixLength(CharSequence charSequence) {
        return getPrefixLengthInternal(charSequence, this.foldCase);
    }

    public int getCaseSensitivePrefixLength(CharSequence charSequence) {
        return getPrefixLengthInternal(charSequence, false);
    }

    private int getPrefixLengthInternal(CharSequence charSequence, boolean z) {
        int iCharCount = 0;
        while (iCharCount < Math.min(length(), charSequence.length())) {
            int iCodePointAt = Character.codePointAt(this, iCharCount);
            if (!codePointsEqual(iCodePointAt, Character.codePointAt(charSequence, iCharCount), z)) {
                break;
            }
            iCharCount += Character.charCount(iCodePointAt);
        }
        return iCharCount;
    }

    private static final boolean codePointsEqual(int i, int i2, boolean z) {
        if (i == i2) {
            return true;
        }
        return z && UCharacter.foldCase(i, true) == UCharacter.foldCase(i2, true);
    }

    public boolean contentEquals(CharSequence charSequence) {
        return Utility.charSequenceEquals(this, charSequence);
    }

    @Override // java.lang.CharSequence
    public String toString() {
        return this.str.substring(0, this.start) + "[" + this.str.substring(this.start, this.end) + "]" + this.str.substring(this.end);
    }

    public String asString() {
        return this.str.substring(this.start, this.end);
    }
}
