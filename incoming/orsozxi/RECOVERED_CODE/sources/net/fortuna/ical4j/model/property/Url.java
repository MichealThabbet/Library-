package net.fortuna.ical4j.model.property;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.ParseException;
import net.fortuna.ical4j.model.Content;
import net.fortuna.ical4j.model.ParameterList;
import net.fortuna.ical4j.model.Property;
import net.fortuna.ical4j.model.PropertyFactory;
import net.fortuna.ical4j.util.Strings;
import net.fortuna.ical4j.util.Uris;
import net.fortuna.ical4j.validate.ValidationException;

/* loaded from: classes3.dex */
public class Url extends Property {
    private static final long serialVersionUID = 1092576402256525737L;
    private URI uri;

    @Override // net.fortuna.ical4j.model.Property
    public void validate() throws ValidationException {
    }

    public Url() {
        super(Property.URL, new Factory());
    }

    public Url(ParameterList parameterList, String str) throws URISyntaxException {
        super(Property.URL, parameterList, new Factory());
        setValue(str);
    }

    public Url(URI uri) {
        super(Property.URL, new Factory());
        this.uri = uri;
    }

    public Url(ParameterList parameterList, URI uri) {
        super(Property.URL, parameterList, new Factory());
        this.uri = uri;
    }

    public final URI getUri() {
        return this.uri;
    }

    @Override // net.fortuna.ical4j.model.Property
    public final void setValue(String str) throws URISyntaxException {
        this.uri = Uris.create(str);
    }

    @Override // net.fortuna.ical4j.model.Content
    public final String getValue() {
        return Uris.decode(Strings.valueOf(getUri()));
    }

    public final void setUri(URI uri) {
        this.uri = uri;
    }

    public static class Factory extends Content.Factory implements PropertyFactory {
        private static final long serialVersionUID = 1;

        public Factory() {
            super(Property.URL);
        }

        @Override // net.fortuna.ical4j.model.PropertyFactory
        public Property createProperty(ParameterList parameterList, String str) throws URISyntaxException, IOException, ParseException {
            return new Url(parameterList, str);
        }

        @Override // net.fortuna.ical4j.model.PropertyFactory
        public Property createProperty() {
            return new Url();
        }
    }
}
