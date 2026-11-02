package net.fortuna.ical4j.model.component;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;
import net.fortuna.ical4j.model.Component;
import net.fortuna.ical4j.model.ComponentFactory;
import net.fortuna.ical4j.model.ComponentList;
import net.fortuna.ical4j.model.Content;
import net.fortuna.ical4j.model.Date;
import net.fortuna.ical4j.model.Property;
import net.fortuna.ical4j.model.PropertyList;
import net.fortuna.ical4j.model.property.Clazz;
import net.fortuna.ical4j.model.property.Created;
import net.fortuna.ical4j.model.property.Description;
import net.fortuna.ical4j.model.property.DtStamp;
import net.fortuna.ical4j.model.property.DtStart;
import net.fortuna.ical4j.model.property.LastModified;
import net.fortuna.ical4j.model.property.Method;
import net.fortuna.ical4j.model.property.Organizer;
import net.fortuna.ical4j.model.property.RecurrenceId;
import net.fortuna.ical4j.model.property.Sequence;
import net.fortuna.ical4j.model.property.Status;
import net.fortuna.ical4j.model.property.Summary;
import net.fortuna.ical4j.model.property.Uid;
import net.fortuna.ical4j.model.property.Url;
import net.fortuna.ical4j.util.CompatibilityHints;
import net.fortuna.ical4j.validate.ComponentValidator;
import net.fortuna.ical4j.validate.PropertyValidator;
import net.fortuna.ical4j.validate.ValidationException;
import net.fortuna.ical4j.validate.ValidationRule;
import net.fortuna.ical4j.validate.Validator;

/* loaded from: classes3.dex */
public class VJournal extends CalendarComponent {
    private static final long serialVersionUID = -7635140949183238830L;
    private final Map<Method, Validator> methodValidators;

    public VJournal() {
        this(true);
    }

    public VJournal(boolean z) {
        super(Component.VJOURNAL);
        HashMap map = new HashMap();
        this.methodValidators = map;
        map.put(Method.ADD, new ComponentValidator(Arrays.asList(new ValidationRule(ValidationRule.ValidationType.One, "DESCRIPTION", Property.DTSTAMP, Property.DTSTART, Property.ORGANIZER, Property.SEQUENCE, Property.UID), new ValidationRule(ValidationRule.ValidationType.OneOrLess, Property.CATEGORIES, Property.CLASS, Property.CREATED, Property.LAST_MODIFIED, Property.STATUS, Property.SUMMARY, Property.URL), new ValidationRule(ValidationRule.ValidationType.None, Property.ATTENDEE, Property.RECURRENCE_ID))));
        this.methodValidators.put(Method.CANCEL, new ComponentValidator(Arrays.asList(new ValidationRule(ValidationRule.ValidationType.One, Property.DTSTAMP, Property.ORGANIZER, Property.SEQUENCE, Property.UID), new ValidationRule(ValidationRule.ValidationType.OneOrLess, Property.CATEGORIES, Property.CLASS, Property.CREATED, "DESCRIPTION", Property.DTSTART, Property.LAST_MODIFIED, Property.RECURRENCE_ID, Property.STATUS, Property.SUMMARY, Property.URL), new ValidationRule(ValidationRule.ValidationType.None, Property.REQUEST_STATUS))));
        this.methodValidators.put(Method.PUBLISH, new ComponentValidator(Arrays.asList(new ValidationRule(ValidationRule.ValidationType.One, "DESCRIPTION", Property.DTSTAMP, Property.DTSTART, Property.ORGANIZER, Property.UID), new ValidationRule(ValidationRule.ValidationType.OneOrLess, Property.CATEGORIES, Property.CLASS, Property.CREATED, Property.LAST_MODIFIED, Property.RECURRENCE_ID, Property.SEQUENCE, Property.STATUS, Property.SUMMARY, Property.URL), new ValidationRule(ValidationRule.ValidationType.None, Property.ATTENDEE))));
        if (z) {
            getProperties().add((PropertyList<Property>) new DtStamp());
        }
    }

