package com.google.android.gms.internal.gtm;

import java.util.Iterator;
import java.util.Map;

/* loaded from: classes.dex */
final class zzrq<K> implements Iterator<Map.Entry<K, Object>> {
    private Iterator<Map.Entry<K, Object>> zzbca;

    public zzrq(Iterator<Map.Entry<K, Object>> it2) {
        this.zzbca = it2;
    }

    @Override // java.util.Iterator
    public final boolean hasNext() {
        return this.zzbca.hasNext();
    }

    @Override // java.util.Iterator
    public final void remove() {
        this.zzbca.remove();
    }

    @Override // java.util.Iterator
    public final /* synthetic */ Object next() {
        Map.Entry<K, Object> next = this.zzbca.next();
        return next.getValue() instanceof zzrn ? new zzrp(next) : next;
    }
}
