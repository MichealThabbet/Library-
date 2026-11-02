package com.google.android.gms.internal.gtm;

import java.io.IOException;
import java.io.Serializable;
import java.nio.charset.Charset;
import java.util.Comparator;
import java.util.Iterator;

/* loaded from: classes.dex */
public abstract class zzps implements Serializable, Iterable<Byte> {
    public static final zzps zzavx = new zzqc(zzre.zzbbh);
    private static final zzpy zzavy;
    private static final Comparator<zzps> zzavz;
    private int zzavn = 0;

    zzps() {
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static int zza(byte b) {
        return b & 255;
    }

    public abstract boolean equals(Object obj);

    public abstract int size();

    protected abstract int zza(int i, int i2, int i3);

    protected abstract String zza(Charset charset);

    abstract void zza(zzpr zzprVar) throws IOException;

    public abstract byte zzak(int i);

    abstract byte zzal(int i);

    public abstract zzps zzc(int i, int i2);

    public abstract boolean zznd();

    public static zzps zzb(byte[] bArr, int i, int i2) {
        zzb(i, i + i2, bArr.length);
        return new zzqc(zzavy.zzc(bArr, i, i2));
    }

    static zzps zzf(byte[] bArr) {
        return new zzqc(bArr);
    }

    public static zzps zzcy(String str) {
        return new zzqc(str.getBytes(zzre.UTF_8));
    }

    public final String zznc() {
        return size() == 0 ? "" : zza(zzre.UTF_8);
    }

    public final int hashCode() {
        int iZza = this.zzavn;
        if (iZza == 0) {
            int size = size();
            iZza = zza(size, 0, size);
            if (iZza == 0) {
                iZza = 1;
            }
            this.zzavn = iZza;
        }
        return iZza;
    }

    static zzqa zzam(int i) {
        return new zzqa(i, null);
    }

    protected final int zzne() {
        return this.zzavn;
    }

    static int zzb(int i, int i2, int i3) {
        int i4 = i2 - i;
        if ((i | i2 | i4 | (i3 - i2)) >= 0) {
            return i4;
        }
        if (i < 0) {
            StringBuilder sb = new StringBuilder(32);
            sb.append("Beginning index: ");
            sb.append(i);
            sb.append(" < 0");
            throw new IndexOutOfBoundsException(sb.toString());
        }
        if (i2 < i) {
            StringBuilder sb2 = new StringBuilder(66);
            sb2.append("Beginning index larger than ending index: ");
            sb2.append(i);
            sb2.append(", ");
            sb2.append(i2);
            throw new IndexOutOfBoundsException(sb2.toString());
        }
        StringBuilder sb3 = new StringBuilder(37);
        sb3.append("End index: ");
        sb3.append(i2);
        sb3.append(" >= ");
        sb3.append(i3);
        throw new IndexOutOfBoundsException(sb3.toString());
    }

    public final String toString() {
        return String.format("<ByteString@%s size=%d>", Integer.toHexString(System.identityHashCode(this)), Integer.valueOf(size()));
    }

    @Override // java.lang.Iterable
    public /* synthetic */ Iterator<Byte> iterator() {
        return new zzpt(this);
    }

    static {
        zzpt zzptVar = null;
        zzavy = zzpp.zzna() ? new zzqd(zzptVar) : new zzpw(zzptVar);
        zzavz = new zzpu();
    }
}
