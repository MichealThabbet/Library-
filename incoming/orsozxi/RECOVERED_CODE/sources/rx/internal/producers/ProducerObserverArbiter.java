package rx.internal.producers;

import java.util.ArrayList;
import java.util.List;
import kotlin.jvm.internal.LongCompanionObject;
import rx.Observer;
import rx.Producer;
import rx.Subscriber;

/* loaded from: classes3.dex */
public final class ProducerObserverArbiter<T> implements Producer, Observer<T> {
    static final Producer NULL_PRODUCER = new Producer() { // from class: rx.internal.producers.ProducerObserverArbiter.1
        @Override // rx.Producer
        public void request(long j) {
        }
    };
    final Subscriber<? super T> child;
    Producer currentProducer;
    boolean emitting;
    volatile boolean hasError;
    Producer missedProducer;
    long missedRequested;
    Object missedTerminal;
    List<T> queue;
    long requested;

    public ProducerObserverArbiter(Subscriber<? super T> subscriber) {
        this.child = subscriber;
    }

    @Override // rx.Observer
    public void onNext(T t) {
        synchronized (this) {
            if (this.emitting) {
                List arrayList = this.queue;
                if (arrayList == null) {
                    arrayList = new ArrayList(4);
                    this.queue = arrayList;
                }
                arrayList.add(t);
                return;
            }
            try {
                this.child.onNext(t);
                long j = this.requested;
                if (j != LongCompanionObject.MAX_VALUE) {
                    this.requested = j - 1;
                }
                emitLoop();
            } catch (Throwable th) {
                synchronized (this) {
                    this.emitting = false;
                    throw th;
                }
            }
        }
    }

    @Override // rx.Observer
    public void onError(Throwable th) {
        boolean z;
        synchronized (this) {
            if (this.emitting) {
                this.missedTerminal = th;
                z = false;
            } else {
                this.emitting = true;
                z = true;
            }
        }
        if (z) {
            this.child.onError(th);
        } else {
            this.hasError = true;
        }
    }

    @Override // rx.Observer
    public void onCompleted() {
        synchronized (this) {
            if (this.emitting) {
                this.missedTerminal = true;
            } else {
                this.emitting = true;
                this.child.onCompleted();
            }
        }
    }

    @Override // rx.Producer
    public void request(long j) {
        if (j < 0) {
            throw new IllegalArgumentException("n >= 0 required");
        }
        if (j == 0) {
            return;
        }
        synchronized (this) {
            if (this.emitting) {
                this.missedRequested += j;
                return;
            }
            this.emitting = true;
            try {
                long j2 = this.requested + j;
                if (j2 < 0) {
                    j2 = LongCompanionObject.MAX_VALUE;
                }
                this.requested = j2;
                Producer producer = this.currentProducer;
                if (producer != null) {
                    producer.request(j);
                }
                emitLoop();
            } catch (Throwable th) {
                synchronized (this) {
                    this.emitting = false;
                    throw th;
                }
            }
        }
    }

