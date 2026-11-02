package net.fortuna.ical4j.model.component;

import java.util.Arrays;
import java.util.function.Consumer;
import net.fortuna.ical4j.model.Component;
import net.fortuna.ical4j.model.ComponentFactory;
import net.fortuna.ical4j.model.ComponentList;
import net.fortuna.ical4j.model.Content;
import net.fortuna.ical4j.model.Parameter;
import net.fortuna.ical4j.model.Property;
import net.fortuna.ical4j.model.PropertyList;
import net.fortuna.ical4j.model.parameter.Value;
import net.fortuna.ical4j.model.property.DtEnd;
import net.fortuna.ical4j.model.property.DtStamp;
import net.fortuna.ical4j.model.property.DtStart;
import net.fortuna.ical4j.model.property.Method;
import net.fortuna.ical4j.validate.PropertyValidator;
import net.fortuna.ical4j.validate.ValidationException;
import net.fortuna.ical4j.validate.Validator;

/* loaded from: classes3.dex */
public class VAvailability extends CalendarComponent {
    private static final long serialVersionUID = -3001603309266267258L;
    private ComponentList<Available> available;

    @Override // net.fortuna.ical4j.model.component.CalendarComponent
    protected Validator getValidator(Method method) {
        return null;
    }

    public VAvailability() {
        this(true);
    }

    public VAvailability(boolean z) {
        super(Component.VAVAILABILITY);
        this.available = new ComponentList<>();
        if (z) {
            getProperties().add((PropertyList<Property>) new DtStamp());
        }
    }

    public VAvailability(PropertyList propertyList) {
        super(Component.VAVAILABILITY, propertyList);
        this.available = new ComponentList<>();
    }

    public VAvailability(PropertyList propertyList, ComponentList<Available> componentList) {
        super(Component.VAVAILABILITY, propertyList);
        this.available = componentList;
    }

    public final ComponentList<Available> getAvailable() {
        return this.available;
    }

    @Override // net.fortuna.ical4j.model.Component
    public final String toString() {
        return "BEGIN:" + getName() + "\r\n" + getProperties() + getAvailable() + "END:" + getName() + "\r\n";
    }

    public /* synthetic */ void lambda$validate$0$VAvailability(String str) throws ValidationException {
        PropertyValidator.assertOne(str, getProperties());
    }

    @Override // net.fortuna.ical4j.model.Component
    public final void validate(boolean z) throws ValidationException {
        Arrays.asList(Property.DTSTART, Property.DTSTAMP, Property.UID).forEach(new Consumer() { // from class: net.fortuna.ical4j.model.component.-$$Lambda$VAvailability$0B7gDjnQZmTRq_wmW19ClVJKxEQ
            @Override // java.util.function.Consumer
            public final void accept(Object obj) throws ValidationException {
                this.f$0.lambda$validate$0$VAvailability((String) obj);
            }
        });
        if (Value.DATE.equals(((DtStart) getProperty(Property.DTSTART)).getParameter(Parameter.VALUE))) {
            throw new ValidationException("Property [DTSTART] must be a " + Value.DATE_TIME);
        }
        if (getProperty(Property.DTEND) != null) {
            PropertyValidator.assertOne(Property.DTEND, getProperties());
            if (Value.DATE.equals(((DtEnd) getProperty(Property.DTEND)).getParameter(Parameter.VALUE))) {
                throw new ValidationException("Property [DTEND] must be a " + Value.DATE_TIME);
            }
            if (getProperty(Property.DURATION) != null) {
                throw new ValidationException("Only one of Property [DTEND] or [DURATION must appear a VAVAILABILITY");
            }
        }
        Arrays.asList(Property.BUSYTYPE, Property.CREATED, Property.LAST_MODIFIED, Property.ORGANIZER, Property.SEQUENCE, Property.SUMMARY, Property.URL).forEach(new Consumer() { // from class: net.fortuna.ical4j.model.component.-$$Lambda$VAvailability$CVu_eFYedbUeTPSXQj-20rdhq-I
            @Override // java.util.function.Consumer
            public final void accept(Object obj) throws ValidationException {
                this.f$0.lambda$validate$1$VAvailability((String) obj);
            }
        });
        if (z) {
            validateProperties();
        }
    }

    public /* synthetic */ void lambda$validate$1$VAvailability(String str) throws ValidationException {
        PropertyValidator.assertOneOrLess(str, getProperties());
    }

    public static class Factory extends Content.Factory implements ComponentFactory<VAvailability> {
        public Factory() {
            super(Component.VAVAILABILITY);
        }

        @Override // net.fortuna.ical4j.model.ComponentFactory
        public VAvailability createComponent() {
            return new VAvailability(false);
        }

        @Override // net.fortuna.ical4j.model.ComponentFactory
        public VAvailability createComponent(PropertyList propertyList) {
            return new VAvailability(propertyList);
        }

        @Override // net.fortuna.ical4j.model.ComponentFactory
        public VAvailability createComponent(PropertyList propertyList, ComponentList componentList) {
            return new VAvailability(propertyList, componentList);
        }
    }
}
