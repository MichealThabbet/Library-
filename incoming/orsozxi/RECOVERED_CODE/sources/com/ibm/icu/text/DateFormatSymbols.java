package com.ibm.icu.text;

import com.ibm.icu.impl.CacheBase;
import com.ibm.icu.impl.CalendarUtil;
import com.ibm.icu.impl.ICUData;
import com.ibm.icu.impl.ICUResourceBundle;
import com.ibm.icu.impl.SoftCache;
import com.ibm.icu.impl.UResource;
import com.ibm.icu.impl.Utility;
import com.ibm.icu.text.TimeZoneNames;
import com.ibm.icu.util.Calendar;
import com.ibm.icu.util.ICUCloneNotSupportedException;
import com.ibm.icu.util.ICUException;
import com.ibm.icu.util.TimeZone;
import com.ibm.icu.util.ULocale;
import com.ibm.icu.util.UResourceBundle;
import com.ibm.icu.util.UResourceBundleIterator;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.NoSuchElementException;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.TreeMap;

/* loaded from: classes2.dex */
public class DateFormatSymbols implements Serializable, Cloneable {
    public static final int ABBREVIATED = 0;
    static final String ALTERNATE_TIME_SEPARATOR = ".";
    private static final String[][] CALENDAR_CLASSES = {new String[]{"GregorianCalendar", "gregorian"}, new String[]{"JapaneseCalendar", "japanese"}, new String[]{"BuddhistCalendar", "buddhist"}, new String[]{"TaiwanCalendar", "roc"}, new String[]{"PersianCalendar", "persian"}, new String[]{"IslamicCalendar", "islamic"}, new String[]{"HebrewCalendar", "hebrew"}, new String[]{"ChineseCalendar", "chinese"}, new String[]{"IndianCalendar", "indian"}, new String[]{"CopticCalendar", "coptic"}, new String[]{"EthiopicCalendar", "ethiopic"}};
    private static final String[] DAY_PERIOD_KEYS;
    static final String DEFAULT_TIME_SEPARATOR = ":";
    private static CacheBase<String, DateFormatSymbols, ULocale> DFSCACHE = null;

    @Deprecated
    public static final int DT_CONTEXT_COUNT = 3;
    static final int DT_LEAP_MONTH_PATTERN_FORMAT_ABBREV = 1;
    static final int DT_LEAP_MONTH_PATTERN_FORMAT_NARROW = 2;
    static final int DT_LEAP_MONTH_PATTERN_FORMAT_WIDE = 0;
    static final int DT_LEAP_MONTH_PATTERN_NUMERIC = 6;
    static final int DT_LEAP_MONTH_PATTERN_STANDALONE_ABBREV = 4;
    static final int DT_LEAP_MONTH_PATTERN_STANDALONE_NARROW = 5;
    static final int DT_LEAP_MONTH_PATTERN_STANDALONE_WIDE = 3;
    static final int DT_MONTH_PATTERN_COUNT = 7;

    @Deprecated
    public static final int DT_WIDTH_COUNT = 4;
    public static final int FORMAT = 0;
    private static final String[] LEAP_MONTH_PATTERNS_PATHS;
    public static final int NARROW = 2;

    @Deprecated
    public static final int NUMERIC = 2;
    public static final int SHORT = 3;
    public static final int STANDALONE = 1;
    public static final int WIDE = 1;
    private static final Map<String, CapitalizationContextUsage> contextUsageTypeMap;
    static final int millisPerHour = 3600000;
    static final String patternChars = "GyMdkHmsSEDFwWahKzYeugAZvcLQqVUOXxrbB";
    private static final long serialVersionUID = -5987973545549424702L;
    String[] abbreviatedDayPeriods;
    private ULocale actualLocale;
    String[] ampms;
    String[] ampmsNarrow;
    Map<CapitalizationContextUsage, boolean[]> capitalization;
    String[] eraNames;
    String[] eras;
    String[] leapMonthPatterns;
    String localPatternChars;
    String[] months;
    String[] narrowDayPeriods;
    String[] narrowEras;
    String[] narrowMonths;
    String[] narrowWeekdays;
    String[] quarters;
    private ULocale requestedLocale;
    String[] shortMonths;
    String[] shortQuarters;
    String[] shortWeekdays;
    String[] shortYearNames;
    String[] shortZodiacNames;
    String[] shorterWeekdays;
    String[] standaloneAbbreviatedDayPeriods;
    String[] standaloneMonths;
    String[] standaloneNarrowDayPeriods;
    String[] standaloneNarrowMonths;
    String[] standaloneNarrowWeekdays;
    String[] standaloneQuarters;
    String[] standaloneShortMonths;
    String[] standaloneShortQuarters;
    String[] standaloneShortWeekdays;
    String[] standaloneShorterWeekdays;
    String[] standaloneWeekdays;
    String[] standaloneWideDayPeriods;
    private String timeSeparator;
    private ULocale validLocale;
    String[] weekdays;
    String[] wideDayPeriods;
    private String[][] zoneStrings;

    enum CapitalizationContextUsage {
        OTHER,
        MONTH_FORMAT,
        MONTH_STANDALONE,
        MONTH_NARROW,
        DAY_FORMAT,
        DAY_STANDALONE,
        DAY_NARROW,
        ERA_WIDE,
        ERA_ABBREV,
        ERA_NARROW,
        ZONE_LONG,
        ZONE_SHORT,
        METAZONE_LONG,
        METAZONE_SHORT
    }

    @Deprecated
    public static ResourceBundle getDateFormatBundle(Calendar calendar, ULocale uLocale) throws MissingResourceException {
        return null;
    }

    @Deprecated
    public static ResourceBundle getDateFormatBundle(Calendar calendar, Locale locale) throws MissingResourceException {
        return null;
    }

    @Deprecated
    public static ResourceBundle getDateFormatBundle(Class<? extends Calendar> cls, ULocale uLocale) throws MissingResourceException {
        return null;
    }

    @Deprecated
    public static ResourceBundle getDateFormatBundle(Class<? extends Calendar> cls, Locale locale) throws MissingResourceException {
        return null;
    }

    public DateFormatSymbols() {
        this(ULocale.getDefault(ULocale.Category.FORMAT));
    }

    public DateFormatSymbols(Locale locale) {
        this(ULocale.forLocale(locale));
    }

