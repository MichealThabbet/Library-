package com.ibm.icu.text;

import it.orsozoxi.android.orsonotes.R2;
import java.nio.BufferOverflowException;
import java.util.Arrays;

/* loaded from: classes2.dex */
public final class Edits {
    private static final int LENGTH_IN_1TRAIL = 61;
    private static final int LENGTH_IN_2TRAIL = 62;
    private static final int MAX_SHORT_CHANGE = 28671;
    private static final int MAX_SHORT_CHANGE_NEW_LENGTH = 7;
    private static final int MAX_SHORT_CHANGE_OLD_LENGTH = 6;
    private static final int MAX_UNCHANGED = 4095;
    private static final int MAX_UNCHANGED_LENGTH = 4096;
    private static final int SHORT_CHANGE_NUM_MASK = 511;
    private static final int STACK_CAPACITY = 100;
    private char[] array = new char[100];
    private int delta;
    private int length;
    private int numChanges;

    public void reset() {
        this.numChanges = 0;
        this.delta = 0;
        this.length = 0;
    }

    private void setLastUnit(int i) {
        this.array[this.length - 1] = (char) i;
    }

    private int lastUnit() {
        int i = this.length;
        if (i > 0) {
            return this.array[i - 1];
        }
        return 65535;
    }

    public void addUnchanged(int i) {
        if (i < 0) {
            throw new IllegalArgumentException("addUnchanged(" + i + "): length must not be negative");
        }
        int iLastUnit = lastUnit();
        if (iLastUnit < 4095) {
            int i2 = 4095 - iLastUnit;
            if (i2 >= i) {
                setLastUnit(iLastUnit + i);
                return;
            } else {
                setLastUnit(4095);
                i -= i2;
            }
        }
        while (i >= 4096) {
            append(4095);
            i -= 4096;
        }
        if (i > 0) {
            append(i - 1);
        }
    }

    public void addReplace(int i, int i2) {
        int i3;
        int i4;
        int i5;
        int i6;
        if (i < 0 || i2 < 0) {
            throw new IllegalArgumentException("addReplace(" + i + ", " + i2 + "): both lengths must be non-negative");
        }
        if (i == 0 && i2 == 0) {
            return;
        }
        this.numChanges++;
        int i7 = i2 - i;
        if (i7 != 0) {
            if ((i7 > 0 && (i6 = this.delta) >= 0 && i7 > Integer.MAX_VALUE - i6) || (i7 < 0 && (i5 = this.delta) < 0 && i7 < Integer.MIN_VALUE - i5)) {
                throw new IndexOutOfBoundsException();
            }
            this.delta += i7;
        }
        if (i > 0 && i <= 6 && i2 <= 7) {
            int i8 = (i << 12) | (i2 << 9);
            int iLastUnit = lastUnit();
            if (4095 < iLastUnit && iLastUnit < MAX_SHORT_CHANGE && (iLastUnit & (-512)) == i8 && (iLastUnit & 511) < 511) {
                setLastUnit(iLastUnit + 1);
                return;
            } else {
                append(i8);
                return;
            }
        }
        if (i < 61 && i2 < 61) {
            append((i << 6) | 28672 | i2);
            return;
        }
        if (this.array.length - this.length >= 5 || growArray()) {
            int i9 = this.length + 1;
            if (i < 61) {
                i3 = (i << 6) | 28672;
            } else if (i <= 32767) {
                this.array[i9] = (char) (i | 32768);
                i9++;
                i3 = 32576;
            } else {
                char[] cArr = this.array;
                int i10 = i9 + 1;
                cArr[i9] = (char) ((i >> 15) | 32768);
                i9 = i10 + 1;
                cArr[i10] = (char) (i | 32768);
                i3 = (((i >> 30) + 62) << 6) | 28672;
            }
            if (i2 < 61) {
                i4 = i3 | i2;
            } else if (i2 <= 32767) {
                i4 = i3 | 61;
                this.array[i9] = (char) (i2 | 32768);
                i9++;
            } else {
                i4 = i3 | ((i2 >> 30) + 62);
                char[] cArr2 = this.array;
                int i11 = i9 + 1;
                cArr2[i9] = (char) ((i2 >> 15) | 32768);
                i9 = i11 + 1;
                cArr2[i11] = (char) (i2 | 32768);
            }
            this.array[this.length] = (char) i4;
            this.length = i9;
        }
    }

