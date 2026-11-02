package com.google.android.gms.internal.gtm;

import java.util.concurrent.Callable;

/* loaded from: classes.dex */
final class zzal implements Callable<Void> {
    private final /* synthetic */ zzae zzvw;

    zzal(zzae zzaeVar) {
        this.zzvw = zzaeVar;
    }

    @Override // java.util.concurrent.Callable
    public final /* synthetic */ Void call() throws Exception {
        this.zzvw.zzvu.zzeb();
        return null;
    }
}
