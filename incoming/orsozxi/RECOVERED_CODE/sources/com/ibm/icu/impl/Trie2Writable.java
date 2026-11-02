package com.ibm.icu.impl;

import com.ibm.icu.impl.Trie2;
import it.orsozoxi.android.orsonotes.R2;
import java.util.Iterator;

/* loaded from: classes2.dex */
public class Trie2Writable extends Trie2 {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final int UNEWTRIE2_DATA_0800_OFFSET = 2176;
    private static final int UNEWTRIE2_DATA_NULL_OFFSET = 192;
    private static final int UNEWTRIE2_DATA_START_OFFSET = 256;
    private static final int UNEWTRIE2_INDEX_2_NULL_OFFSET = 2656;
    private static final int UNEWTRIE2_INDEX_2_START_OFFSET = 2720;
    private static final int UNEWTRIE2_INITIAL_DATA_LENGTH = 16384;
    private static final int UNEWTRIE2_MEDIUM_DATA_LENGTH = 131072;
    private static final int UTRIE2_MAX_DATA_LENGTH = 262140;
    private static final int UTRIE2_MAX_INDEX_LENGTH = 65535;
    private boolean UTRIE2_DEBUG;
    private int[] data;
    private int dataCapacity;
    private int firstFreeBlock;
    private int[] index1;
    private int[] index2;
    private int index2Length;
    private int index2NullOffset;
    private boolean isCompacted;
    private int[] map;

    public Trie2Writable(int i, int i2) {
        this.index1 = new int[R2.attr.indicatorInset];
        this.index2 = new int[35488];
        this.map = new int[34852];
        this.UTRIE2_DEBUG = false;
        init(i, i2);
    }

    private void init(int i, int i2) {
        int i3;
        this.initialValue = i;
        this.errorValue = i2;
        this.highStart = 1114112;
        this.data = new int[16384];
        this.dataCapacity = 16384;
        this.initialValue = i;
        this.errorValue = i2;
        this.highStart = 1114112;
        int i4 = 0;
        this.firstFreeBlock = 0;
        this.isCompacted = false;
        int i5 = 0;
        while (true) {
            if (i5 >= 128) {
                break;
            }
            this.data[i5] = this.initialValue;
            i5++;
        }
        while (i5 < 192) {
            this.data[i5] = this.errorValue;
            i5++;
        }
        for (int i6 = 192; i6 < 256; i6++) {
            this.data[i6] = this.initialValue;
        }
        this.dataNullOffset = 192;
        this.dataLength = 256;
        int i7 = 0;
        int i8 = 0;
        while (i7 < 128) {
            this.index2[i8] = i7;
            this.map[i8] = 1;
            i8++;
            i7 += 32;
        }
        while (i7 < 192) {
            this.map[i8] = 0;
            i8++;
            i7 += 32;
        }
        int i9 = i8 + 1;
        this.map[i8] = 34845;
        for (int i10 = i7 + 32; i10 < 256; i10 += 32) {
            this.map[i9] = 0;
            i9++;
        }
        for (int i11 = 4; i11 < 2080; i11++) {
            this.index2[i11] = 192;
        }
        for (int i12 = 0; i12 < 576; i12++) {
            this.index2[i12 + R2.dimen.toolbar_elevation] = -1;
        }
        for (int i13 = 0; i13 < 64; i13++) {
            this.index2[i13 + 2656] = 192;
        }
        this.index2NullOffset = 2656;
        this.index2Length = 2720;
        int i14 = 0;
        while (i4 < 32) {
            this.index1[i4] = i14;
            i4++;
            i14 += 64;
        }
        while (i4 < 544) {
            this.index1[i4] = 2656;
            i4++;
        }
        for (i3 = 128; i3 < 2048; i3 += 32) {
            set(i3, this.initialValue);
        }
    }

    public Trie2Writable(Trie2 trie2) {
        this.index1 = new int[R2.attr.indicatorInset];
        this.index2 = new int[35488];
        this.map = new int[34852];
        this.UTRIE2_DEBUG = false;
        init(trie2.initialValue, trie2.errorValue);
        Iterator<Trie2.Range> it2 = trie2.iterator();
        while (it2.hasNext()) {
            setRange(it2.next(), true);
        }
    }

