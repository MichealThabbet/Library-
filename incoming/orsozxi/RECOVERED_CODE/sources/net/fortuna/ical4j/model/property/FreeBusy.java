package net.fortuna.ical4j.model.property;

import java.io.IOException;
import java.net.URISyntaxException;
import java.text.ParseException;
import net.fortuna.ical4j.model.Content;
import net.fortuna.ical4j.model.Parameter;
import net.fortuna.ical4j.model.ParameterList;
import net.fortuna.ical4j.model.PeriodList;
import net.fortuna.ical4j.model.Property;
import net.fortuna.ical4j.model.PropertyFactory;
import net.fortuna.ical4j.validate.ParameterValidator;
import net.fortuna.ical4j.validate.ValidationException;

/* loaded from: classes3.dex */
public class FreeBusy extends Property {
    private static final long serialVersionUID = -6415954847619338567L;
    private PeriodList periods;

    public FreeBusy() {
        super(Property.FREEBUSY, new Factory());
        this.periods = new PeriodList();
    }

    public FreeBusy(String str) throws ParseException {
        super(Property.FREEBUSY, new Factory());
        setValue(str);
    }

    public FreeBusy(ParameterList parameterList, String str) throws ParseException {
        super(Property.FREEBUSY, parameterList, new Factory());
        setValue(str);
    }

    public FreeBusy(PeriodList periodList) {
        super(Property.FREEBUSY, new Factory());
        if (!periodList.isUtc()) {
            throw new IllegalArgumentException("Periods must be in UTC format");
        }
        this.periods = periodList;
    }

    public FreeBusy(ParameterList parameterList, PeriodList periodList) {
        super(Property.FREEBUSY, parameterList, new Factory());
        if (!periodList.isUtc()) {
            throw new IllegalArgumentException("Periods must be in UTC format");
        }
        this.periods = periodList;
    }

    @Override // net.fortuna.ical4j.model.Property
    public final void validate() throws ValidationException {
        ParameterValidator.assertOneOrLess(Parameter.FBTYPE, getParameters());
        if (!this.periods.isUtc()) {
            throw new ValidationException("Periods must be in UTC format");
        }
    }

    public final PeriodList getPeriods() {
        return this.periods;
    }

    @Override // net.fortuna.ical4j.model.Property
    public final void setValue(String str) throws ParseException {
        this.periods = new PeriodList(str);
    }

    @Override // net.fortuna.ical4j.model.Content
    public final String getValue() {
        return getPeriods().toString();
    }

    public static class Factory extends Content.Factory implements PropertyFactory {
        private static final long serialVersionUID = 1;

        public Factory() {
            super(Property.FREEBUSY);
        }

        @Override // net.fortuna.ical4j.model.PropertyFactory
        public Property createProperty(ParameterList parameterList, String str) throws URISyntaxException, IOException, ParseException {
            return new FreeBusy(parameterList, str);
        }

        @Override // net.fortuna.ical4j.model.PropertyFactory
        public Property createProperty() {
            return new FreeBusy();
        }
    }
}
