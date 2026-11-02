package net.fortuna.ical4j.model;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Map;
import java.util.WeakHashMap;
import net.fortuna.ical4j.util.CompatibilityHints;
import net.fortuna.ical4j.util.TimeZones;
import org.apache.commons.lang3.builder.EqualsBuilder;

/* loaded from: classes3.dex */
public class DateTime extends Date {
    private static final DateFormatCache DEFAULT_FORMAT;
    private static final String DEFAULT_PATTERN = "yyyyMMdd'T'HHmmss";
    private static final DateFormatCache LENIENT_DEFAULT_FORMAT;
    private static final DateFormatCache RELAXED_FORMAT;
    private static final String RELAXED_PATTERN = "yyyyMMdd";
    private static final DateFormatCache UTC_FORMAT;
    private static final String UTC_PATTERN = "yyyyMMdd'T'HHmmss'Z'";
    private static final DateFormatCache VCARD_FORMAT;
    private static final String VCARD_PATTERN = "yyyy'-'MM'-'dd'T'HH':'mm':'ss'Z'";
    private static final long serialVersionUID = -6407231357919440387L;
    private Time time;
    private TimeZone timezone;

    static {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(UTC_PATTERN);
        simpleDateFormat.setTimeZone(TimeZones.getUtcTimeZone());
        simpleDateFormat.setLenient(false);
        UTC_FORMAT = new DateFormatCache(simpleDateFormat);
        SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat(DEFAULT_PATTERN);
        simpleDateFormat2.setLenient(false);
        DEFAULT_FORMAT = new DateFormatCache(simpleDateFormat2);
        LENIENT_DEFAULT_FORMAT = new DateFormatCache(new SimpleDateFormat(DEFAULT_PATTERN));
        SimpleDateFormat simpleDateFormat3 = new SimpleDateFormat(RELAXED_PATTERN);
        simpleDateFormat3.setLenient(true);
        RELAXED_FORMAT = new DateFormatCache(simpleDateFormat3);
        VCARD_FORMAT = new DateFormatCache(new SimpleDateFormat(VCARD_PATTERN));
    }

    public DateTime() {
        super(0, java.util.TimeZone.getDefault());
        this.time = new Time(getTime(), getFormat().getTimeZone());
    }

    public DateTime(boolean z) {
        this();
        setUtc(z);
    }

    public DateTime(long j) {
        super(j, 0, java.util.TimeZone.getDefault());
        this.time = new Time(j, getFormat().getTimeZone());
    }

    public DateTime(java.util.Date date) {
        super(date.getTime(), 0, java.util.TimeZone.getDefault());
        this.time = new Time(date.getTime(), getFormat().getTimeZone());
        if (date instanceof DateTime) {
            DateTime dateTime = (DateTime) date;
            if (dateTime.isUtc()) {
                setUtc(true);
            } else {
                setTimeZone(dateTime.getTimeZone());
            }
        }
    }

    public DateTime(java.util.Date date, TimeZone timeZone) {
        this(date);
        setTimeZone(timeZone);
    }

    public DateTime(String str) throws ParseException {
        this(str, (TimeZone) null);
    }

    public DateTime(String str, TimeZone timeZone) throws ParseException {
        super(0L, 0, timeZone != null ? timeZone : java.util.TimeZone.getDefault());
        this.time = new Time(getTime(), getFormat().getTimeZone());
        try {
            if (str.endsWith("Z")) {
                setTime(str, UTC_FORMAT.get(), null);
                setUtc(true);
            } else {
                if (timeZone != null) {
                    setTime(str, DEFAULT_FORMAT.get(), timeZone);
                } else {
                    setTime(str, LENIENT_DEFAULT_FORMAT.get(), getFormat().getTimeZone());
                }
                setTimeZone(timeZone);
            }
        } catch (ParseException e) {
            if (CompatibilityHints.isHintEnabled(CompatibilityHints.KEY_VCARD_COMPATIBILITY)) {
                try {
                    setTime(str, VCARD_FORMAT.get(), timeZone);
                    setTimeZone(timeZone);
                    return;
                } catch (ParseException unused) {
                    if (CompatibilityHints.isHintEnabled(CompatibilityHints.KEY_RELAXED_PARSING)) {
                        setTime(str, RELAXED_FORMAT.get(), timeZone);
                        setTimeZone(timeZone);
                        return;
                    }
                    return;
                }
            }
            if (CompatibilityHints.isHintEnabled(CompatibilityHints.KEY_RELAXED_PARSING)) {
                setTime(str, RELAXED_FORMAT.get(), timeZone);
                setTimeZone(timeZone);
                return;
            }
            throw e;
        }
    }

