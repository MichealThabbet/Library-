package com.ibm.icu.text;

import com.ibm.icu.impl.Normalizer2Impl;

/* loaded from: classes2.dex */
public final class UnicodeCompressor implements SCSU {
    private static boolean[] sSingleTagTable = {false, true, true, true, true, true, true, true, true, false, false, true, true, false, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false};
    private static boolean[] sUnicodeTagTable = {false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, false, false, false, false, false, false, false, false, false, false, false, false, false};
    private int fCurrentWindow = 0;
    private int[] fOffsets = new int[8];
    private int fMode = 0;
    private int[] fIndexCount = new int[256];
    private int[] fTimeStamps = new int[8];
    private int fTimeStamp = 0;

    private static boolean isCompressible(int i) {
        return i < 13312 || i >= 57344;
    }

    public UnicodeCompressor() {
        reset();
    }

    public static byte[] compress(String str) {
        return compress(str.toCharArray(), 0, str.length());
    }

    public static byte[] compress(char[] cArr, int i, int i2) {
        UnicodeCompressor unicodeCompressor = new UnicodeCompressor();
        int iMax = Math.max(4, ((i2 - i) * 3) + 1);
        byte[] bArr = new byte[iMax];
        int iCompress = unicodeCompressor.compress(cArr, i, i2, null, bArr, 0, iMax);
        byte[] bArr2 = new byte[iCompress];
        System.arraycopy(bArr, 0, bArr2, 0, iCompress);
        return bArr2;
    }

