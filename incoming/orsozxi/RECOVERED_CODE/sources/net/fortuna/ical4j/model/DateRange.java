package net.fortuna.ical4j.model;

import java.io.Serializable;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

/* loaded from: classes3.dex */
public class DateRange implements Serializable {
    public static final int INCLUSIVE_END = 2;
    public static final int INCLUSIVE_START = 1;
    private static final long serialVersionUID = -7303846680559287286L;
    private final java.util.Date rangeEnd;
    private final java.util.Date rangeStart;

    public DateRange(java.util.Date date, java.util.Date date2) {
        if (date == null) {
            throw new IllegalArgumentException("Range start is null");
        }
        if (date2 == null) {
            throw new IllegalArgumentException("Range end is null");
        }
        if (date2.before(date)) {
            throw new IllegalArgumentException("Range start must be before range end");
        }
        this.rangeStart = date;
        this.rangeEnd = date2;
    }

    public java.util.Date getRangeStart() {
        return this.rangeStart;
    }

    public java.util.Date getRangeEnd() {
        return this.rangeEnd;
    }

    public final boolean includes(java.util.Date date) {
        return includes(date, 3);
    }

    public final boolean includes(java.util.Date date, int i) {
        boolean zBefore;
        if ((i & 1) > 0) {
            zBefore = !this.rangeStart.after(date);
        } else {
            zBefore = this.rangeStart.before(date);
        }
        if ((i & 2) > 0) {
            if (zBefore && !this.rangeEnd.before(date)) {
                return true;
            }
        } else if (zBefore && this.rangeEnd.after(date)) {
            return true;
        }
        return false;
    }

    public final boolean before(DateRange dateRange) {
        return this.rangeEnd.before(dateRange.getRangeStart());
    }

    public final boolean after(DateRange dateRange) {
        return this.rangeStart.after(dateRange.getRangeEnd());
    }

    public final boolean intersects(DateRange dateRange) {
        if (!dateRange.includes(this.rangeStart) || dateRange.getRangeEnd().equals(this.rangeStart)) {
            return includes(dateRange.getRangeStart()) && !this.rangeEnd.equals(dateRange.getRangeStart());
        }
        return true;
    }

    public final boolean adjacent(DateRange dateRange) {
        return this.rangeStart.equals(dateRange.getRangeEnd()) || this.rangeEnd.equals(dateRange.getRangeStart());
    }

    public final boolean contains(DateRange dateRange) {
        return includes(dateRange.getRangeStart()) && includes(dateRange.getRangeEnd());
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        DateRange dateRange = (DateRange) obj;
        return new EqualsBuilder().append(this.rangeStart, dateRange.rangeStart).append(this.rangeEnd, dateRange.rangeEnd).isEquals();
    }

    public int hashCode() {
        return new HashCodeBuilder(17, 37).append(this.rangeStart).append(this.rangeEnd).toHashCode();
    }
}
