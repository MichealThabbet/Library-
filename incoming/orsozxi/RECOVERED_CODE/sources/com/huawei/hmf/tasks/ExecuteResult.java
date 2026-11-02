package com.huawei.hmf.tasks;

/* loaded from: classes2.dex */
public interface ExecuteResult<TResult> {
    void cancel();

    void onComplete(Task<TResult> task);
}