    public int compress(char[] cArr, int i, int i2, int[] iArr, byte[] bArr, int i3, int i4) {
        int i5;
        int i6;
        if (bArr.length < 4 || i4 - i3 < 4) {
            throw new IllegalArgumentException("byteBuffer.length < 4");
        }
        int i7 = i;
        int i8 = i3;
        loop0: while (i7 < i2 && i8 < i4) {
            int i9 = this.fMode;
            if (i9 == 0) {
                while (i7 < i2 && i8 < i4) {
                    i5 = i7 + 1;
                    char c = cArr[i7];
                    char c2 = i5 < i2 ? cArr[i5] : (char) 65535;
                    if (c < 128) {
                        int i10 = c & 255;
                        if (sSingleTagTable[i10]) {
                            int i11 = i8 + 1;
                            if (i11 >= i4) {
                                i7 = i5 - 1;
                                break;
                            }
                            bArr[i8] = 1;
                            i8 = i11;
                        }
                        i6 = i8 + 1;
                        bArr[i8] = (byte) i10;
                        i7 = i5;
                        i8 = i6;
                    } else {
                        if (inDynamicWindow(c, this.fCurrentWindow)) {
                            i6 = i8 + 1;
                            bArr[i8] = (byte) ((c - this.fOffsets[this.fCurrentWindow]) + 128);
                        } else if (!isCompressible(c)) {
                            if (c2 == 65535 || !isCompressible(c2)) {
                                if (i8 + 3 >= i4) {
                                    i7 = i5 - 1;
                                    break;
                                }
                                int i12 = i8 + 1;
                                bArr[i8] = 15;
                                int i13 = c >>> '\b';
                                int i14 = c & 255;
                                if (sUnicodeTagTable[i13]) {
                                    bArr[i12] = -16;
                                    i12++;
                                }
                                int i15 = i12 + 1;
                                bArr[i12] = (byte) i13;
                                i8 = i15 + 1;
                                bArr[i15] = (byte) i14;
                                this.fMode = 1;
                                i7 = i5;
                            } else {
                                if (i8 + 2 >= i4) {
                                    i7 = i5 - 1;
                                    break;
                                }
                                int i16 = i8 + 1;
                                bArr[i8] = 14;
                                int i17 = i16 + 1;
                                bArr[i16] = (byte) (c >>> '\b');
                                i6 = i17 + 1;
                                bArr[i17] = (byte) (c & 255);
                            }
                        } else {
                            int iFindDynamicWindow = findDynamicWindow(c);
                            if (iFindDynamicWindow != -1) {
                                int i18 = i5 + 1;
                                char c3 = i18 < i2 ? cArr[i18] : (char) 65535;
                                if (inDynamicWindow(c2, iFindDynamicWindow) && inDynamicWindow(c3, iFindDynamicWindow)) {
                                    int i19 = i8 + 1;
                                    if (i19 >= i4) {
                                        i7 = i5 - 1;
                                        break;
                                    }
                                    bArr[i8] = (byte) (iFindDynamicWindow + 16);
                                    i8 = i19 + 1;
                                    bArr[i19] = (byte) ((c - this.fOffsets[iFindDynamicWindow]) + 128);
                                    int[] iArr2 = this.fTimeStamps;
                                    int i20 = this.fTimeStamp + 1;
                                    this.fTimeStamp = i20;
                                    iArr2[iFindDynamicWindow] = i20;
                                    this.fCurrentWindow = iFindDynamicWindow;
                                    i7 = i5;
                                } else {
                                    int i21 = i8 + 1;
                                    if (i21 >= i4) {
                                        i7 = i5 - 1;
                                        break;
                                    }
                                    bArr[i8] = (byte) (iFindDynamicWindow + 1);
                                    i8 = i21 + 1;
                                    bArr[i21] = (byte) ((c - this.fOffsets[iFindDynamicWindow]) + 128);
                                    i7 = i5;
                                }
                            } else {
                                int iFindStaticWindow = findStaticWindow(c);
                                if (iFindStaticWindow == -1 || inStaticWindow(c2, iFindStaticWindow)) {
                                    int iMakeIndex = makeIndex(c);
                                    int[] iArr3 = this.fIndexCount;
                                    iArr3[iMakeIndex] = iArr3[iMakeIndex] + 1;
                                    int i22 = i5 + 1;
                                    char c4 = i22 < i2 ? cArr[i22] : (char) 65535;
                                    if (this.fIndexCount[iMakeIndex] > 1 || (iMakeIndex == makeIndex(c2) && iMakeIndex == makeIndex(c4))) {
                                        if (i8 + 2 >= i4) {
                                            i7 = i5 - 1;
                                            break;
                                        }
                                        int lRDefinedWindow = getLRDefinedWindow();
                                        int i23 = i8 + 1;
                                        bArr[i8] = (byte) (lRDefinedWindow + 24);
                                        int i24 = i23 + 1;
                                        bArr[i23] = (byte) iMakeIndex;
                                        int i25 = i24 + 1;
                                        bArr[i24] = (byte) ((c - sOffsetTable[iMakeIndex]) + 128);
                                        this.fOffsets[lRDefinedWindow] = sOffsetTable[iMakeIndex];
                                        this.fCurrentWindow = lRDefinedWindow;
                                        int[] iArr4 = this.fTimeStamps;
                                        int i26 = this.fTimeStamp + 1;
                                        this.fTimeStamp = i26;
                                        iArr4[lRDefinedWindow] = i26;
                                        i7 = i5;
                                        i8 = i25;
                                    } else {
                                        if (i8 + 3 >= i4) {
                                            i7 = i5 - 1;
                                            break;
                                        }
                                        int i27 = i8 + 1;
                                        bArr[i8] = 15;
                                        int i28 = c >>> '\b';
                                        int i29 = c & 255;
                                        if (sUnicodeTagTable[i28]) {
                                            bArr[i27] = -16;
                                            i27++;
                                        }
                                        int i30 = i27 + 1;
                                        bArr[i27] = (byte) i28;
                                        i8 = i30 + 1;
                                        bArr[i30] = (byte) i29;
                                        this.fMode = 1;
                                        i7 = i5;
                                    }
                                } else {
                                    int i31 = i8 + 1;
                                    if (i31 >= i4) {
                                        i7 = i5 - 1;
                                        break;
                                    }
                                    bArr[i8] = (byte) (iFindStaticWindow + 1);
                                    i8 = i31 + 1;
                                    bArr[i31] = (byte) (c - sOffsets[iFindStaticWindow]);
                                    i7 = i5;
                                }
                            }
                        }
                        i7 = i5;
                        i8 = i6;
                    }
                }
            } else if (i9 != 1) {
                continue;
            } else {
                while (i7 < i2 && i8 < i4) {
                    i5 = i7 + 1;
                    char c5 = cArr[i7];
                    char c6 = i5 < i2 ? cArr[i5] : (char) 65535;
                    if (isCompressible(c5) && (c6 == 65535 || isCompressible(c6))) {
                        if (c5 < 128) {
                            int i32 = c5 & 255;
                            if (c6 == 65535 || c6 >= 128 || sSingleTagTable[i32]) {
                                int i33 = i8 + 1;
                                if (i33 < i4) {
                                    bArr[i8] = 0;
                                    i8 = i33 + 1;
                                    bArr[i33] = (byte) i32;
                                    i7 = i5;
                                }
                            } else {
                                int i34 = i8 + 1;
                                if (i34 < i4) {
                                    int i35 = this.fCurrentWindow;
                                    bArr[i8] = (byte) (i35 + 224);
                                    i8 = i34 + 1;
                                    bArr[i34] = (byte) i32;
                                    int[] iArr5 = this.fTimeStamps;
                                    int i36 = this.fTimeStamp + 1;
                                    this.fTimeStamp = i36;
                                    iArr5[i35] = i36;
                                    this.fMode = 0;
                                    i7 = i5;
                                }
                            }
                            i7 = i5 - 1;
                            break;
                        }
                        int iFindDynamicWindow2 = findDynamicWindow(c5);
                        if (iFindDynamicWindow2 != -1) {
                            if (inDynamicWindow(c6, iFindDynamicWindow2)) {
                                int i37 = i8 + 1;
                                if (i37 < i4) {
                                    bArr[i8] = (byte) (iFindDynamicWindow2 + 224);
                                    i8 = i37 + 1;
                                    bArr[i37] = (byte) ((c5 - this.fOffsets[iFindDynamicWindow2]) + 128);
                                    int[] iArr6 = this.fTimeStamps;
                                    int i38 = this.fTimeStamp + 1;
                                    this.fTimeStamp = i38;
                                    iArr6[iFindDynamicWindow2] = i38;
                                    this.fCurrentWindow = iFindDynamicWindow2;
                                    this.fMode = 0;
                                    i7 = i5;
                                }
                            } else if (i8 + 2 < i4) {
                                int i39 = c5 >>> '\b';
                                int i40 = c5 & 255;
                                if (sUnicodeTagTable[i39]) {
                                    bArr[i8] = -16;
                                    i8++;
                                }
                                int i41 = i8 + 1;
                                bArr[i8] = (byte) i39;
                                i8 = i41 + 1;
                                bArr[i41] = (byte) i40;
                                i7 = i5;
                            }
                            i7 = i5 - 1;
                            break;
                        }
                        int iMakeIndex2 = makeIndex(c5);
                        int[] iArr7 = this.fIndexCount;
                        iArr7[iMakeIndex2] = iArr7[iMakeIndex2] + 1;
                        int i42 = i5 + 1;
                        char c7 = i42 < i2 ? cArr[i42] : (char) 65535;
                        if (this.fIndexCount[iMakeIndex2] > 1 || (iMakeIndex2 == makeIndex(c6) && iMakeIndex2 == makeIndex(c7))) {
                            if (i8 + 2 >= i4) {
                                i7 = i5 - 1;
                                break;
                            }
                            int lRDefinedWindow2 = getLRDefinedWindow();
                            int i43 = i8 + 1;
                            bArr[i8] = (byte) (lRDefinedWindow2 + 232);
                            int i44 = i43 + 1;
                            bArr[i43] = (byte) iMakeIndex2;
                            int i45 = i44 + 1;
                            bArr[i44] = (byte) ((c5 - sOffsetTable[iMakeIndex2]) + 128);
                            this.fOffsets[lRDefinedWindow2] = sOffsetTable[iMakeIndex2];
                            this.fCurrentWindow = lRDefinedWindow2;
                            int[] iArr8 = this.fTimeStamps;
                            int i46 = this.fTimeStamp + 1;
                            this.fTimeStamp = i46;
                            iArr8[lRDefinedWindow2] = i46;
                            this.fMode = 0;
                            i7 = i5;
                            i8 = i45;
                        } else {
                            if (i8 + 2 >= i4) {
                                i7 = i5 - 1;
                                break;
                            }
                            int i47 = c5 >>> '\b';
                            int i48 = c5 & 255;
                            if (sUnicodeTagTable[i47]) {
                                bArr[i8] = -16;
                                i8++;
                            }
                            int i49 = i8 + 1;
                            bArr[i8] = (byte) i47;
                            i8 = i49 + 1;
                            bArr[i49] = (byte) i48;
                            i7 = i5;
                        }
                    } else {
                        if (i8 + 2 >= i4) {
                            i7 = i5 - 1;
                            break;
                        }
                        int i50 = c5 >>> '\b';
                        int i51 = c5 & 255;
                        if (sUnicodeTagTable[i50]) {
                            bArr[i8] = -16;
                            i8++;
                        }
                        int i52 = i8 + 1;
                        bArr[i8] = (byte) i50;
                        i8 = i52 + 1;
                        bArr[i52] = (byte) i51;
                        i7 = i5;
                    }
                }
            }
        }
        if (iArr != null) {
            iArr[0] = i7 - i;
        }
        return i8 - i3;
    }

