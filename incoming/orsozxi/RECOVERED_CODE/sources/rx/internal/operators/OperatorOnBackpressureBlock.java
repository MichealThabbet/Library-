package rx.internal.operators;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import rx.Observable;
import rx.Subscriber;
import rx.internal.util.BackpressureDrainManager;

/* loaded from: classes3.dex */
public class OperatorOnBackpressureBlock<T> implements Observable.Operator<T, T> {
    final int max;

    public OperatorOnBackpressureBlock(int i) {
        this.max = i;
    }

    @Override // rx.functions.Func1
    public Subscriber<? super T> call(Subscriber<? super T> subscriber) {
        BlockingSubscriber blockingSubscriber = new BlockingSubscriber(this.max, subscriber);
        blockingSubscriber.init();
        return blockingSubscriber;
    }

    static final class BlockingSubscriber<T> extends Subscriber<T> implements BackpressureDrainManager.BackpressureQueueCallback {
        final Subscriber<? super T> child;
        final BlockingQueue<Object> queue;
        final NotificationLite<T> nl = NotificationLite.instance();
        final BackpressureDrainManager manager = new BackpressureDrainManager(this);

        public BlockingSubscriber(int i, Subscriber<? super T> subscriber) {
            this.queue = new ArrayBlockingQueue(i);
            this.child = subscriber;
        }

        void init() {
            this.child.add(this);
            this.child.setProducer(this.manager);
        }

        @Override // rx.Observer
        public void onNext(T t) throws Throwable {
            try {
                this.queue.put(this.nl.next(t));
                this.manager.drain();
            } catch (InterruptedException e) {
                if (isUnsubscribed()) {
                    return;
                }
                onError(e);
            }
        }

        @Override // rx.Observer
        public void onError(Throwable th) throws Throwable {
            this.manager.terminateAndDrain(th);
        }

        @Override // rx.Observer
        public void onCompleted() throws Throwable {
            this.manager.terminateAndDrain();
        }

        @Override // rx.internal.util.BackpressureDrainManager.BackpressureQueueCallback
        public boolean accept(Object obj) {
            return this.nl.accept(this.child, obj);
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
            return this.queue.poll();
        }
    }
}
