package com.ibm.icu.text;

import com.ibm.icu.impl.FormattedValueFieldPositionIteratorImpl;
import com.ibm.icu.impl.ICUCache;
import com.ibm.icu.impl.ICUData;
import com.ibm.icu.impl.ICUResourceBundle;
import com.ibm.icu.impl.SimpleCache;
import com.ibm.icu.impl.SimpleFormatterImpl;
import com.ibm.icu.impl.Utility;
import com.ibm.icu.text.DateIntervalInfo;
import com.ibm.icu.text.DisplayContext;
import com.ibm.icu.text.UFormat;
import com.ibm.icu.util.Calendar;
import com.ibm.icu.util.DateInterval;
import com.ibm.icu.util.Output;
import com.ibm.icu.util.TimeZone;
import com.ibm.icu.util.ULocale;
import com.ibm.icu.util.UResourceBundle;
import java.io.IOException;
import java.io.InvalidObjectException;
import java.io.ObjectInputStream;
import java.text.AttributedCharacterIterator;
import java.text.FieldPosition;
import java.text.ParsePosition;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import org.slf4j.Marker;

/* loaded from: classes2.dex */
public class DateIntervalFormat extends UFormat {
    private static ICUCache<String, Map<String, DateIntervalInfo.PatternInfo>> LOCAL_PATTERN_CACHE = new SimpleCache();
    private static final long serialVersionUID = 1;
    private DisplayContext fCapitalizationSetting;
    private SimpleDateFormat fDateFormat;
    private String fDatePattern;
    private String fDateTimeFormat;
    private Calendar fFromCalendar;
    private DateIntervalInfo fInfo;
    private transient Map<String, DateIntervalInfo.PatternInfo> fIntervalPatterns;
    private String fSkeleton;
    private String fTimePattern;
    private Calendar fToCalendar;
    private boolean isDateIntervalInfoDefault;

    public static final class FormattedDateInterval implements FormattedValue {
        private final List<FieldPosition> attributes;
        private final String string;

        FormattedDateInterval(CharSequence charSequence, List<FieldPosition> list) {
            this.string = charSequence.toString();
            this.attributes = Collections.unmodifiableList(list);
        }

        @Override // com.ibm.icu.text.FormattedValue, java.lang.CharSequence
        public String toString() {
            return this.string;
        }

        @Override // java.lang.CharSequence
        public int length() {
            return this.string.length();
        }

        @Override // java.lang.CharSequence
        public char charAt(int i) {
            return this.string.charAt(i);
        }

        @Override // java.lang.CharSequence
        public CharSequence subSequence(int i, int i2) {
            return this.string.subSequence(i, i2);
        }

        @Override // com.ibm.icu.text.FormattedValue
        public <A extends Appendable> A appendTo(A a) {
            return (A) Utility.appendTo(this.string, a);
        }

        @Override // com.ibm.icu.text.FormattedValue
        public boolean nextPosition(ConstrainedFieldPosition constrainedFieldPosition) {
            return FormattedValueFieldPositionIteratorImpl.nextPosition(this.attributes, constrainedFieldPosition);
        }

        @Override // com.ibm.icu.text.FormattedValue
        public AttributedCharacterIterator toCharacterIterator() {
            return FormattedValueFieldPositionIteratorImpl.toCharacterIterator(this.string, this.attributes);
        }
    }

    public static final class SpanField extends UFormat.SpanField {
        public static final SpanField DATE_INTERVAL_SPAN = new SpanField("date-interval-span");
        private static final long serialVersionUID = -6330879259553618133L;

        private SpanField(String str) {
            super(str);
        }

        @Override // java.text.AttributedCharacterIterator.Attribute
        @Deprecated
        protected Object readResolve() throws InvalidObjectException {
            if (getName().equals(DATE_INTERVAL_SPAN.getName())) {
                return DATE_INTERVAL_SPAN;
            }
            throw new InvalidObjectException("An invalid object.");
        }
    }

    static final class BestMatchInfo {
        final int bestMatchDistanceInfo;
        final String bestMatchSkeleton;

        BestMatchInfo(String str, int i) {
            this.bestMatchSkeleton = str;
            this.bestMatchDistanceInfo = i;
        }
    }

    private static final class SkeletonAndItsBestMatch {
        final String bestMatchSkeleton;
        final String skeleton;

        SkeletonAndItsBestMatch(String str, String str2) {
            this.skeleton = str;
            this.bestMatchSkeleton = str2;
        }
    }

    private static final class FormatOutput {
        int firstIndex;

        private FormatOutput() {
            this.firstIndex = -1;
        }

        public void register(int i) {
            if (this.firstIndex == -1) {
                this.firstIndex = i;
            }
        }
    }

    private DateIntervalFormat() {
        this.fSkeleton = null;
        this.fIntervalPatterns = null;
        this.fDatePattern = null;
        this.fTimePattern = null;
        this.fDateTimeFormat = null;
        this.fCapitalizationSetting = DisplayContext.CAPITALIZATION_NONE;
    }

    @Deprecated
    public DateIntervalFormat(String str, DateIntervalInfo dateIntervalInfo, SimpleDateFormat simpleDateFormat) {
        this.fSkeleton = null;
        this.fIntervalPatterns = null;
        this.fDatePattern = null;
        this.fTimePattern = null;
        this.fDateTimeFormat = null;
        this.fCapitalizationSetting = DisplayContext.CAPITALIZATION_NONE;
        this.fDateFormat = simpleDateFormat;
        dateIntervalInfo.freeze();
        this.fSkeleton = str;
        this.fInfo = dateIntervalInfo;
        this.isDateIntervalInfoDefault = false;
        this.fFromCalendar = (Calendar) this.fDateFormat.getCalendar().clone();
        this.fToCalendar = (Calendar) this.fDateFormat.getCalendar().clone();
        initializePattern(null);
    }

