package net.fortuna.ical4j.transform.command;

import net.fortuna.ical4j.model.Component;
import net.fortuna.ical4j.model.Property;
import net.fortuna.ical4j.model.PropertyList;
import net.fortuna.ical4j.model.property.Organizer;
import net.fortuna.ical4j.transform.Transformer;

/* loaded from: classes3.dex */
public class OrganizerUpdate implements Transformer<Component> {
    private final Organizer organizer;

    public OrganizerUpdate(Organizer organizer) {
        this.organizer = organizer;
    }

    @Override // net.fortuna.ical4j.transform.Transformer
    public Component transform(Component component) {
        PropertyList<Property> properties = component.getProperties();
        Organizer organizer = (Organizer) properties.getProperty(Property.ORGANIZER);
        if (organizer != null) {
            properties.remove((Property) organizer);
        }
        properties.add((PropertyList<Property>) this.organizer);
        return component;
    }
}
