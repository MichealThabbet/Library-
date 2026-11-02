package com.ibm.icu.util;

import com.ibm.icu.lang.UCharacterEnums;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.NoSuchElementException;

/* loaded from: classes2.dex */
public final class BytesTrie implements Cloneable, Iterable<Entry> {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    static final int kFiveByteDeltaLead = 255;
    static final int kFiveByteValueLead = 127;
    static final int kFourByteDeltaLead = 254;
    static final int kFourByteValueLead = 126;
    static final int kMaxBranchLinearSubNodeLength = 5;
    static final int kMaxLinearMatchLength = 16;
    static final int kMaxOneByteDelta = 191;
    static final int kMaxOneByteValue = 64;
    static final int kMaxThreeByteDelta = 917503;
    static final int kMaxThreeByteValue = 1179647;
    static final int kMaxTwoByteDelta = 12287;
    static final int kMaxTwoByteValue = 6911;
    static final int kMinLinearMatch = 16;
    static final int kMinOneByteValueLead = 16;
    static final int kMinThreeByteDeltaLead = 240;
    static final int kMinThreeByteValueLead = 108;
    static final int kMinTwoByteDeltaLead = 192;
    static final int kMinTwoByteValueLead = 81;
    static final int kMinValueLead = 32;
    private static final int kValueIsFinal = 1;
    private static Result[] valueResults_ = {Result.INTERMEDIATE_VALUE, Result.FINAL_VALUE};
    private byte[] bytes_;
    private int pos_;
    private int remainingMatchLength_;
    private int root_;

    /* JADX INFO: Access modifiers changed from: private */
    public static int skipValue(int i, int i2) {
        return i2 >= 162 ? i2 < 216 ? i + 1 : i2 < 252 ? i + 2 : i + ((i2 >> 1) & 1) + 3 : i;
    }

    public BytesTrie(byte[] bArr, int i) {
        this.bytes_ = bArr;
        this.root_ = i;
        this.pos_ = i;
        this.remainingMatchLength_ = -1;
    }

    public BytesTrie(BytesTrie bytesTrie) {
        this.bytes_ = bytesTrie.bytes_;
        this.root_ = bytesTrie.root_;
        this.pos_ = bytesTrie.pos_;
        this.remainingMatchLength_ = bytesTrie.remainingMatchLength_;
    }

    /* renamed from: clone, reason: merged with bridge method [inline-methods] */
    public BytesTrie m25clone() throws CloneNotSupportedException {
        return (BytesTrie) super.clone();
    }

    public BytesTrie reset() {
        this.pos_ = this.root_;
        this.remainingMatchLength_ = -1;
        return this;
    }

    public long getState64() {
        return (this.remainingMatchLength_ << 32) | this.pos_;
    }

    public BytesTrie resetToState64(long j) {
        this.remainingMatchLength_ = (int) (j >> 32);
        this.pos_ = (int) j;
        return this;
    }

    public static final class State {
        private byte[] bytes;
        private int pos;
        private int remainingMatchLength;
        private int root;
    }

    public BytesTrie saveState(State state) {
        state.bytes = this.bytes_;
        state.root = this.root_;
        state.pos = this.pos_;
        state.remainingMatchLength = this.remainingMatchLength_;
        return this;
    }

    public BytesTrie resetToState(State state) {
        if (this.bytes_ == state.bytes && this.bytes_ != null && this.root_ == state.root) {
            this.pos_ = state.pos;
            this.remainingMatchLength_ = state.remainingMatchLength;
            return this;
        }
        throw new IllegalArgumentException("incompatible trie state");
    }

    public enum Result {
        NO_MATCH,
        NO_VALUE,
        FINAL_VALUE,
        INTERMEDIATE_VALUE;

        public boolean matches() {
            return this != NO_MATCH;
        }

        public boolean hasValue() {
            return ordinal() >= 2;
        }

        public boolean hasNext() {
            return (ordinal() & 1) != 0;
        }
    }

    public Result current() {
        int i;
        int i2 = this.pos_;
        if (i2 < 0) {
            return Result.NO_MATCH;
        }
        return (this.remainingMatchLength_ >= 0 || (i = this.bytes_[i2] & 255) < 32) ? Result.NO_VALUE : valueResults_[i & 1];
    }