    private DateIntervalFormat(String str, ULocale uLocale, SimpleDateFormat simpleDateFormat) {
        this.fSkeleton = null;
        this.fIntervalPatterns = null;
        this.fDatePattern = null;
        this.fTimePattern = null;
        this.fDateTimeFormat = null;
        this.fCapitalizationSetting = DisplayContext.CAPITALIZATION_NONE;
        this.fDateFormat = simpleDateFormat;
        this.fSkeleton = str;
        this.fInfo = new DateIntervalInfo(uLocale).freeze();
        this.isDateIntervalInfoDefault = true;
        this.fFromCalendar = (Calendar) this.fDateFormat.getCalendar().clone();
        this.fToCalendar = (Calendar) this.fDateFormat.getCalendar().clone();
        initializePattern(LOCAL_PATTERN_CACHE);
    }

    public static final DateIntervalFormat getInstance(String str) {
        return getInstance(str, ULocale.getDefault(ULocale.Category.FORMAT));
    }

    public static final DateIntervalFormat getInstance(String str, Locale locale) {
        return getInstance(str, ULocale.forLocale(locale));
    }

    public static final DateIntervalFormat getInstance(String str, ULocale uLocale) {
        return new DateIntervalFormat(str, uLocale, new SimpleDateFormat(DateTimePatternGenerator.getInstance(uLocale).getBestPattern(str), uLocale));
    }

    public static final DateIntervalFormat getInstance(String str, DateIntervalInfo dateIntervalInfo) {
        return getInstance(str, ULocale.getDefault(ULocale.Category.FORMAT), dateIntervalInfo);
    }

    public static final DateIntervalFormat getInstance(String str, Locale locale, DateIntervalInfo dateIntervalInfo) {
        return getInstance(str, ULocale.forLocale(locale), dateIntervalInfo);
    }

    public static final DateIntervalFormat getInstance(String str, ULocale uLocale, DateIntervalInfo dateIntervalInfo) {
        return new DateIntervalFormat(str, (DateIntervalInfo) dateIntervalInfo.clone(), new SimpleDateFormat(DateTimePatternGenerator.getInstance(uLocale).getBestPattern(str), uLocale));
    }

    @Override // java.text.Format
    public synchronized Object clone() {
        DateIntervalFormat dateIntervalFormat;
        dateIntervalFormat = (DateIntervalFormat) super.clone();
        dateIntervalFormat.fDateFormat = (SimpleDateFormat) this.fDateFormat.clone();
        dateIntervalFormat.fInfo = (DateIntervalInfo) this.fInfo.clone();
        dateIntervalFormat.fFromCalendar = (Calendar) this.fFromCalendar.clone();
        dateIntervalFormat.fToCalendar = (Calendar) this.fToCalendar.clone();
        dateIntervalFormat.fDatePattern = this.fDatePattern;
        dateIntervalFormat.fTimePattern = this.fTimePattern;
        dateIntervalFormat.fDateTimeFormat = this.fDateTimeFormat;
        dateIntervalFormat.fCapitalizationSetting = this.fCapitalizationSetting;
        return dateIntervalFormat;
    }

    @Override // java.text.Format
    public final StringBuffer format(Object obj, StringBuffer stringBuffer, FieldPosition fieldPosition) {
        if (obj instanceof DateInterval) {
            return format((DateInterval) obj, stringBuffer, fieldPosition);
        }
        throw new IllegalArgumentException("Cannot format given Object (" + obj.getClass().getName() + ") as a DateInterval");
    }

    public final StringBuffer format(DateInterval dateInterval, StringBuffer stringBuffer, FieldPosition fieldPosition) {
        return formatIntervalImpl(dateInterval, stringBuffer, fieldPosition, null, null);
    }

    public FormattedDateInterval formatToValue(DateInterval dateInterval) {
        StringBuffer stringBuffer = new StringBuffer();
        FieldPosition fieldPosition = new FieldPosition(0);
        FormatOutput formatOutput = new FormatOutput();
        ArrayList arrayList = new ArrayList();
        formatIntervalImpl(dateInterval, stringBuffer, fieldPosition, formatOutput, arrayList);
        if (formatOutput.firstIndex != -1) {
            FormattedValueFieldPositionIteratorImpl.addOverlapSpans(arrayList, SpanField.DATE_INTERVAL_SPAN, formatOutput.firstIndex);
            FormattedValueFieldPositionIteratorImpl.sort(arrayList);
        }
        return new FormattedDateInterval(stringBuffer, arrayList);
    }

    private synchronized StringBuffer formatIntervalImpl(DateInterval dateInterval, StringBuffer stringBuffer, FieldPosition fieldPosition, FormatOutput formatOutput, List<FieldPosition> list) {
        this.fFromCalendar.setTimeInMillis(dateInterval.getFromDate());
        this.fToCalendar.setTimeInMillis(dateInterval.getToDate());
        return formatImpl(this.fFromCalendar, this.fToCalendar, stringBuffer, fieldPosition, formatOutput, list);
    }

