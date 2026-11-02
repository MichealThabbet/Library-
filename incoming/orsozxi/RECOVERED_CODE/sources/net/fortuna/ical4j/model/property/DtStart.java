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
public class DtStart extends DateProperty {
    private static final long serialVersionUID = -5707097476081111815L;

    public DtStart() {
        super(Property.DTSTART, new Factory());
    }

    public DtStart(TimeZone timeZone) {
        super(Property.DTSTART, timeZone, new Factory());
    }

    public DtStart(String str) throws ParseException {
        super(Property.DTSTART, new Factory());
        setValue(str);
    }

    public DtStart(String str, TimeZone timeZone) throws ParseException {
        super(Property.DTSTART, timeZone, new Factory());
        setValue(str);
    }

    public DtStart(ParameterList parameterList, String str) throws ParseException {
        super(Property.DTSTART, parameterList, new Factory());
        setValue(str);
    }

    public DtStart(Date date) {
        super(Property.DTSTART, new Factory());
        setDate(date);
    }

    public DtStart(Date date, boolean z) {
        super(Property.DTSTART, new Factory());
        setDate(date);
        setUtc(z);
    }

    public DtStart(ParameterList parameterList, Date date) {
        super(Property.DTSTART, parameterList, new Factory());
        setDate(date);
    }

    public static class Factory extends Content.Factory implements PropertyFactory {
        private static final long serialVersionUID = 1;

        public Factory() {
            super(Property.DTSTART);
        }

        @Override // net.fortuna.ical4j.model.PropertyFactory
        public Property createProperty(ParameterList parameterList, String str) throws URISyntaxException, IOException, ParseException {
            return new DtStart(parameterList, str);
        }

        @Override // net.fortuna.ical4j.model.PropertyFactory
        public Property createProperty() {
            return new DtStart();
        }
    }
}
