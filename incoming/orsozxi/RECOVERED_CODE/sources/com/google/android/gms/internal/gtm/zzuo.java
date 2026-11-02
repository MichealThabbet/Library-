package com.google.android.gms.internal.gtm;

import it.piapostolos.android.pinotes.R2;
import java.io.IOException;
import java.nio.BufferOverflowException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.ReadOnlyBufferException;

/* loaded from: classes.dex */
public final class zzuo {
    private final ByteBuffer zzawy;
    private zzqj zzbgz;
    private int zzbha;

    private zzuo(byte[] bArr, int i, int i2) {
        this(ByteBuffer.wrap(bArr, i, i2));
    }

    public static int zzbj(int i) {
        if ((i & (-128)) == 0) {
            return 1;
        }
        if ((i & (-16384)) == 0) {
            return 2;
        }
        if (((-2097152) & i) == 0) {
            return 3;
        }
        return (i & (-268435456)) == 0 ? 4 : 5;
    }

    private zzuo(ByteBuffer byteBuffer) {
        this.zzawy = byteBuffer;
        byteBuffer.order(ByteOrder.LITTLE_ENDIAN);
    }

    public static zzuo zzl(byte[] bArr) {
        return zzk(bArr, 0, bArr.length);
    }

    public static zzuo zzk(byte[] bArr, int i, int i2) {
        return new zzuo(bArr, 0, i2);
    }

    private final zzqj zzrw() throws IOException {
        if (this.zzbgz == null) {
            this.zzbgz = zzqj.zza(this.zzawy);
            this.zzbha = this.zzawy.position();
        } else if (this.zzbha != this.zzawy.position()) {
            this.zzbgz.write(this.zzawy.array(), this.zzbha, this.zzawy.position() - this.zzbha);
            this.zzbha = this.zzawy.position();
        }
        return this.zzbgz;
    }

    public final void zzi(int i, long j) throws IOException {
        zzd(i, 0);
        zzab(j);
    }

    public final void zze(int i, int i2) throws IOException {
        zzd(i, 0);
        if (i2 >= 0) {
            zzcb(i2);
        } else {
            zzab(i2);
        }
    }

    public final void zzb(int i, boolean z) throws IOException {
        zzd(i, 0);
        byte b = z ? (byte) 1 : (byte) 0;
        if (!this.zzawy.hasRemaining()) {
            throw new zzup(this.zzawy.position(), this.zzawy.limit());
        }
        this.zzawy.put(b);
    }

    public final void zza(int i, String str) throws IOException {
        zzd(i, 2);
        try {
            int iZzbj = zzbj(str.length());
            if (iZzbj == zzbj(str.length() * 3)) {
                int iPosition = this.zzawy.position();
                if (this.zzawy.remaining() < iZzbj) {
                    throw new zzup(iPosition + iZzbj, this.zzawy.limit());
                }
                this.zzawy.position(iPosition + iZzbj);
                zzd(str, this.zzawy);
                int iPosition2 = this.zzawy.position();
                this.zzawy.position(iPosition);
                zzcb((iPosition2 - iPosition) - iZzbj);
                this.zzawy.position(iPosition2);
                return;
            }
            zzcb(zza(str));
            zzd(str, this.zzawy);
        } catch (BufferOverflowException e) {
            zzup zzupVar = new zzup(this.zzawy.position(), this.zzawy.limit());
            zzupVar.initCause(e);
            throw zzupVar;
        }
    }

    public final void zza(int i, zzuw zzuwVar) throws IOException {
        zzd(i, 2);
        zzb(zzuwVar);
    }

    public final void zze(int i, zzsk zzskVar) throws IOException {
        zzqj zzqjVarZzrw = zzrw();
        zzqjVarZzrw.zza(i, zzskVar);
        zzqjVarZzrw.flush();
        this.zzbha = this.zzawy.position();
    }

    private static int zza(CharSequence charSequence) {
        int length = charSequence.length();
        int i = 0;
        int i2 = 0;
        while (i2 < length && charSequence.charAt(i2) < 128) {
            i2++;
        }
        int i3 = length;
        while (true) {
            if (i2 >= length) {
                break;
            }
            char cCharAt = charSequence.charAt(i2);
            if (cCharAt < 2048) {
                i3 += (127 - cCharAt) >>> 31;
                i2++;
            } else {
                int length2 = charSequence.length();
                while (i2 < length2) {
                    char cCharAt2 = charSequence.charAt(i2);
                    if (cCharAt2 < 2048) {
                        i += (127 - cCharAt2) >>> 31;
                    } else {
                        i += 2;
                        if (55296 <= cCharAt2 && cCharAt2 <= 57343) {
                            if (Character.codePointAt(charSequence, i2) < 65536) {
                                StringBuilder sb = new StringBuilder(39);
                                sb.append("Unpaired surrogate at index ");
                                sb.append(i2);
                                throw new IllegalArgumentException(sb.toString());
                            }
                            i2++;
                        }
                    }
                    i2++;
                }
                i3 += i;
            }
        }
        if (i3 >= length) {
            return i3;
        }
        long j = i3 + 4294967296L;
        StringBuilder sb2 = new StringBuilder(54);
        sb2.append("UTF-8 length does not fit in int: ");
        sb2.append(j);
        throw new IllegalArgumentException(sb2.toString());
    }

