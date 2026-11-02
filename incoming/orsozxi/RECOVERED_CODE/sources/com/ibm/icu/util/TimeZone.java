package com.ibm.icu.util;

import com.ibm.icu.impl.Grego;
import com.ibm.icu.impl.ICUConfig;
import com.ibm.icu.impl.ICUData;
import com.ibm.icu.impl.ICUResourceBundle;
import com.ibm.icu.impl.JavaTimeZone;
import com.ibm.icu.impl.OlsonTimeZone;
import com.ibm.icu.impl.TimeZoneAdapter;
import com.ibm.icu.impl.ZoneMeta;
import com.ibm.icu.impl.units.UnitsData;
import com.ibm.icu.util.ULocale;
import java.io.Serializable;
import java.util.Date;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.logging.Logger;

/* loaded from: classes2.dex */
public abstract class TimeZone implements Serializable, Cloneable, Freezable<TimeZone> {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    public static final int GENERIC_LOCATION = 7;
    public static final TimeZone GMT_ZONE;
    static final String GMT_ZONE_ID = "Etc/GMT";
    public static final int LONG = 1;
    public static final int LONG_GENERIC = 3;
    public static final int LONG_GMT = 5;
    public static final int SHORT = 0;
    public static final int SHORT_COMMONLY_USED = 6;
    public static final int SHORT_GENERIC = 2;
    public static final int SHORT_GMT = 4;
    public static final int TIMEZONE_ICU = 0;
    public static final int TIMEZONE_JDK = 1;
    private static final String TZIMPL_CONFIG_ICU = "ICU";
    private static final String TZIMPL_CONFIG_JDK = "JDK";
    private static final String TZIMPL_CONFIG_KEY = "com.ibm.icu.util.TimeZone.DefaultTimeZoneType";
    private static int TZ_IMPL = 0;
    public static final TimeZone UNKNOWN_ZONE;
    public static final String UNKNOWN_ZONE_ID = "Etc/Unknown";
    private static final long serialVersionUID = -744942128318337471L;
    private String ID;
    private static final Logger LOGGER = Logger.getLogger("com.ibm.icu.util.TimeZone");
    private static volatile TimeZone defaultZone = null;

    public enum SystemTimeZoneType {
        ANY,
        CANONICAL,
        CANONICAL_LOCATION
    }

    public abstract int getOffset(int i, int i2, int i3, int i4, int i5, int i6);

    public abstract int getRawOffset();

    public abstract boolean inDaylightTime(Date date);

    public boolean isFrozen() {
        return false;
    }

    public abstract void setRawOffset(int i);

    public abstract boolean useDaylightTime();

    static {
        int i = 0;
        UNKNOWN_ZONE = new ConstantZone(i, UNKNOWN_ZONE_ID).freeze();
        GMT_ZONE = new ConstantZone(i, "Etc/GMT").freeze();
        TZ_IMPL = 0;
        if (ICUConfig.get(TZIMPL_CONFIG_KEY, TZIMPL_CONFIG_ICU).equalsIgnoreCase(TZIMPL_CONFIG_JDK)) {
            TZ_IMPL = 1;
        }
    }

    public TimeZone() {
    }

    @Deprecated
    protected TimeZone(String str) {
        if (str == null) {
            throw null;
        }
        this.ID = str;
    }

    public int getOffset(long j) {
        int[] iArr = new int[2];
        getOffset(j, false, iArr);
        return iArr[0] + iArr[1];
    }

    public void getOffset(long j, boolean z, int[] iArr) {
        iArr[0] = getRawOffset();
        if (!z) {
            j += iArr[0];
        }
        int[] iArr2 = new int[6];
        int i = 0;
        while (true) {
            Grego.timeToFields(j, iArr2);
            iArr[1] = getOffset(1, iArr2[0], iArr2[1], iArr2[2], iArr2[3], iArr2[5]) - iArr[0];
            if (i != 0 || !z || iArr[1] == 0) {
                return;
            }
            j -= iArr[1];
            i++;
        }
    }

    public String getID() {
        return this.ID;
    }

    public void setID(String str) {
        if (str == null) {
            throw null;
        }
        if (isFrozen()) {
            throw new UnsupportedOperationException("Attempt to modify a frozen TimeZone instance.");
        }
        this.ID = str;
    }

