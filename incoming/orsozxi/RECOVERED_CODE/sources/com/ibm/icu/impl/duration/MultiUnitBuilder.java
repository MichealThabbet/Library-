package com.ibm.icu.impl.duration;

import com.ibm.icu.impl.duration.BasicPeriodBuilderFactory;

/* compiled from: BasicPeriodBuilderFactory.java */
/* loaded from: classes2.dex */
class MultiUnitBuilder extends PeriodBuilderImpl {
    private int nPeriods;

    MultiUnitBuilder(int i, BasicPeriodBuilderFactory.Settings settings) {
        super(settings);
        this.nPeriods = i;
    }

    public static MultiUnitBuilder get(int i, BasicPeriodBuilderFactory.Settings settings) {
        if (i <= 0 || settings == null) {
            return null;
        }
        return new MultiUnitBuilder(i, settings);
    }

    @Override // com.ibm.icu.impl.duration.PeriodBuilderImpl
    protected PeriodBuilder withSettings(BasicPeriodBuilderFactory.Settings settings) {
        return get(this.nPeriods, settings);
    }

    @Override // com.ibm.icu.impl.duration.PeriodBuilderImpl
    protected Period handleCreate(long j, long j2, boolean z) {
        short sEffectiveSet = this.settings.effectiveSet();
        Period periodAnd = null;
        int i = 0;
        for (int i2 = 0; i2 < TimeUnit.units.length; i2++) {
            if (((1 << i2) & sEffectiveSet) != 0) {
                TimeUnit timeUnit = TimeUnit.units[i2];
                if (i == this.nPeriods) {
                    break;
                }
                long jApproximateDurationOf = approximateDurationOf(timeUnit);
                if (j >= jApproximateDurationOf || i > 0) {
                    i++;
                    double d = jApproximateDurationOf;
                    double dFloor = j / d;
                    if (i < this.nPeriods) {
                        dFloor = Math.floor(dFloor);
                        j -= (long) (d * dFloor);
                    }
                    if (periodAnd == null) {
                        periodAnd = Period.at((float) dFloor, timeUnit).inPast(z);
                    } else {
                        periodAnd = periodAnd.and((float) dFloor, timeUnit);
                    }
                }
            }
        }
        return periodAnd;
    }
}
