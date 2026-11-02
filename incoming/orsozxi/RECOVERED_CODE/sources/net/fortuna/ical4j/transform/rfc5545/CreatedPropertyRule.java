package net.fortuna.ical4j.transform.rfc5545;

import java.text.ParseException;
import net.fortuna.ical4j.model.property.Created;

/* loaded from: classes3.dex */
public class CreatedPropertyRule implements Rfc5545PropertyRule<Created> {
    private static final String UTC_MARKER = "Z";

    @Override // net.fortuna.ical4j.transform.rfc5545.Rfc5545Rule
    public void applyTo(Created created) {
        if (created.isUtc() || created.getTimeZone() != null) {
            return;
        }
        try {
            created.setValue(created.getValue() + UTC_MARKER);
        } catch (ParseException unused) {
        }
    }

    @Override // net.fortuna.ical4j.transform.rfc5545.Rfc5545Rule
    public Class<Created> getSupportedType() {
        return Created.class;
    }
}
