package net.fortuna.ical4j.transform.command;

import net.fortuna.ical4j.model.Component;
import net.fortuna.ical4j.model.Property;
import net.fortuna.ical4j.model.PropertyList;
import net.fortuna.ical4j.model.property.Attendee;
import net.fortuna.ical4j.transform.Transformer;

/* loaded from: classes3.dex */
public class AttendeeUpdate implements Transformer<Component> {
    private final Attendee attendee;

    public AttendeeUpdate(Attendee attendee) {
        this.attendee = attendee;
    }

    @Override // net.fortuna.ical4j.transform.Transformer
    public Component transform(Component component) {
        PropertyList<C> properties = component.getProperties().getProperties(Property.ATTENDEE);
        if (properties.contains(this.attendee)) {
            properties.remove((Property) this.attendee);
        }
        properties.add((PropertyList<C>) this.attendee);
        component.getProperties().addAll(properties);
        return component;
    }
}
