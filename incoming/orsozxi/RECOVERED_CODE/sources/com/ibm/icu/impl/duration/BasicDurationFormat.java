package com.ibm.icu.impl.duration;

import com.ibm.icu.text.DurationFormat;
import com.ibm.icu.util.ULocale;
import java.text.FieldPosition;
import java.util.Date;
import javax.xml.datatype.Duration;

/* loaded from: classes2.dex */
public class BasicDurationFormat extends DurationFormat {
    private static final long serialVersionUID = -3146984141909457700L;
    transient DurationFormatter formatter;
    transient PeriodFormatter pformatter;
    transient PeriodFormatterService pfs;

    public static BasicDurationFormat getInstance(ULocale uLocale) {
        return new BasicDurationFormat(uLocale);
    }

    @Override // com.ibm.icu.text.DurationFormat, java.text.Format
    public StringBuffer format(Object obj, StringBuffer stringBuffer, FieldPosition fieldPosition) {
        if (obj instanceof Long) {
            stringBuffer.append(formatDurationFromNow(((Long) obj).longValue()));
            return stringBuffer;
        }
        if (obj instanceof Date) {
            stringBuffer.append(formatDurationFromNowTo((Date) obj));
            return stringBuffer;
        }
        if (obj instanceof Duration) {
            stringBuffer.append(formatDuration(obj));
            return stringBuffer;
        }
        throw new IllegalArgumentException("Cannot format given Object as a Duration");
    }

    public BasicDurationFormat() {
        this.pfs = null;
        BasicPeriodFormatterService basicPeriodFormatterService = BasicPeriodFormatterService.getInstance();
        this.pfs = basicPeriodFormatterService;
        this.formatter = basicPeriodFormatterService.newDurationFormatterFactory().getFormatter();
        this.pformatter = this.pfs.newPeriodFormatterFactory().setDisplayPastFuture(false).getFormatter();
    }

    public BasicDurationFormat(ULocale uLocale) {
        super(uLocale);
        this.pfs = null;
        BasicPeriodFormatterService basicPeriodFormatterService = BasicPeriodFormatterService.getInstance();
        this.pfs = basicPeriodFormatterService;
        this.formatter = basicPeriodFormatterService.newDurationFormatterFactory().setLocale(uLocale.getName()).getFormatter();
        this.pformatter = this.pfs.newPeriodFormatterFactory().setDisplayPastFuture(false).setLocale(uLocale.getName()).getFormatter();
    }

    @Override // com.ibm.icu.text.DurationFormat
    public String formatDurationFrom(long j, long j2) {
        return this.formatter.formatDurationFrom(j, j2);
    }

    @Override // com.ibm.icu.text.DurationFormat
    public String formatDurationFromNow(long j) {
        return this.formatter.formatDurationFromNow(j);
    }

    @Override // com.ibm.icu.text.DurationFormat
    public String formatDurationFromNowTo(Date date) {
        return this.formatter.formatDurationFromNowTo(date);
    }

    /* JADX WARN: Removed duplicated region for block: B:19:0x0093  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public java.lang.String formatDuration(java.lang.Object r21) {
        /*
            Method dump skipped, instructions count: 202
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ibm.icu.impl.duration.BasicDurationFormat.formatDuration(java.lang.Object):java.lang.String");
    }
}