    /* JADX WARN: Type inference failed for: r13v3, types: [T, java.lang.String] */
    @Deprecated
    public String getPatterns(Calendar calendar, Calendar calendar2, Output<String> output) {
        char c = 0;
        if (calendar.get(0) == calendar2.get(0)) {
            if (calendar.get(1) != calendar2.get(1)) {
                c = 1;
            } else if (calendar.get(2) != calendar2.get(2)) {
                c = 2;
            } else if (calendar.get(5) != calendar2.get(5)) {
                c = 5;
            } else if (calendar.get(9) != calendar2.get(9)) {
                c = '\t';
            } else if (calendar.get(10) != calendar2.get(10)) {
                c = '\n';
            } else if (calendar.get(12) != calendar2.get(12)) {
                c = '\f';
            } else if (calendar.get(13) != calendar2.get(13)) {
                c = '\r';
            } else {
                if (calendar.get(14) == calendar2.get(14)) {
                    return null;
                }
                c = 14;
            }
        }
        DateIntervalInfo.PatternInfo patternInfo = this.fIntervalPatterns.get(DateIntervalInfo.CALENDAR_FIELD_TO_PATTERN_LETTER[c]);
        output.value = patternInfo.getSecondPart();
        return patternInfo.getFirstPart();
    }

    public final StringBuffer format(Calendar calendar, Calendar calendar2, StringBuffer stringBuffer, FieldPosition fieldPosition) {
        return formatImpl(calendar, calendar2, stringBuffer, fieldPosition, null, null);
    }

    public FormattedDateInterval formatToValue(Calendar calendar, Calendar calendar2) {
        StringBuffer stringBuffer = new StringBuffer();
        FieldPosition fieldPosition = new FieldPosition(0);
        FormatOutput formatOutput = new FormatOutput();
        ArrayList arrayList = new ArrayList();
        formatImpl(calendar, calendar2, stringBuffer, fieldPosition, formatOutput, arrayList);
        if (formatOutput.firstIndex != -1) {
            FormattedValueFieldPositionIteratorImpl.addOverlapSpans(arrayList, SpanField.DATE_INTERVAL_SPAN, formatOutput.firstIndex);
            FormattedValueFieldPositionIteratorImpl.sort(arrayList);
        }
        return new FormattedDateInterval(stringBuffer, arrayList);
    }

    private synchronized StringBuffer formatImpl(Calendar calendar, Calendar calendar2, StringBuffer stringBuffer, FieldPosition fieldPosition, FormatOutput formatOutput, List<FieldPosition> list) {
        int i;
        Calendar calendar3 = calendar;
        Calendar calendar4 = calendar2;
        synchronized (this) {
            if (!calendar.isEquivalentTo(calendar2)) {
                throw new IllegalArgumentException("can not format on two different calendars");
            }
            this.fDateFormat.setContext(this.fCapitalizationSetting);
            int i2 = 5;
            if (calendar3.get(0) != calendar4.get(0)) {
                i = 14;
                i2 = 0;
            } else if (calendar3.get(1) != calendar4.get(1)) {
                i = 14;
                i2 = 1;
            } else if (calendar3.get(2) != calendar4.get(2)) {
                i = 14;
                i2 = 2;
            } else if (calendar3.get(5) != calendar4.get(5)) {
                i = 14;
            } else if (calendar3.get(9) != calendar4.get(9)) {
                i = 14;
                i2 = 9;
            } else if (calendar3.get(10) != calendar4.get(10)) {
                i = 14;
                i2 = 10;
            } else if (calendar3.get(12) != calendar4.get(12)) {
                i = 14;
                i2 = 12;
            } else if (calendar3.get(13) != calendar4.get(13)) {
                i = 14;
                i2 = 13;
            } else {
                i = 14;
                if (calendar3.get(14) == calendar4.get(14)) {
                    return this.fDateFormat.format(calendar3, stringBuffer, fieldPosition, list);
                }
                i2 = 14;
            }
            boolean z = i2 == 9 || i2 == 10 || i2 == 12 || i2 == 13 || i2 == i;
            DateIntervalInfo.PatternInfo patternInfo = this.fIntervalPatterns.get(DateIntervalInfo.CALENDAR_FIELD_TO_PATTERN_LETTER[i2]);
            if (patternInfo == null) {
                if (this.fDateFormat.isFieldUnitIgnored(i2)) {
                    return this.fDateFormat.format(calendar3, stringBuffer, fieldPosition, list);
                }
                return fallbackFormat(calendar, calendar2, z, stringBuffer, fieldPosition, formatOutput, list);
            }
            if (patternInfo.getFirstPart() == null) {
                return fallbackFormat(calendar, calendar2, z, stringBuffer, fieldPosition, formatOutput, list, patternInfo.getSecondPart());
            }
            if (patternInfo.firstDateInPtnIsLaterDate()) {
                if (formatOutput != null) {
                    formatOutput.register(1);
                }
                calendar4 = calendar3;
                calendar3 = calendar4;
            } else if (formatOutput != null) {
                formatOutput.register(0);
            }
            String pattern = this.fDateFormat.toPattern();
            this.fDateFormat.applyPattern(patternInfo.getFirstPart());
            this.fDateFormat.format(calendar3, stringBuffer, fieldPosition, list);
            FieldPosition fieldPosition2 = fieldPosition.getEndIndex() > 0 ? new FieldPosition(0) : fieldPosition;
            if (patternInfo.getSecondPart() != null) {
                this.fDateFormat.applyPattern(patternInfo.getSecondPart());
                this.fDateFormat.setContext(DisplayContext.CAPITALIZATION_NONE);
                this.fDateFormat.format(calendar4, stringBuffer, fieldPosition2, list);
            }
            this.fDateFormat.applyPattern(pattern);
            return stringBuffer;
        }
    }

