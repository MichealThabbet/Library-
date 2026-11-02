package rx.subscriptions;

import com.facebook.internal.ServerProtocol;
import com.ibm.icu.text.DateFormat;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;
import rx.Subscription;

/* loaded from: classes3.dex */
public final class RefCountSubscription implements Subscription {
    static final State EMPTY_STATE = new State(false, 0);
    static final AtomicReferenceFieldUpdater<RefCountSubscription, State> STATE_UPDATER = AtomicReferenceFieldUpdater.newUpdater(RefCountSubscription.class, State.class, ServerProtocol.DIALOG_PARAM_STATE);
    private final Subscription actual;
    volatile State state = EMPTY_STATE;

    private static final class State {
        final int children;
        final boolean isUnsubscribed;

        State(boolean z, int i) {
            this.isUnsubscribed = z;
            this.children = i;
        }

        State addChild() {
            return new State(this.isUnsubscribed, this.children + 1);
        }

        State removeChild() {
            return new State(this.isUnsubscribed, this.children - 1);
        }

        State unsubscribe() {
            return new State(true, this.children);
        }
    }

    public RefCountSubscription(Subscription subscription) {
        if (subscription == null) {
            throw new IllegalArgumentException(DateFormat.SECOND);
        }
        this.actual = subscription;
    }

    public Subscription get() {
        State state;
        do {
            state = this.state;
            if (state.isUnsubscribed) {
                return Subscriptions.unsubscribed();
            }
        } while (!STATE_UPDATER.compareAndSet(this, state, state.addChild()));
        return new InnerSubscription(this);
    }

    @Override // rx.Subscription
    public boolean isUnsubscribed() {
        return this.state.isUnsubscribed;
    }

    @Override // rx.Subscription
    public void unsubscribe() {
        State state;
        State stateUnsubscribe;
        do {
            state = this.state;
            if (state.isUnsubscribed) {
                return;
            } else {
                stateUnsubscribe = state.unsubscribe();
            }
        } while (!STATE_UPDATER.compareAndSet(this, state, stateUnsubscribe));
        unsubscribeActualIfApplicable(stateUnsubscribe);
    }

    private void unsubscribeActualIfApplicable(State state) {
        if (state.isUnsubscribed && state.children == 0) {
            this.actual.unsubscribe();
        }
    }

    void unsubscribeAChild() {
        State state;
        State stateRemoveChild;
        do {
            state = this.state;
            stateRemoveChild = state.removeChild();
        } while (!STATE_UPDATER.compareAndSet(this, state, stateRemoveChild));
        unsubscribeActualIfApplicable(stateRemoveChild);
    }

    private static final class InnerSubscription implements Subscription {
        static final AtomicIntegerFieldUpdater<InnerSubscription> INNER_DONE_UPDATER = AtomicIntegerFieldUpdater.newUpdater(InnerSubscription.class, "innerDone");
        volatile int innerDone;
        final RefCountSubscription parent;

        public InnerSubscription(RefCountSubscription refCountSubscription) {
            this.parent = refCountSubscription;
        }

        @Override // rx.Subscription
        public void unsubscribe() {
            if (INNER_DONE_UPDATER.compareAndSet(this, 0, 1)) {
                this.parent.unsubscribeAChild();
            }
        }

        @Override // rx.Subscription
        public boolean isUnsubscribed() {
            return this.innerDone != 0;
        }
    }
}
