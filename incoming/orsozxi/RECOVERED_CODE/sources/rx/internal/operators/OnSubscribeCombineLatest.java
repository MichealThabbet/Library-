package rx.internal.operators;

import java.util.BitSet;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicLongFieldUpdater;
import rx.Observable;
import rx.Producer;
import rx.Subscriber;
import rx.exceptions.MissingBackpressureException;
import rx.functions.FuncN;
import rx.internal.util.RxRingBuffer;

/* loaded from: classes3.dex */
public final class OnSubscribeCombineLatest<T, R> implements Observable.OnSubscribe<R> {
    final FuncN<? extends R> combinator;
    final List<? extends Observable<? extends T>> sources;

    public OnSubscribeCombineLatest(List<? extends Observable<? extends T>> list, FuncN<? extends R> funcN) {
        this.sources = list;
        this.combinator = funcN;
        if (list.size() > RxRingBuffer.SIZE) {
            throw new IllegalArgumentException("More than RxRingBuffer.SIZE sources to combineLatest is not supported.");
        }
    }

    @Override // rx.functions.Action1
    public void call(Subscriber<? super R> subscriber) {
        if (this.sources.isEmpty()) {
            subscriber.onCompleted();
        } else if (this.sources.size() == 1) {
            subscriber.setProducer(new SingleSourceProducer(subscriber, this.sources.get(0), this.combinator));
        } else {
            subscriber.setProducer(new MultiSourceProducer(subscriber, this.sources, this.combinator));
        }
    }

    static final class MultiSourceProducer<T, R> implements Producer {
        private static final AtomicLongFieldUpdater<MultiSourceProducer> WIP = AtomicLongFieldUpdater.newUpdater(MultiSourceProducer.class, "counter");
        private final Subscriber<? super R> child;
        private final Object[] collectedValues;
        private final FuncN<? extends R> combinator;
        private final BitSet completion;
        private volatile int completionCount;
        private volatile long counter;
        private final BitSet haveValues;
        private volatile int haveValuesCount;
        private final List<? extends Observable<? extends T>> sources;
        private final MultiSourceRequestableSubscriber<T, R>[] subscribers;
        private final AtomicBoolean started = new AtomicBoolean();
        private final AtomicLong requested = new AtomicLong();
        private final RxRingBuffer buffer = RxRingBuffer.getSpmcInstance();

        public MultiSourceProducer(Subscriber<? super R> subscriber, List<? extends Observable<? extends T>> list, FuncN<? extends R> funcN) {
            this.sources = list;
            this.child = subscriber;
            this.combinator = funcN;
            int size = list.size();
            this.subscribers = new MultiSourceRequestableSubscriber[size];
            this.collectedValues = new Object[size];
            this.haveValues = new BitSet(size);
            this.completion = new BitSet(size);
        }

        @Override // rx.Producer
        public void request(long j) {
            BackpressureUtils.getAndAddRequest(this.requested, j);
            if (!this.started.get()) {
                int i = 0;
                if (this.started.compareAndSet(false, true)) {
                    int size = RxRingBuffer.SIZE / this.sources.size();
                    int size2 = RxRingBuffer.SIZE % this.sources.size();
                    while (i < this.sources.size()) {
                        Observable<? extends T> observable = this.sources.get(i);
                        MultiSourceRequestableSubscriber<T, R> multiSourceRequestableSubscriber = new MultiSourceRequestableSubscriber<>(i, i == this.sources.size() - 1 ? size + size2 : size, this.child, this);
                        this.subscribers[i] = multiSourceRequestableSubscriber;
                        observable.unsafeSubscribe(multiSourceRequestableSubscriber);
                        i++;
                    }
                }
            }
            tick();
        }

        void tick() {
            Object objPoll;
            if (WIP.getAndIncrement(this) == 0) {
                int i = 0;
                do {
                    if (this.requested.get() > 0 && (objPoll = this.buffer.poll()) != null) {
                        if (this.buffer.isCompleted(objPoll)) {
                            this.child.onCompleted();
                        } else {
                            this.buffer.accept(objPoll, this.child);
                            i++;
                            this.requested.decrementAndGet();
                        }
                    }
                } while (WIP.decrementAndGet(this) > 0);
                if (i > 0) {
                    for (MultiSourceRequestableSubscriber<T, R> multiSourceRequestableSubscriber : this.subscribers) {
                        multiSourceRequestableSubscriber.requestUpTo(i);
                    }
                }
            }
        }

