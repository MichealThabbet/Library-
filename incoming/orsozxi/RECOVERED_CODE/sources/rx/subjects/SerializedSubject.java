package rx.subjects;

import rx.Observable;
import rx.Subscriber;
import rx.observers.SerializedObserver;

/* loaded from: classes3.dex */
public class SerializedSubject<T, R> extends Subject<T, R> {
    private final Subject<T, R> actual;
    private final SerializedObserver<T> observer;

    public SerializedSubject(final Subject<T, R> subject) {
        super(new Observable.OnSubscribe<R>() { // from class: rx.subjects.SerializedSubject.1
            @Override // rx.functions.Action1
            public void call(Subscriber<? super R> subscriber) {
                subject.unsafeSubscribe(subscriber);
            }
        });
        this.actual = subject;
        this.observer = new SerializedObserver<>(subject);
    }

    @Override // rx.Observer
    public void onCompleted() {
        this.observer.onCompleted();
    }

    @Override // rx.Observer
    public void onError(Throwable th) {
        this.observer.onError(th);
    }

    @Override // rx.Observer
    public void onNext(T t) {
        this.observer.onNext(t);
    }

    @Override // rx.subjects.Subject
    public boolean hasObservers() {
        return this.actual.hasObservers();
    }

    @Override // rx.subjects.Subject
    public boolean hasCompleted() {
        return this.actual.hasCompleted();
    }

    @Override // rx.subjects.Subject
    public boolean hasThrowable() {
        return this.actual.hasThrowable();
    }

    @Override // rx.subjects.Subject
    public boolean hasValue() {
        return this.actual.hasValue();
    }

    @Override // rx.subjects.Subject
    public Throwable getThrowable() {
        return this.actual.getThrowable();
    }

    @Override // rx.subjects.Subject
    public T getValue() {
        return this.actual.getValue();
    }

    @Override // rx.subjects.Subject
    public Object[] getValues() {
        return this.actual.getValues();
    }

    @Override // rx.subjects.Subject
    public T[] getValues(T[] tArr) {
        return this.actual.getValues(tArr);
    }
}
