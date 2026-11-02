package rx.internal.util;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicLongFieldUpdater;

/* loaded from: classes3.dex */
public final class RxThreadFactory implements ThreadFactory {
    static final AtomicLongFieldUpdater<RxThreadFactory> COUNTER_UPDATER = AtomicLongFieldUpdater.newUpdater(RxThreadFactory.class, "counter");
    volatile long counter;
    final String prefix;

    public RxThreadFactory(String str) {
        this.prefix = str;
    }

    @Override // java.util.concurrent.ThreadFactory
    public Thread newThread(Runnable runnable) {
        Thread thread = new Thread(runnable, this.prefix + COUNTER_UPDATER.incrementAndGet(this));
        thread.setDaemon(true);
        return thread;
    }
}
