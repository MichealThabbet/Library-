package org.ocpsoft.prettytime;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import org.ocpsoft.prettytime.impl.DurationImpl;
import org.ocpsoft.prettytime.impl.ResourcesTimeFormat;
import org.ocpsoft.prettytime.impl.ResourcesTimeUnit;
import org.ocpsoft.prettytime.units.Century;
import org.ocpsoft.prettytime.units.Day;
import org.ocpsoft.prettytime.units.Decade;
import org.ocpsoft.prettytime.units.Hour;
import org.ocpsoft.prettytime.units.JustNow;
import org.ocpsoft.prettytime.units.Millennium;
import org.ocpsoft.prettytime.units.Millisecond;
import org.ocpsoft.prettytime.units.Minute;
import org.ocpsoft.prettytime.units.Month;
import org.ocpsoft.prettytime.units.Second;
import org.ocpsoft.prettytime.units.TimeUnitComparator;
import org.ocpsoft.prettytime.units.Week;
import org.ocpsoft.prettytime.units.Year;

/* loaded from: classes3.dex */
public class PrettyTime {
    private volatile Locale locale;
    private List<TimeUnit> mCachedUnits;
    private volatile Date reference;
    private volatile Map<TimeUnit, TimeFormat> units;

    private long getSign(long j) {
        return 0 > j ? -1L : 1L;
    }

    public PrettyTime() {
        this.locale = Locale.getDefault();
        this.units = new LinkedHashMap();
        initTimeUnits();
    }

    public PrettyTime(Date date) {
        this();
        setReference(date);
    }

    public PrettyTime(Locale locale) {
        this.locale = Locale.getDefault();
        this.units = new LinkedHashMap();
        setLocale(locale);
        initTimeUnits();
    }

    public PrettyTime(Date date, Locale locale) {
        this(locale);
        setReference(date);
    }

    public Duration approximateDuration(Date date) {
        if (date == null) {
            throw new IllegalArgumentException("Date to approximate must not be null.");
        }
        Date date2 = this.reference;
        if (date2 == null) {
            date2 = new Date();
        }
        return calculateDuration(date.getTime() - date2.getTime());
    }

    private void initTimeUnits() {
        addUnit(new JustNow());
        addUnit(new Millisecond());
        addUnit(new Second());
        addUnit(new Minute());
        addUnit(new Hour());
        addUnit(new Day());
        addUnit(new Week());
        addUnit(new Month());
        addUnit(new Year());
        addUnit(new Decade());
        addUnit(new Century());
        addUnit(new Millennium());
    }

    private void addUnit(ResourcesTimeUnit resourcesTimeUnit) {
        registerUnit(resourcesTimeUnit, new ResourcesTimeFormat(resourcesTimeUnit));
    }

    private Duration calculateDuration(long j) {
        long jAbs = Math.abs(j);
        List<TimeUnit> units = getUnits();
        DurationImpl durationImpl = new DurationImpl();
        int i = 0;
        while (i < units.size()) {
            TimeUnit timeUnit = units.get(i);
            long jAbs2 = Math.abs(timeUnit.getMillisPerUnit());
            long jAbs3 = Math.abs(timeUnit.getMaxQuantity());
            boolean z = i == units.size() - 1;
            if (0 == jAbs3 && !z) {
                jAbs3 = units.get(i + 1).getMillisPerUnit() / timeUnit.getMillisPerUnit();
            }
            if (jAbs3 * jAbs2 > jAbs || z) {
                durationImpl.setUnit(timeUnit);
                if (jAbs2 > jAbs) {
                    durationImpl.setQuantity(getSign(j));
                    durationImpl.setDelta(0L);
                } else {
                    durationImpl.setQuantity(j / jAbs2);
                    durationImpl.setDelta(j - (durationImpl.getQuantity() * jAbs2));
                }
                return durationImpl;
            }
            i++;
        }
        return durationImpl;
    }

    public List<Duration> calculatePreciseDuration(Date date) {
        if (date == null) {
            throw new IllegalArgumentException("Date to calculate must not be null.");
        }
        if (this.reference == null) {
            this.reference = new Date();
        }
        ArrayList arrayList = new ArrayList();
        Duration durationCalculateDuration = calculateDuration(date.getTime() - this.reference.getTime());
        arrayList.add(durationCalculateDuration);
        while (0 != durationCalculateDuration.getDelta()) {
            durationCalculateDuration = calculateDuration(durationCalculateDuration.getDelta());
            if (arrayList.size() > 0 && ((Duration) arrayList.get(arrayList.size() - 1)).getUnit().equals(durationCalculateDuration.getUnit())) {
                break;
            }
            arrayList.add(durationCalculateDuration);
        }
        return arrayList;
    }

    public String format(Date date) {
        if (date == null) {
            throw new IllegalArgumentException("Date to format must not be null.");
        }
        return format(approximateDuration(date));
    }

