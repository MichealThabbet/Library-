package com.ibm.icu.util;

import com.ibm.icu.text.DictionaryData;
import com.ibm.icu.util.CodePointMap;
import com.ibm.icu.util.CodePointTrie;
import it.piapostolos.android.pinotes.R2;
import java.util.Arrays;

/* loaded from: classes2.dex */
public final class MutableCodePointTrie extends CodePointMap implements Cloneable {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final byte ALL_SAME = 0;
    private static final int ASCII_I_LIMIT = 8;
    private static final int ASCII_LIMIT = 128;
    private static final int BMP_I_LIMIT = 4096;
    private static final int BMP_LIMIT = 65536;
    private static final byte I3_16 = 2;
    private static final byte I3_18 = 3;
    private static final byte I3_BMP = 1;
    private static final byte I3_NULL = 0;
    private static final int INDEX_3_18BIT_BLOCK_LENGTH = 36;
    private static final int INITIAL_DATA_LENGTH = 16384;
    private static final int I_LIMIT = 69632;
    private static final int MAX_DATA_LENGTH = 1114112;
    private static final int MAX_UNICODE = 1114111;
    private static final int MEDIUM_DATA_LENGTH = 131072;
    private static final byte MIXED = 1;
    private static final byte SAME_AS = 2;
    private static final int SMALL_DATA_BLOCKS_PER_BMP_BLOCK = 4;
    private static final int UNICODE_LIMIT = 1114112;
    private int dataLength;
    private int errorValue;
    private int highStart;
    private int highValue;
    private char[] index16;
    private int initialValue;
    private int origInitialValue;
    private byte[] flags = new byte[I_LIMIT];
    private int[] index = new int[4096];
    private int index3NullOffset = -1;
    private int[] data = new int[16384];
    private int dataNullOffset = -1;

    public MutableCodePointTrie(int i, int i2) {
        this.origInitialValue = i;
        this.initialValue = i;
        this.errorValue = i2;
        this.highValue = i;
    }

    /* renamed from: clone, reason: merged with bridge method [inline-methods] */
    public MutableCodePointTrie m27clone() {
        try {
            MutableCodePointTrie mutableCodePointTrie = (MutableCodePointTrie) super.clone();
            mutableCodePointTrie.index = new int[this.highStart <= 65536 ? 4096 : I_LIMIT];
            mutableCodePointTrie.flags = new byte[I_LIMIT];
            int i = this.highStart >> 4;
            for (int i2 = 0; i2 < i; i2++) {
                mutableCodePointTrie.index[i2] = this.index[i2];
                mutableCodePointTrie.flags[i2] = this.flags[i2];
            }
            mutableCodePointTrie.index3NullOffset = this.index3NullOffset;
            mutableCodePointTrie.data = (int[]) this.data.clone();
            mutableCodePointTrie.dataLength = this.dataLength;
            mutableCodePointTrie.dataNullOffset = this.dataNullOffset;
            mutableCodePointTrie.origInitialValue = this.origInitialValue;
            mutableCodePointTrie.initialValue = this.initialValue;
            mutableCodePointTrie.errorValue = this.errorValue;
            mutableCodePointTrie.highStart = this.highStart;
            mutableCodePointTrie.highValue = this.highValue;
            return mutableCodePointTrie;
        } catch (CloneNotSupportedException unused) {
            return null;
        }
    }

    public static MutableCodePointTrie fromCodePointMap(CodePointMap codePointMap) {
        int i = codePointMap.get(-1);
        int i2 = codePointMap.get(1114111);
        MutableCodePointTrie mutableCodePointTrie = new MutableCodePointTrie(i2, i);
        CodePointMap.Range range = new CodePointMap.Range();
        int i3 = 0;
        while (codePointMap.getRange(i3, null, range)) {
            int end = range.getEnd();
            int value = range.getValue();
            if (value != i2) {
                if (i3 == end) {
                    mutableCodePointTrie.set(i3, value);
                } else {
                    mutableCodePointTrie.setRange(i3, end, value);
                }
            }
            i3 = end + 1;
        }
        return mutableCodePointTrie;
    }

    private void clear() {
        this.dataNullOffset = -1;
        this.index3NullOffset = -1;
        this.dataLength = 0;
        int i = this.origInitialValue;
        this.initialValue = i;
        this.highValue = i;
        this.highStart = 0;
        this.index16 = null;
    }

