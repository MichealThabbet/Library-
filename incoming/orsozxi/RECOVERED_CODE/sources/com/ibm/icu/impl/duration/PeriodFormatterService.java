package com.ibm.icu.impl.duration;

import java.util.Collection;

/* loaded from: classes2.dex */
public interface PeriodFormatterService {
    Collection<String> getAvailableLocaleNames();

    DurationFormatterFactory newDurationFormatterFactory();

    PeriodBuilderFactory newPeriodBuilderFactory();

    PeriodFormatterFactory newPeriodFormatterFactory();
}
