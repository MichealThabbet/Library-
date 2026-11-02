package net.fortuna.ical4j.util;

import com.facebook.internal.ServerProtocol;
import java.util.TimeZone;

/* loaded from: classes3.dex */
public final class TimeZones {
    public static final String GMT_ID = "Etc/GMT";
    public static final String IBM_UTC_ID = "GMT";
    public static final String UTC_ID = "Etc/UTC";
    private static final TimeZone UTC_TIMEZONE = TimeZone.getTimeZone(UTC_ID);

    private TimeZones() {
    }

    public static boolean isUtc(TimeZone timeZone) {
        return UTC_ID.equals(timeZone.getID()) || "GMT".equals(timeZone.getID());
    }

    public static TimeZone getDateTimeZone() {
        if (ServerProtocol.DIALOG_RETURN_SCOPES_TRUE.equals(Configurator.getProperty("net.fortuna.ical4j.timezone.date.floating").orElse("false"))) {
            return TimeZone.getDefault();
        }
        return getUtcTimeZone();
    }

    public static TimeZone getUtcTimeZone() {
        return UTC_TIMEZONE;
    }
}
