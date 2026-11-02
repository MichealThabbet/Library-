package rx.internal.operators;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.concurrent.LinkedBlockingQueue;
import rx.Notification;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.exceptions.Exceptions;

/* loaded from: classes3.dex */
public final class BlockingOperatorToIterator {
    private BlockingOperatorToIterator() {
        throw new IllegalStateException("No instances!");
    }

    public static <T> Iterator<T> toIterator(Observable<? extends T> observable) {
        final LinkedBlockingQueue linkedBlockingQueue = new LinkedBlockingQueue();
        final Subscription subscriptionSubscribe = observable.materialize().subscribe((Subscriber<? super Notification<? extends T>>) new Subscriber<Notification<? extends T>>() { // from class: rx.internal.operators.BlockingOperatorToIterator.1
            @Override // rx.Observer
            public void onCompleted() {
            }

            @Override // rx.Observer
            public void onError(Throwable th) {
                linkedBlockingQueue.offer(Notification.createOnError(th));
            }

            @Override // rx.Observer
            public void onNext(Notification<? extends T> notification) {
                linkedBlockingQueue.offer(notification);
            }
        });
        return new Iterator<T>() { // from class: rx.internal.operators.BlockingOperatorToIterator.2
            private Notification<? extends T> buf;

            @Override // java.util.Iterator
            public boolean hasNext() {
                if (this.buf == null) {
                    this.buf = take();
                }
                if (this.buf.isOnError()) {
                    throw Exceptions.propagate(this.buf.getThrowable());
                }
                return !this.buf.isOnCompleted();
            }

            @Override // java.util.Iterator
            public T next() {
                if (hasNext()) {
                    T value = this.buf.getValue();
                    this.buf = null;
                    return value;
                }
                throw new NoSuchElementException();
            }

            private Notification<? extends T> take() {
                try {
                    Notification<? extends T> notification = (Notification) linkedBlockingQueue.poll();
                    return notification != null ? notification : (Notification) linkedBlockingQueue.take();
                } catch (InterruptedException e) {
                    subscriptionSubscribe.unsubscribe();
                    throw Exceptions.propagate(e);
                }
            }

            @Override // java.util.Iterator
            public void remove() {
                throw new UnsupportedOperationException("Read-only iterator");
            }
        };
    }
}
