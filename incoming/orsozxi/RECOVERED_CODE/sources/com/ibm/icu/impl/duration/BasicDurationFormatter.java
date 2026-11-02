package com.ibm.icu.impl.duration;

import java.util.Date;
import java.util.TimeZone;

/* loaded from: classes2.dex */
class BasicDurationFormatter implements DurationFormatter {
    private PeriodBuilder builder;
    private DateFormatter fallback;
    private long fallbackLimit;
    private PeriodFormatter formatter;
    private String localeName;
    private TimeZone timeZone;

    public BasicDurationFormatter(PeriodFormatter periodFormatter, PeriodBuilder periodBuilder, DateFormatter dateFormatter, long j) {
        this.formatter = periodFormatter;
        this.builder = periodBuilder;
        this.fallback = dateFormatter;
        this.fallbackLimit = j < 0 ? 0L : j;
    }

    protected BasicDurationFormatter(PeriodFormatter periodFormatter, PeriodBuilder periodBuilder, DateFormatter dateFormatter, long j, String str, TimeZone timeZone) {
        this.formatter = periodFormatter;
        this.builder = periodBuilder;
        this.fallback = dateFormatter;
        this.fallbackLimit = j;
        this.localeName = str;
        this.timeZone = timeZone;
    }

    @Override // com.ibm.icu.impl.duration.DurationFormatter
    public String formatDurationFromNowTo(Date date) {
        long jCurrentTimeMillis = System.currentTimeMillis();
        return formatDurationFrom(date.getTime() - jCurrentTimeMillis, jCurrentTimeMillis);
    }

    @Override // com.ibm.icu.impl.duration.DurationFormatter
    public String formatDurationFromNow(long j) {
        return formatDurationFrom(j, System.currentTimeMillis());
    }

    @Override // com.ibm.icu.impl.duration.DurationFormatter
    public String formatDurationFrom(long j, long j2) {
        String strDoFallback = doFallback(j, j2);
        return strDoFallback == null ? doFormat(doBuild(j, j2)) : strDoFallback;
    }

    @Override // com.ibm.icu.impl.duration.DurationFormatter
    public DurationFormatter withLocale(String str) {
        if (str.equals(this.localeName)) {
            return this;
        }
        PeriodFormatter periodFormatterWithLocale = this.formatter.withLocale(str);
        PeriodBuilder periodBuilderWithLocale = this.builder.withLocale(str);
        DateFormatter dateFormatter = this.fallback;
        return new BasicDurationFormatter(periodFormatterWithLocale, periodBuilderWithLocale, dateFormatter == null ? null : dateFormatter.withLocale(str), this.fallbackLimit, str, this.timeZone);
    }

    @Override // com.ibm.icu.impl.duration.DurationFormatter
    public DurationFormatter withTimeZone(TimeZone timeZone) {
        if (timeZone.equals(this.timeZone)) {
            return this;
        }
        PeriodBuilder periodBuilderWithTimeZone = this.builder.withTimeZone(timeZone);
        DateFormatter dateFormatter = this.fallback;
        return new BasicDurationFormatter(this.formatter, periodBuilderWithTimeZone, dateFormatter == null ? null : dateFormatter.withTimeZone(timeZone), this.fallbackLimit, this.localeName, timeZone);
    }

    protected String doFallback(long j, long j2) {
        if (this.fallback == null || this.fallbackLimit <= 0 || Math.abs(j) < this.fallbackLimit) {
            return null;
        }
        return this.fallback.format(j2 + j);
    }

    protected Period doBuild(long j, long j2) {
        return this.builder.createWithReferenceDate(j, j2);
    }

    protected String doFormat(Period period) {
        if (!period.isSet()) {
            throw new IllegalArgumentException("period is not set");
        }
        return this.formatter.format(period);
    }
}
