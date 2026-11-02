package com.ibm.icu.impl;

import com.ibm.icu.text.UCharacterIterator;
import java.text.CharacterIterator;

/* loaded from: classes2.dex */
public class CharacterIteratorWrapper extends UCharacterIterator {
    private CharacterIterator iterator;

    public CharacterIteratorWrapper(CharacterIterator characterIterator) {
        if (characterIterator == null) {
            throw new IllegalArgumentException();
        }
        this.iterator = characterIterator;
    }

    @Override // com.ibm.icu.text.UCharacterIterator
    public int current() {
        char cCurrent = this.iterator.current();
        if (cCurrent == 65535) {
            return -1;
        }
        return cCurrent;
    }

    @Override // com.ibm.icu.text.UCharacterIterator
    public int getLength() {
        return this.iterator.getEndIndex() - this.iterator.getBeginIndex();
    }

    @Override // com.ibm.icu.text.UCharacterIterator
    public int getIndex() {
        return this.iterator.getIndex();
    }

    @Override // com.ibm.icu.text.UCharacterIterator, com.ibm.icu.text.UForwardCharacterIterator
    public int next() {
        char cCurrent = this.iterator.current();
        this.iterator.next();
        if (cCurrent == 65535) {
            return -1;
        }
        return cCurrent;
    }

    @Override // com.ibm.icu.text.UCharacterIterator
    public int previous() {
        char cPrevious = this.iterator.previous();
        if (cPrevious == 65535) {
            return -1;
        }
        return cPrevious;
    }

    @Override // com.ibm.icu.text.UCharacterIterator
    public void setIndex(int i) {
        try {
            this.iterator.setIndex(i);
        } catch (IllegalArgumentException unused) {
            throw new IndexOutOfBoundsException();
        }
    }

    @Override // com.ibm.icu.text.UCharacterIterator
    public void setToLimit() {
        CharacterIterator characterIterator = this.iterator;
        characterIterator.setIndex(characterIterator.getEndIndex());
    }

    @Override // com.ibm.icu.text.UCharacterIterator
    public int getText(char[] cArr, int i) {
        int endIndex = this.iterator.getEndIndex() - this.iterator.getBeginIndex();
        int index = this.iterator.getIndex();
        if (i < 0 || i + endIndex > cArr.length) {
            throw new IndexOutOfBoundsException(Integer.toString(endIndex));
        }
        char cFirst = this.iterator.first();
        while (cFirst != 65535) {
            cArr[i] = cFirst;
            cFirst = this.iterator.next();
            i++;
        }
        this.iterator.setIndex(index);
        return endIndex;
    }

    @Override // com.ibm.icu.text.UCharacterIterator
    public Object clone() {
        try {
            CharacterIteratorWrapper characterIteratorWrapper = (CharacterIteratorWrapper) super.clone();
            characterIteratorWrapper.iterator = (CharacterIterator) this.iterator.clone();
            return characterIteratorWrapper;
        } catch (CloneNotSupportedException unused) {
            return null;
        }
    }

    @Override // com.ibm.icu.text.UCharacterIterator
    public int moveIndex(int i) {
        int endIndex = this.iterator.getEndIndex() - this.iterator.getBeginIndex();
        int index = this.iterator.getIndex() + i;
        if (index < 0) {
            endIndex = 0;
        } else if (index <= endIndex) {
            endIndex = index;
        }
        return this.iterator.setIndex(endIndex);
    }

    @Override // com.ibm.icu.text.UCharacterIterator
    public CharacterIterator getCharacterIterator() {
        return (CharacterIterator) this.iterator.clone();
    }
}
