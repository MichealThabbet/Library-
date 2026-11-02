package net.fortuna.ical4j.model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import net.fortuna.ical4j.util.TimeZones;

/* loaded from: classes3.dex */
public class Time extends Iso8601 {
    private static final String DEFAULT_PATTERN = "HHmmss";
    private static final String UTC_PATTERN = "HHmmss'Z'";
    private static final long serialVersionUID = -8401010870773304348L;
    private boolean utc;

    public Time(java.util.TimeZone timeZone) {
        this(timeZone, TimeZones.isUtc(timeZone));
    }

    public Time(java.util.TimeZone timeZone, boolean z) {
        super(z ? UTC_PATTERN : DEFAULT_PATTERN, 0, timeZone);
        this.utc = false;
        getFormat().setTimeZone(timeZone);
        this.utc = z;
    }

    public Time(long j, java.util.TimeZone timeZone) {
        this(j, timeZone, TimeZones.isUtc(timeZone));
    }

    public Time(long j, java.util.TimeZone timeZone, boolean z) {
        super(j, z ? UTC_PATTERN : DEFAULT_PATTERN, 0, timeZone);
        this.utc = false;
        getFormat().setTimeZone(timeZone);
        this.utc = z;
    }

    public Time(java.util.Date date, java.util.TimeZone timeZone) {
        this(date, timeZone, TimeZones.isUtc(timeZone));
    }

    public Time(java.util.Date date, java.util.TimeZone timeZone, boolean z) {
        super(date.getTime(), z ? UTC_PATTERN : DEFAULT_PATTERN, 0, timeZone);
        this.utc = false;
        getFormat().setTimeZone(timeZone);
        this.utc = z;
    }

    public Time(String str, java.util.TimeZone timeZone) throws ParseException {
        this(str, timeZone, TimeZones.isUtc(timeZone));
    }

    public Time(String str, java.util.TimeZone timeZone, boolean z) throws ParseException {
        this(parseDate(str, timeZone), timeZone, z);
    }

    private static java.util.Date parseDate(String str, java.util.TimeZone timeZone) throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DEFAULT_PATTERN);
        simpleDateFormat.setTimeZone(timeZone);
        try {
            return simpleDateFormat.parse(str);
        } catch (ParseException unused) {
            SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat(UTC_PATTERN);
            simpleDateFormat2.setTimeZone(timeZone);
            return simpleDateFormat2.parse(str);
        }
    }

    public final boolean isUtc() {
        return this.utc;
    }
}
