package net.fortuna.ical4j.model.component;

import java.io.IOException;
import java.net.URISyntaxException;
import java.text.ParseException;
import java.time.temporal.TemporalAmount;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.function.Consumer;
import net.fortuna.ical4j.model.Component;
import net.fortuna.ical4j.model.ComponentFactory;
import net.fortuna.ical4j.model.ComponentList;
import net.fortuna.ical4j.model.Content;
import net.fortuna.ical4j.model.Date;
import net.fortuna.ical4j.model.DateTime;
import net.fortuna.ical4j.model.Parameter;
import net.fortuna.ical4j.model.Period;
import net.fortuna.ical4j.model.PeriodList;
import net.fortuna.ical4j.model.Property;
import net.fortuna.ical4j.model.PropertyList;
import net.fortuna.ical4j.model.parameter.Value;
import net.fortuna.ical4j.model.property.Clazz;
import net.fortuna.ical4j.model.property.Created;
import net.fortuna.ical4j.model.property.Description;
import net.fortuna.ical4j.model.property.DtEnd;
import net.fortuna.ical4j.model.property.DtStamp;
import net.fortuna.ical4j.model.property.DtStart;
import net.fortuna.ical4j.model.property.Duration;
import net.fortuna.ical4j.model.property.Geo;
import net.fortuna.ical4j.model.property.LastModified;
import net.fortuna.ical4j.model.property.Location;
import net.fortuna.ical4j.model.property.Method;
import net.fortuna.ical4j.model.property.Organizer;
import net.fortuna.ical4j.model.property.Priority;
import net.fortuna.ical4j.model.property.RecurrenceId;
import net.fortuna.ical4j.model.property.Sequence;
import net.fortuna.ical4j.model.property.Status;
import net.fortuna.ical4j.model.property.Summary;
import net.fortuna.ical4j.model.property.Transp;
import net.fortuna.ical4j.model.property.Uid;
import net.fortuna.ical4j.model.property.Url;
import net.fortuna.ical4j.util.CompatibilityHints;
import net.fortuna.ical4j.util.Dates;
import net.fortuna.ical4j.validate.PropertyValidator;
import net.fortuna.ical4j.validate.ValidationException;
import net.fortuna.ical4j.validate.ValidationRule;
import net.fortuna.ical4j.validate.Validator;
import net.fortuna.ical4j.validate.component.VEventValidator;
import org.apache.commons.lang3.builder.HashCodeBuilder;

/* loaded from: classes3.dex */
public class VEvent extends CalendarComponent {
    private static final long serialVersionUID = 2547948989200697335L;
    private ComponentList<VAlarm> alarms;
    private final Map<Method, Validator> methodValidators;

    public VEvent() {
        this(true);
    }

