package rx.internal.operators;

import java.util.ArrayList;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicLong;
import kotlin.jvm.internal.LongCompanionObject;
import rx.Observable;
import rx.Producer;
import rx.Subscriber;
import rx.exceptions.CompositeException;
import rx.exceptions.MissingBackpressureException;
import rx.internal.util.RxRingBuffer;
import rx.internal.util.ScalarSynchronousObservable;
import rx.subscriptions.CompositeSubscription;

/* loaded from: classes3.dex */
public final class OperatorMerge<T> implements Observable.Operator<T, Observable<? extends T>> {
    final boolean delayErrors;
    final int maxConcurrent;

    private static final class HolderNoDelay {
        static final OperatorMerge<Object> INSTANCE = new OperatorMerge<>(false, Integer.MAX_VALUE);

        private HolderNoDelay() {
        }
    }

    private static final class HolderDelayErrors {
        static final OperatorMerge<Object> INSTANCE = new OperatorMerge<>(true, Integer.MAX_VALUE);

        private HolderDelayErrors() {
        }
    }

    public static <T> OperatorMerge<T> instance(boolean z) {
        if (z) {
            return (OperatorMerge<T>) HolderDelayErrors.INSTANCE;
        }
        return (OperatorMerge<T>) HolderNoDelay.INSTANCE;
    }

    public static <T> OperatorMerge<T> instance(boolean z, int i) {
        if (i == Integer.MAX_VALUE) {
            return instance(z);
        }
        return new OperatorMerge<>(z, i);
    }

    private OperatorMerge(boolean z, int i) {
        this.delayErrors = z;
        this.maxConcurrent = i;
    }

    @Override // rx.functions.Func1
    public Subscriber<Observable<? extends T>> call(Subscriber<? super T> subscriber) {
        MergeSubscriber mergeSubscriber = new MergeSubscriber(subscriber, this.delayErrors, this.maxConcurrent);
        MergeProducer<T> mergeProducer = new MergeProducer<>(mergeSubscriber);
        mergeSubscriber.producer = mergeProducer;
        subscriber.add(mergeSubscriber);
        subscriber.setProducer(mergeProducer);
        return mergeSubscriber;
    }

    static final class MergeProducer<T> extends AtomicLong implements Producer {
        private static final long serialVersionUID = -1214379189873595503L;
        final MergeSubscriber<T> subscriber;

        public MergeProducer(MergeSubscriber<T> mergeSubscriber) {
            this.subscriber = mergeSubscriber;
        }

        @Override // rx.Producer
        public void request(long j) throws Throwable {
            if (j <= 0) {
                if (j < 0) {
                    throw new IllegalArgumentException("n >= 0 required");
                }
            } else {
                if (get() == LongCompanionObject.MAX_VALUE) {
                    return;
                }
                BackpressureUtils.getAndAddRequest(this, j);
                this.subscriber.emit();
            }
        }

        public long produced(int i) {
            return addAndGet(-i);
        }
    }

    static final class MergeSubscriber<T> extends Subscriber<Observable<? extends T>> {
        static final InnerSubscriber<?>[] EMPTY = new InnerSubscriber[0];
        final Subscriber<? super T> child;
        final boolean delayErrors;
        volatile boolean done;
        boolean emitting;
        volatile ConcurrentLinkedQueue<Throwable> errors;
        long lastId;
        int lastIndex;
        final int maxConcurrent;
        boolean missed;
        MergeProducer<T> producer;
        volatile RxRingBuffer queue;
        volatile CompositeSubscription subscriptions;
        long uniqueId;
        final NotificationLite<T> nl = NotificationLite.instance();
        final Object innerGuard = new Object();
        volatile InnerSubscriber<?>[] innerSubscribers = EMPTY;

        public MergeSubscriber(Subscriber<? super T> subscriber, boolean z, int i) {
            this.child = subscriber;
            this.delayErrors = z;
            this.maxConcurrent = i;
            request(Math.min(i, RxRingBuffer.SIZE));
        }

