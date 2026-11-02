package com.ibm.icu.impl;

import com.ibm.icu.text.Replaceable;
import com.ibm.icu.text.ReplaceableString;
import com.ibm.icu.text.UCharacterIterator;
import com.ibm.icu.text.UTF16;

/* loaded from: classes2.dex */
public class ReplaceableUCharacterIterator extends UCharacterIterator {
    private int currentIndex;
    private Replaceable replaceable;

    public ReplaceableUCharacterIterator(Replaceable replaceable) {
        if (replaceable == null) {
            throw new IllegalArgumentException();
        }
        this.replaceable = replaceable;
        this.currentIndex = 0;
    }

    public ReplaceableUCharacterIterator(String str) {
        if (str == null) {
            throw new IllegalArgumentException();
        }
        this.replaceable = new ReplaceableString(str);
        this.currentIndex = 0;
    }

    public ReplaceableUCharacterIterator(StringBuffer stringBuffer) {
        if (stringBuffer == null) {
            throw new IllegalArgumentException();
        }
        this.replaceable = new ReplaceableString(stringBuffer);
        this.currentIndex = 0;
    }

    @Override // com.ibm.icu.text.UCharacterIterator
    public Object clone() {
        try {
            return super.clone();
        } catch (CloneNotSupportedException unused) {
            return null;
        }
    }

    @Override // com.ibm.icu.text.UCharacterIterator
    public int current() {
        if (this.currentIndex < this.replaceable.length()) {
            return this.replaceable.charAt(this.currentIndex);
        }
        return -1;
    }

    @Override // com.ibm.icu.text.UCharacterIterator
    public int currentCodePoint() {
        int iCurrent = current();
        char c = (char) iCurrent;
        if (!UTF16.isLeadSurrogate(c)) {
            return iCurrent;
        }
        next();
        int iCurrent2 = current();
        previous();
        char c2 = (char) iCurrent2;
        return UTF16.isTrailSurrogate(c2) ? Character.toCodePoint(c, c2) : iCurrent;
    }

    @Override // com.ibm.icu.text.UCharacterIterator
    public int getLength() {
        return this.replaceable.length();
    }

    @Override // com.ibm.icu.text.UCharacterIterator
    public int getIndex() {
        return this.currentIndex;
    }

    @Override // com.ibm.icu.text.UCharacterIterator, com.ibm.icu.text.UForwardCharacterIterator
    public int next() {
        if (this.currentIndex >= this.replaceable.length()) {
            return -1;
        }
        Replaceable replaceable = this.replaceable;
        int i = this.currentIndex;
        this.currentIndex = i + 1;
        return replaceable.charAt(i);
    }

    @Override // com.ibm.icu.text.UCharacterIterator
    public int previous() {
        int i = this.currentIndex;
        if (i <= 0) {
            return -1;
        }
        Replaceable replaceable = this.replaceable;
        int i2 = i - 1;
        this.currentIndex = i2;
        return replaceable.charAt(i2);
    }

    @Override // com.ibm.icu.text.UCharacterIterator
    public void setIndex(int i) throws IndexOutOfBoundsException {
        if (i < 0 || i > this.replaceable.length()) {
            throw new IndexOutOfBoundsException();
        }
        this.currentIndex = i;
    }

    @Override // com.ibm.icu.text.UCharacterIterator
    public int getText(char[] cArr, int i) {
        int length = this.replaceable.length();
        if (i < 0 || i + length > cArr.length) {
            throw new IndexOutOfBoundsException(Integer.toString(length));
        }
        this.replaceable.getChars(0, length, cArr, i);
        return length;
    }
}
