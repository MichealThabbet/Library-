package rx;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import rx.exceptions.Exceptions;
import rx.exceptions.OnErrorNotImplementedException;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.functions.Action2;
import rx.functions.Func0;
import rx.functions.Func1;
import rx.functions.Func2;
import rx.functions.Func3;
import rx.functions.Func4;
import rx.functions.Func5;
import rx.functions.Func6;
import rx.functions.Func7;
import rx.functions.Func8;
import rx.functions.Func9;
import rx.functions.FuncN;
import rx.functions.Functions;
import rx.internal.operators.CachedObservable;
import rx.internal.operators.OnSubscribeAmb;
import rx.internal.operators.OnSubscribeCombineLatest;
import rx.internal.operators.OnSubscribeDefer;
import rx.internal.operators.OnSubscribeDelaySubscription;
import rx.internal.operators.OnSubscribeDelaySubscriptionWithSelector;
import rx.internal.operators.OnSubscribeFromIterable;
import rx.internal.operators.OnSubscribeGroupJoin;
import rx.internal.operators.OnSubscribeJoin;
import rx.internal.operators.OnSubscribeRange;
import rx.internal.operators.OnSubscribeRedo;
import rx.internal.operators.OnSubscribeSingle;
import rx.internal.operators.OnSubscribeTimerOnce;
import rx.internal.operators.OnSubscribeTimerPeriodically;
import rx.internal.operators.OnSubscribeToObservableFuture;
import rx.internal.operators.OnSubscribeUsing;
import rx.internal.operators.OperatorAll;
import rx.internal.operators.OperatorAny;
import rx.internal.operators.OperatorAsObservable;
import rx.internal.operators.OperatorBufferWithSingleObservable;
import rx.internal.operators.OperatorBufferWithSize;
import rx.internal.operators.OperatorBufferWithStartEndObservable;
import rx.internal.operators.OperatorBufferWithTime;
import rx.internal.operators.OperatorCast;
import rx.internal.operators.OperatorConcat;
import rx.internal.operators.OperatorDebounceWithSelector;
import rx.internal.operators.OperatorDebounceWithTime;
import rx.internal.operators.OperatorDelay;
import rx.internal.operators.OperatorDelayWithSelector;
import rx.internal.operators.OperatorDematerialize;
import rx.internal.operators.OperatorDistinct;
import rx.internal.operators.OperatorDistinctUntilChanged;
import rx.internal.operators.OperatorDoOnEach;
import rx.internal.operators.OperatorDoOnRequest;
import rx.internal.operators.OperatorDoOnSubscribe;
import rx.internal.operators.OperatorDoOnUnsubscribe;
import rx.internal.operators.OperatorElementAt;
import rx.internal.operators.OperatorFilter;
import rx.internal.operators.OperatorFinally;
import rx.internal.operators.OperatorGroupBy;
import rx.internal.operators.OperatorIgnoreElements;
import rx.internal.operators.OperatorMap;
import rx.internal.operators.OperatorMapNotification;
import rx.internal.operators.OperatorMapPair;
import rx.internal.operators.OperatorMaterialize;
import rx.internal.operators.OperatorMerge;
import rx.internal.operators.OperatorObserveOn;
import rx.internal.operators.OperatorOnBackpressureBlock;
import rx.internal.operators.OperatorOnBackpressureBuffer;
import rx.internal.operators.OperatorOnBackpressureDrop;
import rx.internal.operators.OperatorOnBackpressureLatest;
import rx.internal.operators.OperatorOnErrorResumeNextViaFunction;
import rx.internal.operators.OperatorOnErrorResumeNextViaObservable;
import rx.internal.operators.OperatorOnErrorReturn;
import rx.internal.operators.OperatorOnExceptionResumeNextViaObservable;
import rx.internal.operators.OperatorPublish;
import rx.internal.operators.OperatorReplay;
import rx.internal.operators.OperatorRetryWithPredicate;
import rx.internal.operators.OperatorSampleWithObservable;
import rx.internal.operators.OperatorSampleWithTime;
import rx.internal.operators.OperatorScan;
import rx.internal.operators.OperatorSequenceEqual;
import rx.internal.operators.OperatorSerialize;
import rx.internal.operators.OperatorSingle;
import rx.internal.operators.OperatorSkip;
import rx.internal.operators.OperatorSkipLast;
import rx.internal.operators.OperatorSkipLastTimed;
import rx.internal.operators.OperatorSkipTimed;
import rx.internal.operators.OperatorSkipUntil;
import rx.internal.operators.OperatorSkipWhile;
import rx.internal.operators.OperatorSubscribeOn;
import rx.internal.operators.OperatorSwitch;
import rx.internal.operators.OperatorSwitchIfEmpty;
import rx.internal.operators.OperatorTake;
import rx.internal.operators.OperatorTakeLast;
import rx.internal.operators.OperatorTakeLastOne;
import rx.internal.operators.OperatorTakeLastTimed;
import rx.internal.operators.OperatorTakeTimed;
import rx.internal.operators.OperatorTakeUntil;
import rx.internal.operators.OperatorTakeUntilPredicate;
import rx.internal.operators.OperatorTakeWhile;
import rx.internal.operators.OperatorThrottleFirst;
import rx.internal.operators.OperatorTimeInterval;
import rx.internal.operators.OperatorTimeout;
import rx.internal.operators.OperatorTimeoutWithSelector;
import rx.internal.operators.OperatorTimestamp;
import rx.internal.operators.OperatorToMap;
import rx.internal.operators.OperatorToMultimap;
import rx.internal.operators.OperatorToObservableList;
import rx.internal.operators.OperatorToObservableSortedList;
import rx.internal.operators.OperatorUnsubscribeOn;
import rx.internal.operators.OperatorWindowWithObservable;
import rx.internal.operators.OperatorWindowWithObservableFactory;
import rx.internal.operators.OperatorWindowWithSize;
import rx.internal.operators.OperatorWindowWithStartEndObservable;
import rx.internal.operators.OperatorWindowWithTime;
import rx.internal.operators.OperatorWithLatestFrom;
import rx.internal.operators.OperatorZip;
import rx.internal.operators.OperatorZipIterable;
import rx.internal.producers.SingleProducer;
import rx.internal.util.RxRingBuffer;
import rx.internal.util.ScalarSynchronousObservable;
import rx.internal.util.UtilityFunctions;
import rx.observables.BlockingObservable;
import rx.observables.ConnectableObservable;
import rx.observables.GroupedObservable;
import rx.observers.SafeSubscriber;
import rx.plugins.RxJavaObservableExecutionHook;
import rx.plugins.RxJavaPlugins;
import rx.schedulers.Schedulers;
import rx.schedulers.TimeInterval;
import rx.schedulers.Timestamped;
import rx.subscriptions.Subscriptions;

/* loaded from: classes3.dex */
public class Observable<T> {
    private static final RxJavaObservableExecutionHook hook = RxJavaPlugins.getInstance().getObservableExecutionHook();
    final OnSubscribe<T> onSubscribe;

    public interface OnSubscribe<T> extends Action1<Subscriber<? super T>> {
    }

    public interface Operator<R, T> extends Func1<Subscriber<? super R>, Subscriber<? super T>> {
    }

    public interface Transformer<T, R> extends Func1<Observable<T>, Observable<R>> {
    }

    protected Observable(OnSubscribe<T> onSubscribe) {
        this.onSubscribe = onSubscribe;
    }

    public static final <T> Observable<T> create(OnSubscribe<T> onSubscribe) {
        return new Observable<>(hook.onCreate(onSubscribe));
    }

    public <R> R x(Func1<? super OnSubscribe<T>, ? extends R> func1) {
        return func1.call(new OnSubscribe<T>() { // from class: rx.Observable.1
            @Override // rx.functions.Action1
            public void call(Subscriber<? super T> subscriber) {
                subscriber.add(Observable.subscribe(subscriber, Observable.this));
            }
        });
    }

    public final <R> Observable<R> lift(final Operator<? extends R, ? super T> operator) {
        return new Observable<>(new OnSubscribe<R>() { // from class: rx.Observable.2
            @Override // rx.functions.Action1
            public void call(Subscriber<? super R> subscriber) {
                try {
                    Subscriber subscriber2 = (Subscriber) Observable.hook.onLift(operator).call(subscriber);
                    try {
                        subscriber2.onStart();
                        Observable.this.onSubscribe.call(subscriber2);
                    } catch (Throwable th) {
                        if (th instanceof OnErrorNotImplementedException) {
                            throw th;
                        }
                        subscriber2.onError(th);
                    }
                } catch (Throwable th2) {
                    if (th2 instanceof OnErrorNotImplementedException) {
                        throw th2;
                    }
                    subscriber.onError(th2);
                }
            }
        });
    }

    public <R> Observable<R> compose(Transformer<? super T, ? extends R> transformer) {
        return (Observable) transformer.call(this);
    }

    public Single<T> toSingle() {
        return new Single<>(OnSubscribeSingle.create(this));
    }

    public static final <T> Observable<T> amb(Iterable<? extends Observable<? extends T>> iterable) {
        return create(OnSubscribeAmb.amb(iterable));
    }

    public static final <T> Observable<T> amb(Observable<? extends T> observable, Observable<? extends T> observable2) {
        return create(OnSubscribeAmb.amb(observable, observable2));
    }

    public static final <T> Observable<T> amb(Observable<? extends T> observable, Observable<? extends T> observable2, Observable<? extends T> observable3) {
        return create(OnSubscribeAmb.amb(observable, observable2, observable3));
    }

    public static final <T> Observable<T> amb(Observable<? extends T> observable, Observable<? extends T> observable2, Observable<? extends T> observable3, Observable<? extends T> observable4) {
        return create(OnSubscribeAmb.amb(observable, observable2, observable3, observable4));
    }

    public static final <T> Observable<T> amb(Observable<? extends T> observable, Observable<? extends T> observable2, Observable<? extends T> observable3, Observable<? extends T> observable4, Observable<? extends T> observable5) {
        return create(OnSubscribeAmb.amb(observable, observable2, observable3, observable4, observable5));
    }

    public static final <T> Observable<T> amb(Observable<? extends T> observable, Observable<? extends T> observable2, Observable<? extends T> observable3, Observable<? extends T> observable4, Observable<? extends T> observable5, Observable<? extends T> observable6) {
        return create(OnSubscribeAmb.amb(observable, observable2, observable3, observable4, observable5, observable6));
    }

    public static final <T> Observable<T> amb(Observable<? extends T> observable, Observable<? extends T> observable2, Observable<? extends T> observable3, Observable<? extends T> observable4, Observable<? extends T> observable5, Observable<? extends T> observable6, Observable<? extends T> observable7) {
        return create(OnSubscribeAmb.amb(observable, observable2, observable3, observable4, observable5, observable6, observable7));
    }

    public static final <T> Observable<T> amb(Observable<? extends T> observable, Observable<? extends T> observable2, Observable<? extends T> observable3, Observable<? extends T> observable4, Observable<? extends T> observable5, Observable<? extends T> observable6, Observable<? extends T> observable7, Observable<? extends T> observable8) {
        return create(OnSubscribeAmb.amb(observable, observable2, observable3, observable4, observable5, observable6, observable7, observable8));
    }

