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
public class Version extends Property {
    public static final Version VERSION_2_0 = new ImmutableVersion("2.0");
    private static final long serialVersionUID = 8872508067309087704L;
    private String maxVersion;
    private String minVersion;

    @Override // net.fortuna.ical4j.model.Property
    public void validate() throws ValidationException {
    }

    private static final class ImmutableVersion extends Version {
        private static final long serialVersionUID = -5040679357859594835L;

        private ImmutableVersion(String str) {
            super(new ParameterList(true), str);
        }

        @Override // net.fortuna.ical4j.model.property.Version, net.fortuna.ical4j.model.Property
        public void setValue(String str) {
            throw new UnsupportedOperationException("Cannot modify constant instances");
        }

        @Override // net.fortuna.ical4j.model.property.Version
        public void setMaxVersion(String str) {
            throw new UnsupportedOperationException("Cannot modify constant instances");
        }

        @Override // net.fortuna.ical4j.model.property.Version
        public void setMinVersion(String str) {
            throw new UnsupportedOperationException("Cannot modify constant instances");
        }
    }

    public Version() {
        super(Property.VERSION, new Factory());
    }

    public Version(ParameterList parameterList, String str) {
        super(Property.VERSION, parameterList, new Factory());
        if (str.indexOf(59) >= 0) {
            this.minVersion = str.substring(0, str.indexOf(59) - 1);
            this.maxVersion = str.substring(str.indexOf(59));
        } else {
            this.maxVersion = str;
        }
    }

    public Version(String str, String str2) {
        super(Property.VERSION, new Factory());
        this.minVersion = str;
        this.maxVersion = str2;
    }

    public Version(ParameterList parameterList, String str, String str2) {
        super(Property.VERSION, parameterList, new Factory());
        this.minVersion = str;
        this.maxVersion = str2;
    }

    public final String getMaxVersion() {
        return this.maxVersion;
    }

    public final String getMinVersion() {
        return this.minVersion;
    }

    @Override // net.fortuna.ical4j.model.Property
    public void setValue(String str) {
        if (str.indexOf(59) >= 0) {
            this.minVersion = str.substring(0, str.indexOf(59) - 1);
            this.maxVersion = str.substring(str.indexOf(59));
        } else {
            this.maxVersion = str;
        }
    }

    @Override // net.fortuna.ical4j.model.Content
    public final String getValue() {
        StringBuilder sb = new StringBuilder();
        if (getMinVersion() != null) {
            sb.append(getMinVersion());
            if (getMaxVersion() != null) {
                sb.append(';');
            }
        }
        if (getMaxVersion() != null) {
            sb.append(getMaxVersion());
        }
        return sb.toString();
    }

    public void setMaxVersion(String str) {
        this.maxVersion = str;
    }

    public void setMinVersion(String str) {
        this.minVersion = str;
    }

    public static class Factory extends Content.Factory implements PropertyFactory<Version> {
        private static final long serialVersionUID = 1;

        public Factory() {
            super(Property.VERSION);
        }

        @Override // net.fortuna.ical4j.model.PropertyFactory
        public Version createProperty(ParameterList parameterList, String str) throws URISyntaxException, IOException, ParseException {
            if (Version.VERSION_2_0.getValue().equals(str)) {
                return Version.VERSION_2_0;
            }
            return new Version(parameterList, str);
        }

        @Override // net.fortuna.ical4j.model.PropertyFactory
        public Version createProperty() {
            return new Version();
        }
    }
}
