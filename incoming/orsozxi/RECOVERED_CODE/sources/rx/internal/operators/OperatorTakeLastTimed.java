package rx.internal.operators;

import java.util.ArrayDeque;
import java.util.concurrent.TimeUnit;
import kotlin.jvm.internal.LongCompanionObject;
import rx.Observable;
import rx.Scheduler;
import rx.Subscriber;

/* loaded from: classes3.dex */
public final class OperatorTakeLastTimed<T> implements Observable.Operator<T, T> {
    private final long ageMillis;
    private final int count;
    private final Scheduler scheduler;

    public OperatorTakeLastTimed(long j, TimeUnit timeUnit, Scheduler scheduler) {
        this.ageMillis = timeUnit.toMillis(j);
        this.scheduler = scheduler;
        this.count = -1;
    }

    public OperatorTakeLastTimed(int i, long j, TimeUnit timeUnit, Scheduler scheduler) {
        if (i < 0) {
            throw new IndexOutOfBoundsException("count could not be negative");
        }
        this.ageMillis = timeUnit.toMillis(j);
        this.scheduler = scheduler;
        this.count = i;
    }

    @Override // rx.functions.Func1
    public Subscriber<? super T> call(final Subscriber<? super T> subscriber) {
        final ArrayDeque arrayDeque = new ArrayDeque();
        final ArrayDeque arrayDeque2 = new ArrayDeque();
        final NotificationLite notificationLiteInstance = NotificationLite.instance();
        final TakeLastQueueProducer takeLastQueueProducer = new TakeLastQueueProducer(notificationLiteInstance, arrayDeque, subscriber);
        subscriber.setProducer(takeLastQueueProducer);
        return new Subscriber<T>(subscriber) { // from class: rx.internal.operators.OperatorTakeLastTimed.1
            protected void runEvictionPolicy(long j) {
                while (OperatorTakeLastTimed.this.count >= 0 && arrayDeque.size() > OperatorTakeLastTimed.this.count) {
                    arrayDeque2.pollFirst();
                    arrayDeque.pollFirst();
                }
                while (!arrayDeque.isEmpty() && ((Long) arrayDeque2.peekFirst()).longValue() < j - OperatorTakeLastTimed.this.ageMillis) {
                    arrayDeque2.pollFirst();
                    arrayDeque.pollFirst();
                }
            }

            @Override // rx.Subscriber
            public void onStart() {
                request(LongCompanionObject.MAX_VALUE);
            }

            @Override // rx.Observer
            public void onNext(T t) {
                long jNow = OperatorTakeLastTimed.this.scheduler.now();
                arrayDeque2.add(Long.valueOf(jNow));
                arrayDeque.add(notificationLiteInstance.next(t));
                runEvictionPolicy(jNow);
            }

            @Override // rx.Observer
            public void onError(Throwable th) {
                arrayDeque2.clear();
                arrayDeque.clear();
                subscriber.onError(th);
            }

            @Override // rx.Observer
            public void onCompleted() {
                runEvictionPolicy(OperatorTakeLastTimed.this.scheduler.now());
                arrayDeque2.clear();
                arrayDeque.offer(notificationLiteInstance.completed());
                takeLastQueueProducer.startEmitting();
            }
        };
    }
}
