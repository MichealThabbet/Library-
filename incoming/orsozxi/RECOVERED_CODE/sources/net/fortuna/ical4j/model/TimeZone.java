package net.fortuna.ical4j.model;

import java.util.Iterator;
import net.fortuna.ical4j.model.component.Daylight;
import net.fortuna.ical4j.model.component.Observance;
import net.fortuna.ical4j.model.component.Standard;
import net.fortuna.ical4j.model.component.VTimeZone;
import net.fortuna.ical4j.model.property.TzId;
import net.fortuna.ical4j.model.property.TzOffsetFrom;
import net.fortuna.ical4j.model.property.TzOffsetTo;

/* loaded from: classes3.dex */
public class TimeZone extends java.util.TimeZone {
    private static final long serialVersionUID = -5620979316746547234L;
    private final int rawOffset;
    private final VTimeZone vTimeZone;

    public TimeZone(VTimeZone vTimeZone) {
        this.vTimeZone = vTimeZone;
        setID(((TzId) vTimeZone.getProperty("TZID")).getValue());
        this.rawOffset = getRawOffset(vTimeZone);
    }

    @Override // java.util.TimeZone
    public final int getOffset(int i, int i2, int i3, int i4, int i5, int i6) {
        int i7 = i6 / 3600000;
        int i8 = i6 - (3600000 * i7);
        int i9 = i8 / 60000;
        int i10 = i8 - (60000 * i9);
        int i11 = i10 / 1000;
        java.util.Calendar calendar = java.util.Calendar.getInstance();
        calendar.clear();
        calendar.set(0, i);
        calendar.set(7, i5);
        calendar.set(i2, i3, i4, i7, i9, i11);
        calendar.set(14, i10 - (i11 * 1000));
        if (this.vTimeZone.getApplicableObservance(new DateTime(calendar.getTime())) != null) {
            return (int) (((TzOffsetTo) r10.getProperty(Property.TZOFFSETTO)).getOffset().getTotalSeconds() * 1000);
        }
        return 0;
    }

    @Override // java.util.TimeZone
    public int getOffset(long j) {
        Observance applicableObservance = this.vTimeZone.getApplicableObservance(new DateTime(j));
        if (applicableObservance == null) {
            return 0;
        }
        TzOffsetTo tzOffsetTo = (TzOffsetTo) applicableObservance.getProperty(Property.TZOFFSETTO);
        if (tzOffsetTo.getOffset().getTotalSeconds() * 1000 < getRawOffset()) {
            return getRawOffset();
        }
        return (int) (tzOffsetTo.getOffset().getTotalSeconds() * 1000);
    }

    @Override // java.util.TimeZone
    public final int getRawOffset() {
        return this.rawOffset;
    }

    @Override // java.util.TimeZone
    public final boolean inDaylightTime(java.util.Date date) {
        return this.vTimeZone.getApplicableObservance(new DateTime(date)) instanceof Daylight;
    }

    @Override // java.util.TimeZone
    public final void setRawOffset(int i) {
        throw new UnsupportedOperationException("Updates to the VTIMEZONE object must be performed directly");
    }

    @Override // java.util.TimeZone
    public final boolean useDaylightTime() {
        return !this.vTimeZone.getObservances().getComponents(Observance.DAYLIGHT).isEmpty();
    }

    public final VTimeZone getVTimeZone() {
        return this.vTimeZone;
    }

    /* JADX WARN: Multi-variable type inference failed */
    private static int getRawOffset(VTimeZone vTimeZone) {
        Observance observance;
        TzOffsetTo tzOffsetTo;
        int totalSeconds;
        ComponentList components = vTimeZone.getObservances().getComponents(Observance.STANDARD);
        if (components.isEmpty()) {
            components = vTimeZone.getObservances().getComponents(Observance.DAYLIGHT);
            if (components.isEmpty()) {
                return 0;
            }
        }
        if (components.size() > 1) {
            DateTime dateTime = new DateTime();
            Iterator<T> it2 = components.iterator();
            observance = null;
            Date date = null;
            while (it2.hasNext()) {
                Observance observance2 = (Observance) it2.next();
                Date latestOnset = observance2.getLatestOnset(dateTime);
                if (latestOnset != null && (date == null || latestOnset.after(date))) {
                    observance = observance2;
                    date = latestOnset;
                }
            }
        } else {
            observance = (Observance) components.get(0);
        }
        if (observance instanceof Daylight) {
            TzOffsetFrom tzOffsetFrom = (TzOffsetFrom) observance.getProperty(Property.TZOFFSETFROM);
            if (tzOffsetFrom != null) {
                totalSeconds = tzOffsetFrom.getOffset().getTotalSeconds();
                return (int) (totalSeconds * 1000);
            }
            return 0;
        }
        if ((observance instanceof Standard) && (tzOffsetTo = (TzOffsetTo) observance.getProperty(Property.TZOFFSETTO)) != null) {
            totalSeconds = tzOffsetTo.getOffset().getTotalSeconds();
            return (int) (totalSeconds * 1000);
        }
        return 0;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        TimeZone timeZone = (TimeZone) obj;
        if (this.rawOffset == timeZone.rawOffset) {
            VTimeZone vTimeZone = this.vTimeZone;
            if (vTimeZone != null) {
                if (vTimeZone.equals(timeZone.vTimeZone)) {
                    return true;
                }
            } else if (timeZone.vTimeZone == null) {
                return true;
            }
        }
        return false;
    }

    public int hashCode() {
        VTimeZone vTimeZone = this.vTimeZone;
        return ((vTimeZone != null ? vTimeZone.hashCode() : 0) * 31) + this.rawOffset;
    }
}
