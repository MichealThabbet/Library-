package net.fortuna.ical4j.model.property;

import java.io.IOException;
import java.net.URISyntaxException;
import java.text.ParseException;
import java.time.temporal.TemporalAmount;
import net.fortuna.ical4j.model.Content;
import net.fortuna.ical4j.model.DateTime;
import net.fortuna.ical4j.model.Dur;
import net.fortuna.ical4j.model.Parameter;
import net.fortuna.ical4j.model.ParameterList;
import net.fortuna.ical4j.model.Property;
import net.fortuna.ical4j.model.PropertyFactory;
import net.fortuna.ical4j.model.TemporalAmountAdapter;
import net.fortuna.ical4j.model.parameter.Value;
import net.fortuna.ical4j.validate.ParameterValidator;
import net.fortuna.ical4j.validate.ValidationException;

/* loaded from: classes3.dex */
public class Trigger extends UtcProperty {
    private static final long serialVersionUID = 5049421499261722194L;
    private TemporalAmountAdapter duration;

    public Trigger() {
        super(Property.TRIGGER, new Factory());
    }

    public Trigger(ParameterList parameterList, String str) {
        super(Property.TRIGGER, parameterList, new Factory());
        setValue(str);
    }

    @Deprecated
    public Trigger(Dur dur) {
        this(TemporalAmountAdapter.from(dur).getDuration());
    }

    public Trigger(TemporalAmount temporalAmount) {
        super(Property.TRIGGER, new Factory());
        setDuration(temporalAmount);
    }

    @Deprecated
    public Trigger(ParameterList parameterList, Dur dur) {
        this(parameterList, TemporalAmountAdapter.from(dur).getDuration());
    }

    public Trigger(ParameterList parameterList, TemporalAmount temporalAmount) {
        super(Property.TRIGGER, parameterList, new Factory());
        setDuration(temporalAmount);
    }

    public Trigger(DateTime dateTime) {
        super(Property.TRIGGER, new Factory());
        setDateTime(dateTime);
    }

    public Trigger(ParameterList parameterList, DateTime dateTime) {
        super(Property.TRIGGER, parameterList, new Factory());
        setDateTime(dateTime);
    }

    @Override // net.fortuna.ical4j.model.property.UtcProperty, net.fortuna.ical4j.model.property.DateProperty, net.fortuna.ical4j.model.Property
    public final void validate() throws ValidationException {
        super.validate();
        Parameter parameter = getParameter(Parameter.RELATED);
        Parameter parameter2 = getParameter(Parameter.VALUE);
        if (parameter != null || !Value.DATE_TIME.equals(parameter2)) {
            ParameterValidator.assertOneOrLess(Parameter.RELATED, getParameters());
            ParameterValidator.assertNullOrEqual(Value.DURATION, getParameters());
            if (getDuration() == null) {
                throw new ValidationException("Duration value not specified");
            }
            return;
        }
        ParameterValidator.assertOne(Parameter.VALUE, getParameters());
        ParameterValidator.assertNullOrEqual(Value.DATE_TIME, getParameters());
        if (getDateTime() == null) {
            throw new ValidationException("DATE-TIME value not specified");
        }
    }

    public final TemporalAmount getDuration() {
        TemporalAmountAdapter temporalAmountAdapter = this.duration;
        if (temporalAmountAdapter != null) {
            return temporalAmountAdapter.getDuration();
        }
        return null;
    }

    @Override // net.fortuna.ical4j.model.property.DateProperty, net.fortuna.ical4j.model.Property
    public final void setValue(String str) {
        try {
            super.setValue(str);
            this.duration = null;
        } catch (ParseException unused) {
            this.duration = TemporalAmountAdapter.parse(str);
            super.setDateTime(null);
        }
    }

    @Override // net.fortuna.ical4j.model.property.DateProperty, net.fortuna.ical4j.model.Content
    public final String getValue() {
        TemporalAmountAdapter temporalAmountAdapter = this.duration;
        if (temporalAmountAdapter != null) {
            return temporalAmountAdapter.toString();
        }
        return super.getValue();
    }

    @Override // net.fortuna.ical4j.model.property.UtcProperty
    public final void setDateTime(DateTime dateTime) {
        super.setDateTime(dateTime);
        this.duration = null;
        getParameters().replace(Value.DATE_TIME);
    }

    public final void setDuration(TemporalAmount temporalAmount) {
        this.duration = new TemporalAmountAdapter(temporalAmount);
        super.setDateTime(null);
        if (getParameter(Parameter.VALUE) != null) {
            getParameters().replace(Value.DURATION);
        }
    }

    public static class Factory extends Content.Factory implements PropertyFactory {
        private static final long serialVersionUID = 1;

        public Factory() {
            super(Property.TRIGGER);
        }

        @Override // net.fortuna.ical4j.model.PropertyFactory
        public Property createProperty(ParameterList parameterList, String str) throws URISyntaxException, IOException, ParseException {
            return new Trigger(parameterList, str);
        }

        @Override // net.fortuna.ical4j.model.PropertyFactory
        public Property createProperty() {
            return new Trigger();
        }
    }
}
