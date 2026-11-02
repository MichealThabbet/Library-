package net.fortuna.ical4j.model.property;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URISyntaxException;
import java.text.ParseException;
import net.fortuna.ical4j.model.Content;
import net.fortuna.ical4j.model.ParameterList;
import net.fortuna.ical4j.model.Property;
import net.fortuna.ical4j.model.PropertyFactory;
import net.fortuna.ical4j.validate.ValidationException;
import org.apache.commons.lang3.StringUtils;

/* loaded from: classes3.dex */
public class Geo extends Property {
    private static final long serialVersionUID = -902100715801867636L;
    private BigDecimal latitude;
    private BigDecimal longitude;

    @Override // net.fortuna.ical4j.model.Property
    public void validate() throws ValidationException {
    }

    public Geo() {
        super(Property.GEO, new Factory());
        this.latitude = BigDecimal.valueOf(0L);
        this.longitude = BigDecimal.valueOf(0L);
    }

    public Geo(String str) {
        super(Property.GEO, new Factory());
        setValue(str);
    }

    public Geo(ParameterList parameterList, String str) {
        super(Property.GEO, parameterList, new Factory());
        setValue(str);
    }

    public Geo(BigDecimal bigDecimal, BigDecimal bigDecimal2) {
        super(Property.GEO, new Factory());
        this.latitude = bigDecimal;
        this.longitude = bigDecimal2;
    }

    public Geo(ParameterList parameterList, BigDecimal bigDecimal, BigDecimal bigDecimal2) {
        super(Property.GEO, parameterList, new Factory());
        this.latitude = bigDecimal;
        this.longitude = bigDecimal2;
    }

    public final BigDecimal getLatitude() {
        return this.latitude;
    }

    public final BigDecimal getLongitude() {
        return this.longitude;
    }

    @Override // net.fortuna.ical4j.model.Property
    public final void setValue(String str) {
        String strSubstring = str.substring(0, str.indexOf(59));
        if (StringUtils.isNotBlank(strSubstring)) {
            this.latitude = new BigDecimal(strSubstring);
        } else {
            this.latitude = BigDecimal.valueOf(0L);
        }
        String strSubstring2 = str.substring(str.indexOf(59) + 1);
        if (StringUtils.isNotBlank(strSubstring2)) {
            this.longitude = new BigDecimal(strSubstring2);
        } else {
            this.longitude = BigDecimal.valueOf(0L);
        }
    }

    @Override // net.fortuna.ical4j.model.Content
    public final String getValue() {
        return String.valueOf(getLatitude()) + ";" + String.valueOf(getLongitude());
    }

    public final void setLatitude(BigDecimal bigDecimal) {
        this.latitude = bigDecimal;
    }

    public final void setLongitude(BigDecimal bigDecimal) {
        this.longitude = bigDecimal;
    }

    public static class Factory extends Content.Factory implements PropertyFactory {
        private static final long serialVersionUID = 1;

        public Factory() {
            super(Property.GEO);
        }

        @Override // net.fortuna.ical4j.model.PropertyFactory
        public Property createProperty(ParameterList parameterList, String str) throws URISyntaxException, IOException, ParseException {
            return new Geo(parameterList, str);
        }

        @Override // net.fortuna.ical4j.model.PropertyFactory
        public Property createProperty() {
            return new Geo();
        }
    }
}
