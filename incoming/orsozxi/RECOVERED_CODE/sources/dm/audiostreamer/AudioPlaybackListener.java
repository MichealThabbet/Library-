package dm.audiostreamer;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.wifi.WifiManager;
import android.text.TextUtils;
import dm.audiostreamer.PlaybackListener;
import java.io.IOException;

/* loaded from: classes2.dex */
public class AudioPlaybackListener implements PlaybackListener, AudioManager.OnAudioFocusChangeListener, MediaPlayer.OnCompletionListener, MediaPlayer.OnErrorListener, MediaPlayer.OnPreparedListener, MediaPlayer.OnSeekCompleteListener {
    private static final int AUDIO_FOCUSED = 2;
    private static final int AUDIO_NO_FOCUS_CAN_DUCK = 1;
    private static final int AUDIO_NO_FOCUS_NO_DUCK = 0;
    private static final String TAG = Logger.makeLogTag(AudioPlaybackListener.class);
    public static final float VOLUME_DUCK = 0.2f;
    public static final float VOLUME_NORMAL = 1.0f;
    private final AudioManager mAudioManager;
    private PlaybackListener.Callback mCallback;
    private final Context mContext;
    private volatile String mCurrentMediaId;
    private volatile int mCurrentPosition;
    private MediaPlayer mMediaPlayer;
    private boolean mPlayOnFocusGain;
    private final WifiManager.WifiLock mWifiLock;
    private int mAudioFocus = 0;
    private final IntentFilter mAudioNoisyIntentFilter = new IntentFilter("android.media.AUDIO_BECOMING_NOISY");
    private final BroadcastReceiver mAudioNoisyReceiver = new BroadcastReceiver() { // from class: dm.audiostreamer.AudioPlaybackListener.1
        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            if ("android.media.AUDIO_BECOMING_NOISY".equals(intent.getAction())) {
                Logger.d(AudioPlaybackListener.TAG, "Headphones disconnected.");
                AudioPlaybackListener.this.isPlaying();
            }
        }
    };
    private int mState = 0;

    @Override // dm.audiostreamer.PlaybackListener
    public boolean isConnected() {
        return true;
    }

    @Override // dm.audiostreamer.PlaybackListener
    public void start() {
    }

    public AudioPlaybackListener(Context context) {
        this.mContext = context;
        this.mAudioManager = (AudioManager) context.getSystemService("audio");
        this.mWifiLock = ((WifiManager) context.getSystemService("wifi")).createWifiLock(1, "dmAudioStreaming_Lock");
    }

    @Override // dm.audiostreamer.PlaybackListener
    public void stop(boolean z) {
        PlaybackListener.Callback callback;
        this.mState = 1;
        if (z && (callback = this.mCallback) != null) {
            callback.onPlaybackStatusChanged(1);
        }
        this.mCurrentPosition = getCurrentStreamPosition();
        giveUpAudioFocus();
        unregisterAudioNoisyReceiver();
        relaxResources(true);
    }

    @Override // dm.audiostreamer.PlaybackListener
    public void setState(int i) {
        this.mState = i;
    }

    @Override // dm.audiostreamer.PlaybackListener
    public int getState() {
        return this.mState;
    }

    @Override // dm.audiostreamer.PlaybackListener
    public boolean isPlaying() {
        MediaPlayer mediaPlayer;
        return this.mPlayOnFocusGain || ((mediaPlayer = this.mMediaPlayer) != null && mediaPlayer.isPlaying());
    }

    @Override // dm.audiostreamer.PlaybackListener
    public int getCurrentStreamPosition() {
        MediaPlayer mediaPlayer = this.mMediaPlayer;
        return mediaPlayer != null ? mediaPlayer.getCurrentPosition() : this.mCurrentPosition;
    }

    @Override // dm.audiostreamer.PlaybackListener
    public void updateLastKnownStreamPosition() {
        MediaPlayer mediaPlayer = this.mMediaPlayer;
        if (mediaPlayer != null) {
            this.mCurrentPosition = mediaPlayer.getCurrentPosition();
        }
    }

    @Override // dm.audiostreamer.PlaybackListener
    public void play(MediaMetaData mediaMetaData) throws IllegalStateException, IOException, SecurityException, IllegalArgumentException {
        this.mPlayOnFocusGain = true;
        tryToGetAudioFocus();
        registerAudioNoisyReceiver();
        String mediaId = mediaMetaData.getMediaId();
        boolean z = !TextUtils.equals(mediaId, this.mCurrentMediaId);
        if (z) {
            this.mCurrentPosition = 0;
            this.mCurrentMediaId = mediaId;
        }
        if (this.mState == 2 && !z && this.mMediaPlayer != null) {
            configMediaPlayerState();
            return;
        }
        this.mState = 1;
        relaxResources(false);
        String mediaUrl = mediaMetaData.getMediaUrl();
        if (mediaUrl != null) {
            mediaUrl = mediaUrl.replaceAll(" ", "%20");
        }
        try {
            createMediaPlayerIfNeeded();
            this.mState = 6;
            this.mMediaPlayer.setAudioStreamType(3);
            this.mMediaPlayer.setDataSource(mediaUrl);
            this.mMediaPlayer.prepareAsync();
            this.mWifiLock.acquire();
            if (this.mCallback != null) {
                this.mCallback.onPlaybackStatusChanged(this.mState);
            }
        } catch (IOException e) {
            Logger.e(TAG, e, "Exception playing song");
            PlaybackListener.Callback callback = this.mCallback;
            if (callback != null) {
                callback.onError(e.getMessage());
            }
        }
    }

    @Override // dm.audiostreamer.PlaybackListener
    public void pause() throws IllegalStateException {
        try {
            if (this.mState == 3) {
                if (this.mMediaPlayer != null && this.mMediaPlayer.isPlaying()) {
                    this.mMediaPlayer.pause();
                    this.mCurrentPosition = this.mMediaPlayer.getCurrentPosition();
                }
                relaxResources(false);
            }
            this.mState = 2;
            if (this.mCallback != null) {
                this.mCallback.onPlaybackStatusChanged(2);
            }
            unregisterAudioNoisyReceiver();
        } catch (IllegalStateException e) {
            Logger.e(TAG, e, "Exception pause IllegalStateException");
            e.printStackTrace();
        }
    }

    @Override // dm.audiostreamer.PlaybackListener
    public void seekTo(int i) throws IllegalStateException {
        Logger.d(TAG, "seekTo called with ", Integer.valueOf(i));
        MediaPlayer mediaPlayer = this.mMediaPlayer;
        if (mediaPlayer == null) {
            this.mCurrentPosition = i;
            return;
        }
        if (mediaPlayer.isPlaying()) {
            this.mState = 6;
        }
        registerAudioNoisyReceiver();
        this.mMediaPlayer.seekTo(i);
        PlaybackListener.Callback callback = this.mCallback;
        if (callback != null) {
            callback.onPlaybackStatusChanged(this.mState);
        }
    }

    @Override // dm.audiostreamer.PlaybackListener
    public void setCallback(PlaybackListener.Callback callback) {
        this.mCallback = callback;
    }

    @Override // dm.audiostreamer.PlaybackListener
    public void setCurrentStreamPosition(int i) {
        this.mCurrentPosition = i;
    }

    @Override // dm.audiostreamer.PlaybackListener
    public void setCurrentMediaId(String str) {
        this.mCurrentMediaId = str;
    }

    @Override // dm.audiostreamer.PlaybackListener
    public String getCurrentMediaId() {
        return this.mCurrentMediaId;
    }

    private void tryToGetAudioFocus() {
        Logger.d(TAG, "tryToGetAudioFocus");
        if (this.mAudioManager.requestAudioFocus(this, 3, 1) == 1) {
            this.mAudioFocus = 2;
        } else {
            this.mAudioFocus = 0;
        }
    }

    private void giveUpAudioFocus() {
        Logger.d(TAG, "giveUpAudioFocus");
        if (this.mAudioManager.abandonAudioFocus(this) == 1) {
            this.mAudioFocus = 0;
        }
    }

    private void configMediaPlayerState() throws IllegalStateException {
        Logger.d(TAG, "configMediaPlayerState. mAudioFocus=", Integer.valueOf(this.mAudioFocus));
        if (this.mAudioFocus == 0) {
            if (this.mState == 3) {
                this.mPlayOnFocusGain = false;
                pause();
            }
        } else {
            registerAudioNoisyReceiver();
            if (this.mAudioFocus == 1) {
                this.mMediaPlayer.setVolume(0.2f, 0.2f);
            } else {
                MediaPlayer mediaPlayer = this.mMediaPlayer;
                if (mediaPlayer != null) {
                    mediaPlayer.setVolume(1.0f, 1.0f);
                }
            }
            if (this.mPlayOnFocusGain) {
                MediaPlayer mediaPlayer2 = this.mMediaPlayer;
                if (mediaPlayer2 != null && !mediaPlayer2.isPlaying()) {
                    Logger.d(TAG, "configMediaPlayerState startMediaPlayer. seeking to ", Integer.valueOf(this.mCurrentPosition));
                    if (this.mCurrentPosition == this.mMediaPlayer.getCurrentPosition()) {
                        this.mMediaPlayer.start();
                        this.mState = 3;
                    } else {
                        this.mMediaPlayer.seekTo(this.mCurrentPosition);
                        this.mState = 6;
                    }
                }
                this.mPlayOnFocusGain = false;
            }
        }
        PlaybackListener.Callback callback = this.mCallback;
        if (callback != null) {
            callback.onPlaybackStatusChanged(this.mState);
        }
    }

    @Override // android.media.AudioManager.OnAudioFocusChangeListener
    public void onAudioFocusChange(int i) throws IllegalStateException {
        Logger.d(TAG, "onAudioFocusChange. focusChange=", Integer.valueOf(i));
        if (i == 1) {
            this.mAudioFocus = 2;
        } else if (i == -1 || i == -2 || i == -3) {
            int i2 = i == -3 ? 1 : 0;
            this.mAudioFocus = i2;
            if (this.mState == 3 && i2 == 0) {
                this.mPlayOnFocusGain = true;
            }
        } else {
            Logger.e(TAG, "onAudioFocusChange: Ignoring unsupported focusChange: ", Integer.valueOf(i));
        }
        configMediaPlayerState();
    }

    @Override // android.media.MediaPlayer.OnSeekCompleteListener
    public void onSeekComplete(MediaPlayer mediaPlayer) throws IllegalStateException {
        Logger.d(TAG, "onSeekComplete from MediaPlayer:", Integer.valueOf(mediaPlayer.getCurrentPosition()));
        this.mCurrentPosition = mediaPlayer.getCurrentPosition();
        if (this.mState == 6) {
            registerAudioNoisyReceiver();
            this.mMediaPlayer.start();
            this.mState = 3;
        }
        PlaybackListener.Callback callback = this.mCallback;
        if (callback != null) {
            callback.onPlaybackStatusChanged(this.mState);
        }
    }

    @Override // android.media.MediaPlayer.OnCompletionListener
    public void onCompletion(MediaPlayer mediaPlayer) {
        Logger.d(TAG, "onCompletion from MediaPlayer");
        PlaybackListener.Callback callback = this.mCallback;
        if (callback != null) {
            callback.onCompletion();
        }
    }

    @Override // android.media.MediaPlayer.OnPreparedListener
    public void onPrepared(MediaPlayer mediaPlayer) throws IllegalStateException {
        Logger.d(TAG, "onPrepared from MediaPlayer");
        configMediaPlayerState();
    }

    @Override // android.media.MediaPlayer.OnErrorListener
    public boolean onError(MediaPlayer mediaPlayer, int i, int i2) {
        Logger.e(TAG, "Media player error: what=" + i + ", extra=" + i2);
        PlaybackListener.Callback callback = this.mCallback;
        if (callback != null) {
            callback.onError("MediaPlayer error " + i + " (" + i2 + ")");
        }
        return true;
    }

    private void createMediaPlayerIfNeeded() {
        String str = TAG;
        Object[] objArr = new Object[2];
        objArr[0] = "createMediaPlayerIfNeeded. needed? ";
        objArr[1] = Boolean.valueOf(this.mMediaPlayer == null);
        Logger.d(str, objArr);
        MediaPlayer mediaPlayer = this.mMediaPlayer;
        if (mediaPlayer == null) {
            MediaPlayer mediaPlayer2 = new MediaPlayer();
            this.mMediaPlayer = mediaPlayer2;
            mediaPlayer2.setWakeMode(this.mContext.getApplicationContext(), 1);
            this.mMediaPlayer.setOnPreparedListener(this);
            this.mMediaPlayer.setOnCompletionListener(this);
            this.mMediaPlayer.setOnErrorListener(this);
            this.mMediaPlayer.setOnSeekCompleteListener(this);
            return;
        }
        mediaPlayer.reset();
    }

    private void relaxResources(boolean z) {
        MediaPlayer mediaPlayer;
        Logger.d(TAG, "relaxResources. releaseMediaPlayer=", Boolean.valueOf(z));
        if (z && (mediaPlayer = this.mMediaPlayer) != null) {
            mediaPlayer.reset();
            this.mMediaPlayer.release();
            this.mMediaPlayer = null;
            this.mCurrentMediaId = null;
            this.mCurrentPosition = 0;
        }
        if (this.mWifiLock.isHeld()) {
            this.mWifiLock.release();
        }
    }

    private void registerAudioNoisyReceiver() {
        try {
            if (this.mAudioNoisyReceiver != null) {
                this.mContext.registerReceiver(this.mAudioNoisyReceiver, this.mAudioNoisyIntentFilter);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void unregisterAudioNoisyReceiver() {
        try {
            if (this.mAudioNoisyReceiver != null) {
                this.mContext.unregisterReceiver(this.mAudioNoisyReceiver);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
