package org.ocpsoft.prettytime.units;

import org.ocpsoft.prettytime.TimeUnit;
import org.ocpsoft.prettytime.impl.ResourcesTimeUnit;

/* loaded from: classes3.dex */
public class Century extends ResourcesTimeUnit implements TimeUnit {
    @Override // org.ocpsoft.prettytime.impl.ResourcesTimeUnit
    protected String getResourceKeyPrefix() {
        return "Century";
    }

    public Century() {
        setMillisPerUnit(3155692597470L);
    }
}
