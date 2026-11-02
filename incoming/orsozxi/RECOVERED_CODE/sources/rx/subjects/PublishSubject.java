package rx.subjects;

import java.util.ArrayList;
import rx.Observable;
import rx.exceptions.Exceptions;
import rx.functions.Action1;
import rx.internal.operators.NotificationLite;
import rx.subjects.SubjectSubscriptionManager;

/* loaded from: classes3.dex */
public final class PublishSubject<T> extends Subject<T, T> {
    private final NotificationLite<T> nl;
    final SubjectSubscriptionManager<T> state;

    @Override // rx.subjects.Subject
    public T getValue() {
        return null;
    }

    @Override // rx.subjects.Subject
    public Object[] getValues() {
        return new Object[0];
    }

    @Override // rx.subjects.Subject
    public boolean hasValue() {
        return false;
    }

    public static <T> PublishSubject<T> create() {
        final SubjectSubscriptionManager subjectSubscriptionManager = new SubjectSubscriptionManager();
        subjectSubscriptionManager.onTerminated = new Action1<SubjectSubscriptionManager.SubjectObserver<T>>() { // from class: rx.subjects.PublishSubject.1
            @Override // rx.functions.Action1
            public void call(SubjectSubscriptionManager.SubjectObserver<T> subjectObserver) {
                subjectObserver.emitFirst(subjectSubscriptionManager.get(), subjectSubscriptionManager.nl);
            }
        };
        return new PublishSubject<>(subjectSubscriptionManager, subjectSubscriptionManager);
    }

    protected PublishSubject(Observable.OnSubscribe<T> onSubscribe, SubjectSubscriptionManager<T> subjectSubscriptionManager) {
        super(onSubscribe);
        this.nl = NotificationLite.instance();
        this.state = subjectSubscriptionManager;
    }

    @Override // rx.Observer
    public void onCompleted() {
        if (this.state.active) {
            Object objCompleted = this.nl.completed();
            for (SubjectSubscriptionManager.SubjectObserver<T> subjectObserver : this.state.terminate(objCompleted)) {
                subjectObserver.emitNext(objCompleted, this.state.nl);
            }
        }
    }

    @Override // rx.Observer
    public void onError(Throwable th) {
        if (this.state.active) {
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
        for (SubjectSubscriptionManager.SubjectObserver<T> subjectObserver : this.state.observers()) {
            subjectObserver.onNext(t);
        }
    }

    @Override // rx.subjects.Subject
    public boolean hasObservers() {
        return this.state.observers().length > 0;
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
    public Throwable getThrowable() {
        Object obj = this.state.get();
        if (this.nl.isError(obj)) {
            return this.nl.getError(obj);
        }
        return null;
    }

    @Override // rx.subjects.Subject
    public T[] getValues(T[] tArr) {
        if (tArr.length > 0) {
            tArr[0] = null;
        }
        return tArr;
    }
}
