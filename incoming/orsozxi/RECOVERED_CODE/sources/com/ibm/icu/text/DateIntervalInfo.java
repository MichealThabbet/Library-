package com.ibm.icu.text;

import com.ibm.icu.impl.ICUCache;
import com.ibm.icu.impl.ICUData;
import com.ibm.icu.impl.ICUResourceBundle;
import com.ibm.icu.impl.SimpleCache;
import com.ibm.icu.impl.UResource;
import com.ibm.icu.text.DateIntervalFormat;
import com.ibm.icu.util.Calendar;
import com.ibm.icu.util.Freezable;
import com.ibm.icu.util.ICUCloneNotSupportedException;
import com.ibm.icu.util.ICUException;
import com.ibm.icu.util.ULocale;
import com.ibm.icu.util.UResourceBundle;
import java.io.Serializable;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Locale;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.Objects;
import java.util.Set;

/* loaded from: classes2.dex */
public class DateIntervalInfo implements Cloneable, Freezable<DateIntervalInfo>, Serializable {
    private static final int MINIMUM_SUPPORTED_CALENDAR_FIELD = 14;
    static final int currentSerialVersion = 1;
    private static final long serialVersionUID = 1;
    private String fFallbackIntervalPattern;
    private boolean fFirstDateInPtnIsLaterDate;
    private Map<String, Map<String, PatternInfo>> fIntervalPatterns;
    private transient boolean fIntervalPatternsReadOnly;
    private volatile transient boolean frozen;
    static final String[] CALENDAR_FIELD_TO_PATTERN_LETTER = {"G", DateFormat.YEAR, DateFormat.NUM_MONTH, "w", "W", DateFormat.DAY, "D", DateFormat.ABBR_WEEKDAY, "F", "a", "h", DateFormat.HOUR24, DateFormat.MINUTE, DateFormat.SECOND, "S", DateFormat.ABBR_SPECIFIC_TZ, " ", "Y", "e", "u", "g", "A", " ", " "};
    private static String CALENDAR_KEY = "calendar";
    private static String INTERVAL_FORMATS_KEY = "intervalFormats";
    private static String FALLBACK_STRING = "fallback";
    private static String LATEST_FIRST_PREFIX = "latestFirst:";
    private static String EARLIEST_FIRST_PREFIX = "earliestFirst:";
    private static final ICUCache<String, DateIntervalInfo> DIICACHE = new SimpleCache();

    private static boolean stringNumeric(int i, int i2, char c) {
        if (c != 'M') {
            return false;
        }
        if (i > 2 || i2 <= 2) {
            return i > 2 && i2 <= 2;
        }
        return true;
    }

    public static final class PatternInfo implements Cloneable, Serializable {
        static final int currentSerialVersion = 1;
        private static final long serialVersionUID = 1;
        private final boolean fFirstDateInPtnIsLaterDate;
        private final String fIntervalPatternFirstPart;
        private final String fIntervalPatternSecondPart;

        public PatternInfo(String str, String str2, boolean z) {
            this.fIntervalPatternFirstPart = str;
            this.fIntervalPatternSecondPart = str2;
            this.fFirstDateInPtnIsLaterDate = z;
        }

        public String getFirstPart() {
            return this.fIntervalPatternFirstPart;
        }

        public String getSecondPart() {
            return this.fIntervalPatternSecondPart;
        }

        public boolean firstDateInPtnIsLaterDate() {
            return this.fFirstDateInPtnIsLaterDate;
        }

        public boolean equals(Object obj) {
            if (!(obj instanceof PatternInfo)) {
                return false;
            }
            PatternInfo patternInfo = (PatternInfo) obj;
            return Objects.equals(this.fIntervalPatternFirstPart, patternInfo.fIntervalPatternFirstPart) && Objects.equals(this.fIntervalPatternSecondPart, patternInfo.fIntervalPatternSecondPart) && this.fFirstDateInPtnIsLaterDate == patternInfo.fFirstDateInPtnIsLaterDate;
        }

        public int hashCode() {
            String str = this.fIntervalPatternFirstPart;
            int iHashCode = str != null ? str.hashCode() : 0;
            String str2 = this.fIntervalPatternSecondPart;
            if (str2 != null) {
                iHashCode ^= str2.hashCode();
            }
            return this.fFirstDateInPtnIsLaterDate ? ~iHashCode : iHashCode;
        }

