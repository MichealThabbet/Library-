package net.fortuna.ical4j.model;

import java.text.DateFormat;
import net.fortuna.ical4j.util.CompatibilityHints;
import net.fortuna.ical4j.util.Dates;
import net.fortuna.ical4j.util.TimeZones;

/* loaded from: classes3.dex */
public abstract class Iso8601 extends java.util.Date {
    private static final long serialVersionUID = -4290728005713946811L;
    private DateFormat format;
    private DateFormat gmtFormat;
    private int precision;

    public Iso8601(long j, String str, int i, java.util.TimeZone timeZone) {
        super(Dates.round(j, i, timeZone));
        DateFormat calendarDateFormatFactory = CalendarDateFormatFactory.getInstance(str);
        this.format = calendarDateFormatFactory;
        calendarDateFormatFactory.setTimeZone(timeZone);
        this.format.setLenient(CompatibilityHints.isHintEnabled(CompatibilityHints.KEY_RELAXED_PARSING));
        this.precision = i;
    }

    public Iso8601(String str, int i, java.util.TimeZone timeZone) {
        this(Dates.getCurrentTimeRounded(), str, i, timeZone);
    }

    public Iso8601(java.util.Date date, String str, int i, java.util.TimeZone timeZone) {
        this(date.getTime(), str, i, timeZone);
    }

    @Override // java.util.Date
    public String toString() {
        java.util.TimeZone timeZone = this.format.getTimeZone();
        if (!(timeZone instanceof TimeZone)) {
            if (this.gmtFormat == null) {
                DateFormat dateFormat = (DateFormat) this.format.clone();
                this.gmtFormat = dateFormat;
                dateFormat.setTimeZone(TimeZone.getTimeZone(TimeZones.GMT_ID));
            }
            if (timeZone.inDaylightTime(this) && timeZone.inDaylightTime(new java.util.Date(getTime() - 1))) {
                return this.gmtFormat.format(new java.util.Date(getTime() + timeZone.getRawOffset() + timeZone.getDSTSavings()));
            }
            return this.gmtFormat.format(new java.util.Date(getTime() + timeZone.getRawOffset()));
        }
        return this.format.format((java.util.Date) this);
    }

    protected final DateFormat getFormat() {
        return this.format;
    }

    @Override // java.util.Date
    public void setTime(long j) {
        DateFormat dateFormat = this.format;
        if (dateFormat != null) {
            super.setTime(Dates.round(j, this.precision, dateFormat.getTimeZone()));
        } else {
            super.setTime(j);
        }
    }
}
