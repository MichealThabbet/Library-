package com.ibm.icu.util;

import com.ibm.icu.impl.Utility;
import java.nio.ByteBuffer;

/* loaded from: classes2.dex */
public class ByteArrayWrapper implements Comparable<ByteArrayWrapper> {
    public byte[] bytes;
    public int size;

    public ByteArrayWrapper() {
    }

    public ByteArrayWrapper(byte[] bArr, int i) {
        if ((bArr == null && i != 0) || i < 0 || (bArr != null && i > bArr.length)) {
            throw new IndexOutOfBoundsException("illegal size: " + i);
        }
        this.bytes = bArr;
        this.size = i;
    }

    public ByteArrayWrapper(ByteBuffer byteBuffer) {
        int iLimit = byteBuffer.limit();
        this.size = iLimit;
        byte[] bArr = new byte[iLimit];
        this.bytes = bArr;
        byteBuffer.get(bArr, 0, iLimit);
    }

    public ByteArrayWrapper ensureCapacity(int i) {
        byte[] bArr = this.bytes;
        if (bArr == null || bArr.length < i) {
            byte[] bArr2 = new byte[i];
            byte[] bArr3 = this.bytes;
            if (bArr3 != null) {
                copyBytes(bArr3, 0, bArr2, 0, this.size);
            }
            this.bytes = bArr2;
        }
        return this;
    }

    public final ByteArrayWrapper set(byte[] bArr, int i, int i2) {
        this.size = 0;
        append(bArr, i, i2);
        return this;
    }

    public final ByteArrayWrapper append(byte[] bArr, int i, int i2) {
        int i3 = i2 - i;
        ensureCapacity(this.size + i3);
        copyBytes(bArr, i, this.bytes, this.size, i3);
        this.size += i3;
        return this;
    }

    public final byte[] releaseBytes() {
        byte[] bArr = this.bytes;
        this.bytes = null;
        this.size = 0;
        return bArr;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < this.size; i++) {
            if (i != 0) {
                sb.append(" ");
            }
            sb.append(Utility.hex(this.bytes[i] & 255, 2));
        }
        return sb.toString();
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        try {
            ByteArrayWrapper byteArrayWrapper = (ByteArrayWrapper) obj;
            if (this.size != byteArrayWrapper.size) {
                return false;
            }
            for (int i = 0; i < this.size; i++) {
                if (this.bytes[i] != byteArrayWrapper.bytes[i]) {
                    return false;
                }
            }
            return true;
        } catch (ClassCastException unused) {
            return false;
        }
    }

    public int hashCode() {
        int i = this.size;
        for (int i2 = 0; i2 < this.size; i2++) {
            i = (i * 37) + this.bytes[i2];
        }
        return i;
    }

    @Override // java.lang.Comparable
    public int compareTo(ByteArrayWrapper byteArrayWrapper) {
        if (this == byteArrayWrapper) {
            return 0;
        }
        int i = this.size;
        int i2 = byteArrayWrapper.size;
        if (i >= i2) {
            i = i2;
        }
        for (int i3 = 0; i3 < i; i3++) {
            byte[] bArr = this.bytes;
            byte b = bArr[i3];
            byte[] bArr2 = byteArrayWrapper.bytes;
            if (b != bArr2[i3]) {
                return (bArr[i3] & 255) - (bArr2[i3] & 255);
            }
        }
        return this.size - byteArrayWrapper.size;
    }

    private static final void copyBytes(byte[] bArr, int i, byte[] bArr2, int i2, int i3) {
        if (i3 >= 64) {
            System.arraycopy(bArr, i, bArr2, i2, i3);
            return;
        }
        while (true) {
            i3--;
            if (i3 < 0) {
                return;
            }
            bArr2[i2] = bArr[i];
            i++;
            i2++;
        }
    }
}