    public DateTime(String str, String str2, TimeZone timeZone) throws ParseException {
        super(0L, 0, timeZone != null ? timeZone : java.util.TimeZone.getDefault());
        this.time = new Time(getTime(), getFormat().getTimeZone());
        setTime(str, CalendarDateFormatFactory.getInstance(str2), timeZone);
    }

    public DateTime(String str, String str2, boolean z) throws ParseException {
        this(0L);
        DateFormat calendarDateFormatFactory = CalendarDateFormatFactory.getInstance(str2);
        if (z) {
            setTime(str, calendarDateFormatFactory, UTC_FORMAT.get().getTimeZone());
        } else {
            setTime(str, calendarDateFormatFactory, null);
        }
        setUtc(z);
    }

    private void setTime(String str, DateFormat dateFormat, java.util.TimeZone timeZone) throws ParseException {
        if (timeZone != null) {
            dateFormat.setTimeZone(timeZone);
        }
        setTime(dateFormat.parse(str).getTime());
    }

    @Override // net.fortuna.ical4j.model.Iso8601, java.util.Date
    public final void setTime(long j) {
        super.setTime(j);
        Time time = this.time;
        if (time != null) {
            time.setTime(j);
        }
    }

    public final boolean isUtc() {
        return this.time.isUtc();
    }

    public final void setUtc(boolean z) {
        this.timezone = null;
        if (z) {
            getFormat().setTimeZone(TimeZones.getUtcTimeZone());
        } else {
            resetTimeZone();
        }
        this.time = new Time(this.time, getFormat().getTimeZone(), z);
    }

    public final void setTimeZone(TimeZone timeZone) {
        this.timezone = timeZone;
        if (timeZone != null) {
            getFormat().setTimeZone(timeZone);
        } else {
            resetTimeZone();
        }
        this.time = new Time((java.util.Date) this.time, getFormat().getTimeZone(), false);
    }

    private void resetTimeZone() {
        getFormat().setTimeZone(TimeZone.getDefault());
    }

    public final TimeZone getTimeZone() {
        return this.timezone;
    }

    @Override // net.fortuna.ical4j.model.Iso8601, java.util.Date
    public final String toString() {
        return super.toString() + 'T' + this.time.toString();
    }

    @Override // java.util.Date
    public boolean equals(Object obj) {
        if (obj instanceof DateTime) {
            return new EqualsBuilder().append(this.time, ((DateTime) obj).time).isEquals();
        }
        return super.equals(obj);
    }

    @Override // java.util.Date
    public int hashCode() {
        return super.hashCode();
    }

    private static class DateFormatCache {
        private final DateFormat templateFormat;
        private final Map<Thread, DateFormat> threadMap;

        private DateFormatCache(DateFormat dateFormat) {
            this.threadMap = Collections.synchronizedMap(new WeakHashMap());
            this.templateFormat = dateFormat;
        }

        public DateFormat get() {
            DateFormat dateFormat = this.threadMap.get(Thread.currentThread());
            if (dateFormat != null) {
                return dateFormat;
            }
            DateFormat dateFormat2 = (DateFormat) this.templateFormat.clone();
            this.threadMap.put(Thread.currentThread(), dateFormat2);
            return dateFormat2;
        }
    }
}
