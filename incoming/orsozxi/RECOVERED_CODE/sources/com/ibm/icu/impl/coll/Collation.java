package com.ibm.icu.impl.coll;

import androidx.core.view.InputDeviceCompat;
import androidx.core.view.MotionEventCompat;

/* loaded from: classes2.dex */
public final class Collation {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    static final int BEFORE_WEIGHT16 = 256;
    static final int BUILDER_DATA_TAG = 7;
    public static final int CASE_AND_QUATERNARY_MASK = 49344;
    static final int CASE_AND_TERTIARY_MASK = 65343;
    public static final int CASE_LEVEL = 3;
    static final int CASE_LEVEL_FLAG = 8;
    public static final int CASE_MASK = 49152;
    static final int COMMON_BYTE = 5;
    static final int COMMON_SECONDARY_CE = 83886080;
    public static final int COMMON_SEC_AND_TER_CE = 83887360;
    static final int COMMON_TERTIARY_CE = 1280;
    public static final int COMMON_WEIGHT16 = 1280;
    static final int CONTRACTION_TAG = 9;
    static final int CONTRACT_NEXT_CCC = 512;
    static final int CONTRACT_SINGLE_CP_NO_MATCH = 256;
    static final int CONTRACT_TRAILING_CCC = 1024;
    static final int DIGIT_TAG = 10;
    public static final int EQUAL = 0;
    static final int EXPANSION32_TAG = 5;
    static final int EXPANSION_TAG = 6;
    static final int FALLBACK_CE32 = 192;
    static final int FALLBACK_TAG = 0;
    static final int FFFD_CE32 = -195323;
    public static final long FFFD_PRIMARY = 4294770688L;
    static final long FIRST_TRAILING_PRIMARY = 4278321664L;
    static final long FIRST_UNASSIGNED_PRIMARY = 4261675520L;
    public static final int GREATER = 1;
    static final int HANGUL_NO_SPECIAL_JAMO = 256;
    static final int HANGUL_TAG = 12;
    public static final int IDENTICAL_LEVEL = 6;
    static final int IDENTICAL_LEVEL_FLAG = 64;
    static final int IMPLICIT_TAG = 15;
    static final int LATIN_EXPANSION_TAG = 4;
    static final int LEAD_ALL_FALLBACK = 256;
    static final int LEAD_ALL_UNASSIGNED = 0;
    static final int LEAD_MIXED = 512;
    static final int LEAD_SURROGATE_TAG = 13;
    static final int LEAD_TYPE_MASK = 768;
    public static final int LESS = -1;
    public static final int LEVEL_SEPARATOR_BYTE = 1;
    static final int LONG_PRIMARY_CE32_LOW_BYTE = 193;
    static final int LONG_PRIMARY_TAG = 1;
    static final int LONG_SECONDARY_TAG = 2;
    static final int MAX_EXPANSION_LENGTH = 31;
    static final int MAX_INDEX = 524287;
    public static final long MAX_PRIMARY = 4294901760L;
    static final int MAX_REGULAR_CE32 = -64251;
    public static final int MERGE_SEPARATOR_BYTE = 2;
    static final int MERGE_SEPARATOR_CE32 = 33555717;
    public static final long MERGE_SEPARATOR_PRIMARY = 33554432;
    public static final long NO_CE = 4311744768L;
    static final int NO_CE32 = 1;
    static final long NO_CE_PRIMARY = 1;
    static final int NO_CE_WEIGHT16 = 256;
    public static final int NO_LEVEL = 0;
    static final int NO_LEVEL_FLAG = 1;
    static final int OFFSET_TAG = 14;
    static final int ONLY_SEC_TER_MASK = -49345;
    public static final int ONLY_TERTIARY_MASK = 16191;
    static final int PREFIX_TAG = 8;
    public static final int PRIMARY_COMPRESSION_HIGH_BYTE = 255;
    public static final int PRIMARY_COMPRESSION_LOW_BYTE = 3;
    public static final int PRIMARY_LEVEL = 1;
    static final int PRIMARY_LEVEL_FLAG = 2;
    public static final int QUATERNARY_LEVEL = 5;
    static final int QUATERNARY_LEVEL_FLAG = 32;
    public static final int QUATERNARY_MASK = 192;
    static final int RESERVED_TAG_3 = 3;
    static final int SECONDARY_AND_CASE_MASK = -16384;
    public static final int SECONDARY_LEVEL = 2;
    static final int SECONDARY_LEVEL_FLAG = 4;
    static final int SECONDARY_MASK = -65536;
    public static final int SENTINEL_CP = -1;
    static final int SPECIAL_CE32_LOW_BYTE = 192;
    public static final int TERMINATOR_BYTE = 0;
    public static final int TERTIARY_LEVEL = 4;
    static final int TERTIARY_LEVEL_FLAG = 16;
    static final int TRAIL_WEIGHT_BYTE = 255;
    static final int U0000_TAG = 11;
    static final int UNASSIGNED_CE32 = -1;
    static final int UNASSIGNED_IMPLICIT_BYTE = 254;
    public static final int ZERO_LEVEL = 7;
    static final int ZERO_LEVEL_FLAG = 128;

