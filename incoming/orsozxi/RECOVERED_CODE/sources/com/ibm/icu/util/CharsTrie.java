package com.ibm.icu.util;

import com.ibm.icu.text.UTF16;
import com.ibm.icu.util.BytesTrie;
import java.io.IOException;
import java.util.ArrayList;
import java.util.NoSuchElementException;

/* loaded from: classes2.dex */
public final class CharsTrie implements Cloneable, Iterable<Entry> {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    static final int kMaxBranchLinearSubNodeLength = 5;
    static final int kMaxLinearMatchLength = 16;
    static final int kMaxOneUnitDelta = 64511;
    static final int kMaxOneUnitNodeValue = 255;
    static final int kMaxOneUnitValue = 16383;
    static final int kMaxTwoUnitDelta = 67043327;
    static final int kMaxTwoUnitNodeValue = 16646143;
    static final int kMaxTwoUnitValue = 1073676287;
    static final int kMinLinearMatch = 48;
    static final int kMinTwoUnitDeltaLead = 64512;
    static final int kMinTwoUnitNodeValueLead = 16448;
    static final int kMinTwoUnitValueLead = 16384;
    static final int kMinValueLead = 64;
    static final int kNodeTypeMask = 63;
    static final int kThreeUnitDeltaLead = 65535;
    static final int kThreeUnitNodeValueLead = 32704;
    static final int kThreeUnitValueLead = 32767;
    static final int kValueIsFinal = 32768;
    private static BytesTrie.Result[] valueResults_ = {BytesTrie.Result.INTERMEDIATE_VALUE, BytesTrie.Result.FINAL_VALUE};
    private CharSequence chars_;
    private int pos_;
    private int remainingMatchLength_;
    private int root_;

