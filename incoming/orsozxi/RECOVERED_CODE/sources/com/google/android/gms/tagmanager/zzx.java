package com.google.android.gms.tagmanager;

import android.os.Looper;
import android.os.Message;
import com.google.android.gms.tagmanager.ContainerHolder;

/* loaded from: classes.dex */
final class zzx extends com.google.android.gms.internal.gtm.zzdj {
    private final ContainerHolder.ContainerAvailableListener zzaes;
    private final /* synthetic */ zzv zzaet;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public zzx(zzv zzvVar, ContainerHolder.ContainerAvailableListener containerAvailableListener, Looper looper) {
        super(looper);
        this.zzaet = zzvVar;
        this.zzaes = containerAvailableListener;
    }

    @Override // android.os.Handler
    public final void handleMessage(Message message) {
        if (message.what == 1) {
            this.zzaes.onContainerAvailable(this.zzaet, (String) message.obj);
        } else {
            zzdi.zzav("Don't know how to handle this message.");
        }
    }
}
