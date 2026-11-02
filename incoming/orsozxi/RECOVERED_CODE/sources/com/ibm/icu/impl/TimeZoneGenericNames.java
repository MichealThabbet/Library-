package com.ibm.icu.impl;

import com.huawei.hms.framework.common.ContainerUtils;
import com.ibm.icu.impl.TextTrieMap;
import com.ibm.icu.impl.units.UnitsData;
import com.ibm.icu.text.LocaleDisplayNames;
import com.ibm.icu.text.TimeZoneFormat;
import com.ibm.icu.text.TimeZoneNames;
import com.ibm.icu.util.Freezable;
import com.ibm.icu.util.Output;
import com.ibm.icu.util.TimeZone;
import com.ibm.icu.util.ULocale;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.lang.ref.WeakReference;
import java.text.MessageFormat;
import java.util.Collection;
import java.util.EnumSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.MissingResourceException;
import java.util.concurrent.ConcurrentHashMap;

/* loaded from: classes2.dex */
public class TimeZoneGenericNames implements Serializable, Freezable<TimeZoneGenericNames> {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final long DST_CHECK_RANGE = 15897600000L;
    private static Cache GENERIC_NAMES_CACHE = new Cache(null);
    private static final TimeZoneNames.NameType[] GENERIC_NON_LOCATION_TYPES = {TimeZoneNames.NameType.LONG_GENERIC, TimeZoneNames.NameType.SHORT_GENERIC};
    private static final long serialVersionUID = 2729910342063468417L;
    private volatile transient boolean _frozen;
    private transient ConcurrentHashMap<String, String> _genericLocationNamesMap;
    private transient ConcurrentHashMap<String, String> _genericPartialLocationNamesMap;
    private transient TextTrieMap<NameInfo> _gnamesTrie;
    private transient boolean _gnamesTrieFullyLoaded;
    private final ULocale _locale;
    private transient WeakReference<LocaleDisplayNames> _localeDisplayNamesRef;
    private transient MessageFormat[] _patternFormatters;
    private transient String _region;
    private TimeZoneNames _tznames;

    /* synthetic */ TimeZoneGenericNames(ULocale uLocale, AnonymousClass1 anonymousClass1) {
        this(uLocale);
    }

    public enum GenericNameType {
        LOCATION("LONG", "SHORT"),
        LONG(new String[0]),
        SHORT(new String[0]);

        String[] _fallbackTypeOf;

        GenericNameType(String... strArr) {
            this._fallbackTypeOf = strArr;
        }

        public boolean isFallbackTypeOf(GenericNameType genericNameType) {
            String string = genericNameType.toString();
            for (String str : this._fallbackTypeOf) {
                if (str.equals(string)) {
                    return true;
                }
            }
            return false;
        }
    }

    public enum Pattern {
        REGION_FORMAT("regionFormat", "({0})"),
        FALLBACK_FORMAT("fallbackFormat", "{1} ({0})");

        String _defaultVal;
        String _key;

        Pattern(String str, String str2) {
            this._key = str;
            this._defaultVal = str2;
        }

        String key() {
            return this._key;
        }

        String defaultValue() {
            return this._defaultVal;
        }
    }

    public TimeZoneGenericNames(ULocale uLocale, TimeZoneNames timeZoneNames) {
        this._locale = uLocale;
        this._tznames = timeZoneNames;
        init();
    }

    private void init() {
        if (this._tznames == null) {
            this._tznames = TimeZoneNames.getInstance(this._locale);
        }
        this._genericLocationNamesMap = new ConcurrentHashMap<>();
        this._genericPartialLocationNamesMap = new ConcurrentHashMap<>();
        this._gnamesTrie = new TextTrieMap<>(true);
        this._gnamesTrieFullyLoaded = false;
        String canonicalCLDRID = ZoneMeta.getCanonicalCLDRID(TimeZone.getDefault());
        if (canonicalCLDRID != null) {
            loadStrings(canonicalCLDRID);
        }
    }

