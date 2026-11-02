package com.ibm.icu.util;

import com.ibm.icu.impl.Grego;
import java.util.Date;

/* loaded from: classes2.dex */
public abstract class BasicTimeZone extends TimeZone {

    @Deprecated
    protected static final int FORMER_LATTER_MASK = 12;

    @Deprecated
    protected static final int LOCAL_DST = 3;

    @Deprecated
    protected static final int LOCAL_FORMER = 4;

    @Deprecated
    protected static final int LOCAL_LATTER = 12;

    @Deprecated
    protected static final int LOCAL_STD = 1;
    private static final long MILLIS_PER_YEAR = 31536000000L;

    @Deprecated
    protected static final int STD_DST_MASK = 3;
    private static final long serialVersionUID = -3204278532246180932L;

    public abstract TimeZoneTransition getNextTransition(long j, boolean z);

    public abstract TimeZoneTransition getPreviousTransition(long j, boolean z);

    public abstract TimeZoneRule[] getTimeZoneRules();

    public boolean hasEquivalentTransitions(TimeZone timeZone, long j, long j2) {
        return hasEquivalentTransitions(timeZone, j, j2, false);
    }

    /* JADX WARN: Code restructure failed: missing block: B:78:0x0165, code lost:
    
        return false;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public boolean hasEquivalentTransitions(com.ibm.icu.util.TimeZone r8, long r9, long r11, boolean r13) {
        /*
            Method dump skipped, instructions count: 402
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ibm.icu.util.BasicTimeZone.hasEquivalentTransitions(com.ibm.icu.util.TimeZone, long, long, boolean):boolean");
    }

    /* JADX WARN: Code restructure failed: missing block: B:18:0x0075, code lost:
    
        return (com.ibm.icu.util.TimeZoneRule[]) r12.toArray(new com.ibm.icu.util.TimeZoneRule[r12.size()]);
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public com.ibm.icu.util.TimeZoneRule[] getTimeZoneRules(long r28) {
        /*
            Method dump skipped, instructions count: 468
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ibm.icu.util.BasicTimeZone.getTimeZoneRules(long):com.ibm.icu.util.TimeZoneRule[]");
    }

    public TimeZoneRule[] getSimpleTimeZoneRulesNear(long j) {
        InitialTimeZoneRule initialTimeZoneRule;
        int i;
        AnnualTimeZoneRule[] annualTimeZoneRuleArr;
        int i2;
        String str;
        int i3;
        int rawOffset;
        int dSTSavings;
        String name;
        long j2;
        AnnualTimeZoneRule[] annualTimeZoneRuleArr2;
        int i4;
        TimeZoneTransition nextTransition;
        TimeZoneTransition nextTransition2 = getNextTransition(j, false);
        if (nextTransition2 != null) {
            String name2 = nextTransition2.getFrom().getName();
            int rawOffset2 = nextTransition2.getFrom().getRawOffset();
            int dSTSavings2 = nextTransition2.getFrom().getDSTSavings();
            long time = nextTransition2.getTime();
            if (((nextTransition2.getFrom().getDSTSavings() != 0 || nextTransition2.getTo().getDSTSavings() == 0) && (nextTransition2.getFrom().getDSTSavings() == 0 || nextTransition2.getTo().getDSTSavings() != 0)) || j + MILLIS_PER_YEAR <= time) {
                i2 = rawOffset2;
                str = name2;
                i3 = dSTSavings2;
            } else {
                AnnualTimeZoneRule[] annualTimeZoneRuleArr3 = new AnnualTimeZoneRule[2];
                str = name2;
                int[] iArrTimeToFields = Grego.timeToFields(nextTransition2.getFrom().getRawOffset() + time + nextTransition2.getFrom().getDSTSavings(), null);
                annualTimeZoneRuleArr3[0] = new AnnualTimeZoneRule(nextTransition2.getTo().getName(), rawOffset2, nextTransition2.getTo().getDSTSavings(), new DateTimeRule(iArrTimeToFields[1], Grego.getDayOfWeekInMonth(iArrTimeToFields[0], iArrTimeToFields[1], iArrTimeToFields[2]), iArrTimeToFields[3], iArrTimeToFields[5], 0), iArrTimeToFields[0], Integer.MAX_VALUE);
                if (nextTransition2.getTo().getRawOffset() != rawOffset2 || (nextTransition = getNextTransition(time, false)) == null || (((nextTransition.getFrom().getDSTSavings() != 0 || nextTransition.getTo().getDSTSavings() == 0) && (nextTransition.getFrom().getDSTSavings() == 0 || nextTransition.getTo().getDSTSavings() != 0)) || time + MILLIS_PER_YEAR <= nextTransition.getTime())) {
                    j2 = time;
                    annualTimeZoneRuleArr2 = annualTimeZoneRuleArr3;
                    i3 = dSTSavings2;
                    i4 = rawOffset2;
                } else {
                    iArrTimeToFields = Grego.timeToFields(nextTransition.getTime() + nextTransition.getFrom().getRawOffset() + nextTransition.getFrom().getDSTSavings(), iArrTimeToFields);
                    AnnualTimeZoneRule annualTimeZoneRule = new AnnualTimeZoneRule(nextTransition.getTo().getName(), nextTransition.getTo().getRawOffset(), nextTransition.getTo().getDSTSavings(), new DateTimeRule(iArrTimeToFields[1], Grego.getDayOfWeekInMonth(iArrTimeToFields[0], iArrTimeToFields[1], iArrTimeToFields[2]), iArrTimeToFields[3], iArrTimeToFields[5], 0), iArrTimeToFields[0] - 1, Integer.MAX_VALUE);
                    j2 = time;
                    annualTimeZoneRuleArr2 = annualTimeZoneRuleArr3;
                    i3 = dSTSavings2;
                    i4 = rawOffset2;
                    Date previousStart = annualTimeZoneRule.getPreviousStart(j, nextTransition.getFrom().getRawOffset(), nextTransition.getFrom().getDSTSavings(), true);
                    if (previousStart != null && previousStart.getTime() <= j && i4 == nextTransition.getTo().getRawOffset() && i3 == nextTransition.getTo().getDSTSavings()) {
                        annualTimeZoneRuleArr2[1] = annualTimeZoneRule;
                    }
                }
                char c = 1;
                if (annualTimeZoneRuleArr2[1] == null) {
                    TimeZoneTransition previousTransition = getPreviousTransition(j, true);
                    if (previousTransition == null || ((previousTransition.getFrom().getDSTSavings() != 0 || previousTransition.getTo().getDSTSavings() == 0) && (previousTransition.getFrom().getDSTSavings() == 0 || previousTransition.getTo().getDSTSavings() != 0))) {
                        i2 = i4;
                        c = 1;
                    } else {
                        int[] iArrTimeToFields2 = Grego.timeToFields(previousTransition.getTime() + previousTransition.getFrom().getRawOffset() + previousTransition.getFrom().getDSTSavings(), iArrTimeToFields);
                        i2 = i4;
                        AnnualTimeZoneRule annualTimeZoneRule2 = new AnnualTimeZoneRule(previousTransition.getTo().getName(), i2, i3, new DateTimeRule(iArrTimeToFields2[1], Grego.getDayOfWeekInMonth(iArrTimeToFields2[0], iArrTimeToFields2[1], iArrTimeToFields2[2]), iArrTimeToFields2[3], iArrTimeToFields2[5], 0), annualTimeZoneRuleArr2[0].getStartYear() - 1, Integer.MAX_VALUE);
                        c = 1;
                        if (annualTimeZoneRule2.getNextStart(j, previousTransition.getFrom().getRawOffset(), previousTransition.getFrom().getDSTSavings(), false).getTime() > j2) {
                            annualTimeZoneRuleArr2[1] = annualTimeZoneRule2;
                        }
                    }
                } else {
                    i2 = i4;
                }
                if (annualTimeZoneRuleArr2[c] != null) {
                    name = annualTimeZoneRuleArr2[0].getName();
                    rawOffset = annualTimeZoneRuleArr2[0].getRawOffset();
                    dSTSavings = annualTimeZoneRuleArr2[0].getDSTSavings();
                    annualTimeZoneRuleArr = annualTimeZoneRuleArr2;
                }
                initialTimeZoneRule = new InitialTimeZoneRule(name, rawOffset, dSTSavings);
                i = 1;
            }
            rawOffset = i2;
            dSTSavings = i3;
            name = str;
            annualTimeZoneRuleArr = null;
            initialTimeZoneRule = new InitialTimeZoneRule(name, rawOffset, dSTSavings);
            i = 1;
        } else {
            TimeZoneTransition previousTransition2 = getPreviousTransition(j, true);
            if (previousTransition2 != null) {
                initialTimeZoneRule = new InitialTimeZoneRule(previousTransition2.getTo().getName(), previousTransition2.getTo().getRawOffset(), previousTransition2.getTo().getDSTSavings());
                i = 1;
            } else {
                int[] iArr = new int[2];
                getOffset(j, false, iArr);
                i = 1;
                initialTimeZoneRule = new InitialTimeZoneRule(getID(), iArr[0], iArr[1]);
            }
            annualTimeZoneRuleArr = null;
        }
        if (annualTimeZoneRuleArr == null) {
            TimeZoneRule[] timeZoneRuleArr = new TimeZoneRule[i];
            timeZoneRuleArr[0] = initialTimeZoneRule;
            return timeZoneRuleArr;
        }
        TimeZoneRule[] timeZoneRuleArr2 = new TimeZoneRule[3];
        timeZoneRuleArr2[0] = initialTimeZoneRule;
        timeZoneRuleArr2[i] = annualTimeZoneRuleArr[0];
        timeZoneRuleArr2[2] = annualTimeZoneRuleArr[i];
        return timeZoneRuleArr2;
    }

    public enum LocalOption {
        FORMER(4),
        LATTER(12),
        STANDARD_FORMER(5),
        STANDARD_LATTER(13),
        DAYLIGHT_FORMER(7),
        DAYLIGHT_LATTER(15);

        private int flagVal;

        LocalOption(int i) {
            this.flagVal = i;
        }
    }

    @Deprecated
    protected static int getLocalOptionValue(LocalOption localOption) {
        return localOption.flagVal;
    }

    public void getOffsetFromLocal(long j, LocalOption localOption, LocalOption localOption2, int[] iArr) {
        throw new IllegalStateException("Not implemented");
    }

    protected BasicTimeZone() {
    }

    @Deprecated
    protected BasicTimeZone(String str) {
        super(str);
    }
}
