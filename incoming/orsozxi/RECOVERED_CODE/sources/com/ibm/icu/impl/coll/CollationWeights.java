package com.ibm.icu.impl.coll;

import java.util.Arrays;

/* loaded from: classes2.dex */
public final class CollationWeights {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private int middleLength;
    private int rangeCount;
    private int rangeIndex;
    private int[] minBytes = new int[5];
    private int[] maxBytes = new int[5];
    private WeightRange[] ranges = new WeightRange[7];

    private static long decWeightTrail(long j, int i) {
        return j - (1 << ((4 - i) * 8));
    }

    private static int getWeightTrail(long j, int i) {
        return ((int) (j >> ((4 - i) * 8))) & 255;
    }

    private static long incWeightTrail(long j, int i) {
        return j + (1 << ((4 - i) * 8));
    }

    public static int lengthOfWeight(long j) {
        if ((16777215 & j) == 0) {
            return 1;
        }
        if ((65535 & j) == 0) {
            return 2;
        }
        return (j & 255) == 0 ? 3 : 4;
    }

    private static long setWeightByte(long j, int i, int i2) {
        int i3 = i * 8;
        int i4 = 32 - i3;
        return (j & ((i3 < 32 ? 4294967295 >> i3 : 0L) | (CollationRootElements.PRIMARY_SENTINEL << i4))) | (i2 << i4);
    }

    private static long setWeightTrail(long j, int i, int i2) {
        int i3 = (4 - i) * 8;
        return (j & (CollationRootElements.PRIMARY_SENTINEL << i3)) | (i2 << i3);
    }

    private static long truncateWeight(long j, int i) {
        return j & (4294967295 << ((4 - i) * 8));
    }

    public void initForPrimary(boolean z) {
        this.middleLength = 1;
        int[] iArr = this.minBytes;
        iArr[1] = 3;
        int[] iArr2 = this.maxBytes;
        iArr2[1] = 255;
        if (z) {
            iArr[2] = 4;
            iArr2[2] = 254;
        } else {
            iArr[2] = 2;
            iArr2[2] = 255;
        }
        int[] iArr3 = this.minBytes;
        iArr3[3] = 2;
        int[] iArr4 = this.maxBytes;
        iArr4[3] = 255;
        iArr3[4] = 2;
        iArr4[4] = 255;
    }

    public void initForSecondary() {
        this.middleLength = 3;
        int[] iArr = this.minBytes;
        iArr[1] = 0;
        int[] iArr2 = this.maxBytes;
        iArr2[1] = 0;
        iArr[2] = 0;
        iArr2[2] = 0;
        iArr[3] = 2;
        iArr2[3] = 255;
        iArr[4] = 2;
        iArr2[4] = 255;
    }

    public void initForTertiary() {
        this.middleLength = 3;
        int[] iArr = this.minBytes;
        iArr[1] = 0;
        int[] iArr2 = this.maxBytes;
        iArr2[1] = 0;
        iArr[2] = 0;
        iArr2[2] = 0;
        iArr[3] = 2;
        iArr2[3] = 63;
        iArr[4] = 2;
        iArr2[4] = 63;
    }

    public boolean allocWeights(long j, long j2, int i) {
        if (!getWeightRanges(j, j2)) {
            return false;
        }
        while (true) {
            int i2 = this.ranges[0].length;
            if (allocWeightsInShortRanges(i, i2)) {
                break;
            }
            if (i2 == 4) {
                return false;
            }
            if (allocWeightsInMinLengthRanges(i, i2)) {
                break;
            }
            for (int i3 = 0; i3 < this.rangeCount && this.ranges[i3].length == i2; i3++) {
                lengthenRange(this.ranges[i3]);
            }
        }
        this.rangeIndex = 0;
        int i4 = this.rangeCount;
        WeightRange[] weightRangeArr = this.ranges;
        if (i4 >= weightRangeArr.length) {
            return true;
        }
        weightRangeArr[i4] = null;
        return true;
    }

    public long nextWeight() {
        int i = this.rangeIndex;
        if (i >= this.rangeCount) {
            return 4294967295L;
        }
        WeightRange weightRange = this.ranges[i];
        long j = weightRange.start;
        int i2 = weightRange.count - 1;
        weightRange.count = i2;
        if (i2 == 0) {
            this.rangeIndex++;
        } else {
            weightRange.start = incWeight(j, weightRange.length);
        }
        return j;
    }

