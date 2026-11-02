package com.ibm.icu.util;

import com.ibm.icu.impl.Grego;
import com.ibm.icu.util.BasicTimeZone;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes2.dex */
public class RuleBasedTimeZone extends BasicTimeZone {
    private static final long serialVersionUID = 7580833058949327935L;
    private AnnualTimeZoneRule[] finalRules;
    private List<TimeZoneRule> historicRules;
    private transient List<TimeZoneTransition> historicTransitions;
    private final InitialTimeZoneRule initialRule;
    private volatile transient boolean isFrozen;
    private transient boolean upToDate;

    private static int getLocalDelta(int i, int i2, int i3, int i4, int i5, int i6) {
        int i7 = i + i2;
        int i8 = i3 + i4;
        boolean z = false;
        boolean z2 = i2 != 0 && i4 == 0;
        if (i2 == 0 && i4 != 0) {
            z = true;
        }
        if (i8 - i7 >= 0) {
            int i9 = i5 & 3;
            if (i9 == 1 && z2) {
                return i7;
            }
            if (i9 == 3 && z) {
                return i7;
            }
            if ((i9 != 1 || !z) && ((i9 != 3 || !z2) && (i5 & 12) == 12)) {
                return i7;
            }
        } else {
            int i10 = i6 & 3;
            if ((i10 != 1 || !z2) && (i10 != 3 || !z)) {
                if (i10 == 1 && z) {
                    return i7;
                }
                if ((i10 == 3 && z2) || (i6 & 12) == 4) {
                    return i7;
                }
            }
        }
        return i8;
    }

    public RuleBasedTimeZone(String str, InitialTimeZoneRule initialTimeZoneRule) {
        super(str);
        this.isFrozen = false;
        this.initialRule = initialTimeZoneRule;
    }

