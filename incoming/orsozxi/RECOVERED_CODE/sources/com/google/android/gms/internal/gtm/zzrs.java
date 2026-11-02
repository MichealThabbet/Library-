package com.google.android.gms.internal.gtm;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.RandomAccess;

/* loaded from: classes.dex */
public final class zzrs extends zzpo<String> implements zzrt, RandomAccess {
    private static final zzrs zzbce;
    private static final zzrt zzbcf;
    private final List<Object> zzbcg;

    public zzrs() {
        this(10);
    }

    public zzrs(int i) {
        this((ArrayList<Object>) new ArrayList(i));
    }

    private zzrs(ArrayList<Object> arrayList) {
        this.zzbcg = arrayList;
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
    public final int size() {
        return this.zzbcg.size();
    }

    @Override // com.google.android.gms.internal.gtm.zzpo, java.util.AbstractCollection, java.util.Collection, java.util.List
    public final boolean addAll(Collection<? extends String> collection) {
        return addAll(size(), collection);
    }

    @Override // com.google.android.gms.internal.gtm.zzpo, java.util.AbstractList, java.util.List
    public final boolean addAll(int i, Collection<? extends String> collection) {
        zzmz();
        if (collection instanceof zzrt) {
            collection = ((zzrt) collection).zzqa();
        }
        boolean zAddAll = this.zzbcg.addAll(i, collection);
        this.modCount++;
        return zAddAll;
    }

    @Override // com.google.android.gms.internal.gtm.zzpo, java.util.AbstractList, java.util.AbstractCollection, java.util.Collection, java.util.List
    public final void clear() {
        zzmz();
        this.zzbcg.clear();
        this.modCount++;
    }

    @Override // com.google.android.gms.internal.gtm.zzrt
    public final void zzc(zzps zzpsVar) {
        zzmz();
        this.zzbcg.add(zzpsVar);
        this.modCount++;
    }

    @Override // com.google.android.gms.internal.gtm.zzrt
    public final Object zzbn(int i) {
        return this.zzbcg.get(i);
    }

    private static String zzv(Object obj) {
        if (obj instanceof String) {
            return (String) obj;
        }
        if (obj instanceof zzps) {
            return ((zzps) obj).zznc();
        }
        return zzre.zzj((byte[]) obj);
    }

    @Override // com.google.android.gms.internal.gtm.zzrt
    public final List<?> zzqa() {
        return Collections.unmodifiableList(this.zzbcg);
    }

    @Override // com.google.android.gms.internal.gtm.zzrt
    public final zzrt zzqb() {
        return zzmy() ? new zztu(this) : this;
    }

    @Override // com.google.android.gms.internal.gtm.zzpo, java.util.AbstractList, java.util.List
    public final /* synthetic */ Object set(int i, Object obj) {
        zzmz();
        return zzv(this.zzbcg.set(i, (String) obj));
    }

    @Override // com.google.android.gms.internal.gtm.zzpo, java.util.AbstractCollection, java.util.Collection, java.util.List
    public final /* bridge */ /* synthetic */ boolean retainAll(Collection collection) {
        return super.retainAll(collection);
    }

    @Override // com.google.android.gms.internal.gtm.zzpo, java.util.AbstractCollection, java.util.Collection, java.util.List
    public final /* bridge */ /* synthetic */ boolean removeAll(Collection collection) {
        return super.removeAll(collection);
    }

    @Override // com.google.android.gms.internal.gtm.zzpo, java.util.AbstractCollection, java.util.Collection, java.util.List
    public final /* bridge */ /* synthetic */ boolean remove(Object obj) {
        return super.remove(obj);
    }

    @Override // com.google.android.gms.internal.gtm.zzpo, java.util.AbstractList, java.util.List
    public final /* synthetic */ Object remove(int i) {
        zzmz();
        Object objRemove = this.zzbcg.remove(i);
        this.modCount++;
        return zzv(objRemove);
    }

    @Override // com.google.android.gms.internal.gtm.zzpo, com.google.android.gms.internal.gtm.zzrj
    public final /* bridge */ /* synthetic */ boolean zzmy() {
        return super.zzmy();
    }

    @Override // com.google.android.gms.internal.gtm.zzpo, java.util.AbstractList, java.util.List
    public final /* synthetic */ void add(int i, Object obj) {
        zzmz();
        this.zzbcg.add(i, (String) obj);
        this.modCount++;
    }

    @Override // com.google.android.gms.internal.gtm.zzpo, java.util.AbstractList, java.util.Collection, java.util.List
    public final /* bridge */ /* synthetic */ int hashCode() {
        return super.hashCode();
    }

    @Override // com.google.android.gms.internal.gtm.zzpo, java.util.AbstractList, java.util.Collection, java.util.List
    public final /* bridge */ /* synthetic */ boolean equals(Object obj) {
        return super.equals(obj);
    }

    @Override // com.google.android.gms.internal.gtm.zzrj
    public final /* synthetic */ zzrj zzaj(int i) {
        if (i < size()) {
            throw new IllegalArgumentException();
        }
        ArrayList arrayList = new ArrayList(i);
        arrayList.addAll(this.zzbcg);
        return new zzrs((ArrayList<Object>) arrayList);
    }

    @Override // java.util.AbstractList, java.util.List
    public final /* synthetic */ Object get(int i) {
        Object obj = this.zzbcg.get(i);
        if (obj instanceof String) {
            return (String) obj;
        }
        if (obj instanceof zzps) {
            zzps zzpsVar = (zzps) obj;
            String strZznc = zzpsVar.zznc();
            if (zzpsVar.zznd()) {
                this.zzbcg.set(i, strZznc);
            }
            return strZznc;
        }
        byte[] bArr = (byte[]) obj;
        String strZzj = zzre.zzj(bArr);
        if (zzre.zzi(bArr)) {
            this.zzbcg.set(i, strZzj);
        }
        return strZzj;
    }

    static {
        zzrs zzrsVar = new zzrs();
        zzbce = zzrsVar;
        zzrsVar.zzmi();
        zzbcf = zzbce;
    }
}