    private void append(int i) {
        if (this.length < this.array.length || growArray()) {
            char[] cArr = this.array;
            int i2 = this.length;
            this.length = i2 + 1;
            cArr[i2] = (char) i;
        }
    }

    private boolean growArray() {
        char[] cArr = this.array;
        int length = Integer.MAX_VALUE;
        if (cArr.length == 100) {
            length = R2.dimen.notification_large_icon_width;
        } else {
            if (cArr.length == Integer.MAX_VALUE) {
                throw new BufferOverflowException();
            }
            if (cArr.length < 1073741823) {
                length = cArr.length * 2;
            }
        }
        char[] cArr2 = this.array;
        if (length - cArr2.length < 5) {
            throw new BufferOverflowException();
        }
        this.array = Arrays.copyOf(cArr2, length);
        return true;
    }

    public int lengthDelta() {
        return this.delta;
    }

    public boolean hasChanges() {
        return this.numChanges != 0;
    }

    public int numberOfChanges() {
        return this.numChanges;
    }

    public static final class Iterator {
        static final /* synthetic */ boolean $assertionsDisabled = false;
        private final char[] array;
        private boolean changed;
        private final boolean coarse;
        private int destIndex;
        private int dir;
        private int index;
        private final int length;
        private int newLength_;
        private int oldLength_;
        private final boolean onlyChanges_;
        private int remaining;
        private int replIndex;
        private int srcIndex;

        private Iterator(char[] cArr, int i, boolean z, boolean z2) {
            this.array = cArr;
            this.length = i;
            this.onlyChanges_ = z;
            this.coarse = z2;
        }

        private int readLength(int i) {
            if (i < 61) {
                return i;
            }
            if (i < 62) {
                char[] cArr = this.array;
                int i2 = this.index;
                this.index = i2 + 1;
                return cArr[i2] & 32767;
            }
            char[] cArr2 = this.array;
            int i3 = this.index;
            int i4 = ((i & 1) << 30) | ((cArr2[i3] & 32767) << 15) | (cArr2[i3 + 1] & 32767);
            this.index = i3 + 2;
            return i4;
        }

        private void updateNextIndexes() {
            this.srcIndex += this.oldLength_;
            if (this.changed) {
                this.replIndex += this.newLength_;
            }
            this.destIndex += this.newLength_;
        }

        private void updatePreviousIndexes() {
            this.srcIndex -= this.oldLength_;
            if (this.changed) {
                this.replIndex -= this.newLength_;
            }
            this.destIndex -= this.newLength_;
        }

        private boolean noNext() {
            this.dir = 0;
            this.changed = false;
            this.newLength_ = 0;
            this.oldLength_ = 0;
            return false;
        }

        public boolean next() {
            return next(this.onlyChanges_);
        }

