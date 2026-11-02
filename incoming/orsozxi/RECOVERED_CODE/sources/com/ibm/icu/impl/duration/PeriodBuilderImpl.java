package com.ibm.icu.impl.duration;

import com.ibm.icu.impl.duration.BasicPeriodBuilderFactory;
import java.util.TimeZone;

/* compiled from: BasicPeriodBuilderFactory.java */
/* loaded from: classes2.dex */
abstract class PeriodBuilderImpl implements PeriodBuilder {
    protected BasicPeriodBuilderFactory.Settings settings;

    protected abstract Period handleCreate(long j, long j2, boolean z);

    protected abstract PeriodBuilder withSettings(BasicPeriodBuilderFactory.Settings settings);

    @Override // com.ibm.icu.impl.duration.PeriodBuilder
    public PeriodBuilder withTimeZone(TimeZone timeZone) {
        return this;
    }

    @Override // com.ibm.icu.impl.duration.PeriodBuilder
    public Period create(long j) {
        return createWithReferenceDate(j, System.currentTimeMillis());
    }

    public long approximateDurationOf(TimeUnit timeUnit) {
        return BasicPeriodBuilderFactory.approximateDurationOf(timeUnit);
    }

    @Override // com.ibm.icu.impl.duration.PeriodBuilder
    public Period createWithReferenceDate(long j, long j2) {
        boolean z = j < 0;
        if (z) {
            j = -j;
        }
        long j3 = j;
        Period periodCreateLimited = this.settings.createLimited(j3, z);
        if (periodCreateLimited != null) {
            return periodCreateLimited;
        }
        Period periodHandleCreate = handleCreate(j3, j2, z);
        return periodHandleCreate == null ? Period.lessThan(1.0f, this.settings.effectiveMinUnit()).inPast(z) : periodHandleCreate;
    }

    @Override // com.ibm.icu.impl.duration.PeriodBuilder
    public PeriodBuilder withLocale(String str) {
        BasicPeriodBuilderFactory.Settings locale = this.settings.setLocale(str);
        return locale != this.settings ? withSettings(locale) : this;
    }

    protected PeriodBuilderImpl(BasicPeriodBuilderFactory.Settings settings) {
        this.settings = settings;
    }
}
