package net.fortuna.ical4j.model.parameter;

import java.net.URI;
import java.net.URISyntaxException;
import net.fortuna.ical4j.model.Content;
import net.fortuna.ical4j.model.Parameter;
import net.fortuna.ical4j.model.ParameterFactory;
import net.fortuna.ical4j.util.Strings;
import net.fortuna.ical4j.util.Uris;

/* loaded from: classes3.dex */
public class Dir extends Parameter {
    private static final long serialVersionUID = -8581904779721020689L;
    private URI uri;

    public Dir(String str) throws URISyntaxException {
        this(Uris.create(Strings.unquote(str)));
    }

    public Dir(URI uri) {
        super(Parameter.DIR, new Factory());
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
            super(Parameter.DIR);
        }

        @Override // net.fortuna.ical4j.model.ParameterFactory
        public Parameter createParameter(String str) throws URISyntaxException {
            return new Dir(str);
        }
    }
}
