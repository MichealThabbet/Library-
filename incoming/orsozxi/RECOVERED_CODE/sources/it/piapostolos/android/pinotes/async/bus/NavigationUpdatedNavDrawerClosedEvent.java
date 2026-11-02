package it.piapostolos.android.pinotes.async.bus;

import it.piapostolos.android.pinotes.helpers.LogDelegate;

/* loaded from: classes3.dex */
public class NavigationUpdatedNavDrawerClosedEvent {
    public final Object navigationItem;

    public NavigationUpdatedNavDrawerClosedEvent(Object obj) {
        LogDelegate.d(getClass().getName());
        this.navigationItem = obj;
    }
}
