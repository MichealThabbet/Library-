package rx.observables;

import java.util.Arrays;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import kotlin.jvm.internal.LongCompanionObject;
import rx.Observable;
import rx.Producer;
import rx.Subscriber;
import rx.Subscription;
import rx.exceptions.CompositeException;
import rx.functions.Action1;
import rx.functions.Actions;
import rx.functions.Func1;
import rx.internal.operators.BackpressureUtils;

/* loaded from: classes3.dex */
public abstract class AbstractOnSubscribe<T, S> implements Observable.OnSubscribe<T> {
    private static final Func1<Object, Object> NULL_FUNC1 = new Func1<Object, Object>() { // from class: rx.observables.AbstractOnSubscribe.1
        @Override // rx.functions.Func1
        public Object call(Object obj) {
            return null;
        }
    };

    protected abstract void next(SubscriptionState<T, S> subscriptionState);

    protected S onSubscribe(Subscriber<? super T> subscriber) {
        return null;
    }

    protected void onTerminated(S s) {
    }

    @Override // rx.functions.Action1
    public final void call(Subscriber<? super T> subscriber) {
        SubscriptionState subscriptionState = new SubscriptionState(subscriber, onSubscribe(subscriber));
        subscriber.add(new SubscriptionCompleter(subscriptionState));
        subscriber.setProducer(new SubscriptionProducer(subscriptionState));
    }

    public final Observable<T> toObservable() {
        return Observable.create(this);
    }

    public static <T, S> AbstractOnSubscribe<T, S> create(Action1<SubscriptionState<T, S>> action1) {
        return create(action1, NULL_FUNC1, Actions.empty());
    }

    public static <T, S> AbstractOnSubscribe<T, S> create(Action1<SubscriptionState<T, S>> action1, Func1<? super Subscriber<? super T>, ? extends S> func1) {
        return create(action1, func1, Actions.empty());
    }

    public static <T, S> AbstractOnSubscribe<T, S> create(Action1<SubscriptionState<T, S>> action1, Func1<? super Subscriber<? super T>, ? extends S> func1, Action1<? super S> action12) {
        return new LambdaOnSubscribe(action1, func1, action12);
    }

    private static final class LambdaOnSubscribe<T, S> extends AbstractOnSubscribe<T, S> {
        final Action1<SubscriptionState<T, S>> next;
        final Func1<? super Subscriber<? super T>, ? extends S> onSubscribe;
        final Action1<? super S> onTerminated;

        @Override // rx.observables.AbstractOnSubscribe, rx.functions.Action1
        public /* bridge */ /* synthetic */ void call(Object obj) {
            super.call((Subscriber) obj);
        }

        private LambdaOnSubscribe(Action1<SubscriptionState<T, S>> action1, Func1<? super Subscriber<? super T>, ? extends S> func1, Action1<? super S> action12) {
            this.next = action1;
            this.onSubscribe = func1;
            this.onTerminated = action12;
        }

        @Override // rx.observables.AbstractOnSubscribe
        protected S onSubscribe(Subscriber<? super T> subscriber) {
            return this.onSubscribe.call(subscriber);
        }

        @Override // rx.observables.AbstractOnSubscribe
        protected void onTerminated(S s) {
            this.onTerminated.call(s);
        }

        @Override // rx.observables.AbstractOnSubscribe
        protected void next(SubscriptionState<T, S> subscriptionState) {
            this.next.call(subscriptionState);
        }
    }

    private static final class SubscriptionCompleter<T, S> extends AtomicBoolean implements Subscription {
        private static final long serialVersionUID = 7993888274897325004L;
        private final SubscriptionState<T, S> state;

        private SubscriptionCompleter(SubscriptionState<T, S> subscriptionState) {
            this.state = subscriptionState;
        }

        @Override // rx.Subscription
        public boolean isUnsubscribed() {
            return get();
        }

        @Override // rx.Subscription
        public void unsubscribe() {
            if (compareAndSet(false, true)) {
                this.state.free();
            }
        }
    }

    private static final class SubscriptionProducer<T, S> implements Producer {
        final SubscriptionState<T, S> state;

        private SubscriptionProducer(SubscriptionState<T, S> subscriptionState) {
            this.state = subscriptionState;
        }

        @Override // rx.Producer
        public void request(long j) {
            if (j <= 0 || BackpressureUtils.getAndAddRequest(((SubscriptionState) this.state).requestCount, j) != 0) {
                return;
            }
            if (j == LongCompanionObject.MAX_VALUE) {
                while (!((SubscriptionState) this.state).subscriber.isUnsubscribed() && doNext()) {
                }
            } else {
                if (((SubscriptionState) this.state).subscriber.isUnsubscribed()) {
                    return;
                }
                while (doNext() && ((SubscriptionState) this.state).requestCount.decrementAndGet() > 0 && !((SubscriptionState) this.state).subscriber.isUnsubscribed()) {
                }
            }
        }

