package com.ibm.icu.text;

import com.ibm.icu.impl.PatternTokenizer;
import com.ibm.icu.text.PluralRules;
import com.ibm.icu.util.ULocale;
import java.text.FieldPosition;
import java.text.ParsePosition;
import java.util.Objects;
import kotlin.text.Typography;
import org.apache.commons.io.IOUtils;

/* loaded from: classes2.dex */
final class NFRule {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    static final int DEFAULT_RULE = -4;
    static final int IMPROPER_FRACTION_RULE = -2;
    static final int INFINITY_RULE = -5;
    static final int NAN_RULE = -6;
    static final int NEGATIVE_NUMBER_RULE = -1;
    static final int PROPER_FRACTION_RULE = -3;
    private long baseValue;
    private final RuleBasedNumberFormat formatter;
    private String ruleText;
    static final Long ZERO = 0L;
    private static final String[] RULE_PREFIXES = {"<<", "<%", "<#", "<0", ">>", ">%", ">#", ">0", "=%", "=#", "=0"};
    private int radix = 10;
    private short exponent = 0;
    private char decimalPoint = 0;
    private PluralFormat rulePatternFormat = null;
    private NFSubstitution sub1 = null;
    private NFSubstitution sub2 = null;

    public int hashCode() {
        return 42;
    }

