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
public class Method extends Property {
    public static final Method ADD;
    public static final Method CANCEL;
    public static final Method COUNTER;
    public static final Method DECLINE_COUNTER;
    public static final Method PUBLISH;
    public static final Method REFRESH;
    public static final Method REPLY;
    public static final Method REQUEST;
    private static final long serialVersionUID = 7220956532685378719L;
    private String value;

    @Override // net.fortuna.ical4j.model.Property
    public void validate() throws ValidationException {
    }

    static {
        PUBLISH = new ImmutableMethod("PUBLISH");
        REQUEST = new ImmutableMethod("REQUEST");
        REPLY = new ImmutableMethod("REPLY");
        ADD = new ImmutableMethod("ADD");
        CANCEL = new ImmutableMethod("CANCEL");
        REFRESH = new ImmutableMethod("REFRESH");
        COUNTER = new ImmutableMethod("COUNTER");
        DECLINE_COUNTER = new ImmutableMethod("DECLINE-COUNTER");
    }

    private static final class ImmutableMethod extends Method {
        private static final long serialVersionUID = 5332607957381969713L;

        private ImmutableMethod(String str) {
            super(new ParameterList(true), str);
        }

        @Override // net.fortuna.ical4j.model.property.Method, net.fortuna.ical4j.model.Property
        public void setValue(String str) {
            throw new UnsupportedOperationException("Cannot modify constant instances");
        }
    }

    public Method() {
        super(Property.METHOD, new Factory());
    }

    public Method(String str) {
        super(Property.METHOD, new Factory());
        this.value = str;
    }

    public Method(ParameterList parameterList, String str) {
        super(Property.METHOD, parameterList, new Factory());
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

    public static class Factory extends Content.Factory implements PropertyFactory<Method> {
        private static final long serialVersionUID = 1;

        public Factory() {
            super(Property.METHOD);
        }

        @Override // net.fortuna.ical4j.model.PropertyFactory
        public Method createProperty(ParameterList parameterList, String str) throws URISyntaxException, IOException, ParseException {
            if (Method.ADD.getValue().equals(str)) {
                return Method.ADD;
            }
            if (Method.CANCEL.getValue().equals(str)) {
                return Method.CANCEL;
            }
            if (Method.COUNTER.getValue().equals(str)) {
                return Method.COUNTER;
            }
            if (Method.DECLINE_COUNTER.getValue().equals(str)) {
                return Method.DECLINE_COUNTER;
            }
            if (Method.PUBLISH.getValue().equals(str)) {
                return Method.PUBLISH;
            }
            if (Method.REFRESH.getValue().equals(str)) {
                return Method.REFRESH;
            }
            if (Method.REPLY.getValue().equals(str)) {
                return Method.REPLY;
            }
            if (Method.REQUEST.getValue().equals(str)) {
                return Method.REQUEST;
            }
            return new Method(parameterList, str);
        }

        @Override // net.fortuna.ical4j.model.PropertyFactory
        public Method createProperty() {
            return new Method();
        }
    }
}
