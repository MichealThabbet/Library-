package com.ibm.icu.util;

import com.huawei.hms.framework.common.ContainerUtils;
import com.ibm.icu.impl.CalType;
import com.ibm.icu.impl.CalendarUtil;
import com.ibm.icu.impl.ICUCache;
import com.ibm.icu.impl.ICUData;
import com.ibm.icu.impl.ICUResourceBundle;
import com.ibm.icu.impl.SimpleCache;
import com.ibm.icu.impl.SimpleFormatterImpl;
import com.ibm.icu.impl.SoftCache;
import com.ibm.icu.impl.locale.BaseLocale;
import com.ibm.icu.impl.units.UnitsData;
import com.ibm.icu.text.DateFormat;
import com.ibm.icu.text.DateFormatSymbols;
import com.ibm.icu.text.SimpleDateFormat;
import com.ibm.icu.util.ULocale;
import it.orsozoxi.android.orsonotes.R2;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.text.StringCharacterIterator;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.MissingResourceException;
import org.apache.http.message.TokenParser;
import org.slf4j.Marker;

/* loaded from: classes2.dex */
public abstract class Calendar implements Serializable, Cloneable, Comparable<Calendar> {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    public static final int AM = 0;
    public static final int AM_PM = 9;
    public static final int APRIL = 3;
    public static final int AUGUST = 7;

    @Deprecated
    protected static final int BASE_FIELD_COUNT = 23;
    public static final int DATE = 5;
    public static final int DAY_OF_MONTH = 5;
    public static final int DAY_OF_WEEK = 7;
    public static final int DAY_OF_WEEK_IN_MONTH = 8;
    public static final int DAY_OF_YEAR = 6;
    public static final int DECEMBER = 11;
    public static final int DOW_LOCAL = 18;
    public static final int DST_OFFSET = 16;
    protected static final int EPOCH_JULIAN_DAY = 2440588;
    public static final int ERA = 0;
    public static final int EXTENDED_YEAR = 19;
    public static final int FEBRUARY = 1;
    private static final int FIELD_DIFF_MAX_INT = Integer.MAX_VALUE;
    public static final int FRIDAY = 6;
    protected static final int GREATEST_MINIMUM = 1;
    public static final int HOUR = 10;
    public static final int HOUR_OF_DAY = 11;
    protected static final int INTERNALLY_SET = 1;
    public static final int IS_LEAP_MONTH = 22;
    public static final int JANUARY = 0;
    protected static final int JAN_1_1_JULIAN_DAY = 1721426;
    public static final int JULIAN_DAY = 20;
    public static final int JULY = 6;
    public static final int JUNE = 5;
    protected static final int LEAST_MAXIMUM = 2;
    public static final int MARCH = 2;
    protected static final int MAXIMUM = 3;

    @Deprecated
    protected static final int MAX_FIELD_COUNT = 32;
    private static final int MAX_HOURS = 548;
    protected static final int MAX_JULIAN = 2130706432;
    protected static final long MAX_MILLIS = 183882168921600000L;
    public static final int MAY = 4;
    public static final int MILLISECOND = 14;
    public static final int MILLISECONDS_IN_DAY = 21;
    protected static final int MINIMUM = 0;
    protected static final int MINIMUM_USER_STAMP = 2;
    public static final int MINUTE = 12;
    protected static final long MIN_MILLIS = -184303902528000000L;
    public static final int MONDAY = 2;
    public static final int MONTH = 2;
    public static final int NOVEMBER = 10;
    public static final int OCTOBER = 9;
    protected static final long ONE_DAY = 86400000;
    protected static final int ONE_HOUR = 3600000;
    protected static final int ONE_MINUTE = 60000;
    protected static final int ONE_SECOND = 1000;
    protected static final long ONE_WEEK = 604800000;
    public static final int PM = 1;
    private static final char QUOTE = '\'';
    protected static final int RESOLVE_REMAP = 32;
    public static final int SATURDAY = 7;
    public static final int SECOND = 13;
    public static final int SEPTEMBER = 8;
    public static final int SUNDAY = 1;
    public static final int THURSDAY = 5;
    public static final int TUESDAY = 3;
    public static final int UNDECIMBER = 12;
    protected static final int UNSET = 0;
    public static final int WALLTIME_FIRST = 1;
    public static final int WALLTIME_LAST = 0;
    public static final int WALLTIME_NEXT_VALID = 2;
    public static final int WEDNESDAY = 4;

    @Deprecated
    public static final int WEEKDAY = 0;

    @Deprecated
    public static final int WEEKEND = 1;

    @Deprecated
    public static final int WEEKEND_CEASE = 3;

    @Deprecated
    public static final int WEEKEND_ONSET = 2;
    public static final int WEEK_OF_MONTH = 4;
    public static final int WEEK_OF_YEAR = 3;
    public static final int YEAR = 1;
    public static final int YEAR_WOY = 17;
    public static final int ZONE_OFFSET = 15;
    private static final long serialVersionUID = 6222646104888790989L;
    private ULocale actualLocale;
    private transient boolean areAllFieldsSet;
    private transient boolean areFieldsSet;
    private transient boolean areFieldsVirtuallySet;
    private transient int[] fields;
    private int firstDayOfWeek;
    private transient int gregorianDayOfMonth;
    private transient int gregorianDayOfYear;
    private transient int gregorianMonth;
    private transient int gregorianYear;
    private transient int internalSetMask;
    private transient boolean isTimeSet;
    private boolean lenient;
    private int minimalDaysInFirstWeek;
    private transient int nextStamp;
    private int repeatedWallTime;
    private int skippedWallTime;
    private transient int[] stamp;
    private long time;
    private ULocale validLocale;
    private int weekendCease;
    private int weekendCeaseMillis;
    private int weekendOnset;
    private int weekendOnsetMillis;
    private TimeZone zone;
    protected static final Date MIN_DATE = new Date(-184303902528000000L);
    protected static final Date MAX_DATE = new Date(183882168921600000L);
    private static int STAMP_MAX = 10000;
    private static final ICUCache<String, PatternData> PATTERN_CACHE = new SimpleCache();
    private static final String[] DEFAULT_PATTERNS = {"HH:mm:ss z", "HH:mm:ss z", "HH:mm:ss", "HH:mm", "EEEE, yyyy MMMM dd", "yyyy MMMM d", "yyyy MMM d", "yy/MM/dd", "{1} {0}", "{1} {0}", "{1} {0}", "{1} {0}", "{1} {0}"};
    private static final String[] TIME_SKELETONS = {"jmmsszzzz", "jmmssz", "jmmss", "jmm"};
    protected static final int MIN_JULIAN = -2130706432;
    private static final int[][] LIMITS = {new int[0], new int[0], new int[0], new int[0], new int[0], new int[0], new int[0], new int[]{1, 1, 7, 7}, new int[0], new int[]{0, 0, 1, 1}, new int[]{0, 0, 11, 11}, new int[]{0, 0, 23, 23}, new int[]{0, 0, 59, 59}, new int[]{0, 0, 59, 59}, new int[]{0, 0, 999, 999}, new int[]{-43200000, -43200000, 43200000, 43200000}, new int[]{0, 0, 3600000, 3600000}, new int[0], new int[]{1, 1, 7, 7}, new int[0], new int[]{MIN_JULIAN, MIN_JULIAN, 2130706432, 2130706432}, new int[]{0, 0, 86399999, 86399999}, new int[]{0, 0, 1, 1}};
    private static final WeekDataCache WEEK_DATA_CACHE = new WeekDataCache();
    static final int[][][] DATE_PRECEDENCE = {new int[][]{new int[]{5}, new int[]{3, 7}, new int[]{4, 7}, new int[]{8, 7}, new int[]{3, 18}, new int[]{4, 18}, new int[]{8, 18}, new int[]{6}, new int[]{37, 1}, new int[]{35, 17}}, new int[][]{new int[]{3}, new int[]{4}, new int[]{8}, new int[]{40, 7}, new int[]{40, 18}}};
    static final int[][][] DOW_PRECEDENCE = {new int[][]{new int[]{7}, new int[]{18}}};
    private static final int[] FIND_ZONE_TRANSITION_TIME_UNITS = {3600000, 1800000, 60000, 1000};
    private static final int[][] GREGORIAN_MONTH_COUNT = {new int[]{31, 31, 0, 0}, new int[]{28, 29, 31, 31}, new int[]{31, 31, 59, 60}, new int[]{30, 30, 90, 91}, new int[]{31, 31, 120, 121}, new int[]{30, 30, 151, 152}, new int[]{31, 31, 181, 182}, new int[]{31, 31, 212, 213}, new int[]{30, 30, 243, 244}, new int[]{31, 31, 273, 274}, new int[]{30, 30, 304, 305}, new int[]{31, 31, R2.attr.cornerRadius, R2.attr.cornerSize}};
    private static final String[] FIELD_NAME = {"ERA", "YEAR", "MONTH", "WEEK_OF_YEAR", "WEEK_OF_MONTH", "DAY_OF_MONTH", "DAY_OF_YEAR", "DAY_OF_WEEK", "DAY_OF_WEEK_IN_MONTH", "AM_PM", "HOUR", "HOUR_OF_DAY", "MINUTE", "SECOND", "MILLISECOND", "ZONE_OFFSET", "DST_OFFSET", "YEAR_WOY", "DOW_LOCAL", "EXTENDED_YEAR", "JULIAN_DAY", "MILLISECONDS_IN_DAY"};

    protected static final long julianDayToMillis(int i) {
        return (i - EPOCH_JULIAN_DAY) * 86400000;
    }

    protected int getDefaultDayInMonth(int i, int i2) {
        return 1;
    }

    protected int getDefaultMonthInYear(int i) {
        return 0;
    }

    public String getType() {
        return "unknown";
    }

    protected abstract int handleComputeMonthStart(int i, int i2, boolean z);

    protected int[] handleCreateFields() {
        return new int[23];
    }

    protected abstract int handleGetExtendedYear();

    protected abstract int handleGetLimit(int i, int i2);

    @Deprecated
    public boolean haveDefaultCentury() {
        return true;
    }

    protected Calendar() {
        this(TimeZone.getDefault(), ULocale.getDefault(ULocale.Category.FORMAT));
    }

    protected Calendar(TimeZone timeZone, Locale locale) {
        this(timeZone, ULocale.forLocale(locale));
    }

    protected Calendar(TimeZone timeZone, ULocale uLocale) {
        this.lenient = true;
        this.repeatedWallTime = 0;
        this.skippedWallTime = 0;
        this.nextStamp = 2;
        this.zone = timeZone;
        setWeekData(getRegionForCalendar(uLocale));
        setCalendarLocale(uLocale);
        initInternal();
    }

