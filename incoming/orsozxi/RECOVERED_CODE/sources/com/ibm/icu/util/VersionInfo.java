package com.ibm.icu.util;

import com.ibm.icu.impl.ICUData;
import com.ibm.icu.text.DateFormat;
import java.util.concurrent.ConcurrentHashMap;

/* loaded from: classes2.dex */
public final class VersionInfo implements Comparable<VersionInfo> {

    @Deprecated
    public static final VersionInfo ICU_DATA_VERSION;

    @Deprecated
    public static final String ICU_DATA_VERSION_PATH = "69b";
    public static final VersionInfo ICU_VERSION;
    private static final String INVALID_VERSION_NUMBER_ = "Invalid version number: Version number may be negative or greater than 255";
    private static final int LAST_BYTE_MASK_ = 255;
    private static volatile String TZDATA_VERSION;
    public static final VersionInfo UCOL_BUILDER_VERSION;
    public static final VersionInfo UCOL_RUNTIME_VERSION;

    @Deprecated
    public static final VersionInfo UCOL_TAILORINGS_VERSION;
    private static final VersionInfo UNICODE_VERSION;
    private int m_version_;
    private static final ConcurrentHashMap<Integer, VersionInfo> MAP_ = new ConcurrentHashMap<>();
    public static final VersionInfo UNICODE_1_0 = getInstance(1, 0, 0, 0);
    public static final VersionInfo UNICODE_1_0_1 = getInstance(1, 0, 1, 0);
    public static final VersionInfo UNICODE_1_1_0 = getInstance(1, 1, 0, 0);
    public static final VersionInfo UNICODE_1_1_5 = getInstance(1, 1, 5, 0);
    public static final VersionInfo UNICODE_2_0 = getInstance(2, 0, 0, 0);
    public static final VersionInfo UNICODE_2_1_2 = getInstance(2, 1, 2, 0);
    public static final VersionInfo UNICODE_2_1_5 = getInstance(2, 1, 5, 0);
    public static final VersionInfo UNICODE_2_1_8 = getInstance(2, 1, 8, 0);
    public static final VersionInfo UNICODE_2_1_9 = getInstance(2, 1, 9, 0);
    public static final VersionInfo UNICODE_3_0 = getInstance(3, 0, 0, 0);
    public static final VersionInfo UNICODE_3_0_1 = getInstance(3, 0, 1, 0);
    public static final VersionInfo UNICODE_3_1_0 = getInstance(3, 1, 0, 0);
    public static final VersionInfo UNICODE_3_1_1 = getInstance(3, 1, 1, 0);
    public static final VersionInfo UNICODE_3_2 = getInstance(3, 2, 0, 0);
    public static final VersionInfo UNICODE_4_0 = getInstance(4, 0, 0, 0);
    public static final VersionInfo UNICODE_4_0_1 = getInstance(4, 0, 1, 0);
    public static final VersionInfo UNICODE_4_1 = getInstance(4, 1, 0, 0);
    public static final VersionInfo UNICODE_5_0 = getInstance(5, 0, 0, 0);
    public static final VersionInfo UNICODE_5_1 = getInstance(5, 1, 0, 0);
    public static final VersionInfo UNICODE_5_2 = getInstance(5, 2, 0, 0);
    public static final VersionInfo UNICODE_6_0 = getInstance(6, 0, 0, 0);
    public static final VersionInfo UNICODE_6_1 = getInstance(6, 1, 0, 0);
    public static final VersionInfo UNICODE_6_2 = getInstance(6, 2, 0, 0);
    public static final VersionInfo UNICODE_6_3 = getInstance(6, 3, 0, 0);
    public static final VersionInfo UNICODE_7_0 = getInstance(7, 0, 0, 0);
    public static final VersionInfo UNICODE_8_0 = getInstance(8, 0, 0, 0);
    public static final VersionInfo UNICODE_9_0 = getInstance(9, 0, 0, 0);
    public static final VersionInfo UNICODE_10_0 = getInstance(10, 0, 0, 0);
    public static final VersionInfo UNICODE_11_0 = getInstance(11, 0, 0, 0);
    public static final VersionInfo UNICODE_12_0 = getInstance(12, 0, 0, 0);
    public static final VersionInfo UNICODE_12_1 = getInstance(12, 1, 0, 0);
    public static final VersionInfo UNICODE_13_0 = getInstance(13, 0, 0, 0);

    private static int getInt(int i, int i2, int i3, int i4) {
        return (i << 24) | (i2 << 16) | (i3 << 8) | i4;
    }

    public boolean equals(Object obj) {
        return obj == this;
    }

    public static VersionInfo getInstance(String str) {
        int length = str.length();
        int[] iArr = {0, 0, 0, 0};
        int i = 0;
        int i2 = 0;
        while (i < 4 && i2 < length) {
            char cCharAt = str.charAt(i2);
            if (cCharAt == '.') {
                i++;
            } else {
                char c = (char) (cCharAt - '0');
                if (c < 0 || c > '\t') {
                    throw new IllegalArgumentException(INVALID_VERSION_NUMBER_);
                }
                iArr[i] = iArr[i] * 10;
                iArr[i] = iArr[i] + c;
            }
            i2++;
        }
        if (i2 != length) {
            throw new IllegalArgumentException("Invalid version number: String '" + str + "' exceeds version format");
        }
        for (int i3 = 0; i3 < 4; i3++) {
            if (iArr[i3] < 0 || iArr[i3] > 255) {
                throw new IllegalArgumentException(INVALID_VERSION_NUMBER_);
            }
        }
        return getInstance(iArr[0], iArr[1], iArr[2], iArr[3]);
    }

