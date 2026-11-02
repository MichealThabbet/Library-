package net.fortuna.ical4j.model.property;

import java.io.IOException;
import java.net.URISyntaxException;
import java.text.ParseException;
import net.fortuna.ical4j.model.Content;
import net.fortuna.ical4j.model.DateTime;
import net.fortuna.ical4j.model.ParameterList;
import net.fortuna.ical4j.model.Property;
import net.fortuna.ical4j.model.PropertyFactory;

/* loaded from: classes3.dex */
public class Created extends UtcProperty {
    private static final long serialVersionUID = -8658935097721652961L;

    public Created() {
        super(Property.CREATED, new Factory());
    }

    public Created(String str) throws ParseException {
        super(Property.CREATED, new Factory());
        setValue(str);
    }

    public Created(ParameterList parameterList, String str) throws ParseException {
        super(Property.CREATED, parameterList, new Factory());
        setValue(str);
    }

    public Created(DateTime dateTime) {
        super(Property.CREATED, new Factory());
        dateTime.setUtc(true);
        setDate(dateTime);
    }

    public Created(ParameterList parameterList, DateTime dateTime) {
        super(Property.CREATED, parameterList, new Factory());
        dateTime.setUtc(true);
        setDate(dateTime);
    }

    public static class Factory extends Content.Factory implements PropertyFactory {
        private static final long serialVersionUID = 1;

        public Factory() {
            super(Property.CREATED);
        }

        @Override // net.fortuna.ical4j.model.PropertyFactory
        public Property createProperty(ParameterList parameterList, String str) throws URISyntaxException, IOException, ParseException {
            return new Created(parameterList, str);
        }

        @Override // net.fortuna.ical4j.model.PropertyFactory
        public Property createProperty() {
            return new Created();
        }
    }
}