    /* JADX INFO: Access modifiers changed from: private */
    public static int skipNodeValue(int i, int i2) {
        return i2 >= kMinTwoUnitNodeValueLead ? i2 < kThreeUnitNodeValueLead ? i + 1 : i + 2 : i;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static int skipValue(int i, int i2) {
        return i2 >= 16384 ? i2 < kThreeUnitValueLead ? i + 1 : i + 2 : i;
    }

    public CharsTrie(CharSequence charSequence, int i) {
        this.chars_ = charSequence;
        this.root_ = i;
        this.pos_ = i;
        this.remainingMatchLength_ = -1;
    }

    public CharsTrie(CharsTrie charsTrie) {
        this.chars_ = charsTrie.chars_;
        this.root_ = charsTrie.root_;
        this.pos_ = charsTrie.pos_;
        this.remainingMatchLength_ = charsTrie.remainingMatchLength_;
    }

    /* renamed from: clone, reason: merged with bridge method [inline-methods] */
    public CharsTrie m26clone() throws CloneNotSupportedException {
        return (CharsTrie) super.clone();
    }

    public CharsTrie reset() {
        this.pos_ = this.root_;
        this.remainingMatchLength_ = -1;
        return this;
    }

    public long getState64() {
        return (this.remainingMatchLength_ << 32) | this.pos_;
    }

    public CharsTrie resetToState64(long j) {
        this.remainingMatchLength_ = (int) (j >> 32);
        this.pos_ = (int) j;
        return this;
    }

    public static final class State {
        private CharSequence chars;
        private int pos;
        private int remainingMatchLength;
        private int root;
    }

    public CharsTrie saveState(State state) {
        state.chars = this.chars_;
        state.root = this.root_;
        state.pos = this.pos_;
        state.remainingMatchLength = this.remainingMatchLength_;
        return this;
    }

    public CharsTrie resetToState(State state) {
        if (this.chars_ == state.chars && this.chars_ != null && this.root_ == state.root) {
            this.pos_ = state.pos;
            this.remainingMatchLength_ = state.remainingMatchLength;
            return this;
        }
        throw new IllegalArgumentException("incompatible trie state");
    }

    public BytesTrie.Result current() {
        char cCharAt;
        int i = this.pos_;
        if (i < 0) {
            return BytesTrie.Result.NO_MATCH;
        }
        return (this.remainingMatchLength_ >= 0 || (cCharAt = this.chars_.charAt(i)) < '@') ? BytesTrie.Result.NO_VALUE : valueResults_[cCharAt >> 15];
    }

    public BytesTrie.Result first(int i) {
        this.remainingMatchLength_ = -1;
        return nextImpl(this.root_, i);
    }

    public BytesTrie.Result firstForCodePoint(int i) {
        if (i <= 65535) {
            return first(i);
        }
        return first(UTF16.getLeadSurrogate(i)).hasNext() ? next(UTF16.getTrailSurrogate(i)) : BytesTrie.Result.NO_MATCH;
    }

    public BytesTrie.Result next(int i) {
        char cCharAt;
        int i2 = this.pos_;
        if (i2 < 0) {
            return BytesTrie.Result.NO_MATCH;
        }
        int i3 = this.remainingMatchLength_;
        if (i3 >= 0) {
            int i4 = i2 + 1;
            if (i == this.chars_.charAt(i2)) {
                int i5 = i3 - 1;
                this.remainingMatchLength_ = i5;
                this.pos_ = i4;
                return (i5 >= 0 || (cCharAt = this.chars_.charAt(i4)) < '@') ? BytesTrie.Result.NO_VALUE : valueResults_[cCharAt >> 15];
            }
            stop();
            return BytesTrie.Result.NO_MATCH;
        }
        return nextImpl(i2, i);
    }

    public BytesTrie.Result nextForCodePoint(int i) {
        if (i <= 65535) {
            return next(i);
        }
        return next(UTF16.getLeadSurrogate(i)).hasNext() ? next(UTF16.getTrailSurrogate(i)) : BytesTrie.Result.NO_MATCH;
    }

    public BytesTrie.Result next(CharSequence charSequence, int i, int i2) {
        char cCharAt;
        if (i >= i2) {
            return current();
        }
        int i3 = this.pos_;
        if (i3 < 0) {
            return BytesTrie.Result.NO_MATCH;
        }
        int i4 = this.remainingMatchLength_;
        while (i != i2) {
            int i5 = i + 1;
            char cCharAt2 = charSequence.charAt(i);
            if (i4 < 0) {
                this.remainingMatchLength_ = i4;
                int iSkipNodeValue = i3 + 1;
                int iCharAt = this.chars_.charAt(i3);
                while (true) {
                    if (iCharAt < 48) {
                        BytesTrie.Result resultBranchNext = branchNext(iSkipNodeValue, iCharAt, cCharAt2);
                        if (resultBranchNext == BytesTrie.Result.NO_MATCH) {
                            return BytesTrie.Result.NO_MATCH;
                        }
                        if (i5 == i2) {
                            return resultBranchNext;
                        }
                        if (resultBranchNext == BytesTrie.Result.FINAL_VALUE) {
                            stop();
                            return BytesTrie.Result.NO_MATCH;
                        }
                        char cCharAt3 = charSequence.charAt(i5);
                        int i6 = this.pos_;
                        iSkipNodeValue = i6 + 1;
                        i5++;
                        cCharAt2 = cCharAt3;
                        iCharAt = this.chars_.charAt(i6);
                    } else if (iCharAt < 64) {
                        int i7 = iCharAt - 48;
                        if (cCharAt2 != this.chars_.charAt(iSkipNodeValue)) {
                            stop();
                            return BytesTrie.Result.NO_MATCH;
                        }
                        i4 = i7 - 1;
                        i3 = iSkipNodeValue + 1;
                    } else {
                        if ((32768 & iCharAt) != 0) {
                            stop();
                            return BytesTrie.Result.NO_MATCH;
                        }
                        iSkipNodeValue = skipNodeValue(iSkipNodeValue, iCharAt);
                        iCharAt &= 63;
                    }
                }
            } else {
                if (cCharAt2 != this.chars_.charAt(i3)) {
                    stop();
                    return BytesTrie.Result.NO_MATCH;
                }
                i3++;
                i4--;
            }
            i = i5;
        }
        this.remainingMatchLength_ = i4;
        this.pos_ = i3;
        return (i4 >= 0 || (cCharAt = this.chars_.charAt(i3)) < '@') ? BytesTrie.Result.NO_VALUE : valueResults_[cCharAt >> 15];
    }

    public int getValue() {
        int i = this.pos_;
        int i2 = i + 1;
        char cCharAt = this.chars_.charAt(i);
        return (32768 & cCharAt) != 0 ? readValue(this.chars_, i2, cCharAt & 32767) : readNodeValue(this.chars_, i2, cCharAt);
    }

    public long getUniqueValue() {
        int i = this.pos_;
        if (i < 0) {
            return 0L;
        }
        return (findUniqueValue(this.chars_, (i + this.remainingMatchLength_) + 1, 0L) << 31) >> 31;
    }

    public int getNextChars(Appendable appendable) throws IOException {
        int i = this.pos_;
        if (i < 0) {
            return 0;
        }
        if (this.remainingMatchLength_ >= 0) {
            append(appendable, this.chars_.charAt(i));
            return 1;
        }
        int iSkipNodeValue = i + 1;
        int iCharAt = this.chars_.charAt(i);
        if (iCharAt >= 64) {
            if ((32768 & iCharAt) != 0) {
                return 0;
            }
            iSkipNodeValue = skipNodeValue(iSkipNodeValue, iCharAt);
            iCharAt &= 63;
        }
        if (iCharAt < 48) {
            if (iCharAt == 0) {
                iCharAt = this.chars_.charAt(iSkipNodeValue);
                iSkipNodeValue++;
            }
            int i2 = iCharAt + 1;
            getNextBranchChars(this.chars_, iSkipNodeValue, i2, appendable);
            return i2;
        }
        append(appendable, this.chars_.charAt(iSkipNodeValue));
        return 1;
    }

    @Override // java.lang.Iterable
    /* renamed from: iterator, reason: merged with bridge method [inline-methods] */
    public java.util.Iterator<Entry> iterator2() {
        return new Iterator(this.chars_, this.pos_, this.remainingMatchLength_, 0);
    }

    public Iterator iterator(int i) {
        return new Iterator(this.chars_, this.pos_, this.remainingMatchLength_, i);
    }

    public static Iterator iterator(CharSequence charSequence, int i, int i2) {
        return new Iterator(charSequence, i, -1, i2);
    }

    public static final class Entry {
        public CharSequence chars;
        public int value;

        private Entry() {
        }
    }

    public static final class Iterator implements java.util.Iterator<Entry> {
        private CharSequence chars_;
        private Entry entry_;
        private int initialPos_;
        private int initialRemainingMatchLength_;
        private int maxLength_;
        private int pos_;
        private int remainingMatchLength_;
        private boolean skipValue_;
        private ArrayList<Long> stack_;
        private StringBuilder str_;

        private Iterator(CharSequence charSequence, int i, int i2, int i3) {
            this.str_ = new StringBuilder();
            this.entry_ = new Entry();
            this.stack_ = new ArrayList<>();
            this.chars_ = charSequence;
            this.initialPos_ = i;
            this.pos_ = i;
            this.initialRemainingMatchLength_ = i2;
            this.remainingMatchLength_ = i2;
            this.maxLength_ = i3;
            if (i2 >= 0) {
                int i4 = i2 + 1;
                i3 = (i3 <= 0 || i4 <= i3) ? i4 : i3;
                StringBuilder sb = this.str_;
                CharSequence charSequence2 = this.chars_;
                int i5 = this.pos_;
                sb.append(charSequence2, i5, i5 + i3);
                this.pos_ += i3;
                this.remainingMatchLength_ -= i3;
            }
        }

        public Iterator reset() {
            this.pos_ = this.initialPos_;
            int i = this.initialRemainingMatchLength_;
            this.remainingMatchLength_ = i;
            this.skipValue_ = false;
            int i2 = i + 1;
            int i3 = this.maxLength_;
            if (i3 > 0 && i2 > i3) {
                i2 = i3;
            }
            this.str_.setLength(i2);
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
                this.str_.setLength(65535 & i);
                int i3 = i >>> 16;
                if (i3 > 1) {
                    iBranchNext = branchNext(i2, i3);
                    if (iBranchNext < 0) {
                        return this.entry_;
                    }
                } else {
                    this.str_.append(this.chars_.charAt(i2));
                    iBranchNext = i2 + 1;
                }
            }
            if (this.remainingMatchLength_ >= 0) {
                return truncateAndStop();
            }
            while (true) {
                int iSkipNodeValue = iBranchNext + 1;
                int iCharAt = this.chars_.charAt(iBranchNext);
                if (iCharAt >= 64) {
                    if (this.skipValue_) {
                        iSkipNodeValue = CharsTrie.skipNodeValue(iSkipNodeValue, iCharAt);
                        iCharAt &= 63;
                        this.skipValue_ = false;
                    } else {
                        boolean z = (32768 & iCharAt) != 0;
                        if (z) {
                            this.entry_.value = CharsTrie.readValue(this.chars_, iSkipNodeValue, iCharAt & CharsTrie.kThreeUnitValueLead);
                        } else {
                            this.entry_.value = CharsTrie.readNodeValue(this.chars_, iSkipNodeValue, iCharAt);
                        }
                        if (z || (this.maxLength_ > 0 && this.str_.length() == this.maxLength_)) {
                            this.pos_ = -1;
                        } else {
                            this.pos_ = iSkipNodeValue - 1;
                            this.skipValue_ = true;
                        }
                        this.entry_.chars = this.str_;
                        return this.entry_;
                    }
                }
                if (this.maxLength_ > 0 && this.str_.length() == this.maxLength_) {
                    return truncateAndStop();
                }
                if (iCharAt < 48) {
                    if (iCharAt == 0) {
                        iCharAt = this.chars_.charAt(iSkipNodeValue);
                        iSkipNodeValue++;
                    }
                    iBranchNext = branchNext(iSkipNodeValue, iCharAt + 1);
                    if (iBranchNext < 0) {
                        return this.entry_;
                    }
                } else {
                    int i4 = (iCharAt - 48) + 1;
                    if (this.maxLength_ > 0) {
                        int length = this.str_.length() + i4;
                        int i5 = this.maxLength_;
                        if (length > i5) {
                            StringBuilder sb = this.str_;
                            sb.append(this.chars_, iSkipNodeValue, (i5 + iSkipNodeValue) - sb.length());
                            return truncateAndStop();
                        }
                    }
                    iBranchNext = i4 + iSkipNodeValue;
                    this.str_.append(this.chars_, iSkipNodeValue, iBranchNext);
                }
            }
        }

        @Override // java.util.Iterator
        public void remove() {
            throw new UnsupportedOperationException();
        }

        private Entry truncateAndStop() {
            this.pos_ = -1;
            this.entry_.chars = this.str_;
            this.entry_.value = -1;
            return this.entry_;
        }

        private int branchNext(int i, int i2) {
            while (i2 > 5) {
                this.stack_.add(Long.valueOf((CharsTrie.skipDelta(this.chars_, r11) << 32) | ((i2 - r3) << 16) | this.str_.length()));
                i = CharsTrie.jumpByDelta(this.chars_, i + 1);
                i2 >>= 1;
            }
            int i3 = i + 1;
            char cCharAt = this.chars_.charAt(i);
            int i4 = i3 + 1;
            char cCharAt2 = this.chars_.charAt(i3);
            boolean z = (32768 & cCharAt2) != 0;
            int i5 = cCharAt2 & 32767;
            int value = CharsTrie.readValue(this.chars_, i4, i5);
            int iSkipValue = CharsTrie.skipValue(i4, i5);
            this.stack_.add(Long.valueOf((iSkipValue << 32) | ((i2 - 1) << 16) | this.str_.length()));
            this.str_.append(cCharAt);
            if (!z) {
                return iSkipValue + value;
            }
            this.pos_ = -1;
            this.entry_.chars = this.str_;
            this.entry_.value = value;
            return -1;
        }
    }

