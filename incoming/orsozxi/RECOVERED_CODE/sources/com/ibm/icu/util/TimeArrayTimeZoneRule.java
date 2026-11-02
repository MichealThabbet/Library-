package com.ibm.icu.util;

import java.util.Arrays;
import java.util.Date;

/* loaded from: classes2.dex */
public class TimeArrayTimeZoneRule extends TimeZoneRule {
    private static final long serialVersionUID = -1117109130077415245L;
    private final long[] startTimes;
    private final int timeType;

    @Override // com.ibm.icu.util.TimeZoneRule
    public boolean isTransitionRule() {
        return true;
    }

    public TimeArrayTimeZoneRule(String str, int i, int i2, long[] jArr, int i3) {
        super(str, i, i2);
        if (jArr == null || jArr.length == 0) {
            throw new IllegalArgumentException("No start times are specified.");
        }
        long[] jArr2 = (long[]) jArr.clone();
        this.startTimes = jArr2;
        Arrays.sort(jArr2);
        this.timeType = i3;
    }

    public long[] getStartTimes() {
        return (long[]) this.startTimes.clone();
    }

    public int getTimeType() {
        return this.timeType;
    }

    @Override // com.ibm.icu.util.TimeZoneRule
    public Date getFirstStart(int i, int i2) {
        return new Date(getUTC(this.startTimes[0], i, i2));
    }

    @Override // com.ibm.icu.util.TimeZoneRule
    public Date getFinalStart(int i, int i2) {
        return new Date(getUTC(this.startTimes[r1.length - 1], i, i2));
    }

    @Override // com.ibm.icu.util.TimeZoneRule
    public Date getNextStart(long j, int i, int i2, boolean z) {
        int length = this.startTimes.length - 1;
        while (length >= 0) {
            long utc = getUTC(this.startTimes[length], i, i2);
            if (utc < j || (!z && utc == j)) {
                break;
            }
            length--;
        }
        if (length == this.startTimes.length - 1) {
            return null;
        }
        return new Date(getUTC(this.startTimes[length + 1], i, i2));
    }

    @Override // com.ibm.icu.util.TimeZoneRule
    public Date getPreviousStart(long j, int i, int i2, boolean z) {
        for (int length = this.startTimes.length - 1; length >= 0; length--) {
            long utc = getUTC(this.startTimes[length], i, i2);
            if (utc < j || (z && utc == j)) {
                return new Date(utc);
            }
        }
        return null;
    }

    @Override // com.ibm.icu.util.TimeZoneRule
    public boolean isEquivalentTo(TimeZoneRule timeZoneRule) {
        if (!(timeZoneRule instanceof TimeArrayTimeZoneRule)) {
            return false;
        }
        TimeArrayTimeZoneRule timeArrayTimeZoneRule = (TimeArrayTimeZoneRule) timeZoneRule;
        if (this.timeType == timeArrayTimeZoneRule.timeType && Arrays.equals(this.startTimes, timeArrayTimeZoneRule.startTimes)) {
            return super.isEquivalentTo(timeZoneRule);
        }
        return false;
    }

    private long getUTC(long j, int i, int i2) {
        if (this.timeType != 2) {
            j -= i;
        }
        return this.timeType == 0 ? j - i2 : j;
    }

    @Override // com.ibm.icu.util.TimeZoneRule
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(super.toString());
        sb.append(", timeType=");
        sb.append(this.timeType);
        sb.append(", startTimes=[");
        for (int i = 0; i < this.startTimes.length; i++) {
            if (i != 0) {
                sb.append(", ");
            }
            sb.append(Long.toString(this.startTimes[i]));
        }
        sb.append("]");
        return sb.toString();
    }
}
