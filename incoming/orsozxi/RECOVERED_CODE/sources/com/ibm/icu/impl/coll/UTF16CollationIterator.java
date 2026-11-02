package com.ibm.icu.impl.coll;

/* loaded from: classes2.dex */
public class UTF16CollationIterator extends CollationIterator {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    protected int limit;
    protected int pos;
    protected CharSequence seq;
    protected int start;

    @Override // com.ibm.icu.impl.coll.CollationIterator
    public int hashCode() {
        return 42;
    }

    public UTF16CollationIterator(CollationData collationData) {
        super(collationData);
    }

    public UTF16CollationIterator(CollationData collationData, boolean z, CharSequence charSequence, int i) {
        super(collationData, z);
        this.seq = charSequence;
        this.start = 0;
        this.pos = i;
        this.limit = charSequence.length();
    }

    @Override // com.ibm.icu.impl.coll.CollationIterator
    public boolean equals(Object obj) {
        if (!super.equals(obj)) {
            return false;
        }
        UTF16CollationIterator uTF16CollationIterator = (UTF16CollationIterator) obj;
        return this.pos - this.start == uTF16CollationIterator.pos - uTF16CollationIterator.start;
    }

    @Override // com.ibm.icu.impl.coll.CollationIterator
    public void resetToOffset(int i) {
        reset();
        this.pos = this.start + i;
    }

    @Override // com.ibm.icu.impl.coll.CollationIterator
    public int getOffset() {
        return this.pos - this.start;
    }

    public void setText(boolean z, CharSequence charSequence, int i) {
        reset(z);
        this.seq = charSequence;
        this.start = 0;
        this.pos = i;
        this.limit = charSequence.length();
    }

    @Override // com.ibm.icu.impl.coll.CollationIterator
    public int nextCodePoint() {
        int i;
        int i2 = this.pos;
        if (i2 == this.limit) {
            return -1;
        }
        CharSequence charSequence = this.seq;
        this.pos = i2 + 1;
        char cCharAt = charSequence.charAt(i2);
        if (!Character.isHighSurrogate(cCharAt) || (i = this.pos) == this.limit) {
            return cCharAt;
        }
        char cCharAt2 = this.seq.charAt(i);
        if (!Character.isLowSurrogate(cCharAt2)) {
            return cCharAt;
        }
        this.pos++;
        return Character.toCodePoint(cCharAt, cCharAt2);
    }

    @Override // com.ibm.icu.impl.coll.CollationIterator
    public int previousCodePoint() {
        int i;
        int i2 = this.pos;
        if (i2 == this.start) {
            return -1;
        }
        CharSequence charSequence = this.seq;
        int i3 = i2 - 1;
        this.pos = i3;
        char cCharAt = charSequence.charAt(i3);
        if (!Character.isLowSurrogate(cCharAt) || (i = this.pos) == this.start) {
            return cCharAt;
        }
        char cCharAt2 = this.seq.charAt(i - 1);
        if (!Character.isHighSurrogate(cCharAt2)) {
            return cCharAt;
        }
        this.pos--;
        return Character.toCodePoint(cCharAt2, cCharAt);
    }

    @Override // com.ibm.icu.impl.coll.CollationIterator
    protected long handleNextCE32() {
        int i = this.pos;
        if (i == this.limit) {
            return -4294967104L;
        }
        CharSequence charSequence = this.seq;
        this.pos = i + 1;
        char cCharAt = charSequence.charAt(i);
        return makeCodePointAndCE32Pair(cCharAt, this.trie.getFromU16SingleLead(cCharAt));
    }

    @Override // com.ibm.icu.impl.coll.CollationIterator
    protected char handleGetTrailSurrogate() {
        int i = this.pos;
        if (i == this.limit) {
            return (char) 0;
        }
        char cCharAt = this.seq.charAt(i);
        if (Character.isLowSurrogate(cCharAt)) {
            this.pos++;
        }
        return cCharAt;
    }

    @Override // com.ibm.icu.impl.coll.CollationIterator
    protected void forwardNumCodePoints(int i) {
        int i2;
        while (i > 0) {
            int i3 = this.pos;
            if (i3 == this.limit) {
                return;
            }
            CharSequence charSequence = this.seq;
            this.pos = i3 + 1;
            i--;
            if (Character.isHighSurrogate(charSequence.charAt(i3)) && (i2 = this.pos) != this.limit && Character.isLowSurrogate(this.seq.charAt(i2))) {
                this.pos++;
            }
        }
    }

    @Override // com.ibm.icu.impl.coll.CollationIterator
    protected void backwardNumCodePoints(int i) {
        int i2;
        while (i > 0) {
            int i3 = this.pos;
            if (i3 == this.start) {
                return;
            }
            CharSequence charSequence = this.seq;
            int i4 = i3 - 1;
            this.pos = i4;
            i--;
            if (Character.isLowSurrogate(charSequence.charAt(i4)) && (i2 = this.pos) != this.start && Character.isHighSurrogate(this.seq.charAt(i2 - 1))) {
                this.pos--;
            }
        }
    }
}
