package net.fortuna.ical4j.model;

import it.piapostolos.android.pinotes.utils.ConstantsBase;
import java.io.Serializable;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.StringTokenizer;
import java.util.function.Function;
import java.util.stream.Collectors;
import net.fortuna.ical4j.model.parameter.Value;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

/* loaded from: classes3.dex */
public class DateList implements List<Date>, Serializable, Iterable<Date> {
    private static final long serialVersionUID = -3700862452550012357L;
    private final List<Date> dates;
    private TimeZone timeZone;
    private final Value type;
    private boolean utc;

    public DateList() {
        this(false);
    }

    public DateList(boolean z) {
        this.type = Value.DATE_TIME;
        if (z) {
            this.dates = Collections.emptyList();
        } else {
            this.dates = new ArrayList();
        }
    }

    public DateList(Value value) {
        this(value, (TimeZone) null);
    }

    public DateList(Value value, TimeZone timeZone) {
        if (value != null) {
            this.type = value;
        } else {
            this.type = Value.DATE_TIME;
        }
        this.timeZone = timeZone;
        this.dates = new ArrayList();
    }

    public DateList(String str, Value value) throws ParseException {
        this(str, value, null);
    }

    public DateList(String str, Value value, TimeZone timeZone) throws ParseException {
        this(value, timeZone);
        StringTokenizer stringTokenizer = new StringTokenizer(str, ConstantsBase.PROPERTIES_PARAMS_SEPARATOR);
        while (stringTokenizer.hasMoreTokens()) {
            if (Value.DATE.equals(this.type)) {
                add(new Date(stringTokenizer.nextToken()));
            } else {
                add((Date) new DateTime(stringTokenizer.nextToken(), timeZone));
            }
        }
    }

    public DateList(DateList dateList, Value value) {
        if (!Value.DATE.equals(value) && !Value.DATE_TIME.equals(value)) {
            throw new IllegalArgumentException("Type must be either DATE or DATE-TIME");
        }
        this.type = value;
        this.dates = new ArrayList();
        if (Value.DATE.equals(value)) {
            Iterator<Date> it2 = dateList.iterator();
            while (it2.hasNext()) {
                add(new Date(it2.next()));
            }
        } else {
            Iterator<Date> it3 = dateList.iterator();
            while (it3.hasNext()) {
                add((Date) new DateTime(it3.next()));
            }
        }
    }

