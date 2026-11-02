package com.google.android.gms.tagmanager;

import android.os.Handler;
import android.os.Message;

/* loaded from: classes.dex */
final class zzfr implements zzfq {
    private Handler handler;
    final /* synthetic */ zzfn zzakz;

    private zzfr(zzfn zzfnVar) {
        this.zzakz = zzfnVar;
        this.handler = new com.google.android.gms.internal.gtm.zzdj(this.zzakz.zzako.getMainLooper(), new zzfs(this));
    }

    @Override // com.google.android.gms.tagmanager.zzfq
    public final void zzjt() {
        this.handler.removeMessages(1, zzfn.zzakn);
        this.handler.sendMessage(obtainMessage());
    }

    @Override // com.google.android.gms.tagmanager.zzfq
    public final void cancel() {
        this.handler.removeMessages(1, zzfn.zzakn);
    }

    @Override // com.google.android.gms.tagmanager.zzfq
    public final void zzh(long j) {
        this.handler.removeMessages(1, zzfn.zzakn);
        this.handler.sendMessageDelayed(obtainMessage(), j);
    }

    private final Message obtainMessage() {
        return this.handler.obtainMessage(1, zzfn.zzakn);
    }

    /* synthetic */ zzfr(zzfn zzfnVar, zzfo zzfoVar) {
        this(zzfnVar);
    }
}
