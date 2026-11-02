package net.fortuna.ical4j.model.property;

import net.fortuna.ical4j.model.Content;
import net.fortuna.ical4j.model.ParameterList;
import net.fortuna.ical4j.model.Property;
import net.fortuna.ical4j.model.PropertyFactory;
import net.fortuna.ical4j.validate.ValidationException;

/* loaded from: classes3.dex */
public class Conference extends Property {
    public static final String PROPERTY_NAME = "CONFERENCE";
    private static final long serialVersionUID = 1;
    private String value;

    @Override // net.fortuna.ical4j.model.Property
    public void validate() throws ValidationException {
    }

    public Conference() {
        super(PROPERTY_NAME, new Factory());
    }

    public Conference(ParameterList parameterList, String str) {
        super(PROPERTY_NAME, parameterList, new Factory());
        setValue(str);
    }

    @Override // net.fortuna.ical4j.model.Property
    public void setValue(String str) {
        this.value = str;
    }

    @Override // net.fortuna.ical4j.model.Content
    public String getValue() {
        return this.value;
    }

    public static class Factory extends Content.Factory implements PropertyFactory<Conference> {
        public Factory() {
            super(Conference.PROPERTY_NAME);
        }

        @Override // net.fortuna.ical4j.model.PropertyFactory
        public Conference createProperty() {
            return new Conference();
        }

        @Override // net.fortuna.ical4j.model.PropertyFactory
        public Conference createProperty(ParameterList parameterList, String str) {
            return new Conference(parameterList, str);
        }
    }
}
