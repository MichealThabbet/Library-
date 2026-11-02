package com.app.orsozoxi.Beans;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import com.app.orsozoxi.R;
import com.huawei.hms.push.constant.RemoteMessageConst;
import it.orsozoxi.android.orsonotes.utils.ConstantsBase;
import java.util.Calendar;

/* loaded from: classes.dex */
public class AlarmReceiver_afr extends BroadcastReceiver {
    private static int NOTIFICATION_ID = 1;

    @Override // android.content.BroadcastReceiver
    public void onReceive(Context context, Intent intent) {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(RemoteMessageConst.NOTIFICATION);
        Notification notification = new Notification(R.drawable.logo, "الأعتراف", System.currentTimeMillis());
        PendingIntent.getActivity(context, NOTIFICATION_ID, new Intent(context, (Class<?>) AlarmReceiver_afr.class), 0);
        Calendar.getInstance().get(11);
        Bundle extras = intent.getExtras();
        extras.getString("title");
        extras.getString(ConstantsBase.INTENT_NOTE);
        notification.flags = 4;
        notification.flags |= 16;
        notification.defaults = 6;
        notification.sound = Uri.parse("android.resource://" + context.getPackageName() + "/" + R.raw.garaselkinisa);
        int i = NOTIFICATION_ID;
        NOTIFICATION_ID = i + 1;
        notificationManager.notify(i, notification);
    }
}
