package net.fortuna.ical4j.model.property;

import java.io.IOException;
import java.net.URISyntaxException;
import java.text.ParseException;
import net.fortuna.ical4j.model.Content;
import net.fortuna.ical4j.model.DateList;
import net.fortuna.ical4j.model.Parameter;
import net.fortuna.ical4j.model.ParameterList;
import net.fortuna.ical4j.model.PeriodList;
import net.fortuna.ical4j.model.Property;
import net.fortuna.ical4j.model.PropertyFactory;
import net.fortuna.ical4j.model.TimeZone;
import net.fortuna.ical4j.model.parameter.Value;
import net.fortuna.ical4j.util.Strings;
import net.fortuna.ical4j.validate.ParameterValidator;
import net.fortuna.ical4j.validate.ValidationException;

/* loaded from: classes3.dex */
public class RDate extends DateListProperty {
    private static final long serialVersionUID = -3320381650013860193L;
    private PeriodList periods;

    public RDate() {
        super(Property.RDATE, new Factory());
        this.periods = new PeriodList(false, true);
    }

    public RDate(ParameterList parameterList, String str) throws ParseException {
        super(Property.RDATE, parameterList, new Factory());
        this.periods = new PeriodList(false, true);
        setValue(str);
    }

    public RDate(DateList dateList) {
        super(Property.RDATE, dateList, new Factory());
        this.periods = new PeriodList(false, true);
    }

    public RDate(ParameterList parameterList, DateList dateList) {
        super(Property.RDATE, parameterList, dateList, new Factory());
        this.periods = new PeriodList(false, true);
    }

    public RDate(PeriodList periodList) {
        super(Property.RDATE, new DateList(true), new Factory());
        this.periods = periodList;
    }

    public RDate(ParameterList parameterList, PeriodList periodList) {
        super(Property.RDATE, parameterList, new DateList(true), new Factory());
        this.periods = periodList;
    }

    @Override // net.fortuna.ical4j.model.Property
    public final void validate() throws ValidationException {
        ParameterValidator.assertOneOrLess(Parameter.VALUE, getParameters());
        Parameter parameter = getParameter(Parameter.VALUE);
        if (parameter != null && !Value.DATE_TIME.equals(parameter) && !Value.DATE.equals(parameter) && !Value.PERIOD.equals(parameter)) {
            throw new ValidationException("Parameter [VALUE] is invalid");
        }
        ParameterValidator.assertOneOrLess("TZID", getParameters());
    }

    public final PeriodList getPeriods() {
        return this.periods;
    }

    @Override // net.fortuna.ical4j.model.property.DateListProperty, net.fortuna.ical4j.model.Property
    public final void setValue(String str) throws ParseException {
        if (Value.PERIOD.equals(getParameter(Parameter.VALUE))) {
            this.periods = new PeriodList(str);
        } else {
            super.setValue(str);
        }
    }

    @Override // net.fortuna.ical4j.model.property.DateListProperty, net.fortuna.ical4j.model.Content
    public final String getValue() {
        PeriodList periodList = this.periods;
        if (periodList != null && (!periodList.isEmpty() || !this.periods.isUnmodifiable())) {
            return Strings.valueOf(getPeriods());
        }
        return super.getValue();
    }

    @Override // net.fortuna.ical4j.model.property.DateListProperty
    public final void setTimeZone(TimeZone timeZone) {
        PeriodList periodList = this.periods;
        if (periodList != null && (!periodList.isEmpty() || !this.periods.isUnmodifiable())) {
            this.periods.setTimeZone(timeZone);
        } else {
            super.setTimeZone(timeZone);
        }
    }

    public static class Factory extends Content.Factory implements PropertyFactory {
        private static final long serialVersionUID = 1;

        public Factory() {
            super(Property.RDATE);
        }

        @Override // net.fortuna.ical4j.model.PropertyFactory
        public Property createProperty(ParameterList parameterList, String str) throws URISyntaxException, IOException, ParseException {
            return new RDate(parameterList, str);
        }

        @Override // net.fortuna.ical4j.model.PropertyFactory
        public Property createProperty() {
            return new RDate();
        }
    }
}
