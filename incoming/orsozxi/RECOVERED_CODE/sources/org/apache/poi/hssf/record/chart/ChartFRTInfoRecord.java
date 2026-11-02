package org.apache.poi.hssf.record.chart;

import org.apache.poi.hssf.record.RecordFormatException;
import org.apache.poi.hssf.record.RecordInputStream;
import org.apache.poi.hssf.record.StandardRecord;
import org.apache.poi.util.HexDump;
import org.apache.poi.util.LittleEndianInput;
import org.apache.poi.util.LittleEndianOutput;

/* loaded from: classes3.dex */
public final class ChartFRTInfoRecord extends StandardRecord {
    public static final short sid = 2128;
    private short grbitFrt;
    private CFRTID[] rgCFRTID;
    private short rt;
    private byte verOriginator;
    private byte verWriter;

    @Override // org.apache.poi.hssf.record.Record
    public short getSid() {
        return sid;
    }

    private static final class CFRTID {
        public static final int ENCODED_SIZE = 4;
        private int rtFirst;
        private int rtLast;

        public CFRTID(LittleEndianInput littleEndianInput) {
            this.rtFirst = littleEndianInput.readShort();
            this.rtLast = littleEndianInput.readShort();
        }

        public void serialize(LittleEndianOutput littleEndianOutput) {
            littleEndianOutput.writeShort(this.rtFirst);
            littleEndianOutput.writeShort(this.rtLast);
        }
    }

    public ChartFRTInfoRecord(RecordInputStream recordInputStream) throws RecordFormatException {
        this.rt = recordInputStream.readShort();
        this.grbitFrt = recordInputStream.readShort();
        this.verOriginator = recordInputStream.readByte();
        this.verWriter = recordInputStream.readByte();
        int i = recordInputStream.readShort();
        this.rgCFRTID = new CFRTID[i];
        for (int i2 = 0; i2 < i; i2++) {
            this.rgCFRTID[i2] = new CFRTID(recordInputStream);
        }
    }

    @Override // org.apache.poi.hssf.record.StandardRecord
    protected int getDataSize() {
        return (this.rgCFRTID.length * 4) + 8;
    }

    @Override // org.apache.poi.hssf.record.StandardRecord
    public void serialize(LittleEndianOutput littleEndianOutput) {
        littleEndianOutput.writeShort(this.rt);
        littleEndianOutput.writeShort(this.grbitFrt);
        littleEndianOutput.writeByte(this.verOriginator);
        littleEndianOutput.writeByte(this.verWriter);
        int length = this.rgCFRTID.length;
        littleEndianOutput.writeShort(length);
        for (int i = 0; i < length; i++) {
            this.rgCFRTID[i].serialize(littleEndianOutput);
        }
    }

    @Override // org.apache.poi.hssf.record.Record
    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("[CHARTFRTINFO]\n");
        stringBuffer.append("    .rt           =");
        stringBuffer.append(HexDump.shortToHex(this.rt));
        stringBuffer.append('\n');
        stringBuffer.append("    .grbitFrt     =");
        stringBuffer.append(HexDump.shortToHex(this.grbitFrt));
        stringBuffer.append('\n');
        stringBuffer.append("    .verOriginator=");
        stringBuffer.append(HexDump.byteToHex(this.verOriginator));
        stringBuffer.append('\n');
        stringBuffer.append("    .verWriter    =");
        stringBuffer.append(HexDump.byteToHex(this.verOriginator));
        stringBuffer.append('\n');
        stringBuffer.append("    .nCFRTIDs     =");
        stringBuffer.append(HexDump.shortToHex(this.rgCFRTID.length));
        stringBuffer.append('\n');
        stringBuffer.append("[/CHARTFRTINFO]\n");
        return stringBuffer.toString();
    }
}
