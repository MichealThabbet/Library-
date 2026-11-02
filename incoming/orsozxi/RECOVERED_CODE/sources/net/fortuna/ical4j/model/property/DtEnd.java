package net.fortuna.ical4j.model.property;

import java.io.IOException;
import java.net.URISyntaxException;
import java.text.ParseException;
import net.fortuna.ical4j.model.Content;
import net.fortuna.ical4j.model.Date;
import net.fortuna.ical4j.model.ParameterList;
import net.fortuna.ical4j.model.Property;
import net.fortuna.ical4j.model.PropertyFactory;
import net.fortuna.ical4j.model.TimeZone;

/* loaded from: classes3.dex */
public class DtEnd extends DateProperty {
    private static final long serialVersionUID = 8107416684717228297L;

    public DtEnd() {
        super(Property.DTEND, new Factory());
    }

    public DtEnd(TimeZone timeZone) {
        super(Property.DTEND, timeZone, new Factory());
    }

    public DtEnd(String str) throws ParseException {
        super(Property.DTEND, new Factory());
        setValue(str);
    }

    public DtEnd(String str, TimeZone timeZone) throws ParseException {
        super(Property.DTEND, timeZone, new Factory());
        setValue(str);
    }

    public DtEnd(ParameterList parameterList, String str) throws ParseException {
        super(Property.DTEND, parameterList, new Factory());
        setValue(str);
    }

    public DtEnd(Date date) {
        super(Property.DTEND, new Factory());
        setDate(date);
    }

    public DtEnd(Date date, boolean z) {
        super(Property.DTEND, new Factory());
        setDate(date);
        setUtc(z);
    }

    public DtEnd(ParameterList parameterList, Date date) {
        super(Property.DTEND, parameterList, new Factory());
        setDate(date);
    }

    public static class Factory extends Content.Factory implements PropertyFactory {
        private static final long serialVersionUID = 1;

        public Factory() {
            super(Property.DTEND);
        }

        @Override // net.fortuna.ical4j.model.PropertyFactory
        public Property createProperty(ParameterList parameterList, String str) throws URISyntaxException, IOException, ParseException {
            return new DtEnd(parameterList, str);
        }

        @Override // net.fortuna.ical4j.model.PropertyFactory
        public Property createProperty() {
            return new DtEnd();
        }
    }
}
