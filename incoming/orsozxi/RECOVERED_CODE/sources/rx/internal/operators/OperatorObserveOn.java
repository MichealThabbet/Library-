package rx.internal.operators;

import java.util.Queue;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;
import java.util.concurrent.atomic.AtomicLongFieldUpdater;
import kotlin.jvm.internal.LongCompanionObject;
import rx.Observable;
import rx.Producer;
import rx.Scheduler;
import rx.Subscriber;
import rx.Subscription;
import rx.exceptions.MissingBackpressureException;
import rx.functions.Action0;
import rx.internal.util.RxRingBuffer;
import rx.internal.util.SynchronizedQueue;
import rx.internal.util.unsafe.SpscArrayQueue;
import rx.internal.util.unsafe.UnsafeAccess;
import rx.schedulers.ImmediateScheduler;
import rx.schedulers.TrampolineScheduler;

/* loaded from: classes3.dex */
public final class OperatorObserveOn<T> implements Observable.Operator<T, T> {
    private final Scheduler scheduler;

    public OperatorObserveOn(Scheduler scheduler) {
        this.scheduler = scheduler;
    }

    @Override // rx.functions.Func1
    public Subscriber<? super T> call(Subscriber<? super T> subscriber) {
        Scheduler scheduler = this.scheduler;
        if ((scheduler instanceof ImmediateScheduler) || (scheduler instanceof TrampolineScheduler)) {
            return subscriber;
        }
        ObserveOnSubscriber observeOnSubscriber = new ObserveOnSubscriber(scheduler, subscriber);
        observeOnSubscriber.init();
        return observeOnSubscriber;
    }

    private static final class ObserveOnSubscriber<T> extends Subscriber<T> {
        final Subscriber<? super T> child;
        volatile long counter;
        volatile Throwable error;
        final Queue<Object> queue;
        final Scheduler.Worker recursiveScheduler;
        final ScheduledUnsubscribe scheduledUnsubscribe;
        static final AtomicLongFieldUpdater<ObserveOnSubscriber> REQUESTED = AtomicLongFieldUpdater.newUpdater(ObserveOnSubscriber.class, "requested");
        static final AtomicLongFieldUpdater<ObserveOnSubscriber> COUNTER_UPDATER = AtomicLongFieldUpdater.newUpdater(ObserveOnSubscriber.class, "counter");
        final NotificationLite<T> on = NotificationLite.instance();
        volatile boolean finished = false;
        volatile long requested = 0;
        final Action0 action = new Action0() { // from class: rx.internal.operators.OperatorObserveOn.ObserveOnSubscriber.2
            @Override // rx.functions.Action0
            public void call() {
                ObserveOnSubscriber.this.pollQueue();
            }
        };

        public ObserveOnSubscriber(Scheduler scheduler, Subscriber<? super T> subscriber) {
            this.child = subscriber;
            this.recursiveScheduler = scheduler.createWorker();
            if (UnsafeAccess.isUnsafeAvailable()) {
                this.queue = new SpscArrayQueue(RxRingBuffer.SIZE);
            } else {
                this.queue = new SynchronizedQueue(RxRingBuffer.SIZE);
            }
            this.scheduledUnsubscribe = new ScheduledUnsubscribe(this.recursiveScheduler);
        }

        void init() {
            this.child.add(this.scheduledUnsubscribe);
            this.child.setProducer(new Producer() { // from class: rx.internal.operators.OperatorObserveOn.ObserveOnSubscriber.1
                @Override // rx.Producer
                public void request(long j) {
                    BackpressureUtils.getAndAddRequest(ObserveOnSubscriber.REQUESTED, ObserveOnSubscriber.this, j);
                    ObserveOnSubscriber.this.schedule();
                }
            });
            this.child.add(this.recursiveScheduler);
            this.child.add(this);
        }

        @Override // rx.Subscriber
        public void onStart() {
            request(RxRingBuffer.SIZE);
        }

        @Override // rx.Observer
        public void onNext(T t) {
            if (isUnsubscribed()) {
                return;
            }
            if (!this.queue.offer(this.on.next(t))) {
                onError(new MissingBackpressureException());
            } else {
                schedule();
            }
        }

        @Override // rx.Observer
        public void onCompleted() {
            if (isUnsubscribed() || this.finished) {
                return;
            }
            this.finished = true;
            schedule();
        }

        @Override // rx.Observer
        public void onError(Throwable th) {
            if (isUnsubscribed() || this.finished) {
                return;
            }
            this.error = th;
            unsubscribe();
            this.finished = true;
            schedule();
        }

        protected void schedule() {
            if (COUNTER_UPDATER.getAndIncrement(this) == 0) {
                this.recursiveScheduler.schedule(this.action);
            }
        }

        void pollQueue() {
            Object objPoll;
            int i = 0;
            do {
                this.counter = 1L;
                long j = this.requested;
                long j2 = 0;
                while (!this.child.isUnsubscribed()) {
                    if (this.finished) {
                        Throwable th = this.error;
                        if (th != null) {
                            this.queue.clear();
                            this.child.onError(th);
                            return;
                        } else if (this.queue.isEmpty()) {
                            this.child.onCompleted();
                            return;
                        }
                    }
                    if (j > 0 && (objPoll = this.queue.poll()) != null) {
                        this.child.onNext(this.on.getValue(objPoll));
                        j--;
                        i++;
                        j2++;
                    } else if (j2 > 0 && this.requested != LongCompanionObject.MAX_VALUE) {
                        REQUESTED.addAndGet(this, -j2);
                    }
                }
                return;
            } while (COUNTER_UPDATER.decrementAndGet(this) > 0);
            if (i > 0) {
                request(i);
            }
        }
    }

    static final class ScheduledUnsubscribe implements Subscription {
        static final AtomicIntegerFieldUpdater<ScheduledUnsubscribe> ONCE_UPDATER = AtomicIntegerFieldUpdater.newUpdater(ScheduledUnsubscribe.class, "once");
        volatile int once;
        volatile boolean unsubscribed = false;
        final Scheduler.Worker worker;

        public ScheduledUnsubscribe(Scheduler.Worker worker) {
            this.worker = worker;
        }

        @Override // rx.Subscription
        public boolean isUnsubscribed() {
            return this.unsubscribed;
        }

        @Override // rx.Subscription
        public void unsubscribe() {
            if (ONCE_UPDATER.getAndSet(this, 1) == 0) {
                this.worker.schedule(new Action0() { // from class: rx.internal.operators.OperatorObserveOn.ScheduledUnsubscribe.1
                    @Override // rx.functions.Action0
                    public void call() {
                        ScheduledUnsubscribe.this.worker.unsubscribe();
                        ScheduledUnsubscribe.this.unsubscribed = true;
                    }
                });
            }
        }
    }
}
