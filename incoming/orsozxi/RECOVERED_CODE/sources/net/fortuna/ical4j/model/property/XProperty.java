package net.fortuna.ical4j.model.property;

import java.io.IOException;
import java.net.URISyntaxException;
import java.text.ParseException;
import net.fortuna.ical4j.model.Content;
import net.fortuna.ical4j.model.Escapable;
import net.fortuna.ical4j.model.ParameterList;
import net.fortuna.ical4j.model.Property;
import net.fortuna.ical4j.model.PropertyFactory;
import net.fortuna.ical4j.util.CompatibilityHints;
import net.fortuna.ical4j.validate.ValidationException;

/* loaded from: classes3.dex */
public class XProperty extends Property implements Escapable {
    private static final long serialVersionUID = 2331763266954894541L;
    private String value;

    public XProperty(String str) {
        super(str, new Factory(str));
    }

    public XProperty(String str, String str2) {
        super(str, new Factory(str));
        setValue(str2);
    }

    public XProperty(String str, ParameterList parameterList, String str2) {
        super(str, parameterList, new Factory(str));
        setValue(str2);
    }

    @Override // net.fortuna.ical4j.model.Property
    public final void setValue(String str) {
        this.value = str;
    }

    @Override // net.fortuna.ical4j.model.Content
    public final String getValue() {
        return this.value;
    }

    @Override // net.fortuna.ical4j.model.Property
    public final void validate() throws ValidationException {
        if (CompatibilityHints.isHintEnabled(CompatibilityHints.KEY_RELAXED_VALIDATION) || getName().startsWith("X-")) {
            return;
        }
        throw new ValidationException("Invalid name [" + getName() + "]. Experimental properties must have the following prefix: X-");
    }

    public static class Factory extends Content.Factory implements PropertyFactory {
        private static final long serialVersionUID = 1;
        private final String name;

        public Factory(String str) {
            super(str);
            this.name = str;
        }

        @Override // net.fortuna.ical4j.model.PropertyFactory
        public Property createProperty(ParameterList parameterList, String str) throws URISyntaxException, IOException, ParseException {
            return new XProperty(this.name, parameterList, str);
        }

        @Override // net.fortuna.ical4j.model.PropertyFactory
        public Property createProperty() {
            return new XProperty(this.name);
        }
    }
}
