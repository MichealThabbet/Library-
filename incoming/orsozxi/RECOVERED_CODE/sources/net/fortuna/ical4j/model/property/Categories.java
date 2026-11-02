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
public class Categories extends Property {
    private static final long serialVersionUID = -7769987073466681634L;
    private TextList categories;
    private Validator<Property> validator;

    public Categories() {
        super(Property.CATEGORIES, new ParameterList(), new Factory());
        this.validator = new PropertyValidator(Arrays.asList(new ValidationRule(ValidationRule.ValidationType.OneOrLess, Parameter.LANGUAGE)));
        this.categories = new TextList();
    }

    public Categories(String str) {
        super(Property.CATEGORIES, new ParameterList(), new Factory());
        this.validator = new PropertyValidator(Arrays.asList(new ValidationRule(ValidationRule.ValidationType.OneOrLess, Parameter.LANGUAGE)));
        setValue(str);
    }

    public Categories(ParameterList parameterList, String str) {
        super(Property.CATEGORIES, parameterList, new Factory());
        this.validator = new PropertyValidator(Arrays.asList(new ValidationRule(ValidationRule.ValidationType.OneOrLess, Parameter.LANGUAGE)));
        setValue(str);
    }

    public Categories(TextList textList) {
        super(Property.CATEGORIES, new ParameterList(), new Factory());
        this.validator = new PropertyValidator(Arrays.asList(new ValidationRule(ValidationRule.ValidationType.OneOrLess, Parameter.LANGUAGE)));
        this.categories = textList;
    }

    public Categories(ParameterList parameterList, TextList textList) {
        super(Property.CATEGORIES, parameterList, new Factory());
        this.validator = new PropertyValidator(Arrays.asList(new ValidationRule(ValidationRule.ValidationType.OneOrLess, Parameter.LANGUAGE)));
        this.categories = textList;
    }

    @Override // net.fortuna.ical4j.model.Property
    public final void setValue(String str) {
        this.categories = new TextList(str);
    }

    public final TextList getCategories() {
        return this.categories;
    }

    @Override // net.fortuna.ical4j.model.Content
    public final String getValue() {
        return getCategories().toString();
    }

    public static class Factory extends Content.Factory implements PropertyFactory {
        private static final long serialVersionUID = 1;

        public Factory() {
            super(Property.CATEGORIES);
        }

        @Override // net.fortuna.ical4j.model.PropertyFactory
        public Property createProperty(ParameterList parameterList, String str) throws URISyntaxException, IOException, ParseException {
            return new Categories(parameterList, str);
        }

        @Override // net.fortuna.ical4j.model.PropertyFactory
        public Property createProperty() {
            return new Categories();
        }
    }

    @Override // net.fortuna.ical4j.model.Property
    public void validate() throws ValidationException {
        this.validator.validate(this);
    }
}
