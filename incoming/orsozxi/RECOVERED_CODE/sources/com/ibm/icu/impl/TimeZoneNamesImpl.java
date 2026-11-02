package com.ibm.icu.impl;

import com.ibm.icu.impl.TextTrieMap;
import com.ibm.icu.impl.UResource;
import com.ibm.icu.impl.units.UnitsData;
import com.ibm.icu.text.TimeZoneNames;
import com.ibm.icu.util.TimeZone;
import com.ibm.icu.util.ULocale;
import com.ibm.icu.util.UResourceBundle;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Pattern;
import org.apache.commons.io.IOUtils;
import org.apache.http.message.TokenParser;

/* loaded from: classes2.dex */
public class TimeZoneNamesImpl extends TimeZoneNames {
    private static final Pattern LOC_EXCLUSION_PATTERN = Pattern.compile("Etc/.*|SystemV/.*|.*/Riyadh8[7-9]");
    private static volatile Set<String> METAZONE_IDS = null;
    private static final String MZ_PREFIX = "meta:";
    private static final MZ2TZsCache MZ_TO_TZS_CACHE;
    private static final TZ2MZsCache TZ_TO_MZS_CACHE;
    private static final String ZONE_STRINGS_BUNDLE = "zoneStrings";
    private static final long serialVersionUID = -2179814848495897472L;
    private transient ConcurrentHashMap<String, ZNames> _mzNamesMap;
    private transient boolean _namesFullyLoaded;
    private transient TextTrieMap<NameInfo> _namesTrie;
    private transient boolean _namesTrieFullyLoaded;
    private transient ConcurrentHashMap<String, ZNames> _tzNamesMap;
    private transient ICUResourceBundle _zoneStrings;

    static {
        AnonymousClass1 anonymousClass1 = null;
        TZ_TO_MZS_CACHE = new TZ2MZsCache(anonymousClass1);
        MZ_TO_TZS_CACHE = new MZ2TZsCache(anonymousClass1);
    }

    public TimeZoneNamesImpl(ULocale uLocale) {
        initialize(uLocale);
    }

    @Override // com.ibm.icu.text.TimeZoneNames
    public Set<String> getAvailableMetaZoneIDs() {
        return _getAvailableMetaZoneIDs();
    }

    static Set<String> _getAvailableMetaZoneIDs() {
        if (METAZONE_IDS == null) {
            synchronized (TimeZoneNamesImpl.class) {
                if (METAZONE_IDS == null) {
                    METAZONE_IDS = Collections.unmodifiableSet(UResourceBundle.getBundleInstance(ICUData.ICU_BASE_NAME, "metaZones").get("mapTimezones").keySet());
                }
            }
        }
        return METAZONE_IDS;
    }

    @Override // com.ibm.icu.text.TimeZoneNames
    public Set<String> getAvailableMetaZoneIDs(String str) {
        return _getAvailableMetaZoneIDs(str);
    }

    static Set<String> _getAvailableMetaZoneIDs(String str) {
        if (str == null || str.length() == 0) {
            return Collections.emptySet();
        }
        List<MZMapEntry> tZ2MZsCache = TZ_TO_MZS_CACHE.getInstance(str, str);
        if (tZ2MZsCache.isEmpty()) {
            return Collections.emptySet();
        }
        HashSet hashSet = new HashSet(tZ2MZsCache.size());
        Iterator<MZMapEntry> it2 = tZ2MZsCache.iterator();
        while (it2.hasNext()) {
            hashSet.add(it2.next().mzID());
        }
        return Collections.unmodifiableSet(hashSet);
    }

    @Override // com.ibm.icu.text.TimeZoneNames
    public String getMetaZoneID(String str, long j) {
        return _getMetaZoneID(str, j);
    }

    static String _getMetaZoneID(String str, long j) {
        if (str == null || str.length() == 0) {
            return null;
        }
        for (MZMapEntry mZMapEntry : TZ_TO_MZS_CACHE.getInstance(str, str)) {
            if (j >= mZMapEntry.from() && j < mZMapEntry.to()) {
                return mZMapEntry.mzID();
            }
        }
        return null;
    }

    @Override // com.ibm.icu.text.TimeZoneNames
    public String getReferenceZoneID(String str, String str2) {
        return _getReferenceZoneID(str, str2);
    }

