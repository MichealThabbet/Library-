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
import net.fortuna.ical4j.model.Property;
import net.fortuna.ical4j.model.PropertyList;
import net.fortuna.ical4j.model.property.Clazz;
import net.fortuna.ical4j.model.property.Completed;
import net.fortuna.ical4j.model.property.Created;
import net.fortuna.ical4j.model.property.Description;
import net.fortuna.ical4j.model.property.DtStamp;
import net.fortuna.ical4j.model.property.DtStart;
import net.fortuna.ical4j.model.property.Due;
import net.fortuna.ical4j.model.property.Duration;
import net.fortuna.ical4j.model.property.Geo;
import net.fortuna.ical4j.model.property.LastModified;
import net.fortuna.ical4j.model.property.Location;
import net.fortuna.ical4j.model.property.Method;
import net.fortuna.ical4j.model.property.Organizer;
import net.fortuna.ical4j.model.property.PercentComplete;
import net.fortuna.ical4j.model.property.Priority;
import net.fortuna.ical4j.model.property.RecurrenceId;
import net.fortuna.ical4j.model.property.Sequence;
import net.fortuna.ical4j.model.property.Status;
import net.fortuna.ical4j.model.property.Summary;
import net.fortuna.ical4j.model.property.Uid;
import net.fortuna.ical4j.model.property.Url;
import net.fortuna.ical4j.util.CompatibilityHints;
import net.fortuna.ical4j.validate.PropertyValidator;
import net.fortuna.ical4j.validate.ValidationException;
import net.fortuna.ical4j.validate.ValidationRule;
import net.fortuna.ical4j.validate.Validator;
import net.fortuna.ical4j.validate.component.VToDoValidator;
import org.apache.commons.lang3.builder.HashCodeBuilder;

/* loaded from: classes3.dex */
public class VToDo extends CalendarComponent {
    private static final long serialVersionUID = -269658210065896668L;
    private ComponentList<VAlarm> alarms;
    private final Map<Method, Validator> methodValidators;

    public VToDo() {
        this(true);
    }

