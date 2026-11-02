package net.fortuna.ical4j.model.property;

import net.fortuna.ical4j.model.Content;
import net.fortuna.ical4j.model.ParameterList;
import net.fortuna.ical4j.model.Property;
import net.fortuna.ical4j.model.PropertyFactory;
import net.fortuna.ical4j.validate.ValidationException;

/* loaded from: classes3.dex */
public class Image extends Property {
    public static final String PROPERTY_NAME = "IMAGE";
    private static final long serialVersionUID = 1;
    private String value;

    @Override // net.fortuna.ical4j.model.Property
    public void validate() throws ValidationException {
    }

    public Image() {
        super("IMAGE", new Factory());
    }

    public Image(ParameterList parameterList, String str) {
        super("IMAGE", parameterList, new Factory());
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

    public static class Factory extends Content.Factory implements PropertyFactory<Image> {
        public Factory() {
            super("IMAGE");
        }

        @Override // net.fortuna.ical4j.model.PropertyFactory
        public Image createProperty() {
            return new Image();
        }

        @Override // net.fortuna.ical4j.model.PropertyFactory
        public Image createProperty(ParameterList parameterList, String str) {
            return new Image(parameterList, str);
        }
    }
}
