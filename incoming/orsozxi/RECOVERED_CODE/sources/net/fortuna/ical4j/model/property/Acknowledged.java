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
public class Acknowledged extends UtcProperty {
    private static final long serialVersionUID = 596619479148598528L;

    public Acknowledged() {
        super(Property.ACKNOWLEDGED, new Factory());
    }

    public Acknowledged(String str) throws ParseException {
        this(new ParameterList(), str);
    }

    public Acknowledged(ParameterList parameterList, String str) throws ParseException {
        super(Property.ACKNOWLEDGED, parameterList, new Factory());
        setValue(str);
    }

    public Acknowledged(DateTime dateTime) {
        super(Property.ACKNOWLEDGED, new Factory());
        dateTime.setUtc(true);
        setDate(dateTime);
    }

    public Acknowledged(ParameterList parameterList, DateTime dateTime) {
        super(Property.ACKNOWLEDGED, parameterList, new Factory());
        dateTime.setUtc(true);
        setDate(dateTime);
    }

    public static class Factory extends Content.Factory implements PropertyFactory<Property> {
        private static final long serialVersionUID = 1;

        public Factory() {
            super(Property.ACKNOWLEDGED);
        }

        @Override // net.fortuna.ical4j.model.PropertyFactory
        public Property createProperty(ParameterList parameterList, String str) throws URISyntaxException, IOException, ParseException {
            return new Acknowledged(parameterList, str);
        }

        @Override // net.fortuna.ical4j.model.PropertyFactory
        public Property createProperty() {
            return new Acknowledged();
        }
    }
}
