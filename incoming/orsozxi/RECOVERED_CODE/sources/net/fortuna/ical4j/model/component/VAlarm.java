package net.fortuna.ical4j.model.component;

import java.time.temporal.TemporalAmount;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import net.fortuna.ical4j.model.Component;
import net.fortuna.ical4j.model.ComponentFactory;
import net.fortuna.ical4j.model.ComponentList;
import net.fortuna.ical4j.model.Content;
import net.fortuna.ical4j.model.DateTime;
import net.fortuna.ical4j.model.Property;
import net.fortuna.ical4j.model.PropertyList;
import net.fortuna.ical4j.model.property.Action;
import net.fortuna.ical4j.model.property.Attach;
import net.fortuna.ical4j.model.property.Description;
import net.fortuna.ical4j.model.property.Duration;
import net.fortuna.ical4j.model.property.Method;
import net.fortuna.ical4j.model.property.Repeat;
import net.fortuna.ical4j.model.property.Summary;
import net.fortuna.ical4j.model.property.Trigger;
import net.fortuna.ical4j.validate.ComponentValidator;
import net.fortuna.ical4j.validate.PropertyValidator;
import net.fortuna.ical4j.validate.ValidationException;
import net.fortuna.ical4j.validate.ValidationRule;
import net.fortuna.ical4j.validate.Validator;

/* loaded from: classes3.dex */
public class VAlarm extends CalendarComponent {
    private static final long serialVersionUID = -8193965477414653802L;
    private final Map<Action, Validator> actionValidators;

    public VAlarm() {
        super(Component.VALARM);
        HashMap map = new HashMap();
        this.actionValidators = map;
        map.put(Action.AUDIO, new ComponentValidator(Collections.singletonList(new ValidationRule(ValidationRule.ValidationType.OneOrLess, Property.ATTACH))));
        this.actionValidators.put(Action.DISPLAY, new ComponentValidator(Collections.singletonList(new ValidationRule(ValidationRule.ValidationType.One, "DESCRIPTION"))));
        this.actionValidators.put(Action.EMAIL, new ComponentValidator(Arrays.asList(new ValidationRule(ValidationRule.ValidationType.One, "DESCRIPTION", Property.SUMMARY), new ValidationRule(ValidationRule.ValidationType.OneOrMore, Property.ATTENDEE))));
        this.actionValidators.put(Action.PROCEDURE, new ComponentValidator(Arrays.asList(new ValidationRule(ValidationRule.ValidationType.One, Property.ATTACH), new ValidationRule(ValidationRule.ValidationType.OneOrLess, "DESCRIPTION"))));
    }

    public VAlarm(PropertyList propertyList) {
        super(Component.VALARM, propertyList);
        HashMap map = new HashMap();
        this.actionValidators = map;
        map.put(Action.AUDIO, new ComponentValidator(Collections.singletonList(new ValidationRule(ValidationRule.ValidationType.OneOrLess, Property.ATTACH))));
        this.actionValidators.put(Action.DISPLAY, new ComponentValidator(Collections.singletonList(new ValidationRule(ValidationRule.ValidationType.One, "DESCRIPTION"))));
        this.actionValidators.put(Action.EMAIL, new ComponentValidator(Arrays.asList(new ValidationRule(ValidationRule.ValidationType.One, "DESCRIPTION", Property.SUMMARY), new ValidationRule(ValidationRule.ValidationType.OneOrMore, Property.ATTENDEE))));
        this.actionValidators.put(Action.PROCEDURE, new ComponentValidator(Arrays.asList(new ValidationRule(ValidationRule.ValidationType.One, Property.ATTACH), new ValidationRule(ValidationRule.ValidationType.OneOrLess, "DESCRIPTION"))));
    }

    public VAlarm(DateTime dateTime) {
        this();
        getProperties().add((PropertyList<Property>) new Trigger(dateTime));
    }

    public VAlarm(TemporalAmount temporalAmount) {
        this();
        getProperties().add((PropertyList<Property>) new Trigger(temporalAmount));
    }

    @Override // net.fortuna.ical4j.model.Component
    public final void validate(boolean z) throws ValidationException {
        PropertyValidator.assertOne("ACTION", getProperties());
        PropertyValidator.assertOne(Property.TRIGGER, getProperties());
        PropertyValidator.assertOneOrLess(Property.DURATION, getProperties());
        PropertyValidator.assertOneOrLess(Property.REPEAT, getProperties());
        try {
            PropertyValidator.assertNone(Property.DURATION, getProperties());
            PropertyValidator.assertNone(Property.REPEAT, getProperties());
        } catch (ValidationException unused) {
            PropertyValidator.assertOne(Property.DURATION, getProperties());
            PropertyValidator.assertOne(Property.REPEAT, getProperties());
        }
        Validator validator = this.actionValidators.get(getAction());
        if (validator != null) {
            validator.validate(this);
        }
        if (z) {
            validateProperties();
        }
    }

    @Override // net.fortuna.ical4j.model.component.CalendarComponent
    protected Validator getValidator(Method method) {
        throw new UnsupportedOperationException("VALARM validation included in VEVENT or VTODO method validator.");
    }

    public final Action getAction() {
        return (Action) getProperty("ACTION");
    }

    public final Trigger getTrigger() {
        return (Trigger) getProperty(Property.TRIGGER);
    }

    public final Duration getDuration() {
        return (Duration) getProperty(Property.DURATION);
    }

    public final Repeat getRepeat() {
        return (Repeat) getProperty(Property.REPEAT);
    }

    public final Attach getAttachment() {
        return (Attach) getProperty(Property.ATTACH);
    }

    public final Description getDescription() {
        return (Description) getProperty("DESCRIPTION");
    }

    public final Summary getSummary() {
        return (Summary) getProperty(Property.SUMMARY);
    }

    public static class Factory extends Content.Factory implements ComponentFactory<VAlarm> {
        public Factory() {
            super(Component.VALARM);
        }

        @Override // net.fortuna.ical4j.model.ComponentFactory
        public VAlarm createComponent() {
            return new VAlarm();
        }

        @Override // net.fortuna.ical4j.model.ComponentFactory
        public VAlarm createComponent(PropertyList propertyList) {
            return new VAlarm(propertyList);
        }

        @Override // net.fortuna.ical4j.model.ComponentFactory
        public VAlarm createComponent(PropertyList propertyList, ComponentList componentList) {
            throw new UnsupportedOperationException(String.format("%s does not support sub-components", Component.VALARM));
        }
    }
}
