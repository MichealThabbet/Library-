package net.fortuna.ical4j.model.parameter;

import java.net.URISyntaxException;
import net.fortuna.ical4j.model.Content;
import net.fortuna.ical4j.model.Parameter;
import net.fortuna.ical4j.model.ParameterFactory;
import net.fortuna.ical4j.util.Strings;

/* loaded from: classes3.dex */
public class Related extends Parameter {
    private static final String VALUE_END = "END";
    private static final long serialVersionUID = 1570525804115869565L;
    private String value;
    private static final String VALUE_START = "START";
    public static final Related START = new Related(VALUE_START);
    public static final Related END = new Related("END");

    public Related(String str) {
        super(Parameter.RELATED, new Factory());
        String strUnquote = Strings.unquote(str);
        this.value = strUnquote;
        if (VALUE_START.equals(strUnquote) || "END".equals(this.value)) {
            return;
        }
        throw new IllegalArgumentException("Invalid value [" + this.value + "]");
    }

    @Override // net.fortuna.ical4j.model.Content
    public final String getValue() {
        return this.value;
    }

    public static class Factory extends Content.Factory implements ParameterFactory {
        private static final long serialVersionUID = 1;

        public Factory() {
            super(Parameter.RELATED);
        }

        @Override // net.fortuna.ical4j.model.ParameterFactory
        public Parameter createParameter(String str) throws URISyntaxException {
            Related related = new Related(str);
            if (Related.START.equals(related)) {
                return Related.START;
            }
            return Related.END.equals(related) ? Related.END : related;
        }
    }
}
