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
public class LastModified extends UtcProperty {
    private static final long serialVersionUID = 5288572652052836062L;

    public LastModified() {
        super(Property.LAST_MODIFIED, new Factory());
    }

    public LastModified(String str) throws ParseException {
        this(new ParameterList(), str);
    }

    public LastModified(ParameterList parameterList, String str) throws ParseException {
        super(Property.LAST_MODIFIED, parameterList, new Factory());
        setValue(str);
    }

    public LastModified(DateTime dateTime) {
        super(Property.LAST_MODIFIED, new Factory());
        dateTime.setUtc(true);
        setDate(dateTime);
    }

    public LastModified(ParameterList parameterList, DateTime dateTime) {
        super(Property.LAST_MODIFIED, parameterList, new Factory());
        dateTime.setUtc(true);
        setDate(dateTime);
    }

    public static class Factory extends Content.Factory implements PropertyFactory {
        private static final long serialVersionUID = 1;

        public Factory() {
            super(Property.LAST_MODIFIED);
        }

        @Override // net.fortuna.ical4j.model.PropertyFactory
        public Property createProperty(ParameterList parameterList, String str) throws URISyntaxException, IOException, ParseException {
            return new LastModified(parameterList, str);
        }

        @Override // net.fortuna.ical4j.model.PropertyFactory
        public Property createProperty() {
            return new LastModified();
        }
    }
}