    public void reset() {
        int[] iArr = this.fOffsets;
        iArr[0] = 128;
        iArr[1] = 192;
        iArr[2] = 1024;
        iArr[3] = 1536;
        iArr[4] = 2304;
        iArr[5] = 12352;
        iArr[6] = 12448;
        iArr[7] = 65280;
        for (int i = 0; i < 8; i++) {
            this.fTimeStamps[i] = 0;
        }
        for (int i2 = 0; i2 <= 255; i2++) {
            this.fIndexCount[i2] = 0;
        }
        this.fTimeStamp = 0;
        this.fCurrentWindow = 0;
        this.fMode = 0;
    }

    private static int makeIndex(int i) {
        int i2;
        if (i >= 192 && i < 320) {
            return 249;
        }
        if (i >= 592 && i < 720) {
            return 250;
        }
        if (i >= 880 && i < 1008) {
            return 251;
        }
        if (i >= 1328 && i < 1424) {
            return 252;
        }
        if (i >= 12352 && i < 12448) {
            return 253;
        }
        if (i >= 12448 && i < 12576) {
            return 254;
        }
        if (i >= 65376 && i < 65439) {
            return 255;
        }
        if (i >= 128 && i < 13312) {
            i2 = i / 128;
        } else {
            if (i < 57344 || i > 65535) {
                return 0;
            }
            i2 = (i - Normalizer2Impl.Hangul.HANGUL_BASE) / 128;
        }
        return i2 & 255;
    }

    private boolean inDynamicWindow(int i, int i2) {
        int[] iArr = this.fOffsets;
        return i >= iArr[i2] && i < iArr[i2] + 128;
    }

    private static boolean inStaticWindow(int i, int i2) {
        return i >= sOffsets[i2] && i < sOffsets[i2] + 128;
    }

    private int findDynamicWindow(int i) {
        for (int i2 = 7; i2 >= 0; i2--) {
            if (inDynamicWindow(i, i2)) {
                int[] iArr = this.fTimeStamps;
                iArr[i2] = iArr[i2] + 1;
                return i2;
            }
        }
        return -1;
    }

    private static int findStaticWindow(int i) {
        for (int i2 = 7; i2 >= 0; i2--) {
            if (inStaticWindow(i, i2)) {
                return i2;
            }
        }
        return -1;
    }

    private int getLRDefinedWindow() {
        int i = -1;
        int i2 = Integer.MAX_VALUE;
        for (int i3 = 7; i3 >= 0; i3--) {
            int[] iArr = this.fTimeStamps;
            if (iArr[i3] < i2) {
                i2 = iArr[i3];
                i = i3;
            }
        }
        return i;
    }
}
