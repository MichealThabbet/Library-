package net.fortuna.ical4j.model.property;

import java.io.IOException;
import java.net.URISyntaxException;
import java.text.ParseException;
import java.util.Arrays;
import net.fortuna.ical4j.model.Content;
import net.fortuna.ical4j.model.LocationTypeList;
import net.fortuna.ical4j.model.Parameter;
import net.fortuna.ical4j.model.ParameterList;
import net.fortuna.ical4j.model.Property;
import net.fortuna.ical4j.model.PropertyFactory;
import net.fortuna.ical4j.validate.PropertyValidator;
import net.fortuna.ical4j.validate.ValidationException;
import net.fortuna.ical4j.validate.ValidationRule;
import net.fortuna.ical4j.validate.Validator;

/* loaded from: classes3.dex */
public class LocationType extends Property {
    private static final long serialVersionUID = -3541686430899510312L;
    private LocationTypeList locationTypes;
    private final Validator<Property> validator;

    public LocationType() {
        super(Property.LOCATION_TYPE, new ParameterList(), new Factory());
        this.validator = new PropertyValidator(Arrays.asList(new ValidationRule(ValidationRule.ValidationType.OneOrLess, Parameter.LANGUAGE)));
        this.locationTypes = new LocationTypeList();
    }

    public LocationType(String str) {
        super(Property.LOCATION_TYPE, new ParameterList(), new Factory());
        this.validator = new PropertyValidator(Arrays.asList(new ValidationRule(ValidationRule.ValidationType.OneOrLess, Parameter.LANGUAGE)));
        setValue(str);
    }

    public LocationType(ParameterList parameterList, String str) {
        super(Property.LOCATION_TYPE, parameterList, new Factory());
        this.validator = new PropertyValidator(Arrays.asList(new ValidationRule(ValidationRule.ValidationType.OneOrLess, Parameter.LANGUAGE)));
        setValue(str);
    }

    public LocationType(LocationTypeList locationTypeList) {
        super(Property.LOCATION_TYPE, new ParameterList(), new Factory());
        this.validator = new PropertyValidator(Arrays.asList(new ValidationRule(ValidationRule.ValidationType.OneOrLess, Parameter.LANGUAGE)));
        this.locationTypes = locationTypeList;
    }

    public LocationType(ParameterList parameterList, LocationTypeList locationTypeList) {
        super(Property.LOCATION_TYPE, parameterList, new Factory());
        this.validator = new PropertyValidator(Arrays.asList(new ValidationRule(ValidationRule.ValidationType.OneOrLess, Parameter.LANGUAGE)));
        this.locationTypes = locationTypeList;
    }

    @Override // net.fortuna.ical4j.model.Property
    public final void setValue(String str) {
        this.locationTypes = new LocationTypeList(str);
    }

    public final LocationTypeList getLocationTypes() {
        return this.locationTypes;
    }

    @Override // net.fortuna.ical4j.model.Content
    public final String getValue() {
        return getLocationTypes().toString();
    }

    @Override // net.fortuna.ical4j.model.Property
    public void validate() throws ValidationException {
        this.validator.validate(this);
    }

    public static class Factory extends Content.Factory implements PropertyFactory {
        private static final long serialVersionUID = 1;

        public Factory() {
            super(Property.LOCATION_TYPE);
        }

        @Override // net.fortuna.ical4j.model.PropertyFactory
        public Property createProperty(ParameterList parameterList, String str) throws URISyntaxException, IOException, ParseException {
            return new LocationType(parameterList, str);
        }

        @Override // net.fortuna.ical4j.model.PropertyFactory
        public Property createProperty() {
            return new LocationType();
        }
    }
}
