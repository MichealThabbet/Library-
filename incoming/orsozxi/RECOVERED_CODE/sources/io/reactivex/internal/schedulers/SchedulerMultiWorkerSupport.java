package io.reactivex.internal.schedulers;

import io.reactivex.Scheduler;

/* loaded from: classes2.dex */
public interface SchedulerMultiWorkerSupport {

    public interface WorkerCallback {
        void onWorker(int i, Scheduler.Worker worker);
    }

    void createWorkers(int i, WorkerCallback workerCallback);
}
