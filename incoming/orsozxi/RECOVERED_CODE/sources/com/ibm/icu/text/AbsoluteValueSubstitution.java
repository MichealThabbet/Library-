package com.ibm.icu.text;

import kotlin.text.Typography;

/* compiled from: NFSubstitution.java */
/* loaded from: classes2.dex */
class AbsoluteValueSubstitution extends NFSubstitution {
    @Override // com.ibm.icu.text.NFSubstitution
    public double calcUpperBound(double d) {
        return Double.MAX_VALUE;
    }

    @Override // com.ibm.icu.text.NFSubstitution
    public double composeRuleValue(double d, double d2) {
        return -d;
    }

    @Override // com.ibm.icu.text.NFSubstitution
    char tokenChar() {
        return Typography.greater;
    }

    AbsoluteValueSubstitution(int i, NFRuleSet nFRuleSet, String str) {
        super(i, nFRuleSet, str);
    }

    @Override // com.ibm.icu.text.NFSubstitution
    public long transformNumber(long j) {
        return Math.abs(j);
    }

    @Override // com.ibm.icu.text.NFSubstitution
    public double transformNumber(double d) {
        return Math.abs(d);
    }
}
