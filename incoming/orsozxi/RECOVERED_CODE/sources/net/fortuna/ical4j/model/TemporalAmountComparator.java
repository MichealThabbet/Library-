package net.fortuna.ical4j.model;

import java.time.Duration;
import java.time.temporal.TemporalAmount;
import java.time.temporal.TemporalUnit;
import java.util.Comparator;
import java.util.function.Predicate;

/* loaded from: classes3.dex */
public class TemporalAmountComparator implements Comparator<TemporalAmount> {
    @Override // java.util.Comparator
    public int compare(TemporalAmount temporalAmount, TemporalAmount temporalAmount2) {
        int days;
        int days2;
        int i = Integer.MAX_VALUE;
        if (!temporalAmount.getClass().equals(temporalAmount2.getClass())) {
            boolean zAnyMatch = temporalAmount.getUnits().stream().anyMatch(new Predicate() { // from class: net.fortuna.ical4j.model.-$$Lambda$TemporalAmountComparator$sk5o0k66GkdwKmxerWzSYMBz43o
                @Override // java.util.function.Predicate
                public final boolean test(Object obj) {
                    return ((TemporalUnit) obj).isDateBased();
                }
            });
            if (zAnyMatch != temporalAmount2.getUnits().stream().anyMatch(new Predicate() { // from class: net.fortuna.ical4j.model.-$$Lambda$TemporalAmountComparator$xQa_hrur2Ok0GPc0Zly7AzHPdUI
                @Override // java.util.function.Predicate
                public final boolean test(Object obj) {
                    return ((TemporalUnit) obj).isDateBased();
                }
            })) {
                return zAnyMatch ? Integer.MAX_VALUE : Integer.MIN_VALUE;
            }
            return 0;
        }
        if ((temporalAmount instanceof java.time.Period) && (temporalAmount2 instanceof java.time.Period)) {
            java.time.Period period = (java.time.Period) temporalAmount;
            java.time.Period period2 = (java.time.Period) temporalAmount2;
            if (period.isNegative() != period2.isNegative()) {
                if (period.isNegative()) {
                    i = Integer.MIN_VALUE;
                }
            } else {
                if (period.getYears() != period2.getYears()) {
                    days = period.getYears();
                    days2 = period2.getYears();
                } else if (period.getMonths() != period2.getMonths()) {
                    days = period.getMonths();
                    days2 = period2.getMonths();
                } else {
                    days = period.getDays();
                    days2 = period2.getDays();
                }
                i = days - days2;
            }
            return period.isNegative() ? -i : i;
        }
        return Duration.from(temporalAmount).compareTo(Duration.from(temporalAmount2));
    }
}
