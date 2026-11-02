package net.fortuna.ical4j.model;

import com.huawei.hms.push.constant.RemoteMessageConst;
import com.ibm.icu.impl.Grego;
import com.ibm.icu.impl.locale.LanguageTag;
import it.piapostolos.android.pinotes.R2;
import java.io.Serializable;
import java.time.Duration;
import java.time.Instant;
import java.time.temporal.TemporalAmount;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

/* loaded from: classes3.dex */
public class TemporalAmountAdapter implements Serializable {
    private final TemporalAmount duration;

    public TemporalAmountAdapter(TemporalAmount temporalAmount) {
        this.duration = temporalAmount;
    }

    public TemporalAmount getDuration() {
        return this.duration;
    }

    public String toString() {
        if (Duration.ZERO.equals(this.duration) || java.time.Period.ZERO.equals(this.duration)) {
            return this.duration.toString();
        }
        TemporalAmount temporalAmount = this.duration;
        if (temporalAmount instanceof java.time.Period) {
            return periodToString(((java.time.Period) temporalAmount).normalized());
        }
        return durationToString((Duration) temporalAmount);
    }

    private String periodToString(java.time.Period period) {
        String string;
        java.time.Period periodNegated = period.isNegative() ? period.negated() : period;
        if (periodNegated.getYears() != 0) {
            string = String.format("P%dW", Integer.valueOf(periodNegated.getYears() * 52));
        } else if (periodNegated.getMonths() != 0) {
            string = String.format("P%dW", Integer.valueOf(periodNegated.getMonths() * 4));
        } else {
            string = periodNegated.getDays() % 7 == 0 ? String.format("P%dW", Integer.valueOf(periodNegated.getDays() / 7)) : periodNegated.toString();
        }
        if (!period.isNegative()) {
            return string;
        }
        return LanguageTag.SEP + string;
    }

    private String durationToString(Duration duration) {
        String string;
        Duration durationAbs = duration.abs();
        int seconds = durationAbs.getSeconds() != 0 ? ((int) durationAbs.getSeconds()) / RemoteMessageConst.DEFAULT_TTL : 0;
        if (seconds != 0) {
            Duration durationMinusDays = durationAbs.minusDays(seconds);
            if (durationMinusDays.getSeconds() != 0) {
                int seconds2 = ((int) durationMinusDays.getSeconds()) / R2.string.fab_transformation_scrim_behavior;
                long j = seconds2;
                int seconds3 = ((int) durationMinusDays.minusHours(j).getSeconds()) / 60;
                int seconds4 = (int) durationMinusDays.minusHours(j).minusMinutes(seconds3).getSeconds();
                string = seconds2 > 0 ? seconds4 > 0 ? String.format("P%dDT%dH%dM%dS", Integer.valueOf(seconds), Integer.valueOf(seconds2), Integer.valueOf(seconds3), Integer.valueOf(seconds4)) : String.format("P%dDT%dH", Integer.valueOf(seconds), Integer.valueOf(seconds2)) : seconds3 > 0 ? String.format("P%dDT%dM", Integer.valueOf(seconds), Integer.valueOf(seconds3)) : seconds4 > 0 ? String.format("P%dDT%dS", Integer.valueOf(seconds), Integer.valueOf(seconds4)) : null;
            } else {
                string = String.format("P%dD", Integer.valueOf(seconds));
            }
        } else {
            string = durationAbs.toString();
        }
        if (!duration.isNegative()) {
            return string;
        }
        return LanguageTag.SEP + string;
    }

    public static TemporalAmountAdapter parse(String str) {
        TemporalAmount temporalAmount;
        if (str.matches("([+-])?P.*(W|D)")) {
            temporalAmount = java.time.Period.parse(str);
        } else {
            temporalAmount = Duration.parse(str);
        }
        return new TemporalAmountAdapter(temporalAmount);
    }

    public static TemporalAmountAdapter fromDateRange(java.util.Date date, java.util.Date date2) {
        TemporalAmount temporalAmountOfMillis;
        long time = date2.getTime() - date.getTime();
        if (time % 86400000 == 0) {
            temporalAmountOfMillis = java.time.Period.ofDays(((int) time) / Grego.MILLIS_PER_DAY);
        } else {
            temporalAmountOfMillis = Duration.ofMillis(time);
        }
        return new TemporalAmountAdapter(temporalAmountOfMillis);
    }

    public static TemporalAmountAdapter from(Dur dur) {
        TemporalAmount temporalAmountNegated;
        if (dur.getWeeks() > 0) {
            java.time.Period periodOfWeeks = java.time.Period.ofWeeks(dur.getWeeks());
            temporalAmountNegated = periodOfWeeks;
            if (dur.isNegative()) {
                temporalAmountNegated = periodOfWeeks.negated();
            }
        } else {
            Duration durationPlusSeconds = Duration.ofDays(dur.getDays()).plusHours(dur.getHours()).plusMinutes(dur.getMinutes()).plusSeconds(dur.getSeconds());
            temporalAmountNegated = durationPlusSeconds;
            if (dur.isNegative()) {
                temporalAmountNegated = durationPlusSeconds.negated();
            }
        }
        return new TemporalAmountAdapter(temporalAmountNegated);
    }

    public final java.util.Date getTime(java.util.Date date) {
        return java.util.Date.from(Instant.from(this.duration.addTo(date.toInstant())));
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        return new EqualsBuilder().append(this.duration, ((TemporalAmountAdapter) obj).duration).isEquals();
    }

    public int hashCode() {
        return new HashCodeBuilder(17, 37).append(this.duration).toHashCode();
    }
}
