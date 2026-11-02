package com.ibm.icu.impl;

import com.ibm.icu.util.Output;
import com.ibm.icu.util.SimpleTimeZone;
import com.ibm.icu.util.TimeZone;
import com.ibm.icu.util.UResourceBundle;
import java.lang.ref.SoftReference;
import java.util.Collections;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.Set;
import java.util.TreeSet;
import org.apache.commons.codec.language.Soundex;
import org.apache.commons.io.IOUtils;

/* loaded from: classes2.dex */
public final class ZoneMeta {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final boolean ASSERT = false;
    private static final CustomTimeZoneCache CUSTOM_ZONE_CACHE;
    private static SoftReference<Set<String>> REF_CANONICAL_SYSTEM_LOCATION_ZONES = null;
    private static SoftReference<Set<String>> REF_CANONICAL_SYSTEM_ZONES = null;
    private static SoftReference<Set<String>> REF_SYSTEM_ZONES = null;
    private static final SystemTimeZoneCache SYSTEM_ZONE_CACHE;
    private static String[] ZONEIDS = null;
    private static final String ZONEINFORESNAME = "zoneinfo64";
    private static final String kCUSTOM_TZ_PREFIX = "GMT";
    private static final String kGMT_ID = "GMT";
    private static final int kMAX_CUSTOM_HOUR = 23;
    private static final int kMAX_CUSTOM_MIN = 59;
    private static final int kMAX_CUSTOM_SEC = 59;
    private static final String kNAMES = "Names";
    private static final String kREGIONS = "Regions";
    private static final String kWorld = "001";
    private static final String kZONES = "Zones";
    private static ICUCache<String, String> CANONICAL_ID_CACHE = new SimpleCache();
    private static ICUCache<String, String> REGION_CACHE = new SimpleCache();
    private static ICUCache<String, Boolean> SINGLE_COUNTRY_CACHE = new SimpleCache();

    private static synchronized Set<String> getSystemZIDs() {
        Set<String> setUnmodifiableSet;
        setUnmodifiableSet = REF_SYSTEM_ZONES != null ? REF_SYSTEM_ZONES.get() : null;
        if (setUnmodifiableSet == null) {
            TreeSet treeSet = new TreeSet();
            for (String str : getZoneIDs()) {
                if (!str.equals(TimeZone.UNKNOWN_ZONE_ID)) {
                    treeSet.add(str);
                }
            }
            setUnmodifiableSet = Collections.unmodifiableSet(treeSet);
            REF_SYSTEM_ZONES = new SoftReference<>(setUnmodifiableSet);
        }
        return setUnmodifiableSet;
    }

    private static synchronized Set<String> getCanonicalSystemZIDs() {
        Set<String> setUnmodifiableSet;
        setUnmodifiableSet = REF_CANONICAL_SYSTEM_ZONES != null ? REF_CANONICAL_SYSTEM_ZONES.get() : null;
        if (setUnmodifiableSet == null) {
            TreeSet treeSet = new TreeSet();
            for (String str : getZoneIDs()) {
                if (!str.equals(TimeZone.UNKNOWN_ZONE_ID) && str.equals(getCanonicalCLDRID(str))) {
                    treeSet.add(str);
                }
            }
            setUnmodifiableSet = Collections.unmodifiableSet(treeSet);
            REF_CANONICAL_SYSTEM_ZONES = new SoftReference<>(setUnmodifiableSet);
        }
        return setUnmodifiableSet;
    }

    private static synchronized Set<String> getCanonicalSystemLocationZIDs() {
        Set<String> setUnmodifiableSet;
        String region;
        setUnmodifiableSet = REF_CANONICAL_SYSTEM_LOCATION_ZONES != null ? REF_CANONICAL_SYSTEM_LOCATION_ZONES.get() : null;
        if (setUnmodifiableSet == null) {
            TreeSet treeSet = new TreeSet();
            for (String str : getZoneIDs()) {
                if (!str.equals(TimeZone.UNKNOWN_ZONE_ID) && str.equals(getCanonicalCLDRID(str)) && (region = getRegion(str)) != null && !region.equals("001")) {
                    treeSet.add(str);
                }
            }
            setUnmodifiableSet = Collections.unmodifiableSet(treeSet);
            REF_CANONICAL_SYSTEM_LOCATION_ZONES = new SoftReference<>(setUnmodifiableSet);
        }
        return setUnmodifiableSet;
    }

