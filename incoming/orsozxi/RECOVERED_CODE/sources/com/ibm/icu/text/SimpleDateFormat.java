package com.ibm.icu.text;

import com.huawei.hms.framework.common.ContainerUtils;
import com.ibm.icu.impl.DateNumberFormat;
import com.ibm.icu.impl.DayPeriodRules;
import com.ibm.icu.impl.ICUCache;
import com.ibm.icu.impl.ICUData;
import com.ibm.icu.impl.ICUResourceBundle;
import com.ibm.icu.impl.PatternProps;
import com.ibm.icu.impl.PatternTokenizer;
import com.ibm.icu.impl.SimpleCache;
import com.ibm.icu.impl.SimpleFormatterImpl;
import com.ibm.icu.lang.UCharacter;
import com.ibm.icu.text.DateFormat;
import com.ibm.icu.text.DisplayContext;
import com.ibm.icu.text.TimeZoneFormat;
import com.ibm.icu.util.Calendar;
import com.ibm.icu.util.Output;
import com.ibm.icu.util.TimeZone;
import com.ibm.icu.util.ULocale;
import com.ibm.icu.util.UResourceBundle;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.text.AttributedCharacterIterator;
import java.text.AttributedString;
import java.text.FieldPosition;
import java.text.Format;
import java.text.ParsePosition;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.UUID;