    static long ceFromCE32(int i) {
        int i2 = i & 255;
        if (i2 < 192) {
            return (((-65536) & i) << 32) | ((i & MotionEventCompat.ACTION_POINTER_INDEX_MASK) << 16) | (i2 << 8);
        }
        int i3 = i - i2;
        return (i2 & 15) == 1 ? (i3 << 32) | 83887360 : i3 & 4294967295L;
    }

    static long ceFromLongPrimaryCE32(int i) {
        return ((i & InputDeviceCompat.SOURCE_ANY) << 32) | 83887360;
    }

    static long ceFromLongSecondaryCE32(int i) {
        return i & CollationRootElements.PRIMARY_SENTINEL;
    }

    static long ceFromSimpleCE32(int i) {
        return (((-65536) & i) << 32) | ((65280 & i) << 16) | ((i & 255) << 8);
    }

    /* JADX WARN: Removed duplicated region for block: B:14:0x0033  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    static long decThreeBytePrimaryByOneStep(long r8, boolean r10, int r11) {
        /*
            r0 = 8
            long r1 = r8 >> r0
            int r2 = (int) r1
            r1 = 255(0xff, float:3.57E-43)
            r2 = r2 & r1
            int r2 = r2 - r11
            r11 = 2
            if (r2 < r11) goto L17
            r10 = 4294901760(0xffff0000, double:2.121963412E-314)
            long r8 = r8 & r10
        L12:
            int r10 = r2 << 8
            long r10 = (long) r10
            long r8 = r8 | r10
            return r8
        L17:
            r3 = 254(0xfe, float:3.56E-43)
            int r2 = r2 + r3
            r4 = 16
            long r5 = r8 >> r4
            int r6 = (int) r5
            r5 = r6 & 255(0xff, float:3.57E-43)
            int r5 = r5 + (-1)
            r6 = 16777216(0x1000000, double:8.289046E-317)
            if (r10 == 0) goto L2f
            r10 = 4
            if (r5 >= r10) goto L33
            long r8 = r8 - r6
            r1 = 254(0xfe, float:3.56E-43)
            goto L34
        L2f:
            if (r5 >= r11) goto L33
            long r8 = r8 - r6
            goto L34
        L33:
            r1 = r5
        L34:
            r10 = 4278190080(0xff000000, double:2.113706745E-314)
            long r8 = r8 & r10
            int r10 = r1 << 16
            long r10 = (long) r10
            long r8 = r8 | r10
            goto L12
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ibm.icu.impl.coll.Collation.decThreeBytePrimaryByOneStep(long, boolean, int):long");
    }

    static long decTwoBytePrimaryByOneStep(long j, boolean z, int i) {
        int i2 = (((int) (j >> 16)) & 255) - i;
        if (z) {
            if (i2 < 4) {
                i2 += 251;
                j -= 16777216;
            }
        } else if (i2 < 2) {
            i2 += 254;
            j -= 16777216;
        }
        return (j & 4278190080L) | (i2 << 16);
    }

    static char digitFromCE32(int i) {
        return (char) ((i >> 8) & 15);
    }

    static int indexFromCE32(int i) {
        return i >>> 13;
    }

    static boolean isAssignedCE32(int i) {
        return (i == 192 || i == -1) ? false : true;
    }

    static boolean isSpecialCE32(int i) {
        return (i & 255) >= 192;
    }

    static long latinCE0FromCE32(int i) {
        return (((-16777216) & i) << 32) | 83886080 | ((i & 16711680) >> 8);
    }

    static long latinCE1FromCE32(int i) {
        return ((i & 65280) << 16) | 1280;
    }

    static int lengthFromCE32(int i) {
        return (i >> 8) & 31;
    }

    public static long makeCE(long j) {
        return (j << 32) | 83887360;
    }

    static long makeCE(long j, int i, int i2, int i3) {
        return (j << 32) | (i << 16) | i2 | (i3 << 6);
    }

    static int makeCE32FromTagAndIndex(int i, int i2) {
        return i | (i2 << 13) | 192;
    }

    static int makeCE32FromTagIndexAndLength(int i, int i2, int i3) {
        return i | (i2 << 13) | (i3 << 8) | 192;
    }

    static int makeLongPrimaryCE32(long j) {
        return (int) (j | 193);
    }

    static int makeLongSecondaryCE32(int i) {
        return i | 192 | 2;
    }

    static long primaryFromLongPrimaryCE32(int i) {
        return i & CollationRootElements.PRIMARY_SENTINEL;
    }

    static int tagFromCE32(int i) {
        return i & 15;
    }

    static boolean hasCE32Tag(int i, int i2) {
        return isSpecialCE32(i) && tagFromCE32(i) == i2;
    }

    static boolean isLongPrimaryCE32(int i) {
        return hasCE32Tag(i, 1);
    }

    static boolean isSimpleOrLongCE32(int i) {
        return !isSpecialCE32(i) || tagFromCE32(i) == 1 || tagFromCE32(i) == 2;
    }

    static boolean isSelfContainedCE32(int i) {
        return !isSpecialCE32(i) || tagFromCE32(i) == 1 || tagFromCE32(i) == 2 || tagFromCE32(i) == 4;
    }

    static boolean isPrefixCE32(int i) {
        return hasCE32Tag(i, 8);
    }

    static boolean isContractionCE32(int i) {
        return hasCE32Tag(i, 9);
    }

    static boolean ce32HasContext(int i) {
        return isSpecialCE32(i) && (tagFromCE32(i) == 8 || tagFromCE32(i) == 9);
    }

    public static long incTwoBytePrimaryByOffset(long j, boolean z, int i) {
        long j2;
        int i2;
        if (z) {
            int i3 = i + ((((int) (j >> 16)) & 255) - 4);
            j2 = ((i3 % 251) + 4) << 16;
            i2 = i3 / 251;
        } else {
            int i4 = i + ((((int) (j >> 16)) & 255) - 2);
            j2 = ((i4 % 254) + 2) << 16;
            i2 = i4 / 254;
        }
        return ((j & 4278190080L) + (i2 << 24)) | j2;
    }

    public static long incThreeBytePrimaryByOffset(long j, boolean z, int i) {
        long j2;
        int i2;
        int i3 = i + ((((int) (j >> 8)) & 255) - 2);
        long j3 = ((i3 % 254) + 2) << 8;
        int i4 = i3 / 254;
        if (z) {
            j2 = j3 | (((r8 % 251) + 4) << 16);
            i2 = (i4 + ((((int) (j >> 16)) & 255) - 4)) / 251;
        } else {
            j2 = j3 | (((r8 % 254) + 2) << 16);
            i2 = (i4 + ((((int) (j >> 16)) & 255) - 2)) / 254;
        }
        return ((j & 4278190080L) + (i2 << 24)) | j2;
    }

    static long getThreeBytePrimaryForOffsetData(int i, long j) {
        int i2 = (int) j;
        return incThreeBytePrimaryByOffset(j >>> 32, (i2 & 128) != 0, (i - (i2 >> 8)) * (i2 & 127));
    }

    static long unassignedPrimaryFromCodePoint(int i) {
        int i2 = i + 1;
        long j = ((i2 % 18) * 14) + 2;
        int i3 = i2 / 18;
        return j | (((i3 % 254) + 2) << 8) | ((((i3 / 254) % 251) + 4) << 16) | 4261412864L;
    }

    static long unassignedCEFromCodePoint(int i) {
        return makeCE(unassignedPrimaryFromCodePoint(i));
    }
}
