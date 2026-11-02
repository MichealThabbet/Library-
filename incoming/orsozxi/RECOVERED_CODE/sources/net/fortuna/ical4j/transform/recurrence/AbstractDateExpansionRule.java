package net.fortuna.ical4j.transform.recurrence;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Optional;
import net.fortuna.ical4j.model.Date;
import net.fortuna.ical4j.model.DateList;
import net.fortuna.ical4j.model.DateTime;
import net.fortuna.ical4j.model.Recur;
import net.fortuna.ical4j.model.WeekDay;
import net.fortuna.ical4j.transform.Transformer;
import net.fortuna.ical4j.util.Dates;

/* loaded from: classes3.dex */
public abstract class AbstractDateExpansionRule implements Transformer<DateList>, Serializable {
    private final int calendarWeekStartDay;
    private final Recur.Frequency frequency;

    public AbstractDateExpansionRule(Recur.Frequency frequency) {
        this(frequency, Optional.of(WeekDay.Day.MO));
    }

    public AbstractDateExpansionRule(Recur.Frequency frequency, Optional<WeekDay.Day> optional) {
        this.frequency = frequency;
        this.calendarWeekStartDay = WeekDay.getCalendarDay(WeekDay.getWeekDay(optional.orElse(WeekDay.Day.MO)));
    }

    protected Recur.Frequency getFrequency() {
        return this.frequency;
    }

    protected Calendar getCalendarInstance(Date date, boolean z) {
        Calendar calendarInstance = Dates.getCalendarInstance(date);
        calendarInstance.setMinimalDaysInFirstWeek(4);
        calendarInstance.setFirstDayOfWeek(this.calendarWeekStartDay);
        calendarInstance.setLenient(z);
        calendarInstance.setTime(date);
        return calendarInstance;
    }

    protected static Date getTime(Date date, Calendar calendar) {
        DateTime dateTime = new DateTime(date);
        dateTime.setTime(calendar.getTime().getTime());
        return dateTime;
    }
}
