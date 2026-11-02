package com.ibm.icu.impl;

import com.ibm.icu.util.ICUUncheckedIOException;
import java.io.IOException;
import java.text.Format;

/* loaded from: classes2.dex */
public final class SimpleFormatterImpl {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final int ARG_NUM_LIMIT = 256;
    private static final String[][] COMMON_PATTERNS = {new String[]{"{0} {1}", "\u0002\u0000ā \u0001"}, new String[]{"{0} ({1})", "\u0002\u0000Ă (\u0001ā)"}, new String[]{"{0}, {1}", "\u0002\u0000Ă, \u0001"}, new String[]{"{0} – {1}", "\u0002\u0000ă – \u0001"}};
    private static final char LEN1_CHAR = 257;
    private static final char LEN2_CHAR = 258;
    private static final char LEN3_CHAR = 259;
    private static final int MAX_SEGMENT_LENGTH = 65279;
    private static final char SEGMENT_LENGTH_ARGUMENT_CHAR = 65535;

    private SimpleFormatterImpl() {
    }

    /* JADX WARN: Removed duplicated region for block: B:61:0x00cd A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:91:0x00d9 A[ADDED_TO_REGION, EDGE_INSN: B:91:0x00d9->B:67:0x00d9 BREAK  A[LOOP:1: B:13:0x0032->B:23:0x0053, LOOP_LABEL: LOOP:1: B:13:0x0032->B:23:0x0053], REMOVE, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public static java.lang.String compileToStringMinMaxArguments(java.lang.CharSequence r19, java.lang.StringBuilder r20, int r21, int r22) {
        /*
            Method dump skipped, instructions count: 383
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ibm.icu.impl.SimpleFormatterImpl.compileToStringMinMaxArguments(java.lang.CharSequence, java.lang.StringBuilder, int, int):java.lang.String");
    }

    public static int getArgumentLimit(String str) {
        return str.charAt(0);
    }

    public static String formatCompiledPattern(String str, CharSequence... charSequenceArr) {
        return formatAndAppend(str, new StringBuilder(), null, charSequenceArr).toString();
    }

    public static String formatRawPattern(String str, int i, int i2, CharSequence... charSequenceArr) {
        StringBuilder sb = new StringBuilder();
        String strCompileToStringMinMaxArguments = compileToStringMinMaxArguments(str, sb, i, i2);
        sb.setLength(0);
        return formatAndAppend(strCompileToStringMinMaxArguments, sb, null, charSequenceArr).toString();
    }

    public static StringBuilder formatAndAppend(String str, StringBuilder sb, int[] iArr, CharSequence... charSequenceArr) {
        if ((charSequenceArr != null ? charSequenceArr.length : 0) < getArgumentLimit(str)) {
            throw new IllegalArgumentException("Too few values.");
        }
        return format(str, charSequenceArr, sb, null, true, iArr);
    }

    public static StringBuilder formatAndReplace(String str, StringBuilder sb, int[] iArr, CharSequence... charSequenceArr) {
        if ((charSequenceArr != null ? charSequenceArr.length : 0) < getArgumentLimit(str)) {
            throw new IllegalArgumentException("Too few values.");
        }
        char c = 65535;
        String string = null;
        if (getArgumentLimit(str) > 0) {
            int i = 1;
            while (i < str.length()) {
                int i2 = i + 1;
                char cCharAt = str.charAt(i);
                if (cCharAt >= 256) {
                    i2 += cCharAt - 256;
                } else if (charSequenceArr[cCharAt] == sb) {
                    if (i2 == 2) {
                        c = cCharAt;
                    } else if (string == null) {
                        string = sb.toString();
                    }
                }
                i = i2;
            }
        }
        String str2 = string;
        if (c < 0) {
            sb.setLength(0);
        }
        return format(str, charSequenceArr, sb, str2, false, iArr);
    }

    public static String getTextWithNoArguments(String str) {
        int i = 1;
        StringBuilder sb = new StringBuilder((str.length() - 1) - getArgumentLimit(str));
        while (i < str.length()) {
            int i2 = i + 1;
            int iCharAt = str.charAt(i) - 256;
            if (iCharAt > 0) {
                i = iCharAt + i2;
                sb.append((CharSequence) str, i2, i);
            } else {
                i = i2;
            }
        }
        return sb.toString();
    }

    public static int getLength(String str, boolean z) {
        int i = 1;
        int iCodePointCount = 0;
        while (i < str.length()) {
            int i2 = i + 1;
            int iCharAt = str.charAt(i) - 256;
            if (iCharAt > 0) {
                i = iCharAt + i2;
                iCodePointCount += z ? Character.codePointCount(str, i2, i) : i - i2;
            } else {
                i = i2;
            }
        }
        return iCodePointCount;
    }

    public static int getPrefixLength(String str) {
        if (str.length() == 1) {
            return 0;
        }
        if (str.charAt(0) == 0) {
            return str.length() - 2;
        }
        if (str.charAt(1) <= 256) {
            return 0;
        }
        return str.charAt(1) - 256;
    }

    public static int formatPrefixSuffix(String str, Format.Field field, int i, int i2, FormattedStringBuilder formattedStringBuilder) {
        int iInsert;
        int i3;
        if (getArgumentLimit(str) == 0) {
            return formattedStringBuilder.splice(i, i2, str, 2, str.length(), field);
        }
        if (str.charAt(1) != 0) {
            int iCharAt = str.charAt(1) - 256;
            iInsert = formattedStringBuilder.insert(i, str, 2, iCharAt + 2, field);
            i3 = iCharAt + 3;
        } else {
            iInsert = 0;
            i3 = 2;
        }
        if (i3 >= str.length()) {
            return iInsert;
        }
        int i4 = i3 + 1;
        return iInsert + formattedStringBuilder.insert(i2 + iInsert, str, i4, i4 + (str.charAt(i3) - 256), field);
    }

    public static class IterInternal {
        static final /* synthetic */ boolean $assertionsDisabled = false;
        public static final long DONE = -1;

