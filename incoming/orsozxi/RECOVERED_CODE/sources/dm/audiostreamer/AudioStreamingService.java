package dm.audiostreamer;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.PendingIntent;
import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.AudioManager;
import android.media.RemoteControlClient;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.RemoteViews;
import androidx.core.app.NotificationCompat;
import com.facebook.places.model.PlaceFields;
import com.huawei.hms.push.constant.RemoteMessageConst;
import com.nostra13.universalimageloader.core.ImageLoader;
import dm.audiostreamer.NotificationManager;
import java.util.Objects;

/* loaded from: classes2.dex */
public class AudioStreamingService extends Service implements NotificationManager.NotificationCenterDelegate {
    public static final String ACTION_CMD = "dm.audiostreaming.ACTION_CMD";
    public static final String CMD_NAME = "CMD_NAME";
    public static final String CMD_PAUSE = "CMD_PAUSE";
    public static final String CMD_STOP_CASTING = "CMD_STOP_CASTING";
    public static final String EXTRA_CONNECTED_CAST = "dm.audiostreaming.CAST_NAME";
    public static final String NOTIFY_CLOSE = "dm.audiostreamer.close";
    public static final String NOTIFY_NEXT = "dm.audiostreamer.next";
    public static final String NOTIFY_PAUSE = "dm.audiostreamer.pause";
    public static final String NOTIFY_PLAY = "dm.audiostreamer.play";
    public static final String NOTIFY_PREVIOUS = "dm.audiostreamer.previous";
    private static final int STOP_DELAY = 30000;
    private static final String TAG = Logger.makeLogTag(AudioStreamingService.class);
    private static boolean supportBigNotifications;
    private static boolean supportLockScreenControls;
    private AudioManager audioManager;
    private AudioStreamingManager audioStreamingManager;
    public PendingIntent pendingIntent;
    private PhoneStateListener phoneStateListener;
    private RemoteControlClient remoteControlClient;

    @Override // dm.audiostreamer.NotificationManager.NotificationCenterDelegate
    public void newSongLoaded(Object... objArr) {
    }

    @Override // android.app.Service
    public IBinder onBind(Intent intent) {
        return null;
    }

    static {
        supportBigNotifications = Build.VERSION.SDK_INT >= 16;
        supportLockScreenControls = Build.VERSION.SDK_INT >= 14;
    }

    @Override // android.app.Service
    public void onCreate() {
        this.audioStreamingManager = AudioStreamingManager.getInstance(this);
        this.audioManager = (AudioManager) getSystemService("audio");
        NotificationManager.getInstance().addObserver(this, NotificationManager.audioProgressDidChanged);
        NotificationManager.getInstance().addObserver(this, NotificationManager.setAnyPendingIntent);
        NotificationManager.getInstance().addObserver(this, NotificationManager.audioPlayStateChanged);
        try {
            this.phoneStateListener = new PhoneStateListener() { // from class: dm.audiostreamer.AudioStreamingService.1
                @Override // android.telephony.PhoneStateListener
                public void onCallStateChanged(int i, String str) {
                    if (i == 1 && AudioStreamingService.this.audioStreamingManager.isPlaying()) {
                        AudioStreamingService.this.audioStreamingManager.handlePauseRequest();
                    }
                    super.onCallStateChanged(i, str);
                }
            };
            TelephonyManager telephonyManager = (TelephonyManager) getSystemService(PlaceFields.PHONE);
            if (telephonyManager != null) {
                telephonyManager.listen(this.phoneStateListener, 32);
            }
        } catch (Exception e) {
            Log.e("tmessages", e.toString());
        }
        super.onCreate();
    }

    @Override // android.app.Service
    public int onStartCommand(Intent intent, int i, int i2) {
        MediaMetaData currentAudio;
        try {
            currentAudio = AudioStreamingManager.getInstance(this).getCurrentAudio();
        } catch (Exception unused) {
        }
        if (currentAudio == null) {
            new Handler(getMainLooper()).post(new Runnable() { // from class: dm.audiostreamer.AudioStreamingService.2
                @Override // java.lang.Runnable
                public void run() {
                    AudioStreamingService.this.stopSelf();
                }
            });
            return 1;
        }
        if (supportLockScreenControls) {
            ComponentName componentName = new ComponentName(getApplicationContext(), AudioStreamingReceiver.class.getName());
            try {
                if (this.remoteControlClient == null) {
                    this.audioManager.registerMediaButtonEventReceiver(componentName);
                    Intent intent2 = new Intent("android.intent.action.MEDIA_BUTTON");
                    intent2.setComponent(componentName);
                    RemoteControlClient remoteControlClient = new RemoteControlClient(PendingIntent.getBroadcast(this, 0, intent2, 0));
                    this.remoteControlClient = remoteControlClient;
                    this.audioManager.registerRemoteControlClient(remoteControlClient);
                }
                this.remoteControlClient.setTransportControlFlags(189);
            } catch (Exception e) {
                Log.e("tmessages", e.toString());
            }
        }
        createNotification(currentAudio);
        if (intent == null) {
            return 2;
        }
        String action = intent.getAction();
        String stringExtra = intent.getStringExtra(CMD_NAME);
        if (!ACTION_CMD.equals(action)) {
            return 2;
        }
        if (CMD_PAUSE.equals(stringExtra)) {
            this.audioStreamingManager.handlePauseRequest();
            return 2;
        }
        CMD_STOP_CASTING.equals(stringExtra);
        return 2;
    }

