package rx.internal.operators;

import java.util.Arrays;
import java.util.concurrent.atomic.AtomicBoolean;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.exceptions.CompositeException;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.functions.Func0;
import rx.functions.Func1;
import rx.observers.Subscribers;

/* loaded from: classes3.dex */
public final class OnSubscribeUsing<T, Resource> implements Observable.OnSubscribe<T> {
    private final Action1<? super Resource> dispose;
    private final boolean disposeEagerly;
    private final Func1<? super Resource, ? extends Observable<? extends T>> observableFactory;
    private final Func0<Resource> resourceFactory;

    public OnSubscribeUsing(Func0<Resource> func0, Func1<? super Resource, ? extends Observable<? extends T>> func1, Action1<? super Resource> action1, boolean z) {
        this.resourceFactory = func0;
        this.observableFactory = func1;
        this.dispose = action1;
        this.disposeEagerly = z;
    }

    @Override // rx.functions.Action1
    public void call(Subscriber<? super T> subscriber) {
        try {
            Resource resourceCall = this.resourceFactory.call();
            DisposeAction disposeAction = new DisposeAction(this.dispose, resourceCall);
            subscriber.add(disposeAction);
            Observable<? extends T> observableCall = this.observableFactory.call(resourceCall);
            if (this.disposeEagerly) {
                observableCall = observableCall.doOnTerminate(disposeAction);
            }
            try {
                observableCall.unsafeSubscribe(Subscribers.wrap(subscriber));
            } catch (Throwable th) {
                Throwable thDisposeEagerlyIfRequested = disposeEagerlyIfRequested(disposeAction);
                if (thDisposeEagerlyIfRequested != null) {
                    subscriber.onError(new CompositeException(Arrays.asList(th, thDisposeEagerlyIfRequested)));
                } else {
                    subscriber.onError(th);
                }
            }
        } catch (Throwable th2) {
            subscriber.onError(th2);
        }
    }

    private Throwable disposeEagerlyIfRequested(Action0 action0) {
        if (!this.disposeEagerly) {
            return null;
        }
        try {
            action0.call();
            return null;
        } catch (Throwable th) {
            return th;
        }
    }

    private static final class DisposeAction<Resource> extends AtomicBoolean implements Action0, Subscription {
        private static final long serialVersionUID = 4262875056400218316L;
        private Action1<? super Resource> dispose;
        private Resource resource;

        private DisposeAction(Action1<? super Resource> action1, Resource resource) {
            this.dispose = action1;
            this.resource = resource;
            lazySet(false);
        }

        /* JADX WARN: Type inference failed for: r0v2, types: [Resource, rx.functions.Action1<? super Resource>] */
        @Override // rx.functions.Action0
        public void call() {
            if (compareAndSet(false, true)) {
                ?? r0 = (Resource) null;
                try {
                    this.dispose.call(this.resource);
                } finally {
                    this.resource = null;
                    this.dispose = null;
                }
            }
        }

        @Override // rx.Subscription
        public boolean isUnsubscribed() {
            return get();
        }

        @Override // rx.Subscription
        public void unsubscribe() {
            call();
        }
    }
}
