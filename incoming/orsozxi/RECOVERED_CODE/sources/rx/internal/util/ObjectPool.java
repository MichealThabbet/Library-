package rx.internal.util;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.TimeUnit;
import rx.Scheduler;
import rx.functions.Action0;
import rx.internal.util.unsafe.MpmcArrayQueue;
import rx.internal.util.unsafe.UnsafeAccess;
import rx.schedulers.Schedulers;

/* loaded from: classes3.dex */
public abstract class ObjectPool<T> {
    private final int maxSize;
    private Queue<T> pool;
    private Scheduler.Worker schedulerWorker;

    protected abstract T createObject();

    public ObjectPool() {
        this(0, 0, 67L);
    }

    private ObjectPool(final int i, final int i2, long j) {
        this.maxSize = i2;
        initialize(i);
        Scheduler.Worker workerCreateWorker = Schedulers.computation().createWorker();
        this.schedulerWorker = workerCreateWorker;
        workerCreateWorker.schedulePeriodically(new Action0() { // from class: rx.internal.util.ObjectPool.1
            @Override // rx.functions.Action0
            public void call() {
                int size = ObjectPool.this.pool.size();
                int i3 = 0;
                if (size < i) {
                    int i4 = i2 - size;
                    while (i3 < i4) {
                        ObjectPool.this.pool.add(ObjectPool.this.createObject());
                        i3++;
                    }
                    return;
                }
                int i5 = i2;
                if (size > i5) {
                    int i6 = size - i5;
                    while (i3 < i6) {
                        ObjectPool.this.pool.poll();
                        i3++;
                    }
                }
            }
        }, j, j, TimeUnit.SECONDS);
    }

    public T borrowObject() {
        T tPoll = this.pool.poll();
        return tPoll == null ? createObject() : tPoll;
    }

    public void returnObject(T t) {
        if (t == null) {
            return;
        }
        this.pool.offer(t);
    }

    public void shutdown() {
        this.schedulerWorker.unsubscribe();
    }

    private void initialize(int i) {
        if (UnsafeAccess.isUnsafeAvailable()) {
            this.pool = new MpmcArrayQueue(Math.max(this.maxSize, 1024));
        } else {
            this.pool = new ConcurrentLinkedQueue();
        }
        for (int i2 = 0; i2 < i; i2++) {
            this.pool.add(createObject());
        }
    }
}
