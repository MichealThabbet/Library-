package rx.internal.operators;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import kotlin.jvm.internal.LongCompanionObject;
import rx.Observable;
import rx.Producer;
import rx.Subscriber;

/* loaded from: classes3.dex */
public final class OperatorBufferWithSize<T> implements Observable.Operator<List<T>, T> {
    final int count;
    final int skip;

    public OperatorBufferWithSize(int i, int i2) {
        if (i <= 0) {
            throw new IllegalArgumentException("count must be greater than 0");
        }
        if (i2 <= 0) {
            throw new IllegalArgumentException("skip must be greater than 0");
        }
        this.count = i;
        this.skip = i2;
    }

    @Override // rx.functions.Func1
    public Subscriber<? super T> call(final Subscriber<? super List<T>> subscriber) {
        if (this.count == this.skip) {
            return new Subscriber<T>(subscriber) { // from class: rx.internal.operators.OperatorBufferWithSize.1
                List<T> buffer;

                @Override // rx.Subscriber
                public void setProducer(final Producer producer) {
                    subscriber.setProducer(new Producer() { // from class: rx.internal.operators.OperatorBufferWithSize.1.1
                        private volatile boolean infinite = false;

                        @Override // rx.Producer
                        public void request(long j) {
                            if (this.infinite) {
                                return;
                            }
                            if (j >= LongCompanionObject.MAX_VALUE / OperatorBufferWithSize.this.count) {
                                this.infinite = true;
                                producer.request(LongCompanionObject.MAX_VALUE);
                            } else {
                                producer.request(j * OperatorBufferWithSize.this.count);
                            }
                        }
                    });
                }

                @Override // rx.Observer
                public void onNext(T t) {
                    if (this.buffer == null) {
                        this.buffer = new ArrayList(OperatorBufferWithSize.this.count);
                    }
                    this.buffer.add(t);
                    if (this.buffer.size() == OperatorBufferWithSize.this.count) {
                        List<T> list = this.buffer;
                        this.buffer = null;
                        subscriber.onNext(list);
                    }
                }

                @Override // rx.Observer
                public void onError(Throwable th) {
                    this.buffer = null;
                    subscriber.onError(th);
                }

                @Override // rx.Observer
                public void onCompleted() {
                    List<T> list = this.buffer;
                    this.buffer = null;
                    if (list != null) {
                        try {
                            subscriber.onNext(list);
                        } catch (Throwable th) {
                            onError(th);
                            return;
                        }
                    }
                    subscriber.onCompleted();
                }
            };
        }
        return new Subscriber<T>(subscriber) { // from class: rx.internal.operators.OperatorBufferWithSize.2
            final List<List<T>> chunks = new LinkedList();
            int index;

            @Override // rx.Subscriber
            public void setProducer(final Producer producer) {
                subscriber.setProducer(new Producer() { // from class: rx.internal.operators.OperatorBufferWithSize.2.1
                    private volatile boolean firstRequest = true;
                    private volatile boolean infinite = false;

                    private void requestInfinite() {
                        this.infinite = true;
                        producer.request(LongCompanionObject.MAX_VALUE);
                    }

                    @Override // rx.Producer
                    public void request(long j) {
                        if (j == 0) {
                            return;
                        }
                        if (j < 0) {
                            throw new IllegalArgumentException("request a negative number: " + j);
                        }
                        if (this.infinite) {
                            return;
                        }
                        if (j == LongCompanionObject.MAX_VALUE) {
                            requestInfinite();
                            return;
                        }
                        if (!this.firstRequest) {
                            if (j >= LongCompanionObject.MAX_VALUE / OperatorBufferWithSize.this.skip) {
                                requestInfinite();
                                return;
                            } else {
                                producer.request(OperatorBufferWithSize.this.skip * j);
                                return;
                            }
                        }
                        this.firstRequest = false;
                        long j2 = j - 1;
                        if (j2 >= (LongCompanionObject.MAX_VALUE - OperatorBufferWithSize.this.count) / OperatorBufferWithSize.this.skip) {
                            requestInfinite();
                        } else {
                            producer.request(OperatorBufferWithSize.this.count + (OperatorBufferWithSize.this.skip * j2));
                        }
                    }
                });
            }

            @Override // rx.Observer
            public void onNext(T t) {
                int i = this.index;
                this.index = i + 1;
                if (i % OperatorBufferWithSize.this.skip == 0) {
                    this.chunks.add(new ArrayList(OperatorBufferWithSize.this.count));
                }
                Iterator<List<T>> it2 = this.chunks.iterator();
                while (it2.hasNext()) {
                    List<T> next = it2.next();
                    next.add(t);
                    if (next.size() == OperatorBufferWithSize.this.count) {
                        it2.remove();
                        subscriber.onNext(next);
                    }
                }
            }

            @Override // rx.Observer
            public void onError(Throwable th) {
                this.chunks.clear();
                subscriber.onError(th);
            }

            @Override // rx.Observer
            public void onCompleted() {
                try {
                    Iterator<List<T>> it2 = this.chunks.iterator();
                    while (it2.hasNext()) {
                        subscriber.onNext(it2.next());
                    }
                    subscriber.onCompleted();
                } catch (Throwable th) {
                    onError(th);
                } finally {
                    this.chunks.clear();
                }
            }
        };
    }
}
