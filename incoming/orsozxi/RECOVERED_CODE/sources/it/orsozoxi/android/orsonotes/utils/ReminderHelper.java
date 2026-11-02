package it.orsozoxi.android.orsonotes.utils;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.text.TextUtils;
import android.widget.Toast;
import it.orsozoxi.android.orsonotes.OrsoNotes;
import it.orsozoxi.android.orsonotes.R;
import it.orsozoxi.android.orsonotes.helpers.date.DateHelper;
import it.orsozoxi.android.orsonotes.models.Note;
import it.orsozoxi.android.orsonotes.receiver.AlarmReceiver;
import it.orsozoxi.android.orsonotes.utils.date.DateUtils;
import java.util.Calendar;
import net.sqlcipher.database.SQLiteDatabase;

/* loaded from: classes3.dex */
public class ReminderHelper {
    private ReminderHelper() {
    }

    public static void addReminder(Context context, Note note) {
        if (note.getAlarm() != null) {
            addReminder(context, note, Long.parseLong(note.getAlarm()));
        }
    }

    public static void addReminder(Context context, Note note, long j) {
        if (DateUtils.isFuture(Long.valueOf(j))) {
            Intent intent = new Intent(context, (Class<?>) AlarmReceiver.class);
            intent.putExtra(ConstantsBase.INTENT_NOTE, ParcelableUtil.marshall(note));
            ((AlarmManager) context.getSystemService("alarm")).setExact(0, j, PendingIntent.getBroadcast(context, getRequestCode(note), intent, SQLiteDatabase.CREATE_IF_NECESSARY));
        }
    }

    public static boolean checkReminder(Context context, Note note) {
        return PendingIntent.getBroadcast(context, getRequestCode(note), new Intent(context, (Class<?>) AlarmReceiver.class), 536870912) != null;
    }

    static int getRequestCode(Note note) {
        return (int) (note.getCreation() != null ? note.getCreation().longValue() : Calendar.getInstance().getTimeInMillis() / 1000);
    }

    public static void removeReminder(Context context, Note note) {
        if (TextUtils.isEmpty(note.getAlarm())) {
            return;
        }
        AlarmManager alarmManager = (AlarmManager) context.getSystemService("alarm");
        PendingIntent broadcast = PendingIntent.getBroadcast(context, getRequestCode(note), new Intent(context, (Class<?>) AlarmReceiver.class), 0);
        alarmManager.cancel(broadcast);
        broadcast.cancel();
    }

    public static void showReminderMessage(String str) throws NumberFormatException {
        if (str != null) {
            final long j = Long.parseLong(str);
            if (j > Calendar.getInstance().getTimeInMillis()) {
                new Handler(OrsoNotes.getAppContext().getMainLooper()).post(new Runnable() { // from class: it.orsozoxi.android.orsonotes.utils.-$$Lambda$ReminderHelper$EsFAjAoS5umparyf7EplMQRWogY
                    @Override // java.lang.Runnable
                    public final void run() {
                        Toast.makeText(OrsoNotes.getAppContext(), OrsoNotes.getAppContext().getString(R.string.alarm_set_on) + " " + DateHelper.getDateTimeShort(OrsoNotes.getAppContext(), Long.valueOf(j)), 1).show();
                    }
                });
            }
        }
    }
}
