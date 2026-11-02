package com.google.android.gms.internal.gtm;

import java.util.Iterator;

/* loaded from: classes.dex */
final class zztw implements Iterator<String> {
    private final /* synthetic */ zztu zzber;
    private Iterator<String> zzbes;

    zztw(zztu zztuVar) {
        this.zzber = zztuVar;
        this.zzbes = this.zzber.zzbeo.iterator();
    }

    @Override // java.util.Iterator
    public final boolean hasNext() {
        return this.zzbes.hasNext();
    }

    @Override // java.util.Iterator
    public final void remove() {
        throw new UnsupportedOperationException();
    }

    @Override // java.util.Iterator
    public final /* synthetic */ String next() {
        return this.zzbes.next();
    }
}