    private TimeZoneGenericNames(ULocale uLocale) {
        this(uLocale, (TimeZoneNames) null);
    }

    public static TimeZoneGenericNames getInstance(ULocale uLocale) {
        return GENERIC_NAMES_CACHE.getInstance(uLocale.getBaseName(), uLocale);
    }

    public String getDisplayName(TimeZone timeZone, GenericNameType genericNameType, long j) {
        String canonicalCLDRID;
        int i = AnonymousClass1.$SwitchMap$com$ibm$icu$impl$TimeZoneGenericNames$GenericNameType[genericNameType.ordinal()];
        if (i == 1) {
            String canonicalCLDRID2 = ZoneMeta.getCanonicalCLDRID(timeZone);
            if (canonicalCLDRID2 != null) {
                return getGenericLocationName(canonicalCLDRID2);
            }
        } else if (i == 2 || i == 3) {
            String genericNonLocationName = formatGenericNonLocationName(timeZone, genericNameType, j);
            return (genericNonLocationName != null || (canonicalCLDRID = ZoneMeta.getCanonicalCLDRID(timeZone)) == null) ? genericNonLocationName : getGenericLocationName(canonicalCLDRID);
        }
        return null;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public String getGenericLocationName(String str) {
        if (str == null || str.length() == 0) {
            return null;
        }
        String pattern = this._genericLocationNamesMap.get(str);
        if (pattern != null) {
            if (pattern.length() == 0) {
                return null;
            }
            return pattern;
        }
        Output output = new Output();
        String canonicalCountry = ZoneMeta.getCanonicalCountry(str, output);
        if (canonicalCountry != null) {
            if (((Boolean) output.value).booleanValue()) {
                pattern = formatPattern(Pattern.REGION_FORMAT, getLocaleDisplayNames().regionDisplayName(canonicalCountry));
            } else {
                pattern = formatPattern(Pattern.REGION_FORMAT, this._tznames.getExemplarLocationName(str));
            }
        }
        if (pattern == null) {
            this._genericLocationNamesMap.putIfAbsent(str.intern(), "");
        } else {
            synchronized (this) {
                String strIntern = str.intern();
                String strPutIfAbsent = this._genericLocationNamesMap.putIfAbsent(strIntern, pattern.intern());
                if (strPutIfAbsent == null) {
                    this._gnamesTrie.put(pattern, new NameInfo(strIntern, GenericNameType.LOCATION));
                } else {
                    pattern = strPutIfAbsent;
                }
            }
        }
        return pattern;
    }

    public TimeZoneGenericNames setFormatPattern(Pattern pattern, String str) {
        if (isFrozen()) {
            throw new UnsupportedOperationException("Attempt to modify frozen object");
        }
        if (!this._genericLocationNamesMap.isEmpty()) {
            this._genericLocationNamesMap = new ConcurrentHashMap<>();
        }
        if (!this._genericPartialLocationNamesMap.isEmpty()) {
            this._genericPartialLocationNamesMap = new ConcurrentHashMap<>();
        }
        this._gnamesTrie = null;
        this._gnamesTrieFullyLoaded = false;
        if (this._patternFormatters == null) {
            this._patternFormatters = new MessageFormat[Pattern.values().length];
        }
        this._patternFormatters[pattern.ordinal()] = new MessageFormat(str);
        return this;
    }

    /* JADX WARN: Removed duplicated region for block: B:44:0x009e  */
    /* JADX WARN: Removed duplicated region for block: B:54:0x00be  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    private java.lang.String formatGenericNonLocationName(com.ibm.icu.util.TimeZone r20, com.ibm.icu.impl.TimeZoneGenericNames.GenericNameType r21, long r22) {
        /*
            Method dump skipped, instructions count: 282
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ibm.icu.impl.TimeZoneGenericNames.formatGenericNonLocationName(com.ibm.icu.util.TimeZone, com.ibm.icu.impl.TimeZoneGenericNames$GenericNameType, long):java.lang.String");
    }

    private synchronized String formatPattern(Pattern pattern, String... strArr) {
        int iOrdinal;
        String strDefaultValue;
        if (this._patternFormatters == null) {
            this._patternFormatters = new MessageFormat[Pattern.values().length];
        }
        iOrdinal = pattern.ordinal();
        if (this._patternFormatters[iOrdinal] == null) {
            try {
                strDefaultValue = ((ICUResourceBundle) ICUResourceBundle.getBundleInstance(ICUData.ICU_ZONE_BASE_NAME, this._locale)).getStringWithFallback("zoneStrings/" + pattern.key());
            } catch (MissingResourceException unused) {
                strDefaultValue = pattern.defaultValue();
            }
            this._patternFormatters[iOrdinal] = new MessageFormat(strDefaultValue);
        }
        return this._patternFormatters[iOrdinal].format(strArr);
    }

    private synchronized LocaleDisplayNames getLocaleDisplayNames() {
        LocaleDisplayNames localeDisplayNames;
        localeDisplayNames = this._localeDisplayNamesRef != null ? this._localeDisplayNamesRef.get() : null;
        if (localeDisplayNames == null) {
            localeDisplayNames = LocaleDisplayNames.getInstance(this._locale);
            this._localeDisplayNamesRef = new WeakReference<>(localeDisplayNames);
        }
        return localeDisplayNames;
    }

    private synchronized void loadStrings(String str) {
        if (str != null) {
            if (str.length() != 0) {
                getGenericLocationName(str);
                for (String str2 : this._tznames.getAvailableMetaZoneIDs(str)) {
                    if (!str.equals(this._tznames.getReferenceZoneID(str2, getTargetRegion()))) {
                        TimeZoneNames.NameType[] nameTypeArr = GENERIC_NON_LOCATION_TYPES;
                        int length = nameTypeArr.length;
                        for (int i = 0; i < length; i++) {
                            TimeZoneNames.NameType nameType = nameTypeArr[i];
                            String metaZoneDisplayName = this._tznames.getMetaZoneDisplayName(str2, nameType);
                            if (metaZoneDisplayName != null) {
                                getPartialLocationName(str, str2, nameType == TimeZoneNames.NameType.LONG_GENERIC, metaZoneDisplayName);
                            }
                        }
                    }
                }
            }
        }
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

    private String getPartialLocationName(String str, String str2, boolean z, String str3) {
        String exemplarLocationName;
        String str4 = str + ContainerUtils.FIELD_DELIMITER + str2 + "#" + (z ? "L" : "S");
        String str5 = this._genericPartialLocationNamesMap.get(str4);
        if (str5 != null) {
            return str5;
        }
        String canonicalCountry = ZoneMeta.getCanonicalCountry(str);
        if (canonicalCountry != null) {
            if (str.equals(this._tznames.getReferenceZoneID(str2, canonicalCountry))) {
                exemplarLocationName = getLocaleDisplayNames().regionDisplayName(canonicalCountry);
            } else {
                exemplarLocationName = this._tznames.getExemplarLocationName(str);
            }
        } else {
            exemplarLocationName = this._tznames.getExemplarLocationName(str);
            if (exemplarLocationName == null) {
                exemplarLocationName = str;
            }
        }
        String pattern = formatPattern(Pattern.FALLBACK_FORMAT, exemplarLocationName, str3);
        synchronized (this) {
            String strPutIfAbsent = this._genericPartialLocationNamesMap.putIfAbsent(str4.intern(), pattern.intern());
            if (strPutIfAbsent == null) {
                this._gnamesTrie.put(pattern, new NameInfo(str.intern(), z ? GenericNameType.LONG : GenericNameType.SHORT));
            } else {
                pattern = strPutIfAbsent;
            }
        }
        return pattern;
    }

    private static class NameInfo {
        final GenericNameType type;
        final String tzID;

        NameInfo(String str, GenericNameType genericNameType) {
            this.tzID = str;
            this.type = genericNameType;
        }
    }

    public static class GenericMatchInfo {
        final int matchLength;
        final GenericNameType nameType;
        final TimeZoneFormat.TimeType timeType;
        final String tzID;

        /* synthetic */ GenericMatchInfo(GenericNameType genericNameType, String str, int i, AnonymousClass1 anonymousClass1) {
            this(genericNameType, str, i);
        }

        /* synthetic */ GenericMatchInfo(GenericNameType genericNameType, String str, int i, TimeZoneFormat.TimeType timeType, AnonymousClass1 anonymousClass1) {
            this(genericNameType, str, i, timeType);
        }

        private GenericMatchInfo(GenericNameType genericNameType, String str, int i) {
            this(genericNameType, str, i, TimeZoneFormat.TimeType.UNKNOWN);
        }

        private GenericMatchInfo(GenericNameType genericNameType, String str, int i, TimeZoneFormat.TimeType timeType) {
            this.nameType = genericNameType;
            this.tzID = str;
            this.matchLength = i;
            this.timeType = timeType;
        }

        public GenericNameType nameType() {
            return this.nameType;
        }

        public String tzID() {
            return this.tzID;
        }

        public TimeZoneFormat.TimeType timeType() {
            return this.timeType;
        }

        public int matchLength() {
            return this.matchLength;
        }
    }