        protected boolean doNext() {
            int iPhase;
            if (!this.state.use()) {
                return false;
            }
            try {
                iPhase = this.state.phase();
                ((SubscriptionState) this.state).parent.next(this.state);
            } finally {
                try {
                    return false;
                } finally {
                }
            }
            if (!this.state.verify()) {
                throw new IllegalStateException("No event produced or stop called @ Phase: " + iPhase + " -> " + this.state.phase() + ", Calls: " + this.state.calls());
            }
            if (!this.state.accept() && !this.state.stopRequested()) {
                SubscriptionState.access$708(this.state);
                this.state.free();
                return true;
            }
            this.state.terminate();
            return false;
        }
    }

    public static final class SubscriptionState<T, S> {
        private long calls;
        private boolean hasCompleted;
        private boolean hasOnNext;
        private final AtomicInteger inUse;
        private final AbstractOnSubscribe<T, S> parent;
        private int phase;
        private final AtomicLong requestCount;
        private final S state;
        private boolean stopRequested;
        private final Subscriber<? super T> subscriber;
        private Throwable theException;
        private T theValue;

        static /* synthetic */ long access$708(SubscriptionState subscriptionState) {
            long j = subscriptionState.calls;
            subscriptionState.calls = 1 + j;
            return j;
        }

        private SubscriptionState(AbstractOnSubscribe<T, S> abstractOnSubscribe, Subscriber<? super T> subscriber, S s) {
            this.parent = abstractOnSubscribe;
            this.subscriber = subscriber;
            this.state = s;
            this.requestCount = new AtomicLong();
            this.inUse = new AtomicInteger(1);
        }

        public S state() {
            return this.state;
        }

        public int phase() {
            return this.phase;
        }

        public void phase(int i) {
            this.phase = i;
        }

        public void advancePhase() {
            advancePhaseBy(1);
        }

        public void advancePhaseBy(int i) {
            this.phase += i;
        }

        public long calls() {
            return this.calls;
        }

        public void onNext(T t) {
            if (this.hasOnNext) {
                throw new IllegalStateException("onNext not consumed yet!");
            }
            if (this.hasCompleted) {
                throw new IllegalStateException("Already terminated", this.theException);
            }
            this.theValue = t;
            this.hasOnNext = true;
        }

        public void onError(Throwable th) {
            if (th == null) {
                throw new NullPointerException("e != null required");
            }
            if (this.hasCompleted) {
                throw new IllegalStateException("Already terminated", this.theException);
            }
            this.theException = th;
            this.hasCompleted = true;
        }

        public void onCompleted() {
            if (this.hasCompleted) {
                throw new IllegalStateException("Already terminated", this.theException);
            }
            this.hasCompleted = true;
        }

        public void stop() {
            this.stopRequested = true;
        }

        protected boolean accept() {
            if (this.hasOnNext) {
                T t = this.theValue;
                this.theValue = null;
                this.hasOnNext = false;
                try {
                    this.subscriber.onNext(t);
                } catch (Throwable th) {
                    this.hasCompleted = true;
                    Throwable th2 = this.theException;
                    this.theException = null;
                    if (th2 == null) {
                        this.subscriber.onError(th);
                    } else {
                        this.subscriber.onError(new CompositeException(Arrays.asList(th, th2)));
                    }
                    return true;
                }
            }
            if (!this.hasCompleted) {
                return false;
            }
            Throwable th3 = this.theException;
            this.theException = null;
            if (th3 != null) {
                this.subscriber.onError(th3);
            } else {
                this.subscriber.onCompleted();
            }
            return true;
        }

        protected boolean verify() {
            return this.hasOnNext || this.hasCompleted || this.stopRequested;
        }

        protected boolean stopRequested() {
            return this.stopRequested;
        }

        protected boolean use() {
            int i = this.inUse.get();
            if (i == 0) {
                return false;
            }
            if (i == 1 && this.inUse.compareAndSet(1, 2)) {
                return true;
            }
            throw new IllegalStateException("This is not reentrant nor threadsafe!");
        }

        protected void free() {
            if (this.inUse.get() > 0 && this.inUse.decrementAndGet() == 0) {
                this.parent.onTerminated(this.state);
            }
        }

        protected void terminate() {
            int i;
            do {
                i = this.inUse.get();
                if (i <= 0) {
                    return;
                }
            } while (!this.inUse.compareAndSet(i, 0));
            this.parent.onTerminated(this.state);
        }
    }
}
