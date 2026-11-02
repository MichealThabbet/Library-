package rx.schedulers;

import rx.Scheduler;
import rx.functions.Action0;

/* loaded from: classes3.dex */
class SleepingAction implements Action0 {
    private final long execTime;
    private final Scheduler.Worker innerScheduler;
    private final Action0 underlying;

    public SleepingAction(Action0 action0, Scheduler.Worker worker, long j) {
        this.underlying = action0;
        this.innerScheduler = worker;
        this.execTime = j;
    }

    @Override // rx.functions.Action0
    public void call() throws InterruptedException {
        if (this.innerScheduler.isUnsubscribed()) {
            return;
        }
        if (this.execTime > this.innerScheduler.now()) {
            long jNow = this.execTime - this.innerScheduler.now();
            if (jNow > 0) {
                try {
                    Thread.sleep(jNow);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    throw new RuntimeException(e);
                }
            }
        }
        if (this.innerScheduler.isUnsubscribed()) {
            return;
        }
        this.underlying.call();
    }
}
