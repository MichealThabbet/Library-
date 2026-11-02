package net.fortuna.ical4j.model.parameter;

import java.net.URI;
import java.net.URISyntaxException;
import net.fortuna.ical4j.model.Content;
import net.fortuna.ical4j.model.Parameter;
import net.fortuna.ical4j.model.ParameterFactory;
import net.fortuna.ical4j.util.Strings;
import net.fortuna.ical4j.util.Uris;

/* loaded from: classes3.dex */
public class SentBy extends Parameter {
    private static final long serialVersionUID = -1169413145174029391L;
    private URI address;

    public SentBy(String str) throws URISyntaxException {
        this(Uris.create(Strings.unquote(str)));
    }

    public SentBy(URI uri) {
        super(Parameter.SENT_BY, new Factory());
        this.address = uri;
    }

    public final URI getAddress() {
        return this.address;
    }

    @Override // net.fortuna.ical4j.model.Content
    public final String getValue() {
        return Uris.decode(Strings.valueOf(getAddress()));
    }

    public static class Factory extends Content.Factory implements ParameterFactory {
        private static final long serialVersionUID = 1;

        public Factory() {
            super(Parameter.SENT_BY);
        }

        @Override // net.fortuna.ical4j.model.ParameterFactory
        public Parameter createParameter(String str) throws URISyntaxException {
            return new SentBy(str);
        }
    }
}