        public void onCompleted(int i, boolean z) {
            boolean z2;
            if (!z) {
                this.child.onCompleted();
                return;
            }
            synchronized (this) {
                z2 = false;
                if (!this.completion.get(i)) {
                    this.completion.set(i);
                    this.completionCount++;
                    if (this.completionCount == this.collectedValues.length) {
                        z2 = true;
                    }
                }
            }
            if (z2) {
                this.buffer.onCompleted();
                tick();
            }
        }

        public boolean onNext(int i, T t) {
            synchronized (this) {
                if (!this.haveValues.get(i)) {
                    this.haveValues.set(i);
                    this.haveValuesCount++;
                }
                this.collectedValues[i] = t;
                if (this.haveValuesCount != this.collectedValues.length) {
                    return false;
                }
                try {
                    this.buffer.onNext(this.combinator.call(this.collectedValues));
                } catch (MissingBackpressureException e) {
                    onError(e);
                } catch (Throwable th) {
                    onError(th);
                }
                tick();
                return true;
            }
        }

        public void onError(Throwable th) {
            this.child.onError(th);
        }
    }

    static final class MultiSourceRequestableSubscriber<T, R> extends Subscriber<T> {
        final AtomicLong emitted;
        boolean hasValue;
        final int index;
        final MultiSourceProducer<T, R> producer;

        public MultiSourceRequestableSubscriber(int i, int i2, Subscriber<? super R> subscriber, MultiSourceProducer<T, R> multiSourceProducer) {
            super(subscriber);
            this.emitted = new AtomicLong();
            this.hasValue = false;
            this.index = i;
            this.producer = multiSourceProducer;
            request(i2);
        }

        public void requestUpTo(long j) {
            long j2;
            long jMin;
            do {
                j2 = this.emitted.get();
                jMin = Math.min(j2, j);
            } while (!this.emitted.compareAndSet(j2, j2 - jMin));
            request(jMin);
        }

        @Override // rx.Observer
        public void onCompleted() {
            this.producer.onCompleted(this.index, this.hasValue);
        }

        @Override // rx.Observer
        public void onError(Throwable th) {
            this.producer.onError(th);
        }

        @Override // rx.Observer
        public void onNext(T t) {
            this.hasValue = true;
            this.emitted.incrementAndGet();
            if (this.producer.onNext(this.index, t)) {
                return;
            }
            request(1L);
        }
    }

    static final class SingleSourceProducer<T, R> implements Producer {
        final Subscriber<? super R> child;
        final FuncN<? extends R> combinator;
        final Observable<? extends T> source;
        final AtomicBoolean started = new AtomicBoolean();
        final SingleSourceRequestableSubscriber<T, R> subscriber;

        public SingleSourceProducer(Subscriber<? super R> subscriber, Observable<? extends T> observable, FuncN<? extends R> funcN) {
            this.source = observable;
            this.child = subscriber;
            this.combinator = funcN;
            this.subscriber = new SingleSourceRequestableSubscriber<>(subscriber, funcN);
        }

        @Override // rx.Producer
        public void request(long j) {
            this.subscriber.requestMore(j);
            if (this.started.compareAndSet(false, true)) {
                this.source.unsafeSubscribe(this.subscriber);
            }
        }
    }

    static final class SingleSourceRequestableSubscriber<T, R> extends Subscriber<T> {
        private final Subscriber<? super R> child;
        private final FuncN<? extends R> combinator;

        SingleSourceRequestableSubscriber(Subscriber<? super R> subscriber, FuncN<? extends R> funcN) {
            super(subscriber);
            this.child = subscriber;
            this.combinator = funcN;
        }

        public void requestMore(long j) {
            request(j);
        }

        @Override // rx.Observer
        public void onNext(T t) {
            this.child.onNext(this.combinator.call(t));
        }

        @Override // rx.Observer
        public void onError(Throwable th) {
            this.child.onError(th);
        }

        @Override // rx.Observer
        public void onCompleted() {
            this.child.onCompleted();
        }
    }
}
