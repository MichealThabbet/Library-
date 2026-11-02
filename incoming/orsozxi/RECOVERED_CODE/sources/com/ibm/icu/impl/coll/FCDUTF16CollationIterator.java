package com.ibm.icu.impl.coll;

import com.ibm.icu.impl.Normalizer2Impl;
import java.io.IOException;

/* loaded from: classes2.dex */
public final class FCDUTF16CollationIterator extends UTF16CollationIterator {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final int rawStart = 0;
    private int checkDir;
    private final Normalizer2Impl nfcImpl;
    private StringBuilder normalized;
    private int rawLimit;
    private CharSequence rawSeq;
    private int segmentLimit;
    private int segmentStart;

    @Override // com.ibm.icu.impl.coll.UTF16CollationIterator, com.ibm.icu.impl.coll.CollationIterator
    public int hashCode() {
        return 42;
    }

    public FCDUTF16CollationIterator(CollationData collationData) {
        super(collationData);
        this.nfcImpl = collationData.nfcImpl;
    }

    public FCDUTF16CollationIterator(CollationData collationData, boolean z, CharSequence charSequence, int i) {
        super(collationData, z, charSequence, i);
        this.rawSeq = charSequence;
        this.segmentStart = i;
        this.rawLimit = charSequence.length();
        this.nfcImpl = collationData.nfcImpl;
        this.checkDir = 1;
    }

    @Override // com.ibm.icu.impl.coll.UTF16CollationIterator, com.ibm.icu.impl.coll.CollationIterator
    public boolean equals(Object obj) {
        if (!(obj instanceof CollationIterator) || !equals(obj) || !(obj instanceof FCDUTF16CollationIterator)) {
            return false;
        }
        FCDUTF16CollationIterator fCDUTF16CollationIterator = (FCDUTF16CollationIterator) obj;
        int i = this.checkDir;
        if (i != fCDUTF16CollationIterator.checkDir) {
            return false;
        }
        if (i == 0) {
            if ((this.seq == this.rawSeq) != (fCDUTF16CollationIterator.seq == fCDUTF16CollationIterator.rawSeq)) {
                return false;
            }
        }
        return (this.checkDir != 0 || this.seq == this.rawSeq) ? this.pos - 0 == fCDUTF16CollationIterator.pos - 0 : this.segmentStart - 0 == fCDUTF16CollationIterator.segmentStart - 0 && this.pos - this.start == fCDUTF16CollationIterator.pos - fCDUTF16CollationIterator.start;
    }

    @Override // com.ibm.icu.impl.coll.UTF16CollationIterator, com.ibm.icu.impl.coll.CollationIterator
    public void resetToOffset(int i) {
        reset();
        this.seq = this.rawSeq;
        int i2 = i + 0;
        this.pos = i2;
        this.segmentStart = i2;
        this.start = i2;
        this.limit = this.rawLimit;
        this.checkDir = 1;
    }

    @Override // com.ibm.icu.impl.coll.UTF16CollationIterator, com.ibm.icu.impl.coll.CollationIterator
    public int getOffset() {
        int i;
        if (this.checkDir != 0 || this.seq == this.rawSeq) {
            i = this.pos;
        } else if (this.pos == this.start) {
            i = this.segmentStart;
        } else {
            i = this.segmentLimit;
        }
        return i + 0;
    }

    @Override // com.ibm.icu.impl.coll.UTF16CollationIterator
    public void setText(boolean z, CharSequence charSequence, int i) {
        super.setText(z, charSequence, i);
        this.rawSeq = charSequence;
        this.segmentStart = i;
        int length = charSequence.length();
        this.limit = length;
        this.rawLimit = length;
        this.checkDir = 1;
    }