    private final void fallbackFormatRange(Calendar calendar, Calendar calendar2, StringBuffer stringBuffer, StringBuilder sb, FieldPosition fieldPosition, FormatOutput formatOutput, List<FieldPosition> list) throws IOException {
        String strCompileToStringMinMaxArguments = SimpleFormatterImpl.compileToStringMinMaxArguments(this.fInfo.getFallbackIntervalPattern(), sb, 2, 2);
        long jStep = 0;
        while (true) {
            jStep = SimpleFormatterImpl.IterInternal.step(jStep, strCompileToStringMinMaxArguments, stringBuffer);
            if (jStep == -1) {
                return;
            }
            if (SimpleFormatterImpl.IterInternal.getArgIndex(jStep) == 0) {
                if (formatOutput != null) {
                    formatOutput.register(0);
                }
                this.fDateFormat.format(calendar, stringBuffer, fieldPosition, list);
            } else {
                if (formatOutput != null) {
                    formatOutput.register(1);
                }
                this.fDateFormat.format(calendar2, stringBuffer, fieldPosition, list);
            }
            if (fieldPosition.getEndIndex() > 0) {
                fieldPosition = new FieldPosition(0);
            }
            this.fDateFormat.setContext(DisplayContext.CAPITALIZATION_NONE);
        }
    }

    private final StringBuffer fallbackFormat(Calendar calendar, Calendar calendar2, boolean z, StringBuffer stringBuffer, FieldPosition fieldPosition, FormatOutput formatOutput, List<FieldPosition> list) throws IOException {
        StringBuilder sb = new StringBuilder();
        if ((!z || this.fDatePattern == null || this.fTimePattern == null) ? false : true) {
            String strCompileToStringMinMaxArguments = SimpleFormatterImpl.compileToStringMinMaxArguments(this.fDateTimeFormat, sb, 2, 2);
            String pattern = this.fDateFormat.toPattern();
            long j = 0;
            FieldPosition fieldPosition2 = fieldPosition;
            while (true) {
                long jStep = SimpleFormatterImpl.IterInternal.step(j, strCompileToStringMinMaxArguments, stringBuffer);
                if (jStep == -1) {
                    break;
                }
                if (SimpleFormatterImpl.IterInternal.getArgIndex(jStep) == 0) {
                    this.fDateFormat.applyPattern(this.fTimePattern);
                    fallbackFormatRange(calendar, calendar2, stringBuffer, sb, fieldPosition2, formatOutput, list);
                } else {
                    this.fDateFormat.applyPattern(this.fDatePattern);
                    this.fDateFormat.format(calendar, stringBuffer, fieldPosition2, list);
                }
                if (fieldPosition2.getEndIndex() > 0) {
                    fieldPosition2 = new FieldPosition(0);
                }
                this.fDateFormat.setContext(DisplayContext.CAPITALIZATION_NONE);
                j = jStep;
            }
            this.fDateFormat.applyPattern(pattern);
        } else {
            fallbackFormatRange(calendar, calendar2, stringBuffer, sb, fieldPosition, formatOutput, list);
        }
        return stringBuffer;
    }

    private final StringBuffer fallbackFormat(Calendar calendar, Calendar calendar2, boolean z, StringBuffer stringBuffer, FieldPosition fieldPosition, FormatOutput formatOutput, List<FieldPosition> list, String str) throws IOException {
        String pattern = this.fDateFormat.toPattern();
        this.fDateFormat.applyPattern(str);
        fallbackFormat(calendar, calendar2, z, stringBuffer, fieldPosition, formatOutput, list);
        this.fDateFormat.applyPattern(pattern);
        return stringBuffer;
    }

    @Override // java.text.Format
    @Deprecated
    public Object parseObject(String str, ParsePosition parsePosition) {
        throw new UnsupportedOperationException("parsing is not supported");
    }

    public DateIntervalInfo getDateIntervalInfo() {
        return (DateIntervalInfo) this.fInfo.clone();
    }

    public void setDateIntervalInfo(DateIntervalInfo dateIntervalInfo) {
        DateIntervalInfo dateIntervalInfo2 = (DateIntervalInfo) dateIntervalInfo.clone();
        this.fInfo = dateIntervalInfo2;
        this.isDateIntervalInfoDefault = false;
        dateIntervalInfo2.freeze();
        if (this.fDateFormat != null) {
            initializePattern(null);
        }
    }

    public TimeZone getTimeZone() {
        SimpleDateFormat simpleDateFormat = this.fDateFormat;
        if (simpleDateFormat != null) {
            return (TimeZone) simpleDateFormat.getTimeZone().clone();
        }
        return TimeZone.getDefault();
    }

    public void setTimeZone(TimeZone timeZone) {
        TimeZone timeZone2 = (TimeZone) timeZone.clone();
        SimpleDateFormat simpleDateFormat = this.fDateFormat;
        if (simpleDateFormat != null) {
            simpleDateFormat.setTimeZone(timeZone2);
        }
        Calendar calendar = this.fFromCalendar;
        if (calendar != null) {
            calendar.setTimeZone(timeZone2);
        }
        Calendar calendar2 = this.fToCalendar;
        if (calendar2 != null) {
            calendar2.setTimeZone(timeZone2);
        }
    }

    public void setContext(DisplayContext displayContext) {
        if (displayContext.type() == DisplayContext.Type.CAPITALIZATION) {
            this.fCapitalizationSetting = displayContext;
        }
    }

    public DisplayContext getContext(DisplayContext.Type type) {
        DisplayContext displayContext;
        return (type != DisplayContext.Type.CAPITALIZATION || (displayContext = this.fCapitalizationSetting) == null) ? DisplayContext.CAPITALIZATION_NONE : displayContext;
    }

    public synchronized DateFormat getDateFormat() {
        return (DateFormat) this.fDateFormat.clone();
    }