    private static class GenericNameSearchHandler implements TextTrieMap.ResultHandler<NameInfo> {
        private Collection<GenericMatchInfo> _matches;
        private int _maxMatchLen;
        private EnumSet<GenericNameType> _types;

        GenericNameSearchHandler(EnumSet<GenericNameType> enumSet) {
            this._types = enumSet;
        }

        @Override // com.ibm.icu.impl.TextTrieMap.ResultHandler
        public boolean handlePrefixMatch(int i, Iterator<NameInfo> it2) {
            while (it2.hasNext()) {
                NameInfo next = it2.next();
                EnumSet<GenericNameType> enumSet = this._types;
                if (enumSet == null || enumSet.contains(next.type)) {
                    GenericMatchInfo genericMatchInfo = new GenericMatchInfo(next.type, next.tzID, i, (AnonymousClass1) null);
                    if (this._matches == null) {
                        this._matches = new LinkedList();
                    }
                    this._matches.add(genericMatchInfo);
                    if (i > this._maxMatchLen) {
                        this._maxMatchLen = i;
                    }
                }
            }
            return true;
        }

        public Collection<GenericMatchInfo> getMatches() {
            return this._matches;
        }

        public int getMaxMatchLen() {
            return this._maxMatchLen;
        }

        public void resetResults() {
            this._matches = null;
            this._maxMatchLen = 0;
        }
    }

