package net.fortuna.ical4j.transform.rfc5545;

import net.fortuna.ical4j.model.property.DtStamp;

/* loaded from: classes3.dex */
public class DTStampRule implements Rfc5545PropertyRule<DtStamp> {
    @Override // net.fortuna.ical4j.transform.rfc5545.Rfc5545Rule
    public void applyTo(DtStamp dtStamp) {
        if (dtStamp.getValue() == null || dtStamp.isUtc()) {
            return;
        }
        dtStamp.setUtc(true);
    }

    @Override // net.fortuna.ical4j.transform.rfc5545.Rfc5545Rule
    public Class<DtStamp> getSupportedType() {
        return DtStamp.class;
    }
}
