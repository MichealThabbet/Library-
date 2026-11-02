package com.google.android.gms.tagmanager;

/* loaded from: classes.dex */
final class zzgi extends Number implements Comparable<zzgi> {
    private double zzall;
    private long zzalm;
    private boolean zzaln = false;

    private zzgi(double d) {
        this.zzall = d;
    }

    private zzgi(long j) {
        this.zzalm = j;
    }

    public static zzgi zza(Double d) {
        return new zzgi(d.doubleValue());
    }

    public static zzgi zzm(long j) {
        return new zzgi(j);
    }

    public static zzgi zzbo(String str) throws NumberFormatException {
        try {
            try {
                return new zzgi(Long.parseLong(str));
            } catch (NumberFormatException unused) {
                throw new NumberFormatException(String.valueOf(str).concat(" is not a valid TypedNumber"));
            }
        } catch (NumberFormatException unused2) {
            return new zzgi(Double.parseDouble(str));
        }
    }

    public final String toString() {
        return this.zzaln ? Long.toString(this.zzalm) : Double.toString(this.zzall);
    }

    public final boolean equals(Object obj) {
        return (obj instanceof zzgi) && compareTo((zzgi) obj) == 0;
    }

    public final int hashCode() {
        return new Long(longValue()).hashCode();
    }

    @Override // java.lang.Comparable
    /* renamed from: zza, reason: merged with bridge method [inline-methods] */
    public final int compareTo(zzgi zzgiVar) {
        if (this.zzaln && zzgiVar.zzaln) {
            return new Long(this.zzalm).compareTo(Long.valueOf(zzgiVar.zzalm));
        }
        return Double.compare(doubleValue(), zzgiVar.doubleValue());
    }

    public final boolean zzju() {
        return !this.zzaln;
    }

    public final boolean zzjv() {
        return this.zzaln;
    }

    @Override // java.lang.Number
    public final double doubleValue() {
        return this.zzaln ? this.zzalm : this.zzall;
    }

    @Override // java.lang.Number
    public final float floatValue() {
        return (float) doubleValue();
    }

    @Override // java.lang.Number
    public final long longValue() {
        return this.zzaln ? this.zzalm : (long) this.zzall;
    }

    @Override // java.lang.Number
    public final int intValue() {
        return (int) longValue();
    }

    @Override // java.lang.Number
    public final short shortValue() {
        return (short) longValue();
    }

    @Override // java.lang.Number
    public final byte byteValue() {
        return (byte) longValue();
    }
}
