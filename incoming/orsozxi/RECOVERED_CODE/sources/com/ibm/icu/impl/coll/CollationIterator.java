package com.ibm.icu.impl.coll;

import androidx.core.view.MotionEventCompat;
import com.ibm.icu.impl.Trie2_32;
import com.ibm.icu.util.BytesTrie;
import com.ibm.icu.util.CharsTrie;
import com.ibm.icu.util.ICUException;

/* loaded from: classes2.dex */
public abstract class CollationIterator {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    protected static final long NO_CP_AND_CE32 = -4294967104L;
    private CEBuffer ceBuffer;
    private int cesIndex;
    protected final CollationData data;
    private boolean isNumeric;
    private int numCpFwd;
    private SkippedState skipped;
    protected final Trie2_32 trie;

    protected static final boolean isLeadSurrogate(int i) {
        return (i & (-1024)) == 55296;
    }

    private static final boolean isSurrogate(int i) {
        return (i & (-2048)) == 55296;
    }

    protected static final boolean isTrailSurrogate(int i) {
        return (i & (-1024)) == 56320;
    }

    protected abstract void backwardNumCodePoints(int i);

    protected boolean forbidSurrogateCodePoints() {
        return false;
    }

    protected abstract void forwardNumCodePoints(int i);

    public abstract int getOffset();

    protected char handleGetTrailSurrogate() {
        return (char) 0;
    }

    public int hashCode() {
        return 0;
    }

    protected long makeCodePointAndCE32Pair(int i, int i2) {
        return (i2 & 4294967295L) | (i << 32);
    }

    public abstract int nextCodePoint();

    public abstract int previousCodePoint();

    public abstract void resetToOffset(int i);

    private static final class CEBuffer {
        private static final int INITIAL_CAPACITY = 40;
        int length = 0;
        private long[] buffer = new long[40];

        CEBuffer() {
        }

        void append(long j) {
            if (this.length >= 40) {
                ensureAppendCapacity(1);
            }
            long[] jArr = this.buffer;
            int i = this.length;
            this.length = i + 1;
            jArr[i] = j;
        }

        void appendUnsafe(long j) {
            long[] jArr = this.buffer;
            int i = this.length;
            this.length = i + 1;
            jArr[i] = j;
        }

        void ensureAppendCapacity(int i) {
            int i2;
            int length = this.buffer.length;
            if (this.length + i <= length) {
                return;
            }
            do {
                length = length < 1000 ? length * 4 : length * 2;
                i2 = this.length;
            } while (length < i2 + i);
            long[] jArr = new long[length];
            System.arraycopy(this.buffer, 0, jArr, 0, i2);
            this.buffer = jArr;
        }

        void incLength() {
            if (this.length >= 40) {
                ensureAppendCapacity(1);
            }
            this.length++;
        }

        long set(int i, long j) {
            this.buffer[i] = j;
            return j;
        }

        long get(int i) {
            return this.buffer[i];
        }

        long[] getCEs() {
            return this.buffer;
        }
    }

    private static final class SkippedState {
        static final /* synthetic */ boolean $assertionsDisabled = false;
        private int pos;
        private int skipLengthAtMatch;
        private final StringBuilder oldBuffer = new StringBuilder();
        private final StringBuilder newBuffer = new StringBuilder();
        private CharsTrie.State state = new CharsTrie.State();

        SkippedState() {
        }

        void clear() {
            this.oldBuffer.setLength(0);
            this.pos = 0;
        }

        boolean isEmpty() {
            return this.oldBuffer.length() == 0;
        }

        boolean hasNext() {
            return this.pos < this.oldBuffer.length();
        }

        int next() {
            int iCodePointAt = this.oldBuffer.codePointAt(this.pos);
            this.pos += Character.charCount(iCodePointAt);
            return iCodePointAt;
        }

        void incBeyond() {
            this.pos++;
        }

        int backwardNumCodePoints(int i) {
            int length = this.oldBuffer.length();
            int i2 = this.pos;
            int i3 = i2 - length;
            if (i3 <= 0) {
                this.pos = this.oldBuffer.offsetByCodePoints(i2, -i);
                return 0;
            }
            if (i3 >= i) {
                this.pos = i2 - i;
                return i;
            }
            this.pos = this.oldBuffer.offsetByCodePoints(length, i3 - i);
            return i3;
        }

