package com.google.android.gms.internal.gtm;

import java.io.IOException;

/* loaded from: classes.dex */
public final class zzun {
    private final byte[] buffer;
    private int zzawf;
    private int zzawl;
    private int zzawn;
    private final int zzbgu;
    private final int zzbgv;
    private int zzbgw;
    private int zzbgx;
    private zzqe zzbgy;
    private int zzawo = Integer.MAX_VALUE;
    private int zzawg = 64;
    private int zzawh = 67108864;

    public static zzun zzk(byte[] bArr) {
        return zzj(bArr, 0, bArr.length);
    }

    public static zzun zzj(byte[] bArr, int i, int i2) {
        return new zzun(bArr, 0, i2);
    }

    public final int zzni() throws IOException {
        if (this.zzbgx == this.zzbgw) {
            this.zzawn = 0;
            return 0;
        }
        int iZzoa = zzoa();
        this.zzawn = iZzoa;
        if (iZzoa != 0) {
            return iZzoa;
        }
        throw new zzuv("Protocol message contained an invalid tag (zero).");
    }

    public final void zzan(int i) throws zzuv {
        if (this.zzawn != i) {
            throw new zzuv("Protocol message end-group tag did not match expected tag.");
        }
    }

    public final boolean zzao(int i) throws IOException {
        int iZzni;
        int i2 = i & 7;
        if (i2 == 0) {
            zzoa();
            return true;
        }
        if (i2 == 1) {
            zzof();
            zzof();
            zzof();
            zzof();
            zzof();
            zzof();
            zzof();
            zzof();
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
                zzoc();
                return true;
            }
            throw new zzuv("Protocol message tag had invalid wire type.");
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

    public final boolean zzno() throws IOException {
        return zzoa() != 0;
    }

    public final String readString() throws IOException {
        int iZzoa = zzoa();
        if (iZzoa < 0) {
            throw zzuv.zzsb();
        }
        if (iZzoa > this.zzbgw - this.zzbgx) {
            throw zzuv.zzsa();
        }
        String str = new String(this.buffer, this.zzbgx, iZzoa, zzuu.UTF_8);
        this.zzbgx += iZzoa;
        return str;
    }

    public final void zza(zzuw zzuwVar, int i) throws IOException {
        int i2 = this.zzawf;
        if (i2 >= this.zzawg) {
            throw zzuv.zzsd();
        }
        this.zzawf = i2 + 1;
        zzuwVar.zza(this);
        zzan((i << 3) | 4);
        this.zzawf--;
    }

    public final void zza(zzuw zzuwVar) throws IOException {
        int iZzoa = zzoa();
        if (this.zzawf >= this.zzawg) {
            throw zzuv.zzsd();
        }
        int iZzaq = zzaq(iZzoa);
        this.zzawf++;
        zzuwVar.zza(this);
        zzan(0);
        this.zzawf--;
        zzar(iZzaq);
    }

    public final int zzoa() throws IOException {
        int i;
        byte bZzof = zzof();
        if (bZzof >= 0) {
            return bZzof;
        }
        int i2 = bZzof & 127;
        byte bZzof2 = zzof();
        if (bZzof2 >= 0) {
            i = bZzof2 << 7;
        } else {
            i2 |= (bZzof2 & 127) << 7;
            byte bZzof3 = zzof();
            if (bZzof3 >= 0) {
                i = bZzof3 << 14;
            } else {
                i2 |= (bZzof3 & 127) << 14;
                byte bZzof4 = zzof();
                if (bZzof4 < 0) {
                    int i3 = i2 | ((bZzof4 & 127) << 21);
                    byte bZzof5 = zzof();
                    int i4 = i3 | (bZzof5 << 28);
                    if (bZzof5 >= 0) {
                        return i4;
                    }
                    for (int i5 = 0; i5 < 5; i5++) {
                        if (zzof() >= 0) {
                            return i4;
                        }
                    }
                    throw zzuv.zzsc();
                }
                i = bZzof4 << 21;
            }
        }
        return i2 | i;
    }

    public final long zzob() throws IOException {
        long j = 0;
        for (int i = 0; i < 64; i += 7) {
            j |= (r3 & 127) << i;
            if ((zzof() & 128) == 0) {
                return j;
            }
        }
        throw zzuv.zzsc();
    }

    public final int zzoc() throws IOException {
        return (zzof() & 255) | ((zzof() & 255) << 8) | ((zzof() & 255) << 16) | ((zzof() & 255) << 24);
    }

    private zzun(byte[] bArr, int i, int i2) {
        this.buffer = bArr;
        this.zzbgu = i;
        int i3 = i2 + i;
        this.zzbgw = i3;
        this.zzbgv = i3;
        this.zzbgx = i;
    }

    private final zzqe zzru() throws IOException {
        if (this.zzbgy == null) {
            this.zzbgy = zzqe.zzd(this.buffer, this.zzbgu, this.zzbgv);
        }
        int iZznz = this.zzbgy.zznz();
        int i = this.zzbgx - this.zzbgu;
        if (iZznz > i) {
            throw new IOException(String.format("CodedInputStream read ahead of CodedInputByteBufferNano: %s > %s", Integer.valueOf(iZznz), Integer.valueOf(i)));
        }
        this.zzbgy.zzas(i - iZznz);
        this.zzbgy.zzap(this.zzawg - this.zzawf);
        return this.zzbgy;
    }

    public final <T extends zzrc<T, ?>> T zza(zzsu<T> zzsuVar) throws IOException {
        try {
            T t = (T) zzru().zza(zzsuVar, zzqp.zzor());
            zzao(this.zzawn);
            return t;
        } catch (zzrk e) {
            throw new zzuv("", e);
        }
    }

    public final int zzaq(int i) throws zzuv {
        if (i < 0) {
            throw zzuv.zzsb();
        }
        int i2 = i + this.zzbgx;
        int i3 = this.zzawo;
        if (i2 > i3) {
            throw zzuv.zzsa();
        }
        this.zzawo = i2;
        zzoe();
        return i3;
    }

    private final void zzoe() {
        int i = this.zzbgw + this.zzawl;
        this.zzbgw = i;
        int i2 = this.zzawo;
        if (i > i2) {
            int i3 = i - i2;
            this.zzawl = i3;
            this.zzbgw = i - i3;
            return;
        }
        this.zzawl = 0;
    }

    public final void zzar(int i) {
        this.zzawo = i;
        zzoe();
    }

    public final int zzrv() {
        int i = this.zzawo;
        if (i == Integer.MAX_VALUE) {
            return -1;
        }
        return i - this.zzbgx;
    }

    public final int getPosition() {
        return this.zzbgx - this.zzbgu;
    }

    public final byte[] zzt(int i, int i2) {
        if (i2 == 0) {
            return zzuz.zzbhw;
        }
        byte[] bArr = new byte[i2];
        System.arraycopy(this.buffer, this.zzbgu + i, bArr, 0, i2);
        return bArr;
    }

    public final void zzbz(int i) {
        zzu(i, this.zzawn);
    }

    final void zzu(int i, int i2) {
        int i3 = this.zzbgx;
        int i4 = this.zzbgu;
        if (i > i3 - i4) {
            int i5 = this.zzbgx - this.zzbgu;
            StringBuilder sb = new StringBuilder(50);
            sb.append("Position ");
            sb.append(i);
            sb.append(" is beyond current ");
            sb.append(i5);
            throw new IllegalArgumentException(sb.toString());
        }
        if (i < 0) {
            StringBuilder sb2 = new StringBuilder(24);
            sb2.append("Bad position ");
            sb2.append(i);
            throw new IllegalArgumentException(sb2.toString());
        }
        this.zzbgx = i4 + i;
        this.zzawn = i2;
    }

    private final byte zzof() throws IOException {
        int i = this.zzbgx;
        if (i == this.zzbgw) {
            throw zzuv.zzsa();
        }
        byte[] bArr = this.buffer;
        this.zzbgx = i + 1;
        return bArr[i];
    }

    private final void zzas(int i) throws IOException {
        if (i < 0) {
            throw zzuv.zzsb();
        }
        int i2 = this.zzbgx;
        int i3 = i2 + i;
        int i4 = this.zzawo;
        if (i3 > i4) {
            zzas(i4 - i2);
            throw zzuv.zzsa();
        }
        if (i <= this.zzbgw - i2) {
            this.zzbgx = i2 + i;
            return;
        }
        throw zzuv.zzsa();
    }
}
