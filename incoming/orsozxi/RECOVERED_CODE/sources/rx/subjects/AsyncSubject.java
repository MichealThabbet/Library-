package rx.subjects;

import java.lang.reflect.Array;
import java.util.ArrayList;
import rx.Observable;
import rx.Observer;
import rx.exceptions.Exceptions;
import rx.functions.Action1;
import rx.internal.operators.NotificationLite;
import rx.subjects.SubjectSubscriptionManager;

/* loaded from: classes3.dex */
public final class AsyncSubject<T> extends Subject<T, T> {
    volatile Object lastValue;
    private final NotificationLite<T> nl;
    final SubjectSubscriptionManager<T> state;

    public static <T> AsyncSubject<T> create() {
        final SubjectSubscriptionManager subjectSubscriptionManager = new SubjectSubscriptionManager();
        subjectSubscriptionManager.onTerminated = new Action1<SubjectSubscriptionManager.SubjectObserver<T>>() { // from class: rx.subjects.AsyncSubject.1
            @Override // rx.functions.Action1
            public void call(SubjectSubscriptionManager.SubjectObserver<T> subjectObserver) {
                Object obj = subjectSubscriptionManager.get();
                NotificationLite<T> notificationLite = subjectSubscriptionManager.nl;
                subjectObserver.accept(obj, notificationLite);
                if (obj == null || !(notificationLite.isCompleted(obj) || notificationLite.isError(obj))) {
                    subjectObserver.onCompleted();
                }
            }
        };
        return new AsyncSubject<>(subjectSubscriptionManager, subjectSubscriptionManager);
    }

    protected AsyncSubject(Observable.OnSubscribe<T> onSubscribe, SubjectSubscriptionManager<T> subjectSubscriptionManager) {
        super(onSubscribe);
        this.nl = NotificationLite.instance();
        this.state = subjectSubscriptionManager;
    }

    @Override // rx.Observer
    public void onCompleted() {
        if (this.state.active) {
            Object objCompleted = this.lastValue;
            if (objCompleted == null) {
                objCompleted = this.nl.completed();
            }
            for (Observer observer : this.state.terminate(objCompleted)) {
                if (objCompleted == this.nl.completed()) {
                    observer.onCompleted();
                } else {
                    observer.onNext(this.nl.getValue(objCompleted));
                    observer.onCompleted();
                }
            }
        }
    }

    @Override // rx.Observer
    public void onError(Throwable th) {
        if (this.state.active) {
            ArrayList arrayList = null;
            for (SubjectSubscriptionManager.SubjectObserver<T> subjectObserver : this.state.terminate(this.nl.error(th))) {
                try {
                    subjectObserver.onError(th);
                } catch (Throwable th2) {
                    if (arrayList == null) {
                        arrayList = new ArrayList();
                    }
                    arrayList.add(th2);
                }
            }
            Exceptions.throwIfAny(arrayList);
        }
    }

    @Override // rx.Observer
    public void onNext(T t) {
        this.lastValue = this.nl.next(t);
    }

    @Override // rx.subjects.Subject
    public boolean hasObservers() {
        return this.state.observers().length > 0;
    }

    @Override // rx.subjects.Subject
    public boolean hasValue() {
        return !this.nl.isError(this.state.get()) && this.nl.isNext(this.lastValue);
    }

    @Override // rx.subjects.Subject
    public boolean hasThrowable() {
        return this.nl.isError(this.state.get());
    }

    @Override // rx.subjects.Subject
    public boolean hasCompleted() {
        Object obj = this.state.get();
        return (obj == null || this.nl.isError(obj)) ? false : true;
    }

    @Override // rx.subjects.Subject
    public T getValue() {
        Object obj = this.lastValue;
        if (this.nl.isError(this.state.get()) || !this.nl.isNext(obj)) {
            return null;
        }
        return this.nl.getValue(obj);
    }

    @Override // rx.subjects.Subject
    public Throwable getThrowable() {
        Object obj = this.state.get();
        if (this.nl.isError(obj)) {
            return this.nl.getError(obj);
        }
        return null;
    }

    @Override // rx.subjects.Subject
    public T[] getValues(T[] tArr) {
        Object obj = this.lastValue;
        if (!this.nl.isError(this.state.get()) && this.nl.isNext(obj)) {
            T value = this.nl.getValue(obj);
            if (tArr.length == 0) {
                tArr = (T[]) ((Object[]) Array.newInstance(tArr.getClass().getComponentType(), 1));
            }
            tArr[0] = value;
            if (tArr.length > 1) {
                tArr[1] = null;
            }
        } else if (tArr.length > 0) {
            tArr[0] = null;
        }
        return tArr;
    }
}