    public VJournal(PropertyList propertyList) {
        super(Component.VJOURNAL, propertyList);
        HashMap map = new HashMap();
        this.methodValidators = map;
        map.put(Method.ADD, new ComponentValidator(Arrays.asList(new ValidationRule(ValidationRule.ValidationType.One, "DESCRIPTION", Property.DTSTAMP, Property.DTSTART, Property.ORGANIZER, Property.SEQUENCE, Property.UID), new ValidationRule(ValidationRule.ValidationType.OneOrLess, Property.CATEGORIES, Property.CLASS, Property.CREATED, Property.LAST_MODIFIED, Property.STATUS, Property.SUMMARY, Property.URL), new ValidationRule(ValidationRule.ValidationType.None, Property.ATTENDEE, Property.RECURRENCE_ID))));
        this.methodValidators.put(Method.CANCEL, new ComponentValidator(Arrays.asList(new ValidationRule(ValidationRule.ValidationType.One, Property.DTSTAMP, Property.ORGANIZER, Property.SEQUENCE, Property.UID), new ValidationRule(ValidationRule.ValidationType.OneOrLess, Property.CATEGORIES, Property.CLASS, Property.CREATED, "DESCRIPTION", Property.DTSTART, Property.LAST_MODIFIED, Property.RECURRENCE_ID, Property.STATUS, Property.SUMMARY, Property.URL), new ValidationRule(ValidationRule.ValidationType.None, Property.REQUEST_STATUS))));
        this.methodValidators.put(Method.PUBLISH, new ComponentValidator(Arrays.asList(new ValidationRule(ValidationRule.ValidationType.One, "DESCRIPTION", Property.DTSTAMP, Property.DTSTART, Property.ORGANIZER, Property.UID), new ValidationRule(ValidationRule.ValidationType.OneOrLess, Property.CATEGORIES, Property.CLASS, Property.CREATED, Property.LAST_MODIFIED, Property.RECURRENCE_ID, Property.SEQUENCE, Property.STATUS, Property.SUMMARY, Property.URL), new ValidationRule(ValidationRule.ValidationType.None, Property.ATTENDEE))));
    }

    public VJournal(Date date, String str) {
        this();
        getProperties().add((PropertyList<Property>) new DtStart(date));
        getProperties().add((PropertyList<Property>) new Summary(str));
    }

    @Override // net.fortuna.ical4j.model.Component
    public final void validate(boolean z) throws ValidationException {
        if (!CompatibilityHints.isHintEnabled(CompatibilityHints.KEY_RELAXED_VALIDATION)) {
            PropertyValidator.assertOne(Property.UID, getProperties());
            PropertyValidator.assertOne(Property.DTSTAMP, getProperties());
        }
        Arrays.asList(Property.CLASS, Property.CREATED, "DESCRIPTION", Property.DTSTART, Property.DTSTAMP, Property.LAST_MODIFIED, Property.ORGANIZER, Property.RECURRENCE_ID, Property.SEQUENCE, Property.STATUS, Property.SUMMARY, Property.UID, Property.URL).forEach(new Consumer() { // from class: net.fortuna.ical4j.model.component.-$$Lambda$VJournal$XJbqkQqK4OQOdTtcyQFOlC-cDqo
            @Override // java.util.function.Consumer
            public final void accept(Object obj) throws ValidationException {
                this.f$0.lambda$validate$0$VJournal((String) obj);
            }
        });
        Status status = (Status) getProperty(Property.STATUS);
        if (status == null || Status.VJOURNAL_DRAFT.getValue().equals(status.getValue()) || Status.VJOURNAL_FINAL.getValue().equals(status.getValue()) || Status.VJOURNAL_CANCELLED.getValue().equals(status.getValue())) {
            if (z) {
                validateProperties();
            }
        } else {
            throw new ValidationException("Status property [" + status.toString() + "] may not occur in VJOURNAL");
        }
    }

    public /* synthetic */ void lambda$validate$0$VJournal(String str) throws ValidationException {
        PropertyValidator.assertOneOrLess(str, getProperties());
    }

    @Override // net.fortuna.ical4j.model.component.CalendarComponent
    protected Validator getValidator(Method method) {
        return this.methodValidators.get(method);
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

    public final LastModified getLastModified() {
        return (LastModified) getProperty(Property.LAST_MODIFIED);
    }

    public final Organizer getOrganizer() {
        return (Organizer) getProperty(Property.ORGANIZER);
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

    public final Uid getUid() {
        return (Uid) getProperty(Property.UID);
    }

    public static class Factory extends Content.Factory implements ComponentFactory<VJournal> {
        public Factory() {
            super(Component.VJOURNAL);
        }

        @Override // net.fortuna.ical4j.model.ComponentFactory
        public VJournal createComponent() {
            return new VJournal(false);
        }

        @Override // net.fortuna.ical4j.model.ComponentFactory
        public VJournal createComponent(PropertyList propertyList) {
            return new VJournal(propertyList);
        }

        @Override // net.fortuna.ical4j.model.ComponentFactory
        public VJournal createComponent(PropertyList propertyList, ComponentList componentList) {
            throw new UnsupportedOperationException(String.format("%s does not support sub-components", Component.VJOURNAL));
        }
    }
}
