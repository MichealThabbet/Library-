package it.piapostolos.android.pinotes.utils.notifications;

import it.piapostolos.android.pinotes.PiNotes;
import it.piapostolos.android.pinotes.R;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes3.dex */
public class NotificationChannels {
    private static List<NotificationChannel> channels = new ArrayList<NotificationChannel>() { // from class: it.piapostolos.android.pinotes.utils.notifications.NotificationChannels.1
        {
            add(new NotificationChannel(3, PiNotes.getAppContext().getString(R.string.channel_backups_name), PiNotes.getAppContext().getString(R.string.channel_backups_description), "com.app.piapostolos.backups"));
            add(new NotificationChannel(3, PiNotes.getAppContext().getString(R.string.channel_reminders_name), PiNotes.getAppContext().getString(R.string.channel_reminders_description), "com.app.piapostolos.reminders"));
        }
    };

    public enum NotificationChannelNames {
        Backups,
        Reminders
    }

    public static List<NotificationChannel> getChannels() {
        return channels;
    }
}