    public GenericMatchInfo findBestMatch(String str, int i, EnumSet<GenericNameType> enumSet) {
        if (str == null || str.length() == 0 || i < 0 || i >= str.length()) {
            throw new IllegalArgumentException("bad input text or range");
        }
        Collection<TimeZoneNames.MatchInfo> collectionFindTimeZoneNames = findTimeZoneNames(str, i, enumSet);
        GenericMatchInfo genericMatchInfoCreateGenericMatchInfo = null;
        if (collectionFindTimeZoneNames != null) {
            TimeZoneNames.MatchInfo matchInfo = null;
            for (TimeZoneNames.MatchInfo matchInfo2 : collectionFindTimeZoneNames) {
                if (matchInfo == null || matchInfo2.matchLength() > matchInfo.matchLength()) {
                    matchInfo = matchInfo2;
                }
            }
            if (matchInfo != null) {
                genericMatchInfoCreateGenericMatchInfo = createGenericMatchInfo(matchInfo);
                if (genericMatchInfoCreateGenericMatchInfo.matchLength() == str.length() - i && genericMatchInfoCreateGenericMatchInfo.timeType != TimeZoneFormat.TimeType.STANDARD) {
                    return genericMatchInfoCreateGenericMatchInfo;
                }
            }
        }
        Collection<GenericMatchInfo> collectionFindLocal = findLocal(str, i, enumSet);
        if (collectionFindLocal != null) {
            for (GenericMatchInfo genericMatchInfo : collectionFindLocal) {
                if (genericMatchInfoCreateGenericMatchInfo == null || genericMatchInfo.matchLength() >= genericMatchInfoCreateGenericMatchInfo.matchLength()) {
                    genericMatchInfoCreateGenericMatchInfo = genericMatchInfo;
                }
            }
        }
        return genericMatchInfoCreateGenericMatchInfo;
    }

