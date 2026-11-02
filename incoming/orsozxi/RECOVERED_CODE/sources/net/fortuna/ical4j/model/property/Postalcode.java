package net.fortuna.ical4j.model.property;

import java.io.IOException;
import java.net.URISyntaxException;
import java.text.ParseException;
import net.fortuna.ical4j.model.Content;
import net.fortuna.ical4j.model.Escapable;
import net.fortuna.ical4j.model.ParameterList;
import net.fortuna.ical4j.model.Property;
import net.fortuna.ical4j.model.PropertyFactory;
import net.fortuna.ical4j.validate.ValidationException;

/* loaded from: classes3.dex */
public class Postalcode extends Property implements Escapable {
    private static final long serialVersionUID = 1983456638722378724L;
    private String value;

    @Override // net.fortuna.ical4j.model.Property
    public void validate() throws ValidationException {
    }

    public Postalcode() {
        super(Property.POSTALCODE, new Factory());
    }

    public Postalcode(String str) {
        super(Property.POSTALCODE, new Factory());
        setValue(str);
    }

    public Postalcode(ParameterList parameterList, String str) {
        super(Property.POSTALCODE, parameterList, new Factory());
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

    public static class Factory extends Content.Factory implements PropertyFactory<Postalcode> {
        private static final long serialVersionUID = 1;

        public Factory() {
            super(Property.POSTALCODE);
        }

        @Override // net.fortuna.ical4j.model.PropertyFactory
        public Postalcode createProperty(ParameterList parameterList, String str) throws URISyntaxException, IOException, ParseException {
            return new Postalcode(parameterList, str);
        }

        @Override // net.fortuna.ical4j.model.PropertyFactory
        public Postalcode createProperty() {
            return new Postalcode();
        }
    }
}
