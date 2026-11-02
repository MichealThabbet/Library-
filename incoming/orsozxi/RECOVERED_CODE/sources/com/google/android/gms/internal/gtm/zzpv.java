package com.google.android.gms.internal.gtm;

/* loaded from: classes.dex */
abstract class zzpv implements zzpz {
    zzpv() {
    }

    @Override // java.util.Iterator
    public final void remove() {
        throw new UnsupportedOperationException();
    }

    @Override // java.util.Iterator
    public /* synthetic */ Byte next() {
        return Byte.valueOf(nextByte());
    }
}
