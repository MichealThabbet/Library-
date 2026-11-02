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
public class Locality extends Property implements Escapable {
    private static final long serialVersionUID = -2324296161017475527L;
    private String value;

    @Override // net.fortuna.ical4j.model.Property
    public void validate() throws ValidationException {
    }

    public Locality() {
        super(Property.LOCALITY, new Factory());
    }

    public Locality(String str) {
        super(Property.LOCALITY, new Factory());
        setValue(str);
    }

    public Locality(ParameterList parameterList, String str) {
        super(Property.LOCALITY, parameterList, new Factory());
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

    public static class Factory extends Content.Factory implements PropertyFactory {
        private static final long serialVersionUID = 1;

        public Factory() {
            super(Property.LOCALITY);
        }

        @Override // net.fortuna.ical4j.model.PropertyFactory
        public Property createProperty(ParameterList parameterList, String str) throws URISyntaxException, IOException, ParseException {
            return new Locality(parameterList, str);
        }

        @Override // net.fortuna.ical4j.model.PropertyFactory
        public Property createProperty() {
            return new Locality();
        }
    }
}
