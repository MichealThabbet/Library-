package net.fortuna.ical4j.model.component;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Iterator;
import net.fortuna.ical4j.model.Component;
import net.fortuna.ical4j.model.ConstraintViolationException;
import net.fortuna.ical4j.model.Date;
import net.fortuna.ical4j.model.DateList;
import net.fortuna.ical4j.model.DateTime;
import net.fortuna.ical4j.model.Property;
import net.fortuna.ical4j.model.PropertyList;
import net.fortuna.ical4j.model.parameter.Value;
import net.fortuna.ical4j.model.property.DtStart;
import net.fortuna.ical4j.model.property.RDate;
import net.fortuna.ical4j.model.property.RRule;
import net.fortuna.ical4j.model.property.TzOffsetFrom;
import net.fortuna.ical4j.model.property.TzOffsetTo;
import net.fortuna.ical4j.util.Dates;
import net.fortuna.ical4j.util.TimeZones;
import net.fortuna.ical4j.validate.PropertyValidator;
import net.fortuna.ical4j.validate.ValidationException;
import org.slf4j.LoggerFactory;

/* loaded from: classes3.dex */
public abstract class Observance extends Component {
    public static final String DAYLIGHT = "DAYLIGHT";
    public static final String STANDARD = "STANDARD";
    private static final DateFormat UTC_FORMAT;
    private static final String UTC_PATTERN = "yyyyMMdd'T'HHmmss";
    private static final long serialVersionUID = 2523330383042085994L;
    private Date initialOnset;
    private Date onsetLimit;
    private DateTime[] onsetsDates;
    private long[] onsetsMillisec;

    static {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(UTC_PATTERN);
        UTC_FORMAT = simpleDateFormat;
        simpleDateFormat.setTimeZone(TimeZones.getUtcTimeZone());
        UTC_FORMAT.setLenient(false);
    }

    protected Observance(String str) {
        super(str);
        this.initialOnset = null;
    }

    protected Observance(String str, PropertyList<Property> propertyList) {
        super(str, propertyList);
        this.initialOnset = null;
    }

    @Override // net.fortuna.ical4j.model.Component
    public final void validate(boolean z) throws ValidationException {
        PropertyValidator.assertOne(Property.TZOFFSETFROM, getProperties());
        PropertyValidator.assertOne(Property.TZOFFSETTO, getProperties());
        PropertyValidator.assertOne(Property.DTSTART, getProperties());
        if (z) {
            validateProperties();
        }
    }

    public final Date getLatestOnset(Date date) {
        Date date2;
        if (this.initialOnset == null) {
            try {
                this.initialOnset = applyOffsetFrom(calculateOnset(((DtStart) getRequiredProperty(Property.DTSTART)).getDate()));
            } catch (ParseException | ConstraintViolationException e) {
                LoggerFactory.getLogger((Class<?>) Observance.class).error("Unexpected error calculating initial onset", e);
                return null;
            }
        }
        if (date.before(this.initialOnset)) {
            return null;
        }
        if (this.onsetsMillisec != null && ((date2 = this.onsetLimit) == null || date.before(date2))) {
            return getCachedOnset(date);
        }
        Date date3 = this.initialOnset;
        try {
            DateTime dateTimeCalculateOnset = calculateOnset(((DtStart) getProperty(Property.DTSTART)).getDate());
            DateList dateList = new DateList();
            dateList.setUtc(true);
            dateList.add(this.initialOnset);
            Iterator<T> it2 = getProperties(Property.RDATE).iterator();
            while (it2.hasNext()) {
                Iterator<Date> it3 = ((RDate) it2.next()).getDates().iterator();
                while (it3.hasNext()) {
                    try {
                        DateTime dateTimeApplyOffsetFrom = applyOffsetFrom(calculateOnset(it3.next()));
                        if (!dateTimeApplyOffsetFrom.after(date) && dateTimeApplyOffsetFrom.after(date3)) {
                            date3 = dateTimeApplyOffsetFrom;
                        }
                        dateList.add((Date) dateTimeApplyOffsetFrom);
                    } catch (ParseException e2) {
                        LoggerFactory.getLogger((Class<?>) Observance.class).error("Unexpected error calculating onset", (Throwable) e2);
                    }
                }
            }
            for (RRule rRule : getProperties(Property.RRULE)) {
                Calendar calendarInstance = Dates.getCalendarInstance(date);
                calendarInstance.setTime(date);
                calendarInstance.add(1, 10);
                this.onsetLimit = Dates.getInstance(calendarInstance.getTime(), Value.DATE_TIME);
                Iterator<Date> it4 = rRule.getRecur().getDates(dateTimeCalculateOnset, this.onsetLimit, Value.DATE_TIME).iterator();
                while (it4.hasNext()) {
                    DateTime dateTimeApplyOffsetFrom2 = applyOffsetFrom((DateTime) it4.next());
                    if (!dateTimeApplyOffsetFrom2.after(date) && dateTimeApplyOffsetFrom2.after(date3)) {
                        date3 = dateTimeApplyOffsetFrom2;
                    }
                    dateList.add((Date) dateTimeApplyOffsetFrom2);
                }
            }
            Collections.sort(dateList);
            long[] jArr = new long[dateList.size()];
            this.onsetsMillisec = jArr;
            this.onsetsDates = new DateTime[jArr.length];
            for (int i = 0; i < this.onsetsMillisec.length; i++) {
                DateTime dateTime = (DateTime) dateList.get(i);
                this.onsetsMillisec[i] = dateTime.getTime();
                this.onsetsDates[i] = dateTime;
            }
            return date3;
        } catch (ParseException e3) {
            LoggerFactory.getLogger((Class<?>) Observance.class).error("Unexpected error calculating initial onset", (Throwable) e3);
            return null;
        }
    }

    private DateTime getCachedOnset(Date date) {
        int iBinarySearch = Arrays.binarySearch(this.onsetsMillisec, date.getTime());
        if (iBinarySearch >= 0) {
            return this.onsetsDates[iBinarySearch];
        }
        return this.onsetsDates[((-iBinarySearch) - 1) - 1];
    }

    public final DtStart getStartDate() {
        return (DtStart) getProperty(Property.DTSTART);
    }

    public final TzOffsetFrom getOffsetFrom() {
        return (TzOffsetFrom) getProperty(Property.TZOFFSETFROM);
    }

    public final TzOffsetTo getOffsetTo() {
        return (TzOffsetTo) getProperty(Property.TZOFFSETTO);
    }

    private DateTime calculateOnset(Date date) throws ParseException {
        return calculateOnset(date.toString());
    }

    private DateTime calculateOnset(String str) throws ParseException {
        long time;
        synchronized (UTC_FORMAT) {
            time = UTC_FORMAT.parse(str).getTime();
        }
        DateTime dateTime = new DateTime(true);
        dateTime.setTime(time);
        return dateTime;
    }

    private DateTime applyOffsetFrom(DateTime dateTime) {
        DateTime dateTime2 = new DateTime(true);
        dateTime2.setTime(dateTime.getTime() - (getOffsetFrom().getOffset().getTotalSeconds() * 1000));
        return dateTime2;
    }
}
