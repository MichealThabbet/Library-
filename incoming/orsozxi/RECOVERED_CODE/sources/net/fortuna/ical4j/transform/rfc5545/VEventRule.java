package net.fortuna.ical4j.transform.rfc5545;

import java.util.Calendar;
import net.fortuna.ical4j.model.Date;
import net.fortuna.ical4j.model.Parameter;
import net.fortuna.ical4j.model.Property;
import net.fortuna.ical4j.model.PropertyList;
import net.fortuna.ical4j.model.component.VEvent;
import net.fortuna.ical4j.model.parameter.Value;
import net.fortuna.ical4j.model.property.DateProperty;
import net.fortuna.ical4j.model.property.DtStamp;

/* loaded from: classes3.dex */
public class VEventRule implements Rfc5545ComponentRule<VEvent> {
    @Override // net.fortuna.ical4j.transform.rfc5545.Rfc5545Rule
    public void applyTo(VEvent vEvent) {
        Property property = vEvent.getProperty(Property.DTSTART);
        Property property2 = vEvent.getProperty(Property.DTEND);
        Property property3 = vEvent.getProperty(Property.DURATION);
        if (property2 != null && property3 != null && property2.getValue() != null && property3 != null) {
            vEvent.getProperties().remove(property3);
        }
        if (property != null && property2 != null) {
            Parameter parameter = property.getParameter(Parameter.VALUE);
            Parameter parameter2 = property2.getParameter(Parameter.VALUE);
            if (parameter != null && parameter2 != null && parameter.getValue().equals(Value.DATE.getValue()) && parameter2.getValue().equals(Value.DATE.getValue()) && property.getValue().equals(property2.getValue()) && (property2 instanceof DateProperty)) {
                DateProperty dateProperty = (DateProperty) property2;
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(dateProperty.getDate());
                calendar.add(5, 1);
                dateProperty.setDate(new Date(calendar.getTime()));
            }
        }
        PropertyList properties = vEvent.getProperties(Property.DTSTAMP);
        if (properties == null || properties.isEmpty()) {
            vEvent.getProperties().add((PropertyList<Property>) new DtStamp());
        }
    }

    @Override // net.fortuna.ical4j.transform.rfc5545.Rfc5545Rule
    public Class<VEvent> getSupportedType() {
        return VEvent.class;
    }
}