    public Result first(int i) {
        this.remainingMatchLength_ = -1;
        if (i < 0) {
            i += 256;
        }
        return nextImpl(this.root_, i);
    }

    public Result next(int i) {
        int i2;
        int i3 = this.pos_;
        if (i3 < 0) {
            return Result.NO_MATCH;
        }
        if (i < 0) {
            i += 256;
        }
        int i4 = this.remainingMatchLength_;
        if (i4 >= 0) {
            byte[] bArr = this.bytes_;
            int i5 = i3 + 1;
            if (i == (bArr[i3] & 255)) {
                int i6 = i4 - 1;
                this.remainingMatchLength_ = i6;
                this.pos_ = i5;
                return (i6 >= 0 || (i2 = bArr[i5] & 255) < 32) ? Result.NO_VALUE : valueResults_[i2 & 1];
            }
            stop();
            return Result.NO_MATCH;
        }
        return nextImpl(i3, i);
    }

    public Result next(byte[] bArr, int i, int i2) {
        int i3;
        if (i >= i2) {
            return current();
        }
        int iSkipValue = this.pos_;
        if (iSkipValue < 0) {
            return Result.NO_MATCH;
        }
        int i4 = this.remainingMatchLength_;
        while (i != i2) {
            int i5 = i + 1;
            byte b = bArr[i];
            if (i4 < 0) {
                this.remainingMatchLength_ = i4;
                while (true) {
                    byte[] bArr2 = this.bytes_;
                    int i6 = iSkipValue + 1;
                    int i7 = bArr2[iSkipValue] & 255;
                    if (i7 < 16) {
                        Result resultBranchNext = branchNext(i6, i7, b & 255);
                        if (resultBranchNext == Result.NO_MATCH) {
                            return Result.NO_MATCH;
                        }
                        if (i5 == i2) {
                            return resultBranchNext;
                        }
                        if (resultBranchNext == Result.FINAL_VALUE) {
                            stop();
                            return Result.NO_MATCH;
                        }
                        byte b2 = bArr[i5];
                        i5++;
                        b = b2;
                        iSkipValue = this.pos_;
                    } else if (i7 < 32) {
                        int i8 = i7 - 16;
                        if (b != bArr2[i6]) {
                            stop();
                            return Result.NO_MATCH;
                        }
                        i4 = i8 - 1;
                        iSkipValue = i6 + 1;
                    } else {
                        if ((i7 & 1) != 0) {
                            stop();
                            return Result.NO_MATCH;
                        }
                        iSkipValue = skipValue(i6, i7);
                    }
                }
            } else {
                if (b != this.bytes_[iSkipValue]) {
                    stop();
                    return Result.NO_MATCH;
                }
                iSkipValue++;
                i4--;
            }
            i = i5;
        }
        this.remainingMatchLength_ = i4;
        this.pos_ = iSkipValue;
        return (i4 >= 0 || (i3 = this.bytes_[iSkipValue] & 255) < 32) ? Result.NO_VALUE : valueResults_[i3 & 1];
    }

    public int getValue() {
        int i = this.pos_;
        byte[] bArr = this.bytes_;
        return readValue(bArr, i + 1, (bArr[i] & 255) >> 1);
    }

    public long getUniqueValue() {
        int i = this.pos_;
        if (i < 0) {
            return 0L;
        }
        return (findUniqueValue(this.bytes_, (i + this.remainingMatchLength_) + 1, 0L) << 31) >> 31;
    }

    public int getNextBytes(Appendable appendable) throws IOException {
        int i = this.pos_;
        if (i < 0) {
            return 0;
        }
        if (this.remainingMatchLength_ >= 0) {
            append(appendable, this.bytes_[i] & 255);
            return 1;
        }
        int i2 = i + 1;
        int i3 = this.bytes_[i] & 255;
        if (i3 >= 32) {
            if ((i3 & 1) != 0) {
                return 0;
            }
            int iSkipValue = skipValue(i2, i3);
            i2 = iSkipValue + 1;
            i3 = this.bytes_[iSkipValue] & 255;
        }
        if (i3 < 16) {
            if (i3 == 0) {
                i3 = this.bytes_[i2] & 255;
                i2++;
            }
            int i4 = i3 + 1;
            getNextBranchBytes(this.bytes_, i2, i4, appendable);
            return i4;
        }
        append(appendable, this.bytes_[i2] & 255);
        return 1;
    }

