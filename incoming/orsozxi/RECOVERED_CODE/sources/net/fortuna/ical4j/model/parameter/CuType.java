package net.fortuna.ical4j.model.parameter;

import java.net.URISyntaxException;
import net.fortuna.ical4j.model.Content;
import net.fortuna.ical4j.model.Parameter;
import net.fortuna.ical4j.model.ParameterFactory;
import net.fortuna.ical4j.util.Strings;

/* loaded from: classes3.dex */
public class CuType extends Parameter {
    private static final String VALUE_UNKNOWN = "UNKNOWN";
    private static final long serialVersionUID = -3134064324693983052L;
    private String value;
    private static final String VALUE_INDIVIDUAL = "INDIVIDUAL";
    public static final CuType INDIVIDUAL = new CuType(VALUE_INDIVIDUAL);
    private static final String VALUE_GROUP = "GROUP";
    public static final CuType GROUP = new CuType(VALUE_GROUP);
    private static final String VALUE_RESOURCE = "RESOURCE";
    public static final CuType RESOURCE = new CuType(VALUE_RESOURCE);
    private static final String VALUE_ROOM = "ROOM";
    public static final CuType ROOM = new CuType(VALUE_ROOM);
    public static final CuType UNKNOWN = new CuType("UNKNOWN");

    public CuType(String str) {
        super(Parameter.CUTYPE, new Factory());
        this.value = Strings.unquote(str);
    }

    @Override // net.fortuna.ical4j.model.Content
    public final String getValue() {
        return this.value;
    }

    public static class Factory extends Content.Factory implements ParameterFactory {
        private static final long serialVersionUID = 1;

        public Factory() {
            super(Parameter.CUTYPE);
        }

        @Override // net.fortuna.ical4j.model.ParameterFactory
        public Parameter createParameter(String str) throws URISyntaxException {
            CuType cuType = new CuType(str);
            if (CuType.INDIVIDUAL.equals(cuType)) {
                return CuType.INDIVIDUAL;
            }
            if (CuType.GROUP.equals(cuType)) {
                return CuType.GROUP;
            }
            if (CuType.RESOURCE.equals(cuType)) {
                return CuType.RESOURCE;
            }
            if (CuType.ROOM.equals(cuType)) {
                return CuType.ROOM;
            }
            return CuType.UNKNOWN.equals(cuType) ? CuType.UNKNOWN : cuType;
        }
    }
}
