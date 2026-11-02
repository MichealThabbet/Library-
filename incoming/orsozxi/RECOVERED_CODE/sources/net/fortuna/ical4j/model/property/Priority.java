package net.fortuna.ical4j.model.property;

import java.io.IOException;
import java.net.URISyntaxException;
import java.text.ParseException;
import net.fortuna.ical4j.model.Content;
import net.fortuna.ical4j.model.ParameterList;
import net.fortuna.ical4j.model.Property;
import net.fortuna.ical4j.model.PropertyFactory;
import net.fortuna.ical4j.util.CompatibilityHints;
import net.fortuna.ical4j.validate.ValidationException;

/* loaded from: classes3.dex */
public class Priority extends Property {
    public static final Priority HIGH;
    public static final Priority LOW;
    public static final Priority MEDIUM;
    public static final Priority UNDEFINED;
    private static final long serialVersionUID = -5654367843953827397L;
    private int level;

    @Override // net.fortuna.ical4j.model.Property
    public void validate() throws ValidationException {
    }

    static {
        UNDEFINED = new ImmutablePriority(0);
        HIGH = new ImmutablePriority(1);
        MEDIUM = new ImmutablePriority(5);
        LOW = new ImmutablePriority(9);
    }

    private static final class ImmutablePriority extends Priority {
        private static final long serialVersionUID = 5884973714694108418L;

        private ImmutablePriority(int i) {
            super(new ParameterList(true), i);
        }

        @Override // net.fortuna.ical4j.model.property.Priority, net.fortuna.ical4j.model.Property
        public void setValue(String str) {
            throw new UnsupportedOperationException("Cannot modify constant instances");
        }

        @Override // net.fortuna.ical4j.model.property.Priority
        public void setLevel(int i) {
            throw new UnsupportedOperationException("Cannot modify constant instances");
        }
    }

    public Priority() {
        super(Property.PRIORITY, new Factory());
        this.level = UNDEFINED.getLevel();
    }

    public Priority(ParameterList parameterList, String str) {
        super(Property.PRIORITY, parameterList, new Factory());
        try {
            this.level = Integer.parseInt(str);
        } catch (NumberFormatException e) {
            if (CompatibilityHints.isHintEnabled(CompatibilityHints.KEY_RELAXED_PARSING)) {
                this.level = UNDEFINED.level;
                return;
            }
            throw e;
        }
    }

    public Priority(int i) {
        super(Property.PRIORITY, new Factory());
        this.level = i;
    }

    public Priority(ParameterList parameterList, int i) {
        super(Property.PRIORITY, parameterList, new Factory());
        this.level = i;
    }

    public final int getLevel() {
        return this.level;
    }

    @Override // net.fortuna.ical4j.model.Property
    public void setValue(String str) {
        this.level = Integer.parseInt(str);
    }

    @Override // net.fortuna.ical4j.model.Content
    public final String getValue() {
        return String.valueOf(getLevel());
    }

    public void setLevel(int i) {
        this.level = i;
    }

    public static class Factory extends Content.Factory implements PropertyFactory<Priority> {
        private static final long serialVersionUID = 1;

        public Factory() {
            super(Property.PRIORITY);
        }

        @Override // net.fortuna.ical4j.model.PropertyFactory
        public Priority createProperty(ParameterList parameterList, String str) throws URISyntaxException, IOException, ParseException {
            if (Priority.HIGH.getValue().equals(str)) {
                return Priority.HIGH;
            }
            if (Priority.MEDIUM.getValue().equals(str)) {
                return Priority.MEDIUM;
            }
            if (Priority.LOW.getValue().equals(str)) {
                return Priority.LOW;
            }
            if (Priority.UNDEFINED.getValue().equals(str)) {
                return Priority.UNDEFINED;
            }
            return new Priority(parameterList, str);
        }

        @Override // net.fortuna.ical4j.model.PropertyFactory
        public Priority createProperty() {
            return new Priority();
        }
    }
}
