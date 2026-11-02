package rx;

import java.util.concurrent.TimeUnit;
import rx.functions.Action0;
import rx.subscriptions.MultipleAssignmentSubscription;

/* loaded from: classes3.dex */
public abstract class Scheduler {
    public abstract Worker createWorker();

    public static abstract class Worker implements Subscription {
        public abstract Subscription schedule(Action0 action0);

        public abstract Subscription schedule(Action0 action0, long j, TimeUnit timeUnit);

        public Subscription schedulePeriodically(final Action0 action0, long j, long j2, TimeUnit timeUnit) {
            final long nanos = timeUnit.toNanos(j2);
            final long nanos2 = TimeUnit.MILLISECONDS.toNanos(now()) + timeUnit.toNanos(j);
            final MultipleAssignmentSubscription multipleAssignmentSubscription = new MultipleAssignmentSubscription();
            Action0 action02 = new Action0() { // from class: rx.Scheduler.Worker.1
                long count = 0;

                @Override // rx.functions.Action0
                public void call() {
                    if (multipleAssignmentSubscription.isUnsubscribed()) {
                        return;
                    }
                    action0.call();
                    long j3 = nanos2;
                    long j4 = this.count + 1;
                    this.count = j4;
                    multipleAssignmentSubscription.set(Worker.this.schedule(this, (j3 + (j4 * nanos)) - TimeUnit.MILLISECONDS.toNanos(Worker.this.now()), TimeUnit.NANOSECONDS));
                }
            };
            MultipleAssignmentSubscription multipleAssignmentSubscription2 = new MultipleAssignmentSubscription();
            multipleAssignmentSubscription.set(multipleAssignmentSubscription2);
            multipleAssignmentSubscription2.set(schedule(action02, j, timeUnit));
            return multipleAssignmentSubscription;
        }

        public long now() {
            return System.currentTimeMillis();
        }
    }

    public long now() {
        return System.currentTimeMillis();
    }
}