        Queue<Throwable> getOrCreateErrorQueue() {
            ConcurrentLinkedQueue<Throwable> concurrentLinkedQueue = this.errors;
            if (concurrentLinkedQueue == null) {
                synchronized (this) {
                    concurrentLinkedQueue = this.errors;
                    if (concurrentLinkedQueue == null) {
                        concurrentLinkedQueue = new ConcurrentLinkedQueue<>();
                        this.errors = concurrentLinkedQueue;
                    }
                }
            }
            return concurrentLinkedQueue;
        }

        CompositeSubscription getOrCreateComposite() {
            CompositeSubscription compositeSubscription;
            CompositeSubscription compositeSubscription2 = this.subscriptions;
            if (compositeSubscription2 != null) {
                return compositeSubscription2;
            }
            boolean z = false;
            synchronized (this) {
                compositeSubscription = this.subscriptions;
                if (compositeSubscription == null) {
                    CompositeSubscription compositeSubscription3 = new CompositeSubscription();
                    this.subscriptions = compositeSubscription3;
                    compositeSubscription = compositeSubscription3;
                    z = true;
                }
            }
            if (z) {
                add(compositeSubscription);
            }
            return compositeSubscription;
        }

        /* JADX WARN: Multi-variable type inference failed */
        @Override // rx.Observer
        public void onNext(Observable<? extends T> observable) throws Throwable {
            if (observable == null) {
                return;
            }
            if (observable instanceof ScalarSynchronousObservable) {
                tryEmit(((ScalarSynchronousObservable) observable).get());
                return;
            }
            long j = this.uniqueId;
            this.uniqueId = 1 + j;
            InnerSubscriber innerSubscriber = new InnerSubscriber(this, j);
            addInner(innerSubscriber);
            observable.unsafeSubscribe(innerSubscriber);
            emit();
        }

        private void reportError() {
            ArrayList arrayList = new ArrayList(this.errors);
            if (arrayList.size() == 1) {
                this.child.onError((Throwable) arrayList.get(0));
            } else {
                this.child.onError(new CompositeException(arrayList));
            }
        }

        @Override // rx.Observer
        public void onError(Throwable th) throws Throwable {
            getOrCreateErrorQueue().offer(th);
            this.done = true;
            emit();
        }

        @Override // rx.Observer
        public void onCompleted() throws Throwable {
            this.done = true;
            emit();
        }

        /* JADX WARN: Multi-variable type inference failed */
        void addInner(InnerSubscriber<T> innerSubscriber) {
            getOrCreateComposite().add(innerSubscriber);
            synchronized (this.innerGuard) {
                InnerSubscriber<?>[] innerSubscriberArr = this.innerSubscribers;
                int length = innerSubscriberArr.length;
                InnerSubscriber<?>[] innerSubscriberArr2 = new InnerSubscriber[length + 1];
                System.arraycopy(innerSubscriberArr, 0, innerSubscriberArr2, 0, length);
                innerSubscriberArr2[length] = innerSubscriber;
                this.innerSubscribers = innerSubscriberArr2;
            }
        }

        void removeInner(InnerSubscriber<T> innerSubscriber) {
            RxRingBuffer rxRingBuffer = innerSubscriber.queue;
            if (rxRingBuffer != null) {
                rxRingBuffer.release();
            }
            this.subscriptions.remove(innerSubscriber);
            synchronized (this.innerGuard) {
                InnerSubscriber<?>[] innerSubscriberArr = this.innerSubscribers;
                int length = innerSubscriberArr.length;
                int i = -1;
                int i2 = 0;
                while (true) {
                    if (i2 >= length) {
                        break;
                    }
                    if (innerSubscriber.equals(innerSubscriberArr[i2])) {
                        i = i2;
                        break;
                    }
                    i2++;
                }
                if (i < 0) {
                    return;
                }
                if (length == 1) {
                    this.innerSubscribers = EMPTY;
                    return;
                }
                InnerSubscriber<?>[] innerSubscriberArr2 = new InnerSubscriber[length - 1];
                System.arraycopy(innerSubscriberArr, 0, innerSubscriberArr2, 0, i);
                System.arraycopy(innerSubscriberArr, i + 1, innerSubscriberArr2, i, (length - i) - 1);
                this.innerSubscribers = innerSubscriberArr2;
            }
        }

