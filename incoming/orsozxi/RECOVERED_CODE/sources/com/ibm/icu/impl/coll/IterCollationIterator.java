package com.ibm.icu.impl.coll;

import com.ibm.icu.text.UCharacterIterator;

/* loaded from: classes2.dex */
public class IterCollationIterator extends CollationIterator {
    protected UCharacterIterator iter;

    public IterCollationIterator(CollationData collationData, boolean z, UCharacterIterator uCharacterIterator) {
        super(collationData, z);
        this.iter = uCharacterIterator;
    }

    @Override // com.ibm.icu.impl.coll.CollationIterator
    public void resetToOffset(int i) {
        reset();
        this.iter.setIndex(i);
    }

    @Override // com.ibm.icu.impl.coll.CollationIterator
    public int getOffset() {
        return this.iter.getIndex();
    }

    @Override // com.ibm.icu.impl.coll.CollationIterator
    public int nextCodePoint() {
        return this.iter.nextCodePoint();
    }

    @Override // com.ibm.icu.impl.coll.CollationIterator
    public int previousCodePoint() {
        return this.iter.previousCodePoint();
    }

    @Override // com.ibm.icu.impl.coll.CollationIterator
    protected long handleNextCE32() {
        int next = this.iter.next();
        if (next < 0) {
            return -4294967104L;
        }
        return makeCodePointAndCE32Pair(next, this.trie.getFromU16SingleLead((char) next));
    }

    @Override // com.ibm.icu.impl.coll.CollationIterator
    protected char handleGetTrailSurrogate() {
        int next = this.iter.next();
        if (!isTrailSurrogate(next) && next >= 0) {
            this.iter.previous();
        }
        return (char) next;
    }

    @Override // com.ibm.icu.impl.coll.CollationIterator
    protected void forwardNumCodePoints(int i) {
        this.iter.moveCodePointIndex(i);
    }

    @Override // com.ibm.icu.impl.coll.CollationIterator
    protected void backwardNumCodePoints(int i) {
        this.iter.moveCodePointIndex(-i);
    }
}