        private boolean next(boolean z) {
            char c;
            int i = this.dir;
            if (i > 0) {
                updateNextIndexes();
            } else {
                if (i < 0 && this.remaining > 0) {
                    this.index++;
                    this.dir = 1;
                    return true;
                }
                this.dir = 1;
            }
            int i2 = this.remaining;
            if (i2 >= 1) {
                if (i2 > 1) {
                    this.remaining = i2 - 1;
                    return true;
                }
                this.remaining = 0;
            }
            int i3 = this.index;
            if (i3 >= this.length) {
                return noNext();
            }
            char[] cArr = this.array;
            this.index = i3 + 1;
            char c2 = cArr[i3];
            if (c2 <= 4095) {
                this.changed = false;
                this.oldLength_ = c2 + 1;
                while (true) {
                    int i4 = this.index;
                    if (i4 >= this.length || (c2 = this.array[i4]) > 4095) {
                        break;
                    }
                    this.index = i4 + 1;
                    this.oldLength_ += c2 + 1;
                }
                this.newLength_ = this.oldLength_;
                if (!z) {
                    return true;
                }
                updateNextIndexes();
                int i5 = this.index;
                if (i5 >= this.length) {
                    return noNext();
                }
                this.index = i5 + 1;
            }
            this.changed = true;
            if (c2 <= Edits.MAX_SHORT_CHANGE) {
                int i6 = c2 >> '\f';
                int i7 = (c2 >> '\t') & 7;
                int i8 = (c2 & 511) + 1;
                if (this.coarse) {
                    this.oldLength_ = i6 * i8;
                    this.newLength_ = i8 * i7;
                } else {
                    this.oldLength_ = i6;
                    this.newLength_ = i7;
                    if (i8 > 1) {
                        this.remaining = i8;
                    }
                    return true;
                }
            } else {
                this.oldLength_ = readLength((c2 >> 6) & 63);
                this.newLength_ = readLength(c2 & '?');
                if (!this.coarse) {
                    return true;
                }
            }
            while (true) {
                int i9 = this.index;
                if (i9 >= this.length || (c = this.array[i9]) <= 4095) {
                    break;
                }
                this.index = i9 + 1;
                if (c <= Edits.MAX_SHORT_CHANGE) {
                    int i10 = (c & 511) + 1;
                    this.oldLength_ += (c >> '\f') * i10;
                    this.newLength_ += ((c >> '\t') & 7) * i10;
                } else {
                    this.oldLength_ += readLength((c >> 6) & 63);
                    this.newLength_ += readLength(c & '?');
                }
            }
            return true;
        }

        private boolean previous() {
            int i;
            char c;
            char c2;
            char c3;
            int i2 = this.dir;
            if (i2 >= 0) {
                if (i2 > 0) {
                    if (this.remaining > 0) {
                        this.index--;
                        this.dir = -1;
                        return true;
                    }
                    updateNextIndexes();
                }
                this.dir = -1;
            }
            int i3 = this.remaining;
            if (i3 > 0) {
                if (i3 <= (this.array[this.index] & 511)) {
                    this.remaining = i3 + 1;
                    updatePreviousIndexes();
                    return true;
                }
                this.remaining = 0;
            }
            int i4 = this.index;
            if (i4 <= 0) {
                return noNext();
            }
            char[] cArr = this.array;
            int i5 = i4 - 1;
            this.index = i5;
            char c4 = cArr[i5];
            if (c4 <= 4095) {
                this.changed = false;
                this.oldLength_ = c4 + 1;
                while (true) {
                    int i6 = this.index;
                    if (i6 <= 0 || (c3 = this.array[i6 - 1]) > 4095) {
                        break;
                    }
                    this.index = i6 - 1;
                    this.oldLength_ += c3 + 1;
                }
                this.newLength_ = this.oldLength_;
                updatePreviousIndexes();
                return true;
            }
            this.changed = true;
            if (c4 <= Edits.MAX_SHORT_CHANGE) {
                int i7 = c4 >> '\f';
                int i8 = (c4 >> '\t') & 7;
                int i9 = (c4 & 511) + 1;
                if (this.coarse) {
                    this.oldLength_ = i7 * i9;
                    this.newLength_ = i9 * i8;
                } else {
                    this.oldLength_ = i7;
                    this.newLength_ = i8;
                    if (i9 > 1) {
                        this.remaining = 1;
                    }
                    updatePreviousIndexes();
                    return true;
                }
            } else {
                if (c4 <= 32767) {
                    this.oldLength_ = readLength((c4 >> 6) & 63);
                    this.newLength_ = readLength(c4 & '?');
                } else {
                    do {
                        char[] cArr2 = this.array;
                        i = this.index - 1;
                        this.index = i;
                        c = cArr2[i];
                    } while (c > 32767);
                    this.index = i + 1;
                    this.oldLength_ = readLength((c >> 6) & 63);
                    this.newLength_ = readLength(c & '?');
                    this.index = i;
                }
                if (!this.coarse) {
                    updatePreviousIndexes();
                    return true;
                }
            }
            while (true) {
                int i10 = this.index;
                if (i10 <= 0 || (c2 = this.array[i10 - 1]) <= 4095) {
                    break;
                }
                int i11 = i10 - 1;
                this.index = i11;
                if (c2 <= Edits.MAX_SHORT_CHANGE) {
                    int i12 = (c2 & 511) + 1;
                    this.oldLength_ += (c2 >> '\f') * i12;
                    this.newLength_ += ((c2 >> '\t') & 7) * i12;
                } else if (c2 <= 32767) {
                    this.index = i11 + 1;
                    this.oldLength_ += readLength((c2 >> 6) & 63);
                    this.newLength_ += readLength(c2 & '?');
                    this.index = i11;
                }
            }
            updatePreviousIndexes();
            return true;
        }

