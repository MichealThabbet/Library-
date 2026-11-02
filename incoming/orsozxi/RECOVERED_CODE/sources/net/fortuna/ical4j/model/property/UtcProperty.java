package net.fortuna.ical4j.model.property;

import net.fortuna.ical4j.model.DateTime;
import net.fortuna.ical4j.model.ParameterList;
import net.fortuna.ical4j.model.PropertyFactory;
import net.fortuna.ical4j.model.TimeZone;
import net.fortuna.ical4j.validate.ValidationException;

/* loaded from: classes3.dex */
public abstract class UtcProperty extends DateProperty {
    private static final long serialVersionUID = 4850079486497487938L;

    public UtcProperty(String str, ParameterList parameterList, PropertyFactory propertyFactory) {
        super(str, parameterList, propertyFactory);
        setDate(new DateTime(true));
    }

    public UtcProperty(String str, PropertyFactory propertyFactory) {
        super(str, propertyFactory);
        setDate(new DateTime(true));
    }

    public final DateTime getDateTime() {
        return (DateTime) getDate();
    }

    public void setDateTime(DateTime dateTime) {
        if (dateTime != null) {
            DateTime dateTime2 = new DateTime(dateTime);
            dateTime2.setUtc(true);
            setDate(dateTime2);
            return;
        }
        setDate(null);
    }

    @Override // net.fortuna.ical4j.model.property.DateProperty
    public void setTimeZone(TimeZone timeZone) {
        throw new UnsupportedOperationException("Cannot set timezone for UTC properties");
    }

    @Override // net.fortuna.ical4j.model.property.DateProperty, net.fortuna.ical4j.model.Property
    public void validate() throws ValidationException {
        super.validate();
        if (getDate() != null && !(getDate() instanceof DateTime)) {
            throw new ValidationException("Property must have a DATE-TIME value");
        }
        DateTime dateTime = (DateTime) getDate();
        if (dateTime == null || dateTime.isUtc()) {
            return;
        }
        throw new ValidationException(getName() + ": DATE-TIME value must be specified in UTC time");
    }
}
