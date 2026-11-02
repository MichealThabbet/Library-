package net.fortuna.ical4j.model.property;

import java.io.IOException;
import java.net.URISyntaxException;
import java.text.ParseException;
import net.fortuna.ical4j.model.Content;
import net.fortuna.ical4j.model.DateList;
import net.fortuna.ical4j.model.Parameter;
import net.fortuna.ical4j.model.ParameterList;
import net.fortuna.ical4j.model.Property;
import net.fortuna.ical4j.model.PropertyFactory;
import net.fortuna.ical4j.model.parameter.Value;
import net.fortuna.ical4j.validate.ParameterValidator;
import net.fortuna.ical4j.validate.ValidationException;

/* loaded from: classes3.dex */
public class ExDate extends DateListProperty {
    private static final long serialVersionUID = 2635730172243974463L;

    public ExDate() {
        super(Property.EXDATE, new Factory());
    }

    public ExDate(ParameterList parameterList, String str) throws ParseException {
        super(Property.EXDATE, parameterList, new Factory());
        setValue(str);
    }

    public ExDate(DateList dateList) {
        super(Property.EXDATE, dateList, new Factory());
    }

    public ExDate(ParameterList parameterList, DateList dateList) {
        super(Property.EXDATE, parameterList, dateList, new Factory());
    }

    @Override // net.fortuna.ical4j.model.Property
    public final void validate() throws ValidationException {
        ParameterValidator.assertOneOrLess(Parameter.VALUE, getParameters());
        Parameter parameter = getParameter(Parameter.VALUE);
        if (parameter != null && !Value.DATE_TIME.equals(parameter) && !Value.DATE.equals(parameter)) {
            throw new ValidationException("Parameter [VALUE] is invalid");
        }
        ParameterValidator.assertOneOrLess("TZID", getParameters());
    }

    public static class Factory extends Content.Factory implements PropertyFactory {
        private static final long serialVersionUID = 1;

        public Factory() {
            super(Property.EXDATE);
        }

        @Override // net.fortuna.ical4j.model.PropertyFactory
        public Property createProperty(ParameterList parameterList, String str) throws URISyntaxException, IOException, ParseException {
            return new ExDate(parameterList, str);
        }

        @Override // net.fortuna.ical4j.model.PropertyFactory
        public Property createProperty() {
            return new ExDate();
        }
    }
}