    public static final <T> Observable<T> amb(Observable<? extends T> observable, Observable<? extends T> observable2, Observable<? extends T> observable3, Observable<? extends T> observable4, Observable<? extends T> observable5, Observable<? extends T> observable6, Observable<? extends T> observable7, Observable<? extends T> observable8, Observable<? extends T> observable9) {
        return create(OnSubscribeAmb.amb(observable, observable2, observable3, observable4, observable5, observable6, observable7, observable8, observable9));
    }

    public static final <T1, T2, R> Observable<R> combineLatest(Observable<? extends T1> observable, Observable<? extends T2> observable2, Func2<? super T1, ? super T2, ? extends R> func2) {
        return combineLatest(Arrays.asList(observable, observable2), Functions.fromFunc(func2));
    }

    public static final <T1, T2, T3, R> Observable<R> combineLatest(Observable<? extends T1> observable, Observable<? extends T2> observable2, Observable<? extends T3> observable3, Func3<? super T1, ? super T2, ? super T3, ? extends R> func3) {
        return combineLatest(Arrays.asList(observable, observable2, observable3), Functions.fromFunc(func3));
    }

    public static final <T1, T2, T3, T4, R> Observable<R> combineLatest(Observable<? extends T1> observable, Observable<? extends T2> observable2, Observable<? extends T3> observable3, Observable<? extends T4> observable4, Func4<? super T1, ? super T2, ? super T3, ? super T4, ? extends R> func4) {
        return combineLatest(Arrays.asList(observable, observable2, observable3, observable4), Functions.fromFunc(func4));
    }

    public static final <T1, T2, T3, T4, T5, R> Observable<R> combineLatest(Observable<? extends T1> observable, Observable<? extends T2> observable2, Observable<? extends T3> observable3, Observable<? extends T4> observable4, Observable<? extends T5> observable5, Func5<? super T1, ? super T2, ? super T3, ? super T4, ? super T5, ? extends R> func5) {
        return combineLatest(Arrays.asList(observable, observable2, observable3, observable4, observable5), Functions.fromFunc(func5));
    }

    public static final <T1, T2, T3, T4, T5, T6, R> Observable<R> combineLatest(Observable<? extends T1> observable, Observable<? extends T2> observable2, Observable<? extends T3> observable3, Observable<? extends T4> observable4, Observable<? extends T5> observable5, Observable<? extends T6> observable6, Func6<? super T1, ? super T2, ? super T3, ? super T4, ? super T5, ? super T6, ? extends R> func6) {
        return combineLatest(Arrays.asList(observable, observable2, observable3, observable4, observable5, observable6), Functions.fromFunc(func6));
    }

    public static final <T1, T2, T3, T4, T5, T6, T7, R> Observable<R> combineLatest(Observable<? extends T1> observable, Observable<? extends T2> observable2, Observable<? extends T3> observable3, Observable<? extends T4> observable4, Observable<? extends T5> observable5, Observable<? extends T6> observable6, Observable<? extends T7> observable7, Func7<? super T1, ? super T2, ? super T3, ? super T4, ? super T5, ? super T6, ? super T7, ? extends R> func7) {
        return combineLatest(Arrays.asList(observable, observable2, observable3, observable4, observable5, observable6, observable7), Functions.fromFunc(func7));
    }

    public static final <T1, T2, T3, T4, T5, T6, T7, T8, R> Observable<R> combineLatest(Observable<? extends T1> observable, Observable<? extends T2> observable2, Observable<? extends T3> observable3, Observable<? extends T4> observable4, Observable<? extends T5> observable5, Observable<? extends T6> observable6, Observable<? extends T7> observable7, Observable<? extends T8> observable8, Func8<? super T1, ? super T2, ? super T3, ? super T4, ? super T5, ? super T6, ? super T7, ? super T8, ? extends R> func8) {
        return combineLatest(Arrays.asList(observable, observable2, observable3, observable4, observable5, observable6, observable7, observable8), Functions.fromFunc(func8));
    }

    public static final <T1, T2, T3, T4, T5, T6, T7, T8, T9, R> Observable<R> combineLatest(Observable<? extends T1> observable, Observable<? extends T2> observable2, Observable<? extends T3> observable3, Observable<? extends T4> observable4, Observable<? extends T5> observable5, Observable<? extends T6> observable6, Observable<? extends T7> observable7, Observable<? extends T8> observable8, Observable<? extends T9> observable9, Func9<? super T1, ? super T2, ? super T3, ? super T4, ? super T5, ? super T6, ? super T7, ? super T8, ? super T9, ? extends R> func9) {
        return combineLatest(Arrays.asList(observable, observable2, observable3, observable4, observable5, observable6, observable7, observable8, observable9), Functions.fromFunc(func9));
    }

    public static final <T, R> Observable<R> combineLatest(List<? extends Observable<? extends T>> list, FuncN<? extends R> funcN) {
        return create(new OnSubscribeCombineLatest(list, funcN));
    }

    public static final <T> Observable<T> concat(Observable<? extends Observable<? extends T>> observable) {
        return (Observable<T>) observable.lift(OperatorConcat.instance());
    }

    public static final <T> Observable<T> concat(Observable<? extends T> observable, Observable<? extends T> observable2) {
        return concat(just(observable, observable2));
    }

    public static final <T> Observable<T> concat(Observable<? extends T> observable, Observable<? extends T> observable2, Observable<? extends T> observable3) {
        return concat(just(observable, observable2, observable3));
    }

    public static final <T> Observable<T> concat(Observable<? extends T> observable, Observable<? extends T> observable2, Observable<? extends T> observable3, Observable<? extends T> observable4) {
        return concat(just(observable, observable2, observable3, observable4));
    }

    public static final <T> Observable<T> concat(Observable<? extends T> observable, Observable<? extends T> observable2, Observable<? extends T> observable3, Observable<? extends T> observable4, Observable<? extends T> observable5) {
        return concat(just(observable, observable2, observable3, observable4, observable5));
    }

    public static final <T> Observable<T> concat(Observable<? extends T> observable, Observable<? extends T> observable2, Observable<? extends T> observable3, Observable<? extends T> observable4, Observable<? extends T> observable5, Observable<? extends T> observable6) {
        return concat(just(observable, observable2, observable3, observable4, observable5, observable6));
    }

    public static final <T> Observable<T> concat(Observable<? extends T> observable, Observable<? extends T> observable2, Observable<? extends T> observable3, Observable<? extends T> observable4, Observable<? extends T> observable5, Observable<? extends T> observable6, Observable<? extends T> observable7) {
        return concat(just(observable, observable2, observable3, observable4, observable5, observable6, observable7));
    }

    public static final <T> Observable<T> concat(Observable<? extends T> observable, Observable<? extends T> observable2, Observable<? extends T> observable3, Observable<? extends T> observable4, Observable<? extends T> observable5, Observable<? extends T> observable6, Observable<? extends T> observable7, Observable<? extends T> observable8) {
        return concat(just(observable, observable2, observable3, observable4, observable5, observable6, observable7, observable8));
    }

    public static final <T> Observable<T> concat(Observable<? extends T> observable, Observable<? extends T> observable2, Observable<? extends T> observable3, Observable<? extends T> observable4, Observable<? extends T> observable5, Observable<? extends T> observable6, Observable<? extends T> observable7, Observable<? extends T> observable8, Observable<? extends T> observable9) {
        return concat(just(observable, observable2, observable3, observable4, observable5, observable6, observable7, observable8, observable9));
    }

    public static final <T> Observable<T> defer(Func0<Observable<T>> func0) {
        return create(new OnSubscribeDefer(func0));
    }

    private static final class EmptyHolder {
        static final Observable<Object> INSTANCE = Observable.create(new OnSubscribe<Object>() { // from class: rx.Observable.EmptyHolder.1
            @Override // rx.functions.Action1
            public void call(Subscriber<? super Object> subscriber) {
                subscriber.onCompleted();
            }
        });

        private EmptyHolder() {
        }
    }

    public static final <T> Observable<T> empty() {
        return (Observable<T>) EmptyHolder.INSTANCE;
    }

    public static final <T> Observable<T> error(Throwable th) {
        return new ThrowObservable(th);
    }

    public static final <T> Observable<T> from(Future<? extends T> future) {
        return create(OnSubscribeToObservableFuture.toObservableFuture(future));
    }

    public static final <T> Observable<T> from(Future<? extends T> future, long j, TimeUnit timeUnit) {
        return create(OnSubscribeToObservableFuture.toObservableFuture(future, j, timeUnit));
    }

    public static final <T> Observable<T> from(Future<? extends T> future, Scheduler scheduler) {
        return create(OnSubscribeToObservableFuture.toObservableFuture(future)).subscribeOn(scheduler);
    }

    public static final <T> Observable<T> from(Iterable<? extends T> iterable) {
        return create(new OnSubscribeFromIterable(iterable));
    }

    public static final <T> Observable<T> from(T[] tArr) {
        return from(Arrays.asList(tArr));
    }

    public static final Observable<Long> interval(long j, TimeUnit timeUnit) {
        return interval(j, j, timeUnit, Schedulers.computation());
    }

    public static final Observable<Long> interval(long j, TimeUnit timeUnit, Scheduler scheduler) {
        return interval(j, j, timeUnit, scheduler);
    }

    public static final Observable<Long> interval(long j, long j2, TimeUnit timeUnit) {
        return interval(j, j2, timeUnit, Schedulers.computation());
    }

    public static final Observable<Long> interval(long j, long j2, TimeUnit timeUnit, Scheduler scheduler) {
        return create(new OnSubscribeTimerPeriodically(j, j2, timeUnit, scheduler));
    }

    public static final <T> Observable<T> just(T t) {
        return ScalarSynchronousObservable.create(t);
    }

    public static final <T> Observable<T> just(T t, T t2) {
        return from(Arrays.asList(t, t2));
    }

    public static final <T> Observable<T> just(T t, T t2, T t3) {
        return from(Arrays.asList(t, t2, t3));
    }

    public static final <T> Observable<T> just(T t, T t2, T t3, T t4) {
        return from(Arrays.asList(t, t2, t3, t4));
    }

    public static final <T> Observable<T> just(T t, T t2, T t3, T t4, T t5) {
        return from(Arrays.asList(t, t2, t3, t4, t5));
    }

    public static final <T> Observable<T> just(T t, T t2, T t3, T t4, T t5, T t6) {
        return from(Arrays.asList(t, t2, t3, t4, t5, t6));
    }

    public static final <T> Observable<T> just(T t, T t2, T t3, T t4, T t5, T t6, T t7) {
        return from(Arrays.asList(t, t2, t3, t4, t5, t6, t7));
    }

    public static final <T> Observable<T> just(T t, T t2, T t3, T t4, T t5, T t6, T t7, T t8) {
        return from(Arrays.asList(t, t2, t3, t4, t5, t6, t7, t8));
    }

    public static final <T> Observable<T> just(T t, T t2, T t3, T t4, T t5, T t6, T t7, T t8, T t9) {
        return from(Arrays.asList(t, t2, t3, t4, t5, t6, t7, t8, t9));
    }

