package net.fortuna.ical4j.model.parameter;

import java.net.URI;
import java.net.URISyntaxException;
import net.fortuna.ical4j.model.Content;
import net.fortuna.ical4j.model.Parameter;
import net.fortuna.ical4j.model.ParameterFactory;
import net.fortuna.ical4j.util.Strings;
import net.fortuna.ical4j.util.Uris;

/* loaded from: classes3.dex */
public class AltRep extends Parameter {
    private static final long serialVersionUID = -2445932592596993470L;
    private URI uri;

    public AltRep(String str) throws URISyntaxException {
        this(Uris.create(Strings.unquote(str)));
    }

    public AltRep(URI uri) {
        super(Parameter.ALTREP, new Factory());
        this.uri = uri;
    }

    public final URI getUri() {
        return this.uri;
    }

    @Override // net.fortuna.ical4j.model.Content
    public final String getValue() {
        return Uris.decode(Strings.valueOf(getUri()));
    }

    public static class Factory extends Content.Factory implements ParameterFactory {
        private static final long serialVersionUID = 1;

        public Factory() {
            super(Parameter.ALTREP);
        }

        @Override // net.fortuna.ical4j.model.ParameterFactory
        public Parameter createParameter(String str) throws URISyntaxException {
            return new AltRep(str);
        }
    }
}