        public boolean findSourceIndex(int i) {
            return findIndex(i, true) == 0;
        }

        public boolean findDestinationIndex(int i) {
            return findIndex(i, false) == 0;
        }

        private int findIndex(int i, boolean z) {
            int i2;
            int i3;
            int i4;
            int i5;
            if (i < 0) {
                return -1;
            }
            if (z) {
                i2 = this.srcIndex;
                i3 = this.oldLength_;
            } else {
                i2 = this.destIndex;
                i3 = this.newLength_;
            }
            if (i < i2) {
                if (i >= i2 / 2) {
                    while (true) {
                        previous();
                        int i6 = z ? this.srcIndex : this.destIndex;
                        if (i >= i6) {
                            return 0;
                        }
                        if (this.remaining > 0) {
                            int i7 = z ? this.oldLength_ : this.newLength_;
                            int i8 = (this.array[this.index] & 511) + 1;
                            int i9 = this.remaining;
                            int i10 = i8 - i9;
                            if (i >= i6 - (i10 * i7)) {
                                int i11 = (((i6 - i) - 1) / i7) + 1;
                                this.srcIndex -= this.oldLength_ * i11;
                                int i12 = this.replIndex;
                                int i13 = this.newLength_;
                                this.replIndex = i12 - (i11 * i13);
                                this.destIndex -= i13 * i11;
                                this.remaining = i9 + i11;
                                return 0;
                            }
                            this.srcIndex -= this.oldLength_ * i10;
                            int i14 = this.replIndex;
                            int i15 = this.newLength_;
                            this.replIndex = i14 - (i10 * i15);
                            this.destIndex -= i10 * i15;
                            this.remaining = 0;
                        }
                    }
                } else {
                    this.dir = 0;
                    this.destIndex = 0;
                    this.replIndex = 0;
                    this.srcIndex = 0;
                    this.newLength_ = 0;
                    this.oldLength_ = 0;
                    this.remaining = 0;
                    this.index = 0;
                }
            } else if (i < i2 + i3) {
                return 0;
            }
            while (next(false)) {
                if (z) {
                    i4 = this.srcIndex;
                    i5 = this.oldLength_;
                } else {
                    i4 = this.destIndex;
                    i5 = this.newLength_;
                }
                if (i < i4 + i5) {
                    return 0;
                }
                int i16 = this.remaining;
                if (i16 > 1) {
                    if (i < (i16 * i5) + i4) {
                        int i17 = (i - i4) / i5;
                        this.srcIndex += this.oldLength_ * i17;
                        int i18 = this.replIndex;
                        int i19 = this.newLength_;
                        this.replIndex = i18 + (i17 * i19);
                        this.destIndex += i19 * i17;
                        this.remaining = i16 - i17;
                        return 0;
                    }
                    this.oldLength_ *= i16;
                    this.newLength_ *= i16;
                    this.remaining = 0;
                }
            }
            return 1;
        }

