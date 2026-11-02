package com.ibm.icu.impl.number;

import com.ibm.icu.impl.PatternTokenizer;
import com.ibm.icu.impl.StandardPlural;
import com.ibm.icu.impl.locale.LanguageTag;
import com.ibm.icu.impl.number.Modifier;
import com.ibm.icu.impl.number.Padder;
import com.ibm.icu.number.NumberFormatter;
import com.ibm.icu.text.DateFormat;
import com.ibm.icu.text.DecimalFormatSymbols;
import it.orsozoxi.android.orsonotes.utils.ConstantsBase;
import java.lang.reflect.Array;
import java.math.BigDecimal;
import kotlin.text.Typography;
import org.apache.commons.codec.language.Soundex;
import org.slf4j.Marker;

/* loaded from: classes2.dex */
public class PatternStringUtils {
    static final /* synthetic */ boolean $assertionsDisabled = false;

    public enum PatternSignType {
        POS,
        POS_SIGN,
        NEG;

        public static final PatternSignType[] VALUES = values();
    }

    public static boolean ignoreRoundingIncrement(BigDecimal bigDecimal, int i) {
        double dDoubleValue = bigDecimal.doubleValue();
        if (dDoubleValue == 0.0d) {
            return true;
        }
        if (i < 0) {
            return false;
        }
        int i2 = 0;
        for (double d = dDoubleValue * 2.0d; i2 <= i && d <= 1.0d; d *= 10.0d) {
            i2++;
        }
        return i2 > i;
    }

