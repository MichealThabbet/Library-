package dm.audiostreamer;

/* loaded from: classes2.dex */
public abstract class StreamingManager {
    public abstract int lastSeekPosition();

    public abstract void onPause();

    public abstract void onPlay(MediaMetaData mediaMetaData);

    public abstract void onSeekTo(long j);

    public abstract void onSkipToNext();

    public abstract void onSkipToPrevious();

    public abstract void onStop();
}
