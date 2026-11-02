package com.ibm.icu.text;

import kotlin.text.Typography;

/* compiled from: NFSubstitution.java */
/* loaded from: classes2.dex */
class MultiplierSubstitution extends NFSubstitution {
    long divisor;

    @Override // com.ibm.icu.text.NFSubstitution
    char tokenChar() {
        return Typography.less;
    }

    MultiplierSubstitution(int i, NFRule nFRule, NFRuleSet nFRuleSet, String str) {
        super(i, nFRuleSet, str);
        long divisor = nFRule.getDivisor();
        this.divisor = divisor;
        if (divisor != 0) {
            return;
        }
        throw new IllegalStateException("Substitution with divisor 0 " + str.substring(0, i) + " | " + str.substring(i));
    }

    @Override // com.ibm.icu.text.NFSubstitution
    public void setDivisor(int i, short s) {
        long jPower = NFRule.power(i, s);
        this.divisor = jPower;
        if (jPower == 0) {
            throw new IllegalStateException("Substitution with divisor 0");
        }
    }

    @Override // com.ibm.icu.text.NFSubstitution
    public boolean equals(Object obj) {
        return super.equals(obj) && this.divisor == ((MultiplierSubstitution) obj).divisor;
    }

    @Override // com.ibm.icu.text.NFSubstitution
    public long transformNumber(long j) {
        return (long) Math.floor(j / this.divisor);
    }

    @Override // com.ibm.icu.text.NFSubstitution
    public double transformNumber(double d) {
        if (this.ruleSet == null) {
            return d / this.divisor;
        }
        return Math.floor(d / this.divisor);
    }

    @Override // com.ibm.icu.text.NFSubstitution
    public double composeRuleValue(double d, double d2) {
        return d * this.divisor;
    }

    @Override // com.ibm.icu.text.NFSubstitution
    public double calcUpperBound(double d) {
        return this.divisor;
    }
}
