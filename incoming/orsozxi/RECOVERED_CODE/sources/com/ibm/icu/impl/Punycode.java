package com.ibm.icu.impl;

import com.ibm.icu.lang.UCharacter;
import com.ibm.icu.text.StringPrepParseException;
import com.ibm.icu.text.UTF16;
import com.ibm.icu.util.ICUInputTooLongException;
import org.apache.commons.codec.language.Soundex;
import org.apache.http.message.TokenParser;

/* loaded from: classes2.dex */
public final class Punycode {
    private static final int BASE = 36;
    private static final int CAPITAL_A = 65;
    private static final int CAPITAL_Z = 90;
    private static final int DAMP = 700;
    private static final int DECODE_MAX_CHARS = 2000;
    private static final char DELIMITER = '-';
    private static final int ENCODE_MAX_CODE_UNITS = 1000;
    private static final char HYPHEN = '-';
    private static final int INITIAL_BIAS = 72;
    private static final int INITIAL_N = 128;
    private static final int SKEW = 38;
    private static final int SMALL_A = 97;
    private static final int SMALL_Z = 122;
    private static final int TMAX = 26;
    private static final int TMIN = 1;
    private static final int ZERO = 48;

    private static char asciiCaseMap(char c, boolean z) {
        int i;
        if (z) {
            if ('a' > c || c > 'z') {
                return c;
            }
            i = c - ' ';
        } else {
            if ('A' > c || c > 'Z') {
                return c;
            }
            i = c + TokenParser.SP;
        }
        return (char) i;
    }

    private static final int decodeDigit(int i) {
        if (i > 90) {
            if (i <= 122) {
                return i - 97;
            }
            return -1;
        }
        if (i > 57) {
            return i - 65;
        }
        if (i < 48) {
            return -1;
        }
        return (i - 48) + 26;
    }

    private static char digitToBasic(int i, boolean z) {
        return (char) (i < 26 ? z ? i + 65 : i + 97 : i + 22);
    }

    private static boolean isBasic(int i) {
        return i < 128;
    }

    private static boolean isBasicUpperCase(int i) {
        return 65 <= i && i >= 90;
    }

    private static boolean isSurrogate(int i) {
        return (i & (-2048)) == 55296;
    }

    private static int adaptBias(int i, int i2, boolean z) {
        int i3;
        if (z) {
            i3 = i / 700;
        } else {
            i3 = i / 2;
        }
        int i4 = i3 + (i3 / i2);
        int i5 = 0;
        while (i4 > 455) {
            i4 /= 35;
            i5 += 36;
        }
        return i5 + ((i4 * 36) / (i4 + 38));
    }

    public static StringBuilder encode(CharSequence charSequence, boolean[] zArr) throws StringPrepParseException {
        int i;
        int length = charSequence.length();
        if (length > 1000) {
            throw new ICUInputTooLongException("input too long: " + length + " UTF-16 code units");
        }
        int[] iArr = new int[length];
        StringBuilder sb = new StringBuilder(length);
        int i2 = 0;
        int i3 = 0;
        while (i2 < length) {
            char cCharAt = charSequence.charAt(i2);
            if (isBasic(cCharAt)) {
                i = i3 + 1;
                iArr[i3] = 0;
                char cAsciiCaseMap = cCharAt;
                if (zArr != null) {
                    cAsciiCaseMap = asciiCaseMap(cCharAt, zArr[i2]);
                }
                sb.append(cAsciiCaseMap);
            } else {
                int i4 = ((zArr == null || !zArr[i2]) ? 0 : 1) << 31;
                boolean zIsSurrogate = UTF16.isSurrogate(cCharAt);
                int codePoint = cCharAt;
                if (zIsSurrogate) {
                    if (UTF16.isLeadSurrogate(cCharAt) && (i2 = i2 + 1) < length) {
                        char cCharAt2 = charSequence.charAt(i2);
                        if (UTF16.isTrailSurrogate(cCharAt2)) {
                            codePoint = UCharacter.getCodePoint(cCharAt, cCharAt2);
                        }
                    }
                    throw new StringPrepParseException("Illegal char found", 1);
                }
                int i5 = codePoint | i4;
                i = i3 + 1;
                iArr[i3] = i5;
            }
            i3 = i;
            i2++;
        }
        int length2 = sb.length();
        if (length2 > 0) {
            sb.append(Soundex.SILENT_MARKER);
        }
        int i6 = 128;
        int i7 = 72;
        int i8 = length2;
        int i9 = 0;
        while (i8 < i3) {
            int i10 = Integer.MAX_VALUE;
            for (int i11 = 0; i11 < i3; i11++) {
                int i12 = iArr[i11] & Integer.MAX_VALUE;
                if (i6 <= i12 && i12 < i10) {
                    i10 = i12;
                }
            }
            int i13 = i10 - i6;
            int i14 = i8 + 1;
            if (i13 > ((Integer.MAX_VALUE - i8) - i9) / i14) {
                throw new IllegalStateException("Internal program error");
            }
            int i15 = i9 + (i13 * i14);
            for (int i16 = 0; i16 < i3; i16++) {
                int i17 = iArr[i16] & Integer.MAX_VALUE;
                if (i17 < i10) {
                    i15++;
                } else if (i17 == i10) {
                    int i18 = 36;
                    int i19 = i15;
                    while (true) {
                        int i20 = i18 - i7;
                        if (i20 < 1) {
                            i20 = 1;
                        } else if (i18 >= i7 + 26) {
                            i20 = 26;
                        }
                        if (i19 < i20) {
                            break;
                        }
                        int i21 = i19 - i20;
                        int i22 = 36 - i20;
                        sb.append(digitToBasic(i20 + (i21 % i22), false));
                        i19 = i21 / i22;
                        i18 += 36;
                    }
                    sb.append(digitToBasic(i19, iArr[i16] < 0));
                    int i23 = i8 + 1;
                    int iAdaptBias = adaptBias(i15, i23, i8 == length2);
                    i15 = 0;
                    i8 = i23;
                    i7 = iAdaptBias;
                }
            }
            i9 = i15 + 1;
            i6 = i10 + 1;
        }
        return sb;
    }

