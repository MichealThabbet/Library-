package rx.internal.util;

import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;
import rx.Subscription;
import rx.functions.Func1;

/* loaded from: classes3.dex */
public final class SubscriptionIndexedRingBuffer<T extends Subscription> implements Subscription {
    private volatile IndexedRingBuffer<T> subscriptions = IndexedRingBuffer.getInstance();
    private volatile int unsubscribed = 0;
    private static final AtomicIntegerFieldUpdater<SubscriptionIndexedRingBuffer> UNSUBSCRIBED = AtomicIntegerFieldUpdater.newUpdater(SubscriptionIndexedRingBuffer.class, "unsubscribed");
    private static final Func1<Subscription, Boolean> UNSUBSCRIBE = new Func1<Subscription, Boolean>() { // from class: rx.internal.util.SubscriptionIndexedRingBuffer.1
        @Override // rx.functions.Func1
        public Boolean call(Subscription subscription) {
            subscription.unsubscribe();
            return Boolean.TRUE;
        }
    };

    @Override // rx.Subscription
    public boolean isUnsubscribed() {
        return this.unsubscribed == 1;
    }

    public synchronized int add(T t) {
        if (this.unsubscribed != 1 && this.subscriptions != null) {
            int iAdd = this.subscriptions.add(t);
            if (this.unsubscribed == 1) {
                t.unsubscribe();
            }
            return iAdd;
        }
        t.unsubscribe();
        return -1;
    }

    public void remove(int i) {
        T tRemove;
        if (this.unsubscribed == 1 || this.subscriptions == null || i < 0 || (tRemove = this.subscriptions.remove(i)) == null || tRemove == null) {
            return;
        }
        tRemove.unsubscribe();
    }

    public void removeSilently(int i) {
        if (this.unsubscribed == 1 || this.subscriptions == null || i < 0) {
            return;
        }
        this.subscriptions.remove(i);
    }

    @Override // rx.Subscription
    public void unsubscribe() {
        if (!UNSUBSCRIBED.compareAndSet(this, 0, 1) || this.subscriptions == null) {
            return;
        }
        unsubscribeFromAll(this.subscriptions);
        IndexedRingBuffer<T> indexedRingBuffer = this.subscriptions;
        this.subscriptions = null;
        indexedRingBuffer.unsubscribe();
    }

    public int forEach(Func1<T, Boolean> func1) {
        return forEach(func1, 0);
    }

    public synchronized int forEach(Func1<T, Boolean> func1, int i) {
        if (this.unsubscribed != 1 && this.subscriptions != null) {
            return this.subscriptions.forEach(func1, i);
        }
        return 0;
    }

    private static void unsubscribeFromAll(IndexedRingBuffer<? extends Subscription> indexedRingBuffer) {
        if (indexedRingBuffer == null) {
            return;
        }
        indexedRingBuffer.forEach(UNSUBSCRIBE);
    }
}
