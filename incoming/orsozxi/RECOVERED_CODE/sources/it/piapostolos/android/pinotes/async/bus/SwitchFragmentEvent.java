package it.piapostolos.android.pinotes.async.bus;

import it.piapostolos.android.pinotes.helpers.LogDelegate;

/* loaded from: classes3.dex */
public class SwitchFragmentEvent {
    public Direction direction;

    public enum Direction {
        CHILDREN,
        PARENT
    }

    public SwitchFragmentEvent(Direction direction) {
        LogDelegate.d(getClass().getName());
        this.direction = direction;
    }
}