    public static final <T> Observable<T> just(T t, T t2, T t3, T t4, T t5, T t6, T t7, T t8, T t9, T t10) {
        return from(Arrays.asList(t, t2, t3, t4, t5, t6, t7, t8, t9, t10));
    }

    public static final <T> Observable<T> merge(Iterable<? extends Observable<? extends T>> iterable) {
        return merge(from(iterable));
    }

    public static final <T> Observable<T> merge(Iterable<? extends Observable<? extends T>> iterable, int i) {
        return merge(from(iterable), i);
    }

    public static final <T> Observable<T> merge(Observable<? extends Observable<? extends T>> observable) {
        if (observable.getClass() == ScalarSynchronousObservable.class) {
            return ((ScalarSynchronousObservable) observable).scalarFlatMap(UtilityFunctions.identity());
        }
        return (Observable<T>) observable.lift(OperatorMerge.instance(false));
    }

    public static final <T> Observable<T> merge(Observable<? extends Observable<? extends T>> observable, int i) {
        if (observable.getClass() == ScalarSynchronousObservable.class) {
            return ((ScalarSynchronousObservable) observable).scalarFlatMap(UtilityFunctions.identity());
        }
        return (Observable<T>) observable.lift(OperatorMerge.instance(false, i));
    }

    public static final <T> Observable<T> merge(Observable<? extends T> observable, Observable<? extends T> observable2) {
        return merge(from(Arrays.asList(observable, observable2)));
    }

    public static final <T> Observable<T> merge(Observable<? extends T> observable, Observable<? extends T> observable2, Observable<? extends T> observable3) {
        return merge(from(Arrays.asList(observable, observable2, observable3)));
    }

    public static final <T> Observable<T> merge(Observable<? extends T> observable, Observable<? extends T> observable2, Observable<? extends T> observable3, Observable<? extends T> observable4) {
        return merge(from(Arrays.asList(observable, observable2, observable3, observable4)));
    }

    public static final <T> Observable<T> merge(Observable<? extends T> observable, Observable<? extends T> observable2, Observable<? extends T> observable3, Observable<? extends T> observable4, Observable<? extends T> observable5) {
        return merge(from(Arrays.asList(observable, observable2, observable3, observable4, observable5)));
    }

    public static final <T> Observable<T> merge(Observable<? extends T> observable, Observable<? extends T> observable2, Observable<? extends T> observable3, Observable<? extends T> observable4, Observable<? extends T> observable5, Observable<? extends T> observable6) {
        return merge(from(Arrays.asList(observable, observable2, observable3, observable4, observable5, observable6)));
    }

    public static final <T> Observable<T> merge(Observable<? extends T> observable, Observable<? extends T> observable2, Observable<? extends T> observable3, Observable<? extends T> observable4, Observable<? extends T> observable5, Observable<? extends T> observable6, Observable<? extends T> observable7) {
        return merge(from(Arrays.asList(observable, observable2, observable3, observable4, observable5, observable6, observable7)));
    }

    public static final <T> Observable<T> merge(Observable<? extends T> observable, Observable<? extends T> observable2, Observable<? extends T> observable3, Observable<? extends T> observable4, Observable<? extends T> observable5, Observable<? extends T> observable6, Observable<? extends T> observable7, Observable<? extends T> observable8) {
        return merge(from(Arrays.asList(observable, observable2, observable3, observable4, observable5, observable6, observable7, observable8)));
    }

    public static final <T> Observable<T> merge(Observable<? extends T> observable, Observable<? extends T> observable2, Observable<? extends T> observable3, Observable<? extends T> observable4, Observable<? extends T> observable5, Observable<? extends T> observable6, Observable<? extends T> observable7, Observable<? extends T> observable8, Observable<? extends T> observable9) {
        return merge(from(Arrays.asList(observable, observable2, observable3, observable4, observable5, observable6, observable7, observable8, observable9)));
    }

    public static final <T> Observable<T> merge(Observable<? extends T>[] observableArr) {
        return merge(from(observableArr));
    }

    public static final <T> Observable<T> merge(Observable<? extends T>[] observableArr, int i) {
        return merge(from(observableArr), i);
    }

    public static final <T> Observable<T> mergeDelayError(Observable<? extends Observable<? extends T>> observable) {
        return (Observable<T>) observable.lift(OperatorMerge.instance(true));
    }

    public static final <T> Observable<T> mergeDelayError(Observable<? extends Observable<? extends T>> observable, int i) {
        return (Observable<T>) observable.lift(OperatorMerge.instance(true, i));
    }

    public static final <T> Observable<T> mergeDelayError(Observable<? extends T> observable, Observable<? extends T> observable2) {
        return mergeDelayError(just(observable, observable2));
    }

    public static final <T> Observable<T> mergeDelayError(Observable<? extends T> observable, Observable<? extends T> observable2, Observable<? extends T> observable3) {
        return mergeDelayError(just(observable, observable2, observable3));
    }

    public static final <T> Observable<T> mergeDelayError(Observable<? extends T> observable, Observable<? extends T> observable2, Observable<? extends T> observable3, Observable<? extends T> observable4) {
        return mergeDelayError(just(observable, observable2, observable3, observable4));
    }

    public static final <T> Observable<T> mergeDelayError(Observable<? extends T> observable, Observable<? extends T> observable2, Observable<? extends T> observable3, Observable<? extends T> observable4, Observable<? extends T> observable5) {
        return mergeDelayError(just(observable, observable2, observable3, observable4, observable5));
    }

    public static final <T> Observable<T> mergeDelayError(Observable<? extends T> observable, Observable<? extends T> observable2, Observable<? extends T> observable3, Observable<? extends T> observable4, Observable<? extends T> observable5, Observable<? extends T> observable6) {
        return mergeDelayError(just(observable, observable2, observable3, observable4, observable5, observable6));
    }

    public static final <T> Observable<T> mergeDelayError(Observable<? extends T> observable, Observable<? extends T> observable2, Observable<? extends T> observable3, Observable<? extends T> observable4, Observable<? extends T> observable5, Observable<? extends T> observable6, Observable<? extends T> observable7) {
        return mergeDelayError(just(observable, observable2, observable3, observable4, observable5, observable6, observable7));
    }

    public static final <T> Observable<T> mergeDelayError(Observable<? extends T> observable, Observable<? extends T> observable2, Observable<? extends T> observable3, Observable<? extends T> observable4, Observable<? extends T> observable5, Observable<? extends T> observable6, Observable<? extends T> observable7, Observable<? extends T> observable8) {
        return mergeDelayError(just(observable, observable2, observable3, observable4, observable5, observable6, observable7, observable8));
    }

    public static final <T> Observable<T> mergeDelayError(Observable<? extends T> observable, Observable<? extends T> observable2, Observable<? extends T> observable3, Observable<? extends T> observable4, Observable<? extends T> observable5, Observable<? extends T> observable6, Observable<? extends T> observable7, Observable<? extends T> observable8, Observable<? extends T> observable9) {
        return mergeDelayError(just(observable, observable2, observable3, observable4, observable5, observable6, observable7, observable8, observable9));
    }

    public final Observable<Observable<T>> nest() {
        return just(this);
    }

    public static final <T> Observable<T> never() {
        return NeverObservable.instance();
    }

    public static final Observable<Integer> range(int i, int i2) {
        if (i2 < 0) {
            throw new IllegalArgumentException("Count can not be negative");
        }
        if (i2 == 0) {
            return empty();
        }
        if (i > (Integer.MAX_VALUE - i2) + 1) {
            throw new IllegalArgumentException("start + count can not exceed Integer.MAX_VALUE");
        }
        if (i2 == 1) {
            return just(Integer.valueOf(i));
        }
        return create(new OnSubscribeRange(i, (i2 - 1) + i));
    }

    public static final Observable<Integer> range(int i, int i2, Scheduler scheduler) {
        return range(i, i2).subscribeOn(scheduler);
    }

    public static final <T> Observable<Boolean> sequenceEqual(Observable<? extends T> observable, Observable<? extends T> observable2) {
        return sequenceEqual(observable, observable2, new Func2<T, T, Boolean>() { // from class: rx.Observable.3
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // rx.functions.Func2
            public final Boolean call(T t, T t2) {
                if (t == null) {
                    return Boolean.valueOf(t2 == null);
                }
                return Boolean.valueOf(t.equals(t2));
            }
        });
    }

    public static final <T> Observable<Boolean> sequenceEqual(Observable<? extends T> observable, Observable<? extends T> observable2, Func2<? super T, ? super T, Boolean> func2) {
        return OperatorSequenceEqual.sequenceEqual(observable, observable2, func2);
    }

    public static final <T> Observable<T> switchOnNext(Observable<? extends Observable<? extends T>> observable) {
        return (Observable<T>) observable.lift(OperatorSwitch.instance());
    }

    @Deprecated
    public static final Observable<Long> timer(long j, long j2, TimeUnit timeUnit) {
        return interval(j, j2, timeUnit, Schedulers.computation());
    }

    @Deprecated
    public static final Observable<Long> timer(long j, long j2, TimeUnit timeUnit, Scheduler scheduler) {
        return interval(j, j2, timeUnit, scheduler);
    }

    public static final Observable<Long> timer(long j, TimeUnit timeUnit) {
        return timer(j, timeUnit, Schedulers.computation());
    }

    public static final Observable<Long> timer(long j, TimeUnit timeUnit, Scheduler scheduler) {
        return create(new OnSubscribeTimerOnce(j, timeUnit, scheduler));
    }

    public static final <T, Resource> Observable<T> using(Func0<Resource> func0, Func1<? super Resource, ? extends Observable<? extends T>> func1, Action1<? super Resource> action1) {
        return using(func0, func1, action1, false);
    }

    public static final <T, Resource> Observable<T> using(Func0<Resource> func0, Func1<? super Resource, ? extends Observable<? extends T>> func1, Action1<? super Resource> action1, boolean z) {
        return create(new OnSubscribeUsing(func0, func1, action1, z));
    }

    public static final <R> Observable<R> zip(Iterable<? extends Observable<?>> iterable, FuncN<? extends R> funcN) {
        ArrayList arrayList = new ArrayList();
        Iterator<? extends Observable<?>> it2 = iterable.iterator();
        while (it2.hasNext()) {
            arrayList.add(it2.next());
        }
        return just(arrayList.toArray(new Observable[arrayList.size()])).lift(new OperatorZip(funcN));
    }

    public static final <R> Observable<R> zip(Observable<? extends Observable<?>> observable, FuncN<? extends R> funcN) {
        return observable.toList().map(new Func1<List<? extends Observable<?>>, Observable<?>[]>() { // from class: rx.Observable.4
            @Override // rx.functions.Func1
            public Observable<?>[] call(List<? extends Observable<?>> list) {
                return (Observable[]) list.toArray(new Observable[list.size()]);
            }
        }).lift(new OperatorZip(funcN));
    }

    public static final <T1, T2, R> Observable<R> zip(Observable<? extends T1> observable, Observable<? extends T2> observable2, Func2<? super T1, ? super T2, ? extends R> func2) {
        return just(new Observable[]{observable, observable2}).lift(new OperatorZip(func2));
    }

