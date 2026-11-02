package io.reactivex.parallel;

/* loaded from: classes2.dex */
public interface ParallelFlowableConverter<T, R> {
    R apply(ParallelFlowable<T> parallelFlowable);
}