    public VEvent(boolean z) {
        super(Component.VEVENT);
        HashMap map = new HashMap();
        this.methodValidators = map;
        map.put(Method.ADD, new VEventValidator(Arrays.asList(new ValidationRule(ValidationRule.ValidationType.One, Property.DTSTAMP, Property.DTSTART, Property.ORGANIZER, Property.SEQUENCE, Property.SUMMARY, Property.UID), new ValidationRule(ValidationRule.ValidationType.OneOrLess, Property.CATEGORIES, Property.CLASS, Property.CREATED, "DESCRIPTION", Property.DTEND, Property.DURATION, Property.GEO, Property.LAST_MODIFIED, "LOCATION", Property.PRIORITY, Property.RESOURCES, Property.STATUS, Property.TRANSP, Property.URL), new ValidationRule(ValidationRule.ValidationType.None, Property.RECURRENCE_ID, Property.REQUEST_STATUS))));
        this.methodValidators.put(Method.CANCEL, new VEventValidator(Arrays.asList(new ValidationRule(ValidationRule.ValidationType.One, Property.DTSTAMP, Property.DTSTART, Property.ORGANIZER, Property.SEQUENCE, Property.UID), new ValidationRule(ValidationRule.ValidationType.OneOrLess, Property.CATEGORIES, Property.CLASS, Property.CREATED, "DESCRIPTION", Property.DTEND, Property.DTSTART, Property.DURATION, Property.GEO, Property.LAST_MODIFIED, "LOCATION", Property.PRIORITY, Property.RECURRENCE_ID, Property.RESOURCES, Property.STATUS, Property.SUMMARY, Property.TRANSP, Property.URL), new ValidationRule(ValidationRule.ValidationType.None, Property.REQUEST_STATUS)), false));
        this.methodValidators.put(Method.COUNTER, new VEventValidator(Arrays.asList(new ValidationRule(ValidationRule.ValidationType.One, Property.DTSTAMP, Property.DTSTART, Property.SEQUENCE, Property.SUMMARY, Property.UID), new ValidationRule(ValidationRule.ValidationType.One, true, Property.ORGANIZER), new ValidationRule(ValidationRule.ValidationType.OneOrLess, Property.CATEGORIES, Property.CLASS, Property.CREATED, "DESCRIPTION", Property.DTEND, Property.DURATION, Property.GEO, Property.LAST_MODIFIED, "LOCATION", Property.PRIORITY, Property.RECURRENCE_ID, Property.RESOURCES, Property.STATUS, Property.TRANSP, Property.URL))));
        this.methodValidators.put(Method.DECLINE_COUNTER, new VEventValidator(Arrays.asList(new ValidationRule(ValidationRule.ValidationType.One, Property.DTSTAMP, Property.ORGANIZER, Property.UID), new ValidationRule(ValidationRule.ValidationType.OneOrLess, Property.RECURRENCE_ID, Property.SEQUENCE), new ValidationRule(ValidationRule.ValidationType.None, Property.ATTACH, Property.ATTENDEE, Property.CATEGORIES, Property.CLASS, Property.CONTACT, Property.CREATED, "DESCRIPTION", Property.DTEND, Property.DTSTART, Property.DURATION, Property.EXDATE, Property.EXRULE, Property.GEO, Property.LAST_MODIFIED, "LOCATION", Property.PRIORITY, Property.RDATE, Property.RELATED_TO, Property.RESOURCES, Property.RRULE, Property.STATUS, Property.SUMMARY, Property.TRANSP, Property.URL)), false));
        this.methodValidators.put(Method.PUBLISH, new VEventValidator(Arrays.asList(new ValidationRule(ValidationRule.ValidationType.One, Property.DTSTART, Property.UID), new ValidationRule(ValidationRule.ValidationType.One, true, Property.DTSTAMP, Property.ORGANIZER, Property.SUMMARY), new ValidationRule(ValidationRule.ValidationType.OneOrLess, Property.RECURRENCE_ID, Property.SEQUENCE, Property.CATEGORIES, Property.CLASS, Property.CREATED, "DESCRIPTION", Property.DTEND, Property.DURATION, Property.GEO, Property.LAST_MODIFIED, "LOCATION", Property.PRIORITY, Property.RESOURCES, Property.STATUS, Property.TRANSP, Property.URL), new ValidationRule(ValidationRule.ValidationType.None, true, Property.ATTENDEE), new ValidationRule(ValidationRule.ValidationType.None, Property.REQUEST_STATUS))));
        this.methodValidators.put(Method.REFRESH, new VEventValidator(Arrays.asList(new ValidationRule(ValidationRule.ValidationType.One, Property.ATTENDEE, Property.DTSTAMP, Property.ORGANIZER, Property.UID), new ValidationRule(ValidationRule.ValidationType.OneOrLess, Property.RECURRENCE_ID), new ValidationRule(ValidationRule.ValidationType.None, Property.ATTACH, Property.CATEGORIES, Property.CLASS, Property.CONTACT, Property.CREATED, "DESCRIPTION", Property.DTEND, Property.DTSTART, Property.DURATION, Property.EXDATE, Property.EXRULE, Property.GEO, Property.LAST_MODIFIED, "LOCATION", Property.PRIORITY, Property.RDATE, Property.RELATED_TO, Property.REQUEST_STATUS, Property.RESOURCES, Property.RRULE, Property.SEQUENCE, Property.STATUS, Property.SUMMARY, Property.TRANSP, Property.URL)), false));
        this.methodValidators.put(Method.REPLY, new VEventValidator(Arrays.asList(new ValidationRule(ValidationRule.ValidationType.One, Property.ATTENDEE, Property.DTSTAMP, Property.ORGANIZER, Property.UID), new ValidationRule(ValidationRule.ValidationType.OneOrLess, Property.RECURRENCE_ID, Property.SEQUENCE, Property.CATEGORIES, Property.CLASS, Property.CREATED, "DESCRIPTION", Property.DTEND, Property.DTSTART, Property.DURATION, Property.GEO, Property.LAST_MODIFIED, "LOCATION", Property.PRIORITY, Property.RESOURCES, Property.STATUS, Property.SUMMARY, Property.TRANSP, Property.URL)), CompatibilityHints.isHintEnabled(CompatibilityHints.KEY_RELAXED_VALIDATION)));
        this.methodValidators.put(Method.REQUEST, new VEventValidator(Arrays.asList(new ValidationRule(ValidationRule.ValidationType.OneOrMore, true, Property.ATTENDEE), new ValidationRule(ValidationRule.ValidationType.One, Property.DTSTAMP, Property.DTSTART, Property.ORGANIZER, Property.SUMMARY, Property.UID), new ValidationRule(ValidationRule.ValidationType.OneOrLess, Property.SEQUENCE, Property.CATEGORIES, Property.CLASS, Property.CREATED, "DESCRIPTION", Property.DTEND, Property.DURATION, Property.GEO, Property.LAST_MODIFIED, "LOCATION", Property.PRIORITY, Property.RECURRENCE_ID, Property.RESOURCES, Property.STATUS, Property.TRANSP, Property.URL))));
        this.alarms = new ComponentList<>();
        if (z) {
            getProperties().add((PropertyList<Property>) new DtStamp());
        }
    }