    public String format(Calendar calendar) {
        if (calendar == null) {
            throw new IllegalArgumentException("Provided Calendar must not be null.");
        }
        return format(calendar.getTime());
    }

    public String formatUnrounded(Date date) {
        if (date == null) {
            throw new IllegalArgumentException("Date to format must not be null.");
        }
        return formatUnrounded(approximateDuration(date));
    }

    public String format(Duration duration) {
        if (duration == null) {
            throw new IllegalArgumentException("Duration to format must not be null.");
        }
        TimeFormat format = getFormat(duration.getUnit());
        return format.decorate(duration, format.format(duration));
    }

    public String formatUnrounded(Duration duration) {
        if (duration == null) {
            throw new IllegalArgumentException("Duration to format must not be null.");
        }
        TimeFormat format = getFormat(duration.getUnit());
        return format.decorateUnrounded(duration, format.formatUnrounded(duration));
    }

    public String format(List<Duration> list) {
        if (list == null) {
            throw new IllegalArgumentException("Duration list must not be null.");
        }
        TimeFormat format = null;
        if (list == null) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        Duration duration = null;
        for (int i = 0; i < list.size(); i++) {
            duration = list.get(i);
            format = getFormat(duration.getUnit());
            sb.append(format.formatUnrounded(duration));
            sb.append(" ");
        }
        return format.decorateUnrounded(duration, sb.toString());
    }

    public String formatApproximateDuration(Date date) {
        return formatDuration(approximateDuration(date));
    }

    public String formatDuration(Duration duration) {
        return getFormat(duration.getUnit()).format(duration);
    }

    public TimeFormat getFormat(TimeUnit timeUnit) {
        if (timeUnit == null) {
            throw new IllegalArgumentException("Time unit must not be null.");
        }
        if (this.units.get(timeUnit) != null) {
            return this.units.get(timeUnit);
        }
        return null;
    }

    public Date getReference() {
        return this.reference;
    }

    public PrettyTime setReference(Date date) {
        this.reference = date;
        return this;
    }

    public List<TimeUnit> getUnits() {
        if (this.mCachedUnits == null) {
            ArrayList arrayList = new ArrayList(this.units.keySet());
            Collections.sort(arrayList, new TimeUnitComparator());
            this.mCachedUnits = Collections.unmodifiableList(arrayList);
        }
        return this.mCachedUnits;
    }

    public <UNIT extends TimeUnit> UNIT getUnit(Class<UNIT> cls) {
        if (cls == null) {
            throw new IllegalArgumentException("Unit type to get must not be null.");
        }
        Iterator<TimeUnit> it2 = this.units.keySet().iterator();
        while (it2.hasNext()) {
            UNIT unit = (UNIT) it2.next();
            if (cls.isAssignableFrom(unit.getClass())) {
                return unit;
            }
        }
        return null;
    }

    public PrettyTime registerUnit(TimeUnit timeUnit, TimeFormat timeFormat) {
        if (timeUnit == null) {
            throw new IllegalArgumentException("Unit to register must not be null.");
        }
        if (timeFormat == null) {
            throw new IllegalArgumentException("Format to register must not be null.");
        }
        this.mCachedUnits = null;
        this.units.put(timeUnit, timeFormat);
        if (timeUnit instanceof LocaleAware) {
            ((LocaleAware) timeUnit).setLocale(this.locale);
        }
        if (timeFormat instanceof LocaleAware) {
            ((LocaleAware) timeFormat).setLocale(this.locale);
        }
        return this;
    }

    public <UNIT extends TimeUnit> TimeFormat removeUnit(Class<UNIT> cls) {
        if (cls == null) {
            throw new IllegalArgumentException("Unit type to remove must not be null.");
        }
        for (TimeUnit timeUnit : this.units.keySet()) {
            if (cls.isAssignableFrom(timeUnit.getClass())) {
                this.mCachedUnits = null;
                return this.units.remove(timeUnit);
            }
        }
        return null;
    }

    public TimeFormat removeUnit(TimeUnit timeUnit) {
        if (timeUnit == null) {
            throw new IllegalArgumentException("Unit to remove must not be null.");
        }
        this.mCachedUnits = null;
        return this.units.remove(timeUnit);
    }

    public Locale getLocale() {
        return this.locale;
    }

    public PrettyTime setLocale(Locale locale) {
        this.locale = locale;
        for (TimeUnit timeUnit : this.units.keySet()) {
            if (timeUnit instanceof LocaleAware) {
                ((LocaleAware) timeUnit).setLocale(locale);
            }
        }
        for (TimeFormat timeFormat : this.units.values()) {
            if (timeFormat instanceof LocaleAware) {
                ((LocaleAware) timeFormat).setLocale(locale);
            }
        }
        return this;
    }

    public String toString() {
        return "PrettyTime [reference=" + this.reference + ", locale=" + this.locale + "]";
    }

    public List<TimeUnit> clearUnits() {
        List<TimeUnit> units = getUnits();
        this.mCachedUnits = null;
        this.units.clear();
        return units;
    }
}