    public void setProducer(Producer producer) {
        synchronized (this) {
            if (this.emitting) {
                if (producer == null) {
                    producer = NULL_PRODUCER;
                }
                this.missedProducer = producer;
                return;
            }
            this.emitting = true;
            try {
                this.currentProducer = producer;
                long j = this.requested;
                if (producer != null && j != 0) {
                    producer.request(j);
                }
                emitLoop();
            } catch (Throwable th) {
                synchronized (this) {
                    this.emitting = false;
                    throw th;
                }
            }
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:92:0x0002, code lost:
    
        continue;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    void emitLoop() {
        /*
            r15 = this;
            rx.Subscriber<? super T> r0 = r15.child
        L2:
            monitor-enter(r15)
            long r1 = r15.missedRequested     // Catch: java.lang.Throwable -> Lc0
            rx.Producer r3 = r15.missedProducer     // Catch: java.lang.Throwable -> Lc0
            java.lang.Object r4 = r15.missedTerminal     // Catch: java.lang.Throwable -> Lc0
            java.util.List<T> r5 = r15.queue     // Catch: java.lang.Throwable -> Lc0
            r6 = 0
            r7 = 0
            int r9 = (r1 > r7 ? 1 : (r1 == r7 ? 0 : -1))
            if (r9 != 0) goto L1c
            if (r3 != 0) goto L1c
            if (r5 != 0) goto L1c
            if (r4 != 0) goto L1c
            r15.emitting = r6     // Catch: java.lang.Throwable -> Lc0
            monitor-exit(r15)     // Catch: java.lang.Throwable -> Lc0
            return
        L1c:
            r15.missedRequested = r7     // Catch: java.lang.Throwable -> Lc0
            r10 = 0
            r15.missedProducer = r10     // Catch: java.lang.Throwable -> Lc0
            r15.queue = r10     // Catch: java.lang.Throwable -> Lc0
            r15.missedTerminal = r10     // Catch: java.lang.Throwable -> Lc0
            monitor-exit(r15)     // Catch: java.lang.Throwable -> Lc0
            if (r5 == 0) goto L2e
            boolean r11 = r5.isEmpty()
            if (r11 == 0) goto L2f
        L2e:
            r6 = 1
        L2f:
            if (r4 == 0) goto L41
            java.lang.Boolean r11 = java.lang.Boolean.TRUE
            if (r4 == r11) goto L3b
            java.lang.Throwable r4 = (java.lang.Throwable) r4
            r0.onError(r4)
            return
        L3b:
            if (r6 == 0) goto L41
            r0.onCompleted()
            return
        L41:
            if (r5 == 0) goto L74
            java.util.Iterator r4 = r5.iterator()
        L47:
            boolean r6 = r4.hasNext()
            if (r6 == 0) goto L6d
            java.lang.Object r6 = r4.next()
            boolean r11 = r0.isUnsubscribed()
            if (r11 == 0) goto L58
            return
        L58:
            boolean r11 = r15.hasError
            if (r11 == 0) goto L5d
            goto L2
        L5d:
            r0.onNext(r6)     // Catch: java.lang.Throwable -> L61
            goto L47
        L61:
            r1 = move-exception
            rx.exceptions.Exceptions.throwIfFatal(r1)
            java.lang.Throwable r1 = rx.exceptions.OnErrorThrowable.addValueAsLastCause(r1, r6)
            r0.onError(r1)
            return
        L6d:
            int r4 = r5.size()
            long r4 = (long) r4
            long r4 = r4 + r7
            goto L75
        L74:
            r4 = r7
        L75:
            long r11 = r15.requested
            r13 = 9223372036854775807(0x7fffffffffffffff, double:NaN)
            int r6 = (r11 > r13 ? 1 : (r11 == r13 ? 0 : -1))
            if (r6 == 0) goto La0
            if (r9 == 0) goto L88
            long r11 = r11 + r1
            int r6 = (r11 > r7 ? 1 : (r11 == r7 ? 0 : -1))
            if (r6 >= 0) goto L88
            r11 = r13
        L88:
            int r6 = (r4 > r7 ? 1 : (r4 == r7 ? 0 : -1))
            if (r6 == 0) goto L9e
            int r6 = (r11 > r13 ? 1 : (r11 == r13 ? 0 : -1))
            if (r6 == 0) goto L9e
            long r11 = r11 - r4
            int r4 = (r11 > r7 ? 1 : (r11 == r7 ? 0 : -1))
            if (r4 < 0) goto L96
            goto L9e
        L96:
            java.lang.IllegalStateException r0 = new java.lang.IllegalStateException
            java.lang.String r1 = "More produced than requested"
            r0.<init>(r1)
            throw r0
        L9e:
            r15.requested = r11
        La0:
            if (r3 == 0) goto Lb5
            rx.Producer r1 = rx.internal.producers.ProducerObserverArbiter.NULL_PRODUCER
            if (r3 != r1) goto Laa
            r15.currentProducer = r10
            goto L2
        Laa:
            r15.currentProducer = r3
            int r1 = (r11 > r7 ? 1 : (r11 == r7 ? 0 : -1))
            if (r1 == 0) goto L2
            r3.request(r11)
            goto L2
        Lb5:
            rx.Producer r3 = r15.currentProducer
            if (r3 == 0) goto L2
            if (r9 == 0) goto L2
            r3.request(r1)
            goto L2
        Lc0:
            r0 = move-exception
            monitor-exit(r15)     // Catch: java.lang.Throwable -> Lc0
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: rx.internal.producers.ProducerObserverArbiter.emitLoop():void");
    }
}