    static String _getReferenceZoneID(String str, String str2) {
        if (str == null || str.length() == 0) {
            return null;
        }
        Map<String, String> mZ2TZsCache = MZ_TO_TZS_CACHE.getInstance(str, str);
        if (mZ2TZsCache.isEmpty()) {
            return null;
        }
        String str3 = mZ2TZsCache.get(str2);
        return str3 == null ? mZ2TZsCache.get(UnitsData.Constants.DEFAULT_REGION) : str3;
    }

    @Override // com.ibm.icu.text.TimeZoneNames
    public String getMetaZoneDisplayName(String str, TimeZoneNames.NameType nameType) {
        if (str == null || str.length() == 0) {
            return null;
        }
        return loadMetaZoneNames(str).getName(nameType);
    }

    @Override // com.ibm.icu.text.TimeZoneNames
    public String getTimeZoneDisplayName(String str, TimeZoneNames.NameType nameType) {
        if (str == null || str.length() == 0) {
            return null;
        }
        return loadTimeZoneNames(str).getName(nameType);
    }

    @Override // com.ibm.icu.text.TimeZoneNames
    public String getExemplarLocationName(String str) {
        if (str == null || str.length() == 0) {
            return null;
        }
        return loadTimeZoneNames(str).getName(TimeZoneNames.NameType.EXEMPLAR_LOCATION);
    }

    @Override // com.ibm.icu.text.TimeZoneNames
    public synchronized Collection<TimeZoneNames.MatchInfo> find(CharSequence charSequence, int i, EnumSet<TimeZoneNames.NameType> enumSet) {
        if (charSequence != null) {
            if (charSequence.length() != 0 && i >= 0 && i < charSequence.length()) {
                NameSearchHandler nameSearchHandler = new NameSearchHandler(enumSet);
                Collection<TimeZoneNames.MatchInfo> collectionDoFind = doFind(nameSearchHandler, charSequence, i);
                if (collectionDoFind != null) {
                    return collectionDoFind;
                }
                addAllNamesIntoTrie();
                Collection<TimeZoneNames.MatchInfo> collectionDoFind2 = doFind(nameSearchHandler, charSequence, i);
                if (collectionDoFind2 != null) {
                    return collectionDoFind2;
                }
                internalLoadAllDisplayNames();
                for (String str : TimeZone.getAvailableIDs(TimeZone.SystemTimeZoneType.CANONICAL, null, null)) {
                    if (!this._tzNamesMap.containsKey(str)) {
                        ZNames.createTimeZoneAndPutInCache(this._tzNamesMap, null, str);
                    }
                }
                addAllNamesIntoTrie();
                this._namesTrieFullyLoaded = true;
                return doFind(nameSearchHandler, charSequence, i);
            }
        }
        throw new IllegalArgumentException("bad input text or range");
    }

    private Collection<TimeZoneNames.MatchInfo> doFind(NameSearchHandler nameSearchHandler, CharSequence charSequence, int i) {
        nameSearchHandler.resetResults();
        this._namesTrie.find(charSequence, i, nameSearchHandler);
        if (nameSearchHandler.getMaxMatchLen() == charSequence.length() - i || this._namesTrieFullyLoaded) {
            return nameSearchHandler.getMatches();
        }
        return null;
    }

    @Override // com.ibm.icu.text.TimeZoneNames
    public synchronized void loadAllDisplayNames() {
        internalLoadAllDisplayNames();
    }

    @Override // com.ibm.icu.text.TimeZoneNames
    public void getDisplayNames(String str, TimeZoneNames.NameType[] nameTypeArr, long j, String[] strArr, int i) {
        if (str == null || str.length() == 0) {
            return;
        }
        ZNames zNamesLoadTimeZoneNames = loadTimeZoneNames(str);
        ZNames zNamesLoadMetaZoneNames = null;
        for (int i2 = 0; i2 < nameTypeArr.length; i2++) {
            TimeZoneNames.NameType nameType = nameTypeArr[i2];
            String name = zNamesLoadTimeZoneNames.getName(nameType);
            if (name == null) {
                if (zNamesLoadMetaZoneNames == null) {
                    String metaZoneID = getMetaZoneID(str, j);
                    if (metaZoneID == null || metaZoneID.length() == 0) {
                        zNamesLoadMetaZoneNames = ZNames.EMPTY_ZNAMES;
                    } else {
                        zNamesLoadMetaZoneNames = loadMetaZoneNames(metaZoneID);
                    }
                }
                name = zNamesLoadMetaZoneNames.getName(nameType);
            }
            strArr[i + i2] = name;
        }
    }

