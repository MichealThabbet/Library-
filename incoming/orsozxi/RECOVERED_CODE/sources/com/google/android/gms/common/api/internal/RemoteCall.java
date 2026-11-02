package com.google.android.gms.common.api.internal;

import android.os.RemoteException;

/* loaded from: classes.dex */
public interface RemoteCall<T, U> {
    void accept(T t, U u) throws RemoteException;
}
