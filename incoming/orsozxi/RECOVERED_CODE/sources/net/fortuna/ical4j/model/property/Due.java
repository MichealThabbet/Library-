package net.fortuna.ical4j.model.property;

import java.io.IOException;
import java.net.URISyntaxException;
import java.text.ParseException;
import net.fortuna.ical4j.model.Content;
import net.fortuna.ical4j.model.Date;
import net.fortuna.ical4j.model.DateTime;
import net.fortuna.ical4j.model.ParameterList;
import net.fortuna.ical4j.model.Property;
import net.fortuna.ical4j.model.PropertyFactory;
import net.fortuna.ical4j.model.TimeZone;

/* loaded from: classes3.dex */
public class Due extends DateProperty {
    private static final long serialVersionUID = -2965312347832730406L;

    public Due() {
        super(Property.DUE, new Factory());
        setDate(new DateTime(true));
    }

    public Due(TimeZone timeZone) {
        super(Property.DUE, timeZone, new Factory());
    }

    public Due(String str) throws ParseException {
        super(Property.DUE, new Factory());
        setValue(str);
    }

    public Due(String str, TimeZone timeZone) throws ParseException {
        super(Property.DUE, timeZone, new Factory());
        setValue(str);
    }

    public Due(ParameterList parameterList, String str) throws ParseException {
        super(Property.DUE, parameterList, new Factory());
        setValue(str);
    }

    public Due(Date date) {
        super(Property.DUE, new Factory());
        setDate(date);
    }

    public Due(ParameterList parameterList, Date date) {
        super(Property.DUE, parameterList, new Factory());
        setDate(date);
    }

    public static class Factory extends Content.Factory implements PropertyFactory {
        private static final long serialVersionUID = 1;

        public Factory() {
            super(Property.DUE);
        }

        @Override // net.fortuna.ical4j.model.PropertyFactory
        public Property createProperty(ParameterList parameterList, String str) throws URISyntaxException, IOException, ParseException {
            return new Due(parameterList, str);
        }

        @Override // net.fortuna.ical4j.model.PropertyFactory
        public Property createProperty() {
            return new Due();
        }
    }
}
