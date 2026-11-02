package dm.audiostreamer;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import dm.audiostreamer.PlaybackListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

/* loaded from: classes2.dex */
public class AudioStreamingManager extends StreamingManager {
    private static final long PROGRESS_UPDATE_INITIAL_INTERVAL = 100;
    private static final long PROGRESS_UPDATE_INTERNAL = 1000;
    private AudioPlaybackListener audioPlayback;
    private Context context;
    private MediaMetaData currentAudio;
    private CurrentSessionCallback currentSessionCallback;
    public int mLastPlaybackState;
    private ScheduledFuture<?> mScheduleFuture;
    public PendingIntent pendingIntent;
    private static final String TAG = Logger.makeLogTag(AudioStreamingManager.class);
    private static volatile AudioStreamingManager instance = null;
    public static volatile Handler applicationHandler = null;
    private int index = 0;
    private boolean playMultiple = false;
    private boolean showPlayerNotification = false;
    private List<MediaMetaData> mediaList = new ArrayList();
    private long currentPosition = 0;
    private final Handler mHandler = new Handler();
    private final ScheduledExecutorService mExecutorService = Executors.newSingleThreadScheduledExecutor();
    private final Runnable mUpdateProgressTask = new Runnable() { // from class: dm.audiostreamer.AudioStreamingManager.2
        @Override // java.lang.Runnable
        public void run() {
            AudioStreamingManager.this.updateProgress();
        }
    };

    public static AudioStreamingManager getInstance(Context context) {
        if (instance == null) {
            synchronized (AudioStreamingManager.class) {
                instance = new AudioStreamingManager();
                instance.context = context;
                instance.audioPlayback = new AudioPlaybackListener(context);
                instance.audioPlayback.setCallback(new MyStatusCallback());
                applicationHandler = new Handler(context.getMainLooper());
            }
        }
        return instance;
    }

    public void subscribesCallBack(CurrentSessionCallback currentSessionCallback) {
        this.currentSessionCallback = currentSessionCallback;
    }

    public int getCurrentIndex() {
        return this.index;
    }

    public void unSubscribeCallBack() {
        this.currentSessionCallback = null;
    }

    public MediaMetaData getCurrentAudio() {
        return this.currentAudio;
    }

    public String getCurrentAudioId() {
        MediaMetaData mediaMetaData = this.currentAudio;
        return mediaMetaData != null ? mediaMetaData.getMediaId() : "";
    }

    public boolean isPlayMultiple() {
        return this.playMultiple;
    }

    public void setPlayMultiple(boolean z) {
        this.playMultiple = z;
    }

    public boolean isPlaying() {
        return instance.audioPlayback.isPlaying();
    }

    public void setPendingIntentAct(PendingIntent pendingIntent) {
        this.pendingIntent = pendingIntent;
    }

    public void setShowPlayerNotification(boolean z) {
        this.showPlayerNotification = z;
    }

    public void setMediaList(List<MediaMetaData> list) {
        List<MediaMetaData> list2 = this.mediaList;
        if (list2 != null) {
            list2.clear();
            this.mediaList.addAll(list);
        }
    }

    public void clearList() {
        List<MediaMetaData> list = this.mediaList;
        if (list == null || list.size() <= 0) {
            return;
        }
        this.mediaList.clear();
        this.index = 0;
        onPause();
    }

    @Override // dm.audiostreamer.StreamingManager
    public void onPlay(MediaMetaData mediaMetaData) throws IllegalStateException, IOException, SecurityException, IllegalArgumentException {
        if (mediaMetaData == null) {
            return;
        }
        if (this.playMultiple && !isMediaListEmpty()) {
            this.index = this.mediaList.indexOf(mediaMetaData);
        }
        MediaMetaData mediaMetaData2 = this.currentAudio;
        if (mediaMetaData2 != null && mediaMetaData2.getMediaId().equalsIgnoreCase(mediaMetaData.getMediaId()) && instance.audioPlayback != null && instance.audioPlayback.isPlaying()) {
            onPause();
            return;
        }
        this.currentAudio = mediaMetaData;
        handlePlayRequest();
        CurrentSessionCallback currentSessionCallback = this.currentSessionCallback;
        if (currentSessionCallback != null) {
            currentSessionCallback.playCurrent(this.index, this.currentAudio);
        }
    }

