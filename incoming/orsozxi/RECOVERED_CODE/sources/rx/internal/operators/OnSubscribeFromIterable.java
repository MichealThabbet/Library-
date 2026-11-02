package rx.internal.operators;

import java.util.Iterator;
import java.util.concurrent.atomic.AtomicLong;
import kotlin.jvm.internal.LongCompanionObject;
import rx.Observable;
import rx.Producer;
import rx.Subscriber;

/* loaded from: classes3.dex */
public final class OnSubscribeFromIterable<T> implements Observable.OnSubscribe<T> {
    final Iterable<? extends T> is;

    public OnSubscribeFromIterable(Iterable<? extends T> iterable) {
        if (iterable == null) {
            throw new NullPointerException("iterable must not be null");
        }
        this.is = iterable;
    }

    @Override // rx.functions.Action1
    public void call(Subscriber<? super T> subscriber) {
        Iterator<? extends T> it2 = this.is.iterator();
        if (!it2.hasNext() && !subscriber.isUnsubscribed()) {
            subscriber.onCompleted();
        } else {
            subscriber.setProducer(new IterableProducer(subscriber, it2));
        }
    }

    private static final class IterableProducer<T> extends AtomicLong implements Producer {
        private static final long serialVersionUID = -8730475647105475802L;

        /* renamed from: it, reason: collision with root package name */
        private final Iterator<? extends T> f42it;
        private final Subscriber<? super T> o;

        private IterableProducer(Subscriber<? super T> subscriber, Iterator<? extends T> it2) {
            this.o = subscriber;
            this.f42it = it2;
        }

        @Override // rx.Producer
        public void request(long j) {
            if (get() == LongCompanionObject.MAX_VALUE) {
                return;
            }
            if (j == LongCompanionObject.MAX_VALUE && compareAndSet(0L, LongCompanionObject.MAX_VALUE)) {
                fastpath();
            } else {
                if (j <= 0 || BackpressureUtils.getAndAddRequest(this, j) != 0) {
                    return;
                }
                slowpath(j);
            }
        }

        void slowpath(long j) {
            Subscriber<? super T> subscriber = this.o;
            Iterator<? extends T> it2 = this.f42it;
            do {
                long j2 = j;
                while (!subscriber.isUnsubscribed()) {
                    if (!it2.hasNext()) {
                        if (subscriber.isUnsubscribed()) {
                            return;
                        }
                        subscriber.onCompleted();
                        return;
                    } else {
                        j2--;
                        if (j2 >= 0) {
                            subscriber.onNext(it2.next());
                        } else {
                            j = addAndGet(-j);
                        }
                    }
                }
                return;
            } while (j != 0);
        }

        void fastpath() {
            Subscriber<? super T> subscriber = this.o;
            Iterator<? extends T> it2 = this.f42it;
            while (!subscriber.isUnsubscribed()) {
                if (it2.hasNext()) {
                    subscriber.onNext(it2.next());
                } else {
                    if (subscriber.isUnsubscribed()) {
                        return;
                    }
                    subscriber.onCompleted();
                    return;
                }
            }
        }
    }
}