    private void createNotification(MediaMetaData mediaMetaData) throws IllegalArgumentException {
        Notification notificationBuild;
        try {
            String notificationChannelId = Build.VERSION.SDK_INT >= 26 ? getNotificationChannelId() : "";
            String mediaTitle = mediaMetaData.getMediaTitle();
            String mediaArtist = mediaMetaData.getMediaArtist();
            mediaMetaData.getMediaAlbum();
            MediaMetaData currentAudio = AudioStreamingManager.getInstance(this).getCurrentAudio();
            RemoteViews remoteViews = new RemoteViews(getApplicationContext().getPackageName(), R.layout.player_small_notification);
            Bitmap bitmapLoadImageSync = null;
            RemoteViews remoteViews2 = supportBigNotifications ? new RemoteViews(getApplicationContext().getPackageName(), R.layout.player_big_notification) : null;
            if (this.pendingIntent != null) {
                notificationBuild = new NotificationCompat.Builder(getApplicationContext(), notificationChannelId).setSmallIcon(R.drawable.player).setContentIntent(this.pendingIntent).setContentTitle(mediaTitle).build();
            } else {
                notificationBuild = new NotificationCompat.Builder(getApplicationContext(), notificationChannelId).setSmallIcon(R.drawable.player).setContentTitle(mediaTitle).build();
            }
            notificationBuild.contentView = remoteViews;
            if (supportBigNotifications) {
                notificationBuild.bigContentView = remoteViews2;
            }
            setListeners(remoteViews);
            if (supportBigNotifications) {
                setListeners(remoteViews2);
            }
            try {
                bitmapLoadImageSync = ImageLoader.getInstance().loadImageSync(currentAudio.getMediaArt());
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (bitmapLoadImageSync != null) {
                notificationBuild.contentView.setImageViewBitmap(R.id.player_album_art, bitmapLoadImageSync);
                if (supportBigNotifications) {
                    notificationBuild.bigContentView.setImageViewBitmap(R.id.player_album_art, bitmapLoadImageSync);
                }
            } else {
                notificationBuild.contentView.setImageViewResource(R.id.player_album_art, R.drawable.bg_default_album_art);
                if (supportBigNotifications) {
                    notificationBuild.bigContentView.setImageViewResource(R.id.player_album_art, R.drawable.bg_default_album_art);
                }
            }
            notificationBuild.contentView.setViewVisibility(R.id.player_progress_bar, 8);
            notificationBuild.contentView.setViewVisibility(R.id.player_next, 0);
            notificationBuild.contentView.setViewVisibility(R.id.player_previous, 0);
            if (supportBigNotifications) {
                notificationBuild.bigContentView.setViewVisibility(R.id.player_next, 0);
                notificationBuild.bigContentView.setViewVisibility(R.id.player_previous, 0);
                notificationBuild.bigContentView.setViewVisibility(R.id.player_progress_bar, 8);
            }
            if (!AudioStreamingManager.getInstance(this).isPlaying()) {
                notificationBuild.contentView.setViewVisibility(R.id.player_pause, 8);
                notificationBuild.contentView.setViewVisibility(R.id.player_play, 0);
                if (supportBigNotifications) {
                    notificationBuild.bigContentView.setViewVisibility(R.id.player_pause, 8);
                    notificationBuild.bigContentView.setViewVisibility(R.id.player_play, 0);
                }
            } else {
                notificationBuild.contentView.setViewVisibility(R.id.player_pause, 0);
                notificationBuild.contentView.setViewVisibility(R.id.player_play, 8);
                if (supportBigNotifications) {
                    notificationBuild.bigContentView.setViewVisibility(R.id.player_pause, 0);
                    notificationBuild.bigContentView.setViewVisibility(R.id.player_play, 8);
                }
            }
            notificationBuild.contentView.setTextViewText(R.id.player_song_name, mediaTitle);
            notificationBuild.contentView.setTextViewText(R.id.player_author_name, mediaArtist);
            if (supportBigNotifications) {
                notificationBuild.bigContentView.setTextViewText(R.id.player_song_name, mediaTitle);
                notificationBuild.bigContentView.setTextViewText(R.id.player_author_name, mediaArtist);
            }
            notificationBuild.flags |= 2;
            startForeground(5, notificationBuild);
            if (this.remoteControlClient != null) {
                RemoteControlClient.MetadataEditor metadataEditorEditMetadata = this.remoteControlClient.editMetadata(true);
                metadataEditorEditMetadata.putString(2, mediaArtist);
                metadataEditorEditMetadata.putString(7, mediaTitle);
                if (bitmapLoadImageSync != null) {
                    metadataEditorEditMetadata.putBitmap(100, bitmapLoadImageSync);
                }
                metadataEditorEditMetadata.apply();
            }
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    private String getNotificationChannelId() {
        NotificationChannel notificationChannel = new NotificationChannel(TAG, getString(R.string.playback), 3);
        notificationChannel.enableLights(true);
        notificationChannel.setLightColor(-16776961);
        notificationChannel.setLockscreenVisibility(0);
        ((android.app.NotificationManager) Objects.requireNonNull((android.app.NotificationManager) getSystemService(RemoteMessageConst.NOTIFICATION))).createNotificationChannel(notificationChannel);
        return TAG;
    }

    private void setListeners(RemoteViews remoteViews) {
        try {
            remoteViews.setOnClickPendingIntent(R.id.player_previous, PendingIntent.getBroadcast(getApplicationContext(), 0, getIntentForNotification(NOTIFY_PREVIOUS), 134217728));
            remoteViews.setOnClickPendingIntent(R.id.player_close, PendingIntent.getBroadcast(getApplicationContext(), 0, getIntentForNotification(NOTIFY_CLOSE), 134217728));
            remoteViews.setOnClickPendingIntent(R.id.player_pause, PendingIntent.getBroadcast(getApplicationContext(), 0, getIntentForNotification(NOTIFY_PAUSE), 134217728));
            remoteViews.setOnClickPendingIntent(R.id.player_next, PendingIntent.getBroadcast(getApplicationContext(), 0, getIntentForNotification(NOTIFY_NEXT), 134217728));
            remoteViews.setOnClickPendingIntent(R.id.player_play, PendingIntent.getBroadcast(getApplicationContext(), 0, getIntentForNotification(NOTIFY_PLAY), 134217728));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Intent getIntentForNotification(String str) {
        Intent intent = new Intent(str);
        intent.setClass(this, AudioStreamingReceiver.class);
        return intent;
    }

    @Override // android.app.Service
    public void onDestroy() {
        super.onDestroy();
        RemoteControlClient remoteControlClient = this.remoteControlClient;
        if (remoteControlClient != null) {
            RemoteControlClient.MetadataEditor metadataEditorEditMetadata = remoteControlClient.editMetadata(true);
            metadataEditorEditMetadata.clear();
            metadataEditorEditMetadata.apply();
            this.audioManager.unregisterRemoteControlClient(this.remoteControlClient);
        }
        try {
            TelephonyManager telephonyManager = (TelephonyManager) getSystemService(PlaceFields.PHONE);
            if (telephonyManager != null) {
                telephonyManager.listen(this.phoneStateListener, 0);
            }
        } catch (Exception e) {
            Log.e("tmessages", e.toString());
        }
        NotificationManager.getInstance().removeObserver(this, NotificationManager.audioProgressDidChanged);
        NotificationManager.getInstance().removeObserver(this, NotificationManager.audioPlayStateChanged);
    }

    @Override // dm.audiostreamer.NotificationManager.NotificationCenterDelegate
    public void didReceivedNotification(int i, Object... objArr) throws IllegalArgumentException {
        if (i == NotificationManager.setAnyPendingIntent) {
            PendingIntent pendingIntent = (PendingIntent) objArr[0];
            if (pendingIntent != null) {
                this.pendingIntent = pendingIntent;
                return;
            }
            return;
        }
        if (i == NotificationManager.audioPlayStateChanged) {
            MediaMetaData currentAudio = AudioStreamingManager.getInstance(this).getCurrentAudio();
            if (currentAudio != null) {
                createNotification(currentAudio);
            } else {
                stopSelf();
            }
        }
    }
}
