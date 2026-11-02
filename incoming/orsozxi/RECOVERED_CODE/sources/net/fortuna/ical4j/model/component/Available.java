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
import net.fortuna.ical4j.model.property.DtStart;
import net.fortuna.ical4j.validate.PropertyValidator;
import net.fortuna.ical4j.validate.ValidationException;

/* loaded from: classes3.dex */
public class Available extends Component {
    private static final long serialVersionUID = -2494710612002978763L;

    public Available() {
        super(Component.AVAILABLE);
    }

    public Available(PropertyList propertyList) {
        super(Component.AVAILABLE, propertyList);
    }

    public /* synthetic */ void lambda$validate$0$Available(String str) throws ValidationException {
        PropertyValidator.assertOne(str, getProperties());
    }

    @Override // net.fortuna.ical4j.model.Component
    public final void validate(boolean z) throws ValidationException {
        Arrays.asList(Property.DTSTART, Property.DTSTAMP, Property.UID).forEach(new Consumer() { // from class: net.fortuna.ical4j.model.component.-$$Lambda$Available$Yy5s-0akNSsu7UkZ9b1iQ92yf6I
            @Override // java.util.function.Consumer
            public final void accept(Object obj) throws ValidationException {
                this.f$0.lambda$validate$0$Available((String) obj);
            }
        });
        if (Value.DATE.equals(((DtStart) getProperty(Property.DTSTART)).getParameter(Parameter.VALUE))) {
            throw new ValidationException("Property [DTSTART] must be a " + Value.DATE_TIME);
        }
        Arrays.asList(Property.CREATED, Property.LAST_MODIFIED, Property.RECURRENCE_ID, Property.RRULE, Property.SUMMARY).forEach(new Consumer() { // from class: net.fortuna.ical4j.model.component.-$$Lambda$Available$wiuYqTvErA3brfvcJDjZbMLTSxw
            @Override // java.util.function.Consumer
            public final void accept(Object obj) throws ValidationException {
                this.f$0.lambda$validate$1$Available((String) obj);
            }
        });
        if (getProperty(Property.DTEND) != null) {
            PropertyValidator.assertOne(Property.DTEND, getProperties());
            if (Value.DATE.equals(((DtEnd) getProperty(Property.DTEND)).getParameter(Parameter.VALUE))) {
                throw new ValidationException("Property [DTEND] must be a " + Value.DATE_TIME);
            }
        } else {
            PropertyValidator.assertOne(Property.DURATION, getProperties());
        }
        if (z) {
            validateProperties();
        }
    }

    public /* synthetic */ void lambda$validate$1$Available(String str) throws ValidationException {
        PropertyValidator.assertOneOrLess(str, getProperties());
    }

    public static class Factory extends Content.Factory implements ComponentFactory<Available> {
        public Factory() {
            super(Component.AVAILABLE);
        }

        @Override // net.fortuna.ical4j.model.ComponentFactory
        public Available createComponent() {
            return new Available();
        }

        @Override // net.fortuna.ical4j.model.ComponentFactory
        public Available createComponent(PropertyList propertyList) {
            return new Available(propertyList);
        }

        @Override // net.fortuna.ical4j.model.ComponentFactory
        public Available createComponent(PropertyList propertyList, ComponentList componentList) {
            throw new UnsupportedOperationException(String.format("%s does not support sub-components", Component.AVAILABLE));
        }
    }
}
