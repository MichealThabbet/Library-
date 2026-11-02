package dm.audiostreamer;

/* loaded from: classes2.dex */
public interface CurrentSessionCallback {
    void currentSeekBarPosition(int i);

    void playCurrent(int i, MediaMetaData mediaMetaData);

    void playNext(int i, MediaMetaData mediaMetaData);

    void playPrevious(int i, MediaMetaData mediaMetaData);

    void playSongComplete();

    void updatePlaybackState(int i);
}