    @Override // dm.audiostreamer.StreamingManager
    public void onPause() {
        handlePauseRequest();
    }

    @Override // dm.audiostreamer.StreamingManager
    public void onStop() {
        handleStopRequest(null);
    }

    @Override // dm.audiostreamer.StreamingManager
    public void onSeekTo(long j) throws IllegalStateException {
        this.audioPlayback.seekTo((int) j);
    }

    @Override // dm.audiostreamer.StreamingManager
    public int lastSeekPosition() {
        AudioPlaybackListener audioPlaybackListener = this.audioPlayback;
        if (audioPlaybackListener == null) {
            return 0;
        }
        return audioPlaybackListener.getCurrentStreamPosition();
    }

    @Override // dm.audiostreamer.StreamingManager
    public void onSkipToNext() throws IllegalStateException, IOException, SecurityException, IllegalArgumentException {
        int i = this.index + 1;
        if (isValidIndex(true, i)) {
            MediaMetaData mediaMetaData = this.mediaList.get(i);
            onPlay(mediaMetaData);
            if (instance.currentSessionCallback != null) {
                this.currentSessionCallback.playNext(i, mediaMetaData);
            }
        }
    }

    @Override // dm.audiostreamer.StreamingManager
    public void onSkipToPrevious() throws IllegalStateException, IOException, SecurityException, IllegalArgumentException {
        int i = this.index - 1;
        if (isValidIndex(false, i)) {
            MediaMetaData mediaMetaData = this.mediaList.get(i);
            onPlay(mediaMetaData);
            if (instance.currentSessionCallback != null) {
                this.currentSessionCallback.playPrevious(i, mediaMetaData);
            }
        }
    }

    public boolean isMediaListEmpty() {
        List<MediaMetaData> list = this.mediaList;
        return list == null || list.size() == 0;
    }

    private boolean isValidIndex(boolean z, int i) {
        return z ? this.playMultiple && !isMediaListEmpty() && this.mediaList.size() > i : this.playMultiple && !isMediaListEmpty() && i >= 0;
    }

    public void handlePlayRequest() throws IllegalStateException, IOException, SecurityException, IllegalArgumentException {
        MediaMetaData mediaMetaData;
        Logger.d(TAG, "handlePlayRequest: mState=" + this.audioPlayback.getState());
        AudioPlaybackListener audioPlaybackListener = this.audioPlayback;
        if (audioPlaybackListener == null || (mediaMetaData = this.currentAudio) == null) {
            return;
        }
        audioPlaybackListener.play(mediaMetaData);
        if (this.showPlayerNotification) {
            if (this.context != null) {
                this.context.startService(new Intent(this.context, (Class<?>) AudioStreamingService.class));
            } else {
                this.context.stopService(new Intent(this.context, (Class<?>) AudioStreamingService.class));
            }
            NotificationManager.getInstance().postNotificationName(NotificationManager.audioDidStarted, this.currentAudio);
            NotificationManager.getInstance().postNotificationName(NotificationManager.audioPlayStateChanged, getCurrentAudio().getMediaId());
            setPendingIntent();
        }
    }

    private void setPendingIntent() {
        new Handler().postDelayed(new Runnable() { // from class: dm.audiostreamer.AudioStreamingManager.1
            @Override // java.lang.Runnable
            public void run() {
                if (AudioStreamingManager.this.pendingIntent != null) {
                    NotificationManager.getInstance().postNotificationName(NotificationManager.setAnyPendingIntent, AudioStreamingManager.this.pendingIntent);
                }
            }
        }, 400L);
    }