    private void stop() {
        this.pos_ = -1;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static int readValue(CharSequence charSequence, int i, int i2) {
        int iCharAt;
        char cCharAt;
        if (i2 < 16384) {
            return i2;
        }
        if (i2 < kThreeUnitValueLead) {
            iCharAt = (i2 - 16384) << 16;
            cCharAt = charSequence.charAt(i);
        } else {
            iCharAt = charSequence.charAt(i) << 16;
            cCharAt = charSequence.charAt(i + 1);
        }
        return iCharAt | cCharAt;
    }

    private static int skipValue(CharSequence charSequence, int i) {
        return skipValue(i + 1, charSequence.charAt(i) & 32767);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static int readNodeValue(CharSequence charSequence, int i, int i2) {
        int iCharAt;
        char cCharAt;
        if (i2 < kMinTwoUnitNodeValueLead) {
            return (i2 >> 6) - 1;
        }
        if (i2 < kThreeUnitNodeValueLead) {
            iCharAt = ((i2 & kThreeUnitNodeValueLead) - kMinTwoUnitNodeValueLead) << 10;
            cCharAt = charSequence.charAt(i);
        } else {
            iCharAt = charSequence.charAt(i) << 16;
            cCharAt = charSequence.charAt(i + 1);
        }
        return cCharAt | iCharAt;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static int jumpByDelta(CharSequence charSequence, int i) {
        int i2 = i + 1;
        int iCharAt = charSequence.charAt(i);
        if (iCharAt >= 64512) {
            if (iCharAt == 65535) {
                iCharAt = (charSequence.charAt(i2) << 16) | charSequence.charAt(i2 + 1);
                i2 += 2;
            } else {
                iCharAt = ((iCharAt - 64512) << 16) | charSequence.charAt(i2);
                i2++;
            }
        }
        return i2 + iCharAt;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static int skipDelta(CharSequence charSequence, int i) {
        int i2 = i + 1;
        char cCharAt = charSequence.charAt(i);
        return cCharAt >= 64512 ? cCharAt == 65535 ? i2 + 2 : i2 + 1 : i2;
    }

    private BytesTrie.Result branchNext(int i, int i2, int i3) {
        BytesTrie.Result result;
        if (i2 == 0) {
            i2 = this.chars_.charAt(i);
            i++;
        }
        int i4 = i2 + 1;
        while (i4 > 5) {
            int i5 = i + 1;
            if (i3 < this.chars_.charAt(i)) {
                i4 >>= 1;
                i = jumpByDelta(this.chars_, i5);
            } else {
                i4 -= i4 >> 1;
                i = skipDelta(this.chars_, i5);
            }
        }
        do {
            int i6 = i + 1;
            if (i3 == this.chars_.charAt(i)) {
                int iCharAt = this.chars_.charAt(i6);
                if ((32768 & iCharAt) != 0) {
                    result = BytesTrie.Result.FINAL_VALUE;
                } else {
                    int i7 = i6 + 1;
                    if (iCharAt >= 16384) {
                        if (iCharAt < kThreeUnitValueLead) {
                            iCharAt = ((iCharAt - 16384) << 16) | this.chars_.charAt(i7);
                            i7++;
                        } else {
                            iCharAt = (this.chars_.charAt(i7) << 16) | this.chars_.charAt(i7 + 1);
                            i7 += 2;
                        }
                    }
                    i6 = i7 + iCharAt;
                    char cCharAt = this.chars_.charAt(i6);
                    result = cCharAt >= '@' ? valueResults_[cCharAt >> 15] : BytesTrie.Result.NO_VALUE;
                }
                this.pos_ = i6;
                return result;
            }
            i4--;
            i = skipValue(this.chars_, i6);
        } while (i4 > 1);
        int i8 = i + 1;
        if (i3 == this.chars_.charAt(i)) {
            this.pos_ = i8;
            char cCharAt2 = this.chars_.charAt(i8);
            return cCharAt2 >= '@' ? valueResults_[cCharAt2 >> 15] : BytesTrie.Result.NO_VALUE;
        }
        stop();
        return BytesTrie.Result.NO_MATCH;
    }

    private BytesTrie.Result nextImpl(int i, int i2) {
        char cCharAt;
        int iSkipNodeValue = i + 1;
        int iCharAt = this.chars_.charAt(i);
        while (iCharAt >= 48) {
            if (iCharAt < 64) {
                int i3 = iCharAt - 48;
                int i4 = iSkipNodeValue + 1;
                if (i2 == this.chars_.charAt(iSkipNodeValue)) {
                    int i5 = i3 - 1;
                    this.remainingMatchLength_ = i5;
                    this.pos_ = i4;
                    return (i5 >= 0 || (cCharAt = this.chars_.charAt(i4)) < '@') ? BytesTrie.Result.NO_VALUE : valueResults_[cCharAt >> 15];
                }
            } else if ((32768 & iCharAt) == 0) {
                iSkipNodeValue = skipNodeValue(iSkipNodeValue, iCharAt);
                iCharAt &= 63;
            }
            stop();
            return BytesTrie.Result.NO_MATCH;
        }
        return branchNext(iSkipNodeValue, iCharAt, i2);
    }

    private static long findUniqueValueFromBranch(CharSequence charSequence, int i, int i2, long j) {
        while (i2 > 5) {
            int i3 = i + 1;
            int i4 = i2 >> 1;
            j = findUniqueValueFromBranch(charSequence, jumpByDelta(charSequence, i3), i4, j);
            if (j == 0) {
                return 0L;
            }
            i2 -= i4;
            i = skipDelta(charSequence, i3);
        }
        do {
            int i5 = i + 1;
            int i6 = i5 + 1;
            char cCharAt = charSequence.charAt(i5);
            boolean z = (32768 & cCharAt) != 0;
            int i7 = cCharAt & 32767;
            int value = readValue(charSequence, i6, i7);
            i = skipValue(i6, i7);
            if (!z) {
                j = findUniqueValue(charSequence, value + i, j);
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

    private static long findUniqueValue(CharSequence charSequence, int i, long j) {
        int nodeValue;
        int iSkipNodeValue = i + 1;
        int iCharAt = charSequence.charAt(i);
        while (true) {
            if (iCharAt < 48) {
                if (iCharAt == 0) {
                    int i2 = iSkipNodeValue + 1;
                    char cCharAt = charSequence.charAt(iSkipNodeValue);
                    iSkipNodeValue = i2;
                    iCharAt = cCharAt;
                }
                j = findUniqueValueFromBranch(charSequence, iSkipNodeValue, iCharAt + 1, j);
                if (j == 0) {
                    return 0L;
                }
                int i3 = (int) (j >>> 33);
                iSkipNodeValue = i3 + 1;
                iCharAt = charSequence.charAt(i3);
            } else if (iCharAt < 64) {
                int i4 = iSkipNodeValue + (iCharAt - 48) + 1;
                int i5 = i4 + 1;
                char cCharAt2 = charSequence.charAt(i4);
                iSkipNodeValue = i5;
                iCharAt = cCharAt2;
            } else {
                boolean z = (32768 & iCharAt) != 0;
                if (z) {
                    nodeValue = readValue(charSequence, iSkipNodeValue, iCharAt & kThreeUnitValueLead);
                } else {
                    nodeValue = readNodeValue(charSequence, iSkipNodeValue, iCharAt);
                }
                if (j == 0) {
                    j = (nodeValue << 1) | 1;
                } else if (nodeValue != ((int) (j >> 1))) {
                    return 0L;
                }
                if (z) {
                    return j;
                }
                iSkipNodeValue = skipNodeValue(iSkipNodeValue, iCharAt);
                iCharAt &= 63;
            }
        }
    }

    private static void getNextBranchChars(CharSequence charSequence, int i, int i2, Appendable appendable) throws IOException {
        while (i2 > 5) {
            int i3 = i + 1;
            int i4 = i2 >> 1;
            getNextBranchChars(charSequence, jumpByDelta(charSequence, i3), i4, appendable);
            i2 -= i4;
            i = skipDelta(charSequence, i3);
        }
        do {
            append(appendable, charSequence.charAt(i));
            i = skipValue(charSequence, i + 1);
            i2--;
        } while (i2 > 1);
        append(appendable, charSequence.charAt(i));
    }

    private static void append(Appendable appendable, int i) throws IOException {
        try {
            appendable.append((char) i);
        } catch (IOException e) {
            throw new ICUUncheckedIOException(e);
        }
    }
}
