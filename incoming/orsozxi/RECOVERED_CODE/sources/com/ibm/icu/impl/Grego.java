package com.ibm.icu.impl;

import it.orsozoxi.android.orsonotes.R2;
import java.util.Locale;

/* loaded from: classes2.dex */
public class Grego {
    private static final int JULIAN_1970_CE = 2440588;
    private static final int JULIAN_1_CE = 1721426;
    public static final long MAX_MILLIS = 183882168921600000L;
    public static final int MILLIS_PER_DAY = 86400000;
    public static final int MILLIS_PER_HOUR = 3600000;
    public static final int MILLIS_PER_MINUTE = 60000;
    public static final int MILLIS_PER_SECOND = 1000;
    public static final long MIN_MILLIS = -184303902528000000L;
    private static final int[] MONTH_LENGTH = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31, 31, 29, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
    private static final int[] DAYS_BEFORE = {0, 31, 59, 90, 120, 151, 181, 212, 243, 273, 304, R2.attr.cornerRadius, 0, 31, 60, 91, 121, 152, 182, 213, 244, 274, 305, R2.attr.cornerSize};

    public static final boolean isLeapYear(int i) {
        return (i & 3) == 0 && (i % 100 != 0 || i % 400 == 0);
    }

    public static final int monthLength(int i, int i2) {
        return MONTH_LENGTH[i2 + (isLeapYear(i) ? 12 : 0)];
    }

    public static final int previousMonthLength(int i, int i2) {
        if (i2 > 0) {
            return monthLength(i, i2 - 1);
        }
        return 31;
    }

    public static long fieldsToDay(int i, int i2, int i3) {
        long j = i - 1;
        return ((((((((r0 * R2.attr.defaultValue) + floorDivide(j, 4L)) + 1721423) + floorDivide(j, 400L)) - floorDivide(j, 100L)) + 2) + DAYS_BEFORE[i2 + (isLeapYear(i) ? 12 : 0)]) + i3) - 2440588;
    }

    public static int dayOfWeek(long j) {
        long[] jArr = new long[1];
        floorDivide(j + 5, 7L, jArr);
        int i = (int) jArr[0];
        if (i == 0) {
            return 7;
        }
        return i;
    }

    public static int[] dayToFields(long j, int[] iArr) {
        if (iArr == null || iArr.length < 5) {
            iArr = new int[5];
        }
        long j2 = j + 719162;
        long[] jArr = new long[1];
        long jFloorDivide = floorDivide(j2, 146097L, jArr);
        long jFloorDivide2 = floorDivide(jArr[0], 36524L, jArr);
        long jFloorDivide3 = floorDivide(jArr[0], 1461L, jArr);
        long jFloorDivide4 = floorDivide(jArr[0], 365L, jArr);
        int i = (int) ((jFloorDivide * 400) + (100 * jFloorDivide2) + (jFloorDivide3 * 4) + jFloorDivide4);
        int i2 = (int) jArr[0];
        if (jFloorDivide2 == 4 || jFloorDivide4 == 4) {
            i2 = R2.attr.defaultValue;
        } else {
            i++;
        }
        boolean zIsLeapYear = isLeapYear(i);
        int i3 = ((((i2 >= (zIsLeapYear ? 60 : 59) ? zIsLeapYear ? 1 : 2 : 0) + i2) * 12) + 6) / R2.attr.deltaPolarRadius;
        int i4 = (i2 - DAYS_BEFORE[zIsLeapYear ? i3 + 12 : i3]) + 1;
        int i5 = (int) ((j2 + 2) % 7);
        if (i5 < 1) {
            i5 += 7;
        }
        iArr[0] = i;
        iArr[1] = i3;
        iArr[2] = i4;
        iArr[3] = i5;
        iArr[4] = i2 + 1;
        return iArr;
    }

    public static int[] timeToFields(long j, int[] iArr) {
        if (iArr == null || iArr.length < 6) {
            iArr = new int[6];
        }
        long[] jArr = new long[1];
        dayToFields(floorDivide(j, 86400000L, jArr), iArr);
        iArr[5] = (int) jArr[0];
        return iArr;
    }

    public static long floorDivide(long j, long j2) {
        return j >= 0 ? j / j2 : ((j + 1) / j2) - 1;
    }

    private static long floorDivide(long j, long j2, long[] jArr) {
        if (j >= 0) {
            jArr[0] = j % j2;
            return j / j2;
        }
        long j3 = ((j + 1) / j2) - 1;
        jArr[0] = j - (j2 * j3);
        return j3;
    }

    public static int getDayOfWeekInMonth(int i, int i2, int i3) {
        int i4 = (i3 + 6) / 7;
        if (i4 == 4) {
            if (i3 + 7 <= monthLength(i, i2)) {
                return i4;
            }
        } else if (i4 != 5) {
            return i4;
        }
        return -1;
    }

    public static String timeToString(long j) {
        int[] iArrTimeToFields = timeToFields(j, null);
        int i = iArrTimeToFields[5];
        int i2 = i / 3600000;
        int i3 = i % 3600000;
        int i4 = i3 / 60000;
        int i5 = i3 % 60000;
        return String.format((Locale) null, "%04d-%02d-%02dT%02d:%02d:%02d.%03dZ", Integer.valueOf(iArrTimeToFields[0]), Integer.valueOf(iArrTimeToFields[1] + 1), Integer.valueOf(iArrTimeToFields[2]), Integer.valueOf(i2), Integer.valueOf(i4), Integer.valueOf(i5 / 1000), Integer.valueOf(i5 % 1000));
    }
}