    private void initializePattern(ICUCache<String, Map<String, DateIntervalInfo.PatternInfo>> iCUCache) {
        String str;
        String str2;
        String pattern = this.fDateFormat.toPattern();
        ULocale locale = this.fDateFormat.getLocale();
        Map<String, DateIntervalInfo.PatternInfo> mapUnmodifiableMap = null;
        if (iCUCache != null) {
            if (this.fSkeleton != null) {
                str2 = locale.toString() + Marker.ANY_NON_NULL_MARKER + pattern + Marker.ANY_NON_NULL_MARKER + this.fSkeleton;
            } else {
                str2 = locale.toString() + Marker.ANY_NON_NULL_MARKER + pattern;
            }
            str = str2;
            mapUnmodifiableMap = iCUCache.get(str2);
        } else {
            str = null;
        }
        if (mapUnmodifiableMap == null) {
            mapUnmodifiableMap = Collections.unmodifiableMap(initializeIntervalPattern(pattern, locale));
            if (iCUCache != null) {
                iCUCache.put(str, mapUnmodifiableMap);
            }
        }
        this.fIntervalPatterns = mapUnmodifiableMap;
    }

    private Map<String, DateIntervalInfo.PatternInfo> initializeIntervalPattern(String str, ULocale uLocale) {
        DateTimePatternGenerator dateTimePatternGenerator = DateTimePatternGenerator.getInstance(uLocale);
        if (this.fSkeleton == null) {
            this.fSkeleton = dateTimePatternGenerator.getSkeleton(str);
        }
        String strNormalizeHourMetacharacters = normalizeHourMetacharacters(this.fSkeleton, uLocale);
        HashMap map = new HashMap();
        StringBuilder sb = new StringBuilder(strNormalizeHourMetacharacters.length());
        StringBuilder sb2 = new StringBuilder(strNormalizeHourMetacharacters.length());
        StringBuilder sb3 = new StringBuilder(strNormalizeHourMetacharacters.length());
        StringBuilder sb4 = new StringBuilder(strNormalizeHourMetacharacters.length());
        getDateTimeSkeleton(strNormalizeHourMetacharacters, sb, sb2, sb3, sb4);
        String string = sb.toString();
        String string2 = sb3.toString();
        String string3 = sb2.toString();
        String string4 = sb4.toString();
        if (sb3.length() != 0 && sb.length() != 0) {
            this.fDateTimeFormat = getConcatenationPattern(uLocale);
        }
        if (!genSeparateDateTimePtn(string3, string4, map, dateTimePatternGenerator)) {
            if (sb3.length() != 0 && sb.length() == 0) {
                String str2 = DateFormat.YEAR_NUM_MONTH_DAY + string2;
                DateIntervalInfo.PatternInfo patternInfo = new DateIntervalInfo.PatternInfo(null, dateTimePatternGenerator.getBestPattern(str2), this.fInfo.getDefaultOrder());
                map.put(DateIntervalInfo.CALENDAR_FIELD_TO_PATTERN_LETTER[5], patternInfo);
                map.put(DateIntervalInfo.CALENDAR_FIELD_TO_PATTERN_LETTER[2], patternInfo);
                map.put(DateIntervalInfo.CALENDAR_FIELD_TO_PATTERN_LETTER[1], patternInfo);
                map.put(DateIntervalInfo.CALENDAR_FIELD_TO_PATTERN_LETTER[0], new DateIntervalInfo.PatternInfo(null, dateTimePatternGenerator.getBestPattern(str2 + "G"), this.fInfo.getDefaultOrder()));
            }
            return map;
        }
        if (sb3.length() != 0) {
            if (sb.length() == 0) {
                String str3 = DateFormat.YEAR_NUM_MONTH_DAY + string2;
                DateIntervalInfo.PatternInfo patternInfo2 = new DateIntervalInfo.PatternInfo(null, dateTimePatternGenerator.getBestPattern(str3), this.fInfo.getDefaultOrder());
                map.put(DateIntervalInfo.CALENDAR_FIELD_TO_PATTERN_LETTER[5], patternInfo2);
                map.put(DateIntervalInfo.CALENDAR_FIELD_TO_PATTERN_LETTER[2], patternInfo2);
                map.put(DateIntervalInfo.CALENDAR_FIELD_TO_PATTERN_LETTER[1], patternInfo2);
                map.put(DateIntervalInfo.CALENDAR_FIELD_TO_PATTERN_LETTER[0], new DateIntervalInfo.PatternInfo(null, dateTimePatternGenerator.getBestPattern(str3 + "G"), this.fInfo.getDefaultOrder()));
            } else {
                if (!fieldExistsInSkeleton(5, string)) {
                    strNormalizeHourMetacharacters = DateIntervalInfo.CALENDAR_FIELD_TO_PATTERN_LETTER[5] + strNormalizeHourMetacharacters;
                    genFallbackPattern(5, strNormalizeHourMetacharacters, map, dateTimePatternGenerator);
                }
                if (!fieldExistsInSkeleton(2, string)) {
                    strNormalizeHourMetacharacters = DateIntervalInfo.CALENDAR_FIELD_TO_PATTERN_LETTER[2] + strNormalizeHourMetacharacters;
                    genFallbackPattern(2, strNormalizeHourMetacharacters, map, dateTimePatternGenerator);
                }
                if (!fieldExistsInSkeleton(1, string)) {
                    strNormalizeHourMetacharacters = DateIntervalInfo.CALENDAR_FIELD_TO_PATTERN_LETTER[1] + strNormalizeHourMetacharacters;
                    genFallbackPattern(1, strNormalizeHourMetacharacters, map, dateTimePatternGenerator);
                }
                if (!fieldExistsInSkeleton(0, string)) {
                    genFallbackPattern(0, DateIntervalInfo.CALENDAR_FIELD_TO_PATTERN_LETTER[0] + strNormalizeHourMetacharacters, map, dateTimePatternGenerator);
                }
                if (this.fDateTimeFormat == null) {
                    this.fDateTimeFormat = "{1} {0}";
                }
                String bestPattern = dateTimePatternGenerator.getBestPattern(string);
                concatSingleDate2TimeInterval(this.fDateTimeFormat, bestPattern, 9, map);
                concatSingleDate2TimeInterval(this.fDateTimeFormat, bestPattern, 10, map);
                concatSingleDate2TimeInterval(this.fDateTimeFormat, bestPattern, 12, map);
            }
        }
        return map;
    }

