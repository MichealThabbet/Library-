package net.fortuna.ical4j.model.parameter;

import net.fortuna.ical4j.model.Content;
import net.fortuna.ical4j.model.Parameter;
import net.fortuna.ical4j.model.ParameterFactory;

/* loaded from: classes3.dex */
public class Label extends Parameter {
    private static final String PARAMETER_NAME = "LABEL";
    private static final long serialVersionUID = 1;
    private final String value;

    public Label(String str) {
        super(PARAMETER_NAME, new Factory());
        this.value = str;
    }

    @Override // net.fortuna.ical4j.model.Content
    public String getValue() {
        return this.value;
    }

    public static class Factory extends Content.Factory implements ParameterFactory {
        private static final long serialVersionUID = 1;

        public Factory() {
            super(Label.PARAMETER_NAME);
        }

        @Override // net.fortuna.ical4j.model.ParameterFactory
        public Parameter createParameter(String str) {
            return new Label(str);
        }
    }
}
