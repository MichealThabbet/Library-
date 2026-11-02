package com.ibm.icu.text;

/* loaded from: classes2.dex */
public final class UnicodeDecompressor implements SCSU {
    private static final int BUFSIZE = 3;
    private int fCurrentWindow = 0;
    private int[] fOffsets = new int[8];
    private int fMode = 0;
    private byte[] fBuffer = new byte[3];
    private int fBufferLength = 0;

    public UnicodeDecompressor() {
        reset();
    }

    public static String decompress(byte[] bArr) {
        return new String(decompress(bArr, 0, bArr.length));
    }

    public static char[] decompress(byte[] bArr, int i, int i2) {
        UnicodeDecompressor unicodeDecompressor = new UnicodeDecompressor();
        int iMax = Math.max(2, (i2 - i) * 2);
        char[] cArr = new char[iMax];
        int iDecompress = unicodeDecompressor.decompress(bArr, i, i2, null, cArr, 0, iMax);
        char[] cArr2 = new char[iDecompress];
        System.arraycopy(cArr, 0, cArr2, 0, iDecompress);
        return cArr2;
    }

    /* JADX WARN: Failed to find 'out' block for switch in B:26:0x0064. Please report as an issue. */
    /* JADX WARN: Failed to find 'out' block for switch in B:46:0x0114. Please report as an issue. */
    /* JADX WARN: Removed duplicated region for block: B:82:0x0220  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public int decompress(byte[] r17, int r18, int r19, int[] r20, char[] r21, int r22, int r23) {
        /*
            Method dump skipped, instructions count: 1114
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ibm.icu.text.UnicodeDecompressor.decompress(byte[], int, int, int[], char[], int, int):int");
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
        this.fCurrentWindow = 0;
        this.fMode = 0;
        this.fBufferLength = 0;
    }
}
