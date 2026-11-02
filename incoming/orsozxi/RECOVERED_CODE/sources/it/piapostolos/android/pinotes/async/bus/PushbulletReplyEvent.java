package it.piapostolos.android.pinotes.async.bus;

import it.piapostolos.android.pinotes.helpers.LogDelegate;

/* loaded from: classes3.dex */
public class PushbulletReplyEvent {
    public String message;

    public PushbulletReplyEvent(String str) {
        LogDelegate.d(getClass().getName());
        this.message = str;
    }
}