        public String toString() {
            return "{first=«" + this.fIntervalPatternFirstPart + "», second=«" + this.fIntervalPatternSecondPart + "», reversed:" + this.fFirstDateInPtnIsLaterDate + "}";
        }
    }

    @Deprecated
    public DateIntervalInfo() {
        this.fFirstDateInPtnIsLaterDate = false;
        this.fIntervalPatterns = null;
        this.frozen = false;
        this.fIntervalPatternsReadOnly = false;
        this.fIntervalPatterns = new HashMap();
        this.fFallbackIntervalPattern = "{0} – {1}";
    }

    public DateIntervalInfo(ULocale uLocale) {
        this.fFirstDateInPtnIsLaterDate = false;
        this.fIntervalPatterns = null;
        this.frozen = false;
        this.fIntervalPatternsReadOnly = false;
        initializeData(uLocale);
    }

    public DateIntervalInfo(Locale locale) {
        this(ULocale.forLocale(locale));
    }

    private void initializeData(ULocale uLocale) {
        String string = uLocale.toString();
        DateIntervalInfo dateIntervalInfo = DIICACHE.get(string);
        if (dateIntervalInfo == null) {
            setup(uLocale);
            this.fIntervalPatternsReadOnly = true;
            DIICACHE.put(string, ((DateIntervalInfo) clone()).freeze());
            return;
        }
        initializeFromReadOnlyPatterns(dateIntervalInfo);
    }

    private void initializeFromReadOnlyPatterns(DateIntervalInfo dateIntervalInfo) {
        this.fFallbackIntervalPattern = dateIntervalInfo.fFallbackIntervalPattern;
        this.fFirstDateInPtnIsLaterDate = dateIntervalInfo.fFirstDateInPtnIsLaterDate;
        this.fIntervalPatterns = dateIntervalInfo.fIntervalPatterns;
        this.fIntervalPatternsReadOnly = true;
    }

    private static final class DateIntervalSink extends UResource.Sink {
        private static final String ACCEPTED_PATTERN_LETTERS = "GyMdahHmsS";
        private static final String DATE_INTERVAL_PATH_PREFIX = "/LOCALE/" + DateIntervalInfo.CALENDAR_KEY + "/";
        private static final String DATE_INTERVAL_PATH_SUFFIX;
        DateIntervalInfo dateIntervalInfo;
        String nextCalendarType;

        public DateIntervalSink(DateIntervalInfo dateIntervalInfo) {
            this.dateIntervalInfo = dateIntervalInfo;
        }

        @Override // com.ibm.icu.impl.UResource.Sink
        public void put(UResource.Key key, UResource.Value value, boolean z) {
            UResource.Table table = value.getTable();
            for (int i = 0; table.getKeyAndValue(i, key, value); i++) {
                if (key.contentEquals(DateIntervalInfo.INTERVAL_FORMATS_KEY)) {
                    if (value.getType() == 3) {
                        this.nextCalendarType = getCalendarTypeFromPath(value.getAliasString());
                        return;
                    } else if (value.getType() == 2) {
                        UResource.Table table2 = value.getTable();
                        for (int i2 = 0; table2.getKeyAndValue(i2, key, value); i2++) {
                            if (value.getType() == 2) {
                                processSkeletonTable(key, value);
                            }
                        }
                        return;
                    }
                }
            }
        }

        public void processSkeletonTable(UResource.Key key, UResource.Value value) {
            CharSequence charSequenceValidateAndProcessPatternLetter;
            String string = key.toString();
            UResource.Table table = value.getTable();
            for (int i = 0; table.getKeyAndValue(i, key, value); i++) {
                if (value.getType() == 0 && (charSequenceValidateAndProcessPatternLetter = validateAndProcessPatternLetter(key)) != null) {
                    setIntervalPatternIfAbsent(string, charSequenceValidateAndProcessPatternLetter.toString(), value);
                }
            }
        }

        public String getAndResetNextCalendarType() {
            String str = this.nextCalendarType;
            this.nextCalendarType = null;
            return str;
        }

        static {
            StringBuilder sb = new StringBuilder();
            sb.append("/");
            sb.append(DateIntervalInfo.INTERVAL_FORMATS_KEY);
            DATE_INTERVAL_PATH_SUFFIX = sb.toString();
        }

        private String getCalendarTypeFromPath(String str) {
            if (str.startsWith(DATE_INTERVAL_PATH_PREFIX) && str.endsWith(DATE_INTERVAL_PATH_SUFFIX)) {
                return str.substring(DATE_INTERVAL_PATH_PREFIX.length(), str.length() - DATE_INTERVAL_PATH_SUFFIX.length());
            }
            throw new ICUException("Malformed 'intervalFormat' alias path: " + str);
        }