    /* JADX WARN: Removed duplicated region for block: B:18:0x0039  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public void addTransitionRule(com.ibm.icu.util.TimeZoneRule r5) {
        /*
            r4 = this;
            boolean r0 = r4.isFrozen()
            if (r0 != 0) goto L54
            boolean r0 = r5.isTransitionRule()
            if (r0 == 0) goto L4c
            boolean r0 = r5 instanceof com.ibm.icu.util.AnnualTimeZoneRule
            r1 = 0
            if (r0 == 0) goto L39
            r0 = r5
            com.ibm.icu.util.AnnualTimeZoneRule r0 = (com.ibm.icu.util.AnnualTimeZoneRule) r0
            int r2 = r0.getEndYear()
            r3 = 2147483647(0x7fffffff, float:NaN)
            if (r2 != r3) goto L39
            com.ibm.icu.util.AnnualTimeZoneRule[] r5 = r4.finalRules
            if (r5 != 0) goto L29
            r5 = 2
            com.ibm.icu.util.AnnualTimeZoneRule[] r5 = new com.ibm.icu.util.AnnualTimeZoneRule[r5]
            r4.finalRules = r5
            r5[r1] = r0
            goto L49
        L29:
            r2 = 1
            r3 = r5[r2]
            if (r3 != 0) goto L31
            r5[r2] = r0
            goto L49
        L31:
            java.lang.IllegalStateException r5 = new java.lang.IllegalStateException
            java.lang.String r0 = "Too many final rules"
            r5.<init>(r0)
            throw r5
        L39:
            java.util.List<com.ibm.icu.util.TimeZoneRule> r0 = r4.historicRules
            if (r0 != 0) goto L44
            java.util.ArrayList r0 = new java.util.ArrayList
            r0.<init>()
            r4.historicRules = r0
        L44:
            java.util.List<com.ibm.icu.util.TimeZoneRule> r0 = r4.historicRules
            r0.add(r5)
        L49:
            r4.upToDate = r1
            return
        L4c:
            java.lang.IllegalArgumentException r5 = new java.lang.IllegalArgumentException
            java.lang.String r0 = "Rule must be a transition rule"
            r5.<init>(r0)
            throw r5
        L54:
            java.lang.UnsupportedOperationException r5 = new java.lang.UnsupportedOperationException
            java.lang.String r0 = "Attempt to modify a frozen RuleBasedTimeZone instance."
            r5.<init>(r0)
            throw r5
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ibm.icu.util.RuleBasedTimeZone.addTransitionRule(com.ibm.icu.util.TimeZoneRule):void");
    }

    @Override // com.ibm.icu.util.TimeZone
    public int getOffset(int i, int i2, int i3, int i4, int i5, int i6) {
        if (i == 0) {
            i2 = 1 - i2;
        }
        int[] iArr = new int[2];
        getOffset((Grego.fieldsToDay(i2, i3, i4) * 86400000) + i6, true, 3, 1, iArr);
        return iArr[0] + iArr[1];
    }

    @Override // com.ibm.icu.util.TimeZone
    public void getOffset(long j, boolean z, int[] iArr) {
        getOffset(j, z, 4, 12, iArr);
    }

    @Override // com.ibm.icu.util.BasicTimeZone
    public void getOffsetFromLocal(long j, BasicTimeZone.LocalOption localOption, BasicTimeZone.LocalOption localOption2, int[] iArr) {
        getOffset(j, true, getLocalOptionValue(localOption), getLocalOptionValue(localOption2), iArr);
    }

    @Override // com.ibm.icu.util.TimeZone
    public int getRawOffset() {
        int[] iArr = new int[2];
        getOffset(System.currentTimeMillis(), false, iArr);
        return iArr[0];
    }

    @Override // com.ibm.icu.util.TimeZone
    public boolean inDaylightTime(Date date) {
        int[] iArr = new int[2];
        getOffset(date.getTime(), false, iArr);
        return iArr[1] != 0;
    }

    @Override // com.ibm.icu.util.TimeZone
    public void setRawOffset(int i) {
        throw new UnsupportedOperationException("setRawOffset in RuleBasedTimeZone is not supported.");
    }

    @Override // com.ibm.icu.util.TimeZone
    public boolean useDaylightTime() {
        long jCurrentTimeMillis = System.currentTimeMillis();
        int[] iArr = new int[2];
        getOffset(jCurrentTimeMillis, false, iArr);
        if (iArr[1] != 0) {
            return true;
        }
        TimeZoneTransition nextTransition = getNextTransition(jCurrentTimeMillis, false);
        return (nextTransition == null || nextTransition.getTo().getDSTSavings() == 0) ? false : true;
    }

    @Override // com.ibm.icu.util.TimeZone
    public boolean observesDaylightTime() {
        long jCurrentTimeMillis = System.currentTimeMillis();
        int[] iArr = new int[2];
        getOffset(jCurrentTimeMillis, false, iArr);
        if (iArr[1] != 0) {
            return true;
        }
        BitSet bitSet = this.finalRules == null ? null : new BitSet(this.finalRules.length);
        while (true) {
            TimeZoneTransition nextTransition = getNextTransition(jCurrentTimeMillis, false);
            if (nextTransition == null) {
                break;
            }
            TimeZoneRule to = nextTransition.getTo();
            if (to.getDSTSavings() != 0) {
                return true;
            }
            if (bitSet != null) {
                int i = 0;
                while (true) {
                    AnnualTimeZoneRule[] annualTimeZoneRuleArr = this.finalRules;
                    if (i >= annualTimeZoneRuleArr.length) {
                        break;
                    }
                    if (annualTimeZoneRuleArr[i].equals(to)) {
                        bitSet.set(i);
                    }
                    i++;
                }
                if (bitSet.cardinality() == this.finalRules.length) {
                    break;
                }
            }
            jCurrentTimeMillis = nextTransition.getTime();
        }
        return false;
    }

    @Override // com.ibm.icu.util.TimeZone
    public boolean hasSameRules(TimeZone timeZone) {
        boolean z;
        if (this == timeZone) {
            return true;
        }
        if (!(timeZone instanceof RuleBasedTimeZone)) {
            return false;
        }
        RuleBasedTimeZone ruleBasedTimeZone = (RuleBasedTimeZone) timeZone;
        if (!this.initialRule.isEquivalentTo(ruleBasedTimeZone.initialRule)) {
            return false;
        }
        if (this.finalRules == null || ruleBasedTimeZone.finalRules == null) {
            if (this.finalRules == null && ruleBasedTimeZone.finalRules == null) {
            }
            return false;
        }
        int i = 0;
        while (true) {
            AnnualTimeZoneRule[] annualTimeZoneRuleArr = this.finalRules;
            if (i >= annualTimeZoneRuleArr.length) {
                break;
            }
            if (annualTimeZoneRuleArr[i] != null || ruleBasedTimeZone.finalRules[i] != null) {
                AnnualTimeZoneRule[] annualTimeZoneRuleArr2 = this.finalRules;
                if (annualTimeZoneRuleArr2[i] == null) {
                    break;
                }
                AnnualTimeZoneRule[] annualTimeZoneRuleArr3 = ruleBasedTimeZone.finalRules;
                if (annualTimeZoneRuleArr3[i] == null || !annualTimeZoneRuleArr2[i].isEquivalentTo(annualTimeZoneRuleArr3[i])) {
                    break;
                }
            }
            i++;
        }
        return false;
        List<TimeZoneRule> list = this.historicRules;
        if (list != null && ruleBasedTimeZone.historicRules != null) {
            if (list.size() != ruleBasedTimeZone.historicRules.size()) {
                return false;
            }
            for (TimeZoneRule timeZoneRule : this.historicRules) {
                Iterator<TimeZoneRule> it2 = ruleBasedTimeZone.historicRules.iterator();
                while (true) {
                    if (!it2.hasNext()) {
                        z = false;
                        break;
                    }
                    if (timeZoneRule.isEquivalentTo(it2.next())) {
                        z = true;
                        break;
                    }
                }
                if (!z) {
                    return false;
                }
            }
        } else if (this.historicRules != null || ruleBasedTimeZone.historicRules != null) {
            return false;
        }
        return true;
    }

    @Override // com.ibm.icu.util.BasicTimeZone
    public TimeZoneRule[] getTimeZoneRules() {
        int i;
        List<TimeZoneRule> list = this.historicRules;
        int size = list != null ? list.size() + 1 : 1;
        AnnualTimeZoneRule[] annualTimeZoneRuleArr = this.finalRules;
        if (annualTimeZoneRuleArr != null) {
            size = annualTimeZoneRuleArr[1] != null ? size + 2 : size + 1;
        }
        TimeZoneRule[] timeZoneRuleArr = new TimeZoneRule[size];
        timeZoneRuleArr[0] = this.initialRule;
        if (this.historicRules != null) {
            i = 1;
            while (i < this.historicRules.size() + 1) {
                timeZoneRuleArr[i] = this.historicRules.get(i - 1);
                i++;
            }
        } else {
            i = 1;
        }
        AnnualTimeZoneRule[] annualTimeZoneRuleArr2 = this.finalRules;
        if (annualTimeZoneRuleArr2 != null) {
            int i2 = i + 1;
            timeZoneRuleArr[i] = annualTimeZoneRuleArr2[0];
            if (annualTimeZoneRuleArr2[1] != null) {
                timeZoneRuleArr[i2] = annualTimeZoneRuleArr2[1];
            }
        }
        return timeZoneRuleArr;
    }

    @Override // com.ibm.icu.util.BasicTimeZone
    public TimeZoneTransition getNextTransition(long j, boolean z) {
        complete();
        List<TimeZoneTransition> list = this.historicTransitions;
        if (list == null) {
            return null;
        }
        TimeZoneTransition timeZoneTransition = list.get(0);
        long time = timeZoneTransition.getTime();
        boolean z2 = true;
        if (time > j || (z && time == j)) {
            z2 = false;
        } else {
            int size = this.historicTransitions.size() - 1;
            TimeZoneTransition timeZoneTransition2 = this.historicTransitions.get(size);
            long time2 = timeZoneTransition2.getTime();
            if (!z || time2 != j) {
                if (time2 <= j) {
                    AnnualTimeZoneRule[] annualTimeZoneRuleArr = this.finalRules;
                    if (annualTimeZoneRuleArr == null) {
                        return null;
                    }
                    Date nextStart = annualTimeZoneRuleArr[0].getNextStart(j, annualTimeZoneRuleArr[1].getRawOffset(), this.finalRules[1].getDSTSavings(), z);
                    AnnualTimeZoneRule[] annualTimeZoneRuleArr2 = this.finalRules;
                    Date nextStart2 = annualTimeZoneRuleArr2[1].getNextStart(j, annualTimeZoneRuleArr2[0].getRawOffset(), this.finalRules[0].getDSTSavings(), z);
                    if (nextStart2.after(nextStart)) {
                        long time3 = nextStart.getTime();
                        AnnualTimeZoneRule[] annualTimeZoneRuleArr3 = this.finalRules;
                        timeZoneTransition = new TimeZoneTransition(time3, annualTimeZoneRuleArr3[1], annualTimeZoneRuleArr3[0]);
                    } else {
                        long time4 = nextStart2.getTime();
                        AnnualTimeZoneRule[] annualTimeZoneRuleArr4 = this.finalRules;
                        timeZoneTransition = new TimeZoneTransition(time4, annualTimeZoneRuleArr4[0], annualTimeZoneRuleArr4[1]);
                    }
                } else {
                    int i = size - 1;
                    while (i > 0) {
                        TimeZoneTransition timeZoneTransition3 = this.historicTransitions.get(i);
                        long time5 = timeZoneTransition3.getTime();
                        if (time5 < j || (!z && time5 == j)) {
                            break;
                        }
                        i--;
                        timeZoneTransition2 = timeZoneTransition3;
                    }
                }
            }
            timeZoneTransition = timeZoneTransition2;
            z2 = false;
        }
        TimeZoneRule from = timeZoneTransition.getFrom();
        TimeZoneRule to = timeZoneTransition.getTo();
        if (from.getRawOffset() != to.getRawOffset() || from.getDSTSavings() != to.getDSTSavings()) {
            return timeZoneTransition;
        }
        if (z2) {
            return null;
        }
        return getNextTransition(timeZoneTransition.getTime(), false);
    }

    @Override // com.ibm.icu.util.BasicTimeZone
    public TimeZoneTransition getPreviousTransition(long j, boolean z) {
        complete();
        List<TimeZoneTransition> list = this.historicTransitions;
        if (list == null) {
            return null;
        }
        TimeZoneTransition timeZoneTransition = list.get(0);
        long time = timeZoneTransition.getTime();
        if (!z || time != j) {
            if (time >= j) {
                return null;
            }
            int size = this.historicTransitions.size() - 1;
            TimeZoneTransition timeZoneTransition2 = this.historicTransitions.get(size);
            long time2 = timeZoneTransition2.getTime();
            if (z && time2 == j) {
                timeZoneTransition = timeZoneTransition2;
            } else {
                if (time2 >= j) {
                    while (true) {
                        size--;
                        if (size < 0) {
                            break;
                        }
                        timeZoneTransition2 = this.historicTransitions.get(size);
                        long time3 = timeZoneTransition2.getTime();
                        if (time3 < j || (z && time3 == j)) {
                            break;
                        }
                    }
                } else {
                    AnnualTimeZoneRule[] annualTimeZoneRuleArr = this.finalRules;
                    if (annualTimeZoneRuleArr != null) {
                        Date previousStart = annualTimeZoneRuleArr[0].getPreviousStart(j, annualTimeZoneRuleArr[1].getRawOffset(), this.finalRules[1].getDSTSavings(), z);
                        AnnualTimeZoneRule[] annualTimeZoneRuleArr2 = this.finalRules;
                        Date previousStart2 = annualTimeZoneRuleArr2[1].getPreviousStart(j, annualTimeZoneRuleArr2[0].getRawOffset(), this.finalRules[0].getDSTSavings(), z);
                        if (previousStart2.before(previousStart)) {
                            long time4 = previousStart.getTime();
                            AnnualTimeZoneRule[] annualTimeZoneRuleArr3 = this.finalRules;
                            timeZoneTransition = new TimeZoneTransition(time4, annualTimeZoneRuleArr3[1], annualTimeZoneRuleArr3[0]);
                        } else {
                            long time5 = previousStart2.getTime();
                            AnnualTimeZoneRule[] annualTimeZoneRuleArr4 = this.finalRules;
                            timeZoneTransition = new TimeZoneTransition(time5, annualTimeZoneRuleArr4[0], annualTimeZoneRuleArr4[1]);
                        }
                    }
                }
                timeZoneTransition = timeZoneTransition2;
            }
        }
        TimeZoneRule from = timeZoneTransition.getFrom();
        TimeZoneRule to = timeZoneTransition.getTo();
        return (from.getRawOffset() == to.getRawOffset() && from.getDSTSavings() == to.getDSTSavings()) ? getPreviousTransition(timeZoneTransition.getTime(), false) : timeZoneTransition;
    }

    @Override // com.ibm.icu.util.TimeZone
    public Object clone() {
        return isFrozen() ? this : cloneAsThawed();
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:17:0x0022  */
    /* JADX WARN: Removed duplicated region for block: B:53:0x00c8  */
    /* JADX WARN: Type inference failed for: r11v4, types: [com.ibm.icu.util.TimeZoneRule] */
    /* JADX WARN: Type inference failed for: r8v7, types: [com.ibm.icu.util.AnnualTimeZoneRule[]] */
    /* JADX WARN: Type inference failed for: r8v8 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    private void complete() {
        /*
            Method dump skipped, instructions count: 484
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ibm.icu.util.RuleBasedTimeZone.complete():void");
    }

    private void getOffset(long j, boolean z, int i, int i2, int[] iArr) {
        TimeZoneRule to;
        complete();
        List<TimeZoneTransition> list = this.historicTransitions;
        if (list == null || j < getTransitionTime(list.get(0), z, i, i2)) {
            to = this.initialRule;
        } else {
            int size = this.historicTransitions.size() - 1;
            if (j > getTransitionTime(this.historicTransitions.get(size), z, i, i2)) {
                to = this.finalRules != null ? findRuleInFinal(j, z, i, i2) : null;
                if (to == null) {
                    to = this.historicTransitions.get(size).getTo();
                }
            } else {
                while (size >= 0 && j < getTransitionTime(this.historicTransitions.get(size), z, i, i2)) {
                    size--;
                }
                to = this.historicTransitions.get(size).getTo();
            }
        }
        iArr[0] = to.getRawOffset();
        iArr[1] = to.getDSTSavings();
    }

    private TimeZoneRule findRuleInFinal(long j, boolean z, int i, int i2) {
        AnnualTimeZoneRule[] annualTimeZoneRuleArr = this.finalRules;
        if (annualTimeZoneRuleArr != null && annualTimeZoneRuleArr.length == 2 && annualTimeZoneRuleArr[0] != null && annualTimeZoneRuleArr[1] != null) {
            long localDelta = z ? j - getLocalDelta(annualTimeZoneRuleArr[1].getRawOffset(), this.finalRules[1].getDSTSavings(), this.finalRules[0].getRawOffset(), this.finalRules[0].getDSTSavings(), i, i2) : j;
            AnnualTimeZoneRule[] annualTimeZoneRuleArr2 = this.finalRules;
            Date previousStart = annualTimeZoneRuleArr2[0].getPreviousStart(localDelta, annualTimeZoneRuleArr2[1].getRawOffset(), this.finalRules[1].getDSTSavings(), true);
            long localDelta2 = z ? j - getLocalDelta(this.finalRules[0].getRawOffset(), this.finalRules[0].getDSTSavings(), this.finalRules[1].getRawOffset(), this.finalRules[1].getDSTSavings(), i, i2) : j;
            AnnualTimeZoneRule[] annualTimeZoneRuleArr3 = this.finalRules;
            Date previousStart2 = annualTimeZoneRuleArr3[1].getPreviousStart(localDelta2, annualTimeZoneRuleArr3[0].getRawOffset(), this.finalRules[0].getDSTSavings(), true);
            if (previousStart != null && previousStart2 != null) {
                return previousStart.after(previousStart2) ? this.finalRules[0] : this.finalRules[1];
            }
            if (previousStart != null) {
                return this.finalRules[0];
            }
            if (previousStart2 != null) {
                return this.finalRules[1];
            }
        }
        return null;
    }

    private static long getTransitionTime(TimeZoneTransition timeZoneTransition, boolean z, int i, int i2) {
        long time = timeZoneTransition.getTime();
        return z ? time + getLocalDelta(timeZoneTransition.getFrom().getRawOffset(), timeZoneTransition.getFrom().getDSTSavings(), timeZoneTransition.getTo().getRawOffset(), timeZoneTransition.getTo().getDSTSavings(), i, i2) : time;
    }

    @Override // com.ibm.icu.util.TimeZone, com.ibm.icu.util.Freezable
    public boolean isFrozen() {
        return this.isFrozen;
    }

    @Override // com.ibm.icu.util.TimeZone, com.ibm.icu.util.Freezable
    public TimeZone freeze() {
        complete();
        this.isFrozen = true;
        return this;
    }

    @Override // com.ibm.icu.util.TimeZone, com.ibm.icu.util.Freezable
    public TimeZone cloneAsThawed() {
        RuleBasedTimeZone ruleBasedTimeZone = (RuleBasedTimeZone) super.cloneAsThawed();
        if (this.historicRules != null) {
            ruleBasedTimeZone.historicRules = new ArrayList(this.historicRules);
        }
        AnnualTimeZoneRule[] annualTimeZoneRuleArr = this.finalRules;
        if (annualTimeZoneRuleArr != null) {
            ruleBasedTimeZone.finalRules = (AnnualTimeZoneRule[]) annualTimeZoneRuleArr.clone();
        }
        ruleBasedTimeZone.isFrozen = false;
        return ruleBasedTimeZone;
    }
}