    public VEvent(PropertyList propertyList) {
        super(Component.VEVENT, propertyList);
        HashMap map = new HashMap();
        this.methodValidators = map;
        map.put(Method.ADD, new VEventValidator(Arrays.asList(new ValidationRule(ValidationRule.ValidationType.One, Property.DTSTAMP, Property.DTSTART, Property.ORGANIZER, Property.SEQUENCE, Property.SUMMARY, Property.UID), new ValidationRule(ValidationRule.ValidationType.OneOrLess, Property.CATEGORIES, Property.CLASS, Property.CREATED, "DESCRIPTION", Property.DTEND, Property.DURATION, Property.GEO, Property.LAST_MODIFIED, "LOCATION", Property.PRIORITY, Property.RESOURCES, Property.STATUS, Property.TRANSP, Property.URL), new ValidationRule(ValidationRule.ValidationType.None, Property.RECURRENCE_ID, Property.REQUEST_STATUS))));
        this.methodValidators.put(Method.CANCEL, new VEventValidator(Arrays.asList(new ValidationRule(ValidationRule.ValidationType.One, Property.DTSTAMP, Property.DTSTART, Property.ORGANIZER, Property.SEQUENCE, Property.UID), new ValidationRule(ValidationRule.ValidationType.OneOrLess, Property.CATEGORIES, Property.CLASS, Property.CREATED, "DESCRIPTION", Property.DTEND, Property.DTSTART, Property.DURATION, Property.GEO, Property.LAST_MODIFIED, "LOCATION", Property.PRIORITY, Property.RECURRENCE_ID, Property.RESOURCES, Property.STATUS, Property.SUMMARY, Property.TRANSP, Property.URL), new ValidationRule(ValidationRule.ValidationType.None, Property.REQUEST_STATUS)), false));
        this.methodValidators.put(Method.COUNTER, new VEventValidator(Arrays.asList(new ValidationRule(ValidationRule.ValidationType.One, Property.DTSTAMP, Property.DTSTART, Property.SEQUENCE, Property.SUMMARY, Property.UID), new ValidationRule(ValidationRule.ValidationType.One, true, Property.ORGANIZER), new ValidationRule(ValidationRule.ValidationType.OneOrLess, Property.CATEGORIES, Property.CLASS, Property.CREATED, "DESCRIPTION", Property.DTEND, Property.DURATION, Property.GEO, Property.LAST_MODIFIED, "LOCATION", Property.PRIORITY, Property.RECURRENCE_ID, Property.RESOURCES, Property.STATUS, Property.TRANSP, Property.URL))));
        this.methodValidators.put(Method.DECLINE_COUNTER, new VEventValidator(Arrays.asList(new ValidationRule(ValidationRule.ValidationType.One, Property.DTSTAMP, Property.ORGANIZER, Property.UID), new ValidationRule(ValidationRule.ValidationType.OneOrLess, Property.RECURRENCE_ID, Property.SEQUENCE), new ValidationRule(ValidationRule.ValidationType.None, Property.ATTACH, Property.ATTENDEE, Property.CATEGORIES, Property.CLASS, Property.CONTACT, Property.CREATED, "DESCRIPTION", Property.DTEND, Property.DTSTART, Property.DURATION, Property.EXDATE, Property.EXRULE, Property.GEO, Property.LAST_MODIFIED, "LOCATION", Property.PRIORITY, Property.RDATE, Property.RELATED_TO, Property.RESOURCES, Property.RRULE, Property.STATUS, Property.SUMMARY, Property.TRANSP, Property.URL)), false));
        this.methodValidators.put(Method.PUBLISH, new VEventValidator(Arrays.asList(new ValidationRule(ValidationRule.ValidationType.One, Property.DTSTART, Property.UID), new ValidationRule(ValidationRule.ValidationType.One, true, Property.DTSTAMP, Property.ORGANIZER, Property.SUMMARY), new ValidationRule(ValidationRule.ValidationType.OneOrLess, Property.RECURRENCE_ID, Property.SEQUENCE, Property.CATEGORIES, Property.CLASS, Property.CREATED, "DESCRIPTION", Property.DTEND, Property.DURATION, Property.GEO, Property.LAST_MODIFIED, "LOCATION", Property.PRIORITY, Property.RESOURCES, Property.STATUS, Property.TRANSP, Property.URL), new ValidationRule(ValidationRule.ValidationType.None, true, Property.ATTENDEE), new ValidationRule(ValidationRule.ValidationType.None, Property.REQUEST_STATUS))));
        this.methodValidators.put(Method.REFRESH, new VEventValidator(Arrays.asList(new ValidationRule(ValidationRule.ValidationType.One, Property.ATTENDEE, Property.DTSTAMP, Property.ORGANIZER, Property.UID), new ValidationRule(ValidationRule.ValidationType.OneOrLess, Property.RECURRENCE_ID), new ValidationRule(ValidationRule.ValidationType.None, Property.ATTACH, Property.CATEGORIES, Property.CLASS, Property.CONTACT, Property.CREATED, "DESCRIPTION", Property.DTEND, Property.DTSTART, Property.DURATION, Property.EXDATE, Property.EXRULE, Property.GEO, Property.LAST_MODIFIED, "LOCATION", Property.PRIORITY, Property.RDATE, Property.RELATED_TO, Property.REQUEST_STATUS, Property.RESOURCES, Property.RRULE, Property.SEQUENCE, Property.STATUS, Property.SUMMARY, Property.TRANSP, Property.URL)), false));
        this.methodValidators.put(Method.REPLY, new VEventValidator(Arrays.asList(new ValidationRule(ValidationRule.ValidationType.One, Property.ATTENDEE, Property.DTSTAMP, Property.ORGANIZER, Property.UID), new ValidationRule(ValidationRule.ValidationType.OneOrLess, Property.RECURRENCE_ID, Property.SEQUENCE, Property.CATEGORIES, Property.CLASS, Property.CREATED, "DESCRIPTION", Property.DTEND, Property.DTSTART, Property.DURATION, Property.GEO, Property.LAST_MODIFIED, "LOCATION", Property.PRIORITY, Property.RESOURCES, Property.STATUS, Property.SUMMARY, Property.TRANSP, Property.URL)), CompatibilityHints.isHintEnabled(CompatibilityHints.KEY_RELAXED_VALIDATION)));
        this.methodValidators.put(Method.REQUEST, new VEventValidator(Arrays.asList(new ValidationRule(ValidationRule.ValidationType.OneOrMore, true, Property.ATTENDEE), new ValidationRule(ValidationRule.ValidationType.One, Property.DTSTAMP, Property.DTSTART, Property.ORGANIZER, Property.SUMMARY, Property.UID), new ValidationRule(ValidationRule.ValidationType.OneOrLess, Property.SEQUENCE, Property.CATEGORIES, Property.CLASS, Property.CREATED, "DESCRIPTION", Property.DTEND, Property.DURATION, Property.GEO, Property.LAST_MODIFIED, "LOCATION", Property.PRIORITY, Property.RECURRENCE_ID, Property.RESOURCES, Property.STATUS, Property.TRANSP, Property.URL))));
        this.alarms = new ComponentList<>();
    }

