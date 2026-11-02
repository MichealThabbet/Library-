package it.orsozoxi.android.orsonotes.services;

import android.provider.Settings;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import de.greenrobot.event.EventBus;
import it.orsozoxi.android.orsonotes.OrsoNotes;
import it.orsozoxi.android.orsonotes.async.bus.NotificationRemovedEvent;
import it.orsozoxi.android.orsonotes.db.DbHelper;
import it.orsozoxi.android.orsonotes.helpers.LogDelegate;
import it.orsozoxi.android.orsonotes.utils.date.DateUtils;

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
        String string = Settings.Secure.getString(OrsoNotes.getAppContext().getContentResolver(), "enabled_notification_listeners");
        return string != null && string.contains(NotificationListener.class.getSimpleName());
    }
}
