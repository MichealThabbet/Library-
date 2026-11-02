package com.ibm.icu.util;

/* loaded from: classes2.dex */
public class TimeUnitAmount extends Measure {
    public TimeUnitAmount(Number number, TimeUnit timeUnit) {
        super(number, timeUnit);
    }

    public TimeUnitAmount(double d, TimeUnit timeUnit) {
        super(new Double(d), timeUnit);
    }

    public TimeUnit getTimeUnit() {
        return (TimeUnit) getUnit();
    }
}