    @Override // com.ibm.icu.util.CodePointMap
    public int get(int i) {
        if (i < 0 || 1114111 < i) {
            return this.errorValue;
        }
        if (i >= this.highStart) {
            return this.highValue;
        }
        int i2 = i >> 4;
        if (this.flags[i2] == 0) {
            return this.index[i2];
        }
        return this.data[this.index[i2] + (i & 15)];
    }

    private static final int maybeFilterValue(int i, int i2, int i3, CodePointMap.ValueFilter valueFilter) {
        return i == i2 ? i3 : valueFilter != null ? valueFilter.apply(i) : i;
    }

    /* JADX WARN: Removed duplicated region for block: B:45:0x0084  */
    /* JADX WARN: Removed duplicated region for block: B:68:0x009a A[EDGE_INSN: B:68:0x009a->B:52:0x009a BREAK  A[LOOP:1: B:43:0x007f->B:71:?], SYNTHETIC] */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:49:0x0093 -> B:39:0x0070). Please report as a decompilation issue!!! */
    @Override // com.ibm.icu.util.CodePointMap
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public boolean getRange(int r11, com.ibm.icu.util.CodePointMap.ValueFilter r12, com.ibm.icu.util.CodePointMap.Range r13) {
        /*
            r10 = this;
            r0 = 0
            if (r11 < 0) goto Lb3
            r1 = 1114111(0x10ffff, float:1.561202E-39)
            if (r1 >= r11) goto La
            goto Lb3
        La:
            int r2 = r10.highStart
            r3 = 1
            if (r11 < r2) goto L1b
            int r0 = r10.highValue
            if (r12 == 0) goto L17
            int r0 = r12.apply(r0)
        L17:
            r13.set(r11, r1, r0)
            return r3
        L1b:
            int r2 = r10.initialValue
            if (r12 == 0) goto L23
            int r2 = r12.apply(r2)
        L23:
            int r4 = r11 >> 4
            r7 = r11
            r6 = r4
            r4 = 0
            r5 = 0
        L29:
            byte[] r8 = r10.flags
            r8 = r8[r6]
            if (r8 != 0) goto L56
            int[] r8 = r10.index
            r8 = r8[r6]
            if (r0 == 0) goto L49
            if (r8 == r4) goto L51
            if (r12 == 0) goto L44
            int r4 = r10.initialValue
            int r4 = maybeFilterValue(r8, r4, r2, r12)
            if (r4 == r5) goto L42
            goto L44
        L42:
            r4 = r8
            goto L51
        L44:
            int r7 = r7 - r3
            r13.set(r11, r7, r5)
            return r3
        L49:
            int r0 = r10.initialValue
            int r5 = maybeFilterValue(r8, r0, r2, r12)
            r4 = r8
            r0 = 1
        L51:
            int r7 = r7 + 16
            r7 = r7 & (-16)
            goto L9a
        L56:
            int[] r8 = r10.index
            r8 = r8[r6]
            r9 = r7 & 15
            int r8 = r8 + r9
            int[] r9 = r10.data
            r9 = r9[r8]
            if (r0 == 0) goto L77
            if (r9 == r4) goto L7f
            if (r12 == 0) goto L72
            int r4 = r10.initialValue
            int r4 = maybeFilterValue(r9, r4, r2, r12)
            if (r4 == r5) goto L70
            goto L72
        L70:
            r4 = r9
            goto L7f
        L72:
            int r7 = r7 - r3
            r13.set(r11, r7, r5)
            return r3
        L77:
            int r0 = r10.initialValue
            int r5 = maybeFilterValue(r9, r0, r2, r12)
            r4 = r9
            r0 = 1
        L7f:
            int r7 = r7 + r3
            r9 = r7 & 15
            if (r9 == 0) goto L9a
            int[] r9 = r10.data
            int r8 = r8 + r3
            r9 = r9[r8]
            if (r9 == r4) goto L7f
            if (r12 == 0) goto L95
            int r4 = r10.initialValue
            int r4 = maybeFilterValue(r9, r4, r2, r12)
            if (r4 == r5) goto L70
        L95:
            int r7 = r7 - r3
            r13.set(r11, r7, r5)
            return r3
        L9a:
            int r6 = r6 + 1
            int r8 = r10.highStart
            if (r7 < r8) goto L29
            int r0 = r10.highValue
            int r4 = r10.initialValue
            int r12 = maybeFilterValue(r0, r4, r2, r12)
            if (r12 == r5) goto Laf
            int r7 = r7 - r3
            r13.set(r11, r7, r5)
            goto Lb2
        Laf:
            r13.set(r11, r1, r5)
        Lb2:
            return r3
        Lb3:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ibm.icu.util.MutableCodePointTrie.getRange(int, com.ibm.icu.util.CodePointMap$ValueFilter, com.ibm.icu.util.CodePointMap$Range):boolean");
    }

    private void writeBlock(int i, int i2) {
        Arrays.fill(this.data, i, i + 16, i2);
    }

    public void set(int i, int i2) {
        if (i < 0 || 1114111 < i) {
            throw new IllegalArgumentException("invalid code point");
        }
        ensureHighStart(i);
        this.data[getDataBlock(i >> 4) + (i & 15)] = i2;
    }

    private void fillBlock(int i, int i2, int i3, int i4) {
        Arrays.fill(this.data, i2 + i, i + i3, i4);
    }

    public void setRange(int i, int i2, int i3) {
        if (i < 0 || 1114111 < i || i2 < 0 || 1114111 < i2 || i > i2) {
            throw new IllegalArgumentException("invalid code point range");
        }
        ensureHighStart(i2);
        int i4 = i2 + 1;
        int i5 = i & 15;
        if (i5 != 0) {
            int dataBlock = getDataBlock(i >> 4);
            i = (i + 15) & (-16);
            if (i <= i4) {
                fillBlock(dataBlock, i5, 16, i3);
            } else {
                fillBlock(dataBlock, i5, i4 & 15, i3);
                return;
            }
        }
        int i6 = i4 & 15;
        int i7 = i4 & (-16);
        while (i < i7) {
            int i8 = i >> 4;
            if (this.flags[i8] == 0) {
                this.index[i8] = i3;
            } else {
                fillBlock(this.index[i8], 0, 16, i3);
            }
            i += 16;
        }
        if (i6 > 0) {
            fillBlock(getDataBlock(i >> 4), 0, i6, i3);
        }
    }

    public CodePointTrie buildImmutable(CodePointTrie.Type type, CodePointTrie.ValueWidth valueWidth) {
        if (type == null || valueWidth == null) {
            throw new IllegalArgumentException("The type and valueWidth must be specified.");
        }
        try {
            return build(type, valueWidth);
        } finally {
            clear();
        }
    }

    private void ensureHighStart(int i) {
        int i2 = this.highStart;
        if (i >= i2) {
            int i3 = (i + 512) & (-512);
            int i4 = i2 >> 4;
            int i5 = i3 >> 4;
            if (i5 > this.index.length) {
                int[] iArr = new int[I_LIMIT];
                for (int i6 = 0; i6 < i4; i6++) {
                    iArr[i6] = this.index[i6];
                }
                this.index = iArr;
            }
            do {
                this.flags[i4] = 0;
                this.index[i4] = this.initialValue;
                i4++;
            } while (i4 < i5);
            this.highStart = i3;
        }
    }

    private int allocDataBlock(int i) {
        int i2 = this.dataLength;
        int i3 = i + i2;
        int[] iArr = this.data;
        if (i3 > iArr.length) {
            int i4 = 1114112;
            if (iArr.length < 131072) {
                i4 = 131072;
            } else if (iArr.length >= 1114112) {
                throw new AssertionError();
            }
            int[] iArr2 = new int[i4];
            for (int i5 = 0; i5 < this.dataLength; i5++) {
                iArr2[i5] = this.data[i5];
            }
            this.data = iArr2;
        }
        this.dataLength = i3;
        return i2;
    }

    private int getDataBlock(int i) {
        if (this.flags[i] == 1) {
            return this.index[i];
        }
        if (i < 4096) {
            int iAllocDataBlock = allocDataBlock(64);
            int i2 = i & (-4);
            int i3 = i2 + 4;
            while (true) {
                writeBlock(iAllocDataBlock, this.index[i2]);
                this.flags[i2] = 1;
                int[] iArr = this.index;
                int i4 = i2 + 1;
                iArr[i2] = iAllocDataBlock;
                iAllocDataBlock += 16;
                if (i4 >= i3) {
                    return iArr[i];
                }
                i2 = i4;
            }
        } else {
            int iAllocDataBlock2 = allocDataBlock(16);
            if (iAllocDataBlock2 < 0) {
                return iAllocDataBlock2;
            }
            writeBlock(iAllocDataBlock2, this.index[i]);
            this.flags[i] = 1;
            this.index[i] = iAllocDataBlock2;
            return iAllocDataBlock2;
        }
    }

    private void maskValues(int i) {
        this.initialValue &= i;
        this.errorValue &= i;
        this.highValue &= i;
        int i2 = this.highStart >> 4;
        for (int i3 = 0; i3 < i2; i3++) {
            if (this.flags[i3] == 0) {
                int[] iArr = this.index;
                iArr[i3] = iArr[i3] & i;
            }
        }
        for (int i4 = 0; i4 < this.dataLength; i4++) {
            int[] iArr2 = this.data;
            iArr2[i4] = iArr2[i4] & i;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean equalBlocks(int[] iArr, int i, int[] iArr2, int i2, int i3) {
        while (i3 > 0 && iArr[i] == iArr2[i2]) {
            i++;
            i2++;
            i3--;
        }
        return i3 == 0;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean equalBlocks(char[] cArr, int i, int[] iArr, int i2, int i3) {
        while (i3 > 0 && cArr[i] == iArr[i2]) {
            i++;
            i2++;
            i3--;
        }
        return i3 == 0;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean equalBlocks(char[] cArr, int i, char[] cArr2, int i2, int i3) {
        while (i3 > 0 && cArr[i] == cArr2[i2]) {
            i++;
            i2++;
            i3--;
        }
        return i3 == 0;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean allValuesSameAs(int[] iArr, int i, int i2, int i3) {
        int i4 = i2 + i;
        while (i < i4 && iArr[i] == i3) {
            i++;
        }
        return i == i4;
    }

    private static int findSameBlock(char[] cArr, int i, int i2, char[] cArr2, int i3, int i4) {
        int i5 = i2 - i4;
        while (i <= i5) {
            if (equalBlocks(cArr, i, cArr2, i3, i4)) {
                return i;
            }
            i++;
        }
        return -1;
    }

    private static int findAllSameBlock(int[] iArr, int i, int i2, int i3, int i4) {
        int i5 = i2 - i4;
        while (i <= i5) {
            if (iArr[i] == i3) {
                for (int i6 = 1; i6 != i4; i6++) {
                    int i7 = i + i6;
                    if (iArr[i7] != i3) {
                        i = i7;
                    }
                }
                return i;
            }
            i++;
        }
        return -1;
    }

    private static int getOverlap(int[] iArr, int i, int[] iArr2, int i2, int i3) {
        int i4 = i3 - 1;
        while (i4 > 0 && !equalBlocks(iArr, i - i4, iArr2, i2, i4)) {
            i4--;
        }
        return i4;
    }

    private static int getOverlap(char[] cArr, int i, int[] iArr, int i2, int i3) {
        int i4 = i3 - 1;
        while (i4 > 0 && !equalBlocks(cArr, i - i4, iArr, i2, i4)) {
            i4--;
        }
        return i4;
    }

    private static int getOverlap(char[] cArr, int i, char[] cArr2, int i2, int i3) {
        int i4 = i3 - 1;
        while (i4 > 0 && !equalBlocks(cArr, i - i4, cArr2, i2, i4)) {
            i4--;
        }
        return i4;
    }

    private static int getAllSameOverlap(int[] iArr, int i, int i2, int i3) {
        int i4 = i - (i3 - 1);
        int i5 = i;
        while (i4 < i5 && iArr[i5 - 1] == i2) {
            i5--;
        }
        return i - i5;
    }

    private static boolean isStartOfSomeFastBlock(int i, int[] iArr, int i2) {
        for (int i3 = 0; i3 < i2; i3 += 4) {
            if (iArr[i3] == i) {
                return true;
            }
        }
        return false;
    }

    private int findHighStart() {
        boolean z;
        int i = this.highStart >> 4;
        do {
            z = false;
            if (i <= 0) {
                return 0;
            }
            i--;
            if (this.flags[i] == 0) {
                if (this.index[i] == this.highValue) {
                    z = true;
                }
            } else {
                int i2 = this.index[i];
                for (int i3 = 0; i3 != 16; i3++) {
                    if (this.data[i2 + i3] != this.highValue) {
                        break;
                    }
                }
                z = true;
            }
        } while (z);
        return (i + 1) << 4;
    }

    private static final class AllSameBlocks {
        static final /* synthetic */ boolean $assertionsDisabled = false;
        private static final int CAPACITY = 32;
        static final int NEW_UNIQUE = -1;
        static final int OVERFLOW = -2;
        private int length;
        private int[] indexes = new int[32];
        private int[] values = new int[32];
        private int[] refCounts = new int[32];
        private int mostRecent = -1;

        AllSameBlocks() {
        }

        int findOrAdd(int i, int i2, int i3) {
            int i4 = this.mostRecent;
            if (i4 >= 0 && this.values[i4] == i3) {
                int[] iArr = this.refCounts;
                iArr[i4] = iArr[i4] + i2;
                return this.indexes[i4];
            }
            int i5 = 0;
            while (true) {
                int i6 = this.length;
                if (i5 >= i6) {
                    if (i6 == 32) {
                        return -2;
                    }
                    this.mostRecent = i6;
                    this.indexes[i6] = i;
                    this.values[i6] = i3;
                    int[] iArr2 = this.refCounts;
                    this.length = i6 + 1;
                    iArr2[i6] = i2;
                    return -1;
                }
                if (this.values[i5] == i3) {
                    this.mostRecent = i5;
                    int[] iArr3 = this.refCounts;
                    iArr3[i5] = iArr3[i5] + i2;
                    return this.indexes[i5];
                }
                i5++;
            }
        }

        void add(int i, int i2, int i3) {
            int i4 = -1;
            int i5 = MutableCodePointTrie.I_LIMIT;
            for (int i6 = 0; i6 < this.length; i6++) {
                int[] iArr = this.refCounts;
                if (iArr[i6] < i5) {
                    i5 = iArr[i6];
                    i4 = i6;
                }
            }
            this.mostRecent = i4;
            this.indexes[i4] = i;
            this.values[i4] = i3;
            this.refCounts[i4] = i2;
        }

        int findMostUsed() {
            if (this.length == 0) {
                return -1;
            }
            int i = 0;
            int i2 = -1;
            for (int i3 = 0; i3 < this.length; i3++) {
                int[] iArr = this.refCounts;
                if (iArr[i3] > i) {
                    i = iArr[i3];
                    i2 = i3;
                }
            }
            return this.indexes[i2];
        }
    }

    private static final class MixedBlocks {
        static final /* synthetic */ boolean $assertionsDisabled = false;
        private int blockLength;
        private int length;
        private int mask;
        private int shift;
        private int[] table;

        private MixedBlocks() {
        }

        /* synthetic */ MixedBlocks(AnonymousClass1 anonymousClass1) {
            this();
        }

        void init(int i, int i2) {
            int i3;
            int i4 = (i - i2) + 1;
            if (i4 <= 4095) {
                i3 = R2.styleable.MaterialProgressBar_mpb_progressBackgroundTint;
                this.shift = 12;
                this.mask = 4095;
            } else if (i4 <= 32767) {
                i3 = 50021;
                this.shift = 15;
                this.mask = 32767;
            } else if (i4 <= 131071) {
                i3 = 200003;
                this.shift = 17;
                this.mask = 131071;
            } else {
                i3 = 1500007;
                this.shift = 21;
                this.mask = DictionaryData.TRANSFORM_OFFSET_MASK;
            }
            int[] iArr = this.table;
            if (iArr == null || i3 > iArr.length) {
                this.table = new int[i3];
            } else {
                Arrays.fill(iArr, 0, i3, 0);
            }
            this.length = i3;
            this.blockLength = i2;
        }

        void extend(int[] iArr, int i, int i2, int i3) {
            int i4 = i2 - this.blockLength;
            if (i4 >= i) {
                i = i4 + 1;
            }
            int i5 = i3 - this.blockLength;
            while (i <= i5) {
                addEntry(iArr, null, i, makeHashCode(iArr, i), i);
                i++;
            }
        }

        void extend(char[] cArr, int i, int i2, int i3) {
            int i4 = i2 - this.blockLength;
            if (i4 >= i) {
                i = i4 + 1;
            }
            int i5 = i3 - this.blockLength;
            while (i <= i5) {
                addEntry(null, cArr, i, makeHashCode(cArr, i), i);
                i++;
            }
        }

        int findBlock(int[] iArr, int[] iArr2, int i) {
            if (findEntry(iArr, null, iArr2, null, i, makeHashCode(iArr2, i)) >= 0) {
                return (this.table[r8] & this.mask) - 1;
            }
            return -1;
        }

        int findBlock(char[] cArr, int[] iArr, int i) {
            if (findEntry(null, cArr, iArr, null, i, makeHashCode(iArr, i)) >= 0) {
                return (this.table[r8] & this.mask) - 1;
            }
            return -1;
        }

        int findBlock(char[] cArr, char[] cArr2, int i) {
            if (findEntry(null, cArr, null, cArr2, i, makeHashCode(cArr2, i)) >= 0) {
                return (this.table[r8] & this.mask) - 1;
            }
            return -1;
        }

        int findAllSameBlock(int[] iArr, int i) {
            if (findEntry(iArr, i, makeHashCode(i)) >= 0) {
                return (this.table[r2] & this.mask) - 1;
            }
            return -1;
        }

        private int makeHashCode(int[] iArr, int i) {
            int i2 = this.blockLength + i;
            int i3 = i + 1;
            int i4 = iArr[i];
            while (true) {
                int i5 = i3 + 1;
                i4 = (i4 * 37) + iArr[i3];
                if (i5 >= i2) {
                    return i4;
                }
                i3 = i5;
            }
        }

        private int makeHashCode(char[] cArr, int i) {
            int i2 = this.blockLength + i;
            int i3 = i + 1;
            int i4 = cArr[i];
            while (true) {
                int i5 = i3 + 1;
                int i6 = (i4 * 37) + cArr[i3];
                if (i5 >= i2) {
                    return i6;
                }
                i3 = i5;
                i4 = i6;
            }
        }

        private int makeHashCode(int i) {
            int i2 = i;
            for (int i3 = 1; i3 < this.blockLength; i3++) {
                i2 = (i2 * 37) + i;
            }
            return i2;
        }

        private void addEntry(int[] iArr, char[] cArr, int i, int i2, int i3) {
            int iFindEntry = findEntry(iArr, cArr, iArr, cArr, i, i2);
            if (iFindEntry < 0) {
                this.table[~iFindEntry] = (i2 << this.shift) | (i3 + 1);
            }
        }

        private int findEntry(int[] iArr, char[] cArr, int[] iArr2, char[] cArr2, int i, int i2) {
            int i3 = i2 << this.shift;
            int iModulo = modulo(i2, this.length - 1) + 1;
            int iNextIndex = iModulo;
            while (true) {
                int i4 = this.table[iNextIndex];
                if (i4 == 0) {
                    return ~iNextIndex;
                }
                int i5 = this.mask;
                if (((~i5) & i4) == i3) {
                    int i6 = (i4 & i5) - 1;
                    if (iArr != null) {
                        if (MutableCodePointTrie.equalBlocks(iArr, i6, iArr2, i, this.blockLength)) {
                            break;
                        }
                    } else if (iArr2 != null) {
                        if (MutableCodePointTrie.equalBlocks(cArr, i6, iArr2, i, this.blockLength)) {
                            break;
                        }
                    } else if (MutableCodePointTrie.equalBlocks(cArr, i6, cArr2, i, this.blockLength)) {
                        break;
                    }
                }
                iNextIndex = nextIndex(iModulo, iNextIndex);
            }
            return iNextIndex;
        }

        private int findEntry(int[] iArr, int i, int i2) {
            int i3 = i2 << this.shift;
            int iModulo = modulo(i2, this.length - 1) + 1;
            int iNextIndex = iModulo;
            while (true) {
                int i4 = this.table[iNextIndex];
                if (i4 == 0) {
                    return ~iNextIndex;
                }
                if (((~this.mask) & i4) == i3 && MutableCodePointTrie.allValuesSameAs(iArr, (i4 & r3) - 1, this.blockLength, i)) {
                    return iNextIndex;
                }
                iNextIndex = nextIndex(iModulo, iNextIndex);
            }
        }

        private int nextIndex(int i, int i2) {
            return (i2 + i) % this.length;
        }

        private int modulo(int i, int i2) {
            int i3 = i % i2;
            return i3 < 0 ? i3 + i2 : i3;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:26:0x0055 A[PHI: r8
      0x0055: PHI (r8v2 int) = (r8v1 int), (r8v1 int), (r8v8 int) binds: [B:13:0x0037, B:21:0x004b, B:11:0x002b] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:28:0x005c  */
    /* JADX WARN: Removed duplicated region for block: B:40:0x007d  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    private int compactWholeDataBlocks(int r14, com.ibm.icu.util.MutableCodePointTrie.AllSameBlocks r15) {
        /*
            r13 = this;
            int r0 = r13.highStart
            r1 = 4
            int r0 = r0 >> r1
            r2 = 0
            r3 = 148(0x94, float:2.07E-43)
            r4 = 64
            r5 = 0
            r6 = 4
        Lb:
            if (r5 >= r0) goto L88
            r7 = 1
            if (r5 != r14) goto L13
            r4 = 16
            r6 = 1
        L13:
            int[] r8 = r13.index
            r8 = r8[r5]
            byte[] r9 = r13.flags
            r9 = r9[r5]
            if (r9 != r7) goto L37
            int[] r9 = r13.data
            r10 = r9[r8]
            int r8 = r8 + 1
            int r11 = r4 + (-1)
            boolean r8 = allValuesSameAs(r9, r8, r11, r10)
            if (r8 == 0) goto L35
            byte[] r8 = r13.flags
            r8[r5] = r2
            int[] r8 = r13.index
            r8[r5] = r10
            r8 = r10
            goto L55
        L35:
            int r3 = r3 + r4
            goto L86
        L37:
            if (r6 <= r7) goto L55
            int r9 = r5 + r6
            int r10 = r5 + 1
        L3d:
            if (r10 >= r9) goto L4a
            int[] r11 = r13.index
            r11 = r11[r10]
            if (r11 == r8) goto L47
            r9 = 0
            goto L4b
        L47:
            int r10 = r10 + 1
            goto L3d
        L4a:
            r9 = 1
        L4b:
            if (r9 != 0) goto L55
            int r7 = r13.getDataBlock(r5)
            if (r7 >= 0) goto L35
            r14 = -1
            return r14
        L55:
            int r9 = r15.findOrAdd(r5, r6, r8)
            r10 = -2
            if (r9 != r10) goto L7b
            r10 = 0
            r11 = 4
        L5e:
            if (r10 != r5) goto L64
            r15.add(r5, r6, r8)
            goto L7b
        L64:
            if (r10 != r14) goto L67
            r11 = 1
        L67:
            byte[] r12 = r13.flags
            r12 = r12[r10]
            if (r12 != 0) goto L79
            int[] r12 = r13.index
            r12 = r12[r10]
            if (r12 != r8) goto L79
            int r11 = r11 + r6
            r15.add(r10, r11, r8)
            r9 = r10
            goto L7b
        L79:
            int r10 = r10 + r11
            goto L5e
        L7b:
            if (r9 < 0) goto L35
            byte[] r7 = r13.flags
            r8 = 2
            r7[r5] = r8
            int[] r7 = r13.index
            r7[r5] = r9
        L86:
            int r5 = r5 + r6
            goto Lb
        L88:
            return r3
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ibm.icu.util.MutableCodePointTrie.compactWholeDataBlocks(int, com.ibm.icu.util.MutableCodePointTrie$AllSameBlocks):int");
    }

    private int compactData(int i, int[] iArr, int i2, MixedBlocks mixedBlocks) {
        int i3;
        int i4;
        int i5 = 0;
        int i6 = 0;
        int i7 = 0;
        while (true) {
            i3 = 4;
            if (i6 >= 128) {
                break;
            }
            this.index[i7] = i6;
            i6 += 64;
            i7 += 4;
        }
        int i8 = 64;
        mixedBlocks.init(iArr.length, 64);
        mixedBlocks.extend(iArr, 0, 0, i6);
        int i9 = this.highStart >> 4;
        int i10 = 0;
        for (int i11 = 8; i11 < i9; i11 += i3) {
            if (i11 == i) {
                i8 = 16;
                mixedBlocks.init(iArr.length, 16);
                mixedBlocks.extend(iArr, i5, i5, i6);
                i10 = i6;
                i3 = 1;
            }
            byte[] bArr = this.flags;
            if (bArr[i11] == 0) {
                int i12 = this.index[i11];
                int iFindAllSameBlock = mixedBlocks.findAllSameBlock(iArr, i12);
                while (iFindAllSameBlock >= 0 && i11 == i2 && i11 >= i && iFindAllSameBlock < i10 && isStartOfSomeFastBlock(iFindAllSameBlock, this.index, i)) {
                    iFindAllSameBlock = findAllSameBlock(iArr, iFindAllSameBlock + 1, i6, i12, i8);
                }
                if (iFindAllSameBlock >= 0) {
                    this.index[i11] = iFindAllSameBlock;
                } else {
                    int allSameOverlap = getAllSameOverlap(iArr, i6, i12, i8);
                    this.index[i11] = i6 - allSameOverlap;
                    i4 = i6;
                    while (allSameOverlap < i8) {
                        iArr[i4] = i12;
                        allSameOverlap++;
                        i4++;
                    }
                    mixedBlocks.extend(iArr, i5, i6, i4);
                    i6 = i4;
                }
            } else if (bArr[i11] == 1) {
                int i13 = this.index[i11];
                int iFindBlock = mixedBlocks.findBlock(iArr, this.data, i13);
                if (iFindBlock >= 0) {
                    this.index[i11] = iFindBlock;
                } else {
                    int overlap = getOverlap(iArr, i6, this.data, i13, i8);
                    this.index[i11] = i6 - overlap;
                    i4 = i6;
                    while (overlap < i8) {
                        iArr[i4] = this.data[overlap + i13];
                        i4++;
                        overlap++;
                        i5 = 0;
                    }
                    mixedBlocks.extend(iArr, i5, i6, i4);
                    i6 = i4;
                }
            } else {
                int[] iArr2 = this.index;
                iArr2[i11] = iArr2[iArr2[i11]];
            }
        }
        return i6;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:108:0x0233 A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:147:0x0237 A[ADDED_TO_REGION, REMOVE, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    private int compactIndex(int r29, com.ibm.icu.util.MutableCodePointTrie.MixedBlocks r30) {
        /*
            Method dump skipped, instructions count: 699
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ibm.icu.util.MutableCodePointTrie.compactIndex(int, com.ibm.icu.util.MutableCodePointTrie$MixedBlocks):int");
    }

    private int compactTrie(int i) {
        this.highValue = get(1114111);
        int iFindHighStart = (findHighStart() + 511) & (-512);
        if (iFindHighStart == 1114112) {
            this.highValue = this.initialValue;
        }
        int i2 = i << 4;
        if (iFindHighStart < i2) {
            for (int i3 = iFindHighStart >> 4; i3 < i; i3++) {
                this.flags[i3] = 0;
                this.index[i3] = this.highValue;
            }
            this.highStart = i2;
        } else {
            this.highStart = iFindHighStart;
        }
        int[] iArr = new int[128];
        for (int i4 = 0; i4 < 128; i4++) {
            iArr[i4] = get(i4);
        }
        AllSameBlocks allSameBlocks = new AllSameBlocks();
        int[] iArrCopyOf = Arrays.copyOf(iArr, compactWholeDataBlocks(i, allSameBlocks));
        int iFindMostUsed = allSameBlocks.findMostUsed();
        MixedBlocks mixedBlocks = new MixedBlocks(null);
        int iCompactData = compactData(i, iArrCopyOf, iFindMostUsed, mixedBlocks);
        this.data = iArrCopyOf;
        this.dataLength = iCompactData;
        if (iCompactData > 262159) {
            throw new IndexOutOfBoundsException("The trie data exceeds limitations of the data structure.");
        }
        if (iFindMostUsed >= 0) {
            int i5 = this.index[iFindMostUsed];
            this.dataNullOffset = i5;
            this.initialValue = iArrCopyOf[i5];
        } else {
            this.dataNullOffset = 1048575;
        }
        int iCompactIndex = compactIndex(i, mixedBlocks);
        this.highStart = iFindHighStart;
        return iCompactIndex;
    }

    /* renamed from: com.ibm.icu.util.MutableCodePointTrie$1, reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$ibm$icu$util$CodePointTrie$ValueWidth;

        static {
            int[] iArr = new int[CodePointTrie.ValueWidth.values().length];
            $SwitchMap$com$ibm$icu$util$CodePointTrie$ValueWidth = iArr;
            try {
                iArr[CodePointTrie.ValueWidth.BITS_32.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$ibm$icu$util$CodePointTrie$ValueWidth[CodePointTrie.ValueWidth.BITS_16.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$ibm$icu$util$CodePointTrie$ValueWidth[CodePointTrie.ValueWidth.BITS_8.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:46:0x00d2  */
    /* JADX WARN: Removed duplicated region for block: B:95:? A[SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    private com.ibm.icu.util.CodePointTrie build(com.ibm.icu.util.CodePointTrie.Type r14, com.ibm.icu.util.CodePointTrie.ValueWidth r15) {
        /*
            Method dump skipped, instructions count: 462
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ibm.icu.util.MutableCodePointTrie.build(com.ibm.icu.util.CodePointTrie$Type, com.ibm.icu.util.CodePointTrie$ValueWidth):com.ibm.icu.util.CodePointTrie");
    }
}
