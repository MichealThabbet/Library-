package net.fortuna.ical4j.model.property;

import java.io.IOException;
import java.net.URISyntaxException;
import java.text.ParseException;
import net.fortuna.ical4j.model.Content;
import net.fortuna.ical4j.model.ParameterList;
import net.fortuna.ical4j.model.Property;
import net.fortuna.ical4j.model.PropertyFactory;
import net.fortuna.ical4j.model.Recur;
import net.fortuna.ical4j.validate.ValidationException;

/* loaded from: classes3.dex */
public class RRule extends Property {
    private static final long serialVersionUID = -9188265089143001164L;
    private Recur recur;

    @Override // net.fortuna.ical4j.model.Property
    public void validate() throws ValidationException {
    }

    public RRule() {
        super(Property.RRULE, new Factory());
        this.recur = new Recur(Recur.Frequency.DAILY, 1);
    }

    public RRule(String str) throws ParseException {
        super(Property.RRULE, new Factory());
        setValue(str);
    }

    public RRule(ParameterList parameterList, String str) throws ParseException {
        super(Property.RRULE, parameterList, new Factory());
        setValue(str);
    }

    public RRule(Recur recur) {
        super(Property.RRULE, new Factory());
        this.recur = recur;
    }

    public RRule(ParameterList parameterList, Recur recur) {
        super(Property.RRULE, parameterList, new Factory());
        this.recur = recur;
    }

    public final Recur getRecur() {
        return this.recur;
    }

    @Override // net.fortuna.ical4j.model.Property
    public final void setValue(String str) throws ParseException {
        this.recur = new Recur(str);
    }

    @Override // net.fortuna.ical4j.model.Content
    public final String getValue() {
        return getRecur().toString();
    }

    public static class Factory extends Content.Factory implements PropertyFactory {
        private static final long serialVersionUID = 1;

        public Factory() {
            super(Property.RRULE);
        }

        @Override // net.fortuna.ical4j.model.PropertyFactory
        public Property createProperty(ParameterList parameterList, String str) throws URISyntaxException, IOException, ParseException {
            return new RRule(parameterList, str);
        }

        @Override // net.fortuna.ical4j.model.PropertyFactory
        public Property createProperty() {
            return new RRule();
        }
    }
}
