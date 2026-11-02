package com.app.orsozoxi.Others;

import android.app.Application;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.util.Log;
import android.widget.Button;
import android.widget.RemoteViews;
import androidx.core.app.NotificationCompat;
import com.app.orsozoxi.MainActivity_Bible;
import com.app.orsozoxi.R;
import com.huawei.hms.api.bean.HwAudioPlayItem;
import com.huawei.hms.api.config.NotificationConfig;
import com.huawei.hms.audiokit.player.manager.HwAudioManager;
import com.huawei.hms.audiokit.player.manager.INotificationFactory;

/* loaded from: classes.dex */
public class SubINotificationFactory implements INotificationFactory {
    private static final String TAG = "SubINotificationFactory";
    private Application application;
    private HwAudioManager hwAudioManager;
    private Button playbutton;

    public SubINotificationFactory(Application application, HwAudioManager hwAudioManager, Button button) {
        this.application = application;
        this.hwAudioManager = hwAudioManager;
        this.playbutton = button;
    }

    @Override // com.huawei.hms.audiokit.player.manager.INotificationFactory
    public Notification createNotification(NotificationConfig notificationConfig) {
        int i = Build.VERSION.SDK_INT;
        int i2 = R.drawable.btn_playback_pause_normal;
        boolean z = false;
        if (i > 23) {
            NotificationCompat.Builder builder = new NotificationCompat.Builder(this.application);
            RemoteViews remoteViews = new RemoteViews(this.application.getPackageName(), R.layout.notification_player);
            builder.setContent(remoteViews);
            builder.setSmallIcon(R.drawable.logo);
            builder.setVisibility(1);
            builder.setCustomBigContentView(remoteViews);
            builder.setAutoCancel(true);
            NotificationUtils.addChannel(this.application, NotificationUtils.NOTIFY_CHANNEL_ID_PLAY, builder);
            boolean zIsQueueEmpty = this.hwAudioManager.getQueueManager().isQueueEmpty();
            setBitmap(remoteViews, notificationConfig.getBitmap());
            if (this.hwAudioManager.getPlayerManager().isPlaying() && !zIsQueueEmpty) {
                z = true;
            }
            if (!z) {
                i2 = R.drawable.btn_playback_play_normal;
            }
            remoteViews.setImageViewResource(R.id.image_toggle, i2);
            HwAudioPlayItem currentPlayItem = this.hwAudioManager.getQueueManager().getCurrentPlayItem();
            remoteViews.setTextViewText(R.id.text_song, currentPlayItem.getAudioTitle());
            remoteViews.setTextViewText(R.id.text_artist, currentPlayItem.getSinger());
            remoteViews.setOnClickPendingIntent(R.id.image_last, notificationConfig.getPrePendingIntent());
            remoteViews.setOnClickPendingIntent(R.id.image_toggle, playPause(notificationConfig));
            remoteViews.setOnClickPendingIntent(R.id.image_next, notificationConfig.getNextPendingIntent());
            remoteViews.setOnClickPendingIntent(R.id.image_close, getCancelPendingIntent());
            remoteViews.setOnClickPendingIntent(R.id.layout_content, getMainIntent());
            return builder.build();
        }
        NotificationCompat.Builder builder2 = new NotificationCompat.Builder(this.application);
        RemoteViews remoteViews2 = new RemoteViews(this.application.getPackageName(), R.layout.statusbar);
        builder2.setContent(remoteViews2);
        builder2.setSmallIcon(R.drawable.logo);
        builder2.setVisibility(1);
        builder2.setCustomBigContentView(remoteViews2);
        builder2.setAutoCancel(true);
        NotificationUtils.addChannel(this.application, NotificationUtils.NOTIFY_CHANNEL_ID_PLAY, builder2);
        boolean zIsQueueEmpty2 = this.hwAudioManager.getQueueManager().isQueueEmpty();
        setBitmap(remoteViews2, notificationConfig.getBitmap());
        if (this.hwAudioManager.getPlayerManager().isPlaying() && !zIsQueueEmpty2) {
            z = true;
        }
        if (!z) {
            i2 = R.drawable.btn_playback_play_normal;
        }
        remoteViews2.setImageViewResource(R.id.widget_id_control_play, i2);
        HwAudioPlayItem currentPlayItem2 = this.hwAudioManager.getQueueManager().getCurrentPlayItem();
        remoteViews2.setTextViewText(R.id.trackname, currentPlayItem2.getAudioTitle());
        remoteViews2.setTextViewText(R.id.artistalbum, currentPlayItem2.getSinger());
        remoteViews2.setOnClickPendingIntent(R.id.widget_id_control_prev, getCancelPendingIntent());
        remoteViews2.setOnClickPendingIntent(R.id.widget_id_control_play, notificationConfig.getPlayPendingIntent());
        remoteViews2.setOnClickPendingIntent(R.id.widget_id_control_next, notificationConfig.getNextPendingIntent());
        remoteViews2.setOnClickPendingIntent(R.id.statusbar_layout, getMainIntent());
        return builder2.build();
    }

    private PendingIntent playPause(NotificationConfig notificationConfig) {
        HwAudioManager hwAudioManager = this.hwAudioManager;
        if (hwAudioManager != null && this.playbutton != null) {
            if (hwAudioManager.getPlayerManager().isPlaying()) {
                this.playbutton.setBackgroundResource(R.drawable.btn_pauseheader);
            } else {
                this.playbutton.setBackgroundResource(R.drawable.btn_playheader);
            }
        }
        return notificationConfig.getPlayPendingIntent();
    }

    private void setBitmap(RemoteViews remoteViews, Bitmap bitmap) {
        if (bitmap != null) {
            Log.i(TAG, "Notification bitmap not empty");
            remoteViews.setImageViewBitmap(R.id.image_cover, bitmap);
        } else {
            Log.w(TAG, "Notification bitmap is null");
            remoteViews.setImageViewResource(R.id.image_cover, R.drawable.logo);
        }
    }

    private PendingIntent getCancelPendingIntent() {
        Log.i(TAG, "getCancelPendingIntent");
        Intent intent = new Intent(PlayActions.CANCEL_NOTIFICATION);
        intent.setPackage(this.application.getPackageName());
        return PendingIntent.getBroadcast(this.application, 2, intent, 134217728);
    }

    private PendingIntent getMainIntent() {
        Intent intent = new Intent("android.intent.action.MAIN");
        intent.addCategory("android.intent.category.LAUNCHER");
        intent.setClass(this.application.getBaseContext(), MainActivity_Bible.class);
        intent.setFlags(270532608);
        return PendingIntent.getActivity(this.application, 0, intent, 0);
    }
}