    private String getConcatenationPattern(ULocale uLocale) {
        ICUResourceBundle iCUResourceBundle = (ICUResourceBundle) ((ICUResourceBundle) UResourceBundle.getBundleInstance(ICUData.ICU_BASE_NAME, uLocale)).getWithFallback("calendar/gregorian/DateTimePatterns").get(8);
        if (iCUResourceBundle.getType() == 0) {
            return iCUResourceBundle.getString();
        }
        return iCUResourceBundle.getString(0);
    }

    private void genFallbackPattern(int i, String str, Map<String, DateIntervalInfo.PatternInfo> map, DateTimePatternGenerator dateTimePatternGenerator) {
        map.put(DateIntervalInfo.CALENDAR_FIELD_TO_PATTERN_LETTER[i], new DateIntervalInfo.PatternInfo(null, dateTimePatternGenerator.getBestPattern(str), this.fInfo.getDefaultOrder()));
    }

    private String normalizeHourMetacharacters(String str, ULocale uLocale) {
        StringBuilder sb = new StringBuilder(str);
        int i = 0;
        char c = 0;
        int i2 = 0;
        int i3 = 0;
        for (int i4 = 0; i4 < sb.length(); i4++) {
            char cCharAt = sb.charAt(i4);
            if (cCharAt != 'j' && cCharAt != 'J' && cCharAt != 'C') {
                if (c != 0) {
                    break;
                }
            } else {
                if (c == 0) {
                    i3 = i4;
                    c = cCharAt;
                }
                i2++;
            }
        }
        if (c != 0) {
            char c2 = 'a';
            String bestPattern = DateTimePatternGenerator.getInstance(uLocale).getBestPattern(String.valueOf(c));
            while (true) {
                int iIndexOf = bestPattern.indexOf(39);
                if (iIndexOf == -1) {
                    break;
                }
                int iIndexOf2 = bestPattern.indexOf(39, iIndexOf + 1);
                if (iIndexOf2 == -1) {
                    iIndexOf2 = iIndexOf;
                }
                bestPattern = bestPattern.substring(0, iIndexOf) + bestPattern.substring(iIndexOf2 + 1);
            }
            char c3 = 'h';
            if (bestPattern.indexOf(104) == -1) {
                if (bestPattern.indexOf(75) != -1) {
                    c3 = 'K';
                } else {
                    c3 = bestPattern.indexOf(107) != -1 ? 'k' : 'H';
                }
            }
            if (bestPattern.indexOf(98) != -1) {
                c2 = 'b';
            } else if (bestPattern.indexOf(66) != -1) {
                c2 = 'B';
            }
            if (c3 == 'H' || c3 == 'k') {
                sb.replace(i3, i2 + i3, String.valueOf(c3));
            } else {
                StringBuilder sb2 = new StringBuilder();
                sb2.append(c3);
                if (i2 == 3 || i2 == 4) {
                    while (i < 4) {
                        sb2.append(c2);
                        i++;
                    }
                } else if (i2 == 5 || i2 == 6) {
                    while (i < 5) {
                        sb2.append(c2);
                        i++;
                    }
                } else {
                    sb2.append(c2);
                }
                sb.replace(i3, i2 + i3, sb2.toString());
            }
        }
        return sb.toString();
    }

    private static void getDateTimeSkeleton(String str, StringBuilder sb, StringBuilder sb2, StringBuilder sb3, StringBuilder sb4) {
        int i = 0;
        int i2 = 0;
        char c = 0;
        int i3 = 0;
        int i4 = 0;
        int i5 = 0;
        int i6 = 0;
        int i7 = 0;
        for (int i8 = 0; i8 < str.length(); i8++) {
            char cCharAt = str.charAt(i8);
            switch (cCharAt) {
                case 'A':
                case 'B':
                case 'S':
                case 'V':
                case 'Z':
                case 'a':
                case 'b':
                case 'j':
                case 's':
                    sb3.append(cCharAt);
                    sb4.append(cCharAt);
                    break;
                case 'D':
                case 'F':
                case 'G':
                case 'L':
                case 'Q':
                case 'U':
                case 'W':
                case 'Y':
                case 'c':
                case 'e':
                case 'g':
                case 'l':
                case 'q':
                case 'r':
                case 'u':
                case 'w':
                    sb2.append(cCharAt);
                    sb.append(cCharAt);
                    break;
                case 'E':
                    sb.append(cCharAt);
                    i2++;
                    break;
                case 'H':
                case 'K':
                case 'h':
                case 'k':
                    sb3.append(cCharAt);
                    if (c == 0) {
                        c = cCharAt;
                        break;
                    } else {
                        break;
                    }
                case 'M':
                    sb.append(cCharAt);
                    i3++;
                    break;
                case 'd':
                    sb.append(cCharAt);
                    i4++;
                    break;
                case 'm':
                    sb3.append(cCharAt);
                    i5++;
                    break;
                case 'v':
                    i6++;
                    sb3.append(cCharAt);
                    break;
                case 'y':
                    sb.append(cCharAt);
                    i++;
                    break;
                case 'z':
                    i7++;
                    sb3.append(cCharAt);
                    break;
            }
        }
        if (i != 0) {
            for (int i9 = 0; i9 < i; i9++) {
                sb2.append('y');
            }
        }
        if (i3 != 0) {
            if (i3 < 3) {
                sb2.append('M');
            } else {
                for (int i10 = 0; i10 < i3 && i10 < 5; i10++) {
                    sb2.append('M');
                }
            }
        }
        if (i2 != 0) {
            if (i2 <= 3) {
                sb2.append('E');
            } else {
                for (int i11 = 0; i11 < i2 && i11 < 5; i11++) {
                    sb2.append('E');
                }
            }
        }
        if (i4 != 0) {
            sb2.append('d');
        }
        if (c != 0) {
            sb4.append(c);
        }
        if (i5 != 0) {
            sb4.append('m');
        }
        if (i7 != 0) {
            sb4.append('z');
        }
        if (i6 != 0) {
            sb4.append('v');
        }
    }

