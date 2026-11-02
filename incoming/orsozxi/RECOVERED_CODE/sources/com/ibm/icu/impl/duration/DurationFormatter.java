package com.ibm.icu.impl.duration;

import java.util.Date;
import java.util.TimeZone;

/* loaded from: classes2.dex */
public interface DurationFormatter {
    String formatDurationFrom(long j, long j2);

    String formatDurationFromNow(long j);

    String formatDurationFromNowTo(Date date);

    DurationFormatter withLocale(String str);

    DurationFormatter withTimeZone(TimeZone timeZone);
}