        void tryEmit(InnerSubscriber<T> innerSubscriber, T t) throws Throwable {
            long j = this.producer.get();
            boolean z = false;
            if (j != 0) {
                synchronized (this) {
                    j = this.producer.get();
                    if (!this.emitting && j != 0) {
                        this.emitting = true;
                        z = true;
                    }
                }
            }
            if (z) {
                emitScalar(innerSubscriber, t, j);
            } else {
                queueScalar(innerSubscriber, t);
            }
        }

        protected void queueScalar(InnerSubscriber<T> innerSubscriber, T t) throws Throwable {
            RxRingBuffer spscInstance = innerSubscriber.queue;
            if (spscInstance == null) {
                spscInstance = RxRingBuffer.getSpscInstance();
                innerSubscriber.add(spscInstance);
                innerSubscriber.queue = spscInstance;
            }
            try {
                spscInstance.onNext(this.nl.next(t));
                emit();
            } catch (IllegalStateException e) {
                if (innerSubscriber.isUnsubscribed()) {
                    return;
                }
                innerSubscriber.unsubscribe();
                innerSubscriber.onError(e);
            } catch (MissingBackpressureException e2) {
                innerSubscriber.unsubscribe();
                innerSubscriber.onError(e2);
            }
        }

        /* JADX WARN: Removed duplicated region for block: B:34:0x004a  */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct add '--show-bad-code' argument
        */
        protected void emitScalar(rx.internal.operators.OperatorMerge.InnerSubscriber<T> r5, T r6, long r7) throws java.lang.Throwable {
            /*
                r4 = this;
                r0 = 1
                r1 = 0
                rx.Subscriber<? super T> r2 = r4.child     // Catch: java.lang.Throwable -> L8
                r2.onNext(r6)     // Catch: java.lang.Throwable -> L8
                goto L20
            L8:
                r6 = move-exception
                boolean r2 = r4.delayErrors     // Catch: java.lang.Throwable -> L46
                if (r2 != 0) goto L19
                rx.exceptions.Exceptions.throwIfFatal(r6)     // Catch: java.lang.Throwable -> L46
                r5.unsubscribe()     // Catch: java.lang.Throwable -> L17
                r5.onError(r6)     // Catch: java.lang.Throwable -> L17
                return
            L17:
                r5 = move-exception
                goto L48
            L19:
                java.util.Queue r2 = r4.getOrCreateErrorQueue()     // Catch: java.lang.Throwable -> L46
                r2.offer(r6)     // Catch: java.lang.Throwable -> L46
            L20:
                r2 = 9223372036854775807(0x7fffffffffffffff, double:NaN)
                int r6 = (r7 > r2 ? 1 : (r7 == r2 ? 0 : -1))
                if (r6 == 0) goto L2e
                rx.internal.operators.OperatorMerge$MergeProducer<T> r6 = r4.producer     // Catch: java.lang.Throwable -> L46
                r6.produced(r0)     // Catch: java.lang.Throwable -> L46
            L2e:
                r6 = 1
                r5.requestMore(r6)     // Catch: java.lang.Throwable -> L46
                monitor-enter(r4)     // Catch: java.lang.Throwable -> L46
                boolean r5 = r4.missed     // Catch: java.lang.Throwable -> L43
                if (r5 != 0) goto L3c
                r4.emitting = r1     // Catch: java.lang.Throwable -> L43
                monitor-exit(r4)     // Catch: java.lang.Throwable -> L43
                return
            L3c:
                r4.missed = r1     // Catch: java.lang.Throwable -> L43
                monitor-exit(r4)     // Catch: java.lang.Throwable -> L43
                r4.emitLoop()
                return
            L43:
                r5 = move-exception
                monitor-exit(r4)     // Catch: java.lang.Throwable -> L43
                throw r5     // Catch: java.lang.Throwable -> L17
            L46:
                r5 = move-exception
                r0 = 0
            L48:
                if (r0 != 0) goto L52
                monitor-enter(r4)
                r4.emitting = r1     // Catch: java.lang.Throwable -> L4f
                monitor-exit(r4)     // Catch: java.lang.Throwable -> L4f
                goto L52
            L4f:
                r5 = move-exception
                monitor-exit(r4)     // Catch: java.lang.Throwable -> L4f
                throw r5
            L52:
                throw r5
            */
            throw new UnsupportedOperationException("Method not decompiled: rx.internal.operators.OperatorMerge.MergeSubscriber.emitScalar(rx.internal.operators.OperatorMerge$InnerSubscriber, java.lang.Object, long):void");
        }

