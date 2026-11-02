package com.ibm.icu.util;

import com.google.android.gms.common.util.GmsVersion;
import com.ibm.icu.impl.CalendarCache;
import com.ibm.icu.util.ULocale;
import it.orsozoxi.android.orsonotes.R2;
import java.util.Date;
import java.util.Locale;

/* loaded from: classes2.dex */
public class HebrewCalendar extends Calendar {
    public static final int ADAR = 6;
    public static final int ADAR_1 = 5;
    public static final int AV = 11;
    private static final long BAHARAD = 12084;
    private static final long DAY_PARTS = 25920;
    public static final int ELUL = 12;
    public static final int HESHVAN = 1;
    private static final long HOUR_PARTS = 1080;
    public static final int IYAR = 8;
    public static final int KISLEV = 2;
    private static final int MONTH_DAYS = 29;
    private static final long MONTH_FRACT = 13753;
    private static final long MONTH_PARTS = 765433;
    public static final int NISAN = 7;
    public static final int SHEVAT = 4;
    public static final int SIVAN = 9;
    public static final int TAMUZ = 10;
    public static final int TEVET = 3;
    public static final int TISHRI = 0;
    private static final long serialVersionUID = -1952524560588825816L;
    private static final int[][] LIMITS = {new int[]{0, 0, 0, 0}, new int[]{-5000000, -5000000, GmsVersion.VERSION_LONGHORN, GmsVersion.VERSION_LONGHORN}, new int[]{0, 0, 12, 12}, new int[]{1, 1, 51, 56}, new int[0], new int[]{1, 1, 29, 30}, new int[]{1, 1, R2.attr.customFloatValue, R2.attr.dragDirection}, new int[0], new int[]{-1, -1, 5, 5}, new int[0], new int[0], new int[0], new int[0], new int[0], new int[0], new int[0], new int[0], new int[]{-5000000, -5000000, GmsVersion.VERSION_LONGHORN, GmsVersion.VERSION_LONGHORN}, new int[0], new int[]{-5000000, -5000000, GmsVersion.VERSION_LONGHORN, GmsVersion.VERSION_LONGHORN}, new int[0], new int[0]};
    private static final int[][] MONTH_LENGTH = {new int[]{30, 30, 30}, new int[]{29, 29, 30}, new int[]{29, 30, 30}, new int[]{29, 29, 29}, new int[]{30, 30, 30}, new int[]{30, 30, 30}, new int[]{29, 29, 29}, new int[]{30, 30, 30}, new int[]{29, 29, 29}, new int[]{30, 30, 30}, new int[]{29, 29, 29}, new int[]{30, 30, 30}, new int[]{29, 29, 29}};
    private static final int[][] MONTH_START = {new int[]{0, 0, 0}, new int[]{30, 30, 30}, new int[]{59, 59, 60}, new int[]{88, 89, 90}, new int[]{117, 118, 119}, new int[]{147, 148, 149}, new int[]{147, 148, 149}, new int[]{176, 177, 178}, new int[]{206, 207, 208}, new int[]{235, 236, 237}, new int[]{265, 266, 267}, new int[]{294, 295, 296}, new int[]{R2.attr.contentPaddingTop, R2.attr.contentScrim, R2.attr.contrast}, new int[]{R2.attr.customFloatValue, R2.attr.customIntegerValue, R2.attr.customNavigationLayout}};
    private static final int[][] LEAP_MONTH_START = {new int[]{0, 0, 0}, new int[]{30, 30, 30}, new int[]{59, 59, 60}, new int[]{88, 89, 90}, new int[]{117, 118, 119}, new int[]{147, 148, 149}, new int[]{177, 178, 179}, new int[]{206, 207, 208}, new int[]{236, 237, 238}, new int[]{265, 266, 267}, new int[]{295, 296, 297}, new int[]{R2.attr.contentPaddingTop, R2.attr.contentScrim, R2.attr.contrast}, new int[]{R2.attr.customIntegerValue, R2.attr.customNavigationLayout, R2.attr.customPixelDimension}, new int[]{383, 384, R2.attr.dragDirection}};
    private static CalendarCache cache = new CalendarCache();

    @Override // com.ibm.icu.util.Calendar
    public String getType() {
        return "hebrew";
    }

