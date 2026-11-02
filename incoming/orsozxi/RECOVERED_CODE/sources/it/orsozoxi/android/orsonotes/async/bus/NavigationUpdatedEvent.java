package it.orsozoxi.android.orsonotes.async.bus;

import it.orsozoxi.android.orsonotes.helpers.LogDelegate;

/* loaded from: classes3.dex */
public class NavigationUpdatedEvent {
    public final Object navigationItem;

    public NavigationUpdatedEvent(Object obj) {
        LogDelegate.d(getClass().getName());
        this.navigationItem = obj;
    }
}
