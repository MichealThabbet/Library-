package rx.schedulers;

import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.concurrent.TimeUnit;
import rx.Scheduler;
import rx.Subscription;
import rx.functions.Action0;
import rx.subscriptions.BooleanSubscription;
import rx.subscriptions.Subscriptions;

/* loaded from: classes3.dex */
public class TestScheduler extends Scheduler {
    private static long counter;
    private final Queue<TimedAction> queue = new PriorityQueue(11, new CompareActionsByTime());
    private long time;

    static /* synthetic */ long access$108() {
        long j = counter;
        counter = 1 + j;
        return j;
    }

    private static final class TimedAction {
        private final Action0 action;
        private final long count;
        private final Scheduler.Worker scheduler;
        private final long time;

        private TimedAction(Scheduler.Worker worker, long j, Action0 action0) {
            this.count = TestScheduler.access$108();
            this.time = j;
            this.action = action0;
            this.scheduler = worker;
        }

        public String toString() {
            return String.format("TimedAction(time = %d, action = %s)", Long.valueOf(this.time), this.action.toString());
        }
    }

    private static class CompareActionsByTime implements Comparator<TimedAction> {
        private CompareActionsByTime() {
        }

        @Override // java.util.Comparator
        public int compare(TimedAction timedAction, TimedAction timedAction2) {
            return timedAction.time == timedAction2.time ? Long.valueOf(timedAction.count).compareTo(Long.valueOf(timedAction2.count)) : Long.valueOf(timedAction.time).compareTo(Long.valueOf(timedAction2.time));
        }
    }

    @Override // rx.Scheduler
    public long now() {
        return TimeUnit.NANOSECONDS.toMillis(this.time);
    }

    public void advanceTimeBy(long j, TimeUnit timeUnit) {
        advanceTimeTo(this.time + timeUnit.toNanos(j), TimeUnit.NANOSECONDS);
    }

    public void advanceTimeTo(long j, TimeUnit timeUnit) {
        triggerActions(timeUnit.toNanos(j));
    }

    public void triggerActions() {
        triggerActions(this.time);
    }

    private void triggerActions(long j) {
        while (!this.queue.isEmpty()) {
            TimedAction timedActionPeek = this.queue.peek();
            if (timedActionPeek.time > j) {
                break;
            }
            this.time = timedActionPeek.time == 0 ? this.time : timedActionPeek.time;
            this.queue.remove();
            if (!timedActionPeek.scheduler.isUnsubscribed()) {
                timedActionPeek.action.call();
            }
        }
        this.time = j;
    }

    @Override // rx.Scheduler
    public Scheduler.Worker createWorker() {
        return new InnerTestScheduler();
    }

    private final class InnerTestScheduler extends Scheduler.Worker {
        private final BooleanSubscription s;

        private InnerTestScheduler() {
            this.s = new BooleanSubscription();
        }

        @Override // rx.Subscription
        public void unsubscribe() {
            this.s.unsubscribe();
        }

        @Override // rx.Subscription
        public boolean isUnsubscribed() {
            return this.s.isUnsubscribed();
        }

        @Override // rx.Scheduler.Worker
        public Subscription schedule(Action0 action0, long j, TimeUnit timeUnit) {
            final TimedAction timedAction = new TimedAction(this, TestScheduler.this.time + timeUnit.toNanos(j), action0);
            TestScheduler.this.queue.add(timedAction);
            return Subscriptions.create(new Action0() { // from class: rx.schedulers.TestScheduler.InnerTestScheduler.1
                @Override // rx.functions.Action0
                public void call() {
                    TestScheduler.this.queue.remove(timedAction);
                }
            });
        }

        @Override // rx.Scheduler.Worker
        public Subscription schedule(Action0 action0) {
            final TimedAction timedAction = new TimedAction(this, 0L, action0);
            TestScheduler.this.queue.add(timedAction);
            return Subscriptions.create(new Action0() { // from class: rx.schedulers.TestScheduler.InnerTestScheduler.2
                @Override // rx.functions.Action0
                public void call() {
                    TestScheduler.this.queue.remove(timedAction);
                }
            });
        }

        @Override // rx.Scheduler.Worker
        public long now() {
            return TestScheduler.this.now();
        }
    }
}
