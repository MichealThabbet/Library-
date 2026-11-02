package rx.observables;

import java.util.Iterator;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicReference;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.internal.operators.BlockingOperatorLatest;
import rx.internal.operators.BlockingOperatorMostRecent;
import rx.internal.operators.BlockingOperatorNext;
import rx.internal.operators.BlockingOperatorToFuture;
import rx.internal.operators.BlockingOperatorToIterator;
import rx.internal.util.UtilityFunctions;

/* loaded from: classes3.dex */
public final class BlockingObservable<T> {
    private final Observable<? extends T> o;

    private BlockingObservable(Observable<? extends T> observable) {
        this.o = observable;
    }

    public static <T> BlockingObservable<T> from(Observable<? extends T> observable) {
        return new BlockingObservable<>(observable);
    }

    public void forEach(final Action1<? super T> action1) throws InterruptedException {
        final CountDownLatch countDownLatch = new CountDownLatch(1);
        final AtomicReference atomicReference = new AtomicReference();
        awaitForComplete(countDownLatch, this.o.subscribe((Subscriber<? super Object>) new Subscriber<T>() { // from class: rx.observables.BlockingObservable.1
            @Override // rx.Observer
            public void onCompleted() {
                countDownLatch.countDown();
            }

            @Override // rx.Observer
            public void onError(Throwable th) {
                atomicReference.set(th);
                countDownLatch.countDown();
            }

            @Override // rx.Observer
            public void onNext(T t) {
                action1.call(t);
            }
        }));
        if (atomicReference.get() != null) {
            if (atomicReference.get() instanceof RuntimeException) {
                throw ((RuntimeException) atomicReference.get());
            }
            throw new RuntimeException((Throwable) atomicReference.get());
        }
    }

    public Iterator<T> getIterator() {
        return BlockingOperatorToIterator.toIterator(this.o);
    }

    public T first() {
        return blockForSingle(this.o.first());
    }

    public T first(Func1<? super T, Boolean> func1) {
        return blockForSingle(this.o.first(func1));
    }

    public T firstOrDefault(T t) {
        return blockForSingle(this.o.map(UtilityFunctions.identity()).firstOrDefault(t));
    }

    public T firstOrDefault(T t, Func1<? super T, Boolean> func1) {
        return blockForSingle(this.o.filter(func1).map(UtilityFunctions.identity()).firstOrDefault(t));
    }

    public T last() {
        return blockForSingle(this.o.last());
    }

    public T last(Func1<? super T, Boolean> func1) {
        return blockForSingle(this.o.last(func1));
    }

    public T lastOrDefault(T t) {
        return blockForSingle(this.o.map(UtilityFunctions.identity()).lastOrDefault(t));
    }

    public T lastOrDefault(T t, Func1<? super T, Boolean> func1) {
        return blockForSingle(this.o.filter(func1).map(UtilityFunctions.identity()).lastOrDefault(t));
    }

    public Iterable<T> mostRecent(T t) {
        return BlockingOperatorMostRecent.mostRecent(this.o, t);
    }

    public Iterable<T> next() {
        return BlockingOperatorNext.next(this.o);
    }

    public Iterable<T> latest() {
        return BlockingOperatorLatest.latest(this.o);
    }

    public T single() {
        return blockForSingle(this.o.single());
    }

    public T single(Func1<? super T, Boolean> func1) {
        return blockForSingle(this.o.single(func1));
    }

    public T singleOrDefault(T t) {
        return blockForSingle(this.o.map(UtilityFunctions.identity()).singleOrDefault(t));
    }

    public T singleOrDefault(T t, Func1<? super T, Boolean> func1) {
        return blockForSingle(this.o.filter(func1).map(UtilityFunctions.identity()).singleOrDefault(t));
    }

    public Future<T> toFuture() {
        return BlockingOperatorToFuture.toFuture(this.o);
    }

    public Iterable<T> toIterable() {
        return new Iterable<T>() { // from class: rx.observables.BlockingObservable.2
            @Override // java.lang.Iterable
            public Iterator<T> iterator() {
                return BlockingObservable.this.getIterator();
            }
        };
    }

    private T blockForSingle(Observable<? extends T> observable) throws InterruptedException {
        final AtomicReference atomicReference = new AtomicReference();
        final AtomicReference atomicReference2 = new AtomicReference();
        final CountDownLatch countDownLatch = new CountDownLatch(1);
        awaitForComplete(countDownLatch, observable.subscribe((Subscriber<? super Object>) new Subscriber<T>() { // from class: rx.observables.BlockingObservable.3
            @Override // rx.Observer
            public void onCompleted() {
                countDownLatch.countDown();
            }

            @Override // rx.Observer
            public void onError(Throwable th) {
                atomicReference2.set(th);
                countDownLatch.countDown();
            }

            @Override // rx.Observer
            public void onNext(T t) {
                atomicReference.set(t);
            }
        }));
        if (atomicReference2.get() != null) {
            if (atomicReference2.get() instanceof RuntimeException) {
                throw ((RuntimeException) atomicReference2.get());
            }
            throw new RuntimeException((Throwable) atomicReference2.get());
        }
        return (T) atomicReference.get();
    }

    private void awaitForComplete(CountDownLatch countDownLatch, Subscription subscription) throws InterruptedException {
        if (countDownLatch.getCount() == 0) {
            return;
        }
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            subscription.unsubscribe();
            Thread.currentThread().interrupt();
            throw new RuntimeException("Interrupted while waiting for subscription to complete.", e);
        }
    }
}
