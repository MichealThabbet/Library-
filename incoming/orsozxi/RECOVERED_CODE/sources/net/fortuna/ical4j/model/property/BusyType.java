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
public class BusyType extends Property {
    public static final BusyType BUSY;
    public static final BusyType BUSY_TENTATIVE;
    public static final BusyType BUSY_UNAVAILABLE;
    private static final long serialVersionUID = -5140360270562621159L;
    private String value;

    @Override // net.fortuna.ical4j.model.Property
    public void validate() throws ValidationException {
    }

    static {
        BUSY = new ImmutableBusyType("BUSY");
        BUSY_UNAVAILABLE = new ImmutableBusyType("BUSY-UNAVAILABLE");
        BUSY_TENTATIVE = new ImmutableBusyType("BUSY-TENTATIVE");
    }

    private static final class ImmutableBusyType extends BusyType {
        private static final long serialVersionUID = -2454749569982470433L;

        private ImmutableBusyType(String str) {
            super(new ParameterList(true), str);
        }

        @Override // net.fortuna.ical4j.model.property.BusyType, net.fortuna.ical4j.model.Property
        public void setValue(String str) {
            throw new UnsupportedOperationException("Cannot modify constant instances");
        }
    }

    public BusyType() {
        super(Property.BUSYTYPE, new Factory());
    }

    public BusyType(String str) {
        super(Property.BUSYTYPE, new Factory());
        this.value = str;
    }

    public BusyType(ParameterList parameterList, String str) {
        super(Property.BUSYTYPE, parameterList, new Factory());
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

    public static class Factory extends Content.Factory implements PropertyFactory<BusyType> {
        private static final long serialVersionUID = 1;

        public Factory() {
            super(Property.BUSYTYPE);
        }

        @Override // net.fortuna.ical4j.model.PropertyFactory
        public BusyType createProperty(ParameterList parameterList, String str) throws URISyntaxException, IOException, ParseException {
            if (BusyType.BUSY.getValue().equals(str)) {
                return BusyType.BUSY;
            }
            if (BusyType.BUSY_TENTATIVE.getValue().equals(str)) {
                return BusyType.BUSY_TENTATIVE;
            }
            if (BusyType.BUSY_UNAVAILABLE.getValue().equals(str)) {
                return BusyType.BUSY_UNAVAILABLE;
            }
            return new BusyType(parameterList, str);
        }

        @Override // net.fortuna.ical4j.model.PropertyFactory
        public BusyType createProperty() {
            return new BusyType();
        }
    }
}
