package com.app.orsozoxi.Alarm.Alert;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import com.app.orsozoxi.Alarm.Alarm;
import com.app.orsozoxi.Alarm.Services.AlarmServiceBroadcastReciever;
import com.app.orsozoxi.Alarm.Services.FlyBitch;
import com.app.orsozoxi.Alarm.Services.FlyBitchJobService;
import com.google.gson.Gson;

/* loaded from: classes.dex */
public class AlarmAlertBroadcastReciever extends BroadcastReceiver {
    @Override // android.content.BroadcastReceiver
    public void onReceive(Context context, Intent intent) {
        context.sendBroadcast(new Intent(context, (Class<?>) AlarmServiceBroadcastReciever.class), null);
        Bundle extras = intent.getExtras();
        if (extras == null || !extras.containsKey("alarm")) {
            return;
        }
        Alarm alarm = (Alarm) new Gson().fromJson(extras.getString("alarm"), Alarm.class);
        if (Build.VERSION.SDK_INT >= 26) {
            Intent intent2 = new Intent();
            intent2.putExtra("alarm", new Gson().toJson(alarm));
            FlyBitchJobService.enqueueWork(context, intent2);
        } else {
            Intent intent3 = new Intent(context, (Class<?>) FlyBitch.class);
            intent3.putExtra("alarm", alarm);
            context.startService(intent3);
        }
    }
}
