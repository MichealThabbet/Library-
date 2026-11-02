package com.app.orsozoxi.Alarm.Services;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import com.app.orsozoxi.Alarm.Alarm;
import com.app.orsozoxi.Alarm.Alert.AlarmAlertBroadcastReciever;
import com.app.orsozoxi.Alarm.Database.Database;
import java.util.Comparator;
import java.util.TreeSet;
import net.sqlcipher.database.SQLiteDatabase;

/* loaded from: classes.dex */
public class AlarmService extends Service {
    @Override // android.app.Service
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override // android.app.Service
    public void onCreate() {
        Log.d(getClass().getSimpleName(), "onCreate()");
        super.onCreate();
    }

    private Alarm getNext() {
        TreeSet treeSet = new TreeSet(new Comparator<Alarm>() { // from class: com.app.orsozoxi.Alarm.Services.AlarmService.1
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

    @Override // android.app.Service
    public void onDestroy() {
        Database.deactivate();
        super.onDestroy();
    }

    @Override // android.app.Service
    public int onStartCommand(Intent intent, int i, int i2) {
        Log.d(getClass().getSimpleName(), "onStartCommand()");
        Alarm next = getNext();
        if (next != null) {
            next.schedule(getApplicationContext());
            Log.d(getClass().getSimpleName(), next.getTimeUntilNextAlarmMessage(getApplicationContext()));
            return 2;
        }
        Intent intent2 = new Intent(getApplicationContext(), (Class<?>) AlarmAlertBroadcastReciever.class);
        intent2.putExtra("alarm", new Alarm());
        ((AlarmManager) getApplicationContext().getSystemService("alarm")).cancel(PendingIntent.getBroadcast(getApplicationContext(), 0, intent2, SQLiteDatabase.CREATE_IF_NECESSARY));
        return 2;
    }
}