    public VEvent(PropertyList propertyList, ComponentList<VAlarm> componentList) {
        super(Component.VEVENT, propertyList);
        HashMap map = new HashMap();
        this.methodValidators = map;
        map.put(Method.ADD, new VEventValidator(Arrays.asList(new ValidationRule(ValidationRule.ValidationType.One, Property.DTSTAMP, Property.DTSTART, Property.ORGANIZER, Property.SEQUENCE, Property.SUMMARY, Property.UID), new ValidationRule(ValidationRule.ValidationType.OneOrLess, Property.CATEGORIES, Property.CLASS, Property.CREATED, "DESCRIPTION", Property.DTEND, Property.DURATION, Property.GEO, Property.LAST_MODIFIED, "LOCATION", Property.PRIORITY, Property.RESOURCES, Property.STATUS, Property.TRANSP, Property.URL), new ValidationRule(ValidationRule.ValidationType.None, Property.RECURRENCE_ID, Property.REQUEST_STATUS))));
        this.methodValidators.put(Method.CANCEL, new VEventValidator(Arrays.asList(new ValidationRule(ValidationRule.ValidationType.One, Property.DTSTAMP, Property.DTSTART, Property.ORGANIZER, Property.SEQUENCE, Property.UID), new ValidationRule(ValidationRule.ValidationType.OneOrLess, Property.CATEGORIES, Property.CLASS, Property.CREATED, "DESCRIPTION", Property.DTEND, Property.DTSTART, Property.DURATION, Property.GEO, Property.LAST_MODIFIED, "LOCATION", Property.PRIORITY, Property.RECURRENCE_ID, Property.RESOURCES, Property.STATUS, Property.SUMMARY, Property.TRANSP, Property.URL), new ValidationRule(ValidationRule.ValidationType.None, Property.REQUEST_STATUS)), false));
        this.methodValidators.put(Method.COUNTER, new VEventValidator(Arrays.asList(new ValidationRule(ValidationRule.ValidationType.One, Property.DTSTAMP, Property.DTSTART, Property.SEQUENCE, Property.SUMMARY, Property.UID), new ValidationRule(ValidationRule.ValidationType.One, true, Property.ORGANIZER), new ValidationRule(ValidationRule.ValidationType.OneOrLess, Property.CATEGORIES, Property.CLASS, Property.CREATED, "DESCRIPTION", Property.DTEND, Property.DURATION, Property.GEO, Property.LAST_MODIFIED, "LOCATION", Property.PRIORITY, Property.RECURRENCE_ID, Property.RESOURCES, Property.STATUS, Property.TRANSP, Property.URL))));
        this.methodValidators.put(Method.DECLINE_COUNTER, new VEventValidator(Arrays.asList(new ValidationRule(ValidationRule.ValidationType.One, Property.DTSTAMP, Property.ORGANIZER, Property.UID), new ValidationRule(ValidationRule.ValidationType.OneOrLess, Property.RECURRENCE_ID, Property.SEQUENCE), new ValidationRule(ValidationRule.ValidationType.None, Property.ATTACH, Property.ATTENDEE, Property.CATEGORIES, Property.CLASS, Property.CONTACT, Property.CREATED, "DESCRIPTION", Property.DTEND, Property.DTSTART, Property.DURATION, Property.EXDATE, Property.EXRULE, Property.GEO, Property.LAST_MODIFIED, "LOCATION", Property.PRIORITY, Property.RDATE, Property.RELATED_TO, Property.RESOURCES, Property.RRULE, Property.STATUS, Property.SUMMARY, Property.TRANSP, Property.URL)), false));
        this.methodValidators.put(Method.PUBLISH, new VEventValidator(Arrays.asList(new ValidationRule(ValidationRule.ValidationType.One, Property.DTSTART, Property.UID), new ValidationRule(ValidationRule.ValidationType.One, true, Property.DTSTAMP, Property.ORGANIZER, Property.SUMMARY), new ValidationRule(ValidationRule.ValidationType.OneOrLess, Property.RECURRENCE_ID, Property.SEQUENCE, Property.CATEGORIES, Property.CLASS, Property.CREATED, "DESCRIPTION", Property.DTEND, Property.DURATION, Property.GEO, Property.LAST_MODIFIED, "LOCATION", Property.PRIORITY, Property.RESOURCES, Property.STATUS, Property.TRANSP, Property.URL), new ValidationRule(ValidationRule.ValidationType.None, true, Property.ATTENDEE), new ValidationRule(ValidationRule.ValidationType.None, Property.REQUEST_STATUS))));
        this.methodValidators.put(Method.REFRESH, new VEventValidator(Arrays.asList(new ValidationRule(ValidationRule.ValidationType.One, Property.ATTENDEE, Property.DTSTAMP, Property.ORGANIZER, Property.UID), new ValidationRule(ValidationRule.ValidationType.OneOrLess, Property.RECURRENCE_ID), new ValidationRule(ValidationRule.ValidationType.None, Property.ATTACH, Property.CATEGORIES, Property.CLASS, Property.CONTACT, Property.CREATED, "DESCRIPTION", Property.DTEND, Property.DTSTART, Property.DURATION, Property.EXDATE, Property.EXRULE, Property.GEO, Property.LAST_MODIFIED, "LOCATION", Property.PRIORITY, Property.RDATE, Property.RELATED_TO, Property.REQUEST_STATUS, Property.RESOURCES, Property.RRULE, Property.SEQUENCE, Property.STATUS, Property.SUMMARY, Property.TRANSP, Property.URL)), false));
        this.methodValidators.put(Method.REPLY, new VEventValidator(Arrays.asList(new ValidationRule(ValidationRule.ValidationType.One, Property.ATTENDEE, Property.DTSTAMP, Property.ORGANIZER, Property.UID), new ValidationRule(ValidationRule.ValidationType.OneOrLess, Property.RECURRENCE_ID, Property.SEQUENCE, Property.CATEGORIES, Property.CLASS, Property.CREATED, "DESCRIPTION", Property.DTEND, Property.DTSTART, Property.DURATION, Property.GEO, Property.LAST_MODIFIED, "LOCATION", Property.PRIORITY, Property.RESOURCES, Property.STATUS, Property.SUMMARY, Property.TRANSP, Property.URL)), CompatibilityHints.isHintEnabled(CompatibilityHints.KEY_RELAXED_VALIDATION)));
        this.methodValidators.put(Method.REQUEST, new VEventValidator(Arrays.asList(new ValidationRule(ValidationRule.ValidationType.OneOrMore, true, Property.ATTENDEE), new ValidationRule(ValidationRule.ValidationType.One, Property.DTSTAMP, Property.DTSTART, Property.ORGANIZER, Property.SUMMARY, Property.UID), new ValidationRule(ValidationRule.ValidationType.OneOrLess, Property.SEQUENCE, Property.CATEGORIES, Property.CLASS, Property.CREATED, "DESCRIPTION", Property.DTEND, Property.DURATION, Property.GEO, Property.LAST_MODIFIED, "LOCATION", Property.PRIORITY, Property.RECURRENCE_ID, Property.RESOURCES, Property.STATUS, Property.TRANSP, Property.URL))));
        this.alarms = componentList;
    }

