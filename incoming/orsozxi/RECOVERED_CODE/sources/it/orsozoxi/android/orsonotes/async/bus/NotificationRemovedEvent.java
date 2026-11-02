package it.orsozoxi.android.orsonotes.async.bus;

import android.service.notification.StatusBarNotification;
import it.orsozoxi.android.orsonotes.helpers.LogDelegate;

/* loaded from: classes3.dex */
public class NotificationRemovedEvent {
    public StatusBarNotification statusBarNotification;

    public NotificationRemovedEvent(StatusBarNotification statusBarNotification) {
        LogDelegate.d(getClass().getName());
        this.statusBarNotification = statusBarNotification;
    }
}
