package org.ocpsoft.prettytime.units;

import net.fortuna.ical4j.util.Dates;
import org.ocpsoft.prettytime.TimeUnit;
import org.ocpsoft.prettytime.impl.ResourcesTimeUnit;

/* loaded from: classes3.dex */
public class Week extends ResourcesTimeUnit implements TimeUnit {
    @Override // org.ocpsoft.prettytime.impl.ResourcesTimeUnit
    protected String getResourceKeyPrefix() {
        return "Week";
    }

    public Week() {
        setMillisPerUnit(Dates.MILLIS_PER_WEEK);
    }
}
