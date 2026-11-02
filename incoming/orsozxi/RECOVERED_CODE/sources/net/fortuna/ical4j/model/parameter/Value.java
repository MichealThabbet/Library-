package net.fortuna.ical4j.model.parameter;

import java.net.URISyntaxException;
import net.fortuna.ical4j.model.Content;
import net.fortuna.ical4j.model.Parameter;
import net.fortuna.ical4j.model.ParameterFactory;
import net.fortuna.ical4j.util.Strings;

/* loaded from: classes3.dex */
public class Value extends Parameter {
    private static final String VALUE_DURATION = "DURATION";
    private static final long serialVersionUID = -7238642734500301768L;
    private String value;
    private static final String VALUE_BINARY = "BINARY";
    public static final Value BINARY = new Value(VALUE_BINARY);
    private static final String VALUE_BOOLEAN = "BOOLEAN";
    public static final Value BOOLEAN = new Value(VALUE_BOOLEAN);
    private static final String VALUE_CAL_ADDRESS = "CAL-ADDRESS";
    public static final Value CAL_ADDRESS = new Value(VALUE_CAL_ADDRESS);
    private static final String VALUE_DATE = "DATE";
    public static final Value DATE = new Value(VALUE_DATE);
    private static final String VALUE_DATE_TIME = "DATE-TIME";
    public static final Value DATE_TIME = new Value(VALUE_DATE_TIME);
    public static final Value DURATION = new Value("DURATION");
    private static final String VALUE_FLOAT = "FLOAT";
    public static final Value FLOAT = new Value(VALUE_FLOAT);
    private static final String VALUE_INTEGER = "INTEGER";
    public static final Value INTEGER = new Value(VALUE_INTEGER);
    private static final String VALUE_PERIOD = "PERIOD";
    public static final Value PERIOD = new Value(VALUE_PERIOD);
    private static final String VALUE_RECUR = "RECUR";
    public static final Value RECUR = new Value(VALUE_RECUR);
    private static final String VALUE_TEXT = "TEXT";
    public static final Value TEXT = new Value(VALUE_TEXT);
    private static final String VALUE_TIME = "TIME";
    public static final Value TIME = new Value(VALUE_TIME);
    private static final String VALUE_URI = "URI";
    public static final Value URI = new Value(VALUE_URI);
    private static final String VALUE_UTC_OFFSET = "UTC-OFFSET";
    public static final Value UTC_OFFSET = new Value(VALUE_UTC_OFFSET);

    public Value(String str) {
        super(Parameter.VALUE, new Factory());
        this.value = Strings.unquote(str);
    }

    @Override // net.fortuna.ical4j.model.Content
    public final String getValue() {
        return this.value;
    }

    public static class Factory extends Content.Factory implements ParameterFactory {
        private static final long serialVersionUID = 1;

        public Factory() {
            super(Parameter.VALUE);
        }

        @Override // net.fortuna.ical4j.model.ParameterFactory
        public Parameter createParameter(String str) throws URISyntaxException {
            Value value = new Value(str);
            if (Value.BINARY.equals(value)) {
                return Value.BINARY;
            }
            if (Value.BOOLEAN.equals(value)) {
                return Value.BOOLEAN;
            }
            if (Value.CAL_ADDRESS.equals(value)) {
                return Value.CAL_ADDRESS;
            }
            if (Value.DATE.equals(value)) {
                return Value.DATE;
            }
            if (Value.DATE_TIME.equals(value)) {
                return Value.DATE_TIME;
            }
            if (Value.DURATION.equals(value)) {
                return Value.DURATION;
            }
            if (Value.FLOAT.equals(value)) {
                return Value.FLOAT;
            }
            if (Value.INTEGER.equals(value)) {
                return Value.INTEGER;
            }
            if (Value.PERIOD.equals(value)) {
                return Value.PERIOD;
            }
            if (Value.RECUR.equals(value)) {
                return Value.RECUR;
            }
            if (Value.TEXT.equals(value)) {
                return Value.TEXT;
            }
            if (Value.TIME.equals(value)) {
                return Value.TIME;
            }
            if (Value.URI.equals(value)) {
                return Value.URI;
            }
            return Value.UTC_OFFSET.equals(value) ? Value.UTC_OFFSET : value;
        }
    }
}
