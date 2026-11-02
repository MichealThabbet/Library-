package com.google.android.gms.internal.gtm;

import java.util.Iterator;
import java.util.Map;

/* JADX INFO: Add missing generic type declarations: [V, K] */
/* loaded from: classes.dex */
final class zztk<K, V> implements Iterator<Map.Entry<K, V>> {
    private int pos;
    private Iterator<Map.Entry<K, V>> zzbee;
    private final /* synthetic */ zztc zzbef;
    private boolean zzbej;

    private zztk(zztc zztcVar) {
        this.zzbef = zztcVar;
        this.pos = -1;
    }

    @Override // java.util.Iterator
    public final boolean hasNext() {
        return this.pos + 1 < this.zzbef.zzbdz.size() || (!this.zzbef.zzbea.isEmpty() && zzrf().hasNext());
    }

    @Override // java.util.Iterator
    public final void remove() {
        if (!this.zzbej) {
            throw new IllegalStateException("remove() was called before next()");
        }
        this.zzbej = false;
        this.zzbef.zzrd();
        if (this.pos < this.zzbef.zzbdz.size()) {
            zztc zztcVar = this.zzbef;
            int i = this.pos;
            this.pos = i - 1;
            zztcVar.zzbw(i);
            return;
        }
        zzrf().remove();
    }

    private final Iterator<Map.Entry<K, V>> zzrf() {
        if (this.zzbee == null) {
            this.zzbee = this.zzbef.zzbea.entrySet().iterator();
        }
        return this.zzbee;
    }

    @Override // java.util.Iterator
    public final /* synthetic */ Object next() {
        this.zzbej = true;
        int i = this.pos + 1;
        this.pos = i;
        if (i >= this.zzbef.zzbdz.size()) {
            return zzrf().next();
        }
        return (Map.Entry) this.zzbef.zzbdz.get(this.pos);
    }

    /* synthetic */ zztk(zztc zztcVar, zztd zztdVar) {
        this(zztcVar);
    }
}
