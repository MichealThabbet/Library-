package it.orsozoxi.android.orsonotes.async.bus;

import it.orsozoxi.android.orsonotes.helpers.LogDelegate;

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