        void setFirstSkipped(int i) {
            this.skipLengthAtMatch = 0;
            this.newBuffer.setLength(0);
            this.newBuffer.appendCodePoint(i);
        }

        void skip(int i) {
            this.newBuffer.appendCodePoint(i);
        }

        void recordMatch() {
            this.skipLengthAtMatch = this.newBuffer.length();
        }

        void replaceMatch() {
            int length = this.oldBuffer.length();
            if (this.pos > length) {
                this.pos = length;
            }
            this.oldBuffer.delete(0, this.pos).insert(0, this.newBuffer, 0, this.skipLengthAtMatch);
            this.pos = 0;
        }

        void saveTrieState(CharsTrie charsTrie) {
            charsTrie.saveState(this.state);
        }

        void resetToTrieState(CharsTrie charsTrie) {
            charsTrie.resetToState(this.state);
        }
    }

    public CollationIterator(CollationData collationData) {
        this.trie = collationData.trie;
        this.data = collationData;
        this.numCpFwd = -1;
        this.isNumeric = false;
        this.ceBuffer = null;
    }

    public CollationIterator(CollationData collationData, boolean z) {
        this.trie = collationData.trie;
        this.data = collationData;
        this.numCpFwd = -1;
        this.isNumeric = z;
        this.ceBuffer = new CEBuffer();
    }

    public boolean equals(Object obj) {
        if (obj == null || !getClass().equals(obj.getClass())) {
            return false;
        }
        CollationIterator collationIterator = (CollationIterator) obj;
        if (this.ceBuffer.length != collationIterator.ceBuffer.length || this.cesIndex != collationIterator.cesIndex || this.numCpFwd != collationIterator.numCpFwd || this.isNumeric != collationIterator.isNumeric) {
            return false;
        }
        for (int i = 0; i < this.ceBuffer.length; i++) {
            if (this.ceBuffer.get(i) != collationIterator.ceBuffer.get(i)) {
                return false;
            }
        }
        return true;
    }

    public final long nextCE() {
        CollationData collationData;
        if (this.cesIndex < this.ceBuffer.length) {
            CEBuffer cEBuffer = this.ceBuffer;
            int i = this.cesIndex;
            this.cesIndex = i + 1;
            return cEBuffer.get(i);
        }
        this.ceBuffer.incLength();
        long jHandleNextCE32 = handleNextCE32();
        int i2 = (int) (jHandleNextCE32 >> 32);
        int ce32 = (int) jHandleNextCE32;
        int i3 = ce32 & 255;
        if (i3 < 192) {
            CEBuffer cEBuffer2 = this.ceBuffer;
            int i4 = this.cesIndex;
            this.cesIndex = i4 + 1;
            return cEBuffer2.set(i4, ((ce32 & MotionEventCompat.ACTION_POINTER_INDEX_MASK) << 16) | (((-65536) & ce32) << 32) | (i3 << 8));
        }
        if (i3 != 192) {
            collationData = this.data;
        } else {
            if (i2 < 0) {
                CEBuffer cEBuffer3 = this.ceBuffer;
                int i5 = this.cesIndex;
                this.cesIndex = i5 + 1;
                return cEBuffer3.set(i5, Collation.NO_CE);
            }
            CollationData collationData2 = this.data.base;
            ce32 = collationData2.getCE32(i2);
            int i6 = ce32 & 255;
            if (i6 < 192) {
                CEBuffer cEBuffer4 = this.ceBuffer;
                int i7 = this.cesIndex;
                this.cesIndex = i7 + 1;
                return cEBuffer4.set(i7, ((ce32 & MotionEventCompat.ACTION_POINTER_INDEX_MASK) << 16) | (((-65536) & ce32) << 32) | (i6 << 8));
            }
            collationData = collationData2;
            i3 = i6;
        }
        if (i3 == 193) {
            CEBuffer cEBuffer5 = this.ceBuffer;
            int i8 = this.cesIndex;
            this.cesIndex = i8 + 1;
            return cEBuffer5.set(i8, ((ce32 - i3) << 32) | 83887360);
        }
        return nextCEFromCE32(collationData, i2, ce32);
    }

