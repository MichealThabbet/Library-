package rx.internal.operators;

import rx.Observable;
import rx.Producer;
import rx.Subscriber;
import rx.exceptions.Exceptions;
import rx.plugins.RxJavaPlugins;

/* loaded from: classes3.dex */
public final class OperatorOnExceptionResumeNextViaObservable<T> implements Observable.Operator<T, T> {
    final Observable<? extends T> resumeSequence;

    public OperatorOnExceptionResumeNextViaObservable(Observable<? extends T> observable) {
        this.resumeSequence = observable;
    }

    @Override // rx.functions.Func1
    public Subscriber<? super T> call(final Subscriber<? super T> subscriber) {
        Subscriber<T> subscriber2 = new Subscriber<T>() { // from class: rx.internal.operators.OperatorOnExceptionResumeNextViaObservable.1
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
                if (th instanceof Exception) {
                    RxJavaPlugins.getInstance().getErrorHandler().handleError(th);
                    unsubscribe();
                    OperatorOnExceptionResumeNextViaObservable.this.resumeSequence.unsafeSubscribe(subscriber);
                    return;
                }
                subscriber.onError(th);
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
                subscriber.setProducer(new Producer() { // from class: rx.internal.operators.OperatorOnExceptionResumeNextViaObservable.1.1
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
