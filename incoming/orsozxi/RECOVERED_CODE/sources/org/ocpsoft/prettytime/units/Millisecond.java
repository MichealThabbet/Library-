package org.ocpsoft.prettytime.units;

import org.ocpsoft.prettytime.TimeUnit;
import org.ocpsoft.prettytime.impl.ResourcesTimeUnit;

/* loaded from: classes3.dex */
public class Millisecond extends ResourcesTimeUnit implements TimeUnit {
    @Override // org.ocpsoft.prettytime.impl.ResourcesTimeUnit
    protected String getResourceKeyPrefix() {
        return "Millisecond";
    }

    public Millisecond() {
        setMillisPerUnit(1L);
    }
}
