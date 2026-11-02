package com.google.android.gms.internal.gtm;

import java.util.AbstractList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.RandomAccess;

/* loaded from: classes.dex */
public final class zztu extends AbstractList<String> implements zzrt, RandomAccess {
    private final zzrt zzbeo;

    public zztu(zzrt zzrtVar) {
        this.zzbeo = zzrtVar;
    }

    @Override // com.google.android.gms.internal.gtm.zzrt
    public final zzrt zzqb() {
        return this;
    }

    @Override // com.google.android.gms.internal.gtm.zzrt
    public final Object zzbn(int i) {
        return this.zzbeo.zzbn(i);
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
    public final int size() {
        return this.zzbeo.size();
    }

    @Override // com.google.android.gms.internal.gtm.zzrt
    public final void zzc(zzps zzpsVar) {
        throw new UnsupportedOperationException();
    }

    @Override // java.util.AbstractList, java.util.List
    public final ListIterator<String> listIterator(int i) {
        return new zztv(this, i);
    }

    @Override // java.util.AbstractList, java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, java.util.List
    public final Iterator<String> iterator() {
        return new zztw(this);
    }

    @Override // com.google.android.gms.internal.gtm.zzrt
    public final List<?> zzqa() {
        return this.zzbeo.zzqa();
    }

    @Override // java.util.AbstractList, java.util.List
    public final /* synthetic */ Object get(int i) {
        return (String) this.zzbeo.get(i);
    }
}
