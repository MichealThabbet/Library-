package net.fortuna.ical4j.model.parameter;

import java.net.URISyntaxException;
import net.fortuna.ical4j.model.Content;
import net.fortuna.ical4j.model.Parameter;
import net.fortuna.ical4j.model.ParameterFactory;
import net.fortuna.ical4j.util.Strings;

/* loaded from: classes3.dex */
public class Encoding extends Parameter {
    private static final long serialVersionUID = 7536336461076399077L;
    private String value;
    private static final String VALUE_SEVEN_BIT = "7BIT";
    public static final Encoding SEVEN_BIT = new Encoding(VALUE_SEVEN_BIT);
    private static final String VALUE_EIGHT_BIT = "8BIT";
    public static final Encoding EIGHT_BIT = new Encoding(VALUE_EIGHT_BIT);
    private static final String VALUE_BINARY = "BINARY";
    public static final Encoding BINARY = new Encoding(VALUE_BINARY);
    private static final String VALUE_QUOTED_PRINTABLE = "QUOTED-PRINTABLE";
    public static final Encoding QUOTED_PRINTABLE = new Encoding(VALUE_QUOTED_PRINTABLE);
    private static final String VALUE_BASE64 = "BASE64";
    public static final Encoding BASE64 = new Encoding(VALUE_BASE64);

    public Encoding(String str) {
        super(Parameter.ENCODING, new Factory());
        this.value = Strings.unquote(str);
    }

    @Override // net.fortuna.ical4j.model.Content
    public final String getValue() {
        return this.value;
    }

    public static class Factory extends Content.Factory implements ParameterFactory {
        private static final long serialVersionUID = 1;

        public Factory() {
            super(Parameter.ENCODING);
        }

        @Override // net.fortuna.ical4j.model.ParameterFactory
        public Parameter createParameter(String str) throws URISyntaxException {
            Encoding encoding = new Encoding(str);
            if (Encoding.EIGHT_BIT.equals(encoding)) {
                return Encoding.EIGHT_BIT;
            }
            return Encoding.BASE64.equals(encoding) ? Encoding.BASE64 : encoding;
        }
    }
}
