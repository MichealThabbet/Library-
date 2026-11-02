package com.ibm.icu.util;

import com.google.android.gms.common.util.GmsVersion;
import com.ibm.icu.util.ULocale;
import it.piapostolos.android.pinotes.R2;
import java.util.Date;
import java.util.Locale;

/* loaded from: classes2.dex */
public class IndianCalendar extends Calendar {
    public static final int AGRAHAYANA = 8;
    public static final int ASADHA = 3;
    public static final int ASVINA = 6;
    public static final int BHADRA = 5;
    public static final int CHAITRA = 0;
    public static final int IE = 0;
    private static final int INDIAN_ERA_START = 78;
    private static final int INDIAN_YEAR_START = 80;
    public static final int JYAISTHA = 2;
    public static final int KARTIKA = 7;
    private static final int[][] LIMITS = {new int[]{0, 0, 0, 0}, new int[]{-5000000, -5000000, GmsVersion.VERSION_LONGHORN, GmsVersion.VERSION_LONGHORN}, new int[]{0, 0, 11, 11}, new int[]{1, 1, 52, 53}, new int[0], new int[]{1, 1, 30, 31}, new int[]{1, 1, R2.attr.defaultValue, 366}, new int[0], new int[]{-1, -1, 5, 5}, new int[0], new int[0], new int[0], new int[0], new int[0], new int[0], new int[0], new int[0], new int[]{-5000000, -5000000, GmsVersion.VERSION_LONGHORN, GmsVersion.VERSION_LONGHORN}, new int[0], new int[]{-5000000, -5000000, GmsVersion.VERSION_LONGHORN, GmsVersion.VERSION_LONGHORN}, new int[0], new int[0]};
    public static final int MAGHA = 10;
    public static final int PAUSA = 9;
    public static final int PHALGUNA = 11;
    public static final int SRAVANA = 4;
    public static final int VAISAKHA = 1;
    private static final long serialVersionUID = 3617859668165014834L;

    @Override // com.ibm.icu.util.Calendar
    public String getType() {
        return "indian";
    }

    public IndianCalendar() {
        this(TimeZone.getDefault(), ULocale.getDefault(ULocale.Category.FORMAT));
    }

    public IndianCalendar(TimeZone timeZone) {
        this(timeZone, ULocale.getDefault(ULocale.Category.FORMAT));
    }

    public IndianCalendar(Locale locale) {
        this(TimeZone.forLocaleOrDefault(locale), locale);
    }

    public IndianCalendar(ULocale uLocale) {
        this(TimeZone.forULocaleOrDefault(uLocale), uLocale);
    }

    public IndianCalendar(TimeZone timeZone, Locale locale) {
        super(timeZone, locale);
        setTimeInMillis(System.currentTimeMillis());
    }

    public IndianCalendar(TimeZone timeZone, ULocale uLocale) {
        super(timeZone, uLocale);
        setTimeInMillis(System.currentTimeMillis());
    }

    public IndianCalendar(Date date) {
        super(TimeZone.getDefault(), ULocale.getDefault(ULocale.Category.FORMAT));
        setTime(date);
    }

    public IndianCalendar(int i, int i2, int i3) {
        super(TimeZone.getDefault(), ULocale.getDefault(ULocale.Category.FORMAT));
        set(1, i);
        set(2, i2);
        set(5, i3);
    }

    public IndianCalendar(int i, int i2, int i3, int i4, int i5, int i6) {
        super(TimeZone.getDefault(), ULocale.getDefault(ULocale.Category.FORMAT));
        set(1, i);
        set(2, i2);
        set(5, i3);
        set(11, i4);
        set(12, i5);
        set(13, i6);
    }

    @Override // com.ibm.icu.util.Calendar
    protected int handleGetExtendedYear() {
        if (newerField(19, 1) == 19) {
            return internalGet(19, 1);
        }
        return internalGet(1, 1);
    }

    @Override // com.ibm.icu.util.Calendar
    protected int handleGetYearLength(int i) {
        return super.handleGetYearLength(i);
    }

    @Override // com.ibm.icu.util.Calendar
    protected int handleGetMonthLength(int i, int i2) {
        if (i2 < 0 || i2 > 11) {
            int[] iArr = new int[1];
            i += floorDivide(i2, 12, iArr);
            i2 = iArr[0];
        }
        if (isGregorianLeapYear(i + 78) && i2 == 0) {
            return 31;
        }
        return (i2 < 1 || i2 > 5) ? 30 : 31;
    }

    @Override // com.ibm.icu.util.Calendar
    protected void handleComputeFields(int i) {
        int i2;
        int i3;
        int i4;
        int i5;
        computeGregorianFields(i);
        int gregorianYear = getGregorianYear();
        int i6 = gregorianYear - 78;
        int iGregorianToJD = (int) (i - gregorianToJD(gregorianYear, 0, 1));
        if (iGregorianToJD < 80) {
            i6--;
            i2 = isGregorianLeapYear(gregorianYear - 1) ? 31 : 30;
            i3 = iGregorianToJD + i2 + 155 + 90 + 10;
        } else {
            i2 = isGregorianLeapYear(gregorianYear) ? 31 : 30;
            i3 = iGregorianToJD - 80;
        }
        if (i3 < i2) {
            i4 = i3 + 1;
            i5 = 0;
        } else {
            int i7 = i3 - i2;
            if (i7 < 155) {
                i5 = (i7 / 31) + 1;
                i4 = (i7 % 31) + 1;
            } else {
                int i8 = i7 - 155;
                int i9 = (i8 / 30) + 6;
                i4 = (i8 % 30) + 1;
                i5 = i9;
            }
        }
        internalSet(0, 0);
        internalSet(19, i6);
        internalSet(1, i6);
        internalSet(2, i5);
        internalSet(5, i4);
        internalSet(6, i3 + 1);
    }

    @Override // com.ibm.icu.util.Calendar
    protected int handleGetLimit(int i, int i2) {
        return LIMITS[i][i2];
    }

    @Override // com.ibm.icu.util.Calendar
    protected int handleComputeMonthStart(int i, int i2, boolean z) {
        if (i2 < 0 || i2 > 11) {
            i += i2 / 12;
            i2 %= 12;
        }
        return (int) IndianToJD(i, i2 + 1, 1);
    }

    private double IndianToJD(int i, int i2, int i3) {
        double dGregorianToJD;
        int i4;
        int i5 = i + 78;
        if (isGregorianLeapYear(i5)) {
            dGregorianToJD = gregorianToJD(i5, 2, 21);
            i4 = 31;
        } else {
            dGregorianToJD = gregorianToJD(i5, 2, 22);
            i4 = 30;
        }
        if (i2 != 1) {
            dGregorianToJD = dGregorianToJD + i4 + (Math.min(i2 - 2, 5) * 31);
            if (i2 >= 8) {
                dGregorianToJD += (i2 - 7) * 30;
            }
        }
        return dGregorianToJD + (i3 - 1);
    }

    private double gregorianToJD(int i, int i2, int i3) {
        return (computeGregorianMonthStart(i, i2) + i3) - 0.5d;
    }
}