    public static final <T1, T2, T3, R> Observable<R> zip(Observable<? extends T1> observable, Observable<? extends T2> observable2, Observable<? extends T3> observable3, Func3<? super T1, ? super T2, ? super T3, ? extends R> func3) {
        return just(new Observable[]{observable, observable2, observable3}).lift(new OperatorZip(func3));
    }

    public static final <T1, T2, T3, T4, R> Observable<R> zip(Observable<? extends T1> observable, Observable<? extends T2> observable2, Observable<? extends T3> observable3, Observable<? extends T4> observable4, Func4<? super T1, ? super T2, ? super T3, ? super T4, ? extends R> func4) {
        return just(new Observable[]{observable, observable2, observable3, observable4}).lift(new OperatorZip(func4));
    }

    public static final <T1, T2, T3, T4, T5, R> Observable<R> zip(Observable<? extends T1> observable, Observable<? extends T2> observable2, Observable<? extends T3> observable3, Observable<? extends T4> observable4, Observable<? extends T5> observable5, Func5<? super T1, ? super T2, ? super T3, ? super T4, ? super T5, ? extends R> func5) {
        return just(new Observable[]{observable, observable2, observable3, observable4, observable5}).lift(new OperatorZip(func5));
    }

    public static final <T1, T2, T3, T4, T5, T6, R> Observable<R> zip(Observable<? extends T1> observable, Observable<? extends T2> observable2, Observable<? extends T3> observable3, Observable<? extends T4> observable4, Observable<? extends T5> observable5, Observable<? extends T6> observable6, Func6<? super T1, ? super T2, ? super T3, ? super T4, ? super T5, ? super T6, ? extends R> func6) {
        return just(new Observable[]{observable, observable2, observable3, observable4, observable5, observable6}).lift(new OperatorZip(func6));
    }

    public static final <T1, T2, T3, T4, T5, T6, T7, R> Observable<R> zip(Observable<? extends T1> observable, Observable<? extends T2> observable2, Observable<? extends T3> observable3, Observable<? extends T4> observable4, Observable<? extends T5> observable5, Observable<? extends T6> observable6, Observable<? extends T7> observable7, Func7<? super T1, ? super T2, ? super T3, ? super T4, ? super T5, ? super T6, ? super T7, ? extends R> func7) {
        return just(new Observable[]{observable, observable2, observable3, observable4, observable5, observable6, observable7}).lift(new OperatorZip(func7));
    }

    public static final <T1, T2, T3, T4, T5, T6, T7, T8, R> Observable<R> zip(Observable<? extends T1> observable, Observable<? extends T2> observable2, Observable<? extends T3> observable3, Observable<? extends T4> observable4, Observable<? extends T5> observable5, Observable<? extends T6> observable6, Observable<? extends T7> observable7, Observable<? extends T8> observable8, Func8<? super T1, ? super T2, ? super T3, ? super T4, ? super T5, ? super T6, ? super T7, ? super T8, ? extends R> func8) {
        return just(new Observable[]{observable, observable2, observable3, observable4, observable5, observable6, observable7, observable8}).lift(new OperatorZip(func8));
    }

    public static final <T1, T2, T3, T4, T5, T6, T7, T8, T9, R> Observable<R> zip(Observable<? extends T1> observable, Observable<? extends T2> observable2, Observable<? extends T3> observable3, Observable<? extends T4> observable4, Observable<? extends T5> observable5, Observable<? extends T6> observable6, Observable<? extends T7> observable7, Observable<? extends T8> observable8, Observable<? extends T9> observable9, Func9<? super T1, ? super T2, ? super T3, ? super T4, ? super T5, ? super T6, ? super T7, ? super T8, ? super T9, ? extends R> func9) {
        return just(new Observable[]{observable, observable2, observable3, observable4, observable5, observable6, observable7, observable8, observable9}).lift(new OperatorZip(func9));
    }

    public final Observable<Boolean> all(Func1<? super T, Boolean> func1) {
        return lift(new OperatorAll(func1));
    }

    public final Observable<T> ambWith(Observable<? extends T> observable) {
        return amb(this, observable);
    }

    public final Observable<T> asObservable() {
        return (Observable<T>) lift(OperatorAsObservable.instance());
    }

    public final <TClosing> Observable<List<T>> buffer(Func0<? extends Observable<? extends TClosing>> func0) {
        return (Observable<List<T>>) lift(new OperatorBufferWithSingleObservable(func0, 16));
    }

    public final Observable<List<T>> buffer(int i) {
        return buffer(i, i);
    }

    public final Observable<List<T>> buffer(int i, int i2) {
        return (Observable<List<T>>) lift(new OperatorBufferWithSize(i, i2));
    }

    public final Observable<List<T>> buffer(long j, long j2, TimeUnit timeUnit) {
        return buffer(j, j2, timeUnit, Schedulers.computation());
    }

    public final Observable<List<T>> buffer(long j, long j2, TimeUnit timeUnit, Scheduler scheduler) {
        return (Observable<List<T>>) lift(new OperatorBufferWithTime(j, j2, timeUnit, Integer.MAX_VALUE, scheduler));
    }

    public final Observable<List<T>> buffer(long j, TimeUnit timeUnit) {
        return buffer(j, timeUnit, Integer.MAX_VALUE, Schedulers.computation());
    }

    public final Observable<List<T>> buffer(long j, TimeUnit timeUnit, int i) {
        return (Observable<List<T>>) lift(new OperatorBufferWithTime(j, j, timeUnit, i, Schedulers.computation()));
    }

    public final Observable<List<T>> buffer(long j, TimeUnit timeUnit, int i, Scheduler scheduler) {
        return (Observable<List<T>>) lift(new OperatorBufferWithTime(j, j, timeUnit, i, scheduler));
    }

    public final Observable<List<T>> buffer(long j, TimeUnit timeUnit, Scheduler scheduler) {
        return buffer(j, j, timeUnit, scheduler);
    }

    public final <TOpening, TClosing> Observable<List<T>> buffer(Observable<? extends TOpening> observable, Func1<? super TOpening, ? extends Observable<? extends TClosing>> func1) {
        return (Observable<List<T>>) lift(new OperatorBufferWithStartEndObservable(observable, func1));
    }

    public final <B> Observable<List<T>> buffer(Observable<B> observable) {
        return buffer(observable, 16);
    }

    public final <B> Observable<List<T>> buffer(Observable<B> observable, int i) {
        return (Observable<List<T>>) lift(new OperatorBufferWithSingleObservable(observable, i));
    }

    public final Observable<T> cache() {
        return CachedObservable.from(this);
    }

    public final Observable<T> cache(int i) {
        return CachedObservable.from(this, i);
    }

    public final <R> Observable<R> cast(Class<R> cls) {
        return lift(new OperatorCast(cls));
    }