        private CharSequence validateAndProcessPatternLetter(CharSequence charSequence) {
            if (charSequence.length() != 1) {
                return null;
            }
            char cCharAt = charSequence.charAt(0);
            if (ACCEPTED_PATTERN_LETTERS.indexOf(cCharAt) < 0 && cCharAt != 'B') {
                return null;
            }
            if (cCharAt == DateIntervalInfo.CALENDAR_FIELD_TO_PATTERN_LETTER[11].charAt(0)) {
                charSequence = DateIntervalInfo.CALENDAR_FIELD_TO_PATTERN_LETTER[10];
            }
            return cCharAt == 'B' ? DateIntervalInfo.CALENDAR_FIELD_TO_PATTERN_LETTER[9] : charSequence;
        }

        private void setIntervalPatternIfAbsent(String str, String str2, UResource.Value value) {
            Map map = (Map) this.dateIntervalInfo.fIntervalPatterns.get(str);
            if (map == null || !map.containsKey(str2)) {
                this.dateIntervalInfo.setIntervalPatternInternally(str, str2, value.toString());
            }
        }
    }

    private void setup(ULocale uLocale) {
        this.fIntervalPatterns = new HashMap(19);
        this.fFallbackIntervalPattern = "{0} – {1}";
        try {
            String keywordValue = uLocale.getKeywordValue("calendar");
            if (keywordValue == null) {
                keywordValue = Calendar.getKeywordValuesForLocale("calendar", uLocale, true)[0];
            }
            if (keywordValue == null) {
                keywordValue = "gregorian";
            }
            DateIntervalSink dateIntervalSink = new DateIntervalSink(this);
            ICUResourceBundle iCUResourceBundle = (ICUResourceBundle) UResourceBundle.getBundleInstance(ICUData.ICU_BASE_NAME, uLocale);
            setFallbackIntervalPattern(iCUResourceBundle.getStringWithFallback(CALENDAR_KEY + "/" + keywordValue + "/" + INTERVAL_FORMATS_KEY + "/" + FALLBACK_STRING));
            HashSet hashSet = new HashSet();
            while (keywordValue != null) {
                if (hashSet.contains(keywordValue)) {
                    throw new ICUException("Loop in calendar type fallback: " + keywordValue);
                }
                hashSet.add(keywordValue);
                iCUResourceBundle.getAllItemsWithFallback(CALENDAR_KEY + "/" + keywordValue, dateIntervalSink);
                keywordValue = dateIntervalSink.getAndResetNextCalendarType();
            }
        } catch (MissingResourceException unused) {
        }
    }

    private static int splitPatternInto2Part(String str) {
        boolean z;
        int[] iArr = new int[58];
        int i = 0;
        char c = 0;
        int i2 = 0;
        boolean z2 = false;
        while (true) {
            z = true;
            if (i >= str.length()) {
                z = false;
                break;
            }
            char cCharAt = str.charAt(i);
            if (cCharAt != c && i2 > 0) {
                int i3 = c - 'A';
                if (iArr[i3] != 0) {
                    break;
                }
                iArr[i3] = 1;
                i2 = 0;
            }
            if (cCharAt == '\'') {
                int i4 = i + 1;
                if (i4 >= str.length() || str.charAt(i4) != '\'') {
                    z2 = !z2;
                } else {
                    i = i4;
                }
            } else if (!z2 && ((cCharAt >= 'a' && cCharAt <= 'z') || (cCharAt >= 'A' && cCharAt <= 'Z'))) {
                i2++;
                c = cCharAt;
            }
            i++;
        }
        return i - ((i2 <= 0 || z || iArr[c - 'A'] != 0) ? i2 : 0);
    }

