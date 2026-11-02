package com.ibm.icu.impl.coll;

import androidx.core.view.MotionEventCompat;

/* loaded from: classes2.dex */
public final class CollationRootElements {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    static final int IX_COMMON_SEC_AND_TER_CE = 3;
    static final int IX_COUNT = 5;
    static final int IX_FIRST_PRIMARY_INDEX = 2;
    static final int IX_FIRST_SECONDARY_INDEX = 1;
    public static final int IX_FIRST_TERTIARY_INDEX = 0;
    static final int IX_SEC_TER_BOUNDARIES = 4;
    public static final long PRIMARY_SENTINEL = 4294967040L;
    public static final int PRIMARY_STEP_MASK = 127;
    public static final int SEC_TER_DELTA_FLAG = 128;
    private long[] elements;

    private static boolean isEndOfPrimaryRange(long j) {
        return (128 & j) == 0 && (j & 127) != 0;
    }

    public CollationRootElements(long[] jArr) {
        this.elements = jArr;
    }

    public int getTertiaryBoundary() {
        return (((int) this.elements[4]) << 8) & MotionEventCompat.ACTION_POINTER_INDEX_MASK;
    }

    long getFirstTertiaryCE() {
        long[] jArr = this.elements;
        return jArr[(int) jArr[0]] & (-129);
    }

    long getLastTertiaryCE() {
        long[] jArr = this.elements;
        return jArr[((int) jArr[1]) - 1] & (-129);
    }

    public int getLastCommonSecondary() {
        return (((int) this.elements[4]) >> 16) & MotionEventCompat.ACTION_POINTER_INDEX_MASK;
    }

    public int getSecondaryBoundary() {
        return (((int) this.elements[4]) >> 8) & MotionEventCompat.ACTION_POINTER_INDEX_MASK;
    }

    long getFirstSecondaryCE() {
        long[] jArr = this.elements;
        return jArr[(int) jArr[1]] & (-129);
    }

    long getLastSecondaryCE() {
        return this.elements[((int) r0[2]) - 1] & (-129);
    }

    long getFirstPrimary() {
        long[] jArr = this.elements;
        return jArr[(int) jArr[2]];
    }

    long getFirstPrimaryCE() {
        return Collation.makeCE(getFirstPrimary());
    }

    long lastCEWithPrimaryBefore(long j) {
        long j2;
        if (j == 0) {
            return 0L;
        }
        int iFindP = findP(j);
        long[] jArr = this.elements;
        long j3 = jArr[iFindP] & PRIMARY_SENTINEL;
        long j4 = 83887360;
        if (j == j3) {
            long j5 = jArr[iFindP - 1];
            if ((j5 & 128) == 0) {
                j3 = j5 & PRIMARY_SENTINEL;
            } else {
                int i = iFindP - 2;
                while (true) {
                    j2 = this.elements[i];
                    if ((j2 & 128) == 0) {
                        break;
                    }
                    i--;
                }
                j3 = j2 & PRIMARY_SENTINEL;
                j4 = j5;
            }
        } else {
            while (true) {
                iFindP++;
                long j6 = this.elements[iFindP];
                if ((j6 & 128) == 0) {
                    break;
                }
                j4 = j6;
            }
        }
        return (j3 << 32) | ((-129) & j4);
    }

    long firstCEWithPrimaryAtLeast(long j) {
        long j2;
        if (j == 0) {
            return 0L;
        }
        int iFindP = findP(j);
        if (j != (this.elements[iFindP] & PRIMARY_SENTINEL)) {
            do {
                iFindP++;
                j2 = this.elements[iFindP];
            } while ((128 & j2) != 0);
            j = j2;
        }
        return (j << 32) | 83887360;
    }

    long getPrimaryBefore(long j, boolean z) {
        int i;
        long j2;
        int iFindPrimary = findPrimary(j);
        long[] jArr = this.elements;
        long j3 = jArr[iFindPrimary];
        if (j == (j3 & PRIMARY_SENTINEL)) {
            i = ((int) j3) & 127;
            if (i == 0) {
                do {
                    iFindPrimary--;
                    j2 = this.elements[iFindPrimary];
                } while ((128 & j2) != 0);
                return j2 & PRIMARY_SENTINEL;
            }
        } else {
            i = ((int) jArr[iFindPrimary + 1]) & 127;
        }
        if ((65535 & j) == 0) {
            return Collation.decTwoBytePrimaryByOneStep(j, z, i);
        }
        return Collation.decThreeBytePrimaryByOneStep(j, z, i);
    }

    int getSecondaryBefore(long j, int i) {
        int iFindPrimary;
        int i2;
        int firstSecTerForPrimary;
        if (j == 0) {
            long[] jArr = this.elements;
            iFindPrimary = (int) jArr[1];
            i2 = 0;
            firstSecTerForPrimary = (int) (jArr[iFindPrimary] >> 16);
        } else {
            iFindPrimary = findPrimary(j) + 1;
            i2 = 256;
            firstSecTerForPrimary = ((int) getFirstSecTerForPrimary(iFindPrimary)) >>> 16;
        }
        while (i > firstSecTerForPrimary) {
            int i3 = iFindPrimary + 1;
            i2 = firstSecTerForPrimary;
            firstSecTerForPrimary = (int) (this.elements[iFindPrimary] >> 16);
            iFindPrimary = i3;
        }
        return i2;
    }

