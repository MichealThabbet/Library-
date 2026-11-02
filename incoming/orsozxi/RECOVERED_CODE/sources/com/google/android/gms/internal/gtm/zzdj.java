package com.google.android.gms.internal.gtm;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

/* loaded from: classes.dex */
public class zzdj extends Handler {
    private static volatile zzdk zzadi;

    public zzdj() {
    }

    public zzdj(Looper looper) {
        super(looper);
    }

    public zzdj(Looper looper, Handler.Callback callback) {
        super(looper, callback);
    }

    @Override // android.os.Handler
    public final void dispatchMessage(Message message) {
        super.dispatchMessage(message);
    }
}
