package com.ibm.icu.text;

import java.text.ParsePosition;
import kotlin.text.Typography;

/* compiled from: NFSubstitution.java */
/* loaded from: classes2.dex */
class ModulusSubstitution extends NFSubstitution {
    long divisor;
    private final NFRule ruleToUse;

    @Override // com.ibm.icu.text.NFSubstitution
    public boolean isModulusSubstitution() {
        return true;
    }

    @Override // com.ibm.icu.text.NFSubstitution
    char tokenChar() {
        return Typography.greater;
    }

    ModulusSubstitution(int i, NFRule nFRule, NFRule nFRule2, NFRuleSet nFRuleSet, String str) {
        super(i, nFRuleSet, str);
        long divisor = nFRule.getDivisor();
        this.divisor = divisor;
        if (divisor == 0) {
            throw new IllegalStateException("Substitution with bad divisor (" + this.divisor + ") " + str.substring(0, i) + " | " + str.substring(i));
        }
        if (str.equals(">>>")) {
            this.ruleToUse = nFRule2;
        } else {
            this.ruleToUse = null;
        }
    }

    @Override // com.ibm.icu.text.NFSubstitution
    public void setDivisor(int i, short s) {
        long jPower = NFRule.power(i, s);
        this.divisor = jPower;
        if (jPower == 0) {
            throw new IllegalStateException("Substitution with bad divisor");
        }
    }

    @Override // com.ibm.icu.text.NFSubstitution
    public boolean equals(Object obj) {
        return super.equals(obj) && this.divisor == ((ModulusSubstitution) obj).divisor;
    }

    @Override // com.ibm.icu.text.NFSubstitution
    public void doSubstitution(long j, StringBuilder sb, int i, int i2) {
        if (this.ruleToUse == null) {
            super.doSubstitution(j, sb, i, i2);
        } else {
            this.ruleToUse.doFormat(transformNumber(j), sb, i + this.pos, i2);
        }
    }

    @Override // com.ibm.icu.text.NFSubstitution
    public void doSubstitution(double d, StringBuilder sb, int i, int i2) {
        if (this.ruleToUse == null) {
            super.doSubstitution(d, sb, i, i2);
        } else {
            this.ruleToUse.doFormat(transformNumber(d), sb, i + this.pos, i2);
        }
    }

    @Override // com.ibm.icu.text.NFSubstitution
    public long transformNumber(long j) {
        return j % this.divisor;
    }

    @Override // com.ibm.icu.text.NFSubstitution
    public double transformNumber(double d) {
        return Math.floor(d % this.divisor);
    }

    @Override // com.ibm.icu.text.NFSubstitution
    public Number doParse(String str, ParsePosition parsePosition, double d, double d2, boolean z, int i) {
        NFRule nFRule = this.ruleToUse;
        if (nFRule == null) {
            return super.doParse(str, parsePosition, d, d2, z, i);
        }
        Number numberDoParse = nFRule.doParse(str, parsePosition, false, d2, i);
        if (parsePosition.getIndex() == 0) {
            return numberDoParse;
        }
        double dComposeRuleValue = composeRuleValue(numberDoParse.doubleValue(), d);
        long j = (long) dComposeRuleValue;
        if (dComposeRuleValue == j) {
            return Long.valueOf(j);
        }
        return new Double(dComposeRuleValue);
    }

    @Override // com.ibm.icu.text.NFSubstitution
    public double composeRuleValue(double d, double d2) {
        return (d2 - (d2 % this.divisor)) + d;
    }

    @Override // com.ibm.icu.text.NFSubstitution
    public double calcUpperBound(double d) {
        return this.divisor;
    }
}