    private boolean isInNullBlock(int i, boolean z) {
        return this.index2[(!Character.isHighSurrogate((char) i) || !z) ? ((i >> 5) & 63) + this.index1[i >> 11] : (i >> 5) + R2.attr.contentPaddingEnd] == this.dataNullOffset;
    }

    private int allocIndex2Block() {
        int i = this.index2Length;
        int i2 = i + 64;
        int[] iArr = this.index2;
        if (i2 > iArr.length) {
            throw new IllegalStateException("Internal error in Trie2 creation.");
        }
        this.index2Length = i2;
        System.arraycopy(iArr, this.index2NullOffset, iArr, i, 64);
        return i;
    }

    private int getIndex2Block(int i, boolean z) {
        if (i >= 55296 && i < 56320 && z) {
            return 2048;
        }
        int i2 = i >> 11;
        int i3 = this.index1[i2];
        if (i3 != this.index2NullOffset) {
            return i3;
        }
        int iAllocIndex2Block = allocIndex2Block();
        this.index1[i2] = iAllocIndex2Block;
        return iAllocIndex2Block;
    }

    private int allocDataBlock(int i) {
        int i2 = this.firstFreeBlock;
        if (i2 != 0) {
            this.firstFreeBlock = -this.map[i2 >> 5];
        } else {
            i2 = this.dataLength;
            int i3 = i2 + 32;
            int i4 = this.dataCapacity;
            if (i3 > i4) {
                int i5 = 1115264;
                if (i4 < 131072) {
                    i5 = 131072;
                } else if (i4 >= 1115264) {
                    throw new IllegalStateException("Internal error in Trie2 creation.");
                }
                int[] iArr = new int[i5];
                System.arraycopy(this.data, 0, iArr, 0, this.dataLength);
                this.data = iArr;
                this.dataCapacity = i5;
            }
            this.dataLength = i3;
        }
        int[] iArr2 = this.data;
        System.arraycopy(iArr2, i, iArr2, i2, 32);
        this.map[i2 >> 5] = 0;
        return i2;
    }

    private void releaseDataBlock(int i) {
        this.map[i >> 5] = -this.firstFreeBlock;
        this.firstFreeBlock = i;
    }

    private boolean isWritableBlock(int i) {
        return i != this.dataNullOffset && 1 == this.map[i >> 5];
    }

    private void setIndex2Entry(int i, int i2) {
        int[] iArr = this.map;
        int i3 = i2 >> 5;
        iArr[i3] = iArr[i3] + 1;
        int i4 = this.index2[i];
        int i5 = i4 >> 5;
        int i6 = iArr[i5] - 1;
        iArr[i5] = i6;
        if (i6 == 0) {
            releaseDataBlock(i4);
        }
        this.index2[i] = i2;
    }

    private int getDataBlock(int i, boolean z) {
        int index2Block = getIndex2Block(i, z) + ((i >> 5) & 63);
        int i2 = this.index2[index2Block];
        if (isWritableBlock(i2)) {
            return i2;
        }
        int iAllocDataBlock = allocDataBlock(i2);
        setIndex2Entry(index2Block, iAllocDataBlock);
        return iAllocDataBlock;
    }

    public Trie2Writable set(int i, int i2) {
        if (i < 0 || i > 1114111) {
            throw new IllegalArgumentException("Invalid code point.");
        }
        set(i, true, i2);
        this.fHash = 0;
        return this;
    }

    private Trie2Writable set(int i, boolean z, int i2) {
        if (this.isCompacted) {
            uncompact();
        }
        this.data[getDataBlock(i, z) + (i & 31)] = i2;
        return this;
    }

    private void uncompact() {
        Trie2Writable trie2Writable = new Trie2Writable(this);
        this.index1 = trie2Writable.index1;
        this.index2 = trie2Writable.index2;
        this.data = trie2Writable.data;
        this.index2Length = trie2Writable.index2Length;
        this.dataCapacity = trie2Writable.dataCapacity;
        this.isCompacted = trie2Writable.isCompacted;
        this.header = trie2Writable.header;
        this.index = trie2Writable.index;
        this.data16 = trie2Writable.data16;
        this.data32 = trie2Writable.data32;
        this.indexLength = trie2Writable.indexLength;
        this.dataLength = trie2Writable.dataLength;
        this.index2NullOffset = trie2Writable.index2NullOffset;
        this.initialValue = trie2Writable.initialValue;
        this.errorValue = trie2Writable.errorValue;
        this.highStart = trie2Writable.highStart;
        this.highValueIndex = trie2Writable.highValueIndex;
        this.dataNullOffset = trie2Writable.dataNullOffset;
    }

