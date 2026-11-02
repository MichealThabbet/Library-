package net.fortuna.ical4j.model.property;

import java.io.IOException;
import java.net.URISyntaxException;
import java.text.ParseException;
import java.util.Arrays;
import java.util.StringTokenizer;
import net.fortuna.ical4j.model.Content;
import net.fortuna.ical4j.model.Parameter;
import net.fortuna.ical4j.model.ParameterList;
import net.fortuna.ical4j.model.Property;
import net.fortuna.ical4j.model.PropertyFactory;
import net.fortuna.ical4j.validate.PropertyValidator;
import net.fortuna.ical4j.validate.ValidationException;
import net.fortuna.ical4j.validate.ValidationRule;
import net.fortuna.ical4j.validate.Validator;

/* loaded from: classes3.dex */
public class RequestStatus extends Property {
    public static final String CLIENT_ERROR = "3";
    public static final String PRELIM_SUCCESS = "1";
    public static final String SCHEDULING_ERROR = "4";
    public static final String SUCCESS = "2";
    private static final long serialVersionUID = -3273944031884755345L;
    private String description;
    private String exData;
    private String statusCode;
    private final Validator<Property> validator;

    public RequestStatus() {
        super(Property.REQUEST_STATUS, new ParameterList(), new Factory());
        this.validator = new PropertyValidator(Arrays.asList(new ValidationRule(ValidationRule.ValidationType.OneOrLess, Parameter.LANGUAGE)));
    }

    public RequestStatus(ParameterList parameterList, String str) {
        super(Property.REQUEST_STATUS, parameterList, new Factory());
        this.validator = new PropertyValidator(Arrays.asList(new ValidationRule(ValidationRule.ValidationType.OneOrLess, Parameter.LANGUAGE)));
        setValue(str);
    }

    public RequestStatus(String str, String str2, String str3) {
        super(Property.REQUEST_STATUS, new ParameterList(), new Factory());
        this.validator = new PropertyValidator(Arrays.asList(new ValidationRule(ValidationRule.ValidationType.OneOrLess, Parameter.LANGUAGE)));
        this.statusCode = str;
        this.description = str2;
        this.exData = str3;
    }

    public RequestStatus(ParameterList parameterList, String str, String str2, String str3) {
        super(Property.REQUEST_STATUS, parameterList, new Factory());
        this.validator = new PropertyValidator(Arrays.asList(new ValidationRule(ValidationRule.ValidationType.OneOrLess, Parameter.LANGUAGE)));
        this.statusCode = str;
        this.description = str2;
        this.exData = str3;
    }

    public final String getDescription() {
        return this.description;
    }

    public final String getExData() {
        return this.exData;
    }

    public final String getStatusCode() {
        return this.statusCode;
    }

    @Override // net.fortuna.ical4j.model.Property
    public final void setValue(String str) {
        StringTokenizer stringTokenizer = new StringTokenizer(str, ";");
        if (stringTokenizer.hasMoreTokens()) {
            this.statusCode = stringTokenizer.nextToken();
        }
        if (stringTokenizer.hasMoreTokens()) {
            this.description = stringTokenizer.nextToken();
        }
        if (stringTokenizer.hasMoreTokens()) {
            this.exData = stringTokenizer.nextToken();
        }
    }

    @Override // net.fortuna.ical4j.model.Content
    public final String getValue() {
        StringBuilder sb = new StringBuilder();
        if (getStatusCode() != null) {
            sb.append(getStatusCode());
        }
        if (getDescription() != null) {
            sb.append(';');
            sb.append(getDescription());
        }
        if (getExData() != null) {
            sb.append(';');
            sb.append(getExData());
        }
        return sb.toString();
    }

    public final void setDescription(String str) {
        this.description = str;
    }

    public final void setExData(String str) {
        this.exData = str;
    }

    public final void setStatusCode(String str) {
        this.statusCode = str;
    }

    @Override // net.fortuna.ical4j.model.Property
    public void validate() throws ValidationException {
        this.validator.validate(this);
    }

    public static class Factory extends Content.Factory implements PropertyFactory {
        private static final long serialVersionUID = 1;

        public Factory() {
            super(Property.REQUEST_STATUS);
        }

        @Override // net.fortuna.ical4j.model.PropertyFactory
        public Property createProperty(ParameterList parameterList, String str) throws URISyntaxException, IOException, ParseException {
            return new RequestStatus(parameterList, str);
        }

        @Override // net.fortuna.ical4j.model.PropertyFactory
        public Property createProperty() {
            return new RequestStatus();
        }
    }
}
