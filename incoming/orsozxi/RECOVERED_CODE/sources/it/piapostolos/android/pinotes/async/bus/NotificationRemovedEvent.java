package it.piapostolos.android.pinotes.async.bus;

import android.service.notification.StatusBarNotification;
import it.piapostolos.android.pinotes.helpers.LogDelegate;

/* loaded from: classes3.dex */
public class NotificationRemovedEvent {
    public StatusBarNotification statusBarNotification;

    public NotificationRemovedEvent(StatusBarNotification statusBarNotification) {
        LogDelegate.d(getClass().getName());
        this.statusBarNotification = statusBarNotification;
    }
}
