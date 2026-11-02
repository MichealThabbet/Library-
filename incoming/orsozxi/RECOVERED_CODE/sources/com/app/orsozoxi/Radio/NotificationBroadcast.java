package com.app.orsozoxi.Radio;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.view.KeyEvent;
import com.app.orsozoxi.NewRadioActivity;

/* loaded from: classes.dex */
public class NotificationBroadcast extends BroadcastReceiver {
    @Override // android.content.BroadcastReceiver
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals("android.intent.action.MEDIA_BUTTON")) {
            KeyEvent keyEvent = (KeyEvent) intent.getExtras().get("android.intent.extra.KEY_EVENT");
            if (keyEvent.getAction() != 0) {
                return;
            }
            int keyCode = keyEvent.getKeyCode();
            if (keyCode == 79 || keyCode == 85) {
                if (!PlayerConstants.SONG_PAUSED) {
                    Controls.pauseControl(context);
                    return;
                } else {
                    Controls.playControl(context);
                    return;
                }
            }
            return;
        }
        if (intent.getAction().equals(SongService.NOTIFY_PLAY)) {
            Controls.playControl(context);
            return;
        }
        if (intent.getAction().equals(SongService.NOTIFY_PAUSE)) {
            Controls.pauseControl(context);
            return;
        }
        if (intent.getAction().equals(SongService.NOTIFY_DELETE)) {
            context.startService(new Intent(context, (Class<?>) SongService.class).setAction("STOP_ACTION"));
        } else if (intent.getAction().equals(SongService.NOTIFY_OPEN_APP)) {
            Intent intent2 = new Intent(context, (Class<?>) NewRadioActivity.class);
            intent2.setFlags(335544320);
            context.startActivity(intent2);
        }
    }

    public String ComponentName() {
        return getClass().getName();
    }
}
