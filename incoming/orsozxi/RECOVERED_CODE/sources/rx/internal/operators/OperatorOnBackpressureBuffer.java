package rx.internal.operators;

import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;
import kotlin.jvm.internal.LongCompanionObject;
import rx.Observable;
import rx.Producer;
import rx.Subscriber;
import rx.exceptions.MissingBackpressureException;
import rx.functions.Action0;
import rx.internal.util.BackpressureDrainManager;

/* loaded from: classes3.dex */
public class OperatorOnBackpressureBuffer<T> implements Observable.Operator<T, T> {
    private final Long capacity;
    private final Action0 onOverflow;

    private static class Holder {
        static final OperatorOnBackpressureBuffer<?> INSTANCE = new OperatorOnBackpressureBuffer<>();

        private Holder() {
        }
    }

    public static <T> OperatorOnBackpressureBuffer<T> instance() {
        return (OperatorOnBackpressureBuffer<T>) Holder.INSTANCE;
    }

    private OperatorOnBackpressureBuffer() {
        this.capacity = null;
        this.onOverflow = null;
    }

    public OperatorOnBackpressureBuffer(long j) {
        this(j, null);
    }

    public OperatorOnBackpressureBuffer(long j, Action0 action0) {
        if (j <= 0) {
            throw new IllegalArgumentException("Buffer capacity must be > 0");
        }
        this.capacity = Long.valueOf(j);
        this.onOverflow = action0;
    }

    @Override // rx.functions.Func1
    public Subscriber<? super T> call(Subscriber<? super T> subscriber) {
        BufferSubscriber bufferSubscriber = new BufferSubscriber(subscriber, this.capacity, this.onOverflow);
        subscriber.add(bufferSubscriber);
        subscriber.setProducer(bufferSubscriber.manager());
        return bufferSubscriber;
    }

    private static final class BufferSubscriber<T> extends Subscriber<T> implements BackpressureDrainManager.BackpressureQueueCallback {
        private final Long baseCapacity;
        private final AtomicLong capacity;
        private final Subscriber<? super T> child;
        private final BackpressureDrainManager manager;
        private final Action0 onOverflow;
        private final ConcurrentLinkedQueue<Object> queue = new ConcurrentLinkedQueue<>();
        private final AtomicBoolean saturated = new AtomicBoolean(false);
        private final NotificationLite<T> on = NotificationLite.instance();

        public BufferSubscriber(Subscriber<? super T> subscriber, Long l, Action0 action0) {
            this.child = subscriber;
            this.baseCapacity = l;
            this.capacity = l != null ? new AtomicLong(l.longValue()) : null;
            this.onOverflow = action0;
            this.manager = new BackpressureDrainManager(this);
        }

        @Override // rx.Subscriber
        public void onStart() {
            request(LongCompanionObject.MAX_VALUE);
        }

        @Override // rx.Observer
        public void onCompleted() throws Throwable {
            if (this.saturated.get()) {
                return;
            }
            this.manager.terminateAndDrain();
        }

        @Override // rx.Observer
        public void onError(Throwable th) throws Throwable {
            if (this.saturated.get()) {
                return;
            }
            this.manager.terminateAndDrain(th);
        }

        @Override // rx.Observer
        public void onNext(T t) throws Throwable {
            if (assertCapacity()) {
                this.queue.offer(this.on.next(t));
                this.manager.drain();
            }
        }

        @Override // rx.internal.util.BackpressureDrainManager.BackpressureQueueCallback
        public boolean accept(Object obj) {
            return this.on.accept(this.child, obj);
        }

        @Override // rx.internal.util.BackpressureDrainManager.BackpressureQueueCallback
        public void complete(Throwable th) {
            if (th != null) {
                this.child.onError(th);
            } else {
                this.child.onCompleted();
            }
        }

        @Override // rx.internal.util.BackpressureDrainManager.BackpressureQueueCallback
        public Object peek() {
            return this.queue.peek();
        }

        @Override // rx.internal.util.BackpressureDrainManager.BackpressureQueueCallback
        public Object poll() {
            Object objPoll = this.queue.poll();
            AtomicLong atomicLong = this.capacity;
            if (atomicLong != null && objPoll != null) {
                atomicLong.incrementAndGet();
            }
            return objPoll;
        }

        private boolean assertCapacity() {
            long j;
            if (this.capacity == null) {
                return true;
            }
            do {
                j = this.capacity.get();
                if (j <= 0) {
                    if (this.saturated.compareAndSet(false, true)) {
                        unsubscribe();
                        this.child.onError(new MissingBackpressureException("Overflowed buffer of " + this.baseCapacity));
                        Action0 action0 = this.onOverflow;
                        if (action0 != null) {
                            action0.call();
                        }
                    }
                    return false;
                }
            } while (!this.capacity.compareAndSet(j, j - 1));
            return true;
        }

        protected Producer manager() {
            return this.manager;
        }
    }
}
