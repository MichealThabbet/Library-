package com.ibm.icu.text;

import java.text.ParsePosition;

/* loaded from: classes2.dex */
abstract class NFSubstitution {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final long MAX_INT64_IN_DOUBLE = 9007199254740991L;
    final DecimalFormat numberFormat;
    final int pos;
    final NFRuleSet ruleSet;

    public abstract double calcUpperBound(double d);

    public abstract double composeRuleValue(double d, double d2);

    public int hashCode() {
        return 42;
    }

    public boolean isModulusSubstitution() {
        return false;
    }

    public void setDivisor(int i, short s) {
    }

    abstract char tokenChar();

    public abstract double transformNumber(double d);

    public abstract long transformNumber(long j);

    public static NFSubstitution makeSubstitution(int i, NFRule nFRule, NFRule nFRule2, NFRuleSet nFRuleSet, RuleBasedNumberFormat ruleBasedNumberFormat, String str) {
        if (str.length() == 0) {
            return null;
        }
        switch (str.charAt(0)) {
            case '<':
                if (nFRule.getBaseValue() == -1) {
                    throw new IllegalArgumentException("<< not allowed in negative-number rule");
                }
                if (nFRule.getBaseValue() == -2 || nFRule.getBaseValue() == -3 || nFRule.getBaseValue() == -4) {
                    return new IntegralPartSubstitution(i, nFRuleSet, str);
                }
                if (nFRuleSet.isFractionSet()) {
                    return new NumeratorSubstitution(i, nFRule.getBaseValue(), ruleBasedNumberFormat.getDefaultRuleSet(), str);
                }
                return new MultiplierSubstitution(i, nFRule, nFRuleSet, str);
            case '=':
                return new SameValueSubstitution(i, nFRuleSet, str);
            case '>':
                if (nFRule.getBaseValue() == -1) {
                    return new AbsoluteValueSubstitution(i, nFRuleSet, str);
                }
                if (nFRule.getBaseValue() == -2 || nFRule.getBaseValue() == -3 || nFRule.getBaseValue() == -4) {
                    return new FractionalPartSubstitution(i, nFRuleSet, str);
                }
                if (nFRuleSet.isFractionSet()) {
                    throw new IllegalArgumentException(">> not allowed in fraction rule set");
                }
                return new ModulusSubstitution(i, nFRule, nFRule2, nFRuleSet, str);
            default:
                throw new IllegalArgumentException("Illegal substitution character");
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:7:0x0021  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    NFSubstitution(int r6, com.ibm.icu.text.NFRuleSet r7, java.lang.String r8) {
        /*
            r5 = this;
            r5.<init>()
            r5.pos = r6
            int r6 = r8.length()
            java.lang.String r0 = "Illegal substitution syntax"
            r1 = 0
            r2 = 2
            if (r6 < r2) goto L21
            char r2 = r8.charAt(r1)
            int r3 = r6 + (-1)
            char r4 = r8.charAt(r3)
            if (r2 != r4) goto L21
            r6 = 1
            java.lang.String r8 = r8.substring(r6, r3)
            goto L23
        L21:
            if (r6 != 0) goto L7a
        L23:
            int r6 = r8.length()
            r2 = 0
            if (r6 != 0) goto L2f
            r5.ruleSet = r7
            r5.numberFormat = r2
            goto L79
        L2f:
            char r6 = r8.charAt(r1)
            r3 = 37
            if (r6 != r3) goto L42
            com.ibm.icu.text.RuleBasedNumberFormat r6 = r7.owner
            com.ibm.icu.text.NFRuleSet r6 = r6.findRuleSet(r8)
            r5.ruleSet = r6
            r5.numberFormat = r2
            goto L79
        L42:
            char r6 = r8.charAt(r1)
            r3 = 35
            if (r6 == r3) goto L66
            char r6 = r8.charAt(r1)
            r3 = 48
            if (r6 != r3) goto L53
            goto L66
        L53:
            char r6 = r8.charAt(r1)
            r8 = 62
            if (r6 != r8) goto L60
            r5.ruleSet = r7
            r5.numberFormat = r2
            goto L79
        L60:
            java.lang.IllegalArgumentException r6 = new java.lang.IllegalArgumentException
            r6.<init>(r0)
            throw r6
        L66:
            r5.ruleSet = r2
            com.ibm.icu.text.RuleBasedNumberFormat r6 = r7.owner
            com.ibm.icu.text.DecimalFormat r6 = r6.getDecimalFormat()
            java.lang.Object r6 = r6.clone()
            com.ibm.icu.text.DecimalFormat r6 = (com.ibm.icu.text.DecimalFormat) r6
            r5.numberFormat = r6
            r6.applyPattern(r8)
        L79:
            return
        L7a:
            java.lang.IllegalArgumentException r6 = new java.lang.IllegalArgumentException
            r6.<init>(r0)
            throw r6
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ibm.icu.text.NFSubstitution.<init>(int, com.ibm.icu.text.NFRuleSet, java.lang.String):void");
    }

    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        NFSubstitution nFSubstitution = (NFSubstitution) obj;
        if (this.pos != nFSubstitution.pos) {
            return false;
        }
        if (this.ruleSet == null && nFSubstitution.ruleSet != null) {
            return false;
        }
        DecimalFormat decimalFormat = this.numberFormat;
        DecimalFormat decimalFormat2 = nFSubstitution.numberFormat;
        if (decimalFormat == null) {
            if (decimalFormat2 != null) {
                return false;
            }
        } else if (!decimalFormat.equals(decimalFormat2)) {
            return false;
        }
        return true;
    }

    public String toString() {
        if (this.ruleSet != null) {
            return tokenChar() + this.ruleSet.getName() + tokenChar();
        }
        return tokenChar() + this.numberFormat.toPattern() + tokenChar();
    }

    public void doSubstitution(long j, StringBuilder sb, int i, int i2) {
        if (this.ruleSet != null) {
            this.ruleSet.format(transformNumber(j), sb, i + this.pos, i2);
        } else {
            if (j <= MAX_INT64_IN_DOUBLE) {
                double dTransformNumber = transformNumber(j);
                if (this.numberFormat.getMaximumFractionDigits() == 0) {
                    dTransformNumber = Math.floor(dTransformNumber);
                }
                sb.insert(i + this.pos, this.numberFormat.format(dTransformNumber));
                return;
            }
            sb.insert(i + this.pos, this.numberFormat.format(transformNumber(j)));
        }
    }

    public void doSubstitution(double d, StringBuilder sb, int i, int i2) {
        NFRuleSet nFRuleSet;
        double dTransformNumber = transformNumber(d);
        if (Double.isInfinite(dTransformNumber)) {
            this.ruleSet.findRule(Double.POSITIVE_INFINITY).doFormat(dTransformNumber, sb, i + this.pos, i2);
            return;
        }
        if (dTransformNumber == Math.floor(dTransformNumber) && (nFRuleSet = this.ruleSet) != null) {
            nFRuleSet.format((long) dTransformNumber, sb, i + this.pos, i2);
            return;
        }
        NFRuleSet nFRuleSet2 = this.ruleSet;
        if (nFRuleSet2 != null) {
            nFRuleSet2.format(dTransformNumber, sb, i + this.pos, i2);
        } else {
            sb.insert(i + this.pos, this.numberFormat.format(dTransformNumber));
        }
    }

    public Number doParse(String str, ParsePosition parsePosition, double d, double d2, boolean z, int i) {
        Number number;
        double dCalcUpperBound = calcUpperBound(d2);
        NFRuleSet nFRuleSet = this.ruleSet;
        if (nFRuleSet != null) {
            number = nFRuleSet.parse(str, parsePosition, dCalcUpperBound, i);
            if (z && !this.ruleSet.isFractionSet() && parsePosition.getIndex() == 0) {
                number = this.ruleSet.owner.getDecimalFormat().parse(str, parsePosition);
            }
        } else {
            number = this.numberFormat.parse(str, parsePosition);
        }
        if (parsePosition.getIndex() == 0) {
            return number;
        }
        double dComposeRuleValue = composeRuleValue(number.doubleValue(), d);
        long j = (long) dComposeRuleValue;
        if (dComposeRuleValue == j) {
            return Long.valueOf(j);
        }
        return new Double(dComposeRuleValue);
    }

    public final int getPos() {
        return this.pos;
    }

    public void setDecimalFormatSymbols(DecimalFormatSymbols decimalFormatSymbols) {
        DecimalFormat decimalFormat = this.numberFormat;
        if (decimalFormat != null) {
            decimalFormat.setDecimalFormatSymbols(decimalFormatSymbols);
        }
    }
}
