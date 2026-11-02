package net.fortuna.ical4j.model.property;

import java.time.temporal.TemporalAmount;
import net.fortuna.ical4j.model.Content;
import net.fortuna.ical4j.model.ParameterList;
import net.fortuna.ical4j.model.Property;
import net.fortuna.ical4j.model.PropertyFactory;
import net.fortuna.ical4j.model.TemporalAmountAdapter;
import net.fortuna.ical4j.validate.ValidationException;

/* loaded from: classes3.dex */
public class RefreshInterval extends Property {
    public static final String PROPERTY_NAME = "REFRESH-INTERVAL";
    private static final long serialVersionUID = 1;
    private TemporalAmountAdapter duration;

    @Override // net.fortuna.ical4j.model.Property
    public void validate() throws ValidationException {
    }

    public RefreshInterval() {
        super(PROPERTY_NAME, new Factory());
    }

    public RefreshInterval(ParameterList parameterList, String str) {
        super(PROPERTY_NAME, parameterList, new Factory());
        setValue(str);
    }

    public RefreshInterval(ParameterList parameterList, TemporalAmount temporalAmount) {
        super(PROPERTY_NAME, parameterList, new Factory());
        this.duration = new TemporalAmountAdapter(temporalAmount);
    }

    @Override // net.fortuna.ical4j.model.Property
    public void setValue(String str) {
        this.duration = TemporalAmountAdapter.parse(str);
    }

    @Override // net.fortuna.ical4j.model.Content
    public String getValue() {
        return this.duration.toString();
    }

    public static class Factory extends Content.Factory implements PropertyFactory<RefreshInterval> {
        public Factory() {
            super(RefreshInterval.PROPERTY_NAME);
        }

        @Override // net.fortuna.ical4j.model.PropertyFactory
        public RefreshInterval createProperty() {
            return new RefreshInterval();
        }

        @Override // net.fortuna.ical4j.model.PropertyFactory
        public RefreshInterval createProperty(ParameterList parameterList, String str) {
            return new RefreshInterval(parameterList, str);
        }
    }
}
