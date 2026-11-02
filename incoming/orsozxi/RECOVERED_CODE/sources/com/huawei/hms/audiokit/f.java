package com.huawei.hms.audiokit;

/* loaded from: classes2.dex */
public abstract class f<T> {
    private T a;

    protected abstract T a();

    public final T b() {
        T t;
        synchronized (this) {
            if (this.a == null) {
                this.a = a();
            }
            t = this.a;
        }
        return t;
    }
}