    @Override // java.lang.Iterable
    /* renamed from: iterator, reason: merged with bridge method [inline-methods] */
    public java.util.Iterator<Entry> iterator2() {
        return new Iterator(this.bytes_, this.pos_, this.remainingMatchLength_, 0);
    }

    public Iterator iterator(int i) {
        return new Iterator(this.bytes_, this.pos_, this.remainingMatchLength_, i);
    }

    public static Iterator iterator(byte[] bArr, int i, int i2) {
        return new Iterator(bArr, i, -1, i2);
    }

    public static final class Entry {
        private byte[] bytes;
        private int length;
        public int value;

        private Entry(int i) {
            this.bytes = new byte[i];
        }

        public int bytesLength() {
            return this.length;
        }

        public byte byteAt(int i) {
            return this.bytes[i];
        }

        public void copyBytesTo(byte[] bArr, int i) {
            System.arraycopy(this.bytes, 0, bArr, i, this.length);
        }

        public ByteBuffer bytesAsByteBuffer() {
            return ByteBuffer.wrap(this.bytes, 0, this.length).asReadOnlyBuffer();
        }

        private void ensureCapacity(int i) {
            byte[] bArr = this.bytes;
            if (bArr.length < i) {
                byte[] bArr2 = new byte[Math.min(bArr.length * 2, i * 2)];
                System.arraycopy(this.bytes, 0, bArr2, 0, this.length);
                this.bytes = bArr2;
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void append(byte b) {
            ensureCapacity(this.length + 1);
            byte[] bArr = this.bytes;
            int i = this.length;
            this.length = i + 1;
            bArr[i] = b;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void append(byte[] bArr, int i, int i2) {
            ensureCapacity(this.length + i2);
            System.arraycopy(bArr, i, this.bytes, this.length, i2);
            this.length += i2;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void truncateString(int i) {
            this.length = i;
        }
    }

    public static final class Iterator implements java.util.Iterator<Entry> {
        private byte[] bytes_;
        private Entry entry_;
        private int initialPos_;
        private int initialRemainingMatchLength_;
        private int maxLength_;
        private int pos_;
        private int remainingMatchLength_;
        private ArrayList<Long> stack_;

        private Iterator(byte[] bArr, int i, int i2, int i3) {
            this.stack_ = new ArrayList<>();
            this.bytes_ = bArr;
            this.initialPos_ = i;
            this.pos_ = i;
            this.initialRemainingMatchLength_ = i2;
            this.remainingMatchLength_ = i2;
            this.maxLength_ = i3;
            this.entry_ = new Entry(i3 == 0 ? 32 : i3);
            int i4 = this.remainingMatchLength_;
            if (i4 >= 0) {
                int i5 = i4 + 1;
                int i6 = this.maxLength_;
                if (i6 > 0 && i5 > i6) {
                    i5 = i6;
                }
                this.entry_.append(this.bytes_, this.pos_, i5);
                this.pos_ += i5;
                this.remainingMatchLength_ -= i5;
            }
        }

        public Iterator reset() {
            this.pos_ = this.initialPos_;
            int i = this.initialRemainingMatchLength_;
            this.remainingMatchLength_ = i;
            int i2 = i + 1;
            int i3 = this.maxLength_;
            if (i3 > 0 && i2 > i3) {
                i2 = i3;
            }
            this.entry_.truncateString(i2);
            this.pos_ += i2;
            this.remainingMatchLength_ -= i2;
            this.stack_.clear();
            return this;
        }

        @Override // java.util.Iterator
        public boolean hasNext() {
            return this.pos_ >= 0 || !this.stack_.isEmpty();
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // java.util.Iterator
        public Entry next() {
            int iBranchNext = this.pos_;
            if (iBranchNext < 0) {
                if (this.stack_.isEmpty()) {
                    throw new NoSuchElementException();
                }
                ArrayList<Long> arrayList = this.stack_;
                long jLongValue = arrayList.remove(arrayList.size() - 1).longValue();
                int i = (int) jLongValue;
                int i2 = (int) (jLongValue >> 32);
                this.entry_.truncateString(65535 & i);
                int i3 = i >>> 16;
                if (i3 <= 1) {
                    this.entry_.append(this.bytes_[i2]);
                    iBranchNext = i2 + 1;
                } else {
                    iBranchNext = branchNext(i2, i3);
                    if (iBranchNext < 0) {
                        return this.entry_;
                    }
                }
            }
            if (this.remainingMatchLength_ >= 0) {
                return truncateAndStop();
            }
            while (true) {
                int i4 = iBranchNext + 1;
                int i5 = this.bytes_[iBranchNext] & 255;
                if (i5 >= 32) {
                    boolean z = (i5 & 1) != 0;
                    this.entry_.value = BytesTrie.readValue(this.bytes_, i4, i5 >> 1);
                    if (!z && (this.maxLength_ <= 0 || this.entry_.length != this.maxLength_)) {
                        this.pos_ = BytesTrie.skipValue(i4, i5);
                    } else {
                        this.pos_ = -1;
                    }
                    return this.entry_;
                }
                if (this.maxLength_ > 0 && this.entry_.length == this.maxLength_) {
                    return truncateAndStop();
                }
                if (i5 < 16) {
                    if (i5 == 0) {
                        i5 = this.bytes_[i4] & 255;
                        i4++;
                    }
                    iBranchNext = branchNext(i4, i5 + 1);
                    if (iBranchNext < 0) {
                        return this.entry_;
                    }
                } else {
                    int i6 = (i5 - 16) + 1;
                    if (this.maxLength_ > 0) {
                        int i7 = this.entry_.length + i6;
                        int i8 = this.maxLength_;
                        if (i7 > i8) {
                            Entry entry = this.entry_;
                            entry.append(this.bytes_, i4, i8 - entry.length);
                            return truncateAndStop();
                        }
                    }
                    this.entry_.append(this.bytes_, i4, i6);
                    iBranchNext = i4 + i6;
                }
            }
        }

        @Override // java.util.Iterator
        public void remove() {
            throw new UnsupportedOperationException();
        }

        private Entry truncateAndStop() {
            this.pos_ = -1;
            this.entry_.value = -1;
            return this.entry_;
        }

        private int branchNext(int i, int i2) {
            while (i2 > 5) {
                this.stack_.add(Long.valueOf((BytesTrie.skipDelta(this.bytes_, r11) << 32) | ((i2 - r3) << 16) | this.entry_.length));
                i = BytesTrie.jumpByDelta(this.bytes_, i + 1);
                i2 >>= 1;
            }
            byte[] bArr = this.bytes_;
            int i3 = i + 1;
            byte b = bArr[i];
            int i4 = i3 + 1;
            int i5 = bArr[i3] & 255;
            boolean z = (i5 & 1) != 0;
            int value = BytesTrie.readValue(this.bytes_, i4, i5 >> 1);
            int iSkipValue = BytesTrie.skipValue(i4, i5);
            this.stack_.add(Long.valueOf((iSkipValue << 32) | ((i2 - 1) << 16) | this.entry_.length));
            this.entry_.append(b);
            if (!z) {
                return iSkipValue + value;
            }
            this.pos_ = -1;
            this.entry_.value = value;
            return -1;
        }
    }

    private void stop() {
        this.pos_ = -1;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static int readValue(byte[] bArr, int i, int i2) {
        int i3;
        int i4;
        if (i2 < 81) {
            return i2 - 16;
        }
        if (i2 < 108) {
            i3 = (i2 - 81) << 8;
            i4 = bArr[i];
        } else if (i2 < 126) {
            i3 = ((i2 - 108) << 16) | ((bArr[i] & 255) << 8);
            i4 = bArr[i + 1];
        } else if (i2 == 126) {
            i3 = ((bArr[i] & 255) << 16) | ((bArr[i + 1] & 255) << 8);
            i4 = bArr[i + 2];
        } else {
            i3 = (bArr[i] << 24) | ((bArr[i + 1] & 255) << 16) | ((bArr[i + 2] & 255) << 8);
            i4 = bArr[i + 3];
        }
        return i3 | (i4 & 255);
    }

    private static int skipValue(byte[] bArr, int i) {
        return skipValue(i + 1, bArr[i] & 255);
    }

    @Deprecated
    public static int jumpByDelta(byte[] bArr, int i) {
        int i2 = i + 1;
        int i3 = bArr[i] & 255;
        if (i3 >= 192) {
            if (i3 < 240) {
                i3 = ((i3 - 192) << 8) | (bArr[i2] & 255);
                i2++;
            } else if (i3 < 254) {
                i3 = ((i3 - 240) << 16) | ((bArr[i2] & 255) << 8) | (bArr[i2 + 1] & 255);
                i2 += 2;
            } else if (i3 == 254) {
                i3 = ((bArr[i2] & 255) << 16) | ((bArr[i2 + 1] & 255) << 8) | (bArr[i2 + 2] & 255);
                i2 += 3;
            } else {
                i3 = (bArr[i2] << 24) | ((bArr[i2 + 1] & 255) << 16) | ((bArr[i2 + 2] & 255) << 8) | (bArr[i2 + 3] & 255);
                i2 += 4;
            }
        }
        return i2 + i3;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static int skipDelta(byte[] bArr, int i) {
        int i2 = i + 1;
        int i3 = bArr[i] & 255;
        return i3 >= 192 ? i3 < 240 ? i2 + 1 : i3 < 254 ? i2 + 2 : i2 + (i3 & 1) + 3 : i2;
    }

    private Result branchNext(int i, int i2, int i3) {
        int i4;
        Result result;
        if (i2 == 0) {
            i2 = this.bytes_[i] & 255;
            i++;
        }
        int i5 = i2 + 1;
        while (i5 > 5) {
            byte[] bArr = this.bytes_;
            int i6 = i + 1;
            if (i3 < (bArr[i] & 255)) {
                i5 >>= 1;
                i = jumpByDelta(bArr, i6);
            } else {
                i5 -= i5 >> 1;
                i = skipDelta(bArr, i6);
            }
        }
        do {
            byte[] bArr2 = this.bytes_;
            int i7 = i + 1;
            if (i3 == (bArr2[i] & 255)) {
                int i8 = bArr2[i7] & 255;
                if ((i8 & 1) != 0) {
                    result = Result.FINAL_VALUE;
                } else {
                    int i9 = i7 + 1;
                    int i10 = i8 >> 1;
                    if (i10 < 81) {
                        i4 = i10 - 16;
                    } else if (i10 < 108) {
                        i4 = ((i10 - 81) << 8) | (bArr2[i9] & 255);
                        i9++;
                    } else if (i10 < 126) {
                        i4 = ((i10 - 108) << 16) | ((bArr2[i9] & 255) << 8) | (bArr2[i9 + 1] & 255);
                        i9 += 2;
                    } else if (i10 == 126) {
                        i4 = ((bArr2[i9] & 255) << 16) | ((bArr2[i9 + 1] & 255) << 8) | (bArr2[i9 + 2] & 255);
                        i9 += 3;
                    } else {
                        i4 = (bArr2[i9] << UCharacterEnums.ECharacterCategory.MATH_SYMBOL) | ((bArr2[i9 + 1] & 255) << 16) | ((bArr2[i9 + 2] & 255) << 8) | (bArr2[i9 + 3] & 255);
                        i9 += 4;
                    }
                    i7 = i9 + i4;
                    int i11 = this.bytes_[i7] & 255;
                    result = i11 >= 32 ? valueResults_[i11 & 1] : Result.NO_VALUE;
                }
                this.pos_ = i7;
                return result;
            }
            i5--;
            i = skipValue(bArr2, i7);
        } while (i5 > 1);
        byte[] bArr3 = this.bytes_;
        int i12 = i + 1;
        if (i3 == (bArr3[i] & 255)) {
            this.pos_ = i12;
            int i13 = bArr3[i12] & 255;
            return i13 >= 32 ? valueResults_[i13 & 1] : Result.NO_VALUE;
        }
        stop();
        return Result.NO_MATCH;
    }

    /* JADX WARN: Code restructure failed: missing block: B:19:0x003a, code lost:
    
        stop();
     */
    /* JADX WARN: Code restructure failed: missing block: B:20:0x003f, code lost:
    
        return com.ibm.icu.util.BytesTrie.Result.NO_MATCH;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    private com.ibm.icu.util.BytesTrie.Result nextImpl(int r5, int r6) {
        /*
            r4 = this;
        L0:
            byte[] r0 = r4.bytes_
            int r1 = r5 + 1
            r5 = r0[r5]
            r5 = r5 & 255(0xff, float:3.57E-43)
            r2 = 16
            if (r5 >= r2) goto L11
            com.ibm.icu.util.BytesTrie$Result r5 = r4.branchNext(r1, r5, r6)
            return r5
        L11:
            r3 = 32
            if (r5 >= r3) goto L36
            int r5 = r5 - r2
            int r2 = r1 + 1
            r1 = r0[r1]
            r1 = r1 & 255(0xff, float:3.57E-43)
            if (r6 != r1) goto L3a
            int r5 = r5 + (-1)
            r4.remainingMatchLength_ = r5
            r4.pos_ = r2
            if (r5 >= 0) goto L33
            r5 = r0[r2]
            r5 = r5 & 255(0xff, float:3.57E-43)
            if (r5 < r3) goto L33
            com.ibm.icu.util.BytesTrie$Result[] r6 = com.ibm.icu.util.BytesTrie.valueResults_
            r5 = r5 & 1
            r5 = r6[r5]
            goto L35
        L33:
            com.ibm.icu.util.BytesTrie$Result r5 = com.ibm.icu.util.BytesTrie.Result.NO_VALUE
        L35:
            return r5
        L36:
            r0 = r5 & 1
            if (r0 == 0) goto L40
        L3a:
            r4.stop()
            com.ibm.icu.util.BytesTrie$Result r5 = com.ibm.icu.util.BytesTrie.Result.NO_MATCH
            return r5
        L40:
            int r5 = skipValue(r1, r5)
            goto L0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ibm.icu.util.BytesTrie.nextImpl(int, int):com.ibm.icu.util.BytesTrie$Result");
    }

    private static long findUniqueValueFromBranch(byte[] bArr, int i, int i2, long j) {
        while (i2 > 5) {
            int i3 = i + 1;
            int i4 = i2 >> 1;
            j = findUniqueValueFromBranch(bArr, jumpByDelta(bArr, i3), i4, j);
            if (j == 0) {
                return 0L;
            }
            i2 -= i4;
            i = skipDelta(bArr, i3);
        }
        do {
            int i5 = i + 1;
            int i6 = i5 + 1;
            int i7 = bArr[i5] & 255;
            boolean z = (i7 & 1) != 0;
            int value = readValue(bArr, i6, i7 >> 1);
            i = skipValue(i6, i7);
            if (!z) {
                j = findUniqueValue(bArr, value + i, j);
                if (j == 0) {
                    return 0L;
                }
            } else if (j == 0) {
                j = (value << 1) | 1;
            } else if (value != ((int) (j >> 1))) {
                return 0L;
            }
            i2--;
        } while (i2 > 1);
        return ((i + 1) << 33) | (j & 8589934591L);
    }

    private static long findUniqueValue(byte[] bArr, int i, long j) {
        while (true) {
            int i2 = i + 1;
            int i3 = bArr[i] & 255;
            if (i3 < 16) {
                if (i3 == 0) {
                    int i4 = i2 + 1;
                    int i5 = bArr[i2] & 255;
                    i2 = i4;
                    i3 = i5;
                }
                long jFindUniqueValueFromBranch = findUniqueValueFromBranch(bArr, i2, i3 + 1, j);
                if (jFindUniqueValueFromBranch == 0) {
                    return 0L;
                }
                i = (int) (jFindUniqueValueFromBranch >>> 33);
                j = jFindUniqueValueFromBranch;
            } else if (i3 < 32) {
                i = i2 + (i3 - 16) + 1;
            } else {
                boolean z = (i3 & 1) != 0;
                int value = readValue(bArr, i2, i3 >> 1);
                if (j == 0) {
                    j = (value << 1) | 1;
                } else if (value != ((int) (j >> 1))) {
                    return 0L;
                }
                if (z) {
                    return j;
                }
                i = skipValue(i2, i3);
            }
        }
    }

    private static void getNextBranchBytes(byte[] bArr, int i, int i2, Appendable appendable) throws IOException {
        while (i2 > 5) {
            int i3 = i + 1;
            int i4 = i2 >> 1;
            getNextBranchBytes(bArr, jumpByDelta(bArr, i3), i4, appendable);
            i2 -= i4;
            i = skipDelta(bArr, i3);
        }
        do {
            append(appendable, bArr[i] & 255);
            i = skipValue(bArr, i + 1);
            i2--;
        } while (i2 > 1);
        append(appendable, bArr[i] & 255);
    }

    private static void append(Appendable appendable, int i) throws IOException {
        try {
            appendable.append((char) i);
        } catch (IOException e) {
            throw new ICUUncheckedIOException(e);
        }
    }
}
