package com.ibm.icu.text;

import com.facebook.GraphRequest;
import com.facebook.places.model.PlaceFields;
import com.ibm.icu.impl.ICUCache;
import com.ibm.icu.impl.ICUData;
import com.ibm.icu.impl.ICUResourceBundle;
import com.ibm.icu.impl.PatternTokenizer;
import com.ibm.icu.impl.SimpleCache;
import com.ibm.icu.impl.SimpleFormatterImpl;
import com.ibm.icu.impl.UResource;
import com.ibm.icu.impl.locale.BaseLocale;
import com.ibm.icu.impl.units.UnitsData;
import com.ibm.icu.text.DateFormat;
import com.ibm.icu.util.Calendar;
import com.ibm.icu.util.Freezable;
import com.ibm.icu.util.ICUCloneNotSupportedException;
import com.ibm.icu.util.Region;
import com.ibm.icu.util.ULocale;
import com.ibm.icu.util.UResourceBundle;
import it.piapostolos.android.pinotes.R2;
import it.piapostolos.android.pinotes.utils.ConstantsBase;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.BitSet;
import java.util.Collections;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

/* loaded from: classes2.dex */
public class DateTimePatternGenerator implements Freezable<DateTimePatternGenerator>, Cloneable {
    private static final DisplayWidth APPENDITEM_WIDTH;
    private static final int APPENDITEM_WIDTH_INT;
    private static final String[] CANONICAL_ITEMS;
    private static final Set<String> CANONICAL_SET;
    private static final String[] CLDR_FIELD_APPEND;
    private static final String[] CLDR_FIELD_NAME;
    private static final DisplayWidth[] CLDR_FIELD_WIDTH;
    private static final int DATE_MASK = 1023;
    public static final int DAY = 7;
    public static final int DAYPERIOD = 10;
    public static final int DAY_OF_WEEK_IN_MONTH = 9;
    public static final int DAY_OF_YEAR = 8;
    private static final boolean DEBUG = false;
    private static final int DELTA = 16;
    private static ICUCache<String, DateTimePatternGenerator> DTPNG_CACHE = null;
    public static final int ERA = 0;
    private static final int EXTRA_FIELD = 65536;
    private static final String[] FIELD_NAME;
    private static final int FRACTIONAL_MASK = 16384;
    public static final int FRACTIONAL_SECOND = 14;
    public static final int HOUR = 11;
    private static final String[] LAST_RESORT_ALLOWED_HOUR_FORMAT = {DateFormat.HOUR24};
    static final Map<String, String[]> LOCALE_TO_ALLOWED_HOUR;
    private static final int LONG = -260;
    public static final int MATCH_ALL_FIELDS_LENGTH = 65535;
    public static final int MATCH_HOUR_FIELD_LENGTH = 2048;

    @Deprecated
    public static final int MATCH_MINUTE_FIELD_LENGTH = 4096;
    public static final int MATCH_NO_OPTIONS = 0;

    @Deprecated
    public static final int MATCH_SECOND_FIELD_LENGTH = 8192;
    public static final int MINUTE = 12;
    private static final int MISSING_FIELD = 4096;
    public static final int MONTH = 3;
    private static final int NARROW = -257;
    private static final int NONE = 0;
    private static final int NUMERIC = 256;
    public static final int QUARTER = 2;
    public static final int SECOND = 13;
    private static final int SECOND_AND_FRACTIONAL_MASK = 24576;
    private static final int SHORT = -259;
    private static final int SHORTER = -258;
    private static final int TIME_MASK = 64512;

    @Deprecated
    public static final int TYPE_LIMIT = 16;
    public static final int WEEKDAY = 6;
    public static final int WEEK_OF_MONTH = 5;
    public static final int WEEK_OF_YEAR = 4;
    public static final int YEAR = 1;
    public static final int ZONE = 15;
    private static final int[][] types;
    private transient DistanceInfo _distanceInfo;
    private String[] allowedHourFormats;
    private transient DateTimeMatcher current;
    private TreeMap<DateTimeMatcher, PatternWithSkeletonFlag> skeleton2pattern = new TreeMap<>();
    private TreeMap<String, PatternWithSkeletonFlag> basePattern_pattern = new TreeMap<>();
    private String decimal = "?";
    private String dateTimeFormat = "{1} {0}";
    private String[] appendItemFormats = new String[16];
    private String[][] fieldDisplayNames = (String[][]) Array.newInstance((Class<?>) String.class, 16, DisplayWidth.COUNT);
    private char defaultHourFormatChar = 'H';
    private volatile boolean frozen = false;
    private transient FormatParser fp = new FormatParser();
    private Set<String> cldrAvailableFormatKeys = new HashSet(20);

    private enum DTPGflags {
        FIX_FRACTIONAL_SECONDS,
        SKELETON_USES_CAP_J
    }

    public static final class PatternInfo {
        public static final int BASE_CONFLICT = 1;
        public static final int CONFLICT = 2;
        public static final int OK = 0;
        public String conflictingPattern;
        public int status;
    }

    private int getTopBitNumber(int i) {
        int i2 = 0;
        while (i != 0) {
            i >>>= 1;
            i2++;
        }
        return i2 - 1;
    }

    public static DateTimePatternGenerator getEmptyInstance() {
        DateTimePatternGenerator dateTimePatternGenerator = new DateTimePatternGenerator();
        dateTimePatternGenerator.addCanonicalItems();
        dateTimePatternGenerator.fillInMissing();
        return dateTimePatternGenerator;
    }

    protected DateTimePatternGenerator() {
        this.current = new DateTimeMatcher();
        this._distanceInfo = new DistanceInfo();
    }

    public static DateTimePatternGenerator getInstance() {
        return getInstance(ULocale.getDefault(ULocale.Category.FORMAT));
    }

    public static DateTimePatternGenerator getInstance(ULocale uLocale) {
        return getFrozenInstance(uLocale).cloneAsThawed();
    }

    public static DateTimePatternGenerator getInstance(Locale locale) {
        return getInstance(ULocale.forLocale(locale));
    }

    @Deprecated
    public static DateTimePatternGenerator getFrozenInstance(ULocale uLocale) {
        String string = uLocale.toString();
        DateTimePatternGenerator dateTimePatternGenerator = DTPNG_CACHE.get(string);
        if (dateTimePatternGenerator != null) {
            return dateTimePatternGenerator;
        }
        DateTimePatternGenerator dateTimePatternGenerator2 = new DateTimePatternGenerator();
        dateTimePatternGenerator2.initData(uLocale, false);
        dateTimePatternGenerator2.freeze();
        DTPNG_CACHE.put(string, dateTimePatternGenerator2);
        return dateTimePatternGenerator2;
    }

    @Deprecated
    public static DateTimePatternGenerator getInstanceNoStdPat(ULocale uLocale) {
        DateTimePatternGenerator dateTimePatternGenerator = new DateTimePatternGenerator();
        dateTimePatternGenerator.initData(uLocale, true);
        return dateTimePatternGenerator;
    }

    private void initData(ULocale uLocale, boolean z) {
        PatternInfo patternInfo = new PatternInfo();
        addCanonicalItems();
        if (!z) {
            addICUPatterns(patternInfo, uLocale);
        }
        addCLDRData(patternInfo, uLocale);
        if (!z) {
            setDateTimeFromCalendar(uLocale);
        } else {
            setDateTimeFormat("{1} {0}");
        }
        setDecimalSymbols(uLocale);
        getAllowedHourFormats(uLocale);
        fillInMissing();
    }

    private void addICUPatterns(PatternInfo patternInfo, ULocale uLocale) {
        for (int i = 0; i <= 3; i++) {
            addPattern(((SimpleDateFormat) DateFormat.getDateInstance(i, uLocale)).toPattern(), false, patternInfo);
            SimpleDateFormat simpleDateFormat = (SimpleDateFormat) DateFormat.getTimeInstance(i, uLocale);
            addPattern(simpleDateFormat.toPattern(), false, patternInfo);
            if (i == 3) {
                consumeShortTimePattern(simpleDateFormat.toPattern(), patternInfo);
            }
        }
    }

    private String getCalendarTypeToUse(ULocale uLocale) {
        String keywordValue = uLocale.getKeywordValue("calendar");
        if (keywordValue == null) {
            keywordValue = Calendar.getKeywordValuesForLocale("calendar", uLocale, true)[0];
        }
        return keywordValue == null ? "gregorian" : keywordValue;
    }

    private void consumeShortTimePattern(String str, PatternInfo patternInfo) {
        hackTimes(patternInfo, str);
    }

