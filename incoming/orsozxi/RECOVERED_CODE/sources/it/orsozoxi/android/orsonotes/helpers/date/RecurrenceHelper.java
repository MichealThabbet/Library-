package it.orsozoxi.android.orsonotes.helpers.date;

import android.content.Context;
import android.text.format.Time;
import com.appeaser.sublimepickerlibrary.recurrencepicker.EventRecurrence;
import com.appeaser.sublimepickerlibrary.recurrencepicker.EventRecurrenceFormatter;
import com.appeaser.sublimepickerlibrary.recurrencepicker.SublimeRecurrencePicker;
import it.orsozoxi.android.orsonotes.OrsoNotes;
import it.orsozoxi.android.orsonotes.R;
import it.orsozoxi.android.orsonotes.helpers.LogDelegate;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import net.fortuna.ical4j.model.DateTime;
import net.fortuna.ical4j.model.Recur;
import net.fortuna.ical4j.model.property.RRule;
import org.apache.commons.lang3.StringUtils;

/* loaded from: classes3.dex */
public class RecurrenceHelper {
    private RecurrenceHelper() {
    }

    public static String formatRecurrence(Context context, String str) {
        if (StringUtils.isEmpty(str)) {
            return "";
        }
        EventRecurrence eventRecurrence = new EventRecurrence();
        eventRecurrence.setStartDate(new Time("" + new Date().getTime()));
        eventRecurrence.parse(str);
        return EventRecurrenceFormatter.getRepeatString(context.getApplicationContext(), context.getResources(), eventRecurrence, true);
    }

    public static Long nextReminderFromRecurrenceRule(long j, String str) {
        return nextReminderFromRecurrenceRule(j, Calendar.getInstance().getTimeInMillis(), str);
    }

    public static Long nextReminderFromRecurrenceRule(long j, long j2, String str) {
        try {
            RRule rRule = new RRule();
            rRule.setValue(str);
            long j3 = 60000 + j;
            if (j3 >= j2) {
                j2 = j3;
            }
            net.fortuna.ical4j.model.Date nextDate = rRule.getRecur().getNextDate(new DateTime(j), new DateTime(j2));
            return Long.valueOf(nextDate == null ? 0L : nextDate.getTime());
        } catch (ParseException unused) {
            LogDelegate.e("Error parsing rrule");
            return 0L;
        }
    }

    public static String getNoteReminderText(long j) {
        return OrsoNotes.getAppContext().getString(R.string.alarm_set_on) + " " + DateHelper.getDateTimeShort(OrsoNotes.getAppContext(), Long.valueOf(j));
    }

    public static String getNoteRecurrentReminderText(long j, String str) {
        return formatRecurrence(OrsoNotes.getAppContext(), str) + " " + OrsoNotes.getAppContext().getString(R.string.starting_from) + " " + DateHelper.getDateTimeShort(OrsoNotes.getAppContext(), Long.valueOf(j));
    }

    public static String buildRecurrenceRuleByRecurrenceOptionAndRule(SublimeRecurrencePicker.RecurrenceOption recurrenceOption, String str) {
        return (str != null || recurrenceOption == SublimeRecurrencePicker.RecurrenceOption.DOES_NOT_REPEAT) ? str : new RRule(new Recur(Recur.Frequency.valueOf(recurrenceOption.toString()), new DateTime(32519731800000L))).toString().replace("RRULE:", "");
    }
}
