package rx.schedulers;

import java.util.Iterator;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;
import rx.Scheduler;
import rx.Subscription;
import rx.functions.Action0;
import rx.internal.schedulers.NewThreadWorker;
import rx.internal.schedulers.ScheduledAction;
import rx.internal.util.RxThreadFactory;
import rx.subscriptions.CompositeSubscription;
import rx.subscriptions.Subscriptions;

/* loaded from: classes3.dex */
final class CachedThreadScheduler extends Scheduler {
    private static final String WORKER_THREAD_NAME_PREFIX = "RxCachedThreadScheduler-";
    private static final RxThreadFactory WORKER_THREAD_FACTORY = new RxThreadFactory(WORKER_THREAD_NAME_PREFIX);
    private static final String EVICTOR_THREAD_NAME_PREFIX = "RxCachedWorkerPoolEvictor-";
    private static final RxThreadFactory EVICTOR_THREAD_FACTORY = new RxThreadFactory(EVICTOR_THREAD_NAME_PREFIX);

    CachedThreadScheduler() {
    }

    private static final class CachedWorkerPool {
        private static CachedWorkerPool INSTANCE = new CachedWorkerPool(60, TimeUnit.SECONDS);
        private final ScheduledExecutorService evictExpiredWorkerExecutor;
        private final ConcurrentLinkedQueue<ThreadWorker> expiringWorkerQueue = new ConcurrentLinkedQueue<>();
        private final long keepAliveTime;

        CachedWorkerPool(long j, TimeUnit timeUnit) {
            this.keepAliveTime = timeUnit.toNanos(j);
            ScheduledExecutorService scheduledExecutorServiceNewScheduledThreadPool = Executors.newScheduledThreadPool(1, CachedThreadScheduler.EVICTOR_THREAD_FACTORY);
            this.evictExpiredWorkerExecutor = scheduledExecutorServiceNewScheduledThreadPool;
            Runnable runnable = new Runnable() { // from class: rx.schedulers.CachedThreadScheduler.CachedWorkerPool.1
                @Override // java.lang.Runnable
                public void run() {
                    CachedWorkerPool.this.evictExpiredWorkers();
                }
            };
            long j2 = this.keepAliveTime;
            scheduledExecutorServiceNewScheduledThreadPool.scheduleWithFixedDelay(runnable, j2, j2, TimeUnit.NANOSECONDS);
        }

        ThreadWorker get() {
            while (!this.expiringWorkerQueue.isEmpty()) {
                ThreadWorker threadWorkerPoll = this.expiringWorkerQueue.poll();
                if (threadWorkerPoll != null) {
                    return threadWorkerPoll;
                }
            }
            return new ThreadWorker(CachedThreadScheduler.WORKER_THREAD_FACTORY);
        }

        void release(ThreadWorker threadWorker) {
            threadWorker.setExpirationTime(now() + this.keepAliveTime);
            this.expiringWorkerQueue.offer(threadWorker);
        }

        void evictExpiredWorkers() {
            if (this.expiringWorkerQueue.isEmpty()) {
                return;
            }
            long jNow = now();
            Iterator<ThreadWorker> it2 = this.expiringWorkerQueue.iterator();
            while (it2.hasNext()) {
                ThreadWorker next = it2.next();
                if (next.getExpirationTime() > jNow) {
                    return;
                }
                if (this.expiringWorkerQueue.remove(next)) {
                    next.unsubscribe();
                }
            }
        }

        long now() {
            return System.nanoTime();
        }
    }

    @Override // rx.Scheduler
    public Scheduler.Worker createWorker() {
        return new EventLoopWorker(CachedWorkerPool.INSTANCE.get());
    }

    private static final class EventLoopWorker extends Scheduler.Worker {
        static final AtomicIntegerFieldUpdater<EventLoopWorker> ONCE_UPDATER = AtomicIntegerFieldUpdater.newUpdater(EventLoopWorker.class, "once");
        private final CompositeSubscription innerSubscription = new CompositeSubscription();
        volatile int once;
        private final ThreadWorker threadWorker;

        EventLoopWorker(ThreadWorker threadWorker) {
            this.threadWorker = threadWorker;
        }

        @Override // rx.Subscription
        public void unsubscribe() {
            if (ONCE_UPDATER.compareAndSet(this, 0, 1)) {
                CachedWorkerPool.INSTANCE.release(this.threadWorker);
            }
            this.innerSubscription.unsubscribe();
        }

        @Override // rx.Subscription
        public boolean isUnsubscribed() {
            return this.innerSubscription.isUnsubscribed();
        }

        @Override // rx.Scheduler.Worker
        public Subscription schedule(Action0 action0) {
            return schedule(action0, 0L, null);
        }

        @Override // rx.Scheduler.Worker
        public Subscription schedule(Action0 action0, long j, TimeUnit timeUnit) {
            if (this.innerSubscription.isUnsubscribed()) {
                return Subscriptions.unsubscribed();
            }
            ScheduledAction scheduledActionScheduleActual = this.threadWorker.scheduleActual(action0, j, timeUnit);
            this.innerSubscription.add(scheduledActionScheduleActual);
            scheduledActionScheduleActual.addParent(this.innerSubscription);
            return scheduledActionScheduleActual;
        }
    }

    private static final class ThreadWorker extends NewThreadWorker {
        private long expirationTime;

        ThreadWorker(ThreadFactory threadFactory) {
            super(threadFactory);
            this.expirationTime = 0L;
        }

        public long getExpirationTime() {
            return this.expirationTime;
        }

        public void setExpirationTime(long j) {
            this.expirationTime = j;
        }
    }
}
