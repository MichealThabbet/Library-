package com.google.android.gms.internal.gtm;

import it.piapostolos.android.pinotes.R2;
import java.nio.ByteBuffer;

/* loaded from: classes.dex */
final class zzue extends zzub {
    zzue() {
    }

    /* JADX WARN: Code restructure failed: missing block: B:33:0x0061, code lost:
    
        return -1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:49:0x008b, code lost:
    
        return -1;
     */
    @Override // com.google.android.gms.internal.gtm.zzub
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    final int zzb(int r16, byte[] r17, int r18, int r19) {
        /*
            Method dump skipped, instructions count: 217
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.gtm.zzue.zzb(int, byte[], int, int):int");
    }

    @Override // com.google.android.gms.internal.gtm.zzub
    final String zzh(byte[] bArr, int i, int i2) throws zzrk {
        if ((i | i2 | ((bArr.length - i) - i2)) < 0) {
            throw new ArrayIndexOutOfBoundsException(String.format("buffer length=%d, index=%d, size=%d", Integer.valueOf(bArr.length), Integer.valueOf(i), Integer.valueOf(i2)));
        }
        int i3 = i + i2;
        char[] cArr = new char[i2];
        int i4 = 0;
        while (i < i3) {
            byte bZza = zztx.zza(bArr, i);
            if (!zzua.zzd(bZza)) {
                break;
            }
            i++;
            zzua.zza(bZza, cArr, i4);
            i4++;
        }
        int i5 = i4;
        while (i < i3) {
            int i6 = i + 1;
            byte bZza2 = zztx.zza(bArr, i);
            if (zzua.zzd(bZza2)) {
                int i7 = i5 + 1;
                zzua.zza(bZza2, cArr, i5);
                while (i6 < i3) {
                    byte bZza3 = zztx.zza(bArr, i6);
                    if (!zzua.zzd(bZza3)) {
                        break;
                    }
                    i6++;
                    zzua.zza(bZza3, cArr, i7);
                    i7++;
                }
                i = i6;
                i5 = i7;
            } else if (zzua.zze(bZza2)) {
                if (i6 < i3) {
                    zzua.zza(bZza2, zztx.zza(bArr, i6), cArr, i5);
                    i = i6 + 1;
                    i5++;
                } else {
                    throw zzrk.zzpw();
                }
            } else if (zzua.zzf(bZza2)) {
                if (i6 < i3 - 1) {
                    int i8 = i6 + 1;
                    zzua.zza(bZza2, zztx.zza(bArr, i6), zztx.zza(bArr, i8), cArr, i5);
                    i = i8 + 1;
                    i5++;
                } else {
                    throw zzrk.zzpw();
                }
            } else {
                if (i6 >= i3 - 2) {
                    throw zzrk.zzpw();
                }
                int i9 = i6 + 1;
                byte bZza4 = zztx.zza(bArr, i6);
                int i10 = i9 + 1;
                zzua.zza(bZza2, bZza4, zztx.zza(bArr, i9), zztx.zza(bArr, i10), cArr, i5);
                i = i10 + 1;
                i5 = i5 + 1 + 1;
            }
        }
        return new String(cArr, 0, i5);
    }

    @Override // com.google.android.gms.internal.gtm.zzub
    final int zzb(CharSequence charSequence, byte[] bArr, int i, int i2) {
        char c;
        long j;
        long j2;
        long j3;
        int i3;
        char cCharAt;
        long j4 = i;
        long j5 = i2 + j4;
        int length = charSequence.length();
        if (length > i2 || bArr.length - i2 < i) {
            char cCharAt2 = charSequence.charAt(length - 1);
            StringBuilder sb = new StringBuilder(37);
            sb.append("Failed writing ");
            sb.append(cCharAt2);
            sb.append(" at index ");
            sb.append(i + i2);
            throw new ArrayIndexOutOfBoundsException(sb.toString());
        }
        int i4 = 0;
        while (true) {
            c = 128;
            j = 1;
            if (i4 >= length || (cCharAt = charSequence.charAt(i4)) >= 128) {
                break;
            }
            zztx.zza(bArr, j4, (byte) cCharAt);
            i4++;
            j4 = 1 + j4;
        }
        if (i4 == length) {
            return (int) j4;
        }
        while (i4 < length) {
            char cCharAt3 = charSequence.charAt(i4);
            if (cCharAt3 >= c || j4 >= j5) {
                if (cCharAt3 < 2048 && j4 <= j5 - 2) {
                    long j6 = j4 + j;
                    zztx.zza(bArr, j4, (byte) ((cCharAt3 >>> 6) | R2.attr.spMonthNameTextColor));
                    zztx.zza(bArr, j6, (byte) ((cCharAt3 & '?') | 128));
                    j2 = j6 + j;
                    j3 = j;
                } else {
                    if ((cCharAt3 >= 55296 && 57343 >= cCharAt3) || j4 > j5 - 3) {
                        if (j4 <= j5 - 4) {
                            int i5 = i4 + 1;
                            if (i5 != length) {
                                char cCharAt4 = charSequence.charAt(i5);
                                if (Character.isSurrogatePair(cCharAt3, cCharAt4)) {
                                    int codePoint = Character.toCodePoint(cCharAt3, cCharAt4);
                                    long j7 = j4 + 1;
                                    zztx.zza(bArr, j4, (byte) ((codePoint >>> 18) | 240));
                                    long j8 = j7 + 1;
                                    zztx.zza(bArr, j7, (byte) (((codePoint >>> 12) & 63) | 128));
                                    long j9 = j8 + 1;
                                    zztx.zza(bArr, j8, (byte) (((codePoint >>> 6) & 63) | 128));
                                    j3 = 1;
                                    j2 = j9 + 1;
                                    zztx.zza(bArr, j9, (byte) ((codePoint & 63) | 128));
                                    i4 = i5;
                                } else {
                                    i4 = i5;
                                }
                            }
                            throw new zzud(i4 - 1, length);
                        }
                        if (55296 <= cCharAt3 && cCharAt3 <= 57343 && ((i3 = i4 + 1) == length || !Character.isSurrogatePair(cCharAt3, charSequence.charAt(i3)))) {
                            throw new zzud(i4, length);
                        }
                        StringBuilder sb2 = new StringBuilder(46);
                        sb2.append("Failed writing ");
                        sb2.append(cCharAt3);
                        sb2.append(" at index ");
                        sb2.append(j4);
                        throw new ArrayIndexOutOfBoundsException(sb2.toString());
                    }
                    long j10 = j4 + j;
                    zztx.zza(bArr, j4, (byte) ((cCharAt3 >>> '\f') | R2.attr.flow_lastHorizontalBias));
                    long j11 = j10 + j;
                    zztx.zza(bArr, j10, (byte) (((cCharAt3 >>> 6) & 63) | 128));
                    zztx.zza(bArr, j11, (byte) ((cCharAt3 & '?') | 128));
                    j2 = j11 + 1;
                    j3 = 1;
                }
                i4++;
                c = 128;
                long j12 = j3;
                j4 = j2;
                j = j12;
            } else {
                long j13 = j4 + j;
                zztx.zza(bArr, j4, (byte) cCharAt3);
                j3 = j;
                j2 = j13;
            }
            i4++;
            c = 128;
            long j122 = j3;
            j4 = j2;
            j = j122;
        }
        return (int) j4;
    }

    @Override // com.google.android.gms.internal.gtm.zzub
    final void zzb(CharSequence charSequence, ByteBuffer byteBuffer) {
        char c;
        long j;
        int i;
        char cCharAt;
        long jZzb = zztx.zzb(byteBuffer);
        long jPosition = byteBuffer.position() + jZzb;
        long jLimit = byteBuffer.limit() + jZzb;
        int length = charSequence.length();
        if (length > jLimit - jPosition) {
            char cCharAt2 = charSequence.charAt(length - 1);
            int iLimit = byteBuffer.limit();
            StringBuilder sb = new StringBuilder(37);
            sb.append("Failed writing ");
            sb.append(cCharAt2);
            sb.append(" at index ");
            sb.append(iLimit);
            throw new ArrayIndexOutOfBoundsException(sb.toString());
        }
        int i2 = 0;
        while (true) {
            c = 128;
            if (i2 >= length || (cCharAt = charSequence.charAt(i2)) >= 128) {
                break;
            }
            zztx.zza(jPosition, (byte) cCharAt);
            i2++;
            jPosition++;
        }
        if (i2 == length) {
            byteBuffer.position((int) (jPosition - jZzb));
            return;
        }
        while (i2 < length) {
            char cCharAt3 = charSequence.charAt(i2);
            if (cCharAt3 < c && jPosition < jLimit) {
                zztx.zza(jPosition, (byte) cCharAt3);
                jPosition++;
                j = jZzb;
            } else if (cCharAt3 >= 2048 || jPosition > jLimit - 2) {
                j = jZzb;
                if ((cCharAt3 >= 55296 && 57343 >= cCharAt3) || jPosition > jLimit - 3) {
                    if (jPosition <= jLimit - 4) {
                        int i3 = i2 + 1;
                        if (i3 != length) {
                            char cCharAt4 = charSequence.charAt(i3);
                            if (Character.isSurrogatePair(cCharAt3, cCharAt4)) {
                                int codePoint = Character.toCodePoint(cCharAt3, cCharAt4);
                                long j2 = jPosition + 1;
                                zztx.zza(jPosition, (byte) ((codePoint >>> 18) | 240));
                                long j3 = j2 + 1;
                                zztx.zza(j2, (byte) (((codePoint >>> 12) & 63) | 128));
                                long j4 = j3 + 1;
                                zztx.zza(j3, (byte) (((codePoint >>> 6) & 63) | 128));
                                long j5 = j4 + 1;
                                zztx.zza(j4, (byte) ((codePoint & 63) | 128));
                                i2 = i3;
                                jPosition = j5;
                            } else {
                                i2 = i3;
                            }
                        }
                        throw new zzud(i2 - 1, length);
                    }
                    if (55296 <= cCharAt3 && cCharAt3 <= 57343 && ((i = i2 + 1) == length || !Character.isSurrogatePair(cCharAt3, charSequence.charAt(i)))) {
                        throw new zzud(i2, length);
                    }
                    StringBuilder sb2 = new StringBuilder(46);
                    sb2.append("Failed writing ");
                    sb2.append(cCharAt3);
                    sb2.append(" at index ");
                    sb2.append(jPosition);
                    throw new ArrayIndexOutOfBoundsException(sb2.toString());
                }
                long j6 = jPosition + 1;
                zztx.zza(jPosition, (byte) ((cCharAt3 >>> '\f') | R2.attr.flow_lastHorizontalBias));
                long j7 = j6 + 1;
                zztx.zza(j6, (byte) (((cCharAt3 >>> 6) & 63) | 128));
                zztx.zza(j7, (byte) ((cCharAt3 & '?') | 128));
                jPosition = j7 + 1;
            } else {
                j = jZzb;
                long j8 = jPosition + 1;
                zztx.zza(jPosition, (byte) ((cCharAt3 >>> 6) | R2.attr.spMonthNameTextColor));
                zztx.zza(j8, (byte) ((cCharAt3 & '?') | 128));
                jPosition = j8 + 1;
            }
            i2++;
            jZzb = j;
            c = 128;
        }
        byteBuffer.position((int) (jPosition - jZzb));
    }

    private static int zza(byte[] bArr, int i, long j, int i2) {
        if (i2 == 0) {
            return zztz.zzbx(i);
        }
        if (i2 == 1) {
            return zztz.zzr(i, zztx.zza(bArr, j));
        }
        if (i2 == 2) {
            return zztz.zzc(i, zztx.zza(bArr, j), zztx.zza(bArr, j + 1));
        }
        throw new AssertionError();
    }
}
