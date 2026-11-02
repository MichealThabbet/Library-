package net.fortuna.ical4j.model.property;

import java.io.IOException;
import java.net.URISyntaxException;
import java.text.ParseException;
import java.time.temporal.TemporalAmount;
import java.util.Date;
import net.fortuna.ical4j.model.Content;
import net.fortuna.ical4j.model.Dur;
import net.fortuna.ical4j.model.ParameterList;
import net.fortuna.ical4j.model.Property;
import net.fortuna.ical4j.model.PropertyFactory;
import net.fortuna.ical4j.model.TemporalAmountAdapter;
import net.fortuna.ical4j.validate.ValidationException;

/* loaded from: classes3.dex */
public class Duration extends Property {
    private static final long serialVersionUID = 9144969653829796798L;
    private TemporalAmountAdapter duration;

    @Override // net.fortuna.ical4j.model.Property
    public void validate() throws ValidationException {
    }

    public Duration() {
        super(Property.DURATION, new Factory());
    }

    public Duration(ParameterList parameterList, String str) {
        super(Property.DURATION, parameterList, new Factory());
        setValue(str);
    }

    @Deprecated
    public Duration(Dur dur) {
        this(TemporalAmountAdapter.from(dur).getDuration());
    }

    public Duration(TemporalAmount temporalAmount) {
        super(Property.DURATION, new Factory());
        this.duration = new TemporalAmountAdapter(temporalAmount);
    }

    @Deprecated
    public Duration(ParameterList parameterList, Dur dur) {
        this(parameterList, TemporalAmountAdapter.from(dur).getDuration());
    }

    public Duration(ParameterList parameterList, TemporalAmount temporalAmount) {
        super(Property.DURATION, parameterList, new Factory());
        setDuration(temporalAmount);
    }

    public Duration(Date date, Date date2) {
        super(Property.DURATION, new Factory());
        setDuration(TemporalAmountAdapter.fromDateRange(date, date2).getDuration());
    }

    public final TemporalAmount getDuration() {
        return this.duration.getDuration();
    }

    @Override // net.fortuna.ical4j.model.Property
    public final void setValue(String str) {
        this.duration = TemporalAmountAdapter.parse(str);
    }

    @Override // net.fortuna.ical4j.model.Content
    public final String getValue() {
        return this.duration.toString();
    }

    public final void setDuration(TemporalAmount temporalAmount) {
        this.duration = new TemporalAmountAdapter(temporalAmount);
    }

    public static class Factory extends Content.Factory implements PropertyFactory {
        private static final long serialVersionUID = 1;

        public Factory() {
            super(Property.DURATION);
        }

        @Override // net.fortuna.ical4j.model.PropertyFactory
        public Property createProperty(ParameterList parameterList, String str) throws URISyntaxException, IOException, ParseException {
            return new Duration(parameterList, str);
        }

        @Override // net.fortuna.ical4j.model.PropertyFactory
        public Property createProperty() {
            return new Duration();
        }
    }
}