    private static final class WeightRange implements Comparable<WeightRange> {
        int count;
        long end;
        int length;
        long start;

        private WeightRange() {
        }

        @Override // java.lang.Comparable
        public int compareTo(WeightRange weightRange) {
            long j = this.start;
            long j2 = weightRange.start;
            if (j < j2) {
                return -1;
            }
            return j > j2 ? 1 : 0;
        }
    }

    private static int getWeightByte(long j, int i) {
        return getWeightTrail(j, i);
    }

    private int countBytes(int i) {
        return (this.maxBytes[i] - this.minBytes[i]) + 1;
    }

    private long incWeight(long j, int i) {
        while (true) {
            int weightByte = getWeightByte(j, i);
            if (weightByte < this.maxBytes[i]) {
                return setWeightByte(j, i, weightByte + 1);
            }
            j = setWeightByte(j, i, this.minBytes[i]);
            i--;
        }
    }

    private long incWeightByOffset(long j, int i, int i2) {
        while (true) {
            int weightByte = i2 + getWeightByte(j, i);
            if (weightByte <= this.maxBytes[i]) {
                return setWeightByte(j, i, weightByte);
            }
            int[] iArr = this.minBytes;
            int i3 = weightByte - iArr[i];
            j = setWeightByte(j, i, iArr[i] + (i3 % countBytes(i)));
            i2 = i3 / countBytes(i);
            i--;
        }
    }

    private void lengthenRange(WeightRange weightRange) {
        int i = weightRange.length + 1;
        weightRange.start = setWeightTrail(weightRange.start, i, this.minBytes[i]);
        weightRange.end = setWeightTrail(weightRange.end, i, this.maxBytes[i]);
        weightRange.count *= countBytes(i);
        weightRange.length = i;
    }

    /* JADX WARN: Removed duplicated region for block: B:87:0x0146 A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:93:0x0155 A[SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    private boolean getWeightRanges(long r12, long r14) {
        /*
            Method dump skipped, instructions count: 414
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ibm.icu.impl.coll.CollationWeights.getWeightRanges(long, long):boolean");
    }

    private boolean allocWeightsInShortRanges(int i, int i2) {
        for (int i3 = 0; i3 < this.rangeCount && this.ranges[i3].length <= i2 + 1; i3++) {
            if (i <= this.ranges[i3].count) {
                if (this.ranges[i3].length > i2) {
                    this.ranges[i3].count = i;
                }
                int i4 = i3 + 1;
                this.rangeCount = i4;
                if (i4 > 1) {
                    Arrays.sort(this.ranges, 0, i4);
                }
                return true;
            }
            i -= this.ranges[i3].count;
        }
        return false;
    }

    private boolean allocWeightsInMinLengthRanges(int i, int i2) {
        int i3 = 0;
        int i4 = 0;
        while (i3 < this.rangeCount && this.ranges[i3].length == i2) {
            i4 += this.ranges[i3].count;
            i3++;
        }
        int iCountBytes = countBytes(i2 + 1);
        if (i > i4 * iCountBytes) {
            return false;
        }
        long j = this.ranges[0].start;
        long j2 = this.ranges[0].end;
        for (int i5 = 1; i5 < i3; i5++) {
            if (this.ranges[i5].start < j) {
                j = this.ranges[i5].start;
            }
            if (this.ranges[i5].end > j2) {
                j2 = this.ranges[i5].end;
            }
        }
        int i6 = (i - i4) / (iCountBytes - 1);
        int i7 = i4 - i6;
        if (i6 == 0 || (iCountBytes * i6) + i7 < i) {
            i6++;
            i7--;
        }
        this.ranges[0].start = j;
        if (i7 == 0) {
            this.ranges[0].end = j2;
            this.ranges[0].count = i4;
            lengthenRange(this.ranges[0]);
            this.rangeCount = 1;
        } else {
            this.ranges[0].end = incWeightByOffset(j, i2, i7 - 1);
            this.ranges[0].count = i7;
            WeightRange[] weightRangeArr = this.ranges;
            if (weightRangeArr[1] == null) {
                weightRangeArr[1] = new WeightRange();
            }
            WeightRange[] weightRangeArr2 = this.ranges;
            weightRangeArr2[1].start = incWeight(weightRangeArr2[0].end, i2);
            this.ranges[1].end = j2;
            this.ranges[1].length = i2;
            this.ranges[1].count = i6;
            lengthenRange(this.ranges[1]);
            this.rangeCount = 2;
        }
        return true;
    }
}
