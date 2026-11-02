package net.fortuna.ical4j.transform.command;

import net.fortuna.ical4j.model.Property;
import net.fortuna.ical4j.model.PropertyList;
import net.fortuna.ical4j.model.component.CalendarComponent;
import net.fortuna.ical4j.model.property.Sequence;
import net.fortuna.ical4j.transform.Transformer;

/* loaded from: classes3.dex */
public class SequenceIncrement implements Transformer<CalendarComponent> {
    @Override // net.fortuna.ical4j.transform.Transformer
    public CalendarComponent transform(CalendarComponent calendarComponent) {
        PropertyList<Property> properties = calendarComponent.getProperties();
        Sequence sequence = (Sequence) properties.getProperty(Property.SEQUENCE);
        if (sequence == null) {
            properties.add((PropertyList<Property>) new Sequence(0));
        } else {
            properties.remove((Property) sequence);
            properties.add((PropertyList<Property>) new Sequence(sequence.getSequenceNo() + 1));
        }
        return calendarComponent;
    }
}
