package org.ocpsoft.prettytime.units;

import org.ocpsoft.prettytime.TimeUnit;
import org.ocpsoft.prettytime.impl.ResourcesTimeUnit;

/* loaded from: classes3.dex */
public class Decade extends ResourcesTimeUnit implements TimeUnit {
    @Override // org.ocpsoft.prettytime.impl.ResourcesTimeUnit
    protected String getResourceKeyPrefix() {
        return "Decade";
    }

    public Decade() {
        setMillisPerUnit(315569259747L);
    }
}
