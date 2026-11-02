package net.fortuna.ical4j.model.parameter;

import java.net.URISyntaxException;
import net.fortuna.ical4j.model.Content;
import net.fortuna.ical4j.model.Parameter;
import net.fortuna.ical4j.model.ParameterFactory;
import net.fortuna.ical4j.util.Strings;

/* loaded from: classes3.dex */
public class Role extends Parameter {
    private static final long serialVersionUID = 1438225631470825963L;
    private String value;
    private static final String VALUE_CHAIR = "CHAIR";
    public static final Role CHAIR = new Role(VALUE_CHAIR);
    private static final String VALUE_REQ_PARTICIPANT = "REQ-PARTICIPANT";
    public static final Role REQ_PARTICIPANT = new Role(VALUE_REQ_PARTICIPANT);
    private static final String VALUE_OPT_PARTICIPANT = "OPT-PARTICIPANT";
    public static final Role OPT_PARTICIPANT = new Role(VALUE_OPT_PARTICIPANT);
    private static final String VALUE_NON_PARTICIPANT = "NON-PARTICIPANT";
    public static final Role NON_PARTICIPANT = new Role(VALUE_NON_PARTICIPANT);

    public Role(String str) {
        super(Parameter.ROLE, new Factory());
        this.value = Strings.unquote(str);
    }

    @Override // net.fortuna.ical4j.model.Content
    public final String getValue() {
        return this.value;
    }

    public static class Factory extends Content.Factory implements ParameterFactory {
        private static final long serialVersionUID = 1;

        public Factory() {
            super(Parameter.ROLE);
        }

        @Override // net.fortuna.ical4j.model.ParameterFactory
        public Parameter createParameter(String str) throws URISyntaxException {
            Role role = new Role(str);
            if (Role.CHAIR.equals(role)) {
                return Role.CHAIR;
            }
            if (Role.REQ_PARTICIPANT.equals(role)) {
                return Role.REQ_PARTICIPANT;
            }
            if (Role.OPT_PARTICIPANT.equals(role)) {
                return Role.OPT_PARTICIPANT;
            }
            return Role.NON_PARTICIPANT.equals(role) ? Role.NON_PARTICIPANT : role;
        }
    }
}
