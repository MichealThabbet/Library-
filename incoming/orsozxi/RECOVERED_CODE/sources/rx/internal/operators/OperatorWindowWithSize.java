package rx.internal.operators;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import kotlin.jvm.internal.LongCompanionObject;
import rx.Observable;
import rx.Observer;
import rx.Producer;
import rx.Subscriber;
import rx.functions.Action0;
import rx.subscriptions.Subscriptions;

/* loaded from: classes3.dex */
public final class OperatorWindowWithSize<T> implements Observable.Operator<Observable<T>, T> {
    final int size;
    final int skip;

    public OperatorWindowWithSize(int i, int i2) {
        this.size = i;
        this.skip = i2;
    }

    @Override // rx.functions.Func1
    public Subscriber<? super T> call(Subscriber<? super Observable<T>> subscriber) {
        if (this.skip == this.size) {
            ExactSubscriber exactSubscriber = new ExactSubscriber(subscriber);
            exactSubscriber.init();
            return exactSubscriber;
        }
        InexactSubscriber inexactSubscriber = new InexactSubscriber(subscriber);
        inexactSubscriber.init();
        return inexactSubscriber;
    }

    final class ExactSubscriber extends Subscriber<T> {
        final Subscriber<? super Observable<T>> child;
        int count;
        volatile boolean noWindow = true;
        BufferUntilSubscriber<T> window;

        public ExactSubscriber(Subscriber<? super Observable<T>> subscriber) {
            this.child = subscriber;
        }

        void init() {
            this.child.add(Subscriptions.create(new Action0() { // from class: rx.internal.operators.OperatorWindowWithSize.ExactSubscriber.1
                @Override // rx.functions.Action0
                public void call() {
                    if (ExactSubscriber.this.noWindow) {
                        ExactSubscriber.this.unsubscribe();
                    }
                }
            }));
            this.child.setProducer(new Producer() { // from class: rx.internal.operators.OperatorWindowWithSize.ExactSubscriber.2
                @Override // rx.Producer
                public void request(long j) {
                    if (j > 0) {
                        long j2 = OperatorWindowWithSize.this.size * j;
                        if ((j2 >>> 31) != 0 && j2 / j != OperatorWindowWithSize.this.size) {
                            j2 = LongCompanionObject.MAX_VALUE;
                        }
                        ExactSubscriber.this.requestMore(j2);
                    }
                }
            });
        }

        void requestMore(long j) {
            request(j);
        }

        @Override // rx.Observer
        public void onNext(T t) {
            if (this.window == null) {
                this.noWindow = false;
                BufferUntilSubscriber<T> bufferUntilSubscriberCreate = BufferUntilSubscriber.create();
                this.window = bufferUntilSubscriberCreate;
                this.child.onNext(bufferUntilSubscriberCreate);
            }
            this.window.onNext(t);
            int i = this.count + 1;
            this.count = i;
            if (i % OperatorWindowWithSize.this.size == 0) {
                this.window.onCompleted();
                this.window = null;
                this.noWindow = true;
                if (this.child.isUnsubscribed()) {
                    unsubscribe();
                }
            }
        }

        @Override // rx.Observer
        public void onError(Throwable th) {
            BufferUntilSubscriber<T> bufferUntilSubscriber = this.window;
            if (bufferUntilSubscriber != null) {
                bufferUntilSubscriber.onError(th);
            }
            this.child.onError(th);
        }

        @Override // rx.Observer
        public void onCompleted() {
            BufferUntilSubscriber<T> bufferUntilSubscriber = this.window;
            if (bufferUntilSubscriber != null) {
                bufferUntilSubscriber.onCompleted();
            }
            this.child.onCompleted();
        }
    }

    final class InexactSubscriber extends Subscriber<T> {
        final Subscriber<? super Observable<T>> child;
        int count;
        final List<CountedSubject<T>> chunks = new LinkedList();
        volatile boolean noWindow = true;

        public InexactSubscriber(Subscriber<? super Observable<T>> subscriber) {
            this.child = subscriber;
        }

        void init() {
            this.child.add(Subscriptions.create(new Action0() { // from class: rx.internal.operators.OperatorWindowWithSize.InexactSubscriber.1
                @Override // rx.functions.Action0
                public void call() {
                    if (InexactSubscriber.this.noWindow) {
                        InexactSubscriber.this.unsubscribe();
                    }
                }
            }));
            this.child.setProducer(new Producer() { // from class: rx.internal.operators.OperatorWindowWithSize.InexactSubscriber.2
                @Override // rx.Producer
                public void request(long j) {
                    if (j > 0) {
                        long j2 = OperatorWindowWithSize.this.size * j;
                        if ((j2 >>> 31) != 0 && j2 / j != OperatorWindowWithSize.this.size) {
                            j2 = LongCompanionObject.MAX_VALUE;
                        }
                        InexactSubscriber.this.requestMore(j2);
                    }
                }
            });
        }

        void requestMore(long j) {
            request(j);
        }

        @Override // rx.Observer
        public void onNext(T t) {
            int i = this.count;
            this.count = i + 1;
            if (i % OperatorWindowWithSize.this.skip == 0 && !this.child.isUnsubscribed()) {
                if (this.chunks.isEmpty()) {
                    this.noWindow = false;
                }
                CountedSubject<T> countedSubjectCreateCountedSubject = createCountedSubject();
                this.chunks.add(countedSubjectCreateCountedSubject);
                this.child.onNext(countedSubjectCreateCountedSubject.producer);
            }
            Iterator<CountedSubject<T>> it2 = this.chunks.iterator();
            while (it2.hasNext()) {
                CountedSubject<T> next = it2.next();
                next.consumer.onNext(t);
                int i2 = next.count + 1;
                next.count = i2;
                if (i2 == OperatorWindowWithSize.this.size) {
                    it2.remove();
                    next.consumer.onCompleted();
                }
            }
            if (this.chunks.isEmpty()) {
                this.noWindow = true;
                if (this.child.isUnsubscribed()) {
                    unsubscribe();
                }
            }
        }

        @Override // rx.Observer
        public void onError(Throwable th) {
            ArrayList arrayList = new ArrayList(this.chunks);
            this.chunks.clear();
            this.noWindow = true;
            Iterator it2 = arrayList.iterator();
            while (it2.hasNext()) {
                ((CountedSubject) it2.next()).consumer.onError(th);
            }
            this.child.onError(th);
        }

        @Override // rx.Observer
        public void onCompleted() {
            ArrayList arrayList = new ArrayList(this.chunks);
            this.chunks.clear();
            this.noWindow = true;
            Iterator it2 = arrayList.iterator();
            while (it2.hasNext()) {
                ((CountedSubject) it2.next()).consumer.onCompleted();
            }
            this.child.onCompleted();
        }

        CountedSubject<T> createCountedSubject() {
            BufferUntilSubscriber bufferUntilSubscriberCreate = BufferUntilSubscriber.create();
            return new CountedSubject<>(bufferUntilSubscriberCreate, bufferUntilSubscriberCreate);
        }
    }

    static final class CountedSubject<T> {
        final Observer<T> consumer;
        int count;
        final Observable<T> producer;

        public CountedSubject(Observer<T> observer, Observable<T> observable) {
            this.consumer = observer;
            this.producer = observable;
        }
    }
}
