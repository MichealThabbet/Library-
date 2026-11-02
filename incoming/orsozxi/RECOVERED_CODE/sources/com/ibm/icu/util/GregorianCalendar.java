package com.ibm.icu.util;

import com.ibm.icu.impl.Grego;
import com.ibm.icu.util.ULocale;
import it.orsozoxi.android.orsonotes.R2;
import java.util.Date;
import java.util.Locale;

/* loaded from: classes2.dex */
public class GregorianCalendar extends Calendar {
    public static final int AD = 1;
    public static final int BC = 0;
    private static final int EPOCH_YEAR = 1970;
    private static final long serialVersionUID = 9199388694351062137L;
    private transient int cutoverJulianDay;
    private long gregorianCutover;
    private transient int gregorianCutoverYear;
    protected transient boolean invertGregorian;
    protected transient boolean isGregorian;
    private static final int[][] MONTH_COUNT = {new int[]{31, 31, 0, 0}, new int[]{28, 29, 31, 31}, new int[]{31, 31, 59, 60}, new int[]{30, 30, 90, 91}, new int[]{31, 31, 120, 121}, new int[]{30, 30, 151, 152}, new int[]{31, 31, 181, 182}, new int[]{31, 31, 212, 213}, new int[]{30, 30, 243, 244}, new int[]{31, 31, 273, 274}, new int[]{30, 30, 304, 305}, new int[]{31, 31, R2.attr.cornerRadius, R2.attr.cornerSize}};
    private static final int[][] LIMITS = {new int[]{0, 0, 1, 1}, new int[]{1, 1, 5828963, 5838270}, new int[]{0, 0, 11, 11}, new int[]{1, 1, 52, 53}, new int[0], new int[]{1, 1, 28, 31}, new int[]{1, 1, R2.attr.defaultValue, 366}, new int[0], new int[]{-1, -1, 4, 5}, new int[0], new int[0], new int[0], new int[0], new int[0], new int[0], new int[0], new int[0], new int[]{-5838270, -5838270, 5828964, 5838271}, new int[0], new int[]{-5838269, -5838269, 5828963, 5838270}, new int[0], new int[0], new int[0]};

    @Override // com.ibm.icu.util.Calendar
    public String getType() {
        return "gregorian";
    }

    @Override // com.ibm.icu.util.Calendar
    protected int handleGetLimit(int i, int i2) {
        return LIMITS[i][i2];
    }

    public GregorianCalendar() {
        this(TimeZone.getDefault(), ULocale.getDefault(ULocale.Category.FORMAT));
    }

    public GregorianCalendar(TimeZone timeZone) {
        this(timeZone, ULocale.getDefault(ULocale.Category.FORMAT));
    }

    public GregorianCalendar(Locale locale) {
        this(TimeZone.forLocaleOrDefault(locale), locale);
    }

    public GregorianCalendar(ULocale uLocale) {
        this(TimeZone.forULocaleOrDefault(uLocale), uLocale);
    }

    public GregorianCalendar(TimeZone timeZone, Locale locale) {
        super(timeZone, locale);
        this.gregorianCutover = -12219292800000L;
        this.cutoverJulianDay = 2299161;
        this.gregorianCutoverYear = R2.dimen.abc_text_size_subtitle_material_toolbar;
        setTimeInMillis(System.currentTimeMillis());
    }

    public GregorianCalendar(TimeZone timeZone, ULocale uLocale) {
        super(timeZone, uLocale);
        this.gregorianCutover = -12219292800000L;
        this.cutoverJulianDay = 2299161;
        this.gregorianCutoverYear = R2.dimen.abc_text_size_subtitle_material_toolbar;
        setTimeInMillis(System.currentTimeMillis());
    }

    public GregorianCalendar(int i, int i2, int i3) {
        super(TimeZone.getDefault(), ULocale.getDefault(ULocale.Category.FORMAT));
        this.gregorianCutover = -12219292800000L;
        this.cutoverJulianDay = 2299161;
        this.gregorianCutoverYear = R2.dimen.abc_text_size_subtitle_material_toolbar;
        set(0, 1);
        set(1, i);
        set(2, i2);
        set(5, i3);
    }

    public GregorianCalendar(int i, int i2, int i3, int i4, int i5) {
        super(TimeZone.getDefault(), ULocale.getDefault(ULocale.Category.FORMAT));
        this.gregorianCutover = -12219292800000L;
        this.cutoverJulianDay = 2299161;
        this.gregorianCutoverYear = R2.dimen.abc_text_size_subtitle_material_toolbar;
        set(0, 1);
        set(1, i);
        set(2, i2);
        set(5, i3);
        set(11, i4);
        set(12, i5);
    }

