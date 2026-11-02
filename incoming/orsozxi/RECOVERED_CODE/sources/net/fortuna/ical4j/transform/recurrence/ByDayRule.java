package net.fortuna.ical4j.transform.recurrence;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import net.fortuna.ical4j.model.Date;
import net.fortuna.ical4j.model.DateList;
import net.fortuna.ical4j.model.Recur;
import net.fortuna.ical4j.model.WeekDay;
import net.fortuna.ical4j.model.WeekDayList;
import net.fortuna.ical4j.model.parameter.Value;
import net.fortuna.ical4j.transform.recurrence.ByDayRule;
import net.fortuna.ical4j.util.Dates;

/* loaded from: classes3.dex */
public class ByDayRule extends AbstractDateExpansionRule {
    private final WeekDayList dayList;

    public ByDayRule(WeekDayList weekDayList, Recur.Frequency frequency) {
        super(frequency);
        this.dayList = weekDayList;
    }

    public ByDayRule(WeekDayList weekDayList, Recur.Frequency frequency, Optional<WeekDay.Day> optional) {
        super(frequency, optional);
        this.dayList = weekDayList;
    }

    @Override // net.fortuna.ical4j.transform.Transformer
    public DateList transform(DateList dateList) {
        Function weeklyExpansionFilter;
        if (this.dayList.isEmpty()) {
            return dateList;
        }
        final DateList dateListInstance = Dates.getDateListInstance(dateList);
        int i = AnonymousClass1.$SwitchMap$net$fortuna$ical4j$model$Recur$Frequency[getFrequency().ordinal()];
        if (i == 1) {
            weeklyExpansionFilter = new WeeklyExpansionFilter(dateList.getType());
        } else if (i == 2) {
            weeklyExpansionFilter = new MonthlyExpansionFilter(dateList.getType());
        } else if (i == 3) {
            weeklyExpansionFilter = new YearlyExpansionFilter(dateList.getType());
        } else {
            weeklyExpansionFilter = new LimitFilter(this, null);
        }
        Iterator<Date> it2 = dateList.iterator();
        while (it2.hasNext()) {
            final List list = (List) weeklyExpansionFilter.apply(it2.next());
            final ArrayList arrayList = new ArrayList();
            this.dayList.forEach(new Consumer() { // from class: net.fortuna.ical4j.transform.recurrence.-$$Lambda$ByDayRule$Vi_HJQUwGrO6s4jbtCTxrShhYuw
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    this.f$0.lambda$transform$2$ByDayRule(arrayList, list, dateListInstance, (WeekDay) obj);
                }
            });
            dateListInstance.addAll(arrayList);
        }
        return dateListInstance;
    }

    /* renamed from: net.fortuna.ical4j.transform.recurrence.ByDayRule$1, reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$net$fortuna$ical4j$model$Recur$Frequency;

        static {
            int[] iArr = new int[Recur.Frequency.values().length];
            $SwitchMap$net$fortuna$ical4j$model$Recur$Frequency = iArr;
            try {
                iArr[Recur.Frequency.WEEKLY.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$net$fortuna$ical4j$model$Recur$Frequency[Recur.Frequency.MONTHLY.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$net$fortuna$ical4j$model$Recur$Frequency[Recur.Frequency.YEARLY.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$net$fortuna$ical4j$model$Recur$Frequency[Recur.Frequency.DAILY.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
        }
    }

    public /* synthetic */ void lambda$transform$2$ByDayRule(List list, List list2, final DateList dateList, final WeekDay weekDay) {
        list.addAll(getOffsetDates((DateList) list2.stream().filter(new Predicate() { // from class: net.fortuna.ical4j.transform.recurrence.-$$Lambda$ByDayRule$qmjiU86w9S31CPnVl-G7nOIy9Dk
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return this.f$0.lambda$transform$0$ByDayRule(weekDay, (Date) obj);
            }
        }).collect(Collectors.toCollection(new Supplier() { // from class: net.fortuna.ical4j.transform.recurrence.-$$Lambda$ByDayRule$gOUHTsqWMuQfp-ZtKHOEIvQPfoA
            @Override // java.util.function.Supplier
            public final Object get() {
                return Dates.getDateListInstance(dateList);
            }
        })), weekDay.getOffset()));
    }

    public /* synthetic */ boolean lambda$transform$0$ByDayRule(WeekDay weekDay, Date date) {
        return getCalendarInstance(date, true).get(7) == WeekDay.getCalendarDay(weekDay);
    }

    private class LimitFilter implements Function<Date, List<Date>> {
        private LimitFilter() {
        }

        /* synthetic */ LimitFilter(ByDayRule byDayRule, AnonymousClass1 anonymousClass1) {
            this();
        }

        @Override // java.util.function.Function
        public List<Date> apply(Date date) {
            if (ByDayRule.this.dayList.contains(WeekDay.getWeekDay(ByDayRule.this.getCalendarInstance(date, true)))) {
                return Arrays.asList(date);
            }
            return Collections.emptyList();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    class WeeklyExpansionFilter implements Function<Date, List<Date>> {
        private final Value type;

        public WeeklyExpansionFilter(Value value) {
            this.type = value;
        }

        @Override // java.util.function.Function
        public List<Date> apply(Date date) {
            ArrayList arrayList = new ArrayList();
            final Calendar calendarInstance = ByDayRule.this.getCalendarInstance(date, true);
            int i = calendarInstance.get(3);
            calendarInstance.set(7, calendarInstance.getFirstDayOfWeek());
            while (calendarInstance.get(3) == i) {
                if (!((List) ByDayRule.this.dayList.stream().map(new Function() { // from class: net.fortuna.ical4j.transform.recurrence.-$$Lambda$ByDayRule$WeeklyExpansionFilter$UIH5qCBxLyINIu-dR7LDZC0ktVE
                    @Override // java.util.function.Function
                    public final Object apply(Object obj) {
                        return Integer.valueOf(WeekDay.getCalendarDay((WeekDay) obj));
                    }
                }).filter(new Predicate() { // from class: net.fortuna.ical4j.transform.recurrence.-$$Lambda$ByDayRule$WeeklyExpansionFilter$mexgFELyft0rroU0pJkLxiIS1Lo
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ByDayRule.WeeklyExpansionFilter.lambda$apply$1(calendarInstance, (Integer) obj);
                    }
                }).collect(Collectors.toList())).isEmpty()) {
                    arrayList.add(Dates.getInstance(AbstractDateExpansionRule.getTime(date, calendarInstance), this.type));
                }
                calendarInstance.add(7, 1);
            }
            return arrayList;
        }

        static /* synthetic */ boolean lambda$apply$1(Calendar calendar, Integer num) {
            return calendar.get(7) == num.intValue();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    class MonthlyExpansionFilter implements Function<Date, List<Date>> {
        private final Value type;

        public MonthlyExpansionFilter(Value value) {
            this.type = value;
        }

        @Override // java.util.function.Function
        public List<Date> apply(Date date) {
            ArrayList arrayList = new ArrayList();
            final Calendar calendarInstance = ByDayRule.this.getCalendarInstance(date, true);
            int i = calendarInstance.get(2);
            calendarInstance.set(5, 1);
            while (calendarInstance.get(2) == i) {
                if (!((List) ByDayRule.this.dayList.stream().map(new Function() { // from class: net.fortuna.ical4j.transform.recurrence.-$$Lambda$ByDayRule$MonthlyExpansionFilter$5hqIgl3Fp_c6XnVtHSjXvVMnZPc
                    @Override // java.util.function.Function
                    public final Object apply(Object obj) {
                        return Integer.valueOf(WeekDay.getCalendarDay((WeekDay) obj));
                    }
                }).filter(new Predicate() { // from class: net.fortuna.ical4j.transform.recurrence.-$$Lambda$ByDayRule$MonthlyExpansionFilter$Ood0AQpxEr8G8_MjoFVw8Tv1_aw
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ByDayRule.MonthlyExpansionFilter.lambda$apply$1(calendarInstance, (Integer) obj);
                    }
                }).collect(Collectors.toList())).isEmpty()) {
                    arrayList.add(Dates.getInstance(AbstractDateExpansionRule.getTime(date, calendarInstance), this.type));
                }
                calendarInstance.add(5, 1);
            }
            return arrayList;
        }

        static /* synthetic */ boolean lambda$apply$1(Calendar calendar, Integer num) {
            return calendar.get(7) == num.intValue();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    class YearlyExpansionFilter implements Function<Date, List<Date>> {
        private final Value type;

        public YearlyExpansionFilter(Value value) {
            this.type = value;
        }

        @Override // java.util.function.Function
        public List<Date> apply(Date date) {
            ArrayList arrayList = new ArrayList();
            final Calendar calendarInstance = ByDayRule.this.getCalendarInstance(date, true);
            int i = calendarInstance.get(1);
            calendarInstance.set(6, 1);
            while (calendarInstance.get(1) == i) {
                if (!((List) ByDayRule.this.dayList.stream().map(new Function() { // from class: net.fortuna.ical4j.transform.recurrence.-$$Lambda$ByDayRule$YearlyExpansionFilter$-Nf89BhugOiy180rMo9Mqu8WyMY
                    @Override // java.util.function.Function
                    public final Object apply(Object obj) {
                        return Integer.valueOf(WeekDay.getCalendarDay((WeekDay) obj));
                    }
                }).filter(new Predicate() { // from class: net.fortuna.ical4j.transform.recurrence.-$$Lambda$ByDayRule$YearlyExpansionFilter$8QxzB2CSC2uXqFiTJmHJmXIzhPQ
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ByDayRule.YearlyExpansionFilter.lambda$apply$1(calendarInstance, (Integer) obj);
                    }
                }).collect(Collectors.toList())).isEmpty()) {
                    arrayList.add(Dates.getInstance(AbstractDateExpansionRule.getTime(date, calendarInstance), this.type));
                }
                calendarInstance.add(6, 1);
            }
            return arrayList;
        }

        static /* synthetic */ boolean lambda$apply$1(Calendar calendar, Integer num) {
            return calendar.get(7) == num.intValue();
        }
    }

    private List<Date> getOffsetDates(DateList dateList, int i) {
        if (i == 0) {
            return dateList;
        }
        DateList dateListInstance = Dates.getDateListInstance(dateList);
        int size = dateList.size();
        if (i < 0 && i >= (-size)) {
            dateListInstance.add((DateList) dateList.get(size + i));
        } else if (i > 0 && i <= size) {
            dateListInstance.add((DateList) dateList.get(i - 1));
        }
        return dateListInstance;
    }
}
