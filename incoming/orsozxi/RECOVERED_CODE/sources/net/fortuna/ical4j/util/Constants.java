package net.fortuna.ical4j.util;

import net.fortuna.ical4j.model.Property;
import net.fortuna.ical4j.model.property.Action;
import net.fortuna.ical4j.model.property.CalScale;
import net.fortuna.ical4j.model.property.Clazz;
import net.fortuna.ical4j.model.property.Method;
import net.fortuna.ical4j.model.property.Priority;
import net.fortuna.ical4j.model.property.Status;
import net.fortuna.ical4j.model.property.Transp;
import net.fortuna.ical4j.model.property.Version;

/* loaded from: classes3.dex */
public final class Constants {
    private Constants() {
    }

    public static Property forProperty(Property property) {
        if (Action.AUDIO.equals(property)) {
            return Action.AUDIO;
        }
        if (Action.DISPLAY.equals(property)) {
            return Action.DISPLAY;
        }
        if (Action.EMAIL.equals(property)) {
            return Action.EMAIL;
        }
        if (Action.PROCEDURE.equals(property)) {
            return Action.PROCEDURE;
        }
        if (CalScale.GREGORIAN.equals(property)) {
            return CalScale.GREGORIAN;
        }
        if (Clazz.CONFIDENTIAL.equals(property)) {
            return Clazz.CONFIDENTIAL;
        }
        if (Clazz.PRIVATE.equals(property)) {
            return Clazz.PRIVATE;
        }
        if (Clazz.PUBLIC.equals(property)) {
            return Clazz.PUBLIC;
        }
        if (Method.ADD.equals(property)) {
            return Method.ADD;
        }
        if (Method.CANCEL.equals(property)) {
            return Method.CANCEL;
        }
        if (Method.COUNTER.equals(property)) {
            return Method.COUNTER;
        }
        if (Method.DECLINE_COUNTER.equals(property)) {
            return Method.DECLINE_COUNTER;
        }
        if (Method.PUBLISH.equals(property)) {
            return Method.PUBLISH;
        }
        if (Method.REFRESH.equals(property)) {
            return Method.REFRESH;
        }
        if (Method.REPLY.equals(property)) {
            return Method.REPLY;
        }
        if (Method.REQUEST.equals(property)) {
            return Method.REQUEST;
        }
        if (Priority.HIGH.equals(property)) {
            return Priority.HIGH;
        }
        if (Priority.LOW.equals(property)) {
            return Priority.LOW;
        }
        if (Priority.MEDIUM.equals(property)) {
            return Priority.MEDIUM;
        }
        if (Priority.UNDEFINED.equals(property)) {
            return Priority.UNDEFINED;
        }
        if (Status.VEVENT_CANCELLED.equals(property)) {
            return Status.VEVENT_CANCELLED;
        }
        if (Status.VEVENT_CONFIRMED.equals(property)) {
            return Status.VEVENT_CONFIRMED;
        }
        if (Status.VEVENT_TENTATIVE.equals(property)) {
            return Status.VEVENT_TENTATIVE;
        }
        if (Status.VJOURNAL_CANCELLED.equals(property)) {
            return Status.VJOURNAL_CANCELLED;
        }
        if (Status.VJOURNAL_DRAFT.equals(property)) {
            return Status.VJOURNAL_DRAFT;
        }
        if (Status.VJOURNAL_FINAL.equals(property)) {
            return Status.VJOURNAL_FINAL;
        }
        if (Status.VTODO_CANCELLED.equals(property)) {
            return Status.VTODO_CANCELLED;
        }
        if (Status.VTODO_COMPLETED.equals(property)) {
            return Status.VTODO_COMPLETED;
        }
        if (Status.VTODO_IN_PROCESS.equals(property)) {
            return Status.VTODO_IN_PROCESS;
        }
        if (Status.VTODO_NEEDS_ACTION.equals(property)) {
            return Status.VTODO_NEEDS_ACTION;
        }
        if (Transp.OPAQUE.equals(property)) {
            return Transp.OPAQUE;
        }
        if (Transp.TRANSPARENT.equals(property)) {
            return Transp.TRANSPARENT;
        }
        return Version.VERSION_2_0.equals(property) ? Version.VERSION_2_0 : property;
    }
}