    private class AppendItemFormatsSink extends UResource.Sink {
        static final /* synthetic */ boolean $assertionsDisabled = false;

        private AppendItemFormatsSink() {
        }

        @Override // com.ibm.icu.impl.UResource.Sink
        public void put(UResource.Key key, UResource.Value value, boolean z) {
            UResource.Table table = value.getTable();
            for (int i = 0; table.getKeyAndValue(i, key, value); i++) {
                int appendFormatNumber = DateTimePatternGenerator.getAppendFormatNumber(key);
                if (DateTimePatternGenerator.this.getAppendItemFormat(appendFormatNumber) == null) {
                    DateTimePatternGenerator.this.setAppendItemFormat(appendFormatNumber, value.toString());
                }
            }
        }
    }

    private class AppendItemNamesSink extends UResource.Sink {
        private AppendItemNamesSink() {
        }

        @Override // com.ibm.icu.impl.UResource.Sink
        public void put(UResource.Key key, UResource.Value value, boolean z) {
            int cLDRFieldAndWidthNumber;
            UResource.Table table = value.getTable();
            for (int i = 0; table.getKeyAndValue(i, key, value); i++) {
                if (value.getType() == 2 && (cLDRFieldAndWidthNumber = DateTimePatternGenerator.getCLDRFieldAndWidthNumber(key)) != -1) {
                    int i2 = cLDRFieldAndWidthNumber / DisplayWidth.COUNT;
                    DisplayWidth displayWidth = DateTimePatternGenerator.CLDR_FIELD_WIDTH[cLDRFieldAndWidthNumber % DisplayWidth.COUNT];
                    UResource.Table table2 = value.getTable();
                    int i3 = 0;
                    while (true) {
                        if (!table2.getKeyAndValue(i3, key, value)) {
                            break;
                        }
                        if (!key.contentEquals("dn")) {
                            i3++;
                        } else if (DateTimePatternGenerator.this.getFieldDisplayName(i2, displayWidth) == null) {
                            DateTimePatternGenerator.this.setFieldDisplayName(i2, displayWidth, value.toString());
                        }
                    }
                }
            }
        }
    }

    private void fillInMissing() {
        for (int i = 0; i < 16; i++) {
            if (getAppendItemFormat(i) == null) {
                setAppendItemFormat(i, "{0} ├{2}: {1}┤");
            }
            if (getFieldDisplayName(i, DisplayWidth.WIDE) == null) {
                setFieldDisplayName(i, DisplayWidth.WIDE, "F" + i);
            }
            if (getFieldDisplayName(i, DisplayWidth.ABBREVIATED) == null) {
                setFieldDisplayName(i, DisplayWidth.ABBREVIATED, getFieldDisplayName(i, DisplayWidth.WIDE));
            }
            if (getFieldDisplayName(i, DisplayWidth.NARROW) == null) {
                setFieldDisplayName(i, DisplayWidth.NARROW, getFieldDisplayName(i, DisplayWidth.ABBREVIATED));
            }
        }
    }

    private class AvailableFormatsSink extends UResource.Sink {
        PatternInfo returnInfo;

        public AvailableFormatsSink(PatternInfo patternInfo) {
            this.returnInfo = patternInfo;
        }

        @Override // com.ibm.icu.impl.UResource.Sink
        public void put(UResource.Key key, UResource.Value value, boolean z) {
            UResource.Table table = value.getTable();
            for (int i = 0; table.getKeyAndValue(i, key, value); i++) {
                String string = key.toString();
                if (!DateTimePatternGenerator.this.isAvailableFormatSet(string)) {
                    DateTimePatternGenerator.this.setAvailableFormat(string);
                    DateTimePatternGenerator.this.addPatternWithSkeleton(value.toString(), string, !z, this.returnInfo);
                }
            }
        }
    }

    private void addCLDRData(PatternInfo patternInfo, ULocale uLocale) {
        ICUResourceBundle iCUResourceBundle = (ICUResourceBundle) UResourceBundle.getBundleInstance(ICUData.ICU_BASE_NAME, uLocale);
        String calendarTypeToUse = getCalendarTypeToUse(uLocale);
        try {
            iCUResourceBundle.getAllItemsWithFallback("calendar/" + calendarTypeToUse + "/appendItems", new AppendItemFormatsSink());
        } catch (MissingResourceException unused) {
        }
        try {
            iCUResourceBundle.getAllItemsWithFallback(GraphRequest.FIELDS_PARAM, new AppendItemNamesSink());
        } catch (MissingResourceException unused2) {
        }
        try {
            iCUResourceBundle.getAllItemsWithFallback("calendar/" + calendarTypeToUse + "/availableFormats", new AvailableFormatsSink(patternInfo));
        } catch (MissingResourceException unused3) {
        }
    }

    private void setDateTimeFromCalendar(ULocale uLocale) {
        setDateTimeFormat(Calendar.getDateTimePattern(Calendar.getInstance(uLocale), uLocale, 2));
    }

    private void setDecimalSymbols(ULocale uLocale) {
        setDecimal(String.valueOf(new DecimalFormatSymbols(uLocale).getDecimalSeparator()));
    }

    static {
        HashMap map = new HashMap();
        ((ICUResourceBundle) ICUResourceBundle.getBundleInstance(ICUData.ICU_BASE_NAME, "supplementalData", ICUResourceBundle.ICU_DATA_CLASS_LOADER)).getAllItemsWithFallback("timeData", new DayPeriodAllowedHoursSink(map));
        LOCALE_TO_ALLOWED_HOUR = Collections.unmodifiableMap(map);
        DisplayWidth displayWidth = DisplayWidth.WIDE;
        APPENDITEM_WIDTH = displayWidth;
        APPENDITEM_WIDTH_INT = displayWidth.ordinal();
        CLDR_FIELD_WIDTH = DisplayWidth.values();
        DTPNG_CACHE = new SimpleCache();
        CLDR_FIELD_APPEND = new String[]{"Era", "Year", "Quarter", "Month", "Week", "*", "Day-Of-Week", "Day", "*", "*", "*", "Hour", "Minute", "Second", "*", "Timezone"};
        CLDR_FIELD_NAME = new String[]{"era", "year", "quarter", "month", "week", "weekOfMonth", "weekday", "day", "dayOfYear", "weekdayOfMonth", "dayperiod", "hour", "minute", "second", "*", "zone"};
        FIELD_NAME = new String[]{"Era", "Year", "Quarter", "Month", "Week_in_Year", "Week_in_Month", "Weekday", "Day", "Day_Of_Year", "Day_of_Week_in_Month", "Dayperiod", "Hour", "Minute", "Second", "Fractional_Second", "Zone"};
        CANONICAL_ITEMS = new String[]{"G", DateFormat.YEAR, "Q", DateFormat.NUM_MONTH, "w", "W", DateFormat.ABBR_WEEKDAY, DateFormat.DAY, "D", "F", "a", DateFormat.HOUR24, DateFormat.MINUTE, DateFormat.SECOND, "S", DateFormat.ABBR_GENERIC_TZ};
        CANONICAL_SET = new HashSet(Arrays.asList(CANONICAL_ITEMS));
        types = new int[][]{new int[]{71, 0, SHORT, 1, 3}, new int[]{71, 0, LONG, 4}, new int[]{71, 0, NARROW, 5}, new int[]{121, 1, 256, 1, 20}, new int[]{89, 1, 272, 1, 20}, new int[]{117, 1, 288, 1, 20}, new int[]{114, 1, 304, 1, 20}, new int[]{85, 1, SHORT, 1, 3}, new int[]{85, 1, LONG, 4}, new int[]{85, 1, NARROW, 5}, new int[]{81, 2, 256, 1, 2}, new int[]{81, 2, SHORT, 3}, new int[]{81, 2, LONG, 4}, new int[]{81, 2, NARROW, 5}, new int[]{113, 2, 272, 1, 2}, new int[]{113, 2, -275, 3}, new int[]{113, 2, -276, 4}, new int[]{113, 2, -273, 5}, new int[]{77, 3, 256, 1, 2}, new int[]{77, 3, SHORT, 3}, new int[]{77, 3, LONG, 4}, new int[]{77, 3, NARROW, 5}, new int[]{76, 3, 272, 1, 2}, new int[]{76, 3, -275, 3}, new int[]{76, 3, -276, 4}, new int[]{76, 3, -273, 5}, new int[]{108, 3, 272, 1, 1}, new int[]{119, 4, 256, 1, 2}, new int[]{87, 5, 256, 1}, new int[]{69, 6, SHORT, 1, 3}, new int[]{69, 6, LONG, 4}, new int[]{69, 6, NARROW, 5}, new int[]{69, 6, SHORTER, 6}, new int[]{99, 6, 288, 1, 2}, new int[]{99, 6, -291, 3}, new int[]{99, 6, -292, 4}, new int[]{99, 6, -289, 5}, new int[]{99, 6, -290, 6}, new int[]{101, 6, 272, 1, 2}, new int[]{101, 6, -275, 3}, new int[]{101, 6, -276, 4}, new int[]{101, 6, -273, 5}, new int[]{101, 6, -274, 6}, new int[]{100, 7, 256, 1, 2}, new int[]{103, 7, 272, 1, 20}, new int[]{68, 8, 256, 1, 3}, new int[]{70, 9, 256, 1}, new int[]{97, 10, SHORT, 1, 3}, new int[]{97, 10, LONG, 4}, new int[]{97, 10, NARROW, 5}, new int[]{98, 10, -275, 1, 3}, new int[]{98, 10, -276, 4}, new int[]{98, 10, -273, 5}, new int[]{66, 10, -307, 1, 3}, new int[]{66, 10, -308, 4}, new int[]{66, 10, -305, 5}, new int[]{72, 11, 416, 1, 2}, new int[]{107, 11, R2.attr.expandedHintEnabled, 1, 2}, new int[]{104, 11, 256, 1, 2}, new int[]{75, 11, 272, 1, 2}, new int[]{109, 12, 256, 1, 2}, new int[]{115, 13, 256, 1, 2}, new int[]{65, 13, 272, 1, 1000}, new int[]{83, 14, 256, 1, 1000}, new int[]{118, 15, -291, 1}, new int[]{118, 15, -292, 4}, new int[]{122, 15, SHORT, 1, 3}, new int[]{122, 15, LONG, 4}, new int[]{90, 15, -273, 1, 3}, new int[]{90, 15, -276, 4}, new int[]{90, 15, -275, 5}, new int[]{79, 15, -275, 1}, new int[]{79, 15, -276, 4}, new int[]{86, 15, -275, 1}, new int[]{86, 15, -276, 2}, new int[]{86, 15, -277, 3}, new int[]{86, 15, -278, 4}, new int[]{88, 15, -273, 1}, new int[]{88, 15, -275, 2}, new int[]{88, 15, -276, 4}, new int[]{120, 15, -273, 1}, new int[]{120, 15, -275, 2}, new int[]{120, 15, -276, 4}};
    }

