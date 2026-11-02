package com.ibm.icu.impl.coll;

import com.ibm.icu.impl.Normalizer2Impl;
import it.piapostolos.android.pinotes.R2;

/* loaded from: classes2.dex */
public final class CollationFastLatin {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    static final int BAIL_OUT = 1;
    public static final int BAIL_OUT_RESULT = -2;
    static final int CASE_AND_TERTIARY_MASK = 31;
    static final int CASE_MASK = 24;
    static final int COMMON_SEC = 160;
    static final int COMMON_SEC_PLUS_OFFSET = 192;
    static final int COMMON_TER = 0;
    static final int COMMON_TER_PLUS_OFFSET = 32;
    static final int CONTRACTION = 1024;
    static final int CONTR_CHAR_MASK = 511;
    static final int CONTR_LENGTH_SHIFT = 9;
    static final int EOS = 2;
    static final int EXPANSION = 2048;
    static final int INDEX_MASK = 1023;
    public static final int LATIN_LIMIT = 384;
    public static final int LATIN_MAX = 383;
    static final int LATIN_MAX_UTF8_LEAD = 197;
    static final int LONG_INC = 8;
    static final int LONG_PRIMARY_MASK = 65528;
    static final int LOWER_CASE = 8;
    static final int MAX_LONG = 4088;
    static final int MAX_SEC_AFTER = 352;
    static final int MAX_SEC_BEFORE = 128;
    static final int MAX_SEC_HIGH = 992;
    static final int MAX_SHORT = 64512;
    static final int MAX_TER_AFTER = 7;
    static final int MERGE_WEIGHT = 3;
    static final int MIN_LONG = 3072;
    static final int MIN_SEC_AFTER = 192;
    static final int MIN_SEC_BEFORE = 0;
    static final int MIN_SEC_HIGH = 384;
    static final int MIN_SHORT = 4096;
    static final int NUM_FAST_CHARS = 448;
    static final int PUNCT_LIMIT = 8256;
    static final int PUNCT_START = 8192;
    static final int SECONDARY_MASK = 992;
    static final int SEC_INC = 32;
    static final int SEC_OFFSET = 32;
    static final int SHORT_INC = 1024;
    static final int SHORT_PRIMARY_MASK = 64512;
    static final int TERTIARY_MASK = 7;
    static final int TER_OFFSET = 32;
    static final int TWO_CASES_MASK = 1572888;
    static final int TWO_COMMON_SEC_PLUS_OFFSET = 12583104;
    static final int TWO_COMMON_TER_PLUS_OFFSET = 2097184;
    static final int TWO_LONG_PRIMARIES_MASK = -458760;
    static final int TWO_LOWER_CASES = 524296;
    static final int TWO_SECONDARIES_MASK = 65012704;
    static final int TWO_SEC_OFFSETS = 2097184;
    static final int TWO_SHORT_PRIMARIES_MASK = -67044352;
    static final int TWO_TERTIARIES_MASK = 458759;
    static final int TWO_TER_OFFSETS = 2097184;
    public static final int VERSION = 2;

    private static int getCases(int i, boolean z, int i2) {
        if (i2 > 65535) {
            int i3 = 65535 & i2;
            if (i3 >= 4096) {
                return (z && ((-67108864) & i2) == 0) ? i2 & 24 : i2 & TWO_CASES_MASK;
            }
            if (i3 > i) {
                return TWO_LOWER_CASES;
            }
        } else {
            if (i2 >= 4096) {
                int i4 = i2 & 24;
                if (!z && (i2 & R2.attr.spSelectedOptionTextColor) >= 384) {
                    i4 |= 524288;
                }
                return i4;
            }
            if (i2 > i) {
                return 8;
            }
            if (i2 < 3072) {
                return i2;
            }
        }
        return 0;
    }

    static int getCharIndex(char c) {
        if (c <= 383) {
            return c;
        }
        if (8192 > c || c >= PUNCT_LIMIT) {
            return -1;
        }
        return c - 7808;
    }

    private static int getPrimaries(int i, int i2) {
        int i3;
        int i4 = 65535 & i2;
        if (i4 >= 4096) {
            i3 = TWO_SHORT_PRIMARIES_MASK;
        } else {
            if (i4 <= i) {
                if (i4 >= 3072) {
                    return 0;
                }
                return i2;
            }
            i3 = TWO_LONG_PRIMARIES_MASK;
        }
        return i3 & i2;
    }

    private static int getQuaternaries(int i, int i2) {
        int i3;
        if (i2 > 65535) {
            if ((i2 & 65535) <= i) {
                i3 = TWO_LONG_PRIMARIES_MASK;
                return i2 & i3;
            }
            return TWO_SHORT_PRIMARIES_MASK;
        }
        if (i2 >= 4096) {
            if ((i2 & R2.attr.spSelectedOptionTextColor) >= 384) {
                return TWO_SHORT_PRIMARIES_MASK;
            }
        } else if (i2 <= i) {
            if (i2 < 3072) {
                return i2;
            }
            i3 = LONG_PRIMARY_MASK;
            return i2 & i3;
        }
        return Normalizer2Impl.MIN_NORMAL_MAYBE_YES;
    }

    private static int getSecondariesFromOneShortCE(int i) {
        int i2 = i & R2.attr.spSelectedOptionTextColor;
        return i2 < 384 ? i2 + 32 : ((i2 + 32) << 16) | 192;
    }

