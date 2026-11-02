package com.ibm.icu.impl.duration;

import com.ibm.icu.impl.duration.impl.PeriodFormatterDataService;
import com.ibm.icu.impl.duration.impl.ResourceBasedPeriodFormatterDataService;
import java.util.Collection;

/* loaded from: classes2.dex */
public class BasicPeriodFormatterService implements PeriodFormatterService {
    private static BasicPeriodFormatterService instance;
    private PeriodFormatterDataService ds;

    public static BasicPeriodFormatterService getInstance() {
        if (instance == null) {
            instance = new BasicPeriodFormatterService(ResourceBasedPeriodFormatterDataService.getInstance());
        }
        return instance;
    }

    public BasicPeriodFormatterService(PeriodFormatterDataService periodFormatterDataService) {
        this.ds = periodFormatterDataService;
    }

    @Override // com.ibm.icu.impl.duration.PeriodFormatterService
    public DurationFormatterFactory newDurationFormatterFactory() {
        return new BasicDurationFormatterFactory(this);
    }

    @Override // com.ibm.icu.impl.duration.PeriodFormatterService
    public PeriodFormatterFactory newPeriodFormatterFactory() {
        return new BasicPeriodFormatterFactory(this.ds);
    }

    @Override // com.ibm.icu.impl.duration.PeriodFormatterService
    public PeriodBuilderFactory newPeriodBuilderFactory() {
        return new BasicPeriodBuilderFactory(this.ds);
    }

    @Override // com.ibm.icu.impl.duration.PeriodFormatterService
    public Collection<String> getAvailableLocaleNames() {
        return this.ds.getAvailableLocales();
    }
}