    public VEvent(Date date, String str) {
        this();
        getProperties().add((PropertyList<Property>) new DtStart(date));
        getProperties().add((PropertyList<Property>) new Summary(str));
    }

    public VEvent(Date date, Date date2, String str) {
        this();
        getProperties().add((PropertyList<Property>) new DtStart(date));
        getProperties().add((PropertyList<Property>) new DtEnd(date2));
        getProperties().add((PropertyList<Property>) new Summary(str));
    }

    public VEvent(Date date, TemporalAmount temporalAmount, String str) {
        this();
        getProperties().add((PropertyList<Property>) new DtStart(date));
        getProperties().add((PropertyList<Property>) new Duration(temporalAmount));
        getProperties().add((PropertyList<Property>) new Summary(str));
    }

    public final ComponentList<VAlarm> getAlarms() {
        return this.alarms;
    }

    @Override // net.fortuna.ical4j.model.Component
    public final String toString() {
        return "BEGIN:" + getName() + "\r\n" + getProperties() + getAlarms() + "END:" + getName() + "\r\n";
    }

    @Override // net.fortuna.ical4j.model.Component
    public final void validate(boolean z) throws ValidationException {
        if (!CompatibilityHints.isHintEnabled(CompatibilityHints.KEY_RELAXED_VALIDATION)) {
            PropertyValidator.assertOne(Property.UID, getProperties());
            PropertyValidator.assertOne(Property.DTSTAMP, getProperties());
        }
        Arrays.asList(Property.CLASS, Property.CREATED, "DESCRIPTION", Property.DTSTART, Property.GEO, Property.LAST_MODIFIED, "LOCATION", Property.ORGANIZER, Property.PRIORITY, Property.DTSTAMP, Property.SEQUENCE, Property.STATUS, Property.SUMMARY, Property.TRANSP, Property.UID, Property.URL, Property.RECURRENCE_ID).forEach(new Consumer() { // from class: net.fortuna.ical4j.model.component.-$$Lambda$VEvent$xznEGYu30wBOVXM7UY-1yEy31IQ
            @Override // java.util.function.Consumer
            public final void accept(Object obj) throws ValidationException {
                this.f$0.lambda$validate$0$VEvent((String) obj);
            }
        });
        Status status = (Status) getProperty(Property.STATUS);
        if (status != null && !Status.VEVENT_TENTATIVE.getValue().equals(status.getValue()) && !Status.VEVENT_CONFIRMED.getValue().equals(status.getValue()) && !Status.VEVENT_CANCELLED.getValue().equals(status.getValue())) {
            throw new ValidationException("Status property [" + status.toString() + "] is not applicable for VEVENT");
        }
        try {
            PropertyValidator.assertNone(Property.DTEND, getProperties());
        } catch (ValidationException unused) {
            PropertyValidator.assertNone(Property.DURATION, getProperties());
        }
        if (getProperty(Property.DTEND) != null) {
            DtStart dtStart = (DtStart) getProperty(Property.DTSTART);
            DtEnd dtEnd = (DtEnd) getProperty(Property.DTEND);
            if (dtStart != null) {
                Parameter parameter = dtStart.getParameter(Parameter.VALUE);
                Parameter parameter2 = dtEnd.getParameter(Parameter.VALUE);
                boolean z2 = false;
                if (parameter2 == null ? !(parameter == null || Value.DATE_TIME.equals(parameter)) : !((parameter == null || parameter2.equals(parameter)) && (parameter != null || Value.DATE_TIME.equals(parameter2)))) {
                    z2 = true;
                }
                if (z2) {
                    throw new ValidationException("Property [DTEND] must have the same [VALUE] as [DTSTART]");
                }
            }
        }
        if (z) {
            validateProperties();
        }
    }