    /* JADX WARN: Removed duplicated region for block: B:28:0x006e  */
    /* JADX WARN: Removed duplicated region for block: B:52:0x00ff  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public static void makeRules(java.lang.String r19, com.ibm.icu.text.NFRuleSet r20, com.ibm.icu.text.NFRule r21, com.ibm.icu.text.RuleBasedNumberFormat r22, java.util.List<com.ibm.icu.text.NFRule> r23) {
        /*
            Method dump skipped, instructions count: 274
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ibm.icu.text.NFRule.makeRules(java.lang.String, com.ibm.icu.text.NFRuleSet, com.ibm.icu.text.NFRule, com.ibm.icu.text.RuleBasedNumberFormat, java.util.List):void");
    }

    public NFRule(RuleBasedNumberFormat ruleBasedNumberFormat, String str) {
        this.ruleText = null;
        this.formatter = ruleBasedNumberFormat;
        this.ruleText = str != null ? parseRuleDescriptor(str) : null;
    }

    /* JADX WARN: Removed duplicated region for block: B:65:0x010e  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    private java.lang.String parseRuleDescriptor(java.lang.String r19) {
        /*
            Method dump skipped, instructions count: 389
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ibm.icu.text.NFRule.parseRuleDescriptor(java.lang.String):java.lang.String");
    }

    private void extractSubstitutions(NFRuleSet nFRuleSet, String str, NFRule nFRule) {
        PluralRules.PluralType pluralType;
        this.ruleText = str;
        NFSubstitution nFSubstitutionExtractSubstitution = extractSubstitution(nFRuleSet, nFRule);
        this.sub1 = nFSubstitutionExtractSubstitution;
        if (nFSubstitutionExtractSubstitution == null) {
            this.sub2 = null;
        } else {
            this.sub2 = extractSubstitution(nFRuleSet, nFRule);
        }
        String str2 = this.ruleText;
        int iIndexOf = str2.indexOf("$(");
        int iIndexOf2 = iIndexOf >= 0 ? str2.indexOf(")$", iIndexOf) : -1;
        if (iIndexOf2 >= 0) {
            int iIndexOf3 = str2.indexOf(44, iIndexOf);
            if (iIndexOf3 < 0) {
                throw new IllegalArgumentException("Rule \"" + str2 + "\" does not have a defined type");
            }
            String strSubstring = this.ruleText.substring(iIndexOf + 2, iIndexOf3);
            if ("cardinal".equals(strSubstring)) {
                pluralType = PluralRules.PluralType.CARDINAL;
            } else if ("ordinal".equals(strSubstring)) {
                pluralType = PluralRules.PluralType.ORDINAL;
            } else {
                throw new IllegalArgumentException(strSubstring + " is an unknown type");
            }
            this.rulePatternFormat = this.formatter.createPluralFormat(pluralType, str2.substring(iIndexOf3 + 1, iIndexOf2));
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:16:0x0042  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    private com.ibm.icu.text.NFSubstitution extractSubstitution(com.ibm.icu.text.NFRuleSet r9, com.ibm.icu.text.NFRule r10) {
        /*
            r8 = this;
            java.lang.String r0 = r8.ruleText
            int r0 = indexOfAnyRulePrefix(r0)
            r1 = 0
            r2 = -1
            if (r0 != r2) goto Lb
            return r1
        Lb:
            java.lang.String r3 = r8.ruleText
            java.lang.String r4 = ">>>"
            boolean r3 = r3.startsWith(r4, r0)
            if (r3 == 0) goto L18
            int r3 = r0 + 2
            goto L43
        L18:
            java.lang.String r3 = r8.ruleText
            char r3 = r3.charAt(r0)
            java.lang.String r4 = r8.ruleText
            int r5 = r0 + 1
            int r4 = r4.indexOf(r3, r5)
            r5 = 60
            if (r3 != r5) goto L42
            if (r4 == r2) goto L42
            java.lang.String r5 = r8.ruleText
            int r5 = r5.length()
            int r5 = r5 + (-1)
            if (r4 >= r5) goto L42
            java.lang.String r5 = r8.ruleText
            int r6 = r4 + 1
            char r5 = r5.charAt(r6)
            if (r5 != r3) goto L42
            r3 = r6
            goto L43
        L42:
            r3 = r4
        L43:
            if (r3 != r2) goto L46
            return r1
        L46:
            com.ibm.icu.text.RuleBasedNumberFormat r5 = r8.formatter
            java.lang.String r1 = r8.ruleText
            int r7 = r3 + 1
            java.lang.String r6 = r1.substring(r0, r7)
            r1 = r0
            r2 = r8
            r3 = r10
            r4 = r9
            com.ibm.icu.text.NFSubstitution r9 = com.ibm.icu.text.NFSubstitution.makeSubstitution(r1, r2, r3, r4, r5, r6)
            java.lang.StringBuilder r10 = new java.lang.StringBuilder
            r10.<init>()
            java.lang.String r1 = r8.ruleText
            r2 = 0
            java.lang.String r0 = r1.substring(r2, r0)
            r10.append(r0)
            java.lang.String r0 = r8.ruleText
            java.lang.String r0 = r0.substring(r7)
            r10.append(r0)
            java.lang.String r10 = r10.toString()
            r8.ruleText = r10
            return r9
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ibm.icu.text.NFRule.extractSubstitution(com.ibm.icu.text.NFRuleSet, com.ibm.icu.text.NFRule):com.ibm.icu.text.NFSubstitution");
    }

    final void setBaseValue(long j) {
        this.baseValue = j;
        this.radix = 10;
        if (j >= 1) {
            short sExpectedExponent = expectedExponent();
            this.exponent = sExpectedExponent;
            NFSubstitution nFSubstitution = this.sub1;
            if (nFSubstitution != null) {
                nFSubstitution.setDivisor(this.radix, sExpectedExponent);
            }
            NFSubstitution nFSubstitution2 = this.sub2;
            if (nFSubstitution2 != null) {
                nFSubstitution2.setDivisor(this.radix, this.exponent);
                return;
            }
            return;
        }
        this.exponent = (short) 0;
    }

    private short expectedExponent() {
        if (this.radix == 0) {
            return (short) 0;
        }
        if (this.baseValue < 1) {
            return (short) 0;
        }
        short sLog = (short) (Math.log(r0) / Math.log(this.radix));
        short s = (short) (sLog + 1);
        return power((long) this.radix, s) <= this.baseValue ? s : sLog;
    }

    private static int indexOfAnyRulePrefix(String str) {
        if (str.length() <= 0) {
            return -1;
        }
        int i = -1;
        for (String str2 : RULE_PREFIXES) {
            int iIndexOf = str.indexOf(str2);
            if (iIndexOf != -1 && (i == -1 || iIndexOf < i)) {
                i = iIndexOf;
            }
        }
        return i;
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof NFRule)) {
            return false;
        }
        NFRule nFRule = (NFRule) obj;
        return this.baseValue == nFRule.baseValue && this.radix == nFRule.radix && this.exponent == nFRule.exponent && this.ruleText.equals(nFRule.ruleText) && Objects.equals(this.sub1, nFRule.sub1) && Objects.equals(this.sub2, nFRule.sub2);
    }

    public String toString() {
        NFSubstitution nFSubstitution;
        StringBuilder sb = new StringBuilder();
        long j = this.baseValue;
        if (j == -1) {
            sb.append("-x: ");
        } else if (j == -2) {
            sb.append(ULocale.PRIVATE_USE_EXTENSION);
            char c = this.decimalPoint;
            sb.append(c != 0 ? c : '.');
            sb.append("x: ");
        } else if (j == -3) {
            sb.append('0');
            char c2 = this.decimalPoint;
            sb.append(c2 != 0 ? c2 : '.');
            sb.append("x: ");
        } else if (j == -4) {
            sb.append(ULocale.PRIVATE_USE_EXTENSION);
            char c3 = this.decimalPoint;
            sb.append(c3 != 0 ? c3 : '.');
            sb.append("0: ");
        } else if (j == -5) {
            sb.append("Inf: ");
        } else if (j == -6) {
            sb.append("NaN: ");
        } else {
            sb.append(String.valueOf(j));
            if (this.radix != 10) {
                sb.append(IOUtils.DIR_SEPARATOR_UNIX);
                sb.append(this.radix);
            }
            int iExpectedExponent = expectedExponent() - this.exponent;
            for (int i = 0; i < iExpectedExponent; i++) {
                sb.append(Typography.greater);
            }
            sb.append(": ");
        }
        if (this.ruleText.startsWith(" ") && ((nFSubstitution = this.sub1) == null || nFSubstitution.getPos() != 0)) {
            sb.append(PatternTokenizer.SINGLE_QUOTE);
        }
        StringBuilder sb2 = new StringBuilder(this.ruleText);
        NFSubstitution nFSubstitution2 = this.sub2;
        if (nFSubstitution2 != null) {
            sb2.insert(nFSubstitution2.getPos(), this.sub2.toString());
        }
        NFSubstitution nFSubstitution3 = this.sub1;
        if (nFSubstitution3 != null) {
            sb2.insert(nFSubstitution3.getPos(), this.sub1.toString());
        }
        sb.append(sb2.toString());
        sb.append(';');
        return sb.toString();
    }

    public final char getDecimalPoint() {
        return this.decimalPoint;
    }

    public final long getBaseValue() {
        return this.baseValue;
    }

    public long getDivisor() {
        return power(this.radix, this.exponent);
    }

    public void doFormat(long j, StringBuilder sb, int i, int i2) {
        int i3;
        int length;
        int length2 = this.ruleText.length();
        if (this.rulePatternFormat == null) {
            sb.insert(i, this.ruleText);
            i3 = length2;
            length = 0;
        } else {
            int iIndexOf = this.ruleText.indexOf("$(");
            int iIndexOf2 = this.ruleText.indexOf(")$", iIndexOf);
            int length3 = sb.length();
            if (iIndexOf2 < this.ruleText.length() - 1) {
                sb.insert(i, this.ruleText.substring(iIndexOf2 + 2));
            }
            sb.insert(i, this.rulePatternFormat.format(j / power(this.radix, this.exponent)));
            if (iIndexOf > 0) {
                sb.insert(i, this.ruleText.substring(0, iIndexOf));
            }
            i3 = iIndexOf;
            length = this.ruleText.length() - (sb.length() - length3);
        }
        NFSubstitution nFSubstitution = this.sub2;
        if (nFSubstitution != null) {
            nFSubstitution.doSubstitution(j, sb, i - (nFSubstitution.getPos() > i3 ? length : 0), i2);
        }
        NFSubstitution nFSubstitution2 = this.sub1;
        if (nFSubstitution2 != null) {
            nFSubstitution2.doSubstitution(j, sb, i - (nFSubstitution2.getPos() > i3 ? length : 0), i2);
        }
    }

    public void doFormat(double d, StringBuilder sb, int i, int i2) {
        double dPower;
        int i3;
        int length;
        int length2 = this.ruleText.length();
        if (this.rulePatternFormat == null) {
            sb.insert(i, this.ruleText);
            i3 = length2;
            length = 0;
        } else {
            int iIndexOf = this.ruleText.indexOf("$(");
            int iIndexOf2 = this.ruleText.indexOf(")$", iIndexOf);
            int length3 = sb.length();
            if (iIndexOf2 < this.ruleText.length() - 1) {
                sb.insert(i, this.ruleText.substring(iIndexOf2 + 2));
            }
            if (0.0d <= d && d < 1.0d) {
                dPower = Math.round(power(this.radix, this.exponent) * d);
            } else {
                dPower = d / power(this.radix, this.exponent);
            }
            sb.insert(i, this.rulePatternFormat.format((long) dPower));
            if (iIndexOf > 0) {
                sb.insert(i, this.ruleText.substring(0, iIndexOf));
            }
            i3 = iIndexOf;
            length = this.ruleText.length() - (sb.length() - length3);
        }
        NFSubstitution nFSubstitution = this.sub2;
        if (nFSubstitution != null) {
            nFSubstitution.doSubstitution(d, sb, i - (nFSubstitution.getPos() > i3 ? length : 0), i2);
        }
        NFSubstitution nFSubstitution2 = this.sub1;
        if (nFSubstitution2 != null) {
            nFSubstitution2.doSubstitution(d, sb, i - (nFSubstitution2.getPos() > i3 ? length : 0), i2);
        }
    }

    static long power(long j, short s) {
        if (s < 0) {
            throw new IllegalArgumentException("Exponent can not be negative");
        }
        if (j < 0) {
            throw new IllegalArgumentException("Base can not be negative");
        }
        long j2 = 1;
        while (s > 0) {
            if ((s & 1) == 1) {
                j2 *= j;
            }
            j *= j;
            s = (short) (s >> 1);
        }
        return j2;
    }

    public boolean shouldRollBack(long j) {
        NFSubstitution nFSubstitution;
        NFSubstitution nFSubstitution2 = this.sub1;
        if ((nFSubstitution2 == null || !nFSubstitution2.isModulusSubstitution()) && ((nFSubstitution = this.sub2) == null || !nFSubstitution.isModulusSubstitution())) {
            return false;
        }
        long jPower = power(this.radix, this.exponent);
        return j % jPower == 0 && this.baseValue % jPower != 0;
    }

    public Number doParse(String str, ParsePosition parsePosition, boolean z, double d, int i) {
        int i2;
        int i3;
        int i4;
        int i5 = 0;
        ParsePosition parsePosition2 = new ParsePosition(0);
        NFSubstitution nFSubstitution = this.sub1;
        int pos = nFSubstitution != null ? nFSubstitution.getPos() : this.ruleText.length();
        NFSubstitution nFSubstitution2 = this.sub2;
        int pos2 = nFSubstitution2 != null ? nFSubstitution2.getPos() : this.ruleText.length();
        String strStripPrefix = stripPrefix(str, this.ruleText.substring(0, pos), parsePosition2);
        int length = str.length() - strStripPrefix.length();
        if (parsePosition2.getIndex() == 0 && pos != 0) {
            return ZERO;
        }
        long j = this.baseValue;
        if (j == -5) {
            parsePosition.setIndex(parsePosition2.getIndex());
            return Double.valueOf(Double.POSITIVE_INFINITY);
        }
        if (j == -6) {
            parsePosition.setIndex(parsePosition2.getIndex());
            return Double.valueOf(Double.NaN);
        }
        double dMax = Math.max(0L, j);
        double d2 = 0.0d;
        int index = 0;
        int i6 = 0;
        while (true) {
            parsePosition2.setIndex(i5);
            int i7 = index;
            double d3 = dMax;
            int i8 = pos2;
            String str2 = strStripPrefix;
            int i9 = pos;
            double dDoubleValue = matchToDelimiter(strStripPrefix, i6, dMax, this.ruleText.substring(pos, pos2), this.rulePatternFormat, parsePosition2, this.sub1, d, i).doubleValue();
            if (parsePosition2.getIndex() != 0 || this.sub1 == null) {
                int index2 = parsePosition2.getIndex();
                String strSubstring = str2.substring(parsePosition2.getIndex());
                ParsePosition parsePosition3 = new ParsePosition(0);
                double dDoubleValue2 = matchToDelimiter(strSubstring, 0, dDoubleValue, this.ruleText.substring(i8), this.rulePatternFormat, parsePosition3, this.sub2, d, i).doubleValue();
                if (parsePosition3.getIndex() != 0 || this.sub2 == null) {
                    i2 = i7;
                    if (length + parsePosition2.getIndex() + parsePosition3.getIndex() > i2) {
                        index = length + parsePosition2.getIndex() + parsePosition3.getIndex();
                        d2 = dDoubleValue2;
                    }
                    i3 = index2;
                    i4 = i8;
                } else {
                    i2 = i7;
                }
                index = i2;
                i3 = index2;
                i4 = i8;
            } else {
                i3 = i6;
                index = i7;
                i4 = i8;
            }
            if (i9 == i4 || parsePosition2.getIndex() <= 0 || parsePosition2.getIndex() >= str2.length() || parsePosition2.getIndex() == i3) {
                break;
            }
            i6 = i3;
            pos2 = i4;
            pos = i9;
            strStripPrefix = str2;
            dMax = d3;
            i5 = 0;
        }
        parsePosition.setIndex(index);
        if (z && index > 0 && this.sub1 == null) {
            d2 = 1.0d / d2;
        }
        double d4 = d2;
        long j2 = (long) d4;
        if (d4 == j2) {
            return Long.valueOf(j2);
        }
        return new Double(d4);
    }

    private String stripPrefix(String str, String str2, ParsePosition parsePosition) {
        int iPrefixLength;
        if (str2.length() == 0 || (iPrefixLength = prefixLength(str, str2)) == 0) {
            return str;
        }
        parsePosition.setIndex(parsePosition.getIndex() + iPrefixLength);
        return str.substring(iPrefixLength);
    }

    private Number matchToDelimiter(String str, int i, double d, String str2, PluralFormat pluralFormat, ParsePosition parsePosition, NFSubstitution nFSubstitution, double d2, int i2) {
        if (allIgnorable(str2)) {
            if (nFSubstitution == null) {
                return Double.valueOf(d);
            }
            ParsePosition parsePosition2 = new ParsePosition(0);
            Long l = ZERO;
            Number numberDoParse = nFSubstitution.doParse(str, parsePosition2, d, d2, this.formatter.lenientParseEnabled(), i2);
            if (parsePosition2.getIndex() == 0) {
                return l;
            }
            parsePosition.setIndex(parsePosition2.getIndex());
            return numberDoParse != null ? numberDoParse : l;
        }
        ParsePosition parsePosition3 = new ParsePosition(0);
        int[] iArrFindText = findText(str, str2, pluralFormat, i);
        int i3 = iArrFindText[0];
        int i4 = iArrFindText[1];
        while (i3 >= 0) {
            String strSubstring = str.substring(0, i3);
            if (strSubstring.length() > 0) {
                Number numberDoParse2 = nFSubstitution.doParse(strSubstring, parsePosition3, d, d2, this.formatter.lenientParseEnabled(), i2);
                if (parsePosition3.getIndex() == i3) {
                    parsePosition.setIndex(i3 + i4);
                    return numberDoParse2;
                }
            }
            parsePosition3.setIndex(0);
            int[] iArrFindText2 = findText(str, str2, pluralFormat, i3 + i4);
            i3 = iArrFindText2[0];
            i4 = iArrFindText2[1];
        }
        parsePosition.setIndex(0);
        return ZERO;
    }

    private int prefixLength(String str, String str2) {
        if (str2.length() == 0) {
            return 0;
        }
        RbnfLenientScanner lenientScanner = this.formatter.getLenientScanner();
        if (lenientScanner != null) {
            if (str.startsWith(str2)) {
                return str2.length();
            }
            return lenientScanner.prefixLength(str, str2);
        }
        if (str.startsWith(str2)) {
            return str2.length();
        }
        return 0;
    }

    private int[] findText(String str, String str2, PluralFormat pluralFormat, int i) {
        RbnfLenientScanner lenientScanner = this.formatter.getLenientScanner();
        if (pluralFormat == null) {
            if (lenientScanner == null) {
                return new int[]{str.indexOf(str2, i), str2.length()};
            }
            int[] iArr = {str.indexOf(str2, i), str2.length()};
            return iArr[0] >= 0 ? iArr : lenientScanner.findText(str, str2, i);
        }
        FieldPosition fieldPosition = new FieldPosition(0);
        fieldPosition.setBeginIndex(i);
        pluralFormat.parseType(str, lenientScanner, fieldPosition);
        int beginIndex = fieldPosition.getBeginIndex();
        if (beginIndex >= 0) {
            int iIndexOf = this.ruleText.indexOf("$(");
            int iIndexOf2 = this.ruleText.indexOf(")$", iIndexOf) + 2;
            int endIndex = fieldPosition.getEndIndex() - beginIndex;
            String strSubstring = this.ruleText.substring(0, iIndexOf);
            String strSubstring2 = this.ruleText.substring(iIndexOf2);
            if (str.regionMatches(beginIndex - strSubstring.length(), strSubstring, 0, strSubstring.length()) && str.regionMatches(beginIndex + endIndex, strSubstring2, 0, strSubstring2.length())) {
                return new int[]{beginIndex - strSubstring.length(), endIndex + strSubstring.length() + strSubstring2.length()};
            }
        }
        return new int[]{-1, 0};
    }

    private boolean allIgnorable(String str) {
        if (str == null || str.length() == 0) {
            return true;
        }
        RbnfLenientScanner lenientScanner = this.formatter.getLenientScanner();
        return lenientScanner != null && lenientScanner.allIgnorable(str);
    }

    public void setDecimalFormatSymbols(DecimalFormatSymbols decimalFormatSymbols) {
        NFSubstitution nFSubstitution = this.sub1;
        if (nFSubstitution != null) {
            nFSubstitution.setDecimalFormatSymbols(decimalFormatSymbols);
        }
        NFSubstitution nFSubstitution2 = this.sub2;
        if (nFSubstitution2 != null) {
            nFSubstitution2.setDecimalFormatSymbols(decimalFormatSymbols);
        }
    }
}
