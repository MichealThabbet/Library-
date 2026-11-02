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
public class ByMinuteRule extends AbstractDateExpansionRule {
    private final NumberList minuteList;

    public ByMinuteRule(NumberList numberList, Recur.Frequency frequency) {
        super(frequency);
        this.minuteList = numberList;
    }

    public ByMinuteRule(NumberList numberList, Recur.Frequency frequency, Optional<WeekDay.Day> optional) {
        super(frequency, optional);
        this.minuteList = numberList;
    }

    @Override // net.fortuna.ical4j.transform.Transformer
    public DateList transform(DateList dateList) {
        if (this.minuteList.isEmpty()) {
            return dateList;
        }
        DateList dateListInstance = Dates.getDateListInstance(dateList);
        Iterator<Date> it2 = dateList.iterator();
        while (it2.hasNext()) {
            Date next = it2.next();
            if (EnumSet.of(Recur.Frequency.HOURLY, Recur.Frequency.DAILY, Recur.Frequency.WEEKLY, Recur.Frequency.MONTHLY, Recur.Frequency.YEARLY).contains(getFrequency())) {
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
            if (ByMinuteRule.this.minuteList.contains(Integer.valueOf(ByMinuteRule.this.getCalendarInstance(date, true).get(12)))) {
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
            final Calendar calendarInstance = ByMinuteRule.this.getCalendarInstance(date, true);
            ByMinuteRule.this.minuteList.forEach(new Consumer() { // from class: net.fortuna.ical4j.transform.recurrence.-$$Lambda$ByMinuteRule$ExpansionFilter$xK_8mN92wlci6IsMsC25_mcSb6s
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    this.f$0.lambda$apply$0$ByMinuteRule$ExpansionFilter(calendarInstance, arrayList, date, (Integer) obj);
                }
            });
            return arrayList;
        }

        public /* synthetic */ void lambda$apply$0$ByMinuteRule$ExpansionFilter(Calendar calendar, List list, Date date, Integer num) {
            calendar.set(12, num.intValue());
            list.add(Dates.getInstance(AbstractDateExpansionRule.getTime(date, calendar), this.type));
        }
    }
}
