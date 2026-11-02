package net.fortuna.ical4j.model.property;

import java.io.IOException;
import java.net.URISyntaxException;
import java.text.ParseException;
import java.util.Arrays;
import net.fortuna.ical4j.model.Content;
import net.fortuna.ical4j.model.Parameter;
import net.fortuna.ical4j.model.ParameterList;
import net.fortuna.ical4j.model.Property;
import net.fortuna.ical4j.model.PropertyFactory;
import net.fortuna.ical4j.model.TextList;
import net.fortuna.ical4j.validate.PropertyValidator;
import net.fortuna.ical4j.validate.ValidationException;
import net.fortuna.ical4j.validate.ValidationRule;
import net.fortuna.ical4j.validate.Validator;

/* loaded from: classes3.dex */
public class Resources extends Property {
    private static final long serialVersionUID = -848562477226746807L;
    private TextList resources;
    private final Validator<Property> validator;

    public Resources() {
        super(Property.RESOURCES, new ParameterList(), new Factory());
        this.validator = new PropertyValidator(Arrays.asList(new ValidationRule(ValidationRule.ValidationType.OneOrLess, Parameter.ALTREP, Parameter.LANGUAGE)));
        this.resources = new TextList();
    }

    public Resources(ParameterList parameterList, String str) {
        super(Property.RESOURCES, parameterList, new Factory());
        this.validator = new PropertyValidator(Arrays.asList(new ValidationRule(ValidationRule.ValidationType.OneOrLess, Parameter.ALTREP, Parameter.LANGUAGE)));
        setValue(str);
    }

    public Resources(TextList textList) {
        super(Property.RESOURCES, new ParameterList(), new Factory());
        this.validator = new PropertyValidator(Arrays.asList(new ValidationRule(ValidationRule.ValidationType.OneOrLess, Parameter.ALTREP, Parameter.LANGUAGE)));
        this.resources = textList;
    }

    public Resources(ParameterList parameterList, TextList textList) {
        super(Property.RESOURCES, parameterList, new Factory());
        this.validator = new PropertyValidator(Arrays.asList(new ValidationRule(ValidationRule.ValidationType.OneOrLess, Parameter.ALTREP, Parameter.LANGUAGE)));
        this.resources = textList;
    }

    public final TextList getResources() {
        return this.resources;
    }

    @Override // net.fortuna.ical4j.model.Property
    public final void setValue(String str) {
        this.resources = new TextList(str);
    }

    @Override // net.fortuna.ical4j.model.Content
    public final String getValue() {
        return getResources().toString();
    }

    @Override // net.fortuna.ical4j.model.Property
    public void validate() throws ValidationException {
        this.validator.validate(this);
    }

    public static class Factory extends Content.Factory implements PropertyFactory {
        private static final long serialVersionUID = 1;

        public Factory() {
            super(Property.RESOURCES);
        }

        @Override // net.fortuna.ical4j.model.PropertyFactory
        public Property createProperty(ParameterList parameterList, String str) throws URISyntaxException, IOException, ParseException {
            return new Resources(parameterList, str);
        }

        @Override // net.fortuna.ical4j.model.PropertyFactory
        public Property createProperty() {
            return new Resources();
        }
    }
}
