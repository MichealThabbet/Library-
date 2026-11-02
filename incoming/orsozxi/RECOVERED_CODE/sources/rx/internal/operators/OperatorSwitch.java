package rx.internal.operators;

import java.util.ArrayList;
import java.util.List;
import rx.Observable;
import rx.Producer;
import rx.Subscriber;
import rx.internal.producers.ProducerArbiter;
import rx.observers.SerializedSubscriber;
import rx.subscriptions.SerialSubscription;

/* loaded from: classes3.dex */
public final class OperatorSwitch<T> implements Observable.Operator<T, Observable<? extends T>> {

    private static final class Holder {
        static final OperatorSwitch<Object> INSTANCE = new OperatorSwitch<>();

        private Holder() {
        }
    }

    public static <T> OperatorSwitch<T> instance() {
        return (OperatorSwitch<T>) Holder.INSTANCE;
    }

    private OperatorSwitch() {
    }

    @Override // rx.functions.Func1
    public Subscriber<? super Observable<? extends T>> call(Subscriber<? super T> subscriber) {
        SwitchSubscriber switchSubscriber = new SwitchSubscriber(subscriber);
        subscriber.add(switchSubscriber);
        return switchSubscriber;
    }

    private static final class SwitchSubscriber<T> extends Subscriber<Observable<? extends T>> {
        boolean active;
        InnerSubscriber<T> currentSubscriber;
        boolean emitting;
        int index;
        boolean mainDone;
        List<Object> queue;
        final SerializedSubscriber<T> serializedChild;
        final SerialSubscription ssub;
        final Object guard = new Object();
        final NotificationLite<?> nl = NotificationLite.instance();
        final ProducerArbiter arbiter = new ProducerArbiter();

        SwitchSubscriber(Subscriber<? super T> subscriber) {
            this.serializedChild = new SerializedSubscriber<>(subscriber);
            SerialSubscription serialSubscription = new SerialSubscription();
            this.ssub = serialSubscription;
            subscriber.add(serialSubscription);
            subscriber.setProducer(new Producer() { // from class: rx.internal.operators.OperatorSwitch.SwitchSubscriber.1
                @Override // rx.Producer
                public void request(long j) {
                    if (j > 0) {
                        SwitchSubscriber.this.arbiter.request(j);
                    }
                }
            });
        }

        @Override // rx.Observer
        public void onNext(Observable<? extends T> observable) {
            InnerSubscriber<T> innerSubscriber;
            synchronized (this.guard) {
                int i = this.index + 1;
                this.index = i;
                this.active = true;
                innerSubscriber = new InnerSubscriber<>(i, this.arbiter, this);
                this.currentSubscriber = innerSubscriber;
            }
            this.ssub.set(innerSubscriber);
            observable.unsafeSubscribe(this.currentSubscriber);
        }

        @Override // rx.Observer
        public void onError(Throwable th) {
            this.serializedChild.onError(th);
            unsubscribe();
        }

        @Override // rx.Observer
        public void onCompleted() {
            synchronized (this.guard) {
                this.mainDone = true;
                if (this.active) {
                    return;
                }
                if (this.emitting) {
                    if (this.queue == null) {
                        this.queue = new ArrayList();
                    }
                    this.queue.add(this.nl.completed());
                } else {
                    List<Object> list = this.queue;
                    this.queue = null;
                    this.emitting = true;
                    drain(list);
                    this.serializedChild.onCompleted();
                    unsubscribe();
                }
            }
        }

