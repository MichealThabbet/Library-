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
public class Status extends Property {
    public static final Status VEVENT_CANCELLED;
    public static final Status VEVENT_CONFIRMED;
    public static final Status VEVENT_TENTATIVE;
    public static final Status VJOURNAL_CANCELLED;
    public static final Status VJOURNAL_DRAFT;
    public static final Status VJOURNAL_FINAL;
    public static final Status VTODO_CANCELLED;
    public static final Status VTODO_COMPLETED;
    public static final Status VTODO_IN_PROCESS;
    public static final Status VTODO_NEEDS_ACTION;
    private static final long serialVersionUID = 7401102230299289898L;
    private String value;

    @Override // net.fortuna.ical4j.model.Property
    public void validate() throws ValidationException {
    }

    static {
        VEVENT_TENTATIVE = new ImmutableStatus("TENTATIVE");
        VEVENT_CONFIRMED = new ImmutableStatus("CONFIRMED");
        String str = "CANCELLED";
        VEVENT_CANCELLED = new ImmutableStatus(str);
        VTODO_NEEDS_ACTION = new ImmutableStatus("NEEDS-ACTION");
        VTODO_COMPLETED = new ImmutableStatus(Property.COMPLETED);
        VTODO_IN_PROCESS = new ImmutableStatus("IN-PROCESS");
        VTODO_CANCELLED = new ImmutableStatus(str);
        VJOURNAL_DRAFT = new ImmutableStatus("DRAFT");
        VJOURNAL_FINAL = new ImmutableStatus("FINAL");
        VJOURNAL_CANCELLED = new ImmutableStatus(str);
    }

    private static final class ImmutableStatus extends Status {
        private static final long serialVersionUID = 7771868877237685612L;

        private ImmutableStatus(String str) {
            super(new ParameterList(true), str);
        }

        @Override // net.fortuna.ical4j.model.property.Status, net.fortuna.ical4j.model.Property
        public void setValue(String str) {
            throw new UnsupportedOperationException("Cannot modify constant instances");
        }
    }

    public Status() {
        super(Property.STATUS, new Factory());
    }

    public Status(String str) {
        super(Property.STATUS, new Factory());
        this.value = str;
    }

    public Status(ParameterList parameterList, String str) {
        super(Property.STATUS, parameterList, new Factory());
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

    public static class Factory extends Content.Factory implements PropertyFactory<Status> {
        private static final long serialVersionUID = 1;

        public Factory() {
            super(Property.STATUS);
        }

        @Override // net.fortuna.ical4j.model.PropertyFactory
        public Status createProperty(ParameterList parameterList, String str) throws URISyntaxException, IOException, ParseException {
            if (Status.VEVENT_CANCELLED.getValue().equals(str)) {
                return Status.VEVENT_CANCELLED;
            }
            if (Status.VEVENT_CONFIRMED.getValue().equals(str)) {
                return Status.VEVENT_CONFIRMED;
            }
            if (Status.VEVENT_TENTATIVE.getValue().equals(str)) {
                return Status.VEVENT_TENTATIVE;
            }
            if (Status.VJOURNAL_CANCELLED.getValue().equals(str)) {
                return Status.VJOURNAL_CANCELLED;
            }
            if (Status.VJOURNAL_DRAFT.getValue().equals(str)) {
                return Status.VJOURNAL_DRAFT;
            }
            if (Status.VJOURNAL_FINAL.getValue().equals(str)) {
                return Status.VJOURNAL_FINAL;
            }
            if (Status.VTODO_CANCELLED.getValue().equals(str)) {
                return Status.VTODO_CANCELLED;
            }
            if (Status.VTODO_COMPLETED.getValue().equals(str)) {
                return Status.VTODO_COMPLETED;
            }
            if (Status.VTODO_IN_PROCESS.getValue().equals(str)) {
                return Status.VTODO_IN_PROCESS;
            }
            if (Status.VTODO_NEEDS_ACTION.getValue().equals(str)) {
                return Status.VTODO_NEEDS_ACTION;
            }
            return new Status(parameterList, str);
        }

        @Override // net.fortuna.ical4j.model.PropertyFactory
        public Status createProperty() {
            return new Status();
        }
    }
}
