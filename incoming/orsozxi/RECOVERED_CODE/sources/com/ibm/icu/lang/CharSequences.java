package com.ibm.icu.lang;

import com.ibm.icu.text.UTF16;

@Deprecated
/* loaded from: classes2.dex */
public class CharSequences {
    @Deprecated
    public static int matchAfter(CharSequence charSequence, CharSequence charSequence2, int i, int i2) {
        int length = charSequence.length();
        int length2 = charSequence2.length();
        int i3 = i;
        while (i3 < length && i2 < length2 && charSequence.charAt(i3) == charSequence2.charAt(i2)) {
            i3++;
            i2++;
        }
        int i4 = i3 - i;
        return (i4 == 0 || onCharacterBoundary(charSequence, i3) || onCharacterBoundary(charSequence2, i2)) ? i4 : i4 - 1;
    }

    @Deprecated
    public int codePointLength(CharSequence charSequence) {
        return Character.codePointCount(charSequence, 0, charSequence.length());
    }

    @Deprecated
    public static final boolean equals(int i, CharSequence charSequence) {
        if (charSequence == null) {
            return false;
        }
        int length = charSequence.length();
        return length != 1 ? length == 2 && i > 65535 && i == Character.codePointAt(charSequence, 0) : i == charSequence.charAt(0);
    }

    @Deprecated
    public static final boolean equals(CharSequence charSequence, int i) {
        return equals(i, charSequence);
    }

    @Deprecated
    public static int compare(CharSequence charSequence, int i) {
        if (i < 0 || i > 1114111) {
            throw new IllegalArgumentException();
        }
        int length = charSequence.length();
        if (length == 0) {
            return -1;
        }
        char cCharAt = charSequence.charAt(0);
        int i2 = i - 65536;
        if (i2 < 0) {
            int i3 = cCharAt - i;
            return i3 != 0 ? i3 : length - 1;
        }
        int i4 = cCharAt - ((char) ((i2 >>> 10) + 55296));
        if (i4 != 0) {
            return i4;
        }
        if (length > 1) {
            int iCharAt = charSequence.charAt(1) - ((char) ((i2 & 1023) + UTF16.TRAIL_SURROGATE_MIN_VALUE));
            if (iCharAt != 0) {
                return iCharAt;
            }
        }
        return length - 2;
    }

    @Deprecated
    public static int compare(int i, CharSequence charSequence) {
        int iCompare = compare(charSequence, i);
        if (iCompare > 0) {
            return -1;
        }
        return iCompare < 0 ? 1 : 0;
    }

    @Deprecated
    public static int getSingleCodePoint(CharSequence charSequence) {
        int length = charSequence.length();
        if (length < 1 || length > 2) {
            return Integer.MAX_VALUE;
        }
        int iCodePointAt = Character.codePointAt(charSequence, 0);
        if ((iCodePointAt < 65536) == (length == 1)) {
            return iCodePointAt;
        }
        return Integer.MAX_VALUE;
    }

    @Deprecated
    public static final <T> boolean equals(T t, T t2) {
        if (t == null) {
            return t2 == null;
        }
        if (t2 == null) {
            return false;
        }
        return t.equals(t2);
    }

    @Deprecated
    public static int compare(CharSequence charSequence, CharSequence charSequence2) {
        int length = charSequence.length();
        int length2 = charSequence2.length();
        int i = length <= length2 ? length : length2;
        for (int i2 = 0; i2 < i; i2++) {
            int iCharAt = charSequence.charAt(i2) - charSequence2.charAt(i2);
            if (iCharAt != 0) {
                return iCharAt;
            }
        }
        return length - length2;
    }

    @Deprecated
    public static boolean equalsChars(CharSequence charSequence, CharSequence charSequence2) {
        return charSequence.length() == charSequence2.length() && compare(charSequence, charSequence2) == 0;
    }

    @Deprecated
    public static boolean onCharacterBoundary(CharSequence charSequence, int i) {
        return i <= 0 || i >= charSequence.length() || !Character.isHighSurrogate(charSequence.charAt(i + (-1))) || !Character.isLowSurrogate(charSequence.charAt(i));
    }

    @Deprecated
    public static int indexOf(CharSequence charSequence, int i) {
        int iCharCount = 0;
        while (iCharCount < charSequence.length()) {
            int iCodePointAt = Character.codePointAt(charSequence, iCharCount);
            if (iCodePointAt == i) {
                return iCharCount;
            }
            iCharCount += Character.charCount(iCodePointAt);
        }
        return -1;
    }

    /* JADX WARN: Removed duplicated region for block: B:15:0x0035  */
    @java.lang.Deprecated
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public static int[] codePoints(java.lang.CharSequence r9) {
        /*
            int r0 = r9.length()
            int[] r1 = new int[r0]
            r2 = 0
            r3 = 0
            r4 = 0
        L9:
            int r5 = r9.length()
            if (r3 >= r5) goto L3d
            char r5 = r9.charAt(r3)
            r6 = 56320(0xdc00, float:7.8921E-41)
            if (r5 < r6) goto L35
            r6 = 57343(0xdfff, float:8.0355E-41)
            if (r5 > r6) goto L35
            if (r3 == 0) goto L35
            int r6 = r4 + (-1)
            r7 = r1[r6]
            char r7 = (char) r7
            r8 = 55296(0xd800, float:7.7486E-41)
            if (r7 < r8) goto L35
            r8 = 56319(0xdbff, float:7.892E-41)
            if (r7 > r8) goto L35
            int r5 = java.lang.Character.toCodePoint(r7, r5)
            r1[r6] = r5
            goto L3a
        L35:
            int r6 = r4 + 1
            r1[r4] = r5
            r4 = r6
        L3a:
            int r3 = r3 + 1
            goto L9
        L3d:
            if (r4 != r0) goto L40
            return r1
        L40:
            int[] r9 = new int[r4]
            java.lang.System.arraycopy(r1, r2, r9, r2, r4)
            return r9
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ibm.icu.lang.CharSequences.codePoints(java.lang.CharSequence):int[]");
    }

    private CharSequences() {
    }
}
