package org.apache.poi.util;

import java.io.IOException;
import java.io.InputStream;

/* loaded from: classes3.dex */
public class LittleEndian implements LittleEndianConsts {
    public static int ubyteToInt(byte b) {
        return b & 255;
    }

    private LittleEndian() {
    }

    public static short getShort(byte[] bArr, int i) {
        return (short) (((bArr[i + 1] & 255) << 8) + ((bArr[i] & 255) << 0));
    }

    public static int getUShort(byte[] bArr, int i) {
        return ((bArr[i + 1] & 255) << 8) + ((bArr[i] & 255) << 0);
    }

    public static short getShort(byte[] bArr) {
        return getShort(bArr, 0);
    }

    public static int getUShort(byte[] bArr) {
        return getUShort(bArr, 0);
    }

    public static int getInt(byte[] bArr, int i) {
        int i2 = i + 1;
        int i3 = bArr[i] & 255;
        int i4 = i2 + 1;
        int i5 = bArr[i2] & 255;
        return ((bArr[i4 + 1] & 255) << 24) + ((bArr[i4] & 255) << 16) + (i5 << 8) + (i3 << 0);
    }

    public static int getInt(byte[] bArr) {
        return getInt(bArr, 0);
    }

    public static long getUInt(byte[] bArr, int i) {
        return getInt(bArr, i) & (-1);
    }

    public static long getUInt(byte[] bArr) {
        return getUInt(bArr, 0);
    }

    public static long getLong(byte[] bArr, int i) {
        long j = 0;
        for (int i2 = (i + 8) - 1; i2 >= i; i2--) {
            j = (j << 8) | (bArr[i2] & 255);
        }
        return j;
    }

    public static double getDouble(byte[] bArr, int i) {
        return Double.longBitsToDouble(getLong(bArr, i));
    }

    public static void putShort(byte[] bArr, int i, short s) {
        bArr[i] = (byte) ((s >>> 0) & 255);
        bArr[i + 1] = (byte) ((s >>> 8) & 255);
    }

    public static void putByte(byte[] bArr, int i, int i2) {
        bArr[i] = (byte) i2;
    }

    public static void putUShort(byte[] bArr, int i, int i2) {
        bArr[i] = (byte) ((i2 >>> 0) & 255);
        bArr[i + 1] = (byte) ((i2 >>> 8) & 255);
    }

    public static void putShort(byte[] bArr, short s) {
        putShort(bArr, 0, s);
    }

    public static void putInt(byte[] bArr, int i, int i2) {
        int i3 = i + 1;
        bArr[i] = (byte) ((i2 >>> 0) & 255);
        int i4 = i3 + 1;
        bArr[i3] = (byte) ((i2 >>> 8) & 255);
        bArr[i4] = (byte) ((i2 >>> 16) & 255);
        bArr[i4 + 1] = (byte) ((i2 >>> 24) & 255);
    }

    public static void putInt(byte[] bArr, int i) {
        putInt(bArr, 0, i);
    }

    public static void putLong(byte[] bArr, int i, long j) {
        int i2 = i + 8;
        while (i < i2) {
            bArr[i] = (byte) (255 & j);
            j >>= 8;
            i++;
        }
    }

    public static void putDouble(byte[] bArr, int i, double d) {
        putLong(bArr, i, Double.doubleToLongBits(d));
    }

    public static final class BufferUnderrunException extends IOException {
        BufferUnderrunException() {
            super("buffer underrun");
        }
    }

    public static short readShort(InputStream inputStream) throws IOException {
        return (short) readUShort(inputStream);
    }

    public static int readUShort(InputStream inputStream) throws IOException {
        int i = inputStream.read();
        int i2 = inputStream.read();
        if ((i | i2) >= 0) {
            return (i2 << 8) + (i << 0);
        }
        throw new BufferUnderrunException();
    }

    public static int readInt(InputStream inputStream) throws IOException {
        int i = inputStream.read();
        int i2 = inputStream.read();
        int i3 = inputStream.read();
        int i4 = inputStream.read();
        if ((i | i2 | i3 | i4) >= 0) {
            return (i4 << 24) + (i3 << 16) + (i2 << 8) + (i << 0);
        }
        throw new BufferUnderrunException();
    }

    public static long readLong(InputStream inputStream) throws IOException {
        int i = inputStream.read();
        int i2 = inputStream.read();
        int i3 = inputStream.read();
        int i4 = inputStream.read();
        int i5 = inputStream.read();
        int i6 = inputStream.read();
        int i7 = inputStream.read();
        int i8 = inputStream.read();
        if ((i | i2 | i3 | i4 | i5 | i6 | i7 | i8) >= 0) {
            return (i8 << 56) + (i7 << 48) + (i6 << 40) + (i5 << 32) + (i4 << 24) + (i3 << 16) + (i2 << 8) + (i << 0);
        }
        throw new BufferUnderrunException();
    }

    public static int getUnsignedByte(byte[] bArr, int i) {
        return bArr[i] & 255;
    }

    public static byte[] getByteArray(byte[] bArr, int i, int i2) {
        byte[] bArr2 = new byte[i2];
        System.arraycopy(bArr, i, bArr2, 0, i2);
        return bArr2;
    }
}
