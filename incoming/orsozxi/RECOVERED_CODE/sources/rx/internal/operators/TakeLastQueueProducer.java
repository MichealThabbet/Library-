package rx.internal.operators;

import java.util.Deque;
import java.util.concurrent.atomic.AtomicLongFieldUpdater;
import kotlin.jvm.internal.LongCompanionObject;
import rx.Producer;
import rx.Subscriber;

/* loaded from: classes3.dex */
final class TakeLastQueueProducer<T> implements Producer {
    private static final AtomicLongFieldUpdater<TakeLastQueueProducer> REQUESTED_UPDATER = AtomicLongFieldUpdater.newUpdater(TakeLastQueueProducer.class, "requested");
    private final Deque<Object> deque;
    private final NotificationLite<T> notification;
    private final Subscriber<? super T> subscriber;
    private volatile boolean emittingStarted = false;
    private volatile long requested = 0;

    public TakeLastQueueProducer(NotificationLite<T> notificationLite, Deque<Object> deque, Subscriber<? super T> subscriber) {
        this.notification = notificationLite;
        this.deque = deque;
        this.subscriber = subscriber;
    }

    void startEmitting() {
        if (this.emittingStarted) {
            return;
        }
        this.emittingStarted = true;
        emit(0L);
    }

    @Override // rx.Producer
    public void request(long j) {
        long andAddRequest;
        if (this.requested == LongCompanionObject.MAX_VALUE) {
            return;
        }
        if (j == LongCompanionObject.MAX_VALUE) {
            andAddRequest = REQUESTED_UPDATER.getAndSet(this, LongCompanionObject.MAX_VALUE);
        } else {
            andAddRequest = BackpressureUtils.getAndAddRequest(REQUESTED_UPDATER, this, j);
        }
        if (this.emittingStarted) {
            emit(andAddRequest);
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:35:0x0077, code lost:
    
        r8 = r12.requested;
        r13 = r8 - r0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:36:0x007e, code lost:
    
        if (r8 != kotlin.jvm.internal.LongCompanionObject.MAX_VALUE) goto L38;
     */
    /* JADX WARN: Code restructure failed: missing block: B:39:0x0089, code lost:
    
        if (rx.internal.operators.TakeLastQueueProducer.REQUESTED_UPDATER.compareAndSet(r12, r8, r13) == false) goto L59;
     */
    /* JADX WARN: Code restructure failed: missing block: B:41:0x008d, code lost:
    
        if (r13 != 0) goto L55;
     */
    /* JADX WARN: Code restructure failed: missing block: B:62:?, code lost:
    
        return;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    void emit(long r13) {
        /*
            r12 = this;
            long r0 = r12.requested
            r2 = 9223372036854775807(0x7fffffffffffffff, double:NaN)
            r4 = 0
            int r6 = (r0 > r2 ? 1 : (r0 == r2 ? 0 : -1))
            if (r6 != 0) goto L4a
            int r0 = (r13 > r4 ? 1 : (r13 == r4 ? 0 : -1))
            if (r0 != 0) goto L8f
            java.util.Deque<java.lang.Object> r13 = r12.deque     // Catch: java.lang.Throwable -> L37
            java.util.Iterator r13 = r13.iterator()     // Catch: java.lang.Throwable -> L37
        L17:
            boolean r14 = r13.hasNext()     // Catch: java.lang.Throwable -> L37
            if (r14 == 0) goto L3d
            java.lang.Object r14 = r13.next()     // Catch: java.lang.Throwable -> L37
            rx.Subscriber<? super T> r0 = r12.subscriber     // Catch: java.lang.Throwable -> L37
            boolean r0 = r0.isUnsubscribed()     // Catch: java.lang.Throwable -> L37
            if (r0 == 0) goto L2f
            java.util.Deque<java.lang.Object> r13 = r12.deque
            r13.clear()
            return
        L2f:
            rx.internal.operators.NotificationLite<T> r0 = r12.notification     // Catch: java.lang.Throwable -> L37
            rx.Subscriber<? super T> r1 = r12.subscriber     // Catch: java.lang.Throwable -> L37
            r0.accept(r1, r14)     // Catch: java.lang.Throwable -> L37
            goto L17
        L37:
            r13 = move-exception
            rx.Subscriber<? super T> r14 = r12.subscriber     // Catch: java.lang.Throwable -> L43
            r14.onError(r13)     // Catch: java.lang.Throwable -> L43
        L3d:
            java.util.Deque<java.lang.Object> r13 = r12.deque
            r13.clear()
            goto L8f
        L43:
            r13 = move-exception
            java.util.Deque<java.lang.Object> r14 = r12.deque
            r14.clear()
            throw r13
        L4a:
            int r0 = (r13 > r4 ? 1 : (r13 == r4 ? 0 : -1))
            if (r0 != 0) goto L8f
        L4e:
            long r13 = r12.requested
            r0 = 0
        L51:
            r6 = 1
            long r13 = r13 - r6
            int r1 = (r13 > r4 ? 1 : (r13 == r4 ? 0 : -1))
            if (r1 < 0) goto L77
            java.util.Deque<java.lang.Object> r1 = r12.deque
            java.lang.Object r1 = r1.poll()
            if (r1 == 0) goto L77
            rx.Subscriber<? super T> r6 = r12.subscriber
            boolean r6 = r6.isUnsubscribed()
            if (r6 == 0) goto L69
            return
        L69:
            rx.internal.operators.NotificationLite<T> r6 = r12.notification
            rx.Subscriber<? super T> r7 = r12.subscriber
            boolean r1 = r6.accept(r7, r1)
            if (r1 == 0) goto L74
            return
        L74:
            int r0 = r0 + 1
            goto L51
        L77:
            long r8 = r12.requested
            long r13 = (long) r0
            long r13 = r8 - r13
            int r1 = (r8 > r2 ? 1 : (r8 == r2 ? 0 : -1))
            if (r1 != 0) goto L81
            goto L4e
        L81:
            java.util.concurrent.atomic.AtomicLongFieldUpdater<rx.internal.operators.TakeLastQueueProducer> r6 = rx.internal.operators.TakeLastQueueProducer.REQUESTED_UPDATER
            r7 = r12
            r10 = r13
            boolean r1 = r6.compareAndSet(r7, r8, r10)
            if (r1 == 0) goto L77
            int r0 = (r13 > r4 ? 1 : (r13 == r4 ? 0 : -1))
            if (r0 != 0) goto L4e
        L8f:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: rx.internal.operators.TakeLastQueueProducer.emit(long):void");
    }
}