    private String[] getAllowedHourFormatsLangCountry(String str, String str2) {
        String[] strArr = LOCALE_TO_ALLOWED_HOUR.get(str + BaseLocale.SEP + str2);
        return strArr == null ? LOCALE_TO_ALLOWED_HOUR.get(str2) : strArr;
    }

    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
    private void getAllowedHourFormats(ULocale uLocale) {
        String language = uLocale.getLanguage();
        String country = uLocale.getCountry();
        if (language.isEmpty() || country.isEmpty()) {
            ULocale uLocaleAddLikelySubtags = ULocale.addLikelySubtags(uLocale);
            String language2 = uLocaleAddLikelySubtags.getLanguage();
            country = uLocaleAddLikelySubtags.getCountry();
            language = language2;
        }
        if (language.isEmpty()) {
            language = "und";
        }
        if (country.isEmpty()) {
            country = UnitsData.Constants.DEFAULT_REGION;
        }
        String[] allowedHourFormatsLangCountry = getAllowedHourFormatsLangCountry(language, country);
        Character ch = null;
        String keywordValue = uLocale.getKeywordValue(PlaceFields.HOURS);
        if (keywordValue != null) {
            char c = 65535;
            switch (keywordValue.hashCode()) {
                case 101512:
                    if (keywordValue.equals("h11")) {
                        c = 3;
                        break;
                    }
                    break;
                case 101513:
                    if (keywordValue.equals("h12")) {
                        c = 2;
                        break;
                    }
                    break;
                case 101545:
                    if (keywordValue.equals("h23")) {
                        c = 1;
                        break;
                    }
                    break;
                case 101546:
                    if (keywordValue.equals("h24")) {
                        c = 0;
                        break;
                    }
                    break;
            }
            if (c == 0) {
                ch = 'k';
            } else if (c == 1) {
                ch = 'H';
            } else if (c == 2) {
                ch = 'h';
            } else if (c == 3) {
                ch = 'K';
            }
        }
        if (allowedHourFormatsLangCountry == null) {
            try {
                allowedHourFormatsLangCountry = getAllowedHourFormatsLangCountry(language, Region.getInstance(country).toString());
            } catch (IllegalArgumentException unused) {
            }
        }
        if (allowedHourFormatsLangCountry != null) {
            this.defaultHourFormatChar = ch != null ? ch.charValue() : allowedHourFormatsLangCountry[0].charAt(0);
            this.allowedHourFormats = (String[]) Arrays.copyOfRange(allowedHourFormatsLangCountry, 1, allowedHourFormatsLangCountry.length - 1);
        } else {
            String[] strArr = LAST_RESORT_ALLOWED_HOUR_FORMAT;
            this.allowedHourFormats = strArr;
            this.defaultHourFormatChar = ch != null ? ch.charValue() : strArr[0].charAt(0);
        }
    }

    private static class DayPeriodAllowedHoursSink extends UResource.Sink {
        HashMap<String, String[]> tempMap;

        private DayPeriodAllowedHoursSink(HashMap<String, String[]> map) {
            this.tempMap = map;
        }

        @Override // com.ibm.icu.impl.UResource.Sink
        public void put(UResource.Key key, UResource.Value value, boolean z) {
            String[] strArr;
            UResource.Table table = value.getTable();
            for (int i = 0; table.getKeyAndValue(i, key, value); i++) {
                String string = key.toString();
                UResource.Table table2 = value.getTable();
                String[] stringArrayOrStringAsArray = null;
                String string2 = null;
                for (int i2 = 0; table2.getKeyAndValue(i2, key, value); i2++) {
                    if (key.contentEquals("allowed")) {
                        stringArrayOrStringAsArray = value.getStringArrayOrStringAsArray();
                    } else if (key.contentEquals("preferred")) {
                        string2 = value.getString();
                    }
                }
                if (stringArrayOrStringAsArray != null && stringArrayOrStringAsArray.length > 0) {
                    strArr = new String[stringArrayOrStringAsArray.length + 1];
                    if (string2 == null) {
                        string2 = stringArrayOrStringAsArray[0];
                    }
                    strArr[0] = string2;
                    System.arraycopy(stringArrayOrStringAsArray, 0, strArr, 1, stringArrayOrStringAsArray.length);
                } else {
                    strArr = new String[2];
                    if (string2 == null) {
                        string2 = DateTimePatternGenerator.LAST_RESORT_ALLOWED_HOUR_FORMAT[0];
                    }
                    strArr[0] = string2;
                    strArr[1] = strArr[0];
                }
                this.tempMap.put(string, strArr);
            }
        }
    }

    @Deprecated
    public char getDefaultHourFormatChar() {
        return this.defaultHourFormatChar;
    }

    @Deprecated
    public void setDefaultHourFormatChar(char c) {
        this.defaultHourFormatChar = c;
    }

    private void hackTimes(PatternInfo patternInfo, String str) {
        this.fp.set(str);
        StringBuilder sb = new StringBuilder();
        int i = 0;
        boolean z = false;
        while (true) {
            if (i >= this.fp.items.size()) {
                break;
            }
            Object obj = this.fp.items.get(i);
            if (!(obj instanceof String)) {
                char cCharAt = obj.toString().charAt(0);
                if (cCharAt != 'm') {
                    if (cCharAt != 's') {
                        if (z || cCharAt == 'z' || cCharAt == 'Z' || cCharAt == 'v' || cCharAt == 'V') {
                            break;
                        }
                    } else if (z) {
                        sb.append(obj);
                        addPattern(sb.toString(), false, patternInfo);
                    }
                } else {
                    sb.append(obj);
                    z = true;
                }
            } else if (z) {
                sb.append(this.fp.quoteLiteral(obj.toString()));
            }
            i++;
        }
        BitSet bitSet = new BitSet();
        BitSet bitSet2 = new BitSet();
        for (int i2 = 0; i2 < this.fp.items.size(); i2++) {
            Object obj2 = this.fp.items.get(i2);
            if (obj2 instanceof VariableField) {
                bitSet.set(i2);
                char cCharAt2 = obj2.toString().charAt(0);
                if (cCharAt2 == 's' || cCharAt2 == 'S') {
                    bitSet2.set(i2);
                    for (int i3 = i2 - 1; i3 >= 0 && !bitSet.get(i3); i3++) {
                        bitSet2.set(i2);
                    }
                }
            }
        }
        addPattern(getFilteredPattern(this.fp, bitSet2), false, patternInfo);
    }

