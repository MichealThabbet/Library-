package net.fortuna.ical4j.transform.rfc5545;

import net.fortuna.ical4j.model.property.DateListProperty;

/* loaded from: classes3.dex */
public class DateListPropertyRule implements Rfc5545PropertyRule<DateListProperty> {
    @Override // net.fortuna.ical4j.transform.rfc5545.Rfc5545Rule
    public void applyTo(DateListProperty dateListProperty) {
        TzHelper.correctTzParameterFrom(dateListProperty);
    }

    @Override // net.fortuna.ical4j.transform.rfc5545.Rfc5545Rule
    public Class<DateListProperty> getSupportedType() {
        return DateListProperty.class;
    }
}
