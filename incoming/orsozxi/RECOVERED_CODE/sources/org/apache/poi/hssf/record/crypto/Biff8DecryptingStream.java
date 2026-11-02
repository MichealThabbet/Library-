package org.apache.poi.hssf.record.crypto;

import java.io.InputStream;
import org.apache.poi.hssf.record.BiffHeaderInput;
import org.apache.poi.util.LittleEndianInput;
import org.apache.poi.util.LittleEndianInputStream;

/* loaded from: classes3.dex */
public final class Biff8DecryptingStream implements BiffHeaderInput, LittleEndianInput {
    private final LittleEndianInput _le;
    private final Biff8RC4 _rc4;

    /* JADX WARN: Multi-variable type inference failed */
    public Biff8DecryptingStream(InputStream inputStream, int i, Biff8EncryptionKey biff8EncryptionKey) {
        this._rc4 = new Biff8RC4(i, biff8EncryptionKey);
        if (inputStream instanceof LittleEndianInput) {
            this._le = (LittleEndianInput) inputStream;
        } else {
            this._le = new LittleEndianInputStream(inputStream);
        }
    }

    @Override // org.apache.poi.hssf.record.BiffHeaderInput
    public int available() {
        return this._le.available();
    }

    @Override // org.apache.poi.hssf.record.BiffHeaderInput
    public int readRecordSID() {
        int uShort = this._le.readUShort();
        this._rc4.skipTwoBytes();
        this._rc4.startRecord(uShort);
        return uShort;
    }

    @Override // org.apache.poi.hssf.record.BiffHeaderInput
    public int readDataSize() {
        int uShort = this._le.readUShort();
        this._rc4.skipTwoBytes();
        return uShort;
    }

    @Override // org.apache.poi.util.LittleEndianInput
    public double readDouble() {
        double dLongBitsToDouble = Double.longBitsToDouble(readLong());
        if (Double.isNaN(dLongBitsToDouble)) {
            throw new RuntimeException("Did not expect to read NaN");
        }
        return dLongBitsToDouble;
    }

    @Override // org.apache.poi.util.LittleEndianInput
    public void readFully(byte[] bArr) {
        readFully(bArr, 0, bArr.length);
    }

    @Override // org.apache.poi.util.LittleEndianInput
    public void readFully(byte[] bArr, int i, int i2) {
        this._le.readFully(bArr, i, i2);
        this._rc4.xor(bArr, i, i2);
    }

    @Override // org.apache.poi.util.LittleEndianInput
    public int readUByte() {
        return this._rc4.xorByte(this._le.readUByte());
    }

    @Override // org.apache.poi.util.LittleEndianInput
    public byte readByte() {
        return (byte) this._rc4.xorByte(this._le.readUByte());
    }

    @Override // org.apache.poi.util.LittleEndianInput
    public int readUShort() {
        return this._rc4.xorShort(this._le.readUShort());
    }

    @Override // org.apache.poi.util.LittleEndianInput
    public short readShort() {
        return (short) this._rc4.xorShort(this._le.readUShort());
    }

    @Override // org.apache.poi.util.LittleEndianInput
    public int readInt() {
        return this._rc4.xorInt(this._le.readInt());
    }

    @Override // org.apache.poi.util.LittleEndianInput
    public long readLong() {
        return this._rc4.xorLong(this._le.readLong());
    }
}