    public VToDo(boolean z) {
        super(Component.VTODO);
        HashMap map = new HashMap();
        this.methodValidators = map;
        map.put(Method.ADD, new VToDoValidator(Arrays.asList(new ValidationRule(ValidationRule.ValidationType.One, Property.DTSTAMP, Property.ORGANIZER, Property.PRIORITY, Property.SEQUENCE, Property.SUMMARY, Property.UID), new ValidationRule(ValidationRule.ValidationType.OneOrLess, Property.CATEGORIES, Property.CLASS, Property.CREATED, "DESCRIPTION", Property.DTSTART, Property.DUE, Property.DURATION, Property.GEO, Property.LAST_MODIFIED, "LOCATION", Property.PERCENT_COMPLETE, Property.RESOURCES, Property.STATUS, Property.URL), new ValidationRule(ValidationRule.ValidationType.None, Property.RECURRENCE_ID, Property.REQUEST_STATUS))));
        this.methodValidators.put(Method.CANCEL, new VToDoValidator(Arrays.asList(new ValidationRule(ValidationRule.ValidationType.One, Property.UID, Property.DTSTAMP, Property.ORGANIZER, Property.SEQUENCE), new ValidationRule(ValidationRule.ValidationType.OneOrLess, Property.CATEGORIES, Property.CLASS, Property.CREATED, "DESCRIPTION", Property.DTSTART, Property.DUE, Property.DURATION, Property.GEO, Property.LAST_MODIFIED, "LOCATION", Property.PERCENT_COMPLETE, Property.RECURRENCE_ID, Property.RESOURCES, Property.PRIORITY, Property.STATUS, Property.URL), new ValidationRule(ValidationRule.ValidationType.None, Property.REQUEST_STATUS)), false));
        this.methodValidators.put(Method.COUNTER, new VToDoValidator(Arrays.asList(new ValidationRule(ValidationRule.ValidationType.OneOrMore, Property.ATTENDEE), new ValidationRule(ValidationRule.ValidationType.One, Property.DTSTAMP, Property.ORGANIZER, Property.PRIORITY, Property.SUMMARY, Property.UID), new ValidationRule(ValidationRule.ValidationType.OneOrLess, Property.CATEGORIES, Property.CLASS, Property.CREATED, "DESCRIPTION", Property.DTSTART, Property.DUE, Property.DURATION, Property.GEO, Property.LAST_MODIFIED, "LOCATION", Property.PERCENT_COMPLETE, Property.RECURRENCE_ID, Property.RESOURCES, Property.RRULE, Property.SEQUENCE, Property.STATUS, Property.URL))));
        this.methodValidators.put(Method.DECLINE_COUNTER, new VToDoValidator(Arrays.asList(new ValidationRule(ValidationRule.ValidationType.OneOrMore, Property.ATTENDEE), new ValidationRule(ValidationRule.ValidationType.One, Property.DTSTAMP, Property.ORGANIZER, Property.SEQUENCE, Property.UID), new ValidationRule(ValidationRule.ValidationType.OneOrLess, Property.CATEGORIES, Property.CLASS, Property.CREATED, "DESCRIPTION", Property.DTSTART, Property.DUE, Property.DURATION, Property.GEO, Property.LAST_MODIFIED, "LOCATION", "LOCATION", Property.PERCENT_COMPLETE, Property.PRIORITY, Property.RECURRENCE_ID, Property.RESOURCES, Property.STATUS, Property.URL)), false));
        this.methodValidators.put(Method.PUBLISH, new VToDoValidator(Arrays.asList(new ValidationRule(ValidationRule.ValidationType.One, Property.DTSTAMP, Property.SUMMARY, Property.UID), new ValidationRule(ValidationRule.ValidationType.One, true, Property.ORGANIZER, Property.PRIORITY), new ValidationRule(ValidationRule.ValidationType.OneOrLess, Property.DTSTART, Property.SEQUENCE, Property.CATEGORIES, Property.CLASS, Property.CREATED, "DESCRIPTION", Property.DUE, Property.DURATION, Property.GEO, Property.LAST_MODIFIED, "LOCATION", Property.PERCENT_COMPLETE, Property.RECURRENCE_ID, Property.RESOURCES, Property.STATUS, Property.URL), new ValidationRule(ValidationRule.ValidationType.None, Property.ATTENDEE, Property.REQUEST_STATUS))));
        this.methodValidators.put(Method.REFRESH, new VToDoValidator(Arrays.asList(new ValidationRule(ValidationRule.ValidationType.One, Property.ATTENDEE, Property.DTSTAMP, Property.UID), new ValidationRule(ValidationRule.ValidationType.OneOrLess, Property.RECURRENCE_ID), new ValidationRule(ValidationRule.ValidationType.None, Property.ATTACH, Property.CATEGORIES, Property.CLASS, Property.CONTACT, Property.CREATED, "DESCRIPTION", Property.DTSTART, Property.DUE, Property.DURATION, Property.EXDATE, Property.EXRULE, Property.GEO, Property.LAST_MODIFIED, "LOCATION", Property.ORGANIZER, Property.PERCENT_COMPLETE, Property.PRIORITY, Property.RDATE, Property.RELATED_TO, Property.REQUEST_STATUS, Property.RESOURCES, Property.RRULE, Property.SEQUENCE, Property.STATUS, Property.URL)), false));
        this.methodValidators.put(Method.REPLY, new VToDoValidator(Arrays.asList(new ValidationRule(ValidationRule.ValidationType.OneOrMore, Property.ATTENDEE), new ValidationRule(ValidationRule.ValidationType.One, Property.DTSTAMP, Property.ORGANIZER, Property.UID), new ValidationRule(ValidationRule.ValidationType.OneOrLess, Property.CATEGORIES, Property.CLASS, Property.CREATED, "DESCRIPTION", Property.DTSTART, Property.DUE, Property.DURATION, Property.GEO, Property.LAST_MODIFIED, "LOCATION", Property.PERCENT_COMPLETE, Property.PRIORITY, Property.RESOURCES, Property.RECURRENCE_ID, Property.SEQUENCE, Property.STATUS, Property.SUMMARY, Property.URL)), false));
        this.methodValidators.put(Method.REQUEST, new VToDoValidator(Arrays.asList(new ValidationRule(ValidationRule.ValidationType.OneOrMore, Property.ATTENDEE), new ValidationRule(ValidationRule.ValidationType.One, Property.DTSTAMP, Property.DTSTART, Property.ORGANIZER, Property.PRIORITY, Property.SUMMARY, Property.UID), new ValidationRule(ValidationRule.ValidationType.OneOrLess, Property.SEQUENCE, Property.CATEGORIES, Property.CLASS, Property.CREATED, "DESCRIPTION", Property.DUE, Property.DURATION, Property.GEO, Property.LAST_MODIFIED, "LOCATION", Property.PERCENT_COMPLETE, Property.RECURRENCE_ID, Property.RESOURCES, Property.STATUS, Property.URL), new ValidationRule(ValidationRule.ValidationType.None, Property.REQUEST_STATUS))));
        this.alarms = new ComponentList<>();
        if (z) {
            getProperties().add((PropertyList<Property>) new DtStamp());
        }
    }