    public Collection<GenericMatchInfo> find(String str, int i, EnumSet<GenericNameType> enumSet) {
        if (str == null || str.length() == 0 || i < 0 || i >= str.length()) {
            throw new IllegalArgumentException("bad input text or range");
        }
        Collection<GenericMatchInfo> collectionFindLocal = findLocal(str, i, enumSet);
        Collection<TimeZoneNames.MatchInfo> collectionFindTimeZoneNames = findTimeZoneNames(str, i, enumSet);
        if (collectionFindTimeZoneNames != null) {
            for (TimeZoneNames.MatchInfo matchInfo : collectionFindTimeZoneNames) {
                if (collectionFindLocal == null) {
                    collectionFindLocal = new LinkedList<>();
                }
                collectionFindLocal.add(createGenericMatchInfo(matchInfo));
            }
        }
        return collectionFindLocal;
    }

    /* renamed from: com.ibm.icu.impl.TimeZoneGenericNames$1, reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$ibm$icu$impl$TimeZoneGenericNames$GenericNameType;
        static final /* synthetic */ int[] $SwitchMap$com$ibm$icu$text$TimeZoneNames$NameType;

        static {
            int[] iArr = new int[TimeZoneNames.NameType.values().length];
            $SwitchMap$com$ibm$icu$text$TimeZoneNames$NameType = iArr;
            try {
                iArr[TimeZoneNames.NameType.LONG_STANDARD.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$ibm$icu$text$TimeZoneNames$NameType[TimeZoneNames.NameType.LONG_GENERIC.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$ibm$icu$text$TimeZoneNames$NameType[TimeZoneNames.NameType.SHORT_STANDARD.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$com$ibm$icu$text$TimeZoneNames$NameType[TimeZoneNames.NameType.SHORT_GENERIC.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            int[] iArr2 = new int[GenericNameType.values().length];
            $SwitchMap$com$ibm$icu$impl$TimeZoneGenericNames$GenericNameType = iArr2;
            try {
                iArr2[GenericNameType.LOCATION.ordinal()] = 1;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                $SwitchMap$com$ibm$icu$impl$TimeZoneGenericNames$GenericNameType[GenericNameType.LONG.ordinal()] = 2;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                $SwitchMap$com$ibm$icu$impl$TimeZoneGenericNames$GenericNameType[GenericNameType.SHORT.ordinal()] = 3;
            } catch (NoSuchFieldError unused7) {
            }
        }
    }

    private GenericMatchInfo createGenericMatchInfo(TimeZoneNames.MatchInfo matchInfo) {
        GenericNameType genericNameType;
        TimeZoneFormat.TimeType timeType = TimeZoneFormat.TimeType.UNKNOWN;
        int i = AnonymousClass1.$SwitchMap$com$ibm$icu$text$TimeZoneNames$NameType[matchInfo.nameType().ordinal()];
        if (i == 1) {
            genericNameType = GenericNameType.LONG;
            timeType = TimeZoneFormat.TimeType.STANDARD;
        } else if (i == 2) {
            genericNameType = GenericNameType.LONG;
        } else if (i == 3) {
            genericNameType = GenericNameType.SHORT;
            timeType = TimeZoneFormat.TimeType.STANDARD;
        } else if (i == 4) {
            genericNameType = GenericNameType.SHORT;
        } else {
            throw new IllegalArgumentException("Unexpected MatchInfo name type - " + matchInfo.nameType());
        }
        TimeZoneFormat.TimeType timeType2 = timeType;
        GenericNameType genericNameType2 = genericNameType;
        String strTzID = matchInfo.tzID();
        if (strTzID == null) {
            strTzID = this._tznames.getReferenceZoneID(matchInfo.mzID(), getTargetRegion());
        }
        return new GenericMatchInfo(genericNameType2, strTzID, matchInfo.matchLength(), timeType2, null);
    }

    private Collection<TimeZoneNames.MatchInfo> findTimeZoneNames(String str, int i, EnumSet<GenericNameType> enumSet) {
        EnumSet<TimeZoneNames.NameType> enumSetNoneOf = EnumSet.noneOf(TimeZoneNames.NameType.class);
        if (enumSet.contains(GenericNameType.LONG)) {
            enumSetNoneOf.add(TimeZoneNames.NameType.LONG_GENERIC);
            enumSetNoneOf.add(TimeZoneNames.NameType.LONG_STANDARD);
        }
        if (enumSet.contains(GenericNameType.SHORT)) {
            enumSetNoneOf.add(TimeZoneNames.NameType.SHORT_GENERIC);
            enumSetNoneOf.add(TimeZoneNames.NameType.SHORT_STANDARD);
        }
        if (enumSetNoneOf.isEmpty()) {
            return null;
        }
        return this._tznames.find(str, i, enumSetNoneOf);
    }

    private synchronized Collection<GenericMatchInfo> findLocal(String str, int i, EnumSet<GenericNameType> enumSet) {
        GenericNameSearchHandler genericNameSearchHandler = new GenericNameSearchHandler(enumSet);
        this._gnamesTrie.find(str, i, genericNameSearchHandler);
        if (genericNameSearchHandler.getMaxMatchLen() != str.length() - i && !this._gnamesTrieFullyLoaded) {
            Iterator<String> it2 = TimeZone.getAvailableIDs(TimeZone.SystemTimeZoneType.CANONICAL, null, null).iterator();
            while (it2.hasNext()) {
                loadStrings(it2.next());
            }
            this._gnamesTrieFullyLoaded = true;
            genericNameSearchHandler.resetResults();
            this._gnamesTrie.find(str, i, genericNameSearchHandler);
            return genericNameSearchHandler.getMatches();
        }
        return genericNameSearchHandler.getMatches();
    }

    private static class Cache extends SoftCache<String, TimeZoneGenericNames, ULocale> {
        private Cache() {
        }

        /* synthetic */ Cache(AnonymousClass1 anonymousClass1) {
            this();
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.ibm.icu.impl.CacheBase
        public TimeZoneGenericNames createInstance(String str, ULocale uLocale) {
            return new TimeZoneGenericNames(uLocale, (AnonymousClass1) null).freeze();
        }
    }

    private void readObject(ObjectInputStream objectInputStream) throws ClassNotFoundException, IOException {
        objectInputStream.defaultReadObject();
        init();
    }

    @Override // com.ibm.icu.util.Freezable
    public boolean isFrozen() {
        return this._frozen;
    }

    @Override // com.ibm.icu.util.Freezable
    public TimeZoneGenericNames freeze() {
        this._frozen = true;
        return this;
    }

    @Override // com.ibm.icu.util.Freezable
    public TimeZoneGenericNames cloneAsThawed() {
        TimeZoneGenericNames timeZoneGenericNames = null;
        try {
            TimeZoneGenericNames timeZoneGenericNames2 = (TimeZoneGenericNames) super.clone();
            try {
                timeZoneGenericNames2._frozen = false;
                return timeZoneGenericNames2;
            } catch (Throwable unused) {
                timeZoneGenericNames = timeZoneGenericNames2;
                return timeZoneGenericNames;
            }
        } catch (Throwable unused2) {
        }
    }
}