        public static int getArgIndex(long j) {
            return (int) j;
        }

        public static long step(long j, CharSequence charSequence, Appendable appendable) throws IOException {
            int i = ((int) (j >>> 32)) + 1;
            while (i < charSequence.length() && charSequence.charAt(i) > 256) {
                int iCharAt = ((charSequence.charAt(i) + i) + 1) - 256;
                try {
                    appendable.append(charSequence, i + 1, iCharAt);
                    i = iCharAt;
                } catch (IOException e) {
                    throw new ICUUncheckedIOException(e);
                }
            }
            if (i == charSequence.length()) {
                return -1L;
            }
            return charSequence.charAt(i) | (i << 32);
        }
    }

    private static StringBuilder format(String str, CharSequence[] charSequenceArr, StringBuilder sb, String str2, boolean z, int[] iArr) {
        int length;
        if (iArr == null) {
            length = 0;
        } else {
            length = iArr.length;
            for (int i = 0; i < length; i++) {
                iArr[i] = -1;
            }
        }
        int i2 = 1;
        while (i2 < str.length()) {
            int i3 = i2 + 1;
            char cCharAt = str.charAt(i2);
            if (cCharAt < 256) {
                CharSequence charSequence = charSequenceArr[cCharAt];
                if (charSequence != sb) {
                    if (cCharAt < length) {
                        iArr[cCharAt] = sb.length();
                    }
                    sb.append(charSequence);
                } else {
                    if (z) {
                        throw new IllegalArgumentException("Value must not be same object as result");
                    }
                    if (i3 != 2) {
                        if (cCharAt < length) {
                            iArr[cCharAt] = sb.length();
                        }
                        sb.append(str2);
                    } else if (cCharAt < length) {
                        iArr[cCharAt] = 0;
                    }
                }
                i2 = i3;
            } else {
                i2 = (cCharAt - 256) + i3;
                sb.append((CharSequence) str, i3, i2);
            }
        }
        return sb;
    }
}
