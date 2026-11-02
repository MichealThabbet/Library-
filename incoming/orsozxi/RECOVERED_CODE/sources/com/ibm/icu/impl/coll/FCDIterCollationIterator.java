package com.ibm.icu.impl.coll;

import com.ibm.icu.impl.Normalizer2Impl;
import com.ibm.icu.text.UCharacterIterator;
import java.io.IOException;

/* loaded from: classes2.dex */
public final class FCDIterCollationIterator extends IterCollationIterator {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private int limit;
    private final Normalizer2Impl nfcImpl;
    private StringBuilder normalized;
    private int pos;
    private StringBuilder s;
    private int start;
    private State state;

    private enum State {
        ITER_CHECK_FWD,
        ITER_CHECK_BWD,
        ITER_IN_FCD_SEGMENT,
        IN_NORM_ITER_AT_LIMIT,
        IN_NORM_ITER_AT_START
    }

    public FCDIterCollationIterator(CollationData collationData, boolean z, UCharacterIterator uCharacterIterator, int i) {
        super(collationData, z, uCharacterIterator);
        this.state = State.ITER_CHECK_FWD;
        this.start = i;
        this.nfcImpl = collationData.nfcImpl;
    }

    @Override // com.ibm.icu.impl.coll.IterCollationIterator, com.ibm.icu.impl.coll.CollationIterator
    public void resetToOffset(int i) {
        super.resetToOffset(i);
        this.start = i;
        this.state = State.ITER_CHECK_FWD;
    }

    @Override // com.ibm.icu.impl.coll.IterCollationIterator, com.ibm.icu.impl.coll.CollationIterator
    public int getOffset() {
        if (this.state.compareTo(State.ITER_CHECK_BWD) <= 0) {
            return this.iter.getIndex();
        }
        if (this.state == State.ITER_IN_FCD_SEGMENT) {
            return this.pos;
        }
        if (this.pos == 0) {
            return this.start;
        }
        return this.limit;
    }