    /* JADX WARN: Removed duplicated region for block: B:25:0x010b A[LOOP:2: B:23:0x0102->B:25:0x010b, LOOP_END] */
    /* JADX WARN: Removed duplicated region for block: B:28:0x0113 A[LOOP:3: B:26:0x0110->B:28:0x0113, LOOP_END] */
    /* JADX WARN: Removed duplicated region for block: B:31:0x0126  */
    /* JADX WARN: Removed duplicated region for block: B:34:0x012e  */
    /* JADX WARN: Removed duplicated region for block: B:35:0x0134  */
    /* JADX WARN: Removed duplicated region for block: B:37:0x0137  */
    /* JADX WARN: Removed duplicated region for block: B:62:0x0184  */
    /* JADX WARN: Removed duplicated region for block: B:70:0x01a9 A[LOOP:6: B:70:0x01a9->B:72:0x01b1, LOOP_START, PHI: r1
      0x01a9: PHI (r1v11 int) = (r1v6 int), (r1v14 int) binds: [B:69:0x01a7, B:72:0x01b1] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:86:0x0207  */
    /* JADX WARN: Removed duplicated region for block: B:90:0x0210  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public static java.lang.String propertiesToPatternString(com.ibm.icu.impl.number.DecimalFormatProperties r25) {
        /*
            Method dump skipped, instructions count: 559
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ibm.icu.impl.number.PatternStringUtils.propertiesToPatternString(com.ibm.icu.impl.number.DecimalFormatProperties):java.lang.String");
    }

    private static int escapePaddingString(CharSequence charSequence, StringBuilder sb, int i) {
        if (charSequence == null || charSequence.length() == 0) {
            charSequence = " ";
        }
        int length = sb.length();
        int i2 = 1;
        if (charSequence.length() == 1) {
            if (charSequence.equals("'")) {
                sb.insert(i, "''");
            } else {
                sb.insert(i, charSequence);
            }
        } else {
            sb.insert(i, PatternTokenizer.SINGLE_QUOTE);
            for (int i3 = 0; i3 < charSequence.length(); i3++) {
                char cCharAt = charSequence.charAt(i3);
                if (cCharAt == '\'') {
                    sb.insert(i + i2, "''");
                    i2 += 2;
                } else {
                    sb.insert(i + i2, cCharAt);
                    i2++;
                }
            }
            sb.insert(i + i2, PatternTokenizer.SINGLE_QUOTE);
        }
        return sb.length() - length;
    }

    public static String convertLocalized(String str, DecimalFormatSymbols decimalFormatSymbols, boolean z) {
        if (str == null) {
            return null;
        }
        char c = 2;
        String[][] strArr = (String[][]) Array.newInstance((Class<?>) String.class, 21, 2);
        int i = !z ? 1 : 0;
        char c2 = 0;
        strArr[0][i] = "%";
        strArr[0][z ? 1 : 0] = decimalFormatSymbols.getPercentString();
        strArr[1][i] = "‰";
        strArr[1][z ? 1 : 0] = decimalFormatSymbols.getPerMillString();
        strArr[2][i] = ".";
        strArr[2][z ? 1 : 0] = decimalFormatSymbols.getDecimalSeparatorString();
        strArr[3][i] = ConstantsBase.PROPERTIES_PARAMS_SEPARATOR;
        strArr[3][z ? 1 : 0] = decimalFormatSymbols.getGroupingSeparatorString();
        strArr[4][i] = LanguageTag.SEP;
        strArr[4][z ? 1 : 0] = decimalFormatSymbols.getMinusSignString();
        strArr[5][i] = Marker.ANY_NON_NULL_MARKER;
        strArr[5][z ? 1 : 0] = decimalFormatSymbols.getPlusSignString();
        strArr[6][i] = ";";
        strArr[6][z ? 1 : 0] = Character.toString(decimalFormatSymbols.getPatternSeparator());
        strArr[7][i] = "@";
        strArr[7][z ? 1 : 0] = Character.toString(decimalFormatSymbols.getSignificantDigit());
        strArr[8][i] = DateFormat.ABBR_WEEKDAY;
        strArr[8][z ? 1 : 0] = decimalFormatSymbols.getExponentSeparator();
        strArr[9][i] = "*";
        strArr[9][z ? 1 : 0] = Character.toString(decimalFormatSymbols.getPadEscape());
        strArr[10][i] = "#";
        strArr[10][z ? 1 : 0] = Character.toString(decimalFormatSymbols.getDigit());
        for (int i2 = 0; i2 < 10; i2++) {
            int i3 = i2 + 11;
            strArr[i3][i] = Character.toString((char) (i2 + 48));
            strArr[i3][z ? 1 : 0] = decimalFormatSymbols.getDigitStringsLocal()[i2];
        }
        for (int i4 = 0; i4 < strArr.length; i4++) {
            strArr[i4][z ? 1 : 0] = strArr[i4][z ? 1 : 0].replace(PatternTokenizer.SINGLE_QUOTE, Typography.rightSingleQuote);
        }
        StringBuilder sb = new StringBuilder();
        int length = 0;
        char c3 = 0;
        while (length < str.length()) {
            char cCharAt = str.charAt(length);
            if (cCharAt == '\'') {
                if (c3 == 0) {
                    sb.append(PatternTokenizer.SINGLE_QUOTE);
                } else if (c3 == 1) {
                    sb.append(PatternTokenizer.SINGLE_QUOTE);
                    c3 = 0;
                } else if (c3 == c) {
                    c3 = 3;
                } else if (c3 == 3) {
                    sb.append(PatternTokenizer.SINGLE_QUOTE);
                    sb.append(PatternTokenizer.SINGLE_QUOTE);
                } else if (c3 == 4) {
                    c3 = 5;
                } else {
                    sb.append(PatternTokenizer.SINGLE_QUOTE);
                    sb.append(PatternTokenizer.SINGLE_QUOTE);
                    c3 = 4;
                }
                c3 = 1;
            } else if (c3 == 0 || c3 == 3 || c3 == 4) {
                int length2 = strArr.length;
                int i5 = 0;
                while (true) {
                    if (i5 < length2) {
                        String[] strArr2 = strArr[i5];
                        if (str.regionMatches(length, strArr2[0], 0, strArr2[0].length())) {
                            length += strArr2[0].length() - 1;
                            if (c3 == 3 || c3 == 4) {
                                sb.append(PatternTokenizer.SINGLE_QUOTE);
                                c3 = 0;
                            }
                            sb.append(strArr2[1]);
                        } else {
                            i5++;
                        }
                    } else {
                        int length3 = strArr.length;
                        int i6 = 0;
                        while (true) {
                            if (i6 < length3) {
                                String[] strArr3 = strArr[i6];
                                if (str.regionMatches(length, strArr3[1], 0, strArr3[1].length())) {
                                    if (c3 == 0) {
                                        sb.append(PatternTokenizer.SINGLE_QUOTE);
                                        c3 = 4;
                                    }
                                    sb.append(cCharAt);
                                } else {
                                    i6++;
                                }
                            } else {
                                if (c3 == 3 || c3 == 4) {
                                    sb.append(PatternTokenizer.SINGLE_QUOTE);
                                    c3 = 0;
                                }
                                sb.append(cCharAt);
                            }
                        }
                    }
                }
            } else {
                sb.append(cCharAt);
                c3 = 2;
            }
            length++;
            c = 2;
        }
        if (c3 == 3 || c3 == 4) {
            sb.append(PatternTokenizer.SINGLE_QUOTE);
        } else {
            c2 = c3;
        }
        if (c2 != 0) {
            throw new IllegalArgumentException("Malformed localized pattern: unterminated quote");
        }
        return sb.toString();
    }

    public static void patternInfoToStringBuilder(AffixPatternProvider affixPatternProvider, boolean z, PatternSignType patternSignType, StandardPlural standardPlural, boolean z2, StringBuilder sb) {
        char cCharAt;
        int i = 1;
        int i2 = (patternSignType != PatternSignType.POS_SIGN || affixPatternProvider.positiveHasPlusSign()) ? 0 : 1;
        boolean z3 = affixPatternProvider.hasNegativeSubpattern() && (patternSignType == PatternSignType.NEG || (affixPatternProvider.negativeHasMinusSign() && i2 != 0));
        int iOrdinal = z3 ? 512 : 0;
        if (z) {
            iOrdinal |= 256;
        }
        if (standardPlural != null) {
            iOrdinal |= standardPlural.ordinal();
        }
        if (!z || z3) {
            i = 0;
        } else if (patternSignType != PatternSignType.NEG) {
            i = i2;
        }
        int length = affixPatternProvider.length(iOrdinal) + i;
        sb.setLength(0);
        for (int i3 = 0; i3 < length; i3++) {
            if (i != 0 && i3 == 0) {
                cCharAt = Soundex.SILENT_MARKER;
            } else if (i != 0) {
                cCharAt = affixPatternProvider.charAt(iOrdinal, i3 - 1);
            } else {
                cCharAt = affixPatternProvider.charAt(iOrdinal, i3);
            }
            if (i2 != 0 && cCharAt == '-') {
                cCharAt = '+';
            }
            if (z2 && cCharAt == '%') {
                cCharAt = 8240;
            }
            sb.append(cCharAt);
        }
    }

    /* renamed from: com.ibm.icu.impl.number.PatternStringUtils$1, reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$ibm$icu$impl$number$Modifier$Signum;
        static final /* synthetic */ int[] $SwitchMap$com$ibm$icu$impl$number$Padder$PadPosition;
        static final /* synthetic */ int[] $SwitchMap$com$ibm$icu$number$NumberFormatter$SignDisplay;

