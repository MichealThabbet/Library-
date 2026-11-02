package net.fortuna.ical4j.model.property;

import java.io.IOException;
import java.net.URISyntaxException;
import java.text.ParseException;
import java.util.Arrays;
import net.fortuna.ical4j.model.Content;
import net.fortuna.ical4j.model.Escapable;
import net.fortuna.ical4j.model.Parameter;
import net.fortuna.ical4j.model.ParameterList;
import net.fortuna.ical4j.model.Property;
import net.fortuna.ical4j.model.PropertyFactory;
import net.fortuna.ical4j.validate.PropertyValidator;
import net.fortuna.ical4j.validate.ValidationException;
import net.fortuna.ical4j.validate.ValidationRule;
import net.fortuna.ical4j.validate.Validator;

/* loaded from: classes3.dex */
public class Region extends Property implements Escapable {
    private static final long serialVersionUID = 7753849118575885600L;
    private final Validator<Property> validator;
    private String value;

    public Region() {
        this(null);
    }

    public Region(String str) {
        this(new ParameterList(), str);
    }

    public Region(ParameterList parameterList, String str) {
        super(Property.REGION, parameterList, new Factory());
        this.validator = new PropertyValidator(Arrays.asList(new ValidationRule(ValidationRule.ValidationType.OneOrLess, Parameter.ABBREV)));
        setValue(str);
    }

    @Override // net.fortuna.ical4j.model.Property
    public final void setValue(String str) {
        this.value = str;
    }

    @Override // net.fortuna.ical4j.model.Content
    public final String getValue() {
        return this.value;
    }

    @Override // net.fortuna.ical4j.model.Property
    public void validate() throws ValidationException {
        this.validator.validate(this);
    }

    public static class Factory extends Content.Factory implements PropertyFactory {
        private static final long serialVersionUID = 1;

        public Factory() {
            super(Property.REGION);
        }

        @Override // net.fortuna.ical4j.model.PropertyFactory
        public Property createProperty(ParameterList parameterList, String str) throws URISyntaxException, IOException, ParseException {
            return new Region(parameterList, str);
        }

        @Override // net.fortuna.ical4j.model.PropertyFactory
        public Property createProperty() {
            return new Region();
        }
    }
}