    private void internalLoadAllDisplayNames() throws MissingResourceException {
        if (this._namesFullyLoaded) {
            return;
        }
        this._namesFullyLoaded = true;
        new ZoneStringsLoader(this, null).load();
    }

    private void addAllNamesIntoTrie() {
        for (Map.Entry<String, ZNames> entry : this._tzNamesMap.entrySet()) {
            entry.getValue().addAsTimeZoneIntoTrie(entry.getKey(), this._namesTrie);
        }
        for (Map.Entry<String, ZNames> entry2 : this._mzNamesMap.entrySet()) {
            entry2.getValue().addAsMetaZoneIntoTrie(entry2.getKey(), this._namesTrie);
        }
    }

    private final class ZoneStringsLoader extends UResource.Sink {
        static final /* synthetic */ boolean $assertionsDisabled = false;
        private static final int INITIAL_NUM_ZONES = 300;
        private HashMap<UResource.Key, ZNamesLoader> keyToLoader;
        private StringBuilder sb;

        private ZoneStringsLoader() {
            this.keyToLoader = new HashMap<>(300);
            this.sb = new StringBuilder(32);
        }

        /* synthetic */ ZoneStringsLoader(TimeZoneNamesImpl timeZoneNamesImpl, AnonymousClass1 anonymousClass1) {
            this();
        }

        void load() throws MissingResourceException {
            TimeZoneNamesImpl.this._zoneStrings.getAllItemsWithFallback("", this);
            for (Map.Entry<UResource.Key, ZNamesLoader> entry : this.keyToLoader.entrySet()) {
                ZNamesLoader value = entry.getValue();
                if (value != ZNamesLoader.DUMMY_LOADER) {
                    UResource.Key key = entry.getKey();
                    if (isMetaZone(key)) {
                        ZNames.createMetaZoneAndPutInCache(TimeZoneNamesImpl.this._mzNamesMap, value.getNames(), mzIDFromKey(key));
                    } else {
                        ZNames.createTimeZoneAndPutInCache(TimeZoneNamesImpl.this._tzNamesMap, value.getNames(), tzIDFromKey(key));
                    }
                }
            }
        }

        @Override // com.ibm.icu.impl.UResource.Sink
        public void put(UResource.Key key, UResource.Value value, boolean z) {
            UResource.Table table = value.getTable();
            for (int i = 0; table.getKeyAndValue(i, key, value); i++) {
                if (value.getType() == 2) {
                    consumeNamesTable(key, value, z);
                }
            }
        }

        private void consumeNamesTable(UResource.Key key, UResource.Value value, boolean z) {
            ZNamesLoader zNamesLoader = this.keyToLoader.get(key);
            if (zNamesLoader == null) {
                AnonymousClass1 anonymousClass1 = null;
                if (isMetaZone(key)) {
                    zNamesLoader = TimeZoneNamesImpl.this._mzNamesMap.containsKey(mzIDFromKey(key)) ? ZNamesLoader.DUMMY_LOADER : new ZNamesLoader(anonymousClass1);
                } else {
                    zNamesLoader = TimeZoneNamesImpl.this._tzNamesMap.containsKey(tzIDFromKey(key)) ? ZNamesLoader.DUMMY_LOADER : new ZNamesLoader(anonymousClass1);
                }
                this.keyToLoader.put(createKey(key), zNamesLoader);
            }
            if (zNamesLoader != ZNamesLoader.DUMMY_LOADER) {
                zNamesLoader.put(key, value, z);
            }
        }

        UResource.Key createKey(UResource.Key key) {
            return key.m21clone();
        }

        boolean isMetaZone(UResource.Key key) {
            return key.startsWith(TimeZoneNamesImpl.MZ_PREFIX);
        }

        private String mzIDFromKey(UResource.Key key) {
            this.sb.setLength(0);
            for (int i = 5; i < key.length(); i++) {
                this.sb.append(key.charAt(i));
            }
            return this.sb.toString();
        }

        private String tzIDFromKey(UResource.Key key) {
            this.sb.setLength(0);
            for (int i = 0; i < key.length(); i++) {
                char cCharAt = key.charAt(i);
                if (cCharAt == ':') {
                    cCharAt = IOUtils.DIR_SEPARATOR_UNIX;
                }
                this.sb.append(cCharAt);
            }
            return this.sb.toString();
        }
    }