    public final int fetchCEs() {
        while (nextCE() != Collation.NO_CE) {
            this.cesIndex = this.ceBuffer.length;
        }
        return this.ceBuffer.length;
    }

    final void setCurrentCE(long j) {
        this.ceBuffer.set(this.cesIndex - 1, j);
    }

    public final long previousCE(UVector32 uVector32) {
        CollationData collationData;
        if (this.ceBuffer.length > 0) {
            CEBuffer cEBuffer = this.ceBuffer;
            int i = cEBuffer.length - 1;
            cEBuffer.length = i;
            return cEBuffer.get(i);
        }
        uVector32.removeAllElements();
        int offset = getOffset();
        int iPreviousCodePoint = previousCodePoint();
        if (iPreviousCodePoint < 0) {
            return Collation.NO_CE;
        }
        if (this.data.isUnsafeBackward(iPreviousCodePoint, this.isNumeric)) {
            return previousCEUnsafe(iPreviousCodePoint, uVector32);
        }
        int ce32 = this.data.getCE32(iPreviousCodePoint);
        if (ce32 == 192) {
            CollationData collationData2 = this.data.base;
            collationData = collationData2;
            ce32 = collationData2.getCE32(iPreviousCodePoint);
        } else {
            collationData = this.data;
        }
        if (Collation.isSimpleOrLongCE32(ce32)) {
            return Collation.ceFromCE32(ce32);
        }
        appendCEsFromCE32(collationData, iPreviousCodePoint, ce32, false);
        if (this.ceBuffer.length > 1) {
            uVector32.addElement(getOffset());
            while (uVector32.size() <= this.ceBuffer.length) {
                uVector32.addElement(offset);
            }
        }
        CEBuffer cEBuffer2 = this.ceBuffer;
        int i2 = cEBuffer2.length - 1;
        cEBuffer2.length = i2;
        return cEBuffer2.get(i2);
    }

    public final int getCEsLength() {
        return this.ceBuffer.length;
    }

    public final long getCE(int i) {
        return this.ceBuffer.get(i);
    }

    public final long[] getCEs() {
        return this.ceBuffer.getCEs();
    }

    final void clearCEs() {
        this.ceBuffer.length = 0;
        this.cesIndex = 0;
    }

    public final void clearCEsIfNoneRemaining() {
        if (this.cesIndex == this.ceBuffer.length) {
            clearCEs();
        }
    }

    protected final void reset() {
        this.ceBuffer.length = 0;
        this.cesIndex = 0;
        SkippedState skippedState = this.skipped;
        if (skippedState != null) {
            skippedState.clear();
        }
    }

    protected final void reset(boolean z) {
        if (this.ceBuffer == null) {
            this.ceBuffer = new CEBuffer();
        }
        reset();
        this.isNumeric = z;
    }

    protected long handleNextCE32() {
        int iNextCodePoint = nextCodePoint();
        return iNextCodePoint < 0 ? NO_CP_AND_CE32 : makeCodePointAndCE32Pair(iNextCodePoint, this.data.getCE32(iNextCodePoint));
    }

    protected int getDataCE32(int i) {
        return this.data.getCE32(i);
    }

    protected int getCE32FromBuilderData(int i) {
        throw new ICUException("internal program error: should be unreachable");
    }

