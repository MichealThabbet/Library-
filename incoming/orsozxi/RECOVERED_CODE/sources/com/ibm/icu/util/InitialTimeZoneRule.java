package com.ibm.icu.util;

import java.util.Date;

/* loaded from: classes2.dex */
public class InitialTimeZoneRule extends TimeZoneRule {
    private static final long serialVersionUID = 1876594993064051206L;

    @Override // com.ibm.icu.util.TimeZoneRule
    public Date getFinalStart(int i, int i2) {
        return null;
    }

    @Override // com.ibm.icu.util.TimeZoneRule
    public Date getFirstStart(int i, int i2) {
        return null;
    }

    @Override // com.ibm.icu.util.TimeZoneRule
    public Date getNextStart(long j, int i, int i2, boolean z) {
        return null;
    }

    @Override // com.ibm.icu.util.TimeZoneRule
    public Date getPreviousStart(long j, int i, int i2, boolean z) {
        return null;
    }

    @Override // com.ibm.icu.util.TimeZoneRule
    public boolean isTransitionRule() {
        return false;
    }

    public InitialTimeZoneRule(String str, int i, int i2) {
        super(str, i, i2);
    }

    @Override // com.ibm.icu.util.TimeZoneRule
    public boolean isEquivalentTo(TimeZoneRule timeZoneRule) {
        if (timeZoneRule instanceof InitialTimeZoneRule) {
            return super.isEquivalentTo(timeZoneRule);
        }
        return false;
    }
}
