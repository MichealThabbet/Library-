package net.fortuna.ical4j.transform.recurrence;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.EnumSet;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import net.fortuna.ical4j.model.Date;
import net.fortuna.ical4j.model.DateList;
import net.fortuna.ical4j.model.NumberList;
import net.fortuna.ical4j.model.Recur;
import net.fortuna.ical4j.model.WeekDay;
import net.fortuna.ical4j.model.parameter.Value;
import net.fortuna.ical4j.util.Dates;

/* loaded from: classes3.dex */
public class ByHourRule extends AbstractDateExpansionRule {
    private final NumberList hourList;

    public ByHourRule(NumberList numberList, Recur.Frequency frequency) {
        super(frequency);
        this.hourList = numberList;
    }

    public ByHourRule(NumberList numberList, Recur.Frequency frequency, Optional<WeekDay.Day> optional) {
        super(frequency, optional);
        this.hourList = numberList;
    }

    @Override // net.fortuna.ical4j.transform.Transformer
    public DateList transform(DateList dateList) {
        if (this.hourList.isEmpty()) {
            return dateList;
        }
        DateList dateListInstance = Dates.getDateListInstance(dateList);
        Iterator<Date> it2 = dateList.iterator();
        while (it2.hasNext()) {
            Date next = it2.next();
            if (EnumSet.of(Recur.Frequency.DAILY, Recur.Frequency.WEEKLY, Recur.Frequency.MONTHLY, Recur.Frequency.YEARLY).contains(getFrequency())) {
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
            if (ByHourRule.this.hourList.contains(Integer.valueOf(ByHourRule.this.getCalendarInstance(date, true).get(11)))) {
                return Optional.of(date);
            }
            return Optional.empty();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    class ExpansionFilter implements Function<Date, List<Date>> {
        private final Value type;

        public ExpansionFilter(Value value) {
            this.type = value;
        }

        @Override // java.util.function.Function
        public List<Date> apply(final Date date) {
            final ArrayList arrayList = new ArrayList();
            final Calendar calendarInstance = ByHourRule.this.getCalendarInstance(date, true);
            ByHourRule.this.hourList.forEach(new Consumer() { // from class: net.fortuna.ical4j.transform.recurrence.-$$Lambda$ByHourRule$ExpansionFilter$HAIUyLfUTTefd6VWS5-l7oXpc5M
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    this.f$0.lambda$apply$0$ByHourRule$ExpansionFilter(calendarInstance, arrayList, date, (Integer) obj);
                }
            });
            return arrayList;
        }

        public /* synthetic */ void lambda$apply$0$ByHourRule$ExpansionFilter(Calendar calendar, List list, Date date, Integer num) {
            calendar.set(11, num.intValue());
            list.add(Dates.getInstance(AbstractDateExpansionRule.getTime(date, calendar), this.type));
        }
    }
}