    /* renamed from: com.ibm.icu.impl.ZoneMeta$1, reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$ibm$icu$util$TimeZone$SystemTimeZoneType;

        static {
            int[] iArr = new int[TimeZone.SystemTimeZoneType.values().length];
            $SwitchMap$com$ibm$icu$util$TimeZone$SystemTimeZoneType = iArr;
            try {
                iArr[TimeZone.SystemTimeZoneType.ANY.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$ibm$icu$util$TimeZone$SystemTimeZoneType[TimeZone.SystemTimeZoneType.CANONICAL.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$ibm$icu$util$TimeZone$SystemTimeZoneType[TimeZone.SystemTimeZoneType.CANONICAL_LOCATION.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
        }
    }

    public static Set<String> getAvailableIDs(TimeZone.SystemTimeZoneType systemTimeZoneType, String str, Integer num) {
        Set<String> systemZIDs;
        OlsonTimeZone systemTimeZone;
        int i = AnonymousClass1.$SwitchMap$com$ibm$icu$util$TimeZone$SystemTimeZoneType[systemTimeZoneType.ordinal()];
        if (i == 1) {
            systemZIDs = getSystemZIDs();
        } else if (i == 2) {
            systemZIDs = getCanonicalSystemZIDs();
        } else if (i == 3) {
            systemZIDs = getCanonicalSystemLocationZIDs();
        } else {
            throw new IllegalArgumentException("Unknown SystemTimeZoneType");
        }
        if (str == null && num == null) {
            return systemZIDs;
        }
        if (str != null) {
            str = str.toUpperCase(Locale.ENGLISH);
        }
        TreeSet treeSet = new TreeSet();
        for (String str2 : systemZIDs) {
            if (str == null || str.equals(getRegion(str2))) {
                if (num == null || ((systemTimeZone = getSystemTimeZone(str2)) != null && num.equals(Integer.valueOf(systemTimeZone.getRawOffset())))) {
                    treeSet.add(str2);
                }
            }
        }
        if (treeSet.isEmpty()) {
            return Collections.emptySet();
        }
        return Collections.unmodifiableSet(treeSet);
    }

    public static synchronized int countEquivalentIDs(String str) {
        int length;
        length = 0;
        UResourceBundle uResourceBundleOpenOlsonResource = openOlsonResource(null, str);
        if (uResourceBundleOpenOlsonResource != null) {
            try {
                length = uResourceBundleOpenOlsonResource.get("links").getIntVector().length;
            } catch (MissingResourceException unused) {
            }
        }
        return length;
    }

    public static synchronized String getEquivalentID(String str, int i) {
        String str2;
        str2 = "";
        if (i >= 0) {
            UResourceBundle uResourceBundleOpenOlsonResource = openOlsonResource(null, str);
            if (uResourceBundleOpenOlsonResource != null) {
                int i2 = -1;
                try {
                    int[] intVector = uResourceBundleOpenOlsonResource.get("links").getIntVector();
                    if (i < intVector.length) {
                        i2 = intVector[i];
                    }
                } catch (MissingResourceException unused) {
                }
                if (i2 >= 0) {
                    String zoneID = getZoneID(i2);
                    if (zoneID != null) {
                        str2 = zoneID;
                    }
                }
            }
        }
        return str2;
    }

    private static synchronized String[] getZoneIDs() {
        if (ZONEIDS == null) {
            try {
                ZONEIDS = UResourceBundle.getBundleInstance(ICUData.ICU_BASE_NAME, ZONEINFORESNAME, ICUResourceBundle.ICU_DATA_CLASS_LOADER).getStringArray(kNAMES);
            } catch (MissingResourceException unused) {
            }
        }
        if (ZONEIDS == null) {
            ZONEIDS = new String[0];
        }
        return ZONEIDS;
    }

    private static String getZoneID(int i) {
        if (i < 0) {
            return null;
        }
        String[] zoneIDs = getZoneIDs();
        if (i < zoneIDs.length) {
            return zoneIDs[i];
        }
        return null;
    }

    private static int getZoneIndex(String str) {
        String[] zoneIDs = getZoneIDs();
        if (zoneIDs.length > 0) {
            int i = 0;
            int length = zoneIDs.length;
            int i2 = Integer.MAX_VALUE;
            while (true) {
                int i3 = (i + length) / 2;
                if (i2 == i3) {
                    break;
                }
                int iCompareTo = str.compareTo(zoneIDs[i3]);
                if (iCompareTo == 0) {
                    return i3;
                }
                if (iCompareTo < 0) {
                    length = i3;
                } else {
                    i = i3;
                }
                i2 = i3;
            }
        }
        return -1;
    }

    static {
        AnonymousClass1 anonymousClass1 = null;
        SYSTEM_ZONE_CACHE = new SystemTimeZoneCache(anonymousClass1);
        CUSTOM_ZONE_CACHE = new CustomTimeZoneCache(anonymousClass1);
    }

    public static String getCanonicalCLDRID(TimeZone timeZone) {
        if (timeZone instanceof OlsonTimeZone) {
            return ((OlsonTimeZone) timeZone).getCanonicalID();
        }
        return getCanonicalCLDRID(timeZone.getID());
    }

    public static String getCanonicalCLDRID(String str) {
        String strFindCLDRCanonicalID = CANONICAL_ID_CACHE.get(str);
        if (strFindCLDRCanonicalID == null) {
            strFindCLDRCanonicalID = findCLDRCanonicalID(str);
            if (strFindCLDRCanonicalID == null) {
                try {
                    int zoneIndex = getZoneIndex(str);
                    if (zoneIndex >= 0) {
                        UResourceBundle uResourceBundle = UResourceBundle.getBundleInstance(ICUData.ICU_BASE_NAME, ZONEINFORESNAME, ICUResourceBundle.ICU_DATA_CLASS_LOADER).get(kZONES).get(zoneIndex);
                        if (uResourceBundle.getType() == 7) {
                            str = getZoneID(uResourceBundle.getInt());
                            strFindCLDRCanonicalID = findCLDRCanonicalID(str);
                        }
                        if (strFindCLDRCanonicalID == null) {
                            strFindCLDRCanonicalID = str;
                        }
                    }
                } catch (MissingResourceException unused) {
                }
            }
            if (strFindCLDRCanonicalID != null) {
                CANONICAL_ID_CACHE.put(str, strFindCLDRCanonicalID);
            }
        }
        return strFindCLDRCanonicalID;
    }

    private static String findCLDRCanonicalID(String str) {
        String strReplace = str.replace(IOUtils.DIR_SEPARATOR_UNIX, ':');
        String str2 = null;
        try {
            UResourceBundle bundleInstance = UResourceBundle.getBundleInstance(ICUData.ICU_BASE_NAME, "keyTypeData", ICUResourceBundle.ICU_DATA_CLASS_LOADER);
            try {
                bundleInstance.get("typeMap").get("timezone").get(strReplace);
            } catch (MissingResourceException unused) {
                str = null;
            }
            if (str != null) {
                return str;
            }
            try {
                return bundleInstance.get("typeAlias").get("timezone").getString(strReplace);
            } catch (MissingResourceException unused2) {
                str2 = str;
                return str2;
            }
        } catch (MissingResourceException unused3) {
        }
    }

    public static String getRegion(String str) {
        int zoneIndex;
        String string = REGION_CACHE.get(str);
        if (string == null && (zoneIndex = getZoneIndex(str)) >= 0) {
            try {
                UResourceBundle uResourceBundle = UResourceBundle.getBundleInstance(ICUData.ICU_BASE_NAME, ZONEINFORESNAME, ICUResourceBundle.ICU_DATA_CLASS_LOADER).get(kREGIONS);
                if (zoneIndex < uResourceBundle.getSize()) {
                    string = uResourceBundle.getString(zoneIndex);
                }
            } catch (MissingResourceException unused) {
            }
            if (string != null) {
                REGION_CACHE.put(str, string);
            }
        }
        return string;
    }

    public static String getCanonicalCountry(String str) {
        String region = getRegion(str);
        if (region == null || !region.equals("001")) {
            return region;
        }
        return null;
    }

    /* JADX WARN: Type inference failed for: r0v0, types: [T, java.lang.Boolean] */
    /* JADX WARN: Type inference failed for: r3v3, types: [T, java.lang.Boolean] */
    /* JADX WARN: Type inference failed for: r3v4, types: [T, java.lang.Boolean] */
    /* JADX WARN: Type inference failed for: r3v5, types: [T, java.lang.Boolean] */
    public static String getCanonicalCountry(String str, Output<Boolean> output) {
        output.value = Boolean.FALSE;
        String region = getRegion(str);
        if (region != null && region.equals("001")) {
            return null;
        }
        Boolean boolValueOf = SINGLE_COUNTRY_CACHE.get(str);
        if (boolValueOf == null) {
            boolValueOf = Boolean.valueOf(TimeZone.getAvailableIDs(TimeZone.SystemTimeZoneType.CANONICAL_LOCATION, region, null).size() <= 1);
            SINGLE_COUNTRY_CACHE.put(str, boolValueOf);
        }
        if (boolValueOf.booleanValue()) {
            output.value = Boolean.TRUE;
        } else {
            try {
                String string = UResourceBundle.getBundleInstance(ICUData.ICU_BASE_NAME, "metaZones").get("primaryZones").getString(region);
                if (str.equals(string)) {
                    output.value = Boolean.TRUE;
                } else {
                    String canonicalCLDRID = getCanonicalCLDRID(str);
                    if (canonicalCLDRID != null && canonicalCLDRID.equals(string)) {
                        output.value = Boolean.TRUE;
                    }
                }
            } catch (MissingResourceException unused) {
            }
        }
        return region;
    }