    @Override // com.ibm.icu.impl.coll.UTF16CollationIterator, com.ibm.icu.impl.coll.CollationIterator
    public int nextCodePoint() throws IOException {
        char cCharAt;
        while (true) {
            int i = this.checkDir;
            if (i > 0) {
                if (this.pos == this.limit) {
                    return -1;
                }
                CharSequence charSequence = this.seq;
                int i2 = this.pos;
                this.pos = i2 + 1;
                cCharAt = charSequence.charAt(i2);
                if (CollationFCD.hasTccc(cCharAt) && (CollationFCD.maybeTibetanCompositeVowel(cCharAt) || (this.pos != this.limit && CollationFCD.hasLccc(this.seq.charAt(this.pos))))) {
                    this.pos--;
                    nextSegment();
                    CharSequence charSequence2 = this.seq;
                    int i3 = this.pos;
                    this.pos = i3 + 1;
                    cCharAt = charSequence2.charAt(i3);
                }
            } else {
                if (i == 0 && this.pos != this.limit) {
                    CharSequence charSequence3 = this.seq;
                    int i4 = this.pos;
                    this.pos = i4 + 1;
                    cCharAt = charSequence3.charAt(i4);
                    break;
                }
                switchToForward();
            }
        }
        if (!Character.isHighSurrogate(cCharAt) || this.pos == this.limit) {
            return cCharAt;
        }
        char cCharAt2 = this.seq.charAt(this.pos);
        if (!Character.isLowSurrogate(cCharAt2)) {
            return cCharAt;
        }
        this.pos++;
        return Character.toCodePoint(cCharAt, cCharAt2);
    }

    @Override // com.ibm.icu.impl.coll.UTF16CollationIterator, com.ibm.icu.impl.coll.CollationIterator
    public int previousCodePoint() throws IOException {
        char cCharAt;
        while (true) {
            int i = this.checkDir;
            if (i < 0) {
                if (this.pos == this.start) {
                    return -1;
                }
                CharSequence charSequence = this.seq;
                int i2 = this.pos - 1;
                this.pos = i2;
                cCharAt = charSequence.charAt(i2);
                if (CollationFCD.hasLccc(cCharAt) && (CollationFCD.maybeTibetanCompositeVowel(cCharAt) || (this.pos != this.start && CollationFCD.hasTccc(this.seq.charAt(this.pos - 1))))) {
                    this.pos++;
                    previousSegment();
                    CharSequence charSequence2 = this.seq;
                    int i3 = this.pos - 1;
                    this.pos = i3;
                    cCharAt = charSequence2.charAt(i3);
                }
            } else {
                if (i == 0 && this.pos != this.start) {
                    CharSequence charSequence3 = this.seq;
                    int i4 = this.pos - 1;
                    this.pos = i4;
                    cCharAt = charSequence3.charAt(i4);
                    break;
                }
                switchToBackward();
            }
        }
        if (!Character.isLowSurrogate(cCharAt) || this.pos == this.start) {
            return cCharAt;
        }
        char cCharAt2 = this.seq.charAt(this.pos - 1);
        if (!Character.isHighSurrogate(cCharAt2)) {
            return cCharAt;
        }
        this.pos--;
        return Character.toCodePoint(cCharAt2, cCharAt);
    }

    @Override // com.ibm.icu.impl.coll.UTF16CollationIterator, com.ibm.icu.impl.coll.CollationIterator
    protected long handleNextCE32() throws IOException {
        char cCharAt;
        while (true) {
            int i = this.checkDir;
            if (i > 0) {
                if (this.pos == this.limit) {
                    return -4294967104L;
                }
                CharSequence charSequence = this.seq;
                int i2 = this.pos;
                this.pos = i2 + 1;
                cCharAt = charSequence.charAt(i2);
                if (CollationFCD.hasTccc(cCharAt) && (CollationFCD.maybeTibetanCompositeVowel(cCharAt) || (this.pos != this.limit && CollationFCD.hasLccc(this.seq.charAt(this.pos))))) {
                    this.pos--;
                    nextSegment();
                    CharSequence charSequence2 = this.seq;
                    int i3 = this.pos;
                    this.pos = i3 + 1;
                    cCharAt = charSequence2.charAt(i3);
                }
            } else {
                if (i == 0 && this.pos != this.limit) {
                    CharSequence charSequence3 = this.seq;
                    int i4 = this.pos;
                    this.pos = i4 + 1;
                    cCharAt = charSequence3.charAt(i4);
                    break;
                }
                switchToForward();
            }
        }
        return makeCodePointAndCE32Pair(cCharAt, this.trie.getFromU16SingleLead(cCharAt));
    }

