package net.fortuna.ical4j.transform.rfc5545;

import net.fortuna.ical4j.model.property.TzId;

/* loaded from: classes3.dex */
public class TzIdRule implements Rfc5545PropertyRule<TzId> {
    @Override // net.fortuna.ical4j.transform.rfc5545.Rfc5545Rule
    public void applyTo(TzId tzId) {
        TzHelper.correctTzValueOf(tzId);
    }

    @Override // net.fortuna.ical4j.transform.rfc5545.Rfc5545Rule
    public Class<TzId> getSupportedType() {
        return TzId.class;
    }
}