    private void setCalendarLocale(ULocale uLocale) {
        if (uLocale.getVariant().length() != 0 || uLocale.getKeywords() != null) {
            StringBuilder sb = new StringBuilder();
            sb.append(uLocale.getLanguage());
            String script = uLocale.getScript();
            if (script.length() > 0) {
                sb.append(BaseLocale.SEP);
                sb.append(script);
            }
            String country = uLocale.getCountry();
            if (country.length() > 0) {
                sb.append(BaseLocale.SEP);
                sb.append(country);
            }
            String keywordValue = uLocale.getKeywordValue("calendar");
            if (keywordValue != null) {
                sb.append("@calendar=");
                sb.append(keywordValue);
            }
            uLocale = new ULocale(sb.toString());
        }
        setLocale(uLocale, uLocale);
    }

    private void recalculateStamp() {
        int[] iArr;
        this.nextStamp = 1;
        for (int i = 0; i < this.stamp.length; i++) {
            int i2 = STAMP_MAX;
            int i3 = -1;
            int i4 = 0;
            while (true) {
                iArr = this.stamp;
                if (i4 >= iArr.length) {
                    break;
                }
                if (iArr[i4] > this.nextStamp && iArr[i4] < i2) {
                    i2 = iArr[i4];
                    i3 = i4;
                }
                i4++;
            }
            if (i3 < 0) {
                break;
            }
            int i5 = this.nextStamp + 1;
            this.nextStamp = i5;
            iArr[i3] = i5;
        }
        this.nextStamp++;
    }

    private void initInternal() {
        int[] iArrHandleCreateFields = handleCreateFields();
        this.fields = iArrHandleCreateFields;
        if (iArrHandleCreateFields != null) {
            if (iArrHandleCreateFields.length >= 23 && iArrHandleCreateFields.length <= 32) {
                this.stamp = new int[iArrHandleCreateFields.length];
                int i = 4718695;
                for (int i2 = 23; i2 < this.fields.length; i2++) {
                    i |= 1 << i2;
                }
                this.internalSetMask = i;
                return;
            }
        }
        throw new IllegalStateException("Invalid fields[]");
    }

    public static Calendar getInstance() {
        return getInstanceInternal(null, null);
    }

    public static Calendar getInstance(TimeZone timeZone) {
        return getInstanceInternal(timeZone, null);
    }

    public static Calendar getInstance(Locale locale) {
        return getInstanceInternal(null, ULocale.forLocale(locale));
    }

    public static Calendar getInstance(ULocale uLocale) {
        return getInstanceInternal(null, uLocale);
    }

    public static Calendar getInstance(TimeZone timeZone, Locale locale) {
        return getInstanceInternal(timeZone, ULocale.forLocale(locale));
    }

    public static Calendar getInstance(TimeZone timeZone, ULocale uLocale) {
        return getInstanceInternal(timeZone, uLocale);
    }

    private static Calendar getInstanceInternal(TimeZone timeZone, ULocale uLocale) {
        if (uLocale == null) {
            uLocale = ULocale.getDefault(ULocale.Category.FORMAT);
        }
        if (timeZone == null) {
            timeZone = TimeZone.forULocaleOrDefault(uLocale);
        }
        Calendar calendarCreateInstance = createInstance(uLocale);
        calendarCreateInstance.setTimeZone(timeZone);
        calendarCreateInstance.setTimeInMillis(System.currentTimeMillis());
        return calendarCreateInstance;
    }

    private static String getRegionForCalendar(ULocale uLocale) {
        String regionForSupplementalData = ULocale.getRegionForSupplementalData(uLocale, true);
        return regionForSupplementalData.length() == 0 ? UnitsData.Constants.DEFAULT_REGION : regionForSupplementalData;
    }

    private static CalType getCalendarTypeForLocale(ULocale uLocale) {
        String calendarType = CalendarUtil.getCalendarType(uLocale);
        if (calendarType != null) {
            String lowerCase = calendarType.toLowerCase(Locale.ENGLISH);
            for (CalType calType : CalType.values()) {
                if (lowerCase.equals(calType.getId())) {
                    return calType;
                }
            }
        }
        return CalType.UNKNOWN;
    }

    private static Calendar createInstance(ULocale uLocale) {
        TimeZone timeZoneForULocaleOrDefault = TimeZone.forULocaleOrDefault(uLocale);
        CalType calendarTypeForLocale = getCalendarTypeForLocale(uLocale);
        if (calendarTypeForLocale == CalType.UNKNOWN) {
            calendarTypeForLocale = CalType.GREGORIAN;
        }
        switch (AnonymousClass1.$SwitchMap$com$ibm$icu$impl$CalType[calendarTypeForLocale.ordinal()]) {
            case 1:
                return new GregorianCalendar(timeZoneForULocaleOrDefault, uLocale);
            case 2:
                GregorianCalendar gregorianCalendar = new GregorianCalendar(timeZoneForULocaleOrDefault, uLocale);
                gregorianCalendar.setFirstDayOfWeek(2);
                gregorianCalendar.setMinimalDaysInFirstWeek(4);
                return gregorianCalendar;
            case 3:
                return new BuddhistCalendar(timeZoneForULocaleOrDefault, uLocale);
            case 4:
                return new ChineseCalendar(timeZoneForULocaleOrDefault, uLocale);
            case 5:
                return new CopticCalendar(timeZoneForULocaleOrDefault, uLocale);
            case 6:
                return new DangiCalendar(timeZoneForULocaleOrDefault, uLocale);
            case 7:
                return new EthiopicCalendar(timeZoneForULocaleOrDefault, uLocale);
            case 8:
                EthiopicCalendar ethiopicCalendar = new EthiopicCalendar(timeZoneForULocaleOrDefault, uLocale);
                ethiopicCalendar.setAmeteAlemEra(true);
                return ethiopicCalendar;
            case 9:
                return new HebrewCalendar(timeZoneForULocaleOrDefault, uLocale);
            case 10:
                return new IndianCalendar(timeZoneForULocaleOrDefault, uLocale);
            case 11:
            case 12:
            case 13:
            case 14:
            case 15:
                return new IslamicCalendar(timeZoneForULocaleOrDefault, uLocale);
            case 16:
                return new JapaneseCalendar(timeZoneForULocaleOrDefault, uLocale);
            case 17:
                return new PersianCalendar(timeZoneForULocaleOrDefault, uLocale);
            case 18:
                return new TaiwanCalendar(timeZoneForULocaleOrDefault, uLocale);
            default:
                throw new IllegalArgumentException("Unknown calendar type");
        }
    }

    public static Locale[] getAvailableLocales() {
        return ICUResourceBundle.getAvailableLocales();
    }

    public static ULocale[] getAvailableULocales() {
        return ICUResourceBundle.getAvailableULocales();
    }

    public static final String[] getKeywordValuesForLocale(String str, ULocale uLocale, boolean z) {
        UResourceBundle uResourceBundle;
        String regionForSupplementalData = ULocale.getRegionForSupplementalData(uLocale, true);
        ArrayList arrayList = new ArrayList();
        UResourceBundle uResourceBundle2 = UResourceBundle.getBundleInstance(ICUData.ICU_BASE_NAME, "supplementalData", ICUResourceBundle.ICU_DATA_CLASS_LOADER).get("calendarPreferenceData");
        try {
            uResourceBundle = uResourceBundle2.get(regionForSupplementalData);
        } catch (MissingResourceException unused) {
            uResourceBundle = uResourceBundle2.get(UnitsData.Constants.DEFAULT_REGION);
        }
        String[] stringArray = uResourceBundle.getStringArray();
        if (z) {
            return stringArray;
        }
        for (String str2 : stringArray) {
            arrayList.add(str2);
        }
        for (CalType calType : CalType.values()) {
            if (!arrayList.contains(calType.getId())) {
                arrayList.add(calType.getId());
            }
        }
        return (String[]) arrayList.toArray(new String[arrayList.size()]);
    }

    public final Date getTime() {
        return new Date(getTimeInMillis());
    }

    public final void setTime(Date date) {
        setTimeInMillis(date.getTime());
    }

    public long getTimeInMillis() {
        if (!this.isTimeSet) {
            updateTime();
        }
        return this.time;
    }

    public void setTimeInMillis(long j) {
        if (j > 183882168921600000L) {
            if (!isLenient()) {
                throw new IllegalArgumentException("millis value greater than upper bounds for a Calendar : " + j);
            }
            j = 183882168921600000L;
        } else if (j < -184303902528000000L) {
            if (!isLenient()) {
                throw new IllegalArgumentException("millis value less than lower bounds for a Calendar : " + j);
            }
            j = -184303902528000000L;
        }
        this.time = j;
        this.areAllFieldsSet = false;
        this.areFieldsSet = false;
        this.areFieldsVirtuallySet = true;
        this.isTimeSet = true;
        int i = 0;
        while (true) {
            int[] iArr = this.fields;
            if (i >= iArr.length) {
                return;
            }
            this.stamp[i] = 0;
            iArr[i] = 0;
            i++;
        }
    }

    public final int get(int i) {
        complete();
        return this.fields[i];
    }

    protected final int internalGet(int i) {
        return this.fields[i];
    }

    protected final int internalGet(int i, int i2) {
        return this.stamp[i] > 0 ? this.fields[i] : i2;
    }

    public final void set(int i, int i2) {
        if (this.areFieldsVirtuallySet) {
            computeFields();
        }
        this.fields[i] = i2;
        if (this.nextStamp == STAMP_MAX) {
            recalculateStamp();
        }
        int[] iArr = this.stamp;
        int i3 = this.nextStamp;
        this.nextStamp = i3 + 1;
        iArr[i] = i3;
        this.areFieldsVirtuallySet = false;
        this.areFieldsSet = false;
        this.isTimeSet = false;
    }

    public final void set(int i, int i2, int i3) {
        set(1, i);
        set(2, i2);
        set(5, i3);
    }

    public final void set(int i, int i2, int i3, int i4, int i5) {
        set(1, i);
        set(2, i2);
        set(5, i3);
        set(11, i4);
        set(12, i5);
    }

    public final void set(int i, int i2, int i3, int i4, int i5, int i6) {
        set(1, i);
        set(2, i2);
        set(5, i3);
        set(11, i4);
        set(12, i5);
        set(13, i6);
    }

