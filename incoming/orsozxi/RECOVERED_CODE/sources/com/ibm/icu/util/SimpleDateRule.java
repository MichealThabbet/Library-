package com.ibm.icu.util;

import java.util.Date;

/* loaded from: classes2.dex */
public class SimpleDateRule implements DateRule {
    private Calendar calendar;
    private int dayOfMonth;
    private int dayOfWeek;
    private int month;

    public SimpleDateRule(int i, int i2) {
        this.calendar = new GregorianCalendar();
        this.month = i;
        this.dayOfMonth = i2;
        this.dayOfWeek = 0;
    }

    SimpleDateRule(int i, int i2, Calendar calendar) {
        this.calendar = new GregorianCalendar();
        this.month = i;
        this.dayOfMonth = i2;
        this.dayOfWeek = 0;
        this.calendar = calendar;
    }

    public SimpleDateRule(int i, int i2, int i3, boolean z) {
        this.calendar = new GregorianCalendar();
        this.month = i;
        this.dayOfMonth = i2;
        this.dayOfWeek = z ? i3 : -i3;
    }

    @Override // com.ibm.icu.util.DateRule
    public Date firstAfter(Date date) {
        return doFirstBetween(date, null);
    }

    @Override // com.ibm.icu.util.DateRule
    public Date firstBetween(Date date, Date date2) {
        return doFirstBetween(date, date2);
    }

    @Override // com.ibm.icu.util.DateRule
    public boolean isOn(Date date) {
        boolean z;
        Calendar calendar = this.calendar;
        synchronized (calendar) {
            calendar.setTime(date);
            int i = calendar.get(6);
            z = true;
            calendar.setTime(computeInYear(calendar.get(1), calendar));
            if (calendar.get(6) != i) {
                z = false;
            }
        }
        return z;
    }

    @Override // com.ibm.icu.util.DateRule
    public boolean isBetween(Date date, Date date2) {
        return firstBetween(date, date2) != null;
    }

    private Date doFirstBetween(Date date, Date date2) {
        Calendar calendar = this.calendar;
        synchronized (calendar) {
            calendar.setTime(date);
            int i = calendar.get(1);
            int i2 = calendar.get(2);
            if (i2 > this.month) {
                i++;
            }
            Date dateComputeInYear = computeInYear(i, calendar);
            if (i2 == this.month && dateComputeInYear.before(date)) {
                dateComputeInYear = computeInYear(i + 1, calendar);
            }
            if (date2 == null || !dateComputeInYear.after(date2)) {
                return dateComputeInYear;
            }
            return null;
        }
    }

    private Date computeInYear(int i, Calendar calendar) {
        Date time;
        int i2;
        synchronized (calendar) {
            calendar.clear();
            calendar.set(0, calendar.getMaximum(0));
            calendar.set(1, i);
            calendar.set(2, this.month);
            calendar.set(5, this.dayOfMonth);
            if (this.dayOfWeek != 0) {
                calendar.setTime(calendar.getTime());
                int i3 = calendar.get(7);
                if (this.dayOfWeek > 0) {
                    i2 = ((this.dayOfWeek - i3) + 7) % 7;
                } else {
                    i2 = -(((this.dayOfWeek + i3) + 7) % 7);
                }
                calendar.add(5, i2);
            }
            time = calendar.getTime();
        }
        return time;
    }
}
