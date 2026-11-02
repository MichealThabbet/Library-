package com.ibm.icu.util;

import com.ibm.icu.impl.Grego;
import java.util.Date;

/* loaded from: classes2.dex */
public class AnnualTimeZoneRule extends TimeZoneRule {
    public static final int MAX_YEAR = Integer.MAX_VALUE;
    private static final long serialVersionUID = -8870666707791230688L;
    private final DateTimeRule dateTimeRule;
    private final int endYear;
    private final int startYear;

    @Override // com.ibm.icu.util.TimeZoneRule
    public boolean isTransitionRule() {
        return true;
    }

    public AnnualTimeZoneRule(String str, int i, int i2, DateTimeRule dateTimeRule, int i3, int i4) {
        super(str, i, i2);
        this.dateTimeRule = dateTimeRule;
        this.startYear = i3;
        this.endYear = i4;
    }

    public DateTimeRule getRule() {
        return this.dateTimeRule;
    }

    public int getStartYear() {
        return this.startYear;
    }

    public int getEndYear() {
        return this.endYear;
    }

    public Date getStartInYear(int i, int i2, int i3) {
        long jFieldsToDay;
        long jFieldsToDay2;
        if (i < this.startYear || i > this.endYear) {
            return null;
        }
        int dateRuleType = this.dateTimeRule.getDateRuleType();
        if (dateRuleType == 0) {
            jFieldsToDay2 = Grego.fieldsToDay(i, this.dateTimeRule.getRuleMonth(), this.dateTimeRule.getRuleDayOfMonth());
        } else {
            boolean z = false;
            if (dateRuleType == 1) {
                if (this.dateTimeRule.getRuleWeekInMonth() > 0) {
                    jFieldsToDay = Grego.fieldsToDay(i, this.dateTimeRule.getRuleMonth(), 1) + ((r0 - 1) * 7);
                    z = true;
                } else {
                    jFieldsToDay = Grego.fieldsToDay(i, this.dateTimeRule.getRuleMonth(), Grego.monthLength(i, this.dateTimeRule.getRuleMonth())) + ((r0 + 1) * 7);
                }
            } else {
                int ruleMonth = this.dateTimeRule.getRuleMonth();
                int ruleDayOfMonth = this.dateTimeRule.getRuleDayOfMonth();
                if (dateRuleType != 3) {
                    z = true;
                } else if (ruleMonth == 1 && ruleDayOfMonth == 29 && !Grego.isLeapYear(i)) {
                    ruleDayOfMonth--;
                }
                jFieldsToDay = Grego.fieldsToDay(i, ruleMonth, ruleDayOfMonth);
            }
            int ruleDayOfWeek = this.dateTimeRule.getRuleDayOfWeek() - Grego.dayOfWeek(jFieldsToDay);
            if (z) {
                if (ruleDayOfWeek < 0) {
                    ruleDayOfWeek += 7;
                }
            } else if (ruleDayOfWeek > 0) {
                ruleDayOfWeek -= 7;
            }
            jFieldsToDay2 = ruleDayOfWeek + jFieldsToDay;
        }
        long ruleMillisInDay = (jFieldsToDay2 * 86400000) + this.dateTimeRule.getRuleMillisInDay();
        if (this.dateTimeRule.getTimeRuleType() != 2) {
            ruleMillisInDay -= i2;
        }
        if (this.dateTimeRule.getTimeRuleType() == 0) {
            ruleMillisInDay -= i3;
        }
        return new Date(ruleMillisInDay);
    }

    @Override // com.ibm.icu.util.TimeZoneRule
    public Date getFirstStart(int i, int i2) {
        return getStartInYear(this.startYear, i, i2);
    }

    @Override // com.ibm.icu.util.TimeZoneRule
    public Date getFinalStart(int i, int i2) {
        int i3 = this.endYear;
        if (i3 == Integer.MAX_VALUE) {
            return null;
        }
        return getStartInYear(i3, i, i2);
    }

    @Override // com.ibm.icu.util.TimeZoneRule
    public Date getNextStart(long j, int i, int i2, boolean z) {
        int i3 = Grego.timeToFields(j, null)[0];
        if (i3 < this.startYear) {
            return getFirstStart(i, i2);
        }
        Date startInYear = getStartInYear(i3, i, i2);
        return startInYear != null ? (startInYear.getTime() < j || (!z && startInYear.getTime() == j)) ? getStartInYear(i3 + 1, i, i2) : startInYear : startInYear;
    }

    @Override // com.ibm.icu.util.TimeZoneRule
    public Date getPreviousStart(long j, int i, int i2, boolean z) {
        int i3 = Grego.timeToFields(j, null)[0];
        if (i3 > this.endYear) {
            return getFinalStart(i, i2);
        }
        Date startInYear = getStartInYear(i3, i, i2);
        return startInYear != null ? (startInYear.getTime() > j || (!z && startInYear.getTime() == j)) ? getStartInYear(i3 - 1, i, i2) : startInYear : startInYear;
    }

    @Override // com.ibm.icu.util.TimeZoneRule
    public boolean isEquivalentTo(TimeZoneRule timeZoneRule) {
        if (!(timeZoneRule instanceof AnnualTimeZoneRule)) {
            return false;
        }
        AnnualTimeZoneRule annualTimeZoneRule = (AnnualTimeZoneRule) timeZoneRule;
        if (this.startYear == annualTimeZoneRule.startYear && this.endYear == annualTimeZoneRule.endYear && this.dateTimeRule.equals(annualTimeZoneRule.dateTimeRule)) {
            return super.isEquivalentTo(timeZoneRule);
        }
        return false;
    }

    @Override // com.ibm.icu.util.TimeZoneRule
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(super.toString());
        sb.append(", rule={" + this.dateTimeRule + "}");
        StringBuilder sb2 = new StringBuilder();
        sb2.append(", startYear=");
        sb2.append(this.startYear);
        sb.append(sb2.toString());
        sb.append(", endYear=");
        int i = this.endYear;
        if (i == Integer.MAX_VALUE) {
            sb.append("max");
        } else {
            sb.append(i);
        }
        return sb.toString();
    }
}