    public VToDo(PropertyList propertyList) {
        super(Component.VTODO, propertyList);
        HashMap map = new HashMap();
        this.methodValidators = map;
        map.put(Method.ADD, new VToDoValidator(Arrays.asList(new ValidationRule(ValidationRule.ValidationType.One, Property.DTSTAMP, Property.ORGANIZER, Property.PRIORITY, Property.SEQUENCE, Property.SUMMARY, Property.UID), new ValidationRule(ValidationRule.ValidationType.OneOrLess, Property.CATEGORIES, Property.CLASS, Property.CREATED, "DESCRIPTION", Property.DTSTART, Property.DUE, Property.DURATION, Property.GEO, Property.LAST_MODIFIED, "LOCATION", Property.PERCENT_COMPLETE, Property.RESOURCES, Property.STATUS, Property.URL), new ValidationRule(ValidationRule.ValidationType.None, Property.RECURRENCE_ID, Property.REQUEST_STATUS))));
        this.methodValidators.put(Method.CANCEL, new VToDoValidator(Arrays.asList(new ValidationRule(ValidationRule.ValidationType.One, Property.UID, Property.DTSTAMP, Property.ORGANIZER, Property.SEQUENCE), new ValidationRule(ValidationRule.ValidationType.OneOrLess, Property.CATEGORIES, Property.CLASS, Property.CREATED, "DESCRIPTION", Property.DTSTART, Property.DUE, Property.DURATION, Property.GEO, Property.LAST_MODIFIED, "LOCATION", Property.PERCENT_COMPLETE, Property.RECURRENCE_ID, Property.RESOURCES, Property.PRIORITY, Property.STATUS, Property.URL), new ValidationRule(ValidationRule.ValidationType.None, Property.REQUEST_STATUS)), false));
        this.methodValidators.put(Method.COUNTER, new VToDoValidator(Arrays.asList(new ValidationRule(ValidationRule.ValidationType.OneOrMore, Property.ATTENDEE), new ValidationRule(ValidationRule.ValidationType.One, Property.DTSTAMP, Property.ORGANIZER, Property.PRIORITY, Property.SUMMARY, Property.UID), new ValidationRule(ValidationRule.ValidationType.OneOrLess, Property.CATEGORIES, Property.CLASS, Property.CREATED, "DESCRIPTION", Property.DTSTART, Property.DUE, Property.DURATION, Property.GEO, Property.LAST_MODIFIED, "LOCATION", Property.PERCENT_COMPLETE, Property.RECURRENCE_ID, Property.RESOURCES, Property.RRULE, Property.SEQUENCE, Property.STATUS, Property.URL))));
        this.methodValidators.put(Method.DECLINE_COUNTER, new VToDoValidator(Arrays.asList(new ValidationRule(ValidationRule.ValidationType.OneOrMore, Property.ATTENDEE), new ValidationRule(ValidationRule.ValidationType.One, Property.DTSTAMP, Property.ORGANIZER, Property.SEQUENCE, Property.UID), new ValidationRule(ValidationRule.ValidationType.OneOrLess, Property.CATEGORIES, Property.CLASS, Property.CREATED, "DESCRIPTION", Property.DTSTART, Property.DUE, Property.DURATION, Property.GEO, Property.LAST_MODIFIED, "LOCATION", "LOCATION", Property.PERCENT_COMPLETE, Property.PRIORITY, Property.RECURRENCE_ID, Property.RESOURCES, Property.STATUS, Property.URL)), false));
        this.methodValidators.put(Method.PUBLISH, new VToDoValidator(Arrays.asList(new ValidationRule(ValidationRule.ValidationType.One, Property.DTSTAMP, Property.SUMMARY, Property.UID), new ValidationRule(ValidationRule.ValidationType.One, true, Property.ORGANIZER, Property.PRIORITY), new ValidationRule(ValidationRule.ValidationType.OneOrLess, Property.DTSTART, Property.SEQUENCE, Property.CATEGORIES, Property.CLASS, Property.CREATED, "DESCRIPTION", Property.DUE, Property.DURATION, Property.GEO, Property.LAST_MODIFIED, "LOCATION", Property.PERCENT_COMPLETE, Property.RECURRENCE_ID, Property.RESOURCES, Property.STATUS, Property.URL), new ValidationRule(ValidationRule.ValidationType.None, Property.ATTENDEE, Property.REQUEST_STATUS))));
        this.methodValidators.put(Method.REFRESH, new VToDoValidator(Arrays.asList(new ValidationRule(ValidationRule.ValidationType.One, Property.ATTENDEE, Property.DTSTAMP, Property.UID), new ValidationRule(ValidationRule.ValidationType.OneOrLess, Property.RECURRENCE_ID), new ValidationRule(ValidationRule.ValidationType.None, Property.ATTACH, Property.CATEGORIES, Property.CLASS, Property.CONTACT, Property.CREATED, "DESCRIPTION", Property.DTSTART, Property.DUE, Property.DURATION, Property.EXDATE, Property.EXRULE, Property.GEO, Property.LAST_MODIFIED, "LOCATION", Property.ORGANIZER, Property.PERCENT_COMPLETE, Property.PRIORITY, Property.RDATE, Property.RELATED_TO, Property.REQUEST_STATUS, Property.RESOURCES, Property.RRULE, Property.SEQUENCE, Property.STATUS, Property.URL)), false));
        this.methodValidators.put(Method.REPLY, new VToDoValidator(Arrays.asList(new ValidationRule(ValidationRule.ValidationType.OneOrMore, Property.ATTENDEE), new ValidationRule(ValidationRule.ValidationType.One, Property.DTSTAMP, Property.ORGANIZER, Property.UID), new ValidationRule(ValidationRule.ValidationType.OneOrLess, Property.CATEGORIES, Property.CLASS, Property.CREATED, "DESCRIPTION", Property.DTSTART, Property.DUE, Property.DURATION, Property.GEO, Property.LAST_MODIFIED, "LOCATION", Property.PERCENT_COMPLETE, Property.PRIORITY, Property.RESOURCES, Property.RECURRENCE_ID, Property.SEQUENCE, Property.STATUS, Property.SUMMARY, Property.URL)), false));
        this.methodValidators.put(Method.REQUEST, new VToDoValidator(Arrays.asList(new ValidationRule(ValidationRule.ValidationType.OneOrMore, Property.ATTENDEE), new ValidationRule(ValidationRule.ValidationType.One, Property.DTSTAMP, Property.DTSTART, Property.ORGANIZER, Property.PRIORITY, Property.SUMMARY, Property.UID), new ValidationRule(ValidationRule.ValidationType.OneOrLess, Property.SEQUENCE, Property.CATEGORIES, Property.CLASS, Property.CREATED, "DESCRIPTION", Property.DUE, Property.DURATION, Property.GEO, Property.LAST_MODIFIED, "LOCATION", Property.PERCENT_COMPLETE, Property.RECURRENCE_ID, Property.RESOURCES, Property.STATUS, Property.URL), new ValidationRule(ValidationRule.ValidationType.None, Property.REQUEST_STATUS))));
        this.alarms = new ComponentList<>();
    }

