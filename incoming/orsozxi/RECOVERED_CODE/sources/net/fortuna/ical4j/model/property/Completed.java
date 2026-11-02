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
public class Completed extends UtcProperty {
    private static final long serialVersionUID = 6824213281785639181L;

    public Completed() {
        super(Property.COMPLETED, new Factory());
    }

    public Completed(String str) throws ParseException {
        super(Property.COMPLETED, new Factory());
        setValue(str);
    }

    public Completed(ParameterList parameterList, String str) throws ParseException {
        super(Property.COMPLETED, parameterList, new Factory());
        setValue(str);
    }

    public Completed(DateTime dateTime) {
        super(Property.COMPLETED, new Factory());
        dateTime.setUtc(true);
        setDate(dateTime);
    }

    public Completed(ParameterList parameterList, DateTime dateTime) {
        super(Property.COMPLETED, parameterList, new Factory());
        dateTime.setUtc(true);
        setDate(dateTime);
    }

    public static class Factory extends Content.Factory implements PropertyFactory {
        private static final long serialVersionUID = 1;

        public Factory() {
            super(Property.COMPLETED);
        }

        @Override // net.fortuna.ical4j.model.PropertyFactory
        public Property createProperty(ParameterList parameterList, String str) throws URISyntaxException, IOException, ParseException {
            return new Completed(parameterList, str);
        }

        @Override // net.fortuna.ical4j.model.PropertyFactory
        public Property createProperty() {
            return new Completed();
        }
    }
}