    public HebrewCalendar() {
        this(TimeZone.getDefault(), ULocale.getDefault(ULocale.Category.FORMAT));
    }

    public HebrewCalendar(TimeZone timeZone) {
        this(timeZone, ULocale.getDefault(ULocale.Category.FORMAT));
    }

    public HebrewCalendar(Locale locale) {
        this(TimeZone.forLocaleOrDefault(locale), locale);
    }

    public HebrewCalendar(ULocale uLocale) {
        this(TimeZone.forULocaleOrDefault(uLocale), uLocale);
    }

    public HebrewCalendar(TimeZone timeZone, Locale locale) {
        super(timeZone, locale);
        setTimeInMillis(System.currentTimeMillis());
    }

    public HebrewCalendar(TimeZone timeZone, ULocale uLocale) {
        super(timeZone, uLocale);
        setTimeInMillis(System.currentTimeMillis());
    }

    public HebrewCalendar(int i, int i2, int i3) {
        super(TimeZone.getDefault(), ULocale.getDefault(ULocale.Category.FORMAT));
        set(1, i);
        set(2, i2);
        set(5, i3);
    }

    public HebrewCalendar(Date date) {
        super(TimeZone.getDefault(), ULocale.getDefault(ULocale.Category.FORMAT));
        setTime(date);
    }

    public HebrewCalendar(int i, int i2, int i3, int i4, int i5, int i6) {
        super(TimeZone.getDefault(), ULocale.getDefault(ULocale.Category.FORMAT));
        set(1, i);
        set(2, i2);
        set(5, i3);
        set(11, i4);
        set(12, i5);
        set(13, i6);
    }

    @Override // com.ibm.icu.util.Calendar
    public void add(int i, int i2) {
        boolean z;
        int i3;
        if (i == 2) {
            int i4 = get(2);
            int i5 = get(1);
            if (i2 > 0) {
                z = i4 < 5;
                i3 = i4 + i2;
                while (true) {
                    if (z && i3 >= 5 && !isLeapYear(i5)) {
                        i3++;
                    }
                    if (i3 <= 12) {
                        break;
                    }
                    i3 -= 13;
                    i5++;
                    z = true;
                }
            } else {
                z = i4 > 5;
                i3 = i4 + i2;
                while (true) {
                    if (z && i3 <= 5 && !isLeapYear(i5)) {
                        i3--;
                    }
                    if (i3 >= 0) {
                        break;
                    }
                    i3 += 13;
                    i5--;
                    z = true;
                }
            }
            set(2, i3);
            set(1, i5);
            pinField(5);
            return;
        }
        super.add(i, i2);
    }

    @Override // com.ibm.icu.util.Calendar
    public void roll(int i, int i2) {
        if (i == 2) {
            int i3 = get(2);
            int i4 = get(1);
            boolean zIsLeapYear = isLeapYear(i4);
            int iMonthsInYear = (i2 % monthsInYear(i4)) + i3;
            if (!zIsLeapYear) {
                if (i2 > 0 && i3 < 5 && iMonthsInYear >= 5) {
                    iMonthsInYear++;
                } else if (i2 < 0 && i3 > 5 && iMonthsInYear <= 5) {
                    iMonthsInYear--;
                }
            }
            set(2, (iMonthsInYear + 13) % 13);
            pinField(5);
            return;
        }
        super.roll(i, i2);
    }

    private static long startOfYear(int i) {
        long j = i;
        long j2 = cache.get(j);
        if (j2 != CalendarCache.EMPTY) {
            return j2;
        }
        long jFloorDivide = (((int) floorDivide((235 * j) - 234, 19L)) * MONTH_FRACT) + BAHARAD;
        long j3 = (r0 * 29) + (jFloorDivide / DAY_PARTS);
        long j4 = jFloorDivide % DAY_PARTS;
        int i2 = (int) (j3 % 7);
        if (i2 == 2 || i2 == 4 || i2 == 6) {
            j3++;
            i2 = (int) (j3 % 7);
        }
        if (i2 == 1 && j4 > 16404 && !isLeapYear(i)) {
            j3 += 2;
        } else if (i2 == 0 && j4 > 23269 && isLeapYear(i - 1)) {
            j3++;
        }
        long j5 = j3;
        cache.put(j, j5);
        return j5;
    }