    private void initialize(ULocale uLocale) {
        this._zoneStrings = (ICUResourceBundle) ((ICUResourceBundle) ICUResourceBundle.getBundleInstance(ICUData.ICU_ZONE_BASE_NAME, uLocale)).get(ZONE_STRINGS_BUNDLE);
        this._tzNamesMap = new ConcurrentHashMap<>();
        this._mzNamesMap = new ConcurrentHashMap<>();
        this._namesFullyLoaded = false;
        this._namesTrie = new TextTrieMap<>(true);
        this._namesTrieFullyLoaded = false;
        String canonicalCLDRID = ZoneMeta.getCanonicalCLDRID(TimeZone.getDefault());
        if (canonicalCLDRID != null) {
            loadStrings(canonicalCLDRID);
        }
    }

    private synchronized void loadStrings(String str) {
        if (str != null) {
            if (str.length() != 0) {
                loadTimeZoneNames(str);
                Iterator<String> it2 = getAvailableMetaZoneIDs(str).iterator();
                while (it2.hasNext()) {
                    loadMetaZoneNames(it2.next());
                }
            }
        }
    }

    private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
        objectOutputStream.writeObject(this._zoneStrings.getULocale());
    }

    private void readObject(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        initialize((ULocale) objectInputStream.readObject());
    }

    private synchronized ZNames loadMetaZoneNames(String str) {
        ZNames zNamesCreateMetaZoneAndPutInCache;
        zNamesCreateMetaZoneAndPutInCache = this._mzNamesMap.get(str);
        if (zNamesCreateMetaZoneAndPutInCache == null) {
            ZNamesLoader zNamesLoader = new ZNamesLoader(null);
            zNamesLoader.loadMetaZone(this._zoneStrings, str);
            zNamesCreateMetaZoneAndPutInCache = ZNames.createMetaZoneAndPutInCache(this._mzNamesMap, zNamesLoader.getNames(), str);
        }
        return zNamesCreateMetaZoneAndPutInCache;
    }

    private synchronized ZNames loadTimeZoneNames(String str) {
        ZNames zNamesCreateTimeZoneAndPutInCache;
        zNamesCreateTimeZoneAndPutInCache = this._tzNamesMap.get(str);
        if (zNamesCreateTimeZoneAndPutInCache == null) {
            ZNamesLoader zNamesLoader = new ZNamesLoader(null);
            zNamesLoader.loadTimeZone(this._zoneStrings, str);
            zNamesCreateTimeZoneAndPutInCache = ZNames.createTimeZoneAndPutInCache(this._tzNamesMap, zNamesLoader.getNames(), str);
        }
        return zNamesCreateTimeZoneAndPutInCache;
    }

    private static class NameInfo {
        String mzID;
        TimeZoneNames.NameType type;
        String tzID;

        private NameInfo() {
        }

        /* synthetic */ NameInfo(AnonymousClass1 anonymousClass1) {
            this();
        }
    }

    private static class NameSearchHandler implements TextTrieMap.ResultHandler<NameInfo> {
        static final /* synthetic */ boolean $assertionsDisabled = false;
        private Collection<TimeZoneNames.MatchInfo> _matches;
        private int _maxMatchLen;
        private EnumSet<TimeZoneNames.NameType> _nameTypes;

        NameSearchHandler(EnumSet<TimeZoneNames.NameType> enumSet) {
            this._nameTypes = enumSet;
        }

        @Override // com.ibm.icu.impl.TextTrieMap.ResultHandler
        public boolean handlePrefixMatch(int i, Iterator<NameInfo> it2) {
            TimeZoneNames.MatchInfo matchInfo;
            while (it2.hasNext()) {
                NameInfo next = it2.next();
                EnumSet<TimeZoneNames.NameType> enumSet = this._nameTypes;
                if (enumSet == null || enumSet.contains(next.type)) {
                    if (next.tzID != null) {
                        matchInfo = new TimeZoneNames.MatchInfo(next.type, next.tzID, null, i);
                    } else {
                        matchInfo = new TimeZoneNames.MatchInfo(next.type, null, next.mzID, i);
                    }
                    if (this._matches == null) {
                        this._matches = new LinkedList();
                    }
                    this._matches.add(matchInfo);
                    if (i > this._maxMatchLen) {
                        this._maxMatchLen = i;
                    }
                }
            }
            return true;
        }

        public Collection<TimeZoneNames.MatchInfo> getMatches() {
            Collection<TimeZoneNames.MatchInfo> collection = this._matches;
            return collection == null ? Collections.emptyList() : collection;
        }

        public int getMaxMatchLen() {
            return this._maxMatchLen;
        }

        public void resetResults() {
            this._matches = null;
            this._maxMatchLen = 0;
        }
    }

    private static final class ZNamesLoader extends UResource.Sink {
        static final /* synthetic */ boolean $assertionsDisabled = false;
        private static ZNamesLoader DUMMY_LOADER = new ZNamesLoader();
        private String[] names;

        private ZNamesLoader() {
        }

        /* synthetic */ ZNamesLoader(AnonymousClass1 anonymousClass1) {
            this();
        }

        void loadMetaZone(ICUResourceBundle iCUResourceBundle, String str) {
            loadNames(iCUResourceBundle, TimeZoneNamesImpl.MZ_PREFIX + str);
        }

        void loadTimeZone(ICUResourceBundle iCUResourceBundle, String str) {
            loadNames(iCUResourceBundle, str.replace(IOUtils.DIR_SEPARATOR_UNIX, ':'));
        }

        void loadNames(ICUResourceBundle iCUResourceBundle, String str) {
            this.names = null;
            try {
                iCUResourceBundle.getAllItemsWithFallback(str, this);
            } catch (MissingResourceException unused) {
            }
        }

        private static ZNames.NameTypeIndex nameTypeIndexFromKey(UResource.Key key) {
            if (key.length() != 2) {
                return null;
            }
            char cCharAt = key.charAt(0);
            char cCharAt2 = key.charAt(1);
            if (cCharAt == 'l') {
                if (cCharAt2 == 'g') {
                    return ZNames.NameTypeIndex.LONG_GENERIC;
                }
                if (cCharAt2 == 's') {
                    return ZNames.NameTypeIndex.LONG_STANDARD;
                }
                if (cCharAt2 == 'd') {
                    return ZNames.NameTypeIndex.LONG_DAYLIGHT;
                }
                return null;
            }
            if (cCharAt != 's') {
                if (cCharAt == 'e' && cCharAt2 == 'c') {
                    return ZNames.NameTypeIndex.EXEMPLAR_LOCATION;
                }
                return null;
            }
            if (cCharAt2 == 'g') {
                return ZNames.NameTypeIndex.SHORT_GENERIC;
            }
            if (cCharAt2 == 's') {
                return ZNames.NameTypeIndex.SHORT_STANDARD;
            }
            if (cCharAt2 == 'd') {
                return ZNames.NameTypeIndex.SHORT_DAYLIGHT;
            }
            return null;
        }

        private void setNameIfEmpty(UResource.Key key, UResource.Value value) {
            if (this.names == null) {
                this.names = new String[7];
            }
            ZNames.NameTypeIndex nameTypeIndexNameTypeIndexFromKey = nameTypeIndexFromKey(key);
            if (nameTypeIndexNameTypeIndexFromKey != null && this.names[nameTypeIndexNameTypeIndexFromKey.ordinal()] == null) {
                this.names[nameTypeIndexNameTypeIndexFromKey.ordinal()] = value.getString();
            }
        }

        @Override // com.ibm.icu.impl.UResource.Sink
        public void put(UResource.Key key, UResource.Value value, boolean z) {
            UResource.Table table = value.getTable();
            for (int i = 0; table.getKeyAndValue(i, key, value); i++) {
                setNameIfEmpty(key, value);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public String[] getNames() {
            if (Utility.sameObjects(this.names, null)) {
                return null;
            }
            int i = 0;
            for (int i2 = 0; i2 < 7; i2++) {
                String str = this.names[i2];
                if (str != null) {
                    if (str.equals(ICUResourceBundle.NO_INHERITANCE_MARKER)) {
                        this.names[i2] = null;
                    } else {
                        i = i2 + 1;
                    }
                }
            }
            if (i == 7) {
                return this.names;
            }
            if (i == 0) {
                return null;
            }
            return (String[]) Arrays.copyOfRange(this.names, 0, i);
        }
    }

    private static class ZNames {
        static final ZNames EMPTY_ZNAMES = new ZNames(null);
        private static final int EX_LOC_INDEX = NameTypeIndex.EXEMPLAR_LOCATION.ordinal();
        public static final int NUM_NAME_TYPES = 7;
        private String[] _names;
        private boolean didAddIntoTrie;

        private enum NameTypeIndex {
            EXEMPLAR_LOCATION,
            LONG_GENERIC,
            LONG_STANDARD,
            LONG_DAYLIGHT,
            SHORT_GENERIC,
            SHORT_STANDARD,
            SHORT_DAYLIGHT;

            static final NameTypeIndex[] values = values();
        }

        private static int getNameTypeIndex(TimeZoneNames.NameType nameType) {
            switch (AnonymousClass1.$SwitchMap$com$ibm$icu$text$TimeZoneNames$NameType[nameType.ordinal()]) {
                case 1:
                    return NameTypeIndex.EXEMPLAR_LOCATION.ordinal();
                case 2:
                    return NameTypeIndex.LONG_GENERIC.ordinal();
                case 3:
                    return NameTypeIndex.LONG_STANDARD.ordinal();
                case 4:
                    return NameTypeIndex.LONG_DAYLIGHT.ordinal();
                case 5:
                    return NameTypeIndex.SHORT_GENERIC.ordinal();
                case 6:
                    return NameTypeIndex.SHORT_STANDARD.ordinal();
                case 7:
                    return NameTypeIndex.SHORT_DAYLIGHT.ordinal();
                default:
                    throw new AssertionError("No NameTypeIndex match for " + nameType);
            }
        }

        private static TimeZoneNames.NameType getNameType(int i) {
            switch (AnonymousClass1.$SwitchMap$com$ibm$icu$impl$TimeZoneNamesImpl$ZNames$NameTypeIndex[NameTypeIndex.values[i].ordinal()]) {
                case 1:
                    return TimeZoneNames.NameType.EXEMPLAR_LOCATION;
                case 2:
                    return TimeZoneNames.NameType.LONG_GENERIC;
                case 3:
                    return TimeZoneNames.NameType.LONG_STANDARD;
                case 4:
                    return TimeZoneNames.NameType.LONG_DAYLIGHT;
                case 5:
                    return TimeZoneNames.NameType.SHORT_GENERIC;
                case 6:
                    return TimeZoneNames.NameType.SHORT_STANDARD;
                case 7:
                    return TimeZoneNames.NameType.SHORT_DAYLIGHT;
                default:
                    throw new AssertionError("No NameType match for " + i);
            }
        }

        protected ZNames(String[] strArr) {
            this._names = strArr;
            this.didAddIntoTrie = strArr == null;
        }

        public static ZNames createMetaZoneAndPutInCache(Map<String, ZNames> map, String[] strArr, String str) {
            ZNames zNames;
            String strIntern = str.intern();
            if (strArr == null) {
                zNames = EMPTY_ZNAMES;
            } else {
                zNames = new ZNames(strArr);
            }
            map.put(strIntern, zNames);
            return zNames;
        }

        public static ZNames createTimeZoneAndPutInCache(Map<String, ZNames> map, String[] strArr, String str) {
            if (strArr == null) {
                strArr = new String[EX_LOC_INDEX + 1];
            }
            int i = EX_LOC_INDEX;
            if (strArr[i] == null) {
                strArr[i] = TimeZoneNamesImpl.getDefaultExemplarLocationName(str);
            }
            String strIntern = str.intern();
            ZNames zNames = new ZNames(strArr);
            map.put(strIntern, zNames);
            return zNames;
        }

        public String getName(TimeZoneNames.NameType nameType) {
            int nameTypeIndex = getNameTypeIndex(nameType);
            String[] strArr = this._names;
            if (strArr == null || nameTypeIndex >= strArr.length) {
                return null;
            }
            return strArr[nameTypeIndex];
        }

        public void addAsMetaZoneIntoTrie(String str, TextTrieMap<NameInfo> textTrieMap) {
            addNamesIntoTrie(str, null, textTrieMap);
        }

        public void addAsTimeZoneIntoTrie(String str, TextTrieMap<NameInfo> textTrieMap) {
            addNamesIntoTrie(null, str, textTrieMap);
        }

        private void addNamesIntoTrie(String str, String str2, TextTrieMap<NameInfo> textTrieMap) {
            if (this._names == null || this.didAddIntoTrie) {
                return;
            }
            this.didAddIntoTrie = true;
            int i = 0;
            while (true) {
                String[] strArr = this._names;
                if (i >= strArr.length) {
                    return;
                }
                String str3 = strArr[i];
                if (str3 != null) {
                    NameInfo nameInfo = new NameInfo(null);
                    nameInfo.mzID = str;
                    nameInfo.tzID = str2;
                    nameInfo.type = getNameType(i);
                    textTrieMap.put(str3, nameInfo);
                }
                i++;
            }
        }
    }

    /* renamed from: com.ibm.icu.impl.TimeZoneNamesImpl$1, reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$ibm$icu$impl$TimeZoneNamesImpl$ZNames$NameTypeIndex;
        static final /* synthetic */ int[] $SwitchMap$com$ibm$icu$text$TimeZoneNames$NameType;

        static {
            int[] iArr = new int[ZNames.NameTypeIndex.values().length];
            $SwitchMap$com$ibm$icu$impl$TimeZoneNamesImpl$ZNames$NameTypeIndex = iArr;
            try {
                iArr[ZNames.NameTypeIndex.EXEMPLAR_LOCATION.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$ibm$icu$impl$TimeZoneNamesImpl$ZNames$NameTypeIndex[ZNames.NameTypeIndex.LONG_GENERIC.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$ibm$icu$impl$TimeZoneNamesImpl$ZNames$NameTypeIndex[ZNames.NameTypeIndex.LONG_STANDARD.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$com$ibm$icu$impl$TimeZoneNamesImpl$ZNames$NameTypeIndex[ZNames.NameTypeIndex.LONG_DAYLIGHT.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$com$ibm$icu$impl$TimeZoneNamesImpl$ZNames$NameTypeIndex[ZNames.NameTypeIndex.SHORT_GENERIC.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                $SwitchMap$com$ibm$icu$impl$TimeZoneNamesImpl$ZNames$NameTypeIndex[ZNames.NameTypeIndex.SHORT_STANDARD.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                $SwitchMap$com$ibm$icu$impl$TimeZoneNamesImpl$ZNames$NameTypeIndex[ZNames.NameTypeIndex.SHORT_DAYLIGHT.ordinal()] = 7;
            } catch (NoSuchFieldError unused7) {
            }
            int[] iArr2 = new int[TimeZoneNames.NameType.values().length];
            $SwitchMap$com$ibm$icu$text$TimeZoneNames$NameType = iArr2;
            try {
                iArr2[TimeZoneNames.NameType.EXEMPLAR_LOCATION.ordinal()] = 1;
            } catch (NoSuchFieldError unused8) {
            }
            try {
                $SwitchMap$com$ibm$icu$text$TimeZoneNames$NameType[TimeZoneNames.NameType.LONG_GENERIC.ordinal()] = 2;
            } catch (NoSuchFieldError unused9) {
            }
            try {
                $SwitchMap$com$ibm$icu$text$TimeZoneNames$NameType[TimeZoneNames.NameType.LONG_STANDARD.ordinal()] = 3;
            } catch (NoSuchFieldError unused10) {
            }
            try {
                $SwitchMap$com$ibm$icu$text$TimeZoneNames$NameType[TimeZoneNames.NameType.LONG_DAYLIGHT.ordinal()] = 4;
            } catch (NoSuchFieldError unused11) {
            }
            try {
                $SwitchMap$com$ibm$icu$text$TimeZoneNames$NameType[TimeZoneNames.NameType.SHORT_GENERIC.ordinal()] = 5;
            } catch (NoSuchFieldError unused12) {
            }
            try {
                $SwitchMap$com$ibm$icu$text$TimeZoneNames$NameType[TimeZoneNames.NameType.SHORT_STANDARD.ordinal()] = 6;
            } catch (NoSuchFieldError unused13) {
            }
            try {
                $SwitchMap$com$ibm$icu$text$TimeZoneNames$NameType[TimeZoneNames.NameType.SHORT_DAYLIGHT.ordinal()] = 7;
            } catch (NoSuchFieldError unused14) {
            }
        }
    }

    private static class MZMapEntry {
        private long _from;
        private String _mzID;
        private long _to;

        MZMapEntry(String str, long j, long j2) {
            this._mzID = str;
            this._from = j;
            this._to = j2;
        }

        String mzID() {
            return this._mzID;
        }

        long from() {
            return this._from;
        }

        long to() {
            return this._to;
        }
    }

    private static class TZ2MZsCache extends SoftCache<String, List<MZMapEntry>, String> {
        private TZ2MZsCache() {
        }

        /* synthetic */ TZ2MZsCache(AnonymousClass1 anonymousClass1) {
            this();
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.ibm.icu.impl.CacheBase
        public List<MZMapEntry> createInstance(String str, String str2) {
            try {
                UResourceBundle uResourceBundle = UResourceBundle.getBundleInstance(ICUData.ICU_BASE_NAME, "metaZones").get("metazoneInfo").get(str2.replace(IOUtils.DIR_SEPARATOR_UNIX, ':'));
                ArrayList arrayList = new ArrayList(uResourceBundle.getSize());
                for (int i = 0; i < uResourceBundle.getSize(); i++) {
                    UResourceBundle uResourceBundle2 = uResourceBundle.get(i);
                    String string = uResourceBundle2.getString(0);
                    String string2 = "1970-01-01 00:00";
                    String string3 = "9999-12-31 23:59";
                    if (uResourceBundle2.getSize() == 3) {
                        string2 = uResourceBundle2.getString(1);
                        string3 = uResourceBundle2.getString(2);
                    }
                    arrayList.add(new MZMapEntry(string, parseDate(string2), parseDate(string3)));
                }
                return arrayList;
            } catch (MissingResourceException unused) {
                return Collections.emptyList();
            }
        }

        private static long parseDate(String str) {
            int i = 0;
            int i2 = 0;
            for (int i3 = 0; i3 <= 3; i3++) {
                int iCharAt = str.charAt(i3) - '0';
                if (iCharAt < 0 || iCharAt >= 10) {
                    throw new IllegalArgumentException("Bad year");
                }
                i2 = (i2 * 10) + iCharAt;
            }
            int i4 = 0;
            for (int i5 = 5; i5 <= 6; i5++) {
                int iCharAt2 = str.charAt(i5) - '0';
                if (iCharAt2 < 0 || iCharAt2 >= 10) {
                    throw new IllegalArgumentException("Bad month");
                }
                i4 = (i4 * 10) + iCharAt2;
            }
            int i6 = 0;
            for (int i7 = 8; i7 <= 9; i7++) {
                int iCharAt3 = str.charAt(i7) - '0';
                if (iCharAt3 < 0 || iCharAt3 >= 10) {
                    throw new IllegalArgumentException("Bad day");
                }
                i6 = (i6 * 10) + iCharAt3;
            }
            int i8 = 0;
            for (int i9 = 11; i9 <= 12; i9++) {
                int iCharAt4 = str.charAt(i9) - '0';
                if (iCharAt4 < 0 || iCharAt4 >= 10) {
                    throw new IllegalArgumentException("Bad hour");
                }
                i8 = (i8 * 10) + iCharAt4;
            }
            for (int i10 = 14; i10 <= 15; i10++) {
                int iCharAt5 = str.charAt(i10) - '0';
                if (iCharAt5 < 0 || iCharAt5 >= 10) {
                    throw new IllegalArgumentException("Bad minute");
                }
                i = (i * 10) + iCharAt5;
            }
            return (Grego.fieldsToDay(i2, i4 - 1, i6) * 86400000) + (i8 * 3600000) + (i * 60000);
        }
    }

    private static class MZ2TZsCache extends SoftCache<String, Map<String, String>, String> {
        private MZ2TZsCache() {
        }

        /* synthetic */ MZ2TZsCache(AnonymousClass1 anonymousClass1) {
            this();
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.ibm.icu.impl.CacheBase
        public Map<String, String> createInstance(String str, String str2) {
            try {
                UResourceBundle uResourceBundle = UResourceBundle.getBundleInstance(ICUData.ICU_BASE_NAME, "metaZones").get("mapTimezones").get(str);
                Set<String> setKeySet = uResourceBundle.keySet();
                HashMap map = new HashMap(setKeySet.size());
                for (String str3 : setKeySet) {
                    map.put(str3.intern(), uResourceBundle.getString(str3).intern());
                }
                return map;
            } catch (MissingResourceException unused) {
                return Collections.emptyMap();
            }
        }
    }

    public static String getDefaultExemplarLocationName(String str) {
        int iLastIndexOf;
        int i;
        if (str == null || str.length() == 0 || LOC_EXCLUSION_PATTERN.matcher(str).matches() || (iLastIndexOf = str.lastIndexOf(47)) <= 0 || (i = iLastIndexOf + 1) >= str.length()) {
            return null;
        }
        return str.substring(i).replace('_', TokenParser.SP);
    }
}