        public void requestMore(long j) {
            request(j);
        }

        void tryEmit(T t) throws Throwable {
            long j = this.producer.get();
            boolean z = false;
            if (j != 0) {
                synchronized (this) {
                    j = this.producer.get();
                    if (!this.emitting && j != 0) {
                        this.emitting = true;
                        z = true;
                    }
                }
            }
            if (z) {
                emitScalar(t, j);
            } else {
                queueScalar(t);
            }
        }

        protected void queueScalar(T t) throws Throwable {
            RxRingBuffer spscInstance = this.queue;
            if (spscInstance == null) {
                spscInstance = RxRingBuffer.getSpscInstance();
                add(spscInstance);
                this.queue = spscInstance;
            }
            try {
                spscInstance.onNext(this.nl.next(t));
                emit();
            } catch (IllegalStateException e) {
                if (isUnsubscribed()) {
                    return;
                }
                unsubscribe();
                onError(e);
            } catch (MissingBackpressureException e2) {
                unsubscribe();
                onError(e2);
            }
        }

        /* JADX WARN: Removed duplicated region for block: B:34:0x004a  */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct add '--show-bad-code' argument
        */
        protected void emitScalar(T r5, long r6) throws java.lang.Throwable {
            /*
                r4 = this;
                r0 = 1
                r1 = 0
                rx.Subscriber<? super T> r2 = r4.child     // Catch: java.lang.Throwable -> L8
                r2.onNext(r5)     // Catch: java.lang.Throwable -> L8
                goto L20
            L8:
                r5 = move-exception
                boolean r2 = r4.delayErrors     // Catch: java.lang.Throwable -> L46
                if (r2 != 0) goto L19
                rx.exceptions.Exceptions.throwIfFatal(r5)     // Catch: java.lang.Throwable -> L46
                r4.unsubscribe()     // Catch: java.lang.Throwable -> L17
                r4.onError(r5)     // Catch: java.lang.Throwable -> L17
                return
            L17:
                r5 = move-exception
                goto L48
            L19:
                java.util.Queue r2 = r4.getOrCreateErrorQueue()     // Catch: java.lang.Throwable -> L46
                r2.offer(r5)     // Catch: java.lang.Throwable -> L46
            L20:
                r2 = 9223372036854775807(0x7fffffffffffffff, double:NaN)
                int r5 = (r6 > r2 ? 1 : (r6 == r2 ? 0 : -1))
                if (r5 == 0) goto L2e
                rx.internal.operators.OperatorMerge$MergeProducer<T> r5 = r4.producer     // Catch: java.lang.Throwable -> L46
                r5.produced(r0)     // Catch: java.lang.Throwable -> L46
            L2e:
                r5 = 1
                r4.requestMore(r5)     // Catch: java.lang.Throwable -> L46
                monitor-enter(r4)     // Catch: java.lang.Throwable -> L46
                boolean r5 = r4.missed     // Catch: java.lang.Throwable -> L43
                if (r5 != 0) goto L3c
                r4.emitting = r1     // Catch: java.lang.Throwable -> L43
                monitor-exit(r4)     // Catch: java.lang.Throwable -> L43
                return
            L3c:
                r4.missed = r1     // Catch: java.lang.Throwable -> L43
                monitor-exit(r4)     // Catch: java.lang.Throwable -> L43
                r4.emitLoop()
                return
            L43:
                r5 = move-exception
                monitor-exit(r4)     // Catch: java.lang.Throwable -> L43
                throw r5     // Catch: java.lang.Throwable -> L17
            L46:
                r5 = move-exception
                r0 = 0
            L48:
                if (r0 != 0) goto L52
                monitor-enter(r4)
                r4.emitting = r1     // Catch: java.lang.Throwable -> L4f
                monitor-exit(r4)     // Catch: java.lang.Throwable -> L4f
                goto L52
            L4f:
                r5 = move-exception
                monitor-exit(r4)     // Catch: java.lang.Throwable -> L4f
                throw r5
            L52:
                throw r5
            */
            throw new UnsupportedOperationException("Method not decompiled: rx.internal.operators.OperatorMerge.MergeSubscriber.emitScalar(java.lang.Object, long):void");
        }