    public final String getDisplayName() {
        return _getDisplayName(3, false, ULocale.getDefault(ULocale.Category.DISPLAY));
    }

    public final String getDisplayName(Locale locale) {
        return _getDisplayName(3, false, ULocale.forLocale(locale));
    }

    public final String getDisplayName(ULocale uLocale) {
        return _getDisplayName(3, false, uLocale);
    }

    public final String getDisplayName(boolean z, int i) {
        return getDisplayName(z, i, ULocale.getDefault(ULocale.Category.DISPLAY));
    }

    public String getDisplayName(boolean z, int i, Locale locale) {
        return getDisplayName(z, i, ULocale.forLocale(locale));
    }

    public String getDisplayName(boolean z, int i, ULocale uLocale) {
        if (i < 0 || i > 7) {
            throw new IllegalArgumentException("Illegal style: " + i);
        }
        return _getDisplayName(i, z, uLocale);
    }

    /* JADX WARN: Removed duplicated region for block: B:21:0x002e  */
    /* JADX WARN: Removed duplicated region for block: B:26:0x003f  */
    /* JADX WARN: Removed duplicated region for block: B:71:? A[RETURN, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    private java.lang.String _getDisplayName(int r12, boolean r13, com.ibm.icu.util.ULocale r14) {
        /*
            Method dump skipped, instructions count: 255
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ibm.icu.util.TimeZone._getDisplayName(int, boolean, com.ibm.icu.util.ULocale):java.lang.String");
    }

    public int getDSTSavings() {
        return useDaylightTime() ? 3600000 : 0;
    }

    public boolean observesDaylightTime() {
        return useDaylightTime() || inDaylightTime(new Date());
    }

    public static TimeZone getTimeZone(String str) {
        return getTimeZone(str, TZ_IMPL, false);
    }

    public static TimeZone getFrozenTimeZone(String str) {
        return getTimeZone(str, TZ_IMPL, true);
    }

    public static TimeZone getTimeZone(String str, int i) {
        return getTimeZone(str, i, false);
    }

    private static TimeZone getTimeZone(String str, int i, boolean z) {
        TimeZone frozenICUTimeZone;
        if (i == 1) {
            JavaTimeZone javaTimeZoneCreateTimeZone = JavaTimeZone.createTimeZone(str);
            if (javaTimeZoneCreateTimeZone != null) {
                return z ? javaTimeZoneCreateTimeZone.freeze() : javaTimeZoneCreateTimeZone;
            }
            frozenICUTimeZone = getFrozenICUTimeZone(str, false);
        } else {
            frozenICUTimeZone = getFrozenICUTimeZone(str, true);
        }
        if (frozenICUTimeZone == null) {
            LOGGER.fine("\"" + str + "\" is a bogus id so timezone is falling back to Etc/Unknown(GMT).");
            frozenICUTimeZone = UNKNOWN_ZONE;
        }
        return z ? frozenICUTimeZone : frozenICUTimeZone.cloneAsThawed();
    }

    static BasicTimeZone getFrozenICUTimeZone(String str, boolean z) {
        OlsonTimeZone systemTimeZone = z ? ZoneMeta.getSystemTimeZone(str) : null;
        return systemTimeZone == null ? ZoneMeta.getCustomTimeZone(str) : systemTimeZone;
    }

    public static synchronized void setDefaultTimeZoneType(int i) {
        if (i != 0 && i != 1) {
            throw new IllegalArgumentException("Invalid timezone type");
        }
        TZ_IMPL = i;
    }

    public static int getDefaultTimeZoneType() {
        return TZ_IMPL;
    }

    public static Set<String> getAvailableIDs(SystemTimeZoneType systemTimeZoneType, String str, Integer num) {
        return ZoneMeta.getAvailableIDs(systemTimeZoneType, str, num);
    }

    public static String[] getAvailableIDs(int i) {
        return (String[]) getAvailableIDs(SystemTimeZoneType.ANY, null, Integer.valueOf(i)).toArray(new String[0]);
    }

    public static String[] getAvailableIDs(String str) {
        return (String[]) getAvailableIDs(SystemTimeZoneType.ANY, str, null).toArray(new String[0]);
    }

    public static String[] getAvailableIDs() {
        return (String[]) getAvailableIDs(SystemTimeZoneType.ANY, null, null).toArray(new String[0]);
    }

    public static int countEquivalentIDs(String str) {
        return ZoneMeta.countEquivalentIDs(str);
    }

    public static String getEquivalentID(String str, int i) {
        return ZoneMeta.getEquivalentID(str, i);
    }

    @Deprecated
    public static TimeZone forULocaleOrDefault(ULocale uLocale) {
        String keywordValue = uLocale.getKeywordValue("timezone");
        return keywordValue == null ? getDefault() : getTimeZone(keywordValue);
    }

    @Deprecated
    public static TimeZone forLocaleOrDefault(Locale locale) {
        return forULocaleOrDefault(ULocale.forLocale(locale));
    }

    public static TimeZone getDefault() {
        TimeZone frozenTimeZone;
        TimeZone timeZone = defaultZone;
        if (timeZone == null) {
            synchronized (java.util.TimeZone.class) {
                synchronized (TimeZone.class) {
                    frozenTimeZone = defaultZone;
                    if (frozenTimeZone == null) {
                        if (TZ_IMPL == 1) {
                            frozenTimeZone = new JavaTimeZone();
                        } else {
                            frozenTimeZone = getFrozenTimeZone(java.util.TimeZone.getDefault().getID());
                        }
                        defaultZone = frozenTimeZone;
                    }
                }
            }
            timeZone = frozenTimeZone;
        }
        return timeZone.cloneAsThawed();
    }

    public static synchronized void setDefault(TimeZone timeZone) {
        setICUDefault(timeZone);
        if (timeZone != null) {
            java.util.TimeZone timeZoneWrap = null;
            if (timeZone instanceof JavaTimeZone) {
                timeZoneWrap = ((JavaTimeZone) timeZone).unwrap();
            } else if (timeZone instanceof OlsonTimeZone) {
                String id = timeZone.getID();
                java.util.TimeZone timeZone2 = java.util.TimeZone.getTimeZone(id);
                if (!id.equals(timeZone2.getID())) {
                    String canonicalID = getCanonicalID(id);
                    timeZone2 = java.util.TimeZone.getTimeZone(canonicalID);
                    if (!canonicalID.equals(timeZone2.getID())) {
                    }
                }
                timeZoneWrap = timeZone2;
            }
            if (timeZoneWrap == null) {
                timeZoneWrap = TimeZoneAdapter.wrap(timeZone);
            }
            java.util.TimeZone.setDefault(timeZoneWrap);
        }
    }

    @Deprecated
    public static synchronized void setICUDefault(TimeZone timeZone) {
        if (timeZone == null) {
            defaultZone = null;
        } else if (timeZone.isFrozen()) {
            defaultZone = timeZone;
        } else {
            defaultZone = ((TimeZone) timeZone.clone()).freeze();
        }
    }

    public boolean hasSameRules(TimeZone timeZone) {
        return timeZone != null && getRawOffset() == timeZone.getRawOffset() && useDaylightTime() == timeZone.useDaylightTime();
    }

    public Object clone() {
        return isFrozen() ? this : cloneAsThawed();
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        return this.ID.equals(((TimeZone) obj).ID);
    }

    public int hashCode() {
        return this.ID.hashCode();
    }

    public static String getTZDataVersion() {
        return VersionInfo.getTZDataVersion();
    }

    public static String getCanonicalID(String str) {
        return getCanonicalID(str, null);
    }

    /* JADX WARN: Removed duplicated region for block: B:16:0x0023  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public static java.lang.String getCanonicalID(java.lang.String r3, boolean[] r4) {
        /*
            java.lang.String r0 = "Etc/Unknown"
            r1 = 0
            if (r3 == 0) goto L1f
            int r2 = r3.length()
            if (r2 == 0) goto L1f
            boolean r2 = r3.equals(r0)
            if (r2 == 0) goto L12
            goto L20
        L12:
            java.lang.String r0 = com.ibm.icu.impl.ZoneMeta.getCanonicalCLDRID(r3)
            if (r0 == 0) goto L1a
            r3 = 1
            goto L21
        L1a:
            java.lang.String r0 = com.ibm.icu.impl.ZoneMeta.getCustomID(r3)
            goto L20
        L1f:
            r0 = 0
        L20:
            r3 = 0
        L21:
            if (r4 == 0) goto L25
            r4[r1] = r3
        L25:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ibm.icu.util.TimeZone.getCanonicalID(java.lang.String, boolean[]):java.lang.String");
    }

    public static String getRegion(String str) {
        String region = !str.equals(UNKNOWN_ZONE_ID) ? ZoneMeta.getRegion(str) : null;
        if (region != null) {
            return region;
        }
        throw new IllegalArgumentException("Unknown system zone id: " + str);
    }

    public static String getWindowsID(String str) throws NoSuchElementException {
        boolean[] zArr = {false};
        String canonicalID = getCanonicalID(str, zArr);
        if (!zArr[0]) {
            return null;
        }
        UResourceBundleIterator iterator = UResourceBundle.getBundleInstance(ICUData.ICU_BASE_NAME, "windowsZones", ICUResourceBundle.ICU_DATA_CLASS_LOADER).get("mapTimezones").getIterator();
        while (iterator.hasNext()) {
            UResourceBundle next = iterator.next();
            if (next.getType() == 2) {
                UResourceBundleIterator iterator2 = next.getIterator();
                while (iterator2.hasNext()) {
                    UResourceBundle next2 = iterator2.next();
                    if (next2.getType() == 0) {
                        for (String str2 : next2.getString().split(" ")) {
                            if (str2.equals(canonicalID)) {
                                return next.getKey();
                            }
                        }
                    }
                }
            }
        }
        return null;
    }

    public static String getIDForWindowsID(String str, String str2) {
        String str3 = null;
        try {
            UResourceBundle uResourceBundle = UResourceBundle.getBundleInstance(ICUData.ICU_BASE_NAME, "windowsZones", ICUResourceBundle.ICU_DATA_CLASS_LOADER).get("mapTimezones").get(str);
            if (str2 != null) {
                try {
                    String string = uResourceBundle.getString(str2);
                    if (string != null) {
                        try {
                            int iIndexOf = string.indexOf(32);
                            if (iIndexOf > 0) {
                                string = string.substring(0, iIndexOf);
                            }
                        } catch (MissingResourceException unused) {
                        }
                    }
                    str3 = string;
                } catch (MissingResourceException unused2) {
                }
            }
            return str3 == null ? uResourceBundle.getString(UnitsData.Constants.DEFAULT_REGION) : str3;
        } catch (MissingResourceException unused3) {
            return null;
        }
    }

    public TimeZone freeze() {
        throw new UnsupportedOperationException("Needs to be implemented by the subclass.");
    }

    public TimeZone cloneAsThawed() {
        try {
            return (TimeZone) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new ICUCloneNotSupportedException(e);
        }
    }

    private static final class ConstantZone extends TimeZone {
        private static final long serialVersionUID = 1;
        private volatile transient boolean isFrozen;
        private int rawOffset;

        @Override // com.ibm.icu.util.TimeZone
        public boolean inDaylightTime(Date date) {
            return false;
        }

        @Override // com.ibm.icu.util.TimeZone
        public boolean useDaylightTime() {
            return false;
        }

        private ConstantZone(int i, String str) {
            super(str);
            this.isFrozen = false;
            this.rawOffset = i;
        }

        @Override // com.ibm.icu.util.TimeZone
        public int getOffset(int i, int i2, int i3, int i4, int i5, int i6) {
            return this.rawOffset;
        }

        @Override // com.ibm.icu.util.TimeZone
        public void setRawOffset(int i) {
            if (isFrozen()) {
                throw new UnsupportedOperationException("Attempt to modify a frozen TimeZone instance.");
            }
            this.rawOffset = i;
        }

        @Override // com.ibm.icu.util.TimeZone
        public int getRawOffset() {
            return this.rawOffset;
        }

        @Override // com.ibm.icu.util.TimeZone, com.ibm.icu.util.Freezable
        public boolean isFrozen() {
            return this.isFrozen;
        }

        @Override // com.ibm.icu.util.TimeZone, com.ibm.icu.util.Freezable
        public TimeZone freeze() {
            this.isFrozen = true;
            return this;
        }

        @Override // com.ibm.icu.util.TimeZone, com.ibm.icu.util.Freezable
        public TimeZone cloneAsThawed() {
            ConstantZone constantZone = (ConstantZone) super.cloneAsThawed();
            constantZone.isFrozen = false;
            return constantZone;
        }
    }
}
