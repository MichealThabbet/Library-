package rx.internal.operators;

import java.util.Arrays;
import rx.Observable;
import rx.Producer;
import rx.Subscriber;
import rx.exceptions.CompositeException;
import rx.exceptions.Exceptions;
import rx.functions.Func1;
import rx.plugins.RxJavaPlugins;

/* loaded from: classes3.dex */
public final class OperatorOnErrorReturn<T> implements Observable.Operator<T, T> {
    final Func1<Throwable, ? extends T> resultFunction;

    public OperatorOnErrorReturn(Func1<Throwable, ? extends T> func1) {
        this.resultFunction = func1;
    }

    @Override // rx.functions.Func1
    public Subscriber<? super T> call(final Subscriber<? super T> subscriber) {
        Subscriber<T> subscriber2 = new Subscriber<T>() { // from class: rx.internal.operators.OperatorOnErrorReturn.1
            private boolean done = false;

            @Override // rx.Observer
            public void onNext(T t) {
                if (this.done) {
                    return;
                }
                subscriber.onNext(t);
            }

            @Override // rx.Observer
            public void onError(Throwable th) {
                if (this.done) {
                    Exceptions.throwIfFatal(th);
                    return;
                }
                this.done = true;
                try {
                    RxJavaPlugins.getInstance().getErrorHandler().handleError(th);
                    unsubscribe();
                    subscriber.onNext(OperatorOnErrorReturn.this.resultFunction.call(th));
                    subscriber.onCompleted();
                } catch (Throwable th2) {
                    subscriber.onError(new CompositeException(Arrays.asList(th, th2)));
                }
            }

            @Override // rx.Observer
            public void onCompleted() {
                if (this.done) {
                    return;
                }
                this.done = true;
                subscriber.onCompleted();
            }

            @Override // rx.Subscriber
            public void setProducer(final Producer producer) {
                subscriber.setProducer(new Producer() { // from class: rx.internal.operators.OperatorOnErrorReturn.1.1
                    @Override // rx.Producer
                    public void request(long j) {
                        producer.request(j);
                    }
                });
            }
        };
        subscriber.add(subscriber2);
        return subscriber2;
    }
}
