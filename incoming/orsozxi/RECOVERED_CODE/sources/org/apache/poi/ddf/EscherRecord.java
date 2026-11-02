package org.apache.poi.ddf;

import java.io.PrintWriter;
import java.util.Collections;
import java.util.List;
import org.apache.http.message.TokenParser;
import org.apache.poi.util.LittleEndian;

/* loaded from: classes3.dex */
public abstract class EscherRecord {
    private short _options;
    private short _recordId;

    public abstract int fillFields(byte[] bArr, int i, EscherRecordFactory escherRecordFactory);

    public abstract String getRecordName();

    public abstract int getRecordSize();

    public abstract int serialize(int i, byte[] bArr, EscherSerializationListener escherSerializationListener);

    protected int fillFields(byte[] bArr, EscherRecordFactory escherRecordFactory) {
        return fillFields(bArr, 0, escherRecordFactory);
    }

    protected int readHeader(byte[] bArr, int i) {
        EscherRecordHeader header = EscherRecordHeader.readHeader(bArr, i);
        this._options = header.getOptions();
        this._recordId = header.getRecordId();
        return header.getRemainingBytes();
    }

    public boolean isContainerRecord() {
        return (this._options & 15) == 15;
    }

    public short getOptions() {
        return this._options;
    }

    public void setOptions(short s) {
        this._options = s;
    }

    public byte[] serialize() {
        byte[] bArr = new byte[getRecordSize()];
        serialize(0, bArr);
        return bArr;
    }

    public int serialize(int i, byte[] bArr) {
        return serialize(i, bArr, new NullEscherSerializationListener());
    }

    public short getRecordId() {
        return this._recordId;
    }

    public void setRecordId(short s) {
        this._recordId = s;
    }

    public List<EscherRecord> getChildRecords() {
        return Collections.emptyList();
    }

    public void setChildRecords(List<EscherRecord> list) {
        throw new UnsupportedOperationException("This record does not support child records.");
    }

    public Object clone() {
        throw new RuntimeException("The class " + getClass().getName() + " needs to define a clone method");
    }

    public EscherRecord getChild(int i) {
        return getChildRecords().get(i);
    }

    public void display(PrintWriter printWriter, int i) {
        for (int i2 = 0; i2 < i * 4; i2++) {
            printWriter.print(TokenParser.SP);
        }
        printWriter.println(getRecordName());
    }

    public short getInstance() {
        return (short) (this._options >> 4);
    }

    static class EscherRecordHeader {
        private short options;
        private short recordId;
        private int remainingBytes;

        private EscherRecordHeader() {
        }

        public static EscherRecordHeader readHeader(byte[] bArr, int i) {
            EscherRecordHeader escherRecordHeader = new EscherRecordHeader();
            escherRecordHeader.options = LittleEndian.getShort(bArr, i);
            escherRecordHeader.recordId = LittleEndian.getShort(bArr, i + 2);
            escherRecordHeader.remainingBytes = LittleEndian.getInt(bArr, i + 4);
            return escherRecordHeader;
        }

        public short getOptions() {
            return this.options;
        }

        public short getRecordId() {
            return this.recordId;
        }

        public int getRemainingBytes() {
            return this.remainingBytes;
        }

        public String toString() {
            return "EscherRecordHeader{options=" + ((int) this.options) + ", recordId=" + ((int) this.recordId) + ", remainingBytes=" + this.remainingBytes + "}";
        }
    }
}
