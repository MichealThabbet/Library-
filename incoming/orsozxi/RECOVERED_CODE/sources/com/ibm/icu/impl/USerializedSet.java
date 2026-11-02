package com.ibm.icu.impl;

/* loaded from: classes2.dex */
public final class USerializedSet {
    private char[] array = new char[8];
    private int arrayOffset;
    private int bmpLength;
    private int length;

    public final boolean getSet(char[] cArr, int i) {
        this.array = null;
        this.length = 0;
        this.bmpLength = 0;
        this.arrayOffset = 0;
        int i2 = i + 1;
        char c = cArr[i];
        this.length = c;
        if ((32768 & c) != 0) {
            int i3 = c & 32767;
            this.length = i3;
            int i4 = i2 + 1;
            if (cArr.length < i3 + i4) {
                this.length = 0;
                throw new IndexOutOfBoundsException();
            }
            this.bmpLength = cArr[i2];
            i2 = i4;
        } else {
            if (cArr.length < i2 + c) {
                this.length = 0;
                throw new IndexOutOfBoundsException();
            }
            this.bmpLength = c;
        }
        int i5 = this.length;
        char[] cArr2 = new char[i5];
        this.array = cArr2;
        System.arraycopy(cArr, i2, cArr2, 0, i5);
        return true;
    }

    public final void setToOne(int i) {
        if (1114111 < i) {
            return;
        }
        if (i < 65535) {
            this.length = 2;
            this.bmpLength = 2;
            char[] cArr = this.array;
            cArr[0] = (char) i;
            cArr[1] = (char) (i + 1);
            return;
        }
        if (i == 65535) {
            this.bmpLength = 1;
            this.length = 3;
            char[] cArr2 = this.array;
            cArr2[0] = 65535;
            cArr2[1] = 1;
            cArr2[2] = 0;
            return;
        }
        if (i < 1114111) {
            this.bmpLength = 0;
            this.length = 4;
            char[] cArr3 = this.array;
            cArr3[0] = (char) (i >> 16);
            cArr3[1] = (char) i;
            int i2 = i + 1;
            cArr3[2] = (char) (i2 >> 16);
            cArr3[3] = (char) i2;
            return;
        }
        this.bmpLength = 0;
        this.length = 2;
        char[] cArr4 = this.array;
        cArr4[0] = 16;
        cArr4[1] = 65535;
    }

    public final boolean getRange(int i, int[] iArr) {
        if (i < 0) {
            return false;
        }
        if (this.array == null) {
            this.array = new char[8];
        }
        if (iArr == null || iArr.length < 2) {
            throw new IllegalArgumentException();
        }
        int i2 = i * 2;
        int i3 = this.bmpLength;
        if (i2 < i3) {
            char[] cArr = this.array;
            int i4 = i2 + 1;
            iArr[0] = cArr[i2];
            if (i4 < i3) {
                iArr[1] = cArr[i4] - 1;
            } else if (i4 < this.length) {
                iArr[1] = ((cArr[i4] << 16) | cArr[i4 + 1]) - 1;
            } else {
                iArr[1] = 1114111;
            }
            return true;
        }
        int i5 = (i2 - i3) * 2;
        int i6 = this.length - i3;
        if (i5 >= i6) {
            return false;
        }
        int i7 = this.arrayOffset + i3;
        char[] cArr2 = this.array;
        int i8 = i7 + i5;
        iArr[0] = cArr2[i8 + 1] | (cArr2[i8] << 16);
        int i9 = i5 + 2;
        if (i9 < i6) {
            int i10 = i7 + i9;
            iArr[1] = ((cArr2[i10] << 16) | cArr2[i10 + 1]) - 1;
        } else {
            iArr[1] = 1114111;
        }
        return true;
    }

    public final boolean contains(int i) {
        if (i > 1114111) {
            return false;
        }
        if (i <= 65535) {
            int i2 = 0;
            while (i2 < this.bmpLength && ((char) i) >= this.array[i2]) {
                i2++;
            }
            return (i2 & 1) != 0;
        }
        char c = (char) (i >> 16);
        char c2 = (char) i;
        int i3 = this.bmpLength;
        while (i3 < this.length) {
            char[] cArr = this.array;
            if (c <= cArr[i3] && (c != cArr[i3] || c2 < cArr[i3 + 1])) {
                break;
            }
            i3 += 2;
        }
        return ((i3 + this.bmpLength) & 2) != 0;
    }

    public final int countRanges() {
        int i = this.bmpLength;
        return ((i + ((this.length - i) / 2)) + 1) / 2;
    }
}
