package net.fortuna.ical4j.filter;

import java.util.function.Predicate;
import net.fortuna.ical4j.model.Component;
import net.fortuna.ical4j.model.Period;

/* loaded from: classes3.dex */
public class PeriodRule<T extends Component> implements Predicate<T> {
    private Period period;

    public PeriodRule(Period period) {
        this.period = period;
    }

    @Override // java.util.function.Predicate
    public final boolean test(Component component) {
        return !component.calculateRecurrenceSet(this.period).isEmpty();
    }
}