    public /* synthetic */ void lambda$validate$0$VEvent(String str) throws ValidationException {
        PropertyValidator.assertOneOrLess(str, getProperties());
    }

    @Override // net.fortuna.ical4j.model.component.CalendarComponent
    protected Validator getValidator(Method method) {
        return this.methodValidators.get(method);
    }

    public final PeriodList getConsumedTime(Date date, Date date2) {
        return getConsumedTime(date, date2, true);
    }

    public final PeriodList getConsumedTime(Date date, Date date2, boolean z) {
        PeriodList periodList = new PeriodList();
        if (Transp.TRANSPARENT.equals(getProperty(Property.TRANSP))) {
            return periodList;
        }
        PeriodList periodListCalculateRecurrenceSet = calculateRecurrenceSet(new Period(new DateTime(date), new DateTime(date2)));
        return (periodListCalculateRecurrenceSet.isEmpty() || !z) ? periodListCalculateRecurrenceSet : periodListCalculateRecurrenceSet.normalise();
    }

    public final VEvent getOccurrence(Date date) throws URISyntaxException, IOException, ParseException {
        Iterator<Period> it2 = getConsumedTime(date, date).iterator();
        while (it2.hasNext()) {
            if (it2.next().getStart().equals(date)) {
                VEvent vEvent = (VEvent) copy();
                vEvent.getProperties().add((PropertyList<Property>) new RecurrenceId(date));
                return vEvent;
            }
        }
        return null;
    }

