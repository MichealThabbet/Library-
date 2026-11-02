package com.appeaser.sublimepickerlibrary.recurrencepicker;

import android.content.Context;
import android.content.res.Resources;
import android.text.format.DateUtils;
import android.text.format.Time;
import android.util.TimeFormatException;
import com.appeaser.sublimepickerlibrary.R;

/* loaded from: classes.dex */
public class EventRecurrenceFormatter {
    private static int[] mMonthRepeatByDayOfWeekIds;
    private static String[][] mMonthRepeatByDayOfWeekStrs;

    public static String getRepeatString(Context context, Resources resources, EventRecurrence eventRecurrence, boolean z) throws Resources.NotFoundException {
        String string;
        String strDayToString;
        String quantityString;
        String quantityString2;
        if (z) {
            StringBuilder sb = new StringBuilder();
            if (eventRecurrence.until != null) {
                try {
                    Time time = new Time();
                    time.parse(eventRecurrence.until);
                    sb.append(resources.getString(R.string.endByDate, DateUtils.formatDateTime(context, time.toMillis(false), 131072)));
                } catch (TimeFormatException unused) {
                }
            }
            if (eventRecurrence.count > 0) {
                sb.append(resources.getQuantityString(R.plurals.endByCount, eventRecurrence.count, Integer.valueOf(eventRecurrence.count)));
            }
            string = sb.toString();
        } else {
            string = "";
        }
        int i = eventRecurrence.interval <= 1 ? 1 : eventRecurrence.interval;
        int i2 = eventRecurrence.freq;
        if (i2 == 4) {
            return resources.getQuantityString(R.plurals.daily, i, Integer.valueOf(i)) + string;
        }
        if (i2 == 5) {
            if (eventRecurrence.repeatsOnEveryWeekDay()) {
                return resources.getString(R.string.every_weekday) + string;
            }
            int i3 = eventRecurrence.bydayCount == 1 ? 10 : 20;
            StringBuilder sb2 = new StringBuilder();
            if (eventRecurrence.bydayCount > 0) {
                int i4 = eventRecurrence.bydayCount - 1;
                for (int i5 = 0; i5 < i4; i5++) {
                    sb2.append(dayToString(eventRecurrence.byday[i5], i3));
                    sb2.append(", ");
                }
                sb2.append(dayToString(eventRecurrence.byday[i4], i3));
                strDayToString = sb2.toString();
            } else {
                if (eventRecurrence.startDate == null) {
                    return null;
                }
                strDayToString = dayToString(EventRecurrence.timeDay2Day(eventRecurrence.startDate.weekDay), 10);
            }
            return resources.getQuantityString(R.plurals.weekly, i, Integer.valueOf(i), strDayToString) + string;
        }
        if (i2 != 6) {
            if (i2 != 7) {
                return null;
            }
            if (i == 1) {
                quantityString2 = resources.getString(R.string.yearly_plain);
            } else {
                quantityString2 = resources.getQuantityString(R.plurals.recurrence_interval_yearly, i, Integer.valueOf(i));
            }
            return quantityString2 + string;
        }
        if (i == 1) {
            quantityString = resources.getString(R.string.monthly);
        } else {
            quantityString = resources.getQuantityString(R.plurals.recurrence_interval_monthly, i, Integer.valueOf(i));
        }
        if (eventRecurrence.bydayCount == 1) {
            int i6 = eventRecurrence.startDate.weekDay;
            cacheMonthRepeatStrings(resources, i6);
            return quantityString + " (" + mMonthRepeatByDayOfWeekStrs[i6][(eventRecurrence.startDate.monthDay - 1) / 7] + ")" + string;
        }
        return quantityString + string;
    }

    private static void cacheMonthRepeatStrings(Resources resources, int i) {
        if (mMonthRepeatByDayOfWeekIds == null) {
            int[] iArr = new int[7];
            mMonthRepeatByDayOfWeekIds = iArr;
            iArr[0] = R.array.repeat_by_nth_sun;
            mMonthRepeatByDayOfWeekIds[1] = R.array.repeat_by_nth_mon;
            mMonthRepeatByDayOfWeekIds[2] = R.array.repeat_by_nth_tues;
            mMonthRepeatByDayOfWeekIds[3] = R.array.repeat_by_nth_wed;
            mMonthRepeatByDayOfWeekIds[4] = R.array.repeat_by_nth_thurs;
            mMonthRepeatByDayOfWeekIds[5] = R.array.repeat_by_nth_fri;
            mMonthRepeatByDayOfWeekIds[6] = R.array.repeat_by_nth_sat;
        }
        if (mMonthRepeatByDayOfWeekStrs == null) {
            mMonthRepeatByDayOfWeekStrs = new String[7][];
        }
        String[][] strArr = mMonthRepeatByDayOfWeekStrs;
        if (strArr[i] == null) {
            strArr[i] = resources.getStringArray(mMonthRepeatByDayOfWeekIds[i]);
        }
    }

    private static String dayToString(int i, int i2) {
        return DateUtils.getDayOfWeekString(dayToUtilDay(i), i2);
    }

    private static int dayToUtilDay(int i) {
        if (i == 65536) {
            return 1;
        }
        if (i == 131072) {
            return 2;
        }
        if (i == 262144) {
            return 3;
        }
        if (i == 524288) {
            return 4;
        }
        if (i == 1048576) {
            return 5;
        }
        if (i == 2097152) {
            return 6;
        }
        if (i == 4194304) {
            return 7;
        }
        throw new IllegalArgumentException("bad day argument: " + i);
    }
}
