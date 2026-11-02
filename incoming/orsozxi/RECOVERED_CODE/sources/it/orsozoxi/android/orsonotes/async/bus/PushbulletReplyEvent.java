package it.orsozoxi.android.orsonotes.async.bus;

import it.orsozoxi.android.orsonotes.helpers.LogDelegate;

/* loaded from: classes3.dex */
public class PushbulletReplyEvent {
    public String message;

    public PushbulletReplyEvent(String str) {
        LogDelegate.d(getClass().getName());
        this.message = str;
    }
}
