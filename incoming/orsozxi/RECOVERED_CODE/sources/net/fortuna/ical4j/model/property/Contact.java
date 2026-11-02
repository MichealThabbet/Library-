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
public class Contact extends Property implements Escapable {
    private static final long serialVersionUID = -4776654229643771385L;
    private Validator<Property> validator;
    private String value;

    public Contact() {
        super(Property.CONTACT, new ParameterList(), new Factory());
        this.validator = new PropertyValidator(Arrays.asList(new ValidationRule(ValidationRule.ValidationType.OneOrLess, Parameter.ALTREP, Parameter.LANGUAGE)));
    }

    public Contact(String str) {
        super(Property.CONTACT, new ParameterList(), new Factory());
        this.validator = new PropertyValidator(Arrays.asList(new ValidationRule(ValidationRule.ValidationType.OneOrLess, Parameter.ALTREP, Parameter.LANGUAGE)));
        setValue(str);
    }

    public Contact(ParameterList parameterList, String str) {
        super(Property.CONTACT, parameterList, new Factory());
        this.validator = new PropertyValidator(Arrays.asList(new ValidationRule(ValidationRule.ValidationType.OneOrLess, Parameter.ALTREP, Parameter.LANGUAGE)));
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
            super(Property.CONTACT);
        }

        @Override // net.fortuna.ical4j.model.PropertyFactory
        public Property createProperty(ParameterList parameterList, String str) throws URISyntaxException, IOException, ParseException {
            return new Contact(parameterList, str);
        }

        @Override // net.fortuna.ical4j.model.PropertyFactory
        public Property createProperty() {
            return new Contact();
        }
    }
}