/* loaded from: classes2.dex */
public class SimpleDateFormat extends DateFormat {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final int DECIMAL_BUF_SIZE = 10;
    static boolean DelayedHebrewMonthCheck = false;
    private static final String FALLBACKPATTERN = "yy/MM/dd HH:mm";
    private static final int HEBREW_CAL_CUR_MILLENIUM_END_YEAR = 6000;
    private static final int HEBREW_CAL_CUR_MILLENIUM_START_YEAR = 5000;
    private static final int ISOSpecialEra = -32000;
    private static final long MAX_DAYLIGHT_DETECTION_RANGE = 946080000000L;
    private static final String NUMERIC_FORMAT_CHARS = "ADdFgHhKkmrSsuWwYy";
    private static final String NUMERIC_FORMAT_CHARS2 = "ceLMQq";
    private static final String SUPPRESS_NEGATIVE_PREFIX = "\uab00";
    static final int currentSerialVersion = 2;
    private static final int millisPerHour = 3600000;
    private static final long serialVersionUID = 4774881970558875024L;
    private transient BreakIterator capitalizationBrkIter;
    private transient char[] decDigits;
    private transient char[] decimalBuf;
    private transient long defaultCenturyBase;
    private Date defaultCenturyStart;
    private transient int defaultCenturyStartYear;
    private DateFormatSymbols formatData;
    private transient boolean hasHanYearChar;
    private transient boolean hasMinute;
    private transient boolean hasSecond;
    private transient ULocale locale;
    private HashMap<String, NumberFormat> numberFormatters;
    private String override;
    private HashMap<Character, String> overrideMap;
    private String pattern;
    private transient Object[] patternItems;
    private int serialVersionOnStream;
    private volatile TimeZoneFormat tzFormat;
    private transient boolean useFastFormat;
    private transient boolean useLocalZeroPaddingNumberFormat;
    private static final int[] CALENDAR_FIELD_TO_LEVEL = {0, 10, 20, 20, 30, 30, 20, 30, 30, 40, 50, 50, 60, 70, 80, 0, 0, 10, 30, 10, 0, 40, 0, 0};
    private static final int[] PATTERN_CHAR_TO_LEVEL = {-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 40, -1, -1, 20, 30, 30, 0, 50, -1, -1, 50, 20, 20, -1, 0, -1, 20, -1, 80, -1, 10, 0, 30, 0, 10, 0, -1, -1, -1, -1, -1, -1, 40, -1, 30, 30, 30, -1, 0, 50, -1, -1, 50, -1, 60, -1, -1, -1, 20, 10, 70, -1, 10, 0, 20, 0, 10, 0, -1, -1, -1, -1, -1};
    private static final boolean[] PATTERN_CHAR_IS_SYNTAX = {false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, false, false, false, false, false, false, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, false, false, false, false, false};
    private static ULocale cachedDefaultLocale = null;
    private static String cachedDefaultPattern = null;
    private static final int[] PATTERN_CHAR_TO_INDEX = {-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 22, 36, -1, 10, 9, 11, 0, 5, -1, -1, 16, 26, 2, -1, 31, -1, 27, -1, 8, -1, 30, 29, 13, 32, 18, 23, -1, -1, -1, -1, -1, -1, 14, 35, 25, 3, 19, -1, 21, 15, -1, -1, 4, -1, 6, -1, -1, -1, 28, 34, 7, -1, 20, 24, 12, 33, 1, 17, -1, -1, -1, -1, -1};
    private static final int[] PATTERN_INDEX_TO_CALENDAR_FIELD = {0, 1, 2, 5, 11, 11, 12, 13, 14, 7, 6, 8, 3, 4, 9, 10, 10, 15, 17, 18, 19, 20, 21, 15, 15, 18, 2, 2, 2, 15, 1, 15, 15, 15, 19, -1, -2};
    private static final int[] PATTERN_INDEX_TO_DATE_FORMAT_FIELD = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37};
    private static final DateFormat.Field[] PATTERN_INDEX_TO_DATE_FORMAT_ATTRIBUTE = {DateFormat.Field.ERA, DateFormat.Field.YEAR, DateFormat.Field.MONTH, DateFormat.Field.DAY_OF_MONTH, DateFormat.Field.HOUR_OF_DAY1, DateFormat.Field.HOUR_OF_DAY0, DateFormat.Field.MINUTE, DateFormat.Field.SECOND, DateFormat.Field.MILLISECOND, DateFormat.Field.DAY_OF_WEEK, DateFormat.Field.DAY_OF_YEAR, DateFormat.Field.DAY_OF_WEEK_IN_MONTH, DateFormat.Field.WEEK_OF_YEAR, DateFormat.Field.WEEK_OF_MONTH, DateFormat.Field.AM_PM, DateFormat.Field.HOUR1, DateFormat.Field.HOUR0, DateFormat.Field.TIME_ZONE, DateFormat.Field.YEAR_WOY, DateFormat.Field.DOW_LOCAL, DateFormat.Field.EXTENDED_YEAR, DateFormat.Field.JULIAN_DAY, DateFormat.Field.MILLISECONDS_IN_DAY, DateFormat.Field.TIME_ZONE, DateFormat.Field.TIME_ZONE, DateFormat.Field.DAY_OF_WEEK, DateFormat.Field.MONTH, DateFormat.Field.QUARTER, DateFormat.Field.QUARTER, DateFormat.Field.TIME_ZONE, DateFormat.Field.YEAR, DateFormat.Field.TIME_ZONE, DateFormat.Field.TIME_ZONE, DateFormat.Field.TIME_ZONE, DateFormat.Field.RELATED_YEAR, DateFormat.Field.AM_PM_MIDNIGHT_NOON, DateFormat.Field.FLEXIBLE_DAY_PERIOD, DateFormat.Field.TIME_SEPARATOR};
    private static ICUCache<String, Object[]> PARSED_PATTERN_CACHE = new SimpleCache();
    static final UnicodeSet DATE_PATTERN_TYPE = new UnicodeSet("[GyYuUQqMLlwWd]").freeze();

    private enum ContextValue {
        UNKNOWN,
        CAPITALIZATION_FOR_MIDDLE_OF_SENTENCE,
        CAPITALIZATION_FOR_BEGINNING_OF_SENTENCE,
        CAPITALIZATION_FOR_UI_LIST_OR_MENU,
        CAPITALIZATION_FOR_STANDALONE
    }

    private boolean allowNumericFallback(int i) {
        return i == 26 || i == 19 || i == 25 || i == 30 || i == 27 || i == 28;
    }

    private static int getLevelFromChar(char c) {
        int[] iArr = PATTERN_CHAR_TO_LEVEL;
        if (c < iArr.length) {
            return iArr[c & 255];
        }
        return -1;
    }

    private static boolean isSyntaxChar(char c) {
        boolean[] zArr = PATTERN_CHAR_IS_SYNTAX;
        if (c < zArr.length) {
            return zArr[c & 255];
        }
        return false;
    }

    public SimpleDateFormat() {
        this(getDefaultPattern(), null, null, null, null, true, null);
    }

    public SimpleDateFormat(String str) {
        this(str, null, null, null, null, true, null);
    }

    public SimpleDateFormat(String str, Locale locale) {
        this(str, null, null, null, ULocale.forLocale(locale), true, null);
    }

    public SimpleDateFormat(String str, ULocale uLocale) {
        this(str, null, null, null, uLocale, true, null);
    }

    public SimpleDateFormat(String str, String str2, ULocale uLocale) {
        this(str, null, null, null, uLocale, false, str2);
    }

    public SimpleDateFormat(String str, DateFormatSymbols dateFormatSymbols) {
        this(str, (DateFormatSymbols) dateFormatSymbols.clone(), null, null, null, true, null);
    }

    @Deprecated
    public SimpleDateFormat(String str, DateFormatSymbols dateFormatSymbols, ULocale uLocale) {
        this(str, (DateFormatSymbols) dateFormatSymbols.clone(), null, null, uLocale, true, null);
    }

    SimpleDateFormat(String str, DateFormatSymbols dateFormatSymbols, Calendar calendar, ULocale uLocale, boolean z, String str2) {
        this(str, (DateFormatSymbols) dateFormatSymbols.clone(), (Calendar) calendar.clone(), null, uLocale, z, str2);
    }

    private SimpleDateFormat(String str, DateFormatSymbols dateFormatSymbols, Calendar calendar, NumberFormat numberFormat, ULocale uLocale, boolean z, String str2) {
        this.serialVersionOnStream = 2;
        this.capitalizationBrkIter = null;
        this.pattern = str;
        this.formatData = dateFormatSymbols;
        this.calendar = calendar;
        this.numberFormat = numberFormat;
        this.locale = uLocale;
        this.useFastFormat = z;
        this.override = str2;
        initialize();
    }

    @Deprecated
    public static SimpleDateFormat getInstance(Calendar.FormatConfiguration formatConfiguration) {
        String overrideString = formatConfiguration.getOverrideString();
        return new SimpleDateFormat(formatConfiguration.getPatternString(), formatConfiguration.getDateFormatSymbols(), formatConfiguration.getCalendar(), null, formatConfiguration.getLocale(), overrideString != null && overrideString.length() > 0, formatConfiguration.getOverrideString());
    }

    private void initialize() {
        ULocale uLocale;
        if (this.locale == null) {
            this.locale = ULocale.getDefault(ULocale.Category.FORMAT);
        }
        if (this.formatData == null) {
            this.formatData = new DateFormatSymbols(this.locale);
        }
        if (this.calendar == null) {
            this.calendar = Calendar.getInstance(this.locale);
        }
        if (this.numberFormat == null) {
            NumberingSystem numberingSystem = NumberingSystem.getInstance(this.locale);
            String description = numberingSystem.getDescription();
            if (numberingSystem.isAlgorithmic() || description.length() != 10) {
                this.numberFormat = NumberFormat.getInstance(this.locale);
            } else {
                this.numberFormat = new DateNumberFormat(this.locale, description, numberingSystem.getName());
            }
        }
        if (this.numberFormat instanceof DecimalFormat) {
            fixNumberFormatForDates(this.numberFormat);
        }
        this.defaultCenturyBase = System.currentTimeMillis();
        setLocale(this.calendar.getLocale(ULocale.VALID_LOCALE), this.calendar.getLocale(ULocale.ACTUAL_LOCALE));
        initLocalZeroPaddingNumberFormat();
        parsePattern();
        if (this.override == null && this.hasHanYearChar && this.calendar != null && this.calendar.getType().equals("japanese") && (uLocale = this.locale) != null && uLocale.getLanguage().equals("ja")) {
            this.override = "y=jpanyear";
        }
        if (this.override != null) {
            initNumberFormatters(this.locale);
        }
    }

    private synchronized void initializeTimeZoneFormat(boolean z) {
        if (!z) {
            if (this.tzFormat == null) {
            }
        }
        this.tzFormat = TimeZoneFormat.getInstance(this.locale);
        String str = null;
        if (this.numberFormat instanceof DecimalFormat) {
            String[] digitStringsLocal = ((DecimalFormat) this.numberFormat).getDecimalFormatSymbols().getDigitStringsLocal();
            StringBuilder sb = new StringBuilder();
            for (String str2 : digitStringsLocal) {
                sb.append(str2);
            }
            str = sb.toString();
        } else if (this.numberFormat instanceof DateNumberFormat) {
            str = new String(((DateNumberFormat) this.numberFormat).getDigits());
        }
        if (str != null && !this.tzFormat.getGMTOffsetDigits().equals(str)) {
            if (this.tzFormat.isFrozen()) {
                this.tzFormat = this.tzFormat.cloneAsThawed();
            }
            this.tzFormat.setGMTOffsetDigits(str);
        }
    }

    private TimeZoneFormat tzFormat() {
        if (this.tzFormat == null) {
            initializeTimeZoneFormat(false);
        }
        return this.tzFormat;
    }

    private static synchronized String getDefaultPattern() {
        ULocale uLocale = ULocale.getDefault(ULocale.Category.FORMAT);
        if (!uLocale.equals(cachedDefaultLocale)) {
            cachedDefaultLocale = uLocale;
            Calendar calendar = Calendar.getInstance(uLocale);
            try {
                ICUResourceBundle iCUResourceBundle = (ICUResourceBundle) UResourceBundle.getBundleInstance(ICUData.ICU_BASE_NAME, cachedDefaultLocale);
                ICUResourceBundle iCUResourceBundleFindWithFallback = iCUResourceBundle.findWithFallback("calendar/" + calendar.getType() + "/DateTimePatterns");
                if (iCUResourceBundleFindWithFallback == null) {
                    iCUResourceBundleFindWithFallback = iCUResourceBundle.findWithFallback("calendar/gregorian/DateTimePatterns");
                }
                if (iCUResourceBundleFindWithFallback == null || iCUResourceBundleFindWithFallback.getSize() < 9) {
                    cachedDefaultPattern = FALLBACKPATTERN;
                } else {
                    cachedDefaultPattern = SimpleFormatterImpl.formatRawPattern(iCUResourceBundleFindWithFallback.getString(iCUResourceBundleFindWithFallback.getSize() >= 13 ? 12 : 8), 2, 2, iCUResourceBundleFindWithFallback.getString(3), iCUResourceBundleFindWithFallback.getString(7));
                }
            } catch (MissingResourceException unused) {
                cachedDefaultPattern = FALLBACKPATTERN;
            }
        }
        return cachedDefaultPattern;
    }

    private void parseAmbiguousDatesAsAfter(Date date) {
        this.defaultCenturyStart = date;
        this.calendar.setTime(date);
        this.defaultCenturyStartYear = this.calendar.get(1);
    }

    private void initializeDefaultCenturyStart(long j) {
        this.defaultCenturyBase = j;
        Calendar calendar = (Calendar) this.calendar.clone();
        calendar.setTimeInMillis(j);
        calendar.add(1, -80);
        this.defaultCenturyStart = calendar.getTime();
        this.defaultCenturyStartYear = calendar.get(1);
    }

    private Date getDefaultCenturyStart() {
        if (this.defaultCenturyStart == null) {
            initializeDefaultCenturyStart(this.defaultCenturyBase);
        }
        return this.defaultCenturyStart;
    }

    private int getDefaultCenturyStartYear() {
        if (this.defaultCenturyStart == null) {
            initializeDefaultCenturyStart(this.defaultCenturyBase);
        }
        return this.defaultCenturyStartYear;
    }

    public void set2DigitYearStart(Date date) {
        parseAmbiguousDatesAsAfter(date);
    }

    public Date get2DigitYearStart() {
        return getDefaultCenturyStart();
    }

    @Override // com.ibm.icu.text.DateFormat
    public void setContext(DisplayContext displayContext) {
        super.setContext(displayContext);
        if (this.capitalizationBrkIter == null) {
            if (displayContext == DisplayContext.CAPITALIZATION_FOR_BEGINNING_OF_SENTENCE || displayContext == DisplayContext.CAPITALIZATION_FOR_UI_LIST_OR_MENU || displayContext == DisplayContext.CAPITALIZATION_FOR_STANDALONE) {
                this.capitalizationBrkIter = BreakIterator.getSentenceInstance(this.locale);
            }
        }
    }

    @Override // com.ibm.icu.text.DateFormat
    public StringBuffer format(Calendar calendar, StringBuffer stringBuffer, FieldPosition fieldPosition) {
        return format(calendar, stringBuffer, fieldPosition, null);
    }

    StringBuffer format(Calendar calendar, StringBuffer stringBuffer, FieldPosition fieldPosition, List<FieldPosition> list) {
        TimeZone timeZone;
        if (calendar == this.calendar || calendar.getType().equals(this.calendar.getType())) {
            timeZone = null;
        } else {
            this.calendar.setTimeInMillis(calendar.getTimeInMillis());
            timeZone = this.calendar.getTimeZone();
            this.calendar.setTimeZone(calendar.getTimeZone());
            calendar = this.calendar;
        }
        StringBuffer stringBuffer2 = format(calendar, getContext(DisplayContext.Type.CAPITALIZATION), stringBuffer, fieldPosition, list);
        if (timeZone != null) {
            this.calendar.setTimeZone(timeZone);
        }
        return stringBuffer2;
    }

    private StringBuffer format(Calendar calendar, DisplayContext displayContext, StringBuffer stringBuffer, FieldPosition fieldPosition, List<FieldPosition> list) {
        int i;
        PatternItem patternItem;
        int i2;
        fieldPosition.setBeginIndex(0);
        fieldPosition.setEndIndex(0);
        Object[] patternItems = getPatternItems();
        int i3 = 0;
        while (i3 < patternItems.length) {
            if (patternItems[i3] instanceof String) {
                stringBuffer.append((String) patternItems[i3]);
                i2 = i3;
            } else {
                PatternItem patternItem2 = (PatternItem) patternItems[i3];
                int length = list != null ? stringBuffer.length() : 0;
                if (this.useFastFormat) {
                    i = length;
                    patternItem = patternItem2;
                    i2 = i3;
                    subFormat(stringBuffer, patternItem2.type, patternItem2.length, stringBuffer.length(), i3, displayContext, fieldPosition, patternItem2.type, calendar);
                } else {
                    i = length;
                    patternItem = patternItem2;
                    i2 = i3;
                    stringBuffer.append(subFormat(patternItem.type, patternItem.length, stringBuffer.length(), i2, displayContext, fieldPosition, patternItem.type, calendar));
                }
                if (list != null) {
                    int length2 = stringBuffer.length();
                    if (length2 - i > 0) {
                        FieldPosition fieldPosition2 = new FieldPosition(patternCharToDateFormatField(patternItem.type));
                        fieldPosition2.setBeginIndex(i);
                        fieldPosition2.setEndIndex(length2);
                        list.add(fieldPosition2);
                    }
                }
            }
            i3 = i2 + 1;
        }
        return stringBuffer;
    }

    private static int getIndexFromChar(char c) {
        int[] iArr = PATTERN_CHAR_TO_INDEX;
        if (c < iArr.length) {
            return iArr[c & 255];
        }
        return -1;
    }

    protected DateFormat.Field patternCharToDateFormatField(char c) {
        int indexFromChar = getIndexFromChar(c);
        if (indexFromChar != -1) {
            return PATTERN_INDEX_TO_DATE_FORMAT_ATTRIBUTE[indexFromChar];
        }
        return null;
    }

    protected String subFormat(char c, int i, int i2, FieldPosition fieldPosition, DateFormatSymbols dateFormatSymbols, Calendar calendar) throws IllegalArgumentException {
        return subFormat(c, i, i2, 0, DisplayContext.CAPITALIZATION_NONE, fieldPosition, c, calendar);
    }

    @Deprecated
    protected String subFormat(char c, int i, int i2, int i3, DisplayContext displayContext, FieldPosition fieldPosition, char c2, Calendar calendar) {
        StringBuffer stringBuffer = new StringBuffer();
        subFormat(stringBuffer, c, i, i2, i3, displayContext, fieldPosition, c2, calendar);
        return stringBuffer.toString();
    }

    /* JADX WARN: Removed duplicated region for block: B:233:0x04af  */
    /* JADX WARN: Removed duplicated region for block: B:235:0x04bd  */
    /* JADX WARN: Removed duplicated region for block: B:326:0x0672  */
    /* JADX WARN: Removed duplicated region for block: B:327:0x067d  */
    /* JADX WARN: Removed duplicated region for block: B:344:0x06e1 A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:351:0x06fd  */
    /* JADX WARN: Removed duplicated region for block: B:363:0x0720  */
    /* JADX WARN: Removed duplicated region for block: B:365:0x0723  */
    /* JADX WARN: Removed duplicated region for block: B:371:0x0758  */
    /* JADX WARN: Removed duplicated region for block: B:378:? A[RETURN, SYNTHETIC] */
    @java.lang.Deprecated
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    protected void subFormat(java.lang.StringBuffer r21, char r22, int r23, int r24, int r25, com.ibm.icu.text.DisplayContext r26, java.text.FieldPosition r27, char r28, com.ibm.icu.util.Calendar r29) {
        /*
            Method dump skipped, instructions count: 2006
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ibm.icu.text.SimpleDateFormat.subFormat(java.lang.StringBuffer, char, int, int, int, com.ibm.icu.text.DisplayContext, java.text.FieldPosition, char, com.ibm.icu.util.Calendar):void");
    }

    /* renamed from: com.ibm.icu.text.SimpleDateFormat$1, reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$ibm$icu$text$DisplayContext;

        static {
            int[] iArr = new int[DisplayContext.values().length];
            $SwitchMap$com$ibm$icu$text$DisplayContext = iArr;
            try {
                iArr[DisplayContext.CAPITALIZATION_FOR_BEGINNING_OF_SENTENCE.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$ibm$icu$text$DisplayContext[DisplayContext.CAPITALIZATION_FOR_UI_LIST_OR_MENU.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$ibm$icu$text$DisplayContext[DisplayContext.CAPITALIZATION_FOR_STANDALONE.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
        }
    }

    private static void safeAppend(String[] strArr, int i, StringBuffer stringBuffer) {
        if (strArr == null || i < 0 || i >= strArr.length) {
            return;
        }
        stringBuffer.append(strArr[i]);
    }

    private static void safeAppendWithMonthPattern(String[] strArr, int i, StringBuffer stringBuffer, String str) {
        if (strArr == null || i < 0 || i >= strArr.length) {
            return;
        }
        if (str == null) {
            stringBuffer.append(strArr[i]);
        } else {
            stringBuffer.append(SimpleFormatterImpl.formatRawPattern(str, 1, 1, strArr[i]));
        }
    }

    private static class PatternItem {
        final boolean isNumeric;
        final int length;
        final char type;

        PatternItem(char c, int i) {
            this.type = c;
            this.length = i;
            this.isNumeric = SimpleDateFormat.isNumeric(c, i);
        }
    }

    private Object[] getPatternItems() {
        Object[] objArr = this.patternItems;
        if (objArr != null) {
            return objArr;
        }
        Object[] objArr2 = PARSED_PATTERN_CACHE.get(this.pattern);
        this.patternItems = objArr2;
        if (objArr2 != null) {
            return objArr2;
        }
        StringBuilder sb = new StringBuilder();
        ArrayList arrayList = new ArrayList();
        char c = 0;
        int i = 1;
        boolean z = false;
        boolean z2 = false;
        for (int i2 = 0; i2 < this.pattern.length(); i2++) {
            char cCharAt = this.pattern.charAt(i2);
            if (cCharAt == '\'') {
                if (z2) {
                    sb.append(PatternTokenizer.SINGLE_QUOTE);
                    z2 = false;
                } else {
                    if (c != 0) {
                        arrayList.add(new PatternItem(c, i));
                        c = 0;
                    }
                    z2 = true;
                }
                z = !z;
            } else {
                if (z) {
                    sb.append(cCharAt);
                } else if (!isSyntaxChar(cCharAt)) {
                    if (c != 0) {
                        arrayList.add(new PatternItem(c, i));
                        c = 0;
                    }
                    sb.append(cCharAt);
                } else if (cCharAt == c) {
                    i++;
                } else {
                    if (c == 0) {
                        if (sb.length() > 0) {
                            arrayList.add(sb.toString());
                            sb.setLength(0);
                        }
                    } else {
                        arrayList.add(new PatternItem(c, i));
                    }
                    c = cCharAt;
                    i = 1;
                }
                z2 = false;
            }
        }
        if (c == 0) {
            if (sb.length() > 0) {
                arrayList.add(sb.toString());
                sb.setLength(0);
            }
        } else {
            arrayList.add(new PatternItem(c, i));
        }
        Object[] array = arrayList.toArray(new Object[arrayList.size()]);
        this.patternItems = array;
        PARSED_PATTERN_CACHE.put(this.pattern, array);
        return this.patternItems;
    }

    @Deprecated
    protected void zeroPaddingNumber(NumberFormat numberFormat, StringBuffer stringBuffer, int i, int i2, int i3) {
        if (this.useLocalZeroPaddingNumberFormat && i >= 0) {
            fastZeroPaddingNumber(stringBuffer, i, i2, i3);
            return;
        }
        numberFormat.setMinimumIntegerDigits(i2);
        numberFormat.setMaximumIntegerDigits(i3);
        numberFormat.format(i, stringBuffer, new FieldPosition(-1));
    }

    @Override // com.ibm.icu.text.DateFormat
    public void setNumberFormat(NumberFormat numberFormat) {
        super.setNumberFormat(numberFormat);
        initLocalZeroPaddingNumberFormat();
        initializeTimeZoneFormat(true);
        if (this.numberFormatters != null) {
            this.numberFormatters = null;
        }
        if (this.overrideMap != null) {
            this.overrideMap = null;
        }
    }

    private void initLocalZeroPaddingNumberFormat() {
        if (this.numberFormat instanceof DecimalFormat) {
            String[] digitStringsLocal = ((DecimalFormat) this.numberFormat).getDecimalFormatSymbols().getDigitStringsLocal();
            this.useLocalZeroPaddingNumberFormat = true;
            this.decDigits = new char[10];
            int i = 0;
            while (true) {
                if (i >= 10) {
                    break;
                }
                if (digitStringsLocal[i].length() > 1) {
                    this.useLocalZeroPaddingNumberFormat = false;
                    break;
                } else {
                    this.decDigits[i] = digitStringsLocal[i].charAt(0);
                    i++;
                }
            }
        } else if (this.numberFormat instanceof DateNumberFormat) {
            this.decDigits = ((DateNumberFormat) this.numberFormat).getDigits();
            this.useLocalZeroPaddingNumberFormat = true;
        } else {
            this.useLocalZeroPaddingNumberFormat = false;
        }
        if (this.useLocalZeroPaddingNumberFormat) {
            this.decimalBuf = new char[10];
        }
    }

    private void fastZeroPaddingNumber(StringBuffer stringBuffer, int i, int i2, int i3) {
        char[] cArr = this.decimalBuf;
        if (cArr.length < i3) {
            i3 = cArr.length;
        }
        int i4 = i3 - 1;
        while (true) {
            this.decimalBuf[i4] = this.decDigits[i % 10];
            i /= 10;
            if (i4 == 0 || i == 0) {
                break;
            } else {
                i4--;
            }
        }
        int i5 = i2 - (i3 - i4);
        while (i5 > 0 && i4 > 0) {
            i4--;
            this.decimalBuf[i4] = this.decDigits[0];
            i5--;
        }
        while (i5 > 0) {
            stringBuffer.append(this.decDigits[0]);
            i5--;
        }
        stringBuffer.append(this.decimalBuf, i4, i3 - i4);
    }

    protected String zeroPaddingNumber(long j, int i, int i2) {
        this.numberFormat.setMinimumIntegerDigits(i);
        this.numberFormat.setMaximumIntegerDigits(i2);
        return this.numberFormat.format(j);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final boolean isNumeric(char c, int i) {
        return NUMERIC_FORMAT_CHARS.indexOf(c) >= 0 || (i <= 2 && NUMERIC_FORMAT_CHARS2.indexOf(c) >= 0);
    }

    /* JADX WARN: Removed duplicated region for block: B:104:0x0252  */
    /* JADX WARN: Removed duplicated region for block: B:172:0x037f A[Catch: IllegalArgumentException -> 0x03fd, TryCatch #0 {IllegalArgumentException -> 0x03fd, blocks: (B:170:0x0379, B:172:0x037f, B:206:0x03f0, B:175:0x0386, B:178:0x038c, B:181:0x039c, B:184:0x03a3, B:187:0x03ae, B:189:0x03b5, B:192:0x03c0, B:195:0x03c8, B:198:0x03d3, B:200:0x03de, B:202:0x03e4, B:168:0x0373), top: B:220:0x0373 }] */
    /* JADX WARN: Removed duplicated region for block: B:175:0x0386 A[Catch: IllegalArgumentException -> 0x03fd, TryCatch #0 {IllegalArgumentException -> 0x03fd, blocks: (B:170:0x0379, B:172:0x037f, B:206:0x03f0, B:175:0x0386, B:178:0x038c, B:181:0x039c, B:184:0x03a3, B:187:0x03ae, B:189:0x03b5, B:192:0x03c0, B:195:0x03c8, B:198:0x03d3, B:200:0x03de, B:202:0x03e4, B:168:0x0373), top: B:220:0x0373 }] */
    /* JADX WARN: Removed duplicated region for block: B:205:0x03ef  */
    @Override // com.ibm.icu.text.DateFormat
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public void parse(java.lang.String r32, com.ibm.icu.util.Calendar r33, java.text.ParsePosition r34) {
        /*
            Method dump skipped, instructions count: 1067
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ibm.icu.text.SimpleDateFormat.parse(java.lang.String, com.ibm.icu.util.Calendar, java.text.ParsePosition):void");
    }

    private int matchLiteral(String str, int i, Object[] objArr, int i2, boolean[] zArr) {
        String str2 = (String) objArr[i2];
        int length = str2.length();
        int length2 = str.length();
        int i3 = i;
        int i4 = 0;
        while (true) {
            if (i4 >= length || i3 >= length2) {
                break;
            }
            char cCharAt = str2.charAt(i4);
            char cCharAt2 = str.charAt(i3);
            if (PatternProps.isWhiteSpace(cCharAt) && PatternProps.isWhiteSpace(cCharAt2)) {
                while (true) {
                    int i5 = i4 + 1;
                    if (i5 >= length || !PatternProps.isWhiteSpace(str2.charAt(i5))) {
                        break;
                    }
                    i4 = i5;
                }
                while (true) {
                    int i6 = i3 + 1;
                    if (i6 >= length2 || !PatternProps.isWhiteSpace(str.charAt(i6))) {
                        break;
                    }
                    i3 = i6;
                }
            } else if (cCharAt != cCharAt2) {
                if (cCharAt2 == '.' && i3 == i && i2 > 0 && getBooleanAttribute(DateFormat.BooleanAttribute.PARSE_ALLOW_WHITESPACE)) {
                    Object obj = objArr[i2 - 1];
                    if (!(obj instanceof PatternItem) || ((PatternItem) obj).isNumeric) {
                        break;
                    }
                    i3++;
                } else {
                    if (((cCharAt != ' ' && cCharAt != '.') || !getBooleanAttribute(DateFormat.BooleanAttribute.PARSE_ALLOW_WHITESPACE)) && (i3 == i || !getBooleanAttribute(DateFormat.BooleanAttribute.PARSE_PARTIAL_LITERAL_MATCH))) {
                        break;
                    }
                    i4++;
                }
            }
            i4++;
            i3++;
        }
        zArr[0] = i4 == length;
        if (!zArr[0] && getBooleanAttribute(DateFormat.BooleanAttribute.PARSE_ALLOW_WHITESPACE) && i2 > 0 && i2 < objArr.length - 1 && i < length2) {
            Object obj2 = objArr[i2 - 1];
            Object obj3 = objArr[i2 + 1];
            if ((obj2 instanceof PatternItem) && (obj3 instanceof PatternItem)) {
                if (DATE_PATTERN_TYPE.contains(((PatternItem) obj2).type) != DATE_PATTERN_TYPE.contains(((PatternItem) obj3).type)) {
                    i3 = i;
                    while (i3 < length2 && PatternProps.isWhiteSpace(str.charAt(i3))) {
                        i3++;
                    }
                    zArr[0] = i3 > i;
                }
            }
        }
        return i3;
    }

    protected int matchString(String str, int i, int i2, String[] strArr, Calendar calendar) {
        return matchString(str, i, i2, strArr, null, calendar);
    }

    @Deprecated
    private int matchString(String str, int i, int i2, String[] strArr, String str2, Calendar calendar) {
        String rawPattern;
        int length;
        int iRegionMatchesWithOptionalDot;
        int iRegionMatchesWithOptionalDot2;
        int length2 = strArr.length;
        int i3 = -1;
        int i4 = 0;
        int i5 = 0;
        for (int i6 = i2 == 7 ? 1 : 0; i6 < length2; i6++) {
            int length3 = strArr[i6].length();
            if (length3 > i4 && (iRegionMatchesWithOptionalDot2 = regionMatchesWithOptionalDot(str, i, strArr[i6], length3)) >= 0) {
                i3 = i6;
                i4 = iRegionMatchesWithOptionalDot2;
                i5 = 0;
            }
            if (str2 != null && (length = (rawPattern = SimpleFormatterImpl.formatRawPattern(str2, 1, 1, strArr[i6])).length()) > i4 && (iRegionMatchesWithOptionalDot = regionMatchesWithOptionalDot(str, i, rawPattern, length)) >= 0) {
                i3 = i6;
                i4 = iRegionMatchesWithOptionalDot;
                i5 = 1;
            }
        }
        if (i3 < 0) {
            return ~i;
        }
        if (i2 >= 0) {
            if (i2 == 1) {
                i3++;
            }
            calendar.set(i2, i3);
            if (str2 != null) {
                calendar.set(22, i5);
            }
        }
        return i + i4;
    }

    private int regionMatchesWithOptionalDot(String str, int i, String str2, int i2) {
        if (str.regionMatches(true, i, str2, 0, i2)) {
            return i2;
        }
        if (str2.length() <= 0 || str2.charAt(str2.length() - 1) != '.') {
            return -1;
        }
        int i3 = i2 - 1;
        if (str.regionMatches(true, i, str2, 0, i3)) {
            return i3;
        }
        return -1;
    }

    protected int matchQuarterString(String str, int i, int i2, String[] strArr, Calendar calendar) {
        int iRegionMatchesWithOptionalDot;
        int length = strArr.length;
        int i3 = 0;
        int i4 = -1;
        for (int i5 = 0; i5 < length; i5++) {
            int length2 = strArr[i5].length();
            if (length2 > i3 && (iRegionMatchesWithOptionalDot = regionMatchesWithOptionalDot(str, i, strArr[i5], length2)) >= 0) {
                i4 = i5;
                i3 = iRegionMatchesWithOptionalDot;
            }
        }
        if (i4 < 0) {
            return -i;
        }
        calendar.set(i2, i4 * 3);
        return i + i3;
    }

    /* JADX WARN: Multi-variable type inference failed */
    private int matchDayPeriodString(String str, int i, String[] strArr, int i2, Output<DayPeriodRules.DayPeriod> output) {
        int length;
        int iRegionMatchesWithOptionalDot;
        int i3 = 0;
        int i4 = -1;
        for (int i5 = 0; i5 < i2; i5++) {
            if (strArr[i5] != null && (length = strArr[i5].length()) > i3 && (iRegionMatchesWithOptionalDot = regionMatchesWithOptionalDot(str, i, strArr[i5], length)) >= 0) {
                i4 = i5;
                i3 = iRegionMatchesWithOptionalDot;
            }
        }
        if (i4 < 0) {
            return -i;
        }
        output.value = DayPeriodRules.DayPeriod.VALUES[i4];
        return i + i3;
    }

    protected int subParse(String str, int i, char c, int i2, boolean z, boolean z2, boolean[] zArr, Calendar calendar) {
        return subParse(str, i, c, i2, z, z2, zArr, calendar, null, null);
    }

    private int subParse(String str, int i, char c, int i2, boolean z, boolean z2, boolean[] zArr, Calendar calendar, MessageFormat messageFormat, Output<TimeZoneFormat.TimeType> output) {
        return subParse(str, i, c, i2, z, z2, zArr, calendar, null, null, null);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:68:0x00e5  */
    /* JADX WARN: Removed duplicated region for block: B:80:0x0118  */
    /* JADX WARN: Removed duplicated region for block: B:82:0x011e  */
    /* JADX WARN: Removed duplicated region for block: B:83:0x0126  */
    /* JADX WARN: Type inference failed for: r9v17 */
    /* JADX WARN: Type inference failed for: r9v18 */
    /* JADX WARN: Type inference failed for: r9v19 */
    /* JADX WARN: Type inference failed for: r9v6, types: [boolean, int] */
    @java.lang.Deprecated
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    private int subParse(java.lang.String r21, int r22, char r23, int r24, boolean r25, boolean r26, boolean[] r27, com.ibm.icu.util.Calendar r28, com.ibm.icu.text.MessageFormat r29, com.ibm.icu.util.Output<com.ibm.icu.text.TimeZoneFormat.TimeType> r30, com.ibm.icu.util.Output<com.ibm.icu.impl.DayPeriodRules.DayPeriod> r31) {
        /*
            Method dump skipped, instructions count: 2172
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ibm.icu.text.SimpleDateFormat.subParse(java.lang.String, int, char, int, boolean, boolean, boolean[], com.ibm.icu.util.Calendar, com.ibm.icu.text.MessageFormat, com.ibm.icu.util.Output, com.ibm.icu.util.Output):int");
    }

    private Number parseInt(String str, ParsePosition parsePosition, boolean z, NumberFormat numberFormat) {
        return parseInt(str, -1, parsePosition, z, numberFormat);
    }

    private Number parseInt(String str, int i, ParsePosition parsePosition, boolean z, NumberFormat numberFormat) {
        Number number;
        int index;
        int index2 = parsePosition.getIndex();
        if (z) {
            number = numberFormat.parse(str, parsePosition);
        } else if (numberFormat instanceof DecimalFormat) {
            DecimalFormat decimalFormat = (DecimalFormat) numberFormat;
            String negativePrefix = decimalFormat.getNegativePrefix();
            decimalFormat.setNegativePrefix(SUPPRESS_NEGATIVE_PREFIX);
            number = numberFormat.parse(str, parsePosition);
            decimalFormat.setNegativePrefix(negativePrefix);
        } else {
            boolean z2 = numberFormat instanceof DateNumberFormat;
            if (z2) {
                ((DateNumberFormat) numberFormat).setParsePositiveOnly(true);
            }
            number = numberFormat.parse(str, parsePosition);
            if (z2) {
                ((DateNumberFormat) numberFormat).setParsePositiveOnly(false);
            }
        }
        if (i <= 0 || (index = parsePosition.getIndex() - index2) <= i) {
            return number;
        }
        double dDoubleValue = number.doubleValue();
        for (int i2 = index - i; i2 > 0; i2--) {
            dDoubleValue /= 10.0d;
        }
        parsePosition.setIndex(index2 + i);
        return Integer.valueOf((int) dDoubleValue);
    }

    private static int countDigits(String str, int i, int i2) {
        int i3 = 0;
        while (i < i2) {
            int iCodePointAt = str.codePointAt(i);
            if (UCharacter.isDigit(iCodePointAt)) {
                i3++;
            }
            i += UCharacter.charCount(iCodePointAt);
        }
        return i3;
    }

    private String translatePattern(String str, String str2, String str3) {
        int iIndexOf;
        StringBuilder sb = new StringBuilder();
        boolean z = false;
        for (int i = 0; i < str.length(); i++) {
            char cCharAt = str.charAt(i);
            if (z) {
                if (cCharAt == '\'') {
                    z = false;
                }
            } else if (cCharAt == '\'') {
                z = true;
            } else if (isSyntaxChar(cCharAt) && (iIndexOf = str2.indexOf(cCharAt)) != -1) {
                cCharAt = str3.charAt(iIndexOf);
            }
            sb.append(cCharAt);
        }
        if (z) {
            throw new IllegalArgumentException("Unfinished quote in pattern");
        }
        return sb.toString();
    }

    public String toPattern() {
        return this.pattern;
    }

    public String toLocalizedPattern() {
        return translatePattern(this.pattern, "GyMdkHmsSEDFwWahKzYeugAZvcLQqVUOXxrbB", this.formatData.localPatternChars);
    }

    public void applyPattern(String str) {
        ULocale uLocale;
        this.pattern = str;
        parsePattern();
        setLocale(null, null);
        this.patternItems = null;
        if (this.calendar == null || !this.calendar.getType().equals("japanese") || (uLocale = this.locale) == null || !uLocale.getLanguage().equals("ja")) {
            return;
        }
        String str2 = this.override;
        if (str2 != null && str2.equals("y=jpanyear") && !this.hasHanYearChar) {
            this.numberFormatters = null;
            this.overrideMap = null;
            this.override = null;
            return;
        }
        if (this.override == null && this.hasHanYearChar) {
            this.numberFormatters = new HashMap<>();
            HashMap<Character, String> map = new HashMap<>();
            this.overrideMap = map;
            map.put('y', "jpanyear");
            NumberFormat numberFormatCreateInstance = NumberFormat.createInstance(new ULocale(this.locale.getBaseName() + "@numbers=jpanyear"), 0);
            numberFormatCreateInstance.setGroupingUsed(false);
            this.useLocalZeroPaddingNumberFormat = false;
            this.numberFormatters.put("jpanyear", numberFormatCreateInstance);
            this.override = "y=jpanyear";
        }
    }

    public void applyLocalizedPattern(String str) {
        this.pattern = translatePattern(str, this.formatData.localPatternChars, "GyMdkHmsSEDFwWahKzYeugAZvcLQqVUOXxrbB");
        setLocale(null, null);
    }

    public DateFormatSymbols getDateFormatSymbols() {
        return (DateFormatSymbols) this.formatData.clone();
    }

    public void setDateFormatSymbols(DateFormatSymbols dateFormatSymbols) {
        this.formatData = (DateFormatSymbols) dateFormatSymbols.clone();
    }

    protected DateFormatSymbols getSymbols() {
        return this.formatData;
    }

    public TimeZoneFormat getTimeZoneFormat() {
        return tzFormat().freeze();
    }

    public void setTimeZoneFormat(TimeZoneFormat timeZoneFormat) {
        if (timeZoneFormat.isFrozen()) {
            this.tzFormat = timeZoneFormat;
        } else {
            this.tzFormat = timeZoneFormat.cloneAsThawed().freeze();
        }
    }

    @Override // com.ibm.icu.text.DateFormat, java.text.Format
    public Object clone() {
        SimpleDateFormat simpleDateFormat = (SimpleDateFormat) super.clone();
        simpleDateFormat.formatData = (DateFormatSymbols) this.formatData.clone();
        if (this.decimalBuf != null) {
            simpleDateFormat.decimalBuf = new char[10];
        }
        return simpleDateFormat;
    }

    @Override // com.ibm.icu.text.DateFormat
    public int hashCode() {
        return this.pattern.hashCode();
    }

    @Override // com.ibm.icu.text.DateFormat
    public boolean equals(Object obj) {
        if (!super.equals(obj)) {
            return false;
        }
        SimpleDateFormat simpleDateFormat = (SimpleDateFormat) obj;
        return this.pattern.equals(simpleDateFormat.pattern) && this.formatData.equals(simpleDateFormat.formatData);
    }

    private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
        if (this.defaultCenturyStart == null) {
            initializeDefaultCenturyStart(this.defaultCenturyBase);
        }
        initializeTimeZoneFormat(false);
        objectOutputStream.defaultWriteObject();
        objectOutputStream.writeInt(getContext(DisplayContext.Type.CAPITALIZATION).value());
    }

    private void readObject(ObjectInputStream objectInputStream) throws ClassNotFoundException, IOException {
        objectInputStream.defaultReadObject();
        int i = this.serialVersionOnStream > 1 ? objectInputStream.readInt() : -1;
        if (this.serialVersionOnStream < 1) {
            this.defaultCenturyBase = System.currentTimeMillis();
        } else {
            parseAmbiguousDatesAsAfter(this.defaultCenturyStart);
        }
        this.serialVersionOnStream = 2;
        ULocale locale = getLocale(ULocale.VALID_LOCALE);
        this.locale = locale;
        if (locale == null) {
            this.locale = ULocale.getDefault(ULocale.Category.FORMAT);
        }
        initLocalZeroPaddingNumberFormat();
        setContext(DisplayContext.CAPITALIZATION_NONE);
        if (i >= 0) {
            DisplayContext[] displayContextArrValues = DisplayContext.values();
            int length = displayContextArrValues.length;
            int i2 = 0;
            while (true) {
                if (i2 >= length) {
                    break;
                }
                DisplayContext displayContext = displayContextArrValues[i2];
                if (displayContext.value() == i) {
                    setContext(displayContext);
                    break;
                }
                i2++;
            }
        }
        if (!getBooleanAttribute(DateFormat.BooleanAttribute.PARSE_PARTIAL_MATCH)) {
            setBooleanAttribute(DateFormat.BooleanAttribute.PARSE_PARTIAL_LITERAL_MATCH, false);
        }
        parsePattern();
    }

    @Override // java.text.Format
    public AttributedCharacterIterator formatToCharacterIterator(Object obj) {
        Calendar calendar = this.calendar;
        if (obj instanceof Calendar) {
            calendar = (Calendar) obj;
        } else if (obj instanceof Date) {
            this.calendar.setTime((Date) obj);
        } else if (obj instanceof Number) {
            this.calendar.setTimeInMillis(((Number) obj).longValue());
        } else {
            throw new IllegalArgumentException("Cannot format given Object as a Date");
        }
        Calendar calendar2 = calendar;
        StringBuffer stringBuffer = new StringBuffer();
        FieldPosition fieldPosition = new FieldPosition(0);
        ArrayList arrayList = new ArrayList();
        format(calendar2, getContext(DisplayContext.Type.CAPITALIZATION), stringBuffer, fieldPosition, arrayList);
        AttributedString attributedString = new AttributedString(stringBuffer.toString());
        for (int i = 0; i < arrayList.size(); i++) {
            FieldPosition fieldPosition2 = arrayList.get(i);
            Format.Field fieldAttribute = fieldPosition2.getFieldAttribute();
            attributedString.addAttribute(fieldAttribute, fieldAttribute, fieldPosition2.getBeginIndex(), fieldPosition2.getEndIndex());
        }
        return attributedString.getIterator();
    }

    ULocale getLocale() {
        return this.locale;
    }

    boolean isFieldUnitIgnored(int i) {
        return isFieldUnitIgnored(this.pattern, i);
    }

    static boolean isFieldUnitIgnored(String str, int i) {
        int i2 = CALENDAR_FIELD_TO_LEVEL[i];
        int i3 = 0;
        int i4 = 0;
        char c = 0;
        boolean z = false;
        while (i3 < str.length()) {
            char cCharAt = str.charAt(i3);
            if (cCharAt != c && i4 > 0) {
                if (i2 <= getLevelFromChar(c)) {
                    return false;
                }
                i4 = 0;
            }
            if (cCharAt == '\'') {
                int i5 = i3 + 1;
                if (i5 >= str.length() || str.charAt(i5) != '\'') {
                    z = !z;
                } else {
                    i3 = i5;
                }
            } else if (!z && isSyntaxChar(cCharAt)) {
                i4++;
                c = cCharAt;
            }
            i3++;
        }
        return i4 <= 0 || i2 > getLevelFromChar(c);
    }

    @Deprecated
    public final StringBuffer intervalFormatByAlgorithm(Calendar calendar, Calendar calendar2, StringBuffer stringBuffer, FieldPosition fieldPosition) throws IllegalArgumentException {
        int i;
        int i2;
        int i3;
        int i4;
        String str;
        if (!calendar.isEquivalentTo(calendar2)) {
            throw new IllegalArgumentException("can not format on two different calendars");
        }
        Object[] patternItems = getPatternItems();
        int i5 = 0;
        while (true) {
            try {
                if (i5 >= patternItems.length) {
                    i5 = -1;
                    break;
                }
                if (diffCalFieldValue(calendar, calendar2, patternItems, i5)) {
                    break;
                }
                i5++;
            } catch (IllegalArgumentException e) {
                throw new IllegalArgumentException(e.toString());
            }
        }
        if (i5 == -1) {
            return format(calendar, stringBuffer, fieldPosition);
        }
        int length = patternItems.length - 1;
        while (true) {
            if (length < i5) {
                length = -1;
                break;
            }
            if (diffCalFieldValue(calendar, calendar2, patternItems, length)) {
                break;
            }
            length--;
        }
        String str2 = " – ";
        if (i5 == 0 && length == patternItems.length - 1) {
            format(calendar, stringBuffer, fieldPosition);
            stringBuffer.append(" – ");
            format(calendar2, stringBuffer, fieldPosition);
            return stringBuffer;
        }
        int i6 = 1000;
        for (int i7 = i5; i7 <= length; i7++) {
            if (!(patternItems[i7] instanceof String)) {
                char c = ((PatternItem) patternItems[i7]).type;
                int indexFromChar = getIndexFromChar(c);
                if (indexFromChar == -1) {
                    throw new IllegalArgumentException("Illegal pattern character '" + c + "' in \"" + this.pattern + '\"');
                }
                if (indexFromChar < i6) {
                    i6 = indexFromChar;
                }
            }
        }
        int i8 = 0;
        while (true) {
            if (i8 >= i5) {
                i = i5;
                break;
            }
            try {
                if (lowerLevel(patternItems, i8, i6)) {
                    i = i8;
                    break;
                }
                i8++;
            } catch (IllegalArgumentException e2) {
                throw new IllegalArgumentException(e2.toString());
            }
        }
        int length2 = patternItems.length - 1;
        while (true) {
            if (length2 <= length) {
                i2 = length;
                break;
            }
            if (lowerLevel(patternItems, length2, i6)) {
                i2 = length2;
                break;
            }
            length2--;
        }
        if (i == 0 && i2 == patternItems.length - 1) {
            format(calendar, stringBuffer, fieldPosition);
            stringBuffer.append(" – ");
            format(calendar2, stringBuffer, fieldPosition);
            return stringBuffer;
        }
        fieldPosition.setBeginIndex(0);
        fieldPosition.setEndIndex(0);
        DisplayContext context = getContext(DisplayContext.Type.CAPITALIZATION);
        int i9 = 0;
        while (i9 <= i2) {
            if (patternItems[i9] instanceof String) {
                stringBuffer.append((String) patternItems[i9]);
                i3 = i9;
                i4 = i2;
                str = str2;
            } else {
                PatternItem patternItem = (PatternItem) patternItems[i9];
                if (this.useFastFormat) {
                    i3 = i9;
                    i4 = i2;
                    str = str2;
                    subFormat(stringBuffer, patternItem.type, patternItem.length, stringBuffer.length(), i9, context, fieldPosition, patternItem.type, calendar);
                } else {
                    i3 = i9;
                    i4 = i2;
                    str = str2;
                    stringBuffer.append(subFormat(patternItem.type, patternItem.length, stringBuffer.length(), i3, context, fieldPosition, patternItem.type, calendar));
                }
            }
            i9 = i3 + 1;
            str2 = str;
            i2 = i4;
        }
        stringBuffer.append(str2);
        for (int i10 = i; i10 < patternItems.length; i10++) {
            if (patternItems[i10] instanceof String) {
                stringBuffer.append((String) patternItems[i10]);
            } else {
                PatternItem patternItem2 = (PatternItem) patternItems[i10];
                if (this.useFastFormat) {
                    subFormat(stringBuffer, patternItem2.type, patternItem2.length, stringBuffer.length(), i10, context, fieldPosition, patternItem2.type, calendar2);
                } else {
                    stringBuffer.append(subFormat(patternItem2.type, patternItem2.length, stringBuffer.length(), i10, context, fieldPosition, patternItem2.type, calendar2));
                }
            }
        }
        return stringBuffer;
    }

    private boolean diffCalFieldValue(Calendar calendar, Calendar calendar2, Object[] objArr, int i) throws IllegalArgumentException {
        if (objArr[i] instanceof String) {
            return false;
        }
        char c = ((PatternItem) objArr[i]).type;
        int indexFromChar = getIndexFromChar(c);
        if (indexFromChar == -1) {
            throw new IllegalArgumentException("Illegal pattern character '" + c + "' in \"" + this.pattern + '\"');
        }
        int i2 = PATTERN_INDEX_TO_CALENDAR_FIELD[indexFromChar];
        return i2 >= 0 && calendar.get(i2) != calendar2.get(i2);
    }

    private boolean lowerLevel(Object[] objArr, int i, int i2) throws IllegalArgumentException {
        if (objArr[i] instanceof String) {
            return false;
        }
        char c = ((PatternItem) objArr[i]).type;
        int levelFromChar = getLevelFromChar(c);
        if (levelFromChar != -1) {
            return levelFromChar >= i2;
        }
        throw new IllegalArgumentException("Illegal pattern character '" + c + "' in \"" + this.pattern + '\"');
    }

    public void setNumberFormat(String str, NumberFormat numberFormat) {
        numberFormat.setGroupingUsed(false);
        String str2 = "$" + UUID.randomUUID().toString();
        if (this.numberFormatters == null) {
            this.numberFormatters = new HashMap<>();
        }
        if (this.overrideMap == null) {
            this.overrideMap = new HashMap<>();
        }
        for (int i = 0; i < str.length(); i++) {
            char cCharAt = str.charAt(i);
            if ("GyMdkHmsSEDFwWahKzYeugAZvcLQqVUOXxrbB".indexOf(cCharAt) == -1) {
                throw new IllegalArgumentException("Illegal field character '" + cCharAt + "' in setNumberFormat.");
            }
            this.overrideMap.put(Character.valueOf(cCharAt), str2);
            this.numberFormatters.put(str2, numberFormat);
        }
        this.useLocalZeroPaddingNumberFormat = false;
    }

    public NumberFormat getNumberFormat(char c) {
        Character chValueOf = Character.valueOf(c);
        HashMap<Character, String> map = this.overrideMap;
        if (map != null && map.containsKey(chValueOf)) {
            return this.numberFormatters.get(this.overrideMap.get(chValueOf).toString());
        }
        return this.numberFormat;
    }

    private void initNumberFormatters(ULocale uLocale) {
        this.numberFormatters = new HashMap<>();
        this.overrideMap = new HashMap<>();
        processOverrideString(uLocale, this.override);
    }

    private void processOverrideString(ULocale uLocale, String str) {
        boolean z;
        int length;
        boolean z2;
        if (str == null || str.length() == 0) {
            return;
        }
        boolean z3 = true;
        int i = 0;
        while (z3) {
            int iIndexOf = str.indexOf(";", i);
            if (iIndexOf == -1) {
                length = str.length();
                z = false;
            } else {
                z = z3;
                length = iIndexOf;
            }
            String strSubstring = str.substring(i, length);
            int iIndexOf2 = strSubstring.indexOf(ContainerUtils.KEY_VALUE_DELIMITER);
            if (iIndexOf2 == -1) {
                z2 = true;
            } else {
                String strSubstring2 = strSubstring.substring(iIndexOf2 + 1);
                this.overrideMap.put(Character.valueOf(strSubstring.charAt(0)), strSubstring2);
                strSubstring = strSubstring2;
                z2 = false;
            }
            NumberFormat numberFormatCreateInstance = NumberFormat.createInstance(new ULocale(uLocale.getBaseName() + "@numbers=" + strSubstring), 0);
            numberFormatCreateInstance.setGroupingUsed(false);
            if (z2) {
                setNumberFormat(numberFormatCreateInstance);
            } else {
                this.useLocalZeroPaddingNumberFormat = false;
            }
            if (!z2 && !this.numberFormatters.containsKey(strSubstring)) {
                this.numberFormatters.put(strSubstring, numberFormatCreateInstance);
            }
            i = iIndexOf + 1;
            z3 = z;
        }
    }

    private void parsePattern() {
        this.hasMinute = false;
        this.hasSecond = false;
        this.hasHanYearChar = false;
        boolean z = false;
        for (int i = 0; i < this.pattern.length(); i++) {
            char cCharAt = this.pattern.charAt(i);
            if (cCharAt == '\'') {
                z = !z;
            }
            if (cCharAt == 24180) {
                this.hasHanYearChar = true;
            }
            if (!z) {
                if (cCharAt == 'm') {
                    this.hasMinute = true;
                }
                if (cCharAt == 's') {
                    this.hasSecond = true;
                }
            }
        }
    }
}