    private void writeBlock(int i, int i2) {
        int i3 = i + 32;
        while (i < i3) {
            this.data[i] = i2;
            i++;
        }
    }

    private void fillBlock(int i, int i2, int i3, int i4, int i5, boolean z) {
        int i6 = i3 + i;
        if (z) {
            for (int i7 = i + i2; i7 < i6; i7++) {
                this.data[i7] = i4;
            }
            return;
        }
        for (int i8 = i + i2; i8 < i6; i8++) {
            int[] iArr = this.data;
            if (iArr[i8] == i5) {
                iArr[i8] = i4;
            }
        }
    }

    public Trie2Writable setRange(int i, int i2, int i3, boolean z) {
        int i4;
        int i5;
        int i6;
        boolean z2;
        if (i > 1114111 || i < 0 || i2 > 1114111 || i2 < 0 || i > i2) {
            throw new IllegalArgumentException("Invalid code point range.");
        }
        if (!z && i3 == this.initialValue) {
            return this;
        }
        this.fHash = 0;
        if (this.isCompacted) {
            uncompact();
        }
        int i7 = i2 + 1;
        int i8 = i & 31;
        if (i8 != 0) {
            int dataBlock = getDataBlock(i, true);
            i4 = (i + 32) & (-32);
            if (i4 <= i7) {
                fillBlock(dataBlock, i8, 32, i3, this.initialValue, z);
            } else {
                fillBlock(dataBlock, i8, i7 & 31, i3, this.initialValue, z);
                return this;
            }
        } else {
            i4 = i;
        }
        int i9 = i7 & 31;
        int i10 = i7 & (-32);
        if (i3 == this.initialValue) {
            i5 = i4;
            i6 = this.dataNullOffset;
        } else {
            i5 = i4;
            i6 = -1;
        }
        while (i5 < i10) {
            if (i3 != this.initialValue || !isInNullBlock(i5, true)) {
                int index2Block = getIndex2Block(i5, true) + ((i5 >> 5) & 63);
                int i11 = this.index2[index2Block];
                if (isWritableBlock(i11)) {
                    if (!z || i11 < 2176) {
                        fillBlock(i11, 0, 32, i3, this.initialValue, z);
                    }
                } else {
                    z2 = this.data[i11] != i3 && (z || i11 == this.dataNullOffset);
                }
                if (z2) {
                    if (i6 >= 0) {
                        setIndex2Entry(index2Block, i6);
                    } else {
                        int dataBlock2 = getDataBlock(i5, true);
                        writeBlock(dataBlock2, i3);
                        i6 = dataBlock2;
                    }
                }
            }
            i5 += 32;
        }
        if (i9 > 0) {
            fillBlock(getDataBlock(i5, true), 0, i9, i3, this.initialValue, z);
        }
        return this;
    }

    public Trie2Writable setRange(Trie2.Range range, boolean z) {
        this.fHash = 0;
        if (range.leadSurrogate) {
            for (int i = range.startCodePoint; i <= range.endCodePoint; i++) {
                if (z || getFromU16SingleLead((char) i) == this.initialValue) {
                    setForLeadSurrogateCodeUnit((char) i, range.value);
                }
            }
        } else {
            setRange(range.startCodePoint, range.endCodePoint, range.value, z);
        }
        return this;
    }

    public Trie2Writable setForLeadSurrogateCodeUnit(char c, int i) {
        this.fHash = 0;
        set(c, false, i);
        return this;
    }

    @Override // com.ibm.icu.impl.Trie2
    public int get(int i) {
        if (i < 0 || i > 1114111) {
            return this.errorValue;
        }
        return get(i, true);
    }

    private int get(int i, boolean z) {
        if (i >= this.highStart && (i < 55296 || i >= 56320 || z)) {
            return this.data[this.dataLength - 4];
        }
        return this.data[this.index2[(i < 55296 || i >= 56320 || !z) ? this.index1[i >> 11] + ((i >> 5) & 63) : (i >> 5) + R2.attr.contentPaddingEnd] + (i & 31)];
    }

    @Override // com.ibm.icu.impl.Trie2
    public int getFromU16SingleLead(char c) {
        return get(c, false);
    }