        /* JADX WARN: Code restructure failed: missing block: B:27:0x0045, code lost:
        
            r5.emitting = false;
         */
        /* JADX WARN: Code restructure failed: missing block: B:32:0x0050, code lost:
        
            if (r5.serializedChild.isUnsubscribed() == false) goto L44;
         */
        /* JADX WARN: Code restructure failed: missing block: B:33:0x0052, code lost:
        
            r1 = false;
         */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct add '--show-bad-code' argument
        */
        void emit(T r6, int r7, rx.internal.operators.OperatorSwitch.InnerSubscriber<T> r8) throws java.lang.Throwable {
            /*
                r5 = this;
                java.lang.Object r8 = r5.guard
                monitor-enter(r8)
                int r0 = r5.index     // Catch: java.lang.Throwable -> L79
                if (r7 == r0) goto L9
                monitor-exit(r8)     // Catch: java.lang.Throwable -> L79
                return
            L9:
                boolean r7 = r5.emitting     // Catch: java.lang.Throwable -> L79
                if (r7 == 0) goto L1f
                java.util.List<java.lang.Object> r7 = r5.queue     // Catch: java.lang.Throwable -> L79
                if (r7 != 0) goto L18
                java.util.ArrayList r7 = new java.util.ArrayList     // Catch: java.lang.Throwable -> L79
                r7.<init>()     // Catch: java.lang.Throwable -> L79
                r5.queue = r7     // Catch: java.lang.Throwable -> L79
            L18:
                java.util.List<java.lang.Object> r7 = r5.queue     // Catch: java.lang.Throwable -> L79
                r7.add(r6)     // Catch: java.lang.Throwable -> L79
                monitor-exit(r8)     // Catch: java.lang.Throwable -> L79
                return
            L1f:
                java.util.List<java.lang.Object> r7 = r5.queue     // Catch: java.lang.Throwable -> L79
                r0 = 0
                r5.queue = r0     // Catch: java.lang.Throwable -> L79
                r1 = 1
                r5.emitting = r1     // Catch: java.lang.Throwable -> L79
                monitor-exit(r8)     // Catch: java.lang.Throwable -> L79
                r8 = 1
            L29:
                r2 = 0
                r5.drain(r7)     // Catch: java.lang.Throwable -> L6a
                if (r8 == 0) goto L3c
                rx.observers.SerializedSubscriber<T> r7 = r5.serializedChild     // Catch: java.lang.Throwable -> L6a
                r7.onNext(r6)     // Catch: java.lang.Throwable -> L6a
                rx.internal.producers.ProducerArbiter r7 = r5.arbiter     // Catch: java.lang.Throwable -> L6a
                r3 = 1
                r7.produced(r3)     // Catch: java.lang.Throwable -> L6a
                r8 = 0
            L3c:
                java.lang.Object r7 = r5.guard     // Catch: java.lang.Throwable -> L6a
                monitor-enter(r7)     // Catch: java.lang.Throwable -> L6a
                java.util.List<java.lang.Object> r3 = r5.queue     // Catch: java.lang.Throwable -> L62
                r5.queue = r0     // Catch: java.lang.Throwable -> L62
                if (r3 != 0) goto L49
                r5.emitting = r2     // Catch: java.lang.Throwable -> L62
                monitor-exit(r7)     // Catch: java.lang.Throwable -> L68
                goto L53
            L49:
                monitor-exit(r7)     // Catch: java.lang.Throwable -> L62
                rx.observers.SerializedSubscriber<T> r7 = r5.serializedChild     // Catch: java.lang.Throwable -> L6a
                boolean r7 = r7.isUnsubscribed()     // Catch: java.lang.Throwable -> L6a
                if (r7 == 0) goto L60
                r1 = 0
            L53:
                if (r1 != 0) goto L5f
                java.lang.Object r6 = r5.guard
                monitor-enter(r6)
                r5.emitting = r2     // Catch: java.lang.Throwable -> L5c
                monitor-exit(r6)     // Catch: java.lang.Throwable -> L5c
                goto L5f
            L5c:
                r7 = move-exception
                monitor-exit(r6)     // Catch: java.lang.Throwable -> L5c
                throw r7
            L5f:
                return
            L60:
                r7 = r3
                goto L29
            L62:
                r6 = move-exception
                r1 = 0
            L64:
                monitor-exit(r7)     // Catch: java.lang.Throwable -> L68
                throw r6     // Catch: java.lang.Throwable -> L66
            L66:
                r6 = move-exception
                goto L6c
            L68:
                r6 = move-exception
                goto L64
            L6a:
                r6 = move-exception
                r1 = 0
            L6c:
                if (r1 != 0) goto L78
                java.lang.Object r7 = r5.guard
                monitor-enter(r7)
                r5.emitting = r2     // Catch: java.lang.Throwable -> L75
                monitor-exit(r7)     // Catch: java.lang.Throwable -> L75
                goto L78
            L75:
                r6 = move-exception
                monitor-exit(r7)     // Catch: java.lang.Throwable -> L75
                throw r6
            L78:
                throw r6
            L79:
                r6 = move-exception
                monitor-exit(r8)     // Catch: java.lang.Throwable -> L79
                throw r6
            */
            throw new UnsupportedOperationException("Method not decompiled: rx.internal.operators.OperatorSwitch.SwitchSubscriber.emit(java.lang.Object, int, rx.internal.operators.OperatorSwitch$InnerSubscriber):void");
        }

        /* JADX WARN: Multi-variable type inference failed */
        void drain(List<Object> list) {
            if (list == null) {
                return;
            }
            for (Object obj : list) {
                if (this.nl.isCompleted(obj)) {
                    this.serializedChild.onCompleted();
                    return;
                } else if (this.nl.isError(obj)) {
                    this.serializedChild.onError(this.nl.getError(obj));
                    return;
                } else {
                    this.serializedChild.onNext(obj);
                    this.arbiter.produced(1L);
                }
            }
        }

        void error(Throwable th, int i) {
            synchronized (this.guard) {
                if (i != this.index) {
                    return;
                }
                if (this.emitting) {
                    if (this.queue == null) {
                        this.queue = new ArrayList();
                    }
                    this.queue.add(this.nl.error(th));
                } else {
                    List<Object> list = this.queue;
                    this.queue = null;
                    this.emitting = true;
                    drain(list);
                    this.serializedChild.onError(th);
                    unsubscribe();
                }
            }
        }

        void complete(int i) {
            synchronized (this.guard) {
                if (i != this.index) {
                    return;
                }
                this.active = false;
                if (this.mainDone) {
                    if (this.emitting) {
                        if (this.queue == null) {
                            this.queue = new ArrayList();
                        }
                        this.queue.add(this.nl.completed());
                    } else {
                        List<Object> list = this.queue;
                        this.queue = null;
                        this.emitting = true;
                        drain(list);
                        this.serializedChild.onCompleted();
                        unsubscribe();
                    }
                }
            }
        }
    }

    private static final class InnerSubscriber<T> extends Subscriber<T> {
        private final ProducerArbiter arbiter;
        private final int id;
        private final SwitchSubscriber<T> parent;

        InnerSubscriber(int i, ProducerArbiter producerArbiter, SwitchSubscriber<T> switchSubscriber) {
            this.id = i;
            this.arbiter = producerArbiter;
            this.parent = switchSubscriber;
        }

        @Override // rx.Subscriber
        public void setProducer(Producer producer) {
            this.arbiter.setProducer(producer);
        }

        @Override // rx.Observer
        public void onNext(T t) throws Throwable {
            this.parent.emit(t, this.id, this);
        }

        @Override // rx.Observer
        public void onError(Throwable th) {
            this.parent.error(th, this.id);
        }

        @Override // rx.Observer
        public void onCompleted() {
            this.parent.complete(this.id);
        }
    }
}