    private static int getTertiaries(int i, boolean z, int i2) {
        int i3;
        int i4;
        if (i2 > 65535) {
            int i5 = 65535 & i2;
            if (i5 >= 4096) {
                return (z ? 2031647 & i2 : i2 & TWO_TERTIARIES_MASK) + 2097184;
            }
            if (i5 > i) {
                int i6 = (i2 & TWO_TERTIARIES_MASK) + 2097184;
                return z ? i6 | TWO_LOWER_CASES : i6;
            }
        } else {
            if (i2 >= 4096) {
                if (z) {
                    i3 = (i2 & 31) + 32;
                    if ((i2 & R2.attr.spSelectedOptionTextColor) >= 384) {
                        i4 = 2621440;
                        return i4 | i3;
                    }
                    return i3;
                }
                i3 = (i2 & 7) + 32;
                if ((i2 & R2.attr.spSelectedOptionTextColor) >= 384) {
                    i4 = 2097152;
                    return i4 | i3;
                }
                return i3;
            }
            if (i2 > i) {
                int i7 = (i2 & 7) + 32;
                return z ? i7 | 8 : i7;
            }
            if (i2 < 3072) {
                return i2;
            }
        }
        return 0;
    }

    /* JADX WARN: Removed duplicated region for block: B:48:0x0089  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public static int getOptions(com.ibm.icu.impl.coll.CollationData r20, com.ibm.icu.impl.coll.CollationSettings r21, char[] r22) {
        /*
            Method dump skipped, instructions count: 193
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ibm.icu.impl.coll.CollationFastLatin.getOptions(com.ibm.icu.impl.coll.CollationData, com.ibm.icu.impl.coll.CollationSettings, char[]):int");
    }

    /* JADX WARN: Code restructure failed: missing block: B:236:0x02ca, code lost:
    
        r7 = 2;
     */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:311:0x00e5 A[EDGE_INSN: B:311:0x00e5->B:70:0x00e5 BREAK  A[LOOP:1: B:4:0x0012->B:303:0x0393], SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:314:0x00e3 A[EDGE_INSN: B:314:0x00e3->B:69:0x00e3 BREAK  A[LOOP:2: B:36:0x0082->B:68:0x00dd], SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:37:0x0084  */
    /* JADX WARN: Removed duplicated region for block: B:74:0x00ee  */
    /* JADX WARN: Type inference failed for: r10v0 */
    /* JADX WARN: Type inference failed for: r10v1 */
    /* JADX WARN: Type inference failed for: r10v2 */
    /* JADX WARN: Type inference failed for: r10v29, types: [int] */
    /* JADX WARN: Type inference failed for: r10v3, types: [int] */
    /* JADX WARN: Type inference failed for: r10v30 */
    /* JADX WARN: Type inference failed for: r10v36 */
    /* JADX WARN: Type inference failed for: r10v38 */
    /* JADX WARN: Type inference failed for: r10v40, types: [int] */
    /* JADX WARN: Type inference failed for: r10v41, types: [char] */
    /* JADX WARN: Type inference failed for: r10v44 */
    /* JADX WARN: Type inference failed for: r10v45 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public static int compareUTF16(char[] r24, char[] r25, int r26, java.lang.CharSequence r27, java.lang.CharSequence r28, int r29) {
        /*
            Method dump skipped, instructions count: 920
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ibm.icu.impl.coll.CollationFastLatin.compareUTF16(char[], char[], int, java.lang.CharSequence, java.lang.CharSequence, int):int");
    }

    private static int lookup(char[] cArr, int i) {
        if (8192 <= i && i < PUNCT_LIMIT) {
            return cArr[(i - 8192) + 384];
        }
        if (i == 65534) {
            return 3;
        }
        return i == 65535 ? 64680 : 1;
    }

    private static long nextPair(char[] cArr, int i, int i2, CharSequence charSequence, int i3) {
        int i4;
        if (i2 >= 3072 || i2 < 1024) {
            return i2;
        }
        if (i2 >= 2048) {
            int i5 = (i2 & 1023) + 448;
            return cArr[i5] | (cArr[i5 + 1] << 16);
        }
        int i6 = (i2 & 1023) + 448;
        boolean z = false;
        if (i3 != charSequence.length()) {
            int iCharAt = charSequence.charAt(i3);
            if (iCharAt > 383) {
                if (8192 <= iCharAt && iCharAt < PUNCT_LIMIT) {
                    iCharAt = (iCharAt - 8192) + 384;
                } else {
                    if (iCharAt != 65534 && iCharAt != 65535) {
                        return 1L;
                    }
                    iCharAt = -1;
                }
            }
            char c = cArr[i6];
            int i7 = i6;
            do {
                i7 += c >> '\t';
                c = cArr[i7];
                i4 = c & 511;
            } while (i4 < iCharAt);
            if (i4 == iCharAt) {
                i6 = i7;
                z = true;
            }
        }
        int i8 = cArr[i6] >> '\t';
        if (i8 == 1) {
            return 1L;
        }
        char c2 = cArr[i6 + 1];
        long j = i8 == 2 ? c2 : (cArr[i6 + 2] << 16) | c2;
        return z ? ~j : j;
    }

    private static int getSecondaries(int i, int i2) {
        if (i2 > 65535) {
            int i3 = 65535 & i2;
            if (i3 >= 4096) {
                return 2097184 + (TWO_SECONDARIES_MASK & i2);
            }
            if (i3 > i) {
                return TWO_COMMON_SEC_PLUS_OFFSET;
            }
        } else {
            if (i2 >= 4096) {
                return getSecondariesFromOneShortCE(i2);
            }
            if (i2 > i) {
                return 192;
            }
            if (i2 < 3072) {
                return i2;
            }
        }
        return 0;
    }

    private CollationFastLatin() {
    }
}