    /* JADX WARN: Code restructure failed: missing block: B:13:0x0027, code lost:
    
        r8.ceBuffer.append(com.ibm.icu.impl.coll.Collation.unassignedCEFromCodePoint(r10));
     */
    /* JADX WARN: Code restructure failed: missing block: B:14:0x0030, code lost:
    
        return;
     */
    /* JADX WARN: Removed duplicated region for block: B:125:0x012e A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:133:0x0000 A[SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    protected final void appendCEsFromCE32(com.ibm.icu.impl.coll.CollationData r9, int r10, int r11, boolean r12) {
        /*
            Method dump skipped, instructions count: 510
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ibm.icu.impl.coll.CollationIterator.appendCEsFromCE32(com.ibm.icu.impl.coll.CollationData, int, int, boolean):void");
    }

    private final long nextCEFromCE32(CollationData collationData, int i, int i2) {
        this.ceBuffer.length--;
        appendCEsFromCE32(collationData, i, i2, true);
        CEBuffer cEBuffer = this.ceBuffer;
        int i3 = this.cesIndex;
        this.cesIndex = i3 + 1;
        return cEBuffer.get(i3);
    }

    private final int getCE32FromPrefix(CollationData collationData, int i) {
        BytesTrie.Result resultNextForCodePoint;
        int iIndexFromCE32 = Collation.indexFromCE32(i);
        int cE32FromContexts = collationData.getCE32FromContexts(iIndexFromCE32);
        CharsTrie charsTrie = new CharsTrie(collationData.contexts, iIndexFromCE32 + 2);
        int i2 = 0;
        do {
            int iPreviousCodePoint = previousCodePoint();
            if (iPreviousCodePoint < 0) {
                break;
            }
            i2++;
            resultNextForCodePoint = charsTrie.nextForCodePoint(iPreviousCodePoint);
            if (resultNextForCodePoint.hasValue()) {
                cE32FromContexts = charsTrie.getValue();
            }
        } while (resultNextForCodePoint.hasNext());
        forwardNumCodePoints(i2);
        return cE32FromContexts;
    }

    private final int nextSkippedCodePoint() {
        SkippedState skippedState = this.skipped;
        if (skippedState != null && skippedState.hasNext()) {
            return this.skipped.next();
        }
        if (this.numCpFwd == 0) {
            return -1;
        }
        int iNextCodePoint = nextCodePoint();
        SkippedState skippedState2 = this.skipped;
        if (skippedState2 != null && !skippedState2.isEmpty() && iNextCodePoint >= 0) {
            this.skipped.incBeyond();
        }
        int i = this.numCpFwd;
        if (i > 0 && iNextCodePoint >= 0) {
            this.numCpFwd = i - 1;
        }
        return iNextCodePoint;
    }

    private final void backwardNumSkipped(int i) {
        SkippedState skippedState = this.skipped;
        if (skippedState != null && !skippedState.isEmpty()) {
            i = this.skipped.backwardNumCodePoints(i);
        }
        backwardNumCodePoints(i);
        int i2 = this.numCpFwd;
        if (i2 >= 0) {
            this.numCpFwd = i2 + i;
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:21:0x0046, code lost:
    
        return r11;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    private final int nextCE32FromContraction(com.ibm.icu.impl.coll.CollationData r7, int r8, java.lang.CharSequence r9, int r10, int r11, int r12) {
        /*
            r6 = this;
            com.ibm.icu.util.CharsTrie r2 = new com.ibm.icu.util.CharsTrie
            r2.<init>(r9, r10)
            com.ibm.icu.impl.coll.CollationIterator$SkippedState r9 = r6.skipped
            if (r9 == 0) goto L14
            boolean r9 = r9.isEmpty()
            if (r9 != 0) goto L14
            com.ibm.icu.impl.coll.CollationIterator$SkippedState r9 = r6.skipped
            r9.saveTrieState(r2)
        L14:
            com.ibm.icu.util.BytesTrie$Result r9 = r2.firstForCodePoint(r12)
            r10 = 1
            r3 = r11
            r11 = 1
            r0 = 1
        L1c:
            boolean r1 = r9.hasValue()
            if (r1 == 0) goto L47
            int r11 = r2.getValue()
            boolean r9 = r9.hasNext()
            if (r9 == 0) goto L46
            int r9 = r6.nextSkippedCodePoint()
            if (r9 >= 0) goto L33
            goto L46
        L33:
            com.ibm.icu.impl.coll.CollationIterator$SkippedState r12 = r6.skipped
            if (r12 == 0) goto L42
            boolean r12 = r12.isEmpty()
            if (r12 != 0) goto L42
            com.ibm.icu.impl.coll.CollationIterator$SkippedState r12 = r6.skipped
            r12.saveTrieState(r2)
        L42:
            r12 = r9
            r3 = r11
            r11 = 1
            goto L55
        L46:
            return r11
        L47:
            com.ibm.icu.util.BytesTrie$Result r1 = com.ibm.icu.util.BytesTrie.Result.NO_MATCH
            if (r9 == r1) goto L5c
            int r9 = r6.nextSkippedCodePoint()
            if (r9 >= 0) goto L52
            goto L5c
        L52:
            int r11 = r11 + 1
            r12 = r9
        L55:
            int r0 = r0 + 1
            com.ibm.icu.util.BytesTrie$Result r9 = r2.nextForCodePoint(r12)
            goto L1c
        L5c:
            r9 = r8 & 1024(0x400, float:1.435E-42)
            if (r9 == 0) goto L87
            r8 = r8 & 256(0x100, float:3.59E-43)
            if (r8 == 0) goto L66
            if (r11 >= r0) goto L87
        L66:
            if (r11 <= r10) goto L74
            r6.backwardNumSkipped(r11)
            int r8 = r6.nextSkippedCodePoint()
            int r11 = r11 + (-1)
            int r0 = r0 - r11
            r5 = r8
            goto L76
        L74:
            r10 = r11
            r5 = r12
        L76:
            r4 = r0
            int r8 = r7.getFCD16(r5)
            r9 = 255(0xff, float:3.57E-43)
            if (r8 <= r9) goto L86
            r0 = r6
            r1 = r7
            int r7 = r0.nextCE32FromDiscontiguousContraction(r1, r2, r3, r4, r5)
            return r7
        L86:
            r11 = r10
        L87:
            r6.backwardNumSkipped(r11)
            return r3
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ibm.icu.impl.coll.CollationIterator.nextCE32FromContraction(com.ibm.icu.impl.coll.CollationData, int, java.lang.CharSequence, int, int, int):int");
    }

    /* JADX WARN: Removed duplicated region for block: B:35:0x0088  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    private final int nextCE32FromDiscontiguousContraction(com.ibm.icu.impl.coll.CollationData r9, com.ibm.icu.util.CharsTrie r10, int r11, int r12, int r13) {
        /*
            r8 = this;
            int r0 = r9.getFCD16(r13)
            int r1 = r8.nextSkippedCodePoint()
            r2 = 1
            if (r1 >= 0) goto Lf
            r8.backwardNumSkipped(r2)
            return r11
        Lf:
            int r12 = r12 + r2
            r3 = 255(0xff, float:3.57E-43)
            r0 = r0 & r3
            int r4 = r9.getFCD16(r1)
            r5 = 2
            if (r4 > r3) goto L1e
            r8.backwardNumSkipped(r5)
            return r11
        L1e:
            com.ibm.icu.impl.coll.CollationIterator$SkippedState r6 = r8.skipped
            if (r6 == 0) goto L2f
            boolean r6 = r6.isEmpty()
            if (r6 == 0) goto L29
            goto L2f
        L29:
            com.ibm.icu.impl.coll.CollationIterator$SkippedState r12 = r8.skipped
            r12.resetToTrieState(r10)
            goto L5e
        L2f:
            com.ibm.icu.impl.coll.CollationIterator$SkippedState r6 = r8.skipped
            if (r6 != 0) goto L3a
            com.ibm.icu.impl.coll.CollationIterator$SkippedState r6 = new com.ibm.icu.impl.coll.CollationIterator$SkippedState
            r6.<init>()
            r8.skipped = r6
        L3a:
            r10.reset()
            if (r12 <= r5) goto L59
            r8.backwardNumCodePoints(r12)
            int r6 = r8.nextCodePoint()
            r10.firstForCodePoint(r6)
            r6 = 3
        L4a:
            if (r6 >= r12) goto L56
            int r7 = r8.nextCodePoint()
            r10.nextForCodePoint(r7)
            int r6 = r6 + 1
            goto L4a
        L56:
            r8.forwardNumCodePoints(r5)
        L59:
            com.ibm.icu.impl.coll.CollationIterator$SkippedState r12 = r8.skipped
            r12.saveTrieState(r10)
        L5e:
            com.ibm.icu.impl.coll.CollationIterator$SkippedState r12 = r8.skipped
            r12.setFirstSkipped(r13)
        L63:
            int r12 = r4 >> 8
            if (r0 >= r12) goto L88
            com.ibm.icu.util.BytesTrie$Result r12 = r10.nextForCodePoint(r1)
            boolean r13 = r12.hasValue()
            if (r13 == 0) goto L88
            int r11 = r10.getValue()
            r5 = 0
            com.ibm.icu.impl.coll.CollationIterator$SkippedState r13 = r8.skipped
            r13.recordMatch()
            boolean r12 = r12.hasNext()
            if (r12 != 0) goto L82
            goto La4
        L82:
            com.ibm.icu.impl.coll.CollationIterator$SkippedState r12 = r8.skipped
            r12.saveTrieState(r10)
            goto L95
        L88:
            com.ibm.icu.impl.coll.CollationIterator$SkippedState r12 = r8.skipped
            r12.skip(r1)
            com.ibm.icu.impl.coll.CollationIterator$SkippedState r12 = r8.skipped
            r12.resetToTrieState(r10)
            r12 = r4 & 255(0xff, float:3.57E-43)
            r0 = r12
        L95:
            int r1 = r8.nextSkippedCodePoint()
            if (r1 >= 0) goto L9c
            goto La4
        L9c:
            int r5 = r5 + 1
            int r4 = r9.getFCD16(r1)
            if (r4 > r3) goto L63
        La4:
            r8.backwardNumSkipped(r5)
            com.ibm.icu.impl.coll.CollationIterator$SkippedState r10 = r8.skipped
            boolean r10 = r10.isEmpty()
            com.ibm.icu.impl.coll.CollationIterator$SkippedState r12 = r8.skipped
            r12.replaceMatch()
            if (r10 == 0) goto Le8
            com.ibm.icu.impl.coll.CollationIterator$SkippedState r10 = r8.skipped
            boolean r10 = r10.isEmpty()
            if (r10 != 0) goto Le8
            r10 = -1
        Lbd:
            r8.appendCEsFromCE32(r9, r10, r11, r2)
            com.ibm.icu.impl.coll.CollationIterator$SkippedState r9 = r8.skipped
            boolean r9 = r9.hasNext()
            if (r9 != 0) goto Lce
            com.ibm.icu.impl.coll.CollationIterator$SkippedState r9 = r8.skipped
            r9.clear()
            goto Le9
        Lce:
            com.ibm.icu.impl.coll.CollationIterator$SkippedState r9 = r8.skipped
            int r10 = r9.next()
            int r11 = r8.getDataCE32(r10)
            r9 = 192(0xc0, float:2.69E-43)
            if (r11 != r9) goto Le5
            com.ibm.icu.impl.coll.CollationData r9 = r8.data
            com.ibm.icu.impl.coll.CollationData r9 = r9.base
            int r11 = r9.getCE32(r10)
            goto Lbd
        Le5:
            com.ibm.icu.impl.coll.CollationData r9 = r8.data
            goto Lbd
        Le8:
            r2 = r11
        Le9:
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ibm.icu.impl.coll.CollationIterator.nextCE32FromDiscontiguousContraction(com.ibm.icu.impl.coll.CollationData, com.ibm.icu.util.CharsTrie, int, int, int):int");
    }

    private final long previousCEUnsafe(int i, UVector32 uVector32) {
        int iPreviousCodePoint;
        int i2 = 1;
        do {
            iPreviousCodePoint = previousCodePoint();
            if (iPreviousCodePoint < 0) {
                break;
            }
            i2++;
        } while (this.data.isUnsafeBackward(iPreviousCodePoint, this.isNumeric));
        this.numCpFwd = i2;
        this.cesIndex = 0;
        int offset = getOffset();
        while (true) {
            int i3 = this.numCpFwd;
            if (i3 > 0) {
                this.numCpFwd = i3 - 1;
                nextCE();
                this.cesIndex = this.ceBuffer.length;
                uVector32.addElement(offset);
                offset = getOffset();
                while (uVector32.size() < this.ceBuffer.length) {
                    uVector32.addElement(offset);
                }
            } else {
                uVector32.addElement(offset);
                this.numCpFwd = -1;
                backwardNumCodePoints(i2);
                this.cesIndex = 0;
                CEBuffer cEBuffer = this.ceBuffer;
                int i4 = cEBuffer.length - 1;
                cEBuffer.length = i4;
                return cEBuffer.get(i4);
            }
        }
    }

    private final void appendNumericCEs(int i, boolean z) {
        int iNextCodePoint;
        StringBuilder sb = new StringBuilder();
        if (z) {
            while (true) {
                sb.append(Collation.digitFromCE32(i));
                if (this.numCpFwd == 0 || (iNextCodePoint = nextCodePoint()) < 0) {
                    break;
                }
                int ce32 = this.data.getCE32(iNextCodePoint);
                i = ce32 == 192 ? this.data.base.getCE32(iNextCodePoint) : ce32;
                if (!Collation.hasCE32Tag(i, 10)) {
                    backwardNumCodePoints(1);
                    break;
                } else {
                    int i2 = this.numCpFwd;
                    if (i2 > 0) {
                        this.numCpFwd = i2 - 1;
                    }
                }
            }
        } else {
            while (true) {
                sb.append(Collation.digitFromCE32(i));
                int iPreviousCodePoint = previousCodePoint();
                if (iPreviousCodePoint < 0) {
                    break;
                }
                int ce322 = this.data.getCE32(iPreviousCodePoint);
                i = ce322 == 192 ? this.data.base.getCE32(iPreviousCodePoint) : ce322;
                if (!Collation.hasCE32Tag(i, 10)) {
                    forwardNumCodePoints(1);
                    break;
                }
            }
            sb.reverse();
        }
        int i3 = 0;
        while (true) {
            if (i3 >= sb.length() - 1 || sb.charAt(i3) != 0) {
                int length = sb.length() - i3;
                if (length > 254) {
                    length = 254;
                }
                int i4 = length + i3;
                appendNumericSegmentCEs(sb.subSequence(i3, i4));
                if (i4 >= sb.length()) {
                    return;
                } else {
                    i3 = i4;
                }
            } else {
                i3++;
            }
        }
    }

    private final void appendNumericSegmentCEs(CharSequence charSequence) {
        int iCharAt;
        int i;
        int length = charSequence.length();
        long j = this.data.numericPrimary;
        int i2 = 8;
        if (length <= 7) {
            int iCharAt2 = charSequence.charAt(0);
            for (int i3 = 1; i3 < length; i3++) {
                iCharAt2 = (iCharAt2 * 10) + charSequence.charAt(i3);
            }
            if (iCharAt2 < 74) {
                this.ceBuffer.append(Collation.makeCE(j | ((iCharAt2 + 2) << 16)));
                return;
            }
            int i4 = iCharAt2 - 74;
            if (i4 < 10160) {
                this.ceBuffer.append(Collation.makeCE(j | ((76 + (i4 / 254)) << 16) | (((i4 % 254) + 2) << 8)));
                return;
            }
            int i5 = i4 - 10160;
            if (i5 < 1032256) {
                long j2 = j | ((i5 % 254) + 2);
                int i6 = i5 / 254;
                this.ceBuffer.append(Collation.makeCE(j2 | (((i6 % 254) + 2) << 8) | ((116 + ((i6 / 254) % 254)) << 16)));
                return;
            }
        }
        long j3 = ((((length + 1) / 2) + 128) << 16) | j;
        while (charSequence.charAt(length - 1) == 0 && charSequence.charAt(length - 2) == 0) {
            length -= 2;
        }
        if ((length & 1) != 0) {
            iCharAt = charSequence.charAt(0);
            i = 1;
        } else {
            iCharAt = (charSequence.charAt(0) * '\n') + charSequence.charAt(1);
            i = 2;
        }
        int iCharAt3 = (iCharAt * 2) + 11;
        while (i < length) {
            if (i2 == 0) {
                this.ceBuffer.append(Collation.makeCE(iCharAt3 | j3));
                j3 = j;
                i2 = 16;
            } else {
                j3 |= iCharAt3 << i2;
                i2 -= 8;
            }
            iCharAt3 = (((charSequence.charAt(i) * '\n') + charSequence.charAt(i + 1)) * 2) + 11;
            i += 2;
        }
        this.ceBuffer.append(Collation.makeCE(((iCharAt3 - 1) << i2) | j3));
    }
}
