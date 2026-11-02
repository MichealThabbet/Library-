package net.fortuna.ical4j.transform.rfc5545;

import net.fortuna.ical4j.model.Property;
import net.fortuna.ical4j.model.PropertyList;
import net.fortuna.ical4j.model.component.VAlarm;
import net.fortuna.ical4j.model.property.Action;
import net.fortuna.ical4j.model.property.Description;

/* loaded from: classes3.dex */
public class VAlarmRule implements Rfc5545ComponentRule<VAlarm> {
    @Override // net.fortuna.ical4j.transform.rfc5545.Rfc5545Rule
    public void applyTo(VAlarm vAlarm) {
        Action action = vAlarm.getAction();
        if (action == null || !"DISPLAY".equals(action.getValue())) {
            return;
        }
        if (vAlarm.getDescription() == null || vAlarm.getDescription().getValue() == null) {
            vAlarm.getProperties().add((PropertyList<Property>) new Description("display"));
        }
    }

    @Override // net.fortuna.ical4j.transform.rfc5545.Rfc5545Rule
    public Class<VAlarm> getSupportedType() {
        return VAlarm.class;
    }
}