    public final String toString() {
        return (String) stream().map(new Function() { // from class: net.fortuna.ical4j.model.-$$Lambda$XaJfRexOewHg8XqneTgcivqnRa8
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return ((Date) obj).toString();
            }
        }).collect(Collectors.joining(ConstantsBase.PROPERTIES_PARAMS_SEPARATOR));
    }

    @Override // java.util.List, java.util.Collection
    public final boolean add(Date date) {
        if (!isUtc() && getTimeZone() == null && (date instanceof DateTime)) {
            DateTime dateTime = (DateTime) date;
            if (dateTime.isUtc()) {
                setUtc(true);
            } else {
                setTimeZone(dateTime.getTimeZone());
            }
        }
        if (date instanceof DateTime) {
            DateTime dateTime2 = (DateTime) date;
            if (isUtc()) {
                dateTime2.setUtc(true);
            } else {
                dateTime2.setTimeZone(getTimeZone());
            }
        } else if (!Value.DATE.equals(getType())) {
            DateTime dateTime3 = new DateTime(date);
            dateTime3.setTimeZone(getTimeZone());
            return this.dates.add(dateTime3);
        }
        return this.dates.add(date);
    }

    public final boolean remove(Date date) {
        return remove((Object) date);
    }

    public final Value getType() {
        return this.type;
    }

    public final boolean isUtc() {
        return this.utc;
    }

    public final void setUtc(boolean z) {
        if (!Value.DATE.equals(this.type)) {
            Iterator<Date> it2 = iterator();
            while (it2.hasNext()) {
                ((DateTime) it2.next()).setUtc(z);
            }
        }
        this.timeZone = null;
        this.utc = z;
    }

    public final void setTimeZone(TimeZone timeZone) {
        if (!Value.DATE.equals(this.type)) {
            Iterator<Date> it2 = iterator();
            while (it2.hasNext()) {
                ((DateTime) it2.next()).setTimeZone(timeZone);
            }
        }
        this.timeZone = timeZone;
        this.utc = false;
    }

    public final TimeZone getTimeZone() {
        return this.timeZone;
    }

    @Override // java.util.List
    public final void add(int i, Date date) {
        this.dates.add(i, date);
    }

    @Override // java.util.List, java.util.Collection
    public final boolean addAll(Collection<? extends Date> collection) {
        return this.dates.addAll(collection);
    }

    @Override // java.util.List
    public final boolean addAll(int i, Collection<? extends Date> collection) {
        return this.dates.addAll(i, collection);
    }

    @Override // java.util.List, java.util.Collection
    public final void clear() {
        this.dates.clear();
    }

    @Override // java.util.List, java.util.Collection
    public final boolean contains(Object obj) {
        return this.dates.contains(obj);
    }

    @Override // java.util.List, java.util.Collection
    public final boolean containsAll(Collection<?> collection) {
        return this.dates.containsAll(collection);
    }

    @Override // java.util.List
    public final Date get(int i) {
        return this.dates.get(i);
    }

    @Override // java.util.List
    public final int indexOf(Object obj) {
        return this.dates.indexOf(obj);
    }

    @Override // java.util.List, java.util.Collection
    public final boolean isEmpty() {
        return this.dates.isEmpty();
    }

    @Override // java.util.List, java.util.Collection, java.lang.Iterable
    public final Iterator<Date> iterator() {
        return this.dates.iterator();
    }

    @Override // java.util.List
    public final int lastIndexOf(Object obj) {
        return this.dates.lastIndexOf(obj);
    }

    @Override // java.util.List
    public final ListIterator<Date> listIterator() {
        return this.dates.listIterator();
    }

    @Override // java.util.List
    public final ListIterator<Date> listIterator(int i) {
        return this.dates.listIterator(i);
    }

    @Override // java.util.List
    public final Date remove(int i) {
        return this.dates.remove(i);
    }

    @Override // java.util.List, java.util.Collection
    public final boolean remove(Object obj) {
        return this.dates.remove(obj);
    }

    @Override // java.util.List, java.util.Collection
    public final boolean removeAll(Collection<?> collection) {
        return this.dates.removeAll(collection);
    }

    @Override // java.util.List, java.util.Collection
    public final boolean retainAll(Collection<?> collection) {
        return this.dates.retainAll(collection);
    }

    @Override // java.util.List
    public final Date set(int i, Date date) {
        return this.dates.set(i, date);
    }

    @Override // java.util.List, java.util.Collection
    public final int size() {
        return this.dates.size();
    }

    @Override // java.util.List
    public final List<Date> subList(int i, int i2) {
        return this.dates.subList(i, i2);
    }

    @Override // java.util.List, java.util.Collection
    public final Object[] toArray() {
        return this.dates.toArray();
    }

    @Override // java.util.List, java.util.Collection
    public final <T> T[] toArray(T[] tArr) {
        return (T[]) this.dates.toArray(tArr);
    }

    @Override // java.util.List, java.util.Collection
    public final boolean equals(Object obj) {
        if (!getClass().isAssignableFrom(obj.getClass())) {
            return false;
        }
        DateList dateList = (DateList) obj;
        EqualsBuilder equalsBuilderAppend = new EqualsBuilder().append(this.dates, dateList.dates).append(this.type, dateList.type).append(this.timeZone, dateList.timeZone);
        boolean z = this.utc;
        return equalsBuilderAppend.append(z, z).isEquals();
    }

    @Override // java.util.List, java.util.Collection
    public final int hashCode() {
        return new HashCodeBuilder().append(this.dates).append(this.type).append(this.timeZone).append(this.utc).toHashCode();
    }
}
