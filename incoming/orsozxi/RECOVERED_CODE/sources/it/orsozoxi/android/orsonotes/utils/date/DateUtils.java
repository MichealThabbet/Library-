package it.orsozoxi.android.orsonotes.utils.date;

import android.content.Context;
import it.orsozoxi.android.orsonotes.helpers.LogDelegate;
import it.orsozoxi.android.orsonotes.utils.ConstantsBase;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import org.apache.commons.lang3.StringUtils;
import org.ocpsoft.prettytime.PrettyTime;

/* loaded from: classes3.dex */
public class DateUtils {
    private DateUtils() {
        throw new IllegalStateException("Utility class");
    }

    public static String getString(long j, String str) {
        return getString(new Date(j), str);
    }

    public static String getString(Date date, String str) {
        return new SimpleDateFormat(str).format(date);
    }

    public static Calendar getDateFromString(String str, String str2) {
        Calendar calendar = Calendar.getInstance();
        try {
            calendar.setTime(new SimpleDateFormat(str2).parse(str));
        } catch (NullPointerException unused) {
            LogDelegate.e("Date or time not set");
        } catch (ParseException e) {
            LogDelegate.e("Malformed datetime string" + e.getMessage());
        }
        return calendar;
    }

    public static Calendar getLongFromDateTime(String str, String str2, String str3, String str4) {
        Calendar calendar = Calendar.getInstance();
        Calendar calendar2 = Calendar.getInstance();
        Calendar calendar3 = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(str2);
        SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat(str4);
        try {
            calendar2.setTime(simpleDateFormat.parse(str));
            calendar3.setTime(simpleDateFormat2.parse(str3));
        } catch (ParseException e) {
            LogDelegate.e("Date or time parsing error: " + e.getMessage());
        }
        calendar.set(1, calendar2.get(1));
        calendar.set(2, calendar2.get(2));
        calendar.set(5, calendar2.get(5));
        calendar.set(11, calendar3.get(11));
        calendar.set(12, calendar3.get(12));
        calendar.set(13, 0);
        return calendar;
    }

    public static Calendar getCalendar(Long l) {
        Calendar calendar = Calendar.getInstance();
        if (l != null && l.longValue() != 0) {
            calendar.setTimeInMillis(l.longValue());
        }
        return calendar;
    }

    public static String getLocalizedDateTime(Context context, String str, String str2) {
        Date date;
        try {
            date = new SimpleDateFormat(str2).parse(str);
        } catch (ParseException unused) {
            try {
                date = new SimpleDateFormat(ConstantsBase.DATE_FORMAT_SORTABLE_OLD).parse(str);
            } catch (ParseException unused2) {
                LogDelegate.e("String is not formattable into date");
                date = null;
            }
        }
        if (date == null) {
            return null;
        }
        return android.text.format.DateUtils.formatDateTime(context, date.getTime(), 65536) + " " + android.text.format.DateUtils.formatDateTime(context, date.getTime(), 1);
    }

    public static boolean is24HourMode(Context context) {
        String dateTime = android.text.format.DateUtils.formatDateTime(context, Calendar.getInstance().getTimeInMillis(), 1);
        return (dateTime.toLowerCase().contains("am") || dateTime.toLowerCase().contains("pm")) ? false : true;
    }

    public static boolean isSameDay(long j, long j2) {
        Calendar calendar = Calendar.getInstance();
        Calendar calendar2 = Calendar.getInstance();
        calendar.setTimeInMillis(j);
        calendar2.setTimeInMillis(j2);
        return calendar.get(1) == calendar2.get(1) && calendar.get(6) == calendar2.get(6);
    }

    public static long getNextMinute() {
        return Calendar.getInstance().getTimeInMillis() + 60000;
    }

    public static long getPresetReminder(Long l) {
        return (l == null || l.longValue() <= Calendar.getInstance().getTimeInMillis()) ? getNextMinute() : l.longValue();
    }

    public static Long getPresetReminder(String str) {
        return Long.valueOf(getPresetReminder(Long.valueOf(str == null ? 0L : Long.parseLong(str))));
    }

    public static boolean isFuture(String str) {
        return !StringUtils.isEmpty(str) && isFuture(Long.valueOf(Long.parseLong(str)));
    }

    public static boolean isFuture(Long l) {
        return l != null && l.longValue() > Calendar.getInstance().getTimeInMillis();
    }

    public static String prettyTime(String str) {
        return str == null ? "" : prettyTime(Long.valueOf(Long.parseLong(str)), new Locale("ar"));
    }

    public static String prettyTime(Long l) {
        return prettyTime(l, new Locale("ar"));
    }

    static String prettyTime(Long l, Locale locale) {
        if (l == null) {
            return "";
        }
        Date date = new Date(l.longValue());
        PrettyTime prettyTime = new PrettyTime();
        if (locale != null) {
            prettyTime.setLocale(locale);
        }
        return prettyTime.format(date);
    }
}
