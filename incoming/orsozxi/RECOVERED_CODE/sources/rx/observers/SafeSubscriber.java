package rx.observers;

import java.util.Arrays;
import rx.Subscriber;
import rx.exceptions.CompositeException;
import rx.exceptions.Exceptions;
import rx.exceptions.OnErrorFailedException;
import rx.exceptions.OnErrorNotImplementedException;
import rx.plugins.RxJavaPlugins;

/* loaded from: classes3.dex */
public class SafeSubscriber<T> extends Subscriber<T> {
    private final Subscriber<? super T> actual;
    boolean done;

    public SafeSubscriber(Subscriber<? super T> subscriber) {
        super(subscriber);
        this.done = false;
        this.actual = subscriber;
    }

    @Override // rx.Observer
    public void onCompleted() {
        if (this.done) {
            return;
        }
        this.done = true;
        try {
            this.actual.onCompleted();
        } finally {
            try {
            } finally {
            }
        }
    }

    @Override // rx.Observer
    public void onError(Throwable th) {
        Exceptions.throwIfFatal(th);
        if (this.done) {
            return;
        }
        this.done = true;
        _onError(th);
    }

    @Override // rx.Observer
    public void onNext(T t) {
        try {
            if (this.done) {
                return;
            }
            this.actual.onNext(t);
        } catch (Throwable th) {
            Exceptions.throwIfFatal(th);
            onError(th);
        }
    }

    protected void _onError(Throwable th) {
        try {
            RxJavaPlugins.getInstance().getErrorHandler().handleError(th);
        } catch (Throwable th2) {
            handlePluginException(th2);
        }
        try {
            this.actual.onError(th);
            try {
                unsubscribe();
            } catch (RuntimeException e) {
                try {
                    RxJavaPlugins.getInstance().getErrorHandler().handleError(e);
                } catch (Throwable th3) {
                    handlePluginException(th3);
                }
                throw new OnErrorFailedException(e);
            }
        } catch (Throwable th4) {
            if (th4 instanceof OnErrorNotImplementedException) {
                try {
                    unsubscribe();
                    throw th4;
                } catch (Throwable th5) {
                    try {
                        RxJavaPlugins.getInstance().getErrorHandler().handleError(th5);
                    } catch (Throwable th6) {
                        handlePluginException(th6);
                    }
                    throw new RuntimeException("Observer.onError not implemented and error while unsubscribing.", new CompositeException(Arrays.asList(th, th5)));
                }
            }
            try {
                RxJavaPlugins.getInstance().getErrorHandler().handleError(th4);
            } catch (Throwable th7) {
                handlePluginException(th7);
            }
            try {
                unsubscribe();
                throw new OnErrorFailedException("Error occurred when trying to propagate error to Observer.onError", new CompositeException(Arrays.asList(th, th4)));
            } catch (Throwable th8) {
                try {
                    RxJavaPlugins.getInstance().getErrorHandler().handleError(th8);
                } catch (Throwable th9) {
                    handlePluginException(th9);
                }
                throw new OnErrorFailedException("Error occurred when trying to propagate error to Observer.onError and during unsubscription.", new CompositeException(Arrays.asList(th, th4, th8)));
            }
        }
    }

    private void handlePluginException(Throwable th) {
        System.err.println("RxJavaErrorHandler threw an Exception. It shouldn't. => " + th.getMessage());
        th.printStackTrace();
    }

    public Subscriber<? super T> getActual() {
        return this.actual;
    }
}
