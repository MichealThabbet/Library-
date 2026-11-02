package rx.subjects;

import java.lang.reflect.Array;
import java.util.ArrayList;
import rx.Observable;
import rx.exceptions.Exceptions;
import rx.functions.Action1;
import rx.internal.operators.NotificationLite;
import rx.subjects.SubjectSubscriptionManager;

/* loaded from: classes3.dex */
public final class BehaviorSubject<T> extends Subject<T, T> {
    private final NotificationLite<T> nl;
    private final SubjectSubscriptionManager<T> state;

    public static <T> BehaviorSubject<T> create() {
        return create(null, false);
    }

    public static <T> BehaviorSubject<T> create(T t) {
        return create(t, true);
    }

    private static <T> BehaviorSubject<T> create(T t, boolean z) {
        final SubjectSubscriptionManager subjectSubscriptionManager = new SubjectSubscriptionManager();
        if (z) {
            subjectSubscriptionManager.set(NotificationLite.instance().next(t));
        }
        subjectSubscriptionManager.onAdded = new Action1<SubjectSubscriptionManager.SubjectObserver<T>>() { // from class: rx.subjects.BehaviorSubject.1
            @Override // rx.functions.Action1
            public void call(SubjectSubscriptionManager.SubjectObserver<T> subjectObserver) {
                subjectObserver.emitFirst(subjectSubscriptionManager.get(), subjectSubscriptionManager.nl);
            }
        };
        subjectSubscriptionManager.onTerminated = subjectSubscriptionManager.onAdded;
        return new BehaviorSubject<>(subjectSubscriptionManager, subjectSubscriptionManager);
    }

    protected BehaviorSubject(Observable.OnSubscribe<T> onSubscribe, SubjectSubscriptionManager<T> subjectSubscriptionManager) {
        super(onSubscribe);
        this.nl = NotificationLite.instance();
        this.state = subjectSubscriptionManager;
    }

    @Override // rx.Observer
    public void onCompleted() {
        if (this.state.get() == null || this.state.active) {
            Object objCompleted = this.nl.completed();
            for (SubjectSubscriptionManager.SubjectObserver<T> subjectObserver : this.state.terminate(objCompleted)) {
                subjectObserver.emitNext(objCompleted, this.state.nl);
            }
        }
    }

    @Override // rx.Observer
    public void onError(Throwable th) {
        if (this.state.get() == null || this.state.active) {
            Object objError = this.nl.error(th);
            ArrayList arrayList = null;
            for (SubjectSubscriptionManager.SubjectObserver<T> subjectObserver : this.state.terminate(objError)) {
                try {
                    subjectObserver.emitNext(objError, this.state.nl);
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
        if (this.state.get() == null || this.state.active) {
            Object next = this.nl.next(t);
            for (SubjectSubscriptionManager.SubjectObserver<T> subjectObserver : this.state.next(next)) {
                subjectObserver.emitNext(next, this.state.nl);
            }
        }
    }

    int subscriberCount() {
        return this.state.observers().length;
    }

    @Override // rx.subjects.Subject
    public boolean hasObservers() {
        return this.state.observers().length > 0;
    }

    @Override // rx.subjects.Subject
    public boolean hasValue() {
        return this.nl.isNext(this.state.get());
    }

    @Override // rx.subjects.Subject
    public boolean hasThrowable() {
        return this.nl.isError(this.state.get());
    }

    @Override // rx.subjects.Subject
    public boolean hasCompleted() {
        return this.nl.isCompleted(this.state.get());
    }

    @Override // rx.subjects.Subject
    public T getValue() {
        Object obj = this.state.get();
        if (this.nl.isNext(obj)) {
            return this.nl.getValue(obj);
        }
        return null;
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
        Object obj = this.state.get();
        if (this.nl.isNext(obj)) {
            if (tArr.length == 0) {
                tArr = (T[]) ((Object[]) Array.newInstance(tArr.getClass().getComponentType(), 1));
            }
            tArr[0] = this.nl.getValue(obj);
            if (tArr.length > 1) {
                tArr[1] = null;
            }
        } else if (tArr.length > 0) {
            tArr[0] = null;
        }
        return tArr;
    }
}