    public GregorianCalendar(int i, int i2, int i3, int i4, int i5, int i6) {
        super(TimeZone.getDefault(), ULocale.getDefault(ULocale.Category.FORMAT));
        this.gregorianCutover = -12219292800000L;
        this.cutoverJulianDay = 2299161;
        this.gregorianCutoverYear = R2.dimen.abc_text_size_subtitle_material_toolbar;
        set(0, 1);
        set(1, i);
        set(2, i2);
        set(5, i3);
        set(11, i4);
        set(12, i5);
        set(13, i6);
    }

    public void setGregorianChange(Date date) {
        long time = date.getTime();
        this.gregorianCutover = time;
        if (time <= Grego.MIN_MILLIS) {
            this.cutoverJulianDay = Integer.MIN_VALUE;
            this.gregorianCutoverYear = Integer.MIN_VALUE;
        } else if (time >= Grego.MAX_MILLIS) {
            this.cutoverJulianDay = Integer.MAX_VALUE;
            this.gregorianCutoverYear = Integer.MAX_VALUE;
        } else {
            this.cutoverJulianDay = (int) floorDivide(time, 86400000L);
            GregorianCalendar gregorianCalendar = new GregorianCalendar(getTimeZone());
            gregorianCalendar.setTime(date);
            this.gregorianCutoverYear = gregorianCalendar.get(19);
        }
    }

    public final Date getGregorianChange() {
        return new Date(this.gregorianCutover);
    }

    public boolean isLeapYear(int i) {
        if (i >= this.gregorianCutoverYear) {
            if (i % 4 == 0 && (i % 100 != 0 || i % 400 == 0)) {
                return true;
            }
        } else if (i % 4 == 0) {
            return true;
        }
        return false;
    }

    @Override // com.ibm.icu.util.Calendar
    public boolean isEquivalentTo(Calendar calendar) {
        return super.isEquivalentTo(calendar) && this.gregorianCutover == ((GregorianCalendar) calendar).gregorianCutover;
    }

    @Override // com.ibm.icu.util.Calendar
    public int hashCode() {
        return super.hashCode() ^ ((int) this.gregorianCutover);
    }

    @Override // com.ibm.icu.util.Calendar
    public void roll(int i, int i2) {
        if (i == 3) {
            int i3 = get(3);
            int i4 = get(17);
            int iInternalGet = internalGet(6);
            if (internalGet(2) == 0) {
                if (i3 >= 52) {
                    iInternalGet += handleGetYearLength(i4);
                }
            } else if (i3 == 1) {
                iInternalGet -= handleGetYearLength(i4 - 1);
            }
            int i5 = i3 + i2;
            if (i5 < 1 || i5 > 52) {
                int iHandleGetYearLength = handleGetYearLength(i4);
                int iInternalGet2 = (((iHandleGetYearLength - iInternalGet) + internalGet(7)) - getFirstDayOfWeek()) % 7;
                if (iInternalGet2 < 0) {
                    iInternalGet2 += 7;
                }
                if (6 - iInternalGet2 >= getMinimalDaysInFirstWeek()) {
                    iHandleGetYearLength -= 7;
                }
                int iWeekNumber = weekNumber(iHandleGetYearLength, iInternalGet2 + 1);
                i5 = (((i5 + iWeekNumber) - 1) % iWeekNumber) + 1;
            }
            set(3, i5);
            set(1, i4);
            return;
        }
        super.roll(i, i2);
    }

    @Override // com.ibm.icu.util.Calendar
    public int getActualMinimum(int i) {
        return getMinimum(i);
    }

    @Override // com.ibm.icu.util.Calendar
    public int getActualMaximum(int i) {
        if (i == 1) {
            Calendar calendar = (Calendar) clone();
            calendar.setLenient(true);
            int i2 = calendar.get(0);
            Date time = calendar.getTime();
            int[][] iArr = LIMITS;
            int i3 = iArr[1][1];
            int i4 = iArr[1][2] + 1;
            while (i3 + 1 < i4) {
                int i5 = (i3 + i4) / 2;
                calendar.set(1, i5);
                if (calendar.get(1) == i5 && calendar.get(0) == i2) {
                    i3 = i5;
                } else {
                    calendar.setTime(time);
                    i4 = i5;
                }
            }
            return i3;
        }
        return super.getActualMaximum(i);
    }

