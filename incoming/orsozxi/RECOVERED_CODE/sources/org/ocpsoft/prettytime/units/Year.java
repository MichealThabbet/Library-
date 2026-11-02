package org.ocpsoft.prettytime.units;

import org.ocpsoft.prettytime.TimeUnit;
import org.ocpsoft.prettytime.impl.ResourcesTimeUnit;

/* loaded from: classes3.dex */
public class Year extends ResourcesTimeUnit implements TimeUnit {
    @Override // org.ocpsoft.prettytime.impl.ResourcesTimeUnit
    protected String getResourceKeyPrefix() {
        return "Year";
    }

    public Year() {
        setMillisPerUnit(31556925960L);
    }
}
