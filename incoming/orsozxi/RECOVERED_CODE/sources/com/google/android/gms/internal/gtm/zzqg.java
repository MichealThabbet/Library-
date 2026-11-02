package com.google.android.gms.internal.gtm;

import java.io.IOException;

/* loaded from: classes.dex */
final class zzqg extends zzqe {
    private final byte[] buffer;
    private int limit;
    private int pos;
    private final boolean zzawk;
    private int zzawl;
    private int zzawm;
    private int zzawn;
    private int zzawo;

    private zzqg(byte[] bArr, int i, int i2, boolean z) {
        super();
        this.zzawo = Integer.MAX_VALUE;
        this.buffer = bArr;
        this.limit = i2 + i;
        this.pos = i;
        this.zzawm = i;
        this.zzawk = z;
    }

    @Override // com.google.android.gms.internal.gtm.zzqe
    public final int zzni() throws IOException {
        if (zzny()) {
            this.zzawn = 0;
            return 0;
        }
        int iZzoa = zzoa();
        this.zzawn = iZzoa;
        if ((iZzoa >>> 3) != 0) {
            return iZzoa;
        }
        throw new zzrk("Protocol message contained an invalid tag (zero).");
    }

    @Override // com.google.android.gms.internal.gtm.zzqe
    public final void zzan(int i) throws zzrk {
        if (this.zzawn != i) {
            throw zzrk.zzps();
        }
    }

    @Override // com.google.android.gms.internal.gtm.zzqe
    public final boolean zzao(int i) throws IOException {
        int iZzni;
        int i2 = i & 7;
        int i3 = 0;
        if (i2 == 0) {
            if (this.limit - this.pos >= 10) {
                while (i3 < 10) {
                    byte[] bArr = this.buffer;
                    int i4 = this.pos;
                    this.pos = i4 + 1;
                    if (bArr[i4] < 0) {
                        i3++;
                    }
                }
                throw zzrk.zzpr();
            }
            while (i3 < 10) {
                if (zzof() < 0) {
                    i3++;
                }
            }
            throw zzrk.zzpr();
            return true;
        }
        if (i2 == 1) {
            zzas(8);
            return true;
        }
        if (i2 == 2) {
            zzas(zzoa());
            return true;
        }
        if (i2 != 3) {
            if (i2 == 4) {
                return false;
            }
            if (i2 == 5) {
                zzas(4);
                return true;
            }
            throw zzrk.zzpt();
        }
        do {
            iZzni = zzni();
            if (iZzni == 0) {
                break;
            }
        } while (zzao(iZzni));
        zzan(((i >>> 3) << 3) | 4);
        return true;
    }

    @Override // com.google.android.gms.internal.gtm.zzqe
    public final double readDouble() throws IOException {
        return Double.longBitsToDouble(zzod());
    }

    @Override // com.google.android.gms.internal.gtm.zzqe
    public final float readFloat() throws IOException {
        return Float.intBitsToFloat(zzoc());
    }

    @Override // com.google.android.gms.internal.gtm.zzqe
    public final long zznj() throws IOException {
        return zzob();
    }

    @Override // com.google.android.gms.internal.gtm.zzqe
    public final long zznk() throws IOException {
        return zzob();
    }

    @Override // com.google.android.gms.internal.gtm.zzqe
    public final int zznl() throws IOException {
        return zzoa();
    }

    @Override // com.google.android.gms.internal.gtm.zzqe
    public final long zznm() throws IOException {
        return zzod();
    }

    @Override // com.google.android.gms.internal.gtm.zzqe
    public final int zznn() throws IOException {
        return zzoc();
    }

    @Override // com.google.android.gms.internal.gtm.zzqe
    public final boolean zzno() throws IOException {
        return zzob() != 0;
    }

    @Override // com.google.android.gms.internal.gtm.zzqe
    public final String readString() throws IOException {
        int iZzoa = zzoa();
        if (iZzoa > 0 && iZzoa <= this.limit - this.pos) {
            String str = new String(this.buffer, this.pos, iZzoa, zzre.UTF_8);
            this.pos += iZzoa;
            return str;
        }
        if (iZzoa == 0) {
            return "";
        }
        if (iZzoa < 0) {
            throw zzrk.zzpq();
        }
        throw zzrk.zzpp();
    }

    @Override // com.google.android.gms.internal.gtm.zzqe
    public final String zznp() throws IOException {
        int iZzoa = zzoa();
        if (iZzoa > 0) {
            int i = this.limit;
            int i2 = this.pos;
            if (iZzoa <= i - i2) {
                String strZzh = zztz.zzh(this.buffer, i2, iZzoa);
                this.pos += iZzoa;
                return strZzh;
            }
        }
        if (iZzoa == 0) {
            return "";
        }
        if (iZzoa <= 0) {
            throw zzrk.zzpq();
        }
        throw zzrk.zzpp();
    }

