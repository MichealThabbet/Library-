package com.app.piapostolos.Alarm.Services;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

/* loaded from: classes.dex */
public class AlarmServiceBroadcastReciever extends BroadcastReceiver {
    @Override // android.content.BroadcastReceiver
    public void onReceive(Context context, Intent intent) {
        if (Build.VERSION.SDK_INT >= 26) {
            AlarmJobService.enqueueWork(context, new Intent());
        } else {
            context.startService(new Intent(context, (Class<?>) AlarmService.class));
        }
    }
}
