package net.fortuna.ical4j.model.component;

import java.util.Arrays;
import java.util.function.Consumer;
import net.fortuna.ical4j.model.ComponentFactory;
import net.fortuna.ical4j.model.ComponentList;
import net.fortuna.ical4j.model.Content;
import net.fortuna.ical4j.model.Property;
import net.fortuna.ical4j.model.PropertyList;
import net.fortuna.ical4j.model.property.Method;
import net.fortuna.ical4j.validate.PropertyValidator;
import net.fortuna.ical4j.validate.ValidationException;
import net.fortuna.ical4j.validate.Validator;

/* loaded from: classes3.dex */
public class VVenue extends CalendarComponent {
    private static final long serialVersionUID = 4502423035501438515L;

    public VVenue() {
        super("VVENUE");
    }

    public VVenue(PropertyList propertyList) {
        super("VVENUE", propertyList);
    }

    @Override // net.fortuna.ical4j.model.Component
    public final String toString() {
        return "BEGIN:" + getName() + "\r\n" + getProperties() + "END:" + getName() + "\r\n";
    }

    @Override // net.fortuna.ical4j.model.Component
    public final void validate(boolean z) throws ValidationException {
        PropertyValidator.assertOne(Property.UID, getProperties());
        Arrays.asList(Property.NAME, "DESCRIPTION", Property.STREET_ADDRESS, Property.EXTENDED_ADDRESS, Property.LOCALITY, Property.REGION, Property.COUNTRY, Property.POSTALCODE, "TZID", Property.GEO, Property.LOCATION_TYPE, Property.CATEGORIES, Property.DTSTAMP, Property.CREATED, Property.LAST_MODIFIED).forEach(new Consumer() { // from class: net.fortuna.ical4j.model.component.-$$Lambda$VVenue$iJZSqbZY1tWq6JGtExJ08j6pULk
            @Override // java.util.function.Consumer
            public final void accept(Object obj) throws ValidationException {
                this.f$0.lambda$validate$0$VVenue((String) obj);
            }
        });
        if (z) {
            validateProperties();
        }
    }

    public /* synthetic */ void lambda$validate$0$VVenue(String str) throws ValidationException {
        PropertyValidator.assertOneOrLess(str, getProperties());
    }

    @Override // net.fortuna.ical4j.model.component.CalendarComponent
    protected Validator getValidator(Method method) {
        return EMPTY_VALIDATOR;
    }

    public static class Factory extends Content.Factory implements ComponentFactory<VVenue> {
        public Factory() {
            super("VVENUE");
        }

        @Override // net.fortuna.ical4j.model.ComponentFactory
        public VVenue createComponent() {
            return new VVenue();
        }

        @Override // net.fortuna.ical4j.model.ComponentFactory
        public VVenue createComponent(PropertyList propertyList) {
            return new VVenue(propertyList);
        }

        @Override // net.fortuna.ical4j.model.ComponentFactory
        public VVenue createComponent(PropertyList propertyList, ComponentList componentList) {
            throw new UnsupportedOperationException(String.format("%s does not support sub-components", "VVENUE"));
        }
    }
}
