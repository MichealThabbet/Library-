package rx.internal.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import rx.Subscription;
import rx.exceptions.Exceptions;
import rx.functions.Action1;

/* loaded from: classes3.dex */
public final class SubscriptionRandomList<T extends Subscription> implements Subscription {
    private Set<T> subscriptions;
    private boolean unsubscribed = false;

    @Override // rx.Subscription
    public synchronized boolean isUnsubscribed() {
        return this.unsubscribed;
    }

    public void add(T t) {
        synchronized (this) {
            if (!this.unsubscribed) {
                if (this.subscriptions == null) {
                    this.subscriptions = new HashSet(4);
                }
                this.subscriptions.add(t);
                t = null;
            }
        }
        if (t != null) {
            t.unsubscribe();
        }
    }

    public void remove(Subscription subscription) {
        synchronized (this) {
            if (!this.unsubscribed && this.subscriptions != null) {
                boolean zRemove = this.subscriptions.remove(subscription);
                if (zRemove) {
                    subscription.unsubscribe();
                }
            }
        }
    }

    public void clear() {
        synchronized (this) {
            if (!this.unsubscribed && this.subscriptions != null) {
                Set<T> set = this.subscriptions;
                this.subscriptions = null;
                unsubscribeFromAll(set);
            }
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public void forEach(Action1<T> action1) {
        synchronized (this) {
            if (!this.unsubscribed && this.subscriptions != null) {
                for (Subscription subscription : (Subscription[]) this.subscriptions.toArray((Object[]) null)) {
                    action1.call(subscription);
                }
            }
        }
    }

    @Override // rx.Subscription
    public void unsubscribe() {
        synchronized (this) {
            if (this.unsubscribed) {
                return;
            }
            this.unsubscribed = true;
            Set<T> set = this.subscriptions;
            this.subscriptions = null;
            unsubscribeFromAll(set);
        }
    }

    private static <T extends Subscription> void unsubscribeFromAll(Collection<T> collection) {
        if (collection == null) {
            return;
        }
        ArrayList arrayList = null;
        Iterator<T> it2 = collection.iterator();
        while (it2.hasNext()) {
            try {
                it2.next().unsubscribe();
            } catch (Throwable th) {
                if (arrayList == null) {
                    arrayList = new ArrayList();
                }
                arrayList.add(th);
            }
        }
        Exceptions.throwIfAny(arrayList);
    }
}
