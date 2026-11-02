package com.app.orsozoxi.Radio;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.RemoteControlClient;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.widget.RemoteViews;
import androidx.core.app.NotificationCompat;
import androidx.core.internal.view.SupportMenu;
import com.app.orsozoxi.NewRadioActivity;
import com.app.orsozoxi.R;
import com.facebook.internal.AnalyticsEvents;
import com.facebook.places.model.PlaceFields;
import com.huawei.hms.push.constant.RemoteMessageConst;
import com.ibm.icu.impl.locale.LanguageTag;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import it.orsozoxi.android.orsonotes.R2;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;
import org.apache.http.HttpHeaders;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

/* loaded from: classes.dex */
public class SongService extends Service implements AudioManager.OnAudioFocusChangeListener {
    public static final String NOTIFY_DELETE = "com.orsozoxi.audioplayer.delete";
    public static final String NOTIFY_OPEN_APP = "com.orsozoxi.audioplayer.openapp";
    public static final String NOTIFY_PAUSE = "com.orsozoxi.audioplayer.pause";
    public static final String NOTIFY_PLAY = "com.orsozoxi.audioplayer.play";
    private static boolean currentVersionSupportBigNotification = false;
    private static boolean currentVersionSupportLockScreenControls = false;
    private static Timer timer;
    AudioManager audioManager;
    Bitmap mDummyAlbumArt;
    private MediaPlayer mp;
    private ComponentName remoteComponentName;
    private RemoteControlClient remoteControlClient;
    String LOG_CLASS = "SongService";
    int NOTIFICATION_ID = R2.attr.textStartPadding;
    private final Handler handler = new Handler() { // from class: com.app.orsozoxi.Radio.SongService.1
        @Override // android.os.Handler
        public void handleMessage(Message message) {
        }
    };

    @Override // android.media.AudioManager.OnAudioFocusChangeListener
    public void onAudioFocusChange(int i) {
    }