    public VToDo(PropertyList propertyList, ComponentList<VAlarm> componentList) {
        super(Component.VTODO, propertyList);
        HashMap map = new HashMap();
        this.methodValidators = map;
        map.put(Method.ADD, new VToDoValidator(Arrays.asList(new ValidationRule(ValidationRule.ValidationType.One, Property.DTSTAMP, Property.ORGANIZER, Property.PRIORITY, Property.SEQUENCE, Property.SUMMARY, Property.UID), new ValidationRule(ValidationRule.ValidationType.OneOrLess, Property.CATEGORIES, Property.CLASS, Property.CREATED, "DESCRIPTION", Property.DTSTART, Property.DUE, Property.DURATION, Property.GEO, Property.LAST_MODIFIED, "LOCATION", Property.PERCENT_COMPLETE, Property.RESOURCES, Property.STATUS, Property.URL), new ValidationRule(ValidationRule.ValidationType.None, Property.RECURRENCE_ID, Property.REQUEST_STATUS))));
        this.methodValidators.put(Method.CANCEL, new VToDoValidator(Arrays.asList(new ValidationRule(ValidationRule.ValidationType.One, Property.UID, Property.DTSTAMP, Property.ORGANIZER, Property.SEQUENCE), new ValidationRule(ValidationRule.ValidationType.OneOrLess, Property.CATEGORIES, Property.CLASS, Property.CREATED, "DESCRIPTION", Property.DTSTART, Property.DUE, Property.DURATION, Property.GEO, Property.LAST_MODIFIED, "LOCATION", Property.PERCENT_COMPLETE, Property.RECURRENCE_ID, Property.RESOURCES, Property.PRIORITY, Property.STATUS, Property.URL), new ValidationRule(ValidationRule.ValidationType.None, Property.REQUEST_STATUS)), false));
        this.methodValidators.put(Method.COUNTER, new VToDoValidator(Arrays.asList(new ValidationRule(ValidationRule.ValidationType.OneOrMore, Property.ATTENDEE), new ValidationRule(ValidationRule.ValidationType.One, Property.DTSTAMP, Property.ORGANIZER, Property.PRIORITY, Property.SUMMARY, Property.UID), new ValidationRule(ValidationRule.ValidationType.OneOrLess, Property.CATEGORIES, Property.CLASS, Property.CREATED, "DESCRIPTION", Property.DTSTART, Property.DUE, Property.DURATION, Property.GEO, Property.LAST_MODIFIED, "LOCATION", Property.PERCENT_COMPLETE, Property.RECURRENCE_ID, Property.RESOURCES, Property.RRULE, Property.SEQUENCE, Property.STATUS, Property.URL))));
        this.methodValidators.put(Method.DECLINE_COUNTER, new VToDoValidator(Arrays.asList(new ValidationRule(ValidationRule.ValidationType.OneOrMore, Property.ATTENDEE), new ValidationRule(ValidationRule.ValidationType.One, Property.DTSTAMP, Property.ORGANIZER, Property.SEQUENCE, Property.UID), new ValidationRule(ValidationRule.ValidationType.OneOrLess, Property.CATEGORIES, Property.CLASS, Property.CREATED, "DESCRIPTION", Property.DTSTART, Property.DUE, Property.DURATION, Property.GEO, Property.LAST_MODIFIED, "LOCATION", "LOCATION", Property.PERCENT_COMPLETE, Property.PRIORITY, Property.RECURRENCE_ID, Property.RESOURCES, Property.STATUS, Property.URL)), false));
        this.methodValidators.put(Method.PUBLISH, new VToDoValidator(Arrays.asList(new ValidationRule(ValidationRule.ValidationType.One, Property.DTSTAMP, Property.SUMMARY, Property.UID), new ValidationRule(ValidationRule.ValidationType.One, true, Property.ORGANIZER, Property.PRIORITY), new ValidationRule(ValidationRule.ValidationType.OneOrLess, Property.DTSTART, Property.SEQUENCE, Property.CATEGORIES, Property.CLASS, Property.CREATED, "DESCRIPTION", Property.DUE, Property.DURATION, Property.GEO, Property.LAST_MODIFIED, "LOCATION", Property.PERCENT_COMPLETE, Property.RECURRENCE_ID, Property.RESOURCES, Property.STATUS, Property.URL), new ValidationRule(ValidationRule.ValidationType.None, Property.ATTENDEE, Property.REQUEST_STATUS))));
        this.methodValidators.put(Method.REFRESH, new VToDoValidator(Arrays.asList(new ValidationRule(ValidationRule.ValidationType.One, Property.ATTENDEE, Property.DTSTAMP, Property.UID), new ValidationRule(ValidationRule.ValidationType.OneOrLess, Property.RECURRENCE_ID), new ValidationRule(ValidationRule.ValidationType.None, Property.ATTACH, Property.CATEGORIES, Property.CLASS, Property.CONTACT, Property.CREATED, "DESCRIPTION", Property.DTSTART, Property.DUE, Property.DURATION, Property.EXDATE, Property.EXRULE, Property.GEO, Property.LAST_MODIFIED, "LOCATION", Property.ORGANIZER, Property.PERCENT_COMPLETE, Property.PRIORITY, Property.RDATE, Property.RELATED_TO, Property.REQUEST_STATUS, Property.RESOURCES, Property.RRULE, Property.SEQUENCE, Property.STATUS, Property.URL)), false));
        this.methodValidators.put(Method.REPLY, new VToDoValidator(Arrays.asList(new ValidationRule(ValidationRule.ValidationType.OneOrMore, Property.ATTENDEE), new ValidationRule(ValidationRule.ValidationType.One, Property.DTSTAMP, Property.ORGANIZER, Property.UID), new ValidationRule(ValidationRule.ValidationType.OneOrLess, Property.CATEGORIES, Property.CLASS, Property.CREATED, "DESCRIPTION", Property.DTSTART, Property.DUE, Property.DURATION, Property.GEO, Property.LAST_MODIFIED, "LOCATION", Property.PERCENT_COMPLETE, Property.PRIORITY, Property.RESOURCES, Property.RECURRENCE_ID, Property.SEQUENCE, Property.STATUS, Property.SUMMARY, Property.URL)), false));
        this.methodValidators.put(Method.REQUEST, new VToDoValidator(Arrays.asList(new ValidationRule(ValidationRule.ValidationType.OneOrMore, Property.ATTENDEE), new ValidationRule(ValidationRule.ValidationType.One, Property.DTSTAMP, Property.DTSTART, Property.ORGANIZER, Property.PRIORITY, Property.SUMMARY, Property.UID), new ValidationRule(ValidationRule.ValidationType.OneOrLess, Property.SEQUENCE, Property.CATEGORIES, Property.CLASS, Property.CREATED, "DESCRIPTION", Property.DUE, Property.DURATION, Property.GEO, Property.LAST_MODIFIED, "LOCATION", Property.PERCENT_COMPLETE, Property.RECURRENCE_ID, Property.RESOURCES, Property.STATUS, Property.URL), new ValidationRule(ValidationRule.ValidationType.None, Property.REQUEST_STATUS))));
        this.alarms = new ComponentList<>();
        this.alarms = componentList;
    }

