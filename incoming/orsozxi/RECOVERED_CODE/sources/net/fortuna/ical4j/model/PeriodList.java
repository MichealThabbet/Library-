package net.fortuna.ical4j.model;

import it.orsozoxi.android.orsonotes.utils.ConstantsBase;
import java.io.Serializable;
import java.text.ParseException;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.TreeSet;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

/* loaded from: classes3.dex */
public class PeriodList implements Set<Period>, Serializable {
    private static final long serialVersionUID = -2317587285790834492L;
    private final Set<Period> periods;
    private TimeZone timezone;
    private final boolean unmodifiable;
    private boolean utc;

    public PeriodList() {
        this(true);
    }

    public PeriodList(boolean z) {
        this(z, false);
    }

    public PeriodList(boolean z, boolean z2) {
        this.utc = z;
        this.unmodifiable = z2;
        if (z2) {
            this.periods = Collections.emptySet();
        } else {
            this.periods = new TreeSet();
        }
    }

    public PeriodList(String str) throws ParseException {
        this();
        StringTokenizer stringTokenizer = new StringTokenizer(str, ConstantsBase.PROPERTIES_PARAMS_SEPARATOR);
        while (stringTokenizer.hasMoreTokens()) {
            add(new Period(stringTokenizer.nextToken()));
        }
    }

    public final String toString() {
        return (String) stream().map(new Function() { // from class: net.fortuna.ical4j.model.-$$Lambda$IweeGVLKezB0pEjIYxtncy0P9NY
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return ((Period) obj).toString();
            }
        }).collect(Collectors.joining(ConstantsBase.PROPERTIES_PARAMS_SEPARATOR));
    }

    @Override // java.util.Set, java.util.Collection
    public final boolean add(Period period) {
        if (isUtc()) {
            period.setUtc(true);
        } else {
            period.setTimeZone(this.timezone);
        }
        return this.periods.add(period);
    }

    @Override // java.util.Set, java.util.Collection
    public final boolean remove(Object obj) {
        return this.periods.remove(obj);
    }

    public final PeriodList normalise() {
        Period periodAdd;
        PeriodList periodList = new PeriodList(isUtc());
        TimeZone timeZone = this.timezone;
        if (timeZone != null) {
            periodList.setTimeZone(timeZone);
        }
        boolean z = false;
        Iterator<Period> it2 = iterator();
        Period period = null;
        while (it2.hasNext()) {
            Period next = it2.next();
            if (!next.isEmpty()) {
                if (period != null) {
                    if (!period.contains(next)) {
                        if (period.intersects(next)) {
                            periodAdd = period.add(next);
                        } else if (period.adjacent(next)) {
                            periodAdd = period.add(next);
                        } else {
                            periodList.add(period);
                        }
                        period = periodAdd;
                    }
                }
                period = next;
            }
            z = true;
        }
        if (period != null) {
            periodList.add(period);
        }
        return z ? periodList : this;
    }

    public final PeriodList add(PeriodList periodList) {
        if (periodList == null) {
            return this;
        }
        PeriodList periodList2 = new PeriodList();
        periodList2.addAll(this);
        periodList2.addAll(periodList);
        return periodList2.normalise();
    }

    public final PeriodList subtract(PeriodList periodList) {
        if (periodList == null || periodList.isEmpty()) {
            return this;
        }
        PeriodList periodList2 = new PeriodList();
        Iterator<Period> it2 = periodList.iterator();
        PeriodList periodList3 = this;
        while (it2.hasNext()) {
            Period next = it2.next();
            Iterator<Period> it3 = periodList3.iterator();
            while (it3.hasNext()) {
                periodList2.addAll(it3.next().subtract(next));
            }
            periodList3 = periodList2;
            periodList2 = new PeriodList();
        }
        return periodList3;
    }

    public final boolean isUtc() {
        return this.utc;
    }

    public boolean isUnmodifiable() {
        return this.unmodifiable;
    }

    public final void setUtc(boolean z) {
        Iterator<Period> it2 = iterator();
        while (it2.hasNext()) {
            it2.next().setUtc(z);
        }
        this.timezone = null;
        this.utc = z;
    }

    public final void setTimeZone(TimeZone timeZone) {
        Iterator<Period> it2 = iterator();
        while (it2.hasNext()) {
            it2.next().setTimeZone(timeZone);
        }
        this.timezone = timeZone;
        this.utc = false;
    }

    public final TimeZone getTimeZone() {
        return this.timezone;
    }

    @Override // java.util.Set, java.util.Collection
    public boolean addAll(Collection<? extends Period> collection) {
        Iterator<? extends Period> it2 = collection.iterator();
        while (it2.hasNext()) {
            add(it2.next());
        }
        return true;
    }

    @Override // java.util.Set, java.util.Collection
    public void clear() {
        this.periods.clear();
    }

    @Override // java.util.Set, java.util.Collection
    public boolean contains(Object obj) {
        return this.periods.contains(obj);
    }

    @Override // java.util.Set, java.util.Collection
    public boolean containsAll(Collection<?> collection) {
        return this.periods.containsAll(collection);
    }

    @Override // java.util.Set, java.util.Collection
    public boolean isEmpty() {
        return this.periods.isEmpty();
    }

    @Override // java.util.Set, java.util.Collection, java.lang.Iterable
    public Iterator<Period> iterator() {
        return this.periods.iterator();
    }

    @Override // java.util.Set, java.util.Collection
    public boolean removeAll(Collection<?> collection) {
        return this.periods.removeAll(collection);
    }

    @Override // java.util.Set, java.util.Collection
    public boolean retainAll(Collection<?> collection) {
        return this.periods.retainAll(collection);
    }

    @Override // java.util.Set, java.util.Collection
    public int size() {
        return this.periods.size();
    }

    @Override // java.util.Set, java.util.Collection
    public Object[] toArray() {
        return this.periods.toArray();
    }

    @Override // java.util.Set, java.util.Collection
    public <T> T[] toArray(T[] tArr) {
        return (T[]) this.periods.toArray(tArr);
    }

    @Override // java.util.Set, java.util.Collection
    public boolean equals(Object obj) {
        if (!(obj instanceof PeriodList)) {
            return false;
        }
        PeriodList periodList = (PeriodList) obj;
        return new EqualsBuilder().append(this.periods, periodList.periods).append(this.timezone, periodList.timezone).append(this.utc, periodList.utc).isEquals();
    }

    @Override // java.util.Set, java.util.Collection
    public int hashCode() {
        return new HashCodeBuilder().append(this.periods).append(this.timezone).append(this.utc).toHashCode();
    }
}