    private boolean equal_int(int[] iArr, int i, int i2, int i3) {
        for (int i4 = 0; i4 < i3; i4++) {
            if (iArr[i + i4] != iArr[i2 + i4]) {
                return false;
            }
        }
        return true;
    }

    private int findSameIndex2Block(int i, int i2) {
        int i3 = i - 64;
        for (int i4 = 0; i4 <= i3; i4++) {
            if (equal_int(this.index2, i4, i2, 64)) {
                return i4;
            }
        }
        return -1;
    }

    private int findSameDataBlock(int i, int i2, int i3) {
        int i4 = i - i3;
        for (int i5 = 0; i5 <= i4; i5 += 4) {
            if (equal_int(this.data, i5, i2, i3)) {
                return i5;
            }
        }
        return -1;
    }

    private int findHighStart(int i) {
        int i2;
        int i3;
        if (i == this.initialValue) {
            i2 = this.index2NullOffset;
            i3 = this.dataNullOffset;
        } else {
            i2 = -1;
            i3 = -1;
        }
        int i4 = 1114112;
        int i5 = R2.attr.indicatorInset;
        while (i4 > 0) {
            i5--;
            int i6 = this.index1[i5];
            if (i6 == i2) {
                i4 -= 2048;
            } else {
                if (i6 != this.index2NullOffset) {
                    int i7 = 64;
                    while (i7 > 0) {
                        i7--;
                        int i8 = this.index2[i6 + i7];
                        if (i8 == i3) {
                            i4 -= 32;
                        } else {
                            if (i8 != this.dataNullOffset) {
                                int i9 = 32;
                                while (i9 > 0) {
                                    i9--;
                                    if (this.data[i8 + i9] != i) {
                                        return i4;
                                    }
                                    i4--;
                                }
                            } else {
                                if (i != this.initialValue) {
                                    return i4;
                                }
                                i4 -= 32;
                            }
                            i3 = i8;
                        }
                    }
                } else {
                    if (i != this.initialValue) {
                        return i4;
                    }
                    i4 -= 2048;
                }
                i2 = i6;
            }
        }
        return 0;
    }

    private void compactData() {
        int i;
        int i2 = 0;
        int i3 = 0;
        while (true) {
            i = 192;
            if (i2 >= 192) {
                break;
            }
            this.map[i3] = i2;
            i2 += 32;
            i3++;
        }
        int i4 = 64;
        int i5 = 192;
        int i6 = 2;
        while (i < this.dataLength) {
            if (i == 2176) {
                i4 = 32;
                i6 = 1;
            }
            int i7 = i >> 5;
            if (this.map[i7] > 0) {
                int iFindSameDataBlock = findSameDataBlock(i5, i, i4);
                if (iFindSameDataBlock >= 0) {
                    int i8 = i6;
                    while (i8 > 0) {
                        this.map[i7] = iFindSameDataBlock;
                        iFindSameDataBlock += 32;
                        i8--;
                        i7++;
                    }
                } else {
                    int i9 = i4 - 4;
                    while (i9 > 0 && !equal_int(this.data, i5 - i9, i, i9)) {
                        i9 -= 4;
                    }
                    if (i9 > 0 || i5 < i) {
                        int i10 = i5 - i9;
                        int i11 = i6;
                        while (i11 > 0) {
                            this.map[i7] = i10;
                            i10 += 32;
                            i11--;
                            i7++;
                        }
                        i += i9;
                        int i12 = i4 - i9;
                        while (i12 > 0) {
                            int[] iArr = this.data;
                            iArr[i5] = iArr[i];
                            i12--;
                            i5++;
                            i++;
                        }
                    } else {
                        i5 = i;
                        int i13 = i6;
                        while (i13 > 0) {
                            this.map[i7] = i5;
                            i5 += 32;
                            i13--;
                            i7++;
                        }
                        i = i5;
                    }
                }
            }
            i += i4;
        }
        int i14 = 0;
        while (i14 < this.index2Length) {
            if (i14 == 2080) {
                i14 += R2.attr.key;
            }
            int[] iArr2 = this.index2;
            iArr2[i14] = this.map[iArr2[i14] >> 5];
            i14++;
        }
        this.dataNullOffset = this.map[this.dataNullOffset >> 5];
        while ((i5 & 3) != 0) {
            this.data[i5] = this.initialValue;
            i5++;
        }
        if (this.UTRIE2_DEBUG) {
            System.out.printf("compacting UTrie2: count of 32-bit data words %d->%d%n", Integer.valueOf(this.dataLength), Integer.valueOf(i5));
        }
        this.dataLength = i5;
    }

