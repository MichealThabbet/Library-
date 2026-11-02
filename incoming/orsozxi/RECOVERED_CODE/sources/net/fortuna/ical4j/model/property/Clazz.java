package net.fortuna.ical4j.model.property;

import java.io.IOException;
import java.net.URISyntaxException;
import java.text.ParseException;
import net.fortuna.ical4j.model.Content;
import net.fortuna.ical4j.model.ParameterList;
import net.fortuna.ical4j.model.Property;
import net.fortuna.ical4j.model.PropertyFactory;
import net.fortuna.ical4j.validate.ValidationException;
import org.jsoup.nodes.DocumentType;

/* loaded from: classes3.dex */
public class Clazz extends Property {
    public static final Clazz CONFIDENTIAL;
    public static final Clazz PRIVATE;
    public static final Clazz PUBLIC;
    private static final long serialVersionUID = 4939943639175551481L;
    private String value;

    @Override // net.fortuna.ical4j.model.Property
    public void validate() throws ValidationException {
    }

    static {
        PUBLIC = new ImmutableClazz(DocumentType.PUBLIC_KEY);
        PRIVATE = new ImmutableClazz("PRIVATE");
        CONFIDENTIAL = new ImmutableClazz("CONFIDENTIAL");
    }

    private static final class ImmutableClazz extends Clazz {
        private static final long serialVersionUID = 5978394762293365042L;

        private ImmutableClazz(String str) {
            super(new ParameterList(true), str);
        }

        @Override // net.fortuna.ical4j.model.property.Clazz, net.fortuna.ical4j.model.Property
        public void setValue(String str) {
            throw new UnsupportedOperationException("Cannot modify constant instances");
        }
    }

    public Clazz() {
        super(Property.CLASS, new Factory());
    }

    public Clazz(String str) {
        super(Property.CLASS, new Factory());
        this.value = str;
    }

    public Clazz(ParameterList parameterList, String str) {
        super(Property.CLASS, parameterList, new Factory());
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

    public static class Factory extends Content.Factory implements PropertyFactory<Clazz> {
        private static final long serialVersionUID = 1;

        public Factory() {
            super(Property.CLASS);
        }

        @Override // net.fortuna.ical4j.model.PropertyFactory
        public Clazz createProperty(ParameterList parameterList, String str) throws URISyntaxException, IOException, ParseException {
            if (Clazz.CONFIDENTIAL.getValue().equals(str)) {
                return Clazz.CONFIDENTIAL;
            }
            if (Clazz.PRIVATE.getValue().equals(str)) {
                return Clazz.PRIVATE;
            }
            if (Clazz.PUBLIC.getValue().equals(str)) {
                return Clazz.PUBLIC;
            }
            return new Clazz(parameterList, str);
        }

        @Override // net.fortuna.ical4j.model.PropertyFactory
        public Clazz createProperty() {
            return new Clazz();
        }
    }
}
