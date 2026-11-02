package rx.internal.operators;

import java.util.NoSuchElementException;
import java.util.concurrent.atomic.AtomicBoolean;
import rx.Observable;
import rx.Producer;
import rx.Subscriber;

/* loaded from: classes3.dex */
public final class OperatorSingle<T> implements Observable.Operator<T, T> {
    private final T defaultValue;
    private final boolean hasDefaultValue;

    private static class Holder {
        static final OperatorSingle<?> INSTANCE = new OperatorSingle<>();

        private Holder() {
        }
    }

    public static <T> OperatorSingle<T> instance() {
        return (OperatorSingle<T>) Holder.INSTANCE;
    }

    private OperatorSingle() {
        this(false, null);
    }

    public OperatorSingle(T t) {
        this(true, t);
    }

    private OperatorSingle(boolean z, T t) {
        this.hasDefaultValue = z;
        this.defaultValue = t;
    }

    @Override // rx.functions.Func1
    public Subscriber<? super T> call(Subscriber<? super T> subscriber) {
        final ParentSubscriber parentSubscriber = new ParentSubscriber(subscriber, this.hasDefaultValue, this.defaultValue);
        subscriber.setProducer(new Producer() { // from class: rx.internal.operators.OperatorSingle.1
            private final AtomicBoolean requestedTwo = new AtomicBoolean(false);

            @Override // rx.Producer
            public void request(long j) {
                if (j <= 0 || !this.requestedTwo.compareAndSet(false, true)) {
                    return;
                }
                parentSubscriber.requestMore(2L);
            }
        });
        subscriber.add(parentSubscriber);
        return parentSubscriber;
    }

    private static final class ParentSubscriber<T> extends Subscriber<T> {
        private final Subscriber<? super T> child;
        private final T defaultValue;
        private final boolean hasDefaultValue;
        private T value;
        private boolean isNonEmpty = false;
        private boolean hasTooManyElements = false;

        ParentSubscriber(Subscriber<? super T> subscriber, boolean z, T t) {
            this.child = subscriber;
            this.hasDefaultValue = z;
            this.defaultValue = t;
        }

        void requestMore(long j) {
            request(j);
        }

        @Override // rx.Observer
        public void onNext(T t) {
            if (this.isNonEmpty) {
                this.hasTooManyElements = true;
                this.child.onError(new IllegalArgumentException("Sequence contains too many elements"));
                unsubscribe();
            } else {
                this.value = t;
                this.isNonEmpty = true;
            }
        }

        @Override // rx.Observer
        public void onCompleted() {
            if (this.hasTooManyElements) {
                return;
            }
            if (this.isNonEmpty) {
                this.child.onNext(this.value);
                this.child.onCompleted();
            } else if (this.hasDefaultValue) {
                this.child.onNext(this.defaultValue);
                this.child.onCompleted();
            } else {
                this.child.onError(new NoSuchElementException("Sequence contains no elements"));
            }
        }

        @Override // rx.Observer
        public void onError(Throwable th) {
            this.child.onError(th);
        }
    }
}
