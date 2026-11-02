package com.google.android.gms.internal.gtm;

/* loaded from: classes.dex */
public final class zzus implements Cloneable {
    private static final zzut zzbhe = new zzut();
    private int mSize;
    private boolean zzbhf;
    private int[] zzbhg;
    private zzut[] zzbhh;

    zzus() {
        this(10);
    }

    private zzus(int i) {
        this.zzbhf = false;
        int iIdealIntArraySize = idealIntArraySize(i);
        this.zzbhg = new int[iIdealIntArraySize];
        this.zzbhh = new zzut[iIdealIntArraySize];
        this.mSize = 0;
    }

    final zzut zzcd(int i) {
        int iZzcf = zzcf(i);
        if (iZzcf < 0) {
            return null;
        }
        zzut[] zzutVarArr = this.zzbhh;
        if (zzutVarArr[iZzcf] == zzbhe) {
            return null;
        }
        return zzutVarArr[iZzcf];
    }

    final void zza(int i, zzut zzutVar) {
        int iZzcf = zzcf(i);
        if (iZzcf >= 0) {
            this.zzbhh[iZzcf] = zzutVar;
            return;
        }
        int i2 = ~iZzcf;
        if (i2 < this.mSize) {
            zzut[] zzutVarArr = this.zzbhh;
            if (zzutVarArr[i2] == zzbhe) {
                this.zzbhg[i2] = i;
                zzutVarArr[i2] = zzutVar;
                return;
            }
        }
        int i3 = this.mSize;
        if (i3 >= this.zzbhg.length) {
            int iIdealIntArraySize = idealIntArraySize(i3 + 1);
            int[] iArr = new int[iIdealIntArraySize];
            zzut[] zzutVarArr2 = new zzut[iIdealIntArraySize];
            int[] iArr2 = this.zzbhg;
            System.arraycopy(iArr2, 0, iArr, 0, iArr2.length);
            zzut[] zzutVarArr3 = this.zzbhh;
            System.arraycopy(zzutVarArr3, 0, zzutVarArr2, 0, zzutVarArr3.length);
            this.zzbhg = iArr;
            this.zzbhh = zzutVarArr2;
        }
        int i4 = this.mSize;
        if (i4 - i2 != 0) {
            int[] iArr3 = this.zzbhg;
            int i5 = i2 + 1;
            System.arraycopy(iArr3, i2, iArr3, i5, i4 - i2);
            zzut[] zzutVarArr4 = this.zzbhh;
            System.arraycopy(zzutVarArr4, i2, zzutVarArr4, i5, this.mSize - i2);
        }
        this.zzbhg[i2] = i;
        this.zzbhh[i2] = zzutVar;
        this.mSize++;
    }

    final int size() {
        return this.mSize;
    }

    public final boolean isEmpty() {
        return this.mSize == 0;
    }

    final zzut zzce(int i) {
        return this.zzbhh[i];
    }

    public final boolean equals(Object obj) {
        boolean z;
        boolean z2;
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof zzus)) {
            return false;
        }
        zzus zzusVar = (zzus) obj;
        int i = this.mSize;
        if (i != zzusVar.mSize) {
            return false;
        }
        int[] iArr = this.zzbhg;
        int[] iArr2 = zzusVar.zzbhg;
        int i2 = 0;
        while (true) {
            if (i2 >= i) {
                z = true;
                break;
            }
            if (iArr[i2] != iArr2[i2]) {
                z = false;
                break;
            }
            i2++;
        }
        if (z) {
            zzut[] zzutVarArr = this.zzbhh;
            zzut[] zzutVarArr2 = zzusVar.zzbhh;
            int i3 = this.mSize;
            int i4 = 0;
            while (true) {
                if (i4 >= i3) {
                    z2 = true;
                    break;
                }
                if (!zzutVarArr[i4].equals(zzutVarArr2[i4])) {
                    z2 = false;
                    break;
                }
                i4++;
            }
            if (z2) {
                return true;
            }
        }
        return false;
    }

    public final int hashCode() {
        int iHashCode = 17;
        for (int i = 0; i < this.mSize; i++) {
            iHashCode = (((iHashCode * 31) + this.zzbhg[i]) * 31) + this.zzbhh[i].hashCode();
        }
        return iHashCode;
    }

    private static int idealIntArraySize(int i) {
        int i2 = i << 2;
        int i3 = 4;
        while (true) {
            if (i3 >= 32) {
                break;
            }
            int i4 = (1 << i3) - 12;
            if (i2 <= i4) {
                i2 = i4;
                break;
            }
            i3++;
        }
        return i2 / 4;
    }

    private final int zzcf(int i) {
        int i2 = this.mSize - 1;
        int i3 = 0;
        while (i3 <= i2) {
            int i4 = (i3 + i2) >>> 1;
            int i5 = this.zzbhg[i4];
            if (i5 < i) {
                i3 = i4 + 1;
            } else {
                if (i5 <= i) {
                    return i4;
                }
                i2 = i4 - 1;
            }
        }
        return ~i3;
    }

    public final /* synthetic */ Object clone() throws CloneNotSupportedException {
        int i = this.mSize;
        zzus zzusVar = new zzus(i);
        System.arraycopy(this.zzbhg, 0, zzusVar.zzbhg, 0, i);
        for (int i2 = 0; i2 < i; i2++) {
            zzut[] zzutVarArr = this.zzbhh;
            if (zzutVarArr[i2] != null) {
                zzusVar.zzbhh[i2] = (zzut) zzutVarArr[i2].clone();
            }
        }
        zzusVar.mSize = i;
        return zzusVar;
    }
}
