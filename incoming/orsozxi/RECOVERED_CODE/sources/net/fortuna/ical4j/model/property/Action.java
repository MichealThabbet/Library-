package net.fortuna.ical4j.model.property;

import java.io.IOException;
import java.net.URISyntaxException;
import java.text.ParseException;
import net.fortuna.ical4j.model.Content;
import net.fortuna.ical4j.model.ParameterList;
import net.fortuna.ical4j.model.Property;
import net.fortuna.ical4j.model.PropertyFactory;
import net.fortuna.ical4j.validate.ValidationException;

/* loaded from: classes3.dex */
public class Action extends Property {
    public static final Action AUDIO;
    public static final Action DISPLAY;
    public static final Action EMAIL;
    public static final Action PROCEDURE;
    private static final long serialVersionUID = -2353353838411753712L;
    private String value;

    @Override // net.fortuna.ical4j.model.Property
    public void validate() throws ValidationException {
    }

    static {
        AUDIO = new ImmutableAction("AUDIO");
        DISPLAY = new ImmutableAction("DISPLAY");
        EMAIL = new ImmutableAction("EMAIL");
        PROCEDURE = new ImmutableAction("PROCEDURE");
    }

    private static final class ImmutableAction extends Action {
        private static final long serialVersionUID = -2752235951243969905L;

        private ImmutableAction(String str) {
            super(new ParameterList(true), str);
        }

        @Override // net.fortuna.ical4j.model.property.Action, net.fortuna.ical4j.model.Property
        public void setValue(String str) {
            throw new UnsupportedOperationException("Cannot modify constant instances");
        }
    }

    public Action() {
        super("ACTION", new Factory());
    }

    public Action(String str) {
        super("ACTION", new Factory());
        this.value = str;
    }

    public Action(ParameterList parameterList, String str) {
        super("ACTION", parameterList, new Factory());
        this.value = str;
    }

    @Override // net.fortuna.ical4j.model.Property
    public void setValue(String str) {
        this.value = str;
    }

    @Override // net.fortuna.ical4j.model.Content
    public final String getValue() {
        return this.value;
    }

    public static class Factory extends Content.Factory implements PropertyFactory<Action> {
        private static final long serialVersionUID = 1;

        public Factory() {
            super("ACTION");
        }

        @Override // net.fortuna.ical4j.model.PropertyFactory
        public Action createProperty(ParameterList parameterList, String str) throws URISyntaxException, IOException, ParseException {
            if (Action.AUDIO.getValue().equals(str)) {
                return Action.AUDIO;
            }
            if (Action.DISPLAY.getValue().equals(str)) {
                return Action.DISPLAY;
            }
            if (Action.EMAIL.getValue().equals(str)) {
                return Action.EMAIL;
            }
            if (Action.PROCEDURE.getValue().equals(str)) {
                return Action.PROCEDURE;
            }
            return new Action(parameterList, str);
        }

        @Override // net.fortuna.ical4j.model.PropertyFactory
        public Action createProperty() {
            return new Action();
        }
    }
}
