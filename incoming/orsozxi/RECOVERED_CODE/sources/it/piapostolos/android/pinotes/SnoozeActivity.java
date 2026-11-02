package it.piapostolos.android.pinotes;

import android.app.NotificationManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import androidx.appcompat.app.AppCompatActivity;
import com.huawei.hms.push.constant.RemoteMessageConst;
import it.piapostolos.android.pinotes.async.notes.SaveNoteTask;
import it.piapostolos.android.pinotes.helpers.date.RecurrenceHelper;
import it.piapostolos.android.pinotes.models.Note;
import it.piapostolos.android.pinotes.models.listeners.OnReminderPickedListener;
import it.piapostolos.android.pinotes.utils.Constants;
import it.piapostolos.android.pinotes.utils.ConstantsBase;
import it.piapostolos.android.pinotes.utils.ReminderHelper;
import it.piapostolos.android.pinotes.utils.date.DateUtils;
import it.piapostolos.android.pinotes.utils.date.ReminderPickers;
import java.util.Arrays;
import java.util.Calendar;

/* loaded from: classes3.dex */
public class SnoozeActivity extends AppCompatActivity implements OnReminderPickedListener {
    private Note note;
    private Note[] notes;

    public static void setNextRecurrentReminder(Note note) throws NumberFormatException {
        if (!TextUtils.isEmpty(note.getRecurrenceRule())) {
            long jLongValue = RecurrenceHelper.nextReminderFromRecurrenceRule(Long.parseLong(note.getAlarm()), note.getRecurrenceRule()).longValue();
            if (jLongValue > 0) {
                updateNoteReminder(jLongValue, note, true);
                return;
            }
            return;
        }
        new SaveNoteTask(false).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, note);
    }

    private static void updateNoteReminder(long j, Note note) throws NumberFormatException {
        updateNoteReminder(j, note, false);
    }

    private static void updateNoteReminder(long j, Note note, boolean z) throws NumberFormatException {
        if (z) {
            note.setAlarm(j);
            new SaveNoteTask(false).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, note);
        } else {
            ReminderHelper.addReminder(PiNotes.getAppContext(), note, j);
            ReminderHelper.showReminderMessage(note.getAlarm());
        }
    }

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    protected void onCreate(Bundle bundle) throws NumberFormatException {
        super.onCreate(bundle);
        if (getIntent().getParcelableExtra(ConstantsBase.INTENT_NOTE) != null) {
            this.note = (Note) getIntent().getParcelableExtra(ConstantsBase.INTENT_NOTE);
            manageNotification(getSharedPreferences(Constants.PREFS_NAME, 4));
        } else {
            Object[] objArr = (Object[]) getIntent().getExtras().get(ConstantsBase.INTENT_NOTE);
            this.notes = (Note[]) Arrays.copyOf(objArr, objArr.length, Note[].class);
            postpone(getSharedPreferences(Constants.PREFS_NAME, 4), Long.valueOf(DateUtils.getNextMinute()), null);
        }
    }

    private void manageNotification(SharedPreferences sharedPreferences) throws NumberFormatException {
        if (ConstantsBase.ACTION_DISMISS.equals(getIntent().getAction())) {
            setNextRecurrentReminder(this.note);
            finish();
        } else if (ConstantsBase.ACTION_SNOOZE.equals(getIntent().getAction())) {
            updateNoteReminder(Calendar.getInstance().getTimeInMillis() + (Integer.parseInt(sharedPreferences.getString("settings_notification_snooze_delay", ConstantsBase.PREF_SNOOZE_DEFAULT)) * 60 * 1000), this.note);
            finish();
        } else if (ConstantsBase.ACTION_POSTPONE.equals(getIntent().getAction())) {
            postpone(sharedPreferences, Long.valueOf(Long.parseLong(this.note.getAlarm())), this.note.getRecurrenceRule());
        } else {
            Intent intent = new Intent(this, (Class<?>) NotesMainActivity.class);
            intent.putExtra("note_id", this.note.get_id());
            intent.setAction(ConstantsBase.ACTION_NOTIFICATION_CLICK);
            startActivity(intent);
            finish();
        }
        removeNotification(this.note);
    }

    private void postpone(SharedPreferences sharedPreferences, Long l, String str) {
        new ReminderPickers(this, this).pick(l, str);
    }

    private void removeNotification(Note note) {
        ((NotificationManager) getSystemService(RemoteMessageConst.NOTIFICATION)).cancel(String.valueOf(note.get_id()), 0);
    }

    @Override // it.piapostolos.android.pinotes.models.listeners.OnReminderPickedListener
    public void onReminderPicked(long j) {
        Note note = this.note;
        if (note != null) {
            note.setAlarm(j);
            return;
        }
        for (Note note2 : this.notes) {
            note2.setAlarm(j);
        }
    }

    @Override // it.piapostolos.android.pinotes.models.listeners.OnReminderPickedListener
    public void onRecurrenceReminderPicked(String str) throws NumberFormatException {
        Note note = this.note;
        if (note != null) {
            note.setRecurrenceRule(str);
            setNextRecurrentReminder(this.note);
        } else {
            for (Note note2 : this.notes) {
                note2.setRecurrenceRule(str);
                setNextRecurrentReminder(note2);
            }
            setResult(-1, getIntent());
        }
        finish();
    }
}