    @Override // com.ibm.icu.impl.coll.UTF16CollationIterator, com.ibm.icu.impl.coll.CollationIterator
    protected void forwardNumCodePoints(int i) {
        while (i > 0 && nextCodePoint() >= 0) {
            i--;
        }
    }

    @Override // com.ibm.icu.impl.coll.UTF16CollationIterator, com.ibm.icu.impl.coll.CollationIterator
    protected void backwardNumCodePoints(int i) {
        while (i > 0 && previousCodePoint() >= 0) {
            i--;
        }
    }

    private void switchToForward() {
        if (this.checkDir < 0) {
            int i = this.pos;
            this.segmentStart = i;
            this.start = i;
            if (this.pos == this.segmentLimit) {
                this.limit = this.rawLimit;
                this.checkDir = 1;
                return;
            } else {
                this.checkDir = 0;
                return;
            }
        }
        CharSequence charSequence = this.seq;
        CharSequence charSequence2 = this.rawSeq;
        if (charSequence != charSequence2) {
            this.seq = charSequence2;
            int i2 = this.segmentLimit;
            this.segmentStart = i2;
            this.start = i2;
            this.pos = i2;
        }
        this.limit = this.rawLimit;
        this.checkDir = 1;
    }

    /* JADX WARN: Code restructure failed: missing block: B:24:0x005d, code lost:
    
        r7.segmentLimit = r4;
        r7.limit = r4;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    private void nextSegment() throws java.io.IOException {
        /*
            r7 = this;
            int r0 = r7.pos
            r1 = 0
            r2 = 0
        L4:
            java.lang.CharSequence r3 = r7.seq
            int r3 = java.lang.Character.codePointAt(r3, r0)
            int r4 = java.lang.Character.charCount(r3)
            int r4 = r4 + r0
            com.ibm.icu.impl.Normalizer2Impl r5 = r7.nfcImpl
            int r3 = r5.getFCD16(r3)
            int r5 = r3 >> 8
            if (r5 != 0) goto L22
            int r6 = r7.pos
            if (r0 == r6) goto L22
            r7.segmentLimit = r0
            r7.limit = r0
            goto L61
        L22:
            if (r5 == 0) goto L52
            if (r2 > r5) goto L2c
            boolean r0 = com.ibm.icu.impl.coll.CollationFCD.isFCD16OfTibetanCompositeVowel(r3)
            if (r0 == 0) goto L52
        L2c:
            int r0 = r7.rawLimit
            if (r4 != r0) goto L31
            goto L46
        L31:
            java.lang.CharSequence r0 = r7.seq
            int r0 = java.lang.Character.codePointAt(r0, r4)
            int r2 = java.lang.Character.charCount(r0)
            int r2 = r2 + r4
            com.ibm.icu.impl.Normalizer2Impl r3 = r7.nfcImpl
            int r0 = r3.getFCD16(r0)
            r3 = 255(0xff, float:3.57E-43)
            if (r0 > r3) goto L50
        L46:
            int r0 = r7.pos
            r7.normalize(r0, r4)
            int r0 = r7.start
            r7.pos = r0
            goto L61
        L50:
            r4 = r2
            goto L2c
        L52:
            r2 = r3 & 255(0xff, float:3.57E-43)
            int r0 = r7.rawLimit
            if (r4 == r0) goto L5d
            if (r2 != 0) goto L5b
            goto L5d
        L5b:
            r0 = r4
            goto L4
        L5d:
            r7.segmentLimit = r4
            r7.limit = r4
        L61:
            r7.checkDir = r1
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ibm.icu.impl.coll.FCDUTF16CollationIterator.nextSegment():void");
    }

    private void switchToBackward() {
        if (this.checkDir > 0) {
            int i = this.pos;
            this.segmentLimit = i;
            this.limit = i;
            if (this.pos == this.segmentStart) {
                this.start = 0;
                this.checkDir = -1;
                return;
            } else {
                this.checkDir = 0;
                return;
            }
        }
        CharSequence charSequence = this.seq;
        CharSequence charSequence2 = this.rawSeq;
        if (charSequence != charSequence2) {
            this.seq = charSequence2;
            int i2 = this.segmentStart;
            this.segmentLimit = i2;
            this.limit = i2;
            this.pos = i2;
        }
        this.start = 0;
        this.checkDir = -1;
    }

    /* JADX WARN: Code restructure failed: missing block: B:14:0x0031, code lost:
    
        if (r3 <= 255) goto L36;
     */
    /* JADX WARN: Code restructure failed: missing block: B:15:0x0033, code lost:
    
        if (r4 != 0) goto L17;
     */
    /* JADX WARN: Code restructure failed: missing block: B:17:0x0036, code lost:
    
        r0 = java.lang.Character.codePointBefore(r7.seq, r4);
        r2 = r4 - java.lang.Character.charCount(r0);
        r3 = r7.nfcImpl.getFCD16(r0);
     */
    /* JADX WARN: Code restructure failed: missing block: B:18:0x0048, code lost:
    
        if (r3 != 0) goto L20;
     */
    /* JADX WARN: Code restructure failed: missing block: B:20:0x004b, code lost:
    
        r4 = r2;
     */
    /* JADX WARN: Code restructure failed: missing block: B:21:0x004d, code lost:
    
        normalize(r4, r7.pos);
        r7.pos = r7.limit;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    private void previousSegment() throws java.io.IOException {
        /*
            r7 = this;
            int r0 = r7.pos
            r1 = 0
            r2 = 0
        L4:
            java.lang.CharSequence r3 = r7.seq
            int r3 = java.lang.Character.codePointBefore(r3, r0)
            int r4 = java.lang.Character.charCount(r3)
            int r4 = r0 - r4
            com.ibm.icu.impl.Normalizer2Impl r5 = r7.nfcImpl
            int r3 = r5.getFCD16(r3)
            r5 = r3 & 255(0xff, float:3.57E-43)
            if (r5 != 0) goto L23
            int r6 = r7.pos
            if (r0 == r6) goto L23
            r7.segmentStart = r0
            r7.start = r0
            goto L64
        L23:
            if (r5 == 0) goto L57
            if (r2 == 0) goto L29
            if (r5 > r2) goto L2f
        L29:
            boolean r0 = com.ibm.icu.impl.coll.CollationFCD.isFCD16OfTibetanCompositeVowel(r3)
            if (r0 == 0) goto L57
        L2f:
            r0 = 255(0xff, float:3.57E-43)
            if (r3 <= r0) goto L4d
            if (r4 != 0) goto L36
            goto L4d
        L36:
            java.lang.CharSequence r0 = r7.seq
            int r0 = java.lang.Character.codePointBefore(r0, r4)
            int r2 = java.lang.Character.charCount(r0)
            int r2 = r4 - r2
            com.ibm.icu.impl.Normalizer2Impl r3 = r7.nfcImpl
            int r3 = r3.getFCD16(r0)
            if (r3 != 0) goto L4b
            goto L4d
        L4b:
            r4 = r2
            goto L2f
        L4d:
            int r0 = r7.pos
            r7.normalize(r4, r0)
            int r0 = r7.limit
            r7.pos = r0
            goto L64
        L57:
            int r2 = r3 >> 8
            if (r4 == 0) goto L60
            if (r2 != 0) goto L5e
            goto L60
        L5e:
            r0 = r4
            goto L4
        L60:
            r7.segmentStart = r4
            r7.start = r4
        L64:
            r7.checkDir = r1
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ibm.icu.impl.coll.FCDUTF16CollationIterator.previousSegment():void");
    }

    private void normalize(int i, int i2) throws IOException {
        if (this.normalized == null) {
            this.normalized = new StringBuilder();
        }
        this.nfcImpl.decompose(this.rawSeq, i, i2, this.normalized, i2 - i);
        this.segmentStart = i;
        this.segmentLimit = i2;
        this.seq = this.normalized;
        this.start = 0;
        this.limit = this.start + this.normalized.length();
    }
}
