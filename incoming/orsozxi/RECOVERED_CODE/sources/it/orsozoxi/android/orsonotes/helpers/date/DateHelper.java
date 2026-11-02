package it.orsozoxi.android.orsonotes.helpers.date;

import android.content.Context;
import android.text.format.DateUtils;
import com.google.android.material.timepicker.TimeModel;
import it.orsozoxi.android.orsonotes.OrsoNotes;
import it.orsozoxi.android.orsonotes.utils.ConstantsBase;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/* loaded from: classes3.dex */
public class DateHelper {
    private DateHelper() {
    }

    public static String getSortableDate() {
        return new SimpleDateFormat(ConstantsBase.DATE_FORMAT_SORTABLE).format(Calendar.getInstance().getTime());
    }

    public static String onDateSet(int i, int i2, int i3, String str) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(str);
        Calendar calendar = Calendar.getInstance();
        calendar.set(1, i);
        calendar.set(2, i2);
        calendar.set(5, i3);
        return simpleDateFormat.format(calendar.getTime());
    }

    public static String onTimeSet(int i, int i2, String str) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(str);
        Calendar calendar = Calendar.getInstance();
        calendar.set(11, i);
        calendar.set(12, i2);
        return simpleDateFormat.format(calendar.getTime());
    }

    public static String getDateTimeShort(Context context, Long l) {
        if (l == null) {
            return "";
        }
        return DateUtils.formatDateTime(context, l.longValue(), 98322) + " " + DateUtils.formatDateTime(context, l.longValue(), 1);
    }

    public static String getTimeShort(Context context, Long l) {
        if (l == null) {
            return "";
        }
        Calendar.getInstance().setTimeInMillis(l.longValue());
        return DateUtils.formatDateTime(context, l.longValue(), 1);
    }

    public static String getTimeShort(Context context, int i, int i2) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(11, i);
        calendar.set(12, i2);
        return DateUtils.formatDateTime(context, calendar.getTimeInMillis(), 1);
    }

    public static String formatShortTime(Context context, long j) {
        long j2 = j / 1000;
        return String.valueOf(j2 / 60) + ":" + String.format(TimeModel.ZERO_LEADING_NUMBER_FORMAT, Long.valueOf(j2 % 60));
    }

    public static String getFormattedDate(Long l, boolean z) {
        if (z) {
            return it.orsozoxi.android.orsonotes.utils.date.DateUtils.prettyTime(l);
        }
        return getDateTimeShort(OrsoNotes.getAppContext(), l);
    }
}
