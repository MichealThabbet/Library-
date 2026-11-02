package rx.subscriptions;

import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;
import rx.Subscription;
import rx.functions.Action0;

/* loaded from: classes3.dex */
public final class BooleanSubscription implements Subscription {
    static final AtomicIntegerFieldUpdater<BooleanSubscription> UNSUBSCRIBED_UPDATER = AtomicIntegerFieldUpdater.newUpdater(BooleanSubscription.class, "unsubscribed");
    private final Action0 action;
    volatile int unsubscribed;

    public BooleanSubscription() {
        this.action = null;
    }

    private BooleanSubscription(Action0 action0) {
        this.action = action0;
    }

    public static BooleanSubscription create() {
        return new BooleanSubscription();
    }

    public static BooleanSubscription create(Action0 action0) {
        return new BooleanSubscription(action0);
    }

    @Override // rx.Subscription
    public boolean isUnsubscribed() {
        return this.unsubscribed != 0;
    }

    @Override // rx.Subscription
    public final void unsubscribe() {
        Action0 action0;
        if (!UNSUBSCRIBED_UPDATER.compareAndSet(this, 0, 1) || (action0 = this.action) == null) {
            return;
        }
        action0.call();
    }
}
