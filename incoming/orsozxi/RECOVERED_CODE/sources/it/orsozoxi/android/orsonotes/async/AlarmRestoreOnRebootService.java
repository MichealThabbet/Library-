package it.orsozoxi.android.orsonotes.async;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import androidx.core.app.JobIntentService;
import it.orsozoxi.android.orsonotes.BaseActivity;
import it.orsozoxi.android.orsonotes.OrsoNotes;
import it.orsozoxi.android.orsonotes.db.DbHelper;
import it.orsozoxi.android.orsonotes.helpers.LogDelegate;
import it.orsozoxi.android.orsonotes.models.Note;
import it.orsozoxi.android.orsonotes.utils.ReminderHelper;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes3.dex */
public class AlarmRestoreOnRebootService extends JobIntentService {
    public static final int JOB_ID = 1;

    public static void enqueueWork(Context context, Intent intent) {
        if (Build.VERSION.SDK_INT >= 26) {
            enqueueWork(context, (Class<?>) AlarmRestoreOnRebootService.class, 1, intent);
        } else {
            context.startService(new Intent(context, (Class<?>) AlarmRestoreOnRebootService.class));
        }
    }

    @Override // androidx.core.app.JobIntentService
    protected void onHandleWork(Intent intent) {
        LogDelegate.i("System rebooted: service refreshing reminders");
        BaseActivity.notifyAppWidgets(getApplicationContext());
        List<Note> notesWithReminderNotFired = DbHelper.getInstance().getNotesWithReminderNotFired();
        LogDelegate.d("Found " + notesWithReminderNotFired.size() + " reminders");
        Iterator<Note> it2 = notesWithReminderNotFired.iterator();
        while (it2.hasNext()) {
            ReminderHelper.addReminder(OrsoNotes.getAppContext(), it2.next());
        }
    }
}