    public static VersionInfo getInstance(int i, int i2, int i3, int i4) {
        if (i < 0 || i > 255 || i2 < 0 || i2 > 255 || i3 < 0 || i3 > 255 || i4 < 0 || i4 > 255) {
            throw new IllegalArgumentException(INVALID_VERSION_NUMBER_);
        }
        int i5 = getInt(i, i2, i3, i4);
        Integer numValueOf = Integer.valueOf(i5);
        VersionInfo versionInfo = MAP_.get(numValueOf);
        if (versionInfo != null) {
            return versionInfo;
        }
        VersionInfo versionInfo2 = new VersionInfo(i5);
        VersionInfo versionInfoPutIfAbsent = MAP_.putIfAbsent(numValueOf, versionInfo2);
        return versionInfoPutIfAbsent != null ? versionInfoPutIfAbsent : versionInfo2;
    }

    public static VersionInfo getInstance(int i, int i2, int i3) {
        return getInstance(i, i2, i3, 0);
    }

    public static VersionInfo getInstance(int i, int i2) {
        return getInstance(i, i2, 0, 0);
    }

    public static VersionInfo getInstance(int i) {
        return getInstance(i, 0, 0, 0);
    }

    public String toString() {
        StringBuilder sb = new StringBuilder(7);
        sb.append(getMajor());
        sb.append('.');
        sb.append(getMinor());
        sb.append('.');
        sb.append(getMilli());
        sb.append('.');
        sb.append(getMicro());
        return sb.toString();
    }

    public int getMajor() {
        return (this.m_version_ >> 24) & 255;
    }

    public int getMinor() {
        return (this.m_version_ >> 16) & 255;
    }

    public int getMilli() {
        return (this.m_version_ >> 8) & 255;
    }

    public int getMicro() {
        return this.m_version_ & 255;
    }

    public int hashCode() {
        return this.m_version_;
    }

    @Override // java.lang.Comparable
    public int compareTo(VersionInfo versionInfo) {
        int i = this.m_version_;
        int i2 = versionInfo.m_version_;
        int i3 = (i >>> 1) - (i2 >>> 1);
        return i3 != 0 ? i3 : (i & 1) - (i2 & 1);
    }

    static {
        VersionInfo versionInfo = getInstance(69, 1, 0, 0);
        ICU_VERSION = versionInfo;
        ICU_DATA_VERSION = versionInfo;
        UNICODE_VERSION = UNICODE_13_0;
        UCOL_RUNTIME_VERSION = getInstance(9);
        UCOL_BUILDER_VERSION = getInstance(9);
        UCOL_TAILORINGS_VERSION = getInstance(1);
        TZDATA_VERSION = null;
    }

    private VersionInfo(int i) {
        this.m_version_ = i;
    }

    public static void main(String[] strArr) {
        String versionString;
        if (ICU_VERSION.getMajor() <= 4) {
            if (ICU_VERSION.getMinor() % 2 != 0) {
                int major = ICU_VERSION.getMajor();
                int minor = ICU_VERSION.getMinor() + 1;
                if (minor >= 10) {
                    minor -= 10;
                    major++;
                }
                versionString = "" + major + "." + minor + DateFormat.NUM_MONTH + ICU_VERSION.getMilli();
            } else {
                versionString = ICU_VERSION.getVersionString(2, 2);
            }
        } else if (ICU_VERSION.getMinor() == 0) {
            versionString = "" + ICU_VERSION.getMajor() + DateFormat.NUM_MONTH + ICU_VERSION.getMilli();
        } else {
            versionString = ICU_VERSION.getVersionString(2, 2);
        }
        System.out.println("International Components for Unicode for Java " + versionString);
        System.out.println("");
        System.out.println("Implementation Version: " + ICU_VERSION.getVersionString(2, 4));
        System.out.println("Unicode Data Version:   " + UNICODE_VERSION.getVersionString(2, 4));
        System.out.println("CLDR Data Version:      " + LocaleData.getCLDRVersion().getVersionString(2, 4));
        System.out.println("Time Zone Data Version: " + getTZDataVersion());
    }

    @Deprecated
    public String getVersionString(int i, int i2) {
        if (i < 1 || i2 < 1 || i > 4 || i2 > 4 || i > i2) {
            throw new IllegalArgumentException("Invalid min/maxDigits range");
        }
        int[] iArr = {getMajor(), getMinor(), getMilli(), getMicro()};
        while (i2 > i && iArr[i2 - 1] == 0) {
            i2--;
        }
        StringBuilder sb = new StringBuilder(7);
        sb.append(iArr[0]);
        for (int i3 = 1; i3 < i2; i3++) {
            sb.append(".");
            sb.append(iArr[i3]);
        }
        return sb.toString();
    }

    static String getTZDataVersion() {
        if (TZDATA_VERSION == null) {
            synchronized (VersionInfo.class) {
                if (TZDATA_VERSION == null) {
                    TZDATA_VERSION = UResourceBundle.getBundleInstance(ICUData.ICU_BASE_NAME, "zoneinfo64").getString("TZVersion");
                }
            }
        }
        return TZDATA_VERSION;
    }
}