    private static String getFilteredPattern(FormatParser formatParser, BitSet bitSet) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < formatParser.items.size(); i++) {
            if (!bitSet.get(i)) {
                Object obj = formatParser.items.get(i);
                if (obj instanceof String) {
                    sb.append(formatParser.quoteLiteral(obj.toString()));
                } else {
                    sb.append(obj.toString());
                }
            }
        }
        return sb.toString();
    }

    @Deprecated
    public static int getAppendFormatNumber(UResource.Key key) {
        int i = 0;
        while (true) {
            String[] strArr = CLDR_FIELD_APPEND;
            if (i >= strArr.length) {
                return -1;
            }
            if (key.contentEquals(strArr[i])) {
                return i;
            }
            i++;
        }
    }

    @Deprecated
    public static int getAppendFormatNumber(String str) {
        int i = 0;
        while (true) {
            String[] strArr = CLDR_FIELD_APPEND;
            if (i >= strArr.length) {
                return -1;
            }
            if (strArr[i].equals(str)) {
                return i;
            }
            i++;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static int getCLDRFieldAndWidthNumber(UResource.Key key) {
        for (int i = 0; i < CLDR_FIELD_NAME.length; i++) {
            for (int i2 = 0; i2 < DisplayWidth.COUNT; i2++) {
                if (key.contentEquals(CLDR_FIELD_NAME[i].concat(CLDR_FIELD_WIDTH[i2].cldrKey()))) {
                    return (i * DisplayWidth.COUNT) + i2;
                }
            }
        }
        return -1;
    }

    public String getBestPattern(String str) {
        return getBestPattern(str, null, 0);
    }

    public String getBestPattern(String str, int i) {
        return getBestPattern(str, null, i);
    }

    private String getBestPattern(String str, DateTimeMatcher dateTimeMatcher, int i) {
        EnumSet<DTPGflags> enumSetNoneOf = EnumSet.noneOf(DTPGflags.class);
        String strMapSkeletonMetacharacters = mapSkeletonMetacharacters(str, enumSetNoneOf);
        synchronized (this) {
            this.current.set(strMapSkeletonMetacharacters, this.fp, false);
            PatternWithMatcher bestRaw = getBestRaw(this.current, -1, this._distanceInfo, dateTimeMatcher);
            if (this._distanceInfo.missingFieldMask == 0 && this._distanceInfo.extraFieldMask == 0) {
                return adjustFieldTypes(bestRaw, this.current, enumSetNoneOf, i);
            }
            int fieldMask = this.current.getFieldMask();
            String bestAppending = getBestAppending(this.current, fieldMask & 1023, this._distanceInfo, dateTimeMatcher, enumSetNoneOf, i);
            String bestAppending2 = getBestAppending(this.current, fieldMask & 64512, this._distanceInfo, dateTimeMatcher, enumSetNoneOf, i);
            return bestAppending == null ? bestAppending2 == null ? "" : bestAppending2 : bestAppending2 == null ? bestAppending : SimpleFormatterImpl.formatRawPattern(getDateTimeFormat(), 2, 2, bestAppending2, bestAppending);
        }
    }

    private String mapSkeletonMetacharacters(String str, EnumSet<DTPGflags> enumSet) {
        char c;
        StringBuilder sb = new StringBuilder();
        int i = 0;
        boolean z = false;
        while (i < str.length()) {
            char cCharAt = str.charAt(i);
            if (cCharAt == '\'') {
                z = !z;
            } else if (!z) {
                if (cCharAt == 'j' || cCharAt == 'C') {
                    int i2 = 0;
                    while (true) {
                        int i3 = i + 1;
                        if (i3 >= str.length() || str.charAt(i3) != cCharAt) {
                            break;
                        }
                        i2++;
                        i = i3;
                    }
                    int i4 = (i2 & 1) + 1;
                    int i5 = i2 < 2 ? 1 : (i2 >> 1) + 3;
                    if (cCharAt == 'j') {
                        c = this.defaultHourFormatChar;
                    } else {
                        String str2 = this.allowedHourFormats[0];
                        char cCharAt2 = str2.charAt(0);
                        char cCharAt3 = str2.charAt(str2.length() - 1);
                        c = (cCharAt3 == 'b' || cCharAt3 == 'B') ? cCharAt3 : 'a';
                        c = cCharAt2;
                    }
                    if (c == 'H' || c == 'k') {
                        i5 = 0;
                    }
                    while (true) {
                        int i6 = i5 - 1;
                        if (i5 <= 0) {
                            break;
                        }
                        sb.append(c);
                        i5 = i6;
                    }
                    while (true) {
                        int i7 = i4 - 1;
                        if (i4 > 0) {
                            sb.append(c);
                            i4 = i7;
                        }
                    }
                } else if (cCharAt == 'J') {
                    sb.append('H');
                    enumSet.add(DTPGflags.SKELETON_USES_CAP_J);
                } else {
                    sb.append(cCharAt);
                }
            }
            i++;
        }
        return sb.toString();
    }

    public DateTimePatternGenerator addPattern(String str, boolean z, PatternInfo patternInfo) {
        return addPatternWithSkeleton(str, null, z, patternInfo);
    }

    @Deprecated
    public DateTimePatternGenerator addPatternWithSkeleton(String str, String str2, boolean z, PatternInfo patternInfo) {
        DateTimeMatcher dateTimeMatcher;
        checkFrozen();
        if (str2 == null) {
            dateTimeMatcher = new DateTimeMatcher().set(str, this.fp, false);
        } else {
            dateTimeMatcher = new DateTimeMatcher().set(str2, this.fp, false);
        }
        String basePattern = dateTimeMatcher.getBasePattern();
        PatternWithSkeletonFlag patternWithSkeletonFlag = this.basePattern_pattern.get(basePattern);
        if (patternWithSkeletonFlag != null && (!patternWithSkeletonFlag.skeletonWasSpecified || (str2 != null && !z))) {
            patternInfo.status = 1;
            patternInfo.conflictingPattern = patternWithSkeletonFlag.pattern;
            if (!z) {
                return this;
            }
        }
        PatternWithSkeletonFlag patternWithSkeletonFlag2 = this.skeleton2pattern.get(dateTimeMatcher);
        if (patternWithSkeletonFlag2 != null) {
            patternInfo.status = 2;
            patternInfo.conflictingPattern = patternWithSkeletonFlag2.pattern;
            if (!z || (str2 != null && patternWithSkeletonFlag2.skeletonWasSpecified)) {
                return this;
            }
        }
        patternInfo.status = 0;
        patternInfo.conflictingPattern = "";
        PatternWithSkeletonFlag patternWithSkeletonFlag3 = new PatternWithSkeletonFlag(str, str2 != null);
        this.skeleton2pattern.put(dateTimeMatcher, patternWithSkeletonFlag3);
        this.basePattern_pattern.put(basePattern, patternWithSkeletonFlag3);
        return this;
    }

    public String getSkeleton(String str) {
        String string;
        synchronized (this) {
            this.current.set(str, this.fp, false);
            string = this.current.toString();
        }
        return string;
    }

    @Deprecated
    public String getSkeletonAllowingDuplicates(String str) {
        String string;
        synchronized (this) {
            this.current.set(str, this.fp, true);
            string = this.current.toString();
        }
        return string;
    }

    @Deprecated
    public String getCanonicalSkeletonAllowingDuplicates(String str) {
        String canonicalString;
        synchronized (this) {
            this.current.set(str, this.fp, true);
            canonicalString = this.current.toCanonicalString();
        }
        return canonicalString;
    }

    public String getBaseSkeleton(String str) {
        String basePattern;
        synchronized (this) {
            this.current.set(str, this.fp, false);
            basePattern = this.current.getBasePattern();
        }
        return basePattern;
    }

    public Map<String, String> getSkeletons(Map<String, String> map) {
        if (map == null) {
            map = new LinkedHashMap<>();
        }
        for (DateTimeMatcher dateTimeMatcher : this.skeleton2pattern.keySet()) {
            String str = this.skeleton2pattern.get(dateTimeMatcher).pattern;
            if (!CANONICAL_SET.contains(str)) {
                map.put(dateTimeMatcher.toString(), str);
            }
        }
        return map;
    }

    public Set<String> getBaseSkeletons(Set<String> set) {
        if (set == null) {
            set = new HashSet<>();
        }
        set.addAll(this.basePattern_pattern.keySet());
        return set;
    }

    public String replaceFieldTypes(String str, String str2) {
        return replaceFieldTypes(str, str2, 0);
    }

    public String replaceFieldTypes(String str, String str2, int i) {
        String strAdjustFieldTypes;
        synchronized (this) {
            strAdjustFieldTypes = adjustFieldTypes(new PatternWithMatcher(str, null), this.current.set(str2, this.fp, false), EnumSet.noneOf(DTPGflags.class), i);
        }
        return strAdjustFieldTypes;
    }

    public void setDateTimeFormat(String str) {
        checkFrozen();
        this.dateTimeFormat = str;
    }

    public String getDateTimeFormat() {
        return this.dateTimeFormat;
    }

    public void setDecimal(String str) {
        checkFrozen();
        this.decimal = str;
    }

    public String getDecimal() {
        return this.decimal;
    }

    /* JADX WARN: Removed duplicated region for block: B:8:0x0018 A[Catch: all -> 0x0046, TryCatch #0 {, blocks: (B:4:0x0003, B:5:0x0008, B:6:0x0012, B:8:0x0018, B:11:0x0031, B:13:0x0040, B:14:0x0044), top: B:19:0x0003 }] */
    @java.lang.Deprecated
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public java.util.Collection<java.lang.String> getRedundants(java.util.Collection<java.lang.String> r6) {
        /*
            r5 = this;
            monitor-enter(r5)
            if (r6 != 0) goto L8
            java.util.LinkedHashSet r6 = new java.util.LinkedHashSet     // Catch: java.lang.Throwable -> L46
            r6.<init>()     // Catch: java.lang.Throwable -> L46
        L8:
            java.util.TreeMap<com.ibm.icu.text.DateTimePatternGenerator$DateTimeMatcher, com.ibm.icu.text.DateTimePatternGenerator$PatternWithSkeletonFlag> r0 = r5.skeleton2pattern     // Catch: java.lang.Throwable -> L46
            java.util.Set r0 = r0.keySet()     // Catch: java.lang.Throwable -> L46
            java.util.Iterator r0 = r0.iterator()     // Catch: java.lang.Throwable -> L46
        L12:
            boolean r1 = r0.hasNext()     // Catch: java.lang.Throwable -> L46
            if (r1 == 0) goto L44
            java.lang.Object r1 = r0.next()     // Catch: java.lang.Throwable -> L46
            com.ibm.icu.text.DateTimePatternGenerator$DateTimeMatcher r1 = (com.ibm.icu.text.DateTimePatternGenerator.DateTimeMatcher) r1     // Catch: java.lang.Throwable -> L46
            java.util.TreeMap<com.ibm.icu.text.DateTimePatternGenerator$DateTimeMatcher, com.ibm.icu.text.DateTimePatternGenerator$PatternWithSkeletonFlag> r2 = r5.skeleton2pattern     // Catch: java.lang.Throwable -> L46
            java.lang.Object r2 = r2.get(r1)     // Catch: java.lang.Throwable -> L46
            com.ibm.icu.text.DateTimePatternGenerator$PatternWithSkeletonFlag r2 = (com.ibm.icu.text.DateTimePatternGenerator.PatternWithSkeletonFlag) r2     // Catch: java.lang.Throwable -> L46
            java.lang.String r2 = r2.pattern     // Catch: java.lang.Throwable -> L46
            java.util.Set<java.lang.String> r3 = com.ibm.icu.text.DateTimePatternGenerator.CANONICAL_SET     // Catch: java.lang.Throwable -> L46
            boolean r3 = r3.contains(r2)     // Catch: java.lang.Throwable -> L46
            if (r3 == 0) goto L31
            goto L12
        L31:
            java.lang.String r3 = r1.toString()     // Catch: java.lang.Throwable -> L46
            r4 = 0
            java.lang.String r1 = r5.getBestPattern(r3, r1, r4)     // Catch: java.lang.Throwable -> L46
            boolean r1 = r1.equals(r2)     // Catch: java.lang.Throwable -> L46
            if (r1 == 0) goto L12
            r6.add(r2)     // Catch: java.lang.Throwable -> L46
            goto L12
        L44:
            monitor-exit(r5)     // Catch: java.lang.Throwable -> L46
            return r6
        L46:
            r6 = move-exception
            monitor-exit(r5)     // Catch: java.lang.Throwable -> L46
            throw r6
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ibm.icu.text.DateTimePatternGenerator.getRedundants(java.util.Collection):java.util.Collection");
    }

    public enum DisplayWidth {
        WIDE(""),
        ABBREVIATED("-short"),
        NARROW("-narrow");


        @Deprecated
        private static int COUNT = values().length;
        private final String cldrKey;

        DisplayWidth(String str) {
            this.cldrKey = str;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public String cldrKey() {
            return this.cldrKey;
        }
    }

    public void setAppendItemFormat(int i, String str) {
        checkFrozen();
        this.appendItemFormats[i] = str;
    }

    public String getAppendItemFormat(int i) {
        return this.appendItemFormats[i];
    }

    public void setAppendItemName(int i, String str) {
        setFieldDisplayName(i, APPENDITEM_WIDTH, str);
    }

    public String getAppendItemName(int i) {
        return getFieldDisplayName(i, APPENDITEM_WIDTH);
    }

    public DateFormat.HourCycle getDefaultHourCycle() {
        char defaultHourFormatChar = getDefaultHourFormatChar();
        if (defaultHourFormatChar == 'H') {
            return DateFormat.HourCycle.HOUR_CYCLE_23;
        }
        if (defaultHourFormatChar == 'K') {
            return DateFormat.HourCycle.HOUR_CYCLE_11;
        }
        if (defaultHourFormatChar == 'h') {
            return DateFormat.HourCycle.HOUR_CYCLE_12;
        }
        if (defaultHourFormatChar == 'k') {
            return DateFormat.HourCycle.HOUR_CYCLE_24;
        }
        throw new AssertionError("should be unreachable");
    }

    /* JADX INFO: Access modifiers changed from: private */
    @Deprecated
    public void setFieldDisplayName(int i, DisplayWidth displayWidth, String str) {
        checkFrozen();
        if (i >= 16 || i < 0) {
            return;
        }
        this.fieldDisplayNames[i][displayWidth.ordinal()] = str;
    }

    public String getFieldDisplayName(int i, DisplayWidth displayWidth) {
        return (i >= 16 || i < 0) ? "" : this.fieldDisplayNames[i][displayWidth.ordinal()];
    }

    @Deprecated
    public static boolean isSingleField(String str) {
        char cCharAt = str.charAt(0);
        for (int i = 1; i < str.length(); i++) {
            if (str.charAt(i) != cCharAt) {
                return false;
            }
        }
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setAvailableFormat(String str) {
        checkFrozen();
        this.cldrAvailableFormatKeys.add(str);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean isAvailableFormatSet(String str) {
        return this.cldrAvailableFormatKeys.contains(str);
    }

    @Override // com.ibm.icu.util.Freezable
    public boolean isFrozen() {
        return this.frozen;
    }

    @Override // com.ibm.icu.util.Freezable
    public DateTimePatternGenerator freeze() {
        this.frozen = true;
        return this;
    }

    @Override // com.ibm.icu.util.Freezable
    public DateTimePatternGenerator cloneAsThawed() {
        DateTimePatternGenerator dateTimePatternGenerator = (DateTimePatternGenerator) clone();
        this.frozen = false;
        return dateTimePatternGenerator;
    }

    public Object clone() {
        try {
            DateTimePatternGenerator dateTimePatternGenerator = (DateTimePatternGenerator) super.clone();
            dateTimePatternGenerator.skeleton2pattern = (TreeMap) this.skeleton2pattern.clone();
            dateTimePatternGenerator.basePattern_pattern = (TreeMap) this.basePattern_pattern.clone();
            dateTimePatternGenerator.appendItemFormats = (String[]) this.appendItemFormats.clone();
            dateTimePatternGenerator.fieldDisplayNames = (String[][]) this.fieldDisplayNames.clone();
            dateTimePatternGenerator.current = new DateTimeMatcher();
            dateTimePatternGenerator.fp = new FormatParser();
            dateTimePatternGenerator._distanceInfo = new DistanceInfo();
            dateTimePatternGenerator.frozen = false;
            return dateTimePatternGenerator;
        } catch (CloneNotSupportedException e) {
            throw new ICUCloneNotSupportedException("Internal Error", e);
        }
    }

    @Deprecated
    public static class VariableField {
        private final int canonicalIndex;
        private final String string;

        @Deprecated
        public VariableField(String str) {
            this(str, false);
        }

        @Deprecated
        public VariableField(String str, boolean z) {
            int canonicalIndex = DateTimePatternGenerator.getCanonicalIndex(str, z);
            this.canonicalIndex = canonicalIndex;
            if (canonicalIndex < 0) {
                throw new IllegalArgumentException("Illegal datetime field:\t" + str);
            }
            this.string = str;
        }

        @Deprecated
        public int getType() {
            return DateTimePatternGenerator.types[this.canonicalIndex][1];
        }

        @Deprecated
        public static String getCanonicalCode(int i) {
            try {
                return DateTimePatternGenerator.CANONICAL_ITEMS[i];
            } catch (Exception unused) {
                return String.valueOf(i);
            }
        }

        @Deprecated
        public boolean isNumeric() {
            return DateTimePatternGenerator.types[this.canonicalIndex][2] > 0;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public int getCanonicalIndex() {
            return this.canonicalIndex;
        }

        @Deprecated
        public String toString() {
            return this.string;
        }
    }

    @Deprecated
    public static class FormatParser {
        private static final UnicodeSet SYNTAX_CHARS = new UnicodeSet("[a-zA-Z]").freeze();
        private static final UnicodeSet QUOTING_CHARS = new UnicodeSet("[[[:script=Latn:][:script=Cyrl:]]&[[:L:][:M:]]]").freeze();
        private transient PatternTokenizer tokenizer = new PatternTokenizer().setSyntaxCharacters(SYNTAX_CHARS).setExtraQuotingCharacters(QUOTING_CHARS).setUsingQuote(true);
        private List<Object> items = new ArrayList();

        @Deprecated
        public FormatParser() {
        }

        @Deprecated
        public final FormatParser set(String str) {
            return set(str, false);
        }

        @Deprecated
        public FormatParser set(String str, boolean z) {
            this.items.clear();
            if (str.length() == 0) {
                return this;
            }
            this.tokenizer.setPattern(str);
            StringBuffer stringBuffer = new StringBuffer();
            StringBuffer stringBuffer2 = new StringBuffer();
            while (true) {
                stringBuffer.setLength(0);
                int next = this.tokenizer.next(stringBuffer);
                if (next == 0) {
                    addVariable(stringBuffer2, false);
                    return this;
                }
                if (next == 1) {
                    if (stringBuffer2.length() != 0 && stringBuffer.charAt(0) != stringBuffer2.charAt(0)) {
                        addVariable(stringBuffer2, false);
                    }
                    stringBuffer2.append(stringBuffer);
                } else {
                    addVariable(stringBuffer2, false);
                    this.items.add(stringBuffer.toString());
                }
            }
        }

        private void addVariable(StringBuffer stringBuffer, boolean z) {
            if (stringBuffer.length() != 0) {
                this.items.add(new VariableField(stringBuffer.toString(), z));
                stringBuffer.setLength(0);
            }
        }

        @Deprecated
        public List<Object> getItems() {
            return this.items;
        }

        @Deprecated
        public String toString() {
            return toString(0, this.items.size());
        }

        @Deprecated
        public String toString(int i, int i2) {
            StringBuilder sb = new StringBuilder();
            while (i < i2) {
                Object obj = this.items.get(i);
                if (obj instanceof String) {
                    sb.append(this.tokenizer.quoteLiteral((String) obj));
                } else {
                    sb.append(this.items.get(i).toString());
                }
                i++;
            }
            return sb.toString();
        }

        @Deprecated
        public boolean hasDateAndTimeFields() {
            int type = 0;
            for (Object obj : this.items) {
                if (obj instanceof VariableField) {
                    type |= 1 << ((VariableField) obj).getType();
                }
            }
            return ((type & 1023) != 0) && ((type & 64512) != 0);
        }

        @Deprecated
        public Object quoteLiteral(String str) {
            return this.tokenizer.quoteLiteral(str);
        }
    }

    @Deprecated
    public boolean skeletonsAreSimilar(String str, String str2) {
        if (str.equals(str2)) {
            return true;
        }
        TreeSet<String> set = getSet(str);
        TreeSet<String> set2 = getSet(str2);
        if (set.size() != set2.size()) {
            return false;
        }
        Iterator<String> it2 = set2.iterator();
        Iterator<String> it3 = set.iterator();
        while (it3.hasNext()) {
            int canonicalIndex = getCanonicalIndex(it3.next(), false);
            int canonicalIndex2 = getCanonicalIndex(it2.next(), false);
            int[][] iArr = types;
            if (iArr[canonicalIndex][1] != iArr[canonicalIndex2][1]) {
                return false;
            }
        }
        return true;
    }

    private TreeSet<String> getSet(String str) {
        List<Object> items = this.fp.set(str).getItems();
        TreeSet<String> treeSet = new TreeSet<>();
        Iterator<Object> it2 = items.iterator();
        while (it2.hasNext()) {
            String string = it2.next().toString();
            if (!string.startsWith("G") && !string.startsWith("a")) {
                treeSet.add(string);
            }
        }
        return treeSet;
    }

    private static class PatternWithMatcher {
        public DateTimeMatcher matcherWithSkeleton;
        public String pattern;

        public PatternWithMatcher(String str, DateTimeMatcher dateTimeMatcher) {
            this.pattern = str;
            this.matcherWithSkeleton = dateTimeMatcher;
        }
    }

    private static class PatternWithSkeletonFlag {
        public String pattern;
        public boolean skeletonWasSpecified;

        public PatternWithSkeletonFlag(String str, boolean z) {
            this.pattern = str;
            this.skeletonWasSpecified = z;
        }

        public String toString() {
            return this.pattern + ConstantsBase.PROPERTIES_PARAMS_SEPARATOR + this.skeletonWasSpecified;
        }
    }

    private void checkFrozen() {
        if (isFrozen()) {
            throw new UnsupportedOperationException("Attempt to modify frozen object");
        }
    }

    private String getBestAppending(DateTimeMatcher dateTimeMatcher, int i, DistanceInfo distanceInfo, DateTimeMatcher dateTimeMatcher2, EnumSet<DTPGflags> enumSet, int i2) {
        if (i == 0) {
            return null;
        }
        PatternWithMatcher bestRaw = getBestRaw(dateTimeMatcher, i, distanceInfo, dateTimeMatcher2);
        String strAdjustFieldTypes = adjustFieldTypes(bestRaw, dateTimeMatcher, enumSet, i2);
        while (distanceInfo.missingFieldMask != 0) {
            if ((distanceInfo.missingFieldMask & SECOND_AND_FRACTIONAL_MASK) == 16384 && (i & SECOND_AND_FRACTIONAL_MASK) == SECOND_AND_FRACTIONAL_MASK) {
                bestRaw.pattern = strAdjustFieldTypes;
                enumSet = EnumSet.copyOf((EnumSet) enumSet);
                enumSet.add(DTPGflags.FIX_FRACTIONAL_SECONDS);
                strAdjustFieldTypes = adjustFieldTypes(bestRaw, dateTimeMatcher, enumSet, i2);
                distanceInfo.missingFieldMask &= -16385;
            } else {
                int i3 = distanceInfo.missingFieldMask;
                String strAdjustFieldTypes2 = adjustFieldTypes(getBestRaw(dateTimeMatcher, distanceInfo.missingFieldMask, distanceInfo, dateTimeMatcher2), dateTimeMatcher, enumSet, i2);
                int topBitNumber = getTopBitNumber(i3 & (~distanceInfo.missingFieldMask));
                strAdjustFieldTypes = SimpleFormatterImpl.formatRawPattern(getAppendFormat(topBitNumber), 2, 3, strAdjustFieldTypes, strAdjustFieldTypes2, getAppendName(topBitNumber));
            }
        }
        return strAdjustFieldTypes;
    }

    private String getAppendName(int i) {
        return "'" + this.fieldDisplayNames[i][APPENDITEM_WIDTH_INT] + "'";
    }

    private String getAppendFormat(int i) {
        return this.appendItemFormats[i];
    }

    private void addCanonicalItems() {
        PatternInfo patternInfo = new PatternInfo();
        int i = 0;
        while (true) {
            String[] strArr = CANONICAL_ITEMS;
            if (i >= strArr.length) {
                return;
            }
            addPattern(String.valueOf(strArr[i]), false, patternInfo);
            i++;
        }
    }

    private PatternWithMatcher getBestRaw(DateTimeMatcher dateTimeMatcher, int i, DistanceInfo distanceInfo, DateTimeMatcher dateTimeMatcher2) {
        int distance;
        PatternWithMatcher patternWithMatcher = new PatternWithMatcher("", null);
        DistanceInfo distanceInfo2 = new DistanceInfo();
        int i2 = Integer.MAX_VALUE;
        int i3 = Integer.MIN_VALUE;
        for (DateTimeMatcher dateTimeMatcher3 : this.skeleton2pattern.keySet()) {
            if (!dateTimeMatcher3.equals(dateTimeMatcher2) && ((distance = dateTimeMatcher.getDistance(dateTimeMatcher3, i, distanceInfo2)) < i2 || (distance == i2 && i3 < distanceInfo2.missingFieldMask))) {
                int i4 = distanceInfo2.missingFieldMask;
                PatternWithSkeletonFlag patternWithSkeletonFlag = this.skeleton2pattern.get(dateTimeMatcher3);
                patternWithMatcher.pattern = patternWithSkeletonFlag.pattern;
                if (patternWithSkeletonFlag.skeletonWasSpecified) {
                    patternWithMatcher.matcherWithSkeleton = dateTimeMatcher3;
                } else {
                    patternWithMatcher.matcherWithSkeleton = null;
                }
                distanceInfo.setTo(distanceInfo2);
                if (distance == 0) {
                    break;
                }
                i3 = i4;
                i2 = distance;
            }
        }
        return patternWithMatcher;
    }

    private String adjustFieldTypes(PatternWithMatcher patternWithMatcher, DateTimeMatcher dateTimeMatcher, EnumSet<DTPGflags> enumSet, int i) {
        char c;
        this.fp.set(patternWithMatcher.pattern);
        StringBuilder sb = new StringBuilder();
        for (Object obj : this.fp.getItems()) {
            if (obj instanceof String) {
                sb.append(this.fp.quoteLiteral((String) obj));
            } else {
                VariableField variableField = (VariableField) obj;
                StringBuilder sb2 = new StringBuilder(variableField.toString());
                int type = variableField.getType();
                if (enumSet.contains(DTPGflags.FIX_FRACTIONAL_SECONDS) && type == 13) {
                    sb2.append(this.decimal);
                    dateTimeMatcher.original.appendFieldTo(14, sb2);
                } else if (dateTimeMatcher.type[type] != 0) {
                    char fieldChar = dateTimeMatcher.original.getFieldChar(type);
                    int fieldLength = dateTimeMatcher.original.getFieldLength(type);
                    if (fieldChar == 'E' && fieldLength < 3) {
                        fieldLength = 3;
                    }
                    DateTimeMatcher dateTimeMatcher2 = patternWithMatcher.matcherWithSkeleton;
                    if ((type == 11 && (i & 2048) == 0) || ((type == 12 && (i & 4096) == 0) || (type == 13 && (i & 8192) == 0))) {
                        fieldLength = sb2.length();
                    } else if (dateTimeMatcher2 != null) {
                        int fieldLength2 = dateTimeMatcher2.original.getFieldLength(type);
                        boolean zIsNumeric = variableField.isNumeric();
                        boolean zFieldIsNumeric = dateTimeMatcher2.fieldIsNumeric(type);
                        if (fieldLength2 == fieldLength || ((zIsNumeric && !zFieldIsNumeric) || (zFieldIsNumeric && !zIsNumeric))) {
                            fieldLength = sb2.length();
                        }
                    }
                    char cCharAt = (type == 11 || type == 3 || type == 6 || (type == 1 && fieldChar != 'Y')) ? sb2.charAt(0) : fieldChar;
                    if (type == 11) {
                        if (enumSet.contains(DTPGflags.SKELETON_USES_CAP_J) || fieldChar == (c = this.defaultHourFormatChar)) {
                            cCharAt = this.defaultHourFormatChar;
                        } else if (fieldChar == 'h' && c == 'K') {
                            cCharAt = 'K';
                        } else if (fieldChar == 'H' && this.defaultHourFormatChar == 'k') {
                            cCharAt = 'k';
                        } else if (fieldChar == 'k' && this.defaultHourFormatChar == 'H') {
                            cCharAt = 'H';
                        } else if (fieldChar == 'K' && this.defaultHourFormatChar == 'h') {
                            cCharAt = 'h';
                        }
                    }
                    sb2 = new StringBuilder();
                    while (fieldLength > 0) {
                        sb2.append(cCharAt);
                        fieldLength--;
                    }
                }
                sb.append((CharSequence) sb2);
            }
        }
        return sb.toString();
    }

    @Deprecated
    public String getFields(String str) {
        this.fp.set(str);
        StringBuilder sb = new StringBuilder();
        for (Object obj : this.fp.getItems()) {
            if (obj instanceof String) {
                sb.append(this.fp.quoteLiteral((String) obj));
            } else {
                sb.append("{" + getName(obj.toString()) + "}");
            }
        }
        return sb.toString();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static String showMask(int i) {
        StringBuilder sb = new StringBuilder();
        for (int i2 = 0; i2 < 16; i2++) {
            if (((1 << i2) & i) != 0) {
                if (sb.length() != 0) {
                    sb.append(" | ");
                }
                sb.append(FIELD_NAME[i2]);
                sb.append(" ");
            }
        }
        return sb.toString();
    }

    private static String getName(String str) {
        int canonicalIndex = getCanonicalIndex(str, true);
        String[] strArr = FIELD_NAME;
        int[][] iArr = types;
        String str2 = strArr[iArr[canonicalIndex][1]];
        if (iArr[canonicalIndex][2] < 0) {
            return str2 + ":S";
        }
        return str2 + ":N";
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static int getCanonicalIndex(String str, boolean z) {
        int length = str.length();
        if (length == 0) {
            return -1;
        }
        char cCharAt = str.charAt(0);
        for (int i = 1; i < length; i++) {
            if (str.charAt(i) != cCharAt) {
                return -1;
            }
        }
        int i2 = 0;
        int i3 = -1;
        while (true) {
            int[][] iArr = types;
            if (i2 >= iArr.length) {
                if (z) {
                    return -1;
                }
                return i3;
            }
            int[] iArr2 = iArr[i2];
            if (iArr2[0] == cCharAt) {
                if (iArr2[3] <= length && iArr2[iArr2.length - 1] >= length) {
                    return i2;
                }
                i3 = i2;
            }
            i2++;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static char getCanonicalChar(int i, char c) {
        if (c == 'h' || c == 'K') {
            return 'h';
        }
        int i2 = 0;
        while (true) {
            int[][] iArr = types;
            if (i2 < iArr.length) {
                int[] iArr2 = iArr[i2];
                if (iArr2[1] == i) {
                    return (char) iArr2[0];
                }
                i2++;
            } else {
                throw new IllegalArgumentException("Could not find field " + i);
            }
        }
    }

    private static class SkeletonFields {
        static final /* synthetic */ boolean $assertionsDisabled = false;
        private static final byte DEFAULT_CHAR = 0;
        private static final byte DEFAULT_LENGTH = 0;
        private byte[] chars;
        private byte[] lengths;

        private SkeletonFields() {
            this.chars = new byte[16];
            this.lengths = new byte[16];
        }

        public void clear() {
            Arrays.fill(this.chars, (byte) 0);
            Arrays.fill(this.lengths, (byte) 0);
        }

        void copyFieldFrom(SkeletonFields skeletonFields, int i) {
            this.chars[i] = skeletonFields.chars[i];
            this.lengths[i] = skeletonFields.lengths[i];
        }

        void clearField(int i) {
            this.chars[i] = 0;
            this.lengths[i] = 0;
        }

        char getFieldChar(int i) {
            return (char) this.chars[i];
        }

        int getFieldLength(int i) {
            return this.lengths[i];
        }

        void populate(int i, String str) {
            for (char c : str.toCharArray()) {
            }
            populate(i, str.charAt(0), str.length());
        }

        void populate(int i, char c, int i2) {
            this.chars[i] = (byte) c;
            this.lengths[i] = (byte) i2;
        }

        public boolean isFieldEmpty(int i) {
            return this.lengths[i] == 0;
        }

        public String toString() {
            return appendTo(new StringBuilder(), false, false).toString();
        }

        public String toString(boolean z) {
            return appendTo(new StringBuilder(), false, z).toString();
        }

        public String toCanonicalString() {
            return appendTo(new StringBuilder(), true, false).toString();
        }

        public String toCanonicalString(boolean z) {
            return appendTo(new StringBuilder(), true, z).toString();
        }

        public StringBuilder appendTo(StringBuilder sb) {
            return appendTo(sb, false, false);
        }

        private StringBuilder appendTo(StringBuilder sb, boolean z, boolean z2) {
            for (int i = 0; i < 16; i++) {
                if (!z2 || i != 10) {
                    appendFieldTo(i, sb, z);
                }
            }
            return sb;
        }

        public StringBuilder appendFieldTo(int i, StringBuilder sb) {
            return appendFieldTo(i, sb, false);
        }

        private StringBuilder appendFieldTo(int i, StringBuilder sb, boolean z) {
            char canonicalChar = (char) this.chars[i];
            byte b = this.lengths[i];
            if (z) {
                canonicalChar = DateTimePatternGenerator.getCanonicalChar(i, canonicalChar);
            }
            for (int i2 = 0; i2 < b; i2++) {
                sb.append(canonicalChar);
            }
            return sb;
        }

        public int compareTo(SkeletonFields skeletonFields) {
            for (int i = 0; i < 16; i++) {
                int i2 = this.chars[i] - skeletonFields.chars[i];
                if (i2 != 0) {
                    return i2;
                }
                int i3 = this.lengths[i] - skeletonFields.lengths[i];
                if (i3 != 0) {
                    return i3;
                }
            }
            return 0;
        }

        public boolean equals(Object obj) {
            return this == obj || (obj != null && (obj instanceof SkeletonFields) && compareTo((SkeletonFields) obj) == 0);
        }

        public int hashCode() {
            return Arrays.hashCode(this.chars) ^ Arrays.hashCode(this.lengths);
        }
    }

    private static class DateTimeMatcher implements Comparable<DateTimeMatcher> {
        private boolean addedDefaultDayPeriod;
        private SkeletonFields baseOriginal;
        private SkeletonFields original;
        private int[] type;

        private DateTimeMatcher() {
            this.type = new int[16];
            this.original = new SkeletonFields();
            this.baseOriginal = new SkeletonFields();
            this.addedDefaultDayPeriod = false;
        }

        public boolean fieldIsNumeric(int i) {
            return this.type[i] > 0;
        }

        public String toString() {
            return this.original.toString(this.addedDefaultDayPeriod);
        }

        public String toCanonicalString() {
            return this.original.toCanonicalString(this.addedDefaultDayPeriod);
        }

        String getBasePattern() {
            return this.baseOriginal.toString(this.addedDefaultDayPeriod);
        }

        DateTimeMatcher set(String str, FormatParser formatParser, boolean z) {
            String string;
            char fieldChar;
            Arrays.fill(this.type, 0);
            this.original.clear();
            this.baseOriginal.clear();
            this.addedDefaultDayPeriod = false;
            formatParser.set(str);
            Iterator<Object> it2 = formatParser.getItems().iterator();
            while (true) {
                if (it2.hasNext()) {
                    Object next = it2.next();
                    if (next instanceof VariableField) {
                        VariableField variableField = (VariableField) next;
                        string = variableField.toString();
                        int[] iArr = DateTimePatternGenerator.types[variableField.getCanonicalIndex()];
                        int i = iArr[1];
                        if (!this.original.isFieldEmpty(i)) {
                            fieldChar = this.original.getFieldChar(i);
                            char cCharAt = string.charAt(0);
                            if (!z && (fieldChar != 'r' || cCharAt != 'U')) {
                                if (fieldChar != 'U' || cCharAt != 'r') {
                                    break;
                                }
                            }
                        } else {
                            this.original.populate(i, string);
                            char c = (char) iArr[0];
                            this.baseOriginal.populate(i, c, "GEzvQ".indexOf(c) < 0 ? iArr[3] : 1);
                            int length = iArr[2];
                            if (length > 0) {
                                length += string.length();
                            }
                            this.type[i] = length;
                        }
                    }
                } else {
                    if (!this.original.isFieldEmpty(12) && !this.original.isFieldEmpty(14) && this.original.isFieldEmpty(13)) {
                        int i2 = 0;
                        while (true) {
                            if (i2 >= DateTimePatternGenerator.types.length) {
                                break;
                            }
                            int[] iArr2 = DateTimePatternGenerator.types[i2];
                            if (iArr2[1] == 13) {
                                this.original.populate(13, (char) iArr2[0], iArr2[3]);
                                this.baseOriginal.populate(13, (char) iArr2[0], iArr2[3]);
                                int i3 = iArr2[2];
                                int[] iArr3 = this.type;
                                if (i3 > 0) {
                                    i3++;
                                }
                                iArr3[13] = i3;
                            } else {
                                i2++;
                            }
                        }
                    }
                    if (!this.original.isFieldEmpty(11)) {
                        if (this.original.getFieldChar(11) == 'h' || this.original.getFieldChar(11) == 'K') {
                            if (this.original.isFieldEmpty(10)) {
                                int i4 = 0;
                                while (true) {
                                    if (i4 >= DateTimePatternGenerator.types.length) {
                                        break;
                                    }
                                    int[] iArr4 = DateTimePatternGenerator.types[i4];
                                    if (iArr4[1] == 10) {
                                        this.original.populate(10, (char) iArr4[0], iArr4[3]);
                                        this.baseOriginal.populate(10, (char) iArr4[0], iArr4[3]);
                                        this.type[10] = iArr4[2];
                                        this.addedDefaultDayPeriod = true;
                                        break;
                                    }
                                    i4++;
                                }
                            }
                        } else if (!this.original.isFieldEmpty(10)) {
                            this.original.clearField(10);
                            this.baseOriginal.clearField(10);
                            this.type[10] = 0;
                        }
                    }
                    return this;
                }
            }
            throw new IllegalArgumentException("Conflicting fields:\t" + fieldChar + ", " + string + "\t in " + str);
        }

        int getFieldMask() {
            int i = 0;
            int i2 = 0;
            while (true) {
                int[] iArr = this.type;
                if (i >= iArr.length) {
                    return i2;
                }
                if (iArr[i] != 0) {
                    i2 |= 1 << i;
                }
                i++;
            }
        }

        void extractFrom(DateTimeMatcher dateTimeMatcher, int i) {
            int i2 = 0;
            while (true) {
                int[] iArr = this.type;
                if (i2 >= iArr.length) {
                    return;
                }
                if (((1 << i2) & i) != 0) {
                    iArr[i2] = dateTimeMatcher.type[i2];
                    this.original.copyFieldFrom(dateTimeMatcher.original, i2);
                } else {
                    iArr[i2] = 0;
                    this.original.clearField(i2);
                }
                i2++;
            }
        }

        int getDistance(DateTimeMatcher dateTimeMatcher, int i, DistanceInfo distanceInfo) {
            distanceInfo.clear();
            int iAbs = 0;
            for (int i2 = 0; i2 < 16; i2++) {
                int i3 = ((1 << i2) & i) == 0 ? 0 : this.type[i2];
                int i4 = dateTimeMatcher.type[i2];
                if (i3 != i4) {
                    if (i3 == 0) {
                        iAbs += 65536;
                        distanceInfo.addExtra(i2);
                    } else if (i4 == 0) {
                        iAbs += 4096;
                        distanceInfo.addMissing(i2);
                    } else {
                        iAbs += Math.abs(i3 - i4);
                    }
                }
            }
            return iAbs;
        }

        @Override // java.lang.Comparable
        public int compareTo(DateTimeMatcher dateTimeMatcher) {
            int iCompareTo = this.original.compareTo(dateTimeMatcher.original);
            if (iCompareTo > 0) {
                return -1;
            }
            return iCompareTo < 0 ? 1 : 0;
        }

        public boolean equals(Object obj) {
            return this == obj || (obj != null && (obj instanceof DateTimeMatcher) && this.original.equals(((DateTimeMatcher) obj).original));
        }

        public int hashCode() {
            return this.original.hashCode();
        }
    }

    private static class DistanceInfo {
        int extraFieldMask;
        int missingFieldMask;

        private DistanceInfo() {
        }

        void clear() {
            this.extraFieldMask = 0;
            this.missingFieldMask = 0;
        }

        void setTo(DistanceInfo distanceInfo) {
            this.missingFieldMask = distanceInfo.missingFieldMask;
            this.extraFieldMask = distanceInfo.extraFieldMask;
        }

        void addMissing(int i) {
            this.missingFieldMask = (1 << i) | this.missingFieldMask;
        }

        void addExtra(int i) {
            this.extraFieldMask = (1 << i) | this.extraFieldMask;
        }

        public String toString() {
            return "missingFieldMask: " + DateTimePatternGenerator.showMask(this.missingFieldMask) + ", extraFieldMask: " + DateTimePatternGenerator.showMask(this.extraFieldMask);
        }
    }
}