    private static void zzd(CharSequence charSequence, ByteBuffer byteBuffer) {
        int i;
        int i2;
        char cCharAt;
        if (byteBuffer.isReadOnly()) {
            throw new ReadOnlyBufferException();
        }
        int i3 = 0;
        if (byteBuffer.hasArray()) {
            try {
                byte[] bArrArray = byteBuffer.array();
                int iArrayOffset = byteBuffer.arrayOffset() + byteBuffer.position();
                int iRemaining = byteBuffer.remaining();
                int length = charSequence.length();
                int i4 = iRemaining + iArrayOffset;
                while (i3 < length) {
                    int i5 = i3 + iArrayOffset;
                    if (i5 >= i4 || (cCharAt = charSequence.charAt(i3)) >= 128) {
                        break;
                    }
                    bArrArray[i5] = (byte) cCharAt;
                    i3++;
                }
                if (i3 == length) {
                    i = iArrayOffset + length;
                } else {
                    i = iArrayOffset + i3;
                    while (i3 < length) {
                        char cCharAt2 = charSequence.charAt(i3);
                        if (cCharAt2 >= 128 || i >= i4) {
                            if (cCharAt2 < 2048 && i <= i4 - 2) {
                                int i6 = i + 1;
                                bArrArray[i] = (byte) ((cCharAt2 >>> 6) | R2.attr.spMonthNameTextColor);
                                i = i6 + 1;
                                bArrArray[i6] = (byte) ((cCharAt2 & '?') | 128);
                            } else {
                                if ((cCharAt2 >= 55296 && 57343 >= cCharAt2) || i > i4 - 3) {
                                    if (i <= i4 - 4) {
                                        int i7 = i3 + 1;
                                        if (i7 != charSequence.length()) {
                                            char cCharAt3 = charSequence.charAt(i7);
                                            if (Character.isSurrogatePair(cCharAt2, cCharAt3)) {
                                                int codePoint = Character.toCodePoint(cCharAt2, cCharAt3);
                                                int i8 = i + 1;
                                                bArrArray[i] = (byte) ((codePoint >>> 18) | 240);
                                                int i9 = i8 + 1;
                                                bArrArray[i8] = (byte) (((codePoint >>> 12) & 63) | 128);
                                                int i10 = i9 + 1;
                                                bArrArray[i9] = (byte) (((codePoint >>> 6) & 63) | 128);
                                                i = i10 + 1;
                                                bArrArray[i10] = (byte) ((codePoint & 63) | 128);
                                                i3 = i7;
                                            } else {
                                                i3 = i7;
                                            }
                                        }
                                        StringBuilder sb = new StringBuilder(39);
                                        sb.append("Unpaired surrogate at index ");
                                        sb.append(i3 - 1);
                                        throw new IllegalArgumentException(sb.toString());
                                    }
                                    StringBuilder sb2 = new StringBuilder(37);
                                    sb2.append("Failed writing ");
                                    sb2.append(cCharAt2);
                                    sb2.append(" at index ");
                                    sb2.append(i);
                                    throw new ArrayIndexOutOfBoundsException(sb2.toString());
                                }
                                int i11 = i + 1;
                                bArrArray[i] = (byte) ((cCharAt2 >>> '\f') | R2.attr.flow_lastHorizontalBias);
                                int i12 = i11 + 1;
                                bArrArray[i11] = (byte) (((cCharAt2 >>> 6) & 63) | 128);
                                i2 = i12 + 1;
                                bArrArray[i12] = (byte) ((cCharAt2 & '?') | 128);
                            }
                            i3++;
                        } else {
                            i2 = i + 1;
                            bArrArray[i] = (byte) cCharAt2;
                        }
                        i = i2;
                        i3++;
                    }
                }
                byteBuffer.position(i - byteBuffer.arrayOffset());
                return;
            } catch (ArrayIndexOutOfBoundsException e) {
                BufferOverflowException bufferOverflowException = new BufferOverflowException();
                bufferOverflowException.initCause(e);
                throw bufferOverflowException;
            }
        }
        int length2 = charSequence.length();
        while (i3 < length2) {
            char cCharAt4 = charSequence.charAt(i3);
            if (cCharAt4 < 128) {
                byteBuffer.put((byte) cCharAt4);
            } else if (cCharAt4 < 2048) {
                byteBuffer.put((byte) ((cCharAt4 >>> 6) | R2.attr.spMonthNameTextColor));
                byteBuffer.put((byte) ((cCharAt4 & '?') | 128));
            } else if (cCharAt4 < 55296 || 57343 < cCharAt4) {
                byteBuffer.put((byte) ((cCharAt4 >>> '\f') | R2.attr.flow_lastHorizontalBias));
                byteBuffer.put((byte) (((cCharAt4 >>> 6) & 63) | 128));
                byteBuffer.put((byte) ((cCharAt4 & '?') | 128));
            } else {
                int i13 = i3 + 1;
                if (i13 != charSequence.length()) {
                    char cCharAt5 = charSequence.charAt(i13);
                    if (Character.isSurrogatePair(cCharAt4, cCharAt5)) {
                        int codePoint2 = Character.toCodePoint(cCharAt4, cCharAt5);
                        byteBuffer.put((byte) ((codePoint2 >>> 18) | 240));
                        byteBuffer.put((byte) (((codePoint2 >>> 12) & 63) | 128));
                        byteBuffer.put((byte) (((codePoint2 >>> 6) & 63) | 128));
                        byteBuffer.put((byte) ((codePoint2 & 63) | 128));
                        i3 = i13;
                    } else {
                        i3 = i13;
                    }
                }
                StringBuilder sb3 = new StringBuilder(39);
                sb3.append("Unpaired surrogate at index ");
                sb3.append(i3 - 1);
                throw new IllegalArgumentException(sb3.toString());
            }
            i3++;
        }
    }