    @Override // android.app.Service
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override // android.app.Service
    public void onCreate() {
        this.mp = new MediaPlayer();
        this.audioManager = (AudioManager) getSystemService("audio");
        currentVersionSupportBigNotification = UtilFunctions.currentVersionSupportBigNotification();
        currentVersionSupportLockScreenControls = UtilFunctions.currentVersionSupportLockScreenControls();
        timer = new Timer();
        this.mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() { // from class: com.app.orsozoxi.Radio.-$$Lambda$SongService$i-ztK_MQ725eiYic0sqV0vmwrms
            @Override // android.media.MediaPlayer.OnCompletionListener
            public final void onCompletion(MediaPlayer mediaPlayer) {
                this.f$0.lambda$onCreate$0$SongService(mediaPlayer);
            }
        });
        super.onCreate();
    }

    public /* synthetic */ void lambda$onCreate$0$SongService(MediaPlayer mediaPlayer) {
        Controls.nextControl(getApplicationContext());
    }

    private class MainTask extends TimerTask {
        private MainTask() {
        }

        @Override // java.util.TimerTask, java.lang.Runnable
        public void run() {
            SongService.this.handler.sendEmptyMessage(0);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:19:0x0051 A[Catch: Exception -> 0x009c, TryCatch #0 {Exception -> 0x009c, blocks: (B:12:0x0028, B:14:0x002e, B:16:0x0038, B:18:0x0040, B:22:0x0065, B:24:0x0073, B:25:0x0076, B:19:0x0051, B:21:0x0059), top: B:30:0x0028 }] */
    @Override // android.app.Service
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public int onStartCommand(android.content.Intent r4, int r5, int r6) {
        /*
            r3 = this;
            java.lang.String r5 = "quality"
            java.lang.String r6 = r4.getAction()
            r0 = 2
            r1 = 1
            if (r6 == 0) goto L1d
            java.lang.String r6 = r4.getAction()
            java.lang.String r2 = "STOP_ACTION"
            boolean r6 = r6.equals(r2)
            if (r6 == 0) goto L1d
            r3.stopForeground(r1)
            r3.stopSelf()
            return r0
        L1d:
            if (r4 != 0) goto L26
            r3.stopForeground(r1)
            r3.stopSelf()
            return r0
        L26:
            if (r4 == 0) goto L51
            android.os.Bundle r6 = r4.getExtras()     // Catch: java.lang.Exception -> L9c
            if (r6 == 0) goto L51
            android.os.Bundle r6 = r4.getExtras()     // Catch: java.lang.Exception -> L9c
            boolean r6 = r6.containsKey(r5)     // Catch: java.lang.Exception -> L9c
            if (r6 == 0) goto L51
            java.util.ArrayList<com.app.orsozoxi.Radio.MediaItem> r6 = com.app.orsozoxi.Radio.PlayerConstants.SONGS_LIST     // Catch: java.lang.Exception -> L9c
            int r6 = r6.size()     // Catch: java.lang.Exception -> L9c
            if (r6 > 0) goto L65
            android.content.Context r6 = r3.getApplicationContext()     // Catch: java.lang.Exception -> L9c
            r0 = 64
            int r4 = r4.getIntExtra(r5, r0)     // Catch: java.lang.Exception -> L9c
            java.util.ArrayList r4 = com.app.orsozoxi.Radio.UtilFunctions.listOfSongs(r6, r4)     // Catch: java.lang.Exception -> L9c
            com.app.orsozoxi.Radio.PlayerConstants.SONGS_LIST = r4     // Catch: java.lang.Exception -> L9c
            goto L65
        L51:
            java.util.ArrayList<com.app.orsozoxi.Radio.MediaItem> r4 = com.app.orsozoxi.Radio.PlayerConstants.SONGS_LIST     // Catch: java.lang.Exception -> L9c
            int r4 = r4.size()     // Catch: java.lang.Exception -> L9c
            if (r4 > 0) goto L65
            android.content.Context r4 = r3.getApplicationContext()     // Catch: java.lang.Exception -> L9c
            r5 = 24
            java.util.ArrayList r4 = com.app.orsozoxi.Radio.UtilFunctions.listOfSongs(r4, r5)     // Catch: java.lang.Exception -> L9c
            com.app.orsozoxi.Radio.PlayerConstants.SONGS_LIST = r4     // Catch: java.lang.Exception -> L9c
        L65:
            java.util.ArrayList<com.app.orsozoxi.Radio.MediaItem> r4 = com.app.orsozoxi.Radio.PlayerConstants.SONGS_LIST     // Catch: java.lang.Exception -> L9c
            int r5 = com.app.orsozoxi.Radio.PlayerConstants.SONG_NUMBER     // Catch: java.lang.Exception -> L9c
            java.lang.Object r4 = r4.get(r5)     // Catch: java.lang.Exception -> L9c
            com.app.orsozoxi.Radio.MediaItem r4 = (com.app.orsozoxi.Radio.MediaItem) r4     // Catch: java.lang.Exception -> L9c
            boolean r5 = com.app.orsozoxi.Radio.SongService.currentVersionSupportLockScreenControls     // Catch: java.lang.Exception -> L9c
            if (r5 == 0) goto L76
            r3.RegisterRemoteClient()     // Catch: java.lang.Exception -> L9c
        L76:
            java.lang.String r5 = r4.getPath()     // Catch: java.lang.Exception -> L9c
            r3.playSong(r5, r4)     // Catch: java.lang.Exception -> L9c
            r3.newNotification()     // Catch: java.lang.Exception -> L9c
            r3.getRadioDetails()     // Catch: java.lang.Exception -> L9c
            android.os.Handler r4 = new android.os.Handler     // Catch: java.lang.Exception -> L9c
            com.app.orsozoxi.Radio.SongService$2 r5 = new com.app.orsozoxi.Radio.SongService$2     // Catch: java.lang.Exception -> L9c
            r5.<init>()     // Catch: java.lang.Exception -> L9c
            r4.<init>(r5)     // Catch: java.lang.Exception -> L9c
            com.app.orsozoxi.Radio.PlayerConstants.SONG_CHANGE_HANDLER = r4     // Catch: java.lang.Exception -> L9c
            android.os.Handler r4 = new android.os.Handler     // Catch: java.lang.Exception -> L9c
            com.app.orsozoxi.Radio.SongService$3 r5 = new com.app.orsozoxi.Radio.SongService$3     // Catch: java.lang.Exception -> L9c
            r5.<init>()     // Catch: java.lang.Exception -> L9c
            r4.<init>(r5)     // Catch: java.lang.Exception -> L9c
            com.app.orsozoxi.Radio.PlayerConstants.PLAY_PAUSE_HANDLER = r4     // Catch: java.lang.Exception -> L9c
            goto La0
        L9c:
            r4 = move-exception
            r4.printStackTrace()
        La0:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.app.orsozoxi.Radio.SongService.onStartCommand(android.content.Intent, int, int):int");
    }

    private String getRadioDetails() {
        Observable.fromCallable(new Callable() { // from class: com.app.orsozoxi.Radio.-$$Lambda$SongService$5rkPxjemMKRuzFZ0lZYk3golhnA
            @Override // java.util.concurrent.Callable
            public final Object call() {
                return SongService.lambda$getRadioDetails$1();
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer() { // from class: com.app.orsozoxi.Radio.-$$Lambda$SongService$fXxGRXerTu98YvIDg26yHF2rz2Q
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) throws Exception {
                this.f$0.lambda$getRadioDetails$3$SongService((String) obj);
            }
        });
        return "";
    }

    static /* synthetic */ String lambda$getRadioDetails$1() throws Exception {
        try {
            Document document = Jsoup.parse(new URL(PlayerConstants.radioUrlGetInfo).openStream(), "UTF-8", PlayerConstants.radioUrlGetInfo);
            for (int i = 0; i < document.getAllElements().size(); i++) {
                System.out.println("Song Title: " + document.getElementsByIndexEquals(i).text());
                if (document.getElementsByIndexEquals(i).text().toString().startsWith("Currently playing:")) {
                    return document.getElementsByIndexEquals(i).text().toString().replace("Currently playing:", "").replace(AnalyticsEvents.PARAMETER_DIALOG_OUTCOME_VALUE_UNKNOWN, "").replaceAll(LanguageTag.SEP, "");
                }
            }
            return "";
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return "Executed";
        } catch (IOException e2) {
            e2.printStackTrace();
            return "Executed";
        }
    }

    public /* synthetic */ void lambda$getRadioDetails$3$SongService(String str) throws Exception {
        System.out.println("Song Current: " + str);
        PlayerConstants.SONGS_LIST.get(PlayerConstants.SONG_NUMBER).setAlbum(str);
        if (PlayerConstants.SONG_PAUSED) {
            return;
        }
        newNotification();
        if (currentVersionSupportLockScreenControls) {
            UpdateMetadata(PlayerConstants.SONGS_LIST.get(PlayerConstants.SONG_NUMBER));
            this.remoteControlClient.setPlaybackState(3);
        }
        Observable.timer(45L, TimeUnit.SECONDS).map(new Function() { // from class: com.app.orsozoxi.Radio.-$$Lambda$SongService$XmIE78uknX5-htzC_68gXfsl9X8
            @Override // io.reactivex.functions.Function
            public final Object apply(Object obj) {
                return this.f$0.lambda$getRadioDetails$2$SongService((Long) obj);
            }
        }).observeOn(AndroidSchedulers.mainThread()).subscribe();
    }

    public /* synthetic */ String lambda$getRadioDetails$2$SongService(Long l) throws Exception {
        return getRadioDetails();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void newNotification() {
        String title = PlayerConstants.SONGS_LIST.get(PlayerConstants.SONG_NUMBER).getTitle();
        String album = PlayerConstants.SONGS_LIST.get(PlayerConstants.SONG_NUMBER).getAlbum();
        RemoteViews remoteViews = new RemoteViews(getApplicationContext().getPackageName(), R.layout.custom_notification);
        RemoteViews remoteViews2 = new RemoteViews(getApplicationContext().getPackageName(), R.layout.big_notification);
        NotificationManager notificationManager = (NotificationManager) getSystemService(RemoteMessageConst.NOTIFICATION);
        if (Build.VERSION.SDK_INT >= 26) {
            NotificationChannel notificationChannel = new NotificationChannel("orso_id_01", "My Notifications", 2);
            notificationChannel.setDescription("Channel");
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(SupportMenu.CATEGORY_MASK);
            notificationChannel.setVibrationPattern(new long[]{0});
            notificationChannel.enableVibration(false);
            notificationChannel.setSound(null, null);
            notificationManager.createNotificationChannel(notificationChannel);
        }
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "orso_id_01");
        builder.setVibrate(new long[]{0});
        builder.setSmallIcon(R.drawable.radioooo).setContentTitle(title).build();
        setListeners(remoteViews);
        setListeners(remoteViews2);
        Notification notificationBuild = builder.build();
        notificationBuild.contentView = remoteViews;
        if (currentVersionSupportBigNotification) {
            notificationBuild.bigContentView = remoteViews2;
        }
        try {
            PlayerConstants.SONGS_LIST.get(PlayerConstants.SONG_NUMBER).getAlbumId();
            notificationBuild.contentView.setImageViewResource(R.id.imageViewAlbumArt, R.drawable.radioooo);
            if (currentVersionSupportBigNotification) {
                notificationBuild.bigContentView.setImageViewResource(R.id.imageViewAlbumArt, R.drawable.radioooo);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (PlayerConstants.SONG_PAUSED) {
            notificationBuild.contentView.setViewVisibility(R.id.btnPause, 8);
            notificationBuild.contentView.setViewVisibility(R.id.btnPlay, 0);
            if (currentVersionSupportBigNotification) {
                notificationBuild.bigContentView.setViewVisibility(R.id.btnPause, 8);
                notificationBuild.bigContentView.setViewVisibility(R.id.btnPlay, 0);
            }
        } else {
            notificationBuild.contentView.setViewVisibility(R.id.btnPause, 0);
            notificationBuild.contentView.setViewVisibility(R.id.btnPlay, 8);
            if (currentVersionSupportBigNotification) {
                notificationBuild.bigContentView.setViewVisibility(R.id.btnPause, 0);
                notificationBuild.bigContentView.setViewVisibility(R.id.btnPlay, 8);
            }
        }
        notificationBuild.contentView.setTextViewText(R.id.textSongName, title);
        notificationBuild.contentView.setTextViewText(R.id.textAlbumName, album);
        if (currentVersionSupportBigNotification) {
            notificationBuild.bigContentView.setTextViewText(R.id.textSongName, title);
            notificationBuild.bigContentView.setTextViewText(R.id.textAlbumName, album);
        }
        notificationManager.notify(this.NOTIFICATION_ID, notificationBuild);
        startForeground(this.NOTIFICATION_ID, notificationBuild);
    }

    public void setListeners(RemoteViews remoteViews) {
        Intent intent = new Intent(NOTIFY_DELETE);
        intent.setClass(this, NotificationBroadcast.class);
        Intent intent2 = new Intent(NOTIFY_PAUSE);
        intent2.setClass(this, NotificationBroadcast.class);
        Intent intent3 = new Intent(NOTIFY_PLAY);
        intent3.setClass(this, NotificationBroadcast.class);
        Intent intent4 = new Intent(NOTIFY_OPEN_APP);
        intent4.setClass(this, NotificationBroadcast.class);
        remoteViews.setOnClickPendingIntent(R.id.btnDelete, PendingIntent.getBroadcast(getApplicationContext(), 0, intent, 201326592));
        remoteViews.setOnClickPendingIntent(R.id.btnPause, PendingIntent.getBroadcast(getApplicationContext(), 0, intent2, 201326592));
        remoteViews.setOnClickPendingIntent(R.id.btnPlay, PendingIntent.getBroadcast(getApplicationContext(), 0, intent3, 201326592));
        remoteViews.setOnClickPendingIntent(R.id.imageViewAlbumArt, PendingIntent.getBroadcast(getApplicationContext(), 0, intent4, 201326592));
    }

    @Override // android.app.Service
    public void onDestroy() throws IllegalStateException {
        MediaPlayer mediaPlayer = this.mp;
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            this.mp = null;
        }
        stopForeground(true);
        super.onDestroy();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void playSong(final String str, final MediaItem mediaItem) throws IllegalStateException, IOException, SecurityException, IllegalArgumentException {
        try {
            if (currentVersionSupportLockScreenControls) {
                UpdateMetadata(mediaItem);
                this.remoteControlClient.setPlaybackState(3);
            }
            this.mp.reset();
            this.mp.setAudioStreamType(3);
            HashMap map = new HashMap();
            map.put("Content-Type", "audio/mp3");
            map.put(HttpHeaders.ACCEPT_RANGES, "bytes");
            map.put("Status", "206");
            map.put("Cache-control", "no-cache");
            this.mp.setDataSource(getApplicationContext(), Uri.parse(str), map);
            NewRadioActivity.startProgress();
            this.mp.prepareAsync();
            this.mp.setOnPreparedListener(new MediaPlayer.OnPreparedListener() { // from class: com.app.orsozoxi.Radio.SongService.4
                @Override // android.media.MediaPlayer.OnPreparedListener
                public void onPrepared(MediaPlayer mediaPlayer) throws IllegalStateException {
                    mediaPlayer.start();
                    NewRadioActivity.dismissProgress();
                }
            });
            this.mp.setOnErrorListener(new MediaPlayer.OnErrorListener() { // from class: com.app.orsozoxi.Radio.SongService.5
                @Override // android.media.MediaPlayer.OnErrorListener
                public boolean onError(MediaPlayer mediaPlayer, int i, int i2) throws IllegalStateException, IOException, SecurityException, IllegalArgumentException {
                    if (i == 100 || i == 1) {
                        mediaPlayer.reset();
                    }
                    SongService.this.playSong(str, mediaItem);
                    return true;
                }
            });
            timer.scheduleAtFixedRate(new MainTask(), 0L, 100L);
        } catch (IOException e) {
            e.printStackTrace();
        }
        PhoneStateListener phoneStateListener = new PhoneStateListener() { // from class: com.app.orsozoxi.Radio.SongService.6
            @Override // android.telephony.PhoneStateListener
            public void onCallStateChanged(int i, String str2) {
                if (i == 1) {
                    SongService.this.stopSelf();
                }
                super.onCallStateChanged(i, str2);
            }
        };
        TelephonyManager telephonyManager = (TelephonyManager) getSystemService(PlaceFields.PHONE);
        if (telephonyManager != null) {
            telephonyManager.listen(phoneStateListener, 32);
        }
    }

    private void RegisterRemoteClient() {
        ComponentName componentName = new ComponentName(getApplicationContext(), new NotificationBroadcast().ComponentName());
        this.remoteComponentName = componentName;
        try {
            if (this.remoteControlClient == null) {
                this.audioManager.registerMediaButtonEventReceiver(componentName);
                Intent intent = new Intent("android.intent.action.MEDIA_BUTTON");
                intent.setComponent(this.remoteComponentName);
                RemoteControlClient remoteControlClient = new RemoteControlClient(PendingIntent.getBroadcast(this, 0, intent, 67108864));
                this.remoteControlClient = remoteControlClient;
                this.audioManager.registerRemoteControlClient(remoteControlClient);
            }
            this.remoteControlClient.setTransportControlFlags(60);
        } catch (Exception unused) {
        }
    }

    private void UpdateMetadata(MediaItem mediaItem) throws IllegalArgumentException {
        RemoteControlClient remoteControlClient = this.remoteControlClient;
        if (remoteControlClient == null) {
            return;
        }
        RemoteControlClient.MetadataEditor metadataEditorEditMetadata = remoteControlClient.editMetadata(true);
        metadataEditorEditMetadata.putString(1, mediaItem.getAlbum());
        metadataEditorEditMetadata.putString(2, mediaItem.getArtist());
        metadataEditorEditMetadata.putString(7, mediaItem.getTitle());
        this.mDummyAlbumArt = null;
        if (0 == 0) {
            this.mDummyAlbumArt = BitmapFactory.decodeResource(getResources(), R.drawable.default_album_art);
        }
        metadataEditorEditMetadata.putBitmap(100, this.mDummyAlbumArt);
        metadataEditorEditMetadata.apply();
        this.audioManager.requestAudioFocus(this, 3, 1);
    }
}
