package com.ibm.icu.impl;

import java.util.Date;
import java.util.TimeZone;

/* loaded from: classes2.dex */
public class TimeZoneAdapter extends TimeZone {
    static final long serialVersionUID = -2040072218820018557L;
    private com.ibm.icu.util.TimeZone zone;

    public static TimeZone wrap(com.ibm.icu.util.TimeZone timeZone) {
        return new TimeZoneAdapter(timeZone);
    }

    public com.ibm.icu.util.TimeZone unwrap() {
        return this.zone;
    }

    public TimeZoneAdapter(com.ibm.icu.util.TimeZone timeZone) {
        this.zone = timeZone;
        super.setID(timeZone.getID());
    }

    @Override // java.util.TimeZone
    public void setID(String str) {
        super.setID(str);
        this.zone.setID(str);
    }

    @Override // java.util.TimeZone
    public boolean hasSameRules(TimeZone timeZone) {
        return (timeZone instanceof TimeZoneAdapter) && this.zone.hasSameRules(((TimeZoneAdapter) timeZone).zone);
    }

    @Override // java.util.TimeZone
    public int getOffset(int i, int i2, int i3, int i4, int i5, int i6) {
        return this.zone.getOffset(i, i2, i3, i4, i5, i6);
    }

    @Override // java.util.TimeZone
    public int getRawOffset() {
        return this.zone.getRawOffset();
    }

    @Override // java.util.TimeZone
    public void setRawOffset(int i) {
        this.zone.setRawOffset(i);
    }

    @Override // java.util.TimeZone
    public boolean useDaylightTime() {
        return this.zone.useDaylightTime();
    }

    @Override // java.util.TimeZone
    public boolean inDaylightTime(Date date) {
        return this.zone.inDaylightTime(date);
    }

    @Override // java.util.TimeZone
    public Object clone() {
        return new TimeZoneAdapter((com.ibm.icu.util.TimeZone) this.zone.clone());
    }

    public synchronized int hashCode() {
        return this.zone.hashCode();
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof TimeZoneAdapter)) {
            return false;
        }
        return this.zone.equals(((TimeZoneAdapter) obj).zone);
    }

    public String toString() {
        return "TimeZoneAdapter: " + this.zone.toString();
    }
}
