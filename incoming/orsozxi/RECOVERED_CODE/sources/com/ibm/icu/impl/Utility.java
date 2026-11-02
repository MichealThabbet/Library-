package com.ibm.icu.impl;

import com.ibm.icu.impl.locale.LanguageTag;
import com.ibm.icu.lang.UCharacter;
import com.ibm.icu.text.Replaceable;
import com.ibm.icu.text.UTF16;
import com.ibm.icu.text.UnicodeMatcher;
import com.ibm.icu.util.ICUUncheckedIOException;
import it.piapostolos.android.pinotes.utils.ConstantsBase;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Locale;
import java.util.regex.Pattern;
import org.apache.commons.codec.language.Soundex;
import org.apache.http.message.TokenParser;

/* loaded from: classes2.dex */
public final class Utility {
    private static final char APOSTROPHE = '\'';
    private static final char BACKSLASH = '\\';
    private static final char ESCAPE = 42405;
    static final byte ESCAPE_BYTE = -91;
    private static final int MAGIC_UNSIGNED = Integer.MIN_VALUE;
    public static String LINE_SEPARATOR = System.getProperty("line.separator");
    static final char[] HEX_DIGIT = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
    private static final char[] UNESCAPE_MAP = {'a', 7, 'b', '\b', 'e', 27, 'f', '\f', 'n', '\n', 'r', '\r', 't', '\t', 'v', 11};
    static final char[] DIGITS = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};

    public static final int compareUnsigned(int i, int i2) {
        int i3 = i - Integer.MIN_VALUE;
        int i4 = i2 - Integer.MIN_VALUE;
        if (i3 < i4) {
            return -1;
        }
        return i3 > i4 ? 1 : 0;
    }

    public static final byte highBit(int i) {
        if (i <= 0) {
            return (byte) -1;
        }
        byte b = 0;
        if (i >= 65536) {
            i >>= 16;
            b = (byte) 16;
        }
        if (i >= 256) {
            i >>= 8;
            b = (byte) (b + 8);
        }
        if (i >= 16) {
            i >>= 4;
            b = (byte) (b + 4);
        }
        if (i >= 4) {
            i >>= 2;
            b = (byte) (b + 2);
        }
        return i >= 2 ? (byte) (b + 1) : b;
    }

    public static boolean isUnprintable(int i) {
        return i < 32 || i > 126;
    }

    public static final boolean sameObjects(Object obj, Object obj2) {
        return obj == obj2;
    }

    public static final boolean arrayEquals(Object[] objArr, Object obj) {
        if (objArr == null) {
            return obj == null;
        }
        if (!(obj instanceof Object[])) {
            return false;
        }
        Object[] objArr2 = (Object[]) obj;
        return objArr.length == objArr2.length && arrayRegionMatches(objArr, 0, objArr2, 0, objArr.length);
    }

    public static final boolean arrayEquals(int[] iArr, Object obj) {
        if (iArr == null) {
            return obj == null;
        }
        if (!(obj instanceof int[])) {
            return false;
        }
        int[] iArr2 = (int[]) obj;
        return iArr.length == iArr2.length && arrayRegionMatches(iArr, 0, iArr2, 0, iArr.length);
    }

    public static final boolean arrayEquals(double[] dArr, Object obj) {
        if (dArr == null) {
            return obj == null;
        }
        if (!(obj instanceof double[])) {
            return false;
        }
        double[] dArr2 = (double[]) obj;
        return dArr.length == dArr2.length && arrayRegionMatches(dArr, 0, dArr2, 0, dArr.length);
    }

    public static final boolean arrayEquals(byte[] bArr, Object obj) {
        if (bArr == null) {
            return obj == null;
        }
        if (!(obj instanceof byte[])) {
            return false;
        }
        byte[] bArr2 = (byte[]) obj;
        return bArr.length == bArr2.length && arrayRegionMatches(bArr, 0, bArr2, 0, bArr.length);
    }

    public static final boolean arrayEquals(Object obj, Object obj2) {
        if (obj == null) {
            return obj2 == null;
        }
        if (obj instanceof Object[]) {
            return arrayEquals((Object[]) obj, obj2);
        }
        if (obj instanceof int[]) {
            return arrayEquals((int[]) obj, obj2);
        }
        if (obj instanceof double[]) {
            return arrayEquals((double[]) obj, obj2);
        }
        if (obj instanceof byte[]) {
            return arrayEquals((byte[]) obj, obj2);
        }
        return obj.equals(obj2);
    }

    public static final boolean arrayRegionMatches(Object[] objArr, int i, Object[] objArr2, int i2, int i3) {
        int i4 = i3 + i;
        int i5 = i2 - i;
        while (i < i4) {
            if (!arrayEquals(objArr[i], objArr2[i + i5])) {
                return false;
            }
            i++;
        }
        return true;
    }

    public static final boolean arrayRegionMatches(char[] cArr, int i, char[] cArr2, int i2, int i3) {
        int i4 = i3 + i;
        int i5 = i2 - i;
        while (i < i4) {
            if (cArr[i] != cArr2[i + i5]) {
                return false;
            }
            i++;
        }
        return true;
    }

    public static final boolean arrayRegionMatches(int[] iArr, int i, int[] iArr2, int i2, int i3) {
        int i4 = i3 + i;
        int i5 = i2 - i;
        while (i < i4) {
            if (iArr[i] != iArr2[i + i5]) {
                return false;
            }
            i++;
        }
        return true;
    }

    public static final boolean arrayRegionMatches(double[] dArr, int i, double[] dArr2, int i2, int i3) {
        int i4 = i3 + i;
        int i5 = i2 - i;
        while (i < i4) {
            if (dArr[i] != dArr2[i + i5]) {
                return false;
            }
            i++;
        }
        return true;
    }

    public static final boolean arrayRegionMatches(byte[] bArr, int i, byte[] bArr2, int i2, int i3) {
        int i4 = i3 + i;
        int i5 = i2 - i;
        while (i < i4) {
            if (bArr[i] != bArr2[i + i5]) {
                return false;
            }
            i++;
        }
        return true;
    }

    public static <T extends Comparable<T>> int checkCompare(T t, T t2) {
        if (t == null) {
            return t2 == null ? 0 : -1;
        }
        if (t2 == null) {
            return 1;
        }
        return t.compareTo(t2);
    }

    public static int checkHash(Object obj) {
        if (obj == null) {
            return 0;
        }
        return obj.hashCode();
    }

    public static final String arrayToRLEString(int[] iArr) throws IOException {
        StringBuilder sb = new StringBuilder();
        appendInt(sb, iArr.length);
        int i = iArr[0];
        int i2 = 1;
        for (int i3 = 1; i3 < iArr.length; i3++) {
            int i4 = iArr[i3];
            if (i4 != i || i2 >= 65535) {
                encodeRun(sb, i, i2);
                i = i4;
                i2 = 1;
            } else {
                i2++;
            }
        }
        encodeRun(sb, i, i2);
        return sb.toString();
    }

    public static final String arrayToRLEString(short[] sArr) throws IOException {
        StringBuilder sb = new StringBuilder();
        sb.append((char) (sArr.length >> 16));
        sb.append((char) sArr.length);
        short s = sArr[0];
        int i = 1;
        for (int i2 = 1; i2 < sArr.length; i2++) {
            short s2 = sArr[i2];
            if (s2 != s || i >= 65535) {
                encodeRun(sb, s, i);
                s = s2;
                i = 1;
            } else {
                i++;
            }
        }
        encodeRun(sb, s, i);
        return sb.toString();
    }

    public static final String arrayToRLEString(char[] cArr) throws IOException {
        StringBuilder sb = new StringBuilder();
        sb.append((char) (cArr.length >> 16));
        sb.append((char) cArr.length);
        char c = cArr[0];
        int i = 1;
        for (int i2 = 1; i2 < cArr.length; i2++) {
            char c2 = cArr[i2];
            if (c2 != c || i >= 65535) {
                encodeRun(sb, (short) c, i);
                c = c2;
                i = 1;
            } else {
                i++;
            }
        }
        encodeRun(sb, (short) c, i);
        return sb.toString();
    }

    public static final String arrayToRLEString(byte[] bArr) throws IOException {
        StringBuilder sb = new StringBuilder();
        sb.append((char) (bArr.length >> 16));
        sb.append((char) bArr.length);
        byte b = bArr[0];
        byte[] bArr2 = new byte[2];
        int i = 1;
        for (int i2 = 1; i2 < bArr.length; i2++) {
            byte b2 = bArr[i2];
            if (b2 != b || i >= 255) {
                encodeRun(sb, b, i, bArr2);
                b = b2;
                i = 1;
            } else {
                i++;
            }
        }
        encodeRun(sb, b, i, bArr2);
        if (bArr2[0] != 0) {
            appendEncodedByte(sb, (byte) 0, bArr2);
        }
        return sb.toString();
    }

    private static final <T extends Appendable> void encodeRun(T t, int i, int i2) throws IOException {
        if (i2 < 4) {
            for (int i3 = 0; i3 < i2; i3++) {
                if (i == 42405) {
                    appendInt(t, i);
                }
                appendInt(t, i);
            }
            return;
        }
        if (i2 == 42405) {
            if (i == 42405) {
                appendInt(t, 42405);
            }
            appendInt(t, i);
            i2--;
        }
        appendInt(t, 42405);
        appendInt(t, i2);
        appendInt(t, i);
    }

    private static final <T extends Appendable> void appendInt(T t, int i) throws IOException {
        try {
            t.append((char) (i >>> 16));
            t.append((char) (i & 65535));
        } catch (IOException e) {
            throw new IllegalIcuArgumentException(e);
        }
    }

    private static final <T extends Appendable> void encodeRun(T t, short s, int i) throws IOException {
        char c = (char) s;
        try {
            if (i < 4) {
                for (int i2 = 0; i2 < i; i2++) {
                    if (c == 42405) {
                        t.append(ESCAPE);
                    }
                    t.append(c);
                }
                return;
            }
            if (i == 42405) {
                if (c == 42405) {
                    t.append(ESCAPE);
                }
                t.append(c);
                i--;
            }
            t.append(ESCAPE);
            t.append((char) i);
            t.append(c);
        } catch (IOException e) {
            throw new IllegalIcuArgumentException(e);
        }
    }

    private static final <T extends Appendable> void encodeRun(T t, byte b, int i, byte[] bArr) throws IOException {
        if (i < 4) {
            for (int i2 = 0; i2 < i; i2++) {
                if (b == -91) {
                    appendEncodedByte(t, ESCAPE_BYTE, bArr);
                }
                appendEncodedByte(t, b, bArr);
            }
            return;
        }
        if (((byte) i) == -91) {
            if (b == -91) {
                appendEncodedByte(t, ESCAPE_BYTE, bArr);
            }
            appendEncodedByte(t, b, bArr);
            i--;
        }
        appendEncodedByte(t, ESCAPE_BYTE, bArr);
        appendEncodedByte(t, (byte) i, bArr);
        appendEncodedByte(t, b, bArr);
    }

    private static final <T extends Appendable> void appendEncodedByte(T t, byte b, byte[] bArr) throws IOException {
        try {
            if (bArr[0] != 0) {
                t.append((char) ((b & 255) | (bArr[1] << 8)));
                bArr[0] = 0;
            } else {
                bArr[0] = 1;
                bArr[1] = b;
            }
        } catch (IOException e) {
            throw new IllegalIcuArgumentException(e);
        }
    }

    public static final int[] RLEStringToIntArray(String str) {
        int i;
        int i2 = getInt(str, 0);
        int[] iArr = new int[i2];
        int length = str.length() / 2;
        int i3 = 1;
        int i4 = 0;
        while (i4 < i2 && i3 < length) {
            int i5 = i3 + 1;
            int i6 = getInt(str, i3);
            if (i6 == 42405) {
                i3 = i5 + 1;
                int i7 = getInt(str, i5);
                if (i7 == 42405) {
                    i = i4 + 1;
                    iArr[i4] = i7;
                } else {
                    int i8 = i3 + 1;
                    int i9 = getInt(str, i3);
                    int i10 = 0;
                    while (i10 < i7) {
                        iArr[i4] = i9;
                        i10++;
                        i4++;
                    }
                    i3 = i8;
                }
            } else {
                i = i4 + 1;
                iArr[i4] = i6;
                i3 = i5;
            }
            i4 = i;
        }
        if (i4 == i2 && i3 == length) {
            return iArr;
        }
        throw new IllegalStateException("Bad run-length encoded int array");
    }

    static final int getInt(String str, int i) {
        int i2 = i * 2;
        return str.charAt(i2 + 1) | (str.charAt(i2) << 16);
    }

    public static final short[] RLEStringToShortArray(String str) {
        int i;
        int iCharAt = (str.charAt(0) << 16) | str.charAt(1);
        short[] sArr = new short[iCharAt];
        int i2 = 2;
        int i3 = 0;
        while (i2 < str.length()) {
            char cCharAt = str.charAt(i2);
            if (cCharAt == 42405) {
                i2++;
                char cCharAt2 = str.charAt(i2);
                if (cCharAt2 == 42405) {
                    i = i3 + 1;
                    sArr[i3] = (short) cCharAt2;
                } else {
                    i2++;
                    short sCharAt = (short) str.charAt(i2);
                    int i4 = 0;
                    while (i4 < cCharAt2) {
                        sArr[i3] = sCharAt;
                        i4++;
                        i3++;
                    }
                    i2++;
                }
            } else {
                i = i3 + 1;
                sArr[i3] = (short) cCharAt;
            }
            i3 = i;
            i2++;
        }
        if (i3 == iCharAt) {
            return sArr;
        }
        throw new IllegalStateException("Bad run-length encoded short array");
    }

    public static final char[] RLEStringToCharArray(String str) {
        int i;
        int iCharAt = (str.charAt(0) << 16) | str.charAt(1);
        char[] cArr = new char[iCharAt];
        int i2 = 2;
        int i3 = 0;
        while (i2 < str.length()) {
            char cCharAt = str.charAt(i2);
            if (cCharAt == 42405) {
                i2++;
                char cCharAt2 = str.charAt(i2);
                if (cCharAt2 == 42405) {
                    i = i3 + 1;
                    cArr[i3] = cCharAt2;
                } else {
                    i2++;
                    char cCharAt3 = str.charAt(i2);
                    int i4 = 0;
                    while (i4 < cCharAt2) {
                        cArr[i3] = cCharAt3;
                        i4++;
                        i3++;
                    }
                    i2++;
                }
            } else {
                i = i3 + 1;
                cArr[i3] = cCharAt;
            }
            i3 = i;
            i2++;
        }
        if (i3 == iCharAt) {
            return cArr;
        }
        throw new IllegalStateException("Bad run-length encoded short array");
    }

    public static final byte[] RLEStringToByteArray(String str) {
        char c;
        int i;
        byte b;
        int iCharAt = (str.charAt(0) << 16) | str.charAt(1);
        byte[] bArr = new byte[iCharAt];
        int i2 = 0;
        char c2 = 0;
        boolean z = true;
        int i3 = 2;
        char c3 = 0;
        int i4 = 0;
        while (i2 < iCharAt) {
            if (z) {
                int i5 = i3 + 1;
                char cCharAt = str.charAt(i3);
                byte b2 = (byte) (cCharAt >> '\b');
                c = cCharAt;
                i = i5;
                z = false;
                b = b2;
            } else {
                byte b3 = (byte) (c3 & 255);
                c = c3;
                i = i3;
                b = b3;
                z = true;
            }
            int i6 = b;
            if (c2 != 0) {
                if (c2 != 1) {
                    if (c2 == 2) {
                        int i7 = 0;
                        while (i7 < i4) {
                            bArr[i2] = b;
                            i7++;
                            i2++;
                        }
                        c2 = 0;
                    }
                } else if (b == -91) {
                    bArr[i2] = ESCAPE_BYTE;
                    i2++;
                    c2 = 0;
                } else {
                    if (b < 0) {
                        i6 = b + 256;
                    }
                    i4 = i6;
                    c2 = 2;
                }
            } else if (b == -91) {
                c2 = 1;
            } else {
                bArr[i2] = b;
                i2++;
            }
            i3 = i;
            c3 = c;
        }
        if (c2 != 0) {
            throw new IllegalStateException("Bad run-length encoded byte array");
        }
        if (i3 == str.length()) {
            return bArr;
        }
        throw new IllegalStateException("Excess data in RLE byte array string");
    }

    public static final String formatForSource(String str) {
        StringBuilder sb = new StringBuilder();
        int i = 0;
        while (i < str.length()) {
            if (i > 0) {
                sb.append('+');
                sb.append(LINE_SEPARATOR);
            }
            sb.append("        \"");
            int i2 = 11;
            while (i < str.length() && i2 < 80) {
                int i3 = i + 1;
                char cCharAt = str.charAt(i);
                if (cCharAt < ' ' || cCharAt == '\"' || cCharAt == '\\') {
                    if (cCharAt == '\n') {
                        sb.append("\\n");
                    } else if (cCharAt == '\t') {
                        sb.append("\\t");
                    } else if (cCharAt == '\r') {
                        sb.append("\\r");
                    } else {
                        sb.append('\\');
                        sb.append(HEX_DIGIT[(cCharAt & 448) >> 6]);
                        sb.append(HEX_DIGIT[(cCharAt & '8') >> 3]);
                        sb.append(HEX_DIGIT[cCharAt & 7]);
                        i2 += 4;
                    }
                    i2 += 2;
                } else if (cCharAt <= '~') {
                    sb.append(cCharAt);
                    i2++;
                } else {
                    sb.append("\\u");
                    sb.append(HEX_DIGIT[(61440 & cCharAt) >> 12]);
                    sb.append(HEX_DIGIT[(cCharAt & 3840) >> 8]);
                    sb.append(HEX_DIGIT[(cCharAt & 240) >> 4]);
                    sb.append(HEX_DIGIT[cCharAt & 15]);
                    i2 += 6;
                }
                i = i3;
            }
            sb.append('\"');
        }
        return sb.toString();
    }

    public static final String format1ForSource(String str) {
        StringBuilder sb = new StringBuilder();
        sb.append("\"");
        int i = 0;
        while (i < str.length()) {
            int i2 = i + 1;
            char cCharAt = str.charAt(i);
            if (cCharAt < ' ' || cCharAt == '\"' || cCharAt == '\\') {
                if (cCharAt == '\n') {
                    sb.append("\\n");
                } else if (cCharAt == '\t') {
                    sb.append("\\t");
                } else if (cCharAt == '\r') {
                    sb.append("\\r");
                } else {
                    sb.append('\\');
                    sb.append(HEX_DIGIT[(cCharAt & 448) >> 6]);
                    sb.append(HEX_DIGIT[(cCharAt & '8') >> 3]);
                    sb.append(HEX_DIGIT[cCharAt & 7]);
                }
            } else if (cCharAt <= '~') {
                sb.append(cCharAt);
            } else {
                sb.append("\\u");
                sb.append(HEX_DIGIT[(61440 & cCharAt) >> 12]);
                sb.append(HEX_DIGIT[(cCharAt & 3840) >> 8]);
                sb.append(HEX_DIGIT[(cCharAt & 240) >> 4]);
                sb.append(HEX_DIGIT[cCharAt & 15]);
            }
            i = i2;
        }
        sb.append('\"');
        return sb.toString();
    }

    public static final String escape(String str) {
        StringBuilder sb = new StringBuilder();
        int charCount = 0;
        while (charCount < str.length()) {
            int iCodePointAt = Character.codePointAt(str, charCount);
            charCount += UTF16.getCharCount(iCodePointAt);
            if (iCodePointAt < 32 || iCodePointAt > 127) {
                boolean z = iCodePointAt <= 65535;
                sb.append(z ? "\\u" : "\\U");
                sb.append(hex(iCodePointAt, z ? 4 : 8));
            } else if (iCodePointAt == 92) {
                sb.append("\\\\");
            } else {
                sb.append((char) iCodePointAt);
            }
        }
        return sb.toString();
    }

    /* JADX WARN: Removed duplicated region for block: B:26:0x005f A[ADDED_TO_REGION, LOOP:0: B:26:0x005f->B:35:0x0075, LOOP_START, PHI: r2 r5 r6 r13
      0x005f: PHI (r2v4 int) = (r2v2 int), (r2v9 int) binds: [B:25:0x005d, B:35:0x0075] A[DONT_GENERATE, DONT_INLINE]
      0x005f: PHI (r5v1 int) = (r5v0 int), (r5v8 int) binds: [B:25:0x005d, B:35:0x0075] A[DONT_GENERATE, DONT_INLINE]
      0x005f: PHI (r6v7 int) = (r6v4 int), (r6v11 int) binds: [B:25:0x005d, B:35:0x0075] A[DONT_GENERATE, DONT_INLINE]
      0x005f: PHI (r13v2 int) = (r13v1 int), (r13v3 int) binds: [B:25:0x005d, B:35:0x0075] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:60:0x00c1  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public static int unescapeAt(java.lang.String r16, int[] r17) {
        /*
            Method dump skipped, instructions count: 241
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ibm.icu.impl.Utility.unescapeAt(java.lang.String, int[]):int");
    }

    public static String unescape(String str) {
        StringBuilder sb = new StringBuilder();
        int[] iArr = new int[1];
        int i = 0;
        while (i < str.length()) {
            int i2 = i + 1;
            char cCharAt = str.charAt(i);
            if (cCharAt == '\\') {
                iArr[0] = i2;
                int iUnescapeAt = unescapeAt(str, iArr);
                if (iUnescapeAt < 0) {
                    throw new IllegalArgumentException("Invalid escape sequence " + str.substring(i2 - 1, Math.min(i2 + 8, str.length())));
                }
                sb.appendCodePoint(iUnescapeAt);
                i = iArr[0];
            } else {
                sb.append(cCharAt);
                i = i2;
            }
        }
        return sb.toString();
    }

    public static String unescapeLeniently(String str) {
        StringBuilder sb = new StringBuilder();
        int[] iArr = new int[1];
        int i = 0;
        while (i < str.length()) {
            int i2 = i + 1;
            char cCharAt = str.charAt(i);
            if (cCharAt == '\\') {
                iArr[0] = i2;
                int iUnescapeAt = unescapeAt(str, iArr);
                if (iUnescapeAt < 0) {
                    sb.append(cCharAt);
                } else {
                    sb.appendCodePoint(iUnescapeAt);
                    i2 = iArr[0];
                }
            } else {
                sb.append(cCharAt);
            }
            i = i2;
        }
        return sb.toString();
    }

    public static String hex(long j) {
        return hex(j, 4);
    }

    public static String hex(long j, int i) {
        if (j == Long.MIN_VALUE) {
            return "-8000000000000000";
        }
        boolean z = j < 0;
        if (z) {
            j = -j;
        }
        String upperCase = Long.toString(j, 16).toUpperCase(Locale.ENGLISH);
        if (upperCase.length() < i) {
            upperCase = "0000000000000000".substring(upperCase.length(), i) + upperCase;
        }
        if (!z) {
            return upperCase;
        }
        return Soundex.SILENT_MARKER + upperCase;
    }

    public static String hex(CharSequence charSequence) {
        return ((StringBuilder) hex(charSequence, 4, ConstantsBase.PROPERTIES_PARAMS_SEPARATOR, true, new StringBuilder())).toString();
    }

    public static <S extends CharSequence, U extends CharSequence, T extends Appendable> T hex(S s, int i, U u, boolean z, T t) throws IOException {
        int charCount = 0;
        try {
            if (z) {
                while (charCount < s.length()) {
                    int iCodePointAt = Character.codePointAt(s, charCount);
                    if (charCount != 0) {
                        t.append(u);
                    }
                    t.append(hex(iCodePointAt, i));
                    charCount += UTF16.getCharCount(iCodePointAt);
                }
            } else {
                while (charCount < s.length()) {
                    if (charCount != 0) {
                        t.append(u);
                    }
                    t.append(hex(s.charAt(charCount), i));
                    charCount++;
                }
            }
            return t;
        } catch (IOException e) {
            throw new IllegalIcuArgumentException(e);
        }
    }

    public static String hex(byte[] bArr, int i, int i2, String str) {
        StringBuilder sb = new StringBuilder();
        while (i < i2) {
            if (i != 0) {
                sb.append(str);
            }
            sb.append(hex(bArr[i]));
            i++;
        }
        return sb.toString();
    }

    public static <S extends CharSequence> String hex(S s, int i, S s2) {
        return ((StringBuilder) hex(s, i, s2, true, new StringBuilder())).toString();
    }

    public static void split(String str, char c, String[] strArr) {
        int i = 0;
        int i2 = 0;
        int i3 = 0;
        while (i < str.length()) {
            if (str.charAt(i) == c) {
                strArr[i2] = str.substring(i3, i);
                i3 = i + 1;
                i2++;
            }
            i++;
        }
        strArr[i2] = str.substring(i3, i);
        for (int i4 = i2 + 1; i4 < strArr.length; i4++) {
            strArr[i4] = "";
        }
    }

    public static String[] split(String str, char c) {
        ArrayList arrayList = new ArrayList();
        int i = 0;
        int i2 = 0;
        while (i < str.length()) {
            if (str.charAt(i) == c) {
                arrayList.add(str.substring(i2, i));
                i2 = i + 1;
            }
            i++;
        }
        arrayList.add(str.substring(i2, i));
        return (String[]) arrayList.toArray(new String[arrayList.size()]);
    }

    public static int lookup(String str, String[] strArr) {
        for (int i = 0; i < strArr.length; i++) {
            if (str.equals(strArr[i])) {
                return i;
            }
        }
        return -1;
    }

    public static boolean parseChar(String str, int[] iArr, char c) {
        int i = iArr[0];
        iArr[0] = PatternProps.skipWhiteSpace(str, iArr[0]);
        if (iArr[0] == str.length() || str.charAt(iArr[0]) != c) {
            iArr[0] = i;
            return false;
        }
        iArr[0] = iArr[0] + 1;
        return true;
    }

    public static int parsePattern(String str, int i, int i2, String str2, int[] iArr) {
        int[] iArr2 = new int[1];
        int i3 = 0;
        for (int i4 = 0; i4 < str2.length(); i4++) {
            char cCharAt = str2.charAt(i4);
            if (cCharAt != ' ') {
                if (cCharAt == '#') {
                    iArr2[0] = i;
                    int i5 = i3 + 1;
                    iArr[i3] = parseInteger(str, iArr2, i2);
                    if (iArr2[0] == i) {
                        return -1;
                    }
                    i = iArr2[0];
                    i3 = i5;
                } else if (cCharAt != '~') {
                    if (i >= i2) {
                        return -1;
                    }
                    int i6 = i + 1;
                    if (((char) UCharacter.toLowerCase(str.charAt(i))) != cCharAt) {
                        return -1;
                    }
                    i = i6;
                }
            } else {
                if (i >= i2) {
                    return -1;
                }
                int i7 = i + 1;
                if (!PatternProps.isWhiteSpace(str.charAt(i))) {
                    return -1;
                }
                i = i7;
            }
            i = PatternProps.skipWhiteSpace(str, i);
        }
        return i;
    }

    public static int parsePattern(String str, Replaceable replaceable, int i, int i2) {
        if (str.length() == 0) {
            return i;
        }
        int i3 = 0;
        int iCodePointAt = Character.codePointAt(str, 0);
        while (i < i2) {
            int iChar32At = replaceable.char32At(i);
            if (iCodePointAt != 126) {
                if (iChar32At != iCodePointAt) {
                    break;
                }
                int charCount = UTF16.getCharCount(iChar32At);
                i += charCount;
                i3 += charCount;
                if (i3 == str.length()) {
                    return i;
                }
            } else if (PatternProps.isWhiteSpace(iChar32At)) {
                i += UTF16.getCharCount(iChar32At);
            } else {
                i3++;
                if (i3 == str.length()) {
                    return i;
                }
            }
            iCodePointAt = UTF16.charAt(str, i3);
        }
        return -1;
    }

    /* JADX WARN: Removed duplicated region for block: B:13:0x002b  */
    /* JADX WARN: Removed duplicated region for block: B:21:0x0047  */
    /* JADX WARN: Removed duplicated region for block: B:24:0x0045 A[EDGE_INSN: B:24:0x0045->B:20:0x0045 BREAK  A[LOOP:0: B:12:0x0029->B:19:0x0042], SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public static int parseInteger(java.lang.String r8, int[] r9, int r10) {
        /*
            r0 = 0
            r7 = r9[r0]
            r2 = 1
            java.lang.String r4 = "0x"
            r5 = 0
            r6 = 2
            r1 = r8
            r3 = r7
            boolean r1 = r1.regionMatches(r2, r3, r4, r5, r6)
            if (r1 == 0) goto L15
            int r7 = r7 + 2
            r1 = 16
            goto L27
        L15:
            if (r7 >= r10) goto L25
            char r1 = r8.charAt(r7)
            r2 = 48
            if (r1 != r2) goto L25
            int r7 = r7 + 1
            r1 = 8
            r2 = 1
            goto L28
        L25:
            r1 = 10
        L27:
            r2 = 0
        L28:
            r3 = 0
        L29:
            if (r7 >= r10) goto L45
            int r4 = r7 + 1
            char r5 = r8.charAt(r7)
            int r5 = com.ibm.icu.lang.UCharacter.digit(r5, r1)
            if (r5 >= 0) goto L3a
            int r7 = r4 + (-1)
            goto L45
        L3a:
            int r2 = r2 + 1
            int r6 = r3 * r1
            int r6 = r6 + r5
            if (r6 > r3) goto L42
            return r0
        L42:
            r7 = r4
            r3 = r6
            goto L29
        L45:
            if (r2 <= 0) goto L49
            r9[r0] = r7
        L49:
            return r3
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ibm.icu.impl.Utility.parseInteger(java.lang.String, int[], int):int");
    }

    public static String parseUnicodeIdentifier(String str, int[] iArr) {
        StringBuilder sb = new StringBuilder();
        int charCount = iArr[0];
        while (charCount < str.length()) {
            int iCodePointAt = Character.codePointAt(str, charCount);
            if (sb.length() == 0) {
                if (!UCharacter.isUnicodeIdentifierStart(iCodePointAt)) {
                    return null;
                }
                sb.appendCodePoint(iCodePointAt);
            } else {
                if (!UCharacter.isUnicodeIdentifierPart(iCodePointAt)) {
                    break;
                }
                sb.appendCodePoint(iCodePointAt);
            }
            charCount += UTF16.getCharCount(iCodePointAt);
        }
        iArr[0] = charCount;
        return sb.toString();
    }

    private static <T extends Appendable> void recursiveAppendNumber(T t, int i, int i2, int i3) throws IOException {
        try {
            int i4 = i % i2;
            if (i >= i2 || i3 > 1) {
                recursiveAppendNumber(t, i / i2, i2, i3 - 1);
            }
            t.append(DIGITS[i4]);
        } catch (IOException e) {
            throw new IllegalIcuArgumentException(e);
        }
    }

    public static <T extends Appendable> T appendNumber(T t, int i, int i2, int i3) throws IOException {
        try {
            if (i2 < 2 || i2 > 36) {
                throw new IllegalArgumentException("Illegal radix " + i2);
            }
            if (i < 0) {
                i = -i;
                t.append(LanguageTag.SEP);
            }
            recursiveAppendNumber(t, i, i2, i3);
            return t;
        } catch (IOException e) {
            throw new IllegalIcuArgumentException(e);
        }
    }

    public static int parseNumber(String str, int[] iArr, int i) {
        int iDigit;
        int i2 = iArr[0];
        int i3 = 0;
        while (i2 < str.length() && (iDigit = UCharacter.digit(Character.codePointAt(str, i2), i)) >= 0) {
            i3 = (i3 * i) + iDigit;
            if (i3 < 0) {
                return -1;
            }
            i2++;
        }
        if (i2 == iArr[0]) {
            return -1;
        }
        iArr[0] = i2;
        return i3;
    }

    public static <T extends Appendable> boolean escapeUnprintable(T t, int i) throws IOException {
        try {
            if (!isUnprintable(i)) {
                return false;
            }
            t.append('\\');
            if (((-65536) & i) != 0) {
                t.append('U');
                t.append(DIGITS[(i >> 28) & 15]);
                t.append(DIGITS[(i >> 24) & 15]);
                t.append(DIGITS[(i >> 20) & 15]);
                t.append(DIGITS[(i >> 16) & 15]);
            } else {
                t.append('u');
            }
            t.append(DIGITS[(i >> 12) & 15]);
            t.append(DIGITS[(i >> 8) & 15]);
            t.append(DIGITS[(i >> 4) & 15]);
            t.append(DIGITS[i & 15]);
            return true;
        } catch (IOException e) {
            throw new IllegalIcuArgumentException(e);
        }
    }

    public static int quotedIndexOf(String str, int i, int i2, String str2) {
        while (i < i2) {
            char cCharAt = str.charAt(i);
            if (cCharAt == '\\') {
                i++;
            } else if (cCharAt == '\'') {
                do {
                    i++;
                    if (i < i2) {
                    }
                } while (str.charAt(i) != '\'');
            } else if (str2.indexOf(cCharAt) >= 0) {
                return i;
            }
            i++;
        }
        return -1;
    }

    public static void appendToRule(StringBuffer stringBuffer, int i, boolean z, boolean z2, StringBuffer stringBuffer2) {
        if (z || (z2 && isUnprintable(i))) {
            if (stringBuffer2.length() > 0) {
                while (stringBuffer2.length() >= 2 && stringBuffer2.charAt(0) == '\'' && stringBuffer2.charAt(1) == '\'') {
                    stringBuffer.append('\\');
                    stringBuffer.append('\'');
                    stringBuffer2.delete(0, 2);
                }
                int i2 = 0;
                while (stringBuffer2.length() >= 2 && stringBuffer2.charAt(stringBuffer2.length() - 2) == '\'' && stringBuffer2.charAt(stringBuffer2.length() - 1) == '\'') {
                    stringBuffer2.setLength(stringBuffer2.length() - 2);
                    i2++;
                }
                if (stringBuffer2.length() > 0) {
                    stringBuffer.append('\'');
                    stringBuffer.append(stringBuffer2);
                    stringBuffer.append('\'');
                    stringBuffer2.setLength(0);
                }
                while (true) {
                    int i3 = i2 - 1;
                    if (i2 <= 0) {
                        break;
                    }
                    stringBuffer.append('\\');
                    stringBuffer.append('\'');
                    i2 = i3;
                }
            }
            if (i != -1) {
                if (i == 32) {
                    int length = stringBuffer.length();
                    if (length <= 0 || stringBuffer.charAt(length - 1) == ' ') {
                        return;
                    }
                    stringBuffer.append(TokenParser.SP);
                    return;
                }
                if (z2 && escapeUnprintable(stringBuffer, i)) {
                    return;
                }
                stringBuffer.appendCodePoint(i);
                return;
            }
            return;
        }
        if (stringBuffer2.length() == 0 && (i == 39 || i == 92)) {
            stringBuffer.append('\\');
            stringBuffer.append((char) i);
            return;
        }
        if (stringBuffer2.length() > 0 || ((i >= 33 && i <= 126 && ((i < 48 || i > 57) && ((i < 65 || i > 90) && (i < 97 || i > 122)))) || PatternProps.isWhiteSpace(i))) {
            stringBuffer2.appendCodePoint(i);
            if (i == 39) {
                stringBuffer2.append((char) i);
                return;
            }
            return;
        }
        stringBuffer.appendCodePoint(i);
    }

    public static void appendToRule(StringBuffer stringBuffer, String str, boolean z, boolean z2, StringBuffer stringBuffer2) {
        for (int i = 0; i < str.length(); i++) {
            appendToRule(stringBuffer, str.charAt(i), z, z2, stringBuffer2);
        }
    }

    public static void appendToRule(StringBuffer stringBuffer, UnicodeMatcher unicodeMatcher, boolean z, StringBuffer stringBuffer2) {
        if (unicodeMatcher != null) {
            appendToRule(stringBuffer, unicodeMatcher.toPattern(z), true, z, stringBuffer2);
        }
    }

    public static String valueOf(int[] iArr) {
        StringBuilder sb = new StringBuilder(iArr.length);
        for (int i : iArr) {
            sb.appendCodePoint(i);
        }
        return sb.toString();
    }

    public static String repeat(String str, int i) {
        if (i <= 0) {
            return "";
        }
        if (i == 1) {
            return str;
        }
        StringBuilder sb = new StringBuilder();
        for (int i2 = 0; i2 < i; i2++) {
            sb.append(str);
        }
        return sb.toString();
    }

    public static String[] splitString(String str, String str2) {
        return str.split("\\Q" + str2 + "\\E");
    }

    public static String[] splitWhitespace(String str) {
        return str.split("\\s+");
    }

    public static String fromHex(String str, int i, String str2) {
        if (str2 == null) {
            str2 = "\\s+";
        }
        return fromHex(str, i, Pattern.compile(str2));
    }

    public static String fromHex(String str, int i, Pattern pattern) {
        StringBuilder sb = new StringBuilder();
        for (String str2 : pattern.split(str)) {
            if (str2.length() < i) {
                throw new IllegalArgumentException("code point too short: " + str2);
            }
            sb.appendCodePoint(Integer.parseInt(str2, 16));
        }
        return sb.toString();
    }

    public static int addExact(int i, int i2) {
        int i3 = i + i2;
        if (((i ^ i3) & (i2 ^ i3)) >= 0) {
            return i3;
        }
        throw new ArithmeticException("integer overflow");
    }

    public static boolean charSequenceEquals(CharSequence charSequence, CharSequence charSequence2) {
        if (charSequence == charSequence2) {
            return true;
        }
        if (charSequence == null || charSequence2 == null || charSequence.length() != charSequence2.length()) {
            return false;
        }
        for (int i = 0; i < charSequence.length(); i++) {
            if (charSequence.charAt(i) != charSequence2.charAt(i)) {
                return false;
            }
        }
        return true;
    }

    public static int charSequenceHashCode(CharSequence charSequence) {
        int iCharAt = 0;
        for (int i = 0; i < charSequence.length(); i++) {
            iCharAt = (iCharAt * 31) + charSequence.charAt(i);
        }
        return iCharAt;
    }

    public static <A extends Appendable> A appendTo(CharSequence charSequence, A a) throws IOException {
        try {
            a.append(charSequence);
            return a;
        } catch (IOException e) {
            throw new ICUUncheckedIOException(e);
        }
    }

    public static String joinStrings(CharSequence charSequence, Iterable<? extends CharSequence> iterable) {
        if (charSequence == null || iterable == null) {
            throw new NullPointerException("Delimiter or elements is null");
        }
        StringBuilder sb = new StringBuilder();
        boolean z = true;
        for (CharSequence charSequence2 : iterable) {
            if (charSequence2 != null) {
                if (z) {
                    z = false;
                } else {
                    sb.append(charSequence);
                }
                sb.append(charSequence2);
            }
        }
        return sb.toString();
    }
}
