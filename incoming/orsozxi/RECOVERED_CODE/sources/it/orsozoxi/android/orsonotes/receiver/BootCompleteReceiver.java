package it.orsozoxi.android.orsonotes.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import it.orsozoxi.android.orsonotes.async.AlarmRestoreOnRebootService;
import it.orsozoxi.android.orsonotes.helpers.LogDelegate;

/* loaded from: classes3.dex */
public class BootCompleteReceiver extends BroadcastReceiver {
    @Override // android.content.BroadcastReceiver
    public void onReceive(Context context, Intent intent) {
        LogDelegate.i("System rebooted: refreshing reminders");
        if ("android.intent.action.BOOT_COMPLETED".equals(intent.getAction())) {
            AlarmRestoreOnRebootService.enqueueWork(context, intent);
        }
    }
}
