package com.google.android.gms.internal.gtm;

import java.util.Arrays;
import java.util.Collection;
import java.util.RandomAccess;

/* loaded from: classes.dex */
final class zzqm extends zzpo<Double> implements zzrj<Double>, zzsv, RandomAccess {
    private static final zzqm zzaxe;
    private int size;
    private double[] zzaxf;

    zzqm() {
        this(new double[10], 0);
    }

    private zzqm(double[] dArr, int i) {
        this.zzaxf = dArr;
        this.size = i;
    }

    @Override // java.util.AbstractList
    protected final void removeRange(int i, int i2) {
        zzmz();
        if (i2 < i) {
            throw new IndexOutOfBoundsException("toIndex < fromIndex");
        }
        double[] dArr = this.zzaxf;
        System.arraycopy(dArr, i2, dArr, i, this.size - i2);
        this.size -= i2 - i;
        this.modCount++;
    }

    @Override // com.google.android.gms.internal.gtm.zzpo, java.util.AbstractList, java.util.Collection, java.util.List
    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof zzqm)) {
            return super.equals(obj);
        }
        zzqm zzqmVar = (zzqm) obj;
        if (this.size != zzqmVar.size) {
            return false;
        }
        double[] dArr = zzqmVar.zzaxf;
        for (int i = 0; i < this.size; i++) {
            if (Double.doubleToLongBits(this.zzaxf[i]) != Double.doubleToLongBits(dArr[i])) {
                return false;
            }
        }
        return true;
    }

    @Override // com.google.android.gms.internal.gtm.zzpo, java.util.AbstractList, java.util.Collection, java.util.List
    public final int hashCode() {
        int iZzz = 1;
        for (int i = 0; i < this.size; i++) {
            iZzz = (iZzz * 31) + zzre.zzz(Double.doubleToLongBits(this.zzaxf[i]));
        }
        return iZzz;
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
    public final int size() {
        return this.size;
    }

    public final void zzd(double d) {
        zzc(this.size, d);
    }

    private final void zzc(int i, double d) {
        int i2;
        zzmz();
        if (i < 0 || i > (i2 = this.size)) {
            throw new IndexOutOfBoundsException(zzai(i));
        }
        double[] dArr = this.zzaxf;
        if (i2 < dArr.length) {
            System.arraycopy(dArr, i, dArr, i + 1, i2 - i);
        } else {
            double[] dArr2 = new double[((i2 * 3) / 2) + 1];
            System.arraycopy(dArr, 0, dArr2, 0, i);
            System.arraycopy(this.zzaxf, i, dArr2, i + 1, this.size - i);
            this.zzaxf = dArr2;
        }
        this.zzaxf[i] = d;
        this.size++;
        this.modCount++;
    }

    @Override // com.google.android.gms.internal.gtm.zzpo, java.util.AbstractCollection, java.util.Collection, java.util.List
    public final boolean addAll(Collection<? extends Double> collection) {
        zzmz();
        zzre.checkNotNull(collection);
        if (!(collection instanceof zzqm)) {
            return super.addAll(collection);
        }
        zzqm zzqmVar = (zzqm) collection;
        int i = zzqmVar.size;
        if (i == 0) {
            return false;
        }
        int i2 = this.size;
        if (Integer.MAX_VALUE - i2 < i) {
            throw new OutOfMemoryError();
        }
        int i3 = i2 + i;
        double[] dArr = this.zzaxf;
        if (i3 > dArr.length) {
            this.zzaxf = Arrays.copyOf(dArr, i3);
        }
        System.arraycopy(zzqmVar.zzaxf, 0, this.zzaxf, this.size, zzqmVar.size);
        this.size = i3;
        this.modCount++;
        return true;
    }

    @Override // com.google.android.gms.internal.gtm.zzpo, java.util.AbstractCollection, java.util.Collection, java.util.List
    public final boolean remove(Object obj) {
        zzmz();
        for (int i = 0; i < this.size; i++) {
            if (obj.equals(Double.valueOf(this.zzaxf[i]))) {
                double[] dArr = this.zzaxf;
                System.arraycopy(dArr, i + 1, dArr, i, (this.size - i) - 1);
                this.size--;
                this.modCount++;
                return true;
            }
        }
        return false;
    }

    private final void zzah(int i) {
        if (i < 0 || i >= this.size) {
            throw new IndexOutOfBoundsException(zzai(i));
        }
    }

    private final String zzai(int i) {
        int i2 = this.size;
        StringBuilder sb = new StringBuilder(35);
        sb.append("Index:");
        sb.append(i);
        sb.append(", Size:");
        sb.append(i2);
        return sb.toString();
    }

    @Override // com.google.android.gms.internal.gtm.zzpo, java.util.AbstractList, java.util.List
    public final /* synthetic */ Object set(int i, Object obj) {
        double dDoubleValue = ((Double) obj).doubleValue();
        zzmz();
        zzah(i);
        double[] dArr = this.zzaxf;
        double d = dArr[i];
        dArr[i] = dDoubleValue;
        return Double.valueOf(d);
    }

    @Override // com.google.android.gms.internal.gtm.zzpo, java.util.AbstractList, java.util.List
    public final /* synthetic */ Object remove(int i) {
        zzmz();
        zzah(i);
        double[] dArr = this.zzaxf;
        double d = dArr[i];
        if (i < this.size - 1) {
            System.arraycopy(dArr, i + 1, dArr, i, (r3 - i) - 1);
        }
        this.size--;
        this.modCount++;
        return Double.valueOf(d);
    }

    @Override // com.google.android.gms.internal.gtm.zzpo, java.util.AbstractList, java.util.List
    public final /* synthetic */ void add(int i, Object obj) {
        zzc(i, ((Double) obj).doubleValue());
    }

    @Override // com.google.android.gms.internal.gtm.zzrj
    public final /* synthetic */ zzrj<Double> zzaj(int i) {
        if (i < this.size) {
            throw new IllegalArgumentException();
        }
        return new zzqm(Arrays.copyOf(this.zzaxf, i), this.size);
    }

    @Override // java.util.AbstractList, java.util.List
    public final /* synthetic */ Object get(int i) {
        zzah(i);
        return Double.valueOf(this.zzaxf[i]);
    }

    static {
        zzqm zzqmVar = new zzqm(new double[0], 0);
        zzaxe = zzqmVar;
        zzqmVar.zzmi();
    }
}