    int getTertiaryBefore(long j, int i, int i2) {
        int iFindPrimary;
        long firstSecTerForPrimary;
        int i3 = 256;
        if (j == 0) {
            if (i == 0) {
                iFindPrimary = (int) this.elements[0];
                i3 = 0;
            } else {
                iFindPrimary = (int) this.elements[1];
            }
            firstSecTerForPrimary = this.elements[iFindPrimary] & (-129);
        } else {
            iFindPrimary = findPrimary(j) + 1;
            firstSecTerForPrimary = getFirstSecTerForPrimary(iFindPrimary);
        }
        long j2 = (i << 16) | i2;
        while (j2 > firstSecTerForPrimary) {
            if (((int) (firstSecTerForPrimary >> 16)) == i) {
                i3 = (int) firstSecTerForPrimary;
            }
            long j3 = this.elements[iFindPrimary] & (-129);
            iFindPrimary++;
            firstSecTerForPrimary = j3;
        }
        return 65535 & i3;
    }

    int findPrimary(long j) {
        return findP(j);
    }

    long getPrimaryAfter(long j, int i, boolean z) {
        int i2;
        int i3 = i + 1;
        long j2 = this.elements[i3];
        if ((j2 & 128) == 0 && (i2 = ((int) j2) & 127) != 0) {
            if ((65535 & j) == 0) {
                return Collation.incTwoBytePrimaryByOffset(j, z, i2);
            }
            return Collation.incThreeBytePrimaryByOffset(j, z, i2);
        }
        while ((j2 & 128) != 0) {
            i3++;
            j2 = this.elements[i3];
        }
        return j2;
    }

    int getSecondaryAfter(int i, int i2) {
        long firstSecTerForPrimary;
        int secondaryBoundary;
        if (i == 0) {
            long[] jArr = this.elements;
            int i3 = (int) jArr[1];
            firstSecTerForPrimary = jArr[i3];
            i = i3;
            secondaryBoundary = 65536;
        } else {
            firstSecTerForPrimary = getFirstSecTerForPrimary(i + 1);
            secondaryBoundary = getSecondaryBoundary();
        }
        do {
            int i4 = (int) (firstSecTerForPrimary >> 16);
            if (i4 > i2) {
                return i4;
            }
            i++;
            firstSecTerForPrimary = this.elements[i];
        } while ((128 & firstSecTerForPrimary) != 0);
        return secondaryBoundary;
    }

    int getTertiaryAfter(int i, int i2, int i3) {
        long firstSecTerForPrimary;
        int tertiaryBoundary;
        int i4;
        if (i == 0) {
            if (i2 == 0) {
                i4 = (int) this.elements[0];
                tertiaryBoundary = 16384;
            } else {
                i4 = (int) this.elements[1];
                tertiaryBoundary = getTertiaryBoundary();
            }
            firstSecTerForPrimary = this.elements[i4] & (-129);
        } else {
            firstSecTerForPrimary = getFirstSecTerForPrimary(i + 1);
            tertiaryBoundary = getTertiaryBoundary();
            i4 = i;
        }
        long j = i2;
        long j2 = ((4294967295L & j) << 16) | i3;
        while (firstSecTerForPrimary <= j2) {
            i4++;
            long j3 = this.elements[i4];
            if ((128 & j3) == 0 || (j3 >> 16) > j) {
                return tertiaryBoundary;
            }
            firstSecTerForPrimary = j3 & (-129);
        }
        return ((int) firstSecTerForPrimary) & 65535;
    }

    private long getFirstSecTerForPrimary(int i) {
        long j = this.elements[i];
        if ((128 & j) == 0) {
            return 83887360L;
        }
        long j2 = j & (-129);
        if (j2 > 83887360) {
            return 83887360L;
        }
        return j2;
    }

    private int findP(long j) {
        long[] jArr = this.elements;
        int i = (int) jArr[2];
        int length = jArr.length - 1;
        while (i + 1 < length) {
            int i2 = (int) ((i + length) / 2);
            long j2 = this.elements[i2];
            if ((j2 & 128) != 0) {
                int i3 = i2 + 1;
                while (true) {
                    if (i3 == length) {
                        break;
                    }
                    long j3 = this.elements[i3];
                    if ((j3 & 128) == 0) {
                        i2 = i3;
                        j2 = j3;
                        break;
                    }
                    i3++;
                    j2 = j3;
                }
                if ((j2 & 128) != 0) {
                    int i4 = i2 - 1;
                    while (true) {
                        if (i4 == i) {
                            break;
                        }
                        long j4 = this.elements[i4];
                        if ((j4 & 128) == 0) {
                            i2 = i4;
                            j2 = j4;
                            break;
                        }
                        i4--;
                        j2 = j4;
                    }
                    if ((128 & j2) != 0) {
                        break;
                    }
                }
            }
            if (j < (j2 & PRIMARY_SENTINEL)) {
                length = i2;
            } else {
                i = i2;
            }
        }
        return i;
    }
}