    /* JADX WARN: Removed duplicated region for block: B:9:0x001f A[PHI: r4
      0x001f: PHI (r4v3 int) = (r4v2 int), (r4v7 int) binds: [B:8:0x001d, B:5:0x0010] A[DONT_GENERATE, DONT_INLINE]] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    private static int gregoYearFromIslamicStart(int r5) {
        /*
            r0 = 0
            r1 = 33
            r2 = 1397(0x575, float:1.958E-42)
            r3 = 1
            if (r5 < r2) goto L13
            int r2 = r5 + (-1397)
            int r4 = r2 / 67
            int r2 = r2 % 67
            int r4 = r4 * 2
            if (r2 < r1) goto L20
            goto L1f
        L13:
            int r2 = r5 + (-1396)
            int r4 = r2 / 67
            int r4 = r4 - r3
            int r2 = -r2
            int r2 = r2 % 67
            int r4 = r4 * 2
            if (r2 > r1) goto L20
        L1f:
            r0 = 1
        L20:
            int r4 = r4 + r0
            int r5 = r5 + 579
            int r5 = r5 - r4
            return r5
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ibm.icu.util.Calendar.gregoYearFromIslamicStart(int):int");
    }

    @Deprecated
    public final int getRelatedYear() {
        int i = get(19);
        CalType calType = CalType.GREGORIAN;
        String type = getType();
        CalType[] calTypeArrValues = CalType.values();
        int length = calTypeArrValues.length;
        int i2 = 0;
        while (true) {
            if (i2 >= length) {
                break;
            }
            CalType calType2 = calTypeArrValues[i2];
            if (type.equals(calType2.getId())) {
                calType = calType2;
                break;
            }
            i2++;
        }
        switch (calType) {
            case CHINESE:
                return i - 2637;
            case COPTIC:
                return i + 284;
            case DANGI:
                return i - 2333;
            case ETHIOPIC:
                return i + 8;
            case ETHIOPIC_AMETE_ALEM:
                return i - 5492;
            case HEBREW:
                return i - 3760;
            case INDIAN:
                return i + 79;
            case ISLAMIC_CIVIL:
            case ISLAMIC_UMALQURA:
            case ISLAMIC_TBLA:
            case ISLAMIC_RGSA:
            case ISLAMIC:
                return gregoYearFromIslamicStart(i);
            case JAPANESE:
            default:
                return i;
            case PERSIAN:
                return i + R2.attr.layout_constraintRight_creator;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:9:0x001f A[PHI: r4
      0x001f: PHI (r4v3 int) = (r4v2 int), (r4v7 int) binds: [B:8:0x001d, B:5:0x0010] A[DONT_GENERATE, DONT_INLINE]] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    private static int firstIslamicStartYearFromGrego(int r5) {
        /*
            r0 = 0
            r1 = 32
            r2 = 1977(0x7b9, float:2.77E-42)
            r3 = 1
            if (r5 < r2) goto L13
            int r2 = r5 + (-1977)
            int r4 = r2 / 65
            int r2 = r2 % 65
            int r4 = r4 * 2
            if (r2 < r1) goto L20
            goto L1f
        L13:
            int r2 = r5 + (-1976)
            int r4 = r2 / 65
            int r4 = r4 - r3
            int r2 = -r2
            int r2 = r2 % 65
            int r4 = r4 * 2
            if (r2 > r1) goto L20
        L1f:
            r0 = 1
        L20:
            int r4 = r4 + r0
            int r5 = r5 + (-579)
            int r5 = r5 + r4
            return r5
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ibm.icu.util.Calendar.firstIslamicStartYearFromGrego(int):int");
    }

    @Deprecated
    public final void setRelatedYear(int i) {
        CalType calType = CalType.GREGORIAN;
        String type = getType();
        CalType[] calTypeArrValues = CalType.values();
        int length = calTypeArrValues.length;
        int i2 = 0;
        while (true) {
            if (i2 >= length) {
                break;
            }
            CalType calType2 = calTypeArrValues[i2];
            if (type.equals(calType2.getId())) {
                calType = calType2;
                break;
            }
            i2++;
        }
        switch (calType) {
            case CHINESE:
                i += R2.id.fab_label;
                break;
            case COPTIC:
                i -= 284;
                break;
            case DANGI:
                i += R2.drawable.material_ic_keyboard_arrow_right_black_24dp;
                break;
            case ETHIOPIC:
                i -= 8;
                break;
            case ETHIOPIC_AMETE_ALEM:
                i += R2.styleable.ConstraintSet_android_id;
                break;
            case HEBREW:
                i += R2.string.reminders;
                break;
            case INDIAN:
                i -= 79;
                break;
            case ISLAMIC_CIVIL:
            case ISLAMIC_UMALQURA:
            case ISLAMIC_TBLA:
            case ISLAMIC_RGSA:
            case ISLAMIC:
                i = firstIslamicStartYearFromGrego(i);
                break;
            case PERSIAN:
                i -= 622;
                break;
        }
        set(19, i);
    }

    public final void clear() {
        int i = 0;
        while (true) {
            int[] iArr = this.fields;
            if (i < iArr.length) {
                this.stamp[i] = 0;
                iArr[i] = 0;
                i++;
            } else {
                this.areFieldsVirtuallySet = false;
                this.areAllFieldsSet = false;
                this.areFieldsSet = false;
                this.isTimeSet = false;
                return;
            }
        }
    }

    public final void clear(int i) {
        if (this.areFieldsVirtuallySet) {
            computeFields();
        }
        this.fields[i] = 0;
        this.stamp[i] = 0;
        this.areFieldsVirtuallySet = false;
        this.areAllFieldsSet = false;
        this.areFieldsSet = false;
        this.isTimeSet = false;
    }

    public final boolean isSet(int i) {
        return this.areFieldsVirtuallySet || this.stamp[i] != 0;
    }

    protected void complete() {
        if (!this.isTimeSet) {
            updateTime();
        }
        if (this.areFieldsSet) {
            return;
        }
        computeFields();
        this.areFieldsSet = true;
        this.areAllFieldsSet = true;
    }

    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        Calendar calendar = (Calendar) obj;
        return isEquivalentTo(calendar) && getTimeInMillis() == calendar.getTime().getTime();
    }

    public boolean isEquivalentTo(Calendar calendar) {
        return getClass() == calendar.getClass() && isLenient() == calendar.isLenient() && getFirstDayOfWeek() == calendar.getFirstDayOfWeek() && getMinimalDaysInFirstWeek() == calendar.getMinimalDaysInFirstWeek() && getTimeZone().equals(calendar.getTimeZone()) && getRepeatedWallTimeOption() == calendar.getRepeatedWallTimeOption() && getSkippedWallTimeOption() == calendar.getSkippedWallTimeOption();
    }

    public int hashCode() {
        boolean z = this.lenient;
        return (z ? 1 : 0) | (this.firstDayOfWeek << 1) | (this.minimalDaysInFirstWeek << 4) | (this.repeatedWallTime << 7) | (this.skippedWallTime << 9) | (this.zone.hashCode() << 11);
    }

    private long compare(Object obj) {
        long time;
        if (obj instanceof Calendar) {
            time = ((Calendar) obj).getTimeInMillis();
        } else if (obj instanceof Date) {
            time = ((Date) obj).getTime();
        } else {
            throw new IllegalArgumentException(obj + "is not a Calendar or Date");
        }
        return getTimeInMillis() - time;
    }

    public boolean before(Object obj) {
        return compare(obj) < 0;
    }

    public boolean after(Object obj) {
        return compare(obj) > 0;
    }

    public int getActualMaximum(int i) {
        if (i != 0 && i != 18) {
            if (i == 5) {
                Calendar calendar = (Calendar) clone();
                calendar.setLenient(true);
                calendar.prepareGetActual(i, false);
                return handleGetMonthLength(calendar.get(19), calendar.get(2));
            }
            if (i == 6) {
                Calendar calendar2 = (Calendar) clone();
                calendar2.setLenient(true);
                calendar2.prepareGetActual(i, false);
                return handleGetYearLength(calendar2.get(19));
            }
            if (i != 7 && i != 20 && i != 21) {
                switch (i) {
                    case 9:
                    case 10:
                    case 11:
                    case 12:
                    case 13:
                    case 14:
                    case 15:
                    case 16:
                        break;
                    default:
                        return getActualHelper(i, getLeastMaximum(i), getMaximum(i));
                }
            }
        }
        return getMaximum(i);
    }

    public int getActualMinimum(int i) {
        switch (i) {
            case 7:
            case 9:
            case 10:
            case 11:
            case 12:
            case 13:
            case 14:
            case 15:
            case 16:
            case 18:
            case 20:
            case 21:
                return getMinimum(i);
            case 8:
            case 17:
            case 19:
            default:
                return getActualHelper(i, getGreatestMinimum(i), getMinimum(i));
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:26:0x004b  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    protected void prepareGetActual(int r6, boolean r7) {
        /*
            r5 = this;
            r0 = 21
            r1 = 0
            r5.set(r0, r1)
            r0 = 1
            if (r6 == r0) goto L4b
            r1 = 2
            r2 = 5
            if (r6 == r1) goto L43
            r1 = 3
            r3 = 7
            if (r6 == r1) goto L34
            r4 = 4
            if (r6 == r4) goto L34
            r7 = 8
            if (r6 == r7) goto L29
            r7 = 17
            if (r6 == r7) goto L21
            r7 = 19
            if (r6 == r7) goto L4b
            goto L53
        L21:
            int r7 = r5.getGreatestMinimum(r1)
            r5.set(r1, r7)
            goto L53
        L29:
            r5.set(r2, r0)
            int r7 = r5.get(r3)
            r5.set(r3, r7)
            goto L53
        L34:
            int r1 = r5.firstDayOfWeek
            if (r7 == 0) goto L3f
            int r1 = r1 + 6
            int r1 = r1 % r3
            if (r1 >= r0) goto L3f
            int r1 = r1 + 7
        L3f:
            r5.set(r3, r1)
            goto L53
        L43:
            int r7 = r5.getGreatestMinimum(r2)
            r5.set(r2, r7)
            goto L53
        L4b:
            r7 = 6
            int r0 = r5.getGreatestMinimum(r7)
            r5.set(r7, r0)
        L53:
            int r7 = r5.getGreatestMinimum(r6)
            r5.set(r6, r7)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ibm.icu.util.Calendar.prepareGetActual(int, boolean):void");
    }

    private int getActualHelper(int i, int i2, int i3) {
        int i4;
        if (i2 == i3) {
            return i2;
        }
        int i5 = i3 > i2 ? 1 : -1;
        Calendar calendar = (Calendar) clone();
        calendar.complete();
        calendar.setLenient(true);
        calendar.prepareGetActual(i, i5 < 0);
        calendar.set(i, i2);
        if (calendar.get(i) != i2 && i != 4 && i5 > 0) {
            return i2;
        }
        do {
            i4 = i2 + i5;
            calendar.add(i, i5);
            if (calendar.get(i) != i4) {
                break;
            }
            i2 = i4;
        } while (i4 != i3);
        return i2;
    }

    public final void roll(int i, boolean z) {
        roll(i, z ? 1 : -1);
    }

    /* JADX WARN: Removed duplicated region for block: B:102:0x01dd  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public void roll(int r12, int r13) {
        /*
            Method dump skipped, instructions count: 588
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ibm.icu.util.Calendar.roll(int, int):void");
    }

    /* JADX WARN: Removed duplicated region for block: B:20:0x004f  */
    /* JADX WARN: Removed duplicated region for block: B:21:0x0060  */
    /* JADX WARN: Removed duplicated region for block: B:24:0x006b  */
    /* JADX WARN: Removed duplicated region for block: B:65:? A[ADDED_TO_REGION, RETURN, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public void add(int r13, int r14) {
        /*
            Method dump skipped, instructions count: 336
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ibm.icu.util.Calendar.add(int, int):void");
    }

    public String getDisplayName(Locale locale) {
        return getClass().getName();
    }

    public String getDisplayName(ULocale uLocale) {
        return getClass().getName();
    }

    @Override // java.lang.Comparable
    public int compareTo(Calendar calendar) {
        long timeInMillis = getTimeInMillis() - calendar.getTimeInMillis();
        if (timeInMillis < 0) {
            return -1;
        }
        return timeInMillis > 0 ? 1 : 0;
    }

    public DateFormat getDateTimeFormat(int i, int i2, Locale locale) {
        return formatHelper(this, ULocale.forLocale(locale), i, i2);
    }

    public DateFormat getDateTimeFormat(int i, int i2, ULocale uLocale) {
        return formatHelper(this, uLocale, i, i2);
    }

    protected DateFormat handleGetDateFormat(String str, Locale locale) {
        return handleGetDateFormat(str, (String) null, ULocale.forLocale(locale));
    }

    protected DateFormat handleGetDateFormat(String str, String str2, Locale locale) {
        return handleGetDateFormat(str, str2, ULocale.forLocale(locale));
    }

    protected DateFormat handleGetDateFormat(String str, ULocale uLocale) {
        return handleGetDateFormat(str, (String) null, uLocale);
    }

    protected DateFormat handleGetDateFormat(String str, String str2, ULocale uLocale) {
        FormatConfiguration formatConfiguration = new FormatConfiguration();
        formatConfiguration.pattern = str;
        formatConfiguration.override = str2;
        formatConfiguration.formatData = new DateFormatSymbols(this, uLocale);
        formatConfiguration.loc = uLocale;
        formatConfiguration.cal = this;
        return SimpleDateFormat.getInstance(formatConfiguration);
    }

    private static DateFormat formatHelper(Calendar calendar, ULocale uLocale, int i, int i2) {
        String rawPattern;
        if (i2 < -1 || i2 > 3) {
            throw new IllegalArgumentException("Illegal time style " + i2);
        }
        if (i < -1 || i > 3) {
            throw new IllegalArgumentException("Illegal date style " + i);
        }
        PatternData patternDataMake = PatternData.make(calendar, uLocale);
        String strMergeOverrideStrings = null;
        if (i2 >= 0 && i >= 0) {
            String dateTimePattern = patternDataMake.getDateTimePattern(i);
            int i3 = i + 4;
            rawPattern = SimpleFormatterImpl.formatRawPattern(dateTimePattern, 2, 2, patternDataMake.patterns[i2], patternDataMake.patterns[i3]);
            if (patternDataMake.overrides != null) {
                strMergeOverrideStrings = mergeOverrideStrings(patternDataMake.patterns[i3], patternDataMake.patterns[i2], patternDataMake.overrides[i3], patternDataMake.overrides[i2]);
            }
        } else if (i2 >= 0) {
            rawPattern = patternDataMake.patterns[i2];
            if (patternDataMake.overrides != null) {
                strMergeOverrideStrings = patternDataMake.overrides[i2];
            }
        } else {
            if (i < 0) {
                throw new IllegalArgumentException("No date or time style specified");
            }
            int i4 = i + 4;
            rawPattern = patternDataMake.patterns[i4];
            if (patternDataMake.overrides != null) {
                strMergeOverrideStrings = patternDataMake.overrides[i4];
            }
        }
        DateFormat dateFormatHandleGetDateFormat = calendar.handleGetDateFormat(rawPattern, strMergeOverrideStrings, uLocale);
        dateFormatHandleGetDateFormat.setCalendar(calendar);
        return dateFormatHandleGetDateFormat;
    }

    static class PatternData {
        private String[] overrides;
        private String[] patterns;

        public PatternData(String[] strArr, String[] strArr2) {
            this.patterns = strArr;
            this.overrides = strArr2;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public String getDateTimePattern(int i) {
            return this.patterns[this.patterns.length >= 13 ? 8 + i + 1 : 8];
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static PatternData make(Calendar calendar, ULocale uLocale) {
            PatternData patternData;
            String type = calendar.getType();
            String str = uLocale.getBaseName() + Marker.ANY_NON_NULL_MARKER + type;
            PatternData patternData2 = (PatternData) Calendar.PATTERN_CACHE.get(str);
            if (patternData2 == null) {
                try {
                    patternData = Calendar.getPatternData(uLocale, type);
                } catch (MissingResourceException unused) {
                    patternData = new PatternData(Calendar.DEFAULT_PATTERNS, null);
                }
                patternData2 = patternData;
                Calendar.PATTERN_CACHE.put(str, patternData2);
            }
            return patternData2;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Removed duplicated region for block: B:21:0x008c  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public static com.ibm.icu.util.Calendar.PatternData getPatternData(com.ibm.icu.util.ULocale r8, java.lang.String r9) throws java.util.MissingResourceException {
        /*
            java.lang.String r0 = "com/ibm/icu/impl/data/icudt69b"
            com.ibm.icu.util.UResourceBundle r0 = com.ibm.icu.util.UResourceBundle.getBundleInstance(r0, r8)
            com.ibm.icu.impl.ICUResourceBundle r0 = (com.ibm.icu.impl.ICUResourceBundle) r0
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            java.lang.String r2 = "calendar/"
            r1.append(r2)
            r1.append(r9)
            java.lang.String r9 = "/DateTimePatterns"
            r1.append(r9)
            java.lang.String r9 = r1.toString()
            com.ibm.icu.impl.ICUResourceBundle r9 = r0.findWithFallback(r9)
            if (r9 != 0) goto L2a
            java.lang.String r9 = "calendar/gregorian/DateTimePatterns"
            com.ibm.icu.impl.ICUResourceBundle r9 = r0.getWithFallback(r9)
        L2a:
            int r0 = r9.getSize()
            java.lang.String[] r1 = new java.lang.String[r0]
            java.lang.String[] r2 = new java.lang.String[r0]
            java.lang.String r3 = r8.getBaseName()
            int r4 = r3.length()
            r5 = 0
            if (r4 <= 0) goto L8c
            java.lang.String r4 = "und"
            boolean r4 = r3.equals(r4)
            if (r4 != 0) goto L8c
            com.ibm.icu.util.ULocale r4 = new com.ibm.icu.util.ULocale
            r4.<init>(r3)
            com.ibm.icu.util.ULocale r3 = r9.getULocale()
            com.ibm.icu.util.ULocale r3 = com.ibm.icu.util.ULocale.addLikelySubtags(r3)
            if (r3 == r4) goto L8c
            java.lang.String r6 = r4.getCountry()
            int r7 = r6.length()
            if (r7 <= 0) goto L69
            java.lang.String r7 = r3.getCountry()
            boolean r6 = r6.equals(r7)
            if (r6 == 0) goto L77
        L69:
            java.lang.String r4 = r4.getLanguage()
            java.lang.String r3 = r3.getLanguage()
            boolean r3 = r4.equals(r3)
            if (r3 != 0) goto L8c
        L77:
            com.ibm.icu.text.DateTimePatternGenerator r8 = com.ibm.icu.text.DateTimePatternGenerator.getInstanceNoStdPat(r8)
            r3 = 0
        L7c:
            r4 = 4
            if (r3 >= r4) goto L8d
            java.lang.String[] r4 = com.ibm.icu.util.Calendar.TIME_SKELETONS
            r4 = r4[r3]
            java.lang.String r4 = r8.getBestPattern(r4)
            r1[r3] = r4
            int r3 = r3 + 1
            goto L7c
        L8c:
            r3 = 0
        L8d:
            if (r3 >= r0) goto Lb7
            com.ibm.icu.util.UResourceBundle r8 = r9.get(r3)
            com.ibm.icu.impl.ICUResourceBundle r8 = (com.ibm.icu.impl.ICUResourceBundle) r8
            int r4 = r8.getType()
            if (r4 == 0) goto Lae
            r6 = 8
            if (r4 == r6) goto La0
            goto Lb4
        La0:
            java.lang.String r4 = r8.getString(r5)
            r1[r3] = r4
            r4 = 1
            java.lang.String r8 = r8.getString(r4)
            r2[r3] = r8
            goto Lb4
        Lae:
            java.lang.String r8 = r8.getString()
            r1[r3] = r8
        Lb4:
            int r3 = r3 + 1
            goto L8d
        Lb7:
            com.ibm.icu.util.Calendar$PatternData r8 = new com.ibm.icu.util.Calendar$PatternData
            r8.<init>(r1, r2)
            return r8
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ibm.icu.util.Calendar.getPatternData(com.ibm.icu.util.ULocale, java.lang.String):com.ibm.icu.util.Calendar$PatternData");
    }

    @Deprecated
    public static String getDateTimePattern(Calendar calendar, ULocale uLocale, int i) {
        return PatternData.make(calendar, uLocale).getDateTimePattern(i);
    }

    private static String mergeOverrideStrings(String str, String str2, String str3, String str4) {
        if (str3 == null && str4 == null) {
            return null;
        }
        if (str3 == null) {
            return expandOverride(str2, str4);
        }
        if (str4 == null) {
            return expandOverride(str, str3);
        }
        if (str3.equals(str4)) {
            return str3;
        }
        return expandOverride(str, str3) + ";" + expandOverride(str2, str4);
    }

    private static String expandOverride(String str, String str2) {
        if (str2.indexOf(61) >= 0) {
            return str2;
        }
        boolean z = false;
        StringBuilder sb = new StringBuilder();
        StringCharacterIterator stringCharacterIterator = new StringCharacterIterator(str);
        char cFirst = stringCharacterIterator.first();
        char c = TokenParser.SP;
        while (cFirst != 65535) {
            if (cFirst == '\'') {
                z = !z;
            } else if (!z && cFirst != c) {
                if (sb.length() > 0) {
                    sb.append(";");
                }
                sb.append(cFirst);
                sb.append(ContainerUtils.KEY_VALUE_DELIMITER);
                sb.append(str2);
            }
            char c2 = cFirst;
            cFirst = stringCharacterIterator.next();
            c = c2;
        }
        return sb.toString();
    }

    @Deprecated
    public static class FormatConfiguration {
        private Calendar cal;
        private DateFormatSymbols formatData;
        private ULocale loc;
        private String override;
        private String pattern;

        private FormatConfiguration() {
        }

        @Deprecated
        public String getPatternString() {
            return this.pattern;
        }

        @Deprecated
        public String getOverrideString() {
            return this.override;
        }

        @Deprecated
        public Calendar getCalendar() {
            return this.cal;
        }

        @Deprecated
        public ULocale getLocale() {
            return this.loc;
        }

        @Deprecated
        public DateFormatSymbols getDateFormatSymbols() {
            return this.formatData;
        }
    }

    protected void pinField(int i) {
        int actualMaximum = getActualMaximum(i);
        int actualMinimum = getActualMinimum(i);
        int[] iArr = this.fields;
        if (iArr[i] > actualMaximum) {
            set(i, actualMaximum);
        } else if (iArr[i] < actualMinimum) {
            set(i, actualMinimum);
        }
    }

    protected int weekNumber(int i, int i2, int i3) {
        int firstDayOfWeek = (((i3 - getFirstDayOfWeek()) - i2) + 1) % 7;
        if (firstDayOfWeek < 0) {
            firstDayOfWeek += 7;
        }
        int i4 = ((i + firstDayOfWeek) - 1) / 7;
        return 7 - firstDayOfWeek >= getMinimalDaysInFirstWeek() ? i4 + 1 : i4;
    }

    protected final int weekNumber(int i, int i2) {
        return weekNumber(i, i, i2);
    }

    public int fieldDifference(Date date, int i) {
        long timeInMillis = getTimeInMillis();
        long time = date.getTime();
        int i2 = 0;
        if (timeInMillis < time) {
            int i3 = 1;
            int i4 = 0;
            while (true) {
                setTimeInMillis(timeInMillis);
                add(i, i3);
                long timeInMillis2 = getTimeInMillis();
                if (timeInMillis2 == time) {
                    return i3;
                }
                if (timeInMillis2 > time) {
                    while (true) {
                        int i5 = i3 - i4;
                        if (i5 <= 1) {
                            i2 = i4;
                            break;
                        }
                        int i6 = (i5 / 2) + i4;
                        setTimeInMillis(timeInMillis);
                        add(i, i6);
                        long timeInMillis3 = getTimeInMillis();
                        if (timeInMillis3 == time) {
                            return i6;
                        }
                        if (timeInMillis3 > time) {
                            i3 = i6;
                        } else {
                            i4 = i6;
                        }
                    }
                } else {
                    int i7 = Integer.MAX_VALUE;
                    if (i3 >= Integer.MAX_VALUE) {
                        throw new RuntimeException();
                    }
                    int i8 = i3 << 1;
                    if (i8 >= 0) {
                        i7 = i8;
                    }
                    int i9 = i7;
                    i4 = i3;
                    i3 = i9;
                }
            }
        } else if (timeInMillis > time) {
            int i10 = -1;
            int i11 = 0;
            while (true) {
                setTimeInMillis(timeInMillis);
                add(i, i10);
                long timeInMillis4 = getTimeInMillis();
                if (timeInMillis4 == time) {
                    return i10;
                }
                if (timeInMillis4 < time) {
                    int i12 = i11;
                    int i13 = i10;
                    i2 = i12;
                    while (i2 - i13 > 1) {
                        int i14 = ((i13 - i2) / 2) + i2;
                        setTimeInMillis(timeInMillis);
                        add(i, i14);
                        long timeInMillis5 = getTimeInMillis();
                        if (timeInMillis5 == time) {
                            return i14;
                        }
                        if (timeInMillis5 < time) {
                            i13 = i14;
                        } else {
                            i2 = i14;
                        }
                    }
                } else {
                    int i15 = i10 << 1;
                    if (i15 == 0) {
                        throw new RuntimeException();
                    }
                    i11 = i10;
                    i10 = i15;
                }
            }
        }
        setTimeInMillis(timeInMillis);
        add(i, i2);
        return i2;
    }

    public void setTimeZone(TimeZone timeZone) {
        this.zone = timeZone;
        this.areFieldsSet = false;
    }

    public TimeZone getTimeZone() {
        return this.zone;
    }

    public void setLenient(boolean z) {
        this.lenient = z;
    }

    public boolean isLenient() {
        return this.lenient;
    }

    public void setRepeatedWallTimeOption(int i) {
        if (i != 0 && i != 1) {
            throw new IllegalArgumentException("Illegal repeated wall time option - " + i);
        }
        this.repeatedWallTime = i;
    }

    public int getRepeatedWallTimeOption() {
        return this.repeatedWallTime;
    }

    public void setSkippedWallTimeOption(int i) {
        if (i != 0 && i != 1 && i != 2) {
            throw new IllegalArgumentException("Illegal skipped wall time option - " + i);
        }
        this.skippedWallTime = i;
    }

    public int getSkippedWallTimeOption() {
        return this.skippedWallTime;
    }

    public void setFirstDayOfWeek(int i) {
        if (this.firstDayOfWeek != i) {
            if (i < 1 || i > 7) {
                throw new IllegalArgumentException("Invalid day of week");
            }
            this.firstDayOfWeek = i;
            this.areFieldsSet = false;
        }
    }

    public int getFirstDayOfWeek() {
        return this.firstDayOfWeek;
    }

    public void setMinimalDaysInFirstWeek(int i) {
        if (i < 1) {
            i = 1;
        } else if (i > 7) {
            i = 7;
        }
        if (this.minimalDaysInFirstWeek != i) {
            this.minimalDaysInFirstWeek = i;
            this.areFieldsSet = false;
        }
    }

    public int getMinimalDaysInFirstWeek() {
        return this.minimalDaysInFirstWeek;
    }

    protected int getLimit(int i, int i2) {
        switch (i) {
            case 4:
                if (i2 == 0) {
                    return getMinimalDaysInFirstWeek() == 1 ? 1 : 0;
                }
                if (i2 == 1) {
                    return 1;
                }
                int minimalDaysInFirstWeek = getMinimalDaysInFirstWeek();
                int iHandleGetLimit = handleGetLimit(5, i2);
                if (i2 == 2) {
                    return (iHandleGetLimit + (7 - minimalDaysInFirstWeek)) / 7;
                }
                return ((iHandleGetLimit + 6) + (7 - minimalDaysInFirstWeek)) / 7;
            case 5:
            case 6:
            case 8:
            case 17:
            case 19:
            default:
                return handleGetLimit(i, i2);
            case 7:
            case 9:
            case 10:
            case 11:
            case 12:
            case 13:
            case 14:
            case 15:
            case 16:
            case 18:
            case 20:
            case 21:
            case 22:
                return LIMITS[i][i2];
        }
    }

    public final int getMinimum(int i) {
        return getLimit(i, 0);
    }

    public final int getMaximum(int i) {
        return getLimit(i, 3);
    }

    public final int getGreatestMinimum(int i) {
        return getLimit(i, 1);
    }

    public final int getLeastMaximum(int i) {
        return getLimit(i, 2);
    }

    @Deprecated
    public int getDayOfWeekType(int i) {
        if (i < 1 || i > 7) {
            throw new IllegalArgumentException("Invalid day of week");
        }
        int i2 = this.weekendOnset;
        int i3 = this.weekendCease;
        if (i2 == i3) {
            if (i != i2) {
                return 0;
            }
            return this.weekendOnsetMillis == 0 ? 1 : 2;
        }
        if (i2 < i3) {
            if (i < i2 || i > i3) {
                return 0;
            }
        } else if (i > i3 && i < i2) {
            return 0;
        }
        return i == this.weekendOnset ? this.weekendOnsetMillis == 0 ? 1 : 2 : (i != this.weekendCease || this.weekendCeaseMillis >= 86400000) ? 1 : 3;
    }

    @Deprecated
    public int getWeekendTransition(int i) {
        if (i == this.weekendOnset) {
            return this.weekendOnsetMillis;
        }
        if (i == this.weekendCease) {
            return this.weekendCeaseMillis;
        }
        throw new IllegalArgumentException("Not weekend transition day");
    }

    public boolean isWeekend(Date date) {
        setTime(date);
        return isWeekend();
    }

    public boolean isWeekend() {
        int i = get(7);
        int dayOfWeekType = getDayOfWeekType(i);
        if (dayOfWeekType == 0) {
            return false;
        }
        if (dayOfWeekType == 1) {
            return true;
        }
        int iInternalGet = internalGet(14) + ((internalGet(13) + ((internalGet(12) + (internalGet(11) * 60)) * 60)) * 1000);
        int weekendTransition = getWeekendTransition(i);
        if (dayOfWeekType == 2) {
            if (iInternalGet < weekendTransition) {
                return false;
            }
        } else if (iInternalGet >= weekendTransition) {
            return false;
        }
        return true;
    }

    public Object clone() {
        try {
            Calendar calendar = (Calendar) super.clone();
            int[] iArr = new int[this.fields.length];
            calendar.fields = iArr;
            calendar.stamp = new int[this.fields.length];
            System.arraycopy(this.fields, 0, iArr, 0, this.fields.length);
            System.arraycopy(this.stamp, 0, calendar.stamp, 0, this.fields.length);
            calendar.zone = (TimeZone) this.zone.clone();
            return calendar;
        } catch (CloneNotSupportedException e) {
            throw new ICUCloneNotSupportedException(e);
        }
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getName());
        sb.append("[time=");
        sb.append(this.isTimeSet ? String.valueOf(this.time) : "?");
        sb.append(",areFieldsSet=");
        sb.append(this.areFieldsSet);
        sb.append(",areAllFieldsSet=");
        sb.append(this.areAllFieldsSet);
        sb.append(",lenient=");
        sb.append(this.lenient);
        sb.append(",zone=");
        sb.append(this.zone);
        sb.append(",firstDayOfWeek=");
        sb.append(this.firstDayOfWeek);
        sb.append(",minimalDaysInFirstWeek=");
        sb.append(this.minimalDaysInFirstWeek);
        sb.append(",repeatedWallTime=");
        sb.append(this.repeatedWallTime);
        sb.append(",skippedWallTime=");
        sb.append(this.skippedWallTime);
        for (int i = 0; i < this.fields.length; i++) {
            sb.append(',');
            sb.append(fieldName(i));
            sb.append('=');
            sb.append(isSet(i) ? String.valueOf(this.fields[i]) : "?");
        }
        sb.append(']');
        return sb.toString();
    }

    public static final class WeekData {
        public final int firstDayOfWeek;
        public final int minimalDaysInFirstWeek;
        public final int weekendCease;
        public final int weekendCeaseMillis;
        public final int weekendOnset;
        public final int weekendOnsetMillis;

        public WeekData(int i, int i2, int i3, int i4, int i5, int i6) {
            this.firstDayOfWeek = i;
            this.minimalDaysInFirstWeek = i2;
            this.weekendOnset = i3;
            this.weekendOnsetMillis = i4;
            this.weekendCease = i5;
            this.weekendCeaseMillis = i6;
        }

        public int hashCode() {
            return (((((((((this.firstDayOfWeek * 37) + this.minimalDaysInFirstWeek) * 37) + this.weekendOnset) * 37) + this.weekendOnsetMillis) * 37) + this.weekendCease) * 37) + this.weekendCeaseMillis;
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof WeekData)) {
                return false;
            }
            WeekData weekData = (WeekData) obj;
            return this.firstDayOfWeek == weekData.firstDayOfWeek && this.minimalDaysInFirstWeek == weekData.minimalDaysInFirstWeek && this.weekendOnset == weekData.weekendOnset && this.weekendOnsetMillis == weekData.weekendOnsetMillis && this.weekendCease == weekData.weekendCease && this.weekendCeaseMillis == weekData.weekendCeaseMillis;
        }

        public String toString() {
            return "{" + this.firstDayOfWeek + ", " + this.minimalDaysInFirstWeek + ", " + this.weekendOnset + ", " + this.weekendOnsetMillis + ", " + this.weekendCease + ", " + this.weekendCeaseMillis + "}";
        }
    }

    public static WeekData getWeekDataForRegion(String str) {
        return WEEK_DATA_CACHE.createInstance(str, str);
    }

    public WeekData getWeekData() {
        return new WeekData(this.firstDayOfWeek, this.minimalDaysInFirstWeek, this.weekendOnset, this.weekendOnsetMillis, this.weekendCease, this.weekendCeaseMillis);
    }

    public Calendar setWeekData(WeekData weekData) {
        setFirstDayOfWeek(weekData.firstDayOfWeek);
        setMinimalDaysInFirstWeek(weekData.minimalDaysInFirstWeek);
        this.weekendOnset = weekData.weekendOnset;
        this.weekendOnsetMillis = weekData.weekendOnsetMillis;
        this.weekendCease = weekData.weekendCease;
        this.weekendCeaseMillis = weekData.weekendCeaseMillis;
        return this;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static WeekData getWeekDataForRegionInternal(String str) {
        UResourceBundle uResourceBundle;
        if (str == null) {
            str = UnitsData.Constants.DEFAULT_REGION;
        }
        UResourceBundle uResourceBundle2 = UResourceBundle.getBundleInstance(ICUData.ICU_BASE_NAME, "supplementalData", ICUResourceBundle.ICU_DATA_CLASS_LOADER).get("weekData");
        try {
            uResourceBundle = uResourceBundle2.get(str);
        } catch (MissingResourceException e) {
            if (!str.equals(UnitsData.Constants.DEFAULT_REGION)) {
                uResourceBundle = uResourceBundle2.get(UnitsData.Constants.DEFAULT_REGION);
            } else {
                throw e;
            }
        }
        int[] intVector = uResourceBundle.getIntVector();
        return new WeekData(intVector[0], intVector[1], intVector[2], intVector[3], intVector[4], intVector[5]);
    }

    private static class WeekDataCache extends SoftCache<String, WeekData, String> {
        private WeekDataCache() {
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.ibm.icu.impl.CacheBase
        public WeekData createInstance(String str, String str2) {
            return Calendar.getWeekDataForRegionInternal(str);
        }
    }

    private void setWeekData(String str) {
        if (str == null) {
            str = UnitsData.Constants.DEFAULT_REGION;
        }
        setWeekData(WEEK_DATA_CACHE.getInstance(str, str));
    }

    private void updateTime() {
        computeTime();
        if (isLenient() || !this.areAllFieldsSet) {
            this.areFieldsSet = false;
        }
        this.isTimeSet = true;
        this.areFieldsVirtuallySet = false;
    }

    private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
        if (!this.isTimeSet) {
            try {
                updateTime();
            } catch (IllegalArgumentException unused) {
            }
        }
        objectOutputStream.defaultWriteObject();
    }

    private void readObject(ObjectInputStream objectInputStream) throws ClassNotFoundException, IOException {
        objectInputStream.defaultReadObject();
        initInternal();
        this.isTimeSet = true;
        this.areAllFieldsSet = false;
        this.areFieldsSet = false;
        this.areFieldsVirtuallySet = true;
        this.nextStamp = 2;
    }

    protected void computeFields() {
        int[] iArr = new int[2];
        getTimeZone().getOffset(this.time, false, iArr);
        long j = this.time + iArr[0] + iArr[1];
        int i = this.internalSetMask;
        for (int i2 = 0; i2 < this.fields.length; i2++) {
            if ((i & 1) == 0) {
                this.stamp[i2] = 1;
            } else {
                this.stamp[i2] = 0;
            }
            i >>= 1;
        }
        long jFloorDivide = floorDivide(j, 86400000L);
        int[] iArr2 = this.fields;
        iArr2[20] = ((int) jFloorDivide) + EPOCH_JULIAN_DAY;
        computeGregorianAndDOWFields(iArr2[20]);
        handleComputeFields(this.fields[20]);
        computeWeekFields();
        int i3 = (int) (j - (jFloorDivide * 86400000));
        int[] iArr3 = this.fields;
        iArr3[21] = i3;
        iArr3[14] = i3 % 1000;
        int i4 = i3 / 1000;
        iArr3[13] = i4 % 60;
        int i5 = i4 / 60;
        iArr3[12] = i5 % 60;
        int i6 = i5 / 60;
        iArr3[11] = i6;
        iArr3[9] = i6 / 12;
        iArr3[10] = i6 % 12;
        iArr3[15] = iArr[0];
        iArr3[16] = iArr[1];
    }

    private final void computeGregorianAndDOWFields(int i) {
        computeGregorianFields(i);
        int[] iArr = this.fields;
        int iJulianDayToDayOfWeek = julianDayToDayOfWeek(i);
        iArr[7] = iJulianDayToDayOfWeek;
        int firstDayOfWeek = (iJulianDayToDayOfWeek - getFirstDayOfWeek()) + 1;
        if (firstDayOfWeek < 1) {
            firstDayOfWeek += 7;
        }
        this.fields[18] = firstDayOfWeek;
    }

    protected final void computeGregorianFields(int i) {
        int[] iArr = new int[1];
        int iFloorDivide = floorDivide(i - JAN_1_1_JULIAN_DAY, 146097, iArr);
        int iFloorDivide2 = floorDivide(iArr[0], 36524, iArr);
        int iFloorDivide3 = floorDivide(iArr[0], R2.color.primary_text_disabled_material_dark, iArr);
        int i2 = iArr[0];
        int i3 = R2.attr.defaultValue;
        int iFloorDivide4 = floorDivide(i2, R2.attr.defaultValue, iArr);
        int i4 = (iFloorDivide * 400) + (iFloorDivide2 * 100) + (iFloorDivide3 * 4) + iFloorDivide4;
        int i5 = iArr[0];
        if (iFloorDivide2 != 4 && iFloorDivide4 != 4) {
            i4++;
            i3 = i5;
        }
        boolean z = (i4 & 3) == 0 && (i4 % 100 != 0 || i4 % 400 == 0);
        int i6 = ((((i3 >= (z ? 60 : 59) ? z ? 1 : 2 : 0) + i3) * 12) + 6) / R2.attr.deltaPolarRadius;
        int i7 = (i3 - GREGORIAN_MONTH_COUNT[i6][z ? (char) 3 : (char) 2]) + 1;
        this.gregorianYear = i4;
        this.gregorianMonth = i6;
        this.gregorianDayOfMonth = i7;
        this.gregorianDayOfYear = i3 + 1;
    }

    private final void computeWeekFields() {
        int[] iArr = this.fields;
        int i = iArr[19];
        int i2 = iArr[7];
        int i3 = iArr[6];
        int firstDayOfWeek = ((i2 + 7) - getFirstDayOfWeek()) % 7;
        int firstDayOfWeek2 = (((i2 - i3) + 7001) - getFirstDayOfWeek()) % 7;
        int iWeekNumber = ((i3 - 1) + firstDayOfWeek2) / 7;
        if (7 - firstDayOfWeek2 >= getMinimalDaysInFirstWeek()) {
            iWeekNumber++;
        }
        if (iWeekNumber == 0) {
            iWeekNumber = weekNumber(i3 + handleGetYearLength(i - 1), i2);
            i--;
        } else {
            int iHandleGetYearLength = handleGetYearLength(i);
            if (i3 >= iHandleGetYearLength - 5) {
                int i4 = ((firstDayOfWeek + iHandleGetYearLength) - i3) % 7;
                if (i4 < 0) {
                    i4 += 7;
                }
                if (6 - i4 >= getMinimalDaysInFirstWeek() && (i3 + 7) - firstDayOfWeek > iHandleGetYearLength) {
                    i++;
                    iWeekNumber = 1;
                }
            }
        }
        int[] iArr2 = this.fields;
        iArr2[3] = iWeekNumber;
        iArr2[17] = i;
        int i5 = iArr2[5];
        iArr2[4] = weekNumber(i5, i2);
        this.fields[8] = ((i5 - 1) / 7) + 1;
    }

    /* JADX WARN: Removed duplicated region for block: B:27:0x0042 A[PHI: r7
      0x0042: PHI (r7v3 int) = (r7v2 int), (r7v5 int), (r7v5 int) binds: [B:22:0x0032, B:24:0x0037, B:26:0x0040] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:29:0x0045  */
    /* JADX WARN: Removed duplicated region for block: B:40:0x0046 A[SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    protected int resolveFields(int[][][] r13) {
        /*
            r12 = this;
            r0 = 0
            r1 = -1
            r2 = 0
        L3:
            int r3 = r13.length
            r4 = 32
            if (r2 >= r3) goto L4c
            if (r1 >= 0) goto L4c
            r3 = r13[r2]
            r5 = 0
            r6 = 0
        Le:
            int r7 = r3.length
            if (r5 >= r7) goto L49
            r7 = r3[r5]
            r8 = r7[r0]
            if (r8 < r4) goto L19
            r8 = 1
            goto L1a
        L19:
            r8 = 0
        L1a:
            r9 = 0
        L1b:
            int r10 = r7.length
            if (r8 >= r10) goto L2e
            int[] r10 = r12.stamp
            r11 = r7[r8]
            r10 = r10[r11]
            if (r10 != 0) goto L27
            goto L46
        L27:
            int r9 = java.lang.Math.max(r9, r10)
            int r8 = r8 + 1
            goto L1b
        L2e:
            if (r9 <= r6) goto L46
            r7 = r7[r0]
            if (r7 < r4) goto L42
            r7 = r7 & 31
            r8 = 5
            if (r7 != r8) goto L42
            int[] r8 = r12.stamp
            r10 = 4
            r10 = r8[r10]
            r8 = r8[r7]
            if (r10 >= r8) goto L43
        L42:
            r1 = r7
        L43:
            if (r1 != r7) goto L46
            r6 = r9
        L46:
            int r5 = r5 + 1
            goto Le
        L49:
            int r2 = r2 + 1
            goto L3
        L4c:
            if (r1 < r4) goto L50
            r1 = r1 & 31
        L50:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ibm.icu.util.Calendar.resolveFields(int[][][]):int");
    }

    protected int newestStamp(int i, int i2, int i3) {
        while (i <= i2) {
            int[] iArr = this.stamp;
            if (iArr[i] > i3) {
                i3 = iArr[i];
            }
            i++;
        }
        return i3;
    }

    protected final int getStamp(int i) {
        return this.stamp[i];
    }

    protected int newerField(int i, int i2) {
        int[] iArr = this.stamp;
        return iArr[i2] > iArr[i] ? i2 : i;
    }

    protected void validateFields() {
        for (int i = 0; i < this.fields.length; i++) {
            if (this.stamp[i] >= 2) {
                validateField(i);
            }
        }
    }

    protected void validateField(int i) {
        if (i == 5) {
            validateField(i, 1, handleGetMonthLength(handleGetExtendedYear(), internalGet(2)));
            return;
        }
        if (i == 6) {
            validateField(i, 1, handleGetYearLength(handleGetExtendedYear()));
        } else {
            if (i == 8) {
                if (internalGet(i) == 0) {
                    throw new IllegalArgumentException("DAY_OF_WEEK_IN_MONTH cannot be zero");
                }
                validateField(i, getMinimum(i), getMaximum(i));
                return;
            }
            validateField(i, getMinimum(i), getMaximum(i));
        }
    }

    protected final void validateField(int i, int i2, int i3) {
        int i4 = this.fields[i];
        if (i4 < i2 || i4 > i3) {
            throw new IllegalArgumentException(fieldName(i) + '=' + i4 + ", valid range=" + i2 + ".." + i3);
        }
    }

    protected void computeTime() {
        int iComputeMillisInDay;
        long jComputeMillisInDayLong;
        int[] iArr;
        if (!isLenient()) {
            validateFields();
        }
        long jJulianDayToMillis = julianDayToMillis(computeJulianDay());
        if (this.stamp[21] >= 2 && newestStamp(9, 14, 0) <= this.stamp[21]) {
            iComputeMillisInDay = internalGet(21);
        } else {
            if (Math.max(Math.abs(internalGet(11)), Math.abs(internalGet(10))) > 548) {
                jComputeMillisInDayLong = computeMillisInDayLong();
                iArr = this.stamp;
                if (iArr[15] < 2 || iArr[16] >= 2) {
                    this.time = (jJulianDayToMillis + jComputeMillisInDayLong) - (internalGet(15) + internalGet(16));
                }
                if (!this.lenient || this.skippedWallTime == 2) {
                    int iComputeZoneOffset = computeZoneOffset(jJulianDayToMillis, jComputeMillisInDayLong);
                    long j = (jJulianDayToMillis + jComputeMillisInDayLong) - iComputeZoneOffset;
                    if (iComputeZoneOffset != this.zone.getOffset(j)) {
                        if (!this.lenient) {
                            throw new IllegalArgumentException("The specified wall time does not exist due to time zone offset transition.");
                        }
                        Long immediatePreviousZoneTransition = getImmediatePreviousZoneTransition(j);
                        if (immediatePreviousZoneTransition == null) {
                            throw new RuntimeException("Could not locate a time zone transition before " + j);
                        }
                        this.time = immediatePreviousZoneTransition.longValue();
                        return;
                    }
                    this.time = j;
                    return;
                }
                this.time = (jJulianDayToMillis + jComputeMillisInDayLong) - computeZoneOffset(jJulianDayToMillis, jComputeMillisInDayLong);
                return;
            }
            iComputeMillisInDay = computeMillisInDay();
        }
        jComputeMillisInDayLong = iComputeMillisInDay;
        iArr = this.stamp;
        if (iArr[15] < 2) {
        }
        this.time = (jJulianDayToMillis + jComputeMillisInDayLong) - (internalGet(15) + internalGet(16));
    }

    private Long getImmediatePreviousZoneTransition(long j) {
        TimeZone timeZone = this.zone;
        if (timeZone instanceof BasicTimeZone) {
            TimeZoneTransition previousTransition = ((BasicTimeZone) timeZone).getPreviousTransition(j, true);
            if (previousTransition != null) {
                return Long.valueOf(previousTransition.getTime());
            }
            return null;
        }
        Long previousZoneTransitionTime = getPreviousZoneTransitionTime(timeZone, j, 7200000L);
        return previousZoneTransitionTime == null ? getPreviousZoneTransitionTime(this.zone, j, 108000000L) : previousZoneTransitionTime;
    }

    private static Long getPreviousZoneTransitionTime(TimeZone timeZone, long j, long j2) {
        long j3 = (j - j2) - 1;
        int offset = timeZone.getOffset(j);
        if (offset == timeZone.getOffset(j3)) {
            return null;
        }
        return findPreviousZoneTransitionTime(timeZone, offset, j, j3);
    }

    private static Long findPreviousZoneTransitionTime(TimeZone timeZone, int i, long j, long j2) {
        long j3;
        long j4;
        long j5;
        int[] iArr = FIND_ZONE_TRANSITION_TIME_UNITS;
        int length = iArr.length;
        boolean z = false;
        int i2 = 0;
        while (true) {
            if (i2 >= length) {
                j3 = 0;
                break;
            }
            long j6 = iArr[i2];
            long j7 = j2 / j6;
            long j8 = j / j6;
            if (j8 > j7) {
                j3 = (((j7 + j8) + 1) >>> 1) * j6;
                z = true;
                break;
            }
            i2++;
        }
        if (!z) {
            j3 = (j + j2) >>> 1;
        }
        long j9 = j3;
        if (z) {
            if (j9 == j) {
                j5 = j;
            } else {
                if (timeZone.getOffset(j9) != i) {
                    return findPreviousZoneTransitionTime(timeZone, i, j, j9);
                }
                j5 = j9;
            }
            j4 = j9 - 1;
        } else {
            j4 = (j + j2) >>> 1;
            j5 = j;
        }
        if (j4 == j2) {
            return Long.valueOf(j5);
        }
        if (timeZone.getOffset(j4) == i) {
            return findPreviousZoneTransitionTime(timeZone, i, j4, j2);
        }
        if (z) {
            return Long.valueOf(j5);
        }
        return findPreviousZoneTransitionTime(timeZone, i, j5, j4);
    }

    @Deprecated
    protected int computeMillisInDay() {
        int[] iArr = this.stamp;
        int i = iArr[11];
        int iMax = Math.max(iArr[10], iArr[9]);
        if (iMax <= i) {
            iMax = i;
        }
        int iInternalGet = 0;
        if (iMax != 0) {
            if (iMax == i) {
                iInternalGet = 0 + internalGet(11);
            } else {
                iInternalGet = internalGet(10) + 0 + (internalGet(9) * 12);
            }
        }
        return (((((iInternalGet * 60) + internalGet(12)) * 60) + internalGet(13)) * 1000) + internalGet(14);
    }

    @Deprecated
    protected long computeMillisInDayLong() {
        int[] iArr = this.stamp;
        int i = iArr[11];
        int iMax = Math.max(iArr[10], iArr[9]);
        if (iMax <= i) {
            iMax = i;
        }
        long jInternalGet = 0;
        if (iMax != 0) {
            if (iMax == i) {
                jInternalGet = 0 + internalGet(11);
            } else {
                jInternalGet = internalGet(10) + 0 + (internalGet(9) * 12);
            }
        }
        return (((((jInternalGet * 60) + internalGet(12)) * 60) + internalGet(13)) * 1000) + internalGet(14);
    }

    /* JADX WARN: Removed duplicated region for block: B:18:0x0056  */
    @java.lang.Deprecated
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    protected int computeZoneOffset(long r9, int r11) {
        /*
            r8 = this;
            r0 = 2
            int[] r0 = new int[r0]
            long r1 = (long) r11
            long r2 = r9 + r1
            com.ibm.icu.util.TimeZone r9 = r8.zone
            boolean r10 = r9 instanceof com.ibm.icu.util.BasicTimeZone
            r11 = 0
            r7 = 1
            if (r10 == 0) goto L2c
            int r9 = r8.skippedWallTime
            if (r9 != r7) goto L15
            com.ibm.icu.util.BasicTimeZone$LocalOption r9 = com.ibm.icu.util.BasicTimeZone.LocalOption.LATTER
            goto L17
        L15:
            com.ibm.icu.util.BasicTimeZone$LocalOption r9 = com.ibm.icu.util.BasicTimeZone.LocalOption.FORMER
        L17:
            r4 = r9
            int r9 = r8.repeatedWallTime
            if (r9 != r7) goto L1f
            com.ibm.icu.util.BasicTimeZone$LocalOption r9 = com.ibm.icu.util.BasicTimeZone.LocalOption.FORMER
            goto L21
        L1f:
            com.ibm.icu.util.BasicTimeZone$LocalOption r9 = com.ibm.icu.util.BasicTimeZone.LocalOption.LATTER
        L21:
            r5 = r9
            com.ibm.icu.util.TimeZone r9 = r8.zone
            r1 = r9
            com.ibm.icu.util.BasicTimeZone r1 = (com.ibm.icu.util.BasicTimeZone) r1
            r6 = r0
            r1.getOffsetFromLocal(r2, r4, r5, r6)
            goto L69
        L2c:
            r9.getOffset(r2, r7, r0)
            int r9 = r8.repeatedWallTime
            if (r9 != r7) goto L56
            r9 = r0[r11]
            r10 = r0[r7]
            int r9 = r9 + r10
            long r9 = (long) r9
            long r9 = r2 - r9
            com.ibm.icu.util.TimeZone r1 = r8.zone
            r4 = 21600000(0x1499700, double:1.0671818E-316)
            long r9 = r9 - r4
            int r9 = r1.getOffset(r9)
            r10 = r0[r11]
            r1 = r0[r7]
            int r10 = r10 + r1
            int r10 = r10 - r9
            if (r10 >= 0) goto L56
            com.ibm.icu.util.TimeZone r9 = r8.zone
            long r4 = (long) r10
            long r4 = r4 + r2
            r9.getOffset(r4, r7, r0)
            r9 = 1
            goto L57
        L56:
            r9 = 0
        L57:
            if (r9 != 0) goto L69
            int r9 = r8.skippedWallTime
            if (r9 != r7) goto L69
            r9 = r0[r11]
            r10 = r0[r7]
            int r9 = r9 + r10
            long r9 = (long) r9
            long r2 = r2 - r9
            com.ibm.icu.util.TimeZone r9 = r8.zone
            r9.getOffset(r2, r11, r0)
        L69:
            r9 = r0[r11]
            r10 = r0[r7]
            int r9 = r9 + r10
            return r9
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ibm.icu.util.Calendar.computeZoneOffset(long, int):int");
    }

    /* JADX WARN: Removed duplicated region for block: B:18:0x0055  */
    @java.lang.Deprecated
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    protected int computeZoneOffset(long r8, long r10) {
        /*
            r7 = this;
            r0 = 2
            int[] r0 = new int[r0]
            long r2 = r8 + r10
            com.ibm.icu.util.TimeZone r8 = r7.zone
            boolean r9 = r8 instanceof com.ibm.icu.util.BasicTimeZone
            r10 = 0
            r11 = 1
            if (r9 == 0) goto L2b
            int r8 = r7.skippedWallTime
            if (r8 != r11) goto L14
            com.ibm.icu.util.BasicTimeZone$LocalOption r8 = com.ibm.icu.util.BasicTimeZone.LocalOption.LATTER
            goto L16
        L14:
            com.ibm.icu.util.BasicTimeZone$LocalOption r8 = com.ibm.icu.util.BasicTimeZone.LocalOption.FORMER
        L16:
            r4 = r8
            int r8 = r7.repeatedWallTime
            if (r8 != r11) goto L1e
            com.ibm.icu.util.BasicTimeZone$LocalOption r8 = com.ibm.icu.util.BasicTimeZone.LocalOption.FORMER
            goto L20
        L1e:
            com.ibm.icu.util.BasicTimeZone$LocalOption r8 = com.ibm.icu.util.BasicTimeZone.LocalOption.LATTER
        L20:
            r5 = r8
            com.ibm.icu.util.TimeZone r8 = r7.zone
            r1 = r8
            com.ibm.icu.util.BasicTimeZone r1 = (com.ibm.icu.util.BasicTimeZone) r1
            r6 = r0
            r1.getOffsetFromLocal(r2, r4, r5, r6)
            goto L68
        L2b:
            r8.getOffset(r2, r11, r0)
            int r8 = r7.repeatedWallTime
            if (r8 != r11) goto L55
            r8 = r0[r10]
            r9 = r0[r11]
            int r8 = r8 + r9
            long r8 = (long) r8
            long r8 = r2 - r8
            com.ibm.icu.util.TimeZone r1 = r7.zone
            r4 = 21600000(0x1499700, double:1.0671818E-316)
            long r8 = r8 - r4
            int r8 = r1.getOffset(r8)
            r9 = r0[r10]
            r1 = r0[r11]
            int r9 = r9 + r1
            int r9 = r9 - r8
            if (r9 >= 0) goto L55
            com.ibm.icu.util.TimeZone r8 = r7.zone
            long r4 = (long) r9
            long r4 = r4 + r2
            r8.getOffset(r4, r11, r0)
            r8 = 1
            goto L56
        L55:
            r8 = 0
        L56:
            if (r8 != 0) goto L68
            int r8 = r7.skippedWallTime
            if (r8 != r11) goto L68
            r8 = r0[r10]
            r9 = r0[r11]
            int r8 = r8 + r9
            long r8 = (long) r8
            long r2 = r2 - r8
            com.ibm.icu.util.TimeZone r8 = r7.zone
            r8.getOffset(r2, r10, r0)
        L68:
            r8 = r0[r10]
            r9 = r0[r11]
            int r8 = r8 + r9
            return r8
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ibm.icu.util.Calendar.computeZoneOffset(long, long):int");
    }

    protected int computeJulianDay() {
        if (this.stamp[20] >= 2 && newestStamp(17, 19, newestStamp(0, 8, 0)) <= this.stamp[20]) {
            return internalGet(20);
        }
        int iResolveFields = resolveFields(getFieldResolutionTable());
        if (iResolveFields < 0) {
            iResolveFields = 5;
        }
        return handleComputeJulianDay(iResolveFields);
    }

    protected int[][][] getFieldResolutionTable() {
        return DATE_PRECEDENCE;
    }

    protected int handleGetMonthLength(int i, int i2) {
        return handleComputeMonthStart(i, i2 + 1, true) - handleComputeMonthStart(i, i2, true);
    }

    protected int handleGetYearLength(int i) {
        return handleComputeMonthStart(i + 1, 0, false) - handleComputeMonthStart(i, 0, false);
    }

    protected int handleComputeJulianDay(int i) {
        int iHandleGetExtendedYear;
        int iInternalGet;
        int iInternalGet2;
        int iHandleGetMonthLength;
        int iInternalGet3;
        boolean z = i == 5 || i == 4 || i == 8;
        if (i == 3 && newerField(17, 1) == 17) {
            iHandleGetExtendedYear = internalGet(17);
        } else {
            iHandleGetExtendedYear = handleGetExtendedYear();
        }
        internalSet(19, iHandleGetExtendedYear);
        int iInternalGet4 = z ? internalGet(2, getDefaultMonthInYear(iHandleGetExtendedYear)) : 0;
        int iHandleComputeMonthStart = handleComputeMonthStart(iHandleGetExtendedYear, iInternalGet4, z);
        if (i == 5) {
            if (isSet(5)) {
                iInternalGet3 = internalGet(5, getDefaultDayInMonth(iHandleGetExtendedYear, iInternalGet4));
            } else {
                iInternalGet3 = getDefaultDayInMonth(iHandleGetExtendedYear, iInternalGet4);
            }
        } else if (i == 6) {
            iInternalGet3 = internalGet(6);
        } else {
            int firstDayOfWeek = getFirstDayOfWeek();
            int iJulianDayToDayOfWeek = julianDayToDayOfWeek(iHandleComputeMonthStart + 1) - firstDayOfWeek;
            if (iJulianDayToDayOfWeek < 0) {
                iJulianDayToDayOfWeek += 7;
            }
            int iResolveFields = resolveFields(DOW_PRECEDENCE);
            if (iResolveFields == 7) {
                iInternalGet = internalGet(7) - firstDayOfWeek;
            } else {
                iInternalGet = iResolveFields != 18 ? 0 : internalGet(18) - 1;
            }
            int i2 = iInternalGet % 7;
            if (i2 < 0) {
                i2 += 7;
            }
            int i3 = (1 - iJulianDayToDayOfWeek) + i2;
            if (i == 8) {
                if (i3 < 1) {
                    i3 += 7;
                }
                iInternalGet2 = internalGet(8, 1);
                if (iInternalGet2 < 0) {
                    iHandleGetMonthLength = i3 + ((((handleGetMonthLength(iHandleGetExtendedYear, internalGet(2, 0)) - i3) / 7) + iInternalGet2 + 1) * 7);
                }
                return iHandleComputeMonthStart + iHandleGetMonthLength;
            }
            if (7 - iJulianDayToDayOfWeek < getMinimalDaysInFirstWeek()) {
                i3 += 7;
            }
            iInternalGet2 = internalGet(i);
            iHandleGetMonthLength = i3 + ((iInternalGet2 - 1) * 7);
            return iHandleComputeMonthStart + iHandleGetMonthLength;
        }
        return iHandleComputeMonthStart + iInternalGet3;
    }

    protected int computeGregorianMonthStart(int i, int i2) {
        boolean z = false;
        if (i2 < 0 || i2 > 11) {
            int[] iArr = new int[1];
            i += floorDivide(i2, 12, iArr);
            i2 = iArr[0];
        }
        if (i % 4 == 0 && (i % 100 != 0 || i % 400 == 0)) {
            z = true;
        }
        int i3 = i - 1;
        int iFloorDivide = (((((i3 * R2.attr.defaultValue) + floorDivide(i3, 4)) - floorDivide(i3, 100)) + floorDivide(i3, 400)) + JAN_1_1_JULIAN_DAY) - 1;
        if (i2 != 0) {
            return iFloorDivide + GREGORIAN_MONTH_COUNT[i2][z ? (char) 3 : (char) 2];
        }
        return iFloorDivide;
    }

    protected void handleComputeFields(int i) {
        int i2;
        internalSet(2, getGregorianMonth());
        internalSet(5, getGregorianDayOfMonth());
        internalSet(6, getGregorianDayOfYear());
        int gregorianYear = getGregorianYear();
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

    protected final int getGregorianYear() {
        return this.gregorianYear;
    }

    protected final int getGregorianMonth() {
        return this.gregorianMonth;
    }

    protected final int getGregorianDayOfYear() {
        return this.gregorianDayOfYear;
    }

    protected final int getGregorianDayOfMonth() {
        return this.gregorianDayOfMonth;
    }

    public final int getFieldCount() {
        return this.fields.length;
    }

    protected final void internalSet(int i, int i2) {
        if (((1 << i) & this.internalSetMask) == 0) {
            throw new IllegalStateException("Subclass cannot set " + fieldName(i));
        }
        this.fields[i] = i2;
        this.stamp[i] = 1;
    }

    protected static final boolean isGregorianLeapYear(int i) {
        return i % 4 == 0 && (i % 100 != 0 || i % 400 == 0);
    }

    protected static final int gregorianMonthLength(int i, int i2) {
        return GREGORIAN_MONTH_COUNT[i2][isGregorianLeapYear(i) ? 1 : 0];
    }

    protected static final int gregorianPreviousMonthLength(int i, int i2) {
        if (i2 > 0) {
            return gregorianMonthLength(i, i2 - 1);
        }
        return 31;
    }

    protected static final long floorDivide(long j, long j2) {
        return j >= 0 ? j / j2 : ((j + 1) / j2) - 1;
    }

    protected static final int floorDivide(int i, int i2) {
        return i >= 0 ? i / i2 : ((i + 1) / i2) - 1;
    }

    protected static final int floorDivide(int i, int i2, int[] iArr) {
        if (i >= 0) {
            iArr[0] = i % i2;
            return i / i2;
        }
        int i3 = ((i + 1) / i2) - 1;
        iArr[0] = i - (i2 * i3);
        return i3;
    }

    protected static final int floorDivide(long j, int i, int[] iArr) {
        if (j >= 0) {
            long j2 = i;
            iArr[0] = (int) (j % j2);
            return (int) (j / j2);
        }
        long j3 = i;
        int i2 = (int) (((j + 1) / j3) - 1);
        iArr[0] = (int) (j - (i2 * j3));
        return i2;
    }

    protected String fieldName(int i) {
        try {
            return FIELD_NAME[i];
        } catch (ArrayIndexOutOfBoundsException unused) {
            return "Field " + i;
        }
    }

    protected static final int millisToJulianDay(long j) {
        return (int) (floorDivide(j, 86400000L) + 2440588);
    }

    protected static final int julianDayToDayOfWeek(int i) {
        int i2 = (i + 2) % 7;
        return i2 < 1 ? i2 + 7 : i2;
    }

    protected final long internalGetTimeInMillis() {
        return this.time;
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
}