    private boolean genSeparateDateTimePtn(String str, String str2, Map<String, DateIntervalInfo.PatternInfo> map, DateTimePatternGenerator dateTimePatternGenerator) {
        String str3 = str2.length() != 0 ? str2 : str;
        BestMatchInfo bestSkeleton = this.fInfo.getBestSkeleton(str3);
        String str4 = bestSkeleton.bestMatchSkeleton;
        int i = bestSkeleton.bestMatchDistanceInfo;
        if (str.length() != 0) {
            this.fDatePattern = dateTimePatternGenerator.getBestPattern(str);
        }
        if (str2.length() != 0) {
            this.fTimePattern = dateTimePatternGenerator.getBestPattern(str2);
        }
        if (i == -1) {
            return false;
        }
        if (str2.length() == 0) {
            String str5 = str3;
            genIntervalPattern(5, str5, str4, i, map);
            SkeletonAndItsBestMatch skeletonAndItsBestMatchGenIntervalPattern = genIntervalPattern(2, str5, str4, i, map);
            if (skeletonAndItsBestMatchGenIntervalPattern != null) {
                String str6 = skeletonAndItsBestMatchGenIntervalPattern.skeleton;
                str3 = skeletonAndItsBestMatchGenIntervalPattern.bestMatchSkeleton;
                str4 = str6;
            }
            String str7 = str3;
            String str8 = str4;
            genIntervalPattern(1, str7, str8, i, map);
            genIntervalPattern(0, str7, str8, i, map);
            return true;
        }
        String str9 = str3;
        genIntervalPattern(12, str9, str4, i, map);
        genIntervalPattern(10, str9, str4, i, map);
        genIntervalPattern(9, str9, str4, i, map);
        return true;
    }

    private SkeletonAndItsBestMatch genIntervalPattern(int i, String str, String str2, int i2, Map<String, DateIntervalInfo.PatternInfo> map) {
        boolean z;
        DateIntervalInfo.PatternInfo intervalPattern = this.fInfo.getIntervalPattern(str2, i);
        SkeletonAndItsBestMatch skeletonAndItsBestMatch = null;
        if (intervalPattern == null) {
            if (SimpleDateFormat.isFieldUnitIgnored(str2, i)) {
                map.put(DateIntervalInfo.CALENDAR_FIELD_TO_PATTERN_LETTER[i], new DateIntervalInfo.PatternInfo(this.fDateFormat.toPattern(), null, this.fInfo.getDefaultOrder()));
                return null;
            }
            if (i == 9) {
                DateIntervalInfo.PatternInfo intervalPattern2 = this.fInfo.getIntervalPattern(str2, 10);
                if (intervalPattern2 != null) {
                    z = this.fSkeleton.indexOf(74) != -1;
                    map.put(DateIntervalInfo.CALENDAR_FIELD_TO_PATTERN_LETTER[i], new DateIntervalInfo.PatternInfo(adjustFieldWidth(str, str2, intervalPattern2.getFirstPart(), i2, z), adjustFieldWidth(str, str2, intervalPattern2.getSecondPart(), i2, z), intervalPattern2.firstDateInPtnIsLaterDate()));
                }
                return null;
            }
            String str3 = DateIntervalInfo.CALENDAR_FIELD_TO_PATTERN_LETTER[i];
            str2 = str3 + str2;
            str = str3 + str;
            intervalPattern = this.fInfo.getIntervalPattern(str2, i);
            if (intervalPattern == null && i2 == 0) {
                BestMatchInfo bestSkeleton = this.fInfo.getBestSkeleton(str);
                String str4 = bestSkeleton.bestMatchSkeleton;
                i2 = bestSkeleton.bestMatchDistanceInfo;
                if (str4.length() != 0 && i2 != -1) {
                    intervalPattern = this.fInfo.getIntervalPattern(str4, i);
                    str2 = str4;
                }
            }
            if (intervalPattern != null) {
                skeletonAndItsBestMatch = new SkeletonAndItsBestMatch(str, str2);
            }
        }
        if (intervalPattern != null) {
            if (i2 != 0) {
                z = this.fSkeleton.indexOf(74) != -1;
                intervalPattern = new DateIntervalInfo.PatternInfo(adjustFieldWidth(str, str2, intervalPattern.getFirstPart(), i2, z), adjustFieldWidth(str, str2, intervalPattern.getSecondPart(), i2, z), intervalPattern.firstDateInPtnIsLaterDate());
            }
            map.put(DateIntervalInfo.CALENDAR_FIELD_TO_PATTERN_LETTER[i], intervalPattern);
        }
        return skeletonAndItsBestMatch;
    }

