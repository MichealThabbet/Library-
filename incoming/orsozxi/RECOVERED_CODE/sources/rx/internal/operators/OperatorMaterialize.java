package rx.internal.operators;

import java.util.concurrent.atomic.AtomicLongFieldUpdater;
import kotlin.jvm.internal.LongCompanionObject;
import rx.Notification;
import rx.Observable;
import rx.Producer;
import rx.Subscriber;
import rx.plugins.RxJavaPlugins;

/* loaded from: classes3.dex */
public final class OperatorMaterialize<T> implements Observable.Operator<Notification<T>, T> {

    private static final class Holder {
        static final OperatorMaterialize<Object> INSTANCE = new OperatorMaterialize<>();

        private Holder() {
        }
    }

    public static <T> OperatorMaterialize<T> instance() {
        return (OperatorMaterialize<T>) Holder.INSTANCE;
    }

    private OperatorMaterialize() {
    }

    @Override // rx.functions.Func1
    public Subscriber<? super T> call(Subscriber<? super Notification<T>> subscriber) {
        final ParentSubscriber parentSubscriber = new ParentSubscriber(subscriber);
        subscriber.add(parentSubscriber);
        subscriber.setProducer(new Producer() { // from class: rx.internal.operators.OperatorMaterialize.1
            @Override // rx.Producer
            public void request(long j) {
                if (j > 0) {
                    parentSubscriber.requestMore(j);
                }
            }
        });
        return parentSubscriber;
    }

    private static class ParentSubscriber<T> extends Subscriber<T> {
        private static final AtomicLongFieldUpdater<ParentSubscriber> REQUESTED = AtomicLongFieldUpdater.newUpdater(ParentSubscriber.class, "requested");
        private final Subscriber<? super Notification<T>> child;
        private volatile long requested;
        private volatile Notification<T> terminalNotification;
        private boolean busy = false;
        private boolean missed = false;

        ParentSubscriber(Subscriber<? super Notification<T>> subscriber) {
            this.child = subscriber;
        }

        @Override // rx.Subscriber
        public void onStart() {
            request(0L);
        }

        void requestMore(long j) {
            BackpressureUtils.getAndAddRequest(REQUESTED, this, j);
            request(j);
            drain();
        }

        @Override // rx.Observer
        public void onCompleted() {
            this.terminalNotification = Notification.createOnCompleted();
            drain();
        }

        @Override // rx.Observer
        public void onError(Throwable th) {
            this.terminalNotification = Notification.createOnError(th);
            RxJavaPlugins.getInstance().getErrorHandler().handleError(th);
            drain();
        }

        @Override // rx.Observer
        public void onNext(T t) {
            this.child.onNext(Notification.createOnNext(t));
            decrementRequested();
        }

        private void decrementRequested() {
            long j;
            do {
                j = this.requested;
                if (j == LongCompanionObject.MAX_VALUE) {
                    return;
                }
            } while (!REQUESTED.compareAndSet(this, j, j - 1));
        }

        private void drain() {
            synchronized (this) {
                if (this.busy) {
                    this.missed = true;
                    return;
                }
                while (!this.child.isUnsubscribed()) {
                    Notification<T> notification = this.terminalNotification;
                    if (notification != null && this.requested > 0) {
                        this.terminalNotification = null;
                        this.child.onNext(notification);
                        if (this.child.isUnsubscribed()) {
                            return;
                        }
                        this.child.onCompleted();
                        return;
                    }
                    synchronized (this) {
                        if (!this.missed) {
                            this.busy = false;
                            return;
                        }
                    }
                }
            }
        }
    }
}
