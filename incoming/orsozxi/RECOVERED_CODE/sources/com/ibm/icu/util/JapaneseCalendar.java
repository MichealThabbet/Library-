package com.ibm.icu.util;

import com.facebook.internal.ServerProtocol;
import com.ibm.icu.impl.CalType;
import com.ibm.icu.impl.EraRules;
import java.util.Date;
import java.util.Locale;

/* loaded from: classes2.dex */
public class JapaneseCalendar extends GregorianCalendar {
    public static final int CURRENT_ERA;
    private static final EraRules ERA_RULES;
    private static final int GREGORIAN_EPOCH = 1970;
    public static final int HEISEI;
    public static final int MEIJI;
    public static final int REIWA;
    public static final int SHOWA;
    public static final int TAISHO;
    private static final long serialVersionUID = -2977189902603704691L;

    @Override // com.ibm.icu.util.GregorianCalendar, com.ibm.icu.util.Calendar
    public String getType() {
        return "japanese";
    }

    @Override // com.ibm.icu.util.Calendar
    @Deprecated
    public boolean haveDefaultCentury() {
        return false;
    }

    public JapaneseCalendar() {
    }

    public JapaneseCalendar(TimeZone timeZone) {
        super(timeZone);
    }

    public JapaneseCalendar(Locale locale) {
        super(locale);
    }

    public JapaneseCalendar(ULocale uLocale) {
        super(uLocale);
    }

    public JapaneseCalendar(TimeZone timeZone, Locale locale) {
        super(timeZone, locale);
    }

    public JapaneseCalendar(TimeZone timeZone, ULocale uLocale) {
        super(timeZone, uLocale);
    }

    public JapaneseCalendar(Date date) {
        this();
        setTime(date);
    }

    public JapaneseCalendar(int i, int i2, int i3, int i4) {
        super(i2, i3, i4);
        set(0, i);
    }

    public JapaneseCalendar(int i, int i2, int i3) {
        super(i, i2, i3);
        set(0, CURRENT_ERA);
    }

    public JapaneseCalendar(int i, int i2, int i3, int i4, int i5, int i6) {
        super(i, i2, i3, i4, i5, i6);
        set(0, CURRENT_ERA);
    }

    static {
        EraRules eraRules = EraRules.getInstance(CalType.JAPANESE, enableTentativeEra());
        ERA_RULES = eraRules;
        MEIJI = 232;
        TAISHO = 233;
        SHOWA = 234;
        HEISEI = 235;
        REIWA = 236;
        CURRENT_ERA = eraRules.getCurrentEraIndex();
    }

    @Deprecated
    public static boolean enableTentativeEra() {
        String property = System.getProperty("ICU_ENABLE_TENTATIVE_ERA");
        if (property == null) {
            property = System.getenv("ICU_ENABLE_TENTATIVE_ERA");
        }
        if (property != null) {
            return property.equalsIgnoreCase(ServerProtocol.DIALOG_RETURN_SCOPES_TRUE);
        }
        return System.getProperty("jdk.calendar.japanese.supplemental.era") != null;
    }

    @Override // com.ibm.icu.util.GregorianCalendar, com.ibm.icu.util.Calendar
    protected int handleGetExtendedYear() {
        if (newerField(19, 1) == 19 && newerField(19, 0) == 19) {
            return internalGet(19, 1970);
        }
        return (internalGet(1, 1) + ERA_RULES.getStartYear(internalGet(0, CURRENT_ERA))) - 1;
    }

    @Override // com.ibm.icu.util.Calendar
    protected int getDefaultMonthInYear(int i) {
        int[] startDate = ERA_RULES.getStartDate(internalGet(0, CURRENT_ERA), null);
        if (i == startDate[0]) {
            return startDate[1] - 1;
        }
        return super.getDefaultMonthInYear(i);
    }

    @Override // com.ibm.icu.util.Calendar
    protected int getDefaultDayInMonth(int i, int i2) {
        int[] startDate = ERA_RULES.getStartDate(internalGet(0, CURRENT_ERA), null);
        if (i == startDate[0] && i2 == startDate[1] - 1) {
            return startDate[2];
        }
        return super.getDefaultDayInMonth(i, i2);
    }

    @Override // com.ibm.icu.util.GregorianCalendar, com.ibm.icu.util.Calendar
    protected void handleComputeFields(int i) {
        super.handleComputeFields(i);
        int iInternalGet = internalGet(19);
        int eraIndex = ERA_RULES.getEraIndex(iInternalGet, internalGet(2) + 1, internalGet(5));
        internalSet(0, eraIndex);
        internalSet(1, (iInternalGet - ERA_RULES.getStartYear(eraIndex)) + 1);
    }

    @Override // com.ibm.icu.util.GregorianCalendar, com.ibm.icu.util.Calendar
    protected int handleGetLimit(int i, int i2) {
        if (i == 0) {
            if (i2 == 0 || i2 == 1) {
                return 0;
            }
            return ERA_RULES.getNumberOfEras() - 1;
        }
        if (i == 1) {
            if (i2 == 0 || i2 == 1 || i2 == 2) {
                return 1;
            }
            if (i2 == 3) {
                return super.handleGetLimit(i, 3) - ERA_RULES.getStartYear(CURRENT_ERA);
            }
        }
        return super.handleGetLimit(i, i2);
    }

    @Override // com.ibm.icu.util.GregorianCalendar, com.ibm.icu.util.Calendar
    public int getActualMaximum(int i) {
        if (i == 1) {
            int i2 = get(0);
            if (i2 == ERA_RULES.getNumberOfEras() - 1) {
                return handleGetLimit(1, 3);
            }
            int[] startDate = ERA_RULES.getStartDate(i2 + 1, null);
            int i3 = startDate[0];
            int i4 = startDate[1];
            int i5 = startDate[2];
            int startYear = (i3 - ERA_RULES.getStartYear(i2)) + 1;
            return (i4 == 1 && i5 == 1) ? startYear - 1 : startYear;
        }
        return super.getActualMaximum(i);
    }
}
