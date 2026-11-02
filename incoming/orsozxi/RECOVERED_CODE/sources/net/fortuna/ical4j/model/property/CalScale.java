package net.fortuna.ical4j.model.property;

import java.io.IOException;
import java.net.URISyntaxException;
import java.text.ParseException;
import net.fortuna.ical4j.model.Content;
import net.fortuna.ical4j.model.ParameterList;
import net.fortuna.ical4j.model.Property;
import net.fortuna.ical4j.model.PropertyFactory;
import net.fortuna.ical4j.util.CompatibilityHints;
import net.fortuna.ical4j.validate.ValidationException;

/* loaded from: classes3.dex */
public class CalScale extends Property {
    public static final CalScale GREGORIAN = new ImmutableCalScale("GREGORIAN");
    private static final long serialVersionUID = 7446184786984981423L;
    private String value;

    private static final class ImmutableCalScale extends CalScale {
        private static final long serialVersionUID = 1750949550694413878L;

        private ImmutableCalScale(String str) {
            super(new ParameterList(true), str);
        }

        @Override // net.fortuna.ical4j.model.property.CalScale, net.fortuna.ical4j.model.Property
        public void setValue(String str) {
            throw new UnsupportedOperationException("Cannot modify constant instances");
        }
    }

    public CalScale() {
        super(Property.CALSCALE, new Factory());
    }

    public CalScale(String str) {
        super(Property.CALSCALE, new Factory());
        this.value = str;
    }

    public CalScale(ParameterList parameterList, String str) {
        super(Property.CALSCALE, parameterList, new Factory());
        this.value = str;
    }

    @Override // net.fortuna.ical4j.model.Property
    public void setValue(String str) {
        this.value = str;
    }

    @Override // net.fortuna.ical4j.model.Content
    public final String getValue() {
        return this.value;
    }

    @Override // net.fortuna.ical4j.model.Property
    public final void validate() throws ValidationException {
        if (CompatibilityHints.isHintEnabled(CompatibilityHints.KEY_RELAXED_VALIDATION)) {
            if (GREGORIAN.getValue().equalsIgnoreCase(this.value)) {
                return;
            }
            throw new ValidationException("Invalid value [" + this.value + "]");
        }
        if (GREGORIAN.getValue().equals(this.value)) {
            return;
        }
        throw new ValidationException("Invalid value [" + this.value + "]");
    }

    public static class Factory extends Content.Factory implements PropertyFactory<CalScale> {
        private static final long serialVersionUID = 1;

        public Factory() {
            super(Property.CALSCALE);
        }

        @Override // net.fortuna.ical4j.model.PropertyFactory
        public CalScale createProperty(ParameterList parameterList, String str) throws URISyntaxException, IOException, ParseException {
            if (CalScale.GREGORIAN.getValue().equals(str)) {
                return CalScale.GREGORIAN;
            }
            return new CalScale(parameterList, str);
        }

        @Override // net.fortuna.ical4j.model.PropertyFactory
        public CalScale createProperty() {
            return new CalScale();
        }
    }
}
