package com.ibm.icu.text;

import com.ibm.icu.impl.ICUData;
import com.ibm.icu.impl.ICUResourceBundle;
import com.ibm.icu.impl.PatternTokenizer;
import com.ibm.icu.impl.SoftCache;
import com.ibm.icu.impl.TZDBTimeZoneNames;
import com.ibm.icu.impl.TextTrieMap;
import com.ibm.icu.impl.TimeZoneGenericNames;
import com.ibm.icu.impl.TimeZoneNamesImpl;
import com.ibm.icu.impl.ZoneMeta;
import com.ibm.icu.impl.units.UnitsData;
import com.ibm.icu.lang.UCharacter;
import com.ibm.icu.text.DateFormat;
import com.ibm.icu.text.TimeZoneNames;
import com.ibm.icu.util.Calendar;
import com.ibm.icu.util.Freezable;
import com.ibm.icu.util.Output;
import com.ibm.icu.util.TimeZone;
import com.ibm.icu.util.ULocale;
import java.io.IOException;
import java.io.InvalidObjectException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectStreamField;
import java.io.Serializable;
import java.text.AttributedCharacterIterator;
import java.text.AttributedString;
import java.text.FieldPosition;
import java.text.ParseException;
import java.text.ParsePosition;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.Collection;
import java.util.Date;
import java.util.EnumSet;
import java.util.Iterator;
import java.util.Locale;
import java.util.MissingResourceException;
import net.fortuna.ical4j.model.property.RequestStatus;
import org.apache.commons.codec.language.Soundex;

/* loaded from: classes2.dex */
public class TimeZoneFormat extends UFormat implements Freezable<TimeZoneFormat>, Serializable {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final String ASCII_DIGITS = "0123456789";
    private static final char DEFAULT_GMT_OFFSET_SEP = ':';
    private static final String DEFAULT_GMT_PATTERN = "GMT{0}";
    private static final String DEFAULT_GMT_ZERO = "GMT";
    private static final String ISO8601_UTC = "Z";
    private static final int ISO_LOCAL_STYLE_FLAG = 256;
    private static final int ISO_Z_STYLE_FLAG = 128;
    private static final int MAX_OFFSET = 86400000;
    private static final int MAX_OFFSET_HOUR = 23;
    private static final int MAX_OFFSET_MINUTE = 59;
    private static final int MAX_OFFSET_SECOND = 59;
    private static final int MILLIS_PER_HOUR = 3600000;
    private static final int MILLIS_PER_MINUTE = 60000;
    private static final int MILLIS_PER_SECOND = 1000;
    private static volatile TextTrieMap<String> SHORT_ZONE_ID_TRIE = null;
    private static final String TZID_GMT = "Etc/GMT";
    private static final String UNKNOWN_LOCATION = "Unknown";
    private static final int UNKNOWN_OFFSET = Integer.MAX_VALUE;
    private static final String UNKNOWN_SHORT_ZONE_ID = "unk";
    private static final String UNKNOWN_ZONE_ID = "Etc/Unknown";
    private static volatile TextTrieMap<String> ZONE_ID_TRIE = null;
    private static final long serialVersionUID = 2281246852693575022L;
    private transient boolean _abuttingOffsetHoursAndMinutes;
    private volatile transient boolean _frozen;
    private String[] _gmtOffsetDigits;
    private transient Object[][] _gmtOffsetPatternItems;
    private String[] _gmtOffsetPatterns;
    private String _gmtPattern;
    private transient String _gmtPatternPrefix;
    private transient String _gmtPatternSuffix;
    private String _gmtZeroFormat;
    private volatile transient TimeZoneGenericNames _gnames;
    private ULocale _locale;
    private boolean _parseAllStyles;
    private boolean _parseTZDBNames;
    private transient String _region;
    private volatile transient TimeZoneNames _tzdbNames;
    private TimeZoneNames _tznames;
    private static final String[] ALT_GMT_STRINGS = {"GMT", "UTC", "UT"};
    private static final String[] DEFAULT_GMT_DIGITS = {"0", "1", RequestStatus.SUCCESS, RequestStatus.CLIENT_ERROR, RequestStatus.SCHEDULING_ERROR, "5", "6", "7", "8", "9"};
    private static final GMTOffsetPatternType[] PARSE_GMT_OFFSET_TYPES = {GMTOffsetPatternType.POSITIVE_HMS, GMTOffsetPatternType.NEGATIVE_HMS, GMTOffsetPatternType.POSITIVE_HM, GMTOffsetPatternType.NEGATIVE_HM, GMTOffsetPatternType.POSITIVE_H, GMTOffsetPatternType.NEGATIVE_H};
    private static TimeZoneFormatCache _tzfCache = new TimeZoneFormatCache(null);
    private static final EnumSet<TimeZoneNames.NameType> ALL_SIMPLE_NAME_TYPES = EnumSet.of(TimeZoneNames.NameType.LONG_STANDARD, TimeZoneNames.NameType.LONG_DAYLIGHT, TimeZoneNames.NameType.SHORT_STANDARD, TimeZoneNames.NameType.SHORT_DAYLIGHT, TimeZoneNames.NameType.EXEMPLAR_LOCATION);
    private static final EnumSet<TimeZoneGenericNames.GenericNameType> ALL_GENERIC_NAME_TYPES = EnumSet.of(TimeZoneGenericNames.GenericNameType.LOCATION, TimeZoneGenericNames.GenericNameType.LONG, TimeZoneGenericNames.GenericNameType.SHORT);
    private static final ObjectStreamField[] serialPersistentFields = {new ObjectStreamField("_locale", ULocale.class), new ObjectStreamField("_tznames", TimeZoneNames.class), new ObjectStreamField("_gmtPattern", String.class), new ObjectStreamField("_gmtOffsetPatterns", String[].class), new ObjectStreamField("_gmtOffsetDigits", String[].class), new ObjectStreamField("_gmtZeroFormat", String.class), new ObjectStreamField("_parseAllStyles", Boolean.TYPE)};

    private enum OffsetFields {
        H,
        HM,
        HMS
    }

    public enum ParseOption {
        ALL_STYLES,
        TZ_DATABASE_ABBREVIATIONS
    }

    public enum TimeType {
        UNKNOWN,
        STANDARD,
        DAYLIGHT
    }

    public enum Style {
        GENERIC_LOCATION(1),
        GENERIC_LONG(2),
        GENERIC_SHORT(4),
        SPECIFIC_LONG(8),
        SPECIFIC_SHORT(16),
        LOCALIZED_GMT(32),
        LOCALIZED_GMT_SHORT(64),
        ISO_BASIC_SHORT(128),
        ISO_BASIC_LOCAL_SHORT(256),
        ISO_BASIC_FIXED(128),
        ISO_BASIC_LOCAL_FIXED(256),
        ISO_BASIC_FULL(128),
        ISO_BASIC_LOCAL_FULL(256),
        ISO_EXTENDED_FIXED(128),
        ISO_EXTENDED_LOCAL_FIXED(256),
        ISO_EXTENDED_FULL(128),
        ISO_EXTENDED_LOCAL_FULL(256),
        ZONE_ID(512),
        ZONE_ID_SHORT(1024),
        EXEMPLAR_LOCATION(2048);

        final int flag;

        Style(int i) {
            this.flag = i;
        }
    }

    public enum GMTOffsetPatternType {
        POSITIVE_HM("+H:mm", DateFormat.HOUR24_MINUTE, true),
        POSITIVE_HMS("+H:mm:ss", DateFormat.HOUR24_MINUTE_SECOND, true),
        NEGATIVE_HM("-H:mm", DateFormat.HOUR24_MINUTE, false),
        NEGATIVE_HMS("-H:mm:ss", DateFormat.HOUR24_MINUTE_SECOND, false),
        POSITIVE_H("+H", DateFormat.HOUR24, true),
        NEGATIVE_H("-H", DateFormat.HOUR24, false);

        private String _defaultPattern;
        private boolean _isPositive;
        private String _required;

