package it.piapostolos.android.pinotes.services;

import android.provider.Settings;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import de.greenrobot.event.EventBus;
import it.piapostolos.android.pinotes.PiNotes;
import it.piapostolos.android.pinotes.async.bus.NotificationRemovedEvent;
import it.piapostolos.android.pinotes.db.DbHelper;
import it.piapostolos.android.pinotes.helpers.LogDelegate;
import it.piapostolos.android.pinotes.utils.date.DateUtils;

/* loaded from: classes3.dex */
public class NotificationListener extends NotificationListenerService {
    @Override // android.app.Service
    public void onCreate() {
        super.onCreate();
        EventBus.getDefault().register(this);
    }

    @Override // android.service.notification.NotificationListenerService, android.app.Service
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override // android.service.notification.NotificationListenerService
    public void onNotificationPosted(StatusBarNotification statusBarNotification) {
        LogDelegate.d("Notification posted for note: " + statusBarNotification.getId());
    }

    @Override // android.service.notification.NotificationListenerService
    public void onNotificationRemoved(StatusBarNotification statusBarNotification) {
        if (statusBarNotification.getPackageName().equals(getPackageName())) {
            EventBus.getDefault().post(new NotificationRemovedEvent(statusBarNotification));
            LogDelegate.d("Notification removed for note: " + statusBarNotification.getId());
        }
    }

    public void onEventAsync(NotificationRemovedEvent notificationRemovedEvent) throws NumberFormatException {
        long j = Long.parseLong(notificationRemovedEvent.statusBarNotification.getTag());
        if (DateUtils.isFuture(DbHelper.getInstance().getNote(j).getAlarm())) {
            return;
        }
        DbHelper.getInstance().setReminderFired(j, true);
    }

    public static boolean isRunning() {
        String string = Settings.Secure.getString(PiNotes.getAppContext().getContentResolver(), "enabled_notification_listeners");
        return string != null && string.contains(NotificationListener.class.getSimpleName());
    }
}