    public final Clazz getClassification() {
        return (Clazz) getProperty(Property.CLASS);
    }

    public final Created getCreated() {
        return (Created) getProperty(Property.CREATED);
    }

    public final Description getDescription() {
        return (Description) getProperty("DESCRIPTION");
    }

    public final DtStart getStartDate() {
        return (DtStart) getProperty(Property.DTSTART);
    }

    public final Geo getGeographicPos() {
        return (Geo) getProperty(Property.GEO);
    }

    public final LastModified getLastModified() {
        return (LastModified) getProperty(Property.LAST_MODIFIED);
    }

    public final Location getLocation() {
        return (Location) getProperty("LOCATION");
    }

    public final Organizer getOrganizer() {
        return (Organizer) getProperty(Property.ORGANIZER);
    }

    public final Priority getPriority() {
        return (Priority) getProperty(Property.PRIORITY);
    }

    public final DtStamp getDateStamp() {
        return (DtStamp) getProperty(Property.DTSTAMP);
    }

    public final Sequence getSequence() {
        return (Sequence) getProperty(Property.SEQUENCE);
    }

    public final Status getStatus() {
        return (Status) getProperty(Property.STATUS);
    }

    public final Summary getSummary() {
        return (Summary) getProperty(Property.SUMMARY);
    }

