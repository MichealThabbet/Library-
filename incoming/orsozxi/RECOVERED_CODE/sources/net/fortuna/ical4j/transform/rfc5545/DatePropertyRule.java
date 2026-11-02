package net.fortuna.ical4j.transform.rfc5545;

import net.fortuna.ical4j.model.property.DateProperty;

/* loaded from: classes3.dex */
public class DatePropertyRule implements Rfc5545PropertyRule<DateProperty> {
    @Override // net.fortuna.ical4j.transform.rfc5545.Rfc5545Rule
    public void applyTo(DateProperty dateProperty) {
        TzHelper.correctTzParameterFrom(dateProperty);
        if (!dateProperty.isUtc() || dateProperty.getParameter("TZID") == null) {
            return;
        }
        dateProperty.getParameters().removeAll("TZID");
        dateProperty.setUtc(true);
    }

    @Override // net.fortuna.ical4j.transform.rfc5545.Rfc5545Rule
    public Class<DateProperty> getSupportedType() {
        return DateProperty.class;
    }
}