    @Override // com.google.android.gms.internal.gtm.zzqe
    public final <T extends zzsk> T zza(zzsu<T> zzsuVar, zzqp zzqpVar) throws IOException {
        int iZzoa = zzoa();
        if (this.zzawf >= this.zzawg) {
            throw zzrk.zzpu();
        }
        int iZzaq = zzaq(iZzoa);
        this.zzawf++;
        T tZza = zzsuVar.zza(this, zzqpVar);
        zzan(0);
        this.zzawf--;
        zzar(iZzaq);
        return tZza;
    }

    /* JADX WARN: Removed duplicated region for block: B:15:0x0031  */
    @Override // com.google.android.gms.internal.gtm.zzqe
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final com.google.android.gms.internal.gtm.zzps zznq() throws java.io.IOException {
        /*
            r3 = this;
            int r0 = r3.zzoa()
            if (r0 <= 0) goto L19
            int r1 = r3.limit
            int r2 = r3.pos
            int r1 = r1 - r2
            if (r0 > r1) goto L19
            byte[] r1 = r3.buffer
            com.google.android.gms.internal.gtm.zzps r1 = com.google.android.gms.internal.gtm.zzps.zzb(r1, r2, r0)
            int r2 = r3.pos
            int r2 = r2 + r0
            r3.pos = r2
            return r1
        L19:
            if (r0 != 0) goto L1e
            com.google.android.gms.internal.gtm.zzps r0 = com.google.android.gms.internal.gtm.zzps.zzavx
            return r0
        L1e:
            if (r0 <= 0) goto L31
            int r1 = r3.limit
            int r2 = r3.pos
            int r1 = r1 - r2
            if (r0 > r1) goto L31
            int r0 = r0 + r2
            r3.pos = r0
            byte[] r1 = r3.buffer
            byte[] r0 = java.util.Arrays.copyOfRange(r1, r2, r0)
            goto L37
        L31:
            if (r0 > 0) goto L41
            if (r0 != 0) goto L3c
            byte[] r0 = com.google.android.gms.internal.gtm.zzre.zzbbh
        L37:
            com.google.android.gms.internal.gtm.zzps r0 = com.google.android.gms.internal.gtm.zzps.zzf(r0)
            return r0
        L3c:
            com.google.android.gms.internal.gtm.zzrk r0 = com.google.android.gms.internal.gtm.zzrk.zzpq()
            throw r0
        L41:
            com.google.android.gms.internal.gtm.zzrk r0 = com.google.android.gms.internal.gtm.zzrk.zzpp()
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.gtm.zzqg.zznq():com.google.android.gms.internal.gtm.zzps");
    }

    @Override // com.google.android.gms.internal.gtm.zzqe
    public final int zznr() throws IOException {
        return zzoa();
    }

    @Override // com.google.android.gms.internal.gtm.zzqe
    public final int zzns() throws IOException {
        return zzoa();
    }

    @Override // com.google.android.gms.internal.gtm.zzqe
    public final int zznt() throws IOException {
        return zzoc();
    }

    @Override // com.google.android.gms.internal.gtm.zzqe
    public final long zznu() throws IOException {
        return zzod();
    }

    @Override // com.google.android.gms.internal.gtm.zzqe
    public final int zznv() throws IOException {
        int iZzoa = zzoa();
        return (-(iZzoa & 1)) ^ (iZzoa >>> 1);
    }

    @Override // com.google.android.gms.internal.gtm.zzqe
    public final long zznw() throws IOException {
        long jZzob = zzob();
        return (-(jZzob & 1)) ^ (jZzob >>> 1);
    }

    /* JADX WARN: Code restructure failed: missing block: B:31:0x0066, code lost:
    
        if (r2[r3] >= 0) goto L32;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    private final int zzoa() throws java.io.IOException {
        /*
            r5 = this;
            int r0 = r5.pos
            int r1 = r5.limit
            if (r1 == r0) goto L6b
            byte[] r2 = r5.buffer
            int r3 = r0 + 1
            r0 = r2[r0]
            if (r0 < 0) goto L11
            r5.pos = r3
            return r0
        L11:
            int r1 = r1 - r3
            r4 = 9
            if (r1 < r4) goto L6b
            int r1 = r3 + 1
            r3 = r2[r3]
            int r3 = r3 << 7
            r0 = r0 ^ r3
            if (r0 >= 0) goto L22
            r0 = r0 ^ (-128(0xffffffffffffff80, float:NaN))
            goto L68
        L22:
            int r3 = r1 + 1
            r1 = r2[r1]
            int r1 = r1 << 14
            r0 = r0 ^ r1
            if (r0 < 0) goto L2f
            r0 = r0 ^ 16256(0x3f80, float:2.278E-41)
        L2d:
            r1 = r3
            goto L68
        L2f:
            int r1 = r3 + 1
            r3 = r2[r3]
            int r3 = r3 << 21
            r0 = r0 ^ r3
            if (r0 >= 0) goto L3d
            r2 = -2080896(0xffffffffffe03f80, float:NaN)
            r0 = r0 ^ r2
            goto L68
        L3d:
            int r3 = r1 + 1
            r1 = r2[r1]
            int r4 = r1 << 28
            r0 = r0 ^ r4
            r4 = 266354560(0xfe03f80, float:2.2112565E-29)
            r0 = r0 ^ r4
            if (r1 >= 0) goto L2d
            int r1 = r3 + 1
            r3 = r2[r3]
            if (r3 >= 0) goto L68
            int r3 = r1 + 1
            r1 = r2[r1]
            if (r1 >= 0) goto L2d
            int r1 = r3 + 1
            r3 = r2[r3]
            if (r3 >= 0) goto L68
            int r3 = r1 + 1
            r1 = r2[r1]
            if (r1 >= 0) goto L2d
            int r1 = r3 + 1
            r2 = r2[r3]
            if (r2 < 0) goto L6b
        L68:
            r5.pos = r1
            return r0
        L6b:
            long r0 = r5.zznx()
            int r1 = (int) r0
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.gtm.zzqg.zzoa():int");
    }

    /* JADX WARN: Code restructure failed: missing block: B:37:0x00b0, code lost:
    
        if (r2[r0] >= 0) goto L40;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    private final long zzob() throws java.io.IOException {
        /*
            Method dump skipped, instructions count: 189
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.gtm.zzqg.zzob():long");
    }

    @Override // com.google.android.gms.internal.gtm.zzqe
    final long zznx() throws IOException {
        long j = 0;
        for (int i = 0; i < 64; i += 7) {
            j |= (r3 & 127) << i;
            if ((zzof() & 128) == 0) {
                return j;
            }
        }
        throw zzrk.zzpr();
    }

    private final int zzoc() throws IOException {
        int i = this.pos;
        if (this.limit - i < 4) {
            throw zzrk.zzpp();
        }
        byte[] bArr = this.buffer;
        this.pos = i + 4;
        return ((bArr[i + 3] & 255) << 24) | (bArr[i] & 255) | ((bArr[i + 1] & 255) << 8) | ((bArr[i + 2] & 255) << 16);
    }

    private final long zzod() throws IOException {
        int i = this.pos;
        if (this.limit - i < 8) {
            throw zzrk.zzpp();
        }
        byte[] bArr = this.buffer;
        this.pos = i + 8;
        return ((bArr[i + 7] & 255) << 56) | (bArr[i] & 255) | ((bArr[i + 1] & 255) << 8) | ((bArr[i + 2] & 255) << 16) | ((bArr[i + 3] & 255) << 24) | ((bArr[i + 4] & 255) << 32) | ((bArr[i + 5] & 255) << 40) | ((bArr[i + 6] & 255) << 48);
    }

    @Override // com.google.android.gms.internal.gtm.zzqe
    public final int zzaq(int i) throws zzrk {
        if (i < 0) {
            throw zzrk.zzpq();
        }
        int iZznz = i + zznz();
        int i2 = this.zzawo;
        if (iZznz > i2) {
            throw zzrk.zzpp();
        }
        this.zzawo = iZznz;
        zzoe();
        return i2;
    }

    private final void zzoe() {
        int i = this.limit + this.zzawl;
        this.limit = i;
        int i2 = i - this.zzawm;
        int i3 = this.zzawo;
        if (i2 > i3) {
            int i4 = i2 - i3;
            this.zzawl = i4;
            this.limit = i - i4;
            return;
        }
        this.zzawl = 0;
    }

    @Override // com.google.android.gms.internal.gtm.zzqe
    public final void zzar(int i) {
        this.zzawo = i;
        zzoe();
    }

    @Override // com.google.android.gms.internal.gtm.zzqe
    public final boolean zzny() throws IOException {
        return this.pos == this.limit;
    }

    @Override // com.google.android.gms.internal.gtm.zzqe
    public final int zznz() {
        return this.pos - this.zzawm;
    }

    private final byte zzof() throws IOException {
        int i = this.pos;
        if (i == this.limit) {
            throw zzrk.zzpp();
        }
        byte[] bArr = this.buffer;
        this.pos = i + 1;
        return bArr[i];
    }

    @Override // com.google.android.gms.internal.gtm.zzqe
    public final void zzas(int i) throws IOException {
        if (i >= 0) {
            int i2 = this.limit;
            int i3 = this.pos;
            if (i <= i2 - i3) {
                this.pos = i3 + i;
                return;
            }
        }
        if (i < 0) {
            throw zzrk.zzpq();
        }
        throw zzrk.zzpp();
    }
}