    public DateFormatSymbols(ULocale uLocale) {
        this.eras = null;
        this.eraNames = null;
        this.narrowEras = null;
        this.months = null;
        this.shortMonths = null;
        this.narrowMonths = null;
        this.standaloneMonths = null;
        this.standaloneShortMonths = null;
        this.standaloneNarrowMonths = null;
        this.weekdays = null;
        this.shortWeekdays = null;
        this.shorterWeekdays = null;
        this.narrowWeekdays = null;
        this.standaloneWeekdays = null;
        this.standaloneShortWeekdays = null;
        this.standaloneShorterWeekdays = null;
        this.standaloneNarrowWeekdays = null;
        this.ampms = null;
        this.ampmsNarrow = null;
        this.timeSeparator = null;
        this.shortQuarters = null;
        this.quarters = null;
        this.standaloneShortQuarters = null;
        this.standaloneQuarters = null;
        this.leapMonthPatterns = null;
        this.shortYearNames = null;
        this.shortZodiacNames = null;
        this.zoneStrings = (String[][]) null;
        this.localPatternChars = null;
        this.abbreviatedDayPeriods = null;
        this.wideDayPeriods = null;
        this.narrowDayPeriods = null;
        this.standaloneAbbreviatedDayPeriods = null;
        this.standaloneWideDayPeriods = null;
        this.standaloneNarrowDayPeriods = null;
        this.capitalization = null;
        initializeData(uLocale, CalendarUtil.getCalendarType(uLocale));
    }

    public static DateFormatSymbols getInstance() {
        return new DateFormatSymbols();
    }

    public static DateFormatSymbols getInstance(Locale locale) {
        return new DateFormatSymbols(locale);
    }

    public static DateFormatSymbols getInstance(ULocale uLocale) {
        return new DateFormatSymbols(uLocale);
    }

    public static Locale[] getAvailableLocales() {
        return ICUResourceBundle.getAvailableLocales();
    }

    public static ULocale[] getAvailableULocales() {
        return ICUResourceBundle.getAvailableULocales();
    }

    static {
        HashMap map = new HashMap();
        contextUsageTypeMap = map;
        map.put("month-format-except-narrow", CapitalizationContextUsage.MONTH_FORMAT);
        contextUsageTypeMap.put("month-standalone-except-narrow", CapitalizationContextUsage.MONTH_STANDALONE);
        contextUsageTypeMap.put("month-narrow", CapitalizationContextUsage.MONTH_NARROW);
        contextUsageTypeMap.put("day-format-except-narrow", CapitalizationContextUsage.DAY_FORMAT);
        contextUsageTypeMap.put("day-standalone-except-narrow", CapitalizationContextUsage.DAY_STANDALONE);
        contextUsageTypeMap.put("day-narrow", CapitalizationContextUsage.DAY_NARROW);
        contextUsageTypeMap.put("era-name", CapitalizationContextUsage.ERA_WIDE);
        contextUsageTypeMap.put("era-abbr", CapitalizationContextUsage.ERA_ABBREV);
        contextUsageTypeMap.put("era-narrow", CapitalizationContextUsage.ERA_NARROW);
        contextUsageTypeMap.put("zone-long", CapitalizationContextUsage.ZONE_LONG);
        contextUsageTypeMap.put("zone-short", CapitalizationContextUsage.ZONE_SHORT);
        contextUsageTypeMap.put("metazone-long", CapitalizationContextUsage.METAZONE_LONG);
        contextUsageTypeMap.put("metazone-short", CapitalizationContextUsage.METAZONE_SHORT);
        DFSCACHE = new SoftCache<String, DateFormatSymbols, ULocale>() { // from class: com.ibm.icu.text.DateFormatSymbols.1
            /* JADX INFO: Access modifiers changed from: protected */
            /* JADX WARN: Multi-variable type inference failed */
            @Override // com.ibm.icu.impl.CacheBase
            public DateFormatSymbols createInstance(String str, ULocale uLocale) {
                int iIndexOf = str.indexOf(43) + 1;
                int iIndexOf2 = str.indexOf(43, iIndexOf);
                if (iIndexOf2 < 0) {
                    iIndexOf2 = str.length();
                }
                return new DateFormatSymbols(uLocale, null, str.substring(iIndexOf, iIndexOf2));
            }
        };
        LEAP_MONTH_PATTERNS_PATHS = new String[]{"monthPatterns/format/wide", "monthPatterns/format/abbreviated", "monthPatterns/format/narrow", "monthPatterns/stand-alone/wide", "monthPatterns/stand-alone/abbreviated", "monthPatterns/stand-alone/narrow", "monthPatterns/numeric/all"};
        DAY_PERIOD_KEYS = new String[]{"midnight", "noon", "morning1", "afternoon1", "evening1", "night1", "morning2", "afternoon2", "evening2", "night2"};
    }

    public String[] getEras() {
        return duplicate(this.eras);
    }

    public void setEras(String[] strArr) {
        this.eras = duplicate(strArr);
    }

    public String[] getEraNames() {
        return duplicate(this.eraNames);
    }

    public void setEraNames(String[] strArr) {
        this.eraNames = duplicate(strArr);
    }

    public String[] getNarrowEras() {
        return duplicate(this.narrowEras);
    }

    public void setNarrowEras(String[] strArr) {
        this.narrowEras = duplicate(strArr);
    }

    public String[] getMonths() {
        return duplicate(this.months);
    }

