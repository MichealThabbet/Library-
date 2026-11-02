package com.ibm.icu.impl.duration;

import com.ibm.icu.impl.duration.BasicPeriodBuilderFactory;

/* compiled from: BasicPeriodBuilderFactory.java */
/* loaded from: classes2.dex */
class OneOrTwoUnitBuilder extends PeriodBuilderImpl {
    OneOrTwoUnitBuilder(BasicPeriodBuilderFactory.Settings settings) {
        super(settings);
    }

    public static OneOrTwoUnitBuilder get(BasicPeriodBuilderFactory.Settings settings) {
        if (settings == null) {
            return null;
        }
        return new OneOrTwoUnitBuilder(settings);
    }

    @Override // com.ibm.icu.impl.duration.PeriodBuilderImpl
    protected PeriodBuilder withSettings(BasicPeriodBuilderFactory.Settings settings) {
        return get(settings);
    }

    @Override // com.ibm.icu.impl.duration.PeriodBuilderImpl
    protected Period handleCreate(long j, long j2, boolean z) {
        short sEffectiveSet = this.settings.effectiveSet();
        Period periodInPast = null;
        for (int i = 0; i < TimeUnit.units.length; i++) {
            if (((1 << i) & sEffectiveSet) != 0) {
                TimeUnit timeUnit = TimeUnit.units[i];
                long jApproximateDurationOf = approximateDurationOf(timeUnit);
                if (j >= jApproximateDurationOf || periodInPast != null) {
                    double d = j / jApproximateDurationOf;
                    if (periodInPast != null) {
                        return d >= 1.0d ? periodInPast.and((float) d, timeUnit) : periodInPast;
                    }
                    if (d >= 2.0d) {
                        return Period.at((float) d, timeUnit);
                    }
                    periodInPast = Period.at(1.0f, timeUnit).inPast(z);
                    j -= jApproximateDurationOf;
                }
            }
        }
        return periodInPast;
    }
}