    public final void zzb(zzuw zzuwVar) throws IOException {
        zzcb(zzuwVar.zzse());
        zzuwVar.zza(this);
    }

    public static int zzd(int i, long j) {
        return zzbb(i) + (((-128) & j) == 0 ? 1 : ((-16384) & j) == 0 ? 2 : ((-2097152) & j) == 0 ? 3 : ((-268435456) & j) == 0 ? 4 : ((-34359738368L) & j) == 0 ? 5 : ((-4398046511104L) & j) == 0 ? 6 : ((-562949953421312L) & j) == 0 ? 7 : ((-72057594037927936L) & j) == 0 ? 8 : (j & Long.MIN_VALUE) == 0 ? 9 : 10);
    }

    public static int zzi(int i, int i2) {
        return zzbb(i) + zzbc(i2);
    }

    public static int zzb(int i, String str) {
        return zzbb(i) + zzda(str);
    }

    public static int zzb(int i, zzuw zzuwVar) {
        int iZzbb = zzbb(i);
        int iZzpe = zzuwVar.zzpe();
        return iZzbb + zzbj(iZzpe) + iZzpe;
    }

    public static int zzbc(int i) {
        if (i >= 0) {
            return zzbj(i);
        }
        return 10;
    }

    public static int zzda(String str) {
        int iZza = zza(str);
        return zzbj(iZza) + iZza;
    }

    public final void zzrx() {
        if (this.zzawy.remaining() != 0) {
            throw new IllegalStateException(String.format("Did not write as much data as expected, %s bytes remaining.", Integer.valueOf(this.zzawy.remaining())));
        }
    }

    private final void zzca(int i) throws IOException {
        byte b = (byte) i;
        if (!this.zzawy.hasRemaining()) {
            throw new zzup(this.zzawy.position(), this.zzawy.limit());
        }
        this.zzawy.put(b);
    }

    public final void zzm(byte[] bArr) throws IOException {
        int length = bArr.length;
        if (this.zzawy.remaining() >= length) {
            this.zzawy.put(bArr, 0, length);
            return;
        }
        throw new zzup(this.zzawy.position(), this.zzawy.limit());
    }

    public final void zzd(int i, int i2) throws IOException {
        zzcb((i << 3) | i2);
    }

    public static int zzbb(int i) {
        return zzbj(i << 3);
    }

    public final void zzcb(int i) throws IOException {
        while ((i & (-128)) != 0) {
            zzca((i & 127) | 128);
            i >>>= 7;
        }
        zzca(i);
    }

    private final void zzab(long j) throws IOException {
        while (((-128) & j) != 0) {
            zzca((((int) j) & 127) | 128);
            j >>>= 7;
        }
        zzca((int) j);
    }

    public final void zzcc(int i) throws IOException {
        if (this.zzawy.remaining() < 4) {
            throw new zzup(this.zzawy.position(), this.zzawy.limit());
        }
        this.zzawy.putInt(i);
    }
}
