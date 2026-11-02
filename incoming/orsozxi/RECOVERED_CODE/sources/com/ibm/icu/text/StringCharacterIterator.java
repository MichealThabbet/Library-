package com.ibm.icu.text;

import com.ibm.icu.util.ICUCloneNotSupportedException;
import java.text.CharacterIterator;

@Deprecated
/* loaded from: classes2.dex */
public final class StringCharacterIterator implements CharacterIterator {
    private int begin;
    private int end;
    private int pos;
    private String text;

    @Deprecated
    public StringCharacterIterator(String str) {
        this(str, 0);
    }

    @Deprecated
    public StringCharacterIterator(String str, int i) {
        this(str, 0, str.length(), i);
    }

    @Deprecated
    public StringCharacterIterator(String str, int i, int i2, int i3) {
        if (str == null) {
            throw null;
        }
        this.text = str;
        if (i < 0 || i > i2 || i2 > str.length()) {
            throw new IllegalArgumentException("Invalid substring range");
        }
        if (i3 < i || i3 > i2) {
            throw new IllegalArgumentException("Invalid position");
        }
        this.begin = i;
        this.end = i2;
        this.pos = i3;
    }

    @Deprecated
    public void setText(String str) {
        if (str == null) {
            throw null;
        }
        this.text = str;
        this.begin = 0;
        this.end = str.length();
        this.pos = 0;
    }

    @Override // java.text.CharacterIterator
    @Deprecated
    public char first() {
        this.pos = this.begin;
        return current();
    }

    @Override // java.text.CharacterIterator
    @Deprecated
    public char last() {
        int i = this.end;
        if (i != this.begin) {
            this.pos = i - 1;
        } else {
            this.pos = i;
        }
        return current();
    }

    @Override // java.text.CharacterIterator
    @Deprecated
    public char setIndex(int i) {
        if (i < this.begin || i > this.end) {
            throw new IllegalArgumentException("Invalid index");
        }
        this.pos = i;
        return current();
    }

    @Override // java.text.CharacterIterator
    @Deprecated
    public char current() {
        int i = this.pos;
        if (i < this.begin || i >= this.end) {
            return (char) 65535;
        }
        return this.text.charAt(i);
    }

    @Override // java.text.CharacterIterator
    @Deprecated
    public char next() {
        int i = this.pos;
        int i2 = this.end;
        if (i < i2 - 1) {
            int i3 = i + 1;
            this.pos = i3;
            return this.text.charAt(i3);
        }
        this.pos = i2;
        return (char) 65535;
    }

    @Override // java.text.CharacterIterator
    @Deprecated
    public char previous() {
        int i = this.pos;
        if (i <= this.begin) {
            return (char) 65535;
        }
        int i2 = i - 1;
        this.pos = i2;
        return this.text.charAt(i2);
    }

    @Override // java.text.CharacterIterator
    @Deprecated
    public int getBeginIndex() {
        return this.begin;
    }

    @Override // java.text.CharacterIterator
    @Deprecated
    public int getEndIndex() {
        return this.end;
    }

    @Override // java.text.CharacterIterator
    @Deprecated
    public int getIndex() {
        return this.pos;
    }

    @Deprecated
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof StringCharacterIterator)) {
            return false;
        }
        StringCharacterIterator stringCharacterIterator = (StringCharacterIterator) obj;
        return hashCode() == stringCharacterIterator.hashCode() && this.text.equals(stringCharacterIterator.text) && this.pos == stringCharacterIterator.pos && this.begin == stringCharacterIterator.begin && this.end == stringCharacterIterator.end;
    }

    @Deprecated
    public int hashCode() {
        return ((this.text.hashCode() ^ this.pos) ^ this.begin) ^ this.end;
    }

    @Override // java.text.CharacterIterator
    @Deprecated
    public Object clone() {
        try {
            return (StringCharacterIterator) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new ICUCloneNotSupportedException(e);
        }
    }
}
