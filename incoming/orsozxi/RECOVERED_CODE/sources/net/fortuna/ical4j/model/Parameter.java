package net.fortuna.ical4j.model;

import java.net.URISyntaxException;
import net.fortuna.ical4j.util.Strings;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

/* loaded from: classes3.dex */
public abstract class Parameter extends Content {
    public static final String ABBREV = "ABBREV";
    public static final String ALTREP = "ALTREP";
    public static final String CN = "CN";
    public static final String CUTYPE = "CUTYPE";
    public static final String DELEGATED_FROM = "DELEGATED-FROM";
    public static final String DELEGATED_TO = "DELEGATED-TO";
    public static final String DIR = "DIR";
    public static final String ENCODING = "ENCODING";
    public static final String EXPERIMENTAL_PREFIX = "X-";
    public static final String FBTYPE = "FBTYPE";
    public static final String FMTTYPE = "FMTTYPE";
    public static final String LANGUAGE = "LANGUAGE";
    public static final String MEMBER = "MEMBER";
    public static final String PARTSTAT = "PARTSTAT";
    public static final String RANGE = "RANGE";
    public static final String RELATED = "RELATED";
    public static final String RELTYPE = "RELTYPE";
    public static final String ROLE = "ROLE";
    public static final String RSVP = "RSVP";
    public static final String SCHEDULE_AGENT = "SCHEDULE-AGENT";
    public static final String SCHEDULE_STATUS = "SCHEDULE-STATUS";
    public static final String SENT_BY = "SENT-BY";
    public static final String TYPE = "TYPE";
    public static final String TZID = "TZID";
    public static final String VALUE = "VALUE";
    public static final String VVENUE = "VVENUE";
    private static final long serialVersionUID = -2058497904769713528L;
    private final ParameterFactory factory;
    private String name;

    public Parameter(String str, ParameterFactory parameterFactory) {
        this.name = str;
        this.factory = parameterFactory;
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getName());
        sb.append('=');
        if (isQuotable()) {
            sb.append(Strings.quote(Strings.valueOf(getValue())));
        } else {
            sb.append(Strings.valueOf(getValue()));
        }
        return sb.toString();
    }

    protected boolean isQuotable() {
        return Strings.PARAM_QUOTE_PATTERN.matcher(Strings.valueOf(getValue())).find();
    }

    @Override // net.fortuna.ical4j.model.Content
    public final String getName() {
        return this.name;
    }

    public final boolean equals(Object obj) {
        if (obj instanceof Parameter) {
            Parameter parameter = (Parameter) obj;
            return new EqualsBuilder().append(getName(), parameter.getName()).append(getValue(), parameter.getValue()).isEquals();
        }
        return super.equals(obj);
    }

    public final int hashCode() {
        return new HashCodeBuilder().append(getName().toUpperCase()).append(getValue()).toHashCode();
    }

    public <T extends Parameter> T copy() throws URISyntaxException {
        ParameterFactory parameterFactory = this.factory;
        if (parameterFactory == null) {
            throw new UnsupportedOperationException("No factory specified");
        }
        return (T) parameterFactory.createParameter(getValue());
    }
}
