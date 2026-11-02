package org.ocpsoft.prettytime.impl;

import org.ocpsoft.prettytime.Duration;
import org.ocpsoft.prettytime.TimeUnit;

/* loaded from: classes3.dex */
public class DurationImpl implements Duration {
    private long delta;
    private long quantity;
    private TimeUnit unit;

    @Override // org.ocpsoft.prettytime.Duration
    public long getQuantity() {
        return this.quantity;
    }

    public void setQuantity(long j) {
        this.quantity = j;
    }

    @Override // org.ocpsoft.prettytime.Duration
    public TimeUnit getUnit() {
        return this.unit;
    }

    public void setUnit(TimeUnit timeUnit) {
        this.unit = timeUnit;
    }

    @Override // org.ocpsoft.prettytime.Duration
    public long getDelta() {
        return this.delta;
    }

    public void setDelta(long j) {
        this.delta = j;
    }

    @Override // org.ocpsoft.prettytime.Duration
    public boolean isInPast() {
        return getQuantity() < 0;
    }

    @Override // org.ocpsoft.prettytime.Duration
    public boolean isInFuture() {
        return !isInPast();
    }

    @Override // org.ocpsoft.prettytime.Duration
    public long getQuantityRounded(int i) {
        long jAbs = Math.abs(getQuantity());
        return (getDelta() == 0 || Math.abs((((double) getDelta()) / ((double) getUnit().getMillisPerUnit())) * 100.0d) <= ((double) i)) ? jAbs : jAbs + 1;
    }

    public String toString() {
        return "DurationImpl [" + this.quantity + " " + this.unit + ", delta=" + this.delta + "]";
    }
}
