package org.apache.commons.lang3.concurrent;

/* loaded from: classes3.dex */
public abstract class LazyInitializer<T> implements ConcurrentInitializer<T> {
    private static final Object NO_INIT = new Object();
    private volatile T object = (T) NO_INIT;

    protected abstract T initialize() throws ConcurrentException;

    @Override // org.apache.commons.lang3.concurrent.ConcurrentInitializer
    public T get() throws ConcurrentException {
        T tInitialize = this.object;
        if (tInitialize == NO_INIT) {
            synchronized (this) {
                tInitialize = this.object;
                if (tInitialize == NO_INIT) {
                    tInitialize = initialize();
                    this.object = tInitialize;
                }
            }
        }
        return tInitialize;
    }
}