    /* JADX WARN: Removed duplicated region for block: B:13:0x0018  */
    /* JADX WARN: Removed duplicated region for block: B:21:0x002a  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public java.lang.String[] getMonths(int r5, int r6) {
        /*
            r4 = this;
            r0 = 3
            r1 = 2
            r2 = 1
            r3 = 0
            if (r5 == 0) goto L1b
            if (r5 == r2) goto L9
            goto L2c
        L9:
            if (r6 == 0) goto L18
            if (r6 == r2) goto L15
            if (r6 == r1) goto L12
            if (r6 == r0) goto L18
            goto L2c
        L12:
            java.lang.String[] r3 = r4.standaloneNarrowMonths
            goto L2c
        L15:
            java.lang.String[] r3 = r4.standaloneMonths
            goto L2c
        L18:
            java.lang.String[] r3 = r4.standaloneShortMonths
            goto L2c
        L1b:
            if (r6 == 0) goto L2a
            if (r6 == r2) goto L27
            if (r6 == r1) goto L24
            if (r6 == r0) goto L2a
            goto L2c
        L24:
            java.lang.String[] r3 = r4.narrowMonths
            goto L2c
        L27:
            java.lang.String[] r3 = r4.months
            goto L2c
        L2a:
            java.lang.String[] r3 = r4.shortMonths
        L2c:
            if (r3 == 0) goto L33
            java.lang.String[] r5 = r4.duplicate(r3)
            return r5
        L33:
            java.lang.IllegalArgumentException r5 = new java.lang.IllegalArgumentException
            java.lang.String r6 = "Bad context or width argument"
            r5.<init>(r6)
            throw r5
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ibm.icu.text.DateFormatSymbols.getMonths(int, int):java.lang.String[]");
    }

    public void setMonths(String[] strArr) {
        this.months = duplicate(strArr);
    }

    public void setMonths(String[] strArr, int i, int i2) {
        if (i == 0) {
            if (i2 == 0) {
                this.shortMonths = duplicate(strArr);
                return;
            } else if (i2 == 1) {
                this.months = duplicate(strArr);
                return;
            } else {
                if (i2 != 2) {
                    return;
                }
                this.narrowMonths = duplicate(strArr);
                return;
            }
        }
        if (i != 1) {
            return;
        }
        if (i2 == 0) {
            this.standaloneShortMonths = duplicate(strArr);
        } else if (i2 == 1) {
            this.standaloneMonths = duplicate(strArr);
        } else {
            if (i2 != 2) {
                return;
            }
            this.standaloneNarrowMonths = duplicate(strArr);
        }
    }

    public String[] getShortMonths() {
        return duplicate(this.shortMonths);
    }

    public void setShortMonths(String[] strArr) {
        this.shortMonths = duplicate(strArr);
    }

    public String[] getWeekdays() {
        return duplicate(this.weekdays);
    }

    public String[] getWeekdays(int i, int i2) {
        String[] strArr;
        String[] strArr2 = null;
        if (i != 0) {
            if (i == 1) {
                if (i2 == 0) {
                    strArr2 = this.standaloneShortWeekdays;
                } else if (i2 == 1) {
                    strArr2 = this.standaloneWeekdays;
                } else if (i2 == 2) {
                    strArr2 = this.standaloneNarrowWeekdays;
                } else if (i2 == 3) {
                    strArr = this.standaloneShorterWeekdays;
                    if (strArr == null) {
                        strArr = this.standaloneShortWeekdays;
                    }
                    strArr2 = strArr;
                }
            }
        } else if (i2 == 0) {
            strArr2 = this.shortWeekdays;
        } else if (i2 == 1) {
            strArr2 = this.weekdays;
        } else if (i2 == 2) {
            strArr2 = this.narrowWeekdays;
        } else if (i2 == 3) {
            strArr = this.shorterWeekdays;
            if (strArr == null) {
                strArr = this.shortWeekdays;
            }
            strArr2 = strArr;
        }
        if (strArr2 == null) {
            throw new IllegalArgumentException("Bad context or width argument");
        }
        return duplicate(strArr2);
    }

    public void setWeekdays(String[] strArr, int i, int i2) {
        if (i == 0) {
            if (i2 == 0) {
                this.shortWeekdays = duplicate(strArr);
                return;
            }
            if (i2 == 1) {
                this.weekdays = duplicate(strArr);
                return;
            } else if (i2 == 2) {
                this.narrowWeekdays = duplicate(strArr);
                return;
            } else {
                if (i2 != 3) {
                    return;
                }
                this.shorterWeekdays = duplicate(strArr);
                return;
            }
        }
        if (i != 1) {
            return;
        }
        if (i2 == 0) {
            this.standaloneShortWeekdays = duplicate(strArr);
            return;
        }
        if (i2 == 1) {
            this.standaloneWeekdays = duplicate(strArr);
        } else if (i2 == 2) {
            this.standaloneNarrowWeekdays = duplicate(strArr);
        } else {
            if (i2 != 3) {
                return;
            }
            this.standaloneShorterWeekdays = duplicate(strArr);
        }
    }

    public void setWeekdays(String[] strArr) {
        this.weekdays = duplicate(strArr);
    }

    public String[] getShortWeekdays() {
        return duplicate(this.shortWeekdays);
    }

    public void setShortWeekdays(String[] strArr) {
        this.shortWeekdays = duplicate(strArr);
    }

    /* JADX WARN: Removed duplicated region for block: B:12:0x0015  */
    /* JADX WARN: Removed duplicated region for block: B:19:0x0024  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public java.lang.String[] getQuarters(int r5, int r6) {
        /*
            r4 = this;
            r0 = 3
            r1 = 2
            r2 = 1
            r3 = 0
            if (r5 == 0) goto L18
            if (r5 == r2) goto L9
            goto L26
        L9:
            if (r6 == 0) goto L15
            if (r6 == r2) goto L12
            if (r6 == r1) goto L26
            if (r6 == r0) goto L15
            goto L26
        L12:
            java.lang.String[] r3 = r4.standaloneQuarters
            goto L26
        L15:
            java.lang.String[] r3 = r4.standaloneShortQuarters
            goto L26
        L18:
            if (r6 == 0) goto L24
            if (r6 == r2) goto L21
            if (r6 == r1) goto L26
            if (r6 == r0) goto L24
            goto L26
        L21:
            java.lang.String[] r3 = r4.quarters
            goto L26
        L24:
            java.lang.String[] r3 = r4.shortQuarters
        L26:
            if (r3 == 0) goto L2d
            java.lang.String[] r5 = r4.duplicate(r3)
            return r5
        L2d:
            java.lang.IllegalArgumentException r5 = new java.lang.IllegalArgumentException
            java.lang.String r6 = "Bad context or width argument"
            r5.<init>(r6)
            throw r5
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ibm.icu.text.DateFormatSymbols.getQuarters(int, int):java.lang.String[]");
    }

    public void setQuarters(String[] strArr, int i, int i2) {
        if (i == 0) {
            if (i2 == 0) {
                this.shortQuarters = duplicate(strArr);
                return;
            } else {
                if (i2 != 1) {
                    return;
                }
                this.quarters = duplicate(strArr);
                return;
            }
        }
        if (i != 1) {
            return;
        }
        if (i2 == 0) {
            this.standaloneShortQuarters = duplicate(strArr);
        } else {
            if (i2 != 1) {
                return;
            }
            this.standaloneQuarters = duplicate(strArr);
        }
    }

    public String[] getYearNames(int i, int i2) {
        String[] strArr = this.shortYearNames;
        if (strArr != null) {
            return duplicate(strArr);
        }
        return null;
    }

    public void setYearNames(String[] strArr, int i, int i2) {
        if (i == 0 && i2 == 0) {
            this.shortYearNames = duplicate(strArr);
        }
    }

    public String[] getZodiacNames(int i, int i2) {
        String[] strArr = this.shortZodiacNames;
        if (strArr != null) {
            return duplicate(strArr);
        }
        return null;
    }

    public void setZodiacNames(String[] strArr, int i, int i2) {
        if (i == 0 && i2 == 0) {
            this.shortZodiacNames = duplicate(strArr);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:17:0x001f  */
    @java.lang.Deprecated
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public java.lang.String getLeapMonthPattern(int r5, int r6) {
        /*
            r4 = this;
            java.lang.String[] r0 = r4.leapMonthPatterns
            if (r0 == 0) goto L3c
            r0 = -1
            r1 = 3
            r2 = 2
            r3 = 1
            if (r5 == 0) goto L21
            if (r5 == r3) goto L11
            if (r5 == r2) goto Lf
            goto L2d
        Lf:
            r0 = 6
            goto L2d
        L11:
            if (r6 == 0) goto L1f
            if (r6 == r3) goto L1d
            if (r6 == r2) goto L1a
            if (r6 == r1) goto L1f
            goto L2d
        L1a:
            r5 = 5
            r0 = 5
            goto L2d
        L1d:
            r0 = 3
            goto L2d
        L1f:
            r0 = 1
            goto L2d
        L21:
            if (r6 == 0) goto L1f
            if (r6 == r3) goto L2c
            if (r6 == r2) goto L2a
            if (r6 == r1) goto L1f
            goto L2d
        L2a:
            r0 = 2
            goto L2d
        L2c:
            r0 = 0
        L2d:
            if (r0 < 0) goto L34
            java.lang.String[] r5 = r4.leapMonthPatterns
            r5 = r5[r0]
            return r5
        L34:
            java.lang.IllegalArgumentException r5 = new java.lang.IllegalArgumentException
            java.lang.String r6 = "Bad context or width argument"
            r5.<init>(r6)
            throw r5
        L3c:
            r5 = 0
            return r5
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ibm.icu.text.DateFormatSymbols.getLeapMonthPattern(int, int):java.lang.String");
    }

    /* JADX WARN: Removed duplicated region for block: B:22:0x0026  */
    @java.lang.Deprecated
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public void setLeapMonthPattern(java.lang.String r4, int r5, int r6) {
        /*
            r3 = this;
            java.lang.String[] r0 = r3.leapMonthPatterns
            if (r0 == 0) goto L2d
            r0 = -1
            r1 = 2
            r2 = 1
            if (r5 == 0) goto L1b
            if (r5 == r2) goto L10
            if (r5 == r1) goto Le
            goto L27
        Le:
            r0 = 6
            goto L27
        L10:
            if (r6 == 0) goto L26
            if (r6 == r2) goto L19
            if (r6 == r1) goto L17
            goto L27
        L17:
            r0 = 5
            goto L27
        L19:
            r0 = 3
            goto L27
        L1b:
            if (r6 == 0) goto L26
            if (r6 == r2) goto L24
            if (r6 == r1) goto L22
            goto L27
        L22:
            r0 = 2
            goto L27
        L24:
            r0 = 0
            goto L27
        L26:
            r0 = 1
        L27:
            if (r0 < 0) goto L2d
            java.lang.String[] r5 = r3.leapMonthPatterns
            r5[r0] = r4
        L2d:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ibm.icu.text.DateFormatSymbols.setLeapMonthPattern(java.lang.String, int, int):void");
    }

    public String[] getAmPmStrings() {
        return duplicate(this.ampms);
    }

    public void setAmPmStrings(String[] strArr) {
        this.ampms = duplicate(strArr);
    }

    @Deprecated
    public String getTimeSeparatorString() {
        return this.timeSeparator;
    }

    @Deprecated
    public void setTimeSeparatorString(String str) {
        this.timeSeparator = str;
    }

    public String[][] getZoneStrings() {
        String[][] strArr = this.zoneStrings;
        if (strArr != null) {
            return duplicate(strArr);
        }
        String[] availableIDs = TimeZone.getAvailableIDs();
        TimeZoneNames timeZoneNames = TimeZoneNames.getInstance(this.validLocale);
        timeZoneNames.loadAllDisplayNames();
        TimeZoneNames.NameType[] nameTypeArr = {TimeZoneNames.NameType.LONG_STANDARD, TimeZoneNames.NameType.SHORT_STANDARD, TimeZoneNames.NameType.LONG_DAYLIGHT, TimeZoneNames.NameType.SHORT_DAYLIGHT};
        long jCurrentTimeMillis = System.currentTimeMillis();
        String[][] strArr2 = (String[][]) Array.newInstance((Class<?>) String.class, availableIDs.length, 5);
        for (int i = 0; i < availableIDs.length; i++) {
            String canonicalID = TimeZone.getCanonicalID(availableIDs[i]);
            if (canonicalID == null) {
                canonicalID = availableIDs[i];
            }
            strArr2[i][0] = availableIDs[i];
            timeZoneNames.getDisplayNames(canonicalID, nameTypeArr, jCurrentTimeMillis, strArr2[i], 1);
        }
        this.zoneStrings = strArr2;
        return strArr2;
    }

    public void setZoneStrings(String[][] strArr) {
        this.zoneStrings = duplicate(strArr);
    }

    public String getLocalPatternChars() {
        return this.localPatternChars;
    }

    public void setLocalPatternChars(String str) {
        this.localPatternChars = str;
    }

    public Object clone() {
        try {
            return (DateFormatSymbols) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new ICUCloneNotSupportedException(e);
        }
    }

    public int hashCode() {
        return this.requestedLocale.toString().hashCode();
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        DateFormatSymbols dateFormatSymbols = (DateFormatSymbols) obj;
        return Utility.arrayEquals((Object[]) this.eras, (Object) dateFormatSymbols.eras) && Utility.arrayEquals((Object[]) this.eraNames, (Object) dateFormatSymbols.eraNames) && Utility.arrayEquals((Object[]) this.narrowEras, (Object) dateFormatSymbols.narrowEras) && Utility.arrayEquals((Object[]) this.months, (Object) dateFormatSymbols.months) && Utility.arrayEquals((Object[]) this.shortMonths, (Object) dateFormatSymbols.shortMonths) && Utility.arrayEquals((Object[]) this.narrowMonths, (Object) dateFormatSymbols.narrowMonths) && Utility.arrayEquals((Object[]) this.standaloneMonths, (Object) dateFormatSymbols.standaloneMonths) && Utility.arrayEquals((Object[]) this.standaloneShortMonths, (Object) dateFormatSymbols.standaloneShortMonths) && Utility.arrayEquals((Object[]) this.standaloneNarrowMonths, (Object) dateFormatSymbols.standaloneNarrowMonths) && Utility.arrayEquals((Object[]) this.weekdays, (Object) dateFormatSymbols.weekdays) && Utility.arrayEquals((Object[]) this.shortWeekdays, (Object) dateFormatSymbols.shortWeekdays) && Utility.arrayEquals((Object[]) this.shorterWeekdays, (Object) dateFormatSymbols.shorterWeekdays) && Utility.arrayEquals((Object[]) this.narrowWeekdays, (Object) dateFormatSymbols.narrowWeekdays) && Utility.arrayEquals((Object[]) this.standaloneWeekdays, (Object) dateFormatSymbols.standaloneWeekdays) && Utility.arrayEquals((Object[]) this.standaloneShortWeekdays, (Object) dateFormatSymbols.standaloneShortWeekdays) && Utility.arrayEquals((Object[]) this.standaloneShorterWeekdays, (Object) dateFormatSymbols.standaloneShorterWeekdays) && Utility.arrayEquals((Object[]) this.standaloneNarrowWeekdays, (Object) dateFormatSymbols.standaloneNarrowWeekdays) && Utility.arrayEquals((Object[]) this.ampms, (Object) dateFormatSymbols.ampms) && Utility.arrayEquals((Object[]) this.ampmsNarrow, (Object) dateFormatSymbols.ampmsNarrow) && Utility.arrayEquals((Object[]) this.abbreviatedDayPeriods, (Object) dateFormatSymbols.abbreviatedDayPeriods) && Utility.arrayEquals((Object[]) this.wideDayPeriods, (Object) dateFormatSymbols.wideDayPeriods) && Utility.arrayEquals((Object[]) this.narrowDayPeriods, (Object) dateFormatSymbols.narrowDayPeriods) && Utility.arrayEquals((Object[]) this.standaloneAbbreviatedDayPeriods, (Object) dateFormatSymbols.standaloneAbbreviatedDayPeriods) && Utility.arrayEquals((Object[]) this.standaloneWideDayPeriods, (Object) dateFormatSymbols.standaloneWideDayPeriods) && Utility.arrayEquals((Object[]) this.standaloneNarrowDayPeriods, (Object) dateFormatSymbols.standaloneNarrowDayPeriods) && Utility.arrayEquals(this.timeSeparator, dateFormatSymbols.timeSeparator) && arrayOfArrayEquals(this.zoneStrings, dateFormatSymbols.zoneStrings) && this.requestedLocale.getDisplayName().equals(dateFormatSymbols.requestedLocale.getDisplayName()) && Utility.arrayEquals(this.localPatternChars, dateFormatSymbols.localPatternChars);
    }

    protected void initializeData(ULocale uLocale, String str) {
        String str2 = uLocale.getBaseName() + '+' + str;
        String keywordValue = uLocale.getKeywordValue("numbers");
        if (keywordValue != null && keywordValue.length() > 0) {
            str2 = str2 + '+' + keywordValue;
        }
        initializeData(DFSCACHE.getInstance(str2, uLocale));
    }

    void initializeData(DateFormatSymbols dateFormatSymbols) {
        this.eras = dateFormatSymbols.eras;
        this.eraNames = dateFormatSymbols.eraNames;
        this.narrowEras = dateFormatSymbols.narrowEras;
        this.months = dateFormatSymbols.months;
        this.shortMonths = dateFormatSymbols.shortMonths;
        this.narrowMonths = dateFormatSymbols.narrowMonths;
        this.standaloneMonths = dateFormatSymbols.standaloneMonths;
        this.standaloneShortMonths = dateFormatSymbols.standaloneShortMonths;
        this.standaloneNarrowMonths = dateFormatSymbols.standaloneNarrowMonths;
        this.weekdays = dateFormatSymbols.weekdays;
        this.shortWeekdays = dateFormatSymbols.shortWeekdays;
        this.shorterWeekdays = dateFormatSymbols.shorterWeekdays;
        this.narrowWeekdays = dateFormatSymbols.narrowWeekdays;
        this.standaloneWeekdays = dateFormatSymbols.standaloneWeekdays;
        this.standaloneShortWeekdays = dateFormatSymbols.standaloneShortWeekdays;
        this.standaloneShorterWeekdays = dateFormatSymbols.standaloneShorterWeekdays;
        this.standaloneNarrowWeekdays = dateFormatSymbols.standaloneNarrowWeekdays;
        this.ampms = dateFormatSymbols.ampms;
        this.ampmsNarrow = dateFormatSymbols.ampmsNarrow;
        this.timeSeparator = dateFormatSymbols.timeSeparator;
        this.shortQuarters = dateFormatSymbols.shortQuarters;
        this.quarters = dateFormatSymbols.quarters;
        this.standaloneShortQuarters = dateFormatSymbols.standaloneShortQuarters;
        this.standaloneQuarters = dateFormatSymbols.standaloneQuarters;
        this.leapMonthPatterns = dateFormatSymbols.leapMonthPatterns;
        this.shortYearNames = dateFormatSymbols.shortYearNames;
        this.shortZodiacNames = dateFormatSymbols.shortZodiacNames;
        this.abbreviatedDayPeriods = dateFormatSymbols.abbreviatedDayPeriods;
        this.wideDayPeriods = dateFormatSymbols.wideDayPeriods;
        this.narrowDayPeriods = dateFormatSymbols.narrowDayPeriods;
        this.standaloneAbbreviatedDayPeriods = dateFormatSymbols.standaloneAbbreviatedDayPeriods;
        this.standaloneWideDayPeriods = dateFormatSymbols.standaloneWideDayPeriods;
        this.standaloneNarrowDayPeriods = dateFormatSymbols.standaloneNarrowDayPeriods;
        this.zoneStrings = dateFormatSymbols.zoneStrings;
        this.localPatternChars = dateFormatSymbols.localPatternChars;
        this.capitalization = dateFormatSymbols.capitalization;
        this.actualLocale = dateFormatSymbols.actualLocale;
        this.validLocale = dateFormatSymbols.validLocale;
        this.requestedLocale = dateFormatSymbols.requestedLocale;
    }

    private static final class CalendarDataSink extends UResource.Sink {
        static final /* synthetic */ boolean $assertionsDisabled = false;
        private static final String CALENDAR_ALIAS_PREFIX = "/LOCALE/calendar/";
        private String aliasRelativePath;
        private Set<String> resourcesToVisit;
        Map<String, String[]> arrays = new TreeMap();
        Map<String, Map<String, String>> maps = new TreeMap();
        List<String> aliasPathPairs = new ArrayList();
        String currentCalendarType = null;
        String nextCalendarType = null;

        private enum AliasType {
            SAME_CALENDAR,
            DIFFERENT_CALENDAR,
            GREGORIAN,
            NONE
        }

        CalendarDataSink() {
        }

        void visitAllResources() {
            this.resourcesToVisit = null;
        }

        void preEnumerate(String str) {
            this.currentCalendarType = str;
            this.nextCalendarType = null;
            this.aliasPathPairs.clear();
        }

        /* JADX WARN: Removed duplicated region for block: B:88:0x0128 A[SYNTHETIC] */
        /* JADX WARN: Removed duplicated region for block: B:89:0x011a A[SYNTHETIC] */
        @Override // com.ibm.icu.impl.UResource.Sink
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct add '--show-bad-code' argument
        */
        public void put(com.ibm.icu.impl.UResource.Key r7, com.ibm.icu.impl.UResource.Value r8, boolean r9) {
            /*
                Method dump skipped, instructions count: 314
                To view this dump add '--comments-level debug' option
            */
            throw new UnsupportedOperationException("Method not decompiled: com.ibm.icu.text.DateFormatSymbols.CalendarDataSink.put(com.ibm.icu.impl.UResource$Key, com.ibm.icu.impl.UResource$Value, boolean):void");
        }

        protected void processResource(String str, UResource.Key key, UResource.Value value) {
            UResource.Table table = value.getTable();
            HashMap map = null;
            for (int i = 0; table.getKeyAndValue(i, key, value); i++) {
                if (!key.endsWith("%variant")) {
                    String string = key.toString();
                    if (value.getType() == 0) {
                        if (i == 0) {
                            map = new HashMap();
                            this.maps.put(str, map);
                        }
                        map.put(string, value.getString());
                    } else {
                        String str2 = str + "/" + string;
                        if ((!str2.startsWith("cyclicNameSets") || "cyclicNameSets/years/format/abbreviated".startsWith(str2) || "cyclicNameSets/zodiacs/format/abbreviated".startsWith(str2) || "cyclicNameSets/dayParts/format/abbreviated".startsWith(str2)) && !this.arrays.containsKey(str2) && !this.maps.containsKey(str2)) {
                            if (processAliasFromValue(str2, value) == AliasType.SAME_CALENDAR) {
                                this.aliasPathPairs.add(this.aliasRelativePath);
                                this.aliasPathPairs.add(str2);
                            } else if (value.getType() == 8) {
                                this.arrays.put(str2, value.getStringArray());
                            } else if (value.getType() == 2) {
                                processResource(str2, key, value);
                            }
                        }
                    }
                }
            }
        }

        private AliasType processAliasFromValue(String str, UResource.Value value) {
            int iIndexOf;
            if (value.getType() == 3) {
                String aliasString = value.getAliasString();
                if (aliasString.startsWith(CALENDAR_ALIAS_PREFIX) && aliasString.length() > 17 && (iIndexOf = aliasString.indexOf(47, 17)) > 17) {
                    String strSubstring = aliasString.substring(17, iIndexOf);
                    this.aliasRelativePath = aliasString.substring(iIndexOf + 1);
                    if (this.currentCalendarType.equals(strSubstring) && !str.equals(this.aliasRelativePath)) {
                        return AliasType.SAME_CALENDAR;
                    }
                    if (!this.currentCalendarType.equals(strSubstring) && str.equals(this.aliasRelativePath)) {
                        if (strSubstring.equals("gregorian")) {
                            return AliasType.GREGORIAN;
                        }
                        String str2 = this.nextCalendarType;
                        if (str2 == null || str2.equals(strSubstring)) {
                            this.nextCalendarType = strSubstring;
                            return AliasType.DIFFERENT_CALENDAR;
                        }
                    }
                }
                throw new ICUException("Malformed 'calendar' alias. Path: " + aliasString);
            }
            return AliasType.NONE;
        }
    }

    private DateFormatSymbols(ULocale uLocale, ICUResourceBundle iCUResourceBundle, String str) throws MissingResourceException, NoSuchElementException {
        this.eras = null;
        this.eraNames = null;
        this.narrowEras = null;
        this.months = null;
        this.shortMonths = null;
        this.narrowMonths = null;
        this.standaloneMonths = null;
        this.standaloneShortMonths = null;
        this.standaloneNarrowMonths = null;
        this.weekdays = null;
        this.shortWeekdays = null;
        this.shorterWeekdays = null;
        this.narrowWeekdays = null;
        this.standaloneWeekdays = null;
        this.standaloneShortWeekdays = null;
        this.standaloneShorterWeekdays = null;
        this.standaloneNarrowWeekdays = null;
        this.ampms = null;
        this.ampmsNarrow = null;
        this.timeSeparator = null;
        this.shortQuarters = null;
        this.quarters = null;
        this.standaloneShortQuarters = null;
        this.standaloneQuarters = null;
        this.leapMonthPatterns = null;
        this.shortYearNames = null;
        this.shortZodiacNames = null;
        this.zoneStrings = (String[][]) null;
        this.localPatternChars = null;
        this.abbreviatedDayPeriods = null;
        this.wideDayPeriods = null;
        this.narrowDayPeriods = null;
        this.standaloneAbbreviatedDayPeriods = null;
        this.standaloneWideDayPeriods = null;
        this.standaloneNarrowDayPeriods = null;
        this.capitalization = null;
        initializeData(uLocale, iCUResourceBundle, str);
    }

    @Deprecated
    protected void initializeData(ULocale uLocale, ICUResourceBundle iCUResourceBundle, String str) throws MissingResourceException, NoSuchElementException {
        ICUResourceBundle withFallback;
        Map<String, String> map;
        String str2;
        CalendarDataSink calendarDataSink = new CalendarDataSink();
        if (iCUResourceBundle == null) {
            iCUResourceBundle = (ICUResourceBundle) UResourceBundle.getBundleInstance(ICUData.ICU_BASE_NAME, uLocale);
        }
        while (str != null) {
            ICUResourceBundle iCUResourceBundleFindWithFallback = iCUResourceBundle.findWithFallback("calendar/" + str);
            if (iCUResourceBundleFindWithFallback == null) {
                if (!"gregorian".equals(str)) {
                    calendarDataSink.visitAllResources();
                } else {
                    throw new MissingResourceException("The 'gregorian' calendar type wasn't found for the locale: " + uLocale.getBaseName(), getClass().getName(), "gregorian");
                }
            } else {
                calendarDataSink.preEnumerate(str);
                iCUResourceBundleFindWithFallback.getAllItemsWithFallback("", calendarDataSink);
                if (str.equals("gregorian")) {
                    break;
                }
                str = calendarDataSink.nextCalendarType;
                if (str == null) {
                    calendarDataSink.visitAllResources();
                }
            }
            str = "gregorian";
        }
        Map<String, String[]> map2 = calendarDataSink.arrays;
        Map<String, Map<String, String>> map3 = calendarDataSink.maps;
        this.eras = map2.get("eras/abbreviated");
        this.eraNames = map2.get("eras/wide");
        this.narrowEras = map2.get("eras/narrow");
        this.months = map2.get("monthNames/format/wide");
        this.shortMonths = map2.get("monthNames/format/abbreviated");
        this.narrowMonths = map2.get("monthNames/format/narrow");
        this.standaloneMonths = map2.get("monthNames/stand-alone/wide");
        this.standaloneShortMonths = map2.get("monthNames/stand-alone/abbreviated");
        this.standaloneNarrowMonths = map2.get("monthNames/stand-alone/narrow");
        String[] strArr = map2.get("dayNames/format/wide");
        String[] strArr2 = new String[8];
        this.weekdays = strArr2;
        strArr2[0] = "";
        System.arraycopy(strArr, 0, strArr2, 1, strArr.length);
        String[] strArr3 = map2.get("dayNames/format/abbreviated");
        String[] strArr4 = new String[8];
        this.shortWeekdays = strArr4;
        strArr4[0] = "";
        System.arraycopy(strArr3, 0, strArr4, 1, strArr3.length);
        String[] strArr5 = map2.get("dayNames/format/short");
        String[] strArr6 = new String[8];
        this.shorterWeekdays = strArr6;
        strArr6[0] = "";
        System.arraycopy(strArr5, 0, strArr6, 1, strArr5.length);
        String[] strArr7 = map2.get("dayNames/format/narrow");
        if (strArr7 == null && (strArr7 = map2.get("dayNames/stand-alone/narrow")) == null && (strArr7 = map2.get("dayNames/format/abbreviated")) == null) {
            throw new MissingResourceException("Resource not found", getClass().getName(), "dayNames/format/abbreviated");
        }
        String[] strArr8 = new String[8];
        this.narrowWeekdays = strArr8;
        strArr8[0] = "";
        System.arraycopy(strArr7, 0, strArr8, 1, strArr7.length);
        String[] strArr9 = map2.get("dayNames/stand-alone/wide");
        String[] strArr10 = new String[8];
        this.standaloneWeekdays = strArr10;
        strArr10[0] = "";
        System.arraycopy(strArr9, 0, strArr10, 1, strArr9.length);
        String[] strArr11 = map2.get("dayNames/stand-alone/abbreviated");
        String[] strArr12 = new String[8];
        this.standaloneShortWeekdays = strArr12;
        strArr12[0] = "";
        System.arraycopy(strArr11, 0, strArr12, 1, strArr11.length);
        String[] strArr13 = map2.get("dayNames/stand-alone/short");
        String[] strArr14 = new String[8];
        this.standaloneShorterWeekdays = strArr14;
        strArr14[0] = "";
        System.arraycopy(strArr13, 0, strArr14, 1, strArr13.length);
        String[] strArr15 = map2.get("dayNames/stand-alone/narrow");
        String[] strArr16 = new String[8];
        this.standaloneNarrowWeekdays = strArr16;
        strArr16[0] = "";
        System.arraycopy(strArr15, 0, strArr16, 1, strArr15.length);
        this.ampms = map2.get("AmPmMarkers");
        this.ampmsNarrow = map2.get("AmPmMarkersNarrow");
        this.quarters = map2.get("quarters/format/wide");
        this.shortQuarters = map2.get("quarters/format/abbreviated");
        this.standaloneQuarters = map2.get("quarters/stand-alone/wide");
        this.standaloneShortQuarters = map2.get("quarters/stand-alone/abbreviated");
        this.abbreviatedDayPeriods = loadDayPeriodStrings(map3.get("dayPeriod/format/abbreviated"));
        this.wideDayPeriods = loadDayPeriodStrings(map3.get("dayPeriod/format/wide"));
        this.narrowDayPeriods = loadDayPeriodStrings(map3.get("dayPeriod/format/narrow"));
        this.standaloneAbbreviatedDayPeriods = loadDayPeriodStrings(map3.get("dayPeriod/stand-alone/abbreviated"));
        this.standaloneWideDayPeriods = loadDayPeriodStrings(map3.get("dayPeriod/stand-alone/wide"));
        this.standaloneNarrowDayPeriods = loadDayPeriodStrings(map3.get("dayPeriod/stand-alone/narrow"));
        for (int i = 0; i < 7; i++) {
            String str3 = LEAP_MONTH_PATTERNS_PATHS[i];
            if (str3 != null && (map = map3.get(str3)) != null && (str2 = map.get("leap")) != null) {
                if (this.leapMonthPatterns == null) {
                    this.leapMonthPatterns = new String[7];
                }
                this.leapMonthPatterns[i] = str2;
            }
        }
        this.shortYearNames = map2.get("cyclicNameSets/years/format/abbreviated");
        this.shortZodiacNames = map2.get("cyclicNameSets/zodiacs/format/abbreviated");
        this.requestedLocale = uLocale;
        ICUResourceBundle iCUResourceBundle2 = (ICUResourceBundle) UResourceBundle.getBundleInstance(ICUData.ICU_BASE_NAME, uLocale);
        this.localPatternChars = patternChars;
        ULocale uLocale2 = iCUResourceBundle2.getULocale();
        setLocale(uLocale2, uLocale2);
        this.capitalization = new HashMap();
        boolean[] zArr = {false, false};
        for (CapitalizationContextUsage capitalizationContextUsage : CapitalizationContextUsage.values()) {
            this.capitalization.put(capitalizationContextUsage, zArr);
        }
        try {
            withFallback = iCUResourceBundle2.getWithFallback("contextTransforms");
        } catch (MissingResourceException unused) {
            withFallback = null;
        }
        if (withFallback != null) {
            UResourceBundleIterator iterator = withFallback.getIterator();
            while (iterator.hasNext()) {
                UResourceBundle next = iterator.next();
                int[] intVector = next.getIntVector();
                if (intVector.length >= 2) {
                    CapitalizationContextUsage capitalizationContextUsage2 = contextUsageTypeMap.get(next.getKey());
                    if (capitalizationContextUsage2 != null) {
                        boolean[] zArr2 = new boolean[2];
                        zArr2[0] = intVector[0] != 0;
                        zArr2[1] = intVector[1] != 0;
                        this.capitalization.put(capitalizationContextUsage2, zArr2);
                    }
                }
            }
        }
        NumberingSystem numberingSystem = NumberingSystem.getInstance(uLocale);
        try {
            setTimeSeparatorString(iCUResourceBundle2.getStringWithFallback("NumberElements/" + (numberingSystem == null ? "latn" : numberingSystem.getName()) + "/symbols/timeSeparator"));
        } catch (MissingResourceException unused2) {
            setTimeSeparatorString(DEFAULT_TIME_SEPARATOR);
        }
    }

    private static final boolean arrayOfArrayEquals(Object[][] objArr, Object[][] objArr2) {
        boolean zArrayEquals = true;
        if (objArr == objArr2) {
            return true;
        }
        if (objArr == null || objArr2 == null || objArr.length != objArr2.length) {
            return false;
        }
        for (int i = 0; i < objArr.length && (zArrayEquals = Utility.arrayEquals(objArr[i], (Object) objArr2[i])); i++) {
        }
        return zArrayEquals;
    }

    private String[] loadDayPeriodStrings(Map<String, String> map) {
        String[] strArr = new String[DAY_PERIOD_KEYS.length];
        if (map != null) {
            int i = 0;
            while (true) {
                String[] strArr2 = DAY_PERIOD_KEYS;
                if (i >= strArr2.length) {
                    break;
                }
                strArr[i] = map.get(strArr2[i]);
                i++;
            }
        }
        return strArr;
    }

    private final String[] duplicate(String[] strArr) {
        return (String[]) strArr.clone();
    }

    private final String[][] duplicate(String[][] strArr) {
        String[][] strArr2 = new String[strArr.length][];
        for (int i = 0; i < strArr.length; i++) {
            strArr2[i] = duplicate(strArr[i]);
        }
        return strArr2;
    }

    public DateFormatSymbols(Calendar calendar, Locale locale) {
        this.eras = null;
        this.eraNames = null;
        this.narrowEras = null;
        this.months = null;
        this.shortMonths = null;
        this.narrowMonths = null;
        this.standaloneMonths = null;
        this.standaloneShortMonths = null;
        this.standaloneNarrowMonths = null;
        this.weekdays = null;
        this.shortWeekdays = null;
        this.shorterWeekdays = null;
        this.narrowWeekdays = null;
        this.standaloneWeekdays = null;
        this.standaloneShortWeekdays = null;
        this.standaloneShorterWeekdays = null;
        this.standaloneNarrowWeekdays = null;
        this.ampms = null;
        this.ampmsNarrow = null;
        this.timeSeparator = null;
        this.shortQuarters = null;
        this.quarters = null;
        this.standaloneShortQuarters = null;
        this.standaloneQuarters = null;
        this.leapMonthPatterns = null;
        this.shortYearNames = null;
        this.shortZodiacNames = null;
        this.zoneStrings = (String[][]) null;
        this.localPatternChars = null;
        this.abbreviatedDayPeriods = null;
        this.wideDayPeriods = null;
        this.narrowDayPeriods = null;
        this.standaloneAbbreviatedDayPeriods = null;
        this.standaloneWideDayPeriods = null;
        this.standaloneNarrowDayPeriods = null;
        this.capitalization = null;
        initializeData(ULocale.forLocale(locale), calendar.getType());
    }

    public DateFormatSymbols(Calendar calendar, ULocale uLocale) {
        this.eras = null;
        this.eraNames = null;
        this.narrowEras = null;
        this.months = null;
        this.shortMonths = null;
        this.narrowMonths = null;
        this.standaloneMonths = null;
        this.standaloneShortMonths = null;
        this.standaloneNarrowMonths = null;
        this.weekdays = null;
        this.shortWeekdays = null;
        this.shorterWeekdays = null;
        this.narrowWeekdays = null;
        this.standaloneWeekdays = null;
        this.standaloneShortWeekdays = null;
        this.standaloneShorterWeekdays = null;
        this.standaloneNarrowWeekdays = null;
        this.ampms = null;
        this.ampmsNarrow = null;
        this.timeSeparator = null;
        this.shortQuarters = null;
        this.quarters = null;
        this.standaloneShortQuarters = null;
        this.standaloneQuarters = null;
        this.leapMonthPatterns = null;
        this.shortYearNames = null;
        this.shortZodiacNames = null;
        this.zoneStrings = (String[][]) null;
        this.localPatternChars = null;
        this.abbreviatedDayPeriods = null;
        this.wideDayPeriods = null;
        this.narrowDayPeriods = null;
        this.standaloneAbbreviatedDayPeriods = null;
        this.standaloneWideDayPeriods = null;
        this.standaloneNarrowDayPeriods = null;
        this.capitalization = null;
        initializeData(uLocale, calendar.getType());
    }

    public DateFormatSymbols(Class<? extends Calendar> cls, Locale locale) {
        this(cls, ULocale.forLocale(locale));
    }

    public DateFormatSymbols(Class<? extends Calendar> cls, ULocale uLocale) {
        String str = null;
        this.eras = null;
        this.eraNames = null;
        this.narrowEras = null;
        this.months = null;
        this.shortMonths = null;
        this.narrowMonths = null;
        this.standaloneMonths = null;
        this.standaloneShortMonths = null;
        this.standaloneNarrowMonths = null;
        this.weekdays = null;
        this.shortWeekdays = null;
        this.shorterWeekdays = null;
        this.narrowWeekdays = null;
        this.standaloneWeekdays = null;
        this.standaloneShortWeekdays = null;
        this.standaloneShorterWeekdays = null;
        this.standaloneNarrowWeekdays = null;
        this.ampms = null;
        this.ampmsNarrow = null;
        this.timeSeparator = null;
        this.shortQuarters = null;
        this.quarters = null;
        this.standaloneShortQuarters = null;
        this.standaloneQuarters = null;
        this.leapMonthPatterns = null;
        this.shortYearNames = null;
        this.shortZodiacNames = null;
        this.zoneStrings = (String[][]) null;
        this.localPatternChars = null;
        this.abbreviatedDayPeriods = null;
        this.wideDayPeriods = null;
        this.narrowDayPeriods = null;
        this.standaloneAbbreviatedDayPeriods = null;
        this.standaloneWideDayPeriods = null;
        this.standaloneNarrowDayPeriods = null;
        this.capitalization = null;
        String name = cls.getName();
        String strSubstring = name.substring(name.lastIndexOf(46) + 1);
        String[][] strArr = CALENDAR_CLASSES;
        int length = strArr.length;
        int i = 0;
        while (true) {
            if (i >= length) {
                break;
            }
            String[] strArr2 = strArr[i];
            if (strArr2[0].equals(strSubstring)) {
                str = strArr2[1];
                break;
            }
            i++;
        }
        initializeData(uLocale, str == null ? strSubstring.replaceAll("Calendar", "").toLowerCase(Locale.ENGLISH) : str);
    }

    public DateFormatSymbols(ResourceBundle resourceBundle, Locale locale) {
        this(resourceBundle, ULocale.forLocale(locale));
    }

    public DateFormatSymbols(ResourceBundle resourceBundle, ULocale uLocale) throws MissingResourceException, NoSuchElementException {
        this.eras = null;
        this.eraNames = null;
        this.narrowEras = null;
        this.months = null;
        this.shortMonths = null;
        this.narrowMonths = null;
        this.standaloneMonths = null;
        this.standaloneShortMonths = null;
        this.standaloneNarrowMonths = null;
        this.weekdays = null;
        this.shortWeekdays = null;
        this.shorterWeekdays = null;
        this.narrowWeekdays = null;
        this.standaloneWeekdays = null;
        this.standaloneShortWeekdays = null;
        this.standaloneShorterWeekdays = null;
        this.standaloneNarrowWeekdays = null;
        this.ampms = null;
        this.ampmsNarrow = null;
        this.timeSeparator = null;
        this.shortQuarters = null;
        this.quarters = null;
        this.standaloneShortQuarters = null;
        this.standaloneQuarters = null;
        this.leapMonthPatterns = null;
        this.shortYearNames = null;
        this.shortZodiacNames = null;
        this.zoneStrings = (String[][]) null;
        this.localPatternChars = null;
        this.abbreviatedDayPeriods = null;
        this.wideDayPeriods = null;
        this.narrowDayPeriods = null;
        this.standaloneAbbreviatedDayPeriods = null;
        this.standaloneWideDayPeriods = null;
        this.standaloneNarrowDayPeriods = null;
        this.capitalization = null;
        initializeData(uLocale, (ICUResourceBundle) resourceBundle, CalendarUtil.getCalendarType(uLocale));
    }

    public final ULocale getLocale(ULocale.Type type) {
        return type == ULocale.ACTUAL_LOCALE ? this.actualLocale : this.validLocale;
    }

    final void setLocale(ULocale uLocale, ULocale uLocale2) {
        if ((uLocale == null) != (uLocale2 == null)) {
            throw new IllegalArgumentException();
        }
        this.validLocale = uLocale;
        this.actualLocale = uLocale2;
    }

    private void readObject(ObjectInputStream objectInputStream) throws ClassNotFoundException, IOException {
        objectInputStream.defaultReadObject();
    }
}