    public static StringBuilder decode(CharSequence charSequence, boolean[] zArr) throws StringPrepParseException {
        int iOffsetByCodePoints;
        int length = charSequence.length();
        if (length > 2000) {
            throw new ICUInputTooLongException("input too long: " + length + " characters");
        }
        StringBuilder sb = new StringBuilder(charSequence.length());
        int i = length;
        while (i > 0) {
            i--;
            if (charSequence.charAt(i) == '-') {
                break;
            }
        }
        for (int i2 = 0; i2 < i; i2++) {
            char cCharAt = charSequence.charAt(i2);
            if (!isBasic(cCharAt)) {
                throw new StringPrepParseException("Illegal char found", 0);
            }
            sb.append(cCharAt);
            if (zArr != null && i2 < zArr.length) {
                zArr[i2] = isBasicUpperCase(cCharAt);
            }
        }
        int i3 = 128;
        int iAdaptBias = 72;
        int i4 = 1000000000;
        int i5 = i > 0 ? i + 1 : 0;
        int i6 = 0;
        while (i5 < length) {
            int i7 = 36;
            int i8 = 1;
            int i9 = i6;
            int i10 = 1;
            while (i5 < length) {
                int i11 = i5 + 1;
                int iDecodeDigit = decodeDigit(charSequence.charAt(i5));
                if (iDecodeDigit < 0) {
                    throw new StringPrepParseException("Invalid char found", 0);
                }
                if (iDecodeDigit > (Integer.MAX_VALUE - i9) / i10) {
                    throw new StringPrepParseException("Illegal char found", 1);
                }
                i9 += iDecodeDigit * i10;
                int i12 = i7 - iAdaptBias;
                if (i12 < i8) {
                    i12 = 1;
                } else if (i7 >= iAdaptBias + 26) {
                    i12 = 26;
                }
                if (iDecodeDigit >= i12) {
                    int i13 = 36 - i12;
                    if (i10 > Integer.MAX_VALUE / i13) {
                        throw new StringPrepParseException("Illegal char found", 1);
                    }
                    i10 *= i13;
                    i7 += 36;
                    i5 = i11;
                    i8 = 1;
                } else {
                    i++;
                    iAdaptBias = adaptBias(i9 - i6, i, i6 == 0);
                    int i14 = i9 / i;
                    if (i14 > Integer.MAX_VALUE - i3) {
                        throw new StringPrepParseException("Illegal char found", 1);
                    }
                    i3 += i14;
                    int i15 = i9 % i;
                    if (i3 > 1114111 || isSurrogate(i3)) {
                        throw new StringPrepParseException("Illegal char found", 1);
                    }
                    int iCharCount = Character.charCount(i3);
                    if (i15 > i4) {
                        iOffsetByCodePoints = sb.offsetByCodePoints(i4, i15 - i4);
                    } else if (iCharCount > 1) {
                        i4 = i15;
                        iOffsetByCodePoints = i4;
                    } else {
                        i4++;
                        iOffsetByCodePoints = i15;
                    }
                    if (zArr != null && sb.length() + iCharCount <= zArr.length) {
                        if (iOffsetByCodePoints < sb.length()) {
                            System.arraycopy(zArr, iOffsetByCodePoints, zArr, iOffsetByCodePoints + iCharCount, sb.length() - iOffsetByCodePoints);
                        }
                        zArr[iOffsetByCodePoints] = isBasicUpperCase(charSequence.charAt(i11 - 1));
                        if (iCharCount == 2) {
                            zArr[iOffsetByCodePoints + 1] = false;
                        }
                    }
                    if (iCharCount == 1) {
                        sb.insert(iOffsetByCodePoints, (char) i3);
                    } else {
                        sb.insert(iOffsetByCodePoints, UTF16.getLeadSurrogate(i3));
                        sb.insert(iOffsetByCodePoints + 1, UTF16.getTrailSurrogate(i3));
                    }
                    i6 = i15 + 1;
                    i5 = i11;
                }
            }
            throw new StringPrepParseException("Illegal char found", 1);
        }
        return sb;
    }
}