        GMTOffsetPatternType(String str, String str2, boolean z) {
            this._defaultPattern = str;
            this._required = str2;
            this._isPositive = z;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public String defaultPattern() {
            return this._defaultPattern;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public String required() {
            return this._required;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public boolean isPositive() {
            return this._isPositive;
        }
    }

    protected TimeZoneFormat(ULocale uLocale) {
        String str;
        String stringWithFallback;
        this._locale = uLocale;
        this._tznames = TimeZoneNames.getInstance(uLocale);
        this._gmtZeroFormat = "GMT";
        String stringWithFallback2 = null;
        try {
            ICUResourceBundle iCUResourceBundle = (ICUResourceBundle) ICUResourceBundle.getBundleInstance(ICUData.ICU_ZONE_BASE_NAME, uLocale);
            try {
                stringWithFallback = iCUResourceBundle.getStringWithFallback("zoneStrings/gmtFormat");
            } catch (MissingResourceException unused) {
                stringWithFallback = null;
            }
            try {
                stringWithFallback2 = iCUResourceBundle.getStringWithFallback("zoneStrings/hourFormat");
            } catch (MissingResourceException unused2) {
            }
            try {
                this._gmtZeroFormat = iCUResourceBundle.getStringWithFallback("zoneStrings/gmtZeroFormat");
            } catch (MissingResourceException unused3) {
            }
            str = stringWithFallback2;
            stringWithFallback2 = stringWithFallback;
        } catch (MissingResourceException unused4) {
            str = null;
        }
        initGMTPattern(stringWithFallback2 == null ? DEFAULT_GMT_PATTERN : stringWithFallback2);
        String[] strArr = new String[GMTOffsetPatternType.values().length];
        if (str != null) {
            String[] strArrSplit = str.split(";", 2);
            strArr[GMTOffsetPatternType.POSITIVE_H.ordinal()] = truncateOffsetPattern(strArrSplit[0]);
            strArr[GMTOffsetPatternType.POSITIVE_HM.ordinal()] = strArrSplit[0];
            strArr[GMTOffsetPatternType.POSITIVE_HMS.ordinal()] = expandOffsetPattern(strArrSplit[0]);
            strArr[GMTOffsetPatternType.NEGATIVE_H.ordinal()] = truncateOffsetPattern(strArrSplit[1]);
            strArr[GMTOffsetPatternType.NEGATIVE_HM.ordinal()] = strArrSplit[1];
            strArr[GMTOffsetPatternType.NEGATIVE_HMS.ordinal()] = expandOffsetPattern(strArrSplit[1]);
        } else {
            for (GMTOffsetPatternType gMTOffsetPatternType : GMTOffsetPatternType.values()) {
                strArr[gMTOffsetPatternType.ordinal()] = gMTOffsetPatternType.defaultPattern();
            }
        }
        initGMTOffsetPatterns(strArr);
        this._gmtOffsetDigits = DEFAULT_GMT_DIGITS;
        NumberingSystem numberingSystem = NumberingSystem.getInstance(uLocale);
        if (numberingSystem.isAlgorithmic()) {
            return;
        }
        this._gmtOffsetDigits = toCodePoints(numberingSystem.getDescription());
    }

    public static TimeZoneFormat getInstance(ULocale uLocale) {
        if (uLocale == null) {
            throw new NullPointerException("locale is null");
        }
        return _tzfCache.getInstance(uLocale, uLocale);
    }

    public static TimeZoneFormat getInstance(Locale locale) {
        return getInstance(ULocale.forLocale(locale));
    }

    public TimeZoneNames getTimeZoneNames() {
        return this._tznames;
    }

    private TimeZoneGenericNames getTimeZoneGenericNames() {
        if (this._gnames == null) {
            synchronized (this) {
                if (this._gnames == null) {
                    this._gnames = TimeZoneGenericNames.getInstance(this._locale);
                }
            }
        }
        return this._gnames;
    }

    private TimeZoneNames getTZDBTimeZoneNames() {
        if (this._tzdbNames == null) {
            synchronized (this) {
                if (this._tzdbNames == null) {
                    this._tzdbNames = new TZDBTimeZoneNames(this._locale);
                }
            }
        }
        return this._tzdbNames;
    }

    public TimeZoneFormat setTimeZoneNames(TimeZoneNames timeZoneNames) {
        if (isFrozen()) {
            throw new UnsupportedOperationException("Attempt to modify frozen object");
        }
        this._tznames = timeZoneNames;
        this._gnames = new TimeZoneGenericNames(this._locale, timeZoneNames);
        return this;
    }

    public String getGMTPattern() {
        return this._gmtPattern;
    }

    public TimeZoneFormat setGMTPattern(String str) {
        if (isFrozen()) {
            throw new UnsupportedOperationException("Attempt to modify frozen object");
        }
        initGMTPattern(str);
        return this;
    }

    public String getGMTOffsetPattern(GMTOffsetPatternType gMTOffsetPatternType) {
        return this._gmtOffsetPatterns[gMTOffsetPatternType.ordinal()];
    }

    public TimeZoneFormat setGMTOffsetPattern(GMTOffsetPatternType gMTOffsetPatternType, String str) {
        if (isFrozen()) {
            throw new UnsupportedOperationException("Attempt to modify frozen object");
        }
        if (str != null) {
            Object[] offsetPattern = parseOffsetPattern(str, gMTOffsetPatternType.required());
            this._gmtOffsetPatterns[gMTOffsetPatternType.ordinal()] = str;
            this._gmtOffsetPatternItems[gMTOffsetPatternType.ordinal()] = offsetPattern;
            checkAbuttingHoursAndMinutes();
            return this;
        }
        throw new NullPointerException("Null GMT offset pattern");
    }

    public String getGMTOffsetDigits() {
        StringBuilder sb = new StringBuilder(this._gmtOffsetDigits.length);
        for (String str : this._gmtOffsetDigits) {
            sb.append(str);
        }
        return sb.toString();
    }

    public TimeZoneFormat setGMTOffsetDigits(String str) {
        if (isFrozen()) {
            throw new UnsupportedOperationException("Attempt to modify frozen object");
        }
        if (str == null) {
            throw new NullPointerException("Null GMT offset digits");
        }
        String[] codePoints = toCodePoints(str);
        if (codePoints.length != 10) {
            throw new IllegalArgumentException("Length of digits must be 10");
        }
        this._gmtOffsetDigits = codePoints;
        return this;
    }

    public String getGMTZeroFormat() {
        return this._gmtZeroFormat;
    }

    public TimeZoneFormat setGMTZeroFormat(String str) {
        if (isFrozen()) {
            throw new UnsupportedOperationException("Attempt to modify frozen object");
        }
        if (str == null) {
            throw new NullPointerException("Null GMT zero format");
        }
        if (str.length() == 0) {
            throw new IllegalArgumentException("Empty GMT zero format");
        }
        this._gmtZeroFormat = str;
        return this;
    }

    public TimeZoneFormat setDefaultParseOptions(EnumSet<ParseOption> enumSet) {
        this._parseAllStyles = enumSet.contains(ParseOption.ALL_STYLES);
        this._parseTZDBNames = enumSet.contains(ParseOption.TZ_DATABASE_ABBREVIATIONS);
        return this;
    }

    public EnumSet<ParseOption> getDefaultParseOptions() {
        if (this._parseAllStyles && this._parseTZDBNames) {
            return EnumSet.of(ParseOption.ALL_STYLES, ParseOption.TZ_DATABASE_ABBREVIATIONS);
        }
        if (this._parseAllStyles) {
            return EnumSet.of(ParseOption.ALL_STYLES);
        }
        if (this._parseTZDBNames) {
            return EnumSet.of(ParseOption.TZ_DATABASE_ABBREVIATIONS);
        }
        return EnumSet.noneOf(ParseOption.class);
    }

    public final String formatOffsetISO8601Basic(int i, boolean z, boolean z2, boolean z3) {
        return formatOffsetISO8601(i, true, z, z2, z3);
    }

    public final String formatOffsetISO8601Extended(int i, boolean z, boolean z2, boolean z3) {
        return formatOffsetISO8601(i, false, z, z2, z3);
    }

    public String formatOffsetLocalizedGMT(int i) {
        return formatOffsetLocalizedGMT(i, false);
    }

    public String formatOffsetShortLocalizedGMT(int i) {
        return formatOffsetLocalizedGMT(i, true);
    }

    public final String format(Style style, TimeZone timeZone, long j) {
        return format(style, timeZone, j, null);
    }

    /* JADX WARN: Failed to find 'out' block for switch in B:5:0x0010. Please report as an issue. */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v32, types: [T, com.ibm.icu.text.TimeZoneFormat$TimeType] */
    public String format(Style style, TimeZone timeZone, long j, Output<TimeType> output) {
        String genericLocationName;
        boolean z;
        if (output != null) {
            output.value = TimeType.UNKNOWN;
        }
        switch (AnonymousClass1.$SwitchMap$com$ibm$icu$text$TimeZoneFormat$Style[style.ordinal()]) {
            case 1:
                genericLocationName = getTimeZoneGenericNames().getGenericLocationName(ZoneMeta.getCanonicalCLDRID(timeZone));
                z = false;
                break;
            case 2:
                genericLocationName = getTimeZoneGenericNames().getDisplayName(timeZone, TimeZoneGenericNames.GenericNameType.LONG, j);
                z = false;
                break;
            case 3:
                genericLocationName = getTimeZoneGenericNames().getDisplayName(timeZone, TimeZoneGenericNames.GenericNameType.SHORT, j);
                z = false;
                break;
            case 4:
                genericLocationName = formatSpecific(timeZone, TimeZoneNames.NameType.LONG_STANDARD, TimeZoneNames.NameType.LONG_DAYLIGHT, j, output);
                z = false;
                break;
            case 5:
                genericLocationName = formatSpecific(timeZone, TimeZoneNames.NameType.SHORT_STANDARD, TimeZoneNames.NameType.SHORT_DAYLIGHT, j, output);
                z = false;
                break;
            case 6:
                genericLocationName = timeZone.getID();
                z = true;
                break;
            case 7:
                genericLocationName = ZoneMeta.getShortID(timeZone);
                if (genericLocationName == null) {
                    genericLocationName = UNKNOWN_SHORT_ZONE_ID;
                }
                z = true;
                break;
            case 8:
                genericLocationName = formatExemplarLocation(timeZone);
                z = true;
                break;
            default:
                genericLocationName = null;
                z = false;
                break;
        }
        if (genericLocationName == null && !z) {
            int[] iArr = {0, 0};
            timeZone.getOffset(j, false, iArr);
            int i = iArr[0] + iArr[1];
            switch (AnonymousClass1.$SwitchMap$com$ibm$icu$text$TimeZoneFormat$Style[style.ordinal()]) {
                case 1:
                case 2:
                case 4:
                case 9:
                    genericLocationName = formatOffsetLocalizedGMT(i);
                    break;
                case 3:
                case 5:
                case 10:
                    genericLocationName = formatOffsetShortLocalizedGMT(i);
                    break;
                case 11:
                    genericLocationName = formatOffsetISO8601Basic(i, true, true, true);
                    break;
                case 12:
                    genericLocationName = formatOffsetISO8601Basic(i, false, true, true);
                    break;
                case 13:
                    genericLocationName = formatOffsetISO8601Basic(i, true, false, true);
                    break;
                case 14:
                    genericLocationName = formatOffsetISO8601Basic(i, false, false, true);
                    break;
                case 15:
                    genericLocationName = formatOffsetISO8601Basic(i, true, false, false);
                    break;
                case 16:
                    genericLocationName = formatOffsetISO8601Basic(i, false, false, false);
                    break;
                case 17:
                    genericLocationName = formatOffsetISO8601Extended(i, true, false, true);
                    break;
                case 18:
                    genericLocationName = formatOffsetISO8601Extended(i, false, false, true);
                    break;
                case 19:
                    genericLocationName = formatOffsetISO8601Extended(i, true, false, false);
                    break;
                case 20:
                    genericLocationName = formatOffsetISO8601Extended(i, false, false, false);
                    break;
            }
            if (output != null) {
                output.value = iArr[1] != 0 ? TimeType.DAYLIGHT : TimeType.STANDARD;
            }
        }
        return genericLocationName;
    }

    public final int parseOffsetISO8601(String str, ParsePosition parsePosition) {
        return parseOffsetISO8601(str, parsePosition, false, null);
    }

    public int parseOffsetLocalizedGMT(String str, ParsePosition parsePosition) {
        return parseOffsetLocalizedGMT(str, parsePosition, false, null);
    }

    public int parseOffsetShortLocalizedGMT(String str, ParsePosition parsePosition) {
        return parseOffsetLocalizedGMT(str, parsePosition, true, null);
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:126:0x02aa  */
    /* JADX WARN: Removed duplicated region for block: B:128:0x02b2  */
    /* JADX WARN: Removed duplicated region for block: B:176:0x03a5  */
    /* JADX WARN: Removed duplicated region for block: B:213:0x0473  */
    /* JADX WARN: Removed duplicated region for block: B:216:0x0479  */
    /* JADX WARN: Removed duplicated region for block: B:222:0x049f  */
    /* JADX WARN: Removed duplicated region for block: B:230:0x04ca  */
    /* JADX WARN: Type inference failed for: r1v18, types: [T, com.ibm.icu.text.TimeZoneFormat$TimeType] */
    /* JADX WARN: Type inference failed for: r1v23, types: [T, com.ibm.icu.text.TimeZoneFormat$TimeType] */
    /* JADX WARN: Type inference failed for: r1v28, types: [T, com.ibm.icu.text.TimeZoneFormat$TimeType] */
    /* JADX WARN: Type inference failed for: r6v0, types: [T, com.ibm.icu.text.TimeZoneFormat$TimeType] */
    /* JADX WARN: Type inference failed for: r8v15 */
    /* JADX WARN: Type inference failed for: r8v16, types: [T] */
    /* JADX WARN: Type inference failed for: r8v19 */
    /* JADX WARN: Type inference failed for: r8v20 */
    /* JADX WARN: Type inference failed for: r8v21 */
    /* JADX WARN: Type inference failed for: r8v23 */
    /* JADX WARN: Type inference failed for: r8v24, types: [com.ibm.icu.text.TimeZoneFormat$TimeType] */
    /* JADX WARN: Type inference failed for: r8v25, types: [com.ibm.icu.text.TimeZoneFormat$TimeType] */
    /* JADX WARN: Type inference failed for: r8v49 */
    /* JADX WARN: Type inference failed for: r8v50 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public com.ibm.icu.util.TimeZone parse(com.ibm.icu.text.TimeZoneFormat.Style r21, java.lang.String r22, java.text.ParsePosition r23, java.util.EnumSet<com.ibm.icu.text.TimeZoneFormat.ParseOption> r24, com.ibm.icu.util.Output<com.ibm.icu.text.TimeZoneFormat.TimeType> r25) {
        /*
            Method dump skipped, instructions count: 1344
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ibm.icu.text.TimeZoneFormat.parse(com.ibm.icu.text.TimeZoneFormat$Style, java.lang.String, java.text.ParsePosition, java.util.EnumSet, com.ibm.icu.util.Output):com.ibm.icu.util.TimeZone");
    }

    public TimeZone parse(Style style, String str, ParsePosition parsePosition, Output<TimeType> output) {
        return parse(style, str, parsePosition, null, output);
    }

    public final TimeZone parse(String str, ParsePosition parsePosition) {
        return parse(Style.GENERIC_LOCATION, str, parsePosition, EnumSet.of(ParseOption.ALL_STYLES), null);
    }

    public final TimeZone parse(String str) throws ParseException {
        ParsePosition parsePosition = new ParsePosition(0);
        TimeZone timeZone = parse(str, parsePosition);
        if (parsePosition.getErrorIndex() < 0) {
            return timeZone;
        }
        throw new ParseException("Unparseable time zone: \"" + str + "\"", 0);
    }

    @Override // java.text.Format
    public StringBuffer format(Object obj, StringBuffer stringBuffer, FieldPosition fieldPosition) {
        TimeZone timeZone;
        long jCurrentTimeMillis = System.currentTimeMillis();
        if (obj instanceof TimeZone) {
            timeZone = (TimeZone) obj;
        } else if (obj instanceof Calendar) {
            Calendar calendar = (Calendar) obj;
            TimeZone timeZone2 = calendar.getTimeZone();
            long timeInMillis = calendar.getTimeInMillis();
            timeZone = timeZone2;
            jCurrentTimeMillis = timeInMillis;
        } else {
            throw new IllegalArgumentException("Cannot format given Object (" + obj.getClass().getName() + ") as a time zone");
        }
        String offsetLocalizedGMT = formatOffsetLocalizedGMT(timeZone.getOffset(jCurrentTimeMillis));
        stringBuffer.append(offsetLocalizedGMT);
        if (fieldPosition.getFieldAttribute() == DateFormat.Field.TIME_ZONE || fieldPosition.getField() == 17) {
            fieldPosition.setBeginIndex(0);
            fieldPosition.setEndIndex(offsetLocalizedGMT.length());
        }
        return stringBuffer;
    }

    @Override // java.text.Format
    public AttributedCharacterIterator formatToCharacterIterator(Object obj) {
        AttributedString attributedString = new AttributedString(format(obj, new StringBuffer(), new FieldPosition(0)).toString());
        attributedString.addAttribute(DateFormat.Field.TIME_ZONE, DateFormat.Field.TIME_ZONE);
        return attributedString.getIterator();
    }

    @Override // java.text.Format
    public Object parseObject(String str, ParsePosition parsePosition) {
        return parse(str, parsePosition);
    }

    private String formatOffsetLocalizedGMT(int i, boolean z) {
        boolean z2;
        Object[] objArr;
        if (i == 0) {
            return this._gmtZeroFormat;
        }
        StringBuilder sb = new StringBuilder();
        if (i < 0) {
            i = -i;
            z2 = false;
        } else {
            z2 = true;
        }
        int i2 = i / 3600000;
        int i3 = i % 3600000;
        int i4 = i3 / 60000;
        int i5 = i3 % 60000;
        int i6 = i5 / 1000;
        if (i2 > 23 || i4 > 59 || i6 > 59) {
            throw new IllegalArgumentException("Offset out of range :" + i5);
        }
        if (z2) {
            if (i6 != 0) {
                objArr = this._gmtOffsetPatternItems[GMTOffsetPatternType.POSITIVE_HMS.ordinal()];
            } else if (i4 != 0 || !z) {
                objArr = this._gmtOffsetPatternItems[GMTOffsetPatternType.POSITIVE_HM.ordinal()];
            } else {
                objArr = this._gmtOffsetPatternItems[GMTOffsetPatternType.POSITIVE_H.ordinal()];
            }
        } else if (i6 != 0) {
            objArr = this._gmtOffsetPatternItems[GMTOffsetPatternType.NEGATIVE_HMS.ordinal()];
        } else if (i4 != 0 || !z) {
            objArr = this._gmtOffsetPatternItems[GMTOffsetPatternType.NEGATIVE_HM.ordinal()];
        } else {
            objArr = this._gmtOffsetPatternItems[GMTOffsetPatternType.NEGATIVE_H.ordinal()];
        }
        sb.append(this._gmtPatternPrefix);
        for (Object obj : objArr) {
            if (obj instanceof String) {
                sb.append((String) obj);
            } else if (obj instanceof GMTOffsetField) {
                char type = ((GMTOffsetField) obj).getType();
                if (type == 'H') {
                    appendOffsetDigits(sb, i2, z ? 1 : 2);
                } else if (type == 'm') {
                    appendOffsetDigits(sb, i4, 2);
                } else if (type == 's') {
                    appendOffsetDigits(sb, i6, 2);
                }
            }
        }
        sb.append(this._gmtPatternSuffix);
        return sb.toString();
    }

    private String formatOffsetISO8601(int i, boolean z, boolean z2, boolean z3, boolean z4) {
        int i2 = i < 0 ? -i : i;
        if (z2) {
            if (i2 < 1000) {
                return ISO8601_UTC;
            }
            if (z4 && i2 < 60000) {
                return ISO8601_UTC;
            }
        }
        OffsetFields offsetFields = z3 ? OffsetFields.H : OffsetFields.HM;
        OffsetFields offsetFields2 = z4 ? OffsetFields.HM : OffsetFields.HMS;
        Character chValueOf = z ? null : Character.valueOf(DEFAULT_GMT_OFFSET_SEP);
        if (i2 >= 86400000) {
            throw new IllegalArgumentException("Offset out of range :" + i);
        }
        int i3 = i2 % 3600000;
        int[] iArr = {i2 / 3600000, i3 / 60000, (i3 % 60000) / 1000};
        int iOrdinal = offsetFields2.ordinal();
        while (iOrdinal > offsetFields.ordinal() && iArr[iOrdinal] == 0) {
            iOrdinal--;
        }
        StringBuilder sb = new StringBuilder();
        char c = '+';
        if (i < 0) {
            int i4 = 0;
            while (true) {
                if (i4 > iOrdinal) {
                    break;
                }
                if (iArr[i4] != 0) {
                    c = Soundex.SILENT_MARKER;
                    break;
                }
                i4++;
            }
        }
        sb.append(c);
        for (int i5 = 0; i5 <= iOrdinal; i5++) {
            if (chValueOf != null && i5 != 0) {
                sb.append(chValueOf);
            }
            if (iArr[i5] < 10) {
                sb.append('0');
            }
            sb.append(iArr[i5]);
        }
        return sb.toString();
    }

    /* JADX WARN: Multi-variable type inference failed */
    private String formatSpecific(TimeZone timeZone, TimeZoneNames.NameType nameType, TimeZoneNames.NameType nameType2, long j, Output<TimeType> output) {
        String displayName;
        boolean zInDaylightTime = timeZone.inDaylightTime(new Date(j));
        if (zInDaylightTime) {
            displayName = getTimeZoneNames().getDisplayName(ZoneMeta.getCanonicalCLDRID(timeZone), nameType2, j);
        } else {
            displayName = getTimeZoneNames().getDisplayName(ZoneMeta.getCanonicalCLDRID(timeZone), nameType, j);
        }
        if (displayName != null && output != null) {
            output.value = zInDaylightTime ? TimeType.DAYLIGHT : TimeType.STANDARD;
        }
        return displayName;
    }

    private String formatExemplarLocation(TimeZone timeZone) {
        String exemplarLocationName = getTimeZoneNames().getExemplarLocationName(ZoneMeta.getCanonicalCLDRID(timeZone));
        if (exemplarLocationName != null) {
            return exemplarLocationName;
        }
        String exemplarLocationName2 = getTimeZoneNames().getExemplarLocationName("Etc/Unknown");
        return exemplarLocationName2 == null ? "Unknown" : exemplarLocationName2;
    }

    private String getTimeZoneID(String str, String str2) {
        if (str != null || (str = this._tznames.getReferenceZoneID(str2, getTargetRegion())) != null) {
            return str;
        }
        throw new IllegalArgumentException("Invalid mzID: " + str2);
    }

    private synchronized String getTargetRegion() {
        if (this._region == null) {
            String country = this._locale.getCountry();
            this._region = country;
            if (country.length() == 0) {
                String country2 = ULocale.addLikelySubtags(this._locale).getCountry();
                this._region = country2;
                if (country2.length() == 0) {
                    this._region = UnitsData.Constants.DEFAULT_REGION;
                }
            }
        }
        return this._region;
    }

    /* renamed from: com.ibm.icu.text.TimeZoneFormat$1, reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$ibm$icu$text$TimeZoneFormat$Style;
        static final /* synthetic */ int[] $SwitchMap$com$ibm$icu$text$TimeZoneNames$NameType;

        static {
            int[] iArr = new int[TimeZoneNames.NameType.values().length];
            $SwitchMap$com$ibm$icu$text$TimeZoneNames$NameType = iArr;
            try {
                iArr[TimeZoneNames.NameType.LONG_STANDARD.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$ibm$icu$text$TimeZoneNames$NameType[TimeZoneNames.NameType.SHORT_STANDARD.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$ibm$icu$text$TimeZoneNames$NameType[TimeZoneNames.NameType.LONG_DAYLIGHT.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$com$ibm$icu$text$TimeZoneNames$NameType[TimeZoneNames.NameType.SHORT_DAYLIGHT.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            int[] iArr2 = new int[Style.values().length];
            $SwitchMap$com$ibm$icu$text$TimeZoneFormat$Style = iArr2;
            try {
                iArr2[Style.GENERIC_LOCATION.ordinal()] = 1;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                $SwitchMap$com$ibm$icu$text$TimeZoneFormat$Style[Style.GENERIC_LONG.ordinal()] = 2;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                $SwitchMap$com$ibm$icu$text$TimeZoneFormat$Style[Style.GENERIC_SHORT.ordinal()] = 3;
            } catch (NoSuchFieldError unused7) {
            }
            try {
                $SwitchMap$com$ibm$icu$text$TimeZoneFormat$Style[Style.SPECIFIC_LONG.ordinal()] = 4;
            } catch (NoSuchFieldError unused8) {
            }
            try {
                $SwitchMap$com$ibm$icu$text$TimeZoneFormat$Style[Style.SPECIFIC_SHORT.ordinal()] = 5;
            } catch (NoSuchFieldError unused9) {
            }
            try {
                $SwitchMap$com$ibm$icu$text$TimeZoneFormat$Style[Style.ZONE_ID.ordinal()] = 6;
            } catch (NoSuchFieldError unused10) {
            }
            try {
                $SwitchMap$com$ibm$icu$text$TimeZoneFormat$Style[Style.ZONE_ID_SHORT.ordinal()] = 7;
            } catch (NoSuchFieldError unused11) {
            }
            try {
                $SwitchMap$com$ibm$icu$text$TimeZoneFormat$Style[Style.EXEMPLAR_LOCATION.ordinal()] = 8;
            } catch (NoSuchFieldError unused12) {
            }
            try {
                $SwitchMap$com$ibm$icu$text$TimeZoneFormat$Style[Style.LOCALIZED_GMT.ordinal()] = 9;
            } catch (NoSuchFieldError unused13) {
            }
            try {
                $SwitchMap$com$ibm$icu$text$TimeZoneFormat$Style[Style.LOCALIZED_GMT_SHORT.ordinal()] = 10;
            } catch (NoSuchFieldError unused14) {
            }
            try {
                $SwitchMap$com$ibm$icu$text$TimeZoneFormat$Style[Style.ISO_BASIC_SHORT.ordinal()] = 11;
            } catch (NoSuchFieldError unused15) {
            }
            try {
                $SwitchMap$com$ibm$icu$text$TimeZoneFormat$Style[Style.ISO_BASIC_LOCAL_SHORT.ordinal()] = 12;
            } catch (NoSuchFieldError unused16) {
            }
            try {
                $SwitchMap$com$ibm$icu$text$TimeZoneFormat$Style[Style.ISO_BASIC_FIXED.ordinal()] = 13;
            } catch (NoSuchFieldError unused17) {
            }
            try {
                $SwitchMap$com$ibm$icu$text$TimeZoneFormat$Style[Style.ISO_BASIC_LOCAL_FIXED.ordinal()] = 14;
            } catch (NoSuchFieldError unused18) {
            }
            try {
                $SwitchMap$com$ibm$icu$text$TimeZoneFormat$Style[Style.ISO_BASIC_FULL.ordinal()] = 15;
            } catch (NoSuchFieldError unused19) {
            }
            try {
                $SwitchMap$com$ibm$icu$text$TimeZoneFormat$Style[Style.ISO_BASIC_LOCAL_FULL.ordinal()] = 16;
            } catch (NoSuchFieldError unused20) {
            }
            try {
                $SwitchMap$com$ibm$icu$text$TimeZoneFormat$Style[Style.ISO_EXTENDED_FIXED.ordinal()] = 17;
            } catch (NoSuchFieldError unused21) {
            }
            try {
                $SwitchMap$com$ibm$icu$text$TimeZoneFormat$Style[Style.ISO_EXTENDED_LOCAL_FIXED.ordinal()] = 18;
            } catch (NoSuchFieldError unused22) {
            }
            try {
                $SwitchMap$com$ibm$icu$text$TimeZoneFormat$Style[Style.ISO_EXTENDED_FULL.ordinal()] = 19;
            } catch (NoSuchFieldError unused23) {
            }
            try {
                $SwitchMap$com$ibm$icu$text$TimeZoneFormat$Style[Style.ISO_EXTENDED_LOCAL_FULL.ordinal()] = 20;
            } catch (NoSuchFieldError unused24) {
            }
        }
    }

    private TimeType getTimeType(TimeZoneNames.NameType nameType) {
        int i = AnonymousClass1.$SwitchMap$com$ibm$icu$text$TimeZoneNames$NameType[nameType.ordinal()];
        if (i == 1 || i == 2) {
            return TimeType.STANDARD;
        }
        if (i == 3 || i == 4) {
            return TimeType.DAYLIGHT;
        }
        return TimeType.UNKNOWN;
    }

    private void initGMTPattern(String str) {
        int iIndexOf = str.indexOf("{0}");
        if (iIndexOf < 0) {
            throw new IllegalArgumentException("Bad localized GMT pattern: " + str);
        }
        this._gmtPattern = str;
        this._gmtPatternPrefix = unquote(str.substring(0, iIndexOf));
        this._gmtPatternSuffix = unquote(str.substring(iIndexOf + 3));
    }

    private static String unquote(String str) {
        if (str.indexOf(39) < 0) {
            return str;
        }
        StringBuilder sb = new StringBuilder();
        boolean z = false;
        for (int i = 0; i < str.length(); i++) {
            char cCharAt = str.charAt(i);
            if (cCharAt != '\'' || z) {
                sb.append(cCharAt);
                z = false;
            } else {
                z = true;
            }
        }
        return sb.toString();
    }

    private void initGMTOffsetPatterns(String[] strArr) {
        int length = GMTOffsetPatternType.values().length;
        if (strArr.length < length) {
            throw new IllegalArgumentException("Insufficient number of elements in gmtOffsetPatterns");
        }
        Object[][] objArr = new Object[length][];
        for (GMTOffsetPatternType gMTOffsetPatternType : GMTOffsetPatternType.values()) {
            int iOrdinal = gMTOffsetPatternType.ordinal();
            objArr[iOrdinal] = parseOffsetPattern(strArr[iOrdinal], gMTOffsetPatternType.required());
        }
        String[] strArr2 = new String[length];
        this._gmtOffsetPatterns = strArr2;
        System.arraycopy(strArr, 0, strArr2, 0, length);
        this._gmtOffsetPatternItems = objArr;
        checkAbuttingHoursAndMinutes();
    }

    private void checkAbuttingHoursAndMinutes() {
        this._abuttingOffsetHoursAndMinutes = false;
        for (Object[] objArr : this._gmtOffsetPatternItems) {
            boolean z = false;
            for (Object obj : objArr) {
                if (!(obj instanceof GMTOffsetField)) {
                    if (z) {
                        break;
                    }
                } else {
                    GMTOffsetField gMTOffsetField = (GMTOffsetField) obj;
                    if (z) {
                        this._abuttingOffsetHoursAndMinutes = true;
                    } else if (gMTOffsetField.getType() == 'H') {
                        z = true;
                    }
                }
            }
        }
    }

    private static class GMTOffsetField {
        final char _type;
        final int _width;

        static boolean isValid(char c, int i) {
            return i == 1 || i == 2;
        }

        GMTOffsetField(char c, int i) {
            this._type = c;
            this._width = i;
        }

        char getType() {
            return this._type;
        }

        int getWidth() {
            return this._width;
        }
    }

    private static Object[] parseOffsetPattern(String str, String str2) {
        boolean z;
        StringBuilder sb = new StringBuilder();
        ArrayList arrayList = new ArrayList();
        BitSet bitSet = new BitSet(str2.length());
        boolean z2 = true;
        boolean z3 = false;
        boolean z4 = false;
        char c = 0;
        int i = 1;
        for (int i2 = 0; i2 < str.length(); i2++) {
            char cCharAt = str.charAt(i2);
            if (cCharAt == '\'') {
                if (z4) {
                    sb.append(PatternTokenizer.SINGLE_QUOTE);
                    z4 = false;
                } else if (c == 0) {
                    z4 = true;
                } else {
                    if (!GMTOffsetField.isValid(c, i)) {
                        z = true;
                        break;
                    }
                    arrayList.add(new GMTOffsetField(c, i));
                    z4 = true;
                    c = 0;
                }
                z3 = !z3;
            } else {
                if (z3) {
                    sb.append(cCharAt);
                } else {
                    int iIndexOf = str2.indexOf(cCharAt);
                    if (iIndexOf < 0) {
                        if (c != 0) {
                            if (!GMTOffsetField.isValid(c, i)) {
                                z = true;
                                break;
                            }
                            arrayList.add(new GMTOffsetField(c, i));
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
                            if (!GMTOffsetField.isValid(c, i)) {
                                z = true;
                                break;
                            }
                            arrayList.add(new GMTOffsetField(c, i));
                        }
                        bitSet.set(iIndexOf);
                        c = cCharAt;
                        z4 = false;
                        i = 1;
                    }
                }
                z4 = false;
            }
        }
        z = false;
        if (z) {
            z2 = z;
        } else {
            if (c == 0) {
                if (sb.length() > 0) {
                    arrayList.add(sb.toString());
                    sb.setLength(0);
                }
            } else if (GMTOffsetField.isValid(c, i)) {
                arrayList.add(new GMTOffsetField(c, i));
            }
            z2 = z;
        }
        if (z2 || bitSet.cardinality() != str2.length()) {
            throw new IllegalStateException("Bad localized GMT offset pattern: " + str);
        }
        return arrayList.toArray(new Object[arrayList.size()]);
    }

    private static String expandOffsetPattern(String str) {
        int iIndexOf = str.indexOf("mm");
        if (iIndexOf < 0) {
            throw new RuntimeException("Bad time zone hour pattern data");
        }
        int iLastIndexOf = str.substring(0, iIndexOf).lastIndexOf(DateFormat.HOUR24);
        String strSubstring = iLastIndexOf >= 0 ? str.substring(iLastIndexOf + 1, iIndexOf) : ":";
        StringBuilder sb = new StringBuilder();
        int i = iIndexOf + 2;
        sb.append(str.substring(0, i));
        sb.append(strSubstring);
        sb.append("ss");
        sb.append(str.substring(i));
        return sb.toString();
    }

    private static String truncateOffsetPattern(String str) {
        int iIndexOf = str.indexOf("mm");
        if (iIndexOf < 0) {
            throw new RuntimeException("Bad time zone hour pattern data");
        }
        int iLastIndexOf = str.substring(0, iIndexOf).lastIndexOf("HH");
        if (iLastIndexOf >= 0) {
            return str.substring(0, iLastIndexOf + 2);
        }
        int iLastIndexOf2 = str.substring(0, iIndexOf).lastIndexOf(DateFormat.HOUR24);
        if (iLastIndexOf2 >= 0) {
            return str.substring(0, iLastIndexOf2 + 1);
        }
        throw new RuntimeException("Bad time zone hour pattern data");
    }

    private void appendOffsetDigits(StringBuilder sb, int i, int i2) {
        int i3 = i >= 10 ? 2 : 1;
        for (int i4 = 0; i4 < i2 - i3; i4++) {
            sb.append(this._gmtOffsetDigits[0]);
        }
        if (i3 == 2) {
            sb.append(this._gmtOffsetDigits[i / 10]);
        }
        sb.append(this._gmtOffsetDigits[i % 10]);
    }

    private TimeZone getTimeZoneForOffset(int i) {
        if (i == 0) {
            return TimeZone.getTimeZone("Etc/GMT");
        }
        return ZoneMeta.getCustomTimeZone(i);
    }

    /* JADX WARN: Type inference failed for: r1v0, types: [T, java.lang.Boolean] */
    /* JADX WARN: Type inference failed for: r2v4, types: [T, java.lang.Boolean] */
    private int parseOffsetLocalizedGMT(String str, ParsePosition parsePosition, boolean z, Output<Boolean> output) {
        int index = parsePosition.getIndex();
        int[] iArr = {0};
        if (output != null) {
            output.value = false;
        }
        int offsetLocalizedGMTPattern = parseOffsetLocalizedGMTPattern(str, index, z, iArr);
        if (iArr[0] > 0) {
            if (output != null) {
                output.value = true;
            }
            parsePosition.setIndex(index + iArr[0]);
            return offsetLocalizedGMTPattern;
        }
        int offsetDefaultLocalizedGMT = parseOffsetDefaultLocalizedGMT(str, index, iArr);
        if (iArr[0] > 0) {
            if (output != null) {
                output.value = true;
            }
            parsePosition.setIndex(index + iArr[0]);
            return offsetDefaultLocalizedGMT;
        }
        String str2 = this._gmtZeroFormat;
        if (str.regionMatches(true, index, str2, 0, str2.length())) {
            parsePosition.setIndex(index + this._gmtZeroFormat.length());
            return 0;
        }
        for (String str3 : ALT_GMT_STRINGS) {
            if (str.regionMatches(true, index, str3, 0, str3.length())) {
                parsePosition.setIndex(index + str3.length());
                return 0;
            }
        }
        parsePosition.setErrorIndex(index);
        return 0;
    }

    /* JADX WARN: Removed duplicated region for block: B:17:0x004c  */
    /* JADX WARN: Removed duplicated region for block: B:18:0x004f  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    private int parseOffsetLocalizedGMTPattern(java.lang.String r17, int r18, boolean r19, int[] r20) {
        /*
            r16 = this;
            r0 = r16
            java.lang.String r1 = r0._gmtPatternPrefix
            int r7 = r1.length()
            r8 = 1
            r9 = 0
            if (r7 <= 0) goto L1f
            r2 = 1
            java.lang.String r4 = r0._gmtPatternPrefix
            r5 = 0
            r1 = r17
            r3 = r18
            r6 = r7
            boolean r1 = r1.regionMatches(r2, r3, r4, r5, r6)
            if (r1 != 0) goto L1f
            r1 = r18
            r4 = 0
            goto L2d
        L1f:
            int r1 = r18 + r7
            int[] r2 = new int[r8]
            r3 = r17
            int r4 = r0.parseOffsetFields(r3, r1, r9, r2)
            r5 = r2[r9]
            if (r5 != 0) goto L2f
        L2d:
            r8 = 0
            goto L4a
        L2f:
            r2 = r2[r9]
            int r1 = r1 + r2
            java.lang.String r2 = r0._gmtPatternSuffix
            int r2 = r2.length()
            if (r2 <= 0) goto L49
            r11 = 1
            java.lang.String r13 = r0._gmtPatternSuffix
            r14 = 0
            r10 = r17
            r12 = r1
            r15 = r2
            boolean r3 = r10.regionMatches(r11, r12, r13, r14, r15)
            if (r3 != 0) goto L49
            goto L2d
        L49:
            int r1 = r1 + r2
        L4a:
            if (r8 == 0) goto L4f
            int r1 = r1 - r18
            goto L50
        L4f:
            r1 = 0
        L50:
            r20[r9] = r1
            return r4
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ibm.icu.text.TimeZoneFormat.parseOffsetLocalizedGMTPattern(java.lang.String, int, boolean, int[]):int");
    }

    private int parseOffsetFields(String str, int i, boolean z, int[] iArr) {
        int i2;
        int i3;
        int i4;
        int i5;
        int i6;
        int i7;
        if (iArr != null && iArr.length >= 1) {
            iArr[0] = 0;
        }
        int[] iArr2 = {0, 0, 0};
        GMTOffsetPatternType[] gMTOffsetPatternTypeArr = PARSE_GMT_OFFSET_TYPES;
        int length = gMTOffsetPatternTypeArr.length;
        int offsetFieldsWithPattern = 0;
        int i8 = 0;
        while (true) {
            i2 = -1;
            if (i8 >= length) {
                i3 = offsetFieldsWithPattern;
                i4 = 1;
                i5 = 0;
                i6 = 0;
                i7 = 0;
                break;
            }
            GMTOffsetPatternType gMTOffsetPatternType = gMTOffsetPatternTypeArr[i8];
            offsetFieldsWithPattern = parseOffsetFieldsWithPattern(str, i, this._gmtOffsetPatternItems[gMTOffsetPatternType.ordinal()], false, iArr2);
            if (offsetFieldsWithPattern > 0) {
                int i9 = gMTOffsetPatternType.isPositive() ? 1 : -1;
                i3 = offsetFieldsWithPattern;
                i4 = i9;
                i5 = iArr2[0];
                i6 = iArr2[1];
                i7 = iArr2[2];
            } else {
                i8++;
            }
        }
        if (i3 > 0 && this._abuttingOffsetHoursAndMinutes) {
            GMTOffsetPatternType[] gMTOffsetPatternTypeArr2 = PARSE_GMT_OFFSET_TYPES;
            int length2 = gMTOffsetPatternTypeArr2.length;
            int offsetFieldsWithPattern2 = 0;
            int i10 = 0;
            while (true) {
                if (i10 >= length2) {
                    break;
                }
                GMTOffsetPatternType gMTOffsetPatternType2 = gMTOffsetPatternTypeArr2[i10];
                int i11 = i10;
                int i12 = length2;
                GMTOffsetPatternType[] gMTOffsetPatternTypeArr3 = gMTOffsetPatternTypeArr2;
                offsetFieldsWithPattern2 = parseOffsetFieldsWithPattern(str, i, this._gmtOffsetPatternItems[gMTOffsetPatternType2.ordinal()], true, iArr2);
                if (offsetFieldsWithPattern2 <= 0) {
                    i10 = i11 + 1;
                    length2 = i12;
                    gMTOffsetPatternTypeArr2 = gMTOffsetPatternTypeArr3;
                } else if (gMTOffsetPatternType2.isPositive()) {
                    break;
                }
            }
            i2 = 1;
            if (offsetFieldsWithPattern2 > i3) {
                i5 = iArr2[0];
                i6 = iArr2[1];
                i7 = iArr2[2];
                i3 = offsetFieldsWithPattern2;
                i4 = i2;
            }
        }
        if (iArr != null && iArr.length >= 1) {
            iArr[0] = i3;
        }
        if (i3 > 0) {
            return ((((i5 * 60) + i6) * 60) + i7) * 1000 * i4;
        }
        return 0;
    }

    /* JADX WARN: Removed duplicated region for block: B:24:0x0065  */
    /* JADX WARN: Removed duplicated region for block: B:48:0x0063 A[SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    private int parseOffsetFieldsWithPattern(java.lang.String r19, int r20, java.lang.Object[] r21, boolean r22, int[] r23) {
        /*
            Method dump skipped, instructions count: 201
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ibm.icu.text.TimeZoneFormat.parseOffsetFieldsWithPattern(java.lang.String, int, java.lang.Object[], boolean, int[]):int");
    }

    private int parseOffsetDefaultLocalizedGMT(String str, int i, int[] iArr) {
        int length;
        int i2;
        int i3;
        int i4;
        int i5;
        int i6;
        int i7;
        String[] strArr = ALT_GMT_STRINGS;
        int length2 = strArr.length;
        int i8 = 0;
        while (true) {
            if (i8 >= length2) {
                length = 0;
                break;
            }
            String str2 = strArr[i8];
            length = str2.length();
            if (str.regionMatches(true, i, str2, 0, length)) {
                break;
            }
            i8++;
        }
        if (length != 0 && (i5 = (i4 = length + i) + 1) < str.length()) {
            char cCharAt = str.charAt(i4);
            if (cCharAt != '+') {
                i6 = cCharAt == '-' ? -1 : 1;
                i2 = 0;
                i3 = 0;
            }
            int[] iArr2 = {0};
            int defaultOffsetFields = parseDefaultOffsetFields(str, i5, DEFAULT_GMT_OFFSET_SEP, iArr2);
            if (iArr2[0] == str.length() - i5) {
                i3 = defaultOffsetFields * i6;
                i7 = iArr2[0];
            } else {
                int[] iArr3 = {0};
                int abuttingOffsetFields = parseAbuttingOffsetFields(str, i5, iArr3);
                if (iArr2[0] > iArr3[0]) {
                    i3 = defaultOffsetFields * i6;
                    i7 = iArr2[0];
                } else {
                    i3 = abuttingOffsetFields * i6;
                    i7 = iArr3[0];
                }
            }
            i2 = (i5 + i7) - i;
        } else {
            i2 = 0;
            i3 = 0;
        }
        iArr[0] = i2;
        return i3;
    }

    /* JADX WARN: Removed duplicated region for block: B:25:0x0082  */
    /* JADX WARN: Removed duplicated region for block: B:27:0x0085  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    private int parseDefaultOffsetFields(java.lang.String r19, int r20, char r21, int[] r22) {
        /*
            r18 = this;
            r8 = r19
            r9 = r20
            r10 = r21
            int r11 = r19.length()
            r12 = 1
            int[] r13 = new int[r12]
            r14 = 0
            r13[r14] = r14
            r3 = 1
            r4 = 2
            r5 = 0
            r6 = 23
            r0 = r18
            r1 = r19
            r2 = r20
            r7 = r13
            int r15 = r0.parseOffsetFieldWithLocalizedDigits(r1, r2, r3, r4, r5, r6, r7)
            r0 = r13[r14]
            if (r0 != 0) goto L26
            r7 = r9
            goto L7d
        L26:
            r0 = r13[r14]
            int r7 = r9 + r0
            int r2 = r7 + 1
            if (r2 >= r11) goto L79
            char r0 = r8.charAt(r7)
            if (r0 != r10) goto L79
            r3 = 2
            r4 = 2
            r5 = 0
            r6 = 59
            r0 = r18
            r1 = r19
            r16 = r7
            r7 = r13
            int r17 = r0.parseOffsetFieldWithLocalizedDigits(r1, r2, r3, r4, r5, r6, r7)
            r0 = r13[r14]
            if (r0 != 0) goto L4b
            r7 = r16
            goto L77
        L4b:
            r0 = r13[r14]
            int r0 = r0 + r12
            int r7 = r16 + r0
            int r2 = r7 + 1
            if (r2 >= r11) goto L75
            char r0 = r8.charAt(r7)
            if (r0 != r10) goto L75
            r3 = 2
            r4 = 2
            r5 = 0
            r6 = 59
            r0 = r18
            r1 = r19
            r8 = r7
            r7 = r13
            int r0 = r0.parseOffsetFieldWithLocalizedDigits(r1, r2, r3, r4, r5, r6, r7)
            r1 = r13[r14]
            if (r1 != 0) goto L6f
            r7 = r8
            goto L80
        L6f:
            r1 = r13[r14]
            int r1 = r1 + r12
            int r7 = r8 + r1
            goto L80
        L75:
            r8 = r7
            r7 = r8
        L77:
            r0 = 0
            goto L80
        L79:
            r16 = r7
            r7 = r16
        L7d:
            r0 = 0
            r17 = 0
        L80:
            if (r7 != r9) goto L85
            r22[r14] = r14
            return r14
        L85:
            int r7 = r7 - r9
            r22[r14] = r7
            r1 = 3600000(0x36ee80, float:5.044674E-39)
            int r15 = r15 * r1
            r1 = 60000(0xea60, float:8.4078E-41)
            int r17 = r17 * r1
            int r15 = r15 + r17
            int r0 = r0 * 1000
            int r15 = r15 + r0
            return r15
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ibm.icu.text.TimeZoneFormat.parseDefaultOffsetFields(java.lang.String, int, char, int[]):int");
    }

    private int parseAbuttingOffsetFields(String str, int i, int[] iArr) {
        int i2;
        int i3;
        int i4;
        int i5;
        int i6;
        int[] iArr2 = new int[6];
        int[] iArr3 = new int[6];
        int[] iArr4 = {0};
        int i7 = i;
        int i8 = 0;
        for (int i9 = 0; i9 < 6; i9++) {
            iArr2[i9] = parseSingleLocalizedDigit(str, i7, iArr4);
            if (iArr2[i9] < 0) {
                break;
            }
            i7 += iArr4[0];
            iArr3[i9] = i7 - i;
            i8++;
        }
        if (i8 == 0) {
            iArr[0] = 0;
            return 0;
        }
        while (i8 > 0) {
            switch (i8) {
                case 1:
                    i2 = iArr2[0];
                    i3 = 0;
                    i4 = 0;
                    break;
                case 2:
                    i2 = (iArr2[0] * 10) + iArr2[1];
                    i3 = 0;
                    i4 = 0;
                    break;
                case 3:
                    i2 = iArr2[0];
                    i5 = iArr2[1] * 10;
                    i6 = iArr2[2];
                    i4 = i5 + i6;
                    i3 = 0;
                    break;
                case 4:
                    i2 = iArr2[1] + (iArr2[0] * 10);
                    i5 = iArr2[2] * 10;
                    i6 = iArr2[3];
                    i4 = i5 + i6;
                    i3 = 0;
                    break;
                case 5:
                    i2 = iArr2[0];
                    i4 = iArr2[2] + (iArr2[1] * 10);
                    i3 = iArr2[4] + (iArr2[3] * 10);
                    break;
                case 6:
                    i2 = (iArr2[0] * 10) + iArr2[1];
                    i4 = (iArr2[2] * 10) + iArr2[3];
                    i3 = (iArr2[4] * 10) + iArr2[5];
                    break;
                default:
                    i3 = 0;
                    i4 = 0;
                    i2 = 0;
                    break;
            }
            if (i2 <= 23 && i4 <= 59 && i3 <= 59) {
                int i10 = (i2 * 3600000) + (i4 * 60000) + (i3 * 1000);
                iArr[0] = iArr3[i8 - 1];
                return i10;
            }
            i8--;
        }
        return 0;
    }

    private int parseOffsetFieldWithLocalizedDigits(String str, int i, int i2, int i3, int i4, int i5, int[] iArr) {
        int singleLocalizedDigit;
        int i6;
        iArr[0] = 0;
        int[] iArr2 = {0};
        int i7 = i;
        int i8 = 0;
        int i9 = 0;
        while (i7 < str.length() && i8 < i3 && (singleLocalizedDigit = parseSingleLocalizedDigit(str, i7, iArr2)) >= 0 && (i6 = (i9 * 10) + singleLocalizedDigit) <= i5) {
            i8++;
            i7 += iArr2[0];
            i9 = i6;
        }
        if (i8 < i2 || i9 < i4) {
            return -1;
        }
        iArr[0] = i7 - i;
        return i9;
    }

    private int parseSingleLocalizedDigit(String str, int i, int[] iArr) {
        iArr[0] = 0;
        int iDigit = -1;
        if (i < str.length()) {
            int iCodePointAt = Character.codePointAt(str, i);
            int i2 = 0;
            while (true) {
                String[] strArr = this._gmtOffsetDigits;
                if (i2 >= strArr.length) {
                    break;
                }
                if (iCodePointAt == strArr[i2].codePointAt(0)) {
                    iDigit = i2;
                    break;
                }
                i2++;
            }
            if (iDigit < 0) {
                iDigit = UCharacter.digit(iCodePointAt);
            }
            if (iDigit >= 0) {
                iArr[0] = Character.charCount(iCodePointAt);
            }
        }
        return iDigit;
    }

    private static String[] toCodePoints(String str) {
        int i = 0;
        int iCodePointCount = str.codePointCount(0, str.length());
        String[] strArr = new String[iCodePointCount];
        int i2 = 0;
        while (i < iCodePointCount) {
            int iCharCount = Character.charCount(str.codePointAt(i2)) + i2;
            strArr[i] = str.substring(i2, iCharCount);
            i++;
            i2 = iCharCount;
        }
        return strArr;
    }

    /* JADX WARN: Type inference failed for: r10v3, types: [T, java.lang.Boolean] */
    /* JADX WARN: Type inference failed for: r1v2, types: [T, java.lang.Boolean] */
    private static int parseOffsetISO8601(String str, ParsePosition parsePosition, boolean z, Output<Boolean> output) {
        int i;
        if (output != null) {
            output.value = false;
        }
        int index = parsePosition.getIndex();
        if (index >= str.length()) {
            parsePosition.setErrorIndex(index);
            return 0;
        }
        char cCharAt = str.charAt(index);
        if (Character.toUpperCase(cCharAt) == ISO8601_UTC.charAt(0)) {
            parsePosition.setIndex(index + 1);
            return 0;
        }
        if (cCharAt == '+') {
            i = 1;
        } else {
            if (cCharAt != '-') {
                parsePosition.setErrorIndex(index);
                return 0;
            }
            i = -1;
        }
        int i2 = index + 1;
        ParsePosition parsePosition2 = new ParsePosition(i2);
        int asciiOffsetFields = parseAsciiOffsetFields(str, parsePosition2, DEFAULT_GMT_OFFSET_SEP, OffsetFields.H, OffsetFields.HMS);
        if (parsePosition2.getErrorIndex() == -1 && !z && parsePosition2.getIndex() - index <= 3) {
            ParsePosition parsePosition3 = new ParsePosition(i2);
            int abuttingAsciiOffsetFields = parseAbuttingAsciiOffsetFields(str, parsePosition3, OffsetFields.H, OffsetFields.HMS, false);
            if (parsePosition3.getErrorIndex() == -1 && parsePosition3.getIndex() > parsePosition2.getIndex()) {
                parsePosition2.setIndex(parsePosition3.getIndex());
                asciiOffsetFields = abuttingAsciiOffsetFields;
            }
        }
        if (parsePosition2.getErrorIndex() != -1) {
            parsePosition.setErrorIndex(index);
            return 0;
        }
        parsePosition.setIndex(parsePosition2.getIndex());
        if (output != null) {
            output.value = true;
        }
        return i * asciiOffsetFields;
    }

    private static int parseAbuttingAsciiOffsetFields(String str, ParsePosition parsePosition, OffsetFields offsetFields, OffsetFields offsetFields2, boolean z) {
        int i;
        int i2;
        int i3;
        int i4;
        int i5;
        int index = parsePosition.getIndex();
        boolean z2 = true;
        int iOrdinal = ((offsetFields.ordinal() + 1) * 2) - (!z ? 1 : 0);
        int iOrdinal2 = (offsetFields2.ordinal() + 1) * 2;
        int[] iArr = new int[iOrdinal2];
        int i6 = 0;
        for (int i7 = index; i6 < iOrdinal2 && i7 < str.length(); i7++) {
            int iIndexOf = ASCII_DIGITS.indexOf(str.charAt(i7));
            if (iIndexOf < 0) {
                break;
            }
            iArr[i6] = iIndexOf;
            i6++;
        }
        if (z && (i6 & 1) != 0) {
            i6--;
        }
        if (i6 < iOrdinal) {
            parsePosition.setErrorIndex(index);
            return 0;
        }
        while (true) {
            if (i6 >= iOrdinal) {
                switch (i6) {
                    case 1:
                        i2 = iArr[0];
                        i = 0;
                        i3 = 0;
                        break;
                    case 2:
                        i2 = (iArr[0] * 10) + iArr[1];
                        i = 0;
                        i3 = 0;
                        break;
                    case 3:
                        i2 = iArr[0];
                        i4 = iArr[1] * 10;
                        i5 = iArr[2];
                        i3 = i4 + i5;
                        i = 0;
                        break;
                    case 4:
                        i2 = iArr[1] + (iArr[0] * 10);
                        i4 = iArr[2] * 10;
                        i5 = iArr[3];
                        i3 = i4 + i5;
                        i = 0;
                        break;
                    case 5:
                        i2 = iArr[0];
                        i3 = (iArr[1] * 10) + iArr[2];
                        i = iArr[4] + (iArr[3] * 10);
                        break;
                    case 6:
                        i2 = (iArr[0] * 10) + iArr[1];
                        i3 = (iArr[2] * 10) + iArr[3];
                        i = (iArr[4] * 10) + iArr[5];
                        break;
                    default:
                        i = 0;
                        i2 = 0;
                        i3 = 0;
                        break;
                }
                if (i2 > 23 || i3 > 59 || i > 59) {
                    i6 -= z ? 2 : 1;
                }
            } else {
                i = 0;
                z2 = false;
                i2 = 0;
                i3 = 0;
            }
        }
        if (!z2) {
            parsePosition.setErrorIndex(index);
            return 0;
        }
        parsePosition.setIndex(index + i6);
        return ((((i2 * 60) + i3) * 60) + i) * 1000;
    }

    /* JADX WARN: Removed duplicated region for block: B:45:0x00b1 A[PHI: r5 r6 r7
      0x00b1: PHI (r5v6 int) = (r5v5 int), (r5v5 int), (r5v8 int), (r5v8 int) binds: [B:35:0x0083, B:37:0x0089, B:40:0x009c, B:42:0x00a0] A[DONT_GENERATE, DONT_INLINE]
      0x00b1: PHI (r6v5 int) = (r6v4 int), (r6v4 int), (r6v6 int), (r6v6 int) binds: [B:35:0x0083, B:37:0x0089, B:40:0x009c, B:42:0x00a0] A[DONT_GENERATE, DONT_INLINE]
      0x00b1: PHI (r7v3 com.ibm.icu.text.TimeZoneFormat$OffsetFields) = 
      (r7v2 com.ibm.icu.text.TimeZoneFormat$OffsetFields)
      (r7v2 com.ibm.icu.text.TimeZoneFormat$OffsetFields)
      (r7v8 com.ibm.icu.text.TimeZoneFormat$OffsetFields)
      (r7v8 com.ibm.icu.text.TimeZoneFormat$OffsetFields)
     binds: [B:35:0x0083, B:37:0x0089, B:40:0x009c, B:42:0x00a0] A[DONT_GENERATE, DONT_INLINE]] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    private static int parseAsciiOffsetFields(java.lang.String r14, java.text.ParsePosition r15, char r16, com.ibm.icu.text.TimeZoneFormat.OffsetFields r17, com.ibm.icu.text.TimeZoneFormat.OffsetFields r18) {
        /*
            Method dump skipped, instructions count: 222
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ibm.icu.text.TimeZoneFormat.parseAsciiOffsetFields(java.lang.String, java.text.ParsePosition, char, com.ibm.icu.text.TimeZoneFormat$OffsetFields, com.ibm.icu.text.TimeZoneFormat$OffsetFields):int");
    }

    private static String parseZoneID(String str, ParsePosition parsePosition) {
        if (ZONE_ID_TRIE == null) {
            synchronized (TimeZoneFormat.class) {
                if (ZONE_ID_TRIE == null) {
                    TextTrieMap<String> textTrieMap = new TextTrieMap<>(true);
                    for (String str2 : TimeZone.getAvailableIDs()) {
                        textTrieMap.put(str2, str2);
                    }
                    ZONE_ID_TRIE = textTrieMap;
                }
            }
        }
        TextTrieMap.Output output = new TextTrieMap.Output();
        Iterator<String> it2 = ZONE_ID_TRIE.get(str, parsePosition.getIndex(), output);
        if (it2 != null) {
            String next = it2.next();
            parsePosition.setIndex(parsePosition.getIndex() + output.matchLength);
            return next;
        }
        parsePosition.setErrorIndex(parsePosition.getIndex());
        return null;
    }

    private static String parseShortZoneID(String str, ParsePosition parsePosition) {
        if (SHORT_ZONE_ID_TRIE == null) {
            synchronized (TimeZoneFormat.class) {
                if (SHORT_ZONE_ID_TRIE == null) {
                    TextTrieMap<String> textTrieMap = new TextTrieMap<>(true);
                    for (String str2 : TimeZone.getAvailableIDs(TimeZone.SystemTimeZoneType.CANONICAL, null, null)) {
                        String shortID = ZoneMeta.getShortID(str2);
                        if (shortID != null) {
                            textTrieMap.put(shortID, str2);
                        }
                    }
                    textTrieMap.put(UNKNOWN_SHORT_ZONE_ID, "Etc/Unknown");
                    SHORT_ZONE_ID_TRIE = textTrieMap;
                }
            }
        }
        TextTrieMap.Output output = new TextTrieMap.Output();
        Iterator<String> it2 = SHORT_ZONE_ID_TRIE.get(str, parsePosition.getIndex(), output);
        if (it2 != null) {
            String next = it2.next();
            parsePosition.setIndex(parsePosition.getIndex() + output.matchLength);
            return next;
        }
        parsePosition.setErrorIndex(parsePosition.getIndex());
        return null;
    }

    private String parseExemplarLocation(String str, ParsePosition parsePosition) {
        int index = parsePosition.getIndex();
        Collection<TimeZoneNames.MatchInfo> collectionFind = this._tznames.find(str, index, EnumSet.of(TimeZoneNames.NameType.EXEMPLAR_LOCATION));
        String timeZoneID = null;
        if (collectionFind != null) {
            int iMatchLength = -1;
            TimeZoneNames.MatchInfo matchInfo = null;
            for (TimeZoneNames.MatchInfo matchInfo2 : collectionFind) {
                if (matchInfo2.matchLength() + index > iMatchLength) {
                    iMatchLength = matchInfo2.matchLength() + index;
                    matchInfo = matchInfo2;
                }
            }
            if (matchInfo != null) {
                timeZoneID = getTimeZoneID(matchInfo.tzID(), matchInfo.mzID());
                parsePosition.setIndex(iMatchLength);
            }
        }
        if (timeZoneID == null) {
            parsePosition.setErrorIndex(index);
        }
        return timeZoneID;
    }

    private static class TimeZoneFormatCache extends SoftCache<ULocale, TimeZoneFormat, ULocale> {
        private TimeZoneFormatCache() {
        }

        /* synthetic */ TimeZoneFormatCache(AnonymousClass1 anonymousClass1) {
            this();
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.ibm.icu.impl.CacheBase
        public TimeZoneFormat createInstance(ULocale uLocale, ULocale uLocale2) {
            TimeZoneFormat timeZoneFormat = new TimeZoneFormat(uLocale2);
            timeZoneFormat.freeze();
            return timeZoneFormat;
        }
    }

    private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
        ObjectOutputStream.PutField putFieldPutFields = objectOutputStream.putFields();
        putFieldPutFields.put("_locale", this._locale);
        putFieldPutFields.put("_tznames", this._tznames);
        putFieldPutFields.put("_gmtPattern", this._gmtPattern);
        putFieldPutFields.put("_gmtOffsetPatterns", this._gmtOffsetPatterns);
        putFieldPutFields.put("_gmtOffsetDigits", this._gmtOffsetDigits);
        putFieldPutFields.put("_gmtZeroFormat", this._gmtZeroFormat);
        putFieldPutFields.put("_parseAllStyles", this._parseAllStyles);
        objectOutputStream.writeFields();
    }

    private void readObject(ObjectInputStream objectInputStream) throws ClassNotFoundException, IOException {
        ObjectInputStream.GetField fields = objectInputStream.readFields();
        ULocale uLocale = (ULocale) fields.get("_locale", (Object) null);
        this._locale = uLocale;
        if (uLocale == null) {
            throw new InvalidObjectException("Missing field: locale");
        }
        TimeZoneNames timeZoneNames = (TimeZoneNames) fields.get("_tznames", (Object) null);
        this._tznames = timeZoneNames;
        if (timeZoneNames == null) {
            throw new InvalidObjectException("Missing field: tznames");
        }
        String str = (String) fields.get("_gmtPattern", (Object) null);
        this._gmtPattern = str;
        if (str == null) {
            throw new InvalidObjectException("Missing field: gmtPattern");
        }
        String[] strArr = (String[]) fields.get("_gmtOffsetPatterns", (Object) null);
        if (strArr == null) {
            throw new InvalidObjectException("Missing field: gmtOffsetPatterns");
        }
        if (strArr.length < 4) {
            throw new InvalidObjectException("Incompatible field: gmtOffsetPatterns");
        }
        this._gmtOffsetPatterns = new String[6];
        if (strArr.length == 4) {
            for (int i = 0; i < 4; i++) {
                this._gmtOffsetPatterns[i] = strArr[i];
            }
            this._gmtOffsetPatterns[GMTOffsetPatternType.POSITIVE_H.ordinal()] = truncateOffsetPattern(this._gmtOffsetPatterns[GMTOffsetPatternType.POSITIVE_HM.ordinal()]);
            this._gmtOffsetPatterns[GMTOffsetPatternType.NEGATIVE_H.ordinal()] = truncateOffsetPattern(this._gmtOffsetPatterns[GMTOffsetPatternType.NEGATIVE_HM.ordinal()]);
        } else {
            this._gmtOffsetPatterns = strArr;
        }
        String[] strArr2 = (String[]) fields.get("_gmtOffsetDigits", (Object) null);
        this._gmtOffsetDigits = strArr2;
        if (strArr2 == null) {
            throw new InvalidObjectException("Missing field: gmtOffsetDigits");
        }
        if (strArr2.length != 10) {
            throw new InvalidObjectException("Incompatible field: gmtOffsetDigits");
        }
        String str2 = (String) fields.get("_gmtZeroFormat", (Object) null);
        this._gmtZeroFormat = str2;
        if (str2 == null) {
            throw new InvalidObjectException("Missing field: gmtZeroFormat");
        }
        this._parseAllStyles = fields.get("_parseAllStyles", false);
        if (fields.defaulted("_parseAllStyles")) {
            throw new InvalidObjectException("Missing field: parseAllStyles");
        }
        TimeZoneNames timeZoneNames2 = this._tznames;
        if (timeZoneNames2 instanceof TimeZoneNamesImpl) {
            this._tznames = TimeZoneNames.getInstance(this._locale);
            this._gnames = null;
        } else {
            this._gnames = new TimeZoneGenericNames(this._locale, timeZoneNames2);
        }
        initGMTPattern(this._gmtPattern);
        initGMTOffsetPatterns(this._gmtOffsetPatterns);
    }

    @Override // com.ibm.icu.util.Freezable
    public boolean isFrozen() {
        return this._frozen;
    }

    @Override // com.ibm.icu.util.Freezable
    public TimeZoneFormat freeze() {
        this._frozen = true;
        return this;
    }

    @Override // com.ibm.icu.util.Freezable
    public TimeZoneFormat cloneAsThawed() {
        TimeZoneFormat timeZoneFormat = (TimeZoneFormat) super.clone();
        timeZoneFormat._frozen = false;
        return timeZoneFormat;
    }
}
