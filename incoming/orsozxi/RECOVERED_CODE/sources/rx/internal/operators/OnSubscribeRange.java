package rx.internal.operators;

import java.util.concurrent.atomic.AtomicLong;
import kotlin.jvm.internal.LongCompanionObject;
import rx.Observable;
import rx.Producer;
import rx.Subscriber;

/* loaded from: classes3.dex */
public final class OnSubscribeRange implements Observable.OnSubscribe<Integer> {
    private final int end;
    private final int start;

    public OnSubscribeRange(int i, int i2) {
        this.start = i;
        this.end = i2;
    }

    @Override // rx.functions.Action1
    public void call(Subscriber<? super Integer> subscriber) {
        subscriber.setProducer(new RangeProducer(subscriber, this.start, this.end));
    }

    private static final class RangeProducer extends AtomicLong implements Producer {
        private static final long serialVersionUID = 4114392207069098388L;
        private final int end;
        private long index;
        private final Subscriber<? super Integer> o;

        private RangeProducer(Subscriber<? super Integer> subscriber, int i, int i2) {
            this.o = subscriber;
            this.index = i;
            this.end = i2;
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
            long j2 = this.index;
            while (true) {
                long j3 = (this.end - j2) + 1;
                long jMin = Math.min(j3, j);
                boolean z = j3 <= j;
                long j4 = jMin + j2;
                Subscriber<? super Integer> subscriber = this.o;
                while (j2 != j4) {
                    if (subscriber.isUnsubscribed()) {
                        return;
                    }
                    subscriber.onNext(Integer.valueOf((int) j2));
                    j2++;
                }
                if (z) {
                    if (subscriber.isUnsubscribed()) {
                        return;
                    }
                    subscriber.onCompleted();
                    return;
                } else {
                    this.index = j4;
                    j = addAndGet(-jMin);
                    if (j == 0) {
                        return;
                    } else {
                        j2 = j4;
                    }
                }
            }
        }

        void fastpath() {
            long j = this.end + 1;
            Subscriber<? super Integer> subscriber = this.o;
            for (long j2 = this.index; j2 != j; j2++) {
                if (subscriber.isUnsubscribed()) {
                    return;
                }
                subscriber.onNext(Integer.valueOf((int) j2));
            }
            if (subscriber.isUnsubscribed()) {
                return;
            }
            subscriber.onCompleted();
        }
    }
}
