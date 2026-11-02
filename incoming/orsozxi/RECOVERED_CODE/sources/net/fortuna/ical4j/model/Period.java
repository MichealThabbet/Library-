package net.fortuna.ical4j.model;

import java.text.ParseException;
import java.time.temporal.TemporalAmount;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

/* loaded from: classes3.dex */
public class Period extends DateRange implements Comparable<Period> {
    private static final long serialVersionUID = 7321090422911676490L;
    private TemporalAmountAdapter duration;

    public Period(String str) throws ParseException {
        super(parseStartDate(str), parseEndDate(str, true));
        try {
            parseEndDate(str, false);
        } catch (ParseException unused) {
            this.duration = parseDuration(str);
        }
        normalise();
    }

    public Period(DateTime dateTime, DateTime dateTime2) {
        super(dateTime, dateTime2);
        normalise();
    }

    @Deprecated
    public Period(DateTime dateTime, Dur dur) {
        this(dateTime, TemporalAmountAdapter.from(dur).getDuration());
    }

    public Period(DateTime dateTime, TemporalAmount temporalAmount) {
        super(dateTime, new DateTime(java.util.Date.from(dateTime.toInstant().plus(temporalAmount))));
        this.duration = new TemporalAmountAdapter(temporalAmount);
        normalise();
    }

    private static DateTime parseStartDate(String str) throws ParseException {
        return new DateTime(str.substring(0, str.indexOf(47)));
    }

    private static DateTime parseEndDate(String str, boolean z) throws ParseException {
        try {
            return new DateTime(str.substring(str.indexOf(47) + 1));
        } catch (ParseException e) {
            if (z) {
                return new DateTime(java.util.Date.from(parseStartDate(str).toInstant().plus(parseDuration(str).getDuration())));
            }
            throw e;
        }
    }

    private static TemporalAmountAdapter parseDuration(String str) {
        return TemporalAmountAdapter.parse(str.substring(str.indexOf(47) + 1));
    }

    private void normalise() {
        if (getStart().isUtc()) {
            getEnd().setUtc(true);
        } else {
            getEnd().setTimeZone(getStart().getTimeZone());
        }
    }

    public final TemporalAmount getDuration() {
        TemporalAmountAdapter temporalAmountAdapter = this.duration;
        if (temporalAmountAdapter == null) {
            return TemporalAmountAdapter.fromDateRange(getStart(), getEnd()).getDuration();
        }
        return temporalAmountAdapter.getDuration();
    }

    public final DateTime getEnd() {
        return (DateTime) getRangeEnd();
    }

    public final DateTime getStart() {
        return (DateTime) getRangeStart();
    }

    public final boolean includes(java.util.Date date, boolean z) {
        if (z) {
            return includes(date, 3);
        }
        return includes(date, 0);
    }

    public final Period add(Period period) {
        DateTime start;
        DateTime end;
        DateTime end2;
        DateTime start2;
        if (period == null) {
            start2 = getStart();
            end2 = getEnd();
        } else {
            if (getStart().before(period.getStart())) {
                start = getStart();
            } else {
                start = period.getStart();
            }
            if (getEnd().after(period.getEnd())) {
                end = getEnd();
            } else {
                end = period.getEnd();
            }
            DateTime dateTime = start;
            end2 = end;
            start2 = dateTime;
        }
        return new Period(start2, end2);
    }

    public final PeriodList subtract(Period period) {
        DateTime end;
        DateTime end2;
        PeriodList periodList = new PeriodList();
        if (period.contains(this)) {
            return periodList;
        }
        if (!period.intersects(this)) {
            periodList.add(this);
            return periodList;
        }
        if (!period.getStart().after(getStart())) {
            end = period.getEnd();
            end2 = getEnd();
        } else if (!period.getEnd().before(getEnd())) {
            DateTime start = getStart();
            end2 = period.getStart();
            end = start;
        } else {
            periodList.add(new Period(getStart(), period.getStart()));
            end = period.getEnd();
            end2 = getEnd();
        }
        periodList.add(new Period(end, end2));
        return periodList;
    }

    public final boolean isEmpty() {
        return getStart().equals(getEnd());
    }

    public void setUtc(boolean z) {
        getStart().setUtc(z);
        getEnd().setUtc(z);
    }

    public final void setTimeZone(TimeZone timeZone) {
        getStart().setUtc(false);
        getStart().setTimeZone(timeZone);
        getEnd().setUtc(false);
        getEnd().setTimeZone(timeZone);
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getStart());
        sb.append(IOUtils.DIR_SEPARATOR_UNIX);
        TemporalAmountAdapter temporalAmountAdapter = this.duration;
        if (temporalAmountAdapter == null) {
            sb.append(getEnd());
        } else {
            sb.append(temporalAmountAdapter);
        }
        return sb.toString();
    }

    @Override // java.lang.Comparable
    public final int compareTo(Period period) {
        int iCompareTo;
        if (period == null) {
            throw new ClassCastException("Cannot compare this object to null");
        }
        int iCompareTo2 = getStart().compareTo((java.util.Date) period.getStart());
        return iCompareTo2 != 0 ? iCompareTo2 : (this.duration != null || (iCompareTo = getEnd().compareTo((java.util.Date) period.getEnd())) == 0) ? new TemporalAmountComparator().compare(getDuration(), period.getDuration()) : iCompareTo;
    }

    @Override // net.fortuna.ical4j.model.DateRange
    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Period)) {
            return false;
        }
        Period period = (Period) obj;
        EqualsBuilder equalsBuilderAppend = new EqualsBuilder().append(getStart(), period.getStart());
        Object end = this.duration;
        if (end == null) {
            end = getEnd();
        }
        Object end2 = period.duration;
        if (end2 == null) {
            end2 = period.getEnd();
        }
        return equalsBuilderAppend.append(end, end2).isEquals();
    }

    @Override // net.fortuna.ical4j.model.DateRange
    public final int hashCode() {
        HashCodeBuilder hashCodeBuilderAppend = new HashCodeBuilder().append(getStart());
        Object end = this.duration;
        if (end == null) {
            end = getEnd();
        }
        return hashCodeBuilderAppend.append(end).toHashCode();
    }
}
