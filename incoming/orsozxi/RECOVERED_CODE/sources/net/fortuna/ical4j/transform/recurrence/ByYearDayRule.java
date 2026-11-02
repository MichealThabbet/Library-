package net.fortuna.ical4j.transform.recurrence;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import net.fortuna.ical4j.model.Date;
import net.fortuna.ical4j.model.DateList;
import net.fortuna.ical4j.model.NumberList;
import net.fortuna.ical4j.model.Recur;
import net.fortuna.ical4j.model.WeekDay;
import net.fortuna.ical4j.model.parameter.Value;
import net.fortuna.ical4j.util.Dates;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/* loaded from: classes3.dex */
public class ByYearDayRule extends AbstractDateExpansionRule {
    private transient Logger log;
    private final NumberList yearDayList;

    public ByYearDayRule(NumberList numberList, Recur.Frequency frequency) {
        super(frequency);
        this.log = LoggerFactory.getLogger((Class<?>) ByYearDayRule.class);
        this.yearDayList = numberList;
    }

    public ByYearDayRule(NumberList numberList, Recur.Frequency frequency, Optional<WeekDay.Day> optional) {
        super(frequency, optional);
        this.log = LoggerFactory.getLogger((Class<?>) ByYearDayRule.class);
        this.yearDayList = numberList;
    }

    @Override // net.fortuna.ical4j.transform.Transformer
    public DateList transform(DateList dateList) {
        if (this.yearDayList.isEmpty()) {
            return dateList;
        }
        DateList dateListInstance = Dates.getDateListInstance(dateList);
        Iterator<Date> it2 = dateList.iterator();
        while (it2.hasNext()) {
            Date next = it2.next();
            if (getFrequency() == Recur.Frequency.YEARLY) {
                dateListInstance.addAll(new ExpansionFilter(dateListInstance.getType()).apply(next));
            } else {
                Optional<Date> optionalApply = new LimitFilter().apply(next);
                if (optionalApply.isPresent()) {
                    dateListInstance.add(optionalApply.get());
                }
            }
        }
        return dateListInstance;
    }

    private class LimitFilter implements Function<Date, Optional<Date>> {
        private LimitFilter() {
        }

        @Override // java.util.function.Function
        public Optional<Date> apply(Date date) {
            if (ByYearDayRule.this.yearDayList.contains(Integer.valueOf(ByYearDayRule.this.getCalendarInstance(date, true).get(6)))) {
                return Optional.of(date);
            }
            return Optional.empty();
        }
    }

    private class ExpansionFilter implements Function<Date, List<Date>> {
        private final Value type;

        public ExpansionFilter(Value value) {
            this.type = value;
        }

        @Override // java.util.function.Function
        public List<Date> apply(Date date) {
            ArrayList arrayList = new ArrayList();
            Calendar calendarInstance = ByYearDayRule.this.getCalendarInstance(date, false);
            Iterator<Integer> it2 = ByYearDayRule.this.yearDayList.iterator();
            while (it2.hasNext()) {
                int iIntValue = it2.next().intValue();
                if (iIntValue == 0 || iIntValue < -366 || iIntValue > 366) {
                    if (ByYearDayRule.this.log.isTraceEnabled()) {
                        ByYearDayRule.this.log.trace("Invalid day of year: " + iIntValue);
                    }
                } else {
                    int actualMaximum = calendarInstance.getActualMaximum(6);
                    if (iIntValue > 0) {
                        if (actualMaximum >= iIntValue) {
                            calendarInstance.set(6, iIntValue);
                            arrayList.add(Dates.getInstance(AbstractDateExpansionRule.getTime(date, calendarInstance), this.type));
                        }
                    } else if (actualMaximum >= (-iIntValue)) {
                        calendarInstance.set(6, actualMaximum);
                        calendarInstance.add(6, iIntValue + 1);
                        arrayList.add(Dates.getInstance(AbstractDateExpansionRule.getTime(date, calendarInstance), this.type));
                    }
                }
            }
            return arrayList;
        }
    }

    private void readObject(ObjectInputStream objectInputStream) throws ClassNotFoundException, IOException {
        objectInputStream.defaultReadObject();
        this.log = LoggerFactory.getLogger((Class<?>) Recur.class);
    }
}
