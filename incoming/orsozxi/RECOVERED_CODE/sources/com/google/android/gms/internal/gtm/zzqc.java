package com.google.android.gms.internal.gtm;

import java.io.IOException;
import java.nio.charset.Charset;

/* loaded from: classes.dex */
class zzqc extends zzqb {
    protected final byte[] zzawe;

    zzqc(byte[] bArr) {
        if (bArr == null) {
            throw null;
        }
        this.zzawe = bArr;
    }

    protected int zznf() {
        return 0;
    }

    @Override // com.google.android.gms.internal.gtm.zzps
    public byte zzak(int i) {
        return this.zzawe[i];
    }

    @Override // com.google.android.gms.internal.gtm.zzps
    byte zzal(int i) {
        return this.zzawe[i];
    }

    @Override // com.google.android.gms.internal.gtm.zzps
    public int size() {
        return this.zzawe.length;
    }

    @Override // com.google.android.gms.internal.gtm.zzps
    public final zzps zzc(int i, int i2) {
        int iZzb = zzb(0, i2, size());
        if (iZzb == 0) {
            return zzps.zzavx;
        }
        return new zzpx(this.zzawe, zznf(), iZzb);
    }

    @Override // com.google.android.gms.internal.gtm.zzps
    final void zza(zzpr zzprVar) throws IOException {
        zzprVar.zza(this.zzawe, zznf(), size());
    }

    @Override // com.google.android.gms.internal.gtm.zzps
    protected final String zza(Charset charset) {
        return new String(this.zzawe, zznf(), size(), charset);
    }

    @Override // com.google.android.gms.internal.gtm.zzps
    public final boolean zznd() {
        int iZznf = zznf();
        return zztz.zzf(this.zzawe, iZznf, size() + iZznf);
    }

    @Override // com.google.android.gms.internal.gtm.zzps
    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof zzps) || size() != ((zzps) obj).size()) {
            return false;
        }
        if (size() == 0) {
            return true;
        }
        if (obj instanceof zzqc) {
            zzqc zzqcVar = (zzqc) obj;
            int iZzne = zzne();
            int iZzne2 = zzqcVar.zzne();
            if (iZzne == 0 || iZzne2 == 0 || iZzne == iZzne2) {
                return zza(zzqcVar, 0, size());
            }
            return false;
        }
        return obj.equals(this);
    }

    @Override // com.google.android.gms.internal.gtm.zzqb
    final boolean zza(zzps zzpsVar, int i, int i2) {
        if (i2 > zzpsVar.size()) {
            int size = size();
            StringBuilder sb = new StringBuilder(40);
            sb.append("Length too large: ");
            sb.append(i2);
            sb.append(size);
            throw new IllegalArgumentException(sb.toString());
        }
        if (i2 > zzpsVar.size()) {
            int size2 = zzpsVar.size();
            StringBuilder sb2 = new StringBuilder(59);
            sb2.append("Ran off end of other: 0, ");
            sb2.append(i2);
            sb2.append(", ");
            sb2.append(size2);
            throw new IllegalArgumentException(sb2.toString());
        }
        if (zzpsVar instanceof zzqc) {
            zzqc zzqcVar = (zzqc) zzpsVar;
            return zztx.zza(this.zzawe, zznf(), zzqcVar.zzawe, zzqcVar.zznf(), i2) == -1;
        }
        return zzpsVar.zzc(0, i2).equals(zzc(0, i2));
    }

    @Override // com.google.android.gms.internal.gtm.zzps
    protected final int zza(int i, int i2, int i3) {
        return zzre.zza(i, this.zzawe, zznf(), i3);
    }
}
