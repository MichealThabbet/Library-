package net.fortuna.ical4j.transform.command;

import net.fortuna.ical4j.model.Calendar;
import net.fortuna.ical4j.model.Property;
import net.fortuna.ical4j.model.PropertyList;
import net.fortuna.ical4j.model.property.Method;
import net.fortuna.ical4j.transform.Transformer;

/* loaded from: classes3.dex */
public class MethodUpdate implements Transformer<Calendar> {
    private final Method newMethod;

    public MethodUpdate(Method method) {
        this.newMethod = method;
    }

    @Override // net.fortuna.ical4j.transform.Transformer
    public Calendar transform(Calendar calendar) {
        PropertyList<Property> properties = calendar.getProperties();
        Property property = (Property) properties.getProperty(Property.METHOD);
        if (property != null) {
            properties.remove(property);
        }
        properties.add((PropertyList<Property>) this.newMethod);
        return calendar;
    }
}
