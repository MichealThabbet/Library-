package com.ibm.icu.text;

import kotlin.text.Typography;

/* compiled from: NFSubstitution.java */
/* loaded from: classes2.dex */
class IntegralPartSubstitution extends NFSubstitution {
    @Override // com.ibm.icu.text.NFSubstitution
    public double calcUpperBound(double d) {
        return Double.MAX_VALUE;
    }

    @Override // com.ibm.icu.text.NFSubstitution
    public double composeRuleValue(double d, double d2) {
        return d + d2;
    }

    @Override // com.ibm.icu.text.NFSubstitution
    char tokenChar() {
        return Typography.less;
    }

    @Override // com.ibm.icu.text.NFSubstitution
    public long transformNumber(long j) {
        return j;
    }

    IntegralPartSubstitution(int i, NFRuleSet nFRuleSet, String str) {
        super(i, nFRuleSet, str);
    }

    @Override // com.ibm.icu.text.NFSubstitution
    public double transformNumber(double d) {
        return Math.floor(d);
    }
}
