package org.apache.poi.hssf.record;

import java.io.ByteArrayOutputStream;
import org.apache.poi.util.HexDump;
import org.apache.poi.util.LittleEndianInput;
import org.apache.poi.util.LittleEndianOutput;
import org.apache.poi.util.LittleEndianOutputStream;

/* loaded from: classes3.dex */
public abstract class SubRecord {
    public abstract Object clone();

    protected abstract int getDataSize();

    public boolean isTerminating() {
        return false;
    }

    public abstract void serialize(LittleEndianOutput littleEndianOutput);

    protected SubRecord() {
    }

    public static SubRecord createSubRecord(LittleEndianInput littleEndianInput, int i) {
        int uShort = littleEndianInput.readUShort();
        int uShort2 = littleEndianInput.readUShort();
        if (uShort == 0) {
            return new EndSubRecord(littleEndianInput, uShort2);
        }
        if (uShort == 6) {
            return new GroupMarkerSubRecord(littleEndianInput, uShort2);
        }
        if (uShort == 9) {
            return new EmbeddedObjectRefSubRecord(littleEndianInput, uShort2);
        }
        if (uShort == 19) {
            return new LbsDataSubRecord(littleEndianInput, uShort2, i);
        }
        if (uShort == 21) {
            return new CommonObjectDataSubRecord(littleEndianInput, uShort2);
        }
        if (uShort == 12) {
            return new FtCblsSubRecord(littleEndianInput, uShort2);
        }
        if (uShort == 13) {
            return new NoteStructureSubRecord(littleEndianInput, uShort2);
        }
        return new UnknownSubRecord(littleEndianInput, uShort, uShort2);
    }

    public byte[] serialize() {
        int dataSize = getDataSize() + 4;
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(dataSize);
        serialize(new LittleEndianOutputStream(byteArrayOutputStream));
        if (byteArrayOutputStream.size() != dataSize) {
            throw new RuntimeException("write size mismatch");
        }
        return byteArrayOutputStream.toByteArray();
    }

    private static final class UnknownSubRecord extends SubRecord {
        private final byte[] _data;
        private final int _sid;

        @Override // org.apache.poi.hssf.record.SubRecord
        public Object clone() {
            return this;
        }

        public UnknownSubRecord(LittleEndianInput littleEndianInput, int i, int i2) {
            this._sid = i;
            byte[] bArr = new byte[i2];
            littleEndianInput.readFully(bArr);
            this._data = bArr;
        }

        @Override // org.apache.poi.hssf.record.SubRecord
        protected int getDataSize() {
            return this._data.length;
        }

        @Override // org.apache.poi.hssf.record.SubRecord
        public void serialize(LittleEndianOutput littleEndianOutput) {
            littleEndianOutput.writeShort(this._sid);
            littleEndianOutput.writeShort(this._data.length);
            littleEndianOutput.write(this._data);
        }

        public String toString() {
            StringBuffer stringBuffer = new StringBuffer(64);
            stringBuffer.append(getClass().getName());
            stringBuffer.append(" [");
            stringBuffer.append("sid=");
            stringBuffer.append(HexDump.shortToHex(this._sid));
            stringBuffer.append(" size=");
            stringBuffer.append(this._data.length);
            stringBuffer.append(" : ");
            stringBuffer.append(HexDump.toHex(this._data));
            stringBuffer.append("]\n");
            return stringBuffer.toString();
        }
    }
}