    private final int yearType(int i) {
        int iHandleGetYearLength = handleGetYearLength(i);
        if (iHandleGetYearLength > 380) {
            iHandleGetYearLength -= 30;
        }
        switch (iHandleGetYearLength) {
            case R2.attr.customFloatValue /* 353 */:
                return 0;
            case R2.attr.customIntegerValue /* 354 */:
                return 1;
            case R2.attr.customNavigationLayout /* 355 */:
                return 2;
            default:
                throw new IllegalArgumentException("Illegal year length " + iHandleGetYearLength + " in year " + i);
        }
    }

    @Deprecated
    public static boolean isLeapYear(int i) {
        int i2 = ((i * 12) + 17) % 19;
        return i2 >= (i2 < 0 ? -7 : 12);
    }

    private static int monthsInYear(int i) {
        return isLeapYear(i) ? 13 : 12;
    }

    @Override // com.ibm.icu.util.Calendar
    protected int handleGetLimit(int i, int i2) {
        return LIMITS[i][i2];
    }

    @Override // com.ibm.icu.util.Calendar
    protected int handleGetMonthLength(int i, int i2) {
        while (i2 < 0) {
            i--;
            i2 += monthsInYear(i);
        }
        while (i2 > 12) {
            i2 -= monthsInYear(i);
            i++;
        }
        if (i2 == 1 || i2 == 2) {
            return MONTH_LENGTH[i2][yearType(i)];
        }
        return MONTH_LENGTH[i2][0];
    }

    @Override // com.ibm.icu.util.Calendar
    protected int handleGetYearLength(int i) {
        return (int) (startOfYear(i + 1) - startOfYear(i));
    }

    @Override // com.ibm.icu.util.Calendar
    @Deprecated
    protected void validateField(int i) {
        if (i == 2 && !isLeapYear(handleGetExtendedYear()) && internalGet(2) == 5) {
            throw new IllegalArgumentException("MONTH cannot be ADAR_1(5) except leap years");
        }
        super.validateField(i);
    }

    @Override // com.ibm.icu.util.Calendar
    protected void handleComputeFields(int i) {
        int i2;
        long j = i - 347997;
        int iFloorDivide = (int) (floorDivide((floorDivide(DAY_PARTS * j, MONTH_PARTS) * 19) + 234, 235L) + 1);
        long jStartOfYear = startOfYear(iFloorDivide);
        while (true) {
            i2 = (int) (j - jStartOfYear);
            if (i2 >= 1) {
                break;
            }
            iFloorDivide--;
            jStartOfYear = startOfYear(iFloorDivide);
        }
        int iYearType = yearType(iFloorDivide);
        int[][] iArr = isLeapYear(iFloorDivide) ? LEAP_MONTH_START : MONTH_START;
        int i3 = 0;
        while (i2 > iArr[i3][iYearType]) {
            i3++;
        }
        int i4 = i3 - 1;
        int i5 = i2 - iArr[i4][iYearType];
        internalSet(0, 0);
        internalSet(1, iFloorDivide);
        internalSet(19, iFloorDivide);
        internalSet(2, i4);
        internalSet(5, i5);
        internalSet(6, i2);
    }

    @Override // com.ibm.icu.util.Calendar
    protected int handleGetExtendedYear() {
        if (newerField(19, 1) == 19) {
            return internalGet(19, 1);
        }
        return internalGet(1, 1);
    }

    @Override // com.ibm.icu.util.Calendar
    protected int handleComputeMonthStart(int i, int i2, boolean z) {
        int i3;
        while (i2 < 0) {
            i--;
            i2 += monthsInYear(i);
        }
        while (i2 > 12) {
            i2 -= monthsInYear(i);
            i++;
        }
        long jStartOfYear = startOfYear(i);
        if (i2 != 0) {
            if (isLeapYear(i)) {
                i3 = LEAP_MONTH_START[i2][yearType(i)];
            } else {
                i3 = MONTH_START[i2][yearType(i)];
            }
            jStartOfYear += i3;
        }
        return (int) (jStartOfYear + 347997);
    }
}
