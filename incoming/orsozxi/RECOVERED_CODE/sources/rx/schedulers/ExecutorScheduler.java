package rx.schedulers;

import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import rx.Scheduler;
import rx.Subscription;
import rx.functions.Action0;
import rx.internal.schedulers.ScheduledAction;
import rx.plugins.RxJavaPlugins;
import rx.subscriptions.CompositeSubscription;
import rx.subscriptions.MultipleAssignmentSubscription;
import rx.subscriptions.Subscriptions;

/* loaded from: classes3.dex */
final class ExecutorScheduler extends Scheduler {
    final Executor executor;

    public ExecutorScheduler(Executor executor) {
        this.executor = executor;
    }

    @Override // rx.Scheduler
    public Scheduler.Worker createWorker() {
        return new ExecutorSchedulerWorker(this.executor);
    }

    static final class ExecutorSchedulerWorker extends Scheduler.Worker implements Runnable {
        final Executor executor;
        final ConcurrentLinkedQueue<ScheduledAction> queue = new ConcurrentLinkedQueue<>();
        final AtomicInteger wip = new AtomicInteger();
        final CompositeSubscription tasks = new CompositeSubscription();

        public ExecutorSchedulerWorker(Executor executor) {
            this.executor = executor;
        }

        @Override // rx.Scheduler.Worker
        public Subscription schedule(Action0 action0) {
            if (isUnsubscribed()) {
                return Subscriptions.unsubscribed();
            }
            ScheduledAction scheduledAction = new ScheduledAction(action0, this.tasks);
            this.tasks.add(scheduledAction);
            this.queue.offer(scheduledAction);
            if (this.wip.getAndIncrement() == 0) {
                try {
                    this.executor.execute(this);
                } catch (RejectedExecutionException e) {
                    this.tasks.remove(scheduledAction);
                    this.wip.decrementAndGet();
                    RxJavaPlugins.getInstance().getErrorHandler().handleError(e);
                    throw e;
                }
            }
            return scheduledAction;
        }

        @Override // java.lang.Runnable
        public void run() {
            do {
                ScheduledAction scheduledActionPoll = this.queue.poll();
                if (!scheduledActionPoll.isUnsubscribed()) {
                    scheduledActionPoll.run();
                }
            } while (this.wip.decrementAndGet() > 0);
        }

        @Override // rx.Scheduler.Worker
        public Subscription schedule(final Action0 action0, long j, TimeUnit timeUnit) {
            ScheduledExecutorService genericScheduledExecutorService;
            if (j <= 0) {
                return schedule(action0);
            }
            if (isUnsubscribed()) {
                return Subscriptions.unsubscribed();
            }
            Executor executor = this.executor;
            if (executor instanceof ScheduledExecutorService) {
                genericScheduledExecutorService = (ScheduledExecutorService) executor;
            } else {
                genericScheduledExecutorService = GenericScheduledExecutorService.getInstance();
            }
            MultipleAssignmentSubscription multipleAssignmentSubscription = new MultipleAssignmentSubscription();
            final MultipleAssignmentSubscription multipleAssignmentSubscription2 = new MultipleAssignmentSubscription();
            multipleAssignmentSubscription2.set(multipleAssignmentSubscription);
            this.tasks.add(multipleAssignmentSubscription2);
            final Subscription subscriptionCreate = Subscriptions.create(new Action0() { // from class: rx.schedulers.ExecutorScheduler.ExecutorSchedulerWorker.1
                @Override // rx.functions.Action0
                public void call() {
                    ExecutorSchedulerWorker.this.tasks.remove(multipleAssignmentSubscription2);
                }
            });
            ScheduledAction scheduledAction = new ScheduledAction(new Action0() { // from class: rx.schedulers.ExecutorScheduler.ExecutorSchedulerWorker.2
                @Override // rx.functions.Action0
                public void call() {
                    if (multipleAssignmentSubscription2.isUnsubscribed()) {
                        return;
                    }
                    Subscription subscriptionSchedule = ExecutorSchedulerWorker.this.schedule(action0);
                    multipleAssignmentSubscription2.set(subscriptionSchedule);
                    if (subscriptionSchedule.getClass() == ScheduledAction.class) {
                        ((ScheduledAction) subscriptionSchedule).add(subscriptionCreate);
                    }
                }
            });
            multipleAssignmentSubscription.set(scheduledAction);
            try {
                scheduledAction.add(genericScheduledExecutorService.schedule(scheduledAction, j, timeUnit));
                return subscriptionCreate;
            } catch (RejectedExecutionException e) {
                RxJavaPlugins.getInstance().getErrorHandler().handleError(e);
                throw e;
            }
        }

        @Override // rx.Subscription
        public boolean isUnsubscribed() {
            return this.tasks.isUnsubscribed();
        }

        @Override // rx.Subscription
        public void unsubscribe() {
            this.tasks.unsubscribe();
        }
    }
}