    public void setIntervalPattern(String str, int i, String str2) {
        if (this.frozen) {
            throw new UnsupportedOperationException("no modification is allowed after DII is frozen");
        }
        if (i > 14) {
            throw new IllegalArgumentException("calendar field is larger than MINIMUM_SUPPORTED_CALENDAR_FIELD");
        }
        if (this.fIntervalPatternsReadOnly) {
            this.fIntervalPatterns = cloneIntervalPatterns(this.fIntervalPatterns);
            this.fIntervalPatternsReadOnly = false;
        }
        PatternInfo intervalPatternInternally = setIntervalPatternInternally(str, CALENDAR_FIELD_TO_PATTERN_LETTER[i], str2);
        if (i == 11) {
            setIntervalPattern(str, CALENDAR_FIELD_TO_PATTERN_LETTER[9], intervalPatternInternally);
            setIntervalPattern(str, CALENDAR_FIELD_TO_PATTERN_LETTER[10], intervalPatternInternally);
        } else if (i == 5 || i == 7) {
            setIntervalPattern(str, CALENDAR_FIELD_TO_PATTERN_LETTER[5], intervalPatternInternally);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public PatternInfo setIntervalPatternInternally(String str, String str2, String str3) {
        boolean z;
        Map<String, PatternInfo> map = this.fIntervalPatterns.get(str);
        boolean z2 = false;
        if (map == null) {
            map = new HashMap<>();
            z = true;
        } else {
            z = false;
        }
        boolean z3 = this.fFirstDateInPtnIsLaterDate;
        if (str3.startsWith(LATEST_FIRST_PREFIX)) {
            str3 = str3.substring(LATEST_FIRST_PREFIX.length(), str3.length());
            z2 = true;
        } else if (str3.startsWith(EARLIEST_FIRST_PREFIX)) {
            str3 = str3.substring(EARLIEST_FIRST_PREFIX.length(), str3.length());
        } else {
            z2 = z3;
        }
        PatternInfo patternInfoGenPatternInfo = genPatternInfo(str3, z2);
        map.put(str2, patternInfoGenPatternInfo);
        if (z) {
            this.fIntervalPatterns.put(str, map);
        }
        return patternInfoGenPatternInfo;
    }

    private void setIntervalPattern(String str, String str2, PatternInfo patternInfo) {
        this.fIntervalPatterns.get(str).put(str2, patternInfo);
    }

    @Deprecated
    public static PatternInfo genPatternInfo(String str, boolean z) {
        int iSplitPatternInto2Part = splitPatternInto2Part(str);
        return new PatternInfo(str.substring(0, iSplitPatternInto2Part), iSplitPatternInto2Part < str.length() ? str.substring(iSplitPatternInto2Part, str.length()) : null, z);
    }

    public PatternInfo getIntervalPattern(String str, int i) {
        PatternInfo patternInfo;
        if (i > 14) {
            throw new IllegalArgumentException("no support for field less than MILLISECOND");
        }
        Map<String, PatternInfo> map = this.fIntervalPatterns.get(str);
        if (map == null || (patternInfo = map.get(CALENDAR_FIELD_TO_PATTERN_LETTER[i])) == null) {
            return null;
        }
        return patternInfo;
    }

    public String getFallbackIntervalPattern() {
        return this.fFallbackIntervalPattern;
    }

    public void setFallbackIntervalPattern(String str) {
        if (this.frozen) {
            throw new UnsupportedOperationException("no modification is allowed after DII is frozen");
        }
        int iIndexOf = str.indexOf("{0}");
        int iIndexOf2 = str.indexOf("{1}");
        if (iIndexOf == -1 || iIndexOf2 == -1) {
            throw new IllegalArgumentException("no pattern {0} or pattern {1} in fallbackPattern");
        }
        if (iIndexOf > iIndexOf2) {
            this.fFirstDateInPtnIsLaterDate = true;
        }
        this.fFallbackIntervalPattern = str;
    }

    public boolean getDefaultOrder() {
        return this.fFirstDateInPtnIsLaterDate;
    }

    public Object clone() {
        return this.frozen ? this : cloneUnfrozenDII();
    }

    private Object cloneUnfrozenDII() {
        try {
            DateIntervalInfo dateIntervalInfo = (DateIntervalInfo) super.clone();
            dateIntervalInfo.fFallbackIntervalPattern = this.fFallbackIntervalPattern;
            dateIntervalInfo.fFirstDateInPtnIsLaterDate = this.fFirstDateInPtnIsLaterDate;
            if (this.fIntervalPatternsReadOnly) {
                dateIntervalInfo.fIntervalPatterns = this.fIntervalPatterns;
                dateIntervalInfo.fIntervalPatternsReadOnly = true;
            } else {
                dateIntervalInfo.fIntervalPatterns = cloneIntervalPatterns(this.fIntervalPatterns);
                dateIntervalInfo.fIntervalPatternsReadOnly = false;
            }
            dateIntervalInfo.frozen = false;
            return dateIntervalInfo;
        } catch (CloneNotSupportedException e) {
            throw new ICUCloneNotSupportedException("clone is not supported", e);
        }
    }

    private static Map<String, Map<String, PatternInfo>> cloneIntervalPatterns(Map<String, Map<String, PatternInfo>> map) {
        HashMap map2 = new HashMap();
        for (Map.Entry<String, Map<String, PatternInfo>> entry : map.entrySet()) {
            String key = entry.getKey();
            Map<String, PatternInfo> value = entry.getValue();
            HashMap map3 = new HashMap();
            for (Map.Entry<String, PatternInfo> entry2 : value.entrySet()) {
                map3.put(entry2.getKey(), entry2.getValue());
            }
            map2.put(key, map3);
        }
        return map2;
    }

    @Override // com.ibm.icu.util.Freezable
    public boolean isFrozen() {
        return this.frozen;
    }

    @Override // com.ibm.icu.util.Freezable
    public DateIntervalInfo freeze() {
        this.fIntervalPatternsReadOnly = true;
        this.frozen = true;
        return this;
    }

    @Override // com.ibm.icu.util.Freezable
    public DateIntervalInfo cloneAsThawed() {
        return (DateIntervalInfo) cloneUnfrozenDII();
    }

    static void parseSkeleton(String str, int[] iArr) {
        for (int i = 0; i < str.length(); i++) {
            int iCharAt = str.charAt(i) - 'A';
            iArr[iCharAt] = iArr[iCharAt] + 1;
        }
    }

    DateIntervalFormat.BestMatchInfo getBestSkeleton(String str) {
        String strReplace;
        boolean z;
        int i;
        String str2 = str;
        int i2 = 58;
        int[] iArr = new int[58];
        int[] iArr2 = new int[58];
        int i3 = 0;
        if (str2.indexOf(122) == -1 && str2.indexOf(107) == -1 && str2.indexOf(75) == -1 && str2.indexOf(97) == -1 && str2.indexOf(98) == -1) {
            strReplace = str2;
            z = false;
        } else {
            strReplace = str2.replace('z', 'v').replace('k', 'H').replace('K', 'h').replace("a", "").replace("b", "");
            z = true;
        }
        parseSkeleton(strReplace, iArr);
        int i4 = Integer.MAX_VALUE;
        Iterator<String> it2 = this.fIntervalPatterns.keySet().iterator();
        int i5 = 0;
        while (true) {
            if (!it2.hasNext()) {
                i = i5;
                break;
            }
            String next = it2.next();
            for (int i6 = 0; i6 < i2; i6++) {
                iArr2[i6] = i3;
            }
            parseSkeleton(next, iArr2);
            int i7 = 0;
            int iAbs = 0;
            int i8 = 1;
            while (i7 < i2) {
                int i9 = iArr[i7];
                int i10 = iArr2[i7];
                if (i9 != i10) {
                    if (i9 == 0 || i10 == 0) {
                        iAbs += 4096;
                        i8 = -1;
                    } else {
                        iAbs = stringNumeric(i9, i10, (char) (i7 + 65)) ? iAbs + 256 : iAbs + Math.abs(i9 - i10);
                    }
                }
                i7++;
                i2 = 58;
            }
            if (iAbs < i4) {
                str2 = next;
                i4 = iAbs;
                i5 = i8;
            }
            if (iAbs == 0) {
                i = 0;
                break;
            }
            i2 = 58;
            i3 = 0;
        }
        if (z && i != -1) {
            i = 2;
        }
        return new DateIntervalFormat.BestMatchInfo(str2, i);
    }

    public boolean equals(Object obj) {
        if (obj instanceof DateIntervalInfo) {
            return this.fIntervalPatterns.equals(((DateIntervalInfo) obj).fIntervalPatterns);
        }
        return false;
    }

    public int hashCode() {
        return this.fIntervalPatterns.hashCode();
    }

    @Deprecated
    public Map<String, Set<String>> getPatterns() {
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        for (Map.Entry<String, Map<String, PatternInfo>> entry : this.fIntervalPatterns.entrySet()) {
            linkedHashMap.put(entry.getKey(), new LinkedHashSet(entry.getValue().keySet()));
        }
        return linkedHashMap;
    }

    @Deprecated
    public Map<String, Map<String, PatternInfo>> getRawPatterns() {
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        for (Map.Entry<String, Map<String, PatternInfo>> entry : this.fIntervalPatterns.entrySet()) {
            linkedHashMap.put(entry.getKey(), new LinkedHashMap(entry.getValue()));
        }
        return linkedHashMap;
    }
}
