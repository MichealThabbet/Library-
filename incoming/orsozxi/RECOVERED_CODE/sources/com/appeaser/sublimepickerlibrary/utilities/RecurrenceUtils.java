package com.appeaser.sublimepickerlibrary.utilities;

import java.util.Calendar;

/* loaded from: classes.dex */
public class RecurrenceUtils {
    public static int getFirstDayOfWeek() {
        int firstDayOfWeek = Calendar.getInstance().getFirstDayOfWeek();
        if (firstDayOfWeek == 7) {
            return 6;
        }
        return firstDayOfWeek == 2 ? 1 : 0;
    }

    public static int getFirstDayOfWeekAsCalendar() {
        return convertDayOfWeekFromTimeToCalendar(getFirstDayOfWeek());
    }

    public static int convertDayOfWeekFromTimeToCalendar(int i) {
        switch (i) {
            case 0:
                return 1;
            case 1:
                return 2;
            case 2:
                return 3;
            case 3:
                return 4;
            case 4:
                return 5;
            case 5:
                return 6;
            case 6:
                return 7;
            default:
                throw new IllegalArgumentException("Argument must be between Time.SUNDAY and Time.SATURDAY");
        }
    }
}