        static {
            int[] iArr = new int[NumberFormatter.SignDisplay.values().length];
            $SwitchMap$com$ibm$icu$number$NumberFormatter$SignDisplay = iArr;
            try {
                iArr[NumberFormatter.SignDisplay.AUTO.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$ibm$icu$number$NumberFormatter$SignDisplay[NumberFormatter.SignDisplay.ACCOUNTING.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$ibm$icu$number$NumberFormatter$SignDisplay[NumberFormatter.SignDisplay.ALWAYS.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$com$ibm$icu$number$NumberFormatter$SignDisplay[NumberFormatter.SignDisplay.ACCOUNTING_ALWAYS.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$com$ibm$icu$number$NumberFormatter$SignDisplay[NumberFormatter.SignDisplay.EXCEPT_ZERO.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                $SwitchMap$com$ibm$icu$number$NumberFormatter$SignDisplay[NumberFormatter.SignDisplay.ACCOUNTING_EXCEPT_ZERO.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                $SwitchMap$com$ibm$icu$number$NumberFormatter$SignDisplay[NumberFormatter.SignDisplay.NEGATIVE.ordinal()] = 7;
            } catch (NoSuchFieldError unused7) {
            }
            try {
                $SwitchMap$com$ibm$icu$number$NumberFormatter$SignDisplay[NumberFormatter.SignDisplay.ACCOUNTING_NEGATIVE.ordinal()] = 8;
            } catch (NoSuchFieldError unused8) {
            }
            try {
                $SwitchMap$com$ibm$icu$number$NumberFormatter$SignDisplay[NumberFormatter.SignDisplay.NEVER.ordinal()] = 9;
            } catch (NoSuchFieldError unused9) {
            }
            int[] iArr2 = new int[Modifier.Signum.values().length];
            $SwitchMap$com$ibm$icu$impl$number$Modifier$Signum = iArr2;
            try {
                iArr2[Modifier.Signum.NEG.ordinal()] = 1;
            } catch (NoSuchFieldError unused10) {
            }
            try {
                $SwitchMap$com$ibm$icu$impl$number$Modifier$Signum[Modifier.Signum.NEG_ZERO.ordinal()] = 2;
            } catch (NoSuchFieldError unused11) {
            }
            try {
                $SwitchMap$com$ibm$icu$impl$number$Modifier$Signum[Modifier.Signum.POS_ZERO.ordinal()] = 3;
            } catch (NoSuchFieldError unused12) {
            }
            try {
                $SwitchMap$com$ibm$icu$impl$number$Modifier$Signum[Modifier.Signum.POS.ordinal()] = 4;
            } catch (NoSuchFieldError unused13) {
            }
            int[] iArr3 = new int[Padder.PadPosition.values().length];
            $SwitchMap$com$ibm$icu$impl$number$Padder$PadPosition = iArr3;
            try {
                iArr3[Padder.PadPosition.BEFORE_PREFIX.ordinal()] = 1;
            } catch (NoSuchFieldError unused14) {
            }
            try {
                $SwitchMap$com$ibm$icu$impl$number$Padder$PadPosition[Padder.PadPosition.AFTER_PREFIX.ordinal()] = 2;
            } catch (NoSuchFieldError unused15) {
            }
            try {
                $SwitchMap$com$ibm$icu$impl$number$Padder$PadPosition[Padder.PadPosition.BEFORE_SUFFIX.ordinal()] = 3;
            } catch (NoSuchFieldError unused16) {
            }
            try {
                $SwitchMap$com$ibm$icu$impl$number$Padder$PadPosition[Padder.PadPosition.AFTER_SUFFIX.ordinal()] = 4;
            } catch (NoSuchFieldError unused17) {
            }
        }
    }

    public static PatternSignType resolveSignDisplay(NumberFormatter.SignDisplay signDisplay, Modifier.Signum signum) {
        switch (AnonymousClass1.$SwitchMap$com$ibm$icu$number$NumberFormatter$SignDisplay[signDisplay.ordinal()]) {
            case 1:
            case 2:
                int i = AnonymousClass1.$SwitchMap$com$ibm$icu$impl$number$Modifier$Signum[signum.ordinal()];
                if (i == 1 || i == 2) {
                    return PatternSignType.NEG;
                }
                if (i == 3 || i == 4) {
                    return PatternSignType.POS;
                }
            case 3:
            case 4:
                int i2 = AnonymousClass1.$SwitchMap$com$ibm$icu$impl$number$Modifier$Signum[signum.ordinal()];
                if (i2 == 1 || i2 == 2) {
                    return PatternSignType.NEG;
                }
                if (i2 == 3 || i2 == 4) {
                    return PatternSignType.POS_SIGN;
                }
            case 5:
            case 6:
                int i3 = AnonymousClass1.$SwitchMap$com$ibm$icu$impl$number$Modifier$Signum[signum.ordinal()];
                if (i3 == 1) {
                    return PatternSignType.NEG;
                }
                if (i3 == 2 || i3 == 3) {
                    return PatternSignType.POS;
                }
                if (i3 == 4) {
                    return PatternSignType.POS_SIGN;
                }
                break;
            case 7:
            case 8:
                int i4 = AnonymousClass1.$SwitchMap$com$ibm$icu$impl$number$Modifier$Signum[signum.ordinal()];
                if (i4 == 1) {
                    return PatternSignType.NEG;
                }
                if (i4 == 2 || i4 == 3 || i4 == 4) {
                    return PatternSignType.POS;
                }
            case 9:
                return PatternSignType.POS;
        }
        throw new AssertionError("Unreachable");
    }
}