    public static UResourceBundle openOlsonResource(UResourceBundle uResourceBundle, String str) {
        int zoneIndex = getZoneIndex(str);
        if (zoneIndex < 0) {
            return null;
        }
        if (uResourceBundle == null) {
            try {
                uResourceBundle = UResourceBundle.getBundleInstance(ICUData.ICU_BASE_NAME, ZONEINFORESNAME, ICUResourceBundle.ICU_DATA_CLASS_LOADER);
            } catch (MissingResourceException unused) {
                return null;
            }
        }
        UResourceBundle uResourceBundle2 = uResourceBundle.get(kZONES);
        UResourceBundle uResourceBundle3 = uResourceBundle2.get(zoneIndex);
        return uResourceBundle3.getType() == 7 ? uResourceBundle2.get(uResourceBundle3.getInt()) : uResourceBundle3;
    }

    private static class SystemTimeZoneCache extends SoftCache<String, OlsonTimeZone, String> {
        private SystemTimeZoneCache() {
        }

        /* synthetic */ SystemTimeZoneCache(AnonymousClass1 anonymousClass1) {
            this();
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.ibm.icu.impl.CacheBase
        public OlsonTimeZone createInstance(String str, String str2) {
            try {
                UResourceBundle bundleInstance = UResourceBundle.getBundleInstance(ICUData.ICU_BASE_NAME, ZoneMeta.ZONEINFORESNAME, ICUResourceBundle.ICU_DATA_CLASS_LOADER);
                UResourceBundle uResourceBundleOpenOlsonResource = ZoneMeta.openOlsonResource(bundleInstance, str2);
                if (uResourceBundleOpenOlsonResource == null) {
                    return null;
                }
                OlsonTimeZone olsonTimeZone = new OlsonTimeZone(bundleInstance, uResourceBundleOpenOlsonResource, str2);
                try {
                    olsonTimeZone.freeze();
                } catch (MissingResourceException unused) {
                }
                return olsonTimeZone;
            } catch (MissingResourceException unused2) {
                return null;
            }
        }
    }

