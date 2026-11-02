package dm.audiostreamer;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.view.KeyEvent;
import java.io.IOException;

/* loaded from: classes2.dex */
public class AudioStreamingReceiver extends BroadcastReceiver {
    private AudioStreamingManager audioStreamingManager;

    @Override // android.content.BroadcastReceiver
    public void onReceive(Context context, Intent intent) throws IllegalStateException, IOException, SecurityException, IllegalArgumentException {
        KeyEvent keyEvent;
        AudioStreamingManager audioStreamingManager = AudioStreamingManager.getInstance(context);
        this.audioStreamingManager = audioStreamingManager;
        if (audioStreamingManager == null) {
            return;
        }
        if (intent.getAction().equals("android.intent.action.MEDIA_BUTTON")) {
            if (intent.getExtras() == null || (keyEvent = (KeyEvent) intent.getExtras().get("android.intent.extra.KEY_EVENT")) == null || keyEvent.getAction() != 0) {
                return;
            }
            int keyCode = keyEvent.getKeyCode();
            if (keyCode == 79 || keyCode == 85) {
                if (this.audioStreamingManager.isPlaying()) {
                    this.audioStreamingManager.onPause();
                    return;
                } else {
                    AudioStreamingManager audioStreamingManager2 = this.audioStreamingManager;
                    audioStreamingManager2.onPlay(audioStreamingManager2.getCurrentAudio());
                    return;
                }
            }
            if (keyCode == 87) {
                this.audioStreamingManager.onSkipToNext();
                return;
            }
            if (keyCode == 88) {
                this.audioStreamingManager.onSkipToPrevious();
                return;
            }
            if (keyCode == 126) {
                AudioStreamingManager audioStreamingManager3 = this.audioStreamingManager;
                audioStreamingManager3.onPlay(audioStreamingManager3.getCurrentAudio());
                return;
            } else {
                if (keyCode != 127) {
                    return;
                }
                this.audioStreamingManager.onPause();
                return;
            }
        }
        this.audioStreamingManager = AudioStreamingManager.getInstance(context);
        if (intent.getAction().equals(AudioStreamingService.NOTIFY_PLAY)) {
            AudioStreamingManager audioStreamingManager4 = this.audioStreamingManager;
            audioStreamingManager4.onPlay(audioStreamingManager4.getCurrentAudio());
            return;
        }
        if (intent.getAction().equals(AudioStreamingService.NOTIFY_PAUSE) || intent.getAction().equals("android.media.AUDIO_BECOMING_NOISY")) {
            this.audioStreamingManager.onPause();
            return;
        }
        if (intent.getAction().equals(AudioStreamingService.NOTIFY_NEXT)) {
            this.audioStreamingManager.onSkipToNext();
        } else if (intent.getAction().equals(AudioStreamingService.NOTIFY_CLOSE)) {
            this.audioStreamingManager.cleanupPlayer(context, true, true);
        } else if (intent.getAction().equals(AudioStreamingService.NOTIFY_PREVIOUS)) {
            this.audioStreamingManager.onSkipToPrevious();
        }
    }
}
