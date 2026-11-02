package com.google.android.gms.internal.gtm;

import java.util.NoSuchElementException;

/* loaded from: classes.dex */
final class zzpt extends zzpv {
    private final int limit;
    private int position = 0;
    private final /* synthetic */ zzps zzawa;

    zzpt(zzps zzpsVar) {
        this.zzawa = zzpsVar;
        this.limit = this.zzawa.size();
    }

    @Override // java.util.Iterator
    public final boolean hasNext() {
        return this.position < this.limit;
    }

    @Override // com.google.android.gms.internal.gtm.zzpz
    public final byte nextByte() {
        int i = this.position;
        if (i >= this.limit) {
            throw new NoSuchElementException();
        }
        this.position = i + 1;
        return this.zzawa.zzal(i);
    }
}