    public static OlsonTimeZone getSystemTimeZone(String str) {
        return SYSTEM_ZONE_CACHE.getInstance(str, str);
    }

    private static class CustomTimeZoneCache extends SoftCache<Integer, SimpleTimeZone, int[]> {
        static final /* synthetic */ boolean $assertionsDisabled = false;

        private CustomTimeZoneCache() {
        }

        /* synthetic */ CustomTimeZoneCache(AnonymousClass1 anonymousClass1) {
            this();
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.ibm.icu.impl.CacheBase
        public SimpleTimeZone createInstance(Integer num, int[] iArr) {
            SimpleTimeZone simpleTimeZone = new SimpleTimeZone(iArr[0] * ((((iArr[1] * 60) + iArr[2]) * 60) + iArr[3]) * 1000, ZoneMeta.formatCustomID(iArr[1], iArr[2], iArr[3], iArr[0] < 0));
            simpleTimeZone.freeze();
            return simpleTimeZone;
        }
    }

    public static SimpleTimeZone getCustomTimeZone(String str) {
        int[] iArr = new int[4];
        if (!parseCustomID(str, iArr)) {
            return null;
        }
        return CUSTOM_ZONE_CACHE.getInstance(Integer.valueOf(iArr[0] * (iArr[1] | (iArr[2] << 5) | (iArr[3] << 11))), iArr);
    }