    private void compactIndex2() {
        int i = 0;
        int i2 = 0;
        while (i < 2080) {
            this.map[i2] = i;
            i += 64;
            i2++;
        }
        int i3 = R2.dimen.toolbar_elevation + ((this.highStart - 65536) >> 11) + 32;
        int i4 = 2656;
        while (i4 < this.index2Length) {
            int iFindSameIndex2Block = findSameIndex2Block(i3, i4);
            if (iFindSameIndex2Block >= 0) {
                this.map[i4 >> 6] = iFindSameIndex2Block;
                i4 += 64;
            } else {
                int i5 = 63;
                while (i5 > 0 && !equal_int(this.index2, i3 - i5, i4, i5)) {
                    i5--;
                }
                if (i5 > 0 || i3 < i4) {
                    this.map[i4 >> 6] = i3 - i5;
                    i4 += i5;
                    int i6 = 64 - i5;
                    while (i6 > 0) {
                        int[] iArr = this.index2;
                        iArr[i3] = iArr[i4];
                        i6--;
                        i3++;
                        i4++;
                    }
                } else {
                    this.map[i4 >> 6] = i4;
                    i3 = i4 + 64;
                    i4 = i3;
                }
            }
        }
        for (int i7 = 0; i7 < 544; i7++) {
            int[] iArr2 = this.index1;
            iArr2[i7] = this.map[iArr2[i7] >> 6];
        }
        this.index2NullOffset = this.map[this.index2NullOffset >> 6];
        while ((i3 & 3) != 0) {
            this.index2[i3] = UTRIE2_MAX_DATA_LENGTH;
            i3++;
        }
        if (this.UTRIE2_DEBUG) {
            System.out.printf("compacting UTrie2: count of 16-bit index-2 words %d->%d%n", Integer.valueOf(this.index2Length), Integer.valueOf(i3));
        }
        this.index2Length = i3;
    }

    private void compactTrie() {
        int i = get(1114111);
        int iFindHighStart = (findHighStart(i) + 2047) & (-2048);
        if (iFindHighStart == 1114112) {
            i = this.errorValue;
        }
        this.highStart = iFindHighStart;
        if (this.UTRIE2_DEBUG) {
            System.out.printf("UTrie2: highStart U+%04x  highValue 0x%x  initialValue 0x%x%n", Integer.valueOf(this.highStart), Integer.valueOf(i), Integer.valueOf(this.initialValue));
        }
        if (this.highStart < 1114112) {
            setRange(this.highStart <= 65536 ? 65536 : this.highStart, 1114111, this.initialValue, true);
        }
        compactData();
        if (this.highStart > 65536) {
            compactIndex2();
        } else if (this.UTRIE2_DEBUG) {
            System.out.printf("UTrie2: highStart U+%04x  count of 16-bit index-2 words %d->%d%n", Integer.valueOf(this.highStart), Integer.valueOf(this.index2Length), Integer.valueOf(R2.drawable.abc_cab_background_top_material));
        }
        int[] iArr = this.data;
        int i2 = this.dataLength;
        this.dataLength = i2 + 1;
        iArr[i2] = i;
        while ((this.dataLength & 3) != 0) {
            int[] iArr2 = this.data;
            int i3 = this.dataLength;
            this.dataLength = i3 + 1;
            iArr2[i3] = this.initialValue;
        }
        this.isCompacted = true;
    }

    public Trie2_16 toTrie2_16() {
        Trie2_16 trie2_16 = new Trie2_16();
        freeze(trie2_16, Trie2.ValueWidth.BITS_16);
        return trie2_16;
    }

    public Trie2_32 toTrie2_32() {
        Trie2_32 trie2_32 = new Trie2_32();
        freeze(trie2_32, Trie2.ValueWidth.BITS_32);
        return trie2_32;
    }

