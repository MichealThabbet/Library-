package com.app.piapostolos.Alarm.Services;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;
import androidx.core.app.JobIntentService;
import com.app.piapostolos.Alarm.Alarm;
import com.app.piapostolos.Alarm.Alert.AlarmAlertBroadcastReciever;
import com.app.piapostolos.Alarm.Database.Database;
import java.util.Comparator;
import java.util.TreeSet;
import net.sqlcipher.database.SQLiteDatabase;

/* loaded from: classes.dex */
public class AlarmJobService extends JobIntentService {
    static final int JOB_ID = 1004;

    private Alarm getNext() {
        TreeSet treeSet = new TreeSet(new Comparator<Alarm>() { // from class: com.app.piapostolos.Alarm.Services.AlarmJobService.1
            @Override // java.util.Comparator
            public int compare(Alarm alarm, Alarm alarm2) {
                long timeInMillis = alarm.getAlarmTime().getTimeInMillis() - alarm2.getAlarmTime().getTimeInMillis();
                if (timeInMillis > 0) {
                    return 1;
                }
                return timeInMillis < 0 ? -1 : 0;
            }
        });
        Database.init(getApplicationContext());
        for (Alarm alarm : Database.getAll()) {
            if (alarm.getAlarmActive().booleanValue()) {
                treeSet.add(alarm);
            }
        }
        if (treeSet.iterator().hasNext()) {
            return (Alarm) treeSet.iterator().next();
        }
        return null;
    }

    @Override // androidx.core.app.JobIntentService, android.app.Service
    public void onDestroy() {
        Database.deactivate();
        super.onDestroy();
    }

    static void enqueueWork(Context context, Intent intent) {
        enqueueWork(context, (Class<?>) AlarmJobService.class, 1004, intent);
    }

    @Override // androidx.core.app.JobIntentService, android.app.Service
    public int onStartCommand(Intent intent, int i, int i2) {
        return super.onStartCommand(intent, i, i2);
    }

    @Override // androidx.core.app.JobIntentService, android.app.Service
    public void onCreate() {
        super.onCreate();
    }

    @Override // androidx.core.app.JobIntentService
    protected void onHandleWork(Intent intent) {
        PendingIntent broadcast;
        Alarm next = getNext();
        if (next != null) {
            next.schedule(getApplicationContext());
            Log.d(getClass().getSimpleName(), next.getTimeUntilNextAlarmMessage(getApplicationContext()));
            return;
        }
        Intent intent2 = new Intent(getApplicationContext(), (Class<?>) AlarmAlertBroadcastReciever.class);
        intent2.putExtra("alarm", new Alarm());
        if (Build.VERSION.SDK_INT >= 23) {
            broadcast = PendingIntent.getBroadcast(getApplicationContext(), 0, intent2, 335544320);
        } else {
            broadcast = PendingIntent.getBroadcast(getApplicationContext(), 0, intent2, SQLiteDatabase.CREATE_IF_NECESSARY);
        }
        ((AlarmManager) getApplicationContext().getSystemService("alarm")).cancel(broadcast);
    }
}