    public void handlePauseRequest() {
        Logger.d(TAG, "handlePauseRequest: mState=" + this.audioPlayback.getState());
        AudioPlaybackListener audioPlaybackListener = this.audioPlayback;
        if (audioPlaybackListener == null || !audioPlaybackListener.isPlaying()) {
            return;
        }
        this.audioPlayback.pause();
        if (this.showPlayerNotification) {
            NotificationManager.getInstance().postNotificationName(NotificationManager.audioPlayStateChanged, getCurrentAudio().getMediaId());
        }
    }

    public void handleStopRequest(String str) {
        Logger.d(TAG, "handleStopRequest: mState=" + this.audioPlayback.getState() + " error=", str);
        this.audioPlayback.stop(true);
    }

    static class MyStatusCallback implements PlaybackListener.Callback {
        @Override // dm.audiostreamer.PlaybackListener.Callback
        public void onError(String str) {
        }

        @Override // dm.audiostreamer.PlaybackListener.Callback
        public void setCurrentMediaId(String str) {
        }

        MyStatusCallback() {
        }

        @Override // dm.audiostreamer.PlaybackListener.Callback
        public void onCompletion() throws IllegalStateException, IOException, SecurityException, IllegalArgumentException {
            if (!AudioStreamingManager.instance.playMultiple || AudioStreamingManager.instance.isMediaListEmpty()) {
                AudioStreamingManager.instance.handleStopRequest(null);
                return;
            }
            if (AudioStreamingManager.instance.currentSessionCallback != null) {
                AudioStreamingManager.instance.currentSessionCallback.playSongComplete();
            }
            AudioStreamingManager.instance.onSkipToNext();
        }

        @Override // dm.audiostreamer.PlaybackListener.Callback
        public void onPlaybackStatusChanged(int i) {
            try {
                if (i == 3) {
                    AudioStreamingManager.instance.scheduleSeekBarUpdate();
                } else {
                    AudioStreamingManager.instance.stopSeekBarUpdate();
                }
                if (AudioStreamingManager.instance.currentSessionCallback != null) {
                    AudioStreamingManager.instance.currentSessionCallback.updatePlaybackState(i);
                }
                AudioStreamingManager.instance.mLastPlaybackState = i;
                if (AudioStreamingManager.instance.currentAudio != null) {
                    AudioStreamingManager.instance.currentAudio.setPlayState(i);
                }
                if (AudioStreamingManager.instance.showPlayerNotification) {
                    NotificationManager.getInstance().postNotificationName(NotificationManager.audioPlayStateChanged, AudioStreamingManager.instance.getCurrentAudio().getMediaId());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void cleanupPlayer(Context context, boolean z, boolean z2) {
        cleanupPlayer(z, z2);
    }

    public void cleanupPlayer(boolean z, boolean z2) {
        handlePauseRequest();
        this.audioPlayback.stop(true);
        if (z2) {
            this.context.stopService(new Intent(this.context, (Class<?>) AudioStreamingService.class));
        }
    }

    public void scheduleSeekBarUpdate() {
        stopSeekBarUpdate();
        if (this.mExecutorService.isShutdown()) {
            return;
        }
        this.mScheduleFuture = this.mExecutorService.scheduleAtFixedRate(new Runnable() { // from class: dm.audiostreamer.AudioStreamingManager.3
            @Override // java.lang.Runnable
            public void run() {
                AudioStreamingManager.this.mHandler.post(AudioStreamingManager.this.mUpdateProgressTask);
            }
        }, PROGRESS_UPDATE_INITIAL_INTERVAL, 1000L, TimeUnit.MILLISECONDS);
    }

    public void stopSeekBarUpdate() {
        ScheduledFuture<?> scheduledFuture = this.mScheduleFuture;
        if (scheduledFuture != null) {
            scheduledFuture.cancel(false);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateProgress() {
        if (instance.mLastPlaybackState == 0 || instance.mLastPlaybackState < 0 || instance.mLastPlaybackState == 2 || instance.currentSessionCallback == null) {
            return;
        }
        instance.currentSessionCallback.currentSeekBarPosition(this.audioPlayback.getCurrentStreamPosition());
    }
}