        void emit() throws Throwable {
            synchronized (this) {
                if (this.emitting) {
                    this.missed = true;
                } else {
                    this.emitting = true;
                    emitLoop();
                }
            }
        }

        /* JADX WARN: Code restructure failed: missing block: B:37:0x0076, code lost:
        
            if (r7 <= 0) goto L41;
         */
        /* JADX WARN: Code restructure failed: missing block: B:38:0x0078, code lost:
        
            if (r10 == false) goto L40;
         */
        /* JADX WARN: Code restructure failed: missing block: B:39:0x007a, code lost:
        
            r16 = kotlin.jvm.internal.LongCompanionObject.MAX_VALUE;
         */
        /* JADX WARN: Code restructure failed: missing block: B:40:0x0080, code lost:
        
            r16 = r23.producer.produced(r7);
         */
        /* JADX WARN: Code restructure failed: missing block: B:42:0x008a, code lost:
        
            if (r16 == 0) goto L198;
         */
        /* JADX WARN: Code restructure failed: missing block: B:43:0x008c, code lost:
        
            if (r0 != null) goto L45;
         */
        /* JADX WARN: Removed duplicated region for block: B:161:0x01b1  */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct add '--show-bad-code' argument
        */
        void emitLoop() throws java.lang.Throwable {
            /*
                Method dump skipped, instructions count: 442
                To view this dump add '--comments-level debug' option
            */
            throw new UnsupportedOperationException("Method not decompiled: rx.internal.operators.OperatorMerge.MergeSubscriber.emitLoop():void");
        }

        boolean checkTerminate() {
            if (this.child.isUnsubscribed()) {
                return true;
            }
            ConcurrentLinkedQueue<Throwable> concurrentLinkedQueue = this.errors;
            if (this.delayErrors || concurrentLinkedQueue == null || concurrentLinkedQueue.isEmpty()) {
                return false;
            }
            try {
                reportError();
                return true;
            } finally {
                unsubscribe();
            }
        }
    }

    static final class InnerSubscriber<T> extends Subscriber<T> {
        static final int limit = RxRingBuffer.SIZE / 4;
        volatile boolean done;
        final long id;
        int outstanding;
        final MergeSubscriber<T> parent;
        volatile RxRingBuffer queue;

        public InnerSubscriber(MergeSubscriber<T> mergeSubscriber, long j) {
            this.parent = mergeSubscriber;
            this.id = j;
        }

        @Override // rx.Subscriber
        public void onStart() {
            this.outstanding = RxRingBuffer.SIZE;
            request(RxRingBuffer.SIZE);
        }

        @Override // rx.Observer
        public void onNext(T t) throws Throwable {
            this.parent.tryEmit(this, t);
        }

        @Override // rx.Observer
        public void onError(Throwable th) throws Throwable {
            this.done = true;
            this.parent.getOrCreateErrorQueue().offer(th);
            this.parent.emit();
        }

        @Override // rx.Observer
        public void onCompleted() throws Throwable {
            this.done = true;
            this.parent.emit();
        }

        public void requestMore(long j) {
            int i = this.outstanding - ((int) j);
            if (i > limit) {
                this.outstanding = i;
                return;
            }
            this.outstanding = RxRingBuffer.SIZE;
            int i2 = RxRingBuffer.SIZE - i;
            if (i2 > 0) {
                request(i2);
            }
        }
    }
}
