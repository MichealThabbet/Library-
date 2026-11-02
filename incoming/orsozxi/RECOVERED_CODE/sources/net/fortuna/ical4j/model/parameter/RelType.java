package net.fortuna.ical4j.model.parameter;

import java.net.URISyntaxException;
import net.fortuna.ical4j.model.Content;
import net.fortuna.ical4j.model.Parameter;
import net.fortuna.ical4j.model.ParameterFactory;
import net.fortuna.ical4j.util.Strings;

/* loaded from: classes3.dex */
public class RelType extends Parameter {
    private static final long serialVersionUID = 5346030888832899016L;
    private String value;
    private static final String VALUE_PARENT = "PARENT";
    public static final RelType PARENT = new RelType(VALUE_PARENT);
    private static final String VALUE_CHILD = "CHILD";
    public static final RelType CHILD = new RelType(VALUE_CHILD);
    private static final String VALUE_SIBLING = "SIBLING";
    public static final RelType SIBLING = new RelType(VALUE_SIBLING);

    public RelType(String str) {
        super(Parameter.RELTYPE, new Factory());
        this.value = Strings.unquote(str);
    }

    @Override // net.fortuna.ical4j.model.Content
    public final String getValue() {
        return this.value;
    }

    public static class Factory extends Content.Factory implements ParameterFactory {
        private static final long serialVersionUID = 1;

        public Factory() {
            super(Parameter.RELTYPE);
        }

        @Override // net.fortuna.ical4j.model.ParameterFactory
        public Parameter createParameter(String str) throws URISyntaxException {
            RelType relType = new RelType(str);
            if (RelType.PARENT.equals(relType)) {
                relType = RelType.PARENT;
            } else if (RelType.CHILD.equals(relType)) {
                relType = RelType.CHILD;
            }
            return RelType.SIBLING.equals(relType) ? RelType.SIBLING : relType;
        }
    }
}
