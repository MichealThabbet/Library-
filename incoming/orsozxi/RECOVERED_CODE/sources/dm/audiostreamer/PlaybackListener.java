package dm.audiostreamer;

/* loaded from: classes2.dex */
public interface PlaybackListener {

    public interface Callback {
        void onCompletion();

        void onError(String str);

        void onPlaybackStatusChanged(int i);

        void setCurrentMediaId(String str);
    }

    String getCurrentMediaId();

    int getCurrentStreamPosition();

    int getState();

    boolean isConnected();

    boolean isPlaying();

    void pause();

    void play(MediaMetaData mediaMetaData);

    void seekTo(int i);

    void setCallback(Callback callback);

    void setCurrentMediaId(String str);

    void setCurrentStreamPosition(int i);

    void setState(int i);

    void start();

    void stop(boolean z);

    void updateLastKnownStreamPosition();
}
