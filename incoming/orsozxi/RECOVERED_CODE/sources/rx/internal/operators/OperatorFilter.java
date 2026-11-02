package rx.internal.operators;

import rx.Observable;
import rx.Subscriber;
import rx.exceptions.OnErrorThrowable;
import rx.functions.Func1;

/* loaded from: classes3.dex */
public final class OperatorFilter<T> implements Observable.Operator<T, T> {
    private final Func1<? super T, Boolean> predicate;

    public OperatorFilter(Func1<? super T, Boolean> func1) {
        this.predicate = func1;
    }

    @Override // rx.functions.Func1
    public Subscriber<? super T> call(final Subscriber<? super T> subscriber) {
        return new Subscriber<T>(subscriber) { // from class: rx.internal.operators.OperatorFilter.1
            @Override // rx.Observer
            public void onCompleted() {
                subscriber.onCompleted();
            }

            @Override // rx.Observer
            public void onError(Throwable th) {
                subscriber.onError(th);
            }

            @Override // rx.Observer
            public void onNext(T t) {
                try {
                    if (((Boolean) OperatorFilter.this.predicate.call(t)).booleanValue()) {
                        subscriber.onNext(t);
                    } else {
                        request(1L);
                    }
                } catch (Throwable th) {
                    subscriber.onError(OnErrorThrowable.addValueAsLastCause(th, t));
                }
            }
        };
    }
}
