package net.fortuna.ical4j.model.parameter;

import java.net.URISyntaxException;
import net.fortuna.ical4j.model.Content;
import net.fortuna.ical4j.model.Parameter;
import net.fortuna.ical4j.model.ParameterFactory;
import net.fortuna.ical4j.util.Strings;

/* loaded from: classes3.dex */
public class FbType extends Parameter {
    private static final long serialVersionUID = -2217689716824679375L;
    private String value;
    private static final String VALUE_FREE = "FREE";
    public static final FbType FREE = new FbType(VALUE_FREE);
    private static final String VALUE_BUSY = "BUSY";
    public static final FbType BUSY = new FbType(VALUE_BUSY);
    private static final String VALUE_BUSY_UNAVAILABLE = "BUSY-UNAVAILABLE";
    public static final FbType BUSY_UNAVAILABLE = new FbType(VALUE_BUSY_UNAVAILABLE);
    private static final String VALUE_BUSY_TENTATIVE = "BUSY-TENTATIVE";
    public static final FbType BUSY_TENTATIVE = new FbType(VALUE_BUSY_TENTATIVE);

    public FbType(String str) {
        super(Parameter.FBTYPE, new Factory());
        this.value = Strings.unquote(str);
    }

    @Override // net.fortuna.ical4j.model.Content
    public final String getValue() {
        return this.value;
    }

    public static class Factory extends Content.Factory implements ParameterFactory {
        private static final long serialVersionUID = 1;

        public Factory() {
            super(Parameter.FBTYPE);
        }

        @Override // net.fortuna.ical4j.model.ParameterFactory
        public Parameter createParameter(String str) throws URISyntaxException {
            FbType fbType = new FbType(str);
            if (FbType.FREE.equals(fbType)) {
                return FbType.FREE;
            }
            if (FbType.BUSY.equals(fbType)) {
                return FbType.BUSY;
            }
            if (FbType.BUSY_TENTATIVE.equals(fbType)) {
                return FbType.BUSY_TENTATIVE;
            }
            return FbType.BUSY_UNAVAILABLE.equals(fbType) ? FbType.BUSY_UNAVAILABLE : fbType;
        }
    }
}
