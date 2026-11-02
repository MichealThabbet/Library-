package com.ibm.icu.util;

import com.ibm.icu.impl.CalendarUtil;
import com.ibm.icu.util.ULocale;
import it.orsozoxi.android.orsonotes.R2;
import java.util.Date;
import java.util.Locale;

/* loaded from: classes2.dex */
public final class EthiopicCalendar extends CECalendar {
    private static final int AMETE_ALEM = 0;
    private static final int AMETE_ALEM_ERA = 1;
    private static final int AMETE_MIHRET = 1;
    private static final int AMETE_MIHRET_DELTA = 5500;
    private static final int AMETE_MIHRET_ERA = 0;
    public static final int GENBOT = 8;
    public static final int HAMLE = 10;
    public static final int HEDAR = 2;
    private static final int JD_EPOCH_OFFSET_AMETE_MIHRET = 1723856;
    public static final int MEGABIT = 6;
    public static final int MESKEREM = 0;
    public static final int MIAZIA = 7;
    public static final int NEHASSE = 11;
    public static final int PAGUMEN = 12;
    public static final int SENE = 9;
    public static final int TAHSAS = 3;
    public static final int TEKEMT = 1;
    public static final int TER = 4;
    public static final int YEKATIT = 5;
    private static final long serialVersionUID = -2438495771339315608L;
    private int eraType;

    @Override // com.ibm.icu.util.CECalendar
    @Deprecated
    protected int getJDEpochOffset() {
        return JD_EPOCH_OFFSET_AMETE_MIHRET;
    }

    public EthiopicCalendar() {
        this(TimeZone.getDefault(), ULocale.getDefault(ULocale.Category.FORMAT));
    }

    public EthiopicCalendar(TimeZone timeZone) {
        this(timeZone, ULocale.getDefault(ULocale.Category.FORMAT));
    }

    public EthiopicCalendar(Locale locale) {
        this(TimeZone.forLocaleOrDefault(locale), locale);
    }

    public EthiopicCalendar(ULocale uLocale) {
        this(TimeZone.forULocaleOrDefault(uLocale), uLocale);
    }

    public EthiopicCalendar(TimeZone timeZone, Locale locale) {
        this(timeZone, ULocale.forLocale(locale));
    }

    public EthiopicCalendar(TimeZone timeZone, ULocale uLocale) {
        super(timeZone, uLocale);
        this.eraType = 0;
        setCalcTypeForLocale(uLocale);
    }

    public EthiopicCalendar(int i, int i2, int i3) {
        super(i, i2, i3);
        this.eraType = 0;
    }

    public EthiopicCalendar(Date date) {
        super(date);
        this.eraType = 0;
    }

    public EthiopicCalendar(int i, int i2, int i3, int i4, int i5, int i6) {
        super(i, i2, i3, i4, i5, i6);
        this.eraType = 0;
    }

    @Override // com.ibm.icu.util.Calendar
    public String getType() {
        return isAmeteAlemEra() ? "ethiopic-amete-alem" : "ethiopic";
    }

    public void setAmeteAlemEra(boolean z) {
        this.eraType = z ? 1 : 0;
    }

    public boolean isAmeteAlemEra() {
        return this.eraType == 1;
    }

    @Override // com.ibm.icu.util.Calendar
    @Deprecated
    protected int handleGetExtendedYear() {
        int iInternalGet;
        if (newerField(19, 1) == 19) {
            return internalGet(19, 1);
        }
        if (isAmeteAlemEra()) {
            iInternalGet = internalGet(1, R2.styleable.ConstraintSet_android_maxHeight);
        } else {
            if (internalGet(0, 1) == 1) {
                return internalGet(1, 1);
            }
            iInternalGet = internalGet(1, 1);
        }
        return iInternalGet - 5500;
    }

    @Override // com.ibm.icu.util.Calendar
    @Deprecated
    protected void handleComputeFields(int i) {
        int i2;
        int i3;
        int[] iArr = new int[3];
        jdToCE(i, getJDEpochOffset(), iArr);
        if (!isAmeteAlemEra() && iArr[0] > 0) {
            i2 = iArr[0];
            i3 = 1;
            internalSet(19, iArr[0]);
            internalSet(0, i3);
            internalSet(1, i2);
            internalSet(2, iArr[1]);
            internalSet(5, iArr[2]);
            internalSet(6, (iArr[1] * 30) + iArr[2]);
        }
        int i4 = iArr[0];
        i2 = i4 + 5500;
        i3 = 0;
        internalSet(19, iArr[0]);
        internalSet(0, i3);
        internalSet(1, i2);
        internalSet(2, iArr[1]);
        internalSet(5, iArr[2]);
        internalSet(6, (iArr[1] * 30) + iArr[2]);
    }

    @Override // com.ibm.icu.util.CECalendar, com.ibm.icu.util.Calendar
    @Deprecated
    protected int handleGetLimit(int i, int i2) {
        if (isAmeteAlemEra() && i == 0) {
            return 0;
        }
        return super.handleGetLimit(i, i2);
    }

    public static int EthiopicToJD(long j, int i, int i2) {
        return ceToJD(j, i, i2, JD_EPOCH_OFFSET_AMETE_MIHRET);
    }

    private void setCalcTypeForLocale(ULocale uLocale) {
        if ("ethiopic-amete-alem".equals(CalendarUtil.getCalendarType(uLocale))) {
            setAmeteAlemEra(true);
        } else {
            setAmeteAlemEra(false);
        }
    }
}
