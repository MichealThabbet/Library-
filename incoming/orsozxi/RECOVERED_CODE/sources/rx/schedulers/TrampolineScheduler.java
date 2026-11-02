package rx.schedulers;

import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;
import rx.Scheduler;
import rx.Subscription;
import rx.functions.Action0;
import rx.subscriptions.BooleanSubscription;
import rx.subscriptions.Subscriptions;

/* loaded from: classes3.dex */
public final class TrampolineScheduler extends Scheduler {
    private static final TrampolineScheduler INSTANCE = new TrampolineScheduler();

    /* JADX INFO: Access modifiers changed from: private */
    public static int compare(int i, int i2) {
        if (i < i2) {
            return -1;
        }
        return i == i2 ? 0 : 1;
    }

    static TrampolineScheduler instance() {
        return INSTANCE;
    }

    @Override // rx.Scheduler
    public Scheduler.Worker createWorker() {
        return new InnerCurrentThreadScheduler();
    }

    TrampolineScheduler() {
    }

    private static class InnerCurrentThreadScheduler extends Scheduler.Worker implements Subscription {
        private static final AtomicIntegerFieldUpdater<InnerCurrentThreadScheduler> COUNTER_UPDATER = AtomicIntegerFieldUpdater.newUpdater(InnerCurrentThreadScheduler.class, "counter");
        volatile int counter;
        private final BooleanSubscription innerSubscription;
        private final PriorityBlockingQueue<TimedAction> queue;
        private final AtomicInteger wip;

        private InnerCurrentThreadScheduler() {
            this.queue = new PriorityBlockingQueue<>();
            this.innerSubscription = new BooleanSubscription();
            this.wip = new AtomicInteger();
        }

        @Override // rx.Scheduler.Worker
        public Subscription schedule(Action0 action0) {
            return enqueue(action0, now());
        }

        @Override // rx.Scheduler.Worker
        public Subscription schedule(Action0 action0, long j, TimeUnit timeUnit) {
            long jNow = now() + timeUnit.toMillis(j);
            return enqueue(new SleepingAction(action0, this, jNow), jNow);
        }

        private Subscription enqueue(Action0 action0, long j) {
            if (this.innerSubscription.isUnsubscribed()) {
                return Subscriptions.unsubscribed();
            }
            final TimedAction timedAction = new TimedAction(action0, Long.valueOf(j), COUNTER_UPDATER.incrementAndGet(this));
            this.queue.add(timedAction);
            if (this.wip.getAndIncrement() == 0) {
                do {
                    TimedAction timedActionPoll = this.queue.poll();
                    if (timedActionPoll != null) {
                        timedActionPoll.action.call();
                    }
                } while (this.wip.decrementAndGet() > 0);
                return Subscriptions.unsubscribed();
            }
            return Subscriptions.create(new Action0() { // from class: rx.schedulers.TrampolineScheduler.InnerCurrentThreadScheduler.1
                @Override // rx.functions.Action0
                public void call() {
                    InnerCurrentThreadScheduler.this.queue.remove(timedAction);
                }
            });
        }

        @Override // rx.Subscription
        public void unsubscribe() {
            this.innerSubscription.unsubscribe();
        }

        @Override // rx.Subscription
        public boolean isUnsubscribed() {
            return this.innerSubscription.isUnsubscribed();
        }
    }

    private static final class TimedAction implements Comparable<TimedAction> {
        final Action0 action;
        final int count;
        final Long execTime;

        private TimedAction(Action0 action0, Long l, int i) {
            this.action = action0;
            this.execTime = l;
            this.count = i;
        }

        @Override // java.lang.Comparable
        public int compareTo(TimedAction timedAction) {
            int iCompareTo = this.execTime.compareTo(timedAction.execTime);
            return iCompareTo == 0 ? TrampolineScheduler.compare(this.count, timedAction.count) : iCompareTo;
        }
    }
}
