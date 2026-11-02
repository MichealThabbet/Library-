package it.orsozoxi.android.orsonotes.utils.notifications;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import androidx.core.app.NotificationCompat;
import androidx.core.internal.view.SupportMenu;
import com.huawei.hms.push.constant.RemoteMessageConst;
import it.orsozoxi.android.orsonotes.R;
import it.orsozoxi.android.orsonotes.utils.notifications.NotificationChannels;

/* loaded from: classes3.dex */
public class NotificationsHelper {
    private NotificationCompat.Builder mBuilder;
    private Context mContext;
    private NotificationManager mNotificationManager;

    public NotificationsHelper(Context context) {
        this.mContext = context.getApplicationContext();
        if (this.mNotificationManager == null) {
            this.mNotificationManager = (NotificationManager) context.getSystemService(RemoteMessageConst.NOTIFICATION);
        }
    }

    public void initNotificationChannels() {
        if (Build.VERSION.SDK_INT > 26) {
            for (NotificationChannel notificationChannel : NotificationChannels.getChannels()) {
                android.app.NotificationChannel notificationChannel2 = new android.app.NotificationChannel(notificationChannel.id, notificationChannel.name, notificationChannel.importance);
                notificationChannel2.setDescription(notificationChannel.description);
                this.mNotificationManager.createNotificationChannel(notificationChannel2);
            }
        }
    }

    public NotificationsHelper createNotification(NotificationChannels.NotificationChannelNames notificationChannelNames, int i, String str, PendingIntent pendingIntent) {
        if (Build.VERSION.SDK_INT >= 26) {
            android.app.NotificationChannel notificationChannel = new android.app.NotificationChannel(notificationChannelNames.name(), "My Notifications", 3);
            notificationChannel.setDescription("Channel");
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(SupportMenu.CATEGORY_MASK);
            notificationChannel.setVibrationPattern(new long[]{0, 1000, 500, 1000});
            notificationChannel.enableVibration(true);
            this.mNotificationManager.createNotificationChannel(notificationChannel);
        }
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this.mContext, notificationChannelNames.name());
        this.mBuilder = builder;
        builder.setCategory(NotificationCompat.CATEGORY_PROMO).setContentTitle(str).setSmallIcon(i).setAutoCancel(true).setColor(this.mContext.getResources().getColor(R.color.colorAccent)).setVisibility(1).setContentIntent(pendingIntent).setPriority(1).setDefaults(-1).build();
        if (Build.VERSION.SDK_INT >= 21) {
            setLargeIcon(R.drawable.logo_notification_lollipop);
        } else {
            setLargeIcon(R.mipmap.ic_launcher);
        }
        return this;
    }

    public NotificationCompat.Builder getBuilder() {
        return this.mBuilder;
    }

    public NotificationsHelper setLargeIcon(Bitmap bitmap) {
        this.mBuilder.setLargeIcon(bitmap);
        return this;
    }

    public NotificationsHelper setLargeIcon(int i) {
        return setLargeIcon(BitmapFactory.decodeResource(this.mContext.getResources(), i));
    }

    public NotificationsHelper setRingtone(String str) {
        if (str != null) {
            this.mBuilder.setSound(Uri.parse(str));
        }
        return this;
    }

    public NotificationsHelper setVibration() {
        return setVibration(null);
    }

    public NotificationsHelper setVibration(long[] jArr) {
        if (jArr == null || jArr.length == 0) {
            jArr = new long[]{500, 500};
        }
        this.mBuilder.setVibrate(jArr);
        return this;
    }

    public NotificationsHelper setLedActive() {
        this.mBuilder.setLights(-16776961, 1000, 1000);
        return this;
    }

    public NotificationsHelper setIcon(int i) {
        this.mBuilder.setSmallIcon(i);
        return this;
    }

    public NotificationsHelper setMessage(String str) {
        this.mBuilder.setContentText(str);
        return this;
    }

    public NotificationsHelper setIndeterminate() {
        this.mBuilder.setProgress(0, 0, true);
        return this;
    }

    public NotificationsHelper setOngoing() {
        this.mBuilder.setOngoing(true);
        return this;
    }

    public NotificationsHelper show() {
        show(0L);
        return this;
    }

    public NotificationsHelper show(long j) {
        Notification notificationBuild = this.mBuilder.build();
        if (notificationBuild.contentIntent == null) {
            this.mBuilder.setContentIntent(PendingIntent.getActivity(this.mContext, 0, new Intent(), 134217728));
        }
        this.mNotificationManager.notify(String.valueOf(j), 0, notificationBuild);
        return this;
    }

    public NotificationsHelper start(NotificationChannels.NotificationChannelNames notificationChannelNames, int i, String str) {
        createNotification(notificationChannelNames, i, str, null).setIndeterminate().setOngoing();
        this.mNotificationManager.notify(0, this.mBuilder.setOnlyAlertOnce(true).build());
        return this;
    }

    public void updateMessage(String str) {
        updateMessage(0, str);
    }

    public void updateMessage(int i, String str) {
        this.mNotificationManager.notify(i, this.mBuilder.setContentText(str).build());
    }

    public void finish(Intent intent, String str) {
        finish(0, intent, str);
    }

    public void finish(int i, Intent intent, String str) {
        this.mBuilder.setContentTitle(str).setProgress(0, 0, false).setOngoing(false);
        this.mNotificationManager.notify(i, this.mBuilder.build());
    }

    public void cancel() {
        this.mNotificationManager.cancel(0);
    }

    public void cancel(int i) {
        this.mNotificationManager.cancel(i);
    }
}
