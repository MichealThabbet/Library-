package net.fortuna.ical4j.transform.recurrence;

import java.util.ArrayList;
import java.util.Calendar;
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
public class ByMonthRule extends AbstractDateExpansionRule {
    private final NumberList monthList;

    public ByMonthRule(NumberList numberList, Recur.Frequency frequency) {
        this(numberList, frequency, Optional.empty());
    }

    public ByMonthRule(NumberList numberList, Recur.Frequency frequency, Optional<WeekDay.Day> optional) {
        super(frequency, optional);
        this.monthList = numberList;
    }

    @Override // net.fortuna.ical4j.transform.Transformer
    public DateList transform(DateList dateList) {
        if (this.monthList.isEmpty()) {
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
            if (ByMonthRule.this.monthList.contains(Integer.valueOf(ByMonthRule.this.getCalendarInstance(date, true).get(2) + 1))) {
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
            final Calendar calendarInstance = ByMonthRule.this.getCalendarInstance(date, true);
            ByMonthRule.this.monthList.forEach(new Consumer() { // from class: net.fortuna.ical4j.transform.recurrence.-$$Lambda$ByMonthRule$ExpansionFilter$CnkIzGhE_Jux_RDSWf5yUDuDjl8
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    this.f$0.lambda$apply$0$ByMonthRule$ExpansionFilter(calendarInstance, arrayList, date, (Integer) obj);
                }
            });
            return arrayList;
        }

        public /* synthetic */ void lambda$apply$0$ByMonthRule$ExpansionFilter(Calendar calendar, List list, Date date, Integer num) {
            calendar.roll(2, (num.intValue() - 1) - calendar.get(2));
            list.add(Dates.getInstance(AbstractDateExpansionRule.getTime(date, calendar), this.type));
        }
    }
}
