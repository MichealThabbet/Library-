package net.fortuna.ical4j.model.parameter;

import java.net.URISyntaxException;
import net.fortuna.ical4j.model.Content;
import net.fortuna.ical4j.model.Parameter;
import net.fortuna.ical4j.model.ParameterFactory;
import net.fortuna.ical4j.util.Strings;

/* loaded from: classes3.dex */
public class XParameter extends Parameter {
    private static final long serialVersionUID = -3372153616695145903L;
    private String value;

    public XParameter(String str, String str2) {
        super(str, new Factory(str));
        this.value = Strings.unquote(str2);
    }

    @Override // net.fortuna.ical4j.model.Content
    public final String getValue() {
        return this.value;
    }

    public static class Factory extends Content.Factory implements ParameterFactory {
        private static final long serialVersionUID = 1;
        private final String name;

        public Factory(String str) {
            super(str);
            this.name = str;
        }

        @Override // net.fortuna.ical4j.model.ParameterFactory
        public Parameter createParameter(String str) throws URISyntaxException {
            return new XParameter(this.name, str);
        }
    }
}
