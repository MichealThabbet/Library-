package com.ibm.icu.util;

import com.google.android.gms.common.util.GmsVersion;
import com.ibm.icu.util.ULocale;
import it.piapostolos.android.pinotes.R2;
import java.util.Date;
import java.util.Locale;

/* loaded from: classes2.dex */
abstract class CECalendar extends Calendar {
    private static final int[][] LIMITS = {new int[]{0, 0, 1, 1}, new int[]{1, 1, GmsVersion.VERSION_LONGHORN, GmsVersion.VERSION_LONGHORN}, new int[]{0, 0, 12, 12}, new int[]{1, 1, 52, 53}, new int[0], new int[]{1, 1, 5, 30}, new int[]{1, 1, R2.attr.defaultValue, 366}, new int[0], new int[]{-1, -1, 1, 5}, new int[0], new int[0], new int[0], new int[0], new int[0], new int[0], new int[0], new int[0], new int[]{-5000000, -5000000, GmsVersion.VERSION_LONGHORN, GmsVersion.VERSION_LONGHORN}, new int[0], new int[]{-5000000, -5000000, GmsVersion.VERSION_LONGHORN, GmsVersion.VERSION_LONGHORN}, new int[0], new int[0]};
    private static final long serialVersionUID = -999547623066414271L;

    protected abstract int getJDEpochOffset();

    protected CECalendar() {
        this(TimeZone.getDefault(), ULocale.getDefault(ULocale.Category.FORMAT));
    }

    protected CECalendar(TimeZone timeZone) {
        this(timeZone, ULocale.getDefault(ULocale.Category.FORMAT));
    }

    protected CECalendar(Locale locale) {
        this(TimeZone.forLocaleOrDefault(locale), locale);
    }

    protected CECalendar(ULocale uLocale) {
        this(TimeZone.forULocaleOrDefault(uLocale), uLocale);
    }

    protected CECalendar(TimeZone timeZone, Locale locale) {
        super(timeZone, locale);
        setTimeInMillis(System.currentTimeMillis());
    }

    protected CECalendar(TimeZone timeZone, ULocale uLocale) {
        super(timeZone, uLocale);
        setTimeInMillis(System.currentTimeMillis());
    }

    protected CECalendar(int i, int i2, int i3) {
        super(TimeZone.getDefault(), ULocale.getDefault(ULocale.Category.FORMAT));
        set(i, i2, i3);
    }

    protected CECalendar(Date date) {
        super(TimeZone.getDefault(), ULocale.getDefault(ULocale.Category.FORMAT));
        setTime(date);
    }

    protected CECalendar(int i, int i2, int i3, int i4, int i5, int i6) {
        super(TimeZone.getDefault(), ULocale.getDefault(ULocale.Category.FORMAT));
        set(i, i2, i3, i4, i5, i6);
    }

    @Override // com.ibm.icu.util.Calendar
    protected int handleComputeMonthStart(int i, int i2, boolean z) {
        return ceToJD(i, i2, 0, getJDEpochOffset());
    }

    @Override // com.ibm.icu.util.Calendar
    protected int handleGetLimit(int i, int i2) {
        return LIMITS[i][i2];
    }

    @Override // com.ibm.icu.util.Calendar
    protected int handleGetMonthLength(int i, int i2) {
        if ((i2 + 1) % 13 != 0) {
            return 30;
        }
        return ((i % 4) / 3) + 5;
    }

    public static int ceToJD(long j, int i, int i2, int i3) {
        long j2;
        int i4;
        if (i >= 0) {
            j2 = j + (i / 13);
            i4 = i % 13;
        } else {
            j2 = j + ((r6 / 13) - 1);
            i4 = ((i + 1) % 13) + 12;
        }
        return (int) (((((i3 + (365 * j2)) + floorDivide(j2, 4L)) + (i4 * 30)) + i2) - 1);
    }

    public static void jdToCE(int i, int i2, int[] iArr) {
        int[] iArr2 = new int[1];
        int iFloorDivide = floorDivide(i - i2, R2.color.primary_text_disabled_material_dark, iArr2) * 4;
        int i3 = iArr2[0];
        int i4 = R2.attr.defaultValue;
        iArr[0] = iFloorDivide + ((i3 / R2.attr.defaultValue) - (iArr2[0] / R2.color.primary_text_default_material_light));
        if (iArr2[0] != 1460) {
            i4 = iArr2[0] % R2.attr.defaultValue;
        }
        iArr[1] = i4 / 30;
        iArr[2] = (i4 % 30) + 1;
    }
}
