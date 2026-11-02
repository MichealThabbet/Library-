package com.ibm.icu.impl.duration;

import java.util.TimeZone;

/* loaded from: classes2.dex */
public interface PeriodBuilder {
    Period create(long j);

    Period createWithReferenceDate(long j, long j2);

    PeriodBuilder withLocale(String str);

    PeriodBuilder withTimeZone(TimeZone timeZone);
}
