package it.orsozoxi.android.orsonotes.utils.notifications;

import it.orsozoxi.android.orsonotes.OrsoNotes;
import it.orsozoxi.android.orsonotes.R;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes3.dex */
public class NotificationChannels {
    private static List<NotificationChannel> channels = new ArrayList<NotificationChannel>() { // from class: it.orsozoxi.android.orsonotes.utils.notifications.NotificationChannels.1
        {
            add(new NotificationChannel(3, OrsoNotes.getAppContext().getString(R.string.channel_backups_name), OrsoNotes.getAppContext().getString(R.string.channel_backups_description), "com.app.orsozoxi.backups"));
            add(new NotificationChannel(3, OrsoNotes.getAppContext().getString(R.string.channel_reminders_name), OrsoNotes.getAppContext().getString(R.string.channel_reminders_description), "com.app.orsozoxi.reminders"));
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
