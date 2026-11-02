package net.fortuna.ical4j.model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import net.fortuna.ical4j.util.CompatibilityHints;
import net.fortuna.ical4j.util.TimeZones;

/* loaded from: classes3.dex */
public class Date extends Iso8601 {
    private static final String DEFAULT_PATTERN = "yyyyMMdd";
    private static final String VCARD_PATTERN = "yyyy'-'MM'-'dd";
    private static final long serialVersionUID = 7136072363141363141L;

    public Date() {
        super(DEFAULT_PATTERN, 1, TimeZones.getDateTimeZone());
    }

    protected Date(int i, java.util.TimeZone timeZone) {
        super(DEFAULT_PATTERN, i, timeZone);
    }

    public Date(long j) {
        super(j, DEFAULT_PATTERN, 1, TimeZones.getDateTimeZone());
    }

    protected Date(long j, int i, java.util.TimeZone timeZone) {
        super(j, DEFAULT_PATTERN, i, timeZone);
    }

    public Date(java.util.Calendar calendar) {
        this(calendar.getTimeInMillis(), 1, TimeZones.getDateTimeZone());
    }

    public Date(java.util.Date date) {
        this(date.getTime(), 1, TimeZones.getDateTimeZone());
    }

    public Date(String str) throws ParseException {
        this();
        try {
            setTime(getFormat().parse(str).getTime());
        } catch (ParseException e) {
            if (CompatibilityHints.isHintEnabled(CompatibilityHints.KEY_VCARD_COMPATIBILITY)) {
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat(VCARD_PATTERN);
                simpleDateFormat.setTimeZone(TimeZones.getDateTimeZone());
                setTime(simpleDateFormat.parse(str).getTime());
                return;
            }
            throw e;
        }
    }

    public Date(String str, String str2) throws ParseException {
        super(DEFAULT_PATTERN, 1, TimeZones.getDateTimeZone());
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(str2);
        simpleDateFormat.setTimeZone(TimeZones.getDateTimeZone());
        setTime(simpleDateFormat.parse(str).getTime());
    }
}
