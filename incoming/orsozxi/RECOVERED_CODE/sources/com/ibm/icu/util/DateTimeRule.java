package com.ibm.icu.util;

import java.io.Serializable;

/* loaded from: classes2.dex */
public class DateTimeRule implements Serializable {
    public static final int DOM = 0;
    public static final int DOW = 1;
    public static final int DOW_GEQ_DOM = 2;
    public static final int DOW_LEQ_DOM = 3;
    public static final int STANDARD_TIME = 1;
    public static final int UTC_TIME = 2;
    public static final int WALL_TIME = 0;
    private static final long serialVersionUID = 2183055795738051443L;
    private final int dateRuleType;
    private final int dayOfMonth;
    private final int dayOfWeek;
    private final int millisInDay;
    private final int month;
    private final int timeRuleType;
    private final int weekInMonth;
    private static final String[] DOWSTR = {"", "Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"};
    private static final String[] MONSTR = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};

    public DateTimeRule(int i, int i2, int i3, int i4) {
        this.dateRuleType = 0;
        this.month = i;
        this.dayOfMonth = i2;
        this.millisInDay = i3;
        this.timeRuleType = i4;
        this.dayOfWeek = 0;
        this.weekInMonth = 0;
    }

    public DateTimeRule(int i, int i2, int i3, int i4, int i5) {
        this.dateRuleType = 1;
        this.month = i;
        this.weekInMonth = i2;
        this.dayOfWeek = i3;
        this.millisInDay = i4;
        this.timeRuleType = i5;
        this.dayOfMonth = 0;
    }

    public DateTimeRule(int i, int i2, int i3, boolean z, int i4, int i5) {
        this.dateRuleType = z ? 2 : 3;
        this.month = i;
        this.dayOfMonth = i2;
        this.dayOfWeek = i3;
        this.millisInDay = i4;
        this.timeRuleType = i5;
        this.weekInMonth = 0;
    }

    public int getDateRuleType() {
        return this.dateRuleType;
    }

    public int getRuleMonth() {
        return this.month;
    }

    public int getRuleDayOfMonth() {
        return this.dayOfMonth;
    }

    public int getRuleDayOfWeek() {
        return this.dayOfWeek;
    }

    public int getRuleWeekInMonth() {
        return this.weekInMonth;
    }

    public int getTimeRuleType() {
        return this.timeRuleType;
    }

    public int getRuleMillisInDay() {
        return this.millisInDay;
    }

    public String toString() {
        String string;
        int i = this.dateRuleType;
        String str = null;
        if (i == 0) {
            string = Integer.toString(this.dayOfMonth);
        } else if (i == 1) {
            string = Integer.toString(this.weekInMonth) + DOWSTR[this.dayOfWeek];
        } else if (i == 2) {
            string = DOWSTR[this.dayOfWeek] + ">=" + Integer.toString(this.dayOfMonth);
        } else if (i != 3) {
            string = null;
        } else {
            string = DOWSTR[this.dayOfWeek] + "<=" + Integer.toString(this.dayOfMonth);
        }
        int i2 = this.timeRuleType;
        if (i2 == 0) {
            str = "WALL";
        } else if (i2 == 1) {
            str = "STD";
        } else if (i2 == 2) {
            str = "UTC";
        }
        int i3 = this.millisInDay;
        int i4 = i3 % 1000;
        int i5 = i3 / 1000;
        int i6 = i5 % 60;
        int i7 = i5 / 60;
        int i8 = i7 % 60;
        return "month=" + MONSTR[this.month] + ", date=" + string + ", time=" + (i7 / 60) + ":" + (i8 / 10) + (i8 % 10) + ":" + (i6 / 10) + (i6 % 10) + "." + (i4 / 100) + ((i4 / 10) % 10) + (i4 % 10) + "(" + str + ")";
    }
}
