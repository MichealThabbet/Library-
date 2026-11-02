package net.fortuna.ical4j.model;

import java.io.IOException;
import java.net.URISyntaxException;
import java.text.ParseException;
import net.fortuna.ical4j.model.parameter.Value;
import net.fortuna.ical4j.model.property.XProperty;
import net.fortuna.ical4j.util.Strings;
import net.fortuna.ical4j.validate.ValidationException;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

/* loaded from: classes3.dex */
public abstract class Property extends Content {
    public static final String ACKNOWLEDGED = "ACKNOWLEDGED";
    public static final String ACTION = "ACTION";
    public static final String ATTACH = "ATTACH";
    public static final String ATTENDEE = "ATTENDEE";
    public static final String BUSYTYPE = "BUSYTYPE";
    public static final String CALSCALE = "CALSCALE";
    public static final String CATEGORIES = "CATEGORIES";
    public static final String CLASS = "CLASS";
    public static final String COMMENT = "COMMENT";
    public static final String COMPLETED = "COMPLETED";
    public static final String CONTACT = "CONTACT";
    public static final String COUNTRY = "COUNTRY";
    public static final String CREATED = "CREATED";
    public static final String DESCRIPTION = "DESCRIPTION";
    public static final String DTEND = "DTEND";
    public static final String DTSTAMP = "DTSTAMP";
    public static final String DTSTART = "DTSTART";
    public static final String DUE = "DUE";
    public static final String DURATION = "DURATION";
    public static final String EXDATE = "EXDATE";
    public static final String EXPERIMENTAL_PREFIX = "X-";
    public static final String EXRULE = "EXRULE";
    public static final String EXTENDED_ADDRESS = "EXTENDED-ADDRESS";
    public static final String FREEBUSY = "FREEBUSY";
    public static final String GEO = "GEO";
    public static final String LAST_MODIFIED = "LAST-MODIFIED";
    public static final String LOCALITY = "LOCALITY";
    public static final String LOCATION = "LOCATION";
    public static final String LOCATION_TYPE = "LOCATION-TYPE";
    public static final String METHOD = "METHOD";
    public static final String NAME = "NAME";
    public static final String ORGANIZER = "ORGANIZER";
    public static final String PERCENT_COMPLETE = "PERCENT-COMPLETE";
    public static final String POSTALCODE = "POSTAL-CODE";
    public static final String PRIORITY = "PRIORITY";
    public static final String PRODID = "PRODID";
    public static final String RDATE = "RDATE";
    public static final String RECURRENCE_ID = "RECURRENCE-ID";
    public static final String REGION = "REGION";
    public static final String RELATED_TO = "RELATED-TO";
    public static final String REPEAT = "REPEAT";
    public static final String REQUEST_STATUS = "REQUEST-STATUS";
    public static final String RESOURCES = "RESOURCES";
    public static final String RRULE = "RRULE";
    public static final String SEQUENCE = "SEQUENCE";
    public static final String STATUS = "STATUS";
    public static final String STREET_ADDRESS = "STREET-ADDRESS";
    public static final String SUMMARY = "SUMMARY";
    public static final String TEL = "TEL";
    public static final String TRANSP = "TRANSP";
    public static final String TRIGGER = "TRIGGER";
    public static final String TZID = "TZID";
    public static final String TZNAME = "TZNAME";
    public static final String TZOFFSETFROM = "TZOFFSETFROM";
    public static final String TZOFFSETTO = "TZOFFSETTO";
    public static final String TZURL = "TZURL";
    public static final String UID = "UID";
    public static final String URL = "URL";
    public static final String VERSION = "VERSION";
    private static final long serialVersionUID = 7048785558435608687L;
    private final PropertyFactory factory;
    private final String name;
    private final ParameterList parameters;

    public abstract void setValue(String str) throws URISyntaxException, IOException, ParseException;

    public abstract void validate() throws ValidationException;

    protected Property(String str, PropertyFactory propertyFactory) {
        this(str, new ParameterList(), propertyFactory);
    }

    protected Property(String str, ParameterList parameterList, PropertyFactory propertyFactory) {
        this.name = str;
        this.parameters = parameterList;
        this.factory = propertyFactory;
    }

    protected Property(Property property) throws URISyntaxException, IOException, ParseException {
        this(property.getName(), new ParameterList(property.getParameters(), false), property.factory);
        setValue(property.getValue());
    }

    public final String toString() {
        Value value;
        StringBuilder sb = new StringBuilder();
        sb.append(getName());
        if (getParameters() != null) {
            sb.append(getParameters());
        }
        sb.append(':');
        boolean z = false;
        if (!(this instanceof XProperty) ? (this instanceof Escapable) : !((value = (Value) getParameter(Parameter.VALUE)) != null && !value.equals(Value.TEXT))) {
            z = true;
        }
        if (z) {
            sb.append(Strings.escape(Strings.valueOf(getValue())));
        } else {
            sb.append(Strings.valueOf(getValue()));
        }
        sb.append("\r\n");
        return sb.toString();
    }

    @Override // net.fortuna.ical4j.model.Content
    public final String getName() {
        return this.name;
    }

    public final ParameterList getParameters() {
        return this.parameters;
    }

    public final ParameterList getParameters(String str) {
        return getParameters().getParameters(str);
    }

    public final <T extends Parameter> T getParameter(String str) {
        return (T) getParameters().getParameter(str);
    }

    public final boolean equals(Object obj) {
        if (obj instanceof Property) {
            Property property = (Property) obj;
            return getName().equals(property.getName()) && new EqualsBuilder().append(getValue(), property.getValue()).append(getParameters(), property.getParameters()).isEquals();
        }
        return super.equals(obj);
    }

    public int hashCode() {
        return new HashCodeBuilder().append(getName().toUpperCase()).append(getValue()).append(getParameters()).toHashCode();
    }

    public Property copy() throws URISyntaxException, IOException, ParseException {
        if (this.factory == null) {
            throw new UnsupportedOperationException("No factory specified");
        }
        return this.factory.createProperty(new ParameterList(getParameters(), false), getValue());
    }
}
