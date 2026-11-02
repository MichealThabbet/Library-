package rx.internal.operators;

import java.util.concurrent.atomic.AtomicBoolean;
import kotlin.jvm.internal.LongCompanionObject;
import rx.Observable;
import rx.Producer;
import rx.Subscriber;
import rx.exceptions.Exceptions;
import rx.exceptions.OnErrorThrowable;
import rx.functions.Func0;
import rx.functions.Func2;

/* loaded from: classes3.dex */
public final class OperatorScan<R, T> implements Observable.Operator<R, T> {
    private static final Object NO_INITIAL_VALUE = new Object();
    private final Func2<R, ? super T, R> accumulator;
    private final Func0<R> initialValueFactory;

    public OperatorScan(final R r, Func2<R, ? super T, R> func2) {
        this((Func0) new Func0<R>() { // from class: rx.internal.operators.OperatorScan.1
            @Override // rx.functions.Func0, java.util.concurrent.Callable
            public R call() {
                return (R) r;
            }
        }, (Func2) func2);
    }

    public OperatorScan(Func0<R> func0, Func2<R, ? super T, R> func2) {
        this.initialValueFactory = func0;
        this.accumulator = func2;
    }

    public OperatorScan(Func2<R, ? super T, R> func2) {
        this(NO_INITIAL_VALUE, func2);
    }

    @Override // rx.functions.Func1
    public Subscriber<? super T> call(final Subscriber<? super R> subscriber) {
        return new Subscriber<T>(subscriber) { // from class: rx.internal.operators.OperatorScan.2
            private final R initialValue;
            boolean initialized;
            private R value;

            {
                R r = (R) OperatorScan.this.initialValueFactory.call();
                this.initialValue = r;
                this.value = r;
                this.initialized = false;
            }

            /* JADX WARN: Multi-variable type inference failed */
            @Override // rx.Observer
            public void onNext(T t) {
                emitInitialValueIfNeeded(subscriber);
                if (this.value != OperatorScan.NO_INITIAL_VALUE) {
                    try {
                        this.value = (R) OperatorScan.this.accumulator.call(this.value, t);
                    } catch (Throwable th) {
                        Exceptions.throwIfFatal(th);
                        subscriber.onError(OnErrorThrowable.addValueAsLastCause(th, t));
                        return;
                    }
                } else {
                    this.value = t;
                }
                subscriber.onNext(this.value);
            }

            @Override // rx.Observer
            public void onError(Throwable th) {
                subscriber.onError(th);
            }

            @Override // rx.Observer
            public void onCompleted() {
                emitInitialValueIfNeeded(subscriber);
                subscriber.onCompleted();
            }

            private void emitInitialValueIfNeeded(Subscriber<? super R> subscriber2) {
                if (this.initialized) {
                    return;
                }
                this.initialized = true;
                if (this.initialValue != OperatorScan.NO_INITIAL_VALUE) {
                    subscriber2.onNext(this.initialValue);
                }
            }

            @Override // rx.Subscriber
            public void setProducer(final Producer producer) {
                subscriber.setProducer(new Producer() { // from class: rx.internal.operators.OperatorScan.2.1
                    final AtomicBoolean once = new AtomicBoolean();
                    final AtomicBoolean excessive = new AtomicBoolean();

                    @Override // rx.Producer
                    public void request(long j) {
                        if (this.once.compareAndSet(false, true)) {
                            if (AnonymousClass2.this.initialValue == OperatorScan.NO_INITIAL_VALUE || j == LongCompanionObject.MAX_VALUE) {
                                producer.request(j);
                                return;
                            } else if (j == 1) {
                                this.excessive.set(true);
                                producer.request(1L);
                                return;
                            } else {
                                producer.request(j - 1);
                                return;
                            }
                        }
                        if (j > 1 && this.excessive.compareAndSet(true, false) && j != LongCompanionObject.MAX_VALUE) {
                            producer.request(j - 1);
                        } else {
                            producer.request(j);
                        }
                    }
                });
            }
        };
    }
}
