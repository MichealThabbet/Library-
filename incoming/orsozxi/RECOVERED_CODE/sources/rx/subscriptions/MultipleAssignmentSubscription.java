package rx.subscriptions;

import com.facebook.internal.ServerProtocol;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;
import rx.Subscription;

/* loaded from: classes3.dex */
public final class MultipleAssignmentSubscription implements Subscription {
    static final AtomicReferenceFieldUpdater<MultipleAssignmentSubscription, State> STATE_UPDATER = AtomicReferenceFieldUpdater.newUpdater(MultipleAssignmentSubscription.class, State.class, ServerProtocol.DIALOG_PARAM_STATE);
    volatile State state = new State(false, Subscriptions.empty());

    private static final class State {
        final boolean isUnsubscribed;
        final Subscription subscription;

        State(boolean z, Subscription subscription) {
            this.isUnsubscribed = z;
            this.subscription = subscription;
        }

        State unsubscribe() {
            return new State(true, this.subscription);
        }

        State set(Subscription subscription) {
            return new State(this.isUnsubscribed, subscription);
        }
    }

    @Override // rx.Subscription
    public boolean isUnsubscribed() {
        return this.state.isUnsubscribed;
    }

    @Override // rx.Subscription
    public void unsubscribe() {
        State state;
        do {
            state = this.state;
            if (state.isUnsubscribed) {
                return;
            }
        } while (!STATE_UPDATER.compareAndSet(this, state, state.unsubscribe()));
        state.subscription.unsubscribe();
    }

    public void set(Subscription subscription) {
        State state;
        if (subscription == null) {
            throw new IllegalArgumentException("Subscription can not be null");
        }
        do {
            state = this.state;
            if (state.isUnsubscribed) {
                subscription.unsubscribe();
                return;
            }
        } while (!STATE_UPDATER.compareAndSet(this, state, state.set(subscription)));
    }

    public Subscription get() {
        return this.state.subscription;
    }
}
