package com.app.piapostolos.Others;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;
import androidx.core.app.NotificationCompat;
import com.huawei.hms.push.constant.RemoteMessageConst;

/* compiled from: SubINotificationFactory.java */
/* loaded from: classes.dex */
class NotificationUtils {
    public static final String NOTIFY_CHANNEL_ID_PLAY = "music_notify_channel_id_play";

    NotificationUtils() {
    }

    public static void addChannel(Application application, String str, NotificationCompat.Builder builder) {
        if (Build.VERSION.SDK_INT < 26) {
            return;
        }
        createNotificationChannel(application, str, builder);
    }

    private static void createNotificationChannel(Application application, String str, NotificationCompat.Builder builder) {
        NotificationChannel notificationChannel = new NotificationChannel(str, "Play", 3);
        notificationChannel.enableVibration(false);
        notificationChannel.setSound(null, null);
        NotificationManager notificationManager = (NotificationManager) application.getSystemService(RemoteMessageConst.NOTIFICATION);
        if (notificationManager != null) {
            notificationManager.createNotificationChannel(notificationChannel);
        }
        builder.setGroup(str);
        builder.setChannelId(str);
    }
}
