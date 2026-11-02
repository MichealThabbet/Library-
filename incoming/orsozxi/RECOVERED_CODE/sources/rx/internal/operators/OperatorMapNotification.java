package rx.internal.operators;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicLong;
import kotlin.jvm.internal.LongCompanionObject;
import rx.Observable;
import rx.Producer;
import rx.Subscriber;
import rx.Subscription;
import rx.exceptions.MissingBackpressureException;
import rx.exceptions.OnErrorThrowable;
import rx.functions.Func0;
import rx.functions.Func1;
import rx.internal.util.unsafe.SpscArrayQueue;
import rx.internal.util.unsafe.UnsafeAccess;

/* loaded from: classes3.dex */
public final class OperatorMapNotification<T, R> implements Observable.Operator<R, T> {
    private final Func0<? extends R> onCompleted;
    private final Func1<? super Throwable, ? extends R> onError;
    private final Func1<? super T, ? extends R> onNext;

    public OperatorMapNotification(Func1<? super T, ? extends R> func1, Func1<? super Throwable, ? extends R> func12, Func0<? extends R> func0) {
        this.onNext = func1;
        this.onError = func12;
        this.onCompleted = func0;
    }

    @Override // rx.functions.Func1
    public Subscriber<? super T> call(final Subscriber<? super R> subscriber) {
        Subscriber<T> subscriber2 = new Subscriber<T>() { // from class: rx.internal.operators.OperatorMapNotification.1
            SingleEmitter<R> emitter;

            @Override // rx.Subscriber
            public void setProducer(Producer producer) {
                SingleEmitter<R> singleEmitter = new SingleEmitter<>(subscriber, producer, this);
                this.emitter = singleEmitter;
                subscriber.setProducer(singleEmitter);
            }

            /* JADX WARN: Multi-variable type inference failed */
            @Override // rx.Observer
            public void onCompleted() {
                try {
                    this.emitter.offerAndComplete(OperatorMapNotification.this.onCompleted.call());
                } catch (Throwable th) {
                    subscriber.onError(th);
                }
            }

            /* JADX WARN: Multi-variable type inference failed */
            @Override // rx.Observer
            public void onError(Throwable th) {
                try {
                    this.emitter.offerAndComplete(OperatorMapNotification.this.onError.call(th));
                } catch (Throwable unused) {
                    subscriber.onError(th);
                }
            }

            /* JADX WARN: Multi-variable type inference failed */
            @Override // rx.Observer
            public void onNext(T t) {
                try {
                    this.emitter.offer(OperatorMapNotification.this.onNext.call(t));
                } catch (Throwable th) {
                    subscriber.onError(OnErrorThrowable.addValueAsLastCause(th, t));
                }
            }
        };
        subscriber.add(subscriber2);
        return subscriber2;
    }

    static final class SingleEmitter<T> extends AtomicLong implements Producer, Subscription {
        private static final long serialVersionUID = -249869671366010660L;
        final Subscription cancel;
        final Subscriber<? super T> child;
        volatile boolean complete;
        boolean emitting;
        boolean missed;
        final NotificationLite<T> nl;
        final Producer producer;
        final Queue<Object> queue;

        public SingleEmitter(Subscriber<? super T> subscriber, Producer producer, Subscription subscription) {
            this.child = subscriber;
            this.producer = producer;
            this.cancel = subscription;
            this.queue = UnsafeAccess.isUnsafeAvailable() ? new SpscArrayQueue<>(2) : new ConcurrentLinkedQueue<>();
            this.nl = NotificationLite.instance();
        }

        @Override // rx.Producer
        public void request(long j) throws Throwable {
            long j2;
            long j3;
            do {
                j2 = get();
                if (j2 < 0) {
                    return;
                }
                j3 = j2 + j;
                if (j3 < 0) {
                    j3 = LongCompanionObject.MAX_VALUE;
                }
            } while (!compareAndSet(j2, j3));
            this.producer.request(j);
            drain();
        }

        void produced(long j) {
            long j2;
            long j3;
            do {
                j2 = get();
                if (j2 < 0) {
                    return;
                }
                j3 = j2 - j;
                if (j3 < 0) {
                    throw new IllegalStateException("More produced (" + j + ") than requested (" + j2 + ")");
                }
            } while (!compareAndSet(j2, j3));
        }

        public void offer(T t) throws Throwable {
            if (!this.queue.offer(t)) {
                this.child.onError(new MissingBackpressureException());
                unsubscribe();
            } else {
                drain();
            }
        }

        public void offerAndComplete(T t) throws Throwable {
            if (!this.queue.offer(t)) {
                this.child.onError(new MissingBackpressureException());
                unsubscribe();
            } else {
                this.complete = true;
                drain();
            }
        }

        void drain() throws Throwable {
            boolean z;
            synchronized (this) {
                boolean z2 = true;
                if (this.emitting) {
                    this.missed = true;
                    return;
                }
                this.emitting = true;
                this.missed = false;
                while (true) {
                    try {
                        long j = get();
                        boolean z3 = this.complete;
                        boolean zIsEmpty = this.queue.isEmpty();
                        if (z3 && zIsEmpty) {
                            this.child.onCompleted();
                            return;
                        }
                        if (j > 0) {
                            Object objPoll = this.queue.poll();
                            if (objPoll != null) {
                                this.child.onNext(this.nl.getValue(objPoll));
                                produced(1L);
                            } else if (z3) {
                                this.child.onCompleted();
                                return;
                            }
                        }
                        try {
                            synchronized (this) {
                                try {
                                    if (!this.missed) {
                                        this.emitting = false;
                                        return;
                                    }
                                    this.missed = false;
                                } catch (Throwable th) {
                                    th = th;
                                    z2 = false;
                                }
                            }
                        } catch (Throwable th2) {
                            th = th2;
                        }
                        try {
                            throw th;
                        } catch (Throwable th3) {
                            z = z2;
                            th = th3;
                            if (!z) {
                                synchronized (this) {
                                    this.emitting = false;
                                }
                            }
                            throw th;
                        }
                    } catch (Throwable th4) {
                        th = th4;
                        z = false;
                    }
                }
            }
        }

        @Override // rx.Subscription
        public boolean isUnsubscribed() {
            return get() < 0;
        }

        @Override // rx.Subscription
        public void unsubscribe() {
            if (get() == Long.MIN_VALUE || getAndSet(Long.MIN_VALUE) == Long.MIN_VALUE) {
                return;
            }
            this.cancel.unsubscribe();
        }
    }
}
