package rx.internal.operators;

import rx.Observable;
import rx.Producer;
import rx.Scheduler;
import rx.Subscriber;
import rx.functions.Action0;

/* loaded from: classes3.dex */
public class OperatorSubscribeOn<T> implements Observable.Operator<T, Observable<T>> {
    private final Scheduler scheduler;

    public OperatorSubscribeOn(Scheduler scheduler) {
        this.scheduler = scheduler;
    }

    @Override // rx.functions.Func1
    public Subscriber<? super Observable<T>> call(Subscriber<? super T> subscriber) {
        Scheduler.Worker workerCreateWorker = this.scheduler.createWorker();
        subscriber.add(workerCreateWorker);
        return new AnonymousClass1(subscriber, subscriber, workerCreateWorker);
    }

    /* renamed from: rx.internal.operators.OperatorSubscribeOn$1, reason: invalid class name */
    class AnonymousClass1 extends Subscriber<Observable<T>> {
        final /* synthetic */ Scheduler.Worker val$inner;
        final /* synthetic */ Subscriber val$subscriber;

        @Override // rx.Observer
        public void onCompleted() {
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        AnonymousClass1(Subscriber subscriber, Subscriber subscriber2, Scheduler.Worker worker) {
            super(subscriber);
            this.val$subscriber = subscriber2;
            this.val$inner = worker;
        }

        @Override // rx.Observer
        public void onError(Throwable th) {
            this.val$subscriber.onError(th);
        }

        /* renamed from: rx.internal.operators.OperatorSubscribeOn$1$1, reason: invalid class name and collision with other inner class name */
        class C00781 implements Action0 {
            final /* synthetic */ Observable val$o;

            C00781(Observable observable) {
                this.val$o = observable;
            }

            @Override // rx.functions.Action0
            public void call() {
                final Thread threadCurrentThread = Thread.currentThread();
                this.val$o.unsafeSubscribe(new Subscriber<T>(AnonymousClass1.this.val$subscriber) { // from class: rx.internal.operators.OperatorSubscribeOn.1.1.1
                    @Override // rx.Observer
                    public void onCompleted() {
                        AnonymousClass1.this.val$subscriber.onCompleted();
                    }

                    @Override // rx.Observer
                    public void onError(Throwable th) {
                        AnonymousClass1.this.val$subscriber.onError(th);
                    }

                    @Override // rx.Observer
                    public void onNext(T t) {
                        AnonymousClass1.this.val$subscriber.onNext(t);
                    }

                    @Override // rx.Subscriber
                    public void setProducer(final Producer producer) {
                        AnonymousClass1.this.val$subscriber.setProducer(new Producer() { // from class: rx.internal.operators.OperatorSubscribeOn.1.1.1.1
                            @Override // rx.Producer
                            public void request(final long j) {
                                if (Thread.currentThread() == threadCurrentThread) {
                                    producer.request(j);
                                } else {
                                    AnonymousClass1.this.val$inner.schedule(new Action0() { // from class: rx.internal.operators.OperatorSubscribeOn.1.1.1.1.1
                                        @Override // rx.functions.Action0
                                        public void call() {
                                            producer.request(j);
                                        }
                                    });
                                }
                            }
                        });
                    }
                });
            }
        }

        @Override // rx.Observer
        public void onNext(Observable<T> observable) {
            this.val$inner.schedule(new C00781(observable));
        }
    }
}
