package rx.subjects;

import rx.Observable;
import rx.Observer;

/* loaded from: classes3.dex */
public abstract class Subject<T, R> extends Observable<R> implements Observer<T> {
    private static final Object[] EMPTY_ARRAY = new Object[0];

    public abstract boolean hasObservers();

    protected Subject(Observable.OnSubscribe<R> onSubscribe) {
        super(onSubscribe);
    }

    public final SerializedSubject<T, R> toSerialized() {
        if (getClass() == SerializedSubject.class) {
            return (SerializedSubject) this;
        }
        return new SerializedSubject<>(this);
    }

    public boolean hasThrowable() {
        throw new UnsupportedOperationException();
    }

    public boolean hasCompleted() {
        throw new UnsupportedOperationException();
    }

    public Throwable getThrowable() {
        throw new UnsupportedOperationException();
    }

    public boolean hasValue() {
        throw new UnsupportedOperationException();
    }

    public T getValue() {
        throw new UnsupportedOperationException();
    }

    /* JADX WARN: Multi-variable type inference failed */
    public Object[] getValues() {
        Object[] values = getValues(EMPTY_ARRAY);
        return values == EMPTY_ARRAY ? new Object[0] : values;
    }

    public T[] getValues(T[] tArr) {
        throw new UnsupportedOperationException();
    }
}
