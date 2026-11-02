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
public class PercentComplete extends Property {
    private static final long serialVersionUID = 7788138484983240112L;
    private int percentage;

    public PercentComplete() {
        super(Property.PERCENT_COMPLETE, new Factory());
    }

    public PercentComplete(ParameterList parameterList, String str) {
        super(Property.PERCENT_COMPLETE, parameterList, new Factory());
        setValue(str);
    }

    public PercentComplete(int i) {
        super(Property.PERCENT_COMPLETE, new Factory());
        this.percentage = i;
    }

    public PercentComplete(ParameterList parameterList, int i) {
        super(Property.PERCENT_COMPLETE, parameterList, new Factory());
        this.percentage = i;
    }

    public final int getPercentage() {
        return this.percentage;
    }

    @Override // net.fortuna.ical4j.model.Property
    public final void setValue(String str) {
        this.percentage = Integer.parseInt(str);
    }

    @Override // net.fortuna.ical4j.model.Content
    public final String getValue() {
        return String.valueOf(getPercentage());
    }

    public final void setPercentage(int i) {
        this.percentage = i;
    }

    @Override // net.fortuna.ical4j.model.Property
    public final void validate() throws ValidationException {
        int i = this.percentage;
        if (i < 0 || i > 100) {
            throw new ValidationException(getName() + " with invalid value: " + this.percentage);
        }
    }

    public static class Factory extends Content.Factory implements PropertyFactory {
        private static final long serialVersionUID = 1;

        public Factory() {
            super(Property.PERCENT_COMPLETE);
        }

        @Override // net.fortuna.ical4j.model.PropertyFactory
        public Property createProperty(ParameterList parameterList, String str) throws URISyntaxException, IOException, ParseException {
            return new PercentComplete(parameterList, str);
        }

        @Override // net.fortuna.ical4j.model.PropertyFactory
        public Property createProperty() {
            return new PercentComplete();
        }
    }
}
