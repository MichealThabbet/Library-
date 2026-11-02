package rx.subjects;

import com.facebook.internal.ServerProtocol;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;
import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.functions.Actions;
import rx.internal.operators.NotificationLite;
import rx.subscriptions.Subscriptions;

/* loaded from: classes3.dex */
final class SubjectSubscriptionManager<T> implements Observable.OnSubscribe<T> {
    volatile Object latest;
    static final AtomicReferenceFieldUpdater<SubjectSubscriptionManager, State> STATE_UPDATER = AtomicReferenceFieldUpdater.newUpdater(SubjectSubscriptionManager.class, State.class, ServerProtocol.DIALOG_PARAM_STATE);
    static final AtomicReferenceFieldUpdater<SubjectSubscriptionManager, Object> LATEST_UPDATER = AtomicReferenceFieldUpdater.newUpdater(SubjectSubscriptionManager.class, Object.class, "latest");
    volatile State<T> state = State.EMPTY;
    boolean active = true;
    Action1<SubjectObserver<T>> onStart = Actions.empty();
    Action1<SubjectObserver<T>> onAdded = Actions.empty();
    Action1<SubjectObserver<T>> onTerminated = Actions.empty();
    public final NotificationLite<T> nl = NotificationLite.instance();

    SubjectSubscriptionManager() {
    }

    @Override // rx.functions.Action1
    public void call(Subscriber<? super T> subscriber) {
        SubjectObserver<T> subjectObserver = new SubjectObserver<>(subscriber);
        addUnsubscriber(subscriber, subjectObserver);
        this.onStart.call(subjectObserver);
        if (!subscriber.isUnsubscribed() && add(subjectObserver) && subscriber.isUnsubscribed()) {
            remove(subjectObserver);
        }
    }

    void addUnsubscriber(Subscriber<? super T> subscriber, final SubjectObserver<T> subjectObserver) {
        subscriber.add(Subscriptions.create(new Action0() { // from class: rx.subjects.SubjectSubscriptionManager.1
            @Override // rx.functions.Action0
            public void call() {
                SubjectSubscriptionManager.this.remove(subjectObserver);
            }
        }));
    }

    void set(Object obj) {
        this.latest = obj;
    }

    Object get() {
        return this.latest;
    }

    SubjectObserver<T>[] observers() {
        return this.state.observers;
    }

    boolean add(SubjectObserver<T> subjectObserver) {
        State<T> state;
        do {
            state = this.state;
            if (state.terminated) {
                this.onTerminated.call(subjectObserver);
                return false;
            }
        } while (!STATE_UPDATER.compareAndSet(this, state, state.add(subjectObserver)));
        this.onAdded.call(subjectObserver);
        return true;
    }

    void remove(SubjectObserver<T> subjectObserver) {
        State<T> state;
        State<T> stateRemove;
        do {
            state = this.state;
            if (state.terminated || (stateRemove = state.remove(subjectObserver)) == state) {
                return;
            }
        } while (!STATE_UPDATER.compareAndSet(this, state, stateRemove));
    }

    SubjectObserver<T>[] next(Object obj) {
        set(obj);
        return this.state.observers;
    }

    SubjectObserver<T>[] terminate(Object obj) {
        set(obj);
        this.active = false;
        if (this.state.terminated) {
            return State.NO_OBSERVERS;
        }
        return STATE_UPDATER.getAndSet(this, State.TERMINATED).observers;
    }

    protected static final class State<T> {
        final SubjectObserver[] observers;
        final boolean terminated;
        static final SubjectObserver[] NO_OBSERVERS = new SubjectObserver[0];
        static final State TERMINATED = new State(true, NO_OBSERVERS);
        static final State EMPTY = new State(false, NO_OBSERVERS);

        public State(boolean z, SubjectObserver[] subjectObserverArr) {
            this.terminated = z;
            this.observers = subjectObserverArr;
        }

        public State add(SubjectObserver subjectObserver) {
            SubjectObserver[] subjectObserverArr = this.observers;
            int length = subjectObserverArr.length;
            SubjectObserver[] subjectObserverArr2 = new SubjectObserver[length + 1];
            System.arraycopy(subjectObserverArr, 0, subjectObserverArr2, 0, length);
            subjectObserverArr2[length] = subjectObserver;
            return new State(this.terminated, subjectObserverArr2);
        }

        public State remove(SubjectObserver subjectObserver) {
            SubjectObserver[] subjectObserverArr = this.observers;
            int length = subjectObserverArr.length;
            if (length == 1 && subjectObserverArr[0] == subjectObserver) {
                return EMPTY;
            }
            if (length == 0) {
                return this;
            }
            int i = length - 1;
            SubjectObserver[] subjectObserverArr2 = new SubjectObserver[i];
            int i2 = 0;
            for (SubjectObserver subjectObserver2 : subjectObserverArr) {
                if (subjectObserver2 != subjectObserver) {
                    if (i2 == i) {
                        return this;
                    }
                    subjectObserverArr2[i2] = subjectObserver2;
                    i2++;
                }
            }
            if (i2 == 0) {
                return EMPTY;
            }
            if (i2 < i) {
                SubjectObserver[] subjectObserverArr3 = new SubjectObserver[i2];
                System.arraycopy(subjectObserverArr2, 0, subjectObserverArr3, 0, i2);
                subjectObserverArr2 = subjectObserverArr3;
            }
            return new State(this.terminated, subjectObserverArr2);
        }
    }

