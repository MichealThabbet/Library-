package org.ocpsoft.prettytime.impl;

import org.ocpsoft.prettytime.TimeUnit;

/* loaded from: classes3.dex */
public abstract class ResourcesTimeUnit implements TimeUnit {
    private long maxQuantity = 0;
    private long millisPerUnit = 1;

    protected String getResourceBundleName() {
        return "org.ocpsoft.prettytime.i18n.Resources";
    }

    protected abstract String getResourceKeyPrefix();

    @Override // org.ocpsoft.prettytime.TimeUnit
    public long getMaxQuantity() {
        return this.maxQuantity;
    }

    public void setMaxQuantity(long j) {
        this.maxQuantity = j;
    }

    @Override // org.ocpsoft.prettytime.TimeUnit
    public long getMillisPerUnit() {
        return this.millisPerUnit;
    }

    public void setMillisPerUnit(long j) {
        this.millisPerUnit = j;
    }

    public String toString() {
        return getResourceKeyPrefix();
    }
}
