package com.ibm.icu.impl.duration;

import com.ibm.icu.impl.duration.BasicPeriodBuilderFactory;

/* compiled from: BasicPeriodBuilderFactory.java */
/* loaded from: classes2.dex */
class FixedUnitBuilder extends PeriodBuilderImpl {
    private TimeUnit unit;

    public static FixedUnitBuilder get(TimeUnit timeUnit, BasicPeriodBuilderFactory.Settings settings) {
        if (settings == null || (settings.effectiveSet() & (1 << timeUnit.ordinal)) == 0) {
            return null;
        }
        return new FixedUnitBuilder(timeUnit, settings);
    }

    FixedUnitBuilder(TimeUnit timeUnit, BasicPeriodBuilderFactory.Settings settings) {
        super(settings);
        this.unit = timeUnit;
    }

    @Override // com.ibm.icu.impl.duration.PeriodBuilderImpl
    protected PeriodBuilder withSettings(BasicPeriodBuilderFactory.Settings settings) {
        return get(this.unit, settings);
    }

    @Override // com.ibm.icu.impl.duration.PeriodBuilderImpl
    protected Period handleCreate(long j, long j2, boolean z) {
        if (this.unit == null) {
            return null;
        }
        return Period.at((float) (j / approximateDurationOf(r3)), this.unit).inPast(z);
    }
}
