package com.ibm.icu.impl.locale;

import java.util.Objects;
import org.apache.commons.codec.language.Soundex;

/* loaded from: classes2.dex */
public final class LSR {
    public static final boolean DEBUG_OUTPUT = false;
    public static final int DONT_CARE_FLAGS = 0;
    public static final int EXPLICIT_LANGUAGE = 4;
    public static final int EXPLICIT_LSR = 7;
    public static final int EXPLICIT_REGION = 1;
    public static final int EXPLICIT_SCRIPT = 2;
    public static final int IMPLICIT_LSR = 0;
    public static final int REGION_INDEX_LIMIT = 1677;
    public final int flags;
    public final String language;
    public final String region;
    final int regionIndex;
    public final String script;

    public LSR(String str, String str2, String str3, int i) {
        this.language = str;
        this.script = str2;
        this.region = str3;
        this.regionIndex = indexForRegion(str3);
        this.flags = i;
    }

    public static final int indexForRegion(String str) {
        int iCharAt;
        int iCharAt2;
        int iCharAt3;
        int iCharAt4;
        if (str.length() == 2) {
            int iCharAt5 = str.charAt(0) - 'A';
            if (iCharAt5 < 0 || 25 < iCharAt5 || str.charAt(1) - 'A' < 0 || 25 < iCharAt4) {
                return 0;
            }
            return (iCharAt5 * 26) + iCharAt4 + 1001;
        }
        if (str.length() != 3 || str.charAt(0) - '0' < 0 || 9 < iCharAt || str.charAt(1) - '0' < 0 || 9 < iCharAt2 || str.charAt(2) - '0' < 0 || 9 < iCharAt3) {
            return 0;
        }
        return (((iCharAt * 10) + iCharAt2) * 10) + iCharAt3 + 1;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder(this.language);
        if (!this.script.isEmpty()) {
            sb.append(Soundex.SILENT_MARKER);
            sb.append(this.script);
        }
        if (!this.region.isEmpty()) {
            sb.append(Soundex.SILENT_MARKER);
            sb.append(this.region);
        }
        return sb.toString();
    }

    public boolean isEquivalentTo(LSR lsr) {
        return this.language.equals(lsr.language) && this.script.equals(lsr.script) && this.region.equals(lsr.region);
    }

    public boolean equals(Object obj) {
        if (this != obj) {
            if (obj != null && obj.getClass() == getClass()) {
                LSR lsr = (LSR) obj;
                if (!this.language.equals(lsr.language) || !this.script.equals(lsr.script) || !this.region.equals(lsr.region) || this.flags != lsr.flags) {
                }
            }
            return false;
        }
        return true;
    }

    public int hashCode() {
        return Objects.hash(this.language, this.script, this.region, Integer.valueOf(this.flags));
    }
}