    public static String getCustomID(String str) {
        int[] iArr = new int[4];
        if (parseCustomID(str, iArr)) {
            return formatCustomID(iArr[1], iArr[2], iArr[3], iArr[0] < 0);
        }
        return null;
    }

    /* JADX WARN: Removed duplicated region for block: B:56:0x00fe  */
    /* JADX WARN: Removed duplicated region for block: B:60:0x0106  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    static boolean parseCustomID(java.lang.String r13, int[] r14) {
        /*
            Method dump skipped, instructions count: 284
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ibm.icu.impl.ZoneMeta.parseCustomID(java.lang.String, int[]):boolean");
    }

    public static SimpleTimeZone getCustomTimeZone(int i) {
        boolean z;
        int i2;
        if (i < 0) {
            z = true;
            i2 = -i;
        } else {
            z = false;
            i2 = i;
        }
        int i3 = i2 / 1000;
        int i4 = i3 % 60;
        int i5 = i3 / 60;
        return new SimpleTimeZone(i, formatCustomID(i5 / 60, i5 % 60, i4, z));
    }

    static String formatCustomID(int i, int i2, int i3, boolean z) {
        StringBuilder sb = new StringBuilder("GMT");
        if (i != 0 || i2 != 0) {
            if (z) {
                sb.append(Soundex.SILENT_MARKER);
            } else {
                sb.append('+');
            }
            if (i < 10) {
                sb.append('0');
            }
            sb.append(i);
            sb.append(':');
            if (i2 < 10) {
                sb.append('0');
            }
            sb.append(i2);
            if (i3 != 0) {
                sb.append(':');
                if (i3 < 10) {
                    sb.append('0');
                }
                sb.append(i3);
            }
        }
        return sb.toString();
    }

    public static String getShortID(TimeZone timeZone) {
        String canonicalCLDRID;
        if (timeZone instanceof OlsonTimeZone) {
            canonicalCLDRID = ((OlsonTimeZone) timeZone).getCanonicalID();
        } else {
            canonicalCLDRID = getCanonicalCLDRID(timeZone.getID());
        }
        if (canonicalCLDRID == null) {
            return null;
        }
        return getShortIDFromCanonical(canonicalCLDRID);
    }

    public static String getShortID(String str) {
        String canonicalCLDRID = getCanonicalCLDRID(str);
        if (canonicalCLDRID == null) {
            return null;
        }
        return getShortIDFromCanonical(canonicalCLDRID);
    }

    private static String getShortIDFromCanonical(String str) {
        try {
            return UResourceBundle.getBundleInstance(ICUData.ICU_BASE_NAME, "keyTypeData", ICUResourceBundle.ICU_DATA_CLASS_LOADER).get("typeMap").get("timezone").getString(str.replace(IOUtils.DIR_SEPARATOR_UNIX, ':'));
        } catch (MissingResourceException unused) {
            return null;
        }
    }
}