    public final <R> Observable<R> collect(Func0<R> func0, final Action2<R, ? super T> action2) {
        return lift(new OperatorScan((Func0) func0, (Func2) new Func2<R, T, R>() { // from class: rx.Observable.5
            @Override // rx.functions.Func2
            public final R call(R r, T t) {
                action2.call(r, t);
                return r;
            }
        })).last();
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final <R> Observable<R> concatMap(Func1<? super T, ? extends Observable<? extends R>> func1) {
        return concat(map(func1));
    }

    public final Observable<T> concatWith(Observable<? extends T> observable) {
        return concat(this, observable);
    }

    public final Observable<Boolean> contains(final Object obj) {
        return exists(new Func1<T, Boolean>() { // from class: rx.Observable.6
            @Override // rx.functions.Func1
            public /* bridge */ /* synthetic */ Boolean call(Object obj2) {
                return call((AnonymousClass6) obj2);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // rx.functions.Func1
            public final Boolean call(T t) {
                Object obj2 = obj;
                return Boolean.valueOf(obj2 == null ? t == null : obj2.equals(t));
            }
        });
    }

    public final Observable<Integer> count() {
        return reduce(0, CountHolder.INSTANCE);
    }

    private static final class CountHolder {
        static final Func2<Integer, Object, Integer> INSTANCE = new Func2<Integer, Object, Integer>() { // from class: rx.Observable.CountHolder.1
            @Override // rx.functions.Func2
            public final Integer call(Integer num, Object obj) {
                return Integer.valueOf(num.intValue() + 1);
            }
        };

        private CountHolder() {
        }
    }

    public final Observable<Long> countLong() {
        return reduce(0L, CountLongHolder.INSTANCE);
    }

    private static final class CountLongHolder {
        static final Func2<Long, Object, Long> INSTANCE = new Func2<Long, Object, Long>() { // from class: rx.Observable.CountLongHolder.1
            @Override // rx.functions.Func2
            public final Long call(Long l, Object obj) {
                return Long.valueOf(l.longValue() + 1);
            }
        };

        private CountLongHolder() {
        }
    }

    public final <U> Observable<T> debounce(Func1<? super T, ? extends Observable<U>> func1) {
        return (Observable<T>) lift(new OperatorDebounceWithSelector(func1));
    }

    public final Observable<T> debounce(long j, TimeUnit timeUnit) {
        return debounce(j, timeUnit, Schedulers.computation());
    }

    public final Observable<T> debounce(long j, TimeUnit timeUnit, Scheduler scheduler) {
        return (Observable<T>) lift(new OperatorDebounceWithTime(j, timeUnit, scheduler));
    }

    public final Observable<T> defaultIfEmpty(final T t) {
        return switchIfEmpty(create(new OnSubscribe<T>() { // from class: rx.Observable.7
            @Override // rx.functions.Action1
            public void call(Subscriber<? super T> subscriber) {
                subscriber.setProducer(new SingleProducer(subscriber, t));
            }
        }));
    }

    public final Observable<T> switchIfEmpty(Observable<? extends T> observable) {
        return (Observable<T>) lift(new OperatorSwitchIfEmpty(observable));
    }

    public final <U, V> Observable<T> delay(Func0<? extends Observable<U>> func0, Func1<? super T, ? extends Observable<V>> func1) {
        return (Observable<T>) delaySubscription(func0).lift(new OperatorDelayWithSelector(this, func1));
    }

    public final <U> Observable<T> delay(Func1<? super T, ? extends Observable<U>> func1) {
        return (Observable<T>) lift(new OperatorDelayWithSelector(this, func1));
    }

    public final Observable<T> delay(long j, TimeUnit timeUnit) {
        return delay(j, timeUnit, Schedulers.computation());
    }

    public final Observable<T> delay(long j, TimeUnit timeUnit, Scheduler scheduler) {
        return (Observable<T>) lift(new OperatorDelay(this, j, timeUnit, scheduler));
    }

    public final Observable<T> delaySubscription(long j, TimeUnit timeUnit) {
        return delaySubscription(j, timeUnit, Schedulers.computation());
    }

    public final Observable<T> delaySubscription(long j, TimeUnit timeUnit, Scheduler scheduler) {
        return create(new OnSubscribeDelaySubscription(this, j, timeUnit, scheduler));
    }

    public final <U> Observable<T> delaySubscription(Func0<? extends Observable<U>> func0) {
        return create(new OnSubscribeDelaySubscriptionWithSelector(this, func0));
    }

    public final <T2> Observable<T2> dematerialize() {
        return (Observable<T2>) lift(OperatorDematerialize.instance());
    }

    public final Observable<T> distinct() {
        return (Observable<T>) lift(OperatorDistinct.instance());
    }

    public final <U> Observable<T> distinct(Func1<? super T, ? extends U> func1) {
        return (Observable<T>) lift(new OperatorDistinct(func1));
    }

    public final Observable<T> distinctUntilChanged() {
        return (Observable<T>) lift(OperatorDistinctUntilChanged.instance());
    }

    public final <U> Observable<T> distinctUntilChanged(Func1<? super T, ? extends U> func1) {
        return (Observable<T>) lift(new OperatorDistinctUntilChanged(func1));
    }

    public final Observable<T> doOnCompleted(final Action0 action0) {
        return (Observable<T>) lift(new OperatorDoOnEach(new Observer<T>() { // from class: rx.Observable.8
            @Override // rx.Observer
            public final void onError(Throwable th) {
            }

            @Override // rx.Observer
            public final void onNext(T t) {
            }

            @Override // rx.Observer
            public final void onCompleted() {
                action0.call();
            }
        }));
    }

    public final Observable<T> doOnEach(final Action1<Notification<? super T>> action1) {
        return (Observable<T>) lift(new OperatorDoOnEach(new Observer<T>() { // from class: rx.Observable.9
            @Override // rx.Observer
            public final void onCompleted() {
                action1.call(Notification.createOnCompleted());
            }

            @Override // rx.Observer
            public final void onError(Throwable th) {
                action1.call(Notification.createOnError(th));
            }

            @Override // rx.Observer
            public final void onNext(T t) {
                action1.call(Notification.createOnNext(t));
            }
        }));
    }

    public final Observable<T> doOnEach(Observer<? super T> observer) {
        return (Observable<T>) lift(new OperatorDoOnEach(observer));
    }

    public final Observable<T> doOnError(final Action1<Throwable> action1) {
        return (Observable<T>) lift(new OperatorDoOnEach(new Observer<T>() { // from class: rx.Observable.10
            @Override // rx.Observer
            public final void onCompleted() {
            }

            @Override // rx.Observer
            public final void onNext(T t) {
            }

            @Override // rx.Observer
            public final void onError(Throwable th) {
                action1.call(th);
            }
        }));
    }

    public final Observable<T> doOnNext(final Action1<? super T> action1) {
        return (Observable<T>) lift(new OperatorDoOnEach(new Observer<T>() { // from class: rx.Observable.11
            @Override // rx.Observer
            public final void onCompleted() {
            }

            @Override // rx.Observer
            public final void onError(Throwable th) {
            }

            @Override // rx.Observer
            public final void onNext(T t) {
                action1.call(t);
            }
        }));
    }

    public final Observable<T> doOnRequest(Action1<Long> action1) {
        return (Observable<T>) lift(new OperatorDoOnRequest(action1));
    }

    public final Observable<T> doOnSubscribe(Action0 action0) {
        return (Observable<T>) lift(new OperatorDoOnSubscribe(action0));
    }

    public final Observable<T> doOnTerminate(final Action0 action0) {
        return (Observable<T>) lift(new OperatorDoOnEach(new Observer<T>() { // from class: rx.Observable.12
            @Override // rx.Observer
            public final void onNext(T t) {
            }

            @Override // rx.Observer
            public final void onCompleted() {
                action0.call();
            }

            @Override // rx.Observer
            public final void onError(Throwable th) {
                action0.call();
            }
        }));
    }

    public final Observable<T> doOnUnsubscribe(Action0 action0) {
        return (Observable<T>) lift(new OperatorDoOnUnsubscribe(action0));
    }

    public final Observable<T> elementAt(int i) {
        return (Observable<T>) lift(new OperatorElementAt(i));
    }

    public final Observable<T> elementAtOrDefault(int i, T t) {
        return (Observable<T>) lift(new OperatorElementAt(i, t));
    }

    public final Observable<Boolean> exists(Func1<? super T, Boolean> func1) {
        return lift(new OperatorAny(func1, false));
    }

    public final Observable<T> filter(Func1<? super T, Boolean> func1) {
        return (Observable<T>) lift(new OperatorFilter(func1));
    }

    public final Observable<T> finallyDo(Action0 action0) {
        return (Observable<T>) lift(new OperatorFinally(action0));
    }

    public final Observable<T> first() {
        return take(1).single();
    }

    public final Observable<T> first(Func1<? super T, Boolean> func1) {
        return takeFirst(func1).single();
    }

    public final Observable<T> firstOrDefault(T t) {
        return take(1).singleOrDefault(t);
    }

    public final Observable<T> firstOrDefault(T t, Func1<? super T, Boolean> func1) {
        return takeFirst(func1).singleOrDefault(t);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final <R> Observable<R> flatMap(Func1<? super T, ? extends Observable<? extends R>> func1) {
        if (getClass() == ScalarSynchronousObservable.class) {
            return ((ScalarSynchronousObservable) this).scalarFlatMap(func1);
        }
        return merge(map(func1));
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final <R> Observable<R> flatMap(Func1<? super T, ? extends Observable<? extends R>> func1, int i) {
        if (getClass() == ScalarSynchronousObservable.class) {
            return ((ScalarSynchronousObservable) this).scalarFlatMap(func1);
        }
        return merge(map(func1), i);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final <R> Observable<R> flatMap(Func1<? super T, ? extends Observable<? extends R>> func1, Func1<? super Throwable, ? extends Observable<? extends R>> func12, Func0<? extends Observable<? extends R>> func0) {
        return merge(mapNotification(func1, func12, func0));
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final <R> Observable<R> flatMap(Func1<? super T, ? extends Observable<? extends R>> func1, Func1<? super Throwable, ? extends Observable<? extends R>> func12, Func0<? extends Observable<? extends R>> func0, int i) {
        return merge(mapNotification(func1, func12, func0), i);
    }

    public final <U, R> Observable<R> flatMap(Func1<? super T, ? extends Observable<? extends U>> func1, Func2<? super T, ? super U, ? extends R> func2) {
        return merge(lift(new OperatorMapPair(func1, func2)));
    }

    public final <U, R> Observable<R> flatMap(Func1<? super T, ? extends Observable<? extends U>> func1, Func2<? super T, ? super U, ? extends R> func2, int i) {
        return merge(lift(new OperatorMapPair(func1, func2)), i);
    }

    public final <R> Observable<R> flatMapIterable(Func1<? super T, ? extends Iterable<? extends R>> func1) {
        return merge(map(OperatorMapPair.convertSelector(func1)));
    }

    public final <U, R> Observable<R> flatMapIterable(Func1<? super T, ? extends Iterable<? extends U>> func1, Func2<? super T, ? super U, ? extends R> func2) {
        return flatMap(OperatorMapPair.convertSelector(func1), func2);
    }

    public final void forEach(Action1<? super T> action1) {
        subscribe(action1);
    }

    public final void forEach(Action1<? super T> action1, Action1<Throwable> action12) {
        subscribe(action1, action12);
    }

    public final void forEach(Action1<? super T> action1, Action1<Throwable> action12, Action0 action0) {
        subscribe(action1, action12, action0);
    }

    public final <K, R> Observable<GroupedObservable<K, R>> groupBy(Func1<? super T, ? extends K> func1, Func1<? super T, ? extends R> func12) {
        return lift(new OperatorGroupBy(func1, func12));
    }

    public final <K> Observable<GroupedObservable<K, T>> groupBy(Func1<? super T, ? extends K> func1) {
        return (Observable<GroupedObservable<K, T>>) lift(new OperatorGroupBy(func1));
    }

    public final <T2, D1, D2, R> Observable<R> groupJoin(Observable<T2> observable, Func1<? super T, ? extends Observable<D1>> func1, Func1<? super T2, ? extends Observable<D2>> func12, Func2<? super T, ? super Observable<T2>, ? extends R> func2) {
        return create(new OnSubscribeGroupJoin(this, observable, func1, func12, func2));
    }

    public final Observable<T> ignoreElements() {
        return (Observable<T>) lift(OperatorIgnoreElements.instance());
    }

    public final Observable<Boolean> isEmpty() {
        return lift(HolderAnyForEmpty.INSTANCE);
    }

    private static class HolderAnyForEmpty {
        static final OperatorAny<?> INSTANCE = new OperatorAny<>(UtilityFunctions.alwaysTrue(), true);

        private HolderAnyForEmpty() {
        }
    }

    public final <TRight, TLeftDuration, TRightDuration, R> Observable<R> join(Observable<TRight> observable, Func1<T, Observable<TLeftDuration>> func1, Func1<TRight, Observable<TRightDuration>> func12, Func2<T, TRight, R> func2) {
        return create(new OnSubscribeJoin(this, observable, func1, func12, func2));
    }

    public final Observable<T> last() {
        return takeLast(1).single();
    }

    public final Observable<T> last(Func1<? super T, Boolean> func1) {
        return filter(func1).takeLast(1).single();
    }

    public final Observable<T> lastOrDefault(T t) {
        return takeLast(1).singleOrDefault(t);
    }

    public final Observable<T> lastOrDefault(T t, Func1<? super T, Boolean> func1) {
        return filter(func1).takeLast(1).singleOrDefault(t);
    }

    public final Observable<T> limit(int i) {
        return take(i);
    }

    public final <R> Observable<R> map(Func1<? super T, ? extends R> func1) {
        return lift(new OperatorMap(func1));
    }

    private final <R> Observable<R> mapNotification(Func1<? super T, ? extends R> func1, Func1<? super Throwable, ? extends R> func12, Func0<? extends R> func0) {
        return lift(new OperatorMapNotification(func1, func12, func0));
    }

    public final Observable<Notification<T>> materialize() {
        return (Observable<Notification<T>>) lift(OperatorMaterialize.instance());
    }

    public final Observable<T> mergeWith(Observable<? extends T> observable) {
        return merge(this, observable);
    }

    public final Observable<T> observeOn(Scheduler scheduler) {
        if (this instanceof ScalarSynchronousObservable) {
            return ((ScalarSynchronousObservable) this).scalarScheduleOn(scheduler);
        }
        return (Observable<T>) lift(new OperatorObserveOn(scheduler));
    }

    public final <R> Observable<R> ofType(final Class<R> cls) {
        return filter(new Func1<T, Boolean>() { // from class: rx.Observable.13
            @Override // rx.functions.Func1
            public /* bridge */ /* synthetic */ Boolean call(Object obj) {
                return call((AnonymousClass13) obj);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // rx.functions.Func1
            public final Boolean call(T t) {
                return Boolean.valueOf(cls.isInstance(t));
            }
        }).cast(cls);
    }

    public final Observable<T> onBackpressureBuffer() {
        return (Observable<T>) lift(OperatorOnBackpressureBuffer.instance());
    }

    public final Observable<T> onBackpressureBuffer(long j) {
        return (Observable<T>) lift(new OperatorOnBackpressureBuffer(j));
    }

    public final Observable<T> onBackpressureBuffer(long j, Action0 action0) {
        return (Observable<T>) lift(new OperatorOnBackpressureBuffer(j, action0));
    }

    public final Observable<T> onBackpressureDrop(Action1<? super T> action1) {
        return (Observable<T>) lift(new OperatorOnBackpressureDrop(action1));
    }

    public final Observable<T> onBackpressureDrop() {
        return (Observable<T>) lift(OperatorOnBackpressureDrop.instance());
    }

    @Deprecated
    public final Observable<T> onBackpressureBlock(int i) {
        return (Observable<T>) lift(new OperatorOnBackpressureBlock(i));
    }

    @Deprecated
    public final Observable<T> onBackpressureBlock() {
        return onBackpressureBlock(RxRingBuffer.SIZE);
    }

    public final Observable<T> onBackpressureLatest() {
        return (Observable<T>) lift(OperatorOnBackpressureLatest.instance());
    }

    public final Observable<T> onErrorResumeNext(Func1<Throwable, ? extends Observable<? extends T>> func1) {
        return (Observable<T>) lift(new OperatorOnErrorResumeNextViaFunction(func1));
    }

    public final Observable<T> onErrorResumeNext(Observable<? extends T> observable) {
        return (Observable<T>) lift(new OperatorOnErrorResumeNextViaObservable(observable));
    }

    public final Observable<T> onErrorReturn(Func1<Throwable, ? extends T> func1) {
        return (Observable<T>) lift(new OperatorOnErrorReturn(func1));
    }

    public final Observable<T> onExceptionResumeNext(Observable<? extends T> observable) {
        return (Observable<T>) lift(new OperatorOnExceptionResumeNextViaObservable(observable));
    }

    public final ConnectableObservable<T> publish() {
        return OperatorPublish.create(this);
    }

    public final <R> Observable<R> publish(Func1<? super Observable<T>, ? extends Observable<R>> func1) {
        return OperatorPublish.create(this, func1);
    }

    public final Observable<T> reduce(Func2<T, T, T> func2) {
        return scan(func2).last();
    }

    public final <R> Observable<R> reduce(R r, Func2<R, ? super T, R> func2) {
        return scan(r, func2).takeLast(1);
    }

    public final Observable<T> repeat() {
        return OnSubscribeRedo.repeat(this);
    }

    public final Observable<T> repeat(Scheduler scheduler) {
        return OnSubscribeRedo.repeat(this, scheduler);
    }

    public final Observable<T> repeat(long j) {
        return OnSubscribeRedo.repeat(this, j);
    }

    public final Observable<T> repeat(long j, Scheduler scheduler) {
        return OnSubscribeRedo.repeat(this, j, scheduler);
    }

    public final Observable<T> repeatWhen(final Func1<? super Observable<? extends Void>, ? extends Observable<?>> func1, Scheduler scheduler) {
        return OnSubscribeRedo.repeat(this, new Func1<Observable<? extends Notification<?>>, Observable<?>>() { // from class: rx.Observable.14
            /* JADX WARN: Multi-variable type inference failed */
            @Override // rx.functions.Func1
            public Observable<?> call(Observable<? extends Notification<?>> observable) {
                return (Observable) func1.call(observable.map(new Func1<Notification<?>, Void>() { // from class: rx.Observable.14.1
                    @Override // rx.functions.Func1
                    public Void call(Notification<?> notification) {
                        return null;
                    }
                }));
            }
        }, scheduler);
    }

    public final Observable<T> repeatWhen(final Func1<? super Observable<? extends Void>, ? extends Observable<?>> func1) {
        return OnSubscribeRedo.repeat(this, new Func1<Observable<? extends Notification<?>>, Observable<?>>() { // from class: rx.Observable.15
            /* JADX WARN: Multi-variable type inference failed */
            @Override // rx.functions.Func1
            public Observable<?> call(Observable<? extends Notification<?>> observable) {
                return (Observable) func1.call(observable.map(new Func1<Notification<?>, Void>() { // from class: rx.Observable.15.1
                    @Override // rx.functions.Func1
                    public Void call(Notification<?> notification) {
                        return null;
                    }
                }));
            }
        });
    }

    public final ConnectableObservable<T> replay() {
        return OperatorReplay.create(this);
    }

    public final <R> Observable<R> replay(Func1<? super Observable<T>, ? extends Observable<R>> func1) {
        return OperatorReplay.multicastSelector(new Func0<ConnectableObservable<T>>() { // from class: rx.Observable.16
            @Override // rx.functions.Func0, java.util.concurrent.Callable
            public ConnectableObservable<T> call() {
                return Observable.this.replay();
            }
        }, func1);
    }

    public final <R> Observable<R> replay(Func1<? super Observable<T>, ? extends Observable<R>> func1, final int i) {
        return OperatorReplay.multicastSelector(new Func0<ConnectableObservable<T>>() { // from class: rx.Observable.17
            @Override // rx.functions.Func0, java.util.concurrent.Callable
            public ConnectableObservable<T> call() {
                return Observable.this.replay(i);
            }
        }, func1);
    }

    public final <R> Observable<R> replay(Func1<? super Observable<T>, ? extends Observable<R>> func1, int i, long j, TimeUnit timeUnit) {
        return replay(func1, i, j, timeUnit, Schedulers.computation());
    }

    public final <R> Observable<R> replay(Func1<? super Observable<T>, ? extends Observable<R>> func1, final int i, final long j, final TimeUnit timeUnit, final Scheduler scheduler) {
        if (i < 0) {
            throw new IllegalArgumentException("bufferSize < 0");
        }
        return OperatorReplay.multicastSelector(new Func0<ConnectableObservable<T>>() { // from class: rx.Observable.18
            @Override // rx.functions.Func0, java.util.concurrent.Callable
            public ConnectableObservable<T> call() {
                return Observable.this.replay(i, j, timeUnit, scheduler);
            }
        }, func1);
    }

    public final <R> Observable<R> replay(final Func1<? super Observable<T>, ? extends Observable<R>> func1, final int i, final Scheduler scheduler) {
        return OperatorReplay.multicastSelector(new Func0<ConnectableObservable<T>>() { // from class: rx.Observable.19
            @Override // rx.functions.Func0, java.util.concurrent.Callable
            public ConnectableObservable<T> call() {
                return Observable.this.replay(i);
            }
        }, new Func1<Observable<T>, Observable<R>>() { // from class: rx.Observable.20
            @Override // rx.functions.Func1
            public Observable<R> call(Observable<T> observable) {
                return ((Observable) func1.call(observable)).observeOn(scheduler);
            }
        });
    }

    public final <R> Observable<R> replay(Func1<? super Observable<T>, ? extends Observable<R>> func1, long j, TimeUnit timeUnit) {
        return replay(func1, j, timeUnit, Schedulers.computation());
    }

    public final <R> Observable<R> replay(Func1<? super Observable<T>, ? extends Observable<R>> func1, final long j, final TimeUnit timeUnit, final Scheduler scheduler) {
        return OperatorReplay.multicastSelector(new Func0<ConnectableObservable<T>>() { // from class: rx.Observable.21
            @Override // rx.functions.Func0, java.util.concurrent.Callable
            public ConnectableObservable<T> call() {
                return Observable.this.replay(j, timeUnit, scheduler);
            }
        }, func1);
    }

    public final <R> Observable<R> replay(final Func1<? super Observable<T>, ? extends Observable<R>> func1, final Scheduler scheduler) {
        return OperatorReplay.multicastSelector(new Func0<ConnectableObservable<T>>() { // from class: rx.Observable.22
            @Override // rx.functions.Func0, java.util.concurrent.Callable
            public ConnectableObservable<T> call() {
                return Observable.this.replay();
            }
        }, new Func1<Observable<T>, Observable<R>>() { // from class: rx.Observable.23
            @Override // rx.functions.Func1
            public Observable<R> call(Observable<T> observable) {
                return ((Observable) func1.call(observable)).observeOn(scheduler);
            }
        });
    }

    public final ConnectableObservable<T> replay(int i) {
        return OperatorReplay.create(this, i);
    }

    public final ConnectableObservable<T> replay(int i, long j, TimeUnit timeUnit) {
        return replay(i, j, timeUnit, Schedulers.computation());
    }

    public final ConnectableObservable<T> replay(int i, long j, TimeUnit timeUnit, Scheduler scheduler) {
        if (i < 0) {
            throw new IllegalArgumentException("bufferSize < 0");
        }
        return OperatorReplay.create(this, j, timeUnit, scheduler, i);
    }

    public final ConnectableObservable<T> replay(int i, Scheduler scheduler) {
        return OperatorReplay.observeOn(replay(i), scheduler);
    }

    public final ConnectableObservable<T> replay(long j, TimeUnit timeUnit) {
        return replay(j, timeUnit, Schedulers.computation());
    }

    public final ConnectableObservable<T> replay(long j, TimeUnit timeUnit, Scheduler scheduler) {
        return OperatorReplay.create(this, j, timeUnit, scheduler);
    }

    public final ConnectableObservable<T> replay(Scheduler scheduler) {
        return OperatorReplay.observeOn(replay(), scheduler);
    }

    public final Observable<T> retry() {
        return OnSubscribeRedo.retry(this);
    }

    public final Observable<T> retry(long j) {
        return OnSubscribeRedo.retry(this, j);
    }

    public final Observable<T> retry(Func2<Integer, Throwable, Boolean> func2) {
        return (Observable<T>) nest().lift(new OperatorRetryWithPredicate(func2));
    }

    public final Observable<T> retryWhen(final Func1<? super Observable<? extends Throwable>, ? extends Observable<?>> func1) {
        return OnSubscribeRedo.retry(this, new Func1<Observable<? extends Notification<?>>, Observable<?>>() { // from class: rx.Observable.24
            /* JADX WARN: Multi-variable type inference failed */
            @Override // rx.functions.Func1
            public Observable<?> call(Observable<? extends Notification<?>> observable) {
                return (Observable) func1.call(observable.map(new Func1<Notification<?>, Throwable>() { // from class: rx.Observable.24.1
                    @Override // rx.functions.Func1
                    public Throwable call(Notification<?> notification) {
                        return notification.getThrowable();
                    }
                }));
            }
        });
    }

    public final Observable<T> retryWhen(final Func1<? super Observable<? extends Throwable>, ? extends Observable<?>> func1, Scheduler scheduler) {
        return OnSubscribeRedo.retry(this, new Func1<Observable<? extends Notification<?>>, Observable<?>>() { // from class: rx.Observable.25
            /* JADX WARN: Multi-variable type inference failed */
            @Override // rx.functions.Func1
            public Observable<?> call(Observable<? extends Notification<?>> observable) {
                return (Observable) func1.call(observable.map(new Func1<Notification<?>, Throwable>() { // from class: rx.Observable.25.1
                    @Override // rx.functions.Func1
                    public Throwable call(Notification<?> notification) {
                        return notification.getThrowable();
                    }
                }));
            }
        }, scheduler);
    }

    public final Observable<T> sample(long j, TimeUnit timeUnit) {
        return sample(j, timeUnit, Schedulers.computation());
    }

    public final Observable<T> sample(long j, TimeUnit timeUnit, Scheduler scheduler) {
        return (Observable<T>) lift(new OperatorSampleWithTime(j, timeUnit, scheduler));
    }

    public final <U> Observable<T> sample(Observable<U> observable) {
        return (Observable<T>) lift(new OperatorSampleWithObservable(observable));
    }

    public final Observable<T> scan(Func2<T, T, T> func2) {
        return (Observable<T>) lift(new OperatorScan(func2));
    }

    public final <R> Observable<R> scan(R r, Func2<R, ? super T, R> func2) {
        return lift(new OperatorScan(r, func2));
    }

    public final Observable<T> serialize() {
        return (Observable<T>) lift(OperatorSerialize.instance());
    }

    public final Observable<T> share() {
        return publish().refCount();
    }

    public final Observable<T> single() {
        return (Observable<T>) lift(OperatorSingle.instance());
    }

    public final Observable<T> single(Func1<? super T, Boolean> func1) {
        return filter(func1).single();
    }

    public final Observable<T> singleOrDefault(T t) {
        return (Observable<T>) lift(new OperatorSingle(t));
    }

    public final Observable<T> singleOrDefault(T t, Func1<? super T, Boolean> func1) {
        return filter(func1).singleOrDefault(t);
    }

    public final Observable<T> skip(int i) {
        return (Observable<T>) lift(new OperatorSkip(i));
    }

    public final Observable<T> skip(long j, TimeUnit timeUnit) {
        return skip(j, timeUnit, Schedulers.computation());
    }

    public final Observable<T> skip(long j, TimeUnit timeUnit, Scheduler scheduler) {
        return (Observable<T>) lift(new OperatorSkipTimed(j, timeUnit, scheduler));
    }

    public final Observable<T> skipLast(int i) {
        return (Observable<T>) lift(new OperatorSkipLast(i));
    }

    public final Observable<T> skipLast(long j, TimeUnit timeUnit) {
        return skipLast(j, timeUnit, Schedulers.computation());
    }

    public final Observable<T> skipLast(long j, TimeUnit timeUnit, Scheduler scheduler) {
        return (Observable<T>) lift(new OperatorSkipLastTimed(j, timeUnit, scheduler));
    }

    public final <U> Observable<T> skipUntil(Observable<U> observable) {
        return (Observable<T>) lift(new OperatorSkipUntil(observable));
    }

    public final Observable<T> skipWhile(Func1<? super T, Boolean> func1) {
        return (Observable<T>) lift(new OperatorSkipWhile(OperatorSkipWhile.toPredicate2(func1)));
    }

    public final Observable<T> startWith(Observable<T> observable) {
        return concat(observable, this);
    }

    public final Observable<T> startWith(Iterable<T> iterable) {
        return concat(from(iterable), this);
    }

    public final Observable<T> startWith(T t) {
        return concat(just(t), this);
    }

    public final Observable<T> startWith(T t, T t2) {
        return concat(just(t, t2), this);
    }

    public final Observable<T> startWith(T t, T t2, T t3) {
        return concat(just(t, t2, t3), this);
    }

    public final Observable<T> startWith(T t, T t2, T t3, T t4) {
        return concat(just(t, t2, t3, t4), this);
    }

    public final Observable<T> startWith(T t, T t2, T t3, T t4, T t5) {
        return concat(just(t, t2, t3, t4, t5), this);
    }

    public final Observable<T> startWith(T t, T t2, T t3, T t4, T t5, T t6) {
        return concat(just(t, t2, t3, t4, t5, t6), this);
    }

    public final Observable<T> startWith(T t, T t2, T t3, T t4, T t5, T t6, T t7) {
        return concat(just(t, t2, t3, t4, t5, t6, t7), this);
    }

    public final Observable<T> startWith(T t, T t2, T t3, T t4, T t5, T t6, T t7, T t8) {
        return concat(just(t, t2, t3, t4, t5, t6, t7, t8), this);
    }

    public final Observable<T> startWith(T t, T t2, T t3, T t4, T t5, T t6, T t7, T t8, T t9) {
        return concat(just(t, t2, t3, t4, t5, t6, t7, t8, t9), this);
    }

    public final Subscription subscribe() {
        return subscribe((Subscriber) new Subscriber<T>() { // from class: rx.Observable.26
            @Override // rx.Observer
            public final void onCompleted() {
            }

            @Override // rx.Observer
            public final void onNext(T t) {
            }

            @Override // rx.Observer
            public final void onError(Throwable th) {
                throw new OnErrorNotImplementedException(th);
            }
        });
    }

    public final Subscription subscribe(final Action1<? super T> action1) {
        if (action1 == null) {
            throw new IllegalArgumentException("onNext can not be null");
        }
        return subscribe((Subscriber) new Subscriber<T>() { // from class: rx.Observable.27
            @Override // rx.Observer
            public final void onCompleted() {
            }

            @Override // rx.Observer
            public final void onError(Throwable th) {
                throw new OnErrorNotImplementedException(th);
            }

            @Override // rx.Observer
            public final void onNext(T t) {
                action1.call(t);
            }
        });
    }

    public final Subscription subscribe(final Action1<? super T> action1, final Action1<Throwable> action12) {
        if (action1 == null) {
            throw new IllegalArgumentException("onNext can not be null");
        }
        if (action12 == null) {
            throw new IllegalArgumentException("onError can not be null");
        }
        return subscribe((Subscriber) new Subscriber<T>() { // from class: rx.Observable.28
            @Override // rx.Observer
            public final void onCompleted() {
            }

            @Override // rx.Observer
            public final void onError(Throwable th) {
                action12.call(th);
            }

            @Override // rx.Observer
            public final void onNext(T t) {
                action1.call(t);
            }
        });
    }

    public final Subscription subscribe(final Action1<? super T> action1, final Action1<Throwable> action12, final Action0 action0) {
        if (action1 == null) {
            throw new IllegalArgumentException("onNext can not be null");
        }
        if (action12 == null) {
            throw new IllegalArgumentException("onError can not be null");
        }
        if (action0 == null) {
            throw new IllegalArgumentException("onComplete can not be null");
        }
        return subscribe((Subscriber) new Subscriber<T>() { // from class: rx.Observable.29
            @Override // rx.Observer
            public final void onCompleted() {
                action0.call();
            }

            @Override // rx.Observer
            public final void onError(Throwable th) {
                action12.call(th);
            }

            @Override // rx.Observer
            public final void onNext(T t) {
                action1.call(t);
            }
        });
    }

    public final Subscription subscribe(final Observer<? super T> observer) {
        if (observer instanceof Subscriber) {
            return subscribe((Subscriber) observer);
        }
        return subscribe((Subscriber) new Subscriber<T>() { // from class: rx.Observable.30
            @Override // rx.Observer
            public void onCompleted() {
                observer.onCompleted();
            }

            @Override // rx.Observer
            public void onError(Throwable th) {
                observer.onError(th);
            }

            @Override // rx.Observer
            public void onNext(T t) {
                observer.onNext(t);
            }
        });
    }

    public final Subscription unsafeSubscribe(Subscriber<? super T> subscriber) {
        try {
            subscriber.onStart();
            hook.onSubscribeStart(this, this.onSubscribe).call(subscriber);
            return hook.onSubscribeReturn(subscriber);
        } catch (Throwable th) {
            Exceptions.throwIfFatal(th);
            try {
                subscriber.onError(hook.onSubscribeError(th));
                return Subscriptions.unsubscribed();
            } catch (OnErrorNotImplementedException e) {
                throw e;
            } catch (Throwable th2) {
                RuntimeException runtimeException = new RuntimeException("Error occurred attempting to subscribe [" + th.getMessage() + "] and then again while trying to pass to onError.", th2);
                hook.onSubscribeError(runtimeException);
                throw runtimeException;
            }
        }
    }

    public final Subscription subscribe(Subscriber<? super T> subscriber) {
        return subscribe(subscriber, this);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static <T> Subscription subscribe(Subscriber<? super T> subscriber, Observable<T> observable) {
        if (subscriber == null) {
            throw new IllegalArgumentException("observer can not be null");
        }
        if (observable.onSubscribe == null) {
            throw new IllegalStateException("onSubscribe function can not be null.");
        }
        subscriber.onStart();
        if (!(subscriber instanceof SafeSubscriber)) {
            subscriber = new SafeSubscriber(subscriber);
        }
        try {
            hook.onSubscribeStart(observable, observable.onSubscribe).call(subscriber);
            return hook.onSubscribeReturn(subscriber);
        } catch (Throwable th) {
            Exceptions.throwIfFatal(th);
            try {
                subscriber.onError(hook.onSubscribeError(th));
                return Subscriptions.unsubscribed();
            } catch (OnErrorNotImplementedException e) {
                throw e;
            } catch (Throwable th2) {
                RuntimeException runtimeException = new RuntimeException("Error occurred attempting to subscribe [" + th.getMessage() + "] and then again while trying to pass to onError.", th2);
                hook.onSubscribeError(runtimeException);
                throw runtimeException;
            }
        }
    }

    public final Observable<T> subscribeOn(Scheduler scheduler) {
        if (this instanceof ScalarSynchronousObservable) {
            return ((ScalarSynchronousObservable) this).scalarScheduleOn(scheduler);
        }
        return (Observable<T>) nest().lift(new OperatorSubscribeOn(scheduler));
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final <R> Observable<R> switchMap(Func1<? super T, ? extends Observable<? extends R>> func1) {
        return switchOnNext(map(func1));
    }

    public final Observable<T> take(int i) {
        return (Observable<T>) lift(new OperatorTake(i));
    }

    public final Observable<T> take(long j, TimeUnit timeUnit) {
        return take(j, timeUnit, Schedulers.computation());
    }

    public final Observable<T> take(long j, TimeUnit timeUnit, Scheduler scheduler) {
        return (Observable<T>) lift(new OperatorTakeTimed(j, timeUnit, scheduler));
    }

    public final Observable<T> takeFirst(Func1<? super T, Boolean> func1) {
        return filter(func1).take(1);
    }

    public final Observable<T> takeLast(int i) {
        if (i == 0) {
            return ignoreElements();
        }
        if (i == 1) {
            return (Observable<T>) lift(OperatorTakeLastOne.instance());
        }
        return (Observable<T>) lift(new OperatorTakeLast(i));
    }

    public final Observable<T> takeLast(int i, long j, TimeUnit timeUnit) {
        return takeLast(i, j, timeUnit, Schedulers.computation());
    }

    public final Observable<T> takeLast(int i, long j, TimeUnit timeUnit, Scheduler scheduler) {
        return (Observable<T>) lift(new OperatorTakeLastTimed(i, j, timeUnit, scheduler));
    }

    public final Observable<T> takeLast(long j, TimeUnit timeUnit) {
        return takeLast(j, timeUnit, Schedulers.computation());
    }

    public final Observable<T> takeLast(long j, TimeUnit timeUnit, Scheduler scheduler) {
        return (Observable<T>) lift(new OperatorTakeLastTimed(j, timeUnit, scheduler));
    }

    public final Observable<List<T>> takeLastBuffer(int i) {
        return takeLast(i).toList();
    }

    public final Observable<List<T>> takeLastBuffer(int i, long j, TimeUnit timeUnit) {
        return takeLast(i, j, timeUnit).toList();
    }

    public final Observable<List<T>> takeLastBuffer(int i, long j, TimeUnit timeUnit, Scheduler scheduler) {
        return takeLast(i, j, timeUnit, scheduler).toList();
    }

    public final Observable<List<T>> takeLastBuffer(long j, TimeUnit timeUnit) {
        return takeLast(j, timeUnit).toList();
    }

    public final Observable<List<T>> takeLastBuffer(long j, TimeUnit timeUnit, Scheduler scheduler) {
        return takeLast(j, timeUnit, scheduler).toList();
    }

    public final <E> Observable<T> takeUntil(Observable<? extends E> observable) {
        return (Observable<T>) lift(new OperatorTakeUntil(observable));
    }

    public final Observable<T> takeWhile(Func1<? super T, Boolean> func1) {
        return (Observable<T>) lift(new OperatorTakeWhile(func1));
    }

    public final Observable<T> takeUntil(Func1<? super T, Boolean> func1) {
        return (Observable<T>) lift(new OperatorTakeUntilPredicate(func1));
    }

    public final Observable<T> throttleFirst(long j, TimeUnit timeUnit) {
        return throttleFirst(j, timeUnit, Schedulers.computation());
    }

    public final Observable<T> throttleFirst(long j, TimeUnit timeUnit, Scheduler scheduler) {
        return (Observable<T>) lift(new OperatorThrottleFirst(j, timeUnit, scheduler));
    }

    public final Observable<T> throttleLast(long j, TimeUnit timeUnit) {
        return sample(j, timeUnit);
    }

    public final Observable<T> throttleLast(long j, TimeUnit timeUnit, Scheduler scheduler) {
        return sample(j, timeUnit, scheduler);
    }

    public final Observable<T> throttleWithTimeout(long j, TimeUnit timeUnit) {
        return debounce(j, timeUnit);
    }

    public final Observable<T> throttleWithTimeout(long j, TimeUnit timeUnit, Scheduler scheduler) {
        return debounce(j, timeUnit, scheduler);
    }

    public final Observable<TimeInterval<T>> timeInterval() {
        return timeInterval(Schedulers.immediate());
    }

    public final Observable<TimeInterval<T>> timeInterval(Scheduler scheduler) {
        return (Observable<TimeInterval<T>>) lift(new OperatorTimeInterval(scheduler));
    }

    public final <U, V> Observable<T> timeout(Func0<? extends Observable<U>> func0, Func1<? super T, ? extends Observable<V>> func1) {
        return timeout(func0, func1, (Observable) null);
    }

    public final <U, V> Observable<T> timeout(Func0<? extends Observable<U>> func0, Func1<? super T, ? extends Observable<V>> func1, Observable<? extends T> observable) {
        if (func1 == null) {
            throw new NullPointerException("timeoutSelector is null");
        }
        return (Observable<T>) lift(new OperatorTimeoutWithSelector(func0, func1, observable));
    }

    public final <V> Observable<T> timeout(Func1<? super T, ? extends Observable<V>> func1) {
        return timeout((Func0) null, func1, (Observable) null);
    }

    public final <V> Observable<T> timeout(Func1<? super T, ? extends Observable<V>> func1, Observable<? extends T> observable) {
        return timeout((Func0) null, func1, observable);
    }

    public final Observable<T> timeout(long j, TimeUnit timeUnit) {
        return timeout(j, timeUnit, null, Schedulers.computation());
    }

    public final Observable<T> timeout(long j, TimeUnit timeUnit, Observable<? extends T> observable) {
        return timeout(j, timeUnit, observable, Schedulers.computation());
    }

    public final Observable<T> timeout(long j, TimeUnit timeUnit, Observable<? extends T> observable, Scheduler scheduler) {
        return (Observable<T>) lift(new OperatorTimeout(j, timeUnit, observable, scheduler));
    }

    public final Observable<T> timeout(long j, TimeUnit timeUnit, Scheduler scheduler) {
        return timeout(j, timeUnit, null, scheduler);
    }

    public final Observable<Timestamped<T>> timestamp() {
        return timestamp(Schedulers.immediate());
    }

    public final Observable<Timestamped<T>> timestamp(Scheduler scheduler) {
        return (Observable<Timestamped<T>>) lift(new OperatorTimestamp(scheduler));
    }

    public final BlockingObservable<T> toBlocking() {
        return BlockingObservable.from(this);
    }

    public final Observable<List<T>> toList() {
        return (Observable<List<T>>) lift(OperatorToObservableList.instance());
    }

    public final <K> Observable<Map<K, T>> toMap(Func1<? super T, ? extends K> func1) {
        return (Observable<Map<K, T>>) lift(new OperatorToMap(func1, UtilityFunctions.identity()));
    }

    public final <K, V> Observable<Map<K, V>> toMap(Func1<? super T, ? extends K> func1, Func1<? super T, ? extends V> func12) {
        return (Observable<Map<K, V>>) lift(new OperatorToMap(func1, func12));
    }

    public final <K, V> Observable<Map<K, V>> toMap(Func1<? super T, ? extends K> func1, Func1<? super T, ? extends V> func12, Func0<? extends Map<K, V>> func0) {
        return (Observable<Map<K, V>>) lift(new OperatorToMap(func1, func12, func0));
    }

    public final <K> Observable<Map<K, Collection<T>>> toMultimap(Func1<? super T, ? extends K> func1) {
        return (Observable<Map<K, Collection<T>>>) lift(new OperatorToMultimap(func1, UtilityFunctions.identity()));
    }

    public final <K, V> Observable<Map<K, Collection<V>>> toMultimap(Func1<? super T, ? extends K> func1, Func1<? super T, ? extends V> func12) {
        return (Observable<Map<K, Collection<V>>>) lift(new OperatorToMultimap(func1, func12));
    }

    public final <K, V> Observable<Map<K, Collection<V>>> toMultimap(Func1<? super T, ? extends K> func1, Func1<? super T, ? extends V> func12, Func0<? extends Map<K, Collection<V>>> func0) {
        return (Observable<Map<K, Collection<V>>>) lift(new OperatorToMultimap(func1, func12, func0));
    }

    public final <K, V> Observable<Map<K, Collection<V>>> toMultimap(Func1<? super T, ? extends K> func1, Func1<? super T, ? extends V> func12, Func0<? extends Map<K, Collection<V>>> func0, Func1<? super K, ? extends Collection<V>> func13) {
        return (Observable<Map<K, Collection<V>>>) lift(new OperatorToMultimap(func1, func12, func0, func13));
    }

    public final Observable<List<T>> toSortedList() {
        return (Observable<List<T>>) lift(new OperatorToObservableSortedList(10));
    }

    public final Observable<List<T>> toSortedList(Func2<? super T, ? super T, Integer> func2) {
        return (Observable<List<T>>) lift(new OperatorToObservableSortedList(func2, 10));
    }

    public final Observable<List<T>> toSortedList(int i) {
        return (Observable<List<T>>) lift(new OperatorToObservableSortedList(i));
    }

    public final Observable<List<T>> toSortedList(Func2<? super T, ? super T, Integer> func2, int i) {
        return (Observable<List<T>>) lift(new OperatorToObservableSortedList(func2, i));
    }

    public final Observable<T> unsubscribeOn(Scheduler scheduler) {
        return (Observable<T>) lift(new OperatorUnsubscribeOn(scheduler));
    }

    public final <U, R> Observable<R> withLatestFrom(Observable<? extends U> observable, Func2<? super T, ? super U, ? extends R> func2) {
        return lift(new OperatorWithLatestFrom(observable, func2));
    }

    public final <TClosing> Observable<Observable<T>> window(Func0<? extends Observable<? extends TClosing>> func0) {
        return (Observable<Observable<T>>) lift(new OperatorWindowWithObservableFactory(func0));
    }

    public final Observable<Observable<T>> window(int i) {
        return window(i, i);
    }

    public final Observable<Observable<T>> window(int i, int i2) {
        return (Observable<Observable<T>>) lift(new OperatorWindowWithSize(i, i2));
    }

    public final Observable<Observable<T>> window(long j, long j2, TimeUnit timeUnit) {
        return window(j, j2, timeUnit, Integer.MAX_VALUE, Schedulers.computation());
    }

    public final Observable<Observable<T>> window(long j, long j2, TimeUnit timeUnit, Scheduler scheduler) {
        return window(j, j2, timeUnit, Integer.MAX_VALUE, scheduler);
    }

    public final Observable<Observable<T>> window(long j, long j2, TimeUnit timeUnit, int i, Scheduler scheduler) {
        return (Observable<Observable<T>>) lift(new OperatorWindowWithTime(j, j2, timeUnit, i, scheduler));
    }

    public final Observable<Observable<T>> window(long j, TimeUnit timeUnit) {
        return window(j, j, timeUnit, Schedulers.computation());
    }

    public final Observable<Observable<T>> window(long j, TimeUnit timeUnit, int i) {
        return window(j, timeUnit, i, Schedulers.computation());
    }

    public final Observable<Observable<T>> window(long j, TimeUnit timeUnit, int i, Scheduler scheduler) {
        return window(j, j, timeUnit, i, scheduler);
    }

    public final Observable<Observable<T>> window(long j, TimeUnit timeUnit, Scheduler scheduler) {
        return window(j, timeUnit, Integer.MAX_VALUE, scheduler);
    }

    public final <TOpening, TClosing> Observable<Observable<T>> window(Observable<? extends TOpening> observable, Func1<? super TOpening, ? extends Observable<? extends TClosing>> func1) {
        return (Observable<Observable<T>>) lift(new OperatorWindowWithStartEndObservable(observable, func1));
    }

    public final <U> Observable<Observable<T>> window(Observable<U> observable) {
        return (Observable<Observable<T>>) lift(new OperatorWindowWithObservable(observable));
    }

    public final <T2, R> Observable<R> zipWith(Iterable<? extends T2> iterable, Func2<? super T, ? super T2, ? extends R> func2) {
        return lift(new OperatorZipIterable(iterable, func2));
    }

    public final <T2, R> Observable<R> zipWith(Observable<? extends T2> observable, Func2<? super T, ? super T2, ? extends R> func2) {
        return zip(this, observable, func2);
    }

    private static class NeverObservable<T> extends Observable<T> {

        private static class Holder {
            static final NeverObservable<?> INSTANCE = new NeverObservable<>();

            private Holder() {
            }
        }

        static <T> NeverObservable<T> instance() {
            return (NeverObservable<T>) Holder.INSTANCE;
        }

        NeverObservable() {
            super(new OnSubscribe<T>() { // from class: rx.Observable.NeverObservable.1
                @Override // rx.functions.Action1
                public void call(Subscriber<? super T> subscriber) {
                }
            });
        }
    }

    private static class ThrowObservable<T> extends Observable<T> {
        public ThrowObservable(final Throwable th) {
            super(new OnSubscribe<T>() { // from class: rx.Observable.ThrowObservable.1
                @Override // rx.functions.Action1
                public void call(Subscriber<? super T> subscriber) {
                    subscriber.onError(th);
                }
            });
        }
    }
}