    boolean inDaylightTime() {
        if (!getTimeZone().useDaylightTime()) {
            return false;
        }
        complete();
        return internalGet(16) != 0;
    }

    @Override // com.ibm.icu.util.Calendar
    protected int handleGetMonthLength(int i, int i2) {
        if (i2 < 0 || i2 > 11) {
            int[] iArr = new int[1];
            i += floorDivide(i2, 12, iArr);
            i2 = iArr[0];
        }
        return MONTH_COUNT[i2][isLeapYear(i) ? 1 : 0];
    }

    @Override // com.ibm.icu.util.Calendar
    protected int handleGetYearLength(int i) {
        if (isLeapYear(i)) {
            return 366;
        }
        return R2.attr.defaultValue;
    }

    @Override // com.ibm.icu.util.Calendar
    protected void handleComputeFields(int i) {
        int gregorianYear;
        int gregorianDayOfMonth;
        int gregorianMonth;
        int gregorianDayOfYear;
        int i2;
        if (i >= this.cutoverJulianDay) {
            gregorianMonth = getGregorianMonth();
            gregorianDayOfMonth = getGregorianDayOfMonth();
            gregorianDayOfYear = getGregorianDayOfYear();
            gregorianYear = getGregorianYear();
        } else {
            long j = i - 1721424;
            int iFloorDivide = (int) floorDivide((j * 4) + 1464, 1461L);
            long j2 = iFloorDivide - 1;
            int iFloorDivide2 = (int) (j - ((365 * j2) + floorDivide(j2, 4L)));
            boolean z = (iFloorDivide & 3) == 0;
            int i3 = ((((iFloorDivide2 >= (z ? 60 : 59) ? z ? 1 : 2 : 0) + iFloorDivide2) * 12) + 6) / R2.attr.deltaPolarRadius;
            int i4 = (iFloorDivide2 - MONTH_COUNT[i3][z ? (char) 3 : (char) 2]) + 1;
            int i5 = iFloorDivide2 + 1;
            gregorianYear = iFloorDivide;
            gregorianDayOfMonth = i4;
            gregorianMonth = i3;
            gregorianDayOfYear = i5;
        }
        internalSet(2, gregorianMonth);
        internalSet(5, gregorianDayOfMonth);
        internalSet(6, gregorianDayOfYear);
        internalSet(19, gregorianYear);
        if (gregorianYear < 1) {
            gregorianYear = 1 - gregorianYear;
            i2 = 0;
        } else {
            i2 = 1;
        }
        internalSet(0, i2);
        internalSet(1, gregorianYear);
    }

    @Override // com.ibm.icu.util.Calendar
    protected int handleGetExtendedYear() {
        if (newerField(19, 1) == 19) {
            return internalGet(19, 1970);
        }
        if (internalGet(0, 1) == 0) {
            return 1 - internalGet(1, 1);
        }
        return internalGet(1, 1970);
    }

    @Override // com.ibm.icu.util.Calendar
    protected int handleComputeJulianDay(int i) {
        this.invertGregorian = false;
        int iHandleComputeJulianDay = super.handleComputeJulianDay(i);
        if (this.isGregorian == (iHandleComputeJulianDay >= this.cutoverJulianDay)) {
            return iHandleComputeJulianDay;
        }
        this.invertGregorian = true;
        return super.handleComputeJulianDay(i);
    }

    @Override // com.ibm.icu.util.Calendar
    protected int handleComputeMonthStart(int i, int i2, boolean z) {
        boolean z2 = false;
        if (i2 < 0 || i2 > 11) {
            int[] iArr = new int[1];
            i += floorDivide(i2, 12, iArr);
            i2 = iArr[0];
        }
        boolean z3 = i % 4 == 0;
        int i3 = i - 1;
        int iFloorDivide = (i3 * R2.attr.defaultValue) + floorDivide(i3, 4) + 1721423;
        boolean z4 = i >= this.gregorianCutoverYear;
        this.isGregorian = z4;
        if (this.invertGregorian) {
            this.isGregorian = !z4;
        }
        if (this.isGregorian) {
            if (z3 && (i % 100 != 0 || i % 400 == 0)) {
                z2 = true;
            }
            iFloorDivide += (floorDivide(i3, 400) - floorDivide(i3, 100)) + 2;
            z3 = z2;
        }
        if (i2 != 0) {
            return iFloorDivide + MONTH_COUNT[i2][z3 ? (char) 3 : (char) 2];
        }
        return iFloorDivide;
    }
}
