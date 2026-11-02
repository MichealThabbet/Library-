package net.fortuna.ical4j.model.parameter;

import java.net.URISyntaxException;
import net.fortuna.ical4j.model.Content;
import net.fortuna.ical4j.model.Parameter;
import net.fortuna.ical4j.model.ParameterFactory;

/* loaded from: classes3.dex */
public class Rsvp extends Parameter {
    private static final long serialVersionUID = -5381653882942018012L;
    private Boolean rsvp;
    private static final String VALUE_TRUE = "TRUE";
    public static final Rsvp TRUE = new Rsvp(VALUE_TRUE);
    private static final String VALUE_FALSE = "FALSE";
    public static final Rsvp FALSE = new Rsvp(VALUE_FALSE);

    public Rsvp(String str) {
        this(Boolean.valueOf(str));
    }

    public Rsvp(Boolean bool) {
        super(Parameter.RSVP, new Factory());
        this.rsvp = bool;
    }

    public final Boolean getRsvp() {
        return this.rsvp;
    }

    @Override // net.fortuna.ical4j.model.Content
    public final String getValue() {
        return this.rsvp.booleanValue() ? VALUE_TRUE : VALUE_FALSE;
    }

    @Override // net.fortuna.ical4j.model.Parameter
    public final Parameter copy() {
        if (this.rsvp.booleanValue()) {
            return TRUE;
        }
        return FALSE;
    }

    public static class Factory extends Content.Factory implements ParameterFactory {
        private static final long serialVersionUID = 1;

        public Factory() {
            super(Parameter.RSVP);
        }

        @Override // net.fortuna.ical4j.model.ParameterFactory
        public Parameter createParameter(String str) throws URISyntaxException {
            Rsvp rsvp = new Rsvp(str);
            if (Rsvp.TRUE.equals(rsvp)) {
                return Rsvp.TRUE;
            }
            return Rsvp.FALSE.equals(rsvp) ? Rsvp.FALSE : rsvp;
        }
    }
}
