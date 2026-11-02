package net.fortuna.ical4j.model.property;

import java.io.IOException;
import java.net.URISyntaxException;
import java.text.ParseException;
import net.fortuna.ical4j.model.Date;
import net.fortuna.ical4j.model.DateTime;
import net.fortuna.ical4j.model.Parameter;
import net.fortuna.ical4j.model.ParameterList;
import net.fortuna.ical4j.model.Property;
import net.fortuna.ical4j.model.PropertyFactory;
import net.fortuna.ical4j.model.TimeZone;
import net.fortuna.ical4j.model.parameter.Value;
import net.fortuna.ical4j.util.Strings;
import net.fortuna.ical4j.validate.ParameterValidator;
import net.fortuna.ical4j.validate.ValidationException;

/* loaded from: classes3.dex */
public abstract class DateProperty extends Property {
    private static final long serialVersionUID = 3160883132732961321L;
    private Date date;
    private TimeZone timeZone;

    public DateProperty(String str, ParameterList parameterList, PropertyFactory propertyFactory) {
        super(str, parameterList, propertyFactory);
    }

    public DateProperty(String str, PropertyFactory propertyFactory) {
        super(str, propertyFactory);
    }

    public DateProperty(String str, TimeZone timeZone, PropertyFactory propertyFactory) {
        super(str, propertyFactory);
        updateTimeZone(timeZone);
    }

    public final Date getDate() {
        return this.date;
    }

    public final void setDate(Date date) {
        this.date = date;
        if (date instanceof DateTime) {
            if (Value.DATE.equals(getParameter(Parameter.VALUE))) {
                getParameters().replace(Value.DATE_TIME);
            }
            updateTimeZone(((DateTime) date).getTimeZone());
        } else {
            if (date != null) {
                getParameters().replace(Value.DATE);
            }
            updateTimeZone(null);
        }
    }

    @Override // net.fortuna.ical4j.model.Property
    public void setValue(String str) throws ParseException {
        if (Value.DATE.equals(getParameter(Parameter.VALUE))) {
            updateTimeZone(null);
            this.date = new Date(str);
        } else {
            if (str == null || str.isEmpty()) {
                return;
            }
            this.date = new DateTime(str, this.timeZone);
        }
    }

    @Override // net.fortuna.ical4j.model.Content
    public String getValue() {
        return Strings.valueOf(getDate());
    }

    public void setTimeZone(TimeZone timeZone) {
        updateTimeZone(timeZone);
    }

    public final TimeZone getTimeZone() {
        return this.timeZone;
    }

    @Override // net.fortuna.ical4j.model.Property
    public int hashCode() {
        if (getDate() != null) {
            return getDate().hashCode();
        }
        return 0;
    }

    private void updateTimeZone(TimeZone timeZone) {
        this.timeZone = timeZone;
        if (timeZone != null) {
            if (getDate() != null && !(getDate() instanceof DateTime)) {
                throw new UnsupportedOperationException("TimeZone is not applicable to current value");
            }
            if (getDate() != null) {
                ((DateTime) getDate()).setTimeZone(timeZone);
            }
            getParameters().replace(new net.fortuna.ical4j.model.parameter.TzId(timeZone.getID()));
            return;
        }
        setUtc(isUtc());
    }

    public final void setUtc(boolean z) {
        if (getDate() != null && (getDate() instanceof DateTime)) {
            ((DateTime) getDate()).setUtc(z);
        }
        getParameters().remove(getParameter("TZID"));
    }

    public final boolean isUtc() {
        return (getDate() instanceof DateTime) && ((DateTime) getDate()).isUtc();
    }

    @Override // net.fortuna.ical4j.model.Property
    public void validate() throws ValidationException {
        ParameterValidator.assertOneOrLess(Parameter.VALUE, getParameters());
        if (isUtc()) {
            ParameterValidator.assertNone("TZID", getParameters());
        } else {
            ParameterValidator.assertOneOrLess("TZID", getParameters());
        }
        Value value = (Value) getParameter(Parameter.VALUE);
        if (getDate() instanceof DateTime) {
            if (value != null && !Value.DATE_TIME.equals(value)) {
                throw new ValidationException("VALUE parameter [" + value + "] is invalid for DATE-TIME instance");
            }
            DateTime dateTime = (DateTime) this.date;
            Parameter parameter = getParameter("TZID");
            if (dateTime.getTimeZone() != null) {
                if (parameter == null || !parameter.getValue().equals(dateTime.getTimeZone().getID())) {
                    throw new ValidationException("TZID parameter [" + parameter + "] does not match the timezone [" + dateTime.getTimeZone().getID() + "]");
                }
                return;
            }
            return;
        }
        if (getDate() != null) {
            if (value == null) {
                throw new ValidationException("VALUE parameter [" + Value.DATE + "] must be specified for DATE instance");
            }
            if (Value.DATE.equals(value)) {
                return;
            }
            throw new ValidationException("VALUE parameter [" + value + "] is invalid for DATE instance");
        }
    }

    @Override // net.fortuna.ical4j.model.Property
    public Property copy() throws URISyntaxException, IOException, ParseException {
        Property propertyCopy = super.copy();
        DateProperty dateProperty = (DateProperty) propertyCopy;
        dateProperty.timeZone = this.timeZone;
        dateProperty.setValue(getValue());
        return propertyCopy;
    }
}
