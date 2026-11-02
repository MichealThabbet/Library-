package net.fortuna.ical4j.filter;

import java.util.Date;
import java.util.function.Predicate;
import net.fortuna.ical4j.model.DateRange;

/* loaded from: classes3.dex */
public class DateInRangeRule implements Predicate<Date> {
    private final int inclusiveMask;
    private final DateRange range;

    public DateInRangeRule(DateRange dateRange, int i) {
        this.range = dateRange;
        this.inclusiveMask = i;
    }

    @Override // java.util.function.Predicate
    public boolean test(Date date) {
        return this.range.includes(date, this.inclusiveMask);
    }
}
