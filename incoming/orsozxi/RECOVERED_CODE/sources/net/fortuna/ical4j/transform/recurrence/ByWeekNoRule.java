package net.fortuna.ical4j.transform.recurrence;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.Calendar;
import java.util.Iterator;
import java.util.Optional;
import net.fortuna.ical4j.model.Date;
import net.fortuna.ical4j.model.DateList;
import net.fortuna.ical4j.model.NumberList;
import net.fortuna.ical4j.model.Recur;
import net.fortuna.ical4j.model.WeekDay;
import net.fortuna.ical4j.util.Dates;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/* loaded from: classes3.dex */
public class ByWeekNoRule extends AbstractDateExpansionRule {
    private transient Logger log;
    private final NumberList weekNoList;

    public ByWeekNoRule(NumberList numberList, Recur.Frequency frequency) {
        super(frequency);
        this.log = LoggerFactory.getLogger((Class<?>) ByWeekNoRule.class);
        this.weekNoList = numberList;
    }

    public ByWeekNoRule(NumberList numberList, Recur.Frequency frequency, Optional<WeekDay.Day> optional) {
        super(frequency, optional);
        this.log = LoggerFactory.getLogger((Class<?>) ByWeekNoRule.class);
        this.weekNoList = numberList;
    }

    @Override // net.fortuna.ical4j.transform.Transformer
    public DateList transform(DateList dateList) {
        if (this.weekNoList.isEmpty()) {
            return dateList;
        }
        DateList dateListInstance = Dates.getDateListInstance(dateList);
        Calendar calendarInstance = getCalendarInstance(dateList.get(0), true);
        Iterator<Date> it2 = dateList.iterator();
        while (it2.hasNext()) {
            Date next = it2.next();
            int actualMaximum = calendarInstance.getActualMaximum(3);
            Iterator<Integer> it3 = this.weekNoList.iterator();
            while (it3.hasNext()) {
                Integer next2 = it3.next();
                if (next2.intValue() == 0 || next2.intValue() < -53 || next2.intValue() > 53) {
                    if (this.log.isTraceEnabled()) {
                        this.log.trace("Invalid week of year: " + next2);
                    }
                } else {
                    Calendar calendarInstance2 = getCalendarInstance(next, true);
                    if (next2.intValue() > 0) {
                        if (actualMaximum >= next2.intValue()) {
                            calendarInstance2.set(3, next2.intValue());
                            dateListInstance.add(Dates.getInstance(getTime(next, calendarInstance2), dateListInstance.getType()));
                        }
                    } else if (actualMaximum >= (-next2.intValue())) {
                        calendarInstance2.set(3, actualMaximum);
                        calendarInstance2.add(3, next2.intValue() + 1);
                        dateListInstance.add(Dates.getInstance(getTime(next, calendarInstance2), dateListInstance.getType()));
                    }
                }
            }
        }
        return dateListInstance;
    }

    private void readObject(ObjectInputStream objectInputStream) throws ClassNotFoundException, IOException {
        objectInputStream.defaultReadObject();
        this.log = LoggerFactory.getLogger((Class<?>) Recur.class);
    }
}
