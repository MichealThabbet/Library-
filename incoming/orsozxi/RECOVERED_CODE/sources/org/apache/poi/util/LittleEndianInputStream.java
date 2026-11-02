package org.apache.poi.util;

import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;

/* loaded from: classes3.dex */
public class LittleEndianInputStream extends FilterInputStream implements LittleEndianInput {
    public LittleEndianInputStream(InputStream inputStream) {
        super(inputStream);
    }

    @Override // java.io.FilterInputStream, java.io.InputStream, org.apache.poi.util.LittleEndianInput
    public int available() {
        try {
            return super.available();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override // org.apache.poi.util.LittleEndianInput
    public byte readByte() {
        return (byte) readUByte();
    }

    @Override // org.apache.poi.util.LittleEndianInput
    public int readUByte() throws IOException {
        try {
            int i = this.in.read();
            checkEOF(i);
            return i;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override // org.apache.poi.util.LittleEndianInput
    public double readDouble() {
        return Double.longBitsToDouble(readLong());
    }

    @Override // org.apache.poi.util.LittleEndianInput
    public int readInt() throws IOException {
        try {
            int i = this.in.read();
            int i2 = this.in.read();
            int i3 = this.in.read();
            int i4 = this.in.read();
            checkEOF(i | i2 | i3 | i4);
            return (i4 << 24) + (i3 << 16) + (i2 << 8) + (i << 0);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override // org.apache.poi.util.LittleEndianInput
    public long readLong() throws IOException {
        try {
            int i = this.in.read();
            int i2 = this.in.read();
            int i3 = this.in.read();
            int i4 = this.in.read();
            int i5 = this.in.read();
            int i6 = this.in.read();
            int i7 = this.in.read();
            int i8 = this.in.read();
            checkEOF(i | i2 | i3 | i4 | i5 | i6 | i7 | i8);
            return (i8 << 56) + (i7 << 48) + (i6 << 40) + (i5 << 32) + (i4 << 24) + (i3 << 16) + (i2 << 8) + (i << 0);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override // org.apache.poi.util.LittleEndianInput
    public short readShort() {
        return (short) readUShort();
    }

    @Override // org.apache.poi.util.LittleEndianInput
    public int readUShort() throws IOException {
        try {
            int i = this.in.read();
            int i2 = this.in.read();
            checkEOF(i | i2);
            return (i2 << 8) + (i << 0);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static void checkEOF(int i) {
        if (i < 0) {
            throw new RuntimeException("Unexpected end-of-file");
        }
    }

    @Override // org.apache.poi.util.LittleEndianInput
    public void readFully(byte[] bArr) throws IOException {
        readFully(bArr, 0, bArr.length);
    }

    @Override // org.apache.poi.util.LittleEndianInput
    public void readFully(byte[] bArr, int i, int i2) throws IOException {
        int i3 = i2 + i;
        while (i < i3) {
            try {
                int i4 = this.in.read();
                checkEOF(i4);
                bArr[i] = (byte) i4;
                i++;
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