    public VToDo(Date date, String str) {
        this();
        getProperties().add((PropertyList<Property>) new DtStart(date));
        getProperties().add((PropertyList<Property>) new Summary(str));
    }

    public VToDo(Date date, Date date2, String str) {
        this();
        getProperties().add((PropertyList<Property>) new DtStart(date));
        getProperties().add((PropertyList<Property>) new Due(date2));
        getProperties().add((PropertyList<Property>) new Summary(str));
    }

    public VToDo(Date date, TemporalAmount temporalAmount, String str) {
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
        Iterator<VAlarm> it2 = getAlarms().iterator();
        while (it2.hasNext()) {
            it2.next().validate(z);
        }
        if (!CompatibilityHints.isHintEnabled(CompatibilityHints.KEY_RELAXED_VALIDATION)) {
            PropertyValidator.assertOne(Property.UID, getProperties());
            PropertyValidator.assertOne(Property.DTSTAMP, getProperties());
        }
        Arrays.asList(Property.CLASS, Property.COMPLETED, Property.CREATED, "DESCRIPTION", Property.DTSTAMP, Property.DTSTART, Property.GEO, Property.LAST_MODIFIED, "LOCATION", Property.ORGANIZER, Property.PERCENT_COMPLETE, Property.PRIORITY, Property.RECURRENCE_ID, Property.SEQUENCE, Property.STATUS, Property.SUMMARY, Property.UID, Property.URL).forEach(new Consumer() { // from class: net.fortuna.ical4j.model.component.-$$Lambda$VToDo$6kMAMHgR7K9TLrhISCnpYO4zz_I
            @Override // java.util.function.Consumer
            public final void accept(Object obj) throws ValidationException {
                this.f$0.lambda$validate$0$VToDo((String) obj);
            }
        });
        Status status = (Status) getProperty(Property.STATUS);
        if (status != null && !Status.VTODO_NEEDS_ACTION.getValue().equals(status.getValue()) && !Status.VTODO_COMPLETED.getValue().equals(status.getValue()) && !Status.VTODO_IN_PROCESS.getValue().equals(status.getValue()) && !Status.VTODO_CANCELLED.getValue().equals(status.getValue())) {
            throw new ValidationException("Status property [" + status.toString() + "] may not occur in VTODO");
        }
        try {
            PropertyValidator.assertNone(Property.DUE, getProperties());
        } catch (ValidationException unused) {
            PropertyValidator.assertNone(Property.DURATION, getProperties());
        }
        if (z) {
            validateProperties();
        }
    }

