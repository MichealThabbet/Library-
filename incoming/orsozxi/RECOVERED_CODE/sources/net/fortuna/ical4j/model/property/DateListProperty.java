package net.fortuna.ical4j.model.property;

import java.io.IOException;
import java.net.URISyntaxException;
import java.text.ParseException;
import net.fortuna.ical4j.model.DateList;
import net.fortuna.ical4j.model.Parameter;
import net.fortuna.ical4j.model.ParameterList;
import net.fortuna.ical4j.model.Property;
import net.fortuna.ical4j.model.PropertyFactory;
import net.fortuna.ical4j.model.TimeZone;
import net.fortuna.ical4j.model.parameter.Value;
import net.fortuna.ical4j.util.Strings;

/* loaded from: classes3.dex */
public abstract class DateListProperty extends Property {
    private static final long serialVersionUID = 5233773091972759919L;
    private DateList dates;
    private TimeZone timeZone;

    public DateListProperty(String str, PropertyFactory propertyFactory) {
        this(str, new DateList(Value.DATE_TIME), propertyFactory);
    }

    public DateListProperty(String str, ParameterList parameterList, PropertyFactory propertyFactory) {
        super(str, parameterList, propertyFactory);
    }

    public DateListProperty(String str, DateList dateList, PropertyFactory propertyFactory) {
        this(str, new ParameterList(), dateList, propertyFactory);
    }

    public DateListProperty(String str, ParameterList parameterList, DateList dateList, PropertyFactory propertyFactory) {
        super(str, parameterList, propertyFactory);
        this.dates = dateList;
        if (dateList == null || Value.DATE_TIME.equals(dateList.getType())) {
            return;
        }
        getParameters().replace(dateList.getType());
    }

    public final DateList getDates() {
        return this.dates;
    }

    @Override // net.fortuna.ical4j.model.Property
    public void setValue(String str) throws ParseException {
        this.dates = new DateList(str, (Value) getParameter(Parameter.VALUE), this.timeZone);
    }

    @Override // net.fortuna.ical4j.model.Content
    public String getValue() {
        return Strings.valueOf(this.dates);
    }

    public void setTimeZone(TimeZone timeZone) {
        if (this.dates == null) {
            throw new UnsupportedOperationException("TimeZone is not applicable to current value");
        }
        this.timeZone = timeZone;
        if (timeZone != null) {
            if (!Value.DATE_TIME.equals(getDates().getType())) {
                throw new UnsupportedOperationException("TimeZone is not applicable to current value");
            }
            this.dates.setTimeZone(timeZone);
            getParameters().remove(getParameter("TZID"));
            getParameters().replace(new net.fortuna.ical4j.model.parameter.TzId(timeZone.getID()));
            return;
        }
        setUtc(false);
    }

    public final TimeZone getTimeZone() {
        return this.timeZone;
    }

    public final void setUtc(boolean z) {
        if (this.dates == null || !Value.DATE_TIME.equals(this.dates.getType())) {
            throw new UnsupportedOperationException("TimeZone is not applicable to current value");
        }
        this.dates.setUtc(z);
        getParameters().remove(getParameter("TZID"));
    }

    @Override // net.fortuna.ical4j.model.Property
    public final Property copy() throws URISyntaxException, IOException, ParseException {
        Property propertyCopy = super.copy();
        DateListProperty dateListProperty = (DateListProperty) propertyCopy;
        dateListProperty.timeZone = this.timeZone;
        dateListProperty.setValue(getValue());
        return propertyCopy;
    }
}