    private void freeze(Trie2 trie2, Trie2.ValueWidth valueWidth) {
        int i;
        if (!this.isCompacted) {
            compactTrie();
        }
        int i2 = this.highStart <= 65536 ? R2.drawable.abc_cab_background_top_material : this.index2Length;
        int i3 = 0;
        int i4 = valueWidth == Trie2.ValueWidth.BITS_16 ? i2 : 0;
        if (i2 > 65535 || this.dataNullOffset + i4 > 65535 || i4 + 2176 > 65535 || this.dataLength + i4 > UTRIE2_MAX_DATA_LENGTH) {
            throw new UnsupportedOperationException("Trie2 data is too large.");
        }
        if (valueWidth == Trie2.ValueWidth.BITS_16) {
            i = this.dataLength + i2;
        } else {
            trie2.data32 = new int[this.dataLength];
            i = i2;
        }
        trie2.index = new char[i];
        trie2.indexLength = i2;
        trie2.dataLength = this.dataLength;
        if (this.highStart <= 65536) {
            trie2.index2NullOffset = 65535;
        } else {
            trie2.index2NullOffset = this.index2NullOffset + 0;
        }
        trie2.initialValue = this.initialValue;
        trie2.errorValue = this.errorValue;
        trie2.highStart = this.highStart;
        trie2.highValueIndex = (this.dataLength + i4) - 4;
        trie2.dataNullOffset = this.dataNullOffset + i4;
        trie2.header = new Trie2.UTrie2Header();
        trie2.header.signature = 1416784178;
        trie2.header.options = valueWidth == Trie2.ValueWidth.BITS_16 ? 0 : 1;
        trie2.header.indexLength = trie2.indexLength;
        trie2.header.shiftedDataLength = trie2.dataLength >> 2;
        trie2.header.index2NullOffset = trie2.index2NullOffset;
        trie2.header.dataNullOffset = trie2.dataNullOffset;
        trie2.header.shiftedHighStart = trie2.highStart >> 11;
        int i5 = 0;
        int i6 = 0;
        while (i5 < 2080) {
            trie2.index[i6] = (char) ((this.index2[i5] + i4) >> 2);
            i5++;
            i6++;
        }
        if (this.UTRIE2_DEBUG) {
            System.out.println("\n\nIndex2 for BMP limit is " + Integer.toHexString(i6));
        }
        int i7 = 0;
        while (i7 < 2) {
            trie2.index[i6] = (char) (i4 + 128);
            i7++;
            i6++;
        }
        while (i7 < 32) {
            trie2.index[i6] = (char) (this.index2[i7 << 1] + i4);
            i7++;
            i6++;
        }
        if (this.UTRIE2_DEBUG) {
            System.out.println("Index2 for UTF-8 2byte values limit is " + Integer.toHexString(i6));
        }
        if (this.highStart > 65536) {
            int i8 = (this.highStart - 65536) >> 11;
            int i9 = i8 + R2.drawable.abc_cab_background_top_material;
            int i10 = 0;
            while (i10 < i8) {
                trie2.index[i6] = (char) (this.index1[i10 + 32] + 0);
                i10++;
                i6++;
            }
            if (this.UTRIE2_DEBUG) {
                System.out.println("Index 1 for supplementals, limit is " + Integer.toHexString(i6));
            }
            int i11 = 0;
            while (i11 < this.index2Length - i9) {
                trie2.index[i6] = (char) ((this.index2[i9 + i11] + i4) >> 2);
                i11++;
                i6++;
            }
            if (this.UTRIE2_DEBUG) {
                System.out.println("Index 2 for supplementals, limit is " + Integer.toHexString(i6));
            }
        }
        int i12 = AnonymousClass1.$SwitchMap$com$ibm$icu$impl$Trie2$ValueWidth[valueWidth.ordinal()];
        if (i12 != 1) {
            if (i12 != 2) {
                return;
            }
            while (i3 < this.dataLength) {
                trie2.data32[i3] = this.data[i3];
                i3++;
            }
            return;
        }
        trie2.data16 = i6;
        while (i3 < this.dataLength) {
            trie2.index[i6] = (char) this.data[i3];
            i3++;
            i6++;
        }
    }

    /* renamed from: com.ibm.icu.impl.Trie2Writable$1, reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$ibm$icu$impl$Trie2$ValueWidth;

        static {
            int[] iArr = new int[Trie2.ValueWidth.values().length];
            $SwitchMap$com$ibm$icu$impl$Trie2$ValueWidth = iArr;
            try {
                iArr[Trie2.ValueWidth.BITS_16.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$ibm$icu$impl$Trie2$ValueWidth[Trie2.ValueWidth.BITS_32.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
        }
    }
}
