package net.fortuna.ical4j.model.property;

import java.io.IOException;
import java.net.URISyntaxException;
import java.text.ParseException;
import net.fortuna.ical4j.model.Content;
import net.fortuna.ical4j.model.ParameterList;
import net.fortuna.ical4j.model.Property;
import net.fortuna.ical4j.model.PropertyFactory;
import net.fortuna.ical4j.validate.ValidationException;

/* loaded from: classes3.dex */
public class Repeat extends Property {
    private static final long serialVersionUID = -1765522613173314831L;
    private int count;

    @Override // net.fortuna.ical4j.model.Property
    public void validate() throws ValidationException {
    }

    public Repeat() {
        super(Property.REPEAT, new Factory());
    }

    public Repeat(ParameterList parameterList, String str) {
        super(Property.REPEAT, parameterList, new Factory());
        setValue(str);
    }

    public Repeat(int i) {
        super(Property.REPEAT, new Factory());
        this.count = i;
    }

    public Repeat(ParameterList parameterList, int i) {
        super(Property.REPEAT, parameterList, new Factory());
        this.count = i;
    }

    public final int getCount() {
        return this.count;
    }

    @Override // net.fortuna.ical4j.model.Property
    public final void setValue(String str) {
        this.count = Integer.parseInt(str);
    }

    @Override // net.fortuna.ical4j.model.Content
    public final String getValue() {
        return String.valueOf(getCount());
    }

    public final void setCount(int i) {
        this.count = i;
    }

    public static class Factory extends Content.Factory implements PropertyFactory {
        private static final long serialVersionUID = 1;

        public Factory() {
            super(Property.REPEAT);
        }

        @Override // net.fortuna.ical4j.model.PropertyFactory
        public Property createProperty(ParameterList parameterList, String str) throws URISyntaxException, IOException, ParseException {
            return new Repeat(parameterList, str);
        }

        @Override // net.fortuna.ical4j.model.PropertyFactory
        public Property createProperty() {
            return new Repeat();
        }
    }
}
