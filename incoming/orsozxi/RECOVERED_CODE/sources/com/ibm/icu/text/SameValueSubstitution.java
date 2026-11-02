package com.ibm.icu.text;

/* compiled from: NFSubstitution.java */
/* loaded from: classes2.dex */
class SameValueSubstitution extends NFSubstitution {
    @Override // com.ibm.icu.text.NFSubstitution
    public double calcUpperBound(double d) {
        return d;
    }

    @Override // com.ibm.icu.text.NFSubstitution
    public double composeRuleValue(double d, double d2) {
        return d;
    }

    @Override // com.ibm.icu.text.NFSubstitution
    char tokenChar() {
        return '=';
    }

    @Override // com.ibm.icu.text.NFSubstitution
    public double transformNumber(double d) {
        return d;
    }

    @Override // com.ibm.icu.text.NFSubstitution
    public long transformNumber(long j) {
        return j;
    }

    SameValueSubstitution(int i, NFRuleSet nFRuleSet, String str) {
        super(i, nFRuleSet, str);
        if (str.equals("==")) {
            throw new IllegalArgumentException("== is not a legal token");
        }
    }
}
