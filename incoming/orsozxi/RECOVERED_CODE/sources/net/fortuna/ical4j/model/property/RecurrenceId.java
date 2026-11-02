package net.fortuna.ical4j.model.property;

import java.io.IOException;
import java.net.URISyntaxException;
import java.text.ParseException;
import net.fortuna.ical4j.model.Content;
import net.fortuna.ical4j.model.Date;
import net.fortuna.ical4j.model.DateTime;
import net.fortuna.ical4j.model.Parameter;
import net.fortuna.ical4j.model.ParameterList;
import net.fortuna.ical4j.model.Property;
import net.fortuna.ical4j.model.PropertyFactory;
import net.fortuna.ical4j.model.TimeZone;
import net.fortuna.ical4j.validate.ParameterValidator;
import net.fortuna.ical4j.validate.ValidationException;

/* loaded from: classes3.dex */
public class RecurrenceId extends DateProperty {
    private static final long serialVersionUID = 4456883817126011006L;

    public RecurrenceId() {
        super(Property.RECURRENCE_ID, new Factory());
        setDate(new DateTime());
    }

    public RecurrenceId(TimeZone timeZone) {
        super(Property.RECURRENCE_ID, timeZone, new Factory());
    }

    public RecurrenceId(String str) throws ParseException {
        super(Property.RECURRENCE_ID, new Factory());
        setValue(str);
    }

    public RecurrenceId(String str, TimeZone timeZone) throws ParseException {
        super(Property.RECURRENCE_ID, timeZone, new Factory());
        setValue(str);
    }

    public RecurrenceId(ParameterList parameterList, String str) throws ParseException {
        super(Property.RECURRENCE_ID, parameterList, new Factory());
        setValue(str);
    }

    public RecurrenceId(Date date) {
        super(Property.RECURRENCE_ID, new Factory());
        setDate(date);
    }

    public RecurrenceId(ParameterList parameterList, Date date) {
        super(Property.RECURRENCE_ID, parameterList, new Factory());
        setDate(date);
    }

    @Override // net.fortuna.ical4j.model.property.DateProperty, net.fortuna.ical4j.model.Property
    public final void validate() throws ValidationException {
        super.validate();
        ParameterValidator.assertOneOrLess(Parameter.RANGE, getParameters());
    }

    public static class Factory extends Content.Factory implements PropertyFactory {
        private static final long serialVersionUID = 1;

        public Factory() {
            super(Property.RECURRENCE_ID);
        }

        @Override // net.fortuna.ical4j.model.PropertyFactory
        public Property createProperty(ParameterList parameterList, String str) throws URISyntaxException, IOException, ParseException {
            return new RecurrenceId(parameterList, str);
        }

        @Override // net.fortuna.ical4j.model.PropertyFactory
        public Property createProperty() {
            return new RecurrenceId();
        }
    }
}