        public int destinationIndexFromSourceIndex(int i) {
            int i2;
            int iFindIndex = findIndex(i, true);
            if (iFindIndex < 0) {
                return 0;
            }
            if (iFindIndex > 0 || i == (i2 = this.srcIndex)) {
                return this.destIndex;
            }
            if (this.changed) {
                return this.destIndex + this.newLength_;
            }
            return this.destIndex + (i - i2);
        }

        public int sourceIndexFromDestinationIndex(int i) {
            int i2;
            int iFindIndex = findIndex(i, false);
            if (iFindIndex < 0) {
                return 0;
            }
            if (iFindIndex > 0 || i == (i2 = this.destIndex)) {
                return this.srcIndex;
            }
            if (this.changed) {
                return this.srcIndex + this.oldLength_;
            }
            return this.srcIndex + (i - i2);
        }

        public boolean hasChange() {
            return this.changed;
        }

        public int oldLength() {
            return this.oldLength_;
        }

        public int newLength() {
            return this.newLength_;
        }

        public int sourceIndex() {
            return this.srcIndex;
        }

        public int replacementIndex() {
            return this.replIndex;
        }

        public int destinationIndex() {
            return this.destIndex;
        }

        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append(super.toString());
            sb.append("{ src[");
            sb.append(this.srcIndex);
            sb.append("..");
            sb.append(this.srcIndex + this.oldLength_);
            if (this.changed) {
                sb.append("] ⇝ dest[");
            } else {
                sb.append("] ≡ dest[");
            }
            sb.append(this.destIndex);
            sb.append("..");
            sb.append(this.destIndex + this.newLength_);
            if (this.changed) {
                sb.append("], repl[");
                sb.append(this.replIndex);
                sb.append("..");
                sb.append(this.replIndex + this.newLength_);
                sb.append("] }");
            } else {
                sb.append("] (no-change) }");
            }
            return sb.toString();
        }
    }

    public Iterator getCoarseChangesIterator() {
        return new Iterator(this.array, this.length, true, true);
    }

    public Iterator getCoarseIterator() {
        return new Iterator(this.array, this.length, false, true);
    }

    public Iterator getFineChangesIterator() {
        return new Iterator(this.array, this.length, true, false);
    }

    public Iterator getFineIterator() {
        return new Iterator(this.array, this.length, false, false);
    }

    /* JADX WARN: Code restructure failed: missing block: B:30:0x005f, code lost:
    
        if (r3 != 0) goto L35;
     */
    /* JADX WARN: Code restructure failed: missing block: B:31:0x0061, code lost:
    
        if (r5 != 0) goto L33;
     */
    /* JADX WARN: Code restructure failed: missing block: B:32:0x0063, code lost:
    
        if (r6 == 0) goto L34;
     */
    /* JADX WARN: Code restructure failed: missing block: B:33:0x0065, code lost:
    
        addReplace(r5, r6);
     */
    /* JADX WARN: Code restructure failed: missing block: B:34:0x0068, code lost:
    
        return r10;
     */
    /* JADX WARN: Code restructure failed: missing block: B:36:0x0070, code lost:
    
        throw new java.lang.IllegalArgumentException("The ab output string is shorter than the bc input string.");
     */
    /* JADX WARN: Removed duplicated region for block: B:67:0x00d8  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public com.ibm.icu.text.Edits mergeAndAppend(com.ibm.icu.text.Edits r11, com.ibm.icu.text.Edits r12) {
        /*
            Method dump skipped, instructions count: 229
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ibm.icu.text.Edits.mergeAndAppend(com.ibm.icu.text.Edits, com.ibm.icu.text.Edits):com.ibm.icu.text.Edits");
    }
}