    public /* synthetic */ void lambda$validate$0$VToDo(String str) throws ValidationException {
        PropertyValidator.assertOneOrLess(str, getProperties());
    }

    @Override // net.fortuna.ical4j.model.component.CalendarComponent
    protected Validator getValidator(Method method) {
        return this.methodValidators.get(method);
    }

    public final Clazz getClassification() {
        return (Clazz) getProperty(Property.CLASS);
    }

    public final Completed getDateCompleted() {
        return (Completed) getProperty(Property.COMPLETED);
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

    public final PercentComplete getPercentComplete() {
        return (PercentComplete) getProperty(Property.PERCENT_COMPLETE);
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

    public final Url getUrl() {
        return (Url) getProperty(Property.URL);
    }

    public final RecurrenceId getRecurrenceId() {
        return (RecurrenceId) getProperty(Property.RECURRENCE_ID);
    }

    public final Duration getDuration() {
        return (Duration) getProperty(Property.DURATION);
    }

    public final Due getDue() {
        return (Due) getProperty(Property.DUE);
    }

    public final Uid getUid() {
        return (Uid) getProperty(Property.UID);
    }

    @Override // net.fortuna.ical4j.model.Component
    public boolean equals(Object obj) {
        if (obj instanceof VToDo) {
            return super.equals(obj) && Objects.equals(this.alarms, ((VToDo) obj).getAlarms());
        }
        return super.equals(obj);
    }

    @Override // net.fortuna.ical4j.model.Component
    public int hashCode() {
        return new HashCodeBuilder().append(getName()).append(getProperties()).append(getAlarms()).toHashCode();
    }

    @Override // net.fortuna.ical4j.model.Component
    public Component copy() throws URISyntaxException, IOException, ParseException {
        VToDo vToDo = (VToDo) super.copy();
        vToDo.alarms = new ComponentList<>((ComponentList) this.alarms);
        return vToDo;
    }

    public static class Factory extends Content.Factory implements ComponentFactory<VToDo> {
        public Factory() {
            super(Component.VTODO);
        }

        @Override // net.fortuna.ical4j.model.ComponentFactory
        public VToDo createComponent() {
            return new VToDo(false);
        }

        @Override // net.fortuna.ical4j.model.ComponentFactory
        public VToDo createComponent(PropertyList propertyList) {
            return new VToDo(propertyList);
        }

        @Override // net.fortuna.ical4j.model.ComponentFactory
        public VToDo createComponent(PropertyList propertyList, ComponentList componentList) {
            return new VToDo(propertyList, (ComponentList<VAlarm>) componentList);
        }
    }
}
