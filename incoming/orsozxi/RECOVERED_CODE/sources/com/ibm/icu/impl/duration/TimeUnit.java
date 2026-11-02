package com.ibm.icu.impl.duration;

import net.fortuna.ical4j.util.Dates;

/* loaded from: classes2.dex */
public final class TimeUnit {
    public static final TimeUnit MILLISECOND;
    static final long[] approxDurations;
    static final TimeUnit[] units;
    final String name;
    final byte ordinal;
    public static final TimeUnit YEAR = new TimeUnit("year", 0);
    public static final TimeUnit MONTH = new TimeUnit("month", 1);
    public static final TimeUnit WEEK = new TimeUnit("week", 2);
    public static final TimeUnit DAY = new TimeUnit("day", 3);
    public static final TimeUnit HOUR = new TimeUnit("hour", 4);
    public static final TimeUnit MINUTE = new TimeUnit("minute", 5);
    public static final TimeUnit SECOND = new TimeUnit("second", 6);

    private TimeUnit(String str, int i) {
        this.name = str;
        this.ordinal = (byte) i;
    }

    public String toString() {
        return this.name;
    }

    static {
        TimeUnit timeUnit = new TimeUnit("millisecond", 7);
        MILLISECOND = timeUnit;
        units = new TimeUnit[]{YEAR, MONTH, WEEK, DAY, HOUR, MINUTE, SECOND, timeUnit};
        approxDurations = new long[]{31557600000L, 2630880000L, Dates.MILLIS_PER_WEEK, 86400000, 3600000, 60000, 1000, 1};
    }

    public TimeUnit larger() {
        byte b = this.ordinal;
        if (b == 0) {
            return null;
        }
        return units[b - 1];
    }

    public TimeUnit smaller() {
        byte b = this.ordinal;
        TimeUnit[] timeUnitArr = units;
        if (b == timeUnitArr.length - 1) {
            return null;
        }
        return timeUnitArr[b + 1];
    }

    public int ordinal() {
        return this.ordinal;
    }
}
