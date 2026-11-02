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
public class Transp extends Property {
    public static final Transp OPAQUE;
    public static final Transp TRANSPARENT;
    private static final long serialVersionUID = 3801479657311785518L;
    private String value;

    @Override // net.fortuna.ical4j.model.Property
    public void validate() throws ValidationException {
    }

    static {
        OPAQUE = new ImmutableTransp("OPAQUE");
        TRANSPARENT = new ImmutableTransp("TRANSPARENT");
    }

    private static final class ImmutableTransp extends Transp {
        private static final long serialVersionUID = -6595830107310111996L;

        private ImmutableTransp(String str) {
            super(new ParameterList(true), str);
        }

        @Override // net.fortuna.ical4j.model.property.Transp, net.fortuna.ical4j.model.Property
        public void setValue(String str) {
            throw new UnsupportedOperationException("Cannot modify constant instances");
        }
    }

    public Transp() {
        super(Property.TRANSP, new Factory());
    }

    public Transp(String str) {
        super(Property.TRANSP, new Factory());
        this.value = str;
    }

    public Transp(ParameterList parameterList, String str) {
        super(Property.TRANSP, parameterList, new Factory());
        this.value = str;
    }

    @Override // net.fortuna.ical4j.model.Property
    public void setValue(String str) {
        this.value = str;
    }

    @Override // net.fortuna.ical4j.model.Content
    public final String getValue() {
        return this.value;
    }

    public static class Factory extends Content.Factory implements PropertyFactory<Transp> {
        private static final long serialVersionUID = 1;

        public Factory() {
            super(Property.TRANSP);
        }

        @Override // net.fortuna.ical4j.model.PropertyFactory
        public Transp createProperty(ParameterList parameterList, String str) throws URISyntaxException, IOException, ParseException {
            if (Transp.OPAQUE.getValue().equals(str)) {
                return Transp.OPAQUE;
            }
            if (Transp.TRANSPARENT.getValue().equals(str)) {
                return Transp.TRANSPARENT;
            }
            return new Transp(parameterList, str);
        }

        @Override // net.fortuna.ical4j.model.PropertyFactory
        public Transp createProperty() {
            return new Transp();
        }
    }
}
