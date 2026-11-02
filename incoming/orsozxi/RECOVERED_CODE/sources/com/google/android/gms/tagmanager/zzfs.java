package com.google.android.gms.tagmanager;

import android.os.Handler;
import android.os.Message;

/* loaded from: classes.dex */
final class zzfs implements Handler.Callback {
    private final /* synthetic */ zzfr zzala;

    zzfs(zzfr zzfrVar) {
        this.zzala = zzfrVar;
    }

    @Override // android.os.Handler.Callback
    public final boolean handleMessage(Message message) {
        if (1 == message.what && zzfn.zzakn.equals(message.obj)) {
            this.zzala.zzakz.dispatch();
            if (!this.zzala.zzakz.isPowerSaveMode()) {
                this.zzala.zzh(r5.zzakz.zzakr);
            }
        }
        return true;
    }
}