    /* JADX WARN: Code restructure failed: missing block: B:18:0x0038, code lost:
    
        if (isLeadSurrogate(r0) == false) goto L25;
     */
    /* JADX WARN: Code restructure failed: missing block: B:19:0x003a, code lost:
    
        r1 = r3.iter.next();
     */
    /* JADX WARN: Code restructure failed: missing block: B:20:0x0044, code lost:
    
        if (isTrailSurrogate(r1) == false) goto L23;
     */
    /* JADX WARN: Code restructure failed: missing block: B:22:0x004c, code lost:
    
        return java.lang.Character.toCodePoint((char) r0, (char) r1);
     */
    /* JADX WARN: Code restructure failed: missing block: B:23:0x004d, code lost:
    
        if (r1 < 0) goto L25;
     */
    /* JADX WARN: Code restructure failed: missing block: B:24:0x004f, code lost:
    
        r3.iter.previous();
     */
    /* JADX WARN: Code restructure failed: missing block: B:25:0x0054, code lost:
    
        return r0;
     */
    @Override // com.ibm.icu.impl.coll.IterCollationIterator, com.ibm.icu.impl.coll.CollationIterator
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public int nextCodePoint() {
        /*
            r3 = this;
        L0:
            com.ibm.icu.impl.coll.FCDIterCollationIterator$State r0 = r3.state
            com.ibm.icu.impl.coll.FCDIterCollationIterator$State r1 = com.ibm.icu.impl.coll.FCDIterCollationIterator.State.ITER_CHECK_FWD
            if (r0 != r1) goto L55
            com.ibm.icu.text.UCharacterIterator r0 = r3.iter
            int r0 = r0.next()
            if (r0 >= 0) goto Lf
            return r0
        Lf:
            boolean r1 = com.ibm.icu.impl.coll.CollationFCD.hasTccc(r0)
            if (r1 == 0) goto L34
            boolean r1 = com.ibm.icu.impl.coll.CollationFCD.maybeTibetanCompositeVowel(r0)
            if (r1 != 0) goto L27
            com.ibm.icu.text.UCharacterIterator r1 = r3.iter
            int r1 = r1.current()
            boolean r1 = com.ibm.icu.impl.coll.CollationFCD.hasLccc(r1)
            if (r1 == 0) goto L34
        L27:
            com.ibm.icu.text.UCharacterIterator r0 = r3.iter
            r0.previous()
            boolean r0 = r3.nextSegment()
            if (r0 != 0) goto L0
            r0 = -1
            return r0
        L34:
            boolean r1 = isLeadSurrogate(r0)
            if (r1 == 0) goto L54
            com.ibm.icu.text.UCharacterIterator r1 = r3.iter
            int r1 = r1.next()
            boolean r2 = isTrailSurrogate(r1)
            if (r2 == 0) goto L4d
            char r0 = (char) r0
            char r1 = (char) r1
            int r0 = java.lang.Character.toCodePoint(r0, r1)
            return r0
        L4d:
            if (r1 < 0) goto L54
            com.ibm.icu.text.UCharacterIterator r1 = r3.iter
            r1.previous()
        L54:
            return r0
        L55:
            com.ibm.icu.impl.coll.FCDIterCollationIterator$State r0 = r3.state
            com.ibm.icu.impl.coll.FCDIterCollationIterator$State r1 = com.ibm.icu.impl.coll.FCDIterCollationIterator.State.ITER_IN_FCD_SEGMENT
            if (r0 != r1) goto L71
            int r0 = r3.pos
            int r1 = r3.limit
            if (r0 == r1) goto L71
            com.ibm.icu.text.UCharacterIterator r0 = r3.iter
            int r0 = r0.nextCodePoint()
            int r1 = r3.pos
            int r2 = java.lang.Character.charCount(r0)
            int r1 = r1 + r2
            r3.pos = r1
            return r0
        L71:
            com.ibm.icu.impl.coll.FCDIterCollationIterator$State r0 = r3.state
            com.ibm.icu.impl.coll.FCDIterCollationIterator$State r1 = com.ibm.icu.impl.coll.FCDIterCollationIterator.State.IN_NORM_ITER_AT_LIMIT
            int r0 = r0.compareTo(r1)
            if (r0 < 0) goto L97
            int r0 = r3.pos
            java.lang.StringBuilder r1 = r3.normalized
            int r1 = r1.length()
            if (r0 == r1) goto L97
            java.lang.StringBuilder r0 = r3.normalized
            int r1 = r3.pos
            int r0 = r0.codePointAt(r1)
            int r1 = r3.pos
            int r2 = java.lang.Character.charCount(r0)
            int r1 = r1 + r2
            r3.pos = r1
            return r0
        L97:
            r3.switchToForward()
            goto L0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ibm.icu.impl.coll.FCDIterCollationIterator.nextCodePoint():int");
    }

    /* JADX WARN: Code restructure failed: missing block: B:32:0x0069, code lost:
    
        return r0;
     */
    @Override // com.ibm.icu.impl.coll.IterCollationIterator, com.ibm.icu.impl.coll.CollationIterator
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public int previousCodePoint() {
        /*
            r4 = this;
        L0:
            com.ibm.icu.impl.coll.FCDIterCollationIterator$State r0 = r4.state
            com.ibm.icu.impl.coll.FCDIterCollationIterator$State r1 = com.ibm.icu.impl.coll.FCDIterCollationIterator.State.ITER_CHECK_BWD
            if (r0 != r1) goto L6a
            com.ibm.icu.text.UCharacterIterator r0 = r4.iter
            int r0 = r0.previous()
            r1 = -1
            if (r0 >= 0) goto L19
            r0 = 0
            r4.pos = r0
            r4.start = r0
            com.ibm.icu.impl.coll.FCDIterCollationIterator$State r0 = com.ibm.icu.impl.coll.FCDIterCollationIterator.State.ITER_IN_FCD_SEGMENT
            r4.state = r0
            return r1
        L19:
            boolean r2 = com.ibm.icu.impl.coll.CollationFCD.hasLccc(r0)
            if (r2 == 0) goto L69
            boolean r2 = com.ibm.icu.impl.coll.CollationFCD.maybeTibetanCompositeVowel(r0)
            if (r2 != 0) goto L55
            com.ibm.icu.text.UCharacterIterator r2 = r4.iter
            int r2 = r2.previous()
            boolean r3 = com.ibm.icu.impl.coll.CollationFCD.hasTccc(r2)
            if (r3 == 0) goto L32
            goto L56
        L32:
            boolean r1 = isTrailSurrogate(r0)
            if (r1 == 0) goto L4d
            if (r2 >= 0) goto L40
            com.ibm.icu.text.UCharacterIterator r1 = r4.iter
            int r2 = r1.previous()
        L40:
            boolean r1 = isLeadSurrogate(r2)
            if (r1 == 0) goto L4d
            char r1 = (char) r2
            char r0 = (char) r0
            int r0 = java.lang.Character.toCodePoint(r1, r0)
            return r0
        L4d:
            if (r2 < 0) goto L69
            com.ibm.icu.text.UCharacterIterator r1 = r4.iter
            r1.next()
            goto L69
        L55:
            r2 = -1
        L56:
            com.ibm.icu.text.UCharacterIterator r0 = r4.iter
            r0.next()
            if (r2 < 0) goto L62
            com.ibm.icu.text.UCharacterIterator r0 = r4.iter
            r0.next()
        L62:
            boolean r0 = r4.previousSegment()
            if (r0 != 0) goto L0
            return r1
        L69:
            return r0
        L6a:
            com.ibm.icu.impl.coll.FCDIterCollationIterator$State r0 = r4.state
            com.ibm.icu.impl.coll.FCDIterCollationIterator$State r1 = com.ibm.icu.impl.coll.FCDIterCollationIterator.State.ITER_IN_FCD_SEGMENT
            if (r0 != r1) goto L86
            int r0 = r4.pos
            int r1 = r4.start
            if (r0 == r1) goto L86
            com.ibm.icu.text.UCharacterIterator r0 = r4.iter
            int r0 = r0.previousCodePoint()
            int r1 = r4.pos
            int r2 = java.lang.Character.charCount(r0)
            int r1 = r1 - r2
            r4.pos = r1
            return r0
        L86:
            com.ibm.icu.impl.coll.FCDIterCollationIterator$State r0 = r4.state
            com.ibm.icu.impl.coll.FCDIterCollationIterator$State r1 = com.ibm.icu.impl.coll.FCDIterCollationIterator.State.IN_NORM_ITER_AT_LIMIT
            int r0 = r0.compareTo(r1)
            if (r0 < 0) goto La4
            int r0 = r4.pos
            if (r0 == 0) goto La4
            java.lang.StringBuilder r1 = r4.normalized
            int r0 = r1.codePointBefore(r0)
            int r1 = r4.pos
            int r2 = java.lang.Character.charCount(r0)
            int r1 = r1 - r2
            r4.pos = r1
            return r0
        La4:
            r4.switchToBackward()
            goto L0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ibm.icu.impl.coll.FCDIterCollationIterator.previousCodePoint():int");
    }

    @Override // com.ibm.icu.impl.coll.IterCollationIterator, com.ibm.icu.impl.coll.CollationIterator
    protected long handleNextCE32() {
        int next;
        while (true) {
            if (this.state == State.ITER_CHECK_FWD) {
                next = this.iter.next();
                if (next < 0) {
                    return -4294967104L;
                }
                if (!CollationFCD.hasTccc(next) || (!CollationFCD.maybeTibetanCompositeVowel(next) && !CollationFCD.hasLccc(this.iter.current()))) {
                    break;
                }
                this.iter.previous();
                if (!nextSegment()) {
                    return 192L;
                }
            } else {
                if (this.state == State.ITER_IN_FCD_SEGMENT && this.pos != this.limit) {
                    next = this.iter.next();
                    this.pos++;
                    break;
                }
                if (this.state.compareTo(State.IN_NORM_ITER_AT_LIMIT) >= 0 && this.pos != this.normalized.length()) {
                    StringBuilder sb = this.normalized;
                    int i = this.pos;
                    this.pos = i + 1;
                    next = sb.charAt(i);
                    break;
                }
                switchToForward();
            }
        }
        return makeCodePointAndCE32Pair(next, this.trie.getFromU16SingleLead((char) next));
    }

    @Override // com.ibm.icu.impl.coll.IterCollationIterator, com.ibm.icu.impl.coll.CollationIterator
    protected char handleGetTrailSurrogate() {
        if (this.state.compareTo(State.ITER_IN_FCD_SEGMENT) <= 0) {
            int next = this.iter.next();
            if (isTrailSurrogate(next)) {
                if (this.state == State.ITER_IN_FCD_SEGMENT) {
                    this.pos++;
                }
            } else if (next >= 0) {
                this.iter.previous();
            }
            return (char) next;
        }
        char cCharAt = this.normalized.charAt(this.pos);
        if (Character.isLowSurrogate(cCharAt)) {
            this.pos++;
        }
        return cCharAt;
    }

    @Override // com.ibm.icu.impl.coll.IterCollationIterator, com.ibm.icu.impl.coll.CollationIterator
    protected void forwardNumCodePoints(int i) {
        while (i > 0 && nextCodePoint() >= 0) {
            i--;
        }
    }

    @Override // com.ibm.icu.impl.coll.IterCollationIterator, com.ibm.icu.impl.coll.CollationIterator
    protected void backwardNumCodePoints(int i) {
        while (i > 0 && previousCodePoint() >= 0) {
            i--;
        }
    }

    private void switchToForward() {
        if (this.state == State.ITER_CHECK_BWD) {
            int index = this.iter.getIndex();
            this.pos = index;
            this.start = index;
            if (index == this.limit) {
                this.state = State.ITER_CHECK_FWD;
                return;
            } else {
                this.state = State.ITER_IN_FCD_SEGMENT;
                return;
            }
        }
        if (this.state != State.ITER_IN_FCD_SEGMENT) {
            if (this.state == State.IN_NORM_ITER_AT_START) {
                this.iter.moveIndex(this.limit - this.start);
            }
            this.start = this.limit;
        }
        this.state = State.ITER_CHECK_FWD;
    }

    /* JADX WARN: Code restructure failed: missing block: B:20:0x004a, code lost:
    
        r0 = r7.iter.nextCodePoint();
     */
    /* JADX WARN: Code restructure failed: missing block: B:21:0x0050, code lost:
    
        if (r0 >= 0) goto L23;
     */
    /* JADX WARN: Code restructure failed: missing block: B:24:0x005b, code lost:
    
        if (r7.nfcImpl.getFCD16(r0) > 255) goto L28;
     */
    /* JADX WARN: Code restructure failed: missing block: B:25:0x005d, code lost:
    
        r7.iter.previousCodePoint();
     */
    /* JADX WARN: Code restructure failed: missing block: B:26:0x0062, code lost:
    
        normalize(r7.s);
        r0 = r7.pos;
        r7.start = r0;
        r7.limit = r0 + r7.s.length();
        r7.state = com.ibm.icu.impl.coll.FCDIterCollationIterator.State.IN_NORM_ITER_AT_LIMIT;
        r7.pos = 0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:27:0x007a, code lost:
    
        return true;
     */
    /* JADX WARN: Code restructure failed: missing block: B:28:0x007b, code lost:
    
        r7.s.appendCodePoint(r0);
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    private boolean nextSegment() throws java.io.IOException {
        /*
            r7 = this;
            com.ibm.icu.text.UCharacterIterator r0 = r7.iter
            int r0 = r0.getIndex()
            r7.pos = r0
            java.lang.StringBuilder r0 = r7.s
            r1 = 0
            if (r0 != 0) goto L15
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>()
            r7.s = r0
            goto L18
        L15:
            r0.setLength(r1)
        L18:
            r0 = 0
        L19:
            com.ibm.icu.text.UCharacterIterator r2 = r7.iter
            int r2 = r2.nextCodePoint()
            r3 = 1
            if (r2 >= 0) goto L23
            goto L85
        L23:
            com.ibm.icu.impl.Normalizer2Impl r4 = r7.nfcImpl
            int r4 = r4.getFCD16(r2)
            int r5 = r4 >> 8
            if (r5 != 0) goto L3b
            java.lang.StringBuilder r6 = r7.s
            int r6 = r6.length()
            if (r6 == 0) goto L3b
            com.ibm.icu.text.UCharacterIterator r0 = r7.iter
            r0.previousCodePoint()
            goto L85
        L3b:
            java.lang.StringBuilder r6 = r7.s
            r6.appendCodePoint(r2)
            if (r5 == 0) goto L81
            if (r0 > r5) goto L4a
            boolean r0 = com.ibm.icu.impl.coll.CollationFCD.isFCD16OfTibetanCompositeVowel(r4)
            if (r0 == 0) goto L81
        L4a:
            com.ibm.icu.text.UCharacterIterator r0 = r7.iter
            int r0 = r0.nextCodePoint()
            if (r0 >= 0) goto L53
            goto L62
        L53:
            com.ibm.icu.impl.Normalizer2Impl r2 = r7.nfcImpl
            int r2 = r2.getFCD16(r0)
            r4 = 255(0xff, float:3.57E-43)
            if (r2 > r4) goto L7b
            com.ibm.icu.text.UCharacterIterator r0 = r7.iter
            r0.previousCodePoint()
        L62:
            java.lang.StringBuilder r0 = r7.s
            r7.normalize(r0)
            int r0 = r7.pos
            r7.start = r0
            java.lang.StringBuilder r2 = r7.s
            int r2 = r2.length()
            int r0 = r0 + r2
            r7.limit = r0
            com.ibm.icu.impl.coll.FCDIterCollationIterator$State r0 = com.ibm.icu.impl.coll.FCDIterCollationIterator.State.IN_NORM_ITER_AT_LIMIT
            r7.state = r0
            r7.pos = r1
            return r3
        L7b:
            java.lang.StringBuilder r2 = r7.s
            r2.appendCodePoint(r0)
            goto L4a
        L81:
            r0 = r4 & 255(0xff, float:3.57E-43)
            if (r0 != 0) goto L19
        L85:
            int r0 = r7.pos
            java.lang.StringBuilder r1 = r7.s
            int r1 = r1.length()
            int r0 = r0 + r1
            r7.limit = r0
            com.ibm.icu.text.UCharacterIterator r0 = r7.iter
            java.lang.StringBuilder r1 = r7.s
            int r1 = r1.length()
            int r1 = -r1
            r0.moveIndex(r1)
            com.ibm.icu.impl.coll.FCDIterCollationIterator$State r0 = com.ibm.icu.impl.coll.FCDIterCollationIterator.State.ITER_IN_FCD_SEGMENT
            r7.state = r0
            return r3
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ibm.icu.impl.coll.FCDIterCollationIterator.nextSegment():boolean");
    }

    private void switchToBackward() {
        if (this.state == State.ITER_CHECK_FWD) {
            int index = this.iter.getIndex();
            this.pos = index;
            this.limit = index;
            if (index == this.start) {
                this.state = State.ITER_CHECK_BWD;
                return;
            } else {
                this.state = State.ITER_IN_FCD_SEGMENT;
                return;
            }
        }
        if (this.state != State.ITER_IN_FCD_SEGMENT) {
            if (this.state == State.IN_NORM_ITER_AT_LIMIT) {
                this.iter.moveIndex(this.start - this.limit);
            }
            this.limit = this.start;
        }
        this.state = State.ITER_CHECK_BWD;
    }

    /* JADX WARN: Code restructure failed: missing block: B:33:0x0095, code lost:
    
        r6.start = r6.pos - r6.s.length();
        r6.iter.moveIndex(r6.s.length());
        r6.state = com.ibm.icu.impl.coll.FCDIterCollationIterator.State.ITER_IN_FCD_SEGMENT;
     */
    /* JADX WARN: Code restructure failed: missing block: B:34:0x00af, code lost:
    
        return true;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    private boolean previousSegment() throws java.io.IOException {
        /*
            r6 = this;
            com.ibm.icu.text.UCharacterIterator r0 = r6.iter
            int r0 = r0.getIndex()
            r6.pos = r0
            java.lang.StringBuilder r0 = r6.s
            r1 = 0
            if (r0 != 0) goto L15
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>()
            r6.s = r0
            goto L18
        L15:
            r0.setLength(r1)
        L18:
            com.ibm.icu.text.UCharacterIterator r0 = r6.iter
            int r0 = r0.previousCodePoint()
            r2 = 1
            if (r0 >= 0) goto L23
            goto L95
        L23:
            com.ibm.icu.impl.Normalizer2Impl r3 = r6.nfcImpl
            int r3 = r3.getFCD16(r0)
            r4 = r3 & 255(0xff, float:3.57E-43)
            if (r4 != 0) goto L3b
            java.lang.StringBuilder r5 = r6.s
            int r5 = r5.length()
            if (r5 == 0) goto L3b
            com.ibm.icu.text.UCharacterIterator r0 = r6.iter
            r0.nextCodePoint()
            goto L95
        L3b:
            java.lang.StringBuilder r5 = r6.s
            r5.appendCodePoint(r0)
            if (r4 == 0) goto L91
            if (r1 == 0) goto L46
            if (r4 > r1) goto L4c
        L46:
            boolean r0 = com.ibm.icu.impl.coll.CollationFCD.isFCD16OfTibetanCompositeVowel(r3)
            if (r0 == 0) goto L91
        L4c:
            r0 = 255(0xff, float:3.57E-43)
            if (r3 <= r0) goto L6d
            com.ibm.icu.text.UCharacterIterator r0 = r6.iter
            int r0 = r0.previousCodePoint()
            if (r0 >= 0) goto L59
            goto L6d
        L59:
            com.ibm.icu.impl.Normalizer2Impl r1 = r6.nfcImpl
            int r3 = r1.getFCD16(r0)
            if (r3 != 0) goto L67
            com.ibm.icu.text.UCharacterIterator r0 = r6.iter
            r0.nextCodePoint()
            goto L6d
        L67:
            java.lang.StringBuilder r1 = r6.s
            r1.appendCodePoint(r0)
            goto L4c
        L6d:
            java.lang.StringBuilder r0 = r6.s
            r0.reverse()
            java.lang.StringBuilder r0 = r6.s
            r6.normalize(r0)
            int r0 = r6.pos
            r6.limit = r0
            java.lang.StringBuilder r1 = r6.s
            int r1 = r1.length()
            int r0 = r0 - r1
            r6.start = r0
            com.ibm.icu.impl.coll.FCDIterCollationIterator$State r0 = com.ibm.icu.impl.coll.FCDIterCollationIterator.State.IN_NORM_ITER_AT_START
            r6.state = r0
            java.lang.StringBuilder r0 = r6.normalized
            int r0 = r0.length()
            r6.pos = r0
            return r2
        L91:
            int r1 = r3 >> 8
            if (r1 != 0) goto L18
        L95:
            int r0 = r6.pos
            java.lang.StringBuilder r1 = r6.s
            int r1 = r1.length()
            int r0 = r0 - r1
            r6.start = r0
            com.ibm.icu.text.UCharacterIterator r0 = r6.iter
            java.lang.StringBuilder r1 = r6.s
            int r1 = r1.length()
            r0.moveIndex(r1)
            com.ibm.icu.impl.coll.FCDIterCollationIterator$State r0 = com.ibm.icu.impl.coll.FCDIterCollationIterator.State.ITER_IN_FCD_SEGMENT
            r6.state = r0
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ibm.icu.impl.coll.FCDIterCollationIterator.previousSegment():boolean");
    }

    private void normalize(CharSequence charSequence) throws IOException {
        if (this.normalized == null) {
            this.normalized = new StringBuilder();
        }
        this.nfcImpl.decompose(charSequence, this.normalized);
    }
}