    private static String adjustFieldWidth(String str, String str2, String str3, int i, boolean z) {
        if (str3 == null) {
            return null;
        }
        int[] iArr = new int[58];
        int[] iArr2 = new int[58];
        DateIntervalInfo.parseSkeleton(str, iArr);
        DateIntervalInfo.parseSkeleton(str2, iArr2);
        if (z) {
            if (str3.indexOf(" a") != -1) {
                str3 = findReplaceInPattern(str3, " a", "");
            } else if (str3.indexOf("a ") != -1) {
                str3 = findReplaceInPattern(str3, "a ", "");
            }
            str3 = findReplaceInPattern(str3, "a", "");
        }
        if (i == 2) {
            if (str.indexOf(122) != -1) {
                str3 = findReplaceInPattern(str3, DateFormat.ABBR_GENERIC_TZ, DateFormat.ABBR_SPECIFIC_TZ);
            }
            if (str.indexOf(75) != -1) {
                str3 = findReplaceInPattern(str3, "h", "K");
            }
            if (str.indexOf(107) != -1) {
                str3 = findReplaceInPattern(str3, DateFormat.HOUR24, "k");
            }
            if (str.indexOf(98) != -1) {
                str3 = findReplaceInPattern(str3, "a", "b");
            }
        }
        if (str3.indexOf(97) != -1 && iArr2[32] == 0) {
            iArr2[32] = 1;
        }
        if (str3.indexOf(98) != -1 && iArr2[33] == 0) {
            iArr2[33] = 1;
        }
        StringBuilder sb = new StringBuilder(str3);
        int length = sb.length();
        int i2 = 0;
        int i3 = 0;
        char c = 0;
        boolean z2 = false;
        while (true) {
            if (i2 >= length) {
                break;
            }
            char cCharAt = sb.charAt(i2);
            if (cCharAt != c && i3 > 0) {
                int i4 = (c != 'L' ? c : 'M') - 'A';
                int i5 = iArr2[i4];
                int i6 = iArr[i4];
                if (i5 == i3 && i6 > i5) {
                    int i7 = i6 - i5;
                    for (int i8 = 0; i8 < i7; i8++) {
                        sb.insert(i2, c);
                    }
                    i2 += i7;
                    length += i7;
                }
                i3 = 0;
            }
            if (cCharAt == '\'') {
                int i9 = i2 + 1;
                if (i9 >= sb.length() || sb.charAt(i9) != '\'') {
                    z2 = !z2;
                } else {
                    i2 = i9;
                }
            } else if (!z2 && ((cCharAt >= 'a' && cCharAt <= 'z') || (cCharAt >= 'A' && cCharAt <= 'Z'))) {
                i3++;
                c = cCharAt;
            }
            i2++;
        }
        if (i3 > 0) {
            int i10 = (c != 'L' ? c : 'M') - 'A';
            int i11 = iArr2[i10];
            int i12 = iArr[i10];
            if (i11 == i3 && i12 > i11) {
                int i13 = i12 - i11;
                for (int i14 = 0; i14 < i13; i14++) {
                    sb.append(c);
                }
            }
        }
        return sb.toString();
    }

    private static String findReplaceInPattern(String str, String str2, String str3) {
        int iIndexOf = str.indexOf("'");
        if (iIndexOf < 0) {
            return str.replace(str2, str3);
        }
        StringBuilder sb = new StringBuilder();
        while (iIndexOf >= 0) {
            int iIndexOf2 = str.indexOf("'", iIndexOf + 1);
            if (iIndexOf2 < 0) {
                iIndexOf2 = str.length() - 1;
            }
            String strSubstring = str.substring(0, iIndexOf);
            int i = iIndexOf2 + 1;
            String strSubstring2 = str.substring(iIndexOf, i);
            sb.append(strSubstring.replace(str2, str3));
            sb.append(strSubstring2);
            str = str.substring(i);
            iIndexOf = str.indexOf("'");
        }
        sb.append(str.replace(str2, str3));
        return sb.toString();
    }

    private void concatSingleDate2TimeInterval(String str, String str2, int i, Map<String, DateIntervalInfo.PatternInfo> map) {
        DateIntervalInfo.PatternInfo patternInfo = map.get(DateIntervalInfo.CALENDAR_FIELD_TO_PATTERN_LETTER[i]);
        if (patternInfo != null) {
            map.put(DateIntervalInfo.CALENDAR_FIELD_TO_PATTERN_LETTER[i], DateIntervalInfo.genPatternInfo(SimpleFormatterImpl.formatRawPattern(str, 2, 2, patternInfo.getFirstPart() + patternInfo.getSecondPart(), str2), patternInfo.firstDateInPtnIsLaterDate()));
        }
    }

    private static boolean fieldExistsInSkeleton(int i, String str) {
        return str.indexOf(DateIntervalInfo.CALENDAR_FIELD_TO_PATTERN_LETTER[i]) != -1;
    }

    private void readObject(ObjectInputStream objectInputStream) throws ClassNotFoundException, IOException {
        objectInputStream.defaultReadObject();
        initializePattern(this.isDateIntervalInfoDefault ? LOCAL_PATTERN_CACHE : null);
        if (this.fCapitalizationSetting == null) {
            this.fCapitalizationSetting = DisplayContext.CAPITALIZATION_NONE;
        }
    }

    @Deprecated
    public Map<String, DateIntervalInfo.PatternInfo> getRawPatterns() {
        return this.fIntervalPatterns;
    }
}