    public final Transp getTransparency() {
        return (Transp) getProperty(Property.TRANSP);
    }

    public final Url getUrl() {
        return (Url) getProperty(Property.URL);
    }

    public final RecurrenceId getRecurrenceId() {
        return (RecurrenceId) getProperty(Property.RECURRENCE_ID);
    }

    public final DtEnd getEndDate() {
        return getEndDate(true);
    }

    public final DtEnd getEndDate(boolean z) {
        Duration duration;
        DtEnd dtEnd = (DtEnd) getProperty(Property.DTEND);
        if (dtEnd != null || !z || getStartDate() == null) {
            return dtEnd;
        }
        DtStart startDate = getStartDate();
        if (getDuration() != null) {
            duration = getDuration();
        } else if (startDate.getDate() instanceof DateTime) {
            duration = new Duration(java.time.Duration.ZERO);
        } else {
            duration = new Duration(java.time.Duration.ofDays(1L));
        }
        DtEnd dtEnd2 = new DtEnd(Dates.getInstance(Date.from(startDate.getDate().toInstant().plus(duration.getDuration())), (Value) startDate.getParameter(Parameter.VALUE)));
        if (startDate.isUtc()) {
            dtEnd2.setUtc(true);
        } else {
            dtEnd2.setTimeZone(startDate.getTimeZone());
        }
        return dtEnd2;
    }

    public final Duration getDuration() {
        return (Duration) getProperty(Property.DURATION);
    }

    public final Uid getUid() {
        return (Uid) getProperty(Property.UID);
    }

    @Override // net.fortuna.ical4j.model.Component
    public boolean equals(Object obj) {
        if (obj instanceof VEvent) {
            return super.equals(obj) && Objects.equals(this.alarms, ((VEvent) obj).getAlarms());
        }
        return super.equals(obj);
    }

    @Override // net.fortuna.ical4j.model.Component
    public int hashCode() {
        return new HashCodeBuilder().append(getName()).append(getProperties()).append(getAlarms()).toHashCode();
    }

    @Override // net.fortuna.ical4j.model.Component
    public Component copy() throws URISyntaxException, IOException, ParseException {
        VEvent vEvent = (VEvent) super.copy();
        vEvent.alarms = new ComponentList<>((ComponentList) this.alarms);
        return vEvent;
    }

    public static class Factory extends Content.Factory implements ComponentFactory<VEvent> {
        public Factory() {
            super(Component.VEVENT);
        }

        @Override // net.fortuna.ical4j.model.ComponentFactory
        public VEvent createComponent() {
            return new VEvent(false);
        }

        @Override // net.fortuna.ical4j.model.ComponentFactory
        public VEvent createComponent(PropertyList propertyList) {
            return new VEvent(propertyList);
        }

        @Override // net.fortuna.ical4j.model.ComponentFactory
        public VEvent createComponent(PropertyList propertyList, ComponentList componentList) {
            return new VEvent(propertyList, (ComponentList<VAlarm>) componentList);
        }
    }
}