    protected static final class SubjectObserver<T> implements Observer<T> {
        final Observer<? super T> actual;
        protected volatile boolean caughtUp;
        boolean emitting;
        boolean fastPath;
        boolean first = true;
        private volatile Object index;
        List<Object> queue;

        public SubjectObserver(Observer<? super T> observer) {
            this.actual = observer;
        }

        @Override // rx.Observer
        public void onNext(T t) {
            this.actual.onNext(t);
        }

        @Override // rx.Observer
        public void onError(Throwable th) {
            this.actual.onError(th);
        }

        @Override // rx.Observer
        public void onCompleted() {
            this.actual.onCompleted();
        }

        protected void emitNext(Object obj, NotificationLite<T> notificationLite) {
            if (!this.fastPath) {
                synchronized (this) {
                    this.first = false;
                    if (this.emitting) {
                        if (this.queue == null) {
                            this.queue = new ArrayList();
                        }
                        this.queue.add(obj);
                        return;
                    }
                    this.fastPath = true;
                }
            }
            notificationLite.accept(this.actual, obj);
        }

        protected void emitFirst(Object obj, NotificationLite<T> notificationLite) {
            synchronized (this) {
                if (this.first && !this.emitting) {
                    this.first = false;
                    this.emitting = obj != null;
                    if (obj != null) {
                        emitLoop(null, obj, notificationLite);
                    }
                }
            }
        }

        /* JADX WARN: Removed duplicated region for block: B:12:0x001c A[Catch: all -> 0x0017, TryCatch #2 {all -> 0x0017, blocks: (B:5:0x0005, B:6:0x0009, B:8:0x000f, B:12:0x001c, B:13:0x0020), top: B:41:0x0005 }] */
        /* JADX WARN: Removed duplicated region for block: B:45:0x0021 A[EXC_TOP_SPLITTER, SYNTHETIC] */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct add '--show-bad-code' argument
        */
        protected void emitLoop(java.util.List<java.lang.Object> r5, java.lang.Object r6, rx.internal.operators.NotificationLite<T> r7) throws java.lang.Throwable {
            /*
                r4 = this;
                r0 = 1
                r1 = 1
            L2:
                r2 = 0
                if (r5 == 0) goto L1a
                java.util.Iterator r5 = r5.iterator()     // Catch: java.lang.Throwable -> L17
            L9:
                boolean r3 = r5.hasNext()     // Catch: java.lang.Throwable -> L17
                if (r3 == 0) goto L1a
                java.lang.Object r3 = r5.next()     // Catch: java.lang.Throwable -> L17
                r4.accept(r3, r7)     // Catch: java.lang.Throwable -> L17
                goto L9
            L17:
                r5 = move-exception
                r0 = 0
                goto L36
            L1a:
                if (r1 == 0) goto L20
                r4.accept(r6, r7)     // Catch: java.lang.Throwable -> L17
                r1 = 0
            L20:
                monitor-enter(r4)     // Catch: java.lang.Throwable -> L17
                java.util.List<java.lang.Object> r5 = r4.queue     // Catch: java.lang.Throwable -> L2e
                r3 = 0
                r4.queue = r3     // Catch: java.lang.Throwable -> L2e
                if (r5 != 0) goto L2c
                r4.emitting = r2     // Catch: java.lang.Throwable -> L2e
                monitor-exit(r4)     // Catch: java.lang.Throwable -> L34
                return
            L2c:
                monitor-exit(r4)     // Catch: java.lang.Throwable -> L2e
                goto L2
            L2e:
                r5 = move-exception
                r0 = 0
            L30:
                monitor-exit(r4)     // Catch: java.lang.Throwable -> L34
                throw r5     // Catch: java.lang.Throwable -> L32
            L32:
                r5 = move-exception
                goto L36
            L34:
                r5 = move-exception
                goto L30
            L36:
                if (r0 != 0) goto L40
                monitor-enter(r4)
                r4.emitting = r2     // Catch: java.lang.Throwable -> L3d
                monitor-exit(r4)     // Catch: java.lang.Throwable -> L3d
                goto L40
            L3d:
                r5 = move-exception
                monitor-exit(r4)     // Catch: java.lang.Throwable -> L3d
                throw r5
            L40:
                throw r5
            */
            throw new UnsupportedOperationException("Method not decompiled: rx.subjects.SubjectSubscriptionManager.SubjectObserver.emitLoop(java.util.List, java.lang.Object, rx.internal.operators.NotificationLite):void");
        }

        protected void accept(Object obj, NotificationLite<T> notificationLite) {
            if (obj != null) {
                notificationLite.accept(this.actual, obj);
            }
        }

        protected Observer<? super T> getActual() {
            return this.actual;
        }

        public <I> I index() {
            return (I) this.index;
        }

        public void index(Object obj) {
            this.index = obj;
        }
    }
}
