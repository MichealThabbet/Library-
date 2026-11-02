package net.fortuna.ical4j.util;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;
import net.fortuna.ical4j.model.DateList;
import net.fortuna.ical4j.model.DateTime;
import net.fortuna.ical4j.model.parameter.Value;

/* loaded from: classes3.dex */
public final class Dates {
    public static final int DAYS_PER_WEEK = 7;
    private static final String INVALID_MONTH_DAY_MESSAGE = "Invalid month day [{0}]";
    private static final String INVALID_WEEK_MESSAGE = "Invalid week number [{0}]";
    private static final String INVALID_YEAR_DAY_MESSAGE = "Invalid year day [{0}]";
    public static final int MAX_DAYS_PER_MONTH = 31;
    public static final int MAX_DAYS_PER_YEAR = 366;
    public static final int MAX_WEEKS_PER_YEAR = 53;
    public static final long MILLIS_PER_DAY = 86400000;
    public static final long MILLIS_PER_HOUR = 3600000;
    public static final long MILLIS_PER_MINUTE = 60000;
    public static final long MILLIS_PER_SECOND = 1000;
    public static final long MILLIS_PER_WEEK = 604800000;
    public static final int PRECISION_DAY = 1;
    public static final int PRECISION_SECOND = 0;

    private Dates() {
    }

    public static int getAbsWeekNo(Date date, int i) {
        if (i == 0 || i < -53 || i > 53) {
            throw new IllegalArgumentException(MessageFormat.format(INVALID_WEEK_MESSAGE, Integer.valueOf(i)));
        }
        if (i > 0) {
            return i;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int i2 = calendar.get(1);
        ArrayList arrayList = new ArrayList();
        calendar.set(3, 1);
        while (calendar.get(1) == i2) {
            arrayList.add(Integer.valueOf(calendar.get(3)));
            calendar.add(3, 1);
        }
        return ((Integer) arrayList.get(arrayList.size() + i)).intValue();
    }

    public static int getAbsYearDay(Date date, int i) {
        if (i == 0 || i < -366 || i > 366) {
            throw new IllegalArgumentException(MessageFormat.format(INVALID_YEAR_DAY_MESSAGE, Integer.valueOf(i)));
        }
        if (i > 0) {
            return i;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int i2 = calendar.get(1);
        ArrayList arrayList = new ArrayList();
        calendar.set(6, 1);
        while (calendar.get(1) == i2) {
            arrayList.add(Integer.valueOf(calendar.get(6)));
            calendar.add(6, 1);
        }
        return ((Integer) arrayList.get(arrayList.size() + i)).intValue();
    }

    public static int getAbsMonthDay(Date date, int i) {
        if (i == 0 || i < -31 || i > 31) {
            throw new IllegalArgumentException(MessageFormat.format(INVALID_MONTH_DAY_MESSAGE, Integer.valueOf(i)));
        }
        if (i > 0) {
            return i;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int i2 = calendar.get(2);
        ArrayList arrayList = new ArrayList();
        calendar.set(5, 1);
        while (calendar.get(2) == i2) {
            arrayList.add(Integer.valueOf(calendar.get(5)));
            calendar.add(5, 1);
        }
        return ((Integer) arrayList.get(arrayList.size() + i)).intValue();
    }

    public static net.fortuna.ical4j.model.Date getInstance(Date date, Value value) {
        if (Value.DATE.equals(value)) {
            return new net.fortuna.ical4j.model.Date(date);
        }
        return new DateTime(date);
    }

    public static Calendar getCalendarInstance(net.fortuna.ical4j.model.Date date) {
        if (date instanceof DateTime) {
            DateTime dateTime = (DateTime) date;
            if (dateTime.getTimeZone() != null) {
                return Calendar.getInstance(dateTime.getTimeZone());
            }
            if (dateTime.isUtc()) {
                return Calendar.getInstance(TimeZones.getUtcTimeZone());
            }
            return Calendar.getInstance();
        }
        return Calendar.getInstance(TimeZones.getDateTimeZone());
    }

    public static DateList getDateListInstance(DateList dateList) {
        DateList dateList2 = new DateList(dateList.getType());
        if (dateList.isUtc()) {
            dateList2.setUtc(true);
        } else {
            dateList2.setTimeZone(dateList.getTimeZone());
        }
        return dateList2;
    }

    public static long round(long j, int i) {
        return round(j, i, TimeZone.getDefault());
    }

    public static long round(long j, int i, TimeZone timeZone) {
        if (i == 0 && j % 1000 == 0) {
            return j;
        }
        Calendar calendar = Calendar.getInstance(timeZone);
        calendar.setTimeInMillis(j);
        if (i == 1) {
            calendar.set(11, 0);
            calendar.clear(12);
            calendar.clear(13);
            calendar.clear(14);
        } else if (i == 0) {
            calendar.clear(14);
        }
        return calendar.getTimeInMillis();
    }

    public static long getCurrentTimeRounded() {
        return ((long) Math.floor(System.currentTimeMillis() / 1000.0d)) * 1000;
    }
}
