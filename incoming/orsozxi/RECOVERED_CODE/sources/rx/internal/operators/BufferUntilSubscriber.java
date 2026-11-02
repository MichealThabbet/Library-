package rx.internal.operators;

import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;
import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.functions.Action0;
import rx.subjects.Subject;
import rx.subscriptions.Subscriptions;

/* loaded from: classes3.dex */
public final class BufferUntilSubscriber<T> extends Subject<T, T> {
    private static final Observer EMPTY_OBSERVER = new Observer() { // from class: rx.internal.operators.BufferUntilSubscriber.1
        @Override // rx.Observer
        public void onCompleted() {
        }

        @Override // rx.Observer
        public void onError(Throwable th) {
        }

        @Override // rx.Observer
        public void onNext(Object obj) {
        }
    };
    private boolean forward;
    final State<T> state;

    public static <T> BufferUntilSubscriber<T> create() {
        return new BufferUntilSubscriber<>(new State());
    }

    static final class State<T> {
        static final AtomicReferenceFieldUpdater<State, Observer> OBSERVER_UPDATER = AtomicReferenceFieldUpdater.newUpdater(State.class, Observer.class, "observerRef");
        volatile Observer<? super T> observerRef = null;
        Object guard = new Object();
        boolean emitting = false;
        final ConcurrentLinkedQueue<Object> buffer = new ConcurrentLinkedQueue<>();
        final NotificationLite<T> nl = NotificationLite.instance();

        State() {
        }

        boolean casObserverRef(Observer<? super T> observer, Observer<? super T> observer2) {
            return OBSERVER_UPDATER.compareAndSet(this, observer, observer2);
        }
    }

    static final class OnSubscribeAction<T> implements Observable.OnSubscribe<T> {
        final State<T> state;

        public OnSubscribeAction(State<T> state) {
            this.state = state;
        }

        @Override // rx.functions.Action1
        public void call(Subscriber<? super T> subscriber) {
            boolean z;
            if (this.state.casObserverRef(null, subscriber)) {
                subscriber.add(Subscriptions.create(new Action0() { // from class: rx.internal.operators.BufferUntilSubscriber.OnSubscribeAction.1
                    @Override // rx.functions.Action0
                    public void call() {
                        OnSubscribeAction.this.state.observerRef = BufferUntilSubscriber.EMPTY_OBSERVER;
                    }
                }));
                synchronized (this.state.guard) {
                    z = true;
                    if (this.state.emitting) {
                        z = false;
                    } else {
                        this.state.emitting = true;
                    }
                }
                if (!z) {
                    return;
                }
                NotificationLite notificationLiteInstance = NotificationLite.instance();
                while (true) {
                    Object objPoll = this.state.buffer.poll();
                    if (objPoll != null) {
                        notificationLiteInstance.accept(this.state.observerRef, objPoll);
                    } else {
                        synchronized (this.state.guard) {
                            if (this.state.buffer.isEmpty()) {
                                this.state.emitting = false;
                                return;
                            }
                        }
                    }
                }
            } else {
                subscriber.onError(new IllegalStateException("Only one subscriber allowed!"));
            }
        }
    }

    private BufferUntilSubscriber(State<T> state) {
        super(new OnSubscribeAction(state));
        this.forward = false;
        this.state = state;
    }

    private void emit(Object obj) {
        synchronized (this.state.guard) {
            this.state.buffer.add(obj);
            if (this.state.observerRef != null && !this.state.emitting) {
                this.forward = true;
                this.state.emitting = true;
            }
        }
        if (!this.forward) {
            return;
        }
        while (true) {
            Object objPoll = this.state.buffer.poll();
            if (objPoll == null) {
                return;
            } else {
                this.state.nl.accept(this.state.observerRef, objPoll);
            }
        }
    }

    @Override // rx.Observer
    public void onCompleted() {
        if (this.forward) {
            this.state.observerRef.onCompleted();
        } else {
            emit(this.state.nl.completed());
        }
    }

    @Override // rx.Observer
    public void onError(Throwable th) {
        if (this.forward) {
            this.state.observerRef.onError(th);
        } else {
            emit(this.state.nl.error(th));
        }
    }

    @Override // rx.Observer
    public void onNext(T t) {
        if (this.forward) {
            this.state.observerRef.onNext(t);
        } else {
            emit(this.state.nl.next(t));
        }
    }

    @Override // rx.subjects.Subject
    public boolean hasObservers() {
        boolean z;
        synchronized (this.state.guard) {
            z = this.state.observerRef != null;
        }
        return z;
    }
}
