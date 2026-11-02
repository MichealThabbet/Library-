package com.appeaser.sublimepickerlibrary.datepicker;

import java.text.DateFormat;
import java.util.Calendar;

/* loaded from: classes.dex */
public class SelectedDate {
    private Calendar mFirstDate;
    private Calendar mSecondDate;

    public enum Type {
        SINGLE,
        RANGE
    }

    public SelectedDate(Calendar calendar, Calendar calendar2) {
        this.mFirstDate = calendar;
        this.mSecondDate = calendar2;
    }

    public SelectedDate(Calendar calendar) {
        this.mSecondDate = calendar;
        this.mFirstDate = calendar;
    }

    public SelectedDate(SelectedDate selectedDate) {
        this.mFirstDate = Calendar.getInstance();
        this.mSecondDate = Calendar.getInstance();
        if (selectedDate != null) {
            this.mFirstDate.setTimeInMillis(selectedDate.getStartDate().getTimeInMillis());
            this.mSecondDate.setTimeInMillis(selectedDate.getEndDate().getTimeInMillis());
        }
    }

    public Calendar getFirstDate() {
        return this.mFirstDate;
    }

    public void setFirstDate(Calendar calendar) {
        this.mFirstDate = calendar;
    }

    public Calendar getSecondDate() {
        return this.mSecondDate;
    }

    public void setSecondDate(Calendar calendar) {
        this.mSecondDate = calendar;
    }

    public void setDate(Calendar calendar) {
        this.mFirstDate = calendar;
        this.mSecondDate = calendar;
    }

    public Calendar getStartDate() {
        return compareDates(this.mFirstDate, this.mSecondDate) == -1 ? this.mFirstDate : this.mSecondDate;
    }

    public Calendar getEndDate() {
        return compareDates(this.mFirstDate, this.mSecondDate) == 1 ? this.mFirstDate : this.mSecondDate;
    }

    public Type getType() {
        return compareDates(this.mFirstDate, this.mSecondDate) == 0 ? Type.SINGLE : Type.RANGE;
    }

    public static int compareDates(Calendar calendar, Calendar calendar2) {
        int i = calendar.get(1);
        int i2 = calendar2.get(1);
        int i3 = calendar.get(2);
        int i4 = calendar2.get(2);
        int i5 = calendar.get(5);
        int i6 = calendar2.get(5);
        if (i < i2) {
            return -1;
        }
        if (i > i2) {
            return 1;
        }
        if (i3 < i4) {
            return -1;
        }
        if (i3 > i4) {
            return 1;
        }
        if (i5 < i6) {
            return -1;
        }
        return i5 > i6 ? 1 : 0;
    }

    public void setTimeInMillis(long j) {
        this.mFirstDate.setTimeInMillis(j);
        this.mSecondDate.setTimeInMillis(j);
    }

    public void set(int i, int i2) {
        this.mFirstDate.set(i, i2);
        this.mSecondDate.set(i, i2);
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        if (this.mFirstDate != null) {
            sb.append(DateFormat.getDateInstance().format(this.mFirstDate.getTime()));
            sb.append("\n");
        }
        if (this.mSecondDate != null) {
            sb.append(DateFormat.getDateInstance().format(this.mSecondDate.getTime()));
        }
        return sb.toString();
    }
}
